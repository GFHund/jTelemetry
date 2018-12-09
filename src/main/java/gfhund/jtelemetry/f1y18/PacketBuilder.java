package gfhund.jtelemetry.f1y18;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class PacketBuilder{
    public static AbstractPacket parseUDPPacket(byte[] rawPacket) throws F1Y2018ParseException{
        
        Header packetHeader = getHeader(rawPacket);
        if(packetHeader == null){
            throw new F1Y2018ParseException();
        }
        switch(packetHeader.getPacketId()){
            case 0:
            return getMotionData(rawPacket, packetHeader);
            case 1:
            return getSessionData(rawPacket, packetHeader);
            case 2:
            return getLapData(rawPacket, packetHeader);
            case 3:
            return getEventData(rawPacket, packetHeader);
            case 4:
            return getParticipantsData(rawPacket, packetHeader);
            case 5:
            return getCarSetupData(rawPacket, packetHeader);
            case 6:
            return getTelemetryData(rawPacket, packetHeader);
            case 7:
            return getPacketCarStatusData(rawPacket, packetHeader);
            default:
            return null;
        }
        
    }

    private static Header getHeader(byte[] packet){
        if(packet.length < 22){
            return null;
        }
        Header ret = new Header();
        ret.setPacketFormat(getShort(packet, 0));
        ret.setPacketVersion(packet[2]);
        ret.setPacketId(packet[3]);

        ret.setSessionUid(getLong(packet,4));

        ret.setSessionTime(getFloat(packet, 12));

        ret.setFrameIdentifier(getInt(packet,16));
        ret.setPlayerCarIndex(packet[20]);
        return ret;

    }

    private static PacketMotionData getMotionData(byte[] rawData,Header head){
        PacketMotionData packet = new PacketMotionData();//21
        packet.setHeader(head);
        for(int i=0;i<20;i++){//20*60
            CarMotionData motionData = getCarMotionData(rawData, i*CarMotionData.getSize()+Header.getSize());
            packet.setCarMotionData(i, motionData);
        }
        for(int i=0;i<4;i++){
            int offset = 20 * CarMotionData.getSize() + Header.getSize();
            packet.setSuspensionPosition(i, getFloat(rawData,i*4+offset));
        }
        for(int i=0;i<4;i++){
            int offset = 4*4+20 * CarMotionData.getSize() + Header.getSize();
            packet.setSuspensionVelocity(i, getFloat(rawData,i*4+offset));
        }
        for(int i=0;i<4;i++){
            int offset = 2*4*4+20 * CarMotionData.getSize() + Header.getSize();
            packet.setSuspensionAcceleration(i, getFloat(rawData,i*4+offset));
        }
        for(int i=0;i<4;i++){
            int offset = 3*4*4+20 * CarMotionData.getSize() + Header.getSize();
            packet.setWheelSpeed(i, getFloat(rawData,i*4+offset));
        }
        for(int i=0;i<4;i++){
            int offset = 4*4*4+20 * CarMotionData.getSize() + Header.getSize();
            packet.setWheelSlip(i, getFloat(rawData, i*4+offset));
        }


        packet.setLocalVelocityX(getFloat(rawData,1301));
        packet.setLocalVelocityY(getFloat(rawData,1305));
        packet.setLocalVelocityZ(getFloat(rawData,1309));

        packet.setAngularVelocityX(getFloat(rawData,1313));
        packet.setAngularVelocityY(getFloat(rawData,1317));
        packet.setAngularVelocityZ(getFloat(rawData,1321));

        packet.setAngularAccelerationX(getFloat(rawData,1325));
        packet.setAngularAccelerationY(getFloat(rawData,1329));
        packet.setAngularAccelerationZ(getFloat(rawData,1333));
        packet.setFrontWheelsAngle(getFloat(rawData,1337));

        return packet;
    }
    private static CarMotionData getCarMotionData(byte[] rawData,int offset){
        CarMotionData carMotion = new CarMotionData();

        carMotion.setWorldPositionX(getFloat(rawData,offset));
        carMotion.setWorldPositionY(getFloat(rawData,offset + 4));
        carMotion.setWorldPositionZ(getFloat(rawData,offset + 8));

        carMotion.setWorldVelocityX(getFloat(rawData,offset + 12));
        carMotion.setWorldVelocityY(getFloat(rawData,offset + 16));
        carMotion.setWorldVelocityZ(getFloat(rawData,offset + 20));

        carMotion.setWorldForwardDirX(getShort(rawData,offset + 24));
        carMotion.setWorldForwardDirY(getShort(rawData,offset + 26));
        carMotion.setWorldForwardDirZ(getShort(rawData,offset + 28));

        carMotion.setWordRightDirX(getShort(rawData,offset + 30));
        carMotion.setWorldRightDirY(getShort(rawData,offset + 32));
        carMotion.setWorldRightDirZ(getShort(rawData,offset + 34));

        carMotion.setGForceLateral(getFloat(rawData,offset + 36));
        carMotion.setGForceLongitudinal(getFloat(rawData,offset + 40));
        carMotion.setGForceVertical(getFloat(rawData,offset + 44));
        return carMotion;
    }

    private static PacketSessionData getSessionData(byte[] rawData,Header head){
        return null;
    }
    private static PacketLapData getLapData(byte[] rawData,Header head){
        PacketLapData packet = new PacketLapData();//21
        packet.setHeader(head);
        for(int i=0;i<20;i++){
            LapData lapData = getCarLapData(rawData, Header.getSize()+i*40);
            packet.setLapData(i, lapData);
        }
        return packet;
    }
    private static LapData getCarLapData(byte[]rawData,int offset){
        LapData lapData = new LapData();

        lapData.setLastLapTime(getFloat(rawData,    offset));
        lapData.setCurrentLapTime(getFloat(rawData, offset + 4));
        lapData.setBestLapTime(getFloat(rawData,    offset + 8));
        lapData.setSector1Time(getFloat(rawData,    offset + 12));
        lapData.setSector2Time(getFloat(rawData,    offset + 16));
        lapData.setLapDistance(getFloat(rawData,    offset + 20));
        lapData.setTotalDistance(getFloat(rawData,  offset + 24));
        lapData.setSafetyCarDelta(getFloat(rawData, offset + 28));
        lapData.setCarPosition(rawData[offset+32]);
        lapData.setCurrentLapNum(rawData[offset+33]);
        lapData.setPitStatus(rawData[offset+34]);
        lapData.setSector(rawData[offset+35]);
        lapData.setCurrentLapInvalid(rawData[offset+36]);
        lapData.setPenalties(rawData[offset+37]);
        lapData.setGridPosition(rawData[offset+38]);
        lapData.setDriverStatus(rawData[offset+39]);
        lapData.setResultStatus(rawData[offset+40]);
        return lapData;
    }
    private static PacketEventData getEventData(byte[] rawData,Header head){
        return null;
    }
    private static PacketParticipantsData getParticipantsData(byte[] rawData,Header head){
        return null;
    }
    private static PacketCarSetupData getCarSetupData(byte[] rawData,Header head){
        return null;
    }
    private static PacketCarTelemetryData getTelemetryData(byte[] rawData,Header head){
        return null;
    }
    private static PacketCarStatusData getPacketCarStatusData(byte[] rawData,Header head){
        PacketCarStatusData ret = new PacketCarStatusData();
        ret.setHeader(head);
        for(int i = 0;i<20;i++){
            CarStatusData data = getCarStatusData(rawData, Header.getSize()+ i*52);
            ret.setCarStatusData(i, data);
        }
        return ret;
    }
    private static CarStatusData getCarStatusData(byte[] rawData,int offset){
        CarStatusData ret = new CarStatusData();
        ret.setTractionControl(rawData[offset]);
        ret.setAntiLockBrakes(rawData[offset+1]);
        ret.setFuelMix(rawData[offset+2]);
        ret.setFrontBrakeBias(rawData[offset+3]);
        ret.setPitLimiterStatus(rawData[offset+4]);
        ret.setFuelInTank(getFloat(rawData, offset+5));
        ret.setFuelCapacity(getFloat(rawData,offset+9));
        ret.setMaxRPM(getShort(rawData, offset+13));
        ret.setIdleRPM(getShort(rawData, offset+15));
        ret.setMaxGears(rawData[offset+17]);
        ret.setDrsAllowed(rawData[offset+18]);
        byte[] wheelWear = new byte[4];
        wheelWear[0] = rawData[offset+19];
        wheelWear[1] = rawData[offset+20];
        wheelWear[2] = rawData[offset+21];
        wheelWear[3] = rawData[offset+22];
        ret.setTyresWear(wheelWear);
        ret.setTyreCompound(rawData[offset+23]);
        byte[] tyresDamage = new byte[4];
        tyresDamage[0] = rawData[offset+24];
        tyresDamage[1] = rawData[offset+25];
        tyresDamage[2] = rawData[offset+26];
        tyresDamage[3] = rawData[offset+27];
        ret.setTyresDamage(tyresDamage);
        ret.setFrontLeftWingDamage(rawData[offset+28]);
        ret.setFrontRightWingDamage(rawData[offset+29]);
        ret.setRearWingDamage(rawData[offset+30]);
        ret.setEngineDamage(rawData[offset+31]);
        ret.setGearBoxDamage(rawData[offset+32]);
        ret.setExhaustDamage(rawData[offset+33]);
        ret.setVehicleFiaFlags(rawData[offset+34]);
        ret.setErsStoreEnergy(getFloat(rawData, offset+35));
        ret.setErsDeployMode(rawData[offset+39]);
        ret.setErsHarvestedThisLapMGUK(getFloat(rawData, offset+40));
        ret.setErsHarvestedThisLapMGUH(getFloat(rawData, offset+44));
        ret.setErsDeployedThisLap(getFloat(rawData, offset+48));
        
        return ret;
    }
    
    private static int getInt(byte[] raw,int position){
        ByteBuffer converter = ByteBuffer.wrap(raw,position,4);
        converter = converter.order(ByteOrder.LITTLE_ENDIAN);
        return converter.getInt();
    }
    private static short getShort(byte[] raw,int position){
        ByteBuffer converter = ByteBuffer.wrap(raw,position,2);
        converter = converter.order(ByteOrder.LITTLE_ENDIAN);
        return converter.getShort();
    }
    private static float getFloat(byte[] raw,int position){
        ByteBuffer converter = ByteBuffer.wrap(raw,position,4);
        converter = converter.order(ByteOrder.LITTLE_ENDIAN);
        return converter.getFloat();
    }
    private static long getLong(byte[] raw,int position){
        ByteBuffer converter = ByteBuffer.wrap(raw,position,8);
        converter = converter.order(ByteOrder.LITTLE_ENDIAN);
        return converter.getLong();
    }

}
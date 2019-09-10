package gfhund.jtelemetry.f1common;

import gfhund.jtelemetry.commontelemetry.AbstractPacket;
import gfhund.jtelemetry.commontelemetry.AbstractSubPackage;
import gfhund.jtelemetry.f1y18.CarMotionData;
import gfhund.jtelemetry.f1y18.CarStatusData;
import gfhund.jtelemetry.f1y18.CarTelemetryData;
import gfhund.jtelemetry.f1y18.F1Y2018ParseException;
import gfhund.jtelemetry.f1y18.Header;
import gfhund.jtelemetry.f1y18.LapData;
import gfhund.jtelemetry.f1y18.MarshalZone;
import gfhund.jtelemetry.f1y18.PacketCarSetupData;
import gfhund.jtelemetry.f1y18.PacketCarStatusData;
import gfhund.jtelemetry.f1y18.PacketCarTelemetryData;
import gfhund.jtelemetry.f1y18.PacketEventData;
import gfhund.jtelemetry.f1y18.PacketLapData;
import gfhund.jtelemetry.f1y18.PacketMotionData;
import gfhund.jtelemetry.f1y18.PacketParticipantsData;
import gfhund.jtelemetry.f1y18.PacketSessionData;
import gfhund.jtelemetry.f1y18.ParticipantData;
import gfhund.jtelemetry.f1y18.SessionType;
import gfhund.jtelemetry.f1y18.TrackID;
import gfhund.jtelemetry.f1y18.Weather;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PacketBuilder{
    private static final Logger logging = Logger.getLogger(PacketBuilder.class.getName());
    public static AbstractPacket parseUDPPacket2018(byte[] rawPacket) throws F1Y2018ParseException{
        
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
                System.out.println("could not find Package");
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

        carMotion.setWorldRightDirX(getShort(rawData,offset + 30));
        carMotion.setWorldRightDirY(getShort(rawData,offset + 32));
        carMotion.setWorldRightDirZ(getShort(rawData,offset + 34));

        carMotion.setGForceLateral(getFloat(rawData,offset + 36));
        carMotion.setGForceLongitudinal(getFloat(rawData,offset + 40));
        carMotion.setGForceVertical(getFloat(rawData,offset + 44));
        return carMotion;
    }
    
     private static MarshalZone getMarshalZones(byte[] rawData,int offset){
        MarshalZone ret = new MarshalZone();
        ret.setZoneStart(getFloat(rawData, offset));
        ret.setZoneFlag(getFloat(rawData, offset+4));
        return ret;
    }
    
    private static PacketSessionData getSessionData(byte[] rawData,Header head){
        //return null;
        PacketSessionData ret = new PacketSessionData();
        ret.setHeader(head);
        ret.setWeather(rawData[21]);
        ret.setTrackTemperature(rawData[22]);
        ret.setAirTemperature(rawData[23]);
        ret.setTotalLaps(rawData[24]);
        ret.setTrackLength(getShort(rawData, 25));
        ret.setSessionType(rawData[27]);
        ret.setTrackId(rawData[28]);
        ret.setEra(rawData[29]);
        ret.setSessionTimeLeft(getShort(rawData, 30));
        ret.setSessionDuration(getShort(rawData, 32));
        ret.setPitSpeedLimit(rawData[34]);
        ret.setGamePaused(rawData[35]);
        ret.setIsSpectating(rawData[36]);
        ret.setSpectatorCarIndex(rawData[37]);
        ret.setSliProNativeSupport(rawData[38]);
        ret.setNumMarshalZones(rawData[39]);
        for(int i=0;i<(int)ret.getNumMarshalZones();i++){
            int offset = 40 + i*MarshalZone.getSize();
            MarshalZone zone = getMarshalZones(rawData, offset);
            ret.setMarshalZone(i, zone);
        }
        int currentOffset = 40 + ret.getNumMarshalZones() * MarshalZone.getSize();
        ret.setSafetyCarStatus(rawData[currentOffset]);
        ret.setNetworkGame(rawData[currentOffset+1]);
        return ret;
    }

    private static PacketLapData getLapData(byte[] rawData,Header head){
        PacketLapData packet = new PacketLapData();//21
        packet.setHeader(head);
        for(int i=0;i<20;i++){
            LapData lapData = getCarLapData(rawData, Header.getSize()+i*LapData.getSize());
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
    
    private static ParticipantData getParticipantData(byte[] rawData,int offset){
        ParticipantData ret = new ParticipantData();
        ret.setAiControlled(rawData[offset]);
        ret.setDriverId(rawData[offset+1]);
        ret.setTeamId(rawData[offset+2]);
        ret.setRaceNumber(rawData[offset+3]);
        ret.setNationality(rawData[offset+4]);
        ret.setName(getString(rawData, offset+5));
        return ret;
    }
    //------------------------------------------------------------------------------------
    private static PacketParticipantsData getParticipantsData(byte[] rawData,Header head){
        PacketParticipantsData ret = new PacketParticipantsData();
        ret.setHeader(head);
        ret.setNumCars(rawData[Header.getSize()]);
        for(int i=0;i<20;i++){
            ParticipantData data = getParticipantData(rawData, i*ParticipantData.getSize()+Header.getSize()+1);
            ret.setParticipantData(i, data);
        }
        return ret;
    }
    private static PacketCarSetupData getCarSetupData(byte[] rawData,Header head){
        return null;
    }
    private static CarTelemetryData getCarTelemetry(byte[] rawData,int offset){
        CarTelemetryData ret = new CarTelemetryData();
        ret.setSpeed(getShort(rawData, offset));
        ret.setThrottle(rawData[offset+2]);
        ret.setSteer(rawData[offset+3]);
        ret.setBrake(rawData[offset+4]);
        ret.setClutch(rawData[offset+5]);
        ret.setGear(rawData[offset+6]);
        ret.setEngineRPM(getShort(rawData, offset+7));
        ret.setDrs(rawData[offset+9]);
        ret.setRevLightsPercent(rawData[offset+10]);
        for(int i=0;i<4;i++){
            int secondOffset= offset+11+i*2;
            ret.setBrakeTemperature(i, getShort(rawData, secondOffset));
        }
        for(int i = 0;i<4;i++){
            int secondOffset = offset+19+i*2;
            ret.setTyreSurfaceTemperature(i, getShort(rawData,secondOffset));
        }
        for(int i=0;i<4;i++){
            int secondOffset=offset+27+i*2;
            ret.setTyreInnerTemperature(i, getShort(rawData, secondOffset));
        }
        ret.setEngineTemperature(getShort(rawData, offset+35));
        for(int i=0;i<4;i++){
            int secondOffset = offset+37+i*4;
            ret.setTyrePressure(i, getFloat(rawData, secondOffset));
        }
        return ret;
    }
    private static PacketCarTelemetryData getTelemetryData(byte[] rawData,Header head){
        PacketCarTelemetryData ret = new PacketCarTelemetryData();
        ret.setHeader(head);
        for(int i=0;i<20;i++){
            int offset = Header.getSize() + i*CarTelemetryData.getSize();
            ret.setCarTelemetryData(i, getCarTelemetry(rawData, offset));
        }
        return ret;
    }
    private static PacketCarStatusData getPacketCarStatusData(byte[] rawData,Header head){
        PacketCarStatusData ret = new PacketCarStatusData();
        ret.setHeader(head);
        for(int i = 0;i<20;i++){
            CarStatusData data = getCarStatusData(rawData, Header.getSize()+ i*CarStatusData.getSize());
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
        try{
            ByteBuffer converter = ByteBuffer.wrap(raw,position,4);
            converter = converter.order(ByteOrder.LITTLE_ENDIAN);
            return converter.getInt();
        }
        catch(IndexOutOfBoundsException e){
            logging.log(Level.WARNING, "Out of Bounds Exception in getInt. Position: "+position+" ArrayLength: "+raw.length, e);
            return 0;
        }
    }
    private static short getShort(byte[] raw,int position){
        try{
            ByteBuffer converter = ByteBuffer.wrap(raw,position,2);
            converter = converter.order(ByteOrder.LITTLE_ENDIAN);
            return converter.getShort();
        }
        catch(IndexOutOfBoundsException e){
            logging.log(Level.WARNING, "Out of Bounds Exception in getInt. Position: "+position+" ArrayLength: "+raw.length, e);
            return 0;
        }
        
    }
    private static float getFloat(byte[] raw,int position){
        try{
            ByteBuffer converter = ByteBuffer.wrap(raw,position,4);
            converter = converter.order(ByteOrder.LITTLE_ENDIAN);
            return converter.getFloat();
        }
        catch(IndexOutOfBoundsException e){
            logging.log(Level.WARNING, "Out of Bounds Exception in getInt. Position: "+position+" ArrayLength: "+raw.length, e);
            return 0;
        }
    }
    private static long getLong(byte[] raw,int position){
        try{
            ByteBuffer converter = ByteBuffer.wrap(raw,position,8);
            converter = converter.order(ByteOrder.LITTLE_ENDIAN);
            return converter.getLong();
        }
        catch(IndexOutOfBoundsException e){
            logging.log(Level.WARNING, "Out of Bounds Exception in getInt. Position: "+position+" ArrayLength: "+raw.length, e);
            return 0;
        }
    }
    private static String getString(byte[] raw,int position){
        try{
            String ret = new String(raw, position, 48);
            return ret;
        }
        catch(IndexOutOfBoundsException e){
            logging.log(Level.WARNING, "Out of Bounds Exception in getInt. Position: "+position+" ArrayLength: "+raw.length, e);
            return "";
        }
    }
    
    
    public static AbstractPacket parseUDPPacket2019(byte[] rawPacket) throws ParseException{
        gfhund.jtelemetry.f1y19.Header packetHeader = getHeader2019(rawPacket);
        
        switch(packetHeader.getPacketId()){
            case 0:
            //return getMotionData2019(rawPacket, packetHeader);
                PacketFields[] fields = getFields(gfhund.jtelemetry.f1y19.PacketMotionData.class.getName());
                return (gfhund.jtelemetry.f1y19.PacketMotionData) parseByteArray(gfhund.jtelemetry.f1y19.PacketMotionData.class.getName(),rawPacket,fields);
            case 1:
            //return getSessionData(rawPacket, packetHeader);
                 PacketFields[] sessionFields = getFields(gfhund.jtelemetry.f1y19.PacketSessionData.class.getName());
                return (gfhund.jtelemetry.f1y19.PacketSessionData) parseByteArray(gfhund.jtelemetry.f1y19.PacketSessionData.class.getName(),rawPacket,sessionFields);
            case 2:
            //return getLapData(rawPacket, packetHeader);
                PacketFields[] lapDataFields = getFields(gfhund.jtelemetry.f1y19.PacketLapData.class.getName());
                return (gfhund.jtelemetry.f1y19.PacketLapData) parseByteArray(gfhund.jtelemetry.f1y19.PacketLapData.class.getName(),rawPacket,lapDataFields);
            case 3:
            //return getEventData(rawPacket, packetHeader);
                // I Comment this out because of the EventDataDetails attribute which should make problems
                //PacketFields[] packetEventFields = getFields(gfhund.jtelemetry.f1y19.PacketEventData.class.getName());
                //return (gfhund.jtelemetry.f1y19.PacketEventData) parseByteArray(gfhund.jtelemetry.f1y19.PacketEventData.class.getName(),rawPacket,packetEventFields);
                return null;
            case 4:
            //return getParticipantsData(rawPacket, packetHeader);
                PacketFields[] packetParticipantsFields = getFields(gfhund.jtelemetry.f1y19.PacketParticipantsData.class.getName());
                return (gfhund.jtelemetry.f1y19.PacketParticipantsData) parseByteArray(gfhund.jtelemetry.f1y19.PacketParticipantsData.class.getName(),rawPacket,packetParticipantsFields);
            case 5:
            //return getCarSetupData(rawPacket, packetHeader);
                PacketFields[] packetCarSetupFields = getFields(gfhund.jtelemetry.f1y19.PacketCarSetupData.class.getName());
                return (gfhund.jtelemetry.f1y19.PacketCarSetupData) parseByteArray(gfhund.jtelemetry.f1y19.PacketCarSetupData.class.getName(),rawPacket,packetCarSetupFields);
            case 6:
            //return getTelemetryData(rawPacket, packetHeader);
                PacketFields[] packetCarTelemetryFields = getFields(gfhund.jtelemetry.f1y19.PacketCarTelemetryData.class.getName());
                return (gfhund.jtelemetry.f1y19.PacketCarTelemetryData) parseByteArray(gfhund.jtelemetry.f1y19.PacketCarTelemetryData.class.getName(),rawPacket,packetCarTelemetryFields);
            case 7:
            //return getPacketCarStatusData(rawPacket, packetHeader);
                PacketFields[] packetCarStatusFields = getFields(gfhund.jtelemetry.f1y19.PacketCarStatusData.class.getName());
                return (gfhund.jtelemetry.f1y19.PacketCarStatusData) parseByteArray(gfhund.jtelemetry.f1y19.PacketCarStatusData.class.getName(),rawPacket,packetCarStatusFields);
            default:
                System.out.println("could not find Package");
            return null;
        }
    }

    private static gfhund.jtelemetry.f1y19.Header getHeader2019(byte[] packet){
        if(packet.length < gfhund.jtelemetry.f1y19.Header.getSize()){
            return null;
        }
        gfhund.jtelemetry.f1y19.Header ret = new gfhund.jtelemetry.f1y19.Header();
        ret.setPacketFormat(getShort(packet, 0));
        ret.setGameMajorVersion(packet[2]);
        ret.setGameMinorVersion(packet[3]);
        ret.setPacketVersion(packet[4]);
        ret.setPacketId(packet[5]);

        ret.setSessionUid(getLong(packet,6));

        ret.setSessionTime(getFloat(packet, 14));

        ret.setFrameIdentifier(getInt(packet,18));
        ret.setPlayerCarIndex(packet[22]);
        return ret;
    }
    
    private static Object parseByteArray(String retClass, byte[] rawData,PacketFields[] fieldNames)throws ParseException{
        return parseByteArray(retClass, rawData, 0, fieldNames);
    }
    
    private static Object parseByteArray(String retClass, byte[] rawData, int offset, PacketFields[] fieldNames) throws ParseException{
        try{
            Class c = Class.forName(retClass);
            Constructor constructor = c.getConstructor();
            Object ret = constructor.newInstance();
            //int offset = 0;
                for(PacketFields fieldName: fieldNames){
                    //Field f = c.getDeclaredField(fieldName.getName());
                    String methodName = "get"+fieldName.getName().substring(0, 1).toUpperCase()+fieldName.getName().substring(1);
                    Method m = c.getMethod(methodName, (Class[])null);
                    //String typeName = f.getType().getName();
                    String typeName = m.getReturnType().getName();
                    
                    if(typeName.startsWith("[")){
                        if(typeName.equalsIgnoreCase("[B")){
                            for(int i=0;i<fieldName.getLength();i++){
                                getArraySetterMethod(c, fieldName.getName(), byte.class).invoke(ret, i, rawData[offset]);
                                offset++;
                            }
                        }
                        else if(typeName.equalsIgnoreCase("[I")){
                            for(int i=0;i<fieldName.getLength();i++){
                                getArraySetterMethod(c, fieldName.getName(), int.class).invoke(ret, i, getInt(rawData,offset));
                                offset += 4;
                            }
                        }
                        else if(typeName.equalsIgnoreCase("[S")){
                            for(int i=0;i<fieldName.getLength();i++){
                                getArraySetterMethod(c, fieldName.getName(), short.class).invoke(ret, i, getShort(rawData,offset));
                                offset += 2;
                            }
                        }
                        else if(typeName.equalsIgnoreCase("[F")){
                            for(int i=0;i<fieldName.getLength();i++){
                                getArraySetterMethod(c, fieldName.getName(), float.class).invoke(ret, i, getFloat(rawData,offset));
                                offset += 4;
                            }
                        }
                        else if(typeName.equalsIgnoreCase("[J")){
                            for(int i=0;i<fieldName.getLength();i++){
                                getArraySetterMethod(c, fieldName.getName(), long.class).invoke(ret, i, getLong(rawData,offset));
                                offset += 8;
                            }
                        }
                        else{
                            String typeNameWoPrefix = typeName.substring(2);
                            typeNameWoPrefix = typeNameWoPrefix.substring(0, typeNameWoPrefix.length()-1);
                            for(int i=0;i<fieldName.getLength();i++){
                                Class subC = Class.forName(typeNameWoPrefix);
                                PacketFields[] subFields = getFields(subC.getName());
                                int size = (int)getSizeMethod(subC).invoke(null,(Object[])null);
                                Object obj = parseByteArray(subC.getName(), rawData, offset, subFields);
                                getArraySetterMethod(c, fieldName.getName(), subC).invoke(ret, i, obj);
                                offset += size;
                            }
                        }
                    }
                    else if(typeName.equalsIgnoreCase("byte")){
                        getSetterMethod(c, fieldName.getName(),byte.class).invoke(ret, rawData[offset]);
                        //f.setByte(ret, rawData[offset]);
                        offset++;
                    }
                    else if(typeName.equalsIgnoreCase("int") || typeName.equalsIgnoreCase("integer")){
                        getSetterMethod(c, fieldName.getName(),int.class).invoke(ret, getInt(rawData, offset));
                        //f.setInt(ret, getInt(rawData, offset));
                        offset += 4;
                    }
                    else if(typeName.equalsIgnoreCase("short")){
                        getSetterMethod(c, fieldName.getName(),short.class).invoke(ret, getShort(rawData, offset));
                        //f.setShort(ret, getShort(rawData, offset));
                        offset += 2;
                    }
                    else if(typeName.equalsIgnoreCase("float")){
                        getSetterMethod(c, fieldName.getName(),float.class).invoke(ret, getFloat(rawData, offset));
                        //f.setFloat(ret, getFloat(rawData, offset));
                        offset += 4;
                    }
                    else if(typeName.equalsIgnoreCase("long")){
                        getSetterMethod(c, fieldName.getName(),long.class).invoke(ret, getLong(rawData, offset));
                        //f.setLong(ret, getLong(rawData, offset));
                        offset += 8;
                    }
                    else if(typeName.equalsIgnoreCase("java.lang.String")){
                        getSetterMethod(c, fieldName.getName(),String.class).invoke(ret, getString(rawData, offset));
                        //f.set(ret, getString(rawData, offset));
                        offset += 48;
                    }
                    else if(typeName.equalsIgnoreCase("gfhund.jtelemetry.f1y19.Header")){
                        //f.set(ret,head);
                        getSetterMethod(c, fieldName.getName(),gfhund.jtelemetry.f1y19.Header.class).invoke(ret, getHeader2019(rawData));
                        offset += gfhund.jtelemetry.f1y19.Header.getSize();
                    }
                    else{
                        PacketFields[] fields = getFields(typeName);
                        Object sub = parseByteArray(typeName,rawData,offset,fields);
                    }
                }
                return ret;
        }
        catch(      ClassNotFoundException|
                    InstantiationException|
                    IllegalAccessException|
                    InvocationTargetException|
                NoSuchMethodException e){
                String message = e.getMessage();
                StackTraceElement[] stacktrace = e.getStackTrace();
                for(StackTraceElement element : stacktrace){
                    message += "\n"+element.toString();
                }

                throw new ParseException(retClass, message);
            }
    }
    
    private static Method getGetterMethod(Class c,String fieldName) throws NoSuchMethodException{
        String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        return c.getMethod(methodName, (Class[])null);  
    }
    
    private static Method getSetterMethod(Class c,String fieldName,Class paramType) throws NoSuchMethodException{
        /*
        String[] fieldNameParts = fieldName.split(".");
        if(fieldNameParts.length > 1){
            fieldName = fieldNameParts[fieldNameParts.length-1];
        }
*/
        
        String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        return c.getMethod(methodName, paramType);  
    }
    
    private static Method getArraySetterMethod(Class c, String fieldName, Class paramType) throws NoSuchMethodException{
        /*String[] fieldNameParts = fieldName.split(".");
        if(fieldNameParts.length > 1){
            fieldName = fieldNameParts[fieldNameParts.length-1];
        }
*/
        
        String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        return c.getMethod(methodName, int.class, paramType);  
    }
    
    private static Method getSizeMethod(Class c)throws NoSuchMethodException {
        return c.getMethod("getSize", (Class[]) null);
    }
    
    private static PacketFields[] getFields(String className){
        HashMap<String,PacketFields[]> map = new HashMap<>();
        PacketFields[] PacketMotionData19 = {
            new PacketFields("header"),
            new PacketFields("carMotionData",20)
        };
        map.put("gfhund.jtelemetry.f1y19.PacketMotionData",PacketMotionData19);
        PacketFields[] carMotionData = {
            new PacketFields("worldPositionX"),
            new PacketFields("worldPositionY"),
            new PacketFields("worldPositionZ"),
            
            new PacketFields("worldVelocityX"),
            new PacketFields("worldVelocityY"),
            new PacketFields("worldVelocityZ"),
            
            new PacketFields("worldForwardDirX"),
            new PacketFields("worldForwardDirY"),
            new PacketFields("worldForwardDirZ"),
            
            new PacketFields("worldRightDirX"),
            new PacketFields("worldRightDirY"),
            new PacketFields("worldRightDirZ"),
            
            new PacketFields("gForceLateral"),
            new PacketFields("gForceLongitudinal"),
            new PacketFields("gForceVertical"),
            
            new PacketFields("yaw"),
            new PacketFields("pitch"),
            new PacketFields("roll"),
            
        };
        map.put("gfhund.jtelemetry.f1y19.CarMotionData",carMotionData);
        
        PacketFields[] packetSessionData = {
            new PacketFields("header19"),
            
            new PacketFields("weather"),
            new PacketFields("trackTemperature"),
            new PacketFields("airTemperature"),
            new PacketFields("totalLaps"),
            new PacketFields("trackLength"),
            new PacketFields("sessionType"),
            new PacketFields("trackId"),
            new PacketFields("era"),
            new PacketFields("sessionTimeLeft"),
            new PacketFields("sessionDuration"),
            new PacketFields("pitSpeedLimit"),
            new PacketFields("gamePaused"),
            new PacketFields("isSpectating"),
            new PacketFields("spectatorCarIndex"),
            new PacketFields("sliProNativeSupport"),
            new PacketFields("numMarshalZones"),
            new PacketFields("marshalZones",21),
            new PacketFields("safetyCarStatus"),
            new PacketFields("networkGame"),
        };
        map.put("gfhund.jtelemetry.f1y19.PacketSessionData",packetSessionData);
        
        PacketFields[] marshalZone = {
            new PacketFields("zoneStart"),
            new PacketFields("zoneFlag")
        };
        map.put("gfhund.jtelemetry.f1y18.MarshalZone",marshalZone);
        
        PacketFields[] PacketLapDataFields = {
            new PacketFields("header"),
            new PacketFields("lapData",20)
        };
        map.put("gfhund.jtelemetry.f1y19.PacketLapData",PacketLapDataFields);
        
        PacketFields[] LapDataFields = {
            new PacketFields("lastLapTime"),
            new PacketFields("currentLapTime"),
            new PacketFields("bestLapTime"),
            new PacketFields("sector1Time"),
            new PacketFields("sector2Time"),
            new PacketFields("lapDistance"),
            new PacketFields("totalDistance"),
            new PacketFields("safetyCarDelta"),
            new PacketFields("carPosition"),
            new PacketFields("currentLapNum"),
            new PacketFields("pitStatus"),
            new PacketFields("sector"),
            new PacketFields("currentLapInvalid"),
            new PacketFields("penalties"),
            new PacketFields("gridPosition"),
            new PacketFields("driverStatus"),
            new PacketFields("resultStatus")
        };
        map.put("gfhund.jtelemetry.f1y19.LapData",LapDataFields);
        
        PacketFields[] PacketParticipantsDataFields = {
            new PacketFields("header19"),
            new PacketFields("numCars"),
            new PacketFields("participants",20)
        };
        map.put("gfhund.jtelemetry.f1y19.PacketParticipantsData",PacketParticipantsDataFields);
        
        PacketFields[] ParticipantsDataFields = {
            new PacketFields("aiControlled"),
            new PacketFields("driverId"),
            new PacketFields("teamId"),
            new PacketFields("raceNumber"),
            new PacketFields("nationality"),
            new PacketFields("name"),
            new PacketFields("yourTelemetry")
        };
        map.put("gfhund.jtelemetry.f1y19.ParticipantData",ParticipantsDataFields);
        
        PacketFields[] PacketCarSetupFields = {
            new PacketFields("header19"),
            new PacketFields("carSetupData",20),
        };
        map.put("gfhund.jtelemetry.f1y19.PacketCarSetupData",PacketCarSetupFields);
        
        PacketFields[] CarSetupFields = {
            new PacketFields("frontWing"),
            new PacketFields("rearWing"),
            new PacketFields("onThrottle"),
            new PacketFields("offThrottle"),
            new PacketFields("frontCamber"),
            new PacketFields("rearCamber"),
            new PacketFields("frontToe"),
            new PacketFields("rearToe"),
            new PacketFields("frontSuspension"),
            new PacketFields("rearSuspension"),
            new PacketFields("frontAntiRollBar"),
            new PacketFields("rearAntiRollBar"),
            new PacketFields("frontSuspensionHeight"),
            new PacketFields("rearSuspensionHeight"),
            new PacketFields("breakPressure"),
            new PacketFields("breakBias"),
            new PacketFields("frontTyrePressure"),
            new PacketFields("rearTyrePressure"),
            new PacketFields("ballast"),
            new PacketFields("fuelLoad"),
            
        };
        map.put("gfhund.jtelemetry.f1y19.CarSetupData",CarSetupFields);
        
        PacketFields[] PacketCarTelemetryFields = {
            new PacketFields("header19"),
            new PacketFields("carTelemetryData",20),
            new PacketFields("buttonStatus"),
        };
        map.put("gfhund.jtelemetry.f1y19.PacketCarTelemetryData",PacketCarTelemetryFields);
        
        PacketFields[] CarTelemetryFields = {
            new PacketFields("speed"),
            new PacketFields("fThrottle"),
            new PacketFields("fSteer"),
            new PacketFields("fBrake"),
            new PacketFields("clutch"),
            new PacketFields("gear"),
            new PacketFields("engineRPM"),
            new PacketFields("drs"),
            new PacketFields("revLightsPercent"),
            new PacketFields("brakesTemperature",4),
            new PacketFields("tyresSurfaceTemperature",4),
            new PacketFields("tyresInnerTemperature",4),
            new PacketFields("engineTemperature"),
            new PacketFields("surfaceType",4),
        };
        map.put("gfhund.jtelemetry.f1y19.CarTelemetryData",CarTelemetryFields);
        
        PacketFields[] PacketCarStatusDataFields = {
            new PacketFields("header"),
            new PacketFields("carStatusData",20)
        };
        map.put("gfhund.jtelemetry.f1y19.PacketCarStatusData",PacketCarStatusDataFields);
        
        PacketFields[] CarStatusDataFields = {
            new PacketFields("tractionControl"),
            new PacketFields("antiLockBrakes"),
            new PacketFields("fuelMix"),
            new PacketFields("frontBrakeBias"),
            new PacketFields("pitLimiterStatus"),
            new PacketFields("fuelInTank"),
            new PacketFields("fuelCapacity"),
            new PacketFields("fuelRemainingLaps"),
            new PacketFields("maxRPM"),
            new PacketFields("idleRPM"),
            new PacketFields("maxGears"),
            new PacketFields("drsAllowed"),
            new PacketFields("tyresWear",4),
            new PacketFields("tyreCompound"),
            new PacketFields("tyreVisualCompound"),
            new PacketFields("tyresDamage",4),
            new PacketFields("frontLeftWingDamage"),
            new PacketFields("frontRightWingDamage"),
            new PacketFields("rearWingDamage"),
            new PacketFields("engineDamage"),
            new PacketFields("gearBoxDamage"),
            new PacketFields("vehicleFiaFlags"),
            new PacketFields("ersStoreEnergy"),
            new PacketFields("ersDeployMode"),
            new PacketFields("ersHarvestedThisLapMGUK"),
            new PacketFields("ersHarvestedThisLapMGUH"),
            new PacketFields("ersDeployedThisLap"),
        };
        map.put("gfhund.jtelemetry.f1y19.CarStatusData",CarStatusDataFields);
        
        return map.get(className);
    }
    
    static class PacketFields{
        protected String name;
        protected int length;
        public PacketFields(String name){
            this.name = name;
            this.length = 1;
        }
        public PacketFields(String name, int length){
            this.name = name;
            this.length = length;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }
        
        
    }
}
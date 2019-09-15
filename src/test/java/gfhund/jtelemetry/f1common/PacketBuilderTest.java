/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfhund.jtelemetry.f1common;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
/**
 *
 * @author PhilippHolzmann
 */
public class PacketBuilderTest {
    
    @Test
    void PacketMotionDataTest(){
        gfhund.jtelemetry.f1y19.PacketMotionData packetMotionData = new gfhund.jtelemetry.f1y19.PacketMotionData();
        gfhund.jtelemetry.f1y19.Header header = getHeader2019((byte)0);
        
        packetMotionData.setHeader(header);
        for(int i=0;i<20;i++){
            packetMotionData.setCarMotionData(i, this.getCarMotionData2019(i));
        }
        
        byte[] raw = packetMotionData.getBytes();
        try{
            gfhund.jtelemetry.f1y19.PacketMotionData afterParse = 
                    (gfhund.jtelemetry.f1y19.PacketMotionData) PacketBuilder.parseUDPPacket2019(raw);
            assertEquals(afterParse.getHeader().getPlayerCarIndex(),packetMotionData.getHeader().getPlayerCarIndex());
            for(int i=0;i<20;i++){
                assertEquals(afterParse.getCarMotionData(0).getWorldForwardDirX(), packetMotionData.getCarMotionData(0).getWorldForwardDirX());
            }
            
        }
        catch(ParseException e){
            System.out.println(e.getMessage());
        }
    }
    
    @Test
    void PacketLapDataTest(){
        gfhund.jtelemetry.f1y19.PacketLapData dummyPacket = new gfhund.jtelemetry.f1y19.PacketLapData();
        gfhund.jtelemetry.f1y19.Header header = getHeader2019((byte)2);
        dummyPacket.setHeader(header);
        for(int i=0;i<20;i++){
            dummyPacket.setLapData(i, getLapData2019());
        }
        byte[] raw = dummyPacket.getBytes();
        try{
            gfhund.jtelemetry.f1y19.PacketLapData afterParse = 
                    (gfhund.jtelemetry.f1y19.PacketLapData) PacketBuilder.parseUDPPacket2019(raw);
            assertEquals(afterParse.getHeader().getPlayerCarIndex(),dummyPacket.getHeader().getPlayerCarIndex());
            for(int i=0;i<20;i++){
                assertEquals(afterParse.getLapData(i).getDriverStatus(), dummyPacket.getLapData(i).getDriverStatus());
                assertEquals(afterParse.getLapData(i).getLapDistance(), dummyPacket.getLapData(i).getLapDistance());
            }
            
        }
        catch(ParseException e){
            System.out.println(e.getMessage());
            assertTrue(true);
        }
    }
    
    @Test
    void PacketCarTelemetryDataTest(){
        gfhund.jtelemetry.f1y19.PacketCarTelemetryData dummyPacket = new gfhund.jtelemetry.f1y19.PacketCarTelemetryData();
        gfhund.jtelemetry.f1y19.Header header = getHeader2019((byte)6);
        dummyPacket.setHeader19(header);
        for(int i=0;i<20;i++){
            dummyPacket.setCarTelemetryData(i, getCarTelemetryData());
        }
        dummyPacket.setButtonStatus(0);
        byte[] raw = dummyPacket.getBytes();
        try{
            gfhund.jtelemetry.f1y19.PacketCarTelemetryData afterParse = 
                    (gfhund.jtelemetry.f1y19.PacketCarTelemetryData) PacketBuilder.parseUDPPacket2019(raw);
            assertEquals(afterParse.getHeader19().getPlayerCarIndex(),dummyPacket.getHeader19().getPlayerCarIndex());
            for(int i=0;i<20;i++){
                assertEquals(afterParse.getCarTelemetryData(i).getEngineTemperature(), dummyPacket.getCarTelemetryData(i).getEngineTemperature());
                assertEquals(afterParse.getCarTelemetryData(i).getSpeed(), dummyPacket.getCarTelemetryData(i).getSpeed());
                assertEquals(afterParse.getCarTelemetryData(i).getFThrottle(), dummyPacket.getCarTelemetryData(i).getFThrottle());
                assertEquals(afterParse.getCarTelemetryData(i).getFBrake(), dummyPacket.getCarTelemetryData(i).getFBrake());
                assertEquals(afterParse.getCarTelemetryData(i).getFSteer(), dummyPacket.getCarTelemetryData(i).getFSteer());
            }
            
        }
        catch(ParseException e){
            System.out.println(e.getMessage());
            assertTrue(true);
        }
    }
    
    @Test
    public void PacketCarSetupTest(){
        gfhund.jtelemetry.f1y19.PacketCarSetupData dummyPacket = new gfhund.jtelemetry.f1y19.PacketCarSetupData();
        gfhund.jtelemetry.f1y19.Header header = getHeader2019((byte)5);
        dummyPacket.setHeader19(header);
        for(int i=0;i<20;i++) {
            dummyPacket.setCarSetupData(i, this.getCarSetupData());
        }
        byte[] raw = dummyPacket.getBytes();
        try{
            gfhund.jtelemetry.f1y19.PacketCarSetupData afterParse = 
                    (gfhund.jtelemetry.f1y19.PacketCarSetupData) PacketBuilder.parseUDPPacket2019(raw);
            assertEquals(afterParse.getHeader19().getPlayerCarIndex(),dummyPacket.getHeader19().getPlayerCarIndex());
            for(int i=0;i<20;i++){
                assertEquals(afterParse.getCarSetupData(i).getFrontWing(), dummyPacket.getCarSetupData(i).getFrontWing());
            }
        }
        catch(ParseException e){
            System.out.println(e.getMessage());
            assertTrue(true);
        }
                
    }
    
    gfhund.jtelemetry.f1y19.CarMotionData getCarMotionData2019(int index){
        gfhund.jtelemetry.f1y19.CarMotionData ret = new gfhund.jtelemetry.f1y19.CarMotionData();
        ret.setWorldPositionX(index);
        ret.setWorldPositionY(index);
        ret.setWorldPositionZ(index);
        
        ret.setWorldVelocityX(index);
        ret.setWorldVelocityY(index);
        ret.setWorldVelocityZ(index);
        
        ret.setWorldForwardDirX((short)index);
        ret.setWorldForwardDirY((short)index);
        ret.setWorldForwardDirZ((short) index);
        
        ret.setWorldRightDirX((short) index);
        ret.setWorldRightDirY((short) index);
        ret.setWorldRightDirZ((short) index);
                
        ret.setGForceLateral(index);
        ret.setGForceLongitudinal(index);
        ret.setGForceVertical(index);
        ret.setYaw(index);
        ret.setPitch(index);
        ret.setRoll(index);
        return ret;
    }
    
    gfhund.jtelemetry.f1y19.LapData getLapData2019(){
        gfhund.jtelemetry.f1y19.LapData ret = new gfhund.jtelemetry.f1y19.LapData();
        ret.setBestLapTime(63.125f);
        ret.setCarPosition((byte)5);
        ret.setCurrentLapInvalid((byte)0);
        ret.setCurrentLapNum((byte)9);
        ret.setCurrentLapTime(20.5f);
        ret.setDriverStatus((byte)1);
        ret.setGridPosition((byte)5);
        ret.setLapDistance(20.0f);
        ret.setLastLapTime(65.126f);
        ret.setPenalties((byte)0);
        ret.setPitStatus((byte)0);
        ret.setResultStatus((byte)2);
        ret.setSafetyCarDelta(1.125f);
        ret.setSector((byte)0);
        ret.setSector1Time(20.5f);
        ret.setSector2Time(20.8f);
        ret.setTotalDistance(80.45f);
        return ret;
    }
    
    gfhund.jtelemetry.f1y19.CarTelemetryData getCarTelemetryData(){
        gfhund.jtelemetry.f1y19.CarTelemetryData ret = new gfhund.jtelemetry.f1y19.CarTelemetryData();
        ret.setSpeed((short)234);
        ret.setFThrottle(100.0f);
        ret.setFBrake(50.0f);
        ret.setFSteer(0.0f);
        ret.setBrakesTemperature(0, (byte)200);
        ret.setBrakesTemperature(1, (byte)300);
        ret.setBrakesTemperature(2, (byte)400);
        ret.setBrakesTemperature(3, (byte)500);
        ret.setClutch((byte)0);
        ret.setDrs((byte)0);
        ret.setEngineRPM((short)11000);
        ret.setEngineTemperature((short)125);
        ret.setGear((byte)5);
        ret.setRevLightsPercent((byte)0);
        
        
        ret.setSurfaceType(0,(byte)0);
        ret.setSurfaceType(1,(byte)0);
        ret.setSurfaceType(2,(byte)0);
        ret.setSurfaceType(3,(byte)0);
        
        ret.setTyrePressure(0, 20);
        ret.setTyrePressure(1, 21);
        ret.setTyrePressure(2, 22);
        ret.setTyrePressure(3, 23);
        ret.setTyresInnerTemperature(0, (byte)100);
        ret.setTyresInnerTemperature(1, (byte)101);
        ret.setTyresInnerTemperature(2, (byte)102);
        ret.setTyresInnerTemperature(3, (byte)103);
        ret.setTyresSurfaceTemperature(0, (byte)100);
        ret.setTyresSurfaceTemperature(1, (byte)101);
        ret.setTyresSurfaceTemperature(2, (byte)102);
        ret.setTyresSurfaceTemperature(3, (byte)103);
        return ret;
    }
    
    gfhund.jtelemetry.f1y19.CarSetupData getCarSetupData(){
        gfhund.jtelemetry.f1y19.CarSetupData ret = new gfhund.jtelemetry.f1y19.CarSetupData();
        ret.setBallast((byte)1);
        ret.setBreakBias((byte)60);
        ret.setBreakPressure((byte)80);
        ret.setFrontAntiRollBar((byte)5);
        ret.setFrontCamber(7.0f);
        ret.setFrontSuspension((byte)5);
        ret.setFrontSuspensionHeight((byte)6);
        ret.setFrontToe(2.0f);
        ret.setFrontTyrePressure(22.0f);
        ret.setFrontWing((byte)10);
        ret.setFuelLoad(30.0f);
        ret.setOffThrottle((byte)75);
        ret.setOnThrottle((byte)80);
        ret.setRearAntiRollBar((byte)7);
        ret.setRearCamber(5.0f);
        ret.setRearSuspension((byte)8);
        ret.setRearSuspensionHeight((byte)9);
        ret.setRearToe(3.0f);
        ret.setRearTyrePressure(23.0f);
        ret.setRearWing((byte)11);
        return ret;
    }
    
    gfhund.jtelemetry.f1y19.Header getHeader2019(byte packageId){
        gfhund.jtelemetry.f1y19.Header header = new gfhund.jtelemetry.f1y19.Header();
        header.setPacketFormat((short)2019);
        header.setGameMajorVersion((byte)1);
        header.setGameMinorVersion((byte)0);
        header.setPacketVersion((byte)1);
        header.setPacketId(packageId);
        header.setSessionUid(504321);
        header.setSessionTime(5.55f);
        header.setFrameIdentifier(205);
        header.setPlayerCarIndex((byte)1);
        return header;
    }
}

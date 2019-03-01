/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfhund.jtelemetry.fxgui;

import gfhund.jtelemetry.f1y18.CarMotionData;
import gfhund.jtelemetry.f1y18.Header;
import gfhund.jtelemetry.f1y18.LapData;
import gfhund.jtelemetry.f1y18.PacketCarTelemetryData;
import gfhund.jtelemetry.f1y18.PacketLapData;
import gfhund.jtelemetry.f1y18.PacketParticipantsData;
import gfhund.jtelemetry.f1y18.ParticipantData;
import gfhund.jtelemetry.f1y18.PacketMotionData;
import gfhund.jtelemetry.f1y18.CarTelemetryData;
import gfhund.jtelemetry.stfFormat.AbstractStfObject;
import gfhund.jtelemetry.stfFormat.StfClass;
import gfhund.jtelemetry.stfFormat.StfDocument;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
/**
 *
 * @author PhilippGL
 */
public class TelemetryWriterTest {
    
    @Test
    void writerTest(){
        TelemetryWriter writer = new TelemetryWriter();
        int childLength = 10;
        PacketParticipantsData participantData = getParticipants();
        PacketLapData lapData = getLapData();
        PacketMotionData motionData = getMotionData();
        PacketCarTelemetryData telemetryData = getCarTelemetryData();
        for(int i=0;i<childLength;i++){
            writer.processPackage(participantData);
            writer.processPackage(lapData);
            writer.processPackage(motionData);
            telemetryData.getHeader().setFrameIdentifier(i);
            writer.processPackage(telemetryData);
        }
        writer.closeTelemetry("./testTelemetryWriter.zip");
        //wait until the Loop is complete
        try{
            //wait(1000);
            Thread.sleep(10000);
        }catch(InterruptedException e){

        }
        File zipfile = new File("./testTelemetryWriter.zip");
        assertTrue(zipfile.exists());
        TelemetryReader reader = new TelemetryReader();
        HashMap<String,StfDocument> documents = reader.read("./testTelemetryWriter.zip");
        for(Map.Entry<String,StfDocument> doc:documents.entrySet()){
            if(doc.getKey().contains("player")){
                //StfClass rootClass = doc.getValue().getValue(doc.getKey());
                StfClass rootClass = (StfClass)doc.getValue().getChild(0);
                AbstractStfObject[] children = rootClass.getChildren();
                for(AbstractStfObject obj: children){
                    if(obj instanceof StfClass){
                        String value = ((StfClass) obj).getValue("worldRightDirX");
                        assertEquals(value, "20");
                    }
                }
            }
            else{
                StfClass rootClass = (StfClass)doc.getValue().getChild(0);
                AbstractStfObject[] children = rootClass.getChildren();
                //assertTrue(children.length == childLength);
                assertEquals(childLength,children.length);
                for(AbstractStfObject obj: children){
                    if(obj instanceof StfClass){
                        String value = ((StfClass) obj).getValue("speed");
                        assertEquals(value, "200");
                    }
                }
            }
        }
    }
    
    public Header getHeader(byte packetId){
        Header h = new Header();
        h.setPacketFormat((short)2018);
        h.setPacketVersion((byte)1);
        h.setPacketId(packetId);
        h.setSessionUid(123124125);
        h.setSessionTime(1.25f);
        h.setFrameIdentifier(50);
        h.setPlayerCarIndex((byte)0);
        return h;
    }
    
    public PacketParticipantsData getParticipants(){
        PacketParticipantsData ret = new PacketParticipantsData();
        Header head = getHeader((byte)4);
        ret.setHeader(head);
        ret.setNumCars((byte)20);
        String[] names = {
            "aaa",
            "bbb",
            "ccc",
            "ddd",
            "eee",
            "fff",
            "ggg",
            "hhh",
            "iii",
            "jjj",
            "kkk",
            "lll",
            "mmm",
            "nnn",
            "ooo",
            "ppp",
            "qqq",
            "rrr",
            "sss",
            "ttt",
            "uuu",
            "vvv",
            "www",
            "xxx",
            "yyy",
            "zzz"
        };
        for(int i=0;i<20;i++){
            ParticipantData parta = new ParticipantData();
            parta.setAiControlled((byte)1);
            parta.setDriverId((byte)0);
            parta.setNationality((byte)0);
            parta.setRaceNumber((byte)0);
            parta.setTeamId((byte)0);
            parta.setName(names[i]);
            ret.setParticipantData(i, parta);
        }
        return ret;
    }
    public PacketLapData getLapData(){
        PacketLapData testObj = new PacketLapData();
        Header h = getHeader((byte)2);
        testObj.setHeader(h);
        for(int i = 0;i<20;i++){
            LapData l = new LapData();
            l.setLastLapTime(76.500f);
            l.setCurrentLapTime(30.213f);
            l.setBestLapTime(71.400f);
            l.setSector1Time(30.125f);
            l.setSector2Time(34.534f);
            l.setLapDistance(42.555f);
            l.setTotalDistance(100.0f);
            l.setSafetyCarDelta(1.245f);
            l.setCarPosition((byte)(i+1));
            l.setCurrentLapNum((byte) 11);
            l.setPitStatus((byte)0);
            l.setSector((byte)1);
            l.setCurrentLapInvalid((byte)0);
            l.setPenalties((byte)3);
            l.setGridPosition((byte) 12);
            l.setDriverStatus((byte)1);
            l.setResultStatus((byte)2);
            testObj.setLapData(i, l);    
        }
        return testObj;
    }
    
    public PacketMotionData getMotionData(){
        PacketMotionData ret = new PacketMotionData();
        ret.setHeader(getHeader((byte)0));
        ret.setAngularAccelerationX(5.0f);
        ret.setAngularAccelerationY(6.0f);
        ret.setAngularAccelerationZ(7.0f);
        ret.setAngularVelocityX(1.0f);
        ret.setAngularVelocityY(2.0f);
        ret.setAngularVelocityZ(3.0f);
        ret.setFrontWheelsAngle(4.0f);
        ret.setLocalVelocityX(1.1f);
        ret.setLocalVelocityY(1.2f);
        ret.setLocalVelocityZ(1.3f);
        for(int i=0;i<4;i++){
            ret.setSuspensionAcceleration(i, 1.4f+i/10.0f);
            ret.setSuspensionPosition(i, 1.8f+i/10.0f);
            ret.setSuspensionVelocity(i, 2.2f+i/10.0f);
            ret.setWheelSlip(i, 2.6f+i/10.0f);
            ret.setWheelSpeed(i, 3.0f+i/10.0f);
        }
        for(int i=0;i<20;i++){
            CarMotionData data = new CarMotionData();
            data.setGForceLateral(3.5f);
            data.setGForceLongitudinal(3.6f);
            data.setGForceVertical(3.7f);
            data.setPitch(3.8f);
            data.setRoll(3.9f);
            data.setWordRightDirX((short)20);
            data.setWorldForwardDirX((short)21);
            data.setWorldForwardDirY((short)22);
            data.setWorldForwardDirZ((short)23);
            data.setWorldPositionX(4.0f);
            data.setWorldPositionY(4.1f);
            data.setWorldPositionZ(4.1f);
            data.setWorldRightDirY((short)24);
            data.setWorldRightDirZ((short)25);
            data.setWorldVelocityX(4.2f);
            data.setWorldVelocityY(4.3f);
            data.setWorldVelocityZ(4.4f);
            data.setYaw(4.5f);
            ret.setCarMotionData(i, data);
        }
        
        return ret;
    }
    
    public PacketCarTelemetryData getCarTelemetryData(){
        PacketCarTelemetryData ret = new PacketCarTelemetryData();
        ret.setHeader(getHeader((byte)6));
        for(int i=0;i<20;i++){
            CarTelemetryData data = new CarTelemetryData();
            data.setBrake((byte)50);
            data.setClutch((byte)51);
            data.setDrs((byte)53);
            data.setEngineRPM((short)12000);
            data.setEngineTemperature((short)100);
            data.setGear((byte)5);
            data.setRevLightsPercent((byte)54);
            data.setSpeed((short)200);
            data.setSteer((byte)50);
            data.setThrottle((byte)50);
            
            for(int j=0;j<4;j++){
                data.setBrakeTemperature(j, (byte)(100+j));
                data.setTyreInnerTemperature(j, (byte)(100+j));
                data.setTyrePressure(j, 2.2f);
                data.setTyreSurfaceTemperature(j, (byte)(100+j));
            }
            ret.setCarTelemetryData(i, data);
        }
        return ret;
    }
}

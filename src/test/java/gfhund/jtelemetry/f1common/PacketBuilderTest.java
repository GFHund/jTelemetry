/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfhund.jtelemetry.f1common;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
/**
 *
 * @author PhilippHolzmann
 */
public class PacketBuilderTest {
    
    @Test
    void PacketMotionDataTest(){
        gfhund.jtelemetry.f1y19.PacketMotionData packetMotionData = new gfhund.jtelemetry.f1y19.PacketMotionData();
        gfhund.jtelemetry.f1y19.Header header = getHeader2019();
        
        packetMotionData.setHeader(header);
        for(int i=0;i<20;i++){
            packetMotionData.setCarMotionData(i, this.getCarMotionData2019(i));
        }
        
        byte[] raw = packetMotionData.getBytes();
        try{
            gfhund.jtelemetry.f1y19.PacketMotionData afterParse = 
                    (gfhund.jtelemetry.f1y19.PacketMotionData) PacketBuilder.parseUDPPacket2019(raw);
            for(int i=0;i<20;i++){
                assertEquals(afterParse.getCarMotionData(0).getWorldForwardDirX(), packetMotionData.getCarMotionData(0).getWorldForwardDirX());
            }
            
        }
        catch(ParseException e){
            System.out.println(e.getMessage());
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
    
    gfhund.jtelemetry.f1y19.Header getHeader2019(){
        gfhund.jtelemetry.f1y19.Header header = new gfhund.jtelemetry.f1y19.Header();
        header.setPacketFormat((short)2019);
        header.setGameMajorVersion((byte)1);
        header.setGameMinorVersion((byte)0);
        header.setPacketVersion((byte)1);
        header.setPacketId((byte)0);
        header.setSessionUid(504321);
        header.setSessionTime(5.55f);
        header.setFrameIdentifier(205);
        header.setPlayerCarIndex((byte)1);
        return header;
    }
}

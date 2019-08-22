/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfhund.jtelemetry.f1y19;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 *
 * @author PhilippHolzmann
 */
public class PacketSessionData extends gfhund.jtelemetry.f1y18.PacketSessionData {
    protected Header header19;
    
    public Header getHeader19(){
        return this.header19;
    }
    public void setHeader19(Header header){
        this.header19 = header;
    }
    
     public static int getSize(){
        return 149;
    }
    
    public byte[] getBytes(){
        ByteBuffer ret = ByteBuffer.allocate(gfhund.jtelemetry.f1y19.PacketSessionData.getSize());
        ret.order(ByteOrder.LITTLE_ENDIAN);
        byte[] headerData = header19.getBytes();
        for(int i=0;i<headerData.length;i++){
            ret.put(i,headerData[i]);
        }
        ret.put(21,this.weather);
        ret.put(22,this.trackTemperature);
        ret.put(23,this.airTemperature);
        ret.put(24,this.totalLaps);
        ret.putShort(25,this.trackLength);
        ret.put(27,this.sessionType);
        ret.put(28,this.trackId);
        ret.put(29,this.era);
        ret.putShort(30,this.sessionTimeLeft);
        ret.putShort(32,this.sessionDuration);
        ret.put(35,this.pitSpeedLimit);
        ret.put(36,gamePaused);
        ret.put(37,isSpectating);
        ret.put(38,spectatorCarIndex);
        ret.put(39,sliProNativeSupport);
        ret.put(40,numMarshalZones);
        for(int i=0;i<numMarshalZones;i++){
            byte[] marshalZone = this.marshalZones[i].getBytes();
            for(int j=0;j<marshalZone.length;j++){
                ret.put(41+j,marshalZone[j]);
            }
        }
        int offset = 41+gfhund.jtelemetry.f1y18.MarshalZone.getSize()*numMarshalZones;
        ret.put(offset,safetyCarStatus);
        ret.put(offset+1,networkGame);
        return ret.array();
    }
}

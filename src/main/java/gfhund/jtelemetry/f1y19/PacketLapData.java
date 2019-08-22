/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfhund.jtelemetry.f1y19;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import gfhund.jtelemetry.commontelemetry.AbstractPacket;

/**
 *
 * @author PhilippHolzmann
 */
public class PacketLapData extends AbstractPacket {
    private Header header;
    private LapData[] lapData = new LapData[20];

    public void setHeader(Header head){
        this.header = head;
    }
    public void setLapData(int i,LapData lapData){
        this.lapData[i] = lapData;
    }
    
    public Header getHeader(){
        return this.header;
    }
    public LapData getLapData(int index){
        return this.lapData[index];
    }
    public LapData[] getLapData(){
        return this.lapData;
    }
    
    public byte[] getBytes(){
        ByteBuffer ret = ByteBuffer.allocate(841);
        ret.order(ByteOrder.LITTLE_ENDIAN);
        byte[] headerData = this.header.getBytes();
        for(int i =0;i<headerData.length;i++){
            ret.put(i,headerData[i]);
        }
        for(int i=0;i<20;i++){
            byte[] lapData = this.lapData[i].getBytes();
            int offset = Header.getSize()+i*LapData.getSize();
            for(int k=0;k<lapData.length;k++){
                ret.put(offset+k,lapData[k]);
            }
        }
        return ret.array();
    }
}

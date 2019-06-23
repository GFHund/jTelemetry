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
    private gfhund.jtelemetry.f1y18.Header m_header;
    private gfhund.jtelemetry.f1y18.LapData[] m_lapData = new gfhund.jtelemetry.f1y18.LapData[20];

    public void setHeader(gfhund.jtelemetry.f1y18.Header head){
        this.m_header = head;
    }
    public void setLapData(int i,gfhund.jtelemetry.f1y18.LapData lapData){
        this.m_lapData[i] = lapData;
    }
    
    public gfhund.jtelemetry.f1y18.Header getHeader(){
        return this.m_header;
    }
    public gfhund.jtelemetry.f1y18.LapData getLapData(int index){
        return this.m_lapData[index];
    }
    
    public byte[] getBytes(){
        ByteBuffer ret = ByteBuffer.allocate(841);
        ret.order(ByteOrder.LITTLE_ENDIAN);
        byte[] headerData = this.m_header.getBytes();
        for(int i =0;i<headerData.length;i++){
            ret.put(i,headerData[i]);
        }
        for(int i=0;i<20;i++){
            byte[] lapData = m_lapData[i].getBytes();
            int offset = gfhund.jtelemetry.f1y18.Header.getSize()+i*gfhund.jtelemetry.f1y18.LapData.getSize();
            for(int k=0;k<lapData.length;k++){
                ret.put(offset+k,lapData[k]);
            }
        }
        return ret.array();
    }
}

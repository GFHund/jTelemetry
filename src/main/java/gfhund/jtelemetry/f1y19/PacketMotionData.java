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
public class PacketMotionData extends AbstractPacket {
    protected Header header;
    protected CarMotionData[] carMotionData = new CarMotionData[20];
    
    public void setHeader(Header head){
        this.header = head;
    }
    public Header getHeader(){
        return this.header;
    }
    public void setCarMotionData(int i,CarMotionData value){
        this.carMotionData[i] = value;
    }
    public CarMotionData getCarMotionData(int i){
        return this.carMotionData[i];
    }
    public CarMotionData[] getCarMotionData(){
        return this.carMotionData;
    }
    
    public static int getSize(){
        return 1343;
    }
    
    public byte[] getBytes(){
        ByteBuffer ret = ByteBuffer.allocate(PacketMotionData.getSize());
        ret.order(ByteOrder.LITTLE_ENDIAN);
        byte[] headerData = this.header.getBytes();
        for(int i=0;i<headerData.length;i++){
            ret.put(i,headerData[i]);
        }
        for(int i=0;i<20;i++){
            byte[] carMotionData = this.carMotionData[i].getBytes();
            int offset = Header.getSize()+i*CarMotionData.getSize();
            for(int k=0;k<carMotionData.length;k++){
                ret.put(offset+k,carMotionData[k]);
            }
        }
        return ret.array();
    }
}

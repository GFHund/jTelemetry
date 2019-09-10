/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfhund.jtelemetry.f1y19;

import gfhund.jtelemetry.commontelemetry.AbstractPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 *
 * @author PhilippHolzmann
 */
public class PacketCarTelemetryData extends AbstractPacket {
    private Header header19;
    private CarTelemetryData[] carTelemetryData = new CarTelemetryData[20];
    private int buttonStatus;
    
    public void setHeader19(Header head){
        this.header19 = head;
    }
    public Header getHeader19(){
        return this.header19;
    }
    public void setCarTelemetryData(int i,CarTelemetryData data){
        this.carTelemetryData[i] = data;
    }
    public CarTelemetryData getCarTelemetryData(int i){
        return this.carTelemetryData[i];
    }
    public CarTelemetryData[] getCarTelemetryData(){
        return this.carTelemetryData;
    }
    public void setButtonStatus(int buttonStatus){
        this.buttonStatus = buttonStatus;
    }
    public int getButtonStatus(){
        return this.buttonStatus;
    }
    public static int getSize(){
        return 1347;
    }
    public byte[] getBytes(){
        ByteBuffer ret = ByteBuffer.allocate(getSize());
        ret.order(ByteOrder.LITTLE_ENDIAN);
        byte[] headerData = this.header19.getBytes();
        for(int i =0;i<headerData.length;i++){
            ret.put(i,headerData[i]);
        }
        for(int i=0;i<20;i++){
            byte[] lapData = this.carTelemetryData[i].getBytes();
            int offset = Header.getSize()+i*CarTelemetryData.getSize();
            for(int k=0;k<lapData.length;k++){
                ret.put(offset+k,lapData[k]);
            }
        }
        ret.putInt(buttonStatus);
        byte[] raw = ret.array();
        return raw;
    }
}

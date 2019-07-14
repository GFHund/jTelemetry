/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfhund.jtelemetry.f1y19;

import gfhund.jtelemetry.commontelemetry.AbstractPacket;

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
    public void setButtonStatus(int buttonStatus){
        this.buttonStatus = buttonStatus;
    }
}

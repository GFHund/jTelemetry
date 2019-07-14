/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfhund.jtelemetry.f1y19;

/**
 *
 * @author PhilippHolzmann
 */
public class CarTelemetryData extends gfhund.jtelemetry.f1y18.CarTelemetryData {
    protected byte surfaceType;
    
    public void setSurfaceType(byte type){
        this.surfaceType = type;
    }
    public byte getSurfaceType(){
        return this.surfaceType;
    }
    
    public static int getSize(){
        return 54;
    }
}

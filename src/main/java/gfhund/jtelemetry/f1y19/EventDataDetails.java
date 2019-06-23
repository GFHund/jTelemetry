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
public abstract class EventDataDetails {
     protected byte vehicleIdx;
     public byte getVehicleIdx(){
        return this.vehicleIdx;
    }
    public void setVehicleIdx( byte index){
        this.vehicleIdx = index;
    }
}

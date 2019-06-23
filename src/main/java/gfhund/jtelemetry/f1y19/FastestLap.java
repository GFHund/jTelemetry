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
public class FastestLap extends EventDataDetails {
   
    protected float lapTime;
    
    
    public float getLapTime(){
        return this.lapTime;
    }
    public void setLapTime(float lapTime){
        this.lapTime = lapTime;
    }
}

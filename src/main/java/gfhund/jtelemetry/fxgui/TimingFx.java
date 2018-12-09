/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfhund.jtelemetry.fxgui;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import gfhund.jtelemetry.data.Timing;
/**
 *
 * @author PhilippGL
 */
public class TimingFx {
    private final SimpleFloatProperty lapTime;
    private final SimpleFloatProperty sector1Time;
    private final SimpleFloatProperty sector2Time;
    private final SimpleFloatProperty sector3Time;
    private final SimpleFloatProperty sector4Time;
    private final SimpleFloatProperty sector5Time;
    
    public TimingFx(){
        this.lapTime = new SimpleFloatProperty(0.0f);
        this.sector1Time = new SimpleFloatProperty(0.0f);
        this.sector2Time = new SimpleFloatProperty(0.0f);
        this.sector3Time = new SimpleFloatProperty(0.0f);
        this.sector4Time = new SimpleFloatProperty(0.0f);
        this.sector5Time = new SimpleFloatProperty(0.0f);
    }
    public TimingFx(Timing timing){
        this.lapTime = new SimpleFloatProperty(timing.getLapTime());
        this.sector1Time = new SimpleFloatProperty(timing.getSector1Time());
        this.sector2Time = new SimpleFloatProperty(timing.getSector2Time());
        this.sector3Time = new SimpleFloatProperty(timing.getSector3Time());
        this.sector4Time = new SimpleFloatProperty(timing.getSector4Time());
        this.sector5Time = new SimpleFloatProperty(timing.getSector5Time());
    }

    public float getLapTime() {
        return lapTime.get();
    }

    public float getSector1Time() {
        return sector1Time.get();
    }

    public float getSector2Time() {
        return sector2Time.get();
    }

    public float getSector3Time() {
        return sector3Time.get();
    }

    public float getSector4Time() {
        return sector4Time.get();
    }

    public float getSector5Time() {
        return sector5Time.get();
    }
    
    public void setLapTime(float value){
        this.lapTime.set(value);
    }
    public void setSector1Time(float value){
        this.sector1Time.set(value);
    }
    public void setSector2Time(float value){
        this.sector2Time.set(value);
    }
    public void setSector3Time(float value){
        this.sector3Time.set(value);
    }
    public void setSector4Time(float value){
        this.sector4Time.set(value);
    }
    public void setSector5Time(float value){
        this.sector5Time.set(value);
    }
    
    
}

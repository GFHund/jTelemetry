/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfhund.jtelemetry.fxgui;

import gfhund.jtelemetry.commontelemetry.LapIdentificationObject;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import gfhund.jtelemetry.data.Timing;
import javafx.beans.property.SimpleStringProperty;
/**
 *
 * @author PhilippGL
 */
public class TimingFx {
    private LapIdentificationObject lapId;
    private final SimpleIntegerProperty lapNum;
    private final SimpleFloatProperty lapTime;
    private final SimpleFloatProperty sector1Time;
    private final SimpleFloatProperty sector2Time;
    private final SimpleFloatProperty sector3Time;
    private final SimpleFloatProperty sector4Time;
    private final SimpleFloatProperty sector5Time;
    private final SimpleStringProperty playerName;
    
    public TimingFx(){
        this.lapNum = new SimpleIntegerProperty(0);
        this.lapTime = new SimpleFloatProperty(0.0f);
        this.sector1Time = new SimpleFloatProperty(0.0f);
        this.sector2Time = new SimpleFloatProperty(0.0f);
        this.sector3Time = new SimpleFloatProperty(0.0f);
        this.sector4Time = new SimpleFloatProperty(0.0f);
        this.sector5Time = new SimpleFloatProperty(0.0f);
        this.playerName = new SimpleStringProperty("");
    }
    public TimingFx(Timing timing){
        this.lapNum = new SimpleIntegerProperty(timing.getLapNum());
        this.lapTime = new SimpleFloatProperty(timing.getLapTime());
        this.sector1Time = new SimpleFloatProperty(timing.getSector1Time());
        this.sector2Time = new SimpleFloatProperty(timing.getSector2Time());
        this.sector3Time = new SimpleFloatProperty(timing.getSector3Time());
        this.sector4Time = new SimpleFloatProperty(timing.getSector4Time());
        this.sector5Time = new SimpleFloatProperty(timing.getSector5Time());
        this.playerName = new SimpleStringProperty("");
    }
    
    public LapIdentificationObject getLapIdentificationObject(){
        return this.lapId;
    }
    public void setLapIdentificationObject(LapIdentificationObject lapId){
        this.lapId = lapId;
    }

    public int getLapNum(){
        return lapNum.get();
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
    
    public void setLapNum(int value){
        this.lapNum.set(value);
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
    
    public void setPlayerName(String playerName){
        this.playerName.set(playerName);
    }
    public String getPlayerName(){
        return this.playerName.get();
    }
}

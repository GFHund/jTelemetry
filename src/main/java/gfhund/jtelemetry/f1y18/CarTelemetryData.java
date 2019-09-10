package gfhund.jtelemetry.f1y18;

import gfhund.jtelemetry.commontelemetry.AbstractSubPackage;

public class CarTelemetryData extends AbstractSubPackage{
    protected short speed;//0 2
    protected byte throttle;//2 1
    protected byte steer;//3 1
    protected byte brake;//4 1
    protected byte clutch;//5 1
    protected byte gear;//6 1
    protected short engineRPM;//7 2
    protected byte drs;//9 1
    protected byte revLightsPercent;//10 1
    protected short[] brakesTemperature = new short[4];//11 4*2 = 8
    protected short[] tyresSurfaceTemperature = new short[4];//19 8
    protected short[] tyresInnerTemperature = new short[4];//27 8
    protected short engineTemperature;//35 2
    protected float[] tyresPressure = new float[4];//37 4*4 = 16
    //53
    public static int getSize(){
        return 53;
    }
    public void setSpeed(short speed){
        this.speed = speed;
    }
    public short getSpeed(){
        return this.speed;
    }
    public void setThrottle(byte throttle){
        this.throttle = throttle;
    }
    public byte getThrottle(){
        return this.throttle;
    }
    public void setSteer(byte steer){
        this.steer = steer;
    }
    public byte getSteer(){
        return this.steer;
    }
    public void setBrake(byte brake){
        this.brake = brake;
    }
    public byte getBrake(){
        return this.brake;
    }
    public void setClutch(byte clutch){
        this.clutch = clutch;
    }
    public byte getClutch(){
        return this.clutch;
    }
    public void setGear(byte gear){
        this.gear = gear;
    }
    public byte getGear(){
        return this.gear;
    }
    public void setEngineRPM(short engineRpm){
        this.engineRPM = engineRpm;
    }
    public short getEngineRPM(){
        return this.engineRPM;
    }
    public void setDrs(byte drs){
        this.drs = drs;
    }
    public byte getDrs(){
        return this.drs;
    }
    public void setRevLightsPercent(byte revLightsPercent){
        this.revLightsPercent = revLightsPercent;
    }
    public byte getRevLightsPercent(){
        return this.revLightsPercent;
    }
    @Deprecated
    public void setBrakeTemperature(int i,short temperature){
        this.brakesTemperature[i] = temperature;
    }
    public void setBrakesTemperature(int i,short temperature){
        this.brakesTemperature[i] = temperature;
    }
    public short getBrakeTemperature(int i){
        return this.brakesTemperature[i];
    }
    public short[] getBrakesTemperature(){
        return this.brakesTemperature;
    }
    @Deprecated
    public void setTyreSurfaceTemperature(int i,short temperature){
        this.tyresSurfaceTemperature[i] = temperature;
    }
    public void setTyresSurfaceTemperature(int i,short temperature){
        this.tyresSurfaceTemperature[i] = temperature;
    }
    public short getTyreSurfaceTemperature(int i){
        return this.tyresSurfaceTemperature[i];
    }
    public short[] getTyresSurfaceTemperature(){
        return this.tyresSurfaceTemperature;
    }
    @Deprecated
    public void setTyreInnerTemperature(int i,short temperature){
        this.tyresInnerTemperature[i] = temperature;
    }
    public void setTyresInnerTemperature(int i,short temperature) {
        this.tyresInnerTemperature[i] = temperature;
    }
    public short getTyreInnerTemperature(int i){
        return this.tyresInnerTemperature[i];
    }
    public short[] getTyresInnerTemperature(){
        return this.tyresInnerTemperature;
    }
    public void setEngineTemperature(short temperature){
        this.engineTemperature = temperature;
    }
    public short getEngineTemperature(){
        return this.engineTemperature;
    }
    public void setTyrePressure(int i,float pressure){
        this.tyresPressure[i] = pressure;
    }
    public float getTyrePressure(int i){
        return this.tyresPressure[i];
    }
}
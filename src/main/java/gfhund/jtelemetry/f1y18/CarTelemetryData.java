package gfhund.jtelemetry.f1y18;

public class CarTelemetryData{
    protected short m_speed;//0 2
    protected byte m_throttle;//2 1
    protected byte m_steer;//3 1
    protected byte m_brake;//4 1
    protected byte m_clutch;//5 1
    protected byte m_gear;//6 1
    protected short m_engineRPM;//7 2
    protected byte m_drs;//9 1
    protected byte m_revLightsPercent;//10 1
    protected short[] m_brakesTemperature = new short[4];//11 4*2 = 8
    protected short[] m_tyresSurfaceTemperature = new short[4];//19 8
    protected short[] m_tyresInnerTemperature = new short[4];//27 8
    protected short m_engineTemperature;//35 2
    protected float[] m_tyresPressure = new float[4];//37 4*4 = 16
    //53
    public static int getSize(){
        return 53;
    }
    public void setSpeed(short speed){
        this.m_speed = speed;
    }
    public short getSpeed(){
        return this.m_speed;
    }
    public void setThrottle(byte throttle){
        this.m_throttle = throttle;
    }
    public byte getThrottle(){
        return this.m_throttle;
    }
    public void setSteer(byte steer){
        this.m_steer = steer;
    }
    public byte getSteer(){
        return this.m_steer;
    }
    public void setBrake(byte brake){
        this.m_brake = brake;
    }
    public byte getBrake(){
        return this.m_brake;
    }
    public void setClutch(byte clutch){
        this.m_clutch = clutch;
    }
    public byte getClutch(){
        return this.m_clutch;
    }
    public void setGear(byte gear){
        this.m_gear = gear;
    }
    public byte getGear(){
        return this.m_gear;
    }
    public void setEngineRPM(short engineRpm){
        this.m_engineRPM = engineRpm;
    }
    public short getEngineRPM(){
        return this.m_engineRPM;
    }
    public void setDrs(byte drs){
        this.m_drs = drs;
    }
    public byte getDrs(){
        return this.m_drs;
    }
    public void setRevLightsPercent(byte revLightsPercent){
        this.m_revLightsPercent = revLightsPercent;
    }
    public byte getRevLightsPercent(){
        return this.m_revLightsPercent;
    }
    public void setBrakeTemperature(int i,short temperature){
        this.m_brakesTemperature[i] = temperature;
    }
    public short getBrakeTemperature(int i){
        return this.m_brakesTemperature[i];
    }
    public void setTyreSurfaceTemperature(int i,short temperature){
        this.m_tyresSurfaceTemperature[i] = temperature;
    }
    public short getTyreSurfaceTemperature(int i){
        return this.m_tyresSurfaceTemperature[i];
    }
    public void setTyreInnerTemperature(int i,short temperature){
        this.m_tyresInnerTemperature[i] = temperature;
    }
    public short getTyreInnerTemperature(int i){
        return this.m_tyresInnerTemperature[i];
    }
    public void setEngineTemperature(short temperature){
        this.m_engineTemperature = temperature;
    }
    public short getEngineTemperature(){
        return this.m_engineTemperature;
    }
    public void setTyrePressure(int i,float pressure){
        this.m_tyresPressure[i] = pressure;
    }
    public float getTyrePressure(int i){
        return this.m_tyresPressure[i];
    }
}
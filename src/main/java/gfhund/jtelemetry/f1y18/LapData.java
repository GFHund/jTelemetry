package gfhund.jtelemetry.f1y18;

import java.nio.ByteBuffer;

public class LapData {
    private float m_lastLapTime;//0
    private float m_currentLapTime;//4
    private float m_bestLapTime;//8
    private float m_sector1Time;//12
    private float m_sector2Time;//16
    private float m_lapDistance;//20
    private float m_totalDistance;//24
    private float m_safetyCarDelta;//28
    private byte m_carPosition;//32
    private byte m_currentLapNum;//33
    private byte m_pitStatus;//34  0 = none, 1 = pitting, 2 = in pit area
    private byte m_sector;//35
    private byte m_currentLapInvalid;//36
    private byte m_penalties;//37
    private byte m_gridPosition;//38
    private byte m_driverStatus;//39  Status of driver - 0 = in garage, 1 = flying lap
    // 2 = in lap, 3 = out lap, 4 = on track
    private byte m_resultStatus;//40 Result status - 0 = invalid, 1 = inactive, 2 = active
    // 3 = finished, 4 = disqualified, 5 = not classified
    // 6 = retired
    public static int getSize(){
        return 41;
    }

    public void setLastLapTime(float lastLapTime) {
        this.m_lastLapTime = lastLapTime;
    }
    public void setCurrentLapTime(float currentLapTime) {
        this.m_currentLapTime = currentLapTime;
    }
    public void setBestLapTime(float bestLapTime) {
        this.m_bestLapTime = bestLapTime;
    }
    public void setSector1Time(float sector1Time) {
        this.m_sector1Time = sector1Time;
    }
    public void setSector2Time(float sector2Time) {
        this.m_sector2Time = sector2Time;
    }
    public void setLapDistance(float lapDistance) {
        this.m_lapDistance = lapDistance;
    }
    public void setTotalDistance(float totalDistance) {
        this.m_totalDistance = totalDistance;
    }
    public void setSafetyCarDelta(float safetyCarDelta) {
        this.m_safetyCarDelta = safetyCarDelta;
    }
    public void setCarPosition(byte carPosition) {
        this.m_carPosition = carPosition;
    }
    public void setCurrentLapNum(byte currentLapNum){
        this.m_currentLapNum = currentLapNum;
    }
    public void setPitStatus(byte pitStatus){
        this.m_pitStatus = pitStatus;
    }
    public void setSector(byte sector){
        this.m_sector = sector;
    }
    public void setCurrentLapInvalid(byte currentLapInvalid){
        this.m_currentLapInvalid = currentLapInvalid;
    }
    public void setPenalties(byte penalties){
        this.m_penalties = penalties;
    }
    public void setGridPosition(byte gridPosition){
        this.m_gridPosition = gridPosition;
    }
    public void setDriverStatus(byte driverStatus){
        this.m_driverStatus = driverStatus;
    }
    public void setResultStatus(byte resultStatus){
        this.m_resultStatus = resultStatus;
    }
    
    public float getCurrentLapTime(){
        return this.m_currentLapTime;
    }
    public float getSector1Time(){
        return this.m_sector1Time;
    }
    public float getSector2Time(){
        return this.m_sector2Time;
    }
    public float getLastLapTime(){
        return this.m_lastLapTime;
    }
    public float getBestLapTime(){
        return this.m_bestLapTime;
    }
    public byte getCarPosition(){
        return this.m_carPosition;
    }
    public byte[] getBytes(){
        ByteBuffer lapDataBuffer = ByteBuffer.allocate(41);
        lapDataBuffer.putFloat(0, m_lastLapTime);
        lapDataBuffer.putFloat(4, m_currentLapTime);
        lapDataBuffer.putFloat(8, m_bestLapTime);
        lapDataBuffer.putFloat(12,m_sector1Time);
        lapDataBuffer.putFloat(16,m_sector2Time);
        lapDataBuffer.putFloat(20,m_lapDistance);
        lapDataBuffer.putFloat(24, m_totalDistance);
        lapDataBuffer.putFloat(28, m_safetyCarDelta);
        lapDataBuffer.put(32,m_carPosition);
        lapDataBuffer.put(33,m_currentLapNum);
        lapDataBuffer.put(34,m_pitStatus);
        lapDataBuffer.put(35,m_sector);
        lapDataBuffer.put(36,m_currentLapInvalid);
        lapDataBuffer.put(37,m_penalties);
        lapDataBuffer.put(38,m_gridPosition);
        lapDataBuffer.put(39,m_driverStatus);
        lapDataBuffer.put(40,m_resultStatus);
        return lapDataBuffer.array();
    }
}
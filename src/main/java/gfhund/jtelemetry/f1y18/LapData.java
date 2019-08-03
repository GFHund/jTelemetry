package gfhund.jtelemetry.f1y18;

import gfhund.jtelemetry.commontelemetry.AbstractSubPackage;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class LapData extends AbstractSubPackage{
    protected float lastLapTime;//0
    protected float currentLapTime;//4
    protected float bestLapTime;//8
    protected float sector1Time;//12
    protected float sector2Time;//16
    protected float lapDistance;//20
    protected float totalDistance;//24
    protected float safetyCarDelta;//28
    protected byte carPosition;//32
    protected byte currentLapNum;//33
    protected byte pitStatus;//34  0 = none, 1 = pitting, 2 = in pit area
    protected byte sector;//35
    protected byte currentLapInvalid;//36
    protected byte penalties;//37
    protected byte gridPosition;//38
    protected byte driverStatus;//39  Status of driver - 0 = in garage, 1 = flying lap
    // 2 = in lap, 3 = out lap, 4 = on track
    protected byte resultStatus;//40 Result status - 0 = invalid, 1 = inactive, 2 = active
    // 3 = finished, 4 = disqualified, 5 = not classified
    // 6 = retired
    public static int getSize(){
        return 41;
    }

    public void setLastLapTime(float lastLapTime) {
        this.lastLapTime = lastLapTime;
    }
    public void setCurrentLapTime(float currentLapTime) {
        this.currentLapTime = currentLapTime;
    }
    public void setBestLapTime(float bestLapTime) {
        this.bestLapTime = bestLapTime;
    }
    public void setSector1Time(float sector1Time) {
        this.sector1Time = sector1Time;
    }
    public void setSector2Time(float sector2Time) {
        this.sector2Time = sector2Time;
    }
    public void setLapDistance(float lapDistance) {
        this.lapDistance = lapDistance;
    }
    public void setTotalDistance(float totalDistance) {
        this.totalDistance = totalDistance;
    }
    public void setSafetyCarDelta(float safetyCarDelta) {
        this.safetyCarDelta = safetyCarDelta;
    }
    public void setCarPosition(byte carPosition) {
        this.carPosition = carPosition;
    }
    public void setCurrentLapNum(byte currentLapNum){
        this.currentLapNum = currentLapNum;
    }
    public void setPitStatus(byte pitStatus){
        this.pitStatus = pitStatus;
    }
    public void setSector(byte sector){
        this.sector = sector;
    }
    public void setCurrentLapInvalid(byte currentLapInvalid){
        this.currentLapInvalid = currentLapInvalid;
    }
    public void setPenalties(byte penalties){
        this.penalties = penalties;
    }
    public void setGridPosition(byte gridPosition){
        this.gridPosition = gridPosition;
    }
    public void setDriverStatus(byte driverStatus){
        this.driverStatus = driverStatus;
    }
    public void setResultStatus(byte resultStatus){
        this.resultStatus = resultStatus;
    }
    
    public float getCurrentLapTime(){
        return this.currentLapTime;
    }
    public float getSector1Time(){
        return this.sector1Time;
    }
    public float getSector2Time(){
        return this.sector2Time;
    }
    public float getLastLapTime(){
        return this.lastLapTime;
    }
    public float getBestLapTime(){
        return this.bestLapTime;
    }
    public byte getCarPosition(){
        return this.carPosition;
    }
    public byte getCurrentLapNum(){
        return this.currentLapNum;
    }
    public float getLapDistance(){
        return this.lapDistance;
    }
    public byte[] getBytes(){
        ByteBuffer lapDataBuffer = ByteBuffer.allocate(41);
        lapDataBuffer.order(ByteOrder.LITTLE_ENDIAN);
        lapDataBuffer.putFloat(0, lastLapTime);
        lapDataBuffer.putFloat(4, currentLapTime);
        lapDataBuffer.putFloat(8, bestLapTime);
        lapDataBuffer.putFloat(12,sector1Time);
        lapDataBuffer.putFloat(16,sector2Time);
        lapDataBuffer.putFloat(20,lapDistance);
        lapDataBuffer.putFloat(24, totalDistance);
        lapDataBuffer.putFloat(28, safetyCarDelta);
        lapDataBuffer.put(32,carPosition);
        lapDataBuffer.put(33,currentLapNum);
        lapDataBuffer.put(34,pitStatus);
        lapDataBuffer.put(35,sector);
        lapDataBuffer.put(36,currentLapInvalid);
        lapDataBuffer.put(37,penalties);
        lapDataBuffer.put(38,gridPosition);
        lapDataBuffer.put(39,driverStatus);
        lapDataBuffer.put(40,resultStatus);
        return lapDataBuffer.array();
    }
}
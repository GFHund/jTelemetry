/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfhund.jtelemetry.commontelemetry;

import gfhund.jtelemetry.Vector3D;

/**
 *
 * @author PhilippHolzmann
 */
public class CommonTelemetryData implements Cloneable{
    private short speed = 0;
    private short rpm = 0;
    private byte gear = 0;
    private byte throttle = 0;
    private byte brake = 0;
    private float distance = 0;//distance between current Position and Startline
    private float currentTime = 0;//time since this round starts;
    private short carIndex = 0;
    private String driverName = "";
    private short lapNum = 0;
    private Vector3D pos = new Vector3D(0, 0, 0);
    private short tyreSurfaceTempFR;//Front Right
    private short tyreSurfaceTempFL;//Front Left
    private short tyreSurfaceTempRR;//Rear Right
    private short tyreSurfaceTempRL;//Rear Left
    private short tyreInnerTempFR;//Front Right
    private short tyreInnerTempFL;//Front Left
    private short tyreInnerTempRR;//Rear Right
    private short tyreInnerTempRL;//Rear Left
    private byte ersDeployMode;
    private float ersHarvestMGUK;//Energie aus vom Bremsen wiedergewinnen
    private float ersHarvestMGUH;//Energie aus den Abgasen wiedergewinnen
    private float ersDeployed;
    private float ersStoreEngergy;//Gesamt Batterie stand
    
    public short getSpeed() {
        return speed;
    }

    public void setSpeed(short speed) {
        this.speed = speed;
    }

    public short getRpm() {
        return rpm;
    }

    public void setRpm(short rpm) {
        this.rpm = rpm;
    }

    public byte getGear() {
        return gear;
    }

    public void setGear(byte gear) {
        this.gear = gear;
    }

    public byte getThrottle() {
        return throttle;
    }

    public void setThrottle(byte throttle) {
        this.throttle = throttle;
    }

    public byte getBrake() {
        return brake;
    }

    public void setBrake(byte brake) {
        this.brake = brake;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public float getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(float currentTime) {
        this.currentTime = currentTime;
    }

    public short getCarIndex() {
        return carIndex;
    }

    public void setCarIndex(short carIndex) {
        this.carIndex = carIndex;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        driverName = driverName.trim();
        this.driverName = driverName;
    }

    public short getLapNum() {
        return lapNum;
    }

    public void setLapNum(short lapNum) {
        this.lapNum = lapNum;
    }

    public Vector3D getPos() {
        return pos;
    }

    public void setPos(Vector3D pos) {
        this.pos = pos;
    }

    public short getTyreSurfaceTempFR() {
        return tyreSurfaceTempFR;
    }

    public void setTyreSurfaceTempFR(short tyreSurfaceTempFR) {
        this.tyreSurfaceTempFR = tyreSurfaceTempFR;
    }

    public short getTyreSurfaceTempFL() {
        return tyreSurfaceTempFL;
    }

    public void setTyreSurfaceTempFL(short tyreSurfaceTempFL) {
        this.tyreSurfaceTempFL = tyreSurfaceTempFL;
    }

    public short getTyreSurfaceTempRR() {
        return tyreSurfaceTempRR;
    }

    public void setTyreSurfaceTempRR(short tyreSurfaceTempRR) {
        this.tyreSurfaceTempRR = tyreSurfaceTempRR;
    }

    public short getTyreSurfaceTempRL() {
        return tyreSurfaceTempRL;
    }

    public void setTyreSurfaceTempRL(short tyreSurfaceTempRL) {
        this.tyreSurfaceTempRL = tyreSurfaceTempRL;
    }

    public short getTyreInnerTempFR() {
        return tyreInnerTempFR;
    }

    public void setTyreInnerTempFR(short tyreInnerTempFR) {
        this.tyreInnerTempFR = tyreInnerTempFR;
    }

    public short getTyreInnerTempFL() {
        return tyreInnerTempFL;
    }

    public void setTyreInnerTempFL(short tyreInnerTempFL) {
        this.tyreInnerTempFL = tyreInnerTempFL;
    }

    public short getTyreInnerTempRR() {
        return tyreInnerTempRR;
    }

    public void setTyreInnerTempRR(short tyreInnerTempRR) {
        this.tyreInnerTempRR = tyreInnerTempRR;
    }

    public short getTyreInnerTempRL() {
        return tyreInnerTempRL;
    }

    public void setTyreInnerTempRL(short tyreInnerTempRL) {
        this.tyreInnerTempRL = tyreInnerTempRL;
    }

    public byte getErsDeployMode() {
        return ersDeployMode;
    }

    public void setErsDeployMode(byte ersDeployMode) {
        this.ersDeployMode = ersDeployMode;
    }

    public float getErsHarvestMGUK() {
        return ersHarvestMGUK;
    }

    public void setErsHarvestMGUK(float ersHarvestMGUK) {
        this.ersHarvestMGUK = ersHarvestMGUK;
    }

    public float getErsHarvestMGUH() {
        return ersHarvestMGUH;
    }

    public void setErsHarvestMGUH(float ersHarvestMGUH) {
        this.ersHarvestMGUH = ersHarvestMGUH;
    }

    public float getErsDeployed() {
        return ersDeployed;
    }

    public void setErsDeployed(float ersDeployed) {
        this.ersDeployed = ersDeployed;
    }

    public float getErsStoreEngergy() {
        return ersStoreEngergy;
    }

    public void setErsStoreEngergy(float ersStoreEngergy) {
        this.ersStoreEngergy = ersStoreEngergy;
    }
    
    
    
    public boolean isReadyToSave(){
        //boolean ret = true;
        if(this.driverName == null){
            return false;
        }
        return true;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        CommonTelemetryData ret = (CommonTelemetryData)super.clone(); //To change body of generated methods, choose Tools | Templates.
        ret.setPos((Vector3D)this.pos.clone());
        return ret;
    }
    
    
}

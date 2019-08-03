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
public class CommonTelemetryData{
    private short speed;
    private short rpm;
    private byte gear;
    private byte throttle;
    private byte brake;
    private float distance;//distance between current Position and Startline
    private float currentTime;//time since this round starts;
    private short carIndex;
    private String driverName;
    private short lapNum;
    private Vector3D pos;
    
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
    
    
}

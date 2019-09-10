/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfhund.jtelemetry.f1y19;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 *
 * @author PhilippHolzmann
 */
public class CarTelemetryData extends gfhund.jtelemetry.f1y18.CarTelemetryData {
    protected byte[] surfaceType = new byte[4];
    
    protected float fThrottle;
    protected float fSteer;
    protected float fBrake;
    
    public void setSurfaceType(int i,byte type){
        this.surfaceType[i] = type;
    }
    public byte[] getSurfaceType(){
        return this.surfaceType;
    }

    public float getFThrottle() {
        return fThrottle;
    }

    public void setFThrottle(float fSpeed) {
        this.fThrottle = fSpeed;
    }

    public float getFSteer() {
        return fSteer;
    }

    public void setFSteer(float fSteer) {
        this.fSteer = fSteer;
    }

    public float getFBrake() {
        return fBrake;
    }

    public void setFBrake(float fBrake) {
        this.fBrake = fBrake;
    }
    
    
    
    public static int getSize(){
        return 66;
    }
    
    public byte[] getBytes(){
        ByteBuffer telemetryDataBuffer = ByteBuffer.allocate(getSize());
        telemetryDataBuffer.order(ByteOrder.LITTLE_ENDIAN);
        telemetryDataBuffer.putShort(0,speed);
        telemetryDataBuffer.putFloat(2,fThrottle);
        telemetryDataBuffer.putFloat(6,fSteer);
        telemetryDataBuffer.putFloat(10,fBrake);
        telemetryDataBuffer.put(14,clutch);
        telemetryDataBuffer.put(15,gear);
        telemetryDataBuffer.putShort(16,this.engineRPM);
        telemetryDataBuffer.put(18,drs);
        telemetryDataBuffer.put(19,revLightsPercent);
        telemetryDataBuffer.putShort(20,brakesTemperature[0]);
        telemetryDataBuffer.putShort(22,brakesTemperature[1]);
        telemetryDataBuffer.putShort(24,brakesTemperature[2]);
        telemetryDataBuffer.putShort(26,brakesTemperature[3]);
        telemetryDataBuffer.putShort(28,tyresSurfaceTemperature[0]);
        telemetryDataBuffer.putShort(30,tyresSurfaceTemperature[1]);
        telemetryDataBuffer.putShort(32,tyresSurfaceTemperature[2]);
        telemetryDataBuffer.putShort(34,tyresSurfaceTemperature[3]);
        telemetryDataBuffer.putShort(36,tyresInnerTemperature[0]);
        telemetryDataBuffer.putShort(38,tyresInnerTemperature[1]);
        telemetryDataBuffer.putShort(40,tyresInnerTemperature[2]);
        telemetryDataBuffer.putShort(42,tyresInnerTemperature[3]);
        telemetryDataBuffer.putShort(44,engineTemperature);
        telemetryDataBuffer.putFloat(46,tyresPressure[0]);
        telemetryDataBuffer.putFloat(50,tyresPressure[1]);
        telemetryDataBuffer.putFloat(54,tyresPressure[2]);
        telemetryDataBuffer.putFloat(58,tyresPressure[3]);
        telemetryDataBuffer.put(62,surfaceType[0]);
        telemetryDataBuffer.put(63,surfaceType[1]);
        telemetryDataBuffer.put(64,surfaceType[2]);
        telemetryDataBuffer.put(65,surfaceType[3]);
        return telemetryDataBuffer.array();
    }
}

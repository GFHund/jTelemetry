package gfhund.jtelemetry.f1y18;

import gfhund.jtelemetry.commontelemetry.AbstractSubPackage;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class CarSetupData extends AbstractSubPackage{
    protected byte frontWing;//0
    protected byte rearWing;//1
    protected byte onThrottle;//2
    protected byte offThrottle;//3
    protected float frontCamber;//4
    protected float rearCamber;//8
    protected float frontToe;//12
    protected float rearToe;//16
    protected byte frontSuspension; //20
    protected byte rearSuspension;//21
    protected byte frontAntiRollBar;//22
    protected byte rearAntiRollBar;//2
    protected byte frontSuspensionHeight;
    protected byte rearSuspensionHeight;
    protected byte breakPressure;
    protected byte breakBias;
    protected float frontTyrePressure;
    protected float rearTyrePressure;
    protected byte ballast;
    protected float fuelLoad;

    public byte getFrontWing() {
        return frontWing;
    }

    public void setFrontWing(byte frontWing) {
        this.frontWing = frontWing;
    }

    public byte getRearWing() {
        return rearWing;
    }

    public void setRearWing(byte rearWing) {
        this.rearWing = rearWing;
    }

    public byte getOnThrottle() {
        return onThrottle;
    }

    public void setOnThrottle(byte onThrottle) {
        this.onThrottle = onThrottle;
    }

    public byte getOffThrottle() {
        return offThrottle;
    }

    public void setOffThrottle(byte offThrottle) {
        this.offThrottle = offThrottle;
    }

    public float getFrontCamber() {
        return frontCamber;
    }

    public void setFrontCamber(float frontCamber) {
        this.frontCamber = frontCamber;
    }

    public float getRearCamber() {
        return rearCamber;
    }

    public void setRearCamber(float rearCamber) {
        this.rearCamber = rearCamber;
    }

    public float getFrontToe() {
        return frontToe;
    }

    public void setFrontToe(float frontToe) {
        this.frontToe = frontToe;
    }

    public float getRearToe() {
        return rearToe;
    }

    public void setRearToe(float rearToe) {
        this.rearToe = rearToe;
    }

    public byte getFrontSuspension() {
        return frontSuspension;
    }

    public void setFrontSuspension(byte frontSuspension) {
        this.frontSuspension = frontSuspension;
    }

    public byte getRearSuspension() {
        return rearSuspension;
    }

    public void setRearSuspension(byte rearSuspension) {
        this.rearSuspension = rearSuspension;
    }

    public byte getFrontAntiRollBar() {
        return frontAntiRollBar;
    }

    public void setFrontAntiRollBar(byte frontAntiRollBar) {
        this.frontAntiRollBar = frontAntiRollBar;
    }

    public byte getRearAntiRollBar() {
        return rearAntiRollBar;
    }

    public void setRearAntiRollBar(byte rearAntiRollBar) {
        this.rearAntiRollBar = rearAntiRollBar;
    }

    public byte getFrontSuspensionHeight() {
        return frontSuspensionHeight;
    }

    public void setFrontSuspensionHeight(byte frontSuspensionHeight) {
        this.frontSuspensionHeight = frontSuspensionHeight;
    }

    public byte getRearSuspensionHeight() {
        return rearSuspensionHeight;
    }

    public void setRearSuspensionHeight(byte rearSuspensionHeight) {
        this.rearSuspensionHeight = rearSuspensionHeight;
    }

    public byte getBreakPressure() {
        return breakPressure;
    }

    public void setBreakPressure(byte breakPressure) {
        this.breakPressure = breakPressure;
    }

    public byte getBreakBias() {
        return breakBias;
    }

    public void setBreakBias(byte breakBias) {
        this.breakBias = breakBias;
    }

    public float getFrontTyrePressure() {
        return frontTyrePressure;
    }

    public void setFrontTyrePressure(float frontTyrePressure) {
        this.frontTyrePressure = frontTyrePressure;
    }

    public float getRearTyrePressure() {
        return rearTyrePressure;
    }

    public void setRearTyrePressure(float rearTyrePressure) {
        this.rearTyrePressure = rearTyrePressure;
    }

    public byte getBallast() {
        return ballast;
    }

    public void setBallast(byte ballast) {
        this.ballast = ballast;
    }

    public float getFuelLoad() {
        return fuelLoad;
    }

    public void setFuelLoad(float fuelLoad) {
        this.fuelLoad = fuelLoad;
    }
    
    public static int getSize(){
        return 41;
    }
    
    public byte[] getBytes(){
        ByteBuffer ret = ByteBuffer.allocate(getSize());
        ret.order(ByteOrder.LITTLE_ENDIAN);
        ret.put(frontWing);
        ret.put(rearWing);
        ret.put(onThrottle);
        ret.put(offThrottle);
        ret.putFloat(frontCamber);
        ret.putFloat(rearCamber);
        ret.putFloat(frontToe);
        ret.putFloat(rearToe);
        ret.put(frontSuspension);
        
        ret.put(rearSuspension);
        ret.put(frontAntiRollBar);
        ret.put(rearAntiRollBar);
        ret.put(frontSuspensionHeight);
        ret.put(rearSuspensionHeight);
        ret.put(breakPressure);
        ret.put(breakBias);
        ret.putFloat(frontTyrePressure);
        ret.putFloat(rearTyrePressure);
        ret.put(ballast);
        ret.putFloat(fuelLoad);
        
        return ret.array();
    }
}
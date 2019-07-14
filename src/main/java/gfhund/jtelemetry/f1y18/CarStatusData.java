package gfhund.jtelemetry.f1y18;

import gfhund.jtelemetry.commontelemetry.AbstractSubPackage;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class CarStatusData extends AbstractSubPackage{
    protected byte tractionControl;
    protected byte antiLockBrakes;
    protected byte fuelMix;
    protected byte frontBrakeBias;
    protected byte pitLimiterStatus;
    protected float fuelInTank;
    protected float fuelCapacity;
    protected short maxRPM;
    protected short idleRPM;
    protected byte maxGears;
    protected byte drsAllowed;
    /*
    0 - Rear Left
    1 - Rear Right
    2 Front Left
    4 Front Right
    */
    protected byte[] tyresWear = new byte[4];
    protected byte tyreCompound;// Modern - 0 = hyper soft, 1 = ultra soft
    // 2 = super soft, 3 = soft, 4 = medium, 5 = hard
    // 6 = super hard, 7 = inter, 8 = wet
    // Classic - 0-6 = dry, 7-8 = wet
    protected byte[] tyresDamage = new byte[4];
    protected byte frontLeftWingDamage;
    protected byte frontRightWingDamage;
    protected byte rearWingDamage;
    protected byte engineDamage;
    protected byte gearBoxDamage;
    protected byte exhaustDamage;
    protected byte vehicleFiaFlags;// -1 = invalid/unknown, 0 = none, 1 = green
    // 2 = blue, 3 = yellow, 4 = red
    protected float ersStoreEnergy;
    protected byte ersDeployMode;
    protected float ersHarvestedThisLapMGUK;
    protected float ersHarvestedThisLapMGUH;
    protected float ersDeployedThisLap;
    
    public static int getSize(){
        return 52;
    }

    public byte getTractionControl() {
        return tractionControl;
    }

    public void setTractionControl(byte m_tractionControl) {
        this.tractionControl = m_tractionControl;
    }

    public byte getAntiLockBrakes() {
        return antiLockBrakes;
    }

    public void setAntiLockBrakes(byte m_antiLockBrakes) {
        this.antiLockBrakes = m_antiLockBrakes;
    }

    public byte getFuelMix() {
        return fuelMix;
    }

    public void setFuelMix(byte m_fuelMix) {
        this.fuelMix = m_fuelMix;
    }

    public byte getFrontBrakeBias() {
        return frontBrakeBias;
    }

    public void setFrontBrakeBias(byte m_frontBrakeBias) {
        this.frontBrakeBias = m_frontBrakeBias;
    }

    public byte getPitLimiterStatus() {
        return pitLimiterStatus;
    }

    public void setPitLimiterStatus(byte m_pitLimiterStatus) {
        this.pitLimiterStatus = m_pitLimiterStatus;
    }

    public float getFuelInTank() {
        return fuelInTank;
    }

    public void setFuelInTank(float m_fuelInTank) {
        this.fuelInTank = m_fuelInTank;
    }

    public float getFuelCapacity() {
        return fuelCapacity;
    }

    public void setFuelCapacity(float m_fuelCapacity) {
        this.fuelCapacity = m_fuelCapacity;
    }

    public short getMaxRPM() {
        return maxRPM;
    }

    public void setMaxRPM(short m_maxRPM) {
        this.maxRPM = m_maxRPM;
    }

    public short getIdleRPM() {
        return idleRPM;
    }

    public void setIdleRPM(short m_idleRPM) {
        this.idleRPM = m_idleRPM;
    }

    public byte getMaxGears() {
        return maxGears;
    }

    public void setMaxGears(byte m_maxGears) {
        this.maxGears = m_maxGears;
    }

    public byte getDrsAllowed() {
        return drsAllowed;
    }

    public void setDrsAllowed(byte m_drsAllowed) {
        this.drsAllowed = m_drsAllowed;
    }

    public byte[] getTyresWear() {
        return tyresWear;
    }

    public void setTyresWear(byte[] m_tyresWear) {
        this.tyresWear = m_tyresWear;
    }
    /*
    0 - Rear Left
    1 - Rear Right
    2 Front Left
    4 Front Right
    */
    public byte getTyresWearRL(){
        return this.tyresWear[0];
    }
    public byte getTyresWearRR(){
        return this.tyresWear[1];
    }
    public byte getTyresWearFL(){
        return this.tyresWear[2];
    }
    public byte getTyresWearFR(){
        return this.tyresWear[3];
    }
    
    public void setTyresWear(int i, byte tyreWear){
        this.tyresWear[i] = tyreWear;
    }
    
    public void setTyresWearRL(byte tyreWear){
        this.tyresWear[0] = tyreWear;
    }
    public void setTyresWearRR(byte tyreWear){
        this.tyresWear[1] = tyreWear;
    }
    public void setTyresWearFL(byte tyreWear){
        this.tyresWear[2] = tyreWear;
    }
    public void setTyresWearFR(byte tyreWear){
        this.tyresWear[3] = tyreWear;
    }

    public byte getTyreCompound() {
        return tyreCompound;
    }

    public void setTyreCompound(byte m_tyreCompound) {
        this.tyreCompound = m_tyreCompound;
    }

    public byte[] getTyresDamage() {
        return tyresDamage;
    }

    public void setTyresDamage(byte[] m_tyresDamage) {
        this.tyresDamage = m_tyresDamage;
    }

    public void setTyresDamage(int i,byte tyresDamage){
        this.tyresDamage = this.tyresDamage;
    }
    
    public byte getFrontLeftWingDamage() {
        return frontLeftWingDamage;
    }

    public void setFrontLeftWingDamage(byte m_frontLeftWingDamage) {
        this.frontLeftWingDamage = m_frontLeftWingDamage;
    }

    public byte getFrontRightWingDamage() {
        return frontRightWingDamage;
    }

    public void setFrontRightWingDamage(byte m_frontRightWingDamage) {
        this.frontRightWingDamage = m_frontRightWingDamage;
    }

    public byte getRearWingDamage() {
        return rearWingDamage;
    }

    public void setRearWingDamage(byte m_rearWingDamage) {
        this.rearWingDamage = m_rearWingDamage;
    }

    public byte getEngineDamage() {
        return engineDamage;
    }

    public void setEngineDamage(byte m_engineDamage) {
        this.engineDamage = m_engineDamage;
    }

    public byte getGearBoxDamage() {
        return gearBoxDamage;
    }

    public void setGearBoxDamage(byte m_gearBoxDamage) {
        this.gearBoxDamage = m_gearBoxDamage;
    }

    public byte getExhaustDamage() {
        return exhaustDamage;
    }

    public void setExhaustDamage(byte m_exhaustDamage) {
        this.exhaustDamage = m_exhaustDamage;
    }

    public byte getVehicleFiaFlags() {
        return vehicleFiaFlags;
    }

    public void setVehicleFiaFlags(byte m_vehicleFiaFlags) {
        this.vehicleFiaFlags = m_vehicleFiaFlags;
    }

    public float getErsStoreEnergy() {
        return ersStoreEnergy;
    }

    public void setErsStoreEnergy(float m_ersStoreEnergy) {
        this.ersStoreEnergy = m_ersStoreEnergy;
    }

    public byte getErsDeployMode() {
        return ersDeployMode;
    }

    public void setErsDeployMode(byte m_ersDeployMode) {
        this.ersDeployMode = m_ersDeployMode;
    }

    public float getErsHarvestedThisLapMGUK() {
        return ersHarvestedThisLapMGUK;
    }

    public void setErsHarvestedThisLapMGUK(float m_ersHarvestedThisLapMGUK) {
        this.ersHarvestedThisLapMGUK = m_ersHarvestedThisLapMGUK;
    }

    public float getErsHarvestedThisLapMGUH() {
        return ersHarvestedThisLapMGUH;
    }

    public void setErsHarvestedThisLapMGUH(float m_ersHarvestedThisLapMGUH) {
        this.ersHarvestedThisLapMGUH = m_ersHarvestedThisLapMGUH;
    }

    public float getErsDeployedThisLap() {
        return ersDeployedThisLap;
    }

    public void setErsDeployedThisLap(float m_ersDeployedThisLap) {
        this.ersDeployedThisLap = m_ersDeployedThisLap;
    }
    
    public byte[] getBytes(){
        ByteBuffer ret = ByteBuffer.allocate(CarStatusData.getSize());
        ret.order(ByteOrder.LITTLE_ENDIAN);
        ret.put(tractionControl);
        ret.put(antiLockBrakes);
        ret.put(fuelMix);
        ret.put(frontBrakeBias);
        ret.put(pitLimiterStatus);
        ret.putFloat(fuelInTank);
        ret.putFloat(fuelCapacity);
        ret.putShort(maxRPM);
        ret.putShort(idleRPM);
        
        ret.put(maxGears);
        ret.put(drsAllowed);
        ret.put(tyresWear[0]);
        ret.put(tyresWear[1]);
        ret.put(tyresWear[2]);
        ret.put(tyresWear[3]);
        ret.put(tyreCompound);
        ret.put(tyresDamage[0]);
        ret.put(tyresDamage[1]);
        ret.put(tyresDamage[2]);
        ret.put(tyresDamage[3]);
        ret.put(frontLeftWingDamage);
        ret.put(frontRightWingDamage);
        ret.put(rearWingDamage);
        ret.put(gearBoxDamage);
        ret.put(exhaustDamage);
        ret.put(vehicleFiaFlags);
        ret.putFloat(ersStoreEnergy);
        ret.put(ersDeployMode);
        ret.putFloat(ersHarvestedThisLapMGUK);
        ret.putFloat(ersHarvestedThisLapMGUH);
        ret.putFloat(ersDeployedThisLap);
        return ret.array();
    }
}
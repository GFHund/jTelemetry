package gfhund.jtelemetry.f1y18;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class CarStatusData{
    protected byte m_tractionControl;
    protected byte m_antiLockBrakes;
    protected byte m_fuelMix;
    protected byte m_frontBrakeBias;
    protected byte m_pitLimiterStatus;
    protected float m_fuelInTank;
    protected float m_fuelCapacity;
    protected short m_maxRPM;
    protected short m_idleRPM;
    protected byte m_maxGears;
    protected byte m_drsAllowed;
    /*
    0 - Rear Left
    1 - Rear Right
    2 Front Left
    4 Front Right
    */
    protected byte[] m_tyresWear = new byte[4];
    protected byte m_tyreCompound;// Modern - 0 = hyper soft, 1 = ultra soft
    // 2 = super soft, 3 = soft, 4 = medium, 5 = hard
    // 6 = super hard, 7 = inter, 8 = wet
    // Classic - 0-6 = dry, 7-8 = wet
    protected byte[] m_tyresDamage = new byte[4];
    protected byte m_frontLeftWingDamage;
    protected byte m_frontRightWingDamage;
    protected byte m_rearWingDamage;
    protected byte m_engineDamage;
    protected byte m_gearBoxDamage;
    protected byte m_exhaustDamage;
    protected byte m_vehicleFiaFlags;// -1 = invalid/unknown, 0 = none, 1 = green
    // 2 = blue, 3 = yellow, 4 = red
    protected float m_ersStoreEnergy;
    protected byte m_ersDeployMode;
    protected float m_ersHarvestedThisLapMGUK;
    protected float m_ersHarvestedThisLapMGUH;
    protected float m_ersDeployedThisLap;
    
    public static int getSize(){
        return 52;
    }

    public byte getTractionControl() {
        return m_tractionControl;
    }

    public void setTractionControl(byte m_tractionControl) {
        this.m_tractionControl = m_tractionControl;
    }

    public byte getAntiLockBrakes() {
        return m_antiLockBrakes;
    }

    public void setAntiLockBrakes(byte m_antiLockBrakes) {
        this.m_antiLockBrakes = m_antiLockBrakes;
    }

    public byte getFuelMix() {
        return m_fuelMix;
    }

    public void setFuelMix(byte m_fuelMix) {
        this.m_fuelMix = m_fuelMix;
    }

    public byte getFrontBrakeBias() {
        return m_frontBrakeBias;
    }

    public void setFrontBrakeBias(byte m_frontBrakeBias) {
        this.m_frontBrakeBias = m_frontBrakeBias;
    }

    public byte getPitLimiterStatus() {
        return m_pitLimiterStatus;
    }

    public void setPitLimiterStatus(byte m_pitLimiterStatus) {
        this.m_pitLimiterStatus = m_pitLimiterStatus;
    }

    public float getFuelInTank() {
        return m_fuelInTank;
    }

    public void setFuelInTank(float m_fuelInTank) {
        this.m_fuelInTank = m_fuelInTank;
    }

    public float getFuelCapacity() {
        return m_fuelCapacity;
    }

    public void setFuelCapacity(float m_fuelCapacity) {
        this.m_fuelCapacity = m_fuelCapacity;
    }

    public short getMaxRPM() {
        return m_maxRPM;
    }

    public void setMaxRPM(short m_maxRPM) {
        this.m_maxRPM = m_maxRPM;
    }

    public short getIdleRPM() {
        return m_idleRPM;
    }

    public void setIdleRPM(short m_idleRPM) {
        this.m_idleRPM = m_idleRPM;
    }

    public byte getMaxGears() {
        return m_maxGears;
    }

    public void setMaxGears(byte m_maxGears) {
        this.m_maxGears = m_maxGears;
    }

    public byte getDrsAllowed() {
        return m_drsAllowed;
    }

    public void setDrsAllowed(byte m_drsAllowed) {
        this.m_drsAllowed = m_drsAllowed;
    }

    public byte[] getTyresWear() {
        return m_tyresWear;
    }

    public void setTyresWear(byte[] m_tyresWear) {
        this.m_tyresWear = m_tyresWear;
    }
    /*
    0 - Rear Left
    1 - Rear Right
    2 Front Left
    4 Front Right
    */
    public byte getTyresWearRL(){
        return this.m_tyresWear[0];
    }
    public byte getTyresWearRR(){
        return this.m_tyresWear[1];
    }
    public byte getTyresWearFL(){
        return this.m_tyresWear[2];
    }
    public byte getTyresWearFR(){
        return this.m_tyresWear[3];
    }
    
    public void setTyresWearRL(byte tyreWear){
        this.m_tyresWear[0] = tyreWear;
    }
    public void setTyresWearRR(byte tyreWear){
        this.m_tyresWear[1] = tyreWear;
    }
    public void setTyresWearFL(byte tyreWear){
        this.m_tyresWear[2] = tyreWear;
    }
    public void setTyresWearFR(byte tyreWear){
        this.m_tyresWear[3] = tyreWear;
    }

    public byte getTyreCompound() {
        return m_tyreCompound;
    }

    public void setTyreCompound(byte m_tyreCompound) {
        this.m_tyreCompound = m_tyreCompound;
    }

    public byte[] getTyresDamage() {
        return m_tyresDamage;
    }

    public void setTyresDamage(byte[] m_tyresDamage) {
        this.m_tyresDamage = m_tyresDamage;
    }

    public byte getFrontLeftWingDamage() {
        return m_frontLeftWingDamage;
    }

    public void setFrontLeftWingDamage(byte m_frontLeftWingDamage) {
        this.m_frontLeftWingDamage = m_frontLeftWingDamage;
    }

    public byte getFrontRightWingDamage() {
        return m_frontRightWingDamage;
    }

    public void setFrontRightWingDamage(byte m_frontRightWingDamage) {
        this.m_frontRightWingDamage = m_frontRightWingDamage;
    }

    public byte getRearWingDamage() {
        return m_rearWingDamage;
    }

    public void setRearWingDamage(byte m_rearWingDamage) {
        this.m_rearWingDamage = m_rearWingDamage;
    }

    public byte getEngineDamage() {
        return m_engineDamage;
    }

    public void setEngineDamage(byte m_engineDamage) {
        this.m_engineDamage = m_engineDamage;
    }

    public byte getGearBoxDamage() {
        return m_gearBoxDamage;
    }

    public void setGearBoxDamage(byte m_gearBoxDamage) {
        this.m_gearBoxDamage = m_gearBoxDamage;
    }

    public byte getExhaustDamage() {
        return m_exhaustDamage;
    }

    public void setExhaustDamage(byte m_exhaustDamage) {
        this.m_exhaustDamage = m_exhaustDamage;
    }

    public byte getVehicleFiaFlags() {
        return m_vehicleFiaFlags;
    }

    public void setVehicleFiaFlags(byte m_vehicleFiaFlags) {
        this.m_vehicleFiaFlags = m_vehicleFiaFlags;
    }

    public float getErsStoreEnergy() {
        return m_ersStoreEnergy;
    }

    public void setErsStoreEnergy(float m_ersStoreEnergy) {
        this.m_ersStoreEnergy = m_ersStoreEnergy;
    }

    public byte getErsDeployMode() {
        return m_ersDeployMode;
    }

    public void setErsDeployMode(byte m_ersDeployMode) {
        this.m_ersDeployMode = m_ersDeployMode;
    }

    public float getErsHarvestedThisLapMGUK() {
        return m_ersHarvestedThisLapMGUK;
    }

    public void setErsHarvestedThisLapMGUK(float m_ersHarvestedThisLapMGUK) {
        this.m_ersHarvestedThisLapMGUK = m_ersHarvestedThisLapMGUK;
    }

    public float getErsHarvestedThisLapMGUH() {
        return m_ersHarvestedThisLapMGUH;
    }

    public void setErsHarvestedThisLapMGUH(float m_ersHarvestedThisLapMGUH) {
        this.m_ersHarvestedThisLapMGUH = m_ersHarvestedThisLapMGUH;
    }

    public float getErsDeployedThisLap() {
        return m_ersDeployedThisLap;
    }

    public void setErsDeployedThisLap(float m_ersDeployedThisLap) {
        this.m_ersDeployedThisLap = m_ersDeployedThisLap;
    }
    
    public byte[] getBytes(){
        ByteBuffer ret = ByteBuffer.allocate(CarStatusData.getSize());
        ret.order(ByteOrder.LITTLE_ENDIAN);
        ret.put(m_tractionControl);
        ret.put(m_antiLockBrakes);
        ret.put(m_fuelMix);
        ret.put(m_frontBrakeBias);
        ret.put(m_pitLimiterStatus);
        ret.putFloat(m_fuelInTank);
        ret.putFloat(m_fuelCapacity);
        ret.putShort(m_maxRPM);
        ret.putShort(m_idleRPM);
        
        ret.put(m_maxGears);
        ret.put(m_drsAllowed);
        ret.put(m_tyresWear[0]);
        ret.put(m_tyresWear[1]);
        ret.put(m_tyresWear[2]);
        ret.put(m_tyresWear[3]);
        ret.put(m_tyreCompound);
        ret.put(m_tyresDamage[0]);
        ret.put(m_tyresDamage[1]);
        ret.put(m_tyresDamage[2]);
        ret.put(m_tyresDamage[3]);
        ret.put(m_frontLeftWingDamage);
        ret.put(m_frontRightWingDamage);
        ret.put(m_rearWingDamage);
        ret.put(m_gearBoxDamage);
        ret.put(m_exhaustDamage);
        ret.put(m_vehicleFiaFlags);
        ret.putFloat(m_ersStoreEnergy);
        ret.put(m_ersDeployMode);
        ret.putFloat(m_ersHarvestedThisLapMGUK);
        ret.putFloat(m_ersHarvestedThisLapMGUH);
        ret.putFloat(m_ersDeployedThisLap);
        return ret.array();
    }
}
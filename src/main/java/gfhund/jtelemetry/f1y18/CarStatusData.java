package gfhund.jtelemetry.f1y18;

public class CarStatusData{
    private byte m_tractionControl;
    private byte m_antiLockBrakes;
    private byte m_fuelMix;
    private byte m_frontBrakeBias;
    private byte m_pitLimiterStatus;
    private float m_fuelInTank;
    private float m_fuelCapacity;
    private short m_maxRPM;
    private short m_idleRPM;
    private byte m_maxGears;
    private byte m_drsAllowed;
    /*
    0 - Rear Left
    1 - Rear Right
    2 Front Left
    4 Front Right
    */
    private byte[] m_tyresWear = new byte[4];
    private byte m_tyreCompound;// Modern - 0 = hyper soft, 1 = ultra soft
    // 2 = super soft, 3 = soft, 4 = medium, 5 = hard
    // 6 = super hard, 7 = inter, 8 = wet
    // Classic - 0-6 = dry, 7-8 = wet
    private byte[] m_tyresDamage = new byte[4];
    private byte m_frontLeftWingDamage;
    private byte m_frontRightWingDamage;
    private byte m_rearWingDamage;
    private byte m_engineDamage;
    private byte m_gearBoxDamage;
    private byte m_exhaustDamage;
    private byte m_vehicleFiaFlags;// -1 = invalid/unknown, 0 = none, 1 = green
    // 2 = blue, 3 = yellow, 4 = red
    private float m_ersStoreEnergy;
    private byte m_ersDeployMode;
    private float m_ersHarvestedThisLapMGUK;
    private float m_ersHarvestedThisLapMGUH;
    private float m_ersDeployedThisLap;

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
    
    
}
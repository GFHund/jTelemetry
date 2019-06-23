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
public class CarStatusData extends gfhund.jtelemetry.f1y18.CarStatusData {
    //Note Exhaust Damage would be removed in f1 2019
    protected float m_fuelRemainingLaps;
    
    protected byte m_tyreVisualCompound;
    
    public float getFuelRemainingLaps(){
        return this.m_fuelRemainingLaps;
    }
    
    public void setFuelRaminingLaps(float remainingLaps){
        this.m_fuelRemainingLaps = remainingLaps;
    }
    
    public byte getTyreVisualCompound(){
        return this.m_tyreVisualCompound;
    }
    public void setTyreVisualCompound(byte visualCompound){
        this.m_tyreVisualCompound = visualCompound;
    }
    
    public static int getSize(){
        return 56;
    }
    
    public byte[] getBytes(){
        ByteBuffer ret = ByteBuffer.allocate(gfhund.jtelemetry.f1y18.CarStatusData.getSize());
        ret.order(ByteOrder.LITTLE_ENDIAN);
        ret.put(m_tractionControl);
        ret.put(m_antiLockBrakes);
        ret.put(m_fuelMix);
        ret.put(m_frontBrakeBias);
        ret.put(m_pitLimiterStatus);
        ret.putFloat(m_fuelInTank);
        ret.putFloat(m_fuelCapacity);
        ret.putFloat(m_fuelRemainingLaps);
        ret.putShort(m_maxRPM);
        ret.putShort(m_idleRPM);
        
        ret.put(m_maxGears);
        ret.put(m_drsAllowed);
        ret.put(m_tyresWear[0]);
        ret.put(m_tyresWear[1]);
        ret.put(m_tyresWear[2]);
        ret.put(m_tyresWear[3]);
        ret.put(m_tyreCompound);
        ret.put(m_tyreVisualCompound);
        ret.put(m_tyresDamage[0]);
        ret.put(m_tyresDamage[1]);
        ret.put(m_tyresDamage[2]);
        ret.put(m_tyresDamage[3]);
        ret.put(m_frontLeftWingDamage);
        ret.put(m_frontRightWingDamage);
        ret.put(m_rearWingDamage);
        ret.put(m_gearBoxDamage);
        ret.put(m_vehicleFiaFlags);
        ret.putFloat(m_ersStoreEnergy);
        ret.put(m_ersDeployMode);
        ret.putFloat(m_ersHarvestedThisLapMGUK);
        ret.putFloat(m_ersHarvestedThisLapMGUH);
        ret.putFloat(m_ersDeployedThisLap);
        return ret.array();
    }
}

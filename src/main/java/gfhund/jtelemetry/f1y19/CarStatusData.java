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
    protected float fuelRemainingLaps;
    
    protected byte tyreVisualCompound;
    
    public float getFuelRemainingLaps(){
        return this.fuelRemainingLaps;
    }
    
    public void setFuelRaminingLaps(float remainingLaps){
        this.fuelRemainingLaps = remainingLaps;
    }
    
    public byte getTyreVisualCompound(){
        return this.tyreVisualCompound;
    }
    public void setTyreVisualCompound(byte visualCompound){
        this.tyreVisualCompound = visualCompound;
    }
    
    public static int getSize(){
        return 56;
    }
    
    public byte[] getBytes(){
        ByteBuffer ret = ByteBuffer.allocate(gfhund.jtelemetry.f1y18.CarStatusData.getSize());
        ret.order(ByteOrder.LITTLE_ENDIAN);
        ret.put(tractionControl);
        ret.put(antiLockBrakes);
        ret.put(fuelMix);
        ret.put(frontBrakeBias);
        ret.put(pitLimiterStatus);
        ret.putFloat(fuelInTank);
        ret.putFloat(fuelCapacity);
        ret.putFloat(fuelRemainingLaps);
        ret.putShort(maxRPM);
        ret.putShort(idleRPM);
        
        ret.put(maxGears);
        ret.put(drsAllowed);
        ret.put(tyresWear[0]);
        ret.put(tyresWear[1]);
        ret.put(tyresWear[2]);
        ret.put(tyresWear[3]);
        ret.put(tyreCompound);
        ret.put(tyreVisualCompound);
        ret.put(tyresDamage[0]);
        ret.put(tyresDamage[1]);
        ret.put(tyresDamage[2]);
        ret.put(tyresDamage[3]);
        ret.put(frontLeftWingDamage);
        ret.put(frontRightWingDamage);
        ret.put(rearWingDamage);
        ret.put(gearBoxDamage);
        ret.put(vehicleFiaFlags);
        ret.putFloat(ersStoreEnergy);
        ret.put(ersDeployMode);
        ret.putFloat(ersHarvestedThisLapMGUK);
        ret.putFloat(ersHarvestedThisLapMGUH);
        ret.putFloat(ersDeployedThisLap);
        return ret.array();
    }
}

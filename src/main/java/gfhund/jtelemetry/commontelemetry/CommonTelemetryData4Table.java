/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfhund.jtelemetry.commontelemetry;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
/**
 *
 * @author PhilippGL
 */
public class CommonTelemetryData4Table {
    private final SimpleIntegerProperty speed = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty rpm = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty gear = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty throttle = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty brake = new SimpleIntegerProperty(0);
    private final SimpleFloatProperty distance = new SimpleFloatProperty(0.0f);
    private final SimpleFloatProperty currentTime = new SimpleFloatProperty(0.0f);
    private final SimpleStringProperty driverName = new SimpleStringProperty("");
    private final SimpleIntegerProperty lapNum = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty ersDeployMode = new SimpleIntegerProperty(0);
    private final SimpleFloatProperty ersHarvestMGUK = new SimpleFloatProperty(0.0f);
    private final SimpleFloatProperty ersHarvestMGUH = new SimpleFloatProperty(0.0f);
    private final SimpleFloatProperty ersDeployed = new SimpleFloatProperty(0.0f);
    private final SimpleFloatProperty ersStoreEnergy = new SimpleFloatProperty(0.0f);
    
            /*
            private byte gear = 0;
    private byte throttle = 0;
    private byte brake = 0;
    private float distance = 0;//distance between current Position and Startline
    private float currentTime = 0;//time since this round starts;
    private String driverName = "";
    private short lapNum = 0;
    
    private byte ersDeployMode;
    private float ersHarvestMGUK;//Energie aus vom Bremsen wiedergewinnen
    private float ersHarvestMGUH;//Energie aus den Abgasen wiedergewinnen
    private float ersDeployed;
    private float ersStoreEngergy;//Gesamt Batterie stand
            */
    
    public CommonTelemetryData4Table(CommonTelemetryData telemetry){
        speed.set(telemetry.getSpeed());
        rpm.set(telemetry.getRpm());
        gear.set(telemetry.getGear());
        throttle.set(telemetry.getThrottle());
        brake.set(telemetry.getBrake());
        distance.set(telemetry.getDistance());
        currentTime.set(telemetry.getCurrentTime());
        driverName.set(telemetry.getDriverName());
        lapNum.set(telemetry.getLapNum());
        ersDeployMode.set(telemetry.getErsDeployMode());
        ersHarvestMGUH.set(telemetry.getErsHarvestMGUH());
        ersHarvestMGUK.set(telemetry.getErsHarvestMGUK());
        ersDeployed.set(telemetry.getErsDeployed());
        ersStoreEnergy.set(telemetry.getErsStoreEngergy());
    }
    
    public int getSpeed(){return speed.get();}
    public void setSpeed(int speed){this.speed.set(speed);}
    
    public int getRpm(){return this.rpm.get();}
    public void setRpm(int rpm){this.rpm.set(rpm);}
    
    public int getGear(){return this.gear.get();}
    public void setGear(int gear){this.gear.set(gear);}
    
    public int getThrottle(){return this.throttle.get();}
    public void setThottle(int throttle){this.throttle.set(throttle);}
    
    public int getBrake(){return this.brake.get();}
    public void setBrake(int brake){this.brake.set(brake);}
    
    public float getDistance(){return this.distance.get();}
    public void setDistance(float distance){this.distance.set(distance);}
    
    public float getCurrentTime(){return this.currentTime.get();}
    public void setCurrentTime(float currentTime){this.currentTime.set(currentTime);}
    
    public String getDriverName(){return this.driverName.get();}
    public void setDriverName(String driverName){this.driverName.set(driverName);}
    
    public int getLapNum(){return this.lapNum.get();}
    public void setLapNum(int lapNum){this.lapNum.set(lapNum);}
    
    public int getErsMode(){return this.ersDeployMode.get();}
    public void setErsMode(int ersMode){this.ersDeployMode.set(ersMode);}
    
    public float getErsHarvestMGUH(){return this.ersHarvestMGUH.get();}
    public void setErsHarvestMGUH(float harvestMGUH){this.ersHarvestMGUH.set(harvestMGUH);}
    
    public float getErsHarvestMGUK(){return this.ersHarvestMGUK.get();}
    public void setErsHarvestMGUK(float harvestMGUK){this.ersHarvestMGUK.set(harvestMGUK);}
    
    public float getErsDeployed(){return this.ersDeployed.get();}
    public void setErsDeployed(float deployed){this.ersDeployed.set(deployed);}
    
    public float getErsStoreEnergy(){return this.ersStoreEnergy.get();}
    public void setErsStoreEnergy(float storeEnergy){this.ersStoreEnergy.set(storeEnergy);}
}

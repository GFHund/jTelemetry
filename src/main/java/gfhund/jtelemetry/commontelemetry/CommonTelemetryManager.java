/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfhund.jtelemetry.commontelemetry;

import gfhund.jtelemetry.ClassManager;
import gfhund.jtelemetry.Vector3D;
import gfhund.jtelemetry.fxgui.TelemetryWriter;
import gfhund.jtelemetry.fxgui.TelemetryReader;
import gfhund.jtelemetry.stfFormat.StfDocument;
import gfhund.jtelemetry.stfFormat.StfClass;
import gfhund.jtelemetry.stfFormat.AbstractStfObject;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author PhilippGL
 */
public class CommonTelemetryManager {
    LapIdentificationObject id;
    ArrayList<CommonTelemetryData> lapData;

    public CommonTelemetryManager(LapIdentificationObject id) {
        this.id = id;
        lapData = new ArrayList<>();
    }
    
    public void addLapData(CommonTelemetryData data){
        try{
            TelemetryWriter tw = (TelemetryWriter)gfhund.jtelemetry.ClassManager.get(TelemetryWriter.class);
            tw.processCommonTelemetryData(data);
        }catch(ClassManager.ClassManagerException e){
            
        }
    }
    
    public CommonTelemetryData getDataFromDistance(float distance){
        ArrayList<CommonTelemetryData> telemetryData = getLapData();
        CommonTelemetryData ret = null;
        float deltaDistance = Float.MAX_VALUE;
        for(CommonTelemetryData value: telemetryData){
            float currDeltaDistance = Math.abs(distance-value.getDistance());
            if(currDeltaDistance < deltaDistance){
                deltaDistance = currDeltaDistance;
                ret = value;
            }
        }
        return ret;
    }
    
    public ArrayList<CommonTelemetryData> getLapData(){
        if(lapData.size() <= 0){
            try{
                TelemetryReader tr = (TelemetryReader)gfhund.jtelemetry.ClassManager.get(TelemetryReader.class);
                int iLapNum = this.id.getLapNum();
                String sLapNum = ""+iLapNum;
                if(iLapNum < 10){
                    sLapNum = "0"+sLapNum;
                }
                File zipFile = new File(this.id.getZipFile());
                StfDocument doc = tr.read(zipFile, this.id.getPlayer()+"/"+this.id.getFilenameDate()+"_"+sLapNum+".stf");
                AbstractStfObject obj = doc.getChild(0);
                StfClass rootClass = (obj instanceof StfClass)?(StfClass)obj:null;
                if(rootClass != null){
                    AbstractStfObject[] children = rootClass.getChildren();
                    for(AbstractStfObject child: children){
                        if(child instanceof StfClass){
                            StfClass telemetryObj = (StfClass)child;
                            CommonTelemetryData telemetryData = convertStfClassToTelemetryData(telemetryObj);
                            if(telemetryData != null){
                                lapData.add(telemetryData);
                            }
                        }
                    }
                }
            } catch(ClassManager.ClassManagerException e){
                
            }
        }
        return (ArrayList<CommonTelemetryData>)this.lapData.clone();
    }
    
    private CommonTelemetryData convertStfClassToTelemetryData(StfClass classObj){
        CommonTelemetryData data = new CommonTelemetryData();
        String sBreak = classObj.getChildPropertyValue("Brake");
        String sName = classObj.getChildPropertyValue("DriverName");
        String sCarIndex = classObj.getChildPropertyValue("CarIndex");
        String sCurrentTime = classObj.getChildPropertyValue("CurrentTime");
        String sDistance = classObj.getChildPropertyValue("Distance");
        String sGear = classObj.getChildPropertyValue("Gear");
        String sLapNum = classObj.getChildPropertyValue("LapNum");
        String sPosX = classObj.getChildPropertyValue("PosX");
        String sPosY = classObj.getChildPropertyValue("PosY");
        String sPosZ = classObj.getChildPropertyValue("PosZ");
        String sRpm = classObj.getChildPropertyValue("Rpm");
        String sSpeed = classObj.getChildPropertyValue("Speed");
        String sThrottle = classObj.getChildPropertyValue("Throttle");
        String sErsDeployMode = classObj.getChildPropertyValue("ErsDeployMode");
        String sErsDeployed = classObj.getChildPropertyValue("ErsDeployed");
        String sErsMGUH = classObj.getChildPropertyValue("ErsMGUH");
        String sErsMGUK = classObj.getChildPropertyValue("ErsMGUK");
        String sErsStoreEngergy = classObj.getChildPropertyValue("ErsStoreEnergy");
        String sTyreInnerTempFL = classObj.getChildPropertyValue("TyreInnerTempFL");
        String sTyreInnerTempFR = classObj.getChildPropertyValue("TyreInnerTempFR");
        String sTyreInnerTempRR = classObj.getChildPropertyValue("TyreInnerTempRR");
        String sTyreInnerTempRL = classObj.getChildPropertyValue("TyreInnerTempRL");
        String sTyreSurfaceTempFL = classObj.getChildPropertyValue("TyreSurfaceTempFL");
        String sTyreSurfaceTempFR = classObj.getChildPropertyValue("TyreSurfaceTempFR");
        String sTyreSurfaceTempRR = classObj.getChildPropertyValue("TyreSurfaceTempRR");
        String sTyreSurfaceTempRL = classObj.getChildPropertyValue("TyreSurfaceTempRL");

        try{
            byte bBreak = Byte.parseByte(sBreak);
            data.setBrake(bBreak);
            data.setDriverName(sName);
            short carIndex = Short.parseShort(sCarIndex);
            data.setCarIndex(carIndex);
            float fCurrentTime = Float.parseFloat(sCurrentTime);
            data.setCurrentTime(fCurrentTime);
            float fDistance = Float.parseFloat(sDistance);
            data.setDistance(fDistance);
            byte bGear = Byte.parseByte(sGear);
            data.setGear(bGear);
            short lapNum = Short.parseShort(sLapNum);
            data.setLapNum(lapNum);
            float fPosX = Float.parseFloat(sPosX);
            float fPosY = Float.parseFloat(sPosY);
            float fPosZ = Float.parseFloat(sPosZ);
            Vector3D vecPos = new Vector3D(fPosX, fPosY, fPosZ);
            data.setPos(vecPos);
            short rpm = Short.parseShort(sRpm);
            data.setRpm(rpm);
            short speed = Short.parseShort(sSpeed);
            data.setSpeed(speed);
            byte bThrottle = Byte.parseByte(sThrottle);
            data.setThrottle(bThrottle);
            
            byte bErsDeployMode = Byte.parseByte(sErsDeployMode);
            float fErsMGUH = Float.parseFloat(sErsMGUH);
            float fErsMGUK = Float.parseFloat(sErsMGUK);
            float fErsStoreEnergy = Float.parseFloat(sErsStoreEngergy);
            float fErsDeployed = Float.parseFloat(sErsDeployed);
            short shTyreInnerTempFL = Short.parseShort(sTyreInnerTempFL);
            short shTyreInnerTempFR = Short.parseShort(sTyreInnerTempFR);
            short shTyreInnerTempRR = Short.parseShort(sTyreInnerTempRR);
            short shTyreInnerTempRL = Short.parseShort(sTyreInnerTempRL);
            short shTyreSurfaceTempFL = Short.parseShort(sTyreSurfaceTempFL);
            short shTyreSurfaceTempFR = Short.parseShort(sTyreSurfaceTempFR);
            short shTyreSurfaceTempRR = Short.parseShort(sTyreSurfaceTempRR);
            short shTyreSurfaceTempRL = Short.parseShort(sTyreSurfaceTempRL);
            data.setErsDeployMode(bErsDeployMode);
            data.setErsHarvestMGUH(fErsMGUH);
            data.setErsHarvestMGUK(fErsMGUK);
            data.setErsStoreEngergy(fErsStoreEnergy);
            data.setErsDeployed(fErsDeployed);
            data.setTyreInnerTempFL(shTyreInnerTempFL);
            data.setTyreInnerTempFR(shTyreInnerTempFR);
            data.setTyreInnerTempRL(shTyreInnerTempRL);
            data.setTyreInnerTempRR(shTyreInnerTempRR);
            data.setTyreSurfaceTempFL(shTyreSurfaceTempFL);
            data.setTyreSurfaceTempFR(shTyreSurfaceTempFR);
            data.setTyreSurfaceTempRL(shTyreSurfaceTempRL);
            data.setTyreSurfaceTempRR(shTyreSurfaceTempRR);
            
        }catch(NumberFormatException e){
            data = null;
        }
        
        return data;
    }
    
    /**/
    public void unloadData(){
        this.lapData.clear();
        this.lapData = new ArrayList<>();
    }
}

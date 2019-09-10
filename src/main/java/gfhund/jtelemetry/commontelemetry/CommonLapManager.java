/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfhund.jtelemetry.commontelemetry;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;
/**
 *
 * @author PhilippGL
 */
public class CommonLapManager {
    HashMap<LapIdentificationObject,CommonTelemetryManager> lapsData;

    public CommonLapManager() {
        lapsData = new HashMap<>();
    }
    /*
    public void addTelemetry(LapIdentificationObject id,CommonTelemetryData data){
        lapsData.get(id).addLapData(data);
    }
    */
    public void addLap(LapIdentificationObject id){
        lapsData.put(id, new CommonTelemetryManager(id));
    }
    
    public ArrayList<CommonTelemetryData> getLapData(LapIdentificationObject id){
        return lapsData.get(id).getLapData();
    }
    
    public void removeLap(LapIdentificationObject id){
        lapsData.get(id).unloadData();
        lapsData.remove(id);
    }
    
    public LapIdentificationObject[] getIds(){
        LapIdentificationObject[] ret = new LapIdentificationObject[this.lapsData.size()]; 
        int pointer = 0;
        for( LapIdentificationObject key: this.lapsData.keySet() ){
            ret[pointer] = key;
            pointer++;
        }
        return ret;
    }
    
}

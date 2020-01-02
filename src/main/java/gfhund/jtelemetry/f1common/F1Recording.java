/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfhund.jtelemetry.f1common;

import gfhund.jtelemetry.ClassManager;
import gfhund.jtelemetry.Vector3D;
import gfhund.jtelemetry.commontelemetry.AbstractPacket;
import gfhund.jtelemetry.commontelemetry.CommonTelemetryData;
import gfhund.jtelemetry.commontelemetry.LapIdentificationObject;
import gfhund.jtelemetry.data.Settings;

import gfhund.jtelemetry.f1y19.F1Y2019ParseThread;

import gfhund.jtelemetry.network.GameNetworkConnection;
import gfhund.jtelemetry.network.ReceiveEvent;
import gfhund.jtelemetry.f1common.F1ParseThread;

import gfhund.jtelemetry.f1y18.F1Y2018ParseThread;
import gfhund.jtelemetry.fxgui.TelemetryWriter;
import gfhund.jtelemetry.stfFormat.StfFormatWriter;
import io.reactivex.BackpressureStrategy;

import java.util.concurrent.locks.ReentrantLock;
import javafx.scene.control.Alert;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
//import jdk.internal.org.jline.utils.InfoCmp;

/**
 *
 * @author PhilippHolzmann
 */
public class F1Recording {
    private Thread m_f1Thread;
    private GameNetworkConnection m_networkThread;
    private CommonTelemetryData[] currentData = new CommonTelemetryData[20];
    private int[] lastLapNum = new int[20];
    private LapIdentificationObject[] lidData = new LapIdentificationObject[20];
    private boolean recordingStarted = false;
    private int playerCarIndex;
    private byte sessionType;
    private static final Logger logging = Logger.getLogger(F1Recording.class.getName());
    StfFormatWriter metawriter;
    
    public F1Recording(){
        for(int i=0;i<currentData.length;i++){
            currentData[i] = new CommonTelemetryData();
        }
        for(int i =0;i<lidData.length;i++){
            lidData[i] = new LapIdentificationObject();
        }
           
        try{
             metawriter= new StfFormatWriter("./temp/metadata.stf","LapIdentification");
        }
        catch(IOException e){
            
        }    
    }
    
    //private Queue<AbstractPacket> m_packetQueue = new Queue<AbstractPacket>();
    
    public Flowable<CommonTelemetryData> startRecording(F1Games game){
        File tempDir = new File("./temp");
        for(File child: tempDir.listFiles()){
            File[] dirs = child.listFiles();
            if(dirs != null){
                for(File childFile:child.listFiles()){
                    childFile.delete();
                }
            }
            child.delete();
        }
        
        Flowable<CommonTelemetryData> source;
        recordingStarted = true;
        source = Flowable.create(new FlowableOnSubscribe<CommonTelemetryData>() {
            @Override
            public void subscribe(FlowableEmitter<CommonTelemetryData> fe) throws Exception {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                ReentrantLock lock = new ReentrantLock();
                java.util.concurrent.locks.Condition cond = lock.newCondition();
                m_networkThread = new GameNetworkConnection(lock,cond,20777,1347);
                //F1Y2018ParseThread parseThread = new F1Y2018ParseThread(lock,cond);
                F1ParseThread parseThread;
                switch(game){
                    case F1_2018:{
                        parseThread = new F1Y2018ParseThread(lock, cond);
                        break;
                    }
                    case F1_2019: {
                        parseThread = new F1Y2019ParseThread(lock,cond);
                        break;
                    }
                    default:{
                        parseThread = new F1Y2019ParseThread(lock,cond);
                    }
                }
                if(m_f1Thread == null){
                    m_f1Thread = new Thread(parseThread);
                }
                parseThread.addParseResultEvent(new F1ParseResultEvent() {
                    @Override
                    public void resultEvent(AbstractPacket packet) {
                        if(processPacket(packet)){
                            Settings set = null;
                            try{
                                set = (Settings)gfhund.jtelemetry.ClassManager.get(Settings.class);
                            }catch(ClassManager.ClassManagerException e){
                                logging.log(Level.WARNING, "Could not find Class Setting.", e);
                            }
                            String recordingType = set.getValue("f1RecordingType","0");
                            Boolean bRecoringType = true;
                            try{
                                int iRecordingType = Integer.parseInt(recordingType);
                                bRecoringType = (iRecordingType == 0)?true:false;
                            }catch(NumberFormatException e){
                                logging.log(Level.WARNING, "Could not parse \"f1RecordingType\" to integer.", e);
                            }
                            if(bRecoringType){
                                fe.onNext(currentData[playerCarIndex]);
                                CommonTelemetryData temp = currentData[playerCarIndex];
                                try{
                                    currentData[playerCarIndex] = (CommonTelemetryData)temp.clone();
                                }catch(CloneNotSupportedException e){
                                    logging.log(Level.WARNING, e.getMessage(), e);
                                }
                                if(lidData[playerCarIndex].getLapNum() != lastLapNum[playerCarIndex]){
                                    HashMap<String, String> map = new HashMap();
                                    map.put("lapNum",""+lidData[playerCarIndex].getLapNum());
                                    map.put("lapTime",""+lidData[playerCarIndex].getLapTime());
                                    map.put("playerName", ""+lidData[playerCarIndex].getPlayer());
                                    Date now = new Date();
                                    map.put("date",getDateFromDatObj(now));
                                    try{
                                        metawriter.writePropertyClass("lapId", map);
                                    }catch(IOException e){
                                        logging.log(Level.WARNING, e.getMessage(), e);
                                    }
                                }
                            }
                            else{
                                for(int i=0;i < currentData.length;i++){
                                    fe.onNext(currentData[i]);

                                    CommonTelemetryData temp = currentData[i];
                                    try{
                                        currentData[i] = (CommonTelemetryData)temp.clone();
                                    }catch(CloneNotSupportedException e){
                                        logging.log(Level.WARNING, e.getMessage(), e);
                                    }
                                }
                                for(int i = 0;i < lidData.length;i++){
                                    if(lidData[i].getLapNum() != lastLapNum[i]){
                                        HashMap<String,String> map = new HashMap();
                                        map.put("lapNum",""+lidData[i].getLapNum());
                                        map.put("lapTime", ""+lidData[i].getLapTime());
                                        map.put("playerName", ""+lidData[i].getPlayer());
                                        Date now = new Date();
                                        map.put("date",getDateFromDatObj(now));
                                        try{
                                            metawriter.writePropertyClass("lapId", map);
                                        }catch(IOException e){
                                            logging.log(Level.WARNING, e.getMessage(), e);
                                        }

                                    }
                                } 
                            }
                            
                        }
                    }
                });
                
                parseThread.addOnInterruptResultEvent(() -> {
                    fe.onComplete();
                });
                m_networkThread.addReciveEvent(new ReceiveEvent(){
                    @Override
                    public void onReceive(byte[] data){
                        //System.out.println("Übergebe einem Consumer Thread");
                        parseThread.addRaw(data);
                    }
                });
                m_f1Thread.start();
                m_networkThread.start();
                
            }
        }, BackpressureStrategy.LATEST);
        
        return source;
    }
    
    public void stopRecording(){
        
        if(!recordingStarted){
            return;
        }
        m_networkThread.interrupt();
        m_f1Thread.interrupt();
        try{
            metawriter.closeFile();
        }
        catch(IOException e){}
        
    }
    
    protected boolean processPacket(AbstractPacket packet){
        //System.out.println("Class: "+packet.getClass().getName());
        if(packet instanceof gfhund.jtelemetry.f1y18.PacketMotionData){
            gfhund.jtelemetry.f1y18.PacketMotionData motionDataPacket = 
                    (gfhund.jtelemetry.f1y18.PacketMotionData) packet;
            playerCarIndex = motionDataPacket.getHeader().getPlayerCarIndex();
            for(int i=0;i<currentData.length;i++){
                float x = motionDataPacket.getCarMotionData(i).getWorldPositionX();
                float y = motionDataPacket.getCarMotionData(i).getWorldPositionX();
                float z = motionDataPacket.getCarMotionData(i).getWorldPositionX();
                currentData[i].setPos(new Vector3D(x,y,z));
                currentData[i].setCarIndex((short)i);
            }
            return false;
            
        } else if(packet instanceof gfhund.jtelemetry.f1y18.PacketSessionData){
            gfhund.jtelemetry.f1y18.PacketSessionData sessionPacket =
                    (gfhund.jtelemetry.f1y18.PacketSessionData) packet;
            this.sessionType = sessionPacket.getSessionType();
            //System.out.println("2018: Session Type: "+this.sessionType);
        } else if(packet instanceof gfhund.jtelemetry.f1y18.PacketLapData){
            gfhund.jtelemetry.f1y18.PacketLapData lapDataPacket = 
                    (gfhund.jtelemetry.f1y18.PacketLapData) packet;
            playerCarIndex = lapDataPacket.getHeader().getPlayerCarIndex();
            boolean isAllReady = true;
            
            for(int i=0;i<currentData.length;i++){
                currentData[i].setDistance(lapDataPacket.getLapData(i).getLapDistance());
                currentData[i].setLapNum(lapDataPacket.getLapData(i).getCurrentLapNum());
                this.lastLapNum[i] = this.lidData[i].getLapNum();
                this.lidData[i].setLapNum(lapDataPacket.getLapData(i).getCurrentLapNum());
                currentData[i].setCurrentTime(lapDataPacket.getLapData(i).getCurrentLapTime());
                currentData[i].setCarIndex((short)i);
                isAllReady = isAllReady && currentData[i].isReadyToSave();
            }
            return isAllReady;
        } else if(packet instanceof gfhund.jtelemetry.f1y18.PacketEventData) {
            //
        } else if(packet instanceof gfhund.jtelemetry.f1y18.PacketParticipantsData){
            gfhund.jtelemetry.f1y18.PacketParticipantsData participantsDataPacket = 
                    (gfhund.jtelemetry.f1y18.PacketParticipantsData) packet;
            playerCarIndex = participantsDataPacket.getHeader().getPlayerCarIndex();
            for(int i=0;i<currentData.length;i++){
                currentData[i].setDriverName(participantsDataPacket.getParticipant(i).getName());
                currentData[i].setCarIndex((short)i);
            }
            return false;
        } else if(packet instanceof gfhund.jtelemetry.f1y18.PacketCarSetupData){
            //
        } else if(packet instanceof gfhund.jtelemetry.f1y18.PacketCarTelemetryData) {
            gfhund.jtelemetry.f1y18.PacketCarTelemetryData telemetryDataPacket = 
                    (gfhund.jtelemetry.f1y18.PacketCarTelemetryData) packet;
            playerCarIndex = telemetryDataPacket.getHeader().getPlayerCarIndex();
            for(int i=0;i<currentData.length;i++){
                currentData[i].setSpeed(telemetryDataPacket.getCarTelemetryData(i).getSpeed());
                currentData[i].setBrake(telemetryDataPacket.getCarTelemetryData(i).getBrake());
                currentData[i].setThrottle(telemetryDataPacket.getCarTelemetryData(i).getThrottle());
                currentData[i].setRpm(telemetryDataPacket.getCarTelemetryData(i).getEngineRPM());
                currentData[i].setGear(telemetryDataPacket.getCarTelemetryData(i).getGear());
                currentData[i].setCarIndex((short)i);
            }
            return false;
        } else if(packet instanceof gfhund.jtelemetry.f1y18.PacketCarStatusData) {
            //
        } else if(packet instanceof gfhund.jtelemetry.f1y19.PacketMotionData) {
            gfhund.jtelemetry.f1y19.PacketMotionData motionDataPacket = 
                    (gfhund.jtelemetry.f1y19.PacketMotionData) packet;
            boolean isAllReady = true;
            playerCarIndex = motionDataPacket.getHeader().getPlayerCarIndex();
            for(int i=0;i<currentData.length;i++){
                float x = motionDataPacket.getCarMotionData(i).getWorldPositionX();
                float y = motionDataPacket.getCarMotionData(i).getWorldPositionY();
                float z = motionDataPacket.getCarMotionData(i).getWorldPositionZ();
                currentData[i].setPos(new Vector3D(x,y,z));
                currentData[i].setCarIndex((short)i);
                isAllReady = isAllReady && currentData[i].isReadyToSave();
            }
            return isAllReady;
        } else if(packet instanceof gfhund.jtelemetry.f1y19.PacketSessionData) {
            gfhund.jtelemetry.f1y19.PacketSessionData sessionPacket =
                    (gfhund.jtelemetry.f1y19.PacketSessionData) packet;
            this.sessionType = sessionPacket.getSessionType();
            System.out.println("Session Type: "+this.sessionType);
        } else if(packet instanceof gfhund.jtelemetry.f1y19.PacketLapData) {
            gfhund.jtelemetry.f1y19.PacketLapData lapDataPacket = 
                    (gfhund.jtelemetry.f1y19.PacketLapData) packet;
            boolean isAllReady = true;
            playerCarIndex = lapDataPacket.getHeader().getPlayerCarIndex();
            //System.out.println("LapDistance: "+lapDataPacket.getLapData(0).getLapDistance());
            for(int i=0;i<currentData.length;i++){
                currentData[i].setDistance(lapDataPacket.getLapData(i).getLapDistance());
                this.lastLapNum[i] = this.lidData[i].getLapNum();
                if(sessionType == 12 && i != lapDataPacket.getHeader().getPlayerCarIndex()){
                    int playerIndex = lapDataPacket.getHeader().getPlayerCarIndex();
                    currentData[i].setLapNum(
                            lapDataPacket.getLapData(playerIndex)
                                    .getCurrentLapNum()
                    );
                    currentData[i].setCurrentTime(
                            lapDataPacket.getLapData(playerIndex)
                                    .getCurrentLapTime()
                    );
                    lidData[i].setLapNum(lapDataPacket.getLapData(playerIndex)
                                    .getCurrentLapNum());
                    lidData[i].setLapTime(lapDataPacket.getLapData(playerIndex)
                            .getLastLapTime());
                }
                else{
                    currentData[i].setLapNum(lapDataPacket.getLapData(i).getCurrentLapNum());
                    lidData[i].setLapNum(lapDataPacket.getLapData(i).getCurrentLapNum());
                    lidData[i].setLapTime(lapDataPacket.getLapData(i).getLastLapTime());
                    currentData[i].setCurrentTime(lapDataPacket.getLapData(i).getCurrentLapTime());
                }
                
                
                currentData[i].setCarIndex((short)i);
                isAllReady = isAllReady && currentData[i].isReadyToSave();
            }
            return isAllReady;
        } else if(packet instanceof gfhund.jtelemetry.f1y19.PacketEventData){
            //
        } else if(packet instanceof gfhund.jtelemetry.f1y19.PacketParticipantsData){
            gfhund.jtelemetry.f1y19.PacketParticipantsData participantsDataPacket = 
                    (gfhund.jtelemetry.f1y19.PacketParticipantsData) packet;
            playerCarIndex = participantsDataPacket.getHeader19().getPlayerCarIndex();
            for(int i=0;i<currentData.length;i++){
                currentData[i].setDriverName(participantsDataPacket.getParticipant(i).getName());
                lidData[i].setPlayer(participantsDataPacket.getParticipant(i).getName());
                currentData[i].setCarIndex((short)i);
            }
            return false;
        } else if(packet instanceof gfhund.jtelemetry.f1y19.PacketCarSetupData){
            gfhund.jtelemetry.f1y19.PacketCarSetupData packetSetup = (gfhund.jtelemetry.f1y19.PacketCarSetupData) packet;
            playerCarIndex = packetSetup.getHeader19().getPlayerCarIndex();
            File setupData = new File("SetupData.raw");
            byte[] raw = packetSetup.getBytes();
            try{
                java.io.FileOutputStream stream = new FileOutputStream(setupData);
                stream.write(raw);
                stream.close();
            }
            catch(FileNotFoundException e){
                System.out.println(e.getMessage());
            }catch(IOException e){
                System.out.println(e.getMessage());
            }
            
            
        } else if(packet instanceof gfhund.jtelemetry.f1y19.PacketCarTelemetryData) {
            gfhund.jtelemetry.f1y19.PacketCarTelemetryData telemetryDataPacket = 
                    (gfhund.jtelemetry.f1y19.PacketCarTelemetryData) packet;
            playerCarIndex = telemetryDataPacket.getHeader19().getPlayerCarIndex();
            for(int i=0;i<currentData.length;i++){
                currentData[i].setSpeed(telemetryDataPacket.getCarTelemetryData(i).getSpeed());
                currentData[i].setBrake((byte)(telemetryDataPacket.getCarTelemetryData(i).getFBrake()*100));
                currentData[i].setThrottle((byte)(telemetryDataPacket.getCarTelemetryData(i).getFThrottle()*100));
                currentData[i].setRpm(telemetryDataPacket.getCarTelemetryData(i).getEngineRPM());
                currentData[i].setGear(telemetryDataPacket.getCarTelemetryData(i).getGear());
                currentData[i].setCarIndex((short)i);
                currentData[i].setTyreInnerTempFL(telemetryDataPacket.getCarTelemetryData(i).getTyreInnerTemperature(2));
                currentData[i].setTyreInnerTempFR(telemetryDataPacket.getCarTelemetryData(i).getTyreInnerTemperature(3));
                currentData[i].setTyreInnerTempRR(telemetryDataPacket.getCarTelemetryData(i).getTyreInnerTemperature(1));
                currentData[i].setTyreInnerTempRL(telemetryDataPacket.getCarTelemetryData(i).getTyreInnerTemperature(0));
                currentData[i].setTyreSurfaceTempFL(telemetryDataPacket.getCarTelemetryData(i).getTyreSurfaceTemperature(2));
                currentData[i].setTyreSurfaceTempFR(telemetryDataPacket.getCarTelemetryData(i).getTyreSurfaceTemperature(3));
                currentData[i].setTyreSurfaceTempRR(telemetryDataPacket.getCarTelemetryData(i).getTyreSurfaceTemperature(1));
                currentData[i].setTyreSurfaceTempRL(telemetryDataPacket.getCarTelemetryData(i).getTyreSurfaceTemperature(0));
                
            }
            return false;
        } else if(packet instanceof gfhund.jtelemetry.f1y19.PacketCarStatusData){
            gfhund.jtelemetry.f1y19.PacketCarStatusData statusData = 
                    (gfhund.jtelemetry.f1y19.PacketCarStatusData) packet;
            for(int i=0;i<currentData.length;i++){
                currentData[i].setErsDeployMode(statusData.getCarStatusData(i).getErsDeployMode());
                currentData[i].setErsDeployed(statusData.getCarStatusData(i).getErsDeployedThisLap());
                currentData[i].setErsHarvestMGUH(statusData.getCarStatusData(i).getErsHarvestedThisLapMGUH());
                currentData[i].setErsHarvestMGUK(statusData.getCarStatusData(i).getErsHarvestedThisLapMGUK());
                currentData[i].setErsStoreEngergy(statusData.getCarStatusData(i).getErsStoreEnergy());
            }
        }
        return false;
    }
    
    private String getDateFromDatObj(Date date) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        
        int month = cal.get(GregorianCalendar.MONTH);
        String sMonth = "" + month;
        if(month < 10) sMonth = "0"+month;
        
        int dayOfMonth = cal.get(GregorianCalendar.DAY_OF_MONTH);
        String sDayOfMonth = "" + dayOfMonth;
        if(dayOfMonth < 10) sDayOfMonth = "0" + sDayOfMonth;
        
        int hour = cal.get(GregorianCalendar.HOUR_OF_DAY);
        String sHour = ""+ hour;
        if(hour < 10) sHour = "0"+hour;
        
        int minute = cal.get(GregorianCalendar.MINUTE);
        String sMinute = "" + minute;
        if(minute < 10) sMinute = "0" + minute;
        
        String ret = 
                "" + 
                cal.get(GregorianCalendar.YEAR) +
                sMonth + 
                sDayOfMonth+
                sHour+
                sMinute;
        return ret;
    }
    
    public static enum F1Games{
        F1_2018((byte) 0),
        F1_2019((byte) 1);
        byte value;

        F1Games(byte value){
            this.value = value;
        }
        
        public byte getValue() {
            return value;
        }

        public void setValue(byte value) {
            this.value = value;
        }
        
        
    }
}

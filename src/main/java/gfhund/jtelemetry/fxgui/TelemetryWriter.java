/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfhund.jtelemetry.fxgui;

import gfhund.jtelemetry.commontelemetry.AbstractPacket;
import gfhund.jtelemetry.commontelemetry.CommonTelemetryData;
import gfhund.jtelemetry.f1y18.CarMotionData;
import gfhund.jtelemetry.f1y18.CarStatusData;
import gfhund.jtelemetry.f1y18.CarTelemetryData;
import gfhund.jtelemetry.f1y18.Header;
import gfhund.jtelemetry.f1y18.PacketCarStatusData;
import gfhund.jtelemetry.f1y18.PacketCarTelemetryData;
import gfhund.jtelemetry.f1y18.PacketLapData;
import gfhund.jtelemetry.f1y18.PacketMotionData;
import gfhund.jtelemetry.f1y18.PacketParticipantsData;
import gfhund.jtelemetry.stfFormat.StfFormatWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.zip.ZipOutputStream;
import java.util.zip.ZipEntry;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.jvm.hotspot.oops.DataLayout;

/**
 *
 * @author PhilippGL
 */
public class TelemetryWriter {
    class PlayerValues{
        public byte m_lap;
        public String name;
        public float lastLapTime;
        public float currentLapTime;
        public float section1Time;
        public float section2Time;
        public float posX;
        public float posY;
        public float posZ;
        public byte tyreDamageRL;
        public byte tyreDamageRR;
        public byte tyreDamageFL;
        public byte tyreDamageFR;
    }
    
    class TelemetryValue{
        public String frameIdentifier;
        public String lap;
        public String currentLapTime;
        public String lastLapTime;
        public String section1Time;
        public String section2Time;
        public String sessionUid;
        public String sessionTime;
        public String speed;
        public String throttle;
        public String brake;
        public String steer;
        public String clutch;
        public String rpm;
        public String gear;
        public String brakeTemperatureRL;
        public String brakeTemperatureRR;
        public String brakeTemperatureFL;
        public String brakeTemperatureFR;
        public String tyresSurfaceTemperatureRL;
        public String tyresSurfaceTemperatureRR;
        public String tyresSurfaceTemperatureFL;
        public String tyresSurfaceTemperatureFR;
        public String tyresInnerTemperatureRL;
        public String tyresInnerTemperatureRR;
        public String tyresInnerTemperatureFL;
        public String tyresInnerTemperatureFR;
        public String tyresPressureRL;
        public String tyresPressureRR;
        public String tyresPressureFL;
        public String tyresPressureFR;
        public String posX;
        public String posY;
        public String posZ;
        public String tyreDamageRL;
        public String tyreDamageRR;
        public String tyreDamageFL;
        public String tyreDamageFR;
        
        public HashMap<String,String> getHashMap(){
             HashMap<String,String> mapping = new HashMap<>();
                mapping.put("lap", ""+this.lap);
                mapping.put("currentLapTime",""+this.currentLapTime);
                mapping.put("lastLapTime",""+this.lastLapTime);
                mapping.put("section1Time",""+this.section1Time);
                mapping.put("section2Time",""+this.section2Time);
                mapping.put("sessionUid", ""+this.sessionUid);
                mapping.put("sessionTime", ""+this.sessionTime);
                
                mapping.put("speed", ""+speed);
                mapping.put("throttle", ""+throttle);
                mapping.put("brake", ""+brake);
                mapping.put("steer", ""+steer);
                //mapping.put("clutch", ""+clutch);
                mapping.put("rpm", ""+rpm);
                mapping.put("gear", ""+gear);
                
                mapping.put("brakeTemperatureRL",""+brakeTemperatureRL);
                mapping.put("brakeTemperatureRR",""+brakeTemperatureRR);
                mapping.put("brakeTemperatureFL",""+brakeTemperatureFL);
                mapping.put("brakeTemperatureFR",""+brakeTemperatureFR);
                
                mapping.put("tyresSurfaceTemperatureRL",""+tyresSurfaceTemperatureRL);
                mapping.put("tyresSurfaceTemperatureRR",""+tyresSurfaceTemperatureRR);
                mapping.put("tyresSurfaceTemperatureFL",""+tyresSurfaceTemperatureFL);
                mapping.put("tyresSurfaceTemperatureFR",""+tyresSurfaceTemperatureFR);
                
                mapping.put("tyresInnerTemperatureRL",""+tyresInnerTemperatureRL);
                mapping.put("tyresInnerTemperatureRR",""+tyresInnerTemperatureRR);
                mapping.put("tyresInnerTemperatureFL",""+tyresInnerTemperatureFL);
                mapping.put("tyresInnerTemperatureFR",""+tyresInnerTemperatureFR);
                
                mapping.put("tyresPressureRL",""+tyresPressureRL);
                mapping.put("tyresPressureRR",""+tyresPressureRR);
                mapping.put("tyresPressureFL",""+tyresPressureFL);
                mapping.put("tyresPressureFR",""+tyresPressureFR);
                mapping.put("posX",""+posX);
                mapping.put("posY",""+posY);
                mapping.put("posZ",""+posZ);
                
                mapping.put("tyreDamageRL", ""+tyreDamageRL);
                mapping.put("tyreDamageRR", ""+tyreDamageRR);
                mapping.put("tyreDamageFL", ""+tyreDamageFL);
                mapping.put("tyreDamageFR", ""+tyreDamageFR);
                
                return mapping;
        }
    }
    
    private static final int BUFFER_SIZE = 1000;
    
    private TelemetryValue[] buffer = new TelemetryValue[BUFFER_SIZE];
    private int bufferPointer = 0;
    
    private StfFormatWriter[] m_playerPosition = new StfFormatWriter[20];
    private StfFormatWriter m_ownTelemetry = null;
    private PlayerValues[] m_playerValues = new PlayerValues[20];
    private byte m_lap;
    private static final Logger logging = Logger.getLogger(TelemetryWriter.class.getName());
    private ArrayList<ProgressEvent> progressEventList = new ArrayList<>();
    private ArrayList<FinishEvent> finishEventList = new ArrayList<>();
    
    
    

    public TelemetryWriter() {
        File directory = new File("./temp");
        if(!directory.isDirectory()){
            if(!directory.isFile()){
                directory.mkdirs();
            }
            else{
                //Fehler
            }
        } 
    }
    
    public void processPackage(AbstractPacket packet){
        if(m_ownTelemetry == null){
            try{
                m_ownTelemetry = new StfFormatWriter("./temp/ownTelemetry.stf","ownTelemetry");

                for(int i=0;i<m_playerPosition.length;i++){

                    //m_playerPosition[i] = new StfFormatWriter("./temp/player"+i+".stf", "player"+i);
                    m_playerValues[i] = new PlayerValues();
                }

            }
            catch(IOException e){
                logging.log(Level.WARNING, "Fehler beim Anlegen der Telemetrie Datei", e);
            }
        }
        if(packet instanceof PacketParticipantsData){
            for(int i=0 ;i<20;i++){
                //this.setPlayerName(i, ((PacketParticipantsData) packet).getParticipant(i).getName());
                this.m_playerValues[i].name = ((PacketParticipantsData) packet).getParticipant(i).getName();
            }
        }else if(packet instanceof PacketLapData){
            for(int i=0;i<20;i++){
               this.m_playerValues[i].m_lap = ((PacketLapData) packet).getLapData(i).getCurrentLapNum();
               this.m_playerValues[i].currentLapTime = ((PacketLapData)packet).getLapData(i).getCurrentLapTime();
               this.m_playerValues[i].lastLapTime = ((PacketLapData)packet).getLapData(i).getLastLapTime();
               this.m_playerValues[i].section1Time = ((PacketLapData)packet).getLapData(i).getSector1Time();
               this.m_playerValues[i].section2Time = ((PacketLapData)packet).getLapData(i).getSector2Time();
            }
        }
        else if(packet instanceof PacketMotionData){
            Header head = ((PacketMotionData) packet).getHeader();
            
            for(int i=0 ; i<20;i++){
                CarMotionData motionData = ((PacketMotionData) packet).getCarMotionData(i);
                HashMap<String,String> mapping = new HashMap<>();
                mapping.put("playerName", ""+this.m_playerValues[i].name);
                mapping.put("lap", ""+this.m_playerValues[i].m_lap);
                
                mapping.put("sessionUid", ""+head.getSessionUid());
                mapping.put("sessionTime", ""+head.getSessionTime());
                
                mapping.put("worldPositionX", ""+motionData.getWorldPositionX());
                mapping.put("worldPositionY", ""+motionData.getWorldPositionY());
                mapping.put("worldPositionZ", ""+motionData.getWorldPositionZ());
                this.m_playerValues[i].posX = motionData.getWorldPositionX();
                this.m_playerValues[i].posY = motionData.getWorldPositionY();
                this.m_playerValues[i].posZ = motionData.getWorldPositionZ();
                
                mapping.put("worldVelocityX", ""+motionData.getWorldVelocityX());
                mapping.put("worldVelocityY", ""+motionData.getWorldVelocityY());
                mapping.put("worldVelocityZ", ""+motionData.getWorldVelocityZ());
                
                mapping.put("worldForwardDirX", ""+motionData.getWorldForwardDirX());
                mapping.put("worldForwardDirY", ""+motionData.getWorldForwardDirY());
                mapping.put("worldForwardDirZ", ""+motionData.getWorldForwardDirZ());
                
                mapping.put("worldRightDirX", ""+motionData.getWorldRightDirX());
                mapping.put("worldRightDirY", ""+motionData.getWorldRightDirY());
                mapping.put("worldRightDirZ",""+motionData.getWorldRightDirZ());
                /*
                try{
                    this.m_playerPosition[i].writePropertyClass("carMotionData"+head.getFrameIdentifier(), mapping);
                }
                catch(IOException e){
                    logging.log(Level.WARNING, "Fehler beim Schreiben der Telemetrie Datei von Spieler "+i, e);
                }
*/
                
            }
        }
        else if( packet instanceof PacketCarTelemetryData){
            int playerIndex = ((PacketCarTelemetryData) packet).getHeader().getPlayerCarIndex();
            if(playerIndex >= 0 && playerIndex<20){
                TelemetryValue value = new TelemetryValue();
                HashMap<String,String> mapping = new HashMap<>();
                value.lap = ""+this.m_playerValues[playerIndex].m_lap;
                value.currentLapTime = ""+this.m_playerValues[playerIndex].currentLapTime;
                value.lastLapTime = ""+this.m_playerValues[playerIndex].lastLapTime;
                value.section1Time = ""+this.m_playerValues[playerIndex].section1Time;
                value.section2Time = ""+this.m_playerValues[playerIndex].section2Time;
                value.posX = ""+this.m_playerValues[playerIndex].posX;
                value.posY = ""+this.m_playerValues[playerIndex].posY;
                value.posZ = ""+this.m_playerValues[playerIndex].posZ;
                value.sessionUid = ""+((PacketCarTelemetryData) packet).getHeader().getSessionUid();
                value.sessionTime = ""+((PacketCarTelemetryData) packet).getHeader().getSessionTime();
                
                CarTelemetryData telemetry = ((PacketCarTelemetryData) packet).getCarTelemetryData(playerIndex);
                
                value.speed =  ""+telemetry.getSpeed();
                value.throttle = ""+telemetry.getThrottle();
                value.brake = ""+telemetry.getBrake();
                value.steer = ""+telemetry.getSteer();
                value.clutch = ""+telemetry.getClutch();
                value.rpm = ""+telemetry.getEngineRPM();
                value.gear = ""+telemetry.getGear();
                
                value.brakeTemperatureRL = ""+telemetry.getBrakeTemperature(0);
                value.brakeTemperatureRR = ""+telemetry.getBrakeTemperature(1);
                value.brakeTemperatureFL = ""+telemetry.getBrakeTemperature(2);
                value.brakeTemperatureFR = ""+telemetry.getBrakeTemperature(3);
                
                value.tyresSurfaceTemperatureRL = ""+telemetry.getTyreSurfaceTemperature(0);
                value.tyresSurfaceTemperatureRR = ""+telemetry.getTyreSurfaceTemperature(1);
                value.tyresSurfaceTemperatureFL = ""+telemetry.getTyreSurfaceTemperature(2);
                value.tyresSurfaceTemperatureFR = ""+telemetry.getTyreSurfaceTemperature(3);
                
                value.tyresInnerTemperatureRL = ""+telemetry.getTyreInnerTemperature(0);
                value.tyresInnerTemperatureRR = ""+telemetry.getTyreInnerTemperature(1);
                value.tyresInnerTemperatureFL = ""+telemetry.getTyreInnerTemperature(2);
                value.tyresInnerTemperatureFR = ""+telemetry.getTyreInnerTemperature(3);
                
                value.tyresPressureRL = ""+telemetry.getTyrePressure(0);
                value.tyresPressureRR = ""+telemetry.getTyrePressure(1);
                value.tyresPressureFL = ""+telemetry.getTyrePressure(2);
                value.tyresPressureFR = ""+telemetry.getTyrePressure(3);
                Header head = ((PacketCarTelemetryData) packet).getHeader();
                value.frameIdentifier = ""+head.getFrameIdentifier();
                value.tyreDamageFL = ""+this.m_playerValues[playerIndex].tyreDamageFL;
                value.tyreDamageRL = ""+this.m_playerValues[playerIndex].tyreDamageRL;
                value.tyreDamageRR = ""+this.m_playerValues[playerIndex].tyreDamageRR;
                value.tyreDamageFR = ""+this.m_playerValues[playerIndex].tyreDamageFR;
                
                buffer[bufferPointer] = value;
                bufferPointer++;
                
                if(bufferPointer >= buffer.length){
                    for(int i=0;i<buffer.length;i++){
                        try{
                            HashMap<String,String> map = buffer[i].getHashMap();
                            this.m_ownTelemetry.writePropertyClass("ownProperty"+buffer[i].frameIdentifier, map);
                        }
                        catch(IOException e){
                            logging.log(Level.WARNING, "Fehler beim Schreiben der eigenen Telemetrie Datei", e);
                        }
                    }
                    bufferPointer = 0;
                    
                }
                
            }
        }else if(packet instanceof PacketCarStatusData) {
            int playerIndex = ((PacketCarTelemetryData) packet).getHeader().getPlayerCarIndex();
            for(int i=0; i < 20;i++) {
                CarStatusData carStatus = ((PacketCarStatusData) packet).getCarStatusData(i);
                this.m_playerValues[i].tyreDamageFL = carStatus.getTyresWearFL();
                this.m_playerValues[i].tyreDamageFR = carStatus.getTyresWearFR();
                this.m_playerValues[i].tyreDamageRR = carStatus.getTyresWearRR();
                this.m_playerValues[i].tyreDamageRL = carStatus.getTyresWearRL();
            }
        }
    }
    
    public void closeTelemetry(File file){
        //Write all remaining data to File
        for(int i=0;i<bufferPointer;i++){
            try{
                HashMap<String,String> map = buffer[i].getHashMap();
                this.m_ownTelemetry.writePropertyClass("ownProperty"+buffer[i].frameIdentifier, map);
            }
            catch(IOException e){
                logging.log(Level.WARNING, "Fehler beim Schreiben der eigenen Telemetrie Datei", e);
            }
        }
        if(bufferPointer != 0){
            try{
                this.m_ownTelemetry.closeFile();
            }catch(IOException e){
                logging.log(Level.WARNING, "Fehler beim schließen der Telemetrie Datei", e);
                return;
            }
        }
        
        for(String key: commonTelemetryBuffer.keySet()){
            CommonTelemetryData[] localBuffer = commonTelemetryBuffer.get(key);
            int pointer = commonTelemetryBufferPointer.get(key);
            if(pointer == 0) continue;
            Date now = new Date();
            String filename = getDateFromDatObj(now)+"_"+localBuffer[0].getLapNum()+".stf";
            File telemetry = new File("./temp/"+key+"/"+filename);
            try {
                StfFormatWriter writer = new StfFormatWriter(telemetry, "ownTelemetry");
                for(int i = 0;i< pointer;i++){
                    HashMap<String,String> dataHashMap = getHashMapFromCommonTelemetryData(localBuffer[i]);
                    writer.writePropertyClass("ownProperty"+i, dataHashMap);
                }
                writer.closeFile();
            }catch(IOException e){
                logging.log(Level.WARNING, "Fehler beim Schreiben der eigenen Telemetrie Datei", e);
            }    
        }
        
        Thread packingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                File tempDirectory = new File("./temp");
                long totalSize= 0;
                long totalBytesWritten=0;
                File[] tempFiles = tempDirectory.listFiles();
                for(int i=0;i<tempFiles.length;i++){
                    totalSize += tempFiles[i].length();
                }
                try{
                    ZipOutputStream zipStream = new ZipOutputStream(Files.newOutputStream(file.toPath()));
                    for(int i=0;i<tempFiles.length;i++){
                        ZipEntry zipEntry = new ZipEntry(tempFiles[i].getName());
                        zipStream.putNextEntry(zipEntry);
                        FileInputStream tempFileInputStream = new FileInputStream(tempFiles[i]);
                        DataInputStream tempFileDataInputStream = new DataInputStream(tempFileInputStream);
                        byte[] data = new byte[1000];
                        for(int j=0;j<tempFiles[i].length();j+=1000){
                            
                            int numBytesWritten = tempFileDataInputStream.read(data);
                            if(numBytesWritten<=0){
                                break;
                            }
                            
                            zipStream.write(data,0,numBytesWritten);
                            totalBytesWritten += numBytesWritten;
                            float writtenInPercent = totalBytesWritten / totalSize;
                            for(ProgressEvent e:progressEventList){
                                e.onProgress(writtenInPercent);
                            }
                            
                        } 
                    }
                    zipStream.close();
                    for(FinishEvent e:finishEventList){
                        e.onFinish();
                    }
                }catch(IOException e){
                    logging.log(Level.WARNING, "Fehler beim Schreiben der eigenen Telemetrie Datei", e);
                }      
            }
        });
        //"./temp/player"+i+".stf"
        packingThread.start();
        
    }
    public void closeTelemetry(String zipFilename){
        File file = new File(zipFilename);
        this.closeTelemetry(file);
    }
    
    public void addProgressListener(ProgressEvent event){
        this.progressEventList.add(event);
    }
    public void addOnFinishListener(FinishEvent event){
        this.finishEventList.add(event);
    }
    
    private HashMap<String,CommonTelemetryData[]> commonTelemetryBuffer = new HashMap<>();//new CommonTelemetryData[1000];
    private HashMap<String, Integer> commonTelemetryBufferPointer = new HashMap<>();
    
    public void processCommonTelemetryData(CommonTelemetryData data){
        File tempDirectory = new File("./temp");
        if(!tempDirectory.isDirectory()){
            if(!tempDirectory.isFile()){
                tempDirectory.mkdirs();
            }
            else{
                //Fehler
            }
        }
        
        File[] driverDirs = tempDirectory.listFiles();
        boolean hasDirectory = false;
        File driverDir = null;
        for(File dir: driverDirs){
            if(dir.getName().equals(data.getDriverName())){
                driverDir = dir;
                hasDirectory = true;
            }
        }
        if(!hasDirectory){
            driverDir = new File("./temp/"+data.getDriverName());
            driverDir.mkdir();
        }
        
        File[] lapFiles = driverDir.listFiles();
        
        for(int i=0;i<lapFiles.length;i++){
            for(int j=i+1;j<lapFiles.length;j++){
                int value = lapFiles[i].getName().compareTo(lapFiles[j].getName());
                if(value > 0){
                    File temp = lapFiles[i];
                    lapFiles[i] = lapFiles[j];
                    lapFiles[j] = temp;
                }
            }
        }
        
        
        if(lapFiles.length != 0){
            String name;
            int lastLap;
            Date dateRound;
            File telemetryFile;
            String nameFirst = lapFiles[0].getName();
            //If the upper sorting algorithm has ordered it from old to new
            String nameLast = lapFiles[lapFiles.length-1].getName();

            int lapFirst = getLapNumFromFilename(nameFirst);
            int lapLast = getLapNumFromFilename(nameLast);
            
            Date dateFirst = getDateFromFilename(nameFirst);
            Date dateLast = getDateFromFilename(nameLast);
            
            if(dateFirst.after(dateLast)){//dateFirst is newer than the dateLast
                name = nameFirst;
                lastLap = lapFirst;
                dateRound = dateFirst;
                telemetryFile = lapFiles[0];
            }
            else {//dateLast ist newer than the dateFirst or both are equal. in this case it doesnt metter which data i use
                name = nameLast;
                lastLap = lapLast;
                dateRound = dateLast;
                telemetryFile = lapFiles[lapFiles.length-1];
            }
            if(lastLap == data.getLapNum()){
                //this.commonTelemetryBuffer[this.commonTelemetryBufferPointer] = data;
                CommonTelemetryData[] buffer;
                int pointer;
                if(this.commonTelemetryBuffer.containsKey(data.getDriverName())){
                    buffer = this.commonTelemetryBuffer.get(data.getDriverName());
                    pointer = this.commonTelemetryBufferPointer.get(data.getDriverName()).intValue();
                }
                else {
                    buffer = new CommonTelemetryData[BUFFER_SIZE];
                    pointer = 0;
                }
                buffer[pointer] = data;
                pointer++;
                //this.commonTelemetryBufferPointer++;
                this.commonTelemetryBuffer.put(data.getDriverName(), buffer);
                this.commonTelemetryBufferPointer.put(data.getDriverName(), pointer);
            }
            else {
                try {
                    if(this.commonTelemetryBufferPointer.get(data.getDriverName()) != 0){
                        StfFormatWriter writer = new StfFormatWriter(telemetryFile,"ownTelemetry");
                        CommonTelemetryData[] buffer = this.commonTelemetryBuffer.get(data.getDriverName());
                        int pointer = this.commonTelemetryBufferPointer.get(data.getDriverName());
                        for(int i = 0;i<pointer;i++ ){
                            HashMap<String, String> telemetryHashMap = getHashMapFromCommonTelemetryData(buffer[i]);
                            writer.writePropertyClass("ownProperty"+i, telemetryHashMap);
                        }
                        writer.closeFile();
                        //this.commonTelemetryBufferPointer = 0;
                        this.commonTelemetryBufferPointer.put(data.getDriverName(),0);
                    }

                    Date now = new Date();
                    String filename = getDateFromDatObj(now) + "_"+ data.getLapNum()+".stf";
                    File lapFile = new File(driverDir, filename);
                    if(!lapFile.createNewFile()){

                    }
                } catch(IOException e){
                    logging.log(Level.WARNING, "Fehler beim Schreiben der eigenen Telemetrie Datei", e);
                }

            }
        }
        else {
            try{
                Date now = new Date();
                String filename = getDateFromDatObj(now) + "_"+ data.getLapNum()+".stf";
                File lapFile = new File(driverDir, filename);
                if(!lapFile.createNewFile()){

                }
            }
            catch(IOException e){
                logging.log(Level.WARNING, "Fehler beim Schreiben der eigenen Telemetrie Datei", e);
            }
            
        }
        
    }
    //files has the format yyyyMMddHHmm-lap.stf
    private int getLapNumFromFilename(String filename){
        String relevantPart = filename.substring(bufferPointer);
        String[] parts = relevantPart.split("-");
        if(parts.length == 2 ){
            try{
                int lap = Integer.parseInt(parts[1]);
                return lap;
            }
            catch(NumberFormatException e){
                return -1;
            }
        }
        return -1;
    }
    private Date getDateFromFilename(String filename){
        String relevantPart = filename.substring(bufferPointer);
        String[] parts = relevantPart.split("-");
        if(parts.length == 2 ) {
            try{
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
                Date ret = format.parse(parts[0]);
                return ret;
            } catch(ParseException e){
                return new Date();
            }
        }
        return new Date();
    }
    private HashMap<String, String> getHashMapFromCommonTelemetryData(CommonTelemetryData data){
        HashMap<String, String> ret = new HashMap();
        ret.put("DriverName", data.getDriverName());
        ret.put("Brake", "" + data.getBrake());
        ret.put("CarIndex", "" + data.getCarIndex());
        ret.put("CurrentTime", "" + data.getCurrentTime());
        ret.put("Distance", "" + data.getDistance());
        ret.put("Gear", "" + data.getGear());
        ret.put("LapNum", "" + data.getLapNum());
        ret.put("PosX", "" + data.getPos().getX());
        ret.put("PosY", "" + data.getPos().getY());
        ret.put("PosZ", "" + data.getPos().getZ());
        ret.put("Rpm", "" + data.getRpm());
        ret.put("Speed", "" + data.getSpeed());
        ret.put("Throttle", "" + data.getThrottle());
        return ret;
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
}



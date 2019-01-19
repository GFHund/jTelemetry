/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfhund.jtelemetry.fxgui;

import gfhund.jtelemetry.f1y18.AbstractPacket;
import gfhund.jtelemetry.f1y18.CarMotionData;
import gfhund.jtelemetry.f1y18.CarTelemetryData;
import gfhund.jtelemetry.f1y18.Header;
import gfhund.jtelemetry.f1y18.PacketCarTelemetryData;
import gfhund.jtelemetry.f1y18.PacketLapData;
import gfhund.jtelemetry.f1y18.PacketMotionData;
import gfhund.jtelemetry.f1y18.PacketParticipantsData;
import gfhund.jtelemetry.stfFormat.StfFormatWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.zip.ZipOutputStream;
import java.util.zip.ZipEntry;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PhilippGL
 */
public class TelemetryWriter {
    class PlayerValues{
        public byte m_lap;
        public String name;
    }
    
    private StfFormatWriter[] m_playerPosition = new StfFormatWriter[20];
    private StfFormatWriter m_ownTelemetry;
    private PlayerValues[] m_playerValues = new PlayerValues[20];
    private byte m_lap;
    private static final Logger logging = Logger.getLogger(TelemetryWriter.class.getName());
    

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
        try{
            m_ownTelemetry = new StfFormatWriter("./temp/ownTelemetry.stf","ownTelemetry");
            for(int i=0;i<m_playerPosition.length;i++){
               
                m_playerPosition[i] = new StfFormatWriter("./temp/player"+i+".stf", "player"+i);
                m_playerValues[i] = new PlayerValues();
            }
        }
        catch(IOException e){
            logging.log(Level.WARNING, "Fehler beim Anlegen der Telemetrie Datei", e);
        }
        
    }
    
    public void processPackage(AbstractPacket packet){
        if(packet instanceof PacketParticipantsData){
            for(int i=0 ;i<20;i++){
                //this.setPlayerName(i, ((PacketParticipantsData) packet).getParticipant(i).getName());
                this.m_playerValues[i].name = ((PacketParticipantsData) packet).getParticipant(i).getName();
            }
        }else if(packet instanceof PacketLapData){
            for(int i=0;i<20;i++){
               this.m_playerValues[i].m_lap = ((PacketLapData) packet).getLapData(i).getCurrentLapNum();
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
                
                mapping.put("worldVelocityX", ""+motionData.getWorldVelocityX());
                mapping.put("worldVelocityY", ""+motionData.getWorldVelocityY());
                mapping.put("worldVelocityZ", ""+motionData.getWorldVelocityZ());
                
                mapping.put("worldForwardDirX", ""+motionData.getWorldForwardDirX());
                mapping.put("worldForwardDirY", ""+motionData.getWorldForwardDirY());
                mapping.put("worldForwardDirZ", ""+motionData.getWorldForwardDirZ());
                
                mapping.put("worldRightDirX", ""+motionData.getWorldRightDirX());
                mapping.put("worldRightDirY", ""+motionData.getWorldRightDirY());
                mapping.put("worldRightDirZ",""+motionData.getWorldRightDirZ());
                try{
                    this.m_playerPosition[i].writePropertyClass("carMotionData", mapping);
                }
                catch(IOException e){
                    logging.log(Level.WARNING, "Fehler beim Schreiben der Telemetrie Datei von Spieler "+i, e);
                }
                
            }
        }
        else if( packet instanceof PacketCarTelemetryData){
            int playerIndex = ((PacketCarTelemetryData) packet).getHeader().getPlayerCarIndex();
            if(playerIndex >= 0 && playerIndex<20){
                HashMap<String,String> mapping = new HashMap<>();
                mapping.put("lap", ""+this.m_playerValues[playerIndex].m_lap);
                mapping.put("sessionUid", ""+((PacketCarTelemetryData) packet).getHeader().getSessionUid());
                mapping.put("sessionTime", ""+((PacketCarTelemetryData) packet).getHeader().getSessionTime());
                
                CarTelemetryData telemetry = ((PacketCarTelemetryData) packet).getCarTelemetryData(playerIndex);
                
                mapping.put("speed", ""+telemetry.getSpeed());
                mapping.put("throttle", ""+telemetry.getThrottle());
                mapping.put("brake", ""+telemetry.getBrake());
                mapping.put("steer", ""+telemetry.getSteer());
                mapping.put("clutch", ""+telemetry.getClutch());
                mapping.put("rpm", ""+telemetry.getEngineRPM());
                mapping.put("gear", ""+telemetry.getGear());
                
                mapping.put("brakeTemperatureRL",""+telemetry.getBrakeTemperature(0));
                mapping.put("brakeTemperatureRR",""+telemetry.getBrakeTemperature(1));
                mapping.put("brakeTemperatureFL",""+telemetry.getBrakeTemperature(2));
                mapping.put("brakeTemperatureFR",""+telemetry.getBrakeTemperature(3));
                
                mapping.put("tyresSurfaceTemperatureRL",""+telemetry.getTyreSurfaceTemperature(0));
                mapping.put("tyresSurfaceTemperatureRR",""+telemetry.getTyreSurfaceTemperature(1));
                mapping.put("tyresSurfaceTemperatureFL",""+telemetry.getTyreSurfaceTemperature(2));
                mapping.put("tyresSurfaceTemperatureFR",""+telemetry.getTyreSurfaceTemperature(3));
                
                mapping.put("tyresInnerTemperatureRL",""+telemetry.getTyreInnerTemperature(0));
                mapping.put("tyresInnerTemperatureRR",""+telemetry.getTyreInnerTemperature(1));
                mapping.put("tyresInnerTemperatureFL",""+telemetry.getTyreInnerTemperature(2));
                mapping.put("tyresInnerTemperatureFR",""+telemetry.getTyreInnerTemperature(3));
                
                mapping.put("tyresPressureRL",""+telemetry.getTyrePressure(0));
                mapping.put("tyresPressureRR",""+telemetry.getTyrePressure(1));
                mapping.put("tyresPressureFL",""+telemetry.getTyrePressure(2));
                mapping.put("tyresPressureFR",""+telemetry.getTyrePressure(3));
                
                try{
                    this.m_ownTelemetry.writePropertyClass("ownProperty", mapping);
                }
                catch(IOException e){
                    logging.log(Level.WARNING, "Fehler beim Schreiben der eigenen Telemetrie Datei", e);
                }
            }
        }
    }
    
    public void closeTelemetry(File file){
        try{
            for(int i=0 ;i<20;i++){
                m_playerPosition[i].closeFile();
            }
            this.m_ownTelemetry.closeFile();
        }catch(IOException e){
            logging.log(Level.WARNING, "Fehler beim schließen der Telemetrie Datei", e);
            return;
        }
        //"./temp/player"+i+".stf"
        File ownTelemetry = new File("./temp/ownTelemetry.stf");
        File[] playerPosition = new File[20];
        for(int i=0;i<20;i++){
            playerPosition[i] = new File("./temp/player"+i+".stf");
        }
        try{
            ZipOutputStream zipStream = new ZipOutputStream(Files.newOutputStream(file.toPath()));
            ZipEntry ownEntry = new ZipEntry("ownTelemetry.stf");
            zipStream.putNextEntry(ownEntry);
            FileInputStream ownInputStream = new FileInputStream(ownTelemetry);
            DataInputStream ownDataInputStream = new DataInputStream(ownInputStream);
            while(true){
                try{
                    byte data = ownDataInputStream.readByte();
                    zipStream.write(data);
                }catch(EOFException f){
                    break;
                }
                
            }
            for(int i=0;i<20;i++){
                ZipEntry playerEntry = new ZipEntry("player"+i+".stf");
                zipStream.putNextEntry(playerEntry);
                FileInputStream playerInputStream = new FileInputStream(playerPosition[i]);
                DataInputStream playerDataInputStream = new DataInputStream(playerInputStream);
                while(true){
                    try{
                        byte data = playerDataInputStream.readByte();
                        zipStream.write(data);
                    }catch(EOFException f){
                        break;
                    }
                }
            }
            zipStream.close();
        }catch(IOException e){
            logging.log(Level.WARNING, "Fehler beim Schreiben der eigenen Telemetrie Datei", e);
        }
        
    }
    public void closeTelemetry(String zipFilename){
        File file = new File(zipFilename);
        this.closeTelemetry(file);
    }
}

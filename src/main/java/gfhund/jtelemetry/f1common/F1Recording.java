/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfhund.jtelemetry.f1common;

import gfhund.jtelemetry.Vector3D;
import gfhund.jtelemetry.commontelemetry.AbstractPacket;
import gfhund.jtelemetry.commontelemetry.CommonTelemetryData;

import gfhund.jtelemetry.f1y19.F1Y2019ParseThread;

import gfhund.jtelemetry.network.GameNetworkConnection;
import gfhund.jtelemetry.network.ReceiveEvent;
import gfhund.jtelemetry.f1common.F1ParseThread;

import gfhund.jtelemetry.f1y18.F1Y2018ParseThread;
import io.reactivex.BackpressureStrategy;

import java.util.concurrent.locks.ReentrantLock;
import javafx.scene.control.Alert;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;

/**
 *
 * @author PhilippHolzmann
 */
public class F1Recording {
    private Thread m_f1Thread;
    private GameNetworkConnection m_networkThread;
    private CommonTelemetryData[] currentData = new CommonTelemetryData[20];
    private boolean recordingStarted = false;
    
    public F1Recording(){
        for(int i=0;i<currentData.length;i++){
            currentData[i] = new CommonTelemetryData();
        }
    }
    
    //private Queue<AbstractPacket> m_packetQueue = new Queue<AbstractPacket>();
    
    public Flowable<CommonTelemetryData> startRecording(F1Games game){
        
        Flowable<CommonTelemetryData> source;
        
        source = Flowable.create(new FlowableOnSubscribe<CommonTelemetryData>() {
            @Override
            public void subscribe(FlowableEmitter<CommonTelemetryData> fe) throws Exception {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                ReentrantLock lock = new ReentrantLock();
                java.util.concurrent.locks.Condition cond = lock.newCondition();
                m_networkThread = new GameNetworkConnection(lock,cond,20777,1341);
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
                /*
                if(m_f1Thread.isAlive()){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Thread not started");
                    alert.setHeaderText(null);
                    alert.setContentText("Thread not started because it is already started");
                    alert.showAndWait();
                    return;
                }
                */
                parseThread.addParseResultEvent(new F1ParseResultEvent() {
                    @Override
                    public void resultEvent(AbstractPacket packet) {
                        //parsePackager(packet);
                        if(processPacket(packet)){
                            for(CommonTelemetryData data: currentData){
                                fe.onNext(data);
                            }
                            
                        }
                        
                    }
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
                recordingStarted = true;
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
    }
    
    protected boolean processPacket(AbstractPacket packet){
        if(packet instanceof gfhund.jtelemetry.f1y18.PacketMotionData){
            gfhund.jtelemetry.f1y18.PacketMotionData motionDataPacket = 
                    (gfhund.jtelemetry.f1y18.PacketMotionData) packet;
            for(int i=0;i<currentData.length;i++){
                float x = motionDataPacket.getCarMotionData(i).getWorldPositionX();
                float y = motionDataPacket.getCarMotionData(i).getWorldPositionX();
                float z = motionDataPacket.getCarMotionData(i).getWorldPositionX();
                currentData[i].setPos(new Vector3D(x,y,z));
                currentData[i].setCarIndex((short)i);
            }
            return false;
            
        } else if(packet instanceof gfhund.jtelemetry.f1y18.PacketSessionData){
            //
        } else if(packet instanceof gfhund.jtelemetry.f1y18.PacketLapData){
            gfhund.jtelemetry.f1y18.PacketLapData lapDataPacket = 
                    (gfhund.jtelemetry.f1y18.PacketLapData) packet;
            for(int i=0;i<currentData.length;i++){
                currentData[i].setDistance(lapDataPacket.getLapData(i).getLapDistance());
                currentData[i].setLapNum(lapDataPacket.getLapData(i).getCurrentLapNum());
                currentData[i].setCurrentTime(lapDataPacket.getLapData(i).getCurrentLapTime());
                currentData[i].setCarIndex((short)i);
            }
            return true;
        } else if(packet instanceof gfhund.jtelemetry.f1y18.PacketEventData) {
            //
        } else if(packet instanceof gfhund.jtelemetry.f1y18.PacketParticipantsData){
            gfhund.jtelemetry.f1y18.PacketParticipantsData participantsDataPacket = 
                    (gfhund.jtelemetry.f1y18.PacketParticipantsData) packet;
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
            for(int i=0;i<currentData.length;i++){
                float x = motionDataPacket.getCarMotionData(i).getWorldPositionX();
                float y = motionDataPacket.getCarMotionData(i).getWorldPositionX();
                float z = motionDataPacket.getCarMotionData(i).getWorldPositionX();
                currentData[i].setPos(new Vector3D(x,y,z));
                currentData[i].setCarIndex((short)i);
            }
            return true;
        } else if(packet instanceof gfhund.jtelemetry.f1y19.PacketSessionData) {
            //
        } else if(packet instanceof gfhund.jtelemetry.f1y19.PacketLapData) {
            gfhund.jtelemetry.f1y19.PacketLapData lapDataPacket = 
                    (gfhund.jtelemetry.f1y19.PacketLapData) packet;
            for(int i=0;i<currentData.length;i++){
                currentData[i].setDistance(lapDataPacket.getLapData(i).getLapDistance());
                currentData[i].setLapNum(lapDataPacket.getLapData(i).getCurrentLapNum());
                currentData[i].setCurrentTime(lapDataPacket.getLapData(i).getCurrentLapTime());
                currentData[i].setCarIndex((short)i);
            }
            return true;
        } else if(packet instanceof gfhund.jtelemetry.f1y19.PacketEventData){
            //
        } else if(packet instanceof gfhund.jtelemetry.f1y19.PacketParticipantsData){
            gfhund.jtelemetry.f1y19.PacketParticipantsData participantsDataPacket = 
                    (gfhund.jtelemetry.f1y19.PacketParticipantsData) packet;
            for(int i=0;i<currentData.length;i++){
                currentData[i].setDriverName(participantsDataPacket.getParticipant(i).getName());
                currentData[i].setCarIndex((short)i);
            }
            return false;
        } else if(packet instanceof gfhund.jtelemetry.f1y19.PacketCarSetupData){
            //
        } else if(packet instanceof gfhund.jtelemetry.f1y19.PacketCarTelemetryData) {
            gfhund.jtelemetry.f1y19.PacketCarTelemetryData telemetryDataPacket = 
                    (gfhund.jtelemetry.f1y19.PacketCarTelemetryData) packet;
            for(int i=0;i<currentData.length;i++){
                currentData[i].setSpeed(telemetryDataPacket.getCarTelemetryData(i).getSpeed());
                currentData[i].setBrake(telemetryDataPacket.getCarTelemetryData(i).getBrake());
                currentData[i].setThrottle(telemetryDataPacket.getCarTelemetryData(i).getThrottle());
                currentData[i].setRpm(telemetryDataPacket.getCarTelemetryData(i).getEngineRPM());
                currentData[i].setGear(telemetryDataPacket.getCarTelemetryData(i).getGear());
                currentData[i].setCarIndex((short)i);
            }
            return false;
        } else if(packet instanceof gfhund.jtelemetry.f1y19.PacketCarStatusData){
            //
        }
        return false;
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

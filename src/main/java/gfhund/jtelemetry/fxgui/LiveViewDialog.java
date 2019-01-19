/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfhund.jtelemetry.fxgui;

import gfhund.jtelemetry.f1y18.AbstractPacket;
import gfhund.jtelemetry.f1y18.LapData;
import gfhund.jtelemetry.f1y18.PacketCarStatusData;
import gfhund.jtelemetry.f1y18.PacketLapData;
import gfhund.jtelemetry.f1y18.PacketParticipantsData;
import gfhund.jtelemetry.f1y18.PacketSessionData;
import gfhund.jtelemetry.network.F1Y2018ParseResultEvent;
import gfhund.jtelemetry.network.F1Y2018ParseThread;
import gfhund.jtelemetry.network.GameNetworkConnection;
import gfhund.jtelemetry.network.ReceiveEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


/**
 *
 * @author PhilippGL
 */
public class LiveViewDialog extends DialogFx{

    private TelemetryWriter m_writer;
    
    public class PlayerObj{
        public String m_name;
        public byte m_index;
        public float m_time;
        public byte m_position;
    }
    
    private Thread m_f1y18Thread;
    private GameNetworkConnection m_networkThread;
    
    private PlayerObj[] m_plyObj;
    private Rectangle[] m_wheel;
    private Text[] m_wheelWear;
    private Text[] m_wheelLapsTo70;
    private Text[] m_playerName;
    private Text[] m_playerTime;
    private Text[] m_playerPosition;
    
    private byte m_frontRightTyreWear=0;
    private byte m_frontLeftTyreWear=0;
    private byte m_rearRightTyreWear=0;
    private byte m_rearLeftTyreWear=0;
    
    
    private boolean m_updateDeltaFrontRightTyre = false;
    private boolean m_updateDeltaFrontLeftTyre = false;
    private boolean m_updateDeltaRearRightTyre = false;
    private boolean m_updateDeltaRearLeftTyre = false;
    
    private byte m_deltaFrontRightTyre = 0;
    private byte m_deltaFrontLeftTyre = 0;
    private byte m_deltaRearRightTyre = 0;
    private byte m_deltaRearLeftTyre = 0;
    
    
    private byte m_frontRightTyreLastRound = -1;
    private byte m_frontLeftTyreLastRound = -1;
    private byte m_rearRightTyreLastRound = -1;
    private byte m_rearLeftTyreLastRound = -1;
    
    private float m_fuelInTank;
    private float m_deltaFuel;
    private boolean m_updateFuel = false;
    private float m_fuelInTankLastRound = -1;
    private Text m_textfuelInTank;
    private Text m_lapsRemainingFuel;
    
    private Text m_textCurrentLap;
    private byte m_currentLapNum;
    private byte m_maxLaps = -1;
    
    
    private Button recordingStart;
    private Button recordingStop;
    private Boolean isRecording;
    
    public LiveViewDialog(Stage parent){
        super(parent);
    }
    
    @Override
    public Scene init(Stage stage) {
        /*
        Rectangle[] rect = new Rectangle[20];
        for(int i= 0;i<20;i++){
            rect[i] = new Rectangle(i*51,100,51.0,51.0);
            rect[i].setFill(Color.AQUA);
        }
*/
        HBox box = new HBox();
        box.setTranslateX(400.0f);
        recordingStart = new Button("Start Recording");
        //recordingStart.setTranslateX(400.0f);
        recordingStart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                startRecording(stage);
            }
        });
        recordingStop = new Button("Stop Recording");
        recordingStop.setDisable(true);
        recordingStop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stopRecording(stage);
            }
        });
        box.getChildren().addAll(recordingStart,recordingStop);
        
        double scaleFactor = 50;//50;
        Scale scale = new Scale(scaleFactor,scaleFactor);
        Scale textScale = new Scale(2,2);
        Translate tranlate = new Translate(500, 500);
        
        
        // Car Values
        Polygon car = new Polygon();
        Double[] points = {
            0.5,2.0,
            1.0,2.0,
            1.0,2.5,
            
            -1.0,2.5,
            -1.0,2.0,
            -0.5,2.0,
            
            -0.5,-2.0,
            -1.0,-2.0,
            -1.0,-2.5,
            
            1.0,-2.5,
            1.0,-2.0,
            0.5,-2.0,
        };
        
        for(int i=0;i<points.length;i+=2){
            points[i] *= 50;
            points[i+1] *= 50;
            //points[i] += 300;
            //points[i+1] += 300;
        }

        car.getPoints().addAll(points);
        car.setFill(Color.DARKGRAY);
        if(this.m_wheelWear == null){
            this.m_wheelWear = new Text[4];
        }
        this.m_wheelWear[0] = new Text(-3.0*scaleFactor,2.00*scaleFactor,"RL");//rear Left
        this.m_wheelWear[1] = new Text(2.5*scaleFactor,2.00*scaleFactor,"RR");//rear Right
        this.m_wheelWear[2] = new Text(-3.0*scaleFactor,-2.60*scaleFactor,"FL");//Front Left
        this.m_wheelWear[3] = new Text(2.5*scaleFactor, -2.60*scaleFactor, "FR");//Front Right
        
        this.m_wheelWear[0].setFont(new Font(30));
        this.m_wheelWear[1].setFont(new Font(30));
        this.m_wheelWear[2].setFont(new Font(30));
        this.m_wheelWear[3].setFont(new Font(30));
        /*
        this.m_wheelWear[0].getTransforms().add(textScale);
        this.m_wheelWear[1].getTransforms().add(textScale);
        this.m_wheelWear[2].getTransforms().add(textScale);
        this.m_wheelWear[3].getTransforms().add(textScale);
        */
        
        if(this.m_wheel == null){
            this.m_wheel = new Rectangle[4];
        }
        m_wheel[0] = new Rectangle(-2.0,1.75,1.0,1.0);
        m_wheel[1] = new Rectangle(-2.0,-2.75,1.0,1.0);
        m_wheel[2] = new Rectangle(1.0,1.75,1.0,1.0);
        m_wheel[3] = new Rectangle(1.0,-2.75,1.0,1.0);
        
        m_wheel[0].getTransforms().add(scale);
        m_wheel[1].getTransforms().add(scale);
        m_wheel[2].getTransforms().add(scale);
        m_wheel[3].getTransforms().add(scale);
        for (Rectangle m_wheel1 : m_wheel) {
            m_wheel1.setFill(Color.GREEN);
        }
        m_wheelLapsTo70 = new Text[4];
        m_wheelLapsTo70[0] = new Text(-3.0*scaleFactor,2.80*scaleFactor,"-");
        m_wheelLapsTo70[1] = new Text(-3.0*scaleFactor,-1.80*scaleFactor,"-");
        m_wheelLapsTo70[2] = new Text(2.5*scaleFactor,2.80*scaleFactor,"-");
        m_wheelLapsTo70[3] = new Text(2.5*scaleFactor,-1.80*scaleFactor,"-");
        for (Text m_wheelLapsTo701 : m_wheelLapsTo70) {
            m_wheelLapsTo701.setFont(new Font(30));
        }
        
        Group wheelGrp = new Group();
        wheelGrp.getChildren().addAll(m_wheel);
        wheelGrp.getChildren().addAll(m_wheelWear);
        wheelGrp.getChildren().addAll(m_wheelLapsTo70);
        
        Group carGrp = new Group();
        carGrp.getChildren().add(car);
        carGrp.getChildren().add(wheelGrp);
        carGrp.getTransforms().addAll(tranlate);//scale,
        
        //Player Data
        Group playerGroup = new Group();
        
        if(this.m_plyObj == null){
            this.m_plyObj = new PlayerObj[20];
        }
        for(int i = 0;i<20;i++){
            this.m_plyObj[i] = new PlayerObj();
            this.m_plyObj[i].m_position = (byte)(i+1);
        }
        
        if(this.m_playerName == null){
            this.m_playerName = new Text[20];
        }
        for(int i=0;i<20;i++){
            this.m_playerName[i] = new Text(100, 50+i*35, "player"+i);
        }
        playerGroup.getChildren().addAll(this.m_playerName);
        
        if(this.m_playerTime == null){
            this.m_playerTime = new Text[20];
        }
        for(int i=0;i<20;i++){
            this.m_playerTime[i] = new Text(200, 50+i*35, "Zeit");
        }
        playerGroup.getChildren().addAll(this.m_playerTime);
        
        if(this.m_playerPosition == null){
            this.m_playerPosition = new Text[20];
        }
        for(int i=0;i<20;i++){
            this.m_playerPosition[i] = new Text(50, 50+i*35, ""+(i+1));
            
        }
        playerGroup.getChildren().addAll(this.m_playerPosition);
        
        m_lapsRemainingFuel = new Text(850,650,"-");
        m_lapsRemainingFuel.setFont(new Font(30));
        
        m_textfuelInTank = new Text(850,600,"-");
        m_textfuelInTank.setFont(new Font(30));
        
        m_textCurrentLap= new Text(850,100,"-");
        m_textCurrentLap.setFont(new Font(30));
        
        Group grp = new Group();
        grp.getChildren().add(carGrp);
        grp.getChildren().add(playerGroup);
        grp.getChildren().add(m_lapsRemainingFuel);
        grp.getChildren().add(m_textfuelInTank);
        grp.getChildren().add(m_textCurrentLap);
        grp.getChildren().add(box);
        
        Scene scene = new Scene(grp,1024,768);
        return scene;
    }
    
    public void startRecording(Stage stage){
        Alert writeFileDialog = new Alert(Alert.AlertType.CONFIRMATION);
        writeFileDialog.setTitle("File Write");
        writeFileDialog.setHeaderText("File Write Dialog");
        writeFileDialog.setContentText("Do you want the record the session?");
        ButtonType buttonYes = new ButtonType("Yes");
        ButtonType buttonNo = new ButtonType("No");
        writeFileDialog.getButtonTypes().setAll(buttonYes,buttonNo);
        isRecording = false;
        Optional<ButtonType> resultWriteFileDialog = writeFileDialog.showAndWait();
        if(resultWriteFileDialog.get() == buttonYes){
            isRecording = true;
        }
        else if(resultWriteFileDialog.get() == buttonNo){
            isRecording = false;
        }
        
        List<String> choices = new ArrayList<>();
        String f1y18 = "Formel1 2018";
        String pc2 = "Project Cars 2";
        choices.add(f1y18);
        choices.add(pc2);
        ChoiceDialog<String> dialog = new ChoiceDialog<>(f1y18,choices);
        dialog.setTitle("Select Game");
        dialog.setContentText("Select Game which you want to record");
        Optional<String> result = dialog.showAndWait();
        if(result.isPresent()){
            this.recordingStart.setDisable(true);
            this.recordingStop.setDisable(false);
            String resultValue = result.get();
            ReentrantLock lock = new ReentrantLock();
            java.util.concurrent.locks.Condition cond = lock.newCondition();
            m_networkThread = new GameNetworkConnection(lock,cond,20777,1341);
            if(resultValue.equals(f1y18)){
                F1Y2018ParseThread parseThread = new F1Y2018ParseThread(lock,cond);
                if(m_f1y18Thread == null){
                    m_f1y18Thread = new Thread(parseThread);
                }
                if(m_f1y18Thread.isAlive()){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Thread not started");
                    alert.setHeaderText(null);
                    alert.setContentText("Thread not started because it is already started");
                    alert.showAndWait();
                    return;
                }
                parseThread.addParseResultEvent(new F1Y2018ParseResultEvent() {
                    @Override
                    public void resultEvent(AbstractPacket packet) {
                        parsePackager(packet);
                    }
                });
                if(isRecording){
                    this.m_writer = new TelemetryWriter();
                    parseThread.addParseResultEvent(new F1Y2018ParseResultEvent() {
                        @Override
                        public void resultEvent(AbstractPacket packet) {
                            m_writer.processPackage(packet);
                        }
                    });
                }
                m_networkThread.addReciveEvent(new ReceiveEvent(){
                    @Override
                    public void onReceive(byte[] data){
                        //System.out.println("Übergebe einem Consumer Thread");
                        parseThread.addRaw(data);
                    }
                });
                m_f1y18Thread.start();
                m_networkThread.start();
            }
        }
    }
    
    public void parsePackager(AbstractPacket packet){
        System.out.println("recived package");
        if(packet instanceof PacketLapData){
            for(int i=0;i<20;i++){
                LapData data = ((PacketLapData) packet).getLapData(i);
                this.setPlayerTime(i, data.getLastLapTime());
                this.setPlayerPosition(i, data.getCarPosition());
            }
            int playerCarIndex = ((PacketLapData) packet).getHeader().getPlayerCarIndex();
            this.setLapNum(((PacketLapData) packet).getLapData(playerCarIndex).getCurrentLapNum());
        }
        else if(packet instanceof PacketCarStatusData){
            int playerCarIndex = ((PacketCarStatusData) packet).getHeader().getPlayerCarIndex();
            byte[] tyreWear = ((PacketCarStatusData) packet).getCarStatusData(playerCarIndex).getTyresWear();
            this.setRLTyreWear(tyreWear[0]);
            this.setRRTyreWear(tyreWear[1]);
            this.setFLTyreWear(tyreWear[2]);
            this.setFRTyreWear(tyreWear[3]);
            this.setFuel(((PacketCarStatusData) packet).getCarStatusData(playerCarIndex).getFuelInTank());
        }else if(packet instanceof PacketParticipantsData){
            for(int i=0 ;i<20;i++){
                this.setPlayerName(i, ((PacketParticipantsData) packet).getParticipant(i).getName());
            }
        }else if(packet instanceof PacketSessionData){
            this.setMaxLapNum(((PacketSessionData) packet).getTotalLaps());
        }
    }
    
    public void stopRecording(Stage stage){
        this.recordingStart.setDisable(false);
        this.recordingStop.setDisable(true);
        
        m_f1y18Thread.interrupt();
        m_networkThread.interrupt();
        
        if(this.isRecording){
            FileChooser fileDialog = new FileChooser();
            fileDialog.setTitle("Save Data File");
            File file = fileDialog.showSaveDialog(stage);
            if(file != null){
                this.m_writer.closeTelemetry(file);
            }
        }
    }
    
    /*
    0 - Rear Left
    1 - Rear Right
    2 Front Left
    4 Front Right
    */
    public synchronized void setRLTyreWear(byte rlTyreWear){
        this.m_rearLeftTyreWear = rlTyreWear;
        this.m_wheelWear[0].setText(""+rlTyreWear+"%");
        if(this.m_updateDeltaRearLeftTyre == true){
            this.m_updateDeltaRearLeftTyre = false;
            if(this.m_rearLeftTyreLastRound != -1 && this.m_rearLeftTyreLastRound != 0){
                this.m_deltaRearLeftTyre = (byte)(rlTyreWear - this.m_rearLeftTyreLastRound);
                float lapsToDriveTo70 = ((70 - rlTyreWear) / this.m_deltaRearLeftTyre);
                this.m_wheelLapsTo70[0].setText("" + lapsToDriveTo70);
            }
            this.m_rearLeftTyreLastRound = rlTyreWear;
            
        }
    }
    public synchronized void setRRTyreWear(byte rrTyreWear){
        this.m_rearRightTyreWear = rrTyreWear;
        this.m_wheelWear[1].setText(""+rrTyreWear+"%");
        if(this.m_updateDeltaRearRightTyre == true){
            this.m_updateDeltaRearRightTyre = false;
            if(this.m_rearRightTyreLastRound != -1 && this.m_rearRightTyreLastRound != 0){
                this.m_deltaRearRightTyre = (byte)(rrTyreWear - this.m_rearRightTyreLastRound);
                float lapsToDriveTo70 = ((70 - rrTyreWear) / this.m_deltaRearRightTyre);
                this.m_wheelLapsTo70[1].setText("" + lapsToDriveTo70);
            }
            this.m_rearRightTyreLastRound = rrTyreWear;
        }
    }
    public synchronized void setFLTyreWear(byte flTyreWear){
        this.m_frontLeftTyreWear = flTyreWear;
        this.m_wheelWear[2].setText(""+flTyreWear+"%");
        if(this.m_updateDeltaFrontLeftTyre == true){
            this.m_updateDeltaFrontLeftTyre = false;
            if(this.m_frontLeftTyreLastRound != -1 && this.m_frontLeftTyreLastRound != 0){
                this.m_deltaFrontLeftTyre = (byte)(flTyreWear - this.m_frontLeftTyreLastRound);
                float lapsToDriveTo70 = ((70 - flTyreWear) / this.m_deltaFrontLeftTyre);
                this.m_wheelLapsTo70[2].setText("" + lapsToDriveTo70);
            }
            this.m_frontLeftTyreLastRound = flTyreWear;
        }
    }
    public synchronized void setFRTyreWear(byte frTyreWear){
        this.m_frontRightTyreWear = frTyreWear;
        this.m_wheelWear[3].setText(""+frTyreWear+"%");
        if(this.m_updateDeltaFrontRightTyre == true){
            this.m_updateDeltaFrontRightTyre = false;
            if(this.m_frontRightTyreLastRound != -1 && this.m_frontRightTyreLastRound != 0){
                this.m_deltaFrontRightTyre = (byte)(frTyreWear - this.m_frontRightTyreLastRound);
                float lapsToDriveTo70 = ((70 - frTyreWear) / this.m_deltaFrontRightTyre);
                this.m_wheelLapsTo70[3].setText("" + lapsToDriveTo70);
            }
            this.m_frontRightTyreLastRound = frTyreWear;
        }
    }
    
    public synchronized void setFuel(float fuel){
        this.m_fuelInTank = fuel;
        this.m_textfuelInTank.setText(""+fuel);
        if(this.m_updateFuel){
            this.m_updateFuel = false;
            if(this.m_fuelInTankLastRound != -1 && this.m_fuelInTankLastRound != 0){
                this.m_deltaFuel = this.m_fuelInTankLastRound - fuel;
                float remainingFuel = fuel / this.m_deltaFuel;
                m_lapsRemainingFuel.setText(""+remainingFuel);
            }
            this.m_fuelInTankLastRound = fuel;
        }
    }
    
    public synchronized void setPlayerName(int index,String name){
        this.m_plyObj[index].m_name = name;
        byte playerPos = this.m_plyObj[index].m_position;
        playerPos--;
        if(playerPos >= 0 && playerPos < this.m_playerName.length && name != null){
            this.m_playerName[playerPos].setText(name);
        }
        
    }
    public synchronized void setPlayerTime(int index,float time){
        if(this.m_plyObj == null){
            this.m_plyObj = new PlayerObj[20];
        }
        if(this.m_plyObj[index] == null){
            this.m_plyObj[index] = new PlayerObj();
            this.m_plyObj[index].m_position = (byte)index;
        }
        this.m_plyObj[index].m_time = time;
        byte playerPos = this.m_plyObj[index].m_position;
        if(playerPos > 20 || playerPos <= 0){
            return;
        }
        this.m_playerTime[playerPos-1].setText("" + time);
    }
    public synchronized void setPlayerPosition(int index,byte pos){
        this.m_plyObj[index].m_position = pos;
    }
    
    public synchronized void setLapNum( byte lapNum){
        String displayText = ""+lapNum;
        if(this.m_maxLaps != -1){
            displayText += "/"+this.m_maxLaps;
        }
        m_textCurrentLap.setText(displayText);
        if(this.m_currentLapNum != lapNum){
            this.m_currentLapNum = lapNum;
            this.m_updateDeltaFrontLeftTyre = true;
            this.m_updateDeltaFrontRightTyre = true;
            this.m_updateDeltaRearLeftTyre = true;
            this.m_updateDeltaRearRightTyre = true;
            this.m_updateFuel = true;
        }
    }
    public synchronized void setMaxLapNum(byte lapNum){
        this.m_maxLaps = lapNum;
    }
    
    
    public void onExit(){
        if(m_networkThread != null && m_networkThread.isAlive()){
            m_networkThread.interrupt();
        }
        if(m_f1y18Thread != null && m_f1y18Thread.isAlive()){
            m_f1y18Thread.interrupt();
        }
    }
}

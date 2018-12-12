/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfhund.jtelemetry;

import gfhund.jtelemetry.data.AbstractPackets;
import gfhund.jtelemetry.data.Timing;
import gfhund.jtelemetry.f1y18.AbstractPacket;
import gfhund.jtelemetry.f1y18.F1Y2018Packets;
import gfhund.jtelemetry.f1y18.F1Y2018ParseException;
import gfhund.jtelemetry.f1y18.LapData;
import gfhund.jtelemetry.fxgui.LiveViewDialog;
import gfhund.jtelemetry.fxgui.TimingFx;
import gfhund.jtelemetry.network.F1Y2018ParseResultEvent;
import gfhund.jtelemetry.network.F1Y2018ParseThread;
import gfhund.jtelemetry.network.GameNetworkConnection;
import gfhund.jtelemetry.network.ReceiveEvent;
import gfhund.jtelemetry.f1y18.PacketLapData;
import gfhund.jtelemetry.f1y18.PacketCarStatusData;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Observable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.*;

/**
 *
 * @author PhilippGL
 */
public class JavaFxMain extends Application{
    private ObservableList<TimingFx> m_timings = FXCollections.observableArrayList();
    private AbstractPackets m_packetManager;
    private TableView<TimingFx> table = new TableView<>();
    private Thread m_f1y18Thread;
    private GameNetworkConnection m_networkThread;
    private static LiveViewDialog m_liveViewDialog;
    private MenuItem m_startRecordMenuItem;
    private MenuItem m_stopRecordMenuItem;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("File");
        Menu menuView = new Menu("View");
        Menu menuRecord = new Menu("Recording");
        menuBar.getMenus().addAll(menuFile,menuView,menuRecord);
        MenuItem openMenuItem = new MenuItem("Open");
        openMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                fileOpenAction(primaryStage);
                //System.out.println("drin");
            }
        });
        
        MenuItem liveViewMenuItem = new MenuItem("Live View");
        liveViewMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                startLiveViewDialog(primaryStage);
            }
        });
        
        m_startRecordMenuItem = new MenuItem("Start");
        m_startRecordMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                startRecording(primaryStage);
            }
        });
        m_stopRecordMenuItem = new MenuItem("Stop");
        m_stopRecordMenuItem.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                stopRecording(primaryStage);
            }
        });
        m_stopRecordMenuItem.setDisable(true);
        menuFile.getItems().add(openMenuItem);
        menuView.getItems().add(liveViewMenuItem);
        menuRecord.getItems().addAll(m_startRecordMenuItem,m_stopRecordMenuItem);
        
        VBox layout = new VBox();
        
        Scene scene = new Scene(layout,400,300);

        
        
        HBox firstRow = new HBox();
        //LineChart<float,float> diagramm = new LineChart<>();
        HBox secondRow = new HBox();
        
        table.setEditable(true);
        
        TableColumn lapTime = new TableColumn("Lap Time");
        lapTime.setCellValueFactory(new PropertyValueFactory<Timing,Float>("lapTime"));
        
        TableColumn sector1Time = new TableColumn("Sector 1");
        sector1Time.setCellValueFactory(new PropertyValueFactory<Timing,Float>("sector1Time"));
        
        TableColumn sector2Time = new TableColumn("Sector 2");
        sector2Time.setCellValueFactory(new PropertyValueFactory<Timing,Float>("sector2Time"));
        
        table.setItems(m_timings);
        table.getColumns().addAll(lapTime,sector1Time,sector2Time);
        secondRow.getChildren().add(table);
        layout.getChildren().addAll(menuBar,firstRow,secondRow);
        
        //layout.a
        //ObservableList<Timing> 
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                if(m_networkThread.isAlive()){
                    m_networkThread.interrupt();
                }
                if(m_f1y18Thread.isAlive()){
                    m_f1y18Thread.interrupt();
                }
            }
        });
        primaryStage.show();
    }
    public void fileOpenAction(Stage stage){
        FileChooser fileDialog = new FileChooser();
        fileDialog.setTitle("Open Data File");
        File file = fileDialog.showOpenDialog(stage);
        //JFileChooser fileDialog = new JFileChooser();
        //fileDialog.setFileFilter(new FileNameExtensionFilter("Formel1 2018",".f1data"));
        //int state = fileDialog.showOpenDialog(null);
        if(file!=null){
            //File file = fileDialog.getSelectedFile();
            long fileSize = file.length();
            if(fileSize<=0){
                //JOptionPane.showMessageDialog(null, "Datei ist kleiner gleich 0", "Falsche Datei größe", JOptionPane.ERROR_MESSAGE);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Falsche Datei größe");
                alert.setHeaderText(null);
                alert.setContentText("Datei ist kleiner gleich 0");
                alert.showAndWait();
                return;
            }
            String sFileContent = "";
            try{
                byte[] fileContent = Files.readAllBytes(file.toPath());
                sFileContent = new String(fileContent);//, Charset.forName("ascii")
            }catch(IOException e){
                //JOptionPane.showMessageDialog(null, "Cannot read File. Reason:"+e.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("File Error");
                alert.setHeaderText(null);
                alert.setContentText("Cannot read File. Reason: "+e.getMessage());
                alert.showAndWait();
                return;
            }
            if(sFileContent.isEmpty()){
                //JOptionPane.showMessageDialog(null, "Cannot read File. Reason: "+"File is empty", "File Error", JOptionPane.ERROR_MESSAGE);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("File Error");
                alert.setHeaderText(null);
                alert.setContentText("Cannot read File. Reason: "+"File is empty");
                alert.showAndWait();
                return;
            }
            try{
                m_packetManager = F1Y2018Packets.parse(sFileContent);
                //this.m_timings.addAll(m_packetManager.getTimings());
                Timing[] timings = m_packetManager.getTimings();
                for(Timing time:timings){
                    System.out.println("Daten Insert");
                    TimingFx timingfx = new TimingFx(time);
                    this.m_timings.add(timingfx);
                }
                System.out.println("Daten Insert");
            }catch(F1Y2018ParseException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Parse Error");
                alert.setHeaderText(null);
                alert.setContentText("Parse Error. Reason: "+e.getMessage());
                alert.showAndWait();
                return;
            }
        }
    }
    
    public void startRecording(Stage stage){
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
                        //System.out.println("Recived packages");
                        if(m_liveViewDialog == null){
                            return;
                        }
                        /*
                        0 - Rear Left
                        1 - Rear Right
                        2 Front Left
                        4 Front Right
                        */
                        if(packet instanceof PacketLapData){
                            for(int i=0;i<20;i++){
                                LapData data = ((PacketLapData) packet).getLapData(i);
                                m_liveViewDialog.setPlayerTime(i, data.getLastLapTime());
                                m_liveViewDialog.setPlayerPosition(i, data.getCarPosition());
                            }
                        }
                        else if(packet instanceof PacketCarStatusData){
                            int playerCarIndex = ((PacketCarStatusData) packet).getHeader().getPlayerCarIndex();
                            byte[] tyreWear = ((PacketCarStatusData) packet).getCarStatusData(playerCarIndex).getTyresWear();
                            m_liveViewDialog.setRLTyreWear(tyreWear[0]);
                            m_liveViewDialog.setRRTyreWear(tyreWear[1]);
                            m_liveViewDialog.setFLTyreWear(tyreWear[2]);
                            m_liveViewDialog.setFRTyreWear(tyreWear[3]);
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
                m_f1y18Thread.start();
                m_networkThread.start();
                
                m_stopRecordMenuItem.setDisable(false);
                m_startRecordMenuItem.setDisable(true);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Recording started");
                alert.setHeaderText(null);
                alert.setContentText("Recording is started");
                alert.showAndWait();
            }
            else{
                m_stopRecordMenuItem.setDisable(false);
                m_startRecordMenuItem.setDisable(true);
            }
        }
        
    }
    
    public void stopRecording(Stage stage){
        m_f1y18Thread.interrupt();
        m_networkThread.interrupt();
        m_stopRecordMenuItem.setDisable(true);
        m_startRecordMenuItem.setDisable(false);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Recording stop");
        alert.setHeaderText(null);
        alert.setContentText("Recording is stopped");
        alert.showAndWait();
                    
    }
    
    public void startLiveViewDialog(Stage stage){
        m_liveViewDialog = new LiveViewDialog(stage);
    }
}

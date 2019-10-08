/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfhund.jtelemetry;

import gfhund.jtelemetry.data.AbstractPackets;
import gfhund.jtelemetry.data.Timing;
import gfhund.jtelemetry.commontelemetry.AbstractPacket;
import gfhund.jtelemetry.commontelemetry.CommonLapManager;
import gfhund.jtelemetry.commontelemetry.CommonTelemetryData;
import gfhund.jtelemetry.commontelemetry.LapIdentificationObject;
import gfhund.jtelemetry.f1common.F1Recording;
import gfhund.jtelemetry.f1y18.F1Y2018Packets;
import gfhund.jtelemetry.f1y18.F1Y2018ParseException;
import gfhund.jtelemetry.f1y18.LapData;
import gfhund.jtelemetry.fxgui.LiveViewDialog;
import gfhund.jtelemetry.fxgui.TimingFx;
import gfhund.jtelemetry.f1y18.F1Y2018ParseThread;
import gfhund.jtelemetry.network.GameNetworkConnection;
import gfhund.jtelemetry.network.ReceiveEvent;
import gfhund.jtelemetry.f1y18.PacketLapData;
import gfhund.jtelemetry.f1y18.PacketCarStatusData;
import gfhund.jtelemetry.f1y18.PacketParticipantsData;
import gfhund.jtelemetry.f1y18.PacketSessionData;
import gfhund.jtelemetry.fxgui.TelemetryReader;
import gfhund.jtelemetry.fxgui.TrackView;
import gfhund.jtelemetry.fxgui.DiagramView;
import gfhund.jtelemetry.fxgui.DiagramViewGroup;
import gfhund.jtelemetry.fxgui.FinishEvent;
import gfhund.jtelemetry.fxgui.LoadingBarDialog;
import gfhund.jtelemetry.fxgui.ProgressEvent;
import gfhund.jtelemetry.fxgui.TelemetryWriter;
import gfhund.jtelemetry.fxgui.FileOpenDialog;
import gfhund.jtelemetry.fxgui.SettingsDialog;
import gfhund.jtelemetry.stfFormat.AbstractStfObject;
import gfhund.jtelemetry.stfFormat.StfDocument;
import gfhund.jtelemetry.stfFormat.StfClass;
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
import java.lang.Float;
import java.util.HashMap;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;

/**
 *
 * @author PhilippGL
 */
public class JavaFxMain extends Application{
    private ObservableList<TimingFx> m_timings = FXCollections.observableArrayList();
    private ObservableList<String> m_properties = FXCollections.observableArrayList();
    private ObservableList<String> m_sessions = FXCollections.observableArrayList();
    ObservableList<XYChart.Series<Number,Number>> diagramData = FXCollections.observableArrayList();
    ObservableList<TrackView.TrackRound> trackRounds = FXCollections.observableArrayList();
    ObservableList<DiagramView.DiagrammLine> newDiagramData = FXCollections.observableArrayList();
    
    private AbstractPackets m_packetManager;
    private TableView<TimingFx> table = new TableView<>();
    TrackView trackView;
    DiagramViewGroup diaGroup;
    ListView<String> list;
    ListView<String> sessionList;
    
    private Thread m_f1y18Thread;
    private GameNetworkConnection m_networkThread;
    private static LiveViewDialog m_liveViewDialog;
    private HashMap<String,StfDocument> m_documents = new HashMap<String, StfDocument>();
    
    private MenuItem startRecordingMenuItem;
    MenuItem stopRecordingMenuItem;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox layout = new VBox();
        createMenu(primaryStage,layout);
        
        Scene scene = new Scene(layout,1500,700);
        HBox content = new HBox();
        VBox firstColumn = new VBox();
        
        table.setEditable(true);
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        table.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<TimingFx>(){
            @Override
            public void onChanged(ListChangeListener.Change<? extends TimingFx> c){
                selectRounds();
            }
        });
        
        TableColumn playerName = new TableColumn("Player Name");
        playerName.setCellValueFactory(new PropertyValueFactory<Timing,String>("playerName"));
        
        TableColumn lapNum = new TableColumn("Lap Num");
        lapNum.setCellValueFactory(new PropertyValueFactory<Timing,Integer>("lapNum"));
        
        TableColumn lapTime = new TableColumn("Lap Time");
        lapTime.setCellValueFactory(new PropertyValueFactory<Timing,Float>("lapTime"));
        /*
        TableColumn sector1Time = new TableColumn("Sector 1");
        sector1Time.setCellValueFactory(new PropertyValueFactory<Timing,Float>("sector1Time"));
        
        TableColumn sector2Time = new TableColumn("Sector 2");
        sector2Time.setCellValueFactory(new PropertyValueFactory<Timing,Float>("sector2Time"));
*/
        table.setItems(m_timings);
        table.getColumns().add(playerName);
        table.getColumns().add(lapNum);
        table.getColumns().add(lapTime);
        //table.getColumns().addAll(lapNum,lapTime,sector1Time,sector2Time);
        firstColumn.getChildren().add(table);
        
        
        trackView = new TrackView();
        trackView.setData(trackRounds);
        firstColumn.getChildren().add(trackView);
        
        diaGroup = new DiagramViewGroup();
        diaGroup.addOnSelectValueEvent((float value)->{
            selectDataValue(value);
        });
        content.getChildren().addAll(firstColumn,diaGroup);
        layout.getChildren().addAll(content);

        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                if(m_networkThread != null && m_networkThread.isAlive()){
                    m_networkThread.interrupt();
                }
                if(m_f1y18Thread != null && m_f1y18Thread.isAlive()){
                    m_f1y18Thread.interrupt();
                }
            }
        });
        primaryStage.show();
    }
    
    private void createMenu(Stage primaryStage, Pane pane) {
        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("File");
        Menu menuView = new Menu("View");
        Menu recordingView = new Menu("Recording");
        
        menuBar.getMenus().addAll(menuFile,menuView,recordingView);
        MenuItem openMenuItem = new MenuItem("Open");
        openMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                fileOpenAction(primaryStage);
            }
        });
        
        MenuItem settingMenuItem = new MenuItem("Settings");
        settingMenuItem.setOnAction((event)->{
            new SettingsDialog(primaryStage);
        });
        
        MenuItem liveViewMenuItem = new MenuItem("Live View");
        liveViewMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                startLiveViewDialog(primaryStage);
            }
        });
        
        startRecordingMenuItem = new MenuItem("Start Recording");
        startRecordingMenuItem.setOnAction((event)->{
            startRecord();
        });
        stopRecordingMenuItem = new MenuItem("Stop Recording");
        stopRecordingMenuItem.setOnAction((event) -> {
            stopRecord(primaryStage);
        });
        stopRecordingMenuItem.setDisable(true);
        
        menuFile.getItems().addAll(openMenuItem,settingMenuItem);
        menuView.getItems().add(liveViewMenuItem);
        recordingView.getItems().addAll(startRecordingMenuItem,stopRecordingMenuItem);
        
        pane.getChildren().addAll(menuBar);
    }
    
    public void fileOpenAction(Stage stage){
        FileOpenDialog dia = new FileOpenDialog(stage);
        dia.registerSaveHandler(()->{
            CommonLapManager lapManager;
            try{
                lapManager = (CommonLapManager)gfhund.jtelemetry.ClassManager.get(CommonLapManager.class);
            }catch(ClassManager.ClassManagerException e){
                return;
            }
            LapIdentificationObject[] keys = lapManager.getIds();
            for(LapIdentificationObject key: keys){
                TimingFx obj = new TimingFx();
                obj.setLapNum(key.getLapNum());
                obj.setPlayerName(key.getPlayer());
                obj.setLapIdentificationObject(key);
                this.m_timings.add(obj);
            }
        });
        return;
        
    }
    
    public void startLiveViewDialog(Stage stage){
        m_liveViewDialog = new LiveViewDialog(stage);
    }
    
    
    public void selectSession(){
        this.m_timings.clear();
        String selectedItem = sessionList.getSelectionModel().getSelectedItem();
        StfDocument doc = this.m_documents.get("ownTelemetry.stf");
        StfClass rootClass = (StfClass) doc.getChild(0);
        AbstractStfObject[] children = rootClass.getChildren();
        StfClass lastObj = null;
        TimingFx lastTimingFx = null;
        for(AbstractStfObject obj: children){
            if(obj instanceof StfClass){
                String lapNum = ((StfClass) obj).getChildPropertyValue("lap");
                String sessionIdentifier = ((StfClass) obj).getChildPropertyValue("sessionUid");
                String section1Time = ((StfClass) obj).getChildPropertyValue("section1Time");
                String section2Time = ((StfClass) obj).getChildPropertyValue("section2Time");
                String lastLapTime = ((StfClass) obj).getChildPropertyValue("lastLapTime");
                if(selectedItem.equals(sessionIdentifier)){
                    TimingFx newTableEntry = new TimingFx();
                    newTableEntry.setSector1Time(0);
                    if(lastObj != null){
                        String lastLapNum = lastObj.getChildPropertyValue("lap");
                        
                        
                        int iLapNum = Integer.parseInt(lapNum);
                        int iLastLapNum = Integer.parseInt(lastLapNum);
                        
                        if(iLapNum != iLastLapNum){
                            float fLastLapTime = Float.parseFloat(lastLapTime);
                            lastTimingFx.setLapTime(fLastLapTime);
                            try{
                                float fSection1Time = Float.parseFloat(section1Time);
                                float fSection2Time = Float.parseFloat(section2Time);
                                newTableEntry.setSector1Time(fSection1Time);
                                newTableEntry.setSector2Time(fSection2Time);
                            }catch(NumberFormatException e){

                            }
                            newTableEntry.setLapNum(iLapNum);
                            m_timings.add(newTableEntry);
                            lastTimingFx = newTableEntry;
                            lastObj = (StfClass)obj;
                        }
                        else{
                            try{
                                float fSection1Time = Float.parseFloat(section1Time);
                                float fSection2Time = Float.parseFloat(section2Time);
                                lastTimingFx.setSector1Time(fSection1Time);
                                lastTimingFx.setSector2Time(fSection2Time);
                            }catch(NumberFormatException e){

                            }
                        }
                    }
                    else{
                        try{
                            float fSection1Time = Float.parseFloat(section1Time);
                            float fSection2Time = Float.parseFloat(section2Time);
                            newTableEntry.setSector1Time(fSection1Time);
                            newTableEntry.setSector2Time(fSection2Time);
                        }catch(NumberFormatException e){
                            
                        }
                        int iLapNum = Integer.parseInt(lapNum);
                        newTableEntry.setLapNum(iLapNum);
                        m_timings.add(newTableEntry);
                        lastTimingFx = newTableEntry;
                        lastObj = (StfClass)obj;
                    }
                }
                
            }
        }
    }
    
    public void selectRounds(){
        ObservableList<TimingFx> selectedRounds = table.getSelectionModel().getSelectedItems();
        CommonLapManager lapManager;
        try{
            lapManager = (CommonLapManager)gfhund.jtelemetry.ClassManager.get(CommonLapManager.class);
        }catch(ClassManager.ClassManagerException e){
            return;
        }
        this.diaGroup.clearTelemetryData();
        for(TimingFx selectedRows: selectedRounds){
            LapIdentificationObject id = selectedRows.getLapIdentificationObject();
            ArrayList<CommonTelemetryData> telemetryData = lapManager.getLapData(id);
            this.diaGroup.addTelemetryData(telemetryData);
            
            trackRounds.clear();
            TrackView.TrackRound trackRound = new TrackView.TrackRound();
            for(CommonTelemetryData data: telemetryData){
                TrackView.TrackPoint p = new TrackView.TrackPoint(data.getPos().getX(),data.getPos().getZ());
                //System.out.println("X: "+data.getPos().getX());
                //System.out.println("Y: "+data.getPos().getY());
                //System.out.println("Z: "+data.getPos().getZ());
                trackRound.addTrackPoint(p);
            }
            trackRounds.add(trackRound);
        }
        
    }

    public void startRecord(){
        
        List<String> choices = new ArrayList<>();
        String f1y18 = "Formel1 2018";
        String f1y19 = "Formel1 2019";
        String pc2 = "Project Cars 2";
        choices.add(f1y19);
        choices.add(f1y18);
        choices.add(pc2);
        ChoiceDialog<String> dialog = new ChoiceDialog<>(f1y18,choices);
        dialog.setTitle("Select Game");
        dialog.setContentText("Select Game which you want to record");
        Optional<String> result = dialog.showAndWait();
        if(result.isPresent()){
            String resultValue = result.get();
            F1Recording.F1Games game;
            if(resultValue.equals(f1y18)){
                 game = F1Recording.F1Games.F1_2018;
            }
            else{
                game = F1Recording.F1Games.F1_2019;
            }
            try{
               
                F1Recording recording = (F1Recording)gfhund.jtelemetry.ClassManager.get(F1Recording.class);
                TelemetryWriter tw = (TelemetryWriter)gfhund.jtelemetry.ClassManager.get(TelemetryWriter.class);
                
                recording.startRecording(game).subscribe(packet -> {
                    tw.processCommonTelemetryData(packet);
                });
                this.startRecordingMenuItem.setDisable(true);
                this.stopRecordingMenuItem.setDisable(false);
            }
            catch(ClassManager.ClassManagerException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error to get Recording Model");
                alert.setHeaderText(null);
                alert.setContentText("If you see this this should be an bug in this program. Error: "+e.getMessage());
                alert.showAndWait();  
            }
            
        }
    }
    
    public void stopRecord(Stage stage){
        try{
            F1Recording recording = (F1Recording)gfhund.jtelemetry.ClassManager.get(F1Recording.class);
            recording.stopRecording();
            this.startRecordingMenuItem.setDisable(false);
            this.stopRecordingMenuItem.setDisable(true);
        }
        catch(ClassManager.ClassManagerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error to get Recording Model");
            alert.setHeaderText(null);
            alert.setContentText("If you see this this should be an bug in this program. Error: "+e.getMessage());
            alert.showAndWait();  
        }
        
        FileChooser fileDialog = new FileChooser();
        fileDialog.setTitle("Save Data File");
        fileDialog.getExtensionFilters().add(new FileChooser.ExtensionFilter("Zip File", "*.zip"));
        File file = fileDialog.showSaveDialog(stage);
        if(file != null){
            try{
                TelemetryWriter tw = (TelemetryWriter)gfhund.jtelemetry.ClassManager.get(TelemetryWriter.class);
                LoadingBarDialog loadingBar = new LoadingBarDialog(stage);
                tw.addProgressListener(new ProgressEvent() {
                    @Override
                    public void onProgress(float progress) {
                        loadingBar.setValue(progress);
                    }
                });
                tw.addOnFinishListener(new FinishEvent() {
                    @Override
                    public void onFinish() {
                        Platform.runLater(()->{
                            loadingBar.close();
                        });

                    }
                });
                tw.closeTelemetry(file);
            }catch(ClassManager.ClassManagerException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error to get Recording Model");
                alert.setHeaderText(null);
                alert.setContentText("If you see this this should be an bug in this program. Error: "+e.getMessage());
                alert.showAndWait();  
            }
        }
    }
    
    public void selectDataValue(float value){
        ObservableList<TimingFx> selectedRounds = table.getSelectionModel().getSelectedItems();
        CommonLapManager lapManager;
        try{
            lapManager = (CommonLapManager)gfhund.jtelemetry.ClassManager.get(CommonLapManager.class);
        }catch(ClassManager.ClassManagerException e){
            return;
        }
        trackView.clearPoints();
        for(TimingFx selectedRows: selectedRounds){
            LapIdentificationObject id = selectedRows.getLapIdentificationObject();
            CommonTelemetryData data = lapManager.getDataFromDistance(id, value);
            TrackView.TrackPoint p = new TrackView.TrackPoint(data.getPos().getX(),data.getPos().getZ());
            trackView.addPoint(p);
        }
    }
}

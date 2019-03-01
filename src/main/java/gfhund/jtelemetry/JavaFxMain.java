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
import gfhund.jtelemetry.f1y18.PacketParticipantsData;
import gfhund.jtelemetry.f1y18.PacketSessionData;
import gfhund.jtelemetry.fxgui.TelemetryReader;
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
    
    private AbstractPackets m_packetManager;
    private TableView<TimingFx> table = new TableView<>();
    ListView<String> list;
    ListView<String> sessionList;
    
    private Thread m_f1y18Thread;
    private GameNetworkConnection m_networkThread;
    private static LiveViewDialog m_liveViewDialog;
    private HashMap<String,StfDocument> m_documents;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("File");
        Menu menuView = new Menu("View");
        menuBar.getMenus().addAll(menuFile,menuView);
        MenuItem openMenuItem = new MenuItem("Open");
        openMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                fileOpenAction(primaryStage);
            }
        });
        
        MenuItem liveViewMenuItem = new MenuItem("Live View");
        liveViewMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                startLiveViewDialog(primaryStage);
            }
        });
        
        menuFile.getItems().add(openMenuItem);
        menuView.getItems().add(liveViewMenuItem);
        
        VBox layout = new VBox();
        
        Scene scene = new Scene(layout,400,300);

        HBox firstRow = new HBox();
        
        list = new ListView<String>();
        list.setItems(this.m_properties);
        list.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        list.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends String> c) {
                selectRounds();
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        firstRow.getChildren().add(list);
        
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart<Number,Number> diagramm = new LineChart<>(xAxis,yAxis);
        firstRow.getChildren().add(diagramm);
        ObservableList<XYChart.Data<Number,Number>> series = FXCollections.observableArrayList();
        for(int i=0;i<5;i++){
            series.add(new XYChart.Data<Number, Number>(new Float((float)i) ,new Float((float)(i*i))));
        }
        //ObservableList<XYChart.Series<Number,Number>> diagramData = FXCollections.observableArrayList();
        diagramData.add(new LineChart.Series<>(series));
        diagramm.setData(diagramData);
        diagramm.setCreateSymbols(false);
        
        HBox secondRow = new HBox();
        
        sessionList = new ListView<String>();
        sessionList.setItems(this.m_sessions);
        sessionList.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends String> c) {
                selectSession();
            }
        });
        secondRow.getChildren().add(sessionList);
        
        table.setEditable(true);
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        table.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<TimingFx>(){
            @Override
            public void onChanged(ListChangeListener.Change<? extends TimingFx> c){
                selectRounds();
            }
        });
        
        
        TableColumn lapNum = new TableColumn("Lap Num");
        lapNum.setCellValueFactory(new PropertyValueFactory<Timing,Integer>("lapNum"));
        
        TableColumn lapTime = new TableColumn("Lap Time");
        lapTime.setCellValueFactory(new PropertyValueFactory<Timing,Float>("lapTime"));
        
        TableColumn sector1Time = new TableColumn("Sector 1");
        sector1Time.setCellValueFactory(new PropertyValueFactory<Timing,Float>("sector1Time"));
        
        TableColumn sector2Time = new TableColumn("Sector 2");
        sector2Time.setCellValueFactory(new PropertyValueFactory<Timing,Float>("sector2Time"));
        
        //m_timings.addListener(new Lis);
        
        table.setItems(m_timings);
        table.getColumns().addAll(lapNum,lapTime,sector1Time,sector2Time);
        secondRow.getChildren().add(table);
        layout.getChildren().addAll(menuBar,firstRow,secondRow);
        
        //layout.a
        //ObservableList<Timing> 
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
    public void fileOpenAction(Stage stage){
        FileChooser fileDialog = new FileChooser();
        fileDialog.setTitle("Open Data File");
        fileDialog.getExtensionFilters().add(new FileChooser.ExtensionFilter("F1 Telemetry File", "*.zip"));
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
            TelemetryReader reader = new TelemetryReader();
            HashMap<String,StfDocument> documents = reader.read(file);
            StfClass rootClass = (StfClass)documents.get("ownTelemetry.stf").getChild(0);
            StfClass dataClass = (StfClass)rootClass.getChild(0);
            String[] properties = dataClass.getChildPropertyName();
            this.m_properties.addAll(properties);
            AbstractStfObject[] children = rootClass.getChildren();
            ArrayList<String> sessions = new ArrayList<>();
            for(AbstractStfObject obj:children){
                
                if(obj instanceof StfClass){
                    String sessionIdentifier = ((StfClass) obj).getChildPropertyValue("sessionUid");
                    boolean bFound = false;
                    for(int i=0;i<sessions.size();i++){
                        if(sessions.get(i).compareTo(sessionIdentifier)==0){
                            bFound = true;
                        }
                    }
                    if(!bFound){
                        sessions.add(sessionIdentifier);
                    }
                }
                
            }
            this.m_sessions.addAll(sessions);
            m_documents = documents;
        }
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
        ObservableList<String> selectedProperties = list.getSelectionModel().getSelectedItems();
        String selectedItem = sessionList.getSelectionModel().getSelectedItem();
        if(selectedRounds.size() <=0 || selectedProperties.size() <= 0){
            return;
        }
        StfDocument doc = this.m_documents.get("ownTelemetry.stf");
        StfClass rootClass = (StfClass) doc.getChild(0);
        AbstractStfObject[] children = rootClass.getChildren();
        //ObservableList<XYChart.Data<Number,Number>>[] series;
        //series = new ObservableList<XYChart.Data<Number, Number>>[5];
        /*
        for(AbstractStfObject obj: children){
            
        }
*/
        diagramData.clear();
        for(TimingFx rounds:selectedRounds){
            for(String property: selectedProperties){
                ObservableList<XYChart.Data<Number,Number>> series = FXCollections.observableArrayList();
                for(AbstractStfObject obj: children){
                    try{
                        String sLapNum = ((StfClass)obj).getChildPropertyValue("lap");
                        String sessionIdentifier = ((StfClass) obj).getChildPropertyValue("sessionUid");
                        int iLapNum = Integer.parseInt(sLapNum);
                        if(selectedItem.equals(sessionIdentifier) && iLapNum == rounds.getLapNum()){
                            String lapTime = ((StfClass)obj).getChildPropertyValue("currentLapTime");
                            String propertyTrim = property.trim();
                            String selectedProperty = ((StfClass)obj).getChildPropertyValue(propertyTrim);
                            float fLapTime = Float.parseFloat(lapTime);
                            float fProperty = Float.parseFloat(selectedProperty);//I dont know the datatype. So i use float because all other datatype should be covered
                            series.add(new XYChart.Data<Number,Number>(new Float(fLapTime),new Float(fProperty)));
                        }
                    }catch(NumberFormatException e){
                        
                    }
                }
                LineChart.Series<Number,Number> temp = new LineChart.Series<>(series);
                temp.setName("Runde "+rounds.getLapNum());
                diagramData.add(temp);
            }
        }
    }
}
/*
ObservableList<XYChart.Data<Number,Number>> series = FXCollections.observableArrayList();
        for(int i=0;i<5;i++){
            series.add(new XYChart.Data<Number, Number>(new Float((float)i) ,new Float((float)(i*i))));
        }
        //ObservableList<XYChart.Series<Number,Number>> diagramData = FXCollections.observableArrayList();
        diagramData.add(new LineChart.Series<>(series));
*/
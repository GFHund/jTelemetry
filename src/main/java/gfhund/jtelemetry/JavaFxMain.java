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
        
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart<Number,Number> diagramm = new LineChart<>(xAxis,yAxis);
        firstRow.getChildren().add(diagramm);
        ObservableList<XYChart.Data<Number,Number>> series = FXCollections.observableArrayList();
        for(int i=0;i<5;i++){
            series.add(new XYChart.Data<Number, Number>(new Float((float)i) ,new Float((float)(i*i))));
        }
        ObservableList<XYChart.Series<Number,Number>> data = FXCollections.observableArrayList();
        data.add(new LineChart.Series<>(series));
        diagramm.setData(data);
        
        
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
    
    public void startLiveViewDialog(Stage stage){
        m_liveViewDialog = new LiveViewDialog(stage);
    }
}

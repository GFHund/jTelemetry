/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfhund.jtelemetry.fxgui;

import gfhund.jtelemetry.ClassManager;
import gfhund.jtelemetry.commontelemetry.CommonLapManager;
import gfhund.jtelemetry.commontelemetry.CommonTelemetryData;
import gfhund.jtelemetry.commontelemetry.CommonTelemetryData4Table;
import gfhund.jtelemetry.commontelemetry.LapIdentificationObject;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author PhilippHolzmann
 */
public class DiagramViewGroup extends Region{
    private DiagramView[] diagrams = new DiagramView[5];
    private ObservableList<DiagramView.DiagrammLine> data0 = FXCollections.observableArrayList();
    private ObservableList<DiagramView.DiagrammLine> data1 = FXCollections.observableArrayList();
    private ObservableList<DiagramView.DiagrammLine> data2 = FXCollections.observableArrayList();
    private ObservableList<DiagramView.DiagrammLine> data3 = FXCollections.observableArrayList();
    private ObservableList<DiagramView.DiagrammLine> data4 = FXCollections.observableArrayList();
    private ObservableList<CommonTelemetryData4Table> m_data = FXCollections.observableArrayList();
    
    private DiagramView[] ersDiagrams = new DiagramView[5];
    private DiagramView[] tyreInnerDiagrams = new DiagramView[5];
    private DiagramView[] tyreSurfaceDiagrams = new DiagramView[5];
    TableView<CommonTelemetryData4Table>[] ValueTables = new TableView[5];
    ArrayList<OnSelectValue> registeredOnSelectValueEvents = new ArrayList<>();
    ArrayList<LapIdentificationObject> shownIds = new ArrayList<>();
    
    private static double V_GAP = 10.0f;

    public DiagramViewGroup() {
        String[] diagramTitles = {"Geschwindigkeit","Gang","Drehzahl","Gas Pedal","Brems Pedal"};
        String[] ersDiagramTitles = {"Geschwindigkeit","ers Mode","ERS Energie","ERS Ausgegeben","ERS Gewonnen"};
        String[] tyreInnerDigramTitles = {"Geschwindigkeit","Vorne Links","Vorne Rechts","Hinten Links","Hinten Rechts"};
        for(int i=0;i<diagrams.length;i++){
            diagrams[i] = new DiagramView(diagramTitles[i]);
            diagrams[i].addOnSelectValue((float value)->{findNearestTelemetry(value);});
            ersDiagrams[i] = new DiagramView(ersDiagramTitles[i]);
            ersDiagrams[i].addOnSelectValue((float value)->{findNearestTelemetry(value);});
            tyreInnerDiagrams[i] = new DiagramView(tyreInnerDigramTitles[i]);
            tyreInnerDiagrams[i].addOnSelectValue((float value)->{findNearestTelemetry(value);});
            tyreSurfaceDiagrams[i] = new DiagramView(tyreInnerDigramTitles[i]);
            tyreSurfaceDiagrams[i].addOnSelectValue((float value)->{findNearestTelemetry(value);});
            
        }
        diagrams[0].setParameterMode(DiagramView.ParameterMode.SPEED);
        diagrams[0].setData(data0);
        
        diagrams[1].setParameterMode(DiagramView.ParameterMode.GEAR);
        diagrams[1].setData(data1);
        
        diagrams[2].setParameterMode(DiagramView.ParameterMode.RPM);
        diagrams[2].setData(data2);
        
        diagrams[3].setParameterMode(DiagramView.ParameterMode.THROTTLE);
        diagrams[3].setData(data3);
        
        diagrams[4].setParameterMode(DiagramView.ParameterMode.BREAK);
        diagrams[4].setData(data4);
        
        for(int i=0;i<ValueTables.length;i++){
            ValueTables[i] = new TableView<>();
            ValueTables[i].setPrefHeight(200);
            TableColumn playerName = new TableColumn<>("name");
            playerName.setCellValueFactory(new PropertyValueFactory<CommonTelemetryData4Table,String>("driverName"));
            
            TableColumn lapNum = new TableColumn<>("lap");
            lapNum.setCellValueFactory(new PropertyValueFactory<CommonTelemetryData4Table,Integer>("lapNum"));
            
            TableColumn speed = new TableColumn<>("speed");
            speed.setCellValueFactory(new PropertyValueFactory<CommonTelemetryData4Table,Integer>("speed"));
            if(i==0){
                TableColumn currentTime = new TableColumn<>("Zeit");
                currentTime.setCellValueFactory(new PropertyValueFactory<CommonTelemetryData4Table,Integer>("currentTime"));
                
                TableColumn gear = new TableColumn<>("Gang");
                gear.setCellValueFactory(new PropertyValueFactory<CommonTelemetryData4Table,Integer>("gear"));
                
                TableColumn rpm = new TableColumn<>("RPM");
                rpm.setCellValueFactory(new PropertyValueFactory<CommonTelemetryData4Table,Integer>("rpm"));
                
                TableColumn throttle = new TableColumn<>("Gas");
                throttle.setCellValueFactory(new PropertyValueFactory<CommonTelemetryData4Table,Integer>("throttle"));
                
                TableColumn brake = new TableColumn<>("bremse");
                brake.setCellValueFactory(new PropertyValueFactory<CommonTelemetryData4Table,Integer>("brake"));
                
                ValueTables[i].getColumns().addAll(playerName,lapNum,currentTime,speed,gear,rpm,throttle,brake);
                ValueTables[i].setItems(m_data);
            } else if(i==1){
                
                TableColumn ersDeployMode = new TableColumn<>("Mode");
                ersDeployMode.setCellValueFactory(new PropertyValueFactory<CommonTelemetryData4Table,Integer>("ersDeployMode"));
                
                TableColumn ersHarvestMGUK = new TableColumn<>("MGUK");
                ersHarvestMGUK.setCellValueFactory(new PropertyValueFactory<CommonTelemetryData4Table,Float>("ersHarvestMGUK"));
                
                TableColumn ersHarvestMGUH = new TableColumn<>("MGUH");
                ersHarvestMGUH.setCellValueFactory(new PropertyValueFactory<CommonTelemetryData4Table,Float>("ersHarvestMGUH"));
                
                TableColumn ersDeployed = new TableColumn<>("Verbraucht");
                ersDeployed.setCellValueFactory(new PropertyValueFactory<CommonTelemetryData4Table,Float>("ersDeployed"));
                
                TableColumn ersStoreEnergy = new TableColumn<>("Total");
                ersStoreEnergy.setCellValueFactory(new PropertyValueFactory<CommonTelemetryData4Table,Float>("ersStoreEnergy"));
                
                ValueTables[i].getColumns().addAll(playerName,lapNum,speed,ersDeployMode,ersHarvestMGUH,ersHarvestMGUK,ersDeployed,ersStoreEnergy);
                ValueTables[i].setItems(m_data);
            }
        }
        
        //VBox layout = new VBox();
        //layout.setSpacing(20);
        //layout.getChildren().addAll(diagrams);
        
        GridPane grid = new GridPane();
        grid.add(diagrams[0],0,0);
        grid.add(diagrams[1],1,0);
        grid.add(diagrams[2],0,1);
        grid.add(diagrams[3],1,1);
        grid.add(diagrams[4],0,2);
        grid.add(ValueTables[0],1,2);
        grid.setVgap(V_GAP);
        //grid.setHgap(10);
        //grid.setPadding(arg0);
        
        TabPane pane = new TabPane();
        pane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        pane.setSide(Side.BOTTOM);
        
        Tab driveData = new Tab("Fahrdaten");
        //driveData.setContent(layout);
        driveData.setContent(grid);
        
        GridPane ersGrid = new GridPane();
        ersGrid.add(ersDiagrams[0], 0, 0);
        ersGrid.add(ersDiagrams[1], 1, 0);
        ersGrid.add(ersDiagrams[2], 0, 1);
        ersGrid.add(ersDiagrams[3], 1, 1);
        ersGrid.add(ersDiagrams[4], 0, 2);
        ersGrid.add(ValueTables[1], 1, 2);
        ersGrid.setVgap(V_GAP);
        
        Tab ersData = new Tab("Ers");
        ersData.setContent(ersGrid);
        
        GridPane tyreInnerGrid = new GridPane();
        tyreInnerGrid.add(tyreInnerDiagrams[0],0,0); 
        tyreInnerGrid.add(tyreInnerDiagrams[1],0,1); 
        tyreInnerGrid.add(tyreInnerDiagrams[2],1,1); 
        tyreInnerGrid.add(tyreInnerDiagrams[3],0,2); 
        tyreInnerGrid.add(tyreInnerDiagrams[4],1,2); 
        tyreInnerGrid.setVgap(V_GAP);
 
        
        Tab tyreInnerData = new Tab("Reifen Innen Temp");
        tyreInnerData.setContent(tyreInnerGrid);
        
        GridPane tyreSurfaceGrid = new GridPane();
        tyreSurfaceGrid.add(tyreSurfaceDiagrams[0], 0, 0);
        tyreSurfaceGrid.add(tyreSurfaceDiagrams[1], 0, 1);
        tyreSurfaceGrid.add(tyreSurfaceDiagrams[2], 1, 1);
        tyreSurfaceGrid.add(tyreSurfaceDiagrams[3], 0, 2);
        tyreSurfaceGrid.add(tyreSurfaceDiagrams[4], 1, 2);
        tyreSurfaceGrid.setVgap(V_GAP);
        
        Tab tyreSurfaceData = new Tab("Reifen Oberfläche Temp");
        tyreSurfaceData.setContent(tyreSurfaceGrid);
        pane.getTabs().addAll(driveData,ersData,tyreInnerData,tyreSurfaceData);
        
        getChildren().add(pane);
    }
    
    public void clearTelemetryData(){
        this.data0.clear();
        this.data1.clear();
        this.data2.clear();
        this.data3.clear();
        this.data4.clear();
        for(int i=0;i<this.ersDiagrams.length;i++){
            this.ersDiagrams[i].getData().clear();
        }
        for(int i=0;i<this.tyreInnerDiagrams.length;i++){
            this.tyreInnerDiagrams[i].getData().clear();
        }
        for(int i=0;i<this.tyreSurfaceDiagrams.length;i++){
            this.tyreSurfaceDiagrams[i].getData().clear();
        }
        this.m_data.clear();
    }
    
    public void addTelemetryData(LapIdentificationObject data){
        shownIds.add(data);
        CommonLapManager lapManager;
        try{
            lapManager = (CommonLapManager)gfhund.jtelemetry.ClassManager.get(CommonLapManager.class);
        }catch(ClassManager.ClassManagerException e){
            return;
        }
        ArrayList<CommonTelemetryData> telemetryData = lapManager.getLapData(data);
        
        DiagramView.DiagrammLine[] lines = new DiagramView.DiagrammLine[20];
        for(int i=0;i<lines.length;i++){
            lines[i] = new DiagramView.DiagrammLine();
        }
        for(CommonTelemetryData date:telemetryData){
            DiagramView.DiagrammPoint point0;
            DiagramView.DiagrammPoint point1;
            DiagramView.DiagrammPoint point2;
            DiagramView.DiagrammPoint point3;
            DiagramView.DiagrammPoint point4;
            
            float value0 = date.getSpeed();
            float value1 = date.getGear();
            float value2 = date.getRpm();
            float value3 = date.getThrottle();
            float value4 = date.getBrake();
            float value5 = date.getSpeed();//Reference
            float value6 = date.getErsDeployMode();
            float value7 = date.getErsStoreEngergy();//Reference
            float value8 = date.getErsDeployed();//Reference
            float value9 = date.getErsHarvestMGUH();//Reference
            float value10 = date.getSpeed();//Reference
            float value11 = date.getTyreInnerTempFL();//Reference
            float value12 = date.getTyreInnerTempFR();//Reference
            float value13 = date.getTyreInnerTempRL();//Reference
            float value14 = date.getTyreInnerTempRR();//Reference
            float value15 = date.getSpeed();//Reference
            float value16 = date.getTyreSurfaceTempFL();//Reference
            float value17 = date.getTyreSurfaceTempFR();//Reference
            float value18 = date.getTyreSurfaceTempRL();//Reference
            float value19 = date.getTyreSurfaceTempRR();//Reference
            
            
            point0 = new DiagramView.DiagrammPoint(date.getDistance(), value0);
            point1 = new DiagramView.DiagrammPoint(date.getDistance(), value1);
            point2 = new DiagramView.DiagrammPoint(date.getDistance(), value2);
            point3 = new DiagramView.DiagrammPoint(date.getDistance(), value3);
            point4 = new DiagramView.DiagrammPoint(date.getDistance(), value4);
            DiagramView.DiagrammPoint point5 = 
                    new DiagramView.DiagrammPoint(date.getDistance(),value5);
            DiagramView.DiagrammPoint point6 =
                    new DiagramView.DiagrammPoint(date.getDistance(), value6);
            DiagramView.DiagrammPoint point7 = 
                    new DiagramView.DiagrammPoint(date.getDistance(),value7);
            DiagramView.DiagrammPoint point8 =
                    new DiagramView.DiagrammPoint(date.getDistance(), value8);
            DiagramView.DiagrammPoint point9 =
                    new DiagramView.DiagrammPoint(date.getDistance(), value9);
            DiagramView.DiagrammPoint point10 = 
                    new DiagramView.DiagrammPoint(date.getDistance(), value10);
            DiagramView.DiagrammPoint point11 =
                    new DiagramView.DiagrammPoint(date.getDistance(), value11);
            DiagramView.DiagrammPoint point12 = 
                    new DiagramView.DiagrammPoint(date.getDistance(), value12);
            DiagramView.DiagrammPoint point13 =
                    new DiagramView.DiagrammPoint(date.getDistance(), value13);
            DiagramView.DiagrammPoint point14 =
                    new DiagramView.DiagrammPoint(date.getDistance(), value14);
            DiagramView.DiagrammPoint point15 =
                    new DiagramView.DiagrammPoint(date.getDistance(), value15);
            DiagramView.DiagrammPoint point16 =
                    new DiagramView.DiagrammPoint(date.getDistance(), value16);
            DiagramView.DiagrammPoint point17 =
                    new DiagramView.DiagrammPoint(date.getDistance(), value17);
            DiagramView.DiagrammPoint point18 =
                    new DiagramView.DiagrammPoint(date.getDistance(), value18);
            DiagramView.DiagrammPoint point19 =
                    new DiagramView.DiagrammPoint(date.getDistance(), value19);
            
            lines[0].addTrackPoint(point0);
            lines[1].addTrackPoint(point1);
            lines[2].addTrackPoint(point2);
            lines[3].addTrackPoint(point3);
            lines[4].addTrackPoint(point4);
            lines[5].addTrackPoint(point5);
            lines[6].addTrackPoint(point6);
            lines[7].addTrackPoint(point7);
            lines[8].addTrackPoint(point8);
            lines[9].addTrackPoint(point9);
            lines[10].addTrackPoint(point10);
            lines[11].addTrackPoint(point11);
            lines[12].addTrackPoint(point12);
            lines[13].addTrackPoint(point13);
            lines[14].addTrackPoint(point14);
            lines[15].addTrackPoint(point15);
            lines[16].addTrackPoint(point16);
            lines[17].addTrackPoint(point17);
            lines[18].addTrackPoint(point18);
            lines[19].addTrackPoint(point19);
            
        }
        this.data0.add(lines[0]);
        this.data1.add(lines[1]);
        this.data2.add(lines[2]);
        this.data3.add(lines[3]);
        this.data4.add(lines[4]);
        
        this.ersDiagrams[0].getData().add(lines[5]);
        this.ersDiagrams[1].getData().add(lines[6]);
        this.ersDiagrams[2].getData().add(lines[7]);
        this.ersDiagrams[3].getData().add(lines[8]);
        this.ersDiagrams[4].getData().add(lines[9]);
        
        this.tyreInnerDiagrams[0].getData().add(lines[10]);
        this.tyreInnerDiagrams[1].getData().add(lines[11]);
        this.tyreInnerDiagrams[2].getData().add(lines[12]);
        this.tyreInnerDiagrams[3].getData().add(lines[13]);
        this.tyreInnerDiagrams[4].getData().add(lines[14]);
        
        this.tyreSurfaceDiagrams[0].getData().add(lines[15]);
        this.tyreSurfaceDiagrams[1].getData().add(lines[16]);
        this.tyreSurfaceDiagrams[2].getData().add(lines[17]);
        this.tyreSurfaceDiagrams[3].getData().add(lines[18]);
        this.tyreSurfaceDiagrams[4].getData().add(lines[19]);
    }
    

    
    private void findNearestTelemetry(float value){
        CommonLapManager lapManager;
        this.m_data.clear();
        try{
            lapManager = (CommonLapManager)gfhund.jtelemetry.ClassManager.get(CommonLapManager.class);
            for(LapIdentificationObject id: this.shownIds){
                CommonTelemetryData data = lapManager.getDataFromDistance(id, value);
                m_data.add(new CommonTelemetryData4Table(data));
            }
        }catch(ClassManager.ClassManagerException e){
            return;
        }
        for(OnSelectValue event: this.registeredOnSelectValueEvents){
            event.onSelectValue(value);
        }
    }
    public void addOnSelectValueEvent(OnSelectValue event){
        this.registeredOnSelectValueEvents.add(event);
    }
    
    
}

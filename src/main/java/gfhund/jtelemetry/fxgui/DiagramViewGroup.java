/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfhund.jtelemetry.fxgui;

import gfhund.jtelemetry.commontelemetry.CommonTelemetryData;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;

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

    public DiagramViewGroup() {
        for(int i=0;i<diagrams.length;i++){
            diagrams[i] = new DiagramView();
            
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
        
        VBox layout = new VBox();
        layout.setSpacing(20);
        layout.getChildren().addAll(diagrams);
        getChildren().add(layout);
    }
    
    public void clearTelemetryData(){
        this.data0.clear();
        this.data1.clear();
        this.data2.clear();
        this.data3.clear();
        this.data4.clear();
    }
    
    public void addTelemetryData(ArrayList<CommonTelemetryData> data){
        
        DiagramView.DiagrammLine[] lines = new DiagramView.DiagrammLine[diagrams.length];
        for(int i=0;i<lines.length;i++){
            lines[i] = new DiagramView.DiagrammLine();
        }
        for(CommonTelemetryData date:data){
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
            
            point0 = new DiagramView.DiagrammPoint(date.getDistance(), value0);
            point1 = new DiagramView.DiagrammPoint(date.getDistance(), value1);
            point2 = new DiagramView.DiagrammPoint(date.getDistance(), value2);
            point3 = new DiagramView.DiagrammPoint(date.getDistance(), value3);
            point4 = new DiagramView.DiagrammPoint(date.getDistance(), value4);
            
            lines[0].addTrackPoint(point0);
            lines[1].addTrackPoint(point1);
            lines[2].addTrackPoint(point2);
            lines[3].addTrackPoint(point3);
            lines[4].addTrackPoint(point4);
        }
        this.data0.add(lines[0]);
        this.data1.add(lines[1]);
        this.data2.add(lines[2]);
        this.data3.add(lines[3]);
        this.data4.add(lines[4]);
        /*
                for(DiagramView view:diagrams){
            view.addData(data);
        }
*/
    }
    
}

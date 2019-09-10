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

/**
 *
 * @author PhilippHolzmann
 */
public class DiagramViewGroup extends Region{
    private DiagramView[] diagrams = new DiagramView[5];

    public DiagramViewGroup() {
        for(int i=0;i<diagrams.length;i++){
            diagrams[i] = new DiagramView();
        }
        diagrams[0].setParameterMode(DiagramView.ParameterMode.SPEED);
        diagrams[1].setParameterMode(DiagramView.ParameterMode.GEAR);
        diagrams[2].setParameterMode(DiagramView.ParameterMode.RPM);
        diagrams[3].setParameterMode(DiagramView.ParameterMode.THROTTLE);
        diagrams[4].setParameterMode(DiagramView.ParameterMode.BREAK);
        VBox layout = new VBox();
        layout.getChildren().addAll(diagrams);
        getChildren().add(layout);
    }
    public void addTelemetryData(ArrayList<CommonTelemetryData> data){
        for(DiagramView view:diagrams){
            view.addData(data);
        }
    }
    
}

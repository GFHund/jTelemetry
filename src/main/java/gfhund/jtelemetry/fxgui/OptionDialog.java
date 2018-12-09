/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfhund.jtelemetry.fxgui;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;

/**
 *
 * @author PhilippGL
 */
public class OptionDialog extends DialogFx {

    public OptionDialog(Stage parent) {
        super(parent);
    }
    
    @Override
    public Scene init(Stage stage){
        GridPane pane = new GridPane();
        return new Scene(pane);
    }
    
}

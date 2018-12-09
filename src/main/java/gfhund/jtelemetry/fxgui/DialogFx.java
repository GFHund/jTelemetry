/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfhund.jtelemetry.fxgui;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author PhilippGL
 */
public abstract class DialogFx {
    public DialogFx(Stage parent){
        Stage dialogStage = new Stage();
        Scene dialogScene = init(dialogStage);
        dialogStage.setScene(dialogScene);
        dialogStage.show();
    }
    
    public abstract Scene init(Stage stage);
}

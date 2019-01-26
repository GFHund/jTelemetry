/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfhund.jtelemetry.fxgui;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author PhilippGL
 */
public class LoadingBarDialog extends DialogFx {

    ProgressBar m_bar;
    //Button m_btn;
    
    public LoadingBarDialog(Stage parent){
        super(parent);
    }
    
    @Override
    public Scene init(Stage stage) {
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        m_bar = new ProgressBar(0);
        //m_bar.
        VBox layout = new VBox();
        layout.getChildren().add(m_bar);
        m_bar.setMaxWidth(Double.MAX_VALUE);
        m_bar.setMaxHeight(Double.MAX_VALUE);
        Insets padding = new Insets(25, 0, 0, 0);
        m_bar.setPadding(padding);
        Scene scene = new Scene(layout,300,100);
        return scene;
    }
    
    public void setValue(double progress){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                m_bar.setProgress(progress);
            }
        });
    }
    public void close(){
        this.dialogStage.close();
    }
}

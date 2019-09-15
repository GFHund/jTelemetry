/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfhund.jtelemetry.fxgui;

import gfhund.jtelemetry.ClassManager;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import gfhund.jtelemetry.data.Settings;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;

/**
 *
 * @author PhilippHolzmann
 */
public class SettingsDialog extends DialogFx {

    
    protected String[] recordingTypeChoices;
    public static final String F1_RECORDING_TYPE_SETTINGS_NAME = "f1RecordingType";
    public static final String F1_RAW_SETUP_DATA_SETTINGS_NAME = "f1RawSetup";
    
    public SettingsDialog(Stage parent) {
        super(parent);
    }

    ChoiceBox recordingTypeOptions;
    CheckBox f1RawSetupData;
    
    @Override
    public Scene init(Stage stage) {
        recordingTypeChoices = new String[2];
        recordingTypeChoices[0] = "Nur das eigene Auto";
        recordingTypeChoices[1] = "Das gesamteFeld";
        Settings set = null;
        try{
            set = (Settings)gfhund.jtelemetry.ClassManager.get(Settings.class);
        }catch(ClassManager.ClassManagerException e){
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error to get Settings");
            error.setHeaderText(null);
            error.setContentText("If you see this this should be an bug in this program. Error: "+e.getMessage());
            error.showAndWait();  
        }
        
        
        
        VBox layout = new VBox();
        
        TabPane tabPane = new TabPane();
        Tab tabCommon = new Tab("Allgemein");
        //tabCommon.setC
        Tab tabF1 = new Tab("F1");
        //recordingTypeOptions
        Label recordingType = new Label("Recording Typ");
        recordingTypeOptions = new ChoiceBox();
        recordingTypeOptions.getItems().addAll(recordingTypeChoices[0],recordingTypeChoices[1]);
        
        Label getRawSetupSettings = new Label("Schreibe die Rohen Fahrzeug \n Settings in eine Datei");
        f1RawSetupData = new CheckBox();
        
        String settingF1RecordingType = set.getValue(F1_RECORDING_TYPE_SETTINGS_NAME,"0");
        String settingRawSetupData = set.getValue(F1_RAW_SETUP_DATA_SETTINGS_NAME, "0");
        try{
            int iSettingF1RecordingType = Integer.parseInt(settingF1RecordingType);
            if(iSettingF1RecordingType >=0 && iSettingF1RecordingType < recordingTypeChoices.length){
                recordingTypeOptions.setValue(recordingTypeChoices[iSettingF1RecordingType]);
            }
            int iRawSetupData = Integer.parseInt(settingRawSetupData);
            Boolean bRawSetupData = (iRawSetupData == 1)? true:false;
            f1RawSetupData.selectedProperty().set(bRawSetupData);
            
        }catch(NumberFormatException e){
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Invalid Format of Recording Typ");
            error.setHeaderText(null);
            error.setContentText("If you see this this, your settings is corrupted. To Solve this delete the old settings.stf file. Error: "+e.getMessage());
            error.showAndWait();  
        }
        
        
        GridPane f1ControlsLayout = new GridPane();
        f1ControlsLayout.add(recordingType, 0, 0);
        f1ControlsLayout.add(recordingTypeOptions,1,0);
        f1ControlsLayout.add(getRawSetupSettings,0,1);
        f1ControlsLayout.add(f1RawSetupData,1,1);
        
        
        tabF1.setContent(f1ControlsLayout);
        
        tabPane.getTabs().addAll(tabCommon,tabF1);
        HBox buttonContainer = new HBox();
        
        Button saveButton = new Button("Save");
        saveButton.setOnAction((event)->{});
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction((event)->{this.dialogStage.close();});
        
        buttonContainer.getChildren().addAll(saveButton,cancelButton);
        
        layout.getChildren().addAll(tabPane,buttonContainer);
        
        return new Scene(layout,1024,768);
    }
    
    public void onSaveClicked(){
        Settings set = null;
        try{
            set = (Settings)gfhund.jtelemetry.ClassManager.get(Settings.class);
        }catch(ClassManager.ClassManagerException e){
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error to get Settings");
            error.setHeaderText(null);
            error.setContentText("If you see this this should be an bug in this program. Error: "+e.getMessage());
            error.showAndWait();  
        }
        
        String recordingTypeChoice = (String)recordingTypeOptions.getValue();
        int recordingTypeChoiceIndex = 0;
        for(int i=0;i<recordingTypeChoices.length;i++){
            if(recordingTypeChoice.equals(recordingTypeChoices[i])){
                recordingTypeChoiceIndex = i;
            }
        }
        set.setValue(F1_RECORDING_TYPE_SETTINGS_NAME,""+recordingTypeChoiceIndex);
        
        Boolean bRawSetupData = this.f1RawSetupData.selectedProperty().get();
        set.setValue(F1_RAW_SETUP_DATA_SETTINGS_NAME, (bRawSetupData)?"1":"0");
    }
    
}

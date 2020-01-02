/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfhund.jtelemetry.fxgui;

import gfhund.jtelemetry.ClassManager;
import gfhund.jtelemetry.commontelemetry.LapIdentificationObject;
import gfhund.jtelemetry.data.Timing;
import gfhund.jtelemetry.stfFormat.StfClass;
import gfhund.jtelemetry.stfFormat.StfDocument;
import java.io.File;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventType;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import java.util.Date;
import java.util.ArrayList;
import gfhund.jtelemetry.commontelemetry.LapIdentificationObject;
import gfhund.jtelemetry.commontelemetry.CommonLapManager;

/**
 *
 * @author PhilippGL
 */
public class FileOpenDialog extends DialogFx {

    private TextField pathField;
    private Button selectFile;
    private Button loadFile;
    private TableView<RoundSelection> availableRounds;
    private TableView<RoundSelection> selectedRounds;
    private Button moveRight;
    private Button moveLeft;
    private ObservableList<RoundSelection> availableRoundsList;// = FXCollections.observableArrayList();
    private ObservableList<RoundSelection> selectedRoundsList;// = FXCollections.observableArrayList();
    private Button saveBtn;
    private Button cancelBtn;
    private ArrayList<SaveEventHandler> eventHandler;
    
    
    public FileOpenDialog(Stage stage) {
        super(stage);
        this.eventHandler = new ArrayList<>();
    }

    
    
    @Override
    public Scene init(Stage stage) {
        this.pathField = new TextField();
        this.selectFile = new Button("Select File");
        this.selectFile.setOnAction(event -> {
            selectFile(stage);
        });
        this.loadFile = new Button("Load");
        this.loadFile.setOnAction(event -> {
            loadFile();
        });

        HBox fileSelectBox = new HBox(this.pathField,this.selectFile,this.loadFile);

        availableRounds = new TableView<>();
        TableColumn availableRoundsPlayer = new TableColumn("Name");
        availableRoundsPlayer.setCellValueFactory(new PropertyValueFactory("player"));
        TableColumn availableRoundsLapNum = new TableColumn("Lap Num");
        availableRoundsLapNum.setCellValueFactory(new PropertyValueFactory<Timing,Integer>("lapNum"));
        TableColumn availableRoundsTiming = new TableColumn("Zeit");
        availableRoundsTiming.setCellValueFactory(new PropertyValueFactory<Timing,Integer>("time"));
        availableRounds.getColumns().addAll(availableRoundsPlayer,availableRoundsLapNum,availableRoundsTiming);
        availableRoundsList = FXCollections.observableArrayList();
        availableRounds.setItems(availableRoundsList);
        
        moveRight = new Button(">>");
        this.moveRight.setOnAction((event)->{moveRight();});
        moveLeft = new Button("<<");
        this.moveLeft.setOnAction((event)->moveLeft());
        
        selectedRounds = new TableView<>();
        TableColumn selectedRoundsPlayer = new TableColumn("player");
        selectedRoundsPlayer.setCellValueFactory(new PropertyValueFactory("player"));
        TableColumn selectedRoundsLapNum = new TableColumn("Lap Num");
        selectedRoundsLapNum.setCellValueFactory(new PropertyValueFactory<Timing,Integer>("lapNum"));
        TableColumn selectedRoundsTiming = new TableColumn("Zeit");
        selectedRoundsTiming.setCellValueFactory(new PropertyValueFactory<Timing,Integer>("time"));
        selectedRounds.getColumns().addAll(selectedRoundsPlayer,selectedRoundsLapNum,selectedRoundsTiming);
        selectedRoundsList = FXCollections.observableArrayList();
        selectedRounds.setItems(selectedRoundsList);
        
        CommonLapManager lapManager;
        try{
            lapManager = (CommonLapManager)gfhund.jtelemetry.ClassManager.get(CommonLapManager.class);
            LapIdentificationObject[] lapIds = lapManager.getIds();
            for(LapIdentificationObject lapId: lapIds){
                RoundSelection newEntry = new RoundSelection();
                newEntry.setDateDriven(lapId.getDateLapDriven());
                newEntry.setLapNum(lapId.getLapNum());
                newEntry.setPlayer(lapId.getPlayer());
                newEntry.setTime(0);
                newEntry.setZipFile(lapId.getZipFile());
                selectedRoundsList.add(newEntry);
            }        
        }catch(ClassManager.ClassManagerException e){
            //This Should Not happen
        }

        HBox selectRound = new HBox(availableRounds,moveLeft,moveRight,selectedRounds);

        VBox layout = new VBox(fileSelectBox,selectRound);

        return new Scene(layout,1024,768);
    }
    
    private void selectFile(Stage stage){
        this.availableRoundsList.clear();
        FileChooser fileDialog = new FileChooser();
        fileDialog.setTitle("Open Data File");
        fileDialog.getExtensionFilters().add(new FileChooser.ExtensionFilter("F1 Telemetry File", "*.zip"));
        File file = fileDialog.showOpenDialog(stage);
        if(file!=null){
            long fileSize = file.length();
            if(fileSize<=0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Falsche Datei größe");
                alert.setHeaderText(null);
                alert.setContentText("Datei ist kleiner gleich 0");
                alert.showAndWait();
                return;
            }
            this.pathField.setText(file.getAbsolutePath());
        }
    }
    
    private void loadFile(){
        availableRoundsList.clear();
        try{
            File file = new File(this.pathField.getText());
            TelemetryReader tr = (TelemetryReader)gfhund.jtelemetry.ClassManager.get(TelemetryReader.class);
            String[] names = tr.getPlayerNames(file);
            for(String name: names){
                
                Date[] roundDates = tr.getPlayerDates(file, name);
                for(Date roundDate: roundDates){
                    int[] lapNums = tr.getPlayerLaps(file,name,roundDate);
                    for(int lapNum:lapNums){
                        
                        RoundSelection data = new RoundSelection();
                        data.setPlayer(name);
                        data.setLapNum(lapNum);
                        data.setTime(0.0f);
                        data.setZipFile(this.pathField.getText());
                        data.setDateDriven(roundDate);
                        availableRoundsList.add(data);
                    }
                }
            }
            //StfDocument doc = tr.read(file, "ownTelemetry.stf");//
            //StfClass rootClass = (StfClass)doc.getChild(0);
            //StfClass dataClass = (StfClass)rootClass.getChild(0);
        }catch(ClassManager.ClassManagerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Falsche Datei größe");
            alert.setHeaderText(null);
            alert.setContentText("Fehler beim Laden der Datei. FehlerCode: 1");
            alert.showAndWait();
            return;
        }
    }
    
    //Move left means user select round for analysis
    private void moveRight(){
        ObservableList<RoundSelection> selectedRounds = availableRounds.getSelectionModel().getSelectedItems();
        selectedRoundsList.addAll(selectedRounds);
        CommonLapManager lapManager;
        try{
            lapManager = (CommonLapManager)gfhund.jtelemetry.ClassManager.get(CommonLapManager.class);
        }catch(ClassManager.ClassManagerException e){
            lapManager = null;
        }
        for(RoundSelection rounds: selectedRounds){
            LapIdentificationObject id = new LapIdentificationObject();
            id.setZipFile(rounds.getZipFile());
            id.setLapNum(rounds.getLapNum());
            id.setPlayer(rounds.getPlayer());
            id.setDateLapDriven(rounds.getDateDriven());
            if(lapManager != null){
                lapManager.addLap(id);
            }
            availableRoundsList.remove(rounds);
        }
        
    }
    
    //Move right means user select round to remove it from the analysis
    private void moveLeft(){
        ObservableList<RoundSelection> roundsSelection = selectedRounds.getSelectionModel().getSelectedItems();
        availableRoundsList.addAll(roundsSelection);
        CommonLapManager lapManager;
        try{
            lapManager = (CommonLapManager)gfhund.jtelemetry.ClassManager.get(CommonLapManager.class);
        }catch(ClassManager.ClassManagerException e){
            lapManager = null;
        }
        for(RoundSelection rounds: roundsSelection){
            LapIdentificationObject id = new LapIdentificationObject();
            id.setZipFile(rounds.getZipFile());
            id.setLapNum(rounds.getLapNum());
            id.setPlayer(rounds.getPlayer());
            id.setDateLapDriven(rounds.getDateDriven());
            if(lapManager != null){
                lapManager.removeLap(id);
            }
            selectedRoundsList.remove(rounds);
        }
    }

    public void registerSaveHandler(SaveEventHandler handler){
        this.eventHandler.add(handler);
    }
    @Override
    public void onExit() {
        for(SaveEventHandler handler: this.eventHandler){
            handler.OnSaveEvent();
        }
        this.dialogStage.close();
    }
    
    
    
    public class RoundSelection{
        private final SimpleStringProperty player;
        private final SimpleIntegerProperty lapNum;
        private final SimpleFloatProperty time;
        private final SimpleStringProperty zipFile;
        private Date dateDriven;
        
        public RoundSelection(){
            player = new SimpleStringProperty("");
            lapNum = new SimpleIntegerProperty(0);
            time = new SimpleFloatProperty(0.0f);
            zipFile = new SimpleStringProperty("");
        }
        
        public String getPlayer(){
            return player.get();
        }
        public int getLapNum(){
            return lapNum.get();
        }
        public float getTime(){
            return time.get();
        }
        public String getZipFile(){
            return this.zipFile.get();
        }
        
        
        public void setPlayer(String player){
            this.player.set(player);
        }
        public void setLapNum(int num){
            lapNum.set(num);
        }
        public void setTime(float time){
            this.time.set(time);
        }
        public void setZipFile(String str){
            this.zipFile.set(str);
        }

        public Date getDateDriven() {
            return dateDriven;
        }

        public void setDateDriven(Date dateDriven) {
            this.dateDriven = dateDriven;
        }

        @Override
        public boolean equals(Object obj) {
            if(obj instanceof RoundSelection){
                RoundSelection o = (RoundSelection) obj;
                Boolean ret = (this.lapNum.get() == o.getLapNum());
                ret = ret && this.player.get().equals(o.getPlayer());
                ret = ret && (Float.compare(this.time.get(), o.getTime()) == 0); 
                return ret;
            }
            return false;
        }
        
        
    }
    
    public interface SaveEventHandler{
        public void OnSaveEvent();
    }
}

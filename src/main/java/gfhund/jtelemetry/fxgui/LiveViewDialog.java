/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfhund.jtelemetry.fxgui;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;


/**
 *
 * @author PhilippGL
 */
public class LiveViewDialog extends DialogFx{

    public class PlayerObj{
        public String m_name;
        public byte m_index;
        public float m_time;
        public byte m_position;
    }
    
    private PlayerObj[] m_plyObj;
    private Rectangle[] m_wheel;
    private Text[] m_wheelWear;
    private Text[] m_wheelLapsTo70;
    private Text[] m_playerName;
    private Text[] m_playerTime;
    private Text[] m_playerPosition;
    
    private byte m_frontRightTyreWear=0;
    private byte m_frontLeftTyreWear=0;
    private byte m_rearRightTyreWear=0;
    private byte m_rearLeftTyreWear=0;
    
    private byte m_currentLapNum;
    private boolean m_updateDeltaFrontRightTyre = false;
    private boolean m_updateDeltaFrontLeftTyre = false;
    private boolean m_updateDeltaRearRightTyre = false;
    private boolean m_updateDeltaRearLeftTyre = false;
    
    private byte m_deltaFrontRightTyre = 0;
    private byte m_deltaFrontLeftTyre = 0;
    private byte m_deltaRearRightTyre = 0;
    private byte m_deltaRearLeftTyre = 0;
    
    
    private byte m_frontRightTyreLastRound = -1;
    private byte m_frontLeftTyreLastRound = -1;
    private byte m_rearRightTyreLastRound = -1;
    private byte m_rearLeftTyreLastRound = -1;
    
    private float m_fuelInTank;
    private float m_deltaFuel;
    private boolean m_updateFuel = false;
    private float m_fuelInTankLastRound = -1;
    private Text m_lapsRemainingFuel;
    
    
    public LiveViewDialog(Stage parent){
        super(parent);
    }
    
    @Override
    public Scene init(Stage stage) {
        /*
        Rectangle[] rect = new Rectangle[20];
        for(int i= 0;i<20;i++){
            rect[i] = new Rectangle(i*51,100,51.0,51.0);
            rect[i].setFill(Color.AQUA);
        }
*/
        double scaleFactor = 50;//50;
        Scale scale = new Scale(scaleFactor,scaleFactor);
        Scale textScale = new Scale(2,2);
        Translate tranlate = new Translate(500, 500);
        
        
        // Car Values
        Polygon car = new Polygon();
        Double[] points = {
            0.5,2.0,
            1.0,2.0,
            1.0,2.5,
            
            -1.0,2.5,
            -1.0,2.0,
            -0.5,2.0,
            
            -0.5,-2.0,
            -1.0,-2.0,
            -1.0,-2.5,
            
            1.0,-2.5,
            1.0,-2.0,
            0.5,-2.0,
        };
        
        for(int i=0;i<points.length;i+=2){
            points[i] *= 50;
            points[i+1] *= 50;
            //points[i] += 300;
            //points[i+1] += 300;
        }

        car.getPoints().addAll(points);
        car.setFill(Color.DARKGRAY);
        if(this.m_wheelWear == null){
            this.m_wheelWear = new Text[4];
        }
        this.m_wheelWear[0] = new Text(-3.0*scaleFactor,2.00*scaleFactor,"0%");
        this.m_wheelWear[1] = new Text(-3.0*scaleFactor,-2.60*scaleFactor,"0%");
        this.m_wheelWear[2] = new Text(2.5*scaleFactor,2.00*scaleFactor,"0%");
        this.m_wheelWear[3] = new Text(2.5*scaleFactor, -2.60*scaleFactor, "0%");
        
        this.m_wheelWear[0].setFont(new Font(30));
        this.m_wheelWear[1].setFont(new Font(30));
        this.m_wheelWear[2].setFont(new Font(30));
        this.m_wheelWear[3].setFont(new Font(30));
        /*
        this.m_wheelWear[0].getTransforms().add(textScale);
        this.m_wheelWear[1].getTransforms().add(textScale);
        this.m_wheelWear[2].getTransforms().add(textScale);
        this.m_wheelWear[3].getTransforms().add(textScale);
        */
        
        if(this.m_wheel == null){
            this.m_wheel = new Rectangle[4];
        }
        m_wheel[0] = new Rectangle(-2.0,1.75,1.0,1.0);
        m_wheel[1] = new Rectangle(-2.0,-2.75,1.0,1.0);
        m_wheel[2] = new Rectangle(1.0,1.75,1.0,1.0);
        m_wheel[3] = new Rectangle(1.0,-2.75,1.0,1.0);
        
        m_wheel[0].getTransforms().add(scale);
        m_wheel[1].getTransforms().add(scale);
        m_wheel[2].getTransforms().add(scale);
        m_wheel[3].getTransforms().add(scale);
        for (Rectangle m_wheel1 : m_wheel) {
            m_wheel1.setFill(Color.GREEN);
        }
        m_wheelLapsTo70 = new Text[4];
        m_wheelLapsTo70[0] = new Text(-3.0*scaleFactor,2.80*scaleFactor,"-");
        m_wheelLapsTo70[1] = new Text(-3.0*scaleFactor,-1.80*scaleFactor,"-");
        m_wheelLapsTo70[2] = new Text(2.5*scaleFactor,2.80*scaleFactor,"-");
        m_wheelLapsTo70[3] = new Text(2.5*scaleFactor,-1.80*scaleFactor,"-");
        for (Text m_wheelLapsTo701 : m_wheelLapsTo70) {
            m_wheelLapsTo701.setFont(new Font(30));
        }
        
        Group wheelGrp = new Group();
        wheelGrp.getChildren().addAll(m_wheel);
        wheelGrp.getChildren().addAll(m_wheelWear);
        wheelGrp.getChildren().addAll(m_wheelLapsTo70);
        
        Group carGrp = new Group();
        carGrp.getChildren().add(car);
        carGrp.getChildren().add(wheelGrp);
        carGrp.getTransforms().addAll(tranlate);//scale,
        
        //Player Data
        Group playerGroup = new Group();
        
        if(this.m_plyObj == null){
            this.m_plyObj = new PlayerObj[20];
        }
        for(int i = 0;i<20;i++){
            this.m_plyObj[i] = new PlayerObj();
            this.m_plyObj[i].m_position = (byte)(i+1);
        }
        
        if(this.m_playerName == null){
            this.m_playerName = new Text[20];
        }
        for(int i=0;i<20;i++){
            this.m_playerName[i] = new Text(100, 50+i*35, "player"+i);
        }
        playerGroup.getChildren().addAll(this.m_playerName);
        
        if(this.m_playerTime == null){
            this.m_playerTime = new Text[20];
        }
        for(int i=0;i<20;i++){
            this.m_playerTime[i] = new Text(200, 50+i*35, "Zeit");
        }
        playerGroup.getChildren().addAll(this.m_playerTime);
        
        if(this.m_playerPosition == null){
            this.m_playerPosition = new Text[20];
        }
        for(int i=0;i<20;i++){
            this.m_playerPosition[i] = new Text(50, 50+i*35, ""+(i+1));
            
        }
        playerGroup.getChildren().addAll(this.m_playerPosition);
        
        m_lapsRemainingFuel = new Text(850,650,"-");
        m_lapsRemainingFuel.setFont(new Font(30));
        
        Group grp = new Group();
        grp.getChildren().add(carGrp);
        grp.getChildren().add(playerGroup);
        grp.getChildren().add(m_lapsRemainingFuel);
        
        Scene scene = new Scene(grp,1024,768);
        return scene;
    }
    /*
    0 - Rear Left
    1 - Rear Right
    2 Front Left
    4 Front Right
    */
    public synchronized void setRLTyreWear(byte rlTyreWear){
        this.m_rearLeftTyreWear = rlTyreWear;
        this.m_wheelWear[0].setText(""+rlTyreWear+"%");
        if(this.m_updateDeltaRearLeftTyre == true){
            this.m_updateDeltaRearLeftTyre = false;
            if(this.m_rearLeftTyreLastRound != -1){
                this.m_deltaRearLeftTyre = (byte)(rlTyreWear - this.m_rearLeftTyreLastRound);
                float lapsToDriveTo70 = ((70 - rlTyreWear) / this.m_deltaRearLeftTyre);
                this.m_wheelLapsTo70[0].setText("" + lapsToDriveTo70);
            }
            this.m_rearLeftTyreLastRound = rlTyreWear;
            
        }
    }
    public synchronized void setRRTyreWear(byte rrTyreWear){
        this.m_rearRightTyreWear = rrTyreWear;
        this.m_wheelWear[1].setText(""+rrTyreWear+"%");
        if(this.m_updateDeltaRearRightTyre == true){
            this.m_updateDeltaRearRightTyre = false;
            if(this.m_rearRightTyreLastRound != -1){
                this.m_deltaRearRightTyre = (byte)(rrTyreWear - this.m_rearRightTyreLastRound);
                float lapsToDriveTo70 = ((70 - rrTyreWear) / this.m_rearRightTyreLastRound);
                this.m_wheelLapsTo70[1].setText("" + lapsToDriveTo70);
            }
            this.m_rearRightTyreLastRound = rrTyreWear;
        }
    }
    public synchronized void setFLTyreWear(byte flTyreWear){
        this.m_frontLeftTyreWear = flTyreWear;
        this.m_wheelWear[2].setText(""+flTyreWear+"%");
        if(this.m_updateDeltaFrontLeftTyre == true){
            this.m_updateDeltaFrontLeftTyre = false;
            if(this.m_frontLeftTyreLastRound != -1){
                this.m_deltaFrontLeftTyre = (byte)(flTyreWear - this.m_frontLeftTyreLastRound);
                float lapsToDriveTo70 = ((70 - flTyreWear) / this.m_frontLeftTyreLastRound);
                this.m_wheelLapsTo70[2].setText("" + lapsToDriveTo70);
            }
            this.m_frontLeftTyreLastRound = flTyreWear;
        }
    }
    public synchronized void setFRTyreWear(byte frTyreWear){
        this.m_frontRightTyreWear = frTyreWear;
        this.m_wheelWear[3].setText(""+frTyreWear+"%");
        if(this.m_updateDeltaFrontRightTyre == true){
            this.m_updateDeltaFrontRightTyre = false;
            if(this.m_frontRightTyreLastRound != -1){
                this.m_deltaFrontRightTyre = (byte)(frTyreWear - this.m_frontRightTyreLastRound);
                float lapsToDriveTo70 = ((70 - frTyreWear) / this.m_frontRightTyreLastRound);
                this.m_wheelLapsTo70[1].setText("" + lapsToDriveTo70);
            }
            this.m_frontRightTyreLastRound = frTyreWear;
        }
    }
    
    public synchronized void setFuel(float fuel){
        this.m_fuelInTank = fuel;
        if(this.m_updateFuel){
            this.m_updateFuel = false;
            if(this.m_fuelInTankLastRound != -1){
                this.m_deltaFuel = this.m_fuelInTankLastRound - fuel;
                float remainingFuel = fuel / this.m_deltaFuel;
                m_lapsRemainingFuel.setText(""+remainingFuel);
            }
            this.m_fuelInTankLastRound = fuel;
        }
    }
    
    public synchronized void setPlayerName(int index,String name){
        this.m_plyObj[index].m_name = name;
        byte playerPos = this.m_plyObj[index].m_position;
        this.m_playerName[playerPos].setText(name);
    }
    public synchronized void setPlayerTime(int index,float time){
        if(this.m_plyObj == null){
            this.m_plyObj = new PlayerObj[20];
        }
        if(this.m_plyObj[index] == null){
            this.m_plyObj[index] = new PlayerObj();
            this.m_plyObj[index].m_position = (byte)index;
        }
        this.m_plyObj[index].m_time = time;
        byte playerPos = this.m_plyObj[index].m_position;
        if(playerPos > 20 || playerPos <= 0){
            return;
        }
        this.m_playerTime[playerPos-1].setText("" + time);
    }
    public synchronized void setPlayerPosition(int index,byte pos){
        this.m_plyObj[index].m_position = pos;
    }
    
    public synchronized void setLapNum( byte lapNum){
        this.m_currentLapNum = lapNum;
        this.m_updateDeltaFrontLeftTyre = true;
        this.m_updateDeltaFrontRightTyre = true;
        this.m_updateDeltaRearLeftTyre = true;
        this.m_updateDeltaRearRightTyre = true;
        this.m_updateFuel = true;
    }
}

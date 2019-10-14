/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfhund.jtelemetry.fxgui;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
//import com.sun.javafx.jmx.MXNodeAlgorithm;
//import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;
import com.sun.javafx.sg.prism.NGRegion;
import com.sun.prism.Graphics;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import com.sun.javafx.geom.transform.Affine2D;
import com.sun.prism.paint.Color;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Circle;
//import com.sun.javafx.sg.PGNode;
import javafx.animation.Transition;
import javafx.util.Duration;
import com.sun.javafx.scene.DirtyBits;
import gfhund.jtelemetry.Vector3D;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.PathElement;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author PhilippGL
 */
public class TrackView extends Region {

    //private PGTrackView pgNode;
    private Group drawArea = new Group();
    private Pane m_pane = new Pane();
    private Path line = new Path();
    public static int WIDTH = 200;
    public static int HEIGHT = 200;
    
    javafx.scene.paint.Color[] colors = {javafx.scene.paint.Color.BLACK,javafx.scene.paint.Color.ORANGERED,javafx.scene.paint.Color.GREEN,javafx.scene.paint.Color.ORANGE,javafx.scene.paint.Color.BROWN,javafx.scene.paint.Color.BLUEVIOLET,javafx.scene.paint.Color.CYAN,javafx.scene.paint.Color.DEEPPINK,javafx.scene.paint.Color.INDIGO,javafx.scene.paint.Color.CADETBLUE};
    ArrayList<TrackPoint> points = new ArrayList<>(); 
    
    ObservableList<TrackRound> m_data;
    Circle[] circles = new Circle[10];
    
    public TrackView(){
        setCache(false);
        setPrefWidth(200);
        setPrefHeight(200);
        Rectangle rect = new Rectangle(0, 0, WIDTH, HEIGHT);
        rect.setFill(javafx.scene.paint.Color.WHITE);
        
        for(int i = 0; i< circles.length;i++){
            circles[i] = new Circle(0,0,4);
            circles[i].setVisible(false);
        }
        
        drawArea.getChildren().addAll(rect,line);
        drawArea.getChildren().addAll(circles);
        m_pane.getChildren().add(drawArea);
        getChildren().add(m_pane);
        
    }
    
    @Override
    public ObservableList<Node> getChildren(){
        return super.getChildren();
    }
    
    @Override
    public double computePrefWidth(double height){
        return height;
    }
    
    @Override
    public double computePrefHeight(double width){
        return width;
    }
    
    @Override
    protected void layoutChildren(){
        /*
        line.getElements().addAll(
                new MoveTo(0, 0),
                new LineTo(200, 200),
                new ClosePath()
        );
        */
        
        
        redraw();
        
    }
    
    protected void redraw(){
        line.getElements().clear();
        
        float xMax = Float.MIN_VALUE;
        float xMin = Float.MAX_VALUE;
        float yMax = Float.MIN_VALUE;
        float yMin = Float.MAX_VALUE;
        for(TrackRound round:m_data){
            for(TrackPoint point:round.rounds){
                if(xMax < point.posX){
                    xMax = point.posX;
                }
                if(xMin > point.posX){
                    xMin = point.posX;
                }
                if(yMax < point.posY){
                    yMax = point.posY;
                }
                if(yMin > point.posY){
                    yMin = point.posY;
                }
            }
        }
        float scalingX = WIDTH/(xMax-xMin);
        float scalingY = HEIGHT/(yMax-yMin);
        for(TrackRound round:m_data){
            Boolean isFirst = true;
            ArrayList<PathElement> pathElements = new ArrayList<>();
            for(TrackPoint point:round.rounds){
                float posX = (point.posX - xMin)*scalingX;
                float posY = (point.posY - yMin)*scalingY;
                if(isFirst){
                    isFirst = false;
                    
                    pathElements.add(new MoveTo(posX, posY));
                }
                else{
                    pathElements.add(new LineTo(posX, posY));
                }
            }
            pathElements.add(new ClosePath());
            line.getElements().addAll(pathElements);
        }
        int i=0;
        for(TrackPoint p:points){
            float posX = (p.posX - xMin) * scalingX;
            float posY = (p.posY - yMin) * scalingY;
            circles[i].setCenterX(posX);
            circles[i].setCenterY(posY);
            circles[i].setVisible(true);
            i++;
        }
    }
    
    public void setData(ObservableList<TrackRound> data){
        m_data = data;
        data.addListener(new ListChangeListener<TrackRound>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends TrackRound> c) {
                redraw();
            }
        });
    }
    
    public void clearPoints(){
        points.clear();
        redraw();
    }
    public void addPoint(TrackPoint e){
        points.add(e);
        redraw();
    }
    /*
    public static TrackPoint createTrackPoint(float x,float y){
        //return new TrackPoint();
    }
    */
    /*
    @Override
    public PGNode impl_createPGNode() {
        pgNode = new PGTrackView();

        // bind this to an fps property
        double framerate = 60;
        new Transition(framerate) {
                {
                        setCycleDuration(Duration.INDEFINITE);
                }
                @Override
                protected void interpolate(double frac) {
                        impl_markDirty(DirtyBits.NODE_CONTENTS);
                }
        }.play();

        return pgNode;
    }
*/
    public static class TrackPoint{
        float posX;
        float posY;
        
        
        public TrackPoint(float x,float y){
            this.posX = x;
            this.posY = y;
        }


        public float getPosX() {
            return posX;
        }

        public void setPosX(float posX) {
            this.posX = posX;
        }

        public float getPosY() {
            return posY;
        }

        public void setPosY(float posY) {
            this.posY = posY;
        }
    }
    
    public static class TrackRound{
        ObservableList<TrackPoint> rounds = FXCollections.observableArrayList();

        public void addTrackPoint(TrackPoint point){
            rounds.add(point);
        }
        public void setData(ObservableList<TrackPoint> points){
            rounds = points;
        }
        
    }
}

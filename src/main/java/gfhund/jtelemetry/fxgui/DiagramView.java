/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfhund.jtelemetry.fxgui;

import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ScrollBar;
import javafx.scene.text.Text;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Button;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.Line;
import javafx.collections.ListChangeListener;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.PathElement;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author PhilippGL
 */
public class DiagramView extends Region{
    private ObservableList<DiagrammLine> m_data;
    private Group drawArea = new Group();
    //private ScrollPane m_pane = new ScrollPane();
    private Pane m_pane = new Pane();
    private VBox m_vBox = new VBox();
    
    private Line rightLine;
    private Line leftLine;
    private Line bottomLine;
    private Line[] diagrammValueLines;
    private Line[] diagrammXValuesLines;
    private Path[] diagramm;
    ScrollBar bar;
    private Text[] legendY;
    private Text[] legendX;
    private Text[] lineDesc;
    
    private static int PREF_WIDTH = 470;
    private static int PREF_HEIGHT = 350;
    private static int PADDING = 60;
    private static int LINE_VALUE_DISTANCE = 15;
    private static int LINE_VALUE_X_DISTANCE = 30;
    private static int LINE_VALUE_LENGTH = 5;
    private static int TEXT_HEIGHT = 20;
    private static int TEXT_WIDTH = 30;
    
    private int zoomValue = 100;
    
    public DiagramView(){
        setCache(false);
        setPrefWidth(DiagramView.PREF_WIDTH);
        setPrefHeight(DiagramView.PREF_HEIGHT);
        
        VBox verticallyBox = new VBox();
        
        //both y axis and the bottom x axis
        rightLine = new Line(DiagramView.PREF_WIDTH - DiagramView.PADDING, DiagramView.PADDING, DiagramView.PREF_WIDTH - DiagramView.PADDING, DiagramView.PREF_HEIGHT-DiagramView.PADDING);
        rightLine.setStroke(Color.GREEN);
        leftLine = new Line(DiagramView.PADDING,DiagramView.PADDING,DiagramView.PADDING,DiagramView.PREF_HEIGHT - DiagramView.PADDING);
        leftLine.setStroke(Color.RED);
        bottomLine = new Line(DiagramView.PADDING,DiagramView.PREF_HEIGHT - DiagramView.PADDING,DiagramView.PREF_WIDTH - DiagramView.PADDING,DiagramView.PREF_HEIGHT - DiagramView.PADDING);
        
        //calculation for the y legend
        int drawHeight = PREF_HEIGHT - 2*PADDING;
        int numLines = drawHeight / LINE_VALUE_DISTANCE;
        diagrammValueLines = new Line[numLines];
        for(int i=1;i <=diagrammValueLines.length;i++){
            int yPos = (PREF_HEIGHT - PADDING)-i*LINE_VALUE_DISTANCE;
            diagrammValueLines[i-1] = new Line(PADDING, yPos, PADDING - LINE_VALUE_LENGTH, yPos);
        }
        //calculations for the x legend
        int drawXWidth = PREF_WIDTH - 2 * PADDING;
        double fNumXLines = drawXWidth / LINE_VALUE_X_DISTANCE;
        int numXLines = (int)fNumXLines;
        numXLines++;
        diagrammXValuesLines = new Line[numXLines];
        for(int i=0;i<numXLines;i++){
            diagrammXValuesLines[i] = new Line(
                    PADDING+i*LINE_VALUE_X_DISTANCE,
                    PREF_HEIGHT-PADDING,
                    PADDING+i*LINE_VALUE_X_DISTANCE,
                    PREF_HEIGHT - PADDING + LINE_VALUE_LENGTH
            );
        }
        
        
        bar = new ScrollBar();
        bar.setMinWidth(PREF_WIDTH);
        bar.setMin(0);
        bar.setMax(5);
        bar.setVisibleAmount(5);
        bar.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                redraw();
            }
        });
        
        diagramm = new Path[10];
        Color[] colors = {Color.BLACK,Color.LIGHTBLUE,Color.GREEN,Color.ORANGE,Color.BROWN,Color.BLUEVIOLET,Color.CYAN,Color.DEEPPINK,Color.INDIGO,Color.CADETBLUE};
        for(int i=0;i<diagramm.length;i++){
            diagramm[i] = new Path();
            diagramm[i].setStroke(colors[i]);
        }
        boolean isFirst = true;
        for(int i=0 ;i<5;i++){
            if(isFirst){
                diagramm[0].getElements().add(new MoveTo(50, 50));
                isFirst = false;
            }
            else{
                diagramm[0].getElements().add(new LineTo(50 + i*10, 50));
            }
        }
        
        this.legendY = new Text[drawHeight / LINE_VALUE_DISTANCE];
        for(int i=0;i<legendY.length;i++){
            legendY[i] = new Text(PADDING - TEXT_WIDTH, (legendY.length - i)*LINE_VALUE_DISTANCE + PADDING, "" + i);
        }
        
        
        this.legendX = new Text[numXLines];
        for(int i=0;i<legendX.length;i++){
            legendX[i] = new Text(PADDING + i*LINE_VALUE_X_DISTANCE,(PREF_HEIGHT - PADDING) + TEXT_HEIGHT + LINE_VALUE_LENGTH,""+i);
        }
        
        
        //verticallyBox.getChildren().add(diagramm);
        //verticallyBox.getChildren().add(bar);
        //verticallyBox.getChildren().addAll(rightLine,leftLine,bottomLine);
        //verticallyBox.getChildren().add(rightLine);
        //verticallyBox.getChildren().add(leftLine);
        //verticallyBox.getChildren().add(bottomLine);
        //m_pane.setContent(diagramm);
        //m_pane.setPrefSize(200, 200);
        
        //drawArea.getChildren().addAll(verticallyBox);
        Rectangle rect = new Rectangle(0, 0, PREF_WIDTH, PREF_HEIGHT);
        rect.setFill(Color.WHITE);
        drawArea.getChildren().add(rect);
        drawArea.getChildren().addAll(rightLine,leftLine,bottomLine);
        drawArea.getChildren().addAll(this.diagrammValueLines);
        drawArea.getChildren().addAll(legendY);
        drawArea.getChildren().addAll(diagrammXValuesLines);
        drawArea.getChildren().addAll(legendX);
        drawArea.getChildren().addAll(diagramm);
        
        drawArea.setOnScroll(new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                onScroll(event.getDeltaY());
            }
        });
        
        //m_pane.getChildren().add(drawArea);
        //m_pane.getChildren().add(bar);
        
        //getChildren().add(m_pane);

        
        m_vBox.getChildren().add(drawArea);
        m_vBox.getChildren().add(bar);
        
        getChildren().add(m_vBox);
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
    }
    
    protected void onDiagramScroll(Boolean zoomIn){
        
    }
    
    protected void redraw(){
        for(int i=0;i<diagramm.length;i++){
            diagramm[i].getElements().clear();
        }
        //for(int i=0;i<d)
        
        float xMax = Float.MIN_VALUE;
        float xMin = Float.MAX_VALUE;
        float yMax = Float.MIN_VALUE;
        float yMin = Float.MAX_VALUE;
        for(DiagrammLine round:m_data){
            for(DiagrammPoint point:round.points){
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
        this.bar.setMax(xMax);
        this.bar.setMin(xMin);
        
        if(this.zoomValue <100){
            float delta = xMax - xMin;
            float remainingLength = delta * ((float)this.zoomValue / 100.0f);
            float deltaMinMax = delta - remainingLength;
            xMin = xMin + deltaMinMax / 2;
            xMax = xMax - deltaMinMax / 2;
            this.bar.setVisibleAmount(xMax - xMin);
            double scrollBarValue = bar.getValue();
            xMin = (float)scrollBarValue;
            xMax = (float)(scrollBarValue + bar.getVisibleAmount());
            
        }
        else{
            this.bar.setVisibleAmount(xMax - xMin);
        }
        
                
        //Choose the best Fitting Y Axis
        float minRangeYAxis;
        float maxRangeYAxis;
        int drawHeight = PREF_HEIGHT - 2*PADDING;
        float multiplierSteps = yMax / this.legendY.length;
        float steps = drawHeight / yMax ;
        for(int i=0;i<this.legendY.length;i++){
            this.legendY[i].setText("" + (int)(multiplierSteps * (i)));
        }
        
        //get X value
        float drawXWidth = PREF_WIDTH - 2 * PADDING;
        float stepsX = drawXWidth / (xMax - xMin) ;
        float multiplierXSteps = (xMax - xMin) / this.legendX.length;
        for(int i=0;i<this.legendX.length;i++){
            float temp = xMin + multiplierXSteps * (i+1);
            this.legendX[i].setText("" + (int)temp);
        }
        
        int i=0;
        int xOffset = (int)(xMin * stepsX);
        for(DiagrammLine round:m_data){
            Boolean isFirst = true;
            ArrayList<PathElement> pathElements = new ArrayList<>();
            for(DiagrammPoint point:round.points){
                if(point.posX < xMin || point.posX > xMax){
                    continue;
                }
                float posX = PADDING + ((point.posX*stepsX)-xOffset);
                float posY = (PADDING + drawHeight) - point.posY * steps;
                if(isFirst){
                    isFirst = false;
                    pathElements.add(new MoveTo(posX,posY));
                }
                else{
                    pathElements.add(new LineTo(posX,posY));
                }
            }
            diagramm[i].getElements().addAll(pathElements);
            i++;
        }
        /*
        for(TrackView.TrackRound round:m_data){
            Boolean isFirst = true;
            ArrayList<PathElement> pathElements = new ArrayList<>();
            for(TrackView.TrackPoint point:round.rounds){
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
*/
        
    }
    
    public void setData(ObservableList<DiagrammLine> data){
        m_data = data;
        data.addListener(new ListChangeListener<DiagrammLine>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends DiagrammLine> c) {
                redraw();
            }
        });
    }
    
    protected void onScroll(double deltaY){
        //System.out.println(""+deltaY);
        if(deltaY < 0){
            this.zoomValue += 10;
        }
        else{
            this.zoomValue -= 10;
        }
        //this.zoomValue += deltaY;
        if(this.zoomValue >= 100){
            this.zoomValue = 100;
        }
        if(this.zoomValue <= 0) {
            this.zoomValue = 0;
        }
        redraw();
    }
    
    public static class DiagrammLine{
        ObservableList<DiagrammPoint> points = FXCollections.observableArrayList();
        String name;

        public DiagrammLine() {
        }
        
        public void addTrackPoint(DiagrammPoint point){
            this.points.add(point);
        }
        public void setData(ObservableList<DiagrammPoint> points){
            this.points = points;
        }
        public void setName(String name){
            this.name = name;
        }
        public String getName(){
            return this.name;
        }
    }
    
    public static class DiagrammPoint{
        float posX;
        float posY;
        
        
        public DiagrammPoint(float x,float y){
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
}

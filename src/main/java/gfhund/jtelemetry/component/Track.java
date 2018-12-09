package gfhund.jtelemetry.component;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.util.*;
import javax.swing.*;

public class Track extends JComponent{
    private Dimension mPreferredSize = new Dimension(400,300);
    private Dimension mMinimumSize = new Dimension(100,100);
    private Dimension mMaximumSize = new Dimension(1000,1000);
    private Dimension mSize = new Dimension(100,100);
    
    public Track(){
        setForeground(Color.black);
        setBackground(Color.black);
    }

    public void paintComponent(Graphics g){
        //hallo
        g.setColor(Color.blue);
        g.fillRect(0,0,mPreferredSize.width,mPreferredSize.height);
    }

    @Override
    public Dimension getPreferredSize( ) {
        return this.mPreferredSize;
    }

    @Override
    public Dimension getSize( ) {
        return this.mSize;
    }

    @Override
    public Dimension getMinimumSize(){
        return this.mMinimumSize;
    }

    @Override
    public Dimension getMaximumSize(){
        return this.mMaximumSize;
    }

    @Override
    public void setPreferredSize(Dimension d){
        this.mPreferredSize = d;
    }
    @Override 
    public void setMinimumSize(Dimension d){
        this.mMinimumSize = d;
    }

    @Override 
    public void setMaximumSize(Dimension d){
        this.mMaximumSize = d;
    }

    @Override
    public void setSize(Dimension d){
        this.mSize = d;
    }
}
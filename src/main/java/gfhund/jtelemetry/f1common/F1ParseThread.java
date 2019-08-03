/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfhund.jtelemetry.f1common;

import gfhund.jtelemetry.commontelemetry.AbstractPacket;
import gfhund.jtelemetry.f1y18.F1Y2018ParseException;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.ArrayList;
import javafx.concurrent.Task;
/**
 *
 * @author PhilippHolzmann
 */
public abstract class F1ParseThread implements Runnable {
    
    protected ArrayList<F1ParseResultEvent> m_events = new ArrayList<>();
    protected ConcurrentLinkedQueue<byte[]> m_inputQueue = new ConcurrentLinkedQueue<>();
    
    public void addParseResultEvent(F1ParseResultEvent event){
        m_events.add(event);
    }
    
    public void addRaw(byte[] raw){
        //System.out.println("Recived Raw Bytes");
        if(this.m_inputQueue == null){
            this.m_inputQueue = new ConcurrentLinkedQueue<>();
        }
        if(raw == null){
            System.out.println("recived Null");
            return;
        }
        this.m_inputQueue.add(raw);
    }
}

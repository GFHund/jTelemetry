/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfhund.jtelemetry.f1y19;

import gfhund.jtelemetry.commontelemetry.AbstractPacket;
import gfhund.jtelemetry.f1common.PacketBuilder;
import gfhund.jtelemetry.f1common.ParseException;
import gfhund.jtelemetry.f1y18.F1Y2018ParseResultEvent;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author PhilippGL
 */
public class F1Y2019ParseThread implements Runnable {
    private final ReentrantLock m_lock;
    private Condition m_cond;
    private ConcurrentLinkedQueue<byte[]> m_inputQueue = new ConcurrentLinkedQueue<>();
    private ArrayList<F1Y2018ParseResultEvent> m_events = new ArrayList<>();
    public F1Y2019ParseThread(ReentrantLock lock,Condition cond){
        this.m_lock = lock;
        this.m_cond = cond;
    }
    
    @Override
    //public AbstractPacket call() {
    public void run(){
        while(!Thread.currentThread().isInterrupted()){
            this.m_lock.lock();
            try{
                m_cond.await();
            }catch(InterruptedException e){
                Thread.currentThread().interrupt();
                break;
            }
            finally{
                this.m_lock.unlock();
            }
            while(!this.m_inputQueue.isEmpty()){
                byte[] raw = this.m_inputQueue.poll();
                try{
                    AbstractPacket packet = PacketBuilder.parseUDPPacket2019(raw);
                    for(F1Y2018ParseResultEvent event:this.m_events){
                        event.resultEvent(packet);
                    }
                }catch(ParseException e){
                    continue;
                }
                
            }
        }
        //return null;
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
    public void addParseResultEvent(F1Y2018ParseResultEvent event){
        m_events.add(event);
    }
}

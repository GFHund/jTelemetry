/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfhund.jtelemetry.network;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.ConcurrentLinkedQueue;
import gfhund.jtelemetry.f1y18.PacketBuilder;
import gfhund.jtelemetry.f1y18.AbstractPacket;
import gfhund.jtelemetry.f1y18.F1Y2018ParseException;
import java.util.ArrayList;

/**
 *
 * @author PhilippGL
 */
public class F1Y2018ParseThread implements Runnable{

    private final ReentrantLock m_lock;
    private ConcurrentLinkedQueue<byte[]> m_inputQueue = new ConcurrentLinkedQueue<>();
    private ArrayList<F1Y2018ParseResultEvent> m_events = new ArrayList<>();
    public F1Y2018ParseThread(ReentrantLock lock){
        this.m_lock = lock;
    }
    
    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()){
            Condition cond = this.m_lock.newCondition();
            this.m_lock.lock();
            try{
                cond.await();
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
                    AbstractPacket packet = PacketBuilder.parseUDPPacket(raw);
                    for(F1Y2018ParseResultEvent event:this.m_events){
                        event.resultEvent(packet);
                    }
                }catch(F1Y2018ParseException e){
                    continue;
                }
                
            }
        }
    }
    public void addRaw(byte[] raw){
        System.out.println("Recived Raw Bytes");
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

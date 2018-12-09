/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfhund.jtelemetry.network;

import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.DatagramPacket;
import java.net.SocketTimeoutException;
import java.util.concurrent.locks.Condition;
import java.util.ArrayList;
/**
 *
 * @author PhilippGL
 */
public class GameNetworkConnection extends Thread {

    private final ReentrantLock m_lock;
    private int m_port;
    private int m_maxLength;
    private ArrayList<ReceiveEvent> m_events = new ArrayList<>();
    public GameNetworkConnection(ReentrantLock lock,int port,int maxLength){
        m_lock = lock;
        this.m_port = port;
        this.m_maxLength = maxLength;
    }
    @Override
    public void run() {
        try(DatagramSocket socket = new DatagramSocket(this.m_port)){
            socket.setSoTimeout(1000);//I use this tolet the thread regulary over the interupt signal
            while(!Thread.currentThread().isInterrupted()){
                Condition cond = this.m_lock.newCondition();
                DatagramPacket packet = new DatagramPacket(new byte[this.m_maxLength],this.m_maxLength);
                try{
                    socket.receive(packet);
                    System.out.println("recived Package");
                    for(ReceiveEvent e:this.m_events){
                        e.onReceive(packet.getData());
                    }
                    cond.signalAll();
                }
                catch(SocketTimeoutException e){
                    System.out.println("recived timeout");
                }
                catch(IOException e){
                    //throw e;
                }
                
                
            }
        }catch(SocketException e){
            
        }
        
    }
    
    public void addReciveEvent(ReceiveEvent e){
        m_events.add(e);
    }
    
    public void shutdown(){
        
    }
    
}

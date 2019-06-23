/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfhund.jtelemetry.f1y19;

/**
 *
 * @author PhilippHolzmann
 */
public class PacketMotionData extends gfhund.jtelemetry.f1y18.PacketMotionData {
    protected Header m_header19;
    protected CarMotionData[] m_carMotionData19 = new CarMotionData[20];
    
    public void setHeader19(Header head){
        this.m_header19 = head;
    }
    public Header getHeader19(){
        return this.m_header19;
    }
    public void setCarMotionData19(int i,CarMotionData value){
        this.m_carMotionData19[i] = value;
    }
    public CarMotionData getCarMotionData(int i){
        return this.m_carMotionData19[i];
    }
}

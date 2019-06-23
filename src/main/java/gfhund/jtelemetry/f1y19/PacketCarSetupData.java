/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfhund.jtelemetry.f1y19;
import gfhund.jtelemetry.commontelemetry.AbstractPacket;

/**
 *
 * @author PhilippHolzmann
 */
public class PacketCarSetupData {
    protected Header m_header19;
    protected CarSetupData[] m_carSetup = new CarSetupData[20];

    public Header getHeader19() {
        return m_header19;
    }

    public void setHeader(Header m_header) {
        this.m_header19 = m_header;
    }
    
    public void setCarSetupData(int i,CarSetupData data){
        this.m_carSetup[i] = data;
    }
    public CarSetupData getCarSetupData(int i){
        return this.m_carSetup[i];
    }
    
    public static int getSize(){
        return 842;
    }
}

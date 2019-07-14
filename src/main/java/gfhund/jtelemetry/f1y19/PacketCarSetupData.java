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
public class PacketCarSetupData extends AbstractPacket {
    protected Header header19;
    protected CarSetupData[] carSetupData = new CarSetupData[20];

    public Header getHeader19() {
        return header19;
    }

    public void setHeader(Header m_header) {
        this.header19 = m_header;
    }
    
    public void setCarSetupData(int i,CarSetupData data){
        this.carSetupData[i] = data;
    }
    public CarSetupData getCarSetupData(int i){
        return this.carSetupData[i];
    }
    
    public static int getSize(){
        return 842;
    }
}

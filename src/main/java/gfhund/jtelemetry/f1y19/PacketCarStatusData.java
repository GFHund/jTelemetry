/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfhund.jtelemetry.f1y19;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import gfhund.jtelemetry.commontelemetry.AbstractPacket;

/**
 *
 * @author PhilippHolzmann
 */
public class PacketCarStatusData extends AbstractPacket {
    private Header m_header19;
    private CarStatusData[] m_carStatusData = new CarStatusData[20];

    public Header getHeader19() {
        return m_header19;
    }

    public void setHeader(Header m_header) {
        this.m_header19 = m_header;
    }
    
    public void setCarStatusData(int i,CarStatusData data){
        this.m_carStatusData[i] = data;
    }
    public CarStatusData getCarStatusData(int i){
        return this.m_carStatusData[i];
    }
    
    public static int getSize(){
        return 1143;
    }
    /*
    public byte[] getBytes(){
        ByteBuffer ret = ByteBuffer.allocate(gfhund.jtelemetry.f1y18.PacketCarStatusData.getSize());
        ret.order(ByteOrder.LITTLE_ENDIAN);
        byte[] headerData = this.m_header19.getBytes();
        for(int i=0;i<headerData.length;i++){
            ret.put(i,headerData[i]);
        }
        for(int i=0;i<20;i++){
            byte[] carStatusData = this.m_carSetup[i].getBytes();
            int offset = Header.getSize()+i*CarSetupData.getSize();
            for(int k=0;k<carStatusData.length;k++){
                ret.put(offset+k,carStatusData[k]);
            }
        }
        return ret.array();
    }
*/
}

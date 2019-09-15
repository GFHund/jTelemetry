/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfhund.jtelemetry.f1y19;
import gfhund.jtelemetry.commontelemetry.AbstractPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

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

    public void setHeader19(Header m_header) {
        this.header19 = m_header;
    }
    
    public void setCarSetupData(int i,CarSetupData data){
        this.carSetupData[i] = data;
    }
    public CarSetupData getCarSetupData(int i){
        return this.carSetupData[i];
    }
    public CarSetupData[] getCarSetupData(){
        return this.carSetupData;
    }
    
    public static int getSize(){
        return 843;
    }
    
    public byte[] getBytes(){
        ByteBuffer ret = ByteBuffer.allocate(getSize());
        ret.order(ByteOrder.LITTLE_ENDIAN);
        byte[] headerData = this.header19.getBytes();
        for(int i=0;i<headerData.length;i++){
            ret.put(i,headerData[i]);
        }
        for(int i=0;i<20;i++){
            byte[] carStatusData = this.carSetupData[i].getBytes();
            int offset = gfhund.jtelemetry.f1y19.Header.getSize()+i*CarSetupData.getSize();
            for(int k=0;k<carStatusData.length;k++){
                ret.put(offset+k,carStatusData[k]);
            }
        }
        return ret.array();
    }
}

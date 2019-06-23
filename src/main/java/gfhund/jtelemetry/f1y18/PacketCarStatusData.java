package gfhund.jtelemetry.f1y18;

import gfhund.jtelemetry.commontelemetry.AbstractPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class PacketCarStatusData extends AbstractPacket{
    private Header m_header;
    private CarStatusData[] m_carStatusData = new CarStatusData[20];

    public Header getHeader() {
        return m_header;
    }

    public void setHeader(Header m_header) {
        this.m_header = m_header;
    }
    
    public void setCarStatusData(int i,CarStatusData data){
        this.m_carStatusData[i] = data;
    }
    public CarStatusData getCarStatusData(int i){
        return this.m_carStatusData[i];
    }
    
    public static int getSize(){
        return 1061;
    }
    
    public byte[] getBytes(){
        ByteBuffer ret = ByteBuffer.allocate(PacketCarStatusData.getSize());
        ret.order(ByteOrder.LITTLE_ENDIAN);
        byte[] headerData = this.m_header.getBytes();
        for(int i=0;i<headerData.length;i++){
            ret.put(i,headerData[i]);
        }
        for(int i=0;i<20;i++){
            byte[] carStatusData = this.m_carStatusData[i].getBytes();
            int offset = Header.getSize()+i*CarStatusData.getSize();
            for(int k=0;k<carStatusData.length;k++){
                ret.put(offset+k,carStatusData[k]);
            }
        }
        return ret.array();
    }
}
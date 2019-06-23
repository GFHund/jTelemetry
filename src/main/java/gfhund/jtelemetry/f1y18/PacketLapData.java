package gfhund.jtelemetry.f1y18;
import gfhund.jtelemetry.commontelemetry.AbstractPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
public class PacketLapData extends AbstractPacket{
    private Header m_header;
    private LapData[] m_lapData = new LapData[20];

    public void setHeader(Header head){
        this.m_header = head;
    }
    public void setLapData(int i,LapData lapData){
        this.m_lapData[i] = lapData;
    }
    
    public Header getHeader(){
        return this.m_header;
    }
    public LapData getLapData(int index){
        return this.m_lapData[index];
    }
    
    public byte[] getBytes(){
        ByteBuffer ret = ByteBuffer.allocate(841);
        ret.order(ByteOrder.LITTLE_ENDIAN);
        byte[] headerData = this.m_header.getBytes();
        for(int i =0;i<headerData.length;i++){
            ret.put(i,headerData[i]);
        }
        for(int i=0;i<20;i++){
            byte[] lapData = m_lapData[i].getBytes();
            int offset = Header.getSize()+i*LapData.getSize();
            for(int k=0;k<lapData.length;k++){
                ret.put(offset+k,lapData[k]);
            }
        }
        return ret.array();
    }
}
package gfhund.jtelemetry.f1y18;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
public class PacketParticipantsData extends AbstractPacket{
    private Header m_header;
    private byte m_numCars;
    private ParticipantData[] m_participants = new ParticipantData[20];

    public Header getHeader() {
        return m_header;
    }

    public void setHeader(Header m_header) {
        this.m_header = m_header;
    }

    public byte getNumCars() {
        return m_numCars;
    }

    public void setNumCars(byte m_numCars) {
        this.m_numCars = m_numCars;
    }
    
    public void setParticipantData(int i,ParticipantData data){
        this.m_participants[i] = data;
    }
    public ParticipantData getParticipant(int i){
        return this.m_participants[i];
    }
    public static int getSize(){
        return 1082;
    }
    public byte[] getBytes(){
        ByteBuffer ret = ByteBuffer.allocate(PacketParticipantsData.getSize());
        ret.order(ByteOrder.LITTLE_ENDIAN);
        byte[] headerData = m_header.getBytes();
        for(int i=0;i<headerData.length;i++){
            ret.put(i,headerData[i]);
        }
        ret.put(Header.getSize(),this.m_numCars);
        for(int i=0;i<20;i++){
            byte[] participantData = m_participants[i].getBytes();
            int offset = Header.getSize()+1+i*ParticipantData.getSize();
            for(int k=0;k<participantData.length;k++){
                ret.put(offset+k,participantData[k]);
            }
        }
        return ret.array();
    }
}
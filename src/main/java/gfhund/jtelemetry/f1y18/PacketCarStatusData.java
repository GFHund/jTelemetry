package gfhund.jtelemetry.f1y18;

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
}
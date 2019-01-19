package gfhund.jtelemetry.f1y18;

public class PacketCarTelemetryData extends AbstractPacket{
    private Header m_header;
    private CarTelemetryData[] m_carTelemetryData = new CarTelemetryData[20];
    private int m_buttonStatus;
    
    public void setHeader(Header head){
        this.m_header = head;
    }
    public Header getHeader(){
        return this.m_header;
    }
    public void setCarTelemetryData(int i,CarTelemetryData data){
        this.m_carTelemetryData[i] = data;
    }
    public CarTelemetryData getCarTelemetryData(int i){
        return this.m_carTelemetryData[i];
    }
    public void setButtonStatus(int buttonStatus){
        this.m_buttonStatus = buttonStatus;
    }
}
package gfhund.jtelemetry.f1y18;

class PacketCarTelemetryData extends AbstractPacket{
    private Header m_header;
    private CarTelemetryData[] m_carTelemetryData = new CarTelemetryData[20];
    private int m_buttonStatus;
}
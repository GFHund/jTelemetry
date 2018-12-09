package gfhund.jtelemetry.f1y18;

class PacketCarSetupData extends AbstractPacket{
    private Header m_header;
    private CarSetupData[] m_carSetup = new CarSetupData[20];
}
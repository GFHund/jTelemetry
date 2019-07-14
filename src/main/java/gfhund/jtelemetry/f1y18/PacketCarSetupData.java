package gfhund.jtelemetry.f1y18;

import gfhund.jtelemetry.commontelemetry.AbstractPacket;

public class PacketCarSetupData extends AbstractPacket{
    private Header m_header;
    private CarSetupData[] m_carSetup = new CarSetupData[20];
}
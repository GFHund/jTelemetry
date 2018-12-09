package gfhund.jtelemetry.f1y18;

class PacketParticipantsData extends AbstractPacket{
    private Header m_header;
    private byte m_numCars;
    private ParticipantData[] m_participants = new ParticipantData[20];
}
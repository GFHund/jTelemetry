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
public class PacketParticipantsData extends AbstractPacket {
    private Header m_header19;
    private byte m_numCars;
    private ParticipantData[] m_participants = new ParticipantData[20];

    public Header getHeader19() {
        return m_header19;
    }

    public void setHeader19(Header m_header) {
        this.m_header19 = m_header;
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
        return 1102;
    }
    public byte[] getBytes(){
        ByteBuffer ret = ByteBuffer.allocate(gfhund.jtelemetry.f1y18.PacketParticipantsData.getSize());
        ret.order(ByteOrder.LITTLE_ENDIAN);
        byte[] headerData = m_header19.getBytes();
        for(int i=0;i<headerData.length;i++){
            ret.put(i,headerData[i]);
        }
        ret.put(gfhund.jtelemetry.f1y18.Header.getSize(),this.m_numCars);
        for(int i=0;i<20;i++){
            byte[] participantData = m_participants[i].getBytes();
            int offset = gfhund.jtelemetry.f1y18.Header.getSize()+1+i*gfhund.jtelemetry.f1y18.ParticipantData.getSize();
            for(int k=0;k<participantData.length;k++){
                ret.put(offset+k,participantData[k]);
            }
        }
        return ret.array();
    }
}

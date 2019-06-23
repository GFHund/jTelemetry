/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfhund.jtelemetry.f1y19;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 *
 * @author PhilippHolzmann
 */
public class ParticipantData extends gfhund.jtelemetry.f1y18.ParticipantData {
    protected byte m_yourTelemetry;//The Player'S udp settings, 0 = restricted,1=public
    
    public void setYourTelemetry(byte yourTelemetry) {
        this.m_yourTelemetry = yourTelemetry;
    }
    public byte getYourTelemetry(){
        return this.m_yourTelemetry;
    }
    
    public byte[] getBytes(){
        ByteBuffer ret = ByteBuffer.allocate(gfhund.jtelemetry.f1y18.ParticipantData.getSize());
        ret.order(ByteOrder.LITTLE_ENDIAN);
        ret.put(m_aiControlled);
        ret.put(m_driverId);
        ret.put(m_teamId);
        ret.put(m_raceNumber);
        ret.put(m_nationality);
        if(m_name.length() <= 48){
            for(int i=m_name.length();i<48;i++){
                m_name += " ";
            }
        }
        ret.put(m_name.getBytes());
        ret.put(this.m_yourTelemetry);
        return ret.array();
    }
    
    public static int getSize(){
        return 54;
    }
}

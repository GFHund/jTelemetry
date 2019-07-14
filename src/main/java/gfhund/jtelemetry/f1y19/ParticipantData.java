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
    protected byte yourTelemetry;//The Player'S udp settings, 0 = restricted,1=public
    
    public void setYourTelemetry(byte yourTelemetry) {
        this.yourTelemetry = yourTelemetry;
    }
    public byte getYourTelemetry(){
        return this.yourTelemetry;
    }
    
    public byte[] getBytes(){
        ByteBuffer ret = ByteBuffer.allocate(gfhund.jtelemetry.f1y18.ParticipantData.getSize());
        ret.order(ByteOrder.LITTLE_ENDIAN);
        ret.put(aiControlled);
        ret.put(driverId);
        ret.put(teamId);
        ret.put(raceNumber);
        ret.put(nationality);
        if(name.length() <= 48){
            for(int i=name.length();i<48;i++){
                name += " ";
            }
        }
        ret.put(name.getBytes());
        ret.put(this.yourTelemetry);
        return ret.array();
    }
    
    public static int getSize(){
        return 54;
    }
}

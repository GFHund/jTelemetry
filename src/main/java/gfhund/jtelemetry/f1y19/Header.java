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
public class Header extends gfhund.jtelemetry.f1y18.Header {
    protected byte m_gameMajorVersion;
    protected byte m_gameMinorVersion;
    
    public void setGameMajorVersion(byte majorVersion){
        this.m_gameMajorVersion = majorVersion;
    }
    public byte getGameMajorVersion() {
        return this.m_gameMajorVersion;
    }
    
    public void setGameMinorVersion(byte minorVersion){
        this.m_gameMinorVersion = minorVersion;
    }
    public byte getGameMinorVersion(){
        return this.m_gameMinorVersion;
    }
    
    public static int getSize(){
        return 23;
    }
    
    public byte[] getBytes(){
        ByteBuffer headerBuffer = ByteBuffer.allocate(21);
        headerBuffer.order(ByteOrder.LITTLE_ENDIAN);
        headerBuffer.putShort(this.m_packetFormat);
        headerBuffer.put(2,this.m_gameMajorVersion);
        headerBuffer.put(3,this.m_gameMinorVersion);
        headerBuffer.put(4, this.m_packetVersion);
        headerBuffer.put(5, this.m_packetId);
        headerBuffer.putLong(6, m_sessionUID);
        headerBuffer.putFloat(14, m_sessionTime);
        headerBuffer.putInt(18, m_frameIdentifier);
        headerBuffer.put(22,m_playerCarIndex);
        
        return headerBuffer.array();
    }
}

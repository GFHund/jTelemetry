package gfhund.jtelemetry.f1y18;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ParticipantData{
    private byte m_aiControlled;
    private byte m_driverId ;
    private byte m_teamId;
    private byte m_raceNumber;
    private byte m_nationality;
    private String m_name; 
    
    public byte[] getBytes(){
        ByteBuffer ret = ByteBuffer.allocate(ParticipantData.getSize());
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
        return ret.array();
    }
    
    public byte getAiControlled(){
        return this.m_aiControlled;
    }
    public byte getDriverId(){
        return this.m_driverId;
    }
    public byte getTeamId(){
        return this.m_teamId;
    }
    public byte getRaceNumber(){
        return this.m_raceNumber;
    }
    public String getName(){
        return this.m_name;
    }
    
    public void setAiControlled(byte aiControlled){
        this.m_aiControlled = aiControlled;
    }
    public void setDriverId(byte driverId){
        this.m_driverId = driverId;
    }
    public void setTeamId(byte teamId){
        this.m_teamId = teamId;
    }
    public void setRaceNumber(byte raceNumber){
        this.m_raceNumber = raceNumber;
    }
    public void setNationality(byte nationality){
        this.m_nationality = nationality;
    }
    
    public void setName(String name){
        this.m_name = name;
    }
    public static int getSize(){
        return 53;
    }
   
}
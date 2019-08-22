package gfhund.jtelemetry.f1y18;

import gfhund.jtelemetry.commontelemetry.AbstractSubPackage;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ParticipantData extends AbstractSubPackage{
    protected byte aiControlled;
    protected byte driverId ;
    protected byte teamId;
    protected byte raceNumber;
    protected byte nationality;
    protected String name; 
    
    public byte[] getBytes(){
        ByteBuffer ret = ByteBuffer.allocate(ParticipantData.getSize());
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
        return ret.array();
    }
    
    public byte getAiControlled(){
        return this.aiControlled;
    }
    public byte getDriverId(){
        return this.driverId;
    }
    public byte getTeamId(){
        return this.teamId;
    }
    public byte getRaceNumber(){
        return this.raceNumber;
    }
    public String getName(){
        return this.name;
    }
    
    public void setAiControlled(byte aiControlled){
        this.aiControlled = aiControlled;
    }
    public void setDriverId(byte driverId){
        this.driverId = driverId;
    }
    public void setTeamId(byte teamId){
        this.teamId = teamId;
    }
    public void setRaceNumber(byte raceNumber){
        this.raceNumber = raceNumber;
    }
    public void setNationality(byte nationality){
        this.nationality = nationality;
    }
    
    public void setName(String name){
        this.name = name;
    }

    public byte getNationality() {
        return nationality;
    }
    
    public static int getSize(){
        return 53;
    }
}
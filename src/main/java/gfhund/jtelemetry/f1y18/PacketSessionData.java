package gfhund.jtelemetry.f1y18;

import gfhund.jtelemetry.commontelemetry.AbstractPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class PacketSessionData extends AbstractPacket{
    protected Header m_header;
    protected Weather m_weather;
    protected byte m_trackTemperature;
    protected byte m_airTemperature;
    protected byte m_totalLaps;
    protected short m_trackLength;
    protected SessionType m_sessionType;
    protected TrackID m_trackId;
    protected byte m_era;
    protected short m_sessionTimeLeft;
    protected short m_sessionDuration;
    protected byte m_pitSpeedLimit;// Pit speed limit in kilometres per hour
    protected byte m_gamePaused;
    protected byte m_isSpectating;
    protected byte m_spectatorCarIndex;
    protected byte m_sliProNativeSupport;
    protected byte m_numMarshalZones;
    protected MarshalZone[] m_marshalZones = new MarshalZone[21];
    protected byte m_safetyCarStatus;// 0 = no safety car, 1 = full safety car
    // 2 = virtual safety car
    protected byte m_networkGame;// 0 = offline, 1 = online

    public static int getSize(){
        return 147;
    }
    
    public byte[] getBytes(){
        ByteBuffer ret = ByteBuffer.allocate(PacketSessionData.getSize());
        ret.order(ByteOrder.LITTLE_ENDIAN);
        byte[] headerData = m_header.getBytes();
        for(int i=0;i<headerData.length;i++){
            ret.put(i,headerData[i]);
        }
        ret.put(21,m_weather.getValue());
        ret.put(22,this.m_trackTemperature);
        ret.put(23,this.m_airTemperature);
        ret.put(24,this.m_totalLaps);
        ret.putShort(25,this.m_trackLength);
        ret.put(27,this.m_sessionType.getValue());
        ret.put(28,this.m_trackId.getValue());
        ret.put(29,this.m_era);
        ret.putShort(30,this.m_sessionTimeLeft);
        ret.putShort(32,this.m_sessionDuration);
        ret.put(35,this.m_pitSpeedLimit);
        ret.put(36,m_gamePaused);
        ret.put(37,m_isSpectating);
        ret.put(38,m_spectatorCarIndex);
        ret.put(39,m_sliProNativeSupport);
        ret.put(40,m_numMarshalZones);
        for(int i=0;i<m_numMarshalZones;i++){
            byte[] marshalZone = this.m_marshalZones[i].getBytes();
            for(int j=0;j<marshalZone.length;j++){
                ret.put(41+j,marshalZone[j]);
            }
        }
        int offset = 41+MarshalZone.getSize()*m_numMarshalZones;
        ret.put(offset,m_safetyCarStatus);
        ret.put(offset+1,m_networkGame);
        return ret.array();
    }
    
    public Header getHeader(){
        return this.m_header;
    }
    public void setHeader(Header head){
        this.m_header = head;
    }
    public Weather getWeather(){
        return this.m_weather;
    }
    public void setWeather(Weather weather){
        this.m_weather = weather;
    }
    public byte getTrackTemperature(){
        return this.m_trackTemperature;
    }
    public void setTrackTemperature(byte temperature){
        this.m_trackTemperature = temperature;
    }
    public byte getAirTemperature(){
        return this.m_airTemperature;
    }
    public void setAirTemperature(byte temperature){
        this.m_airTemperature = temperature;
    }
    public byte getTotalLaps(){
        return this.m_totalLaps;
    }
    public void setTotalLaps(byte totalLaps){
        this.m_totalLaps = totalLaps;
    }
    public short getTrackLength(){
        return this.m_trackLength;
    }
    public void setTrackLength(short length){
        this.m_trackLength = length;
    }
    public SessionType getSessionType(){
        return this.m_sessionType;
    }
    public void setSessionType(SessionType type){
        this.m_sessionType = type;
    }
    public TrackID getTrackId(){
        return this.m_trackId;
    }
    public void setTrackId(TrackID track){
        this.m_trackId = track;
    }
    public byte getEra(){
        return this.m_era;
    }
    public void setEra(byte era){
        this.m_era = era;
    }    
    public short getSessionTimeLeft(){
        return this.m_sessionTimeLeft;
    }
    public void setSessionTimeLeft(short left){
        this.m_sessionTimeLeft = left;
    }
    public short getSessionDuration(){
        return this.m_sessionDuration;
    }
    public void setSessionDuration(short duration){
        this.m_sessionDuration = duration;
    }
    
    public void setPitSpeedLimit(byte m_pitSpeedLimit) {
        this.m_pitSpeedLimit = m_pitSpeedLimit;
    }

    public void setGamePaused(byte m_gamePaused) {
        this.m_gamePaused = m_gamePaused;
    }

    public void setIsSpectating(byte m_isSpectating) {
        this.m_isSpectating = m_isSpectating;
    }

    public void setSpectatorCarIndex(byte m_spectatorCarIndex) {
        this.m_spectatorCarIndex = m_spectatorCarIndex;
    }

    public void setSliProNativeSupport(byte m_sliProNativeSupport) {
        this.m_sliProNativeSupport = m_sliProNativeSupport;
    }

    public void setNumMarshalZones(byte m_numMarshalZones) {
        this.m_numMarshalZones = m_numMarshalZones;
    }
    public byte getNumMarshalZones(){
        return this.m_numMarshalZones;
    }

    public void setMarshalZones(MarshalZone[] m_marshalZones) {
        this.m_marshalZones = m_marshalZones;
    }
    public void setMarshalZone(int i,MarshalZone marshalZone){
        this.m_marshalZones[i] = marshalZone;
    }
    

    public void setSafetyCarStatus(byte m_safetyCarStatus) {
        this.m_safetyCarStatus = m_safetyCarStatus;
    }

    public void setNetworkGame(byte m_networkGame) {
        this.m_networkGame = m_networkGame;
    }
    
    
}




package gfhund.jtelemetry.f1y18;

import gfhund.jtelemetry.commontelemetry.AbstractPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class PacketSessionData extends AbstractPacket{
    protected Header header;
    protected byte weather;
    protected byte trackTemperature;
    protected byte airTemperature;
    protected byte totalLaps;
    protected short trackLength;
    protected byte sessionType;
    protected byte trackId;
    protected byte era;
    protected short sessionTimeLeft;
    protected short sessionDuration;
    protected byte pitSpeedLimit;// Pit speed limit in kilometres per hour
    protected byte gamePaused;
    protected byte isSpectating;
    protected byte spectatorCarIndex;
    protected byte sliProNativeSupport;
    protected byte numMarshalZones;
    protected MarshalZone[] marshalZones = new MarshalZone[21];
    protected byte safetyCarStatus;// 0 = no safety car, 1 = full safety car
    // 2 = virtual safety car
    protected byte networkGame;// 0 = offline, 1 = online

    public static int getSize(){
        return 147;
    }
    
    public byte[] getBytes(){
        ByteBuffer ret = ByteBuffer.allocate(PacketSessionData.getSize());
        ret.order(ByteOrder.LITTLE_ENDIAN);
        byte[] headerData = header.getBytes();
        for(int i=0;i<headerData.length;i++){
            ret.put(i,headerData[i]);
        }
        ret.put(21,weather);
        ret.put(22,this.trackTemperature);
        ret.put(23,this.airTemperature);
        ret.put(24,this.totalLaps);
        ret.putShort(25,this.trackLength);
        ret.put(27,this.sessionType);
        ret.put(28,this.trackId);
        ret.put(29,this.era);
        ret.putShort(30,this.sessionTimeLeft);
        ret.putShort(32,this.sessionDuration);
        ret.put(35,this.pitSpeedLimit);
        ret.put(36,gamePaused);
        ret.put(37,isSpectating);
        ret.put(38,spectatorCarIndex);
        ret.put(39,sliProNativeSupport);
        ret.put(40,numMarshalZones);
        for(int i=0;i<numMarshalZones;i++){
            byte[] marshalZone = this.marshalZones[i].getBytes();
            for(int j=0;j<marshalZone.length;j++){
                ret.put(41+j,marshalZone[j]);
            }
        }
        int offset = 41+MarshalZone.getSize()*numMarshalZones;
        ret.put(offset,safetyCarStatus);
        ret.put(offset+1,networkGame);
        return ret.array();
    }
    
    public Header getHeader(){
        return this.header;
    }
    public void setHeader(Header head){
        this.header = head;
    }
    public byte getWeather(){
        return this.weather;
    }
    public void setWeather(byte weather){
        this.weather = weather;
    }
    public byte getTrackTemperature(){
        return this.trackTemperature;
    }
    public void setTrackTemperature(byte temperature){
        this.trackTemperature = temperature;
    }
    public byte getAirTemperature(){
        return this.airTemperature;
    }
    public void setAirTemperature(byte temperature){
        this.airTemperature = temperature;
    }
    public byte getTotalLaps(){
        return this.totalLaps;
    }
    public void setTotalLaps(byte totalLaps){
        this.totalLaps = totalLaps;
    }
    public short getTrackLength(){
        return this.trackLength;
    }
    public void setTrackLength(short length){
        this.trackLength = length;
    }
    public byte getSessionType(){
        return this.sessionType;
    }
    public void setSessionType(byte type){
        this.sessionType = type;
    }
    public byte getTrackId(){
        return this.trackId;
    }
    public void setTrackId(byte track){
        this.trackId = track;
    }
    public byte getEra(){
        return this.era;
    }
    public void setEra(byte era){
        this.era = era;
    }    
    public short getSessionTimeLeft(){
        return this.sessionTimeLeft;
    }
    public void setSessionTimeLeft(short left){
        this.sessionTimeLeft = left;
    }
    public short getSessionDuration(){
        return this.sessionDuration;
    }
    public void setSessionDuration(short duration){
        this.sessionDuration = duration;
    }
    
    public void setPitSpeedLimit(byte m_pitSpeedLimit) {
        this.pitSpeedLimit = m_pitSpeedLimit;
    }

    public void setGamePaused(byte m_gamePaused) {
        this.gamePaused = m_gamePaused;
    }

    public void setIsSpectating(byte m_isSpectating) {
        this.isSpectating = m_isSpectating;
    }

    public void setSpectatorCarIndex(byte m_spectatorCarIndex) {
        this.spectatorCarIndex = m_spectatorCarIndex;
    }

    public void setSliProNativeSupport(byte m_sliProNativeSupport) {
        this.sliProNativeSupport = m_sliProNativeSupport;
    }

    public void setNumMarshalZones(byte m_numMarshalZones) {
        this.numMarshalZones = m_numMarshalZones;
    }
    public byte getNumMarshalZones(){
        return this.numMarshalZones;
    }

    public void setMarshalZones(MarshalZone[] m_marshalZones) {
        this.marshalZones = m_marshalZones;
    }
    /**
     * @deprecated 
     * @param i
     * @param marshalZone 
     */
    public void setMarshalZone(int i,MarshalZone marshalZone){
        this.marshalZones[i] = marshalZone;
    }
    public void setMarshalZones(int i,MarshalZone marshalZone){
        this.marshalZones[i] = marshalZone;
    }

    public void setSafetyCarStatus(byte m_safetyCarStatus) {
        this.safetyCarStatus = m_safetyCarStatus;
    }

    public void setNetworkGame(byte m_networkGame) {
        this.networkGame = m_networkGame;
    }

    public byte getPitSpeedLimit() {
        return pitSpeedLimit;
    }

    public byte getGamePaused() {
        return gamePaused;
    }

    public byte getIsSpectating() {
        return isSpectating;
    }

    public byte getSpectatorCarIndex() {
        return spectatorCarIndex;
    }

    public byte getSliProNativeSupport() {
        return sliProNativeSupport;
    }

    public MarshalZone[] getMarshalZones() {
        return marshalZones;
    }

    public byte getSafetyCarStatus() {
        return safetyCarStatus;
    }

    public byte getNetworkGame() {
        return networkGame;
    }
    
    
}




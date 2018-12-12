package gfhund.jtelemetry.f1y18;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Header{
    private short m_packetFormat;//2  0
    private byte m_packetVersion;//1  2
    private byte m_packetId;//1       3
    private long m_sessionUID;//8     4
    private float m_sessionTime;//4   12
    private int m_frameIdentifier;//4 16
    private byte m_playerCarIndex;//1 20
    //total: 21
    public static int getSize(){
        return 21;
    }

    public short getPacketFormat(){
        return this.m_packetFormat;
    }
    public byte getPacketVersion(){
        return this.m_packetVersion;
    }
    public byte getPacketId(){
        return this.m_packetId;
    }
    public long getSessionUid(){
        return this.m_sessionUID;
    }
    public float getSessionTime(){
        return this.m_sessionTime;
    }
    public int getFrameIdentifier(){
        return this.m_frameIdentifier;
    }
    public byte getPlayerCarIndex(){
        return this.m_playerCarIndex;
    }

    public void setPacketFormat(short packetFormat){
        this.m_packetFormat = packetFormat;
    }
    public void setPacketVersion(byte packetVersion){
        this.m_packetVersion = packetVersion;
    }
    public void setPacketId(byte packetId){
        this.m_packetId = packetId;
    }
    public void setSessionUid(long sessionUid){
        this.m_sessionUID = sessionUid;
    }
    public void setSessionTime(float sessionTime){
        this.m_sessionTime = sessionTime;
    }
    public void setFrameIdentifier(int frameIdentifier){
        this.m_frameIdentifier = frameIdentifier;
    }
    public void setPlayerCarIndex(byte playerCarIndex){
        this.m_playerCarIndex = playerCarIndex;
    }
    
    public byte[] getBytes(){
        ByteBuffer headerBuffer = ByteBuffer.allocate(21);
        headerBuffer.order(ByteOrder.LITTLE_ENDIAN);
        headerBuffer.putShort(this.m_packetFormat);
        headerBuffer.put(2, this.m_packetVersion);
        headerBuffer.put(3, this.m_packetId);
        headerBuffer.putLong(4, m_sessionUID);
        headerBuffer.putFloat(12, m_sessionTime);
        headerBuffer.putInt(16, m_frameIdentifier);
        headerBuffer.put(20,m_playerCarIndex);
        
        return headerBuffer.array();
    }
}
enum PacketType{
    MOTION( 0 ),
    SESSION(1),
    LAP_DATA ( 2),
    EVENT (3),
    PARTICIPANTS (4),
    CAR_SETUPS (5),
    CAR_TELEMETRY (6),
    CAR_STATUS (7);

    private int value;
    PacketType(int num){
        this.value = num;
    }
    public int getValue(){
        return this.value;
    }
}
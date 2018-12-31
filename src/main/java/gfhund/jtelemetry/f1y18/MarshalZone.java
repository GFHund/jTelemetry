package gfhund.jtelemetry.f1y18;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

class MarshalZone{
    float m_zoneStart;
    float m_zoneFlag;
    
    public void setZoneStart(float zoneStart){
        this.m_zoneStart = zoneStart;
    }
    public void setZoneFlag(float zoneFlag){
        this.m_zoneFlag = zoneFlag;
    }
    public static int getSize(){
        return 8;
    }
    
    public byte[] getBytes(){
        ByteBuffer ret = ByteBuffer.allocate(MarshalZone.getSize());
        ret.order(ByteOrder.LITTLE_ENDIAN);
        ret.putFloat(m_zoneStart);
        ret.putFloat(m_zoneFlag);
        return ret.array();
    }
    
   
}
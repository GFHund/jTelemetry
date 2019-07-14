package gfhund.jtelemetry.f1y18;

import gfhund.jtelemetry.commontelemetry.AbstractSubPackage;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class MarshalZone extends AbstractSubPackage{
    protected float zoneStart;
    protected float zoneFlag;
    
    public void setZoneStart(float zoneStart){
        this.zoneStart = zoneStart;
    }
    public void setZoneFlag(float zoneFlag){
        this.zoneFlag = zoneFlag;
    }
    public static int getSize(){
        return 8;
    }
    
    public byte[] getBytes(){
        ByteBuffer ret = ByteBuffer.allocate(MarshalZone.getSize());
        ret.order(ByteOrder.LITTLE_ENDIAN);
        ret.putFloat(zoneStart);
        ret.putFloat(zoneFlag);
        return ret.array();
    }
    
   
}
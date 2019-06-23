package gfhund.jtelemetry.f1y18;

import gfhund.jtelemetry.commontelemetry.AbstractPacket;

public class PacketMotionData extends AbstractPacket{
    protected Header m_header;//0 21
    protected CarMotionData[] m_carMotionData = new CarMotionData[20];//21 20*60 = 1200 
    protected float m_suspensionPosition[] = new float[4];//1221 4*4 = 16
    protected float m_suspensionVelocity[] = new float[4];//1237 4*4 = 16
    protected float m_suspensionAcceleration[] = new float[4];//1253 4*4 = 16
    protected float m_wheelSpeed[] = new float[4];//1269 4*4=16
    protected float m_wheelSlip[] = new float[4];//1285 4*4=16
    protected float m_localVelocityX;//1301 4
    protected float m_localVelocityY;//4
    protected float m_localVelocityZ;//4
    protected float m_angularVelocityX;//4
    protected float m_angularVelocityY;//4
    protected float m_angularVelocityZ;//4
    protected float m_angularAccelerationX;//4
    protected float m_angularAccelerationY;//4
    protected float m_angularAccelerationZ;//4
    protected float m_frontWheelsAngle;//4
    //total = 21+1200+5*16+10*4 = 21+1200+80+40=1200+141=1341

    public void setHeader(Header head){
        this.m_header = head;
    }
    public Header getHeader(){
        return this.m_header;
    }
    public void setCarMotionData(int i,CarMotionData value){
        this.m_carMotionData[i] = value;
    }
    public CarMotionData getCarMotionData(int i){
        return this.m_carMotionData[i];
    }
    public void setSuspensionPosition(int i,float value){
        this.m_suspensionPosition[i] = value;
    }
    public void setSuspensionVelocity(int i,float value){
        this.m_suspensionVelocity[i] = value;
    }
    public void setSuspensionAcceleration(int i,float value){
        this.m_suspensionAcceleration[i] = value;
    }
    public void setWheelSpeed(int i,float value){
        this.m_wheelSpeed[i] = value;
    }
    public void setWheelSlip(int i,float value){
        this.m_wheelSlip[i] = value;
    }
    public void setLocalVelocityX(float value){
        this.m_localVelocityX = value;
    }
    public void setLocalVelocityY(float value){
        this.m_localVelocityY = value;
    }
    public void setLocalVelocityZ(float value){
        this.m_localVelocityZ = value;
    }
    public void setAngularVelocityX(float value){
        this.m_angularVelocityX = value;
    }
    public void setAngularVelocityY(float value){
        this.m_angularVelocityY = value;
    }
    public void setAngularVelocityZ(float value){
        this.m_angularVelocityZ = value;
    }
    public void setAngularAccelerationX(float value){
        this.m_angularAccelerationX = value;
    }
    public void setAngularAccelerationY(float value){
        this.m_angularAccelerationY = value;
    }
    public void setAngularAccelerationZ(float value){
        this.m_angularAccelerationZ = value;
    }
    public void setFrontWheelsAngle(float value){
        this.m_frontWheelsAngle = value;
    }
}


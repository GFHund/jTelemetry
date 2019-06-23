package gfhund.jtelemetry.f1y18;

public class CarMotionData{
    protected float m_worldPositionX;//4
    protected float m_worldPositionY;//4
    protected float m_worldPositionZ;//4

    protected float m_worldVelocityX;//4
    protected float m_worldVelocityY;//4
    protected float m_worldVelocityZ;//4

    protected short m_worldForwardDirX;//2
    protected short m_worldForwardDirY;//2
    protected short m_worldForwardDirZ;//2

    protected short m_worldRightDirX;//2
    protected short m_worldRightDirY;//2
    protected short m_worldRightDirZ;//2

    protected float m_gForceLateral;//4
    protected float m_gForceLongitudinal;//4
    protected float m_gForceVertical;//4

    protected float m_yaw;//4
    protected float m_pitch;//4
    protected float m_roll;//4
    //total: 12*4+6*2 = 60

    public static int getSize(){
        return 60;
    }

    public void setWorldPositionX(float worldPositionX){
        this.m_worldPositionX = worldPositionX;
    }
    public float getWorldPositionX(){
        return this.m_worldPositionX;
    }
    
    public void setWorldPositionY(float worldPositionY){
        this.m_worldPositionY = worldPositionY;
    }
    public float getWorldPositionY(){
        return this.m_worldPositionY;
    }
    
    public void setWorldPositionZ(float worldPositionZ){
        this.m_worldPositionZ = worldPositionZ;
    }
    public float getWorldPositionZ(){
        return this.m_worldPositionZ;
    }
    
    public void setWorldVelocityX(float worldVelocityX){
        this.m_worldVelocityX = worldVelocityX;
    }
    public float getWorldVelocityX(){
        return this.m_worldVelocityX;
    }
    
    public void setWorldVelocityY(float worldVelocyityY){
        this.m_worldVelocityY = worldVelocyityY;
    }
    public float getWorldVelocityY(){
        return this.m_worldVelocityY;
    }
    
    public void setWorldVelocityZ(float worldVelocityZ){
        this.m_worldVelocityZ = worldVelocityZ;
    }
    public float getWorldVelocityZ(){
        return this.m_worldVelocityZ;
    }
    
    public void setWorldForwardDirX(short worldForwardDirX){
        this.m_worldForwardDirX = worldForwardDirX;
    }
    public short getWorldForwardDirX(){
        return this.m_worldForwardDirX;
    }
    
    public void setWorldForwardDirY(short worldForwardDirY){
        this.m_worldForwardDirY = worldForwardDirY;
    }
    public short getWorldForwardDirY(){
        return this.m_worldForwardDirY;
    }
    
    public void setWorldForwardDirZ(short worldForwardDirZ){
        this.m_worldForwardDirZ = worldForwardDirZ;
    }
    public short getWorldForwardDirZ(){
        return this.m_worldForwardDirZ;
    }
    
    public void setWordRightDirX(short worldRightDirX){
        this.m_worldRightDirX = worldRightDirX;
    }
    public short getWorldRightDirX(){
        return this.m_worldRightDirX;
    }
    
    
    public void setWorldRightDirY(short worldRightDirY){
        this.m_worldRightDirY = worldRightDirY;
    }
    public short getWorldRightDirY(){
        return this.m_worldRightDirY;
    }
    
    public void setWorldRightDirZ(short worldRightDirZ){
        this.m_worldRightDirZ = worldRightDirZ;
    }
    public short getWorldRightDirZ(){
        return this.m_worldRightDirZ;
    }
    public void setGForceLateral(float gForceLateral){
        this.m_gForceLateral = gForceLateral;
    }
    public void setGForceLongitudinal(float gForceLongitudinal){
        this.m_gForceLongitudinal = gForceLongitudinal;
    }
    public void setGForceVertical(float gForceVertical){
        this.m_gForceVertical = gForceVertical;
    }
    public void setYaw(float yaw){
        this.m_yaw = yaw;
    }
    public void setRoll(float roll){
        this.m_roll = roll;
    }
    public void setPitch(float pitch){
        this.m_pitch = pitch;
    }
}
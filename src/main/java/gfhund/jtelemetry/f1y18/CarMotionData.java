package gfhund.jtelemetry.f1y18;

class CarMotionData{
    private float m_worldPositionX;//4
    private float m_worldPositionY;//4
    private float m_worldPositionZ;//4

    private float m_worldVelocityX;//4
    private float m_worldVelocityY;//4
    private float m_worldVelocityZ;//4

    private short m_worldForwardDirX;//2
    private short m_worldForwardDirY;//2
    private short m_worldForwardDirZ;//2

    private short m_worldRightDirX;//2
    private short m_worldRightDirY;//2
    private short m_worldRightDirZ;//2

    private float m_gForceLateral;//4
    private float m_gForceLongitudinal;//4
    private float m_gForceVertical;//4

    private float m_yaw;//4
    private float m_pitch;//4
    private float m_roll;//4
    //total: 12*4+6*2 = 60

    public static int getSize(){
        return 60;
    }

    public void setWorldPositionX(float worldPositionX){
        this.m_worldPositionX = worldPositionX;
    }
    public void setWorldPositionY(float worldPositionY){
        this.m_worldPositionY = worldPositionY;
    }
    public void setWorldPositionZ(float worldPositionZ){
        this.m_worldPositionZ = worldPositionZ;
    }
    public void setWorldVelocityX(float worldVelocityX){
        this.m_worldVelocityX = worldVelocityX;
    }
    public void setWorldVelocityY(float worldVelocyityY){
        this.m_worldVelocityY = worldVelocyityY;
    }
    public void setWorldVelocityZ(float worldVelocityZ){
        this.m_worldVelocityZ = worldVelocityZ;
    }
    public void setWorldForwardDirX(short worldForwardDirX){
        this.m_worldForwardDirX = worldForwardDirX;
    }
    public void setWorldForwardDirY(short worldForwardDirY){
        this.m_worldForwardDirY = worldForwardDirY;
    }
    public void setWorldForwardDirZ(short worldForwardDirZ){
        this.m_worldForwardDirZ = worldForwardDirZ;
    }
    public void setWordRightDirX(short worldRightDirX){
        this.m_worldRightDirX = worldRightDirX;
    }
    public void setWorldRightDirY(short worldRightDirY){
        this.m_worldRightDirY = worldRightDirY;
    }
    public void setWorldRightDirZ(short worldRightDirZ){
        this.m_worldRightDirZ = worldRightDirZ;
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
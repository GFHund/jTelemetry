package gfhund.jtelemetry.f1y18;

import gfhund.jtelemetry.commontelemetry.AbstractSubPackage;
import java.nio.ByteBuffer;

public class CarMotionData extends AbstractSubPackage{
    protected float worldPositionX;//4
    protected float worldPositionY;//4
    protected float worldPositionZ;//4

    protected float worldVelocityX;//4
    protected float worldVelocityY;//4
    protected float worldVelocityZ;//4

    protected short worldForwardDirX;//2
    protected short worldForwardDirY;//2
    protected short worldForwardDirZ;//2

    protected short worldRightDirX;//2
    protected short worldRightDirY;//2
    protected short worldRightDirZ;//2

    protected float gForceLateral;//4
    protected float gForceLongitudinal;//4
    protected float gForceVertical;//4

    protected float yaw;//4
    protected float pitch;//4
    protected float roll;//4
    //total: 12*4+6*2 = 60

    public static int getSize(){
        return 60;
    }

    public void setWorldPositionX(float worldPositionX){
        this.worldPositionX = worldPositionX;
    }
    public float getWorldPositionX(){
        return this.worldPositionX;
    }
    
    public void setWorldPositionY(float worldPositionY){
        this.worldPositionY = worldPositionY;
    }
    public float getWorldPositionY(){
        return this.worldPositionY;
    }
    
    public void setWorldPositionZ(float worldPositionZ){
        this.worldPositionZ = worldPositionZ;
    }
    public float getWorldPositionZ(){
        return this.worldPositionZ;
    }
    
    public void setWorldVelocityX(float worldVelocityX){
        this.worldVelocityX = worldVelocityX;
    }
    public float getWorldVelocityX(){
        return this.worldVelocityX;
    }
    
    public void setWorldVelocityY(float worldVelocyityY){
        this.worldVelocityY = worldVelocyityY;
    }
    public float getWorldVelocityY(){
        return this.worldVelocityY;
    }
    
    public void setWorldVelocityZ(float worldVelocityZ){
        this.worldVelocityZ = worldVelocityZ;
    }
    public float getWorldVelocityZ(){
        return this.worldVelocityZ;
    }
    
    public void setWorldForwardDirX(short worldForwardDirX){
        this.worldForwardDirX = worldForwardDirX;
    }
    public short getWorldForwardDirX(){
        return this.worldForwardDirX;
    }
    
    public void setWorldForwardDirY(short worldForwardDirY){
        this.worldForwardDirY = worldForwardDirY;
    }
    public short getWorldForwardDirY(){
        return this.worldForwardDirY;
    }
    
    public void setWorldForwardDirZ(short worldForwardDirZ){
        this.worldForwardDirZ = worldForwardDirZ;
    }
    public short getWorldForwardDirZ(){
        return this.worldForwardDirZ;
    }
    
    public void setWorldRightDirX(short worldRightDirX){
        this.worldRightDirX = worldRightDirX;
    }
    public short getWorldRightDirX(){
        return this.worldRightDirX;
    }
    
    
    public void setWorldRightDirY(short worldRightDirY){
        this.worldRightDirY = worldRightDirY;
    }
    public short getWorldRightDirY(){
        return this.worldRightDirY;
    }
    
    public void setWorldRightDirZ(short worldRightDirZ){
        this.worldRightDirZ = worldRightDirZ;
    }
    public short getWorldRightDirZ(){
        return this.worldRightDirZ;
    }
    
    public void setGForceLateral(float gForceLateral){
        this.gForceLateral = gForceLateral;
    }
    public float getGForceLateral(){
        return this.gForceLateral;
    }
    
    public void setGForceLongitudinal(float gForceLongitudinal){
        this.gForceLongitudinal = gForceLongitudinal;
    }
    public float getGForceLongitudinal(){
        return this.gForceLongitudinal;
    }
    
    public void setGForceVertical(float gForceVertical){
        this.gForceVertical = gForceVertical;
    }
    public float getGForceVertical(){
        return this.gForceVertical;
    }
    
    public void setYaw(float yaw){
        this.yaw = yaw;
    }
    public float getYaw(){
        return this.yaw;
    }
    
    public void setRoll(float roll){
        this.roll = roll;
    }
    public float getRoll(){
        return this.roll;
    }
    
    public void setPitch(float pitch){
        this.pitch = pitch;
    }
    public float getPitch(){
        return this.pitch;
    }
   
    public byte[] getBytes(){
        ByteBuffer ret = ByteBuffer.allocate(CarMotionData.getSize());
        ret.putFloat(this.getWorldPositionX());
        ret.putFloat(4, this.getWorldPositionY());
        ret.putFloat(8, this.getWorldPositionZ());
        
        ret.putFloat(12, this.getWorldVelocityX());
        ret.putFloat(16, this.getWorldVelocityY());
        ret.putFloat(20, this.getWorldVelocityZ());
        
        ret.putShort(24, this.getWorldForwardDirX());
        ret.putShort(26, this.getWorldForwardDirY());
        ret.putShort(28, this.getWorldForwardDirZ());
        
        ret.putShort(30, this.getWorldRightDirX());
        ret.putShort(32, this.getWorldRightDirY());
        ret.putShort(34, this.getWorldRightDirZ());
        
        ret.putFloat(36, this.getGForceLateral());
        ret.putFloat(40, this.getGForceLongitudinal());
        ret.putFloat(44, this.getGForceVertical());
        
        ret.putFloat(48, this.getYaw());
        ret.putFloat(52, this.getPitch());
        ret.putFloat(56, this.getRoll());
        return ret.array();
    }
}
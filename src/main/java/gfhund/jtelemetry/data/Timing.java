package gfhund.jtelemetry.data;

public class Timing{
    private int mLapNum;
    private float mLapTime;
    private float mSector1Time;
    private float mSector2Time;
    private float mSector3Time;
    private float mSector4Time;
    private float mSector5Time;

    public  int getLapNum(){
        return this.mLapNum;
    }
    
    public float getLapTime(){
        return this.mLapTime;
    }
    public float getSector1Time(){
        return this.mSector1Time;
    }
    public float getSector2Time(){
        return this.mSector2Time;
    }
    public float getSector3Time(){
        return this.mSector3Time;
    }
    public float getSector4Time(){
        return this.mSector4Time;
    }
    public float getSector5Time(){
        return this.mSector5Time;
    }

    public void setLapNum(int lapNum){
        this.mLapNum = lapNum;
    }
    public void setLapTime(float lapTime){
        this.mLapTime = lapTime;
    }
    public void setSector1Time(float sector1Time){
        this.mSector1Time = sector1Time;
    }
    public void setSector2Time(float sector2Time){
        this.mSector2Time = sector2Time;
    }
    public void setSector3Time(float sector3Time){
        this.mSector3Time = sector3Time;
    }
    public void setSector4Time(float sector4Time){
        this.mSector4Time = sector4Time;
    }
    public void setSector5Time(float sector5Time){
        this.mSector5Time = sector5Time;
    }
}
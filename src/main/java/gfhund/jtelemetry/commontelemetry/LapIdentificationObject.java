/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfhund.jtelemetry.commontelemetry;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;
/**
 *
 * @author PhilippGL
 */
public class LapIdentificationObject {
    private int lapNum;
    private Date dateLapDriven;
    private String player;
    private String zipFile;
    private float lapTime;

    public int getLapNum() {
        return lapNum;
    }

    public void setLapNum(int lapNum) {
        this.lapNum = lapNum;
    }

    public Date getDateLapDriven() {
        return dateLapDriven;
    }

    public void setDateLapDriven(Date time) {
        this.dateLapDriven = time;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getZipFile() {
        return zipFile;
    }

    public void setZipFile(String zipFile) {
        this.zipFile = zipFile;
    }
    
    public void setLapTime(float lapTime){
        this.lapTime = lapTime;
    }
    public float getLapTime(){
        return this.lapTime;
    }
    
    public String getFilenameDate() {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(dateLapDriven);
        
        int month = cal.get(GregorianCalendar.MONTH);
        month++;
        String sMonth = "" + month;
        if(month < 10) sMonth = "0"+month;
        
        int dayOfMonth = cal.get(GregorianCalendar.DAY_OF_MONTH);
        String sDayOfMonth = "" + dayOfMonth;
        if(dayOfMonth < 10) sDayOfMonth = "0" + sDayOfMonth;
        
        int hour = cal.get(GregorianCalendar.HOUR_OF_DAY);
        String sHour = ""+ hour;
        if(hour < 10) sHour = "0"+hour;
        
        int minute = cal.get(GregorianCalendar.MINUTE);
        String sMinute = "" + minute;
        if(minute < 10) sMinute = "0" + minute;
        
        String ret = 
                "" + 
                cal.get(GregorianCalendar.YEAR) +
                sMonth + 
                sDayOfMonth+
                sHour+
                sMinute;
        return ret;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + this.lapNum;
        hash = 53 * hash + Objects.hashCode(this.dateLapDriven);
        hash = 53 * hash + Objects.hashCode(this.player);
        hash = 53 * hash + Objects.hashCode(this.zipFile);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LapIdentificationObject other = (LapIdentificationObject) obj;
        if (this.lapNum != other.lapNum) {
            return false;
        }
        if (!Objects.equals(this.player, other.player)) {
            return false;
        }
        if (!Objects.equals(this.dateLapDriven, other.dateLapDriven)) {
            return false;
        }
        if(!this.zipFile.equals(other.zipFile)){
            return false;
        }
        return true;
    }
    
    
}

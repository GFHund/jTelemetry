/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfhund.jtelemetry.f1y18;

/**
 *
 * @author PhilippGL
 */
public enum Weather{
    CLEAR( (byte)0 ),
    LIGHT_CLOUD((byte)1),
    OVERCAST ( (byte)2),
    LIGHT_RAIN ((byte)3),
    HEAVY_RAIN ((byte)4),
    STORM ((byte)5);

    private byte value;
    Weather(byte num){
        this.value = num;
    }
    public static Weather getWeatherFromValue(byte value){
        switch(value){
            case 0:
                return Weather.CLEAR;
            case 1:
                return Weather.LIGHT_CLOUD;
            case 2:
                return Weather.OVERCAST;
            case 3:
                return Weather.LIGHT_RAIN;
            case 4:
                return Weather.HEAVY_RAIN;
            case 5:
                return Weather.STORM;
            default:
                return Weather.CLEAR;
        }
    }
    public byte getValue(){
        return this.value;
    }
}
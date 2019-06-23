/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfhund.jtelemetry.f1y19;

/**
 *
 * @author PhilippHolzmann
 */
public enum SurfaceType {
    TARMAC((byte)0),
    RUMBLE_STRIP((byte)1),
    CONCRETE((byte)2),
    ROCK((byte)3),
    GRAVEL((byte)4),
    MUD((byte)5),
    SAND((byte)6),
    GRASS((byte)7),
    WATER((byte)8),
    COBBLESTONE((byte)9),
    METAL((byte)10),
    RIDGED((byte)11);
    
    private byte value;
    SurfaceType(byte num){
        this.value = num;
    }
    
    public static SurfaceType getWeatherFromValue(byte value){
        switch(value){
            case 0:
                return SurfaceType.TARMAC;
            case 1:
                return SurfaceType.RUMBLE_STRIP;
            case 2:
                return SurfaceType.CONCRETE;
            case 3:
                return SurfaceType.ROCK;
            case 4:
                return SurfaceType.GRAVEL;
            case 5:
                return SurfaceType.MUD;
            case 6:
                return SurfaceType.SAND;
            case 7:
                return SurfaceType.GRASS;
            case 8:
                return SurfaceType.WATER;
            case 9:
                return SurfaceType.COBBLESTONE;
            case 10:
                return SurfaceType.METAL;
            case 11:
                return SurfaceType.RIDGED;
            default:
                return SurfaceType.TARMAC;
        }
    }
    
    public byte getValue(){
        return this.value;
    }
}

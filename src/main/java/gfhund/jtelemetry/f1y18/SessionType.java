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
public enum SessionType{
    UNKNOWN( (byte)0 ),
    P1((byte)1),
    P2 ( (byte)2),
    P3 ((byte)3),
    SHORT_P ((byte)4),
    Q1 ((byte)5),
    Q2 ((byte)6),
    Q3 ((byte)7),
    SHORT_Q ((byte)8),
    OSQ((byte)9),
    R((byte)10),
    R2((byte)11),
    TIME_TRIAL((byte)12);

    private byte value;
    SessionType(byte num){
        this.value = num;
    }
    public static SessionType getTypeFromValue(byte value){
        return SessionType.values()[value];
    }
    public byte getValue(){
        return this.value;
    }
}
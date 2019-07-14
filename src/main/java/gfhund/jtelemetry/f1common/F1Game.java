/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfhund.jtelemetry.f1common;

/**
 *
 * @author PhilippHolzmann
 */
public enum F1Game {
    F1_2018((byte)0),
    F1_2019((byte)1);
    
    private byte value;
    F1Game(byte num){
        this.value = num;
    }
    public byte getValue(){
        return this.value;
    }
}

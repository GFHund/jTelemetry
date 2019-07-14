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
public class ParseException extends Exception{
    private String typeName;
    private String message;
    
    public ParseException(String typeName, String message){
        this.typeName = typeName;
        this.message = message;
    }
    
    
    @Override
    public String getMessage(){
        return "Could not Parse " + typeName + ": "+ this.message;
    }
}

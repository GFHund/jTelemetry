/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfhund.jtelemetry.stfFormat;

/**
 *
 * @author PhilippGL
 */
public class StfParseException extends Exception {
    String nearbyCode;

    public StfParseException(String nearbyCode, String message) {
        super(message);
        this.nearbyCode = nearbyCode;
    }
    
}

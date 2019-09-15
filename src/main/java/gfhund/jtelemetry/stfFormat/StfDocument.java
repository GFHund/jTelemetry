/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfhund.jtelemetry.stfFormat;

import java.util.ArrayList;

/**
 *
 * @author PhilippGL
 */
public class StfDocument extends StfClass {
    public StfDocument(){
        super("root");
    }
    
    /**
     * Every Document has one root class. Because of this, this method returns an empty string
     * @param propertyPath
     * @return 
     */
    public String getProperty(String propertyPath){
        return "";
    }
}

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
public class StfProperty extends AbstractStfObject {
    private String value;

    public StfProperty(String propertyName,String value){
        this.setPropertyName(propertyName);
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
}

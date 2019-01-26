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
public class StfClass extends AbstractStfObject {
    private ArrayList<AbstractStfObject> objects;
    
    public StfClass(String propertyName){
        this.setPropertyName(propertyName);
    }
    
    public AbstractStfObject getObject(String name){
        for(AbstractStfObject obj: objects){
            if(obj.getPropertyName().equals(name)){
                return obj;
            }
        }
        return null;
    }
    public void addObject(AbstractStfObject obj){
        this.objects.add(obj);
    }
}

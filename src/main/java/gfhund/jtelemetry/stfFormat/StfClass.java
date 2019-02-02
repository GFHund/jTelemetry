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
        this.objects = new ArrayList<>();
    }
    //--------------------------------------------------------------
    public AbstractStfObject getObject(String name){
        for(AbstractStfObject obj: objects){
            if(obj.getPropertyName().equals(name)){
                return obj;
            }
        }
        return null;
    }
    //--------------------------------------------------------------
    public void addObject(AbstractStfObject obj){
        this.objects.add(obj);
    }
    //--------------------------------------------------------------
    public String getValue(String identifier){
        String ret = "";
        String[] classes = identifier.split("\\.");
        if(classes.length == 0){
            classes = new String[1];
            classes[0] = identifier;
        }
        for(AbstractStfObject obj:this.objects){
            String propertyName = obj.getPropertyName();
            propertyName = propertyName.trim();
            int result = classes[0].compareTo(propertyName); 
            if(result ==0){
                if(obj instanceof StfProperty){
                    ret = ((StfProperty) obj).getValue();
                    break;
                }
                else if(obj instanceof StfClass){
                    String newIdentifier = "";
                    for(int i=1;i<classes.length;i++){
                        newIdentifier += classes[i]+'.';
                    }
                    //Remove the last point
                    newIdentifier = newIdentifier.substring(0, newIdentifier.length()-1);
                    ret = ((StfClass) obj).getValue(newIdentifier);
                    break;
                }
            }
        }
        return ret;
    }
    //--------------------------------------------------------------
    public AbstractStfObject[] getChildren(){
        AbstractStfObject[] ret = new AbstractStfObject[this.objects.size()];
        for(int i = 0;i<this.objects.size();i++){
            ret[i] = objects.get(i);
        }
        return ret;
    }
}

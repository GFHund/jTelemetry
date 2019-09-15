/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfhund.jtelemetry.data;

import gfhund.jtelemetry.stfFormat.AbstractStfObject;
import gfhund.jtelemetry.stfFormat.StfDocument;
import gfhund.jtelemetry.stfFormat.StfClass;
import java.io.File;
import gfhund.jtelemetry.stfFormat.StfFormatReader;
import gfhund.jtelemetry.stfFormat.StfFormatWriter;
import gfhund.jtelemetry.stfFormat.StfProperty;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author PhilippHolzmann
 */
public class Settings {
    private static final Logger logging = Logger.getLogger(Settings.class.getName());
    private HashMap<String,String> settingsCache = new HashMap<>();
    private static final String FILEPATH = "./settings.stf";
    private static final String ROOTCLASSNAME = "settings";
    
    public String getValue(String name){
        return getValue(name,"");
    }
    /**
     * 
     * @param name Name des Einstellungsvariable
     * @param defaultValue Wert der Einstellungsvariable wenn diese nicht in der Einstellungsdatei gefunden wurde.
     */
    public String getValue(String name,String defaultValue){
        File settingsFile = new File(FILEPATH);
        String ret = defaultValue;
        if(!this.settingsCache.containsKey(ret)){
            if(!settingsFile.exists()){
                this.settingsCache.put(name, defaultValue);
                StfDocument doc = new StfDocument();
                StfClass rootClass = new StfClass(ROOTCLASSNAME);
                StfProperty prop = new StfProperty(name, defaultValue);
                rootClass.addObject(prop);
                doc.addObject(rootClass);
                try{
                    StfFormatWriter writer = new StfFormatWriter(settingsFile);
                    writer.writeStfDocument(doc);
                    ret = defaultValue;
                }catch(IOException e){
                    logging.log(Level.WARNING, "Exception while writing the settings file", e);
                }

            }
            else{
                StfFormatReader reader = new StfFormatReader();
                try{
                    StfDocument doc = reader.read(settingsFile);
                    StfClass settingClass = (StfClass)doc.getChild(0);
                    
                    //caching the properties of the whole file;
                    AbstractStfObject[] children = settingClass.getChildren();
                    for(AbstractStfObject child: children){
                        if(child instanceof StfProperty){
                            StfProperty p = (StfProperty) child;
                            String propertyName = p.getPropertyName();
                            String propertyValue = p.getValue();
                            this.settingsCache.put(propertyName, propertyValue);
                        }
                    }
                    //getting the Value of the property. if it does not exists in the file we create it.
                    String settingsValue = settingClass.getChildPropertyValue(name);
                    if(settingsValue.length() == 0){
                        settingClass.addObject(new StfProperty(name, defaultValue));
                        StfFormatWriter writer = new StfFormatWriter(settingsFile);
                        writer.writeStfDocument(doc);
                        ret = defaultValue;
                        this.settingsCache.put(name, defaultValue);
                    }
                    else{
                        ret = settingsValue;
                        this.settingsCache.put(name, settingsValue);
                    }
                }catch(IOException e){
                    logging.log(Level.WARNING, "Exception while loading the settings file", e);
                }

            }
        }else {
            ret = this.settingsCache.get(name);
        }
        
        
        return ret;
    }
    
    public void setValue(String name,String value){
        File settingsFile = new File(FILEPATH);
        this.settingsCache.put(name, value);
        if(!settingsFile.exists()){
            StfDocument doc = new StfDocument();
            StfClass cls = new StfClass(ROOTCLASSNAME);
            StfProperty prop = new StfProperty(name, value);
            cls.addObject(prop);
            doc.addObject(cls);
            try{
                StfFormatWriter writer = new StfFormatWriter(settingsFile);
                writer.writeStfDocument(doc);
            }catch(IOException e){
                logging.log(Level.WARNING, "Exception while writing the settings file", e);
            }   
            return;
        }
        StfFormatReader reader = new StfFormatReader();
        try{
            StfDocument doc = reader.read(settingsFile);
            StfClass settingClass = (StfClass)doc.getChild(0);
            AbstractStfObject searchedProperty = settingClass.getObject(name);
            StfFormatWriter writer = new StfFormatWriter(settingsFile);
            if(searchedProperty instanceof StfProperty){
                StfProperty prop = (StfProperty)searchedProperty;
                prop.setValue(value);
                writer.writeStfDocument(doc);
            }
            else if(searchedProperty == null){
                StfProperty prop = new StfProperty(name, value);
                settingClass.addObject(prop);
                writer.writeStfDocument(doc);
            }
        }catch(IOException e){
            logging.log(Level.WARNING, "Exception while writing the settings file", e);
        }
    }
}

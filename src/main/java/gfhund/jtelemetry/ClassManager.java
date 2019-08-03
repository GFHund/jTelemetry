/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfhund.jtelemetry;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/**
 *
 * @author PhilippHolzmann
 */
public class ClassManager {
    private static HashMap<String,Object> loadedClasses = new HashMap<>();
    
    public static Object get(Class className)throws ClassManagerException{
        try {
            Object ret = loadedClasses.get(className.getName());
            if(ret == null) {
                Constructor c = className.getConstructor();
                ret = c.newInstance();
            }
            return ret;
        } catch (InstantiationException|IllegalAccessException|InvocationTargetException|NoSuchMethodException e){
            throw new ClassManagerException();
        }
    }
    
    public static class ClassManagerException extends Exception{
        
    }
}

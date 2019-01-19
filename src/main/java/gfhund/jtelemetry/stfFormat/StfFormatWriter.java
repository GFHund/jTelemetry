/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfhund.jtelemetry.stfFormat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 *
 * @author PhilippGL
 */
public class StfFormatWriter {
    private File m_fileHandle;
    private FileWriter m_fileWriterHandle;
    private int m_depth;
    public StfFormatWriter(String filename) throws IOException{
        //m_fileHandle = new File(filename);
        try{
            m_fileWriterHandle = new FileWriter(filename);
        }catch(IOException e){
            throw e;
        }
        
        m_depth = 0;
    }
    public StfFormatWriter(String filename,String rootName) throws IOException{
        //m_fileHandle = new File(filename);
        try{
            m_fileWriterHandle = new FileWriter(filename);
            m_fileWriterHandle.write("["+rootName+"]{");
        }catch(IOException e){
            throw e;
        }
        m_depth = 1;
    }
    
    public void writeProperty(String propertyName,String propertyValue) throws IOException{
        try{
            this.m_fileWriterHandle.write("["+propertyName+"]="+propertyValue);
        }catch(IOException e){
            throw e;
        }
    }
    
    public void writePropertyClass(String propertyName,HashMap<String,String> values)throws IOException{
        String propertyClass = "["+propertyName+"]{";
        for(Map.Entry<String,String> e:values.entrySet()){
            propertyClass += "["+e.getKey()+"]="+e.getValue();
        }
        propertyClass+="}";
        try{
            this.m_fileWriterHandle.write(propertyClass);
        }catch(IOException e){
            throw e;
        }
    }
    
    public void writePropertyClass(String propertyName)throws IOException{
        try{
            this.m_fileWriterHandle.write("["+propertyName+"]{");
            this.m_depth++;
        }catch(IOException e){
            throw e;
        }
    }
    
    public void closePropertyClass()throws IOException{
        try{
            this.m_fileWriterHandle.write("}");
            this.m_depth--;
        }catch(IOException e){
            throw e;
        }
    }
    
    public void closeFile() throws IOException{
        try{
            for(int i=0;i<m_depth;i++){
                this.m_fileWriterHandle.write("}");
            }
            this.m_fileWriterHandle.close();
        }
        catch(IOException e){
            throw e;
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfhund.jtelemetry.fxgui;

import gfhund.jtelemetry.stfFormat.StfDocument;
import gfhund.jtelemetry.stfFormat.StfFormatReader;
import gfhund.jtelemetry.stfFormat.AbstractStfObject;
       
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.HashMap;
/**
 *
 * @author PhilippGL
 */
public class TelemetryReader {
    
    public TelemetryReader(){
        
    }
    
    public HashMap<String,StfDocument> read(String filename){
        File file = new File(filename);
        return read(file);
    }
    
    public HashMap<String,StfDocument> read(File file){
        StfFormatReader reader = new StfFormatReader();
        HashMap<String,StfDocument> ret = new HashMap<>();
        try{
            ZipFile zipFile = new ZipFile(file);
            //ArrayList<ZipEntry> entries = Collections.list(zipFile.entries());
            for(ZipEntry entry : Collections.list(zipFile.entries())){
                InputStream stream = zipFile.getInputStream(entry);
                StfDocument doc = reader.read(stream,entry.getSize());
                ret.put(entry.getName(), doc);
            }
        }
        catch(IOException e){
            
        }
        return ret;
    }
    
    /**
     * 
     * @param file zip File
     * @param filename File in the Zip File;
     * @return 
     */
    public StfDocument read(File file,String filename){
        StfFormatReader reader = new StfFormatReader();
        try{
            ZipFile zipFile = new ZipFile(file);
            //ArrayList<ZipEntry> entries = Collections.list(zipFile.entries());
            for(ZipEntry entry : Collections.list(zipFile.entries())){
                if(entry.getName().equals(filename)){
                    InputStream stream = zipFile.getInputStream(entry);
                    StfDocument doc = reader.read(stream,entry.getSize());
                    return doc;
                }
                
            }
        }
        catch(IOException e){
            
        }
        return null;
    }
}

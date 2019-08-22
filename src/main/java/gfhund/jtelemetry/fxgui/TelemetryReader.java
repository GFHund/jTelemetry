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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
    
    public String[] getPlayerNames(File file){
        ArrayList<String> ret = new ArrayList<>();
        try{
            
            ZipFile zipFile = new ZipFile(file);
            for(ZipEntry entry : Collections.list(zipFile.entries())){
                String name = entry.getName();
                String[] pathParts = name.split("/");
                ret.add(pathParts[0]);
            }
        }catch(IOException e){
            return (String[])ret.toArray();
        }
        return (String[])ret.toArray();
    }
    public int[] getPlayerLaps(File file,String playername,Date date){
        ArrayList<Integer> ret = new ArrayList<>();
        try{
            ZipFile zipFile = new ZipFile(file);
            for(ZipEntry entry: Collections.list(zipFile.entries())){
                String name = entry.getName();
                String[] pathParts = name.split("/");
                Date fileDate = getDateFromFilename(pathParts[pathParts.length-1]);
                if(fileDate.equals(date)){
                    int lapNum = getLapNumFromFilename(pathParts[pathParts.length-1]);
                    ret.add(lapNum);
                }
            }
        }catch(IOException e){
            
        }
        //return (Integer[]) ret.toArray();
        int[] r = new int[ret.size()];
        for(int i=0;i < r.length;i++){
            r[i] = ret.get(i);
        }
        return r;
    }
    
    public Date[] getPlayerDates(File file,String playername){
        ArrayList<Date> ret = new ArrayList<>();
        try{
            ZipFile zipFile = new ZipFile(file);
            for(ZipEntry entry: Collections.list(zipFile.entries())){
                String name = entry.getName();
                String[] pathParts = name.split("/");
                Date date = getDateFromFilename(pathParts[pathParts.length-1]);
                ret.add(date);
            }
        }catch(IOException e){
            
        }
        return (Date[]) ret.toArray();
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
    
    
    //files has the format yyyyMMddHHmm-lap.stf
    private int getLapNumFromFilename(String filename){
        String relevantPart = filename.substring(filename.length()-19,15);//12 for date +1 + 2 for lap
        String[] parts = relevantPart.split("-");
        if(parts.length == 2 ){
            try{
                int lap = Integer.parseInt(parts[1]);
                return lap;
            }
            catch(NumberFormatException e){
                return -1;
            }
        }
        return -1;
    }
    private Date getDateFromFilename(String filename){
        String relevantPart = filename.substring(filename.length()-19,15);
        String[] parts = relevantPart.split("-");
        if(parts.length == 2 ) {
            try{
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
                Date ret = format.parse(parts[0]);
                return ret;
            } catch(ParseException e){
                return new Date();
            }
        }
        return new Date();
    }
}

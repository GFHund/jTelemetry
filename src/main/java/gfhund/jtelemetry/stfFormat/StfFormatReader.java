/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfhund.jtelemetry.stfFormat;
import java.io.EOFException;
import java.io.File;
//import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author PhilippGL
 */
public class StfFormatReader {
    
    private HashMap<String,Object> m_data;
    public StfDocument read(String filename)throws IOException{
        try{
            return read(new File(filename));
        }
        catch(IOException e){
            throw e;
        }
    }
    public StfDocument read(File file)throws IOException{
        StfDocument ret = new StfDocument();
        try{
            RandomAccessFile reader = new RandomAccessFile(file, "r");
            m_data = new HashMap<>();
            //char character = reader.readChar();
            parseIdentifier(reader,ret);
        }
        catch(IOException e){
            throw e;
        }
        return ret;
    }
    
    private AbstractStfObject parseIdentifier(RandomAccessFile reader,StfClass parent){
        char readChar;
        String identifier = "";
        do{
            try{
                readChar = peekNextCharacter(reader);
                if(readChar == '='){
                    reader.readChar();
                    String value = parseValue(reader);
                    identifier = identifier.replace('[',Character.MIN_VALUE);
                    identifier = identifier.replace(']', Character.MIN_VALUE);
                    parent.addObject(new StfProperty(identifier, value));
                    identifier = "";
                }
                else if(readChar == '{'){
                    reader.readChar();
                    identifier = identifier.replace('[',Character.MIN_VALUE);
                    identifier = identifier.replace(']', Character.MIN_VALUE);
                    AbstractStfObject obj = parseClass(reader,identifier);
                }
                else{
                    readChar = reader.readChar();
                    identifier += readChar;
                }
            }catch(EOFException e){
                break;
            }
            catch(IOException e){
                //return false;
            }
          
        }while(true);
        
        return parent;
    }
    private String parseValue(RandomAccessFile reader){
        char readChar;
        String value = "";
        do{
            try{
                readChar = peekNextCharacter(reader);
                if(readChar != '[' || readChar != '}'){
                    readChar = reader.readChar();
                    value += readChar;
                }else{
                    return value;
                }
            }catch(IOException e){
                break;
            }
            
        }while(true);
        return "";
    }
    private StfClass parseClass(RandomAccessFile reader,String propertyName){
        char readChar;
        //ArrayList<AbstractStfObject> objects = new ArrayList<AbstractStfObject>();
        StfClass ret = new StfClass(propertyName);
        do{
            try{
                readChar = peekNextCharacter(reader);
                if(readChar != '}'){
                    AbstractStfObject obj = parseIdentifier(reader, ret);
                    ret.addObject(obj);
                }
                else {
                    break;
                }
            }catch(IOException e){
                break;
            }
        }while(true);
        return ret;
    }
    private char peekNextCharacter(RandomAccessFile reader)throws IOException{
        try{
            long filePointer = reader.getFilePointer();
            char nextCharacter = reader.readChar();
            reader.seek(filePointer);
            return nextCharacter;
        }catch(IOException e){
            throw e;
        }
    }
}

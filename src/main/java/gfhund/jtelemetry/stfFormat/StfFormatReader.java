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
            ret = (StfDocument)parseIdentifier(reader,ret);
        }
        catch(IOException e){
            throw e;
        }
        return ret;
    }
    
    private AbstractStfObject parseIdentifier(RandomAccessFile reader,StfClass parent){
        char readChar;
        String identifier = "";
        try{
            long fileLength = reader.length();
            long curOffset = reader.getFilePointer();
            do{
                readChar = peekNextCharacter(reader);
                if(readChar == '='){
                    reader.read();
                    String value = parseValue(reader);
                    identifier = identifier.replace('[',Character.MIN_VALUE);
                    identifier = identifier.replace(']', Character.MIN_VALUE);
                    parent.addObject(new StfProperty(identifier, value));
                    identifier = "";
                }
                else if(readChar == '{'){
                    reader.read();
                    identifier = identifier.replace('[',Character.MIN_VALUE);
                    identifier = identifier.replace(']', Character.MIN_VALUE);
                    AbstractStfObject obj = parseClass(reader,identifier);
                    parent.addObject(obj);
                    identifier = "";
                }
                else if(readChar == '}'){
                    break;
                }
                else{
                    readChar = (char)reader.read();
                    identifier += readChar;
                }
                curOffset = reader.getFilePointer();

            }while(fileLength > curOffset);
        }catch(EOFException e){
            //break;
        }
        catch(IOException e){
            //return false;
        }
        
        return parent;
    }
    private String parseValue(RandomAccessFile reader){
        char readChar;
        String value = "";
        try{
            long fileLength = reader.length();
            long curOffset = reader.getFilePointer();
            do{
                readChar = peekNextCharacter(reader);
                if(readChar != '[' && readChar != '}'){
                    readChar = (char)reader.read();
                    value += readChar;
                }else{
                    return value;
                }
                curOffset = reader.getFilePointer();

            }while(fileLength > curOffset);
        }catch(IOException e){
            return "";
        }
        return value;
    }
    private StfClass parseClass(RandomAccessFile reader,String propertyName){
        char readChar;
        //ArrayList<AbstractStfObject> objects = new ArrayList<AbstractStfObject>();
        StfClass ret = new StfClass(propertyName);
        try{
            long fileLength = reader.length();
            long curOffset = reader.getFilePointer();
            do{
                readChar = peekNextCharacter(reader);
                if(readChar != '}'){
                    AbstractStfObject obj = parseIdentifier(reader, ret);
                    //ret.addObject(obj);
                    ret = (StfClass)obj;
                }
                else {
                    break;
                }
                curOffset = reader.getFilePointer();
            }while(fileLength > curOffset);
        }catch(IOException e){
            return null;
        }
        return ret;
    }
    private char peekNextCharacter(RandomAccessFile reader)throws IOException{
        try{
            long filePointer = reader.getFilePointer();
            //char nextCharacter = reader.readChar();
            char nextCharacter = (char)reader.read();
            reader.seek(filePointer);
            return nextCharacter;
        }catch(IOException e){
            throw e;
        }
    }
}

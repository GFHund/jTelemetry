/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gfhund.jtelemetry.stfFormat;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
/**
 *
 * @author PhilippGL
 */
class StfFormatReaderTest {
    
    @BeforeAll
    static void prepareTest(){
        try{
            File testCase1 = new File("testCase1.stf");
            FileWriter testCase1Writer = new FileWriter(testCase1);
            testCase1Writer.write("[prop1]=50[prop2]=5.5[prop3]=Hallo Welt");
            testCase1Writer.close();
            
            File testCase2 = new File("testCase2.stf");
            FileWriter testCase2Writer = new FileWriter(testCase2);
            testCase2Writer.write("[class1]{[prop1]=50[prop2]=5.5[prop3]=Hallo Welt}");
            testCase2Writer.close();
            
            File testCase3 = new File("testCase3.stf");
            FileWriter testCase3Writer = new FileWriter(testCase3);
            testCase3Writer.write("[class1]{[class2]{[prop1]=50[prop2]=5.5[prop3]=Hallo Welt}}");
            testCase3Writer.close();
            
            //File wrongTestCase1 = new File("wrongTestCase1.stf");
            //FileWriter wrongTestCase5Writer = new FileWriter(wrongTestCase1);
            //wrongTestCase5Writer.write("[prop1]=50[prop2]=5.5[prop3]=Hallo Welt");
        }catch(IOException e){
            
        }
        
    }
    
    @Test
    void testCase1Test() throws IOException{
        StfFormatReader reader = new StfFormatReader();
        try{
            StfDocument obj = reader.read("testCase1.stf");
            assertTrue(obj instanceof StfDocument);
            String value = obj.getValue("prop1");
            assertEquals("50",value);
            value = obj.getValue("prop2");
            assertEquals("5.5",value);
            value = obj.getValue("prop3");
            assertEquals("Hallo Welt",value);
        }catch(IOException e){
            throw e;
        }
        
        
        //assertEquals(2,1+1);//hallo Welt
    }
    
    @Test
    void testCase2Test()throws IOException{
       StfFormatReader reader = new StfFormatReader();
        try{
            StfDocument obj = reader.read("testCase2.stf");
            assertTrue(obj instanceof StfDocument);
            String value = obj.getValue("class1.prop1");
            assertEquals("50",value);
            value = obj.getValue("class1.prop2");
            assertEquals("5.5",value);
            value = obj.getValue("class1.prop3");
            assertEquals("Hallo Welt",value);
        }catch(IOException e){
            throw e;
        } 
    }
    
    @Test
    void testCase3Test()throws IOException{
       StfFormatReader reader = new StfFormatReader();
        try{
            StfDocument obj = reader.read("testCase3.stf");
            assertTrue(obj instanceof StfDocument);
            String value = obj.getValue("class1.class2.prop1");
            assertEquals("50",value);
            value = obj.getValue("class1.class2.prop2");
            assertEquals("5.5",value);
            value = obj.getValue("class1.class2.prop3");
            assertEquals("Hallo Welt",value);
        }catch(IOException e){
            throw e;
        } 
    }
    
    @AfterAll
    static void cleanUp(){
        
    }
}

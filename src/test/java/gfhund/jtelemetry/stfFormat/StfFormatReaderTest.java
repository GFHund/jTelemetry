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
    void prepareTest(){
        try{
            File testCase1 = new File("testCase1.stf");
            FileWriter testCase1Writer = new FileWriter(testCase1);
            testCase1Writer.write("[prop1]=50[prop2]=5.5[prop3]=Hallo Welt");
            
            File testCase2 = new File("testCase2.stf");
            FileWriter testCase2Writer = new FileWriter(testCase2);
            testCase2Writer.write("[class1]{[prop1]=50[prop2]=5.5[prop3]=Hallo Welt}");
            
            File testCase3 = new File("testCase3.stf");
            FileWriter testCase3Writer = new FileWriter(testCase3);
            testCase3Writer.write("[class1]{[class2]={[prop1]=50[prop2]=5.5[prop3]=Hallo Welt}}");
            
            File testCase4 = new File("testCase4.stf");
            FileWriter testCase4Writer = new FileWriter(testCase4);
            testCase4Writer.write("[class1]{[class2]={[prop1]=50[prop2]=5.5[prop3]=Hallo Welt}}");
            
            File testCase5 = new File("testCase5.stf");
            FileWriter testCase5Writer = new FileWriter(testCase5);
            testCase5Writer.write("[class1]{[prop1]=50[class2]={[prop2]=5.5[prop3]=Hallo Welt}}");
            
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
            AbstractStfObject obj = reader.read("testCase1.stf");
            assertTrue(obj instanceof StfDocument);
            
        }catch(IOException e){
            throw e;
        }
        
        
        assertEquals(2,1+1);//hallo Welt
    }
    
    @AfterAll
    void cleanUp(){
        
    }
}

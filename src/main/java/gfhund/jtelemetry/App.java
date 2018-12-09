/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfhund.jtelemetry;

import java.awt.EventQueue;
import javafx.application.Application;

/**
 *
 * @author PhilippGL
 */
public class App {
    public static void main(String[] args){
        String guiProperty = System.getProperty("useSwing");
        boolean useSwing = Boolean.parseBoolean(guiProperty);
        if(useSwing){
            EventQueue.invokeLater(()->{
                SwingMain app = new SwingMain();
                app.setVisible(true);
            });
            
        }
        else{
            //new JavaFxMain();
            Application.launch(JavaFxMain.class, args);
        }
    }
}

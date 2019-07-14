/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfhund.jtelemetry.fxgui;

/**
 *
 * @author PhilippHolzmann
 */
public class ControllerManager {
    private static ControllerManager m_instance = null;
    
    public static ControllerManager getInstance(){
        if(ControllerManager.m_instance == null){
            ControllerManager.m_instance = new ControllerManager();
        }
        return ControllerManager.m_instance;
    }
}

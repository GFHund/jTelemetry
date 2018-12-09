/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfhund.jtelemetry.f1y18;

/**
 *
 * @author PhilippGL
 */
public class F1Y2018ParseException extends Exception{
    @Override
    public String getMessage(){
        return "Could not Parse File or String";
    }
}

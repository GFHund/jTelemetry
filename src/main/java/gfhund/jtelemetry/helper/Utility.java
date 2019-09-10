/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfhund.jtelemetry.helper;

/**
 *
 * @author PhilippHolzmann
 */
public class Utility {
    public static String handlePlayerName(String playerName){
        return playerName.replaceAll("[\\\\|\\||:|\\/|*|<|>|\\?|\\\"]", "");
    }
}

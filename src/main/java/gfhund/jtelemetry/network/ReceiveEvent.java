/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfhund.jtelemetry.network;

/**
 *
 * @author PhilippGL
 */
public abstract class ReceiveEvent {
    public abstract void onReceive(byte[] data);
}

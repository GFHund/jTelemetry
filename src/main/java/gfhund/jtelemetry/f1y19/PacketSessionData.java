/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfhund.jtelemetry.f1y19;

/**
 *
 * @author PhilippHolzmann
 */
public class PacketSessionData extends gfhund.jtelemetry.f1y18.PacketSessionData {
    protected Header header19;
    
    public Header getHeader19(){
        return this.header19;
    }
    public void setHeader19(Header header){
        this.header19 = header;
    }
}

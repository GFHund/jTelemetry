/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfhund.jtelemetry.f1y19;

import gfhund.jtelemetry.commontelemetry.AbstractPacket;
/**
 *
 * @author PhilippHolzmann
 */
public class PacketEventData extends AbstractPacket {
    protected Header header19;
    protected String eventStringCode;
    protected EventDataDetails details;
    
    public Header getHeader19(){
        return this.header19;
    }
    public void setHeader19(Header header){
        this.header19 = header;
    }
    public String getEventStringCode(){
        return this.eventStringCode;
    }
    public void setEventStringCode(String code){
        this.eventStringCode = code;
    }
    public EventDataDetails getDetails(){
        return this.details;
    }
    public void setDetails(EventDataDetails det){
        this.details = det;
    }
}

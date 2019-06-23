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
public class PacketEventData {
    protected Header m_header19;
    protected String m_eventStringCode;
    protected EventDataDetails m_details;
    
    public Header getHeader19(){
        return this.m_header19;
    }
    public void setHeader19(Header header){
        this.m_header19 = header;
    }
    public String getEventStringCode(){
        return this.m_eventStringCode;
    }
    public void setEventStringCode(String code){
        this.m_eventStringCode = code;
    }
    public EventDataDetails getDetails(){
        return this.m_details;
    }
    public void setDetails(EventDataDetails det){
        this.m_details = det;
    }
}

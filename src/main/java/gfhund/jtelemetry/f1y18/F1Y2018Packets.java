package gfhund.jtelemetry.f1y18;

import gfhund.jtelemetry.f1common.PacketBuilder;
import gfhund.jtelemetry.commontelemetry.AbstractPacket;
import java.util.ArrayList;
import java.util.List;
import gfhund.jtelemetry.data.AbstractPackets;
import gfhund.jtelemetry.data.Timing;
import java.nio.charset.Charset;


public class F1Y2018Packets extends AbstractPackets{
    private ArrayList<AbstractPacket> m_packets; 


    private F1Y2018Packets(){}

    public static F1Y2018Packets parse(String fileContent) throws F1Y2018ParseException{
        ArrayList<AbstractPacket> packets = new ArrayList<AbstractPacket>();
        byte[] rawSplit = {(byte)0xaa,(byte)0xaa,(byte)0xaa,(byte)0xaa};
        String split = new String(rawSplit);//,Charset.forName("ascii")
        String[] sPackets = fileContent.split(split);
        for(String packet:sPackets){
            try{
                byte[] rawPacket = packet.getBytes();
                AbstractPacket packetObj = PacketBuilder.parseUDPPacket2018(rawPacket);
                if(packetObj != null){
                    packets.add(packetObj);
                }
            }catch(F1Y2018ParseException e ){
                throw e;
            }
        }
        F1Y2018Packets ret = new F1Y2018Packets();
        ret.setPackets(packets);
        return ret;
        
    }
    private static void splitArray(){
        
    }
    public void setPackets(ArrayList<AbstractPacket> packets){
        this.m_packets = packets;
    }
    
    public int countTimings(){
        int i=0;
        for(AbstractPacket packet: this.m_packets){
            if(packet instanceof PacketLapData){
                i++;
            }
        }
        return i;
    }
    
    public Timing[] getTimings(){
        int length = countTimings();
        Timing[] ret = new Timing[length];
        int i=0;
        for(AbstractPacket packet: this.m_packets){
            if(packet instanceof PacketLapData){
                int playerIndex = ((PacketLapData) packet).getHeader().getPlayerCarIndex();
                LapData lapData = ((PacketLapData) packet).getLapData(playerIndex);
                ret[i]=new Timing();
                ret[i].setLapTime(lapData.getCurrentLapTime());
                ret[i].setSector1Time(lapData.getSector1Time());
                ret[i].setSector2Time(lapData.getSector2Time());
                i++;
            }
        }
        return ret;
    } 
}
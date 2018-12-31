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
public enum TrackID{
    MELBOURNE( (byte)0 ),
    PAUL_RICARD((byte)1),
    SHANGHAI ( (byte)2),
    BAHRAIN ((byte)3),
    CATALUNYA ((byte)4),
    MONACO ((byte)5),
    MONTREAL ((byte)6),
    SILBERSTONE ((byte)7),
    HOCKENHEIM ((byte)8),
    HUNGARORING((byte)9),
    SPA((byte)10),
    MONZA((byte)11),
    SINGAPORE((byte)12),
    SUZUKA((byte)13),
    ABU_DHABI((byte)14),
    TEXAS((byte)15),
    BRAZIL((byte)16),
    AUSTRIA((byte)17),
    SOCHI((byte)18),
    MEXICO((byte)19),
    BAKU((byte)20),
    BAHRAIN_SHORT((byte)21),
    SILVERSTONE_SHORT((byte)22),
    TEXAS_SHORT((byte)23),
    SUZUKA_SHORT((byte)24);

    private byte value;
    TrackID(byte num){
        this.value = num;
    }
    public static TrackID getTrackIdFromValue(byte value){
        return TrackID.values()[value];
    }
    public byte getValue(){
        return this.value;
    }
}
package gfhund.jtelemetry.f1y18;

class PacketSessionData extends AbstractPacket{
    private Header m_header;
    private Weather m_weather;
    private byte m_trackTemperature;
    private byte m_airTemperature;
    private byte m_totalLaps;
    private short m_trackLenghth;
    private SessionType m_sessionType;
    private TrackID m_trackId;
    private byte m_era;
    private short m_sessionTimeLeft;
    private short m_sessionDuration;
    private byte m_pitSpeedLimit;// Pit speed limit in kilometres per hour
    private byte m_gamePaused;
    private byte m_isSpectating;
    private byte m_spectatorCarIndex;
    private byte m_sliProNativeSupport;
    private byte m_numMarshalZones;
    private MarshalZone[] m_marshalZones = new MarshalZone[21];
    private byte m_safetyCarStatus;// 0 = no safety car, 1 = full safety car
    // 2 = virtual safety car
    private byte m_networkGame;// 0 = offline, 1 = online
}

enum Weather{
    CLEAR( (byte)0 ),
    LIGHT_CLOUD((byte)1),
    OVERCAST ( (byte)2),
    LIGHT_RAIN ((byte)3),
    HEAVY_RAIN ((byte)4),
    STORM ((byte)5);

    private byte value;
    Weather(byte num){
        this.value = num;
    }
    public int getValue(){
        return this.value;
    }
}
enum SessionType{
    UNKNOWN( (byte)0 ),
    P1((byte)1),
    P2 ( (byte)2),
    P3 ((byte)3),
    SHORT_P ((byte)4),
    Q1 ((byte)5),
    Q2 ((byte)6),
    Q3 ((byte)7),
    SHORT_Q ((byte)8),
    OSQ((byte)9),
    R((byte)10),
    R2((byte)11),
    TIME_TRIAL((byte)12);

    private byte value;
    SessionType(byte num){
        this.value = num;
    }
    public int getValue(){
        return this.value;
    }
}
enum TrackID{
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
    public int getValue(){
        return this.value;
    }
}
package gfhund.jtelemetry.f1y18;

class CarTelemetryData{
    private short m_speed;
    private byte m_throttle;
    private byte m_steer;
    private byte m_brake;
    private byte m_clutch;
    private byte m_gear;
    private short m_engineRPM;
    private byte m_drs;
    private byte m_revLightsPercent;
    private short[] m_brakesTemperature = new short[4];
    private short[] m_tyresSurfaceTemperature = new short[4];
    private short[] m_tyresInnerTemperature = new short[4];
    private short m_engineTemperature;
    private float[] m_tyresPressure = new float[4];
}
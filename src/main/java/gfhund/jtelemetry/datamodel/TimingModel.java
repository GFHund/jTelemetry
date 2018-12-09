package gfhund.jtelemetry.datamodel;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import gfhund.jtelemetry.data.Timing;

public class TimingModel extends AbstractTableModel{
    private static final long serialVersionUID = 1L;
    private ArrayList<Timing> mLaps;
    private String[] mColumnHeader = {
        "select",
        "Lap Time",
        "Sector1 Time",
        "Sector2 Time",
        "Sector3 Time",
        "Sector4 Time",
        "Sector5 Time"
    };

    public TimingModel(ArrayList<Timing> laps){
        this.mLaps = laps;
    }
    
    @Override 
    public int getRowCount()
    {
        return this.mLaps.size();
    }

    @Override 
    public int getColumnCount()
    {
        return mColumnHeader.length;
    }

    @Override 
    public Object getValueAt( int row, int col )
    {
        switch(col){
            case 0:{
                break;
            }
            case 1:{
                return this.mLaps.get(row).getLapTime();
                //break;
            }
            case 2:{
                return this.mLaps.get(row).getSector1Time();
                //break;
            }
            case 3:{
                return this.mLaps.get(row).getSector2Time();
                //break;
            }
            case 4:{
                return this.mLaps.get(row).getSector3Time();
                //break;
            }
            case 5:{
                return this.mLaps.get(row).getSector4Time();
                //break;
            }
            case 6:{
                return this.mLaps.get(row).getSector5Time();
                //break;
            }
        }
        return "";
    }

    @Override
    public String getColumnName(int col)
    {
        return mColumnHeader[col];
    }

    @Override
    public Class<?> getColumnClass(int col)
    {
        if(col == 0){
            return Boolean.class;
        }
        return super.getColumnClass(col);
    }
}
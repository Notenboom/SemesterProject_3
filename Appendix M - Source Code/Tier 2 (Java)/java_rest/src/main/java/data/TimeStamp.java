package data;

import org.joda.time.DateTime;

import java.util.Objects;

/**
 * Class that hold the timeStamp object
 * @author Group 6
 * @version 0.8.4
 */
public class TimeStamp
{
    private DateTime TimePunch;
    private int EmployeeId;
    private boolean IsBreak;
    private boolean PunchIn;
    private boolean PunchInBreak;

    /**
     * No argument constructor needed for Json Serialization/Deserialization
     */
    public TimeStamp()
    {
        super();
    }

    /**
     * Extended class constructor
     * @param date
     * @param employeeId
     * @param isBreak
     * @param PunchIn
     * @param PunchInBreak
     */
    public TimeStamp(DateTime date, int employeeId, boolean isBreak, boolean PunchIn, boolean PunchInBreak)
    {
        this.TimePunch = date;
        this.EmployeeId = employeeId;
        this.IsBreak = isBreak;
        this.PunchIn = PunchIn;
        this.PunchInBreak = PunchInBreak;
    }

    public boolean isPunchInBreak()
    {
        return PunchInBreak;
    }

    public void setPunchInBreak(boolean punchInBreak)
    {
        PunchInBreak = punchInBreak;
    }

    public boolean isPunchIn()
    {
        return PunchIn;
    }

    public void setPunchIn(boolean punchIn)
    {
        PunchIn = punchIn;
    }

     public DateTime getTimePunch()
    {
        return TimePunch;
    }

    public int getEmployeeId()
    {
        return EmployeeId;
    }

    public boolean isBreak()
    {
        return IsBreak;
    }

   public void setTimePunch(DateTime timePunch)
   {
       this.TimePunch = timePunch;
   }

    public void setEmployeeId(int employeeId)
    {
        this.EmployeeId = employeeId;
    }

    public void setBreak(boolean aBreak)
    {
        IsBreak = aBreak;
    }

    @Override
    public String toString()
    {
        return "TimeStamp{" + "timePunch='" + TimePunch + '\'' + ", employeeId='" + EmployeeId + '\'' + ", isBreak=" + IsBreak + '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }
        if(o == null || getClass() != o.getClass())
        {
            return false;
        }
        TimeStamp timeStamp = (TimeStamp) o;
        return EmployeeId == timeStamp.EmployeeId && IsBreak == timeStamp.IsBreak && PunchIn == timeStamp.PunchIn && PunchInBreak == timeStamp.PunchInBreak  && Objects.equals(TimePunch, timeStamp.TimePunch);
    }
}

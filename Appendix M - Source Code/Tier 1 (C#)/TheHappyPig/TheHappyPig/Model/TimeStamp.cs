using System;

namespace TheHappyPig.Model
{
  public class TimeStamp
  {
        public DateTime TimePunch { get; set; }
        public int EmployeeId { get; set; }
        public Boolean IsBreak { get; set; }
        public Boolean PunchIn { get; set; }
        public Boolean PunchInBreak { get; set; }

        //No argument constructor for Json Serialization and Deserialization
        public TimeStamp() : base()
        {

        } 
        public TimeStamp(DateTime TimePunch, int EmployeeId, Boolean IsBreak)
        {
            this.TimePunch = TimePunch;
            this.EmployeeId = EmployeeId;
            this.IsBreak = IsBreak;
            this.PunchIn = true;
            this.PunchInBreak = false; 
        }

        public TimeStamp(DateTime TimePunch, int EmployeeId, Boolean IsBreak, Boolean PunchIn)
        {
            this.TimePunch = TimePunch;
            this.EmployeeId = EmployeeId;
            this.IsBreak = IsBreak;
            this.PunchIn = PunchIn;
        }

        override
            public String ToString()
        {
            return "TimePunch: " + TimePunch + " EmployeeId: " + EmployeeId + " IsBreak: " + IsBreak; 
        }
   }
}

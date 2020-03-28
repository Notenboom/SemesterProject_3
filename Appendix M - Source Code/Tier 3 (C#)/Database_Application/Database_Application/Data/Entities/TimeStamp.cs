using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Text;

namespace Database_Application.Data.Entities
{
    class TimeStamp
    {
        public DateTime TimePunch { get; set; }
        public int EmployeeId { get; set; }
        public Boolean IsBreak { get; set; }
        public Boolean PunchIn { get; set; }
        public Boolean PunchInBreak { get; set; }
        public TimeStamp() : base() 
        {

        }
        public TimeStamp(DateTime TimePunch, int EmployeeId, Boolean IsBreak, Boolean PunchIn, Boolean PunchInBreak)
        {
            this.TimePunch = TimePunch;
            this.EmployeeId = EmployeeId;
            this.IsBreak = IsBreak;
            this.PunchIn = PunchIn;
            this.PunchInBreak = PunchInBreak;
        }

        public override string ToString()
        {
            return "EmployeeId: " + EmployeeId + " Punch: " + TimePunch.ToString() + " Break: " + IsBreak;
    }
    }
}

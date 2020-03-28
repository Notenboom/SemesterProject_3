using Database_Application.Data.Entities;
using System;
using System.Linq;

namespace Database_Application.Data
{
    class DbInitializer
    {
        public static void Initiliaze(TimeStampContext context)
        {
            context.Database.EnsureCreated();
       

            if (context.TimeStamps.Any())
            {
                Console.WriteLine("Connecting to the Database");
                return;
            }

            Console.WriteLine("Initializing the Database");

            var timeStamps = new TimeStamp[]
            {
                new TimeStamp
                {
                    TimePunch = DateTime.Now,
                    EmployeeId = 1111,
                    IsBreak = false,
                    PunchIn = true,
                    PunchInBreak = false
                }
            };

            foreach (var timeStamp in timeStamps)
            {
                context.Add(timeStamp);
                context.SaveChanges();
            }
        }
    }
}

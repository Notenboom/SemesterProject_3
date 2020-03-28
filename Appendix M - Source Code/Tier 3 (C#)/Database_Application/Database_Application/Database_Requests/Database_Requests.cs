using Database_Application.Data.Entities;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Database_Application.Database_Requests
{
    //Handles database requests
   //Singleton implementation
   //Needs fixing with multi-threading
   //Needs catching the exceptions
    sealed class Database_Requests
    {
        private static readonly object padlock = new object();
        private static volatile Database_Requests instance = null;
        private TimeStampContext context = TimeStampContext.Instance;

        private Database_Requests()
        {

        }
        public static Database_Requests Instance
        {
            get
            {
                if (instance == null)
                {
                    lock (padlock)
                    {
                        if (instance == null)
                        {
                            instance = new Database_Requests();
                        }
                    }
                }

                return instance;
            }
        }


        public TimeStamp getTimeStampById()
        {
            return context.TimeStamps.Select(p => new TimeStamp(p.TimePunch, p.EmployeeId, p.IsBreak, p.PunchIn, p.PunchInBreak)).ToList().Last();
        }

        public void insertTimeStamp(TimeStamp timeStamp)
        {
            try
            {
                context.TimeStamps.Add(timeStamp);
                context.SaveChanges();
            }
            catch(Exception ex)
            {
                
            }

        }

        public async Task<List<Employee>> getAllEmployees()
        {
            return await context.Employees.Select(p => new Employee(p.FirstName, p.LastName, p.Email, p.EmployeeId, p.Password)).ToListAsync(); 
        }

        public async Task<List<TimeStamp>> getAllRegistrations()
        {
            return await context.TimeStamps.Select(p => new TimeStamp(p.TimePunch, p.EmployeeId, p.IsBreak, p.PunchIn, p.PunchInBreak)).ToListAsync();
        }

        public void insertEmployee(Employee employee)
        {
           

            try
            {
                context.Employees.Add(employee);
                context.SaveChanges();
            }
            catch(Exception ex)
            {
                Console.WriteLine("Base employee already in the system"); ;
            }
            
        }

        public void deleteEmployee(Employee employeeObj)
        {
            context.Remove(context.Employees.Single(o => o.EmployeeId == employeeObj.EmployeeId));
            context.SaveChanges(); 
        }
    }
}
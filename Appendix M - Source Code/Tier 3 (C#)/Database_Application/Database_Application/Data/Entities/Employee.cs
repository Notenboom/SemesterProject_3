using System;
using System.Collections.Generic;
using System.Text;

namespace Database_Application.Data.Entities
{
    class Employee
    {
        public string FirstName { get; set; }
        public string LastName { get; set; }
        public string Email { get; set; }
        public int EmployeeId { get; set; }
        public string Password { get; set; }

        public Employee() : base()
        {

        }

        public Employee(string FirstName, string LastName, string Email, int EmployeeId, string Password)
        {
            this.FirstName = FirstName;
            this.LastName = LastName;
            this.Email = Email;
            this.EmployeeId = EmployeeId;
            this.Password = Password;
        }
    }
}

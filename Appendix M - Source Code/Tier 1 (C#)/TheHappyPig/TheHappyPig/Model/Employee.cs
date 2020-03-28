namespace TheHappyPig.Model
{
    public class Employee
    {
        public string FirstName { get; set; }
        public string LastName { get; set; }
        public string Email { get; set; }
        public int EmployeeId { get; set; }
       
        public string Password { get; set; }

        //No argument constructor for Json Serialization and Deserialization
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

        public Employee(int EmployeeId, string Password)
        {
            this.FirstName = "Not Set";
            this.LastName = "Not Set";
            this.Email = "Not Set";
            this.EmployeeId = EmployeeId;
            this.Password = Password;
        }
    }
}

package data;

import java.util.Objects;

/**
 * Class for holding the employee object
 * @author Group 6
 * @version 1.7.2
 */
public class Employee
{

    private String FirstName;
    private String LastName;
    private String Email;
    private int EmployeeId;
    private String Password;

    /**
     * No argument constructor needed for Json Serialization/Deserialization
     */
    public Employee()
    {
        super();
    }

    /**
     * Base class constructor
     * @param employeeId
     * @param password
     */
    public Employee(int employeeId, String password)
    {
        EmployeeId = employeeId;
        Password = password;
    }

    /**
     * Extended class constructor (added @version 1.0)
     * @param FirstName
     * @param LastName
     * @param Email
     * @param EmployeeId
     * @param Password
     */
    public Employee(String FirstName, String LastName, String Email, int EmployeeId, String Password)
    {
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Email = Email;
        this.EmployeeId = EmployeeId;
        this.Password = Password;
    }

    public String getFirstName()
    {
        return FirstName;
    }

    public void setFirstName(String firstName)
    {
        FirstName = firstName;
    }

    public String getLastName()
    {
        return LastName;
    }

    public void setLastName(String lastName)
    {
        LastName = lastName;
    }

    public String getEmail()
    {
        return Email;
    }

    public void setEmail(String email)
    {
        Email = email;
    }

    public int getEmployeeId()
    {
        return EmployeeId;
    }

    public void setEmployeeId(int employeeId)
    {
        EmployeeId = employeeId;
    }

    public String getPassword()
    {
        return Password;
    }

    public void setPassword(String password)
    {
        Password = password;
    }

    @Override
    public String toString()
    {
        return "Employee{" + "EmployeeId='" + EmployeeId + '\'' + ", Password='" + Password + '\'' + '}';
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
        Employee employee = (Employee) o;

        return EmployeeId == employee.EmployeeId && Objects.equals(Password, employee.Password);
    }
}

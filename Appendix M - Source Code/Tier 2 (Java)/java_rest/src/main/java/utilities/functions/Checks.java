package utilities.functions;

import data.Employee;

import java.util.ArrayList;
import java.util.Base64;

/**
 * Class holding similar validation functions
 * @author Group 6
 * @version 2.3.1
 */
public class Checks
{
    /**
     * Validation of the credentials of a employee against any existing employees in the system (used for basic logins)
     * @param emploeeList
     * @param employee
     * @return True for validated, false for not validated
     */
    public static boolean checkCredentials(ArrayList<Employee> emploeeList, Employee employee)
    {
        return emploeeList.stream().anyMatch(o -> o.getEmployeeId() == employee.getEmployeeId() && HashFunctions.checkPassword(employee.getPassword(), o.getPassword()));
    }

    /**
     * Validation for double registering an employee
     * @param emploeeList
     * @param employee
     * @return False if the employee to be checked already exists in the system, true if it does not
     */
    public static boolean checkEmployees(ArrayList<Employee> emploeeList, Employee employee)
    {
        return !emploeeList.contains(employee);
    }

    /**
     * Validation for the user's credentials against existing users upon HTTP Requests
     * @implNote Uses checkCredentials subroutine
     * @param authString
     * @param employeesList
     * @return
     */
    public static boolean isUserAuthenticated(String authString, ArrayList<Employee> employeesList)
    {
        String decodedAuth = "";

        String[] authParts = authString.split("\\s+");
        String authInfo = authParts[1];

        byte[] bytes = null;

        bytes = Base64.getDecoder().decode(authInfo);

        decodedAuth = new String(bytes);

        String[] authorizationInfo = decodedAuth.split(":");

        return checkCredentials(employeesList, new Employee(Integer.parseInt(authorizationInfo[0]), authorizationInfo[1]));
    }
}

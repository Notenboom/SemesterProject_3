package java_rest;

import com.fatboyindustrial.gsonjodatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import data.TimeStamp;
import data.Employee;

import org.joda.time.DateTime;
import utilities.functions.Checks;
import utilities.functions.DataStream;
import utilities.functions.HashFunctions;
import utilities.objects.API_Response;
import utilities.objects.Logedin;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;


/**
 * RESTful API Implementation
 * @implNote Used as a business logic backbone for requests coming from the clients
 * @author Group 6
 * @version 3.2.7
 */
@Path("/thehappypig")
public class RESTApi
{
    // Main from/to Json Converter
    private static Gson gson = Converters.registerDateTime(new GsonBuilder()).create();

    /**
     * Initializes the system at the first startup
     * @return String Confirmation that the system was successfully initialized
     */
    @GET()
    @Path("/initiate")
    public String initial()
    {
        // Calls the internal implementation
        initialRegistration();

        // Client confirmation
        return "<html> " + "<title>" + "The Happy Pig Company" + "</title>"
                + "<body><h1>" + "The system was successfully started" + "</body></h1>" + "</html> ";
    }

    /**
     * Registers time stamps comming from the client
     * @implNote Includes certain logic for checking for human error as well as properly
     * categorizing the time stamps
     * @param registration Json coming from the client
     * @return A string that confirms the state of the registration
     */
    @POST
    @Path("/punchin")
    @Produces(MediaType.APPLICATION_JSON)
    public String registerTimePunch(String registration)
    {
        // Deserialization of the incoming time stamp
        TimeStamp timeStamp = gson.fromJson(registration, TimeStamp.class);

        // Preparing the response for the client
        API_Response response = new API_Response();

        // Getting the list of the currently registered employees
        ArrayList<Employee> employeeList = DataStream.getAllEmployees();

        // Different validations and categorizing of the time stamp
        for(int i = 0; i < employeeList.size(); i++)
        {
            if(timeStamp.getEmployeeId() == employeeList.get(i).getEmployeeId())
            {
                ArrayList<TimeStamp> registrationList = DataStream.getAllRegistrations();

                for(int j = 0; j < registrationList.size(); j++)
                {
                    if(registrationList.get(j).getEmployeeId() == timeStamp.getEmployeeId())
                    {
                        if(!timeStamp.isBreak() && !registrationList.get(j).isPunchInBreak())
                        {
                            if(registrationList.get(j).isPunchIn() || (registrationList.get(j).isBreak() && !registrationList.get(j).isPunchInBreak()))
                            {
                                timeStamp.setPunchIn(false);
                                response.setMessage("Punching out...");
                                response.setAttribute(gson.toJson(timeStamp));
                            }
                            else
                            {
                                timeStamp.setPunchIn(true);
                                response.setMessage("Punching in...");
                                response.setAttribute(gson.toJson(timeStamp));
                            }
                        }
                        else if(!timeStamp.isBreak() && registrationList.get(j).isPunchInBreak())
                        {
                            response.setMessage("Cannot punch out. Employee on break.");
                            response.setAttribute(null);
                        }
                        else if(timeStamp.isBreak() && !registrationList.get(j).isPunchIn() && registrationList.get(j).isPunchInBreak())
                        {

                                timeStamp.setPunchInBreak(false);
                                timeStamp.setPunchIn(registrationList.get(j).isPunchIn());
                                response.setMessage("From break...");
                                response.setAttribute(gson.toJson(timeStamp));
                        }
                        else if(timeStamp.isBreak() && registrationList.get(j).isPunchIn() && !registrationList.get(j).isPunchInBreak())
                        {
                            timeStamp.setPunchInBreak(true);
                            timeStamp.setPunchIn(!registrationList.get(j).isPunchIn());
                            response.setMessage("To break...");
                            response.setMessage("To break...");
                            response.setAttribute(gson.toJson(timeStamp));
                        }
                        else if(timeStamp.isBreak() && !registrationList.get(j).isPunchIn())
                        {
                            response.setMessage("Please punch in before going/coming to/from break...");
                            response.setAttribute(null);
                        }
                    }
                }
                break;
            }
            else
            {
                response.setMessage("Employee not found.");
                response.setAttribute(null);
            }
        }

        if(response.getAttribute() != null)
        {
           response.setAttribute(gson.toJson(DataStream.punchIn(timeStamp)));
           return gson.toJson(response);
        }
        else
        {
            return  gson.toJson(response);
        }
    }

    /**
     * Request for getting all the registrations from the database
     * @param authString for checking the the authorized clients
     * @return returns all the registrations in Json format
     */
    @GET
    @Path("/allpunches")
    @Produces(MediaType.APPLICATION_JSON)
    public String getLastRegistrations(@HeaderParam("authorization") String authString)
    {
        ArrayList<TimeStamp> registrationList = DataStream.getAllRegistrations();
        ArrayList<Employee> employeeList = DataStream.getAllEmployees();

        // Authorizes the client and performs request based on the authorization
        return Checks.isUserAuthenticated(authString, employeeList) ? gson.toJson(registrationList) : "Error Message";
    }

    /**
     * Request for getting all the registered employees from the database
     * @param authString
     * @return returns all the registered employees in Json format
     */
    @GET
    @Path("/allemployees")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllEmployees(@HeaderParam("authorization") String authString)
    {
        ArrayList<Employee> employeeList = DataStream.getAllEmployees();

        // Authorizes the client and performs request based on the authorization
        return Checks.isUserAuthenticated(authString, employeeList) ? gson.toJson(employeeList) : "Error Message";
    }

    /**
     * Request for logging in the client based on the provided credentials
     * @param credentials
     * @return A confirmation for whether the client was logged in or not
     */
    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public String loginUser(String credentials)
    {
        //Creating the "credentials employee"
        Employee employee = gson.fromJson(credentials, Employee.class);

        ArrayList<Employee> empployeeList = DataStream.getAllEmployees();

        //Returns a false or true (in a LogedIn Object for better serialization) for whether the client was logged or not
        return gson.toJson(new Logedin(Checks.checkCredentials(empployeeList, employee)));
    }

    /**
     * Request for registering a new employee into the system
     * @param authString
     * @param employeeFromClient
     * @return A confirmation for whether the employee was registered or not
     */
    @POST
    @Path("/register")
    @Produces(MediaType.APPLICATION_JSON)
    public String registerEmployee(@HeaderParam("authorization") String authString, String employeeFromClient)
    {
        Employee employee = gson.fromJson(employeeFromClient, Employee.class);

        //Hashing the password
        employee.setPassword(HashFunctions.hashPassword(employee.getPassword()));

        ArrayList<Employee> empployeeList = DataStream.getAllEmployees();

        //Verifies the credentials of the client posting the request and checks of the employee to be registered already exists in the system
        if(Checks.isUserAuthenticated(authString, empployeeList) && Checks.checkEmployees(empployeeList, employee))
        {
            //Registers a punch in and out
            DataStream.punchIn(new TimeStamp(DateTime.now(), employee.getEmployeeId(), false, true, false));
            DataStream.punchIn(new TimeStamp(DateTime.now(), employee.getEmployeeId(), false, false, false));

            //Registers to the database
            return gson.toJson(DataStream.registerEmployee(employee));
        }

        return gson.toJson(new Logedin(false));
    }

    /**
     * Request for modifying information about an existing employee
     * @param authString
     * @param employeeFromClient
     * @return A confirmation for whether the employee was modified or not
     */
    @POST
    @Path("/edit")
    @Produces(MediaType.APPLICATION_JSON)
    public String editEmployee(@HeaderParam("authorization") String authString, String employeeFromClient)
    {
        Employee employee = gson.fromJson(employeeFromClient, Employee.class);
        employee.setPassword(HashFunctions.hashPassword(employee.getPassword()));

        ArrayList<Employee> empployeeList = DataStream.getAllEmployees();

        if(Checks.isUserAuthenticated(authString, empployeeList))
        {
            for(int i = 0; i < empployeeList.size(); i++)
            {
                if(empployeeList.get(i).getEmployeeId() == employee.getEmployeeId())
                {
                    DataStream.deleteEmployee(empployeeList.get(i));
                    break;
                }
            }

            DataStream.punchIn(new TimeStamp(DateTime.now(), employee.getEmployeeId(), false, true, false));
            DataStream.punchIn(new TimeStamp(DateTime.now(), employee.getEmployeeId(), false, false, false));
            return gson.toJson(DataStream.registerEmployee(employee));
        }

        return gson.toJson(new Logedin(false));
    }

    /**
     * Initialization of the system. First time start
     */
    private void initialRegistration()
    {
        Employee employee = new Employee("Gais", "El-AAsi", "el-aasi@happypig.com", 1111, "changeit");
        employee.setPassword(HashFunctions.hashPassword(employee.getPassword()));

        DataStream.registerEmployee(employee);
    }
}

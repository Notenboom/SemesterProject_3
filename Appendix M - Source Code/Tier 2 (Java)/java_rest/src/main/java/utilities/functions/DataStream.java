package utilities.functions;

import com.fatboyindustrial.gsonjodatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import data.Employee;
import data.TimeStamp;

import utilities.objects.Database_Request;
import utilities.objects.Logedin;
import utilities.objects.Requests;

import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Connection to the Data (Third) Tier
 * @author Group 6
 * @version 5.1.6.
 */
public class DataStream
{
    //Json serializer/deserializer
    private static Gson gson = Converters.registerDateTime(new GsonBuilder()).create();

    /**
     * Subroutine for connecting via sockets to the Third Tier
     * @param str
     * @return
     */
    private static String streamData(String str)
    {
        Socket socket = null;
        String clientIn = null;

        try
        {
            //!!!NEEDS FUNCTIONALITY FOR DYNAMICAL CHANGING THE IP AND PORT Marcel @version 4.5.1.
            socket = new Socket("127.0.0.1", 5000);

            Charset charset = Charset.forName("ASCII");

            final OutputStream outToServer = socket.getOutputStream();
            final DataOutputStream out = new DataOutputStream(outToServer);
            out.write(str.getBytes(charset));

            final InputStream inFromServer = socket.getInputStream();
            final BufferedReader input = new BufferedReader(new InputStreamReader(inFromServer, charset));

            clientIn = input.readLine();

            socket.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        finally
        {
            try
            {
                socket.close();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }

        return clientIn;
    }

    /**
     * Method for getting all the employees in the system
     * @return List of employees
     */
    public static ArrayList<Employee> getAllEmployees()
    {
        String employeeListJson = DataStream.streamData(gson.toJson(new Database_Request(Requests.GETALLEMPLOYEES)));

        //Deserialization of a Json to type ArrayList
        Type listType = new TypeToken<ArrayList<Employee>>(){}.getType();
        return new Gson().fromJson(employeeListJson, listType);
    }

    /**
     * Method for getting all the time stamps in the system
     * @return List of time stamps
     */
    public static ArrayList<TimeStamp> getAllRegistrations()
    {
        String registrationListJson = DataStream.streamData(gson.toJson(new Database_Request(Requests.GETALLREGISTRATIONS)));

        Type listType = new TypeToken<ArrayList<TimeStamp>>(){}.getType();
        return gson.fromJson(registrationListJson, listType);
    }

    /**
     * Method for sending time stamp to be registered in the system
     * @return last time stamp
     */
    public static TimeStamp punchIn(TimeStamp timeStamp)
    {
        String timeStampJson = DataStream.streamData(gson.toJson(new Database_Request(Requests.PUNCHIN, gson.toJson(timeStamp))));
        return gson.fromJson(timeStampJson, TimeStamp.class);
    }

    /**
     * Method for sending an employee to be registered in the system
     * @return True or false based on the outcome
     */
    public static Logedin registerEmployee(Employee employee)
    {
        DataStream.streamData(gson.toJson(new Database_Request(Requests.REGISTEREMPLOYEE, gson.toJson(employee))));
        return new Logedin(true);
    }

    /**
     * Method for sending the information about an employee to be deleted
     */
    public static void deleteEmployee(Employee employee)
    {
        DataStream.streamData(gson.toJson(new Database_Request(Requests.DELETEEMPLOYEE, gson.toJson(employee))));
    }
}

package utilities.objects;

/**
 * Class for holding objects-protocol sent to the third tier
 * @author Group 6
 * @version 0.3.7
 */
public class Database_Request
{
    private Requests request;
    private String attribute = "Not set";

    //TOO SIMILAR TO API_RESPONSE CLASS TO BE REVISED Gais @version 0.3.7

    /**
     * No argument constructor used for Json Serialization/Deserialization
     */
    public Database_Request()
    {
        super();
    }

    /**
     * Base class constructor
     * @param request
     */
    public Database_Request(Requests request)
    {
        this.request = request;
    }

    /**
     * Extended class constructor
     * @param request
     * @param attribute
     */
    public Database_Request(Requests request, String attribute)
    {
        this.request = request;
        this.attribute = attribute;
    }

    public Requests getRequest()
    {
        return request;
    }

    public void setRequest(Requests request)
    {
        this.request = request;
    }

    public String getAttribute()
    {
        return attribute;
    }

    public void setAttribute(String attribute)
    {
        this.attribute = attribute;
    }
}

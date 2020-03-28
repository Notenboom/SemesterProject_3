package utilities.objects;

/**
 * Class for holding the helper object-protocol for sending responses to HTTP Requests
 * @author Group6
 * @version 0.7.1.
 */
public class API_Response
{
    private String message;
    private String attribute;

    /**
     * No argument constructor used in Json Deserialization/Serialization
     */
    public API_Response()
    {
        super();
    }

    /**
     * Base Constructor
     * @param message
     */
    public API_Response(String message)
    {
        this.message = message;
        this.attribute = null;
    }

    /**
     * Extended constructor
     * @param message
     * @param attribute
     */
    public API_Response(String message, String attribute)
    {
        this.message = message;
        this.attribute = attribute;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
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

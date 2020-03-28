package utilities.objects;

/**
 * Class for holding the boolean objects, needed for proper functioning of Json Serialization/Deserialization
 * @author Group6
 * @version 0.1.0
 */
public class Logedin
{
    private boolean logedin;

    /**
     * No argument constructor used for Json Serialization/Deserialization
     */
    public Logedin()
    {
        super();
    }

    /**
     * Base constructor
     * @param logedin
     */
    public Logedin(boolean logedin)
    {
        this.logedin = logedin;
    }

    public boolean isLogedin()
    {
        return logedin;
    }

    public void setLogedin(boolean logedin)
    {
        this.logedin = logedin;
    }
}

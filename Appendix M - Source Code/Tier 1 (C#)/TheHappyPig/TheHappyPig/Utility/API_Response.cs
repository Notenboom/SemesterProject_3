namespace TheHappyPig.Model.Utility
{
    public class API_Response
    {
        public string message { get; set; }
        public string attribute { get; set; }

        public API_Response() : base()
        {

        }

        public API_Response(string message, string attribute)
        {
            this.message = message;
            this.attribute = attribute;
        }
    }

}

using System;
using System.Collections.Generic;
using System.Text;

namespace Database_Application.Connection
{

    //Protocol for incoming requests
    class Request
    {
        public string request { get; set; }
        public string attribute { get; set; }

        public Request() : base()
        {

        }

        public Request(string request)
        {
            this.request = request;
            this.attribute = "Not set";
        }

        public Request(string request, string attribute)
        {
            this.request = request;
            this.attribute = attribute;
        }
    }
}

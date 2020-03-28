using System;

namespace TheHappyPig.Model.Utility
{
    public class Logedin
    {
        public bool logedin { get; set; }

        public Logedin() : base()
        {

        }

        public Logedin(bool logedin)
        {
            this.logedin = logedin;
        }
    }
}

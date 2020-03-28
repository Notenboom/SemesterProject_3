using Database_Application.Connection;
using Database_Application.Data.Entities;
using System;

namespace Database_Application
{
    class Program
    {
        static void Main()
        {
            Console.WriteLine("Application Started");

            var database = TimeStampContext.Instance;
            database.Initialize(); 

            Console.WriteLine("Database up and runnning");

            var connectionHandler = new IncomingConnectionHandler();

            while(true)
            {
                connectionHandler.OpeningConnection();
            }
        
        }
    }
}

using Database_Application.Data.Entities;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Net;
using System.Net.Sockets;
using System.Text;

namespace Database_Application.Connection
{
    //Handles all the connection comming via the tcp 
    class IncomingConnectionHandler
    {
        private TcpListener listener;
        private Database_Requests.Database_Requests db = Database_Requests.Database_Requests.Instance;
        
        public IncomingConnectionHandler(String ip)
        {
            Console.WriteLine("Preparing for incomming connections");
            listener = new TcpListener(IPAddress.Parse(ip), 5000);
            listener.Start();
            Console.WriteLine("Server Started");
        }

        public IncomingConnectionHandler()
        {
            Console.WriteLine("Preparing for incomming connections");
            listener = new TcpListener(IPAddress.Parse("127.0.0.1"), 5000);
            listener.Start();
            Console.WriteLine("Server Started");
        }

        public async void OpeningConnection()
        {
            Console.WriteLine("Waiting for clients");
            TcpClient client = listener.AcceptTcpClient();

            Console.WriteLine("Client Accepted");
            NetworkStream stream = client.GetStream();

            //Receiving the request
            byte[] bytesFromBusinessLogic = new byte[1024];
            int bytesRead = stream.Read(bytesFromBusinessLogic, 0, bytesFromBusinessLogic.Length);
            string request = Encoding.ASCII.GetString(bytesFromBusinessLogic, 0, bytesRead);

            //Checking the requests
            var requestFromClient = JsonConvert.DeserializeObject<Request>(request);
            Console.WriteLine("Reading the request");

            //Deciding upon the request (Maybe should be replaces with a switch? Marcel) 
            if (requestFromClient.request.Equals("PUNCHIN"))
            {
                Console.WriteLine("Request type: PUNCHIN. Performing request. Please wait...");

                var timeStampObj = JsonConvert.DeserializeObject<TimeStamp>(requestFromClient.attribute);

                db.insertTimeStamp(timeStampObj);

                var timeStampObjFromDb = db.getTimeStampById();

                String timeStampObjFromDbJson = System.Text.Json.JsonSerializer.Serialize<TimeStamp>(timeStampObjFromDb);


                Console.WriteLine("To the client: " + timeStampObjFromDbJson);

                Console.WriteLine("Sending Object to client");

                byte[] byteToBusinessLogic = Encoding.ASCII.GetBytes(timeStampObjFromDbJson);
                stream.Write(byteToBusinessLogic, 0, byteToBusinessLogic.Length);

                Console.WriteLine("Object sent");

                client.Close();

                Console.WriteLine("Client Closed");
            }
            else if(requestFromClient.request.Equals("GETALLEMPLOYEES"))
            {
                Console.WriteLine("Request type: GETALLEMPLOYEES. Performing request. Please wait...");

                var employeesList = await db.getAllEmployees();
                var employeesListJson = System.Text.Json.JsonSerializer.Serialize<List<Employee>>(employeesList);

                Console.WriteLine(employeesListJson);
                Console.WriteLine("Sending Object to client");

                byte[] byteToBusinessLogic = Encoding.ASCII.GetBytes(employeesListJson);
                stream.Write(byteToBusinessLogic, 0, byteToBusinessLogic.Length);

                Console.WriteLine("Object sent");

                client.Close();

                Console.WriteLine("Client Closed");
            }
            else if (requestFromClient.request.Equals("GETALLREGISTRATIONS"))
            {
                Console.WriteLine("Request type: GETALLREGISTRATIONS. Performing request. Please wait...");

                var timeStampList = await db.getAllRegistrations();
                var timeStampListJson = System.Text.Json.JsonSerializer.Serialize<List<TimeStamp>>(timeStampList);

                Console.WriteLine("Sending Object to client");

                byte[] byteToBusinessLogic = Encoding.ASCII.GetBytes(timeStampListJson);
                stream.Write(byteToBusinessLogic, 0, byteToBusinessLogic.Length);

                Console.WriteLine("Object sent");

                client.Close();

                Console.WriteLine("Client Closed");
            }
            else if(requestFromClient.request.Equals("REGISTEREMPLOYEE"))
            {
                Console.WriteLine("Request type: REGISTEREMPLOYEE. Performing request. Please wait...");

                var employeeObj = JsonConvert.DeserializeObject<Employee>(requestFromClient.attribute);
                db.insertEmployee(employeeObj);

                Console.WriteLine("Request performed. Employee registered.");

                client.Close();

                Console.WriteLine("Client Closed");

            }
            else if(requestFromClient.request.Equals("DELETEEMPLOYEE"))
            {
                Console.WriteLine("Request type: DELETEEMPLOYEE. Performing requests. Please wait...");

                var employeeObj = JsonConvert.DeserializeObject<Employee>(requestFromClient.attribute);
                db.deleteEmployee(employeeObj);

                Console.WriteLine("Request performed. Employee deleted.");

                client.Close();

                Console.WriteLine("Client Closed");

            }
            else
                Console.WriteLine("Request type: UNKNOWN. Request cannot be performed. Closing the client...");
                client.Close();

                Console.WriteLine("Client Closed");
            }

        }
    }


            
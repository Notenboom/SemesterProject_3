using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;

using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;

using Newtonsoft.Json;

using TheHappyPig.Model;

namespace TheHappyPig.Pages.Manager
{
    public class ViewEmployeeModel : PageModel
    {
        [BindProperty]
        public List<Employee> Employees { get; set; }
        public void OnGet()
        {
            OnPostRefreshEmployees();
        }

        //Gets the current list of employees
        public void OnPostRefreshEmployees()
        {
            Employees = GetEmployees().GetAwaiter().GetResult();
            Employees.Reverse();
        }

        //Post request for editing an existing employee
        public async Task<PageResult> OnPostEditEmployee()
        {
            using var client = new HttpClient();

            //Getting the inputs made in the form on post requests
            var FirstName = Request.Form["FirstName"];
            var LastName = Request.Form["LastName"];
            var Email = Request.Form["Email"];
            var EmployeeId = Request.Form["EmployeeId"];
            var Password = Request.Form["Password"];

            //Preparing the http request
            StringContent employeeHttp = new StringContent(System.Text.Json.JsonSerializer.Serialize(new Employee(FirstName, LastName, Email, int.Parse(EmployeeId), Password)));

            //Authorization for the API
            var byteArray = Encoding.ASCII.GetBytes(ViewLoginModel.Login + ":" + ViewLoginModel.Password);
            client.DefaultRequestHeaders.Authorization = new System.Net.Http.Headers.AuthenticationHeaderValue("Basic", Convert.ToBase64String(byteArray));

            //Post Request
            HttpResponseMessage response = await client.PostAsync("http://127.0.0.1:1127/api/thehappypig/edit", employeeHttp);

            //Refreshes the employees list to include the last added employee
            OnPostRefreshEmployees();

            return Page();
        }

        //Get request for the list of employees
        private async Task<List<Employee>> GetEmployees()
        {
            using var client = new HttpClient();

            //Authorization for the API
            var byteArray = Encoding.ASCII.GetBytes(ViewLoginModel.Login + ":" + ViewLoginModel.Password);
            client.DefaultRequestHeaders.Authorization = new System.Net.Http.Headers.AuthenticationHeaderValue("Basic", Convert.ToBase64String(byteArray));

            //Post Request
            HttpResponseMessage response = await client.GetAsync("https://www.api.el-aasi.com/api/thehappypig/allemployees");
            var responseContent = await response.Content.ReadAsStringAsync();

            return new List<Employee>(JsonConvert.DeserializeObject<List<Employee>>(responseContent));
        }


    }
}
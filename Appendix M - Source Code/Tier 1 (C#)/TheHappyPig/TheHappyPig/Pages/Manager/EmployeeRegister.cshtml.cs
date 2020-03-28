using System;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;

using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;

using TheHappyPig.Model;

namespace TheHappyPig.Pages.Manager
{
    public class EmployeeRegisterModel : PageModel
    {

        public async Task<IActionResult> OnPost()
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
            HttpResponseMessage response = await client.PostAsync("http://127.0.0.1:1127/api/thehappypig/register", employeeHttp);

            return RedirectToPagePermanent("/Manager/ViewEmployee");
        }
        public void OnGet()
        {

        }
    }
}
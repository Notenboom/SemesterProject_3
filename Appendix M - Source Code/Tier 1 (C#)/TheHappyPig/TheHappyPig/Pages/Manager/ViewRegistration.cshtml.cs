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
    public class ViewRegistrationModel : PageModel
    {
        [BindProperty]
        public List<TimeStamp> Registrations { get; set; }

        public void OnGet()
        {
            OnPostRefreshRegistrations();
        }

        private async Task<List<TimeStamp>> GetRegistration()
        {
            using var client = new HttpClient();

            //Authorization for the API
            var byteArray = Encoding.ASCII.GetBytes(ViewLoginModel.Login + ":" + ViewLoginModel.Password);
            client.DefaultRequestHeaders.Authorization = new System.Net.Http.Headers.AuthenticationHeaderValue("Basic", Convert.ToBase64String(byteArray));

            //Get request for the list of registrations
            HttpResponseMessage response = await client.GetAsync("http://127.0.0.1:1127/api/thehappypig/allpunches");
            var responseContent = await response.Content.ReadAsStringAsync();

            return new List<TimeStamp>(JsonConvert.DeserializeObject<List<TimeStamp>>(responseContent));
        }

        public void OnPostRefreshRegistrations()
        {
            Registrations = GetRegistration().GetAwaiter().GetResult();
        }
    }
}
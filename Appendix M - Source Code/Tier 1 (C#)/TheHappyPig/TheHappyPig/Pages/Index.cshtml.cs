using System;
using System.Net.Http;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.Extensions.Logging;
using Newtonsoft.Json;
using TheHappyPig.Model;
using TheHappyPig.Model.Utility;

namespace TheHappyPig.Pages
{
    public class IndexModel : PageModel
    {
        [BindProperty]
        public string Punch { get; set; }

        [BindProperty]
        public API_Response responseAPI { get; set; }

        [BindProperty]
        public TimeStamp timeStamp { get; set; }

        private readonly ILogger<IndexModel> _logger;

        public IndexModel(ILogger<IndexModel> logger)
        {
            _logger = logger;
            timeStamp = null;
        }

        public void OnGet()
        {

        }

        public async Task<IActionResult> OnPost()
        {
            var flag = false;
            var EmployeeId = Request.Form["EmployeeId"];


            using var client = new HttpClient();

            if (Punch.Equals("break"))
            {
                flag = true;
            }

            StringContent timeStampHttp = new StringContent(System.Text.Json.JsonSerializer.Serialize<TimeStamp>(new TimeStamp(DateTime.Now, Int32.Parse(EmployeeId), flag)));
            HttpResponseMessage response = await client.PostAsync("http://127.0.0.1:1127/api/thehappypig/punchin", timeStampHttp);
            var responseContent = await response.Content.ReadAsStringAsync();


            responseAPI = JsonConvert.DeserializeObject<API_Response>(responseContent);

            if(String.IsNullOrEmpty(responseAPI.attribute))
            {
                timeStamp = null; 
            }
            else
            {
                timeStamp = JsonConvert.DeserializeObject<TimeStamp>(responseAPI.attribute);
            }

            return Page();
        }
    }
}

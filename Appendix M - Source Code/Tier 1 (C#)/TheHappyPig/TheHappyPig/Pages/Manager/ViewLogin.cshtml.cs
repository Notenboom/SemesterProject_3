using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.Extensions.Logging;

using Newtonsoft.Json;

using System.Net.Http;
using System.Threading.Tasks;

using TheHappyPig.Model;
using TheHappyPig.Model.Utility;

namespace TheHappyPig.Pages.Manager
{
    public class ViewLoginModel : PageModel
    {
        [BindProperty]
        public static string Login { get; set; }

        [BindProperty]
        public static string Password { get; set; }

        [BindProperty]
        public Logedin logedin { get; set; }

        [BindProperty]
        public bool Display { get; set; }

        private readonly ILogger<ViewLoginModel> _logger;

        public ViewLoginModel(ILogger<ViewLoginModel> logger)
        {
            _logger = logger;
            Display = false;
        }

        public async Task<IActionResult> OnPost()
        {
            var client = new HttpClient();

            //Login Information retrieval
            Login = Request.Form["EmployeeId"];
            Password = Request.Form["Password"];

            //Login post request
            HttpResponseMessage response = await client.PostAsync("http://127.0.0.1:1127/api/thehappypig/login", new StringContent(System.Text.Json.JsonSerializer.Serialize(new Employee(int.Parse(Login), Password))));
            var responseContent = await response.Content.ReadAsStringAsync();

            //Checking the success state
            logedin = JsonConvert.DeserializeObject<Logedin>(responseContent);

            if (logedin.logedin)
            {
                return RedirectToPagePermanent("/Manager/ViewRegistration");
            }
            else
            {
                Display = true;
                return Page();
            }

        }
    }
}

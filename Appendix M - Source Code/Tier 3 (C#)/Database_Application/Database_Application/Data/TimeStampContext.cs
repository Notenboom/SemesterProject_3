using Microsoft.EntityFrameworkCore;

namespace Database_Application.Data.Entities
{

    //Singleton Implementation
    sealed class TimeStampContext : DbContext
    {
        private static readonly object padlock = new object();
        private static volatile TimeStampContext instance = null; 

        private TimeStampContext()
        {

        }
        public static TimeStampContext Instance
        {
            get
            {
                if(instance == null)
                {
                    lock(padlock)
                    {
                        if(instance == null)
                        {
                            instance = new TimeStampContext(); 
                        }
                    }
                }

                return instance; 
            }
        }


        //Databaseconnection
        public DbSet<TimeStamp> TimeStamps { get; set; }
        public DbSet<Employee> Employees { get; set; }

        //Connection to the database
        protected override void OnConfiguring(DbContextOptionsBuilder options)
            => options.UseNpgsql("User ID=gais;Password=changeitafterproject;Host=35.246.226.193;Port=5432;Database=thehappypigdb;Pooling=true;");

        //Setting up keys
        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<TimeStamp>()
                .HasKey(o => new { o.EmployeeId, o.TimePunch});

            modelBuilder.Entity<Employee>()
               .HasKey(o => new { o.EmployeeId });
        }

        public void Initialize()
        {
            DbInitializer.Initiliaze(this); 
        }
    }
}

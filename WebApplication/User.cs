using NLog.Fluent;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace WebApplication
{
    public class User
    {
        public string Email { get; set; }
        public string Password { get; set; }

        public User(string Email, string Password)
        {
            this.Email = Email;
            this.Password = Password;
        }
    }
}

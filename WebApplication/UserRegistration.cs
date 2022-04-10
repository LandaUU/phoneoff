using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Threading.Tasks;

namespace WebApplication
{
    public class UserRegistration
    {
        public string Email { get; set; }
        public string Login { get; set; }
        public string Password { get; set; }
        public string FIO { get; set; }
        public string Address { get; set; }
        public string PhoneNumber { get; set; }


        public UserRegistration()
        {

        }

        public UserRegistration(string email, string login, string password, string fIO, string address, string phoneNumber)
        {
            Email = email;
            Login = login;
            Password = password;
            FIO = fIO;
            Address = address;
            PhoneNumber = phoneNumber;
        }

        public UserRegistration(DataRow row)
        {
            Email = row.Field<string>("Email");
            Login = row.Field<string>("Логин");
            Password = row.Field<string>("Пароль");
            FIO = row.Field<string>("ФИО");
            Address = row.Field<string>("Адрес");
            PhoneNumber = row.Field<string>("Номер телефона");
        }
    }


    public class UserRegistration1
    {
        public string Email { get; set; }
        public string Login { get; set; }
        public byte[] Password { get; set; }
        public string FIO { get; set; }
        public string Address { get; set; }
        public string PhoneNumber { get; set; }


        public UserRegistration1()
        {

        }

        public UserRegistration1(string email, string login, byte[] password, string fIO, string address, string phoneNumber)
        {
            Email = email;
            Login = login;
            Password = password;
            FIO = fIO;
            Address = address;
            PhoneNumber = phoneNumber;
        }

        public UserRegistration1(DataRow row)
        {
            Email = row.Field<string>("Email");
            Login = row.Field<string>("Логин");
            Password = row.Field<byte[]>("Пароль");
            FIO = row.Field<string>("ФИО");
            Address = row.Field<string>("Адрес");
            PhoneNumber = row.Field<string>("Номер телефона");
        }
    }
}

using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Threading.Tasks;

namespace WebApplication
{
    public class UserTemporary
    {
        public string Email { get; set; }
        public string Login { get; set; }
        public byte[] Password { get; set; }
        public string FIO { get; set; }
        public string Address { get; set; }
        public string PhoneNumber { get; set; }
        public string Code { get; set; }

        public UserTemporary(string email, string login, byte[] password, string fIO, string address, string phoneNumber, string code)
        {
            Email = email;
            Login = login;
            Password = password;
            FIO = fIO;
            Address = address;
            PhoneNumber = phoneNumber;
            Code = code;
        }

        public UserTemporary(DataRow row)
        {
            Email = row.Field<string>("Email");
            Login = row.Field<string>("Логин");
            Password = row.Field<byte[]>("Пароль");
            FIO = row.Field<string>("ФИО");
            Address = row.Field<string>("Адрес");
            PhoneNumber = row.Field<string>("Номер телефона");
            Code = row.Field<string>("Код подтверждения");
        }
    }
}

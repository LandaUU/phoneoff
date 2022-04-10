using NLog.Fluent;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Threading.Tasks;

namespace WebApplication
{
    public class Order
    {
        
        public int[] prodId;
        public string email;
        public string date;
        public decimal price;


        public Order(int[] array, string email, string date, decimal price)
        {
            prodId = array;
            this.email = email;
            this.date = date;
            this.price = price;
        }
        
    }


    public class Order1
    {
        public int idOrder { get; set; }
        public string Status { get; set; }
        public decimal Summa { get; set; }

        public Order1(int idOrder, string status, decimal summa)
        {
            this.idOrder = idOrder;
            Status = status;
            Summa = summa;
        }

        public Order1(DataRow row)
        {
            idOrder = row.Field<int>("id");
            Status = row.Field<string>("Статус");
            Summa = row.Field<decimal>("Сумма");
        }
    }

    public class Order2
    {
        public int id { get; set; }
        public string Email { get; set; }
        public DateTime DateOrder { get; set; }
        public decimal Summa { get; set; }
        public string Status { get; set; }
        public string Code { get; set; }
        
        public Order2(int id, string email, DateTime dateOrder, decimal summa, string status, string code)
        {
            this.id = id;
            Email = email;
            DateOrder = dateOrder;
            Summa = summa;
            Status = status;
            Code = code;
        }

        public Order2(DataRow row)
        {
            id = row.Field<int>("id");
            Email = row.Field<string>("Email");
            DateOrder = row.Field<DateTime>("Дата заказа");
            Summa = row.Field<decimal>("Сумма");
            Status = row.Field<string>("Статус");
            Code = row.Field<string>("Код_заказа");
        }
    }

    public class Order3
    {
        public int id { get; set; }
        public string Status { get; set; }

        public Order3(int id, string status)
        {
            this.id = id;
            Status = status;
        }
    }
}

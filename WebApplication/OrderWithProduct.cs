using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace WebApplication
{
    public class OrderWithProduct
    {
        public int id { get; set; }
        public string Email { get; set; }
        public DateTime DateOrder { get; set; }
        public decimal Summa { get; set; }
        public string Status { get; set; }
        public string CodeOrder { get; set; }
        public List<Product> Products { get; set; }

        public OrderWithProduct(int id, string email, DateTime dateOrder, decimal summa, string status, string codeOrder, List<Product> products)
        {
            this.id = id;
            Email = email;
            DateOrder = dateOrder;
            Summa = summa;
            Status = status;
            CodeOrder = codeOrder;
            Products = products;
        }

        public OrderWithProduct(int id, string email, DateTime dateOrder, decimal summa, string status, string codeOrder)
        {
            this.id = id;
            Email = email;
            DateOrder = dateOrder;
            Summa = summa;
            Status = status;
            CodeOrder = codeOrder;
        }
    }
}

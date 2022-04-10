using System.Data;

namespace WebApplication
{
    public class ProductProp
    {
        public int Id { get; set; }
        public string Value { get; set; }


        public ProductProp(int id, string value)
        {
            Id = id;
            Value = value;
        }

        public ProductProp(DataRow row)
        {
            Id = row.Field<int>("id");
            Value = row.Field<string>("Значение");
        }
        
        public ProductProp(DataRow row, int i)
        {
            if (i == 1)
            {
                Id = row.Field<int>("id");
                Value = row.Field<string>("Цвет");
            }
            else
            {
                Id = row.Field<int>("id");
                Value = row.Field<string>("Производитель");
            }
        }
        
    }

    public class ProductPropWithoutId
    {
        public string Value { get; set; }

        public ProductPropWithoutId(string value)
        {
            Value = value;
        }
    }

    public class ProductPropId
    {
        public int Id { get; set; }

        public ProductPropId(int id)
        {
            Id = id;
        }
    }
}
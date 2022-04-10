using System;
using System.Data;

namespace WebApplication
{
    public class Product
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public double Cost { get; set; }
        public string Description { get; set; }
        public string Color { get; set; }
        public string Manufacturer { get; set; }
        public int ROM { get; set; }
        public int RAM { get; set; }
        public double Diagonal { get; set; }
        public byte[] Image { get; set; }
        public int Count { get; set; }

        public Product(int id, string name, double cost, string description, string color, string manufacturer, int rOM, int rAM, double diagonal, byte[] image, int count)
        {
            Id = id;
            Name = name;
            Cost = cost;
            Description = description;
            Color = color;
            Manufacturer = manufacturer;
            ROM = rOM;
            RAM = rAM;
            Diagonal = diagonal;
            Image = image;
            Count = count;
        }

        public Product(DataRow row)
        {
            Id = int.Parse(row[0].ToString());
            Name = row[1].ToString();
            Description = row[2].ToString();
            Cost = int.Parse(row[3].ToString());
            Image = (byte[]) row[4];
            Manufacturer = (string) row[5];
            Color = (string) row[6];
            ROM = int.Parse(row[7].ToString());
            RAM = int.Parse(row[8].ToString());
            Diagonal = double.Parse(row[9].ToString());
            Count = int.Parse(row[10].ToString());
        }
    }


    public class ProductWithoutId
    {
        public string Name { get; set; }
        public double Cost { get; set; }
        public string Description { get; set; }
        public int Color { get; set; }
        public int Manufacturer { get; set; }
        public int ROM { get; set; }
        public int RAM { get; set; }
        public int Diagonal { get; set; }
        public byte[] Image { get; set; }
        public int Count { get; set; }

        public ProductWithoutId(string name, double cost, string description, int color, int manufacturer, int rOM, int rAM, int diagonal, byte[] image, int count)
        {
            Name = name;
            Cost = cost;
            Description = description;
            Color = color;
            Manufacturer = manufacturer;
            ROM = rOM;
            RAM = rAM;
            Diagonal = diagonal;
            Image = image;
            Count = count;
        }
    }


    public class ProductUpdate
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public double Cost { get; set; }
        public string Description { get; set; }
        public int Color { get; set; }
        public int Manufacturer { get; set; }
        public int ROM { get; set; }
        public int RAM { get; set; }
        public int Diagonal { get; set; }
        public byte[] Image { get; set; }
        public int Count { get; set; }

        public ProductUpdate(int id, string name, double cost, string description, int color, int manufacturer, int rOM, int rAM, int diagonal, byte[] image, int count)
        {
            Id = id;
            Name = name;
            Cost = cost;
            Description = description;
            Color = color;
            Manufacturer = manufacturer;
            ROM = rOM;
            RAM = rAM;
            Diagonal = diagonal;
            Image = image;
            Count = count;
        }
    }

    public class ProductCount
    {
        public int Id { get; set; }
        public int Count { get; set; }

        public ProductCount(int id, int count)
        {
            Id = id;
            Count = count;
        }
    }
}
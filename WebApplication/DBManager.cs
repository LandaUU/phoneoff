using Konscious.Security.Cryptography;
using NLog;
using NLog.Fluent;
using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Text.Json;
using System.Text.Json.Serialization;

namespace WebApplication
{
    public class DBManager
    {
        private static readonly Logger logger = LogManager.GetCurrentClassLogger();
        private static string connectionstring =
    @"Server=PhoneOFF;Database=DB;User Id=Serv;Password=<=aq(7D@t<rR";

        public static string GetPhones()
        {
            DataTable table = GetData();
            logger.Info($"Количество телефонов: {table.Rows.Count}");
            List<Product> products = FromTableToProducts(table);
            string json = JsonSerializer.Serialize(products);
            return json;
        }
        
        private static DataTable GetData()
        {
            DataTable table = new DataTable();
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionstring))
                {
                    using (SqlCommand command = connection.CreateCommand())
                    {
                        command.CommandText =
                            @"SELECT T.id, T.Наименование, T.Описание, T.Цена, X.Изображение, Man.Производитель, C.Цвет, V.Значение as [Встроенная память], 
                    O.Значение as [Оперативная память], D.Значение as Диагональ, Количество FROM Товар T
                        INNER JOIN Характеристика X ON T.idХарактеристики=X.id
                    INNER JOIN Производитель Man ON Man.id=x.idПроизводителя
                    INNER JOIN Цвет C ON C.id=X.idЦвета
                    INNER JOIN [Встроенная память] V ON V.id=X.idВстроенной_памяти
                    INNER JOIN [Оперативная память] O on O.id=X.idОперативной_памяти
                    INNER JOIN Диагональ D ON D.id=X.idДиагонали";
                        connection.Open();
                        SqlDataReader reader = command.ExecuteReader();
                        table.Load(reader);
                    }
                }

                return table;
            }
            catch (Exception ex)
            {
                logger.Error(ex);
                return table;
            }
        }

        public static string GetPhone(int id)
        {
            DataTable table = new DataTable();
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionstring))
                {
                    using (SqlCommand command = connection.CreateCommand())
                    {
                        command.CommandText =
                            @"SELECT T.id, T.Наименование, T.Описание, T.Цена, X.Изображение, Man.Производитель, C.Цвет, V.Значение as [Встроенная память], 
                    O.Значение as [Оперативная память], D.Значение as Диагональ, Количество FROM Товар T
                        INNER JOIN Характеристика X ON T.idХарактеристики=X.id
                    INNER JOIN Производитель Man ON Man.id=x.idПроизводителя
                    INNER JOIN Цвет C ON C.id=X.idЦвета
                    INNER JOIN [Встроенная память] V ON V.id=X.idВстроенной_памяти
                    INNER JOIN [Оперативная память] O on O.id=X.idОперативной_памяти
                    INNER JOIN Диагональ D ON D.id=X.idДиагонали
                    WHERE T.id = @id";
                        connection.Open();
                        command.Parameters.AddWithValue("@id", id);
                        SqlDataReader reader = command.ExecuteReader();
                        table.Load(reader);
                       
                    }
                }
                Product product = new Product((DataRow)table.Rows[0]);
                return JsonSerializer.Serialize(product);

            }
            catch (Exception ex)
            {
                logger.Error(ex);
                return "";
            }
        }

        public static bool UpdateProduct(ProductUpdate product)
        {
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionstring))
                {
                    connection.Open();
                    using (SqlCommand command = connection.CreateCommand())
                    {
                        command.CommandText = $@"UPDATE Товар
                                            SET Наименование = @Name, Цена = @Cost, Описание = @Description, Количество = @Count
                                            WHERE id = @id";
                        command.Parameters.AddWithValue("@Name", product.Name);
                        command.Parameters.AddWithValue("@Cost", product.Cost);
                        command.Parameters.AddWithValue("@Description", product.Description);
                        command.Parameters.AddWithValue("@id", product.Id);
                        command.Parameters.AddWithValue("@Count", product.Count);
                        command.ExecuteNonQuery();
                    }
                    using (SqlCommand command = connection.CreateCommand())
                    {
                        command.CommandText = $@"UPDATE Характеристика
                                             SET idЦвета = @Color, idПроизводителя = @Manufacter, idОперативной_памяти = @ROM, idВстроенной_памяти = @RAM, idДиагонали = @Diagonal, Изображение = @Image
                                             WHERE id = (SELECT idХарактеристики FROM Товар WHERE id = @id)";
                        command.Parameters.AddWithValue("@Color", product.Color);
                        command.Parameters.AddWithValue("@Manufacter", product.Manufacturer);
                        command.Parameters.AddWithValue("@ROM", product.ROM);
                        command.Parameters.AddWithValue("@RAM", product.RAM);
                        command.Parameters.AddWithValue("@Diagonal", product.Diagonal);
                        command.Parameters.AddWithValue("@Image", product.Image);
                        command.Parameters.AddWithValue("@id", product.Id);
                        command.ExecuteNonQuery();
                    }
                }
                return true;
            }
            catch (Exception ex)
            {
                logger.Error(ex);
                return false;
            }
        }


        
        public static List<ProductProp> GetColor()
        {
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionstring))
                {
                    connection.Open();
                    using (SqlCommand command = connection.CreateCommand())
                    {
                        command.CommandText = @"SELECT * FROM Цвет";

                        DataTable table = new DataTable();
                        table.Load(command.ExecuteReader());
                        List<ProductProp> props = new List<ProductProp>();


                        foreach (DataRow row in table.Rows)
                        {
                            props.Add(new ProductProp(row, 1));
                        }

                        return props;
                    }

                }
            }
            catch (Exception ex)
            {
                logger.Error(ex);
                return null;
            }
        }
        
        
        public static List<ProductProp> GetManufacturer()
        {
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionstring))
                {
                    connection.Open();
                    using (SqlCommand command = connection.CreateCommand())
                    {
                        command.CommandText = @"SELECT * FROM Производитель";

                        DataTable table = new DataTable();
                        table.Load(command.ExecuteReader());
                        List<ProductProp> props = new List<ProductProp>();


                        foreach (DataRow row in table.Rows)
                        {
                            props.Add(new ProductProp(row, 2));
                        }

                        return props;
                    }

                }
            }
            catch (Exception ex)
            {
                logger.Error(ex);
                return null;
            }
        }
        
        
        public static List<ProductProp> GetRAM()
        {
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionstring))
                {
                    connection.Open();
                    using (SqlCommand command = connection.CreateCommand())
                    {
                        command.CommandText = @"SELECT * FROM [Оперативная память]";

                        DataTable table = new DataTable();
                        table.Load(command.ExecuteReader());
                        List<ProductProp> props = new List<ProductProp>();


                        foreach (DataRow row in table.Rows)
                        {
                            props.Add(new ProductProp(row));
                        }

                        return props;
                    }

                }
            }
            catch (Exception ex)
            {
                logger.Error(ex);
                return null;
            }
        }
        
        
        public static List<ProductProp> GetROM()
        {
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionstring))
                {
                    connection.Open();
                    using (SqlCommand command = connection.CreateCommand())
                    {
                        command.CommandText = @"SELECT * FROM [Встроенная память]";

                        DataTable table = new DataTable();
                        table.Load(command.ExecuteReader());
                        List<ProductProp> props = new List<ProductProp>();


                        foreach (DataRow row in table.Rows)
                        {
                            props.Add(new ProductProp(row));
                        }

                        return props;
                    }

                }
            }
            catch (Exception ex)
            {
                logger.Error(ex);
                return null;
            }
        }
        
        public static List<ProductProp> GetDiagonal()
        {
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionstring))
                {
                    connection.Open();
                    using (SqlCommand command = connection.CreateCommand())
                    {
                        command.CommandText = @"SELECT * FROM Диагональ";

                        DataTable table = new DataTable();
                        table.Load(command.ExecuteReader());
                        List<ProductProp> props = new List<ProductProp>();


                        foreach (DataRow row in table.Rows)
                        {
                            props.Add(new ProductProp(row));
                        }

                        return props;
                    }

                }
            }
            catch (Exception ex)
            {
                logger.Error(ex);
                return null;
            }
        }

        public static bool AddColor(string Value)
        {
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionstring))
                {
                    connection.Open();
                    using (SqlCommand command = connection.CreateCommand())
                    {
                        command.CommandText = @"INSERT INTO Цвет VALUES(@Color)";
                        command.Parameters.AddWithValue("@Color", Value);
                        command.ExecuteNonQuery();
                    }
                }
                return true;
            }
            catch(Exception ex)
            {
                logger.Error(ex);
                return false;
            }
        }

        public static bool AddManufacturer(string Value)
        {
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionstring))
                {
                    connection.Open();
                    using (SqlCommand command = connection.CreateCommand())
                    {
                        command.CommandText = @"INSERT INTO Производитель VALUES(@Man)";
                        command.Parameters.AddWithValue("@Man", Value);
                        command.ExecuteNonQuery();
                    }
                }
                return true;
            }
            catch (Exception ex)
            {
                logger.Error(ex);
                return false;
            }
        }

        public static bool AddRAM(int Value)
        {
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionstring))
                {
                    connection.Open();
                    using (SqlCommand command = connection.CreateCommand())
                    {
                        command.CommandText = @"INSERT INTO [Оперативная память] VALUES(@RAM)";
                        command.Parameters.AddWithValue("@RAM", Value);
                        command.ExecuteNonQuery();
                    }
                }
                return true;
            }
            catch (Exception ex)
            {
                logger.Error(ex);
                return false;
            }
        }

        public static bool AddROM(int Value)
        {
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionstring))
                {
                    connection.Open();
                    using (SqlCommand command = connection.CreateCommand())
                    {
                        command.CommandText = @"INSERT INTO [Встроенная память] VALUES(@ROM)";
                        command.Parameters.AddWithValue("@ROM", Value);
                        command.ExecuteNonQuery();
                    }
                }
                return true;
            }
            catch (Exception ex)
            {
                logger.Error(ex);
                return false;
            }
        }

        public static bool AddDiag(double Value)
        {
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionstring))
                {
                    connection.Open();
                    using (SqlCommand command = connection.CreateCommand())
                    {
                        command.CommandText = @"INSERT INTO Диагональ VALUES(@Diag)";
                        command.Parameters.AddWithValue("@Diag", Value);
                        command.ExecuteNonQuery();
                    }
                }
                return true;
            }
            catch (Exception ex)
            {
                logger.Error(ex);
                return false;
            }
        }


        public static bool DeleteColor(int id)
        {
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionstring))
                {
                    connection.Open();
                    using (SqlCommand command = connection.CreateCommand())
                    {
                        command.CommandText = "DELETE FROM Цвет WHERE id = @Id";
                        command.Parameters.AddWithValue("@Id", id);
                        command.ExecuteNonQuery();
                        return true;
                    }
                }
            }
            catch(Exception ex)
            {
                logger.Error(ex);
                return false;
            }

        }

        public static bool DeleteManufacturer(int id)
        {
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionstring))
                {
                    connection.Open();
                    using (SqlCommand command = connection.CreateCommand())
                    {
                        command.CommandText = "DELETE FROM Производитель WHERE id = @Id";
                        command.Parameters.AddWithValue("@Id", id);
                        command.ExecuteNonQuery();
                        return true;
                    }
                }
            }
            catch (Exception ex)
            {
                logger.Error(ex);
                return false;
            }
        }

        public static bool DeleteRAM(int id)
        {
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionstring))
                {
                    connection.Open();
                    using (SqlCommand command = connection.CreateCommand())
                    {
                        command.CommandText = "DELETE FROM [Оперативная память] WHERE id = @Id";
                        command.Parameters.AddWithValue("@Id", id);
                        command.ExecuteNonQuery();
                        return true;
                    }
                }
            }
            catch (Exception ex)
            {
                logger.Error(ex);
                return false;
            }
        }

        public static bool DeleteROM(int id)
        {
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionstring))
                {
                    connection.Open();
                    using (SqlCommand command = connection.CreateCommand())
                    {
                        command.CommandText = "DELETE FROM [Встроенная память] WHERE id = @Id";
                        command.Parameters.AddWithValue("@Id", id);
                        command.ExecuteNonQuery();
                        return true;
                    }
                }
            }
            catch (Exception ex)
            {
                logger.Error(ex);
                return false;
            }
        }

        public static bool DeleteDiag(int id)
        {
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionstring))
                {
                    connection.Open();
                    using (SqlCommand command = connection.CreateCommand())
                    {
                        command.CommandText = "DELETE FROM Диагональ WHERE id = @Id";
                        command.Parameters.AddWithValue("@Id", id);
                        command.ExecuteNonQuery();
                        return true;
                    }
                }
            }
            catch (Exception ex)
            {
                logger.Error(ex);
                return false;
            }
        }

        public static int AddOrder(string email, DateTime date, decimal Summa)
        {
            try
            {
                int modified;
                using (SqlConnection connection = new SqlConnection(connectionstring))
                {
                    connection.Open();
                    using (SqlCommand command = connection.CreateCommand())
                    {
                        command.CommandText = @"INSERT INTO Заказ output INSERTED.ID VALUES(@email, @datetime, @Summa, @Status, @Code) ";
                        command.Parameters.AddWithValue("@email", email);
                        command.Parameters.AddWithValue("@datetime", date);
                        command.Parameters.AddWithValue("@Summa", Summa);
                        command.Parameters.AddWithValue("@Status", "Принято");
                        command.Parameters.AddWithValue("@Code", "TEST");
                        logger.Debug($@"INSERT INTO Заказ output INSERTED.ID VALUES({email}, {date.ToShortTimeString()}, {Summa}, ""TEST"", ""Принято"", ""TEST""); ");
                        modified = (int)command.ExecuteScalar();
                    }
                }
                return modified;
            }
            catch (Exception ex)
            {
                logger.Error(ex);
                return -1;
            }
        }

        public static void GetOrderWithProducts(string Email)
        {
            using (SqlConnection connection = new SqlConnection(connectionstring))
            {
                connection.Open();
                using (SqlCommand command = connection.CreateCommand())
                {
                    command.CommandText = @"SELECT Z.id, Z.Email, Z.[Дата заказа], Z.Сумма, Z.Статус, Z.Код_заказа, T.id as idТовара, T.Наименование, T.Описание, T.Цена, Color.Цвет, 
                                            Man.Производитель, VP.Значение as [Встроенная память], OP.Значение as [Оперативная память], Di.Значение as [Диагональ] FROM Заказ Z 
                                            INNER JOIN Список_товаров S ON Z.id = S.idЗаказа
                                            INNER JOIN Товар T ON S.idТовара = T.id
                                            INNER JOIN Характеристика X ON X.id = T.idХарактеристики
                                            INNER JOIN Цвет Color ON Color.id = X.idЦвета
                                            INNER JOIN Производитель Man ON Man.id = X.idПроизводителя
                                            INNER JOIN [Встроенная память] VP ON VP.id = X.idВстроенной_памяти
                                            INNER JOIN [Оперативная память] OP ON OP.id = X.idОперативной_памяти
                                            INNER JOIN Диагональ Di ON Di.id = X.idДиагонали
                                            WHERE Email = @Email";

                    command.Parameters.AddWithValue("@Email", Email);

                    DataTable table = new DataTable();
                    table.Load(command.ExecuteReader());

                    var Orders_id = table.AsEnumerable().Select(x =>
                    new OrderWithProduct(x.Field<int>("id"), x.Field<string>("Email"), x.Field<DateTime>("[Дата заказа]"), x.Field<decimal>("Сумма"),
                    x.Field<string>("Статус"), x.Field<string>("Код_заказа"))).ToArray();


                }
            }
        }

        public static List<Order1> GetOrder(string Email)
        {
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionstring))
                {
                    connection.Open();
                    using (SqlCommand command = connection.CreateCommand())
                    {
                        command.CommandText = @"SELECT id, Статус, Сумма FROM Заказ WHERE Email = @Email";
                        command.Parameters.AddWithValue("@Email", Email);

                        DataTable table = new DataTable();
                        table.Load(command.ExecuteReader());

                        List<Order1> orders = new List<Order1>();

                        foreach (DataRow row in table.Rows)
                        {
                            orders.Add(new Order1(row));
                        }

                        return orders;
                    }
                }
            }
            catch(Exception ex)
            {
                logger.Info(ex);
                return null;
            }
        }

        public static List<Order2> GetAcceptedOrders()
        {
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionstring))
                {
                    connection.Open();
                    using (SqlCommand command = connection.CreateCommand())
                    {
                        command.CommandText = @"SELECT * FROM Заказ WHERE Статус = @Status ORDER BY [Дата заказа] DESC";
                        command.Parameters.AddWithValue("@Status", "Принято");
                        DataTable table = new DataTable();
                        table.Load(command.ExecuteReader());

                        List<Order2> order2s = new List<Order2>();
                        foreach (DataRow row in table.Rows)
                        {
                            order2s.Add(new Order2(row));
                        }

                        return order2s;
                    }
                }
            }
            catch (Exception ex)
            {
                logger.Error(ex);
                return null;
            }
        }

        public static List<Order2> GetProcessedOrders()
        {
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionstring))
                {
                    connection.Open();
                    using (SqlCommand command = connection.CreateCommand())
                    {
                        command.CommandText = @"SELECT * FROM Заказ WHERE Статус = @Status ORDER BY [Дата заказа] DESC";
                        command.Parameters.AddWithValue("@Status", "Обработано");
                        DataTable table = new DataTable();
                        table.Load(command.ExecuteReader());

                        List<Order2> order2s = new List<Order2>();
                        foreach (DataRow row in table.Rows)
                        {
                            order2s.Add(new Order2(row));
                        }

                        return order2s;
                    }
                }
            }
            catch (Exception ex)
            {
                logger.Error(ex);
                return null;
            }
        }

        public static bool ChangeStatus(int id, string Status)
        {
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionstring))
                {
                    connection.Open();
                    using (SqlCommand command = connection.CreateCommand())
                    {
                        command.CommandText = "UPDATE Заказ SET Статус=@Status WHERE id=@id";
                        command.Parameters.AddWithValue("@Status", Status);
                        command.Parameters.AddWithValue("@id", id);
                        command.ExecuteNonQuery();
                        return true;
                    }
                }
            }
            catch (Exception ex)
            {
                logger.Error(ex);
                return false;
            }
        }

        public static bool UpdateCount(int id, int Count)
        {
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionstring))
                {
                    connection.Open();
                    using (SqlCommand command = connection.CreateCommand())
                    {
                        command.CommandText = @"UPDATE Товар SET Количество = @Count WHERE id = @Id";
                        command.Parameters.AddWithValue("@Count", Count);
                        command.Parameters.AddWithValue("@Id", id);
                        command.ExecuteNonQuery();
                        return true;
                    }
                }
            }
            catch (Exception ex)
            {
                logger.Error(ex);
                return false;
            }
        }

        public static List<Order2> GetAllOrders()
        {
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionstring))
                {
                    connection.Open();
                    using (SqlCommand command = connection.CreateCommand())
                    {
                        command.CommandText = @"SELECT * FROM Заказ";
                        DataTable table = new DataTable();
                        table.Load(command.ExecuteReader());
                        
                        List<Order2> order2s = new List<Order2>();
                        foreach (DataRow row in table.Rows)
                        {
                            order2s.Add(new Order2(row));
                        }

                        return order2s;
                    }
                }
            }
            catch (Exception ex)
            {
                logger.Error(ex);
                return null;
            }
        }

        public static void AddListProducts(int Zakaz,int idTovar)
        {
            using (SqlConnection connection = new SqlConnection(connectionstring))
            {
                connection.Open();
                using (SqlCommand command = connection.CreateCommand())
                {
                    command.CommandText = @"INSERT INTO Список_товаров VALUES(@Zakaz, @Tovar);";
                    command.Parameters.AddWithValue("@Zakaz", Zakaz);
                    command.Parameters.AddWithValue("@Tovar", idTovar);
                    command.ExecuteNonQuery();
                }
            }
        }

        public static bool MinusCount(int idTovar)
        {
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionstring))
                {
                    connection.Open();
                    using (SqlCommand command = connection.CreateCommand())
                    {
                        command.CommandText = @"Update Товар SET Количество = Количество - 1 WHERE id = @Id";
                        command.Parameters.AddWithValue("@Id", idTovar);
                        int result = command.ExecuteNonQuery();
                        logger.Info($"Product {idTovar}, Result {result}");
                        return true;
                    }
                }
            }
            catch(Exception ex)
            {
                logger.Error(ex);
                return false;
            }
        }

        public static bool CheckUser(string email, byte[] Password)
        {
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionstring))
                {
                    connection.Open();
                    using (SqlCommand command = connection.CreateCommand())
                    {
                        command.CommandText = @"SELECT COUNT(*) FROM Пользователь WHERE Email = @email AND Пароль = @Password";
                        command.Parameters.AddWithValue("@Email", email);
                        command.Parameters.AddWithValue("@Password", Password);
                        DataTable table = new DataTable();
                        table.Load(command.ExecuteReader());
                        logger.Debug(@$"Select * FROM Пользователь Where Email = {email} AND Пароль = {Password}");
                        logger.Info($"CheckUser result = {int.Parse(table.Rows[0].ItemArray[0].ToString())}");
                        return int.Parse(table.Rows[0].ItemArray[0].ToString()) > 0;
                    }
                }
            }
            catch (Exception ex)
            {
                logger.Error(ex);
                return false;
            }
        }

        public static UserRegistration1 GetUser1(string email)
        {
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionstring))
                {
                    connection.Open();
                    using (SqlCommand command = connection.CreateCommand())
                    {
                        command.CommandText = @"SELECT * FROM Пользователь WHERE Email = @email";
                        command.Parameters.AddWithValue("@Email", email);
                        DataTable table = new DataTable();
                        table.Load(command.ExecuteReader());
                        return new UserRegistration1(table.Rows[0]);
                    }
                }
            }
            catch (Exception ex)
            {
                logger.Error(ex);
                return null;
            }
        }

        public static UserRegistration1 GetUserAuth(string email)
        {
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionstring))
                {
                    connection.Open();
                    using (SqlCommand command = connection.CreateCommand())
                    {
                        command.CommandText = @"SELECT * FROM Пользователь WHERE Email = @email";
                        command.Parameters.AddWithValue("@Email", email);
                        DataTable table = new DataTable();
                        table.Load(command.ExecuteReader());
                        return new UserRegistration1(table.Rows[0]);
                    }
                }
            }
            catch (Exception ex)
            {
                logger.Error(ex);
                return null;
            }
        }

        public static bool AddUser(string Email, string Login, byte[] Password, string FIO, string Address, string PhoneNumber)
        {
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionstring))
                {
                    connection.Open();
                    using (SqlCommand command = connection.CreateCommand())
                    {
                        command.CommandText = @"INSERT INTO Пользователь VALUES(@Email, @Login, @Password, @FIO, @Address, @PhoneNumber)";
                        command.Parameters.AddWithValue("@Email", Email);
                        command.Parameters.AddWithValue("@Login", Login);
                        command.Parameters.AddWithValue("@Password", Password);
                        command.Parameters.AddWithValue("@FIO", FIO);
                        command.Parameters.AddWithValue("@Address", Address);
                        command.Parameters.AddWithValue("@PhoneNumber", PhoneNumber);
                        command.ExecuteNonQuery();
                    }
                }
                return true;
            }
            catch (Exception ex)
            {
                logger.Error(ex);
                return false;
            }
        }

        public static bool InsertProduct(ProductWithoutId product)
        {
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionstring))
                {
                    int inserted = 0;
                    connection.Open();
                    using (SqlCommand command = connection.CreateCommand())
                    {
                        command.CommandText = @"INSERT INTO Характеристика output INSERTED.ID VALUES(@idColor, @idManufacturer, @idRAM, @idROM, @idDiag, @Image)";
                        command.Parameters.AddWithValue("@idColor", product.Color);
                        command.Parameters.AddWithValue("@idManufacturer", product.Manufacturer);
                        command.Parameters.AddWithValue("@idRAM", product.RAM);
                        command.Parameters.AddWithValue("@idROM", product.ROM);
                        command.Parameters.AddWithValue("@idDiag", product.Diagonal);
                        command.Parameters.AddWithValue("@Image", product.Image);
                        inserted = (int)command.ExecuteScalar();
                    }
                    using (SqlCommand command = connection.CreateCommand())
                    {
                        command.CommandText = @"INSERT INTO Товар VALUES(@Name, @Cost, @Description, @Charact, @Count)";
                        command.Parameters.AddWithValue("@Name", product.Name);
                        command.Parameters.AddWithValue("@Cost", product.Cost);
                        command.Parameters.AddWithValue("@Description", product.Description);
                        command.Parameters.AddWithValue("@Charact", inserted);
                        command.Parameters.AddWithValue("@Count", product.Count);
                        command.ExecuteNonQuery();
                    }
                }
                return true;
            }
            catch(Exception ex)
            {
                logger.Error(ex);
                return false;
            }
        }

        public static bool DeleteProduct(int id)
        {
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionstring))
                {
                    connection.Open();
                    using (SqlCommand command = connection.CreateCommand())
                    {
                        command.CommandText = @"DELETE FROM Товар Where id=@id";
                        command.Parameters.AddWithValue("@id", id);
                        command.ExecuteNonQuery();
                    }
                }
                return true;
            }
            catch(Exception ex)
            {
                logger.Error(ex);
                return false;
            }
        }

        private static List<Product> FromTableToProducts(DataTable table)
        {
            List<Product> list = new List<Product>();
            try
            {
                foreach (DataRow row in table.Rows)
                {
                    Product product = new Product(row);
                    list.Add(product);
                }

                return list;
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex);
                Console.WriteLine(ex.StackTrace);
                return list;
            }
        } 

        public static bool InsertUserTemporary(UserRegistration user, byte[] hashPassword, int code)
        {
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionstring))
                {
                    connection.Open();
                    using (SqlCommand command = connection.CreateCommand())
                    {
                        command.CommandText = "INSERT INTO ВПользователь VALUES(@Email, @Login, @Password, @FIO, @Address, @PhoneNumber, @Code)";
                        command.Parameters.AddWithValue("@Email", user.Email);
                        command.Parameters.AddWithValue("@Login", user.Login);
                        command.Parameters.AddWithValue("@Password", hashPassword);
                        command.Parameters.AddWithValue("@FIO", user.FIO);
                        command.Parameters.AddWithValue("@Address", user.Address);
                        command.Parameters.AddWithValue("@PhoneNumber", user.PhoneNumber);
                        command.Parameters.AddWithValue("@Code", code);
                        command.ExecuteNonQuery();
                        return true;

                    }
                }
            }
            catch (Exception ex)
            {
                logger.Error(ex);
                return false;
            }
        }

        public static byte[] HashPassword(string Password)
        {
            var argon2 = new Argon2i(Encoding.UTF8.GetBytes(Password));
            argon2.Salt = new byte[] { 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10 };
            argon2.DegreeOfParallelism = 16;
            argon2.MemorySize = 8192;
            argon2.Iterations = 40;
            return argon2.GetBytes(512);
        }

        public static bool ExistCode(int code)
        {
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionstring))
                {
                    connection.Open();
                    using (SqlCommand command = connection.CreateCommand())
                    {
                        command.CommandText = @"SELECT Count(*) FROM ВПользователь WHERE [Код подтверждения] = @Code";
                        command.Parameters.AddWithValue("@Code", code);
                        return (int)command.ExecuteScalar() > 0;
                    }
                }
            }
            catch (Exception ex)
            {
                logger.Error(ex);
                return false;
            }
        }

        public static UserTemporary GetTemporaryUser(int code)
        {
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionstring))
                {
                    connection.Open();
                    using (SqlCommand command = connection.CreateCommand())
                    {
                        command.CommandText = @"SELECT * FROM ВПользователь WHERE [Код подтверждения] = @Code";
                        command.Parameters.AddWithValue("@Code", code);
                        DataTable table = new DataTable();
                        table.Load(command.ExecuteReader());

                        UserTemporary user = new UserTemporary(table.Rows[0]);
                        return user;
                    }
                }
            }
            catch (Exception ex)
            {
                logger.Error(ex);
                return null;
            }
        }

        public static bool DeleteTemporaryUser(int code)
        {
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionstring))
                {
                    connection.Open();
                    using (SqlCommand command = connection.CreateCommand())
                    {
                        command.CommandText = @"DELETE FROM ВПользователь WHERE [Код подтверждения] = @Code";
                        command.Parameters.AddWithValue("@Code", code);
                        command.ExecuteNonQuery();

                        return true;
                    }
                }
            }
            catch (Exception ex)
            {
                logger.Error(ex);
                return false;
            }
        }
    }
}
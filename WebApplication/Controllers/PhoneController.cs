using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.IdentityModel.Tokens;
using Newtonsoft.Json;
using NLog;
using NLog.Fluent;
using System;
using System.Collections.Generic;
using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Text;
using System.Net;
using System.Linq;

namespace WebApplication.Controllers
{

    [ApiController]
    [Route("[controller]")]
    public class PhoneController : ControllerBase
    {
        private static readonly Logger logger = LogManager.GetCurrentClassLogger();

        [HttpGet]
        [Route("GetPhones")]
        public IActionResult GetPhones()
        {
            try
            {
                logger.Info("Запрос всех телефонов");
                string json = DBManager.GetPhones();
                return Ok(json);
            }
            catch (Exception ex)
            {
                logger.Error(ex);
                return Problem();
            }
        }

        [HttpGet]
        [Route("GetPhone")]
        public IActionResult GetPhone([FromQuery] int id)
        {
            try
            {
                logger.Info($"Запрос телефона id={id}");
                string json = DBManager.GetPhone(id);
                return Ok(json);
            }
            catch (Exception ex)
            {
                logger.Error(ex);
                return Problem();
            }
        }

        [HttpPut]
        [Route("UpdatePhone")]
        public IActionResult UpdatePhone(ProductUpdate product)
        {
            try
            {
                if (DBManager.UpdateProduct(product))
                    return Ok("Товар успешно изменен");
                else
                    return Problem("Не удалось обновить товар");
            }
            catch (Exception ex)
            {
                logger.Error(ex);
                return Problem();
            }
            
        }
        [HttpPost]
        [Route("AddPhone")]
        public IActionResult AddPhone(ProductWithoutId product)
        {
            try
            {
                if (DBManager.InsertProduct(product))
                    return Ok("Товар добавлен");
                else
                    return Problem("Не удалось добавить товар");
            }
            catch (Exception ex)
            {
                logger.Error(ex);
                return Problem();
            }
        }

        [HttpDelete]
        [Route("DeletePhone")]
        public IActionResult DeletePhone([FromQuery] int id)
        {
            try
            {
                if (DBManager.DeleteProduct(id))
                    return Ok("Товар успешно удален");
                else
                    return Problem("Не удалось удалить товар");
            }
            catch (Exception ex)
            {
                logger.Error(ex);
                return Problem();
            }
        }

        [HttpPost]
        [Route("AddManufacturer")]
        public IActionResult AddManufacturer(ProductPropWithoutId product)
        {
            if (DBManager.AddManufacturer(product.Value))
                return Ok("Производитель успешно добавлен");
            else
                return Problem("Не удалось добавить производителя");
        }

        [HttpPost]
        [Route("AddColor")]
        public IActionResult AddColor(ProductPropWithoutId product)
        {
            if (DBManager.AddColor(product.Value))
                return Ok("Цвет успешно добавлен");
            else
                return Problem("Не удалось добавить цвет");
        }

        [HttpPost]
        [Route("AddRAM")]
        public IActionResult AddRAM(ProductPropWithoutId product)
        {
            if (DBManager.AddRAM(int.Parse(product.Value)))
                return Ok("Оп.память успешно добавлена");
            else
                return Problem("Не удалось добавить Оп.память");
        }

        [HttpPost]
        [Route("AddROM")]
        public IActionResult AddROM(ProductPropWithoutId product)
        {
            if (DBManager.AddROM(int.Parse(product.Value)))
                return Ok("Встр.память успешно добавлена");
            else
                return Problem("Не удалось добавить Встр.память");
        }

        [HttpPost]
        [Route("AddDiagonal")]
        public IActionResult AddDiagonal(ProductPropWithoutId product)
        {
            if (DBManager.AddDiag(double.Parse(product.Value)))
                return Ok("Диагональ успешно добавлена");
            else
                return Problem("Не удалось добавить Диагональ");
        }
        
        [HttpGet]
        [Route("GetColors")]
        public IActionResult GetColors()
        {
            try
            {
                var result = DBManager.GetColor();
                return Ok(result);
            }
            catch (Exception ex)
            {
                logger.Error(ex);
                return Problem();
            }
        }
        
        [HttpGet]
        [Route("GetManufacturer")]
        public IActionResult GetManufacturer()
        {
            try
            {
                var result = DBManager.GetManufacturer();
                return Ok(result);
            }
            catch (Exception ex)
            {
                logger.Error(ex);
                return Problem();
            }
        }
        
        [HttpGet]
        [Route("GetRAM")]
        public IActionResult GetRAM()
        {
            try
            {
                var result = DBManager.GetRAM();
                return Ok(result);
            }
            catch (Exception ex)
            {
                logger.Error(ex);
                return Problem();
            }
        }
        
        [HttpGet]
        [Route("GetROM")]
        public IActionResult GetROM()
        {
            try
            {
                var result = DBManager.GetROM();
                return Ok(result);
            }
            catch (Exception ex)
            {
                logger.Error(ex);
                return Problem();
            }
        }
        
        [HttpGet]
        [Route("GetDiagonal")]
        public IActionResult GetDiagonal()
        {
            try
            {
                var result = DBManager.GetDiagonal();
                return Ok(result);
            }
            catch (Exception ex)
            {
                logger.Error(ex);
                return Problem();
            }
        }


        [HttpDelete]
        [Route("DeleteColor")]
        public IActionResult DeleteColor(ProductPropId propId)
        {
            if (DBManager.DeleteColor(propId.Id))
                return Ok("Цвет успешно удален");
            else
                return Problem("Не удалось удалить цвет");
        }
        
        [HttpDelete]
        [Route("DeleteManufacturer")]
        public IActionResult DeleteManufacturer(ProductPropId propId)
        {

            if (DBManager.DeleteManufacturer(propId.Id))
                return Ok("Производитель успешно удален");
            else
                return Problem("Не удалось удалить производителя");
        }
        
        [HttpDelete]
        [Route("DeleteRAM")]
        public IActionResult DeleteRAM(ProductPropId propId)
        {
            if (DBManager.DeleteRAM(propId.Id))
                return Ok("Опер.память успешно удалена");
            else
                return Problem("Не удалось удалить Опер.память");
        }
        
        [HttpDelete]
        [Route("DeleteROM")]
        public IActionResult DeleteROM(ProductPropId propId)
        {
            if (DBManager.DeleteROM(propId.Id))
                return Ok("Встр.память успешно удалена");
            else
                return Problem("Не удалось удалить Встр.память");
        }
        
        [HttpDelete]
        [Route("DeleteDiagonal")]
        public IActionResult DeleteDiagonal(ProductPropId propId)
        {
            if (DBManager.DeleteDiag(propId.Id))
                return Ok("Диагональ успешно удалена");
            else
                return Problem("Не удалось удалить диагональ");
        }
        


        [HttpPost]
        [Route("Registration")]
        public IActionResult Registration(UserRegistration user)
        {
            try
            {
                logger.Info($"Регистрация пользователя, Name = {user.Login}, Email = {user.Email}");
                //var hashPass1 = Sodium.PasswordHash.ArgonHashBinary(Encoding.UTF8.GetBytes(user.Password), new byte[] { 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10 }, PasswordHash.StrengthArgon.Interactive);
                var hashPassword = DBManager.HashPassword(user.Password);
                //var hashPass = Sodium.PasswordHash.ArgonHashString(user.Password, PasswordHash.StrengthArgon.Interactive);
                if (DBManager.AddUser(user.Email, user.Login, hashPassword, user.FIO, user.Address, user.PhoneNumber))
                    return Ok("Succesful registration");
                else
                    return Problem("Failed registration");
            }
            catch (Exception ex)
            {
                logger.Error(ex);
                return Problem();
            }
        }

        [HttpPost]
        [Route("AddOrder")]
        public IActionResult AddOrder(Order order)
        {
            try
            {
                DateTime time;
                int id = -1;
                logger.Info($"email пользователя = {order.email}");
                logger.Info($"Дата_заказа = {order.date}");
                logger.Info($"Summa = {order.price}");
                if (!DateTime.TryParse(order.date, out time))
                    time = DateTime.Now;
                logger.Info($"DATETIME TRYPARSE {DateTime.TryParse(order.date, out time) }");
                logger.Info($"DATETIME = {time.ToShortDateString()}");
                logger.Info($"Пришел заказ с кол-вом товаров = {order.prodId.Length}");

                id = DBManager.AddOrder(order.email, time, order.price);
                if (id != -1)
                {
                    for (int i = 0; i < order.prodId.Length; i++)
                    {
                        logger.Info($"Добавление товара {i}");
                        if (!DBManager.MinusCount(order.prodId[i])) throw new Exception("Не удалось уменьшить кол-во товара");
                        DBManager.AddListProducts(id, order.prodId[i]);
                    }
                    logger.Info("Вставка заказа прошла успешно");
                }
                return Ok(id);
            }
            catch (Exception ex)
            {
                logger.Error(ex);
                return Problem();
            }
        }

        [HttpPut]
        [Route("ChangeStatus")]
        public IActionResult ChangeStatus(Order3 order)
        {
            if (DBManager.ChangeStatus(order.id, order.Status))
                return Ok("Статус заказа успешно обновлен");
            else
                return Problem("Не удалось изменить статус заказа");
        }

        [HttpPut]
        [Route("ChangeCount")]
        public IActionResult ChangeCount(ProductCount product)
        {
            if (DBManager.UpdateCount(product.Id, product.Count))
                return Ok("Количество товара обновлено");
            else
                return Problem("Не удалось обновить количество товара");
        }

        [HttpGet("Auth")]
        public IActionResult Auth()
        {
            try
            {
                logger.Info($"IsAuth {User.Identity.IsAuthenticated}");
                logger.Info(User.Identities.Count());
                if (User.Identity.IsAuthenticated)
                {
                    string Email = User.Claims.ToList()[0].Value;
                    UserRegistration1 user = DBManager.GetUser1(Email);
                    var response = new
                    {
                        resultCode = 0,
                        Email = user.Email,
                        Login = user.Login,
                        FIO = user.FIO,
                        Address = user.Address,
                        PhoneNumber = user.PhoneNumber
                    };
                    return Ok(response);
                }
                else
                {
                    var response = new
                    {
                        resultCode = -1,
                        Comment = "Пользователь неавторизован"
                    };
                    return Unauthorized(response);
                }
            }
            catch(Exception ex)
            {
                logger.Error(ex);
                return Problem();
            }
        }

        [HttpGet("GetOrder")]
        public IActionResult GetOrder()
        {
            try
            {
                if (User.Identity.IsAuthenticated)
                {
                    string Email = User.Claims.ToList()[0].Value;
                    List<Order1> orders = DBManager.GetOrder(Email);
                    return Ok(orders);
                }
                else
                {
                    return Unauthorized();
                }
            }
            catch(Exception ex)
            {
                logger.Info(ex);
                return Problem();
            }
        }

        [HttpGet("GetAcceptedOrders")]
        public IActionResult GetAcceptedOrders()
        {
            var list = DBManager.GetAcceptedOrders();
            if (list == null) return Problem();
            return Ok(list);
        }

        [HttpGet("GetProcessedOrders")]
        public IActionResult GetProcessedOrders()
        {
            var list = DBManager.GetProcessedOrders();
            if (list == null) return Problem();
            return Ok(list);
        }

        [HttpGet]
        [Route("GetAllOrders")]
        public IActionResult GetAllOrders()
        {
            try
            {
                List<Order2> order2s = DBManager.GetAllOrders();
                return Ok(order2s);
            }
            catch (Exception ex)
            {
                logger.Error(ex);
                return Problem();
            }
        }


        [HttpPost("Authentication")]
        public IActionResult Authentication(User user, [FromServices] IJwtSigningEncodingKey signingEncodingKey)
        {
            try
            {
                logger.Info($"Login, user.eMail = {user.Email}");
                var hashPassword = DBManager.HashPassword(user.Password);
                if (DBManager.CheckUser(user.Email, hashPassword) != null)
                {
                    logger.Info("Пользователь прошёл проверку");
                    var claims = new Claim[]
                           {
                               new Claim(ClaimTypes.Email, user.Email)
                           };
                    var token = new JwtSecurityToken(
                        issuer: "DemoApp",
                        audience: "DemoAppClient",
                        claims: claims,
                        expires: DateTime.Now.AddMinutes(5),
                        signingCredentials: new SigningCredentials(
                                signingEncodingKey.GetKey(),
                                signingEncodingKey.SigningAlgorithm)
                    );

                    string jwtToken = new JwtSecurityTokenHandler().WriteToken(token);

                    var response = new
                    {
                        access_token = jwtToken,
                        username = user.Email
                    };

                    return Ok(response);
                }
                else
                {
                    return Unauthorized("Пользователь не найден");
                }
            }
            catch (Exception ex)
            {
                logger.Error(ex);
                return Problem();
            }
        }


        [HttpPost]
        [Route("SendMail")]
        public IActionResult SendEmail(UserRegistration user)
        {
            try
            {
                EmailService service = new EmailService();

                Random random = new Random();
                int code = random.Next(100000, 999999);

                if (DBManager.InsertUserTemporary(user, DBManager.HashPassword(user.Password), code))
                    service.SendEmailAsync(user.Email, "Код подтверждения для регистрации", $"Код: {code}");
                else
                    return Problem("Не удалось отправить email");
                return Ok(code);
            }
            catch (Exception ex)
            {
                logger.Error(ex);
                return Problem();
            }
        }

        [HttpGet]
        [Route("CodeRegistration")]
        public IActionResult CodeRegistration(int code)
        {
            if (DBManager.ExistCode(code))
            {
                logger.Info("Code exist");
                var user = DBManager.GetTemporaryUser(code);
                logger.Info(user.Email);
                if (DBManager.AddUser(user.Email, user.Login, user.Password, user.FIO, user.Address, user.PhoneNumber))
                {
                    DBManager.DeleteTemporaryUser(code);
                    return Ok("Регистрация прошла успешно");
                }
                return Problem("Не удалось зарегистрировать пользователя при верном коде подтверждения");
            }
            else
            {
                return Problem("Неверно введен код подтверждения");
            }
        }

    }
}
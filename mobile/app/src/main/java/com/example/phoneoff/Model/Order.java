package com.example.phoneoff.Model;

public class Order {
    public int idOrder;
    public String status;
    public int summa;

    public Order(int Id, String Status, int Summa) {
        idOrder = Id;
        status = Status;
        summa = Summa;
    }
}

package com.example.phoneoff;

public class RegistrationUser {
    public String email;
    public String login;
    public String password;
    public String fio;
    public String address;
    public String phoneNumber;


    public RegistrationUser(String email, String login, String password, String fio, String address, String phoneNumber) {
        this.email = email;
        this.login = login;
        this.password = password;
        this.fio = fio;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
}

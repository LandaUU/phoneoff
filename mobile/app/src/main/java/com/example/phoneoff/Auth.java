package com.example.phoneoff;

public class Auth {

    public String access_token;
    public String username;

    public Auth() {
    }

    public Auth(String access_token, String username) {
        this.access_token = access_token;
        this.username = username;
    }
}

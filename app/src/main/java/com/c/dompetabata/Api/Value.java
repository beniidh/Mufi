package com.c.dompetabata.Api;

import com.c.dompetabata.Model.Mlogin;
import com.c.dompetabata.Model.data;

import java.util.List;

public class Value {

    String credentials;
    String password;
    data data;
    String message;

    public String getMessage() {
        return message;
    }

    public Value() {
    }

    public data getData() {
        return data;
    }


    public Value(String credentials, String password) {
        this.credentials = credentials;
        this.password = password;
    }

    public String getCredentials() {
        return credentials;
    }

    public void setCredentials(String credentials) {
        this.credentials = credentials;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

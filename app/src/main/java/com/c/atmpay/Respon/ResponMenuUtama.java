package com.c.atmpay.Respon;

import com.c.atmpay.Model.ModelMenuUtama;

import java.util.ArrayList;

public class ResponMenuUtama {

    String code,error,message;
    ArrayList<ModelMenuUtama> data;

    public String getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<ModelMenuUtama> getData() {
        return data;
    }
}

package com.c.apay.Respon;

import com.c.apay.Model.MSubMenu;
import com.c.apay.Model.data;

import java.util.ArrayList;

public class ResponProfil {
    String code, error, message;
    data data;
    ArrayList<MSubMenu> menu;

    public String getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public com.c.apay.Model.data getData() {
        return data;
    }


}

package com.c.cpay.Respon;

import com.c.cpay.Model.MSubMenu;
import com.c.cpay.Model.data;

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

    public com.c.cpay.Model.data getData() {
        return data;
    }


}

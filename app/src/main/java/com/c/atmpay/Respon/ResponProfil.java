package com.c.atmpay.Respon;

import com.c.atmpay.Model.MSubMenu;
import com.c.atmpay.Model.data;

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

    public com.c.atmpay.Model.data getData() {
        return data;
    }


}

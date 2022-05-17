package com.c.kreload.Respon;

import com.c.kreload.Model.MSubMenu;
import com.c.kreload.Model.data;

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

    public com.c.kreload.Model.data getData() {
        return data;
    }


}

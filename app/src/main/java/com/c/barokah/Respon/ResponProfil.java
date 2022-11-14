package com.c.barokah.Respon;

import com.c.barokah.Model.MSubMenu;
import com.c.barokah.Model.data;

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

    public com.c.barokah.Model.data getData() {
        return data;
    }


}

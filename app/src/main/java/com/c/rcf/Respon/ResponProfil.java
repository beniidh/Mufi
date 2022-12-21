package com.c.rcf.Respon;

import com.c.rcf.Model.MSubMenu;
import com.c.rcf.Model.data;

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

    public com.c.rcf.Model.data getData() {
        return data;
    }


}

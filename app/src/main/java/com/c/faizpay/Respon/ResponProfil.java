package com.c.faizpay.Respon;

import com.c.faizpay.Model.MSubMenu;
import com.c.faizpay.Model.data;

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

    public com.c.faizpay.Model.data getData() {
        return data;
    }


}

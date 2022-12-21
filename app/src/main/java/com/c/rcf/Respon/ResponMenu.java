package com.c.rcf.Respon;

import com.c.rcf.Model.Micon;

import java.util.List;

public class ResponMenu {
    String code,error,message;
    List<Micon> data;

    public String getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public List<Micon> getData() {
        return data;
    }
}

package com.c.atmpay.Respon;

import com.c.atmpay.Model.MBanner;

import java.util.ArrayList;

public class ResponBanner {

    String code;
    String message;
    ArrayList<MBanner> data;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<MBanner> getData() {
        return data;
    }
}

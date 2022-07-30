package com.c.cpay.Respon;

import com.c.cpay.Model.MSubPLN;

import java.util.ArrayList;

public class ResponSubP {
    String code ;
    String error;
    ArrayList<MSubPLN> data;

    public String getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    public ArrayList<MSubPLN> getData() {
        return data;
    }
}

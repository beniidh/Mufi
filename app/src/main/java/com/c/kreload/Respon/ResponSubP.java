package com.c.kreload.Respon;

import com.c.kreload.Model.MSubPLN;

import java.util.List;

public class ResponSubP {
    String code ;
    String error;
    List<MSubPLN> data;

    public String getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    public List<MSubPLN> getData() {
        return data;
    }
}

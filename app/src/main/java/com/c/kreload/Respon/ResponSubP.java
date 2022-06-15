package com.c.kreload.Respon;

import com.c.kreload.Model.MSubPLN;

import java.util.ArrayList;
import java.util.List;

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

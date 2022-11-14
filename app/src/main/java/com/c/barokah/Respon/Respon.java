package com.c.barokah.Respon;

import com.c.barokah.Model.ModelProvinsi;

import java.util.List;

public class Respon {
    String code ;
    String error;
    List<ModelProvinsi> data;

    public String getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    public List<ModelProvinsi> getData() {
        return data;
    }

}

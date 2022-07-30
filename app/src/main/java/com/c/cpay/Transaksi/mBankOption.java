package com.c.cpay.Transaksi;

import java.util.ArrayList;

public class mBankOption {

    String code, error;

    ArrayList<Data> data;

    public String getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    public ArrayList<Data> getData() {
        return data;
    }

    public class Data {

        String id, value, name, account_no, account_name,icon;

        public String getId() {
            return id;
        }

        public String getValue() {
            return value;
        }

        public String getIcon() {
            return icon;
        }

        public String getName() {
            return name;
        }

        public String getAccount_no() {
            return account_no;
        }

        public String getAccount_name() {
            return account_name;
        }
    }
}

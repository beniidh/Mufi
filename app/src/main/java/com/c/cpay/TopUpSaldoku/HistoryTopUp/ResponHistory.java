package com.c.cpay.TopUpSaldoku.HistoryTopUp;

import java.util.ArrayList;

public class ResponHistory {
    String code, error;
    ArrayList<mData>data;

    public String getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    public ArrayList<mData> getData() {
        return data;
    }

    class mData {

        String id, amount, proof_payment, status, created_at;

        public String getId() {
            return id;
        }

        public String getAmount() {
            return amount;
        }

        public String getProof_payment() {
            return proof_payment;
        }

        public String getStatus() {
            return status;
        }

        public String getCreated_at() {
            return created_at;
        }
    }
}

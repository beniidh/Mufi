package com.c.kreload.Notifikasi.Pesan;

public class mPesanDetail {

    String code,error;
    mData data;

    public String getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    public mData getData() {
        return data;
    }

    public class mData{

        String id,title,message,server,tujuan,status,created_at;

        public String getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getMessage() {
            return message;
        }

        public String getServer() {
            return server;
        }

        public String getTujuan() {
            return tujuan;
        }

        public String getStatus() {
            return status;
        }

        public String getCreated_at() {
            return created_at;
        }
    }
}

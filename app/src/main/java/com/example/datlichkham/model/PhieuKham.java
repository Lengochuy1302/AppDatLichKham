package com.example.datlichkham.model;

public class PhieuKham {
    private String id;
    private String idBs, idBn;
    private String tenBs, tenBn;
    private String status;
    private String benh;
    private String note;
    private String date;
    private String time;
    private int rate;

    public PhieuKham() {
    }

    public PhieuKham(String id, String idBs, String idBn, String tenBs, String tenBn, String status, String note, String date, String time, String benh, int rate) {
        this.id = id;
        this.idBs = idBs;
        this.idBn = idBn;
        this.tenBs = tenBs;
        this.tenBn = tenBn;
        this.status = status;
        this.note = note;
        this.date = date;
        this.time = time;
        this.rate = rate;
        this.benh = benh;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdBs() {
        return idBs;
    }

    public void setIdBs(String idBs) {
        this.idBs = idBs;
    }

    public String getIdBn() {
        return idBn;
    }

    public void setIdBn(String idBn) {
        this.idBn = idBn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getTenBs() {
        return tenBs;
    }

    public void setTenBs(String tenBs) {
        this.tenBs = tenBs;
    }

    public String getTenBn() {
        return tenBn;
    }

    public void setTenBn(String tenBn) {
        this.tenBn = tenBn;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBenh() {
        return benh;
    }

    public void setBenh(String benh) {
        this.benh = benh;
    }
}

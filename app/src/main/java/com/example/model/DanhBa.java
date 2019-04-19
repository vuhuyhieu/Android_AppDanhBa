package com.example.model;

import java.io.Serializable;

public class DanhBa implements Serializable {


    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getNhom() {
        return nhom;
    }

    public void setNhom(String nhom) {
        this.nhom = nhom;
    }

    public boolean isThich() {
        return thich;
    }

    public void setThich(boolean thich) {
        this.thich = thich;
    }

    private String hoTen;
    private String soDienThoai;
    private String nhom;
    private boolean thich;

    public DanhBa(String hoTen, String soDienThoai, String nhom, boolean thich){
        this.hoTen = hoTen;
        this.soDienThoai = soDienThoai;
        this.nhom = nhom;
        this.thich = thich;
    }

    public DanhBa (String hoTen, String soDienThoai){
        this.hoTen = hoTen;
        this.soDienThoai = soDienThoai;
    }

    public DanhBa(){
        this.thich=false;
    }
}

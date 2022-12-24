package com.laporankeuangan.Model;

public class OutcomeModel {
    private String imgurl;
    private String kategori;
    private String tanggal;
    private int jumlah;
    private String notes;



    public OutcomeModel(String kategori, String tanggal, int jumlah, String notes, String imgurl) {

        this.kategori = kategori;
        this.tanggal = tanggal;
        this.jumlah = jumlah;
        this.notes = notes;
        this.imgurl = imgurl;

    }

    public OutcomeModel() {

    }


    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getimgurl() {
        return imgurl;
    }

    public void setimgurl(String imgurl) {
        this.imgurl = imgurl;
    }
}


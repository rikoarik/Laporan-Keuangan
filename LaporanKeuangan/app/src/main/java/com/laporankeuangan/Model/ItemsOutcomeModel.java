package com.laporankeuangan.Model;

public class ItemsOutcomeModel {

        private String imgurl;
        private int jumlah;
        private String kategori;
        private String keterangan;
        private String tanggal;
        private String time;
        private String timestamp;

        public ItemsOutcomeModel(String imgurl, int jumlah, String kategori, String keterangan, String tanggal, String time, String timestamp) {

            this.imgurl = imgurl;
            this.jumlah = jumlah;
            this.kategori = kategori;
            this.tanggal = tanggal;
            this.time = time;
            this.timestamp = timestamp;
            this.keterangan = keterangan;


        }

    public ItemsOutcomeModel() {
            super();

    }

    public String getimgurl() {
        return imgurl;
    }

    public void setimgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
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

        public String getKeterangan() {
            return keterangan;
        }

        public void setKeterangan(String keterangan) {
            this.keterangan = keterangan;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
    }
}

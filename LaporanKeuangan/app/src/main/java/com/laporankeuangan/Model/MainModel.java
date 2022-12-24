package com.laporankeuangan.Model;

import java.util.Date;

public class MainModel {
    private int saldosekarang;
    private int outcomenow;
    private int incomenow;
    private String dateincome;
    private String dateOutcome;
    private String timeincome;
    private String timeoutcome;


    public MainModel(int saldosekarang, int outcomenow, int incomenow, String dateincome, String dateOutcome, String timeincome, String timeoutcome){
        this.saldosekarang = saldosekarang;
        this.outcomenow = outcomenow;
        this.incomenow = incomenow;
        this.dateincome = dateincome;
        this.dateOutcome = dateOutcome;
        this.timeincome = timeincome;
        this.timeoutcome = timeoutcome;

    }
    public MainModel(){}

    public int getSaldosekarang() {
        return saldosekarang;
    }

    public void setSaldosekarang(int saldosekarang) {
        this.saldosekarang = saldosekarang;
    }

    public int getOutcomenow() {
        return outcomenow;
    }

    public void setOutcomenow(int outcomenow) {
        this.outcomenow = outcomenow;
    }

    public int getIncomenow() {
        return incomenow;
    }

    public void setIncomenow(int incomenow) {
        this.incomenow = incomenow;
    }

    public String getDateincome() {
        return dateincome;
    }

    public void setDateincome(String dateincome) {
        this.dateincome = dateincome;
    }

    public String getDateOutcome() {
        return dateOutcome;
    }

    public void setDateOutcome(String dateOutcome) {
        this.dateOutcome = dateOutcome;
    }

    public String getTimeincome() {
        return timeincome;
    }

    public void setTimeincome(String timeincome) {
        this.timeincome = timeincome;
    }

    public String getTimeoutcome() {
        return timeoutcome;
    }

    public void setTimeoutcome(String timeoutcome) {
        this.timeoutcome = timeoutcome;
    }
}

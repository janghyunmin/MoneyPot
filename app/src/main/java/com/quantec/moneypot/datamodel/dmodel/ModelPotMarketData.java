package com.quantec.moneypot.datamodel.dmodel;

public class ModelPotMarketData {

    String url;
    int number;
    String title;
    String potCode;
    double rate;
    boolean check;

    public ModelPotMarketData(String url, int number, String title, String potCode, double rate, boolean check) {
        this.url = url;
        this.number = number;
        this.title = title;
        this.potCode = potCode;
        this.rate = rate;
        this.check = check;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPotCode() {
        return potCode;
    }

    public void setPotCode(String potCode) {
        this.potCode = potCode;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}

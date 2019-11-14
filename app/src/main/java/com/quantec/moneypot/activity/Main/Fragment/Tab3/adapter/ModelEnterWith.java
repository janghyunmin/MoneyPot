package com.quantec.moneypot.activity.Main.Fragment.Tab3.adapter;

public class ModelEnterWith {

    String title;
    String code;
    double rate;

    public ModelEnterWith(String title, String code, double rate) {
        this.title = title;
        this.code = code;
        this.rate = rate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}

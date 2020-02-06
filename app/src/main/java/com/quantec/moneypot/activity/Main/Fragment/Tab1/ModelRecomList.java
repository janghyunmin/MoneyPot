package com.quantec.moneypot.activity.Main.Fragment.Tab1;

public class ModelRecomList {

    String title;
    String code;
    String desc;
    double rate;
    String topTitle;
    int price;

    public ModelRecomList(String title, String code, String desc, double rate, String topTitle, int price) {
        this.title = title;
        this.code = code;
        this.desc = desc;
        this.rate = rate;
        this.topTitle = topTitle;
        this.price = price;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getTopTitle() {
        return topTitle;
    }

    public void setTopTitle(String topTitle) {
        this.topTitle = topTitle;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}

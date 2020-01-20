package com.quantec.moneypot.activity.Main.Fragment.Tab1.Activity;

public class ModelSnpTab {

    String filter;
    String title;
    String code;
    double rate;
    String price;
    int follow;

    public ModelSnpTab(String filter, String title, String code, double rate, String price, int follow) {
        this.filter = filter;
        this.title = title;
        this.code = code;
        this.rate = rate;
        this.price = price;
        this.follow = follow;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getFollow() {
        return follow;
    }

    public void setFollow(int follow) {
        this.follow = follow;
    }
}

package com.quantec.moneypot.activity.Main.Fragment.Tab1;

public class ModelFollowHome {

    boolean bottom;
    boolean empty;
    String title;
    String code;
    double rate;
    double price;

    public ModelFollowHome(boolean bottom, boolean empty, String title, String code, double rate, double price) {
        this.bottom = bottom;
        this.empty = empty;
        this.title = title;
        this.code = code;
        this.rate = rate;
        this.price = price;
    }

    public boolean isBottom() {
        return bottom;
    }

    public void setBottom(boolean bottom) {
        this.bottom = bottom;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

package com.quantec.moneypot.activity.Main.Fragment.Tab1;

public class ModelFollowHome {

    boolean empty;
    String title;
    String code;
    double rate;
    int price;

    public ModelFollowHome(boolean empty, String title, String code, double rate, int price) {
        this.empty = empty;
        this.title = title;
        this.code = code;
        this.rate = rate;
        this.price = price;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}

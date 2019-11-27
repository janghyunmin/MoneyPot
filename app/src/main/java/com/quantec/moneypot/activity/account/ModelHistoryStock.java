package com.quantec.moneypot.activity.account;

public class ModelHistoryStock {

    String title;
    String code;
    String price;
    double rate;
    double stock;

    boolean empty;

    public ModelHistoryStock(String title, String code, String price, double rate, double stock, boolean empty) {
        this.title = title;
        this.code = code;
        this.price = price;
        this.rate = rate;
        this.stock = stock;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getStock() {
        return stock;
    }

    public void setStock(double stock) {
        this.stock = stock;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }
}

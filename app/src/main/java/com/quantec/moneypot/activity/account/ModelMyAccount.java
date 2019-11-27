package com.quantec.moneypot.activity.account;

public class ModelMyAccount {

    String name;
    String code;
    String price;
    double rate;
    double stock;
    boolean empty;
    boolean bottom;

    public ModelMyAccount(String name, String code, String price, double rate, double stock, boolean empty, boolean bottom) {
        this.name = name;
        this.code = code;
        this.price = price;
        this.rate = rate;
        this.stock = stock;
        this.empty = empty;
        this.bottom = bottom;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean isBottom() {
        return bottom;
    }

    public void setBottom(boolean bottom) {
        this.bottom = bottom;
    }
}

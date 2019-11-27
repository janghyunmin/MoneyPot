package com.quantec.moneypot.activity.account;

public class ModelIncomeOut {

    String type;
    String date;
    String price;
    String tPrice;
    boolean empty;

    public ModelIncomeOut(String type, String date, String price, String tPrice, boolean empty) {
        this.type = type;
        this.date = date;
        this.price = price;
        this.tPrice = tPrice;
        this.empty = empty;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String gettPrice() {
        return tPrice;
    }

    public void settPrice(String tPrice) {
        this.tPrice = tPrice;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }
}

package com.quantec.moneypot.activity.account;

public class ModelHistoryComplete {

    String type;
    String title;
    String price;
    String date;
    boolean empty;

    public ModelHistoryComplete(String type, String title, String price, String date, boolean empty) {
        this.type = type;
        this.title = title;
        this.price = price;
        this.date = date;
        this.empty = empty;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }
}

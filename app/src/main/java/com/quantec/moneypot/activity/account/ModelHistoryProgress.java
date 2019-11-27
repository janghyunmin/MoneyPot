package com.quantec.moneypot.activity.account;

public class ModelHistoryProgress {

    String type;
    String name;
    String price;
    String date;
    boolean empty;

    public ModelHistoryProgress(String type, String name, String price, String date, boolean empty) {
        this.type = type;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

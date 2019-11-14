package com.quantec.moneypot.activity.Main.Fragment.Tab3;

public class ModelSelectItem {

    String title;
    String code;
    double rate;
    boolean empty;

    public ModelSelectItem(String title, String code, double rate, boolean empty) {
        this.title = title;
        this.code = code;
        this.rate = rate;
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

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }
}

package com.quantec.moneypot.activity.Main.Fragment.Tab1;

public class ModelStockList {

    boolean top;
    String title;
    double rate;

    public ModelStockList(boolean top, String title, double rate) {
        this.top = top;
        this.title = title;
        this.rate = rate;
    }

    public boolean isTop() {
        return top;
    }

    public void setTop(boolean top) {
        this.top = top;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}

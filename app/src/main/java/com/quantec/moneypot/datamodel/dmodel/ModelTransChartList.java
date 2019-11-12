package com.quantec.moneypot.datamodel.dmodel;

public class ModelTransChartList {

    String date;
    double price;
    double rate;

    public ModelTransChartList(String date, double price, double rate) {
        this.date = date;
        this.price = price;
        this.rate = rate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}

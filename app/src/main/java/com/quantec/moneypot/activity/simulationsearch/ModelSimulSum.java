package com.quantec.moneypot.activity.simulationsearch;

public class ModelSimulSum {

    boolean empty;
    String name;
    String code;
    String stock;
    double rate;

    public ModelSimulSum(boolean empty, String name, String code, String stock, double rate) {
        this.empty = empty;
        this.name = name;
        this.code = code;
        this.stock = stock;
        this.rate = rate;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
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

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}

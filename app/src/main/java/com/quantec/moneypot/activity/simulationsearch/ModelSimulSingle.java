package com.quantec.moneypot.activity.simulationsearch;

public class ModelSimulSingle {

    boolean empty;
    int total;
    String name;
    String code;
    double rate;

    public ModelSimulSingle(boolean empty, int total, String name, String code, double rate) {
        this.empty = empty;
        this.total = total;
        this.name = name;
        this.code = code;
        this.rate = rate;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
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

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}

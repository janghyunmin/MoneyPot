package com.quantec.moneypot.activity.simulationsearch;

public class ModelPreSimulSingle {

    int total;
    boolean empty;
    String title;
    String code;
    double rate;

    public ModelPreSimulSingle(int total, boolean empty, String title, String code, double rate) {
        this.total = total;
        this.empty = empty;
        this.title = title;
        this.code = code;
        this.rate = rate;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
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
}

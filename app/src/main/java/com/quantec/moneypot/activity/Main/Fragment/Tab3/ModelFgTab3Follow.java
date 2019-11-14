package com.quantec.moneypot.activity.Main.Fragment.Tab3;

public class ModelFgTab3Follow {

    String title;
    String code;
    double rate;
    int gubun;
    boolean empty;
    boolean open;

    public ModelFgTab3Follow(String title, String code, double rate, int gubun, boolean empty, boolean open) {
        this.title = title;
        this.code = code;
        this.rate = rate;
        this.gubun = gubun;
        this.empty = empty;
        this.open = open;
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

    public int getGubun() {
        return gubun;
    }

    public void setGubun(int gubun) {
        this.gubun = gubun;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}

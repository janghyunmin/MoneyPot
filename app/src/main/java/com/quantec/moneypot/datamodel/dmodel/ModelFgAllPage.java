package com.quantec.moneypot.datamodel.dmodel;

public class ModelFgAllPage {

    boolean empty;
    int type;
    String title;
    String code;
    double rate;
    int follow;
    String filterTitle;

    public ModelFgAllPage(boolean empty, int type, String title, String code, double rate, int follow, String filterTitle) {
        this.empty = empty;
        this.type = type;
        this.title = title;
        this.code = code;
        this.rate = rate;
        this.follow = follow;
        this.filterTitle = filterTitle;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public int getFollow() {
        return follow;
    }

    public void setFollow(int follow) {
        this.follow = follow;
    }

    public String getFilterTitle() {
        return filterTitle;
    }

    public void setFilterTitle(String filterTitle) {
        this.filterTitle = filterTitle;
    }
}

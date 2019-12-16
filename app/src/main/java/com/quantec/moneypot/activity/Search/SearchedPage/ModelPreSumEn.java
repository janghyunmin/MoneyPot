package com.quantec.moneypot.activity.Search.SearchedPage;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelPreSumEn {

    int total;
    boolean empty;
    String title;
    String code;
    String stock;
    double rate;
    int follow;

    public ModelPreSumEn(int total, boolean empty, String title, String code, String stock, double rate, int follow) {
        this.total = total;
        this.empty = empty;
        this.title = title;
        this.code = code;
        this.stock = stock;
        this.rate = rate;
        this.follow = follow;
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

    public int getFollow() {
        return follow;
    }

    public void setFollow(int follow) {
        this.follow = follow;
    }
}
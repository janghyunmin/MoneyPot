package com.quantec.moneypot.activity.Search.SearchedPage;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelPreNewsEn {

    boolean empty;
    int total;
    String title;
    String date;
    String url;

    public ModelPreNewsEn(boolean empty, int total, String title, String date, String url) {
        this.empty = empty;
        this.total = total;
        this.title = title;
        this.date = date;
        this.url = url;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

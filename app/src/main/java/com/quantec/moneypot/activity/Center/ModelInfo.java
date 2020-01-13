package com.quantec.moneypot.activity.center;

public class ModelInfo {

    boolean top;
    String type;
    String title;
    String date;
    String url;

    public ModelInfo(boolean top, String type, String title, String date, String url) {
        this.top = top;
        this.type = type;
        this.title = title;
        this.date = date;
        this.url = url;
    }

    public boolean isTop() {
        return top;
    }

    public void setTop(boolean top) {
        this.top = top;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

package com.quantec.moneypot.activity.center;

public class ModelQuestion {

    boolean empty;
    boolean top;
    String type;
    String title;
    String desc;
    String url;

    public ModelQuestion(boolean empty, boolean top, String type, String title, String desc, String url) {
        this.empty = empty;
        this.top = top;
        this.type = type;
        this.title = title;
        this.desc = desc;
        this.url = url;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

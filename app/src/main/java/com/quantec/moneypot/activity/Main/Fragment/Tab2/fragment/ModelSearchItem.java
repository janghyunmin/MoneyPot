package com.quantec.moneypot.activity.Main.Fragment.Tab2.fragment;

public class ModelSearchItem {

    String title;
    String code;
    boolean empty;

    public ModelSearchItem(String title, String code, boolean empty) {
        this.title = title;
        this.code = code;
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

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }
}

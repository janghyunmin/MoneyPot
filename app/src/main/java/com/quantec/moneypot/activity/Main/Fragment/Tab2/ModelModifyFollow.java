package com.quantec.moneypot.activity.Main.Fragment.Tab2;

public class ModelModifyFollow {

    boolean empty;
    int total;
    String title;
    String code;

    public ModelModifyFollow(boolean empty, int total, String title, String code) {
        this.empty = empty;
        this.total = total;
        this.title = title;
        this.code = code;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

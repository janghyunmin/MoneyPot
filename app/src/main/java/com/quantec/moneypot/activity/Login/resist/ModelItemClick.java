package com.quantec.moneypot.activity.Login.resist;

public class ModelItemClick {

    int total;
    boolean itemClick;
    String title;

    public ModelItemClick(int total, boolean itemClick, String title) {
        this.total = total;
        this.itemClick = itemClick;
        this.title = title;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public boolean isItemClick() {
        return itemClick;
    }

    public void setItemClick(boolean itemClick) {
        this.itemClick = itemClick;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

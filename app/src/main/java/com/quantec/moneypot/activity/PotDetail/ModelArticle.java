package com.quantec.moneypot.activity.PotDetail;

public class ModelArticle {

    int total;
    String title;
    String newspaper;
    String date;
    String url;
    String img;
    boolean empty;
    boolean bottom;
    boolean bottomState;

    public ModelArticle(int total, String title, String newspaper, String date, String url, String img, boolean empty, boolean bottom, boolean bottomState) {
        this.total = total;
        this.title = title;
        this.newspaper = newspaper;
        this.date = date;
        this.url = url;
        this.img = img;
        this.empty = empty;
        this.bottom = bottom;
        this.bottomState = bottomState;
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

    public String getNewspaper() {
        return newspaper;
    }

    public void setNewspaper(String newspaper) {
        this.newspaper = newspaper;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public boolean isBottom() {
        return bottom;
    }

    public void setBottom(boolean bottom) {
        this.bottom = bottom;
    }

    public boolean isBottomState() {
        return bottomState;
    }

    public void setBottomState(boolean bottomState) {
        this.bottomState = bottomState;
    }
}

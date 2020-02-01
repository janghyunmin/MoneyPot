package com.quantec.moneypot.datamodel.dmodel;

public class ModelArticle {

    int total;
    String title;
    String newspaper;
    String date;
    String url;
    String img;

    public ModelArticle(int total, String title, String newspaper, String date, String url, String img) {
        this.total = total;
        this.title = title;
        this.newspaper = newspaper;
        this.date = date;
        this.url = url;
        this.img = img;
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
}

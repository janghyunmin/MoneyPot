package com.quantec.moneypot.activity.PotDetail;

public class ModelLikeEnter {

    String img;
    String title;
    String code;
    double rate;

    public ModelLikeEnter(String img, String title, String code, double rate) {
        this.img = img;
        this.title = title;
        this.code = code;
        this.rate = rate;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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
}

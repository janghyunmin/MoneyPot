package com.quantec.moneypot.datamodel.dmodel;

import android.graphics.drawable.Drawable;

public class ModelAllRankingTop3 {

    Drawable image;
    String title;
    double rate;

    public ModelAllRankingTop3(Drawable image, String title, double rate) {
        this.image = image;
        this.title = title;
        this.rate = rate;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}

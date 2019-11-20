package com.quantec.moneypot.activity.Main.Fragment.Tab3;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Core {

    @SerializedName("nowPrice")
    @Expose
    private double nowPrice;
    @SerializedName("period")
    @Expose
    private String period;
    @SerializedName("rate")
    @Expose
    private double rate;
    @SerializedName("minPrice")
    @Expose
    private double minPrice;
    @SerializedName("totPrice")
    @Expose
    private double totPrice;


    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getNowPrice() {
        return nowPrice;
    }

    public void setNowPrice(double nowPrice) {
        this.nowPrice = nowPrice;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public double getTotPrice() {
        return totPrice;
    }

    public void setTotPrice(double totPrice) {
        this.totPrice = totPrice;
    }
}
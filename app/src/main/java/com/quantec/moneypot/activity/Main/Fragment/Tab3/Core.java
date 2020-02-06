package com.quantec.moneypot.activity.Main.Fragment.Tab3;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Core {

    @SerializedName("nowPrice")
    @Expose
    private Integer nowPrice;
    @SerializedName("period")
    @Expose
    private String period;
    @SerializedName("rate")
    @Expose
    private double rate;
    @SerializedName("minPrice")
    @Expose
    private Integer minPrice;
    @SerializedName("totPrice")
    @Expose
    private Integer totPrice;


    public Integer getNowPrice() {
        return nowPrice;
    }

    public void setNowPrice(Integer nowPrice) {
        this.nowPrice = nowPrice;
    }

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

    public Integer getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Integer minPrice) {
        this.minPrice = minPrice;
    }

    public Integer getTotPrice() {
        return totPrice;
    }

    public void setTotPrice(Integer totPrice) {
        this.totPrice = totPrice;
    }
}
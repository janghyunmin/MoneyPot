package com.quantec.moneypot.datamodel.nmodel.customdetail;

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
    private Double rate;
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

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public void setMinPrice(Integer minPrice) {
        this.minPrice = minPrice;
    }

    public void setTotPrice(Integer totPrice) {
        this.totPrice = totPrice;
    }

    public Integer getMinPrice() {
        return minPrice;
    }

    public Integer getTotPrice() {
        return totPrice;
    }
}

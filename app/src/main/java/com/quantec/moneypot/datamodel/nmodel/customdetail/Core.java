package com.quantec.moneypot.datamodel.nmodel.customdetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Core {
    @SerializedName("nowPrice")
    @Expose
    private Double nowPrice;
    @SerializedName("period")
    @Expose
    private String period;
    @SerializedName("rate")
    @Expose
    private Double rate;
    @SerializedName("minPrice")
    @Expose
    private Double minPrice;
    @SerializedName("totPrice")
    @Expose
    private Double totPrice;

    public Double getNowPrice() {
        return nowPrice;
    }

    public void setNowPrice(Double nowPrice) {
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

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Double getTotPrice() {
        return totPrice;
    }

    public void setTotPrice(Double totPrice) {
        this.totPrice = totPrice;
    }
}

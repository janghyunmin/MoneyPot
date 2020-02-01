package com.quantec.moneypot.datamodel.nmodel.customdetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Chart {
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("ln")
    @Expose
    private Double ln;
    @SerializedName("exp")
    @Expose
    private Double exp;
    @SerializedName("price")
    @Expose
    private Double price;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getLn() {
        return ln;
    }

    public void setLn(Double ln) {
        this.ln = ln;
    }

    public Double getExp() {
        return exp;
    }

    public void setExp(Double exp) {
        this.exp = exp;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}

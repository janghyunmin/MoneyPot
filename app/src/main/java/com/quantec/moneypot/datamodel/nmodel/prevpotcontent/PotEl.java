package com.quantec.moneypot.datamodel.nmodel.prevpotcontent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PotEl {

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("elCode")
    @Expose
    private String elCode;
    @SerializedName("elName")
    @Expose
    private String elName;
    @SerializedName("elNum")
    @Expose
    private Object elNum;
    @SerializedName("rate")
    @Expose
    private Double rate;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("weight")
    @Expose
    private Double weight;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getElCode() {
        return elCode;
    }

    public void setElCode(String elCode) {
        this.elCode = elCode;
    }

    public String getElName() {
        return elName;
    }

    public void setElName(String elName) {
        this.elName = elName;
    }

    public Object getElNum() {
        return elNum;
    }

    public void setElNum(Object elNum) {
        this.elNum = elNum;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

}
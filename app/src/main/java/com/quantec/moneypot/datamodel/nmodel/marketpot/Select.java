package com.quantec.moneypot.datamodel.nmodel.marketpot;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Select {

    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("isZim")
    @Expose
    private Boolean isZim;
    @SerializedName("isDam")
    @Expose
    private Boolean isDam;
    @SerializedName("selected")
    @Expose
    private Object selected;
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("name")
    @Expose
    private Object name;
    @SerializedName("descript")
    @Expose
    private Object descript;
    @SerializedName("minPrice")
    @Expose
    private Object minPrice;
    @SerializedName("rate")
    @Expose
    private Double rate;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getIsZim() {
        return isZim;
    }

    public void setIsZim(Boolean isZim) {
        this.isZim = isZim;
    }

    public Boolean getIsDam() {
        return isDam;
    }

    public void setIsDam(Boolean isDam) {
        this.isDam = isDam;
    }

    public Object getSelected() {
        return selected;
    }

    public void setSelected(Object selected) {
        this.selected = selected;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public Object getDescript() {
        return descript;
    }

    public void setDescript(Object descript) {
        this.descript = descript;
    }

    public Object getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Object minPrice) {
        this.minPrice = minPrice;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

}
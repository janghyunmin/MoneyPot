package com.quantec.moneypot.datamodel.nmodel.customdel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Content {

    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("rate")
    @Expose
    private Double rate;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("els")
    @Expose
    private Object els;
    @SerializedName("files")
    @Expose
    private Object files;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Object getEls() {
        return els;
    }

    public void setEls(Object els) {
        this.els = els;
    }

    public Object getFiles() {
        return files;
    }

    public void setFiles(Object files) {
        this.files = files;
    }
}

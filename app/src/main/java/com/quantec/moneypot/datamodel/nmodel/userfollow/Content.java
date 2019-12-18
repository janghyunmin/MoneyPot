package com.quantec.moneypot.datamodel.nmodel.userfollow;

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
    @SerializedName("descript")
    @Expose
    private String descript;
    @SerializedName("rate")
    @Expose
    private Double rate;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("userSelect")
    @Expose
    private UserSelect userSelect;
    @SerializedName("els")
    @Expose
    private Object els;

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

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
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

    public UserSelect getUserSelect() {
        return userSelect;
    }

    public void setUserSelect(UserSelect userSelect) {
        this.userSelect = userSelect;
    }

    public Object getEls() {
        return els;
    }

    public void setEls(Object els) {
        this.els = els;
    }
}

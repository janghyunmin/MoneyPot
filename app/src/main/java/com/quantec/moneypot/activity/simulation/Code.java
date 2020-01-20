package com.quantec.moneypot.activity.simulation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Code {
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("ptCode")
    @Expose
    private String ptCode;
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("weight")
    @Expose
    private Integer weight;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPtCode() {
        return ptCode;
    }

    public void setPtCode(String ptCode) {
        this.ptCode = ptCode;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}

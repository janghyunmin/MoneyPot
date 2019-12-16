package com.quantec.moneypot.activity.Main.Fragment.Tab3.aaa;


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
    private float weight;

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

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }
}
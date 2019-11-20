package com.quantec.moneypot.activity.Main.Fragment.Tab3.aaa;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Code {

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("type")
    @Expose
    private Integer type;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}
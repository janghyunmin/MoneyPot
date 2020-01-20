package com.quantec.moneypot.activity.Main.Fragment.Tab2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelModifyCustom {
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("descript")
    @Expose
    private String descript;
    @SerializedName("name")
    @Expose
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

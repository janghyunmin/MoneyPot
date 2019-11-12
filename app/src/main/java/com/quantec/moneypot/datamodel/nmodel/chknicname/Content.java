package com.quantec.moneypot.datamodel.nmodel.chknicname;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Content {

    @SerializedName("isOk")
    @Expose
    private Boolean isOk;

    public Boolean getIsOk() {
        return isOk;
    }

    public void setIsOk(Boolean isOk) {
        this.isOk = isOk;
    }

}
package com.quantec.moneypot.activity.propensity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelSetPropensity {
    @SerializedName("propensity")
    @Expose
    private String propensity;

    public String getPropensity() {
        return propensity;
    }

    public void setPropensity(String propensity) {
        this.propensity = propensity;
    }
}

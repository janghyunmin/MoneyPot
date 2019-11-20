package com.quantec.moneypot.activity.Main.Fragment.Tab3.aaa;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Ex {

    @SerializedName("codes")
    @Expose
    private List<Code> codes = null;
    @SerializedName("period")
    @Expose
    private String period;
    @SerializedName("propensity")
    @Expose
    private Integer propensity;

    public List<Code> getCodes() {
        return codes;
    }

    public void setCodes(List<Code> codes) {
        this.codes = codes;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Integer getPropensity() {
        return propensity;
    }

    public void setPropensity(Integer propensity) {
        this.propensity = propensity;
    }

}

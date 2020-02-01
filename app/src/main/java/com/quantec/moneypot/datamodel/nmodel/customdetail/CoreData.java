package com.quantec.moneypot.datamodel.nmodel.customdetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CoreData {
    @SerializedName("propensity")
    @Expose
    private Integer propensity;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("core")
    @Expose
    private Core core;
    @SerializedName("chart")
    @Expose
    private List<Chart> chart = null;

    public Integer getPropensity() {
        return propensity;
    }

    public void setPropensity(Integer propensity) {
        this.propensity = propensity;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Core getCore() {
        return core;
    }

    public void setCore(Core core) {
        this.core = core;
    }

    public List<Chart> getChart() {
        return chart;
    }

    public void setChart(List<Chart> chart) {
        this.chart = chart;
    }

}

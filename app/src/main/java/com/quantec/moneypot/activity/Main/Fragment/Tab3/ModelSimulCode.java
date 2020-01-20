package com.quantec.moneypot.activity.Main.Fragment.Tab3;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.quantec.moneypot.activity.simulation.Code;
//import com.quantec.moneypot.activity.Main.Fragment.Tab3.aaa.Code;

import java.util.ArrayList;
import java.util.List;

public class ModelSimulCode {

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("codes")
    @Expose
    private List<Code> codes = new ArrayList<>();
    @SerializedName("descript")
    @Expose
    private String descript;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("period")
    @Expose
    private String period;
    @SerializedName("propensity")
    @Expose
    private Integer propensity;
    @SerializedName("rate")
    @Expose
    private Integer rate;
    @SerializedName("type")
    @Expose
    private Integer type;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Code> getCodes() {
        return codes;
    }

    public void setCodes(List<Code> codes) {
        this.codes = codes;
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

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}

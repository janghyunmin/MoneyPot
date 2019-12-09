package com.quantec.moneypot.datamodel.nmodel.seardb;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Content {

//    @SerializedName("codes")
//    @Expose
//    private Codes codes;
//    @SerializedName("userSelects")
//    @Expose
//    private List<UserSelect> userSelects = null;
//
//    public Codes getCodes() {
//        return codes;
//    }
//
//    public void setCodes(Codes codes) {
//        this.codes = codes;
//    }
//
//    public List<UserSelect> getUserSelects() {
//        return userSelects;
//    }
//
//    public void setUserSelects(List<UserSelect> userSelects) {
//        this.userSelects = userSelects;
//    }

    @SerializedName("codes")
    @Expose
    private Codes codes;
    @SerializedName("rateList")
    @Expose
    private List<RateList> rateList = null;
    @SerializedName("userSelect")
    @Expose
    private List<Object> userSelect = null;

    public Codes getCodes() {
        return codes;
    }

    public void setCodes(Codes codes) {
        this.codes = codes;
    }

    public List<RateList> getRateList() {
        return rateList;
    }

    public void setRateList(List<RateList> rateList) {
        this.rateList = rateList;
    }

    public List<Object> getUserSelect() {
        return userSelect;
    }

    public void setUserSelect(List<Object> userSelect) {
        this.userSelect = userSelect;
    }
}
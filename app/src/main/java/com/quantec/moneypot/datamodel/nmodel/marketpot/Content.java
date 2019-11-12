package com.quantec.moneypot.datamodel.nmodel.marketpot;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Content {

    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("investType")
    @Expose
    private Integer investType;
    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("descript")
    @Expose
    private String descript;
    @SerializedName("minPrice")
    @Expose
    private Integer minPrice;
    @SerializedName("elNum")
    @Expose
    private Integer elNum;
    @SerializedName("ivNum")
    @Expose
    private Object ivNum;
    @SerializedName("rate")
    @Expose
    private Double rate;
    @SerializedName("rateOne")
    @Expose
    private Double rateOne;
    @SerializedName("rateThr")
    @Expose
    private Double rateThr;
    @SerializedName("rateSix")
    @Expose
    private Double rateSix;
    @SerializedName("active")
    @Expose
    private Integer active;
    @SerializedName("view")
    @Expose
    private Integer view;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("potEls")
    @Expose
    private Object potEls;
    @SerializedName("codes")
    @Expose
    private Object codes;
    @SerializedName("file")
    @Expose
    private Object file;
    @SerializedName("select")
    @Expose
    private Select select;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getInvestType() {
        return investType;
    }

    public void setInvestType(Integer investType) {
        this.investType = investType;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public Integer getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Integer minPrice) {
        this.minPrice = minPrice;
    }

    public Integer getElNum() {
        return elNum;
    }

    public void setElNum(Integer elNum) {
        this.elNum = elNum;
    }

    public Object getIvNum() {
        return ivNum;
    }

    public void setIvNum(Object ivNum) {
        this.ivNum = ivNum;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Double getRateOne() {
        return rateOne;
    }

    public void setRateOne(Double rateOne) {
        this.rateOne = rateOne;
    }

    public Double getRateThr() {
        return rateThr;
    }

    public void setRateThr(Double rateThr) {
        this.rateThr = rateThr;
    }

    public Double getRateSix() {
        return rateSix;
    }

    public void setRateSix(Double rateSix) {
        this.rateSix = rateSix;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Integer getView() {
        return view;
    }

    public void setView(Integer view) {
        this.view = view;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Object getPotEls() {
        return potEls;
    }

    public void setPotEls(Object potEls) {
        this.potEls = potEls;
    }

    public Object getCodes() {
        return codes;
    }

    public void setCodes(Object codes) {
        this.codes = codes;
    }

    public Object getFile() {
        return file;
    }

    public void setFile(Object file) {
        this.file = file;
    }

    public Select getSelect() {
        return select;
    }

    public void setSelect(Select select) {
        this.select = select;
    }

}
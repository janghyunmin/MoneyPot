package com.quantec.moneypot.datamodel.nmodel.lifelist;

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
    @SerializedName("hopePrice")
    @Expose
    private Integer hopePrice;
    @SerializedName("nowPrice")
    @Expose
    private Integer nowPrice;
    @SerializedName("monPrice")
    @Expose
    private Integer monPrice;
    @SerializedName("lifeYear")
    @Expose
    private Integer lifeYear;
    @SerializedName("isIv")
    @Expose
    private Boolean isIv;
    @SerializedName("lcCode")
    @Expose
    private String lcCode;
    @SerializedName("ivCode")
    @Expose
    private Object ivCode;
    @SerializedName("active")
    @Expose
    private Integer active;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("upDate")
    @Expose
    private String upDate;
    @SerializedName("file")
    @Expose
    private File file;
    @SerializedName("pname")
    @Expose
    private String pname;
    @SerializedName("pdescript")
    @Expose
    private String pdescript;
    @SerializedName("prate")
    @Expose
    private Double prate;

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

    public Integer getHopePrice() {
        return hopePrice;
    }

    public void setHopePrice(Integer hopePrice) {
        this.hopePrice = hopePrice;
    }

    public Integer getNowPrice() {
        return nowPrice;
    }

    public void setNowPrice(Integer nowPrice) {
        this.nowPrice = nowPrice;
    }

    public Integer getMonPrice() {
        return monPrice;
    }

    public void setMonPrice(Integer monPrice) {
        this.monPrice = monPrice;
    }

    public Integer getLifeYear() {
        return lifeYear;
    }

    public void setLifeYear(Integer lifeYear) {
        this.lifeYear = lifeYear;
    }

    public Boolean getIsIv() {
        return isIv;
    }

    public void setIsIv(Boolean isIv) {
        this.isIv = isIv;
    }

    public String getLcCode() {
        return lcCode;
    }

    public void setLcCode(String lcCode) {
        this.lcCode = lcCode;
    }

    public Object getIvCode() {
        return ivCode;
    }

    public void setIvCode(Object ivCode) {
        this.ivCode = ivCode;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUpDate() {
        return upDate;
    }

    public void setUpDate(String upDate) {
        this.upDate = upDate;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPdescript() {
        return pdescript;
    }

    public void setPdescript(String pdescript) {
        this.pdescript = pdescript;
    }

    public Double getPrate() {
        return prate;
    }

    public void setPrate(Double prate) {
        this.prate = prate;
    }
}

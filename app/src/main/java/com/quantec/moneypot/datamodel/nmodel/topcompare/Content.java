package com.quantec.moneypot.datamodel.nmodel.topcompare;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Content {
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("descript")
    @Expose
    private String descript;
    @SerializedName("descriptEtc")
    @Expose
    private String descriptEtc;
    @SerializedName("rate")
    @Expose
    private Double rate;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("minPrice")
    @Expose
    private Double minPrice;
    @SerializedName("elCodes")
    @Expose
    private Object elCodes;
    @SerializedName("files")
    @Expose
    private List<File> files = null;
    @SerializedName("userSelect")
    @Expose
    private Object userSelect;
    @SerializedName("searchField")
    @Expose
    private Object searchField;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public String getDescriptEtc() {
        return descriptEtc;
    }

    public void setDescriptEtc(String descriptEtc) {
        this.descriptEtc = descriptEtc;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Object getElCodes() {
        return elCodes;
    }

    public void setElCodes(Object elCodes) {
        this.elCodes = elCodes;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public Object getUserSelect() {
        return userSelect;
    }

    public void setUserSelect(Object userSelect) {
        this.userSelect = userSelect;
    }

    public Object getSearchField() {
        return searchField;
    }

    public void setSearchField(Object searchField) {
        this.searchField = searchField;
    }
}

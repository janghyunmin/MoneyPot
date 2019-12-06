package com.quantec.moneypot.datamodel.nmodel.seardb;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RateList {

    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("rate")
    @Expose
    private Double rate;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("file")
    @Expose
    private File file;
    @SerializedName("userSelect")
    @Expose
    private Object userSelect;
    @SerializedName("searchField")
    @Expose
    private List<String> searchField = null;

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

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Object getUserSelect() {
        return userSelect;
    }

    public void setUserSelect(Object userSelect) {
        this.userSelect = userSelect;
    }

    public List<String> getSearchField() {
        return searchField;
    }

    public void setSearchField(List<String> searchField) {
        this.searchField = searchField;
    }
}

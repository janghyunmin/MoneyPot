package com.quantec.moneypot.datamodel.nmodel.seardb;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserSelect {

    @SerializedName("isZim")
    @Expose
    private Integer isZim;
    @SerializedName("isDam")
    @Expose
    private Integer isDam;
    @SerializedName("isLike")
    @Expose
    private Integer isLike;
    @SerializedName("isFollow")
    @Expose
    private Integer isFollow;

    public Integer getIsZim() {
        return isZim;
    }

    public void setIsZim(Integer isZim) {
        this.isZim = isZim;
    }

    public Integer getIsDam() {
        return isDam;
    }

    public void setIsDam(Integer isDam) {
        this.isDam = isDam;
    }

    public Integer getIsLike() {
        return isLike;
    }

    public void setIsLike(Integer isLike) {
        this.isLike = isLike;
    }

    public Integer getIsFollow() {
        return isFollow;
    }

    public void setIsFollow(Integer isFollow) {
        this.isFollow = isFollow;
    }


//    @SerializedName("type")
//    @Expose
//    private Integer type;
//    @SerializedName("code")
//    @Expose
//    private String code;
//    @SerializedName("name")
//    @Expose
//    private String name;
//    @SerializedName("descript")
//    @Expose
//    private String descript;
//    @SerializedName("rate")
//    @Expose
//    private Double rate;
//    @SerializedName("price")
//    @Expose
//    private Double price;
//    @SerializedName("userSelect")
//    @Expose
//    private Object userSelect;
//    @SerializedName("els")
//    @Expose
//    private Object els;
//    @SerializedName("searchField")
//    @Expose
//    private List<String> searchField = null;
//    @SerializedName("elCodes")
//    @Expose
//    private Object elCodes;
//    @SerializedName("file")
//    @Expose
//    private File file;
//
//    public Integer getType() {
//        return type;
//    }
//
//    public void setType(Integer type) {
//        this.type = type;
//    }
//
//    public String getCode() {
//        return code;
//    }
//
//    public void setCode(String code) {
//        this.code = code;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getDescript() {
//        return descript;
//    }
//
//    public void setDescript(String descript) {
//        this.descript = descript;
//    }
//
//    public Double getRate() {
//        return rate;
//    }
//
//    public void setRate(Double rate) {
//        this.rate = rate;
//    }
//
//    public Double getPrice() {
//        return price;
//    }
//
//    public void setPrice(Double price) {
//        this.price = price;
//    }
//
//    public Object getUserSelect() {
//        return userSelect;
//    }
//
//    public void setUserSelect(Object userSelect) {
//        this.userSelect = userSelect;
//    }
//
//    public Object getEls() {
//        return els;
//    }
//
//    public void setEls(Object els) {
//        this.els = els;
//    }
//
//    public List<String> getSearchField() {
//        return searchField;
//    }
//
//    public void setSearchField(List<String> searchField) {
//        this.searchField = searchField;
//    }
//
//    public Object getElCodes() {
//        return elCodes;
//    }
//
//    public void setElCodes(Object elCodes) {
//        this.elCodes = elCodes;
//    }
//
//    public File getFile() {
//        return file;
//    }
//
//    public void setFile(File file) {
//        this.file = file;
//    }
}

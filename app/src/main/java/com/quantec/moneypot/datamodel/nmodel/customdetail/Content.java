package com.quantec.moneypot.datamodel.nmodel.customdetail;

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
    @SerializedName("newsPlus")
    @Expose
    private Integer newsPlus;
    @SerializedName("newsMinus")
    @Expose
    private Integer newsMinus;
    @SerializedName("tags")
    @Expose
    private List<Tag> tags = null;
    @SerializedName("userSelect")
    @Expose
    private UserSelect userSelect;
    @SerializedName("coreData")
    @Expose
    private CoreData coreData;
    @SerializedName("news")
    @Expose
    private News news;

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

    public Integer getNewsPlus() {
        return newsPlus;
    }

    public void setNewsPlus(Integer newsPlus) {
        this.newsPlus = newsPlus;
    }

    public Integer getNewsMinus() {
        return newsMinus;
    }

    public void setNewsMinus(Integer newsMinus) {
        this.newsMinus = newsMinus;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public UserSelect getUserSelect() {
        return userSelect;
    }

    public void setUserSelect(UserSelect userSelect) {
        this.userSelect = userSelect;
    }

    public CoreData getCoreData() {
        return coreData;
    }

    public void setCoreData(CoreData coreData) {
        this.coreData = coreData;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }
}

package com.quantec.moneypot.datamodel.nmodel.article;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Content {
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("newsData")
    @Expose
    private List<NewsData> newsData = null;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<NewsData> getNewsData() {
        return newsData;
    }

    public void setNewsData(List<NewsData> newsData) {
        this.newsData = newsData;
    }
}

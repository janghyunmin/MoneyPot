package com.quantec.moneypot.datamodel.nmodel.article;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewsData {
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("from")
    @Expose
    private String from;
    @SerializedName("newsUrl")
    @Expose
    private String newsUrl;
    @SerializedName("imgUrl")
    @Expose
    private String imgUrl;
    @SerializedName("date")
    @Expose
    private String date;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}

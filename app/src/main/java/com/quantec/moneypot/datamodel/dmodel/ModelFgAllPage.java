package com.quantec.moneypot.datamodel.dmodel;

public class ModelFgAllPage {

    boolean empty;
    String title;
    String code;
    double rate;
    boolean isZim;
    boolean isDam;
    String image;
    boolean onenChart;
    Long mincost;

    String filterText;

    public ModelFgAllPage(boolean empty, String title, String code, double rate, boolean isZim, boolean isDam, String image, boolean onenChart, Long mincost, String filterText) {
        this.empty = empty;
        this.title = title;
        this.code = code;
        this.rate = rate;
        this.isZim = isZim;
        this.isDam = isDam;
        this.image = image;
        this.onenChart = onenChart;
        this.mincost = mincost;
        this.filterText = filterText;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public boolean isZim() {
        return isZim;
    }

    public void setZim(boolean zim) {
        isZim = zim;
    }

    public boolean isDam() {
        return isDam;
    }

    public void setDam(boolean dam) {
        isDam = dam;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isOnenChart() {
        return onenChart;
    }

    public void setOnenChart(boolean onenChart) {
        this.onenChart = onenChart;
    }

    public Long getMincost() {
        return mincost;
    }

    public void setMincost(Long mincost) {
        this.mincost = mincost;
    }

    public String getFilterText() {
        return filterText;
    }

    public void setFilterText(String filterText) {
        this.filterText = filterText;
    }
}

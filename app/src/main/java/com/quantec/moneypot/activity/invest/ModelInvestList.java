package com.quantec.moneypot.activity.invest;

public class ModelInvestList {

    String title;
    String code;
    double rate;
    String ratio;

    public ModelInvestList(String title, String code, double rate, String ratio) {
        this.title = title;
        this.code = code;
        this.rate = rate;
        this.ratio = ratio;
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

    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }
}

package com.quantec.moneypot.activity.invest;

public class ModelTrDetail {

    String num;
    String title;
    String code;
    String ratio;

    public ModelTrDetail(String num, String title, String code, String ratio) {
        this.num = num;
        this.title = title;
        this.code = code;
        this.ratio = ratio;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
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

    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }
}

package com.quantec.moneypot.activity.Main.Fragment.Tab1;

import java.util.ArrayList;

public class ModelYieldChart {

    int type;

    String title1;
    String title2;
    String title3;

    String code1;
    String code2;
    String code3;

    double rate1;
    double rate2;
    double rate3;

    int price1;
    int price2;
    int price3;

    public ModelYieldChart(int type, String title1, String title2, String title3, String code1, String code2, String code3, double rate1, double rate2, double rate3, int price1, int price2, int price3) {
        this.type = type;
        this.title1 = title1;
        this.title2 = title2;
        this.title3 = title3;
        this.code1 = code1;
        this.code2 = code2;
        this.code3 = code3;
        this.rate1 = rate1;
        this.rate2 = rate2;
        this.rate3 = rate3;
        this.price1 = price1;
        this.price2 = price2;
        this.price3 = price3;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle1() {
        return title1;
    }

    public void setTitle1(String title1) {
        this.title1 = title1;
    }

    public String getTitle2() {
        return title2;
    }

    public void setTitle2(String title2) {
        this.title2 = title2;
    }

    public String getTitle3() {
        return title3;
    }

    public void setTitle3(String title3) {
        this.title3 = title3;
    }

    public String getCode1() {
        return code1;
    }

    public void setCode1(String code1) {
        this.code1 = code1;
    }

    public String getCode2() {
        return code2;
    }

    public void setCode2(String code2) {
        this.code2 = code2;
    }

    public String getCode3() {
        return code3;
    }

    public void setCode3(String code3) {
        this.code3 = code3;
    }

    public double getRate1() {
        return rate1;
    }

    public void setRate1(double rate1) {
        this.rate1 = rate1;
    }

    public double getRate2() {
        return rate2;
    }

    public void setRate2(double rate2) {
        this.rate2 = rate2;
    }

    public double getRate3() {
        return rate3;
    }

    public void setRate3(double rate3) {
        this.rate3 = rate3;
    }

    public int getPrice1() {
        return price1;
    }

    public void setPrice1(int price1) {
        this.price1 = price1;
    }

    public int getPrice2() {
        return price2;
    }

    public void setPrice2(int price2) {
        this.price2 = price2;
    }

    public int getPrice3() {
        return price3;
    }

    public void setPrice3(int price3) {
        this.price3 = price3;
    }
}

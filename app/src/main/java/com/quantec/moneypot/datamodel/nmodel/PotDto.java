package com.quantec.moneypot.datamodel.nmodel;

import java.util.ArrayList;

public class PotDto {

    ArrayList<String> codes;
    int investType;
    Long minPrice;
    int type;

    public PotDto(ArrayList<String> codes, int investType, Long minPrice, int type) {
        this.codes = codes;
        this.investType = investType;
        this.minPrice = minPrice;
        this.type = type;
    }

    public ArrayList<String> getCodes() {
        return codes;
    }

    public void setCodes(ArrayList<String> codes) {
        this.codes = codes;
    }

    public int getInvestType() {
        return investType;
    }

    public void setInvestType(int investType) {
        this.investType = investType;
    }

    public Long getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Long minPrice) {
        this.minPrice = minPrice;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "{" +
                "codes=" + codes +
                ", investType=" + investType +
                ", minPrice=" + minPrice +
                ", type=" + type +
                '}';
    }

    //    public PotDto(ArrayList<String> codes, int investType, Long minPrice) {
//        this.codes = codes;
//        this.investType = investType;
//        this.minPrice = minPrice;
//    }
//
//    public ArrayList<String> getCodes() {
//        return codes;
//    }
//
//    public void setCodes(ArrayList<String> codes) {
//        this.codes = codes;
//    }
//
//    public int getInvestType() {
//        return investType;
//    }
//
//    public void setInvestType(int investType) {
//        this.investType = investType;
//    }
//
//    public Long getMinPrice() {
//        return minPrice;
//    }
//
//    public void setMinPrice(Long minPrice) {
//        this.minPrice = minPrice;
//    }
//
//    @Override
//    public String toString() {
//        return "{" +
//                "codes=" + codes +
//                ", investType=" + investType +
//                ", minPrice=" + minPrice +
//                "}";
//    }
}

package com.quantec.moneypot.datamodel.nmodel.accounts;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Content {

    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("account")
    @Expose
    private String account;
    @SerializedName("valance")
    @Expose
    private Integer valance;
    @SerializedName("partner")
    @Expose
    private Integer partner;
    @SerializedName("partnerName")
    @Expose
    private String partnerName;
    @SerializedName("rate")
    @Expose
    private Object rate;
    @SerializedName("date")
    @Expose
    private String date;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getValance() {
        return valance;
    }

    public void setValance(Integer valance) {
        this.valance = valance;
    }

    public Integer getPartner() {
        return partner;
    }

    public void setPartner(Integer partner) {
        this.partner = partner;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public Object getRate() {
        return rate;
    }

    public void setRate(Object rate) {
        this.rate = rate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}

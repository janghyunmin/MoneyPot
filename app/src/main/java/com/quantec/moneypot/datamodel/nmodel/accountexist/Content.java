package com.quantec.moneypot.datamodel.nmodel.accountexist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Content {

    @SerializedName("resultCode")
    @Expose
    private String resultCode;
    @SerializedName("account")
    @Expose
    private String account;
    @SerializedName("encData")
    @Expose
    private String encData;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getEncData() {
        return encData;
    }

    public void setEncData(String encData) {
        this.encData = encData;
    }

}

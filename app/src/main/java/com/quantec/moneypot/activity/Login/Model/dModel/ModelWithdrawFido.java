package com.quantec.moneypot.activity.Login.Model.dModel;

public class ModelWithdrawFido {

    String authCode;
    String site;
    String uid;

    public ModelWithdrawFido(String authCode, String site, String uid) {
        this.authCode = authCode;
        this.site = site;
        this.uid = uid;
    }
}

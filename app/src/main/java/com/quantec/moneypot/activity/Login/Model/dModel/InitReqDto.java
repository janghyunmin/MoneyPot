package com.quantec.moneypot.activity.Login.Model.dModel;

public class InitReqDto {

    String authCode;
    String cid;
    String token;
    String uid;

    public InitReqDto(String authCode, String cid, String token, String uid) {
        this.authCode = authCode;
        this.cid = cid;
        this.token = token;
        this.uid = uid;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}

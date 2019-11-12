package com.quantec.moneypot.activity.Login.Model.dModel;

public class UserInfo {


    int activeStep;
    String authCode;
    String cid;
    String email;
    String hpNumber;
    String password;
    String uid;
    String userName;

    public UserInfo(int activeStep, String authCode, String cid, String email, String hpNumber, String password, String uid, String userName) {
        this.activeStep = activeStep;
        this.authCode = authCode;
        this.cid = cid;
        this.email = email;
        this.hpNumber = hpNumber;
        this.password = password;
        this.uid = uid;
        this.userName = userName;
    }

    public int getActiveStep() {
        return activeStep;
    }

    public void setActiveStep(int activeStep) {
        this.activeStep = activeStep;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHpNumber() {
        return hpNumber;
    }

    public void setHpNumber(String hpNumber) {
        this.hpNumber = hpNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    //    String hpNumber;
//    String nickName;
//    String password;
//    String userId;
//    String userName;
//
//    public UserInfo(String hpNumber, String nickName, String password, String userId, String userName) {
//        this.hpNumber = hpNumber;
//        this.nickName = nickName;
//        this.password = password;
//        this.userId = userId;
//        this.userName = userName;
//    }
//
//    public String getHpNumber() {
//        return hpNumber;
//    }
//
//    public void setHpNumber(String hpNumber) {
//        this.hpNumber = hpNumber;
//    }
//
//    public String getNickName() {
//        return nickName;
//    }
//
//    public void setNickName(String nickName) {
//        this.nickName = nickName;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getUserId() {
//        return userId;
//    }
//
//    public void setUserId(String userId) {
//        this.userId = userId;
//    }
//
//    public String getUserName() {
//        return userName;
//    }
//
//    public void setUserName(String userName) {
//        this.userName = userName;
//    }
}

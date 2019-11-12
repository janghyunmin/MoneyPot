package com.quantec.moneypot.activity.Login.Model.dModel;

public class IdentifyDto {

   String birth;
   String ci;
   String code;
   String di;
   String gender;
   String inputCode;
   String message;
   String mobileNum;
   String name;
   String nation;
   String reqNum;
   String telco;
   String type;
   String uid;

    public IdentifyDto(String birth, String ci, String code, String di, String gender, String inputCode, String message, String mobileNum, String name, String nation, String reqNum, String telco, String type, String uid) {
        this.birth = birth;
        this.ci = ci;
        this.code = code;
        this.di = di;
        this.gender = gender;
        this.inputCode = inputCode;
        this.message = message;
        this.mobileNum = mobileNum;
        this.name = name;
        this.nation = nation;
        this.reqNum = reqNum;
        this.telco = telco;
        this.type = type;
        this.uid = uid;
    }
}

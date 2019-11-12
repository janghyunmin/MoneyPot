package com.quantec.moneypot.database.realm.accountdata;

import io.realm.RealmObject;

public class AccountDetail extends RealmObject {

    String account;
    double valance;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }


    public double getValance() {
        return valance;
    }

    public void setValance(double valance) {
        this.valance = valance;
    }
}

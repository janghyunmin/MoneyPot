package com.quantec.moneypot.datamodel.nmodel.seardb;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Codes {

    @SerializedName("PARTNER")
    @Expose
    private List<PARTNER> pARTNER = null;
    @SerializedName("INCOME")
    @Expose
    private List<INCOME> iNCOME = null;
    @SerializedName("ISWEAK")
    @Expose
    private List<ISWEAK> iSWEAK = null;
    @SerializedName("PURPOSE")
    @Expose
    private List<PURPOSE> pURPOSE = null;
    @SerializedName("WEIGHT")
    @Expose
    private List<WEIGHT> wEIGHT = null;
    @SerializedName("EXP")
    @Expose
    private List<EXP> eXP = null;
    @SerializedName("TYPE")
    @Expose
    private List<TYPE> tYPE = null;
    @SerializedName("STOCK")
    @Expose
    private List<STOCK> sTOCK = null;

    public List<PARTNER> getPARTNER() {
        return pARTNER;
    }

    public void setPARTNER(List<PARTNER> pARTNER) {
        this.pARTNER = pARTNER;
    }

    public List<INCOME> getINCOME() {
        return iNCOME;
    }

    public void setINCOME(List<INCOME> iNCOME) {
        this.iNCOME = iNCOME;
    }

    public List<ISWEAK> getISWEAK() {
        return iSWEAK;
    }

    public void setISWEAK(List<ISWEAK> iSWEAK) {
        this.iSWEAK = iSWEAK;
    }

    public List<PURPOSE> getPURPOSE() {
        return pURPOSE;
    }

    public void setPURPOSE(List<PURPOSE> pURPOSE) {
        this.pURPOSE = pURPOSE;
    }

    public List<WEIGHT> getWEIGHT() {
        return wEIGHT;
    }

    public void setWEIGHT(List<WEIGHT> wEIGHT) {
        this.wEIGHT = wEIGHT;
    }

    public List<EXP> getEXP() {
        return eXP;
    }

    public void setEXP(List<EXP> eXP) {
        this.eXP = eXP;
    }

    public List<TYPE> getTYPE() {
        return tYPE;
    }

    public void setTYPE(List<TYPE> tYPE) {
        this.tYPE = tYPE;
    }

    public List<STOCK> getSTOCK() {
        return sTOCK;
    }

    public void setSTOCK(List<STOCK> sTOCK) {
        this.sTOCK = sTOCK;
    }
}

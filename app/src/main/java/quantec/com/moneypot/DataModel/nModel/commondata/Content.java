package quantec.com.moneypot.DataModel.nModel.commondata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Content {

    @SerializedName("PARTNER")
    @Expose
    private List<PARTNER> pARTNER = null;
    @SerializedName("INCOME")
    @Expose
    private List<INCOME> iNCOME = null;
    @SerializedName("PURPOSE")
    @Expose
    private List<PURPOSE> pURPOSE = null;
    @SerializedName("TIME")
    @Expose
    private List<TIME> tIME = null;
    @SerializedName("WEIGHT")
    @Expose
    private List<WEIGHT> wEIGHT = null;
    @SerializedName("EXP")
    @Expose
    private List<EXP> eXP = null;
    @SerializedName("TYPE")
    @Expose
    private List<TYPE> tYPE = null;
    @SerializedName("GAIN")
    @Expose
    private List<GAIN> gAIN = null;
    @SerializedName("AGE")
    @Expose
    private List<AGE> aGE = null;

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

    public List<PURPOSE> getPURPOSE() {
        return pURPOSE;
    }

    public void setPURPOSE(List<PURPOSE> pURPOSE) {
        this.pURPOSE = pURPOSE;
    }

    public List<TIME> getTIME() {
        return tIME;
    }

    public void setTIME(List<TIME> tIME) {
        this.tIME = tIME;
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

    public List<GAIN> getGAIN() {
        return gAIN;
    }

    public void setGAIN(List<GAIN> gAIN) {
        this.gAIN = gAIN;
    }

    public List<AGE> getAGE() {
        return aGE;
    }

    public void setAGE(List<AGE> aGE) {
        this.aGE = aGE;
    }

}

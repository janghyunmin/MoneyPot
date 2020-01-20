package com.quantec.moneypot.activity.Main.Fragment.Tab2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelCustomDel {
    @SerializedName("codes")
    @Expose
    private List<String> codes = null;

    public List<String> getCodes() {
        return codes;
    }

    public void setCodes(List<String> codes) {
        this.codes = codes;
    }
}

package com.quantec.moneypot.datamodel.dmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import com.quantec.moneypot.datamodel.dmodel.userselectdto.Select;

public class ModelUserSelectDto {
    @SerializedName("selects")
    @Expose
    private List<Select> selects = null;

    public List<Select> getSelects() {
        return selects;
    }

    public void setSelects(List<Select> selects) {
        this.selects = selects;
    }
}

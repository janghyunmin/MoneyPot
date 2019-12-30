package com.quantec.moneypot.datamodel.dmodel.userselectdto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Select {

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("isDam")
    @Expose
    private Integer isDam;
    @SerializedName("isFollow")
    @Expose
    private Integer isFollow;
    @SerializedName("isLike")
    @Expose
    private Integer isLike;
    @SerializedName("isZim")
    @Expose
    private Integer isZim;
    @SerializedName("type")
    @Expose
    private Integer type;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getIsDam() {
        return isDam;
    }

    public void setIsDam(Integer isDam) {
        this.isDam = isDam;
    }

    public Integer getIsFollow() {
        return isFollow;
    }

    public void setIsFollow(Integer isFollow) {
        this.isFollow = isFollow;
    }

    public Integer getIsLike() {
        return isLike;
    }

    public void setIsLike(Integer isLike) {
        this.isLike = isLike;
    }

    public Integer getIsZim() {
        return isZim;
    }

    public void setIsZim(Integer isZim) {
        this.isZim = isZim;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}

package com.quantec.moneypot.datamodel.nmodel.customdetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserSelect {
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("isZim")
    @Expose
    private Integer isZim;
    @SerializedName("isDam")
    @Expose
    private Integer isDam;
    @SerializedName("isLike")
    @Expose
    private Integer isLike;
    @SerializedName("isFollow")
    @Expose
    private Integer isFollow;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getIsZim() {
        return isZim;
    }

    public void setIsZim(Integer isZim) {
        this.isZim = isZim;
    }

    public Integer getIsDam() {
        return isDam;
    }

    public void setIsDam(Integer isDam) {
        this.isDam = isDam;
    }

    public Integer getIsLike() {
        return isLike;
    }

    public void setIsLike(Integer isLike) {
        this.isLike = isLike;
    }

    public Integer getIsFollow() {
        return isFollow;
    }

    public void setIsFollow(Integer isFollow) {
        this.isFollow = isFollow;
    }
}

package quantec.com.moneypot.DataModel.nModel.zimpotlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Content {

    @SerializedName("uid")
    @Expose
    private Object uid;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("isZim")
    @Expose
    private Boolean isZim;
    @SerializedName("isDam")
    @Expose
    private Boolean isDam;
    @SerializedName("selected")
    @Expose
    private Object selected;
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("descript")
    @Expose
    private String descript;
    @SerializedName("minPrice")
    @Expose
    private Integer minPrice;
    @SerializedName("rate")
    @Expose
    private Double rate;

    public Object getUid() {
        return uid;
    }

    public void setUid(Object uid) {
        this.uid = uid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getIsZim() {
        return isZim;
    }

    public void setIsZim(Boolean isZim) {
        this.isZim = isZim;
    }

    public Boolean getIsDam() {
        return isDam;
    }

    public void setIsDam(Boolean isDam) {
        this.isDam = isDam;
    }

    public Object getSelected() {
        return selected;
    }

    public void setSelected(Object selected) {
        this.selected = selected;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public Integer getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Integer minPrice) {
        this.minPrice = minPrice;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

}

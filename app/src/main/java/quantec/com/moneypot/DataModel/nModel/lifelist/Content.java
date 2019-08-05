package quantec.com.moneypot.DataModel.nModel.lifelist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Content {

    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("investType")
    @Expose
    private Object investType;
    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("descript")
    @Expose
    private String descript;
    @SerializedName("minPrice")
    @Expose
    private Object minPrice;
    @SerializedName("hopePrice")
    @Expose
    private Object hopePrice;
    @SerializedName("nowPrice")
    @Expose
    private Object nowPrice;
    @SerializedName("monPrice")
    @Expose
    private Object monPrice;
    @SerializedName("lifeYear")
    @Expose
    private Object lifeYear;
    @SerializedName("isIv")
    @Expose
    private Object isIv;
    @SerializedName("elNum")
    @Expose
    private Object elNum;
    @SerializedName("ivNum")
    @Expose
    private Object ivNum;
    @SerializedName("rate")
    @Expose
    private Double rate;
    @SerializedName("rateOne")
    @Expose
    private Object rateOne;
    @SerializedName("rateThr")
    @Expose
    private Object rateThr;
    @SerializedName("rateSix")
    @Expose
    private Object rateSix;
    @SerializedName("active")
    @Expose
    private Integer active;
    @SerializedName("view")
    @Expose
    private Integer view;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("potEls")
    @Expose
    private Object potEls;
    @SerializedName("codes")
    @Expose
    private Object codes;
    @SerializedName("lifeSelects")
    @Expose
    private Object lifeSelects;
    @SerializedName("file")
    @Expose
    private Object file;
    @SerializedName("select")
    @Expose
    private Object select;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Object getInvestType() {
        return investType;
    }

    public void setInvestType(Object investType) {
        this.investType = investType;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Object getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Object minPrice) {
        this.minPrice = minPrice;
    }

    public Object getHopePrice() {
        return hopePrice;
    }

    public void setHopePrice(Object hopePrice) {
        this.hopePrice = hopePrice;
    }

    public Object getNowPrice() {
        return nowPrice;
    }

    public void setNowPrice(Object nowPrice) {
        this.nowPrice = nowPrice;
    }

    public Object getMonPrice() {
        return monPrice;
    }

    public void setMonPrice(Object monPrice) {
        this.monPrice = monPrice;
    }

    public Object getLifeYear() {
        return lifeYear;
    }

    public void setLifeYear(Object lifeYear) {
        this.lifeYear = lifeYear;
    }

    public Object getIsIv() {
        return isIv;
    }

    public void setIsIv(Object isIv) {
        this.isIv = isIv;
    }

    public Object getElNum() {
        return elNum;
    }

    public void setElNum(Object elNum) {
        this.elNum = elNum;
    }

    public Object getIvNum() {
        return ivNum;
    }

    public void setIvNum(Object ivNum) {
        this.ivNum = ivNum;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Object getRateOne() {
        return rateOne;
    }

    public void setRateOne(Object rateOne) {
        this.rateOne = rateOne;
    }

    public Object getRateThr() {
        return rateThr;
    }

    public void setRateThr(Object rateThr) {
        this.rateThr = rateThr;
    }

    public Object getRateSix() {
        return rateSix;
    }

    public void setRateSix(Object rateSix) {
        this.rateSix = rateSix;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Integer getView() {
        return view;
    }

    public void setView(Integer view) {
        this.view = view;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Object getPotEls() {
        return potEls;
    }

    public void setPotEls(Object potEls) {
        this.potEls = potEls;
    }

    public Object getCodes() {
        return codes;
    }

    public void setCodes(Object codes) {
        this.codes = codes;
    }

    public Object getLifeSelects() {
        return lifeSelects;
    }

    public void setLifeSelects(Object lifeSelects) {
        this.lifeSelects = lifeSelects;
    }

    public Object getFile() {
        return file;
    }

    public void setFile(Object file) {
        this.file = file;
    }

    public Object getSelect() {
        return select;
    }

    public void setSelect(Object select) {
        this.select = select;
    }
}

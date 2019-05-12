package quantec.com.moneypot.Activity.ActivityLifeChallenge;

public class ModelChartInfoLsit {

    String totalPrice;
    String yieldPrice;
    String potYear;
    String normalYear;
    String potTotal;
    String normalTotal;

    public ModelChartInfoLsit(String totalPrice, String yieldPrice, String potYear, String normalYear, String potTotal, String normalTotal) {
        this.totalPrice = totalPrice;
        this.yieldPrice = yieldPrice;
        this.potYear = potYear;
        this.normalYear = normalYear;
        this.potTotal = potTotal;
        this.normalTotal = normalTotal;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getYieldPrice() {
        return yieldPrice;
    }

    public void setYieldPrice(String yieldPrice) {
        this.yieldPrice = yieldPrice;
    }

    public String getPotYear() {
        return potYear;
    }

    public void setPotYear(String potYear) {
        this.potYear = potYear;
    }

    public String getNormalYear() {
        return normalYear;
    }

    public void setNormalYear(String normalYear) {
        this.normalYear = normalYear;
    }

    public String getPotTotal() {
        return potTotal;
    }

    public void setPotTotal(String potTotal) {
        this.potTotal = potTotal;
    }

    public String getNormalTotal() {
        return normalTotal;
    }

    public void setNormalTotal(String normalTotal) {
        this.normalTotal = normalTotal;
    }
}

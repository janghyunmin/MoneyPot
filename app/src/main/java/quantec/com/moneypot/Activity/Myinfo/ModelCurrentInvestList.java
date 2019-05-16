package quantec.com.moneypot.Activity.Myinfo;

public class ModelCurrentInvestList {

    String investName;
    String investYield;
    String totalCash;
    String yieldCash;
    String investTime;
    String revalencingTime;

    public ModelCurrentInvestList(String investName, String investYield, String totalCash, String yieldCash, String investTime, String revalencingTime) {
        this.investName = investName;
        this.investYield = investYield;
        this.totalCash = totalCash;
        this.yieldCash = yieldCash;
        this.investTime = investTime;
        this.revalencingTime = revalencingTime;
    }

    public String getInvestName() {
        return investName;
    }

    public void setInvestName(String investName) {
        this.investName = investName;
    }

    public String getInvestYield() {
        return investYield;
    }

    public void setInvestYield(String investYield) {
        this.investYield = investYield;
    }

    public String getTotalCash() {
        return totalCash;
    }

    public void setTotalCash(String totalCash) {
        this.totalCash = totalCash;
    }

    public String getYieldCash() {
        return yieldCash;
    }

    public void setYieldCash(String yieldCash) {
        this.yieldCash = yieldCash;
    }

    public String getInvestTime() {
        return investTime;
    }

    public void setInvestTime(String investTime) {
        this.investTime = investTime;
    }

    public String getRevalencingTime() {
        return revalencingTime;
    }

    public void setRevalencingTime(String revalencingTime) {
        this.revalencingTime = revalencingTime;
    }
}

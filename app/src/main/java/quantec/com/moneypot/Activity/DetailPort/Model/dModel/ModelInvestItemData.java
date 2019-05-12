package quantec.com.moneypot.Activity.DetailPort.Model.dModel;

public class ModelInvestItemData {

    String investName;
    double investRate;
    String investPer;

    public ModelInvestItemData(String investName, double investRate, String investPer) {
        this.investName = investName;
        this.investRate = investRate;
        this.investPer = investPer;
    }

    public String getInvestName() {
        return investName;
    }

    public void setInvestName(String investName) {
        this.investName = investName;
    }

    public double getInvestRate() {
        return investRate;
    }

    public void setInvestRate(double investRate) {
        this.investRate = investRate;
    }

    public String getInvestPer() {
        return investPer;
    }

    public void setInvestPer(String investPer) {
        this.investPer = investPer;
    }
}

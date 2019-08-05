package quantec.com.moneypot.Activity.Main.Fragment.Tab2;

public class ModelMakeMyPot {

    String potTitle;
    String potCode;
    String potDate;
    double potRate;
    boolean dataEmpty;

    public ModelMakeMyPot(String potTitle, String potCode, String potDate, double potRate, boolean dataEmpty) {
        this.potTitle = potTitle;
        this.potCode = potCode;
        this.potDate = potDate;
        this.potRate = potRate;
        this.dataEmpty = dataEmpty;
    }

    public String getPotTitle() {
        return potTitle;
    }

    public void setPotTitle(String potTitle) {
        this.potTitle = potTitle;
    }

    public String getPotCode() {
        return potCode;
    }

    public void setPotCode(String potCode) {
        this.potCode = potCode;
    }

    public String getPotDate() {
        return potDate;
    }

    public void setPotDate(String potDate) {
        this.potDate = potDate;
    }

    public double getPotRate() {
        return potRate;
    }

    public void setPotRate(double potRate) {
        this.potRate = potRate;
    }

    public boolean isDataEmpty() {
        return dataEmpty;
    }

    public void setDataEmpty(boolean dataEmpty) {
        this.dataEmpty = dataEmpty;
    }
}

package quantec.com.moneypot.Activity.Main;

public class PotEls {

    String ptCode;
    String stCode;
    String stName;
    int weight;

    public PotEls(String ptCode, String stCode, String stName, int weight) {
        this.ptCode = ptCode;
        this.stCode = stCode;
        this.stName = stName;
        this.weight = weight;
    }

    public String getPtCode() {
        return ptCode;
    }

    public void setPtCode(String ptCode) {
        this.ptCode = ptCode;
    }

    public String getStCode() {
        return stCode;
    }

    public void setStCode(String stCode) {
        this.stCode = stCode;
    }

    public String getStName() {
        return stName;
    }

    public void setStName(String stName) {
        this.stName = stName;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}

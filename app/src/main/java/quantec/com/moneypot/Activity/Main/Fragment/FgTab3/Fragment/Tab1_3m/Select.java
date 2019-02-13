package quantec.com.moneypot.Activity.Main.Fragment.FgTab3.Fragment.Tab1_3m;

public class Select {

    String code;
    String descript;
    boolean isDam;
    boolean isZim;
    int minCost;
    String name;
    double rate;
    int type;

    public Select(String code, String descript, boolean isDam, boolean isZim, int minCost, String name, double rate, int type) {
        this.code = code;
        this.descript = descript;
        this.isDam = isDam;
        this.isZim = isZim;
        this.minCost = minCost;
        this.name = name;
        this.rate = rate;
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public boolean isDam() {
        return isDam;
    }

    public void setDam(boolean dam) {
        isDam = dam;
    }

    public boolean isZim() {
        return isZim;
    }

    public void setZim(boolean zim) {
        isZim = zim;
    }

    public int getMinCost() {
        return minCost;
    }

    public void setMinCost(int minCost) {
        this.minCost = minCost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "{" +
                "code=" + code+
                ", descript=" + descript  +
                ", isDam=" + isDam +
                ", isZim=" + isZim +
                ", minCost=" + minCost +
                ", name=" + name +
                ", rate=" + rate +
                ", type=" + type +
                "}";
    }
}

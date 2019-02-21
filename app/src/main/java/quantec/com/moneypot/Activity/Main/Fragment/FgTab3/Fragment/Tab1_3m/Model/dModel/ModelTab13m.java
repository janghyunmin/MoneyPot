package quantec.com.moneypot.Activity.Main.Fragment.FgTab3.Fragment.Tab1_3m.Model.dModel;

public class ModelTab13m {

    String title;
    String code;
    double rate;
    boolean isZim;
    boolean isDam;
    int image;
    boolean onenChart;
    Long mincost;
    int type;


    public ModelTab13m(String title, String code, double rate, boolean isZim, boolean isDam, int image, boolean onenChart, Long mincost, int type) {
        this.title = title;
        this.code = code;
        this.rate = rate;
        this.isZim = isZim;
        this.isDam = isDam;
        this.image = image;
        this.onenChart = onenChart;
        this.mincost = mincost;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public boolean isZim() {
        return isZim;
    }

    public void setZim(boolean zim) {
        isZim = zim;
    }

    public boolean isDam() {
        return isDam;
    }

    public void setDam(boolean dam) {
        isDam = dam;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public boolean isOnenChart() {
        return onenChart;
    }

    public void setOnenChart(boolean onenChart) {
        this.onenChart = onenChart;
    }

    public Long getMincost() {
        return mincost;
    }

    public void setMincost(Long mincost) {
        this.mincost = mincost;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

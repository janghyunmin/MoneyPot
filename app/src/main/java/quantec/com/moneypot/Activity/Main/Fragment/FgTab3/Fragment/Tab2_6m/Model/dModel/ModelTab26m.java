package quantec.com.moneypot.Activity.Main.Fragment.FgTab3.Fragment.Tab2_6m.Model.dModel;

public class ModelTab26m {

    String title;
    String code;
    double rate;
    int check;
    int image;
    boolean onenChart;
    long mincost;

    public ModelTab26m(String title, String code, double rate, int check, int image, boolean onenChart, long mincost) {
        this.title = title;
        this.code = code;
        this.rate = rate;
        this.check = check;
        this.image = image;
        this.onenChart = onenChart;
        this.mincost = mincost;
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

    public int getCheck() {
        return check;
    }

    public void setCheck(int check) {
        this.check = check;
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

    public long getMincost() {
        return mincost;
    }

    public void setMincost(long mincost) {
        this.mincost = mincost;
    }
}

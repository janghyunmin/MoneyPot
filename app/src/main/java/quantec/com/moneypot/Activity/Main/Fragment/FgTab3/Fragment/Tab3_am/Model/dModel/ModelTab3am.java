package quantec.com.moneypot.Activity.Main.Fragment.FgTab3.Fragment.Tab3_am.Model.dModel;

public class ModelTab3am {

    String title;
    int code;
    String rate;
    boolean check;
    int image;
    boolean onenChart;
    long mincost;

    public ModelTab3am(String title, int code, String rate, boolean check, int image, boolean onenChart, long mincost) {
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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
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

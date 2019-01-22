package quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.Cookpage2.Model.dModel;

public class ModelCookList {
    String name;
    double rate;
    int code;
    boolean openchart;
    Long mincost;
    int checked;

    public ModelCookList(String name, double rate, int code, boolean openchart, Long mincost, int checked) {
        this.name = name;
        this.rate = rate;
        this.code = code;
        this.openchart = openchart;
        this.mincost = mincost;
        this.checked = checked;
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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isOpenchart() {
        return openchart;
    }

    public void setOpenchart(boolean openchart) {
        this.openchart = openchart;
    }

    public Long getMincost() {
        return mincost;
    }

    public void setMincost(Long mincost) {
        this.mincost = mincost;
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }
}

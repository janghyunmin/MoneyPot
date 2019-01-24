package quantec.com.moneypot.Activity.Main.Fragment.FgTab4.Model.nModel;

public class ModelFgTab4 {

    String Title;
    String Rate;
    int Image;
    int code;
    String Desc;
    int checked;
    Long mincost;
    boolean openChart;
    boolean ZzimCheck;

    public ModelFgTab4(String title, String rate, int image, int code, String desc, int checked, Long mincost, boolean openChart, boolean zzimCheck) {
        Title = title;
        Rate = rate;
        Image = image;
        this.code = code;
        Desc = desc;
        this.checked = checked;
        this.mincost = mincost;
        this.openChart = openChart;
        ZzimCheck = zzimCheck;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getRate() {
        return Rate;
    }

    public void setRate(String rate) {
        Rate = rate;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }

    public Long getMincost() {
        return mincost;
    }

    public void setMincost(Long mincost) {
        this.mincost = mincost;
    }

    public boolean isOpenChart() {
        return openChart;
    }

    public void setOpenChart(boolean openChart) {
        this.openChart = openChart;
    }

    public boolean isZzimCheck() {
        return ZzimCheck;
    }

    public void setZzimCheck(boolean zzimCheck) {
        ZzimCheck = zzimCheck;
    }
}

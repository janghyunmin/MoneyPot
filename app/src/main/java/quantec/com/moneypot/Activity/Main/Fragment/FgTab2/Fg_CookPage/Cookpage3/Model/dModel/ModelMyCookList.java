package quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.Cookpage3.Model.dModel;

public class ModelMyCookList {

    String title;
    double rate;
    int ucode;
    boolean openchart;
    Long mincost;
    String image;
    int photo;
    String descript;
    int type;

    public ModelMyCookList(String title, double rate, int ucode, boolean openchart, Long mincost, String image, int photo, String descript, int type) {
        this.title = title;
        this.rate = rate;
        this.ucode = ucode;
        this.openchart = openchart;
        this.mincost = mincost;
        this.image = image;
        this.photo = photo;
        this.descript = descript;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getUcode() {
        return ucode;
    }

    public void setUcode(int ucode) {
        this.ucode = ucode;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

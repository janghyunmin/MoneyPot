package quantec.com.moneypot.Activity.Main.Fragment.FgTab2;

public class ModelPotCookAll {

    boolean openView;
    boolean addSt;
    String stTitle;
    String stSubTitle;
    String imgUrl;

    public ModelPotCookAll(boolean openView, boolean addSt, String stTitle, String stSubTitle, String imgUrl) {
        this.openView = openView;
        this.addSt = addSt;
        this.stTitle = stTitle;
        this.stSubTitle = stSubTitle;
        this.imgUrl = imgUrl;
    }

    public boolean isOpenView() {
        return openView;
    }

    public void setOpenView(boolean openView) {
        this.openView = openView;
    }

    public boolean isAddSt() {
        return addSt;
    }

    public void setAddSt(boolean addSt) {
        this.addSt = addSt;
    }

    public String getStTitle() {
        return stTitle;
    }

    public void setStTitle(String stTitle) {
        this.stTitle = stTitle;
    }

    public String getStSubTitle() {
        return stSubTitle;
    }

    public void setStSubTitle(String stSubTitle) {
        this.stSubTitle = stSubTitle;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}

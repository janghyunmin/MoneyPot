package quantec.com.moneypot.Activity.Main.Fragment.Tab1;

public class ModelFitPotList {

    boolean isEmpty;
    String webViewUrl;
    String liftTitle;
    String lifeContent;
    String lifeYear;
    String lifePrice;
    String lifePlan;
    String lifeExp;
    String lifeType;
    String lifeStTitle;
    String lifeStYeild;

    public ModelFitPotList(boolean isEmpty, String webViewUrl, String liftTitle, String lifeContent, String lifeYear, String lifePrice, String lifePlan, String lifeExp, String lifeType, String lifeStTitle, String lifeStYeild) {
        this.isEmpty = isEmpty;
        this.webViewUrl = webViewUrl;
        this.liftTitle = liftTitle;
        this.lifeContent = lifeContent;
        this.lifeYear = lifeYear;
        this.lifePrice = lifePrice;
        this.lifePlan = lifePlan;
        this.lifeExp = lifeExp;
        this.lifeType = lifeType;
        this.lifeStTitle = lifeStTitle;
        this.lifeStYeild = lifeStYeild;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    public String getWebViewUrl() {
        return webViewUrl;
    }

    public void setWebViewUrl(String webViewUrl) {
        this.webViewUrl = webViewUrl;
    }

    public String getLiftTitle() {
        return liftTitle;
    }

    public void setLiftTitle(String liftTitle) {
        this.liftTitle = liftTitle;
    }

    public String getLifeContent() {
        return lifeContent;
    }

    public void setLifeContent(String lifeContent) {
        this.lifeContent = lifeContent;
    }

    public String getLifeYear() {
        return lifeYear;
    }

    public void setLifeYear(String lifeYear) {
        this.lifeYear = lifeYear;
    }

    public String getLifePrice() {
        return lifePrice;
    }

    public void setLifePrice(String lifePrice) {
        this.lifePrice = lifePrice;
    }

    public String getLifePlan() {
        return lifePlan;
    }

    public void setLifePlan(String lifePlan) {
        this.lifePlan = lifePlan;
    }

    public String getLifeExp() {
        return lifeExp;
    }

    public void setLifeExp(String lifeExp) {
        this.lifeExp = lifeExp;
    }

    public String getLifeType() {
        return lifeType;
    }

    public void setLifeType(String lifeType) {
        this.lifeType = lifeType;
    }

    public String getLifeStTitle() {
        return lifeStTitle;
    }

    public void setLifeStTitle(String lifeStTitle) {
        this.lifeStTitle = lifeStTitle;
    }

    public String getLifeStYeild() {
        return lifeStYeild;
    }

    public void setLifeStYeild(String lifeStYeild) {
        this.lifeStYeild = lifeStYeild;
    }
}

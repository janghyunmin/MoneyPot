package quantec.com.moneypot.Activity.Main.Fragment.Tab1;

public class ModelFitPotList {

    boolean webView;
    boolean itemView;

    boolean insertView;
    boolean chartView;
    boolean addView;
    boolean recomView;
    boolean investView;


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

    public ModelFitPotList(boolean webView, boolean itemView) {
        this.webView = webView;
        this.itemView = itemView;
    }

    public ModelFitPotList(boolean webView, boolean itemView, boolean insertView, boolean chartView, boolean addView, boolean recomView, boolean investView, String webViewUrl, String liftTitle, String lifeContent, String lifeYear, String lifePrice, String lifePlan, String lifeExp, String lifeType, String lifeStTitle, String lifeStYeild) {
        this.webView = webView;
        this.itemView = itemView;
        this.insertView = insertView;
        this.chartView = chartView;
        this.addView = addView;
        this.recomView = recomView;
        this.investView = investView;
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

    public boolean isWebView() {
        return webView;
    }

    public void setWebView(boolean webView) {
        this.webView = webView;
    }

    public boolean isItemView() {
        return itemView;
    }

    public void setItemView(boolean itemView) {
        this.itemView = itemView;
    }

    public boolean isInsertView() {
        return insertView;
    }

    public void setInsertView(boolean insertView) {
        this.insertView = insertView;
    }

    public boolean isChartView() {
        return chartView;
    }

    public void setChartView(boolean chartView) {
        this.chartView = chartView;
    }

    public boolean isAddView() {
        return addView;
    }

    public void setAddView(boolean addView) {
        this.addView = addView;
    }

    public boolean isRecomView() {
        return recomView;
    }

    public void setRecomView(boolean recomView) {
        this.recomView = recomView;
    }

    public boolean isInvestView() {
        return investView;
    }

    public void setInvestView(boolean investView) {
        this.investView = investView;
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

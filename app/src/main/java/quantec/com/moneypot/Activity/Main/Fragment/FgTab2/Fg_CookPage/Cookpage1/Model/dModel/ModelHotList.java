package quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.Cookpage1.Model.dModel;

public class ModelHotList {

    String title;
    double rate;
    int zzim;
    int basket;
    int code;

    public ModelHotList(String title, double rate, int zzim, int basket, int code) {
        this.title = title;
        this.rate = rate;
        this.zzim = zzim;
        this.basket = basket;
        this.code = code;
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

    public int getZzim() {
        return zzim;
    }

    public void setZzim(int zzim) {
        this.zzim = zzim;
    }

    public int getBasket() {
        return basket;
    }

    public void setBasket(int basket) {
        this.basket = basket;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}

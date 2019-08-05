package quantec.com.moneypot.Activity.Main.Fragment.Tab2.Adapter;

public class ModelMyPotPage {

    boolean empty;
    String title;
    String code;
    double rate;
    String date;

    public ModelMyPotPage(boolean empty, String title, String code, double rate, String date) {
        this.empty = empty;
        this.title = title;
        this.code = code;
        this.rate = rate;
        this.date = date;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

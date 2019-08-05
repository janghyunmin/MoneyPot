package quantec.com.moneypot.Activity.Main.Fragment.Tab2.Adapter;

public class ModelModifyPot {

    boolean empty;
    String title;
    String desc;
    String date;
    String code;
    double rate;

    public ModelModifyPot(boolean empty, String title, String desc, String date, String code, double rate) {
        this.empty = empty;
        this.title = title;
        this.desc = desc;
        this.date = date;
        this.code = code;
        this.rate = rate;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
}

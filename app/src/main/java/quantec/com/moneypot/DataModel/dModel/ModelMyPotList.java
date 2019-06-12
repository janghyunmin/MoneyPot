package quantec.com.moneypot.DataModel.dModel;

public class ModelMyPotList {

    boolean empty;
    boolean checkBt;
    String date;
    String title;
    double rate;

    public ModelMyPotList(boolean empty, boolean checkBt, String date, String title, double rate) {
        this.empty = empty;
        this.checkBt = checkBt;
        this.date = date;
        this.title = title;
        this.rate = rate;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public boolean isCheckBt() {
        return checkBt;
    }

    public void setCheckBt(boolean checkBt) {
        this.checkBt = checkBt;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
}

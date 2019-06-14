package quantec.com.moneypot.Activity.Main.Fragment.Tab2.Adapter;

public class ModelLeagueFormMadePot {

    boolean empty;
    boolean checkBt;
    String date;
    String title;
    double rate;

    public ModelLeagueFormMadePot(boolean empty, boolean checkBt, String date, String title, double rate) {
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

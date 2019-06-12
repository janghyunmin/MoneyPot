package quantec.com.moneypot.Activity.Main.Fragment.Tab2.Adapter;

public class ModelLeagueFormSelectPot {

    boolean empty;
    String title;
    double rate;

    public ModelLeagueFormSelectPot(boolean empty, String title, double rate) {
        this.empty = empty;
        this.title = title;
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

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}

package quantec.com.moneypot.Activity.Main.Fragment.Tab2.Adapter;

public class ModelLeagueFormMadePot {

    boolean empty;
    String date;
    String title;
    boolean rate;

    public ModelLeagueFormMadePot(boolean empty, String date, String title, boolean rate) {
        this.empty = empty;
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

    public boolean isRate() {
        return rate;
    }

    public void setRate(boolean rate) {
        this.rate = rate;
    }
}

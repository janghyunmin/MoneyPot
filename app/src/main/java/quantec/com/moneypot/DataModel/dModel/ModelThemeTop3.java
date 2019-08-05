package quantec.com.moneypot.DataModel.dModel;

public class ModelThemeTop3 {

    int number;
    String title;
    double rate;

    public ModelThemeTop3(int number, String title, double rate) {
        this.number = number;
        this.title = title;
        this.rate = rate;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
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

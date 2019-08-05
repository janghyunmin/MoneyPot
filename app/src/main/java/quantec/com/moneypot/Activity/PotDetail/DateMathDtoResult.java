package quantec.com.moneypot.Activity.PotDetail;

public class DateMathDtoResult {

    String date;
    double exp;

    public DateMathDtoResult(String date, double exp) {
        this.date = date;
        this.exp = exp;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getExp() {
        return exp;
    }

    public void setExp(double exp) {
        this.exp = exp;
    }
}

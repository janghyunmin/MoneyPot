package quantec.com.moneypot.Activity.PotDetail;

public class DateMathDto {

    String date;
    double price;
    double rate;

    public DateMathDto(String date, double price, double rate) {
        this.date = date;
        this.price = price;
        this.rate = rate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}

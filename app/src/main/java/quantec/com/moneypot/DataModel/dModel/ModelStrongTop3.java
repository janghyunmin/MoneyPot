package quantec.com.moneypot.DataModel.dModel;

import android.graphics.drawable.Drawable;

public class ModelStrongTop3 {

    int number;
    String title;
    Drawable image;
    double rate;

    public ModelStrongTop3(int number, String title, Drawable image, double rate) {
        this.number = number;
        this.title = title;
        this.image = image;
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

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}

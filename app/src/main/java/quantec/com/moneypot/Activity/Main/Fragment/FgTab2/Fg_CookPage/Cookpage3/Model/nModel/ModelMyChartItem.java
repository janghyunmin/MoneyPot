package quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.Cookpage3.Model.nModel;

import java.util.ArrayList;

public class ModelMyChartItem {

    double drate;
    int num;
    ArrayList<Elements> elements = new ArrayList<>();

    public double getDrate() {
        return drate;
    }

    public void setDrate(double drate) {
        this.drate = drate;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public ArrayList<Elements> getElements() {
        return elements;
    }

    public void setElements(ArrayList<Elements> elements) {
        this.elements = elements;
    }

    public class Elements {

        String date;
        float rate;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public float getRate() {
            return rate;
        }

        public void setRate(float rate) {
            this.rate = rate;
        }
    }
}

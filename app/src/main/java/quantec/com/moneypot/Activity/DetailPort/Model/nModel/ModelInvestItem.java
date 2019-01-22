package quantec.com.moneypot.Activity.DetailPort.Model.nModel;

import java.util.ArrayList;

public class ModelInvestItem {

    int num;
    ArrayList<Elements> elements = new ArrayList<>();

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
        String name;
        String bDate;
        double rate;
        double weight;
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getbDate() {
            return bDate;
        }
        public void setbDate(String bDate) {
            this.bDate = bDate;
        }
        public double getRate() {
            return rate;
        }
        public void setRate(double rate) {
            this.rate = rate;
        }

        public double getWeight() {
            return weight;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }
    }
}

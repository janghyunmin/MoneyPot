package quantec.com.moneypot.Activity.DetailPort.Model.nModel;

import java.util.ArrayList;

public class ModelDetailPage {

    ArrayList<Elements> elements = new ArrayList<>();

    public ArrayList<Elements> getElements() {
        return elements;
    }

    public void setElements(ArrayList<Elements> elements) {
        this.elements = elements;
    }

    public class Elements {

        String minCost;
        String bDate;
        String descript;
        int select;
        int put;

        public String getMinCost() {
            return minCost;
        }

        public void setMinCost(String minCost) {
            this.minCost = minCost;
        }

        public String getbDate() {
            return bDate;
        }

        public void setbDate(String bDate) {
            this.bDate = bDate;
        }

        public String getDescript() {
            return descript;
        }

        public void setDescript(String descript) {
            this.descript = descript;
        }

        public int getSelect() {
            return select;
        }

        public void setSelect(int select) {
            this.select = select;
        }

        public int getPut() {
            return put;
        }

        public void setPut(int put) {
            this.put = put;
        }
    }
}

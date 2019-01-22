package quantec.com.moneypot.Activity.SearchPort.SearchedPage.Fragment.AllPageTab.Model.nModel;

import java.util.ArrayList;

public class ModelPortZzim {

    String result;
    int num;
    ArrayList<Elements> elements = new ArrayList<>();

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
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
        String user;
        int product;

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public int getProduct() {
            return product;
        }

        public void setProduct(int product) {
            this.product = product;
        }
    }

}

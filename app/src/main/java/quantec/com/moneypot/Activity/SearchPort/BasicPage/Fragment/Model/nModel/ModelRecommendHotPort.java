package quantec.com.moneypot.Activity.SearchPort.BasicPage.Fragment.Model.nModel;

import java.util.ArrayList;

public class ModelRecommendHotPort {

    int num;
    ArrayList<Product> product = new ArrayList<>();

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public ArrayList<Product> getProduct() {
        return product;
    }

    public void setProduct(ArrayList<Product> product) {
        this.product = product;
    }

    public class Product {

        int code;
        String name;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}

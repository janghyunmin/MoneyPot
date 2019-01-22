package quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.Cookpage1.Model.nModel;

import java.util.ArrayList;

public class ModelCookpage1Item {

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
        double rate;
        String desc;
        int select;
        int put;

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

        public double getRate() {
            return rate;
        }

        public void setRate(double rate) {
            this.rate = rate;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
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

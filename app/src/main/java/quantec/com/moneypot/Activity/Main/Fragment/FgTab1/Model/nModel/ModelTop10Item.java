package quantec.com.moneypot.Activity.Main.Fragment.FgTab1.Model.nModel;

import java.util.ArrayList;

public class ModelTop10Item {

    int errorcode;
    int totalElements;
    ArrayList<Content> content = new ArrayList<>();

    public int getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(int errorcode) {
        this.errorcode = errorcode;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public ArrayList<Content> getContent() {
        return content;
    }

    public void setContent(ArrayList<Content> content) {
        this.content = content;
    }

    public class Content {

        String stCode;
        String name;
        String descript;
        int minCost;
        String beginDate;
        double rate;
        double rate30;
        double rate90;
        double rate180;
        int selected;

        public String getStCode() {
            return stCode;
        }

        public void setStCode(String stCode) {
            this.stCode = stCode;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescript() {
            return descript;
        }

        public void setDescript(String descript) {
            this.descript = descript;
        }

        public int getMinCost() {
            return minCost;
        }

        public void setMinCost(int minCost) {
            this.minCost = minCost;
        }

        public String getBeginDate() {
            return beginDate;
        }

        public void setBeginDate(String beginDate) {
            this.beginDate = beginDate;
        }

        public double getRate() {
            return rate;
        }

        public void setRate(double rate) {
            this.rate = rate;
        }

        public double getRate30() {
            return rate30;
        }

        public void setRate30(double rate30) {
            this.rate30 = rate30;
        }

        public double getRate90() {
            return rate90;
        }

        public void setRate90(double rate90) {
            this.rate90 = rate90;
        }

        public double getRate180() {
            return rate180;
        }

        public void setRate180(double rate180) {
            this.rate180 = rate180;
        }

        public int getSelected() {
            return selected;
        }

        public void setSelected(int selected) {
            this.selected = selected;
        }
    }

//    int num;
//    ArrayList<Product> product = new ArrayList<>();
//
//    public int getNum() {
//        return num;
//    }
//
//    public void setNum(int num) {
//        this.num = num;
//    }
//
//    public ArrayList<Product> getProduct() {
//        return product;
//    }
//
//    public void setProduct(ArrayList<Product> product) {
//        this.product = product;
//    }
//
//    public class Product{
//
//        int code;
//        String name;
//        double rate;
//        String desc;
//
//        public int getCode() {
//            return code;
//        }
//
//        public void setCode(int code) {
//            this.code = code;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public double getRate() {
//            return rate;
//        }
//
//        public void setRate(double rate) {
//            this.rate = rate;
//        }
//
//        public String getDesc() {
//            return desc;
//        }
//
//        public void setDesc(String desc) {
//            this.desc = desc;
//        }
//    }
}

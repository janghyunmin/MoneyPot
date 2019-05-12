package quantec.com.moneypot.Activity.SearchPort.SearchedPage.Model.nModel;

import java.util.ArrayList;

public class ModelSearchPage {

    int res_code;
    ArrayList<Title> title = new ArrayList<>();
    ArrayList<Descript> descript = new ArrayList<>();
    ArrayList<Stock> stock = new ArrayList<>();
    ArrayList<Suggest> suggest = new ArrayList<>();

    public int getRes_code() {
        return res_code;
    }

    public void setRes_code(int res_code) {
        this.res_code = res_code;
    }

    public ArrayList<Title> getTitle() {
        return title;
    }

    public void setTitle(ArrayList<Title> title) {
        this.title = title;
    }

    public ArrayList<Descript> getDescript() {
        return descript;
    }

    public void setDescript(ArrayList<Descript> descript) {
        this.descript = descript;
    }

    public ArrayList<Stock> getStock() {
        return stock;
    }

    public void setStock(ArrayList<Stock> stock) {
        this.stock = stock;
    }

    public ArrayList<Suggest> getSuggest() {
        return suggest;
    }

    public void setSuggest(ArrayList<Suggest> suggest) {
        this.suggest = suggest;
    }

    public class Title {

        int totalNum;
        ArrayList<Data> data = new ArrayList<>();

        public int getTotalNum() {
            return totalNum;
        }

        public void setTotalNum(int totalNum) {
            this.totalNum = totalNum;
        }

        public ArrayList<Data> getData() {
            return data;
        }

        public void setData(ArrayList<Data> data) {
            this.data = data;
        }

        public class Data {

            int code;
            String name;
            double rate;
            int select;
            int type;

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

            public int getSelect() {
                return select;
            }

            public void setSelect(int select) {
                this.select = select;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }

    }

    public class Descript {

        int totalNum;
        ArrayList<Data> data = new ArrayList<>();

        public int getTotalNum() {
            return totalNum;
        }

        public void setTotalNum(int totalNum) {
            this.totalNum = totalNum;
        }

        public ArrayList<Data> getData() {
            return data;
        }

        public void setData(ArrayList<Data> data) {
            this.data = data;
        }

        public class Data {

            int code;
            String name;
            String desc;
            double rate;
            int select;
            int type;

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

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public double getRate() {
                return rate;
            }

            public void setRate(double rate) {
                this.rate = rate;
            }

            public int getSelect() {
                return select;
            }

            public void setSelect(int select) {
                this.select = select;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }

    }

    public class Stock {

        int totalNum;
        ArrayList<Data> data = new ArrayList<>();

        public int getTotalNum() {
            return totalNum;
        }

        public void setTotalNum(int totalNum) {
            this.totalNum = totalNum;
        }

        public ArrayList<Data> getData() {
            return data;
        }

        public void setData(ArrayList<Data> data) {
            this.data = data;
        }

        public class Data {
            int code;
            String name;
            double rate;
            int sNum;
            int select;
            int type;

            ArrayList<Elements> elements = new ArrayList<>();

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

            public int getsNum() {
                return sNum;
            }

            public void setsNum(int sNum) {
                this.sNum = sNum;
            }

            public int getSelect() {
                return select;
            }

            public void setSelect(int select) {
                this.select = select;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public ArrayList<Elements> getElements() {
                return elements;
            }

            public void setElements(ArrayList<Elements> elements) {
                this.elements = elements;
            }

            public class Elements {
                String sname;

                public String getSname() {
                    return sname;
                }

                public void setSname(String sname) {
                    this.sname = sname;
                }
            }

        }
    }

    public class Suggest {

        int totalNum;
        ArrayList<Data> data = new ArrayList<>();

        public int getTotalNum() {
            return totalNum;
        }

        public void setTotalNum(int totalNum) {
            this.totalNum = totalNum;
        }

        public ArrayList<Data> getData() {
            return data;
        }

        public void setData(ArrayList<Data> data) {
            this.data = data;
        }

        public class Data {
            int code;
            String name;
            double rate;
            int select;
            int type;

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

            public int getSelect() {
                return select;
            }

            public void setSelect(int select) {
                this.select = select;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }
    }
}

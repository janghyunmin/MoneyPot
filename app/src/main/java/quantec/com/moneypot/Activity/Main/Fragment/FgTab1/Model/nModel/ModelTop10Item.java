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

        int type;
        int investType;
        String code;
        String name;
        String descript;
        int minPrice;
        int elNum;
        double rate;
        double rateOne;
        double rateThr;
        double rateSix;
        int active;
        int view;
        int status;
        String date;
        PackEls packEls;
        Codes codes;
        Filter filter;
        Select select;
        int uid;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getInvestType() {
            return investType;
        }

        public void setInvestType(int investType) {
            this.investType = investType;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
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

        public int getMinPrice() {
            return minPrice;
        }

        public void setMinPrice(int minPrice) {
            this.minPrice = minPrice;
        }

        public int getElNum() {
            return elNum;
        }

        public void setElNum(int elNum) {
            this.elNum = elNum;
        }

        public double getRate() {
            return rate;
        }

        public void setRate(double rate) {
            this.rate = rate;
        }

        public double getRateOne() {
            return rateOne;
        }

        public void setRateOne(double rateOne) {
            this.rateOne = rateOne;
        }

        public double getRateThr() {
            return rateThr;
        }

        public void setRateThr(double rateThr) {
            this.rateThr = rateThr;
        }

        public double getRateSix() {
            return rateSix;
        }

        public void setRateSix(double rateSix) {
            this.rateSix = rateSix;
        }

        public int getActive() {
            return active;
        }

        public void setActive(int active) {
            this.active = active;
        }

        public int getView() {
            return view;
        }

        public void setView(int view) {
            this.view = view;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public PackEls getPackEls() {
            return packEls;
        }

        public void setPackEls(PackEls packEls) {
            this.packEls = packEls;
        }

        public Codes getCodes() {
            return codes;
        }

        public void setCodes(Codes codes) {
            this.codes = codes;
        }

        public Filter getFilter() {
            return filter;
        }

        public void setFilter(Filter filter) {
            this.filter = filter;
        }

        public Select getSelect() {
            return select;
        }

        public void setSelect(Select select) {
            this.select = select;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public class PackEls{
        }
        public class Codes{
        }
        public class Filter{
        }
        public class Select{
        }

    }

}

package quantec.com.moneypot.Activity.FinishMakePort.Model.nModel;

import java.util.ArrayList;

public class ModelPortSavedInfo {

    int errorcode;
    int totalElements;
    Content content = new Content();
    Page page;

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

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public class Content {
        int type;
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
        ArrayList<PackEls> packEls = new ArrayList<>();
        Codes codes;
        File file;
        Select select;
        int uid;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
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

        public ArrayList<PackEls> getPackEls() {
            return packEls;
        }

        public void setPackEls(ArrayList<PackEls> packEls) {
            this.packEls = packEls;
        }

        public Codes getCodes() {
            return codes;
        }

        public void setCodes(Codes codes) {
            this.codes = codes;
        }

        public File getFile() {
            return file;
        }

        public void setFile(File file) {
            this.file = file;
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

        public class PackEls {
            String code;
            String elCode;
            String elName;
            double rate;
            String beginDate;
            String endDate;
            double weight;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getElCode() {
                return elCode;
            }

            public void setElCode(String elCode) {
                this.elCode = elCode;
            }

            public String getElName() {
                return elName;
            }

            public void setElName(String elName) {
                this.elName = elName;
            }

            public double getRate() {
                return rate;
            }

            public void setRate(double rate) {
                this.rate = rate;
            }

            public String getBeginDate() {
                return beginDate;
            }

            public void setBeginDate(String beginDate) {
                this.beginDate = beginDate;
            }

            public String getEndDate() {
                return endDate;
            }

            public void setEndDate(String endDate) {
                this.endDate = endDate;
            }

            public double getWeight() {
                return weight;
            }

            public void setWeight(double weight) {
                this.weight = weight;
            }
        }
        public class Codes{}
        public class File{}
        public class Select{}
    }
    public class Page{}

}

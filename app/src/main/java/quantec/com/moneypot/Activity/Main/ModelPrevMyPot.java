package quantec.com.moneypot.Activity.Main;

import java.util.ArrayList;

public class ModelPrevMyPot {

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
        String ptCode;
        String date;
        String descript;
        String name;
        double rate;
        double rate30;
        double rate60;
        double rate90;
        double rate180;
        int investType;
        int minPrice;
        ArrayList<PotEls> potEls = new ArrayList<>();
        StCodes stCodes;
        PotFile potFile;

        public String getPtCode() {
            return ptCode;
        }

        public void setPtCode(String ptCode) {
            this.ptCode = ptCode;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getDescript() {
            return descript;
        }

        public void setDescript(String descript) {
            this.descript = descript;
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

        public double getRate30() {
            return rate30;
        }

        public void setRate30(double rate30) {
            this.rate30 = rate30;
        }

        public double getRate60() {
            return rate60;
        }

        public void setRate60(double rate60) {
            this.rate60 = rate60;
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

        public int getInvestType() {
            return investType;
        }

        public void setInvestType(int investType) {
            this.investType = investType;
        }

        public int getMinPrice() {
            return minPrice;
        }

        public void setMinPrice(int minPrice) {
            this.minPrice = minPrice;
        }

        public ArrayList<PotEls> getPotEls() {
            return potEls;
        }

        public void setPotEls(ArrayList<PotEls> potEls) {
            this.potEls = potEls;
        }

        public StCodes getStCodes() {
            return stCodes;
        }

        public void setStCodes(StCodes stCodes) {
            this.stCodes = stCodes;
        }

        public PotFile getPotFile() {
            return potFile;
        }

        public void setPotFile(PotFile potFile) {
            this.potFile = potFile;
        }

        public class PotEls {

            String ptCode;
            String stCode;
            String stName;
            double weight;

            public String getPtCode() {
                return ptCode;
            }

            public void setPtCode(String ptCode) {
                this.ptCode = ptCode;
            }

            public String getStCode() {
                return stCode;
            }

            public void setStCode(String stCode) {
                this.stCode = stCode;
            }

            public String getStName() {
                return stName;
            }

            public void setStName(String stName) {
                this.stName = stName;
            }

            public double getWeight() {
                return weight;
            }

            public void setWeight(double weight) {
                this.weight = weight;
            }
        }
        public class StCodes{}
        public class PotFile{}
    }
    public class Page{}
}

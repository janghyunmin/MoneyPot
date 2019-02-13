package quantec.com.moneypot.Activity.DetailPort.Model.nModel;

import java.util.ArrayList;

public class ModelInvestItem {

    int errorcode;
    int totalElements;
    Content content = new Content();
    Page page = new Page();

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
        String stCode;
        String name;
        String descript;
        int minCost;
        String beginDate;
        double rate;
        double rate30;
        double rate90;
        double rate180;
        Select select = new Select();        ArrayList<StEls> stEls = new ArrayList<>();
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

        public Select getSelect() {
            return select;
        }

        public void setSelect(Select select) {
            this.select = select;
        }

        public class Select {
            int type;
            boolean isZim;
            boolean isDam;
            String code;
            String name;
            String descript;
            int minCost;
            double rate;

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public boolean isZim() {
                return isZim;
            }

            public void setZim(boolean zim) {
                isZim = zim;
            }

            public boolean isDam() {
                return isDam;
            }

            public void setDam(boolean dam) {
                isDam = dam;
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

            public int getMinCost() {
                return minCost;
            }

            public void setMinCost(int minCost) {
                this.minCost = minCost;
            }

            public double getRate() {
                return rate;
            }

            public void setRate(double rate) {
                this.rate = rate;
            }
        }

        public ArrayList<StEls> getStEls() {
            return stEls;
        }

        public void setStEls(ArrayList<StEls> stEls) {
            this.stEls = stEls;
        }

        public class StEls {
            String elCode;
            String name;
            String beginDate;
            String endDate;
            double rate;
            float weight;

            public String getElCode() {
                return elCode;
            }

            public void setElCode(String elCode) {
                this.elCode = elCode;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
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

            public double getRate() {
                return rate;
            }

            public void setRate(double rate) {
                this.rate = rate;
            }

            public float getWeight() {
                return weight;
            }

            public void setWeight(float weight) {
                this.weight = weight;
            }
        }
    }

    public class Page{

    }

}

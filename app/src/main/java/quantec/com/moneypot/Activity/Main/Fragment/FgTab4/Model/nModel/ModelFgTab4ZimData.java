package quantec.com.moneypot.Activity.Main.Fragment.FgTab4.Model.nModel;

import java.util.ArrayList;

public class ModelFgTab4ZimData {

    int errorcode;
    int totalElements;
    ArrayList<Content> content = new ArrayList<>();
    ArrayList<Page> page = new ArrayList<>();

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

    public ArrayList<Page> getPage() {
        return page;
    }

    public void setPage(ArrayList<Page> page) {
        this.page = page;
    }

    public class Content {

        int type;
        boolean isZim;
        boolean isDam;
        String code;
        String name;
        String descript;
        Long minPrice;
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

        public Long getMinPrice() {
            return minPrice;
        }

        public void setMinPrice(Long minPrice) {
            this.minPrice = minPrice;
        }

        public double getRate() {
            return rate;
        }

        public void setRate(double rate) {
            this.rate = rate;
        }
    }

    public class Page{

    }
}

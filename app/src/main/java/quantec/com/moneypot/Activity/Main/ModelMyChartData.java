package quantec.com.moneypot.Activity.Main;

import java.util.ArrayList;

public class ModelMyChartData {
    int errorcode;
    int totalElements;
    ArrayList<Content> content = new ArrayList<>();
    String page;

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

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public class Content {
        String date;
        float ln;
        float exp;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public float getLn() {
            return ln;
        }

        public void setLn(float ln) {
            this.ln = ln;
        }

        public float getExp() {
            return exp;
        }

        public void setExp(float exp) {
            this.exp = exp;
        }
    }

}

package quantec.com.moneypot.Activity.Intro;

import java.util.ArrayList;

public class ModelRegChk {

    int status;
    long timestamp;
    int totalElements;
    boolean noContent;
    ArrayList<Content> content = new ArrayList<>();

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public boolean isNoContent() {
        return noContent;
    }

    public void setNoContent(boolean noContent) {
        this.noContent = noContent;
    }

    public ArrayList<Content> getContent() {
        return content;
    }

    public void setContent(ArrayList<Content> content) {
        this.content = content;
    }

    public class Content {

        boolean isFidoReg;
        String site;
        String authcode;
        String deviceid;
        String statusCode;

        public boolean isFidoReg() {
            return isFidoReg;
        }

        public void setFidoReg(boolean fidoReg) {
            isFidoReg = fidoReg;
        }

        public String getSite() {
            return site;
        }

        public void setSite(String site) {
            this.site = site;
        }

        public String getAuthcode() {
            return authcode;
        }

        public void setAuthcode(String authcode) {
            this.authcode = authcode;
        }

        public String getDeviceid() {
            return deviceid;
        }

        public void setDeviceid(String deviceid) {
            this.deviceid = deviceid;
        }

        public String getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(String statusCode) {
            this.statusCode = statusCode;
        }
    }

}

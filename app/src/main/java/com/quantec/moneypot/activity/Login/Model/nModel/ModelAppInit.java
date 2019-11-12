package com.quantec.moneypot.activity.Login.Model.nModel;

public class ModelAppInit {

//    int status;
//    String timestamp;
//    int totalElements;
//    boolean noContent;
////    Object content;
//    String content;
//    Page page;
//
//    public int getStatus() {
//        return status;
//    }
//
//    public void setStatus(int status) {
//        this.status = status;
//    }
//
//    public String getTimestamp() {
//        return timestamp;
//    }
//
//    public void setTimestamp(String timestamp) {
//        this.timestamp = timestamp;
//    }
//
//    public int getTotalElements() {
//        return totalElements;
//    }
//
//    public void setTotalElements(int totalElements) {
//        this.totalElements = totalElements;
//    }
//
//    public boolean isNoContent() {
//        return noContent;
//    }
//
//    public void setNoContent(boolean noContent) {
//        this.noContent = noContent;
//    }
//
//
//
//    public Page getPage() {
//        return page;
//    }
//
//    public void setPage(Page page) {
//        this.page = page;
//    }
//
//
//    public String getContent() {
//        return content;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }
//
//    public class Page{
//
//    }



    int status;
    String timestamp;
    int totalElements;
    boolean noContent;
    Content content = new Content();
    Page page;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
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

//    public String getContent() {
//        return content;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }

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

        int activeStep;
        String uid;
        String cid;

        public int getActiveStep() {
            return activeStep;
        }

        public void setActiveStep(int activeStep) {
            this.activeStep = activeStep;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }
    }

    public class Page{

    }
}

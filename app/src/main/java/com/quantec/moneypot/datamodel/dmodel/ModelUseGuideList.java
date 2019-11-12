package com.quantec.moneypot.datamodel.dmodel;

public class ModelUseGuideList {
    public String title;
    public String content;
    public int resId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public ModelUseGuideList(String title, String content, int resId) {
        this.title = title;
        this.content = content;
        this.resId = resId;
    }

    //    String title;
//    String subtitle;
//    String content;
//
//    public ModelUseGuideList(String title, String subtitle, String content) {
//        this.title = title;
//        this.subtitle = subtitle;
//        this.content = content;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getSubtitle() {
//        return subtitle;
//    }
//
//    public void setSubtitle(String subtitle) {
//        this.subtitle = subtitle;
//    }
//
//    public String getContent() {
//        return content;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }
}

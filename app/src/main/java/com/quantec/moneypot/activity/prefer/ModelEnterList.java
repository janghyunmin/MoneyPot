package com.quantec.moneypot.activity.prefer;

public class ModelEnterList {

    String title;
    String image;
    boolean select;
    boolean endPoint;

    public ModelEnterList(String title, String image, boolean select, boolean endPoint) {
        this.title = title;
        this.image = image;
        this.select = select;
        this.endPoint = endPoint;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public boolean isEndPoint() {
        return endPoint;
    }

    public void setEndPoint(boolean endPoint) {
        this.endPoint = endPoint;
    }
}

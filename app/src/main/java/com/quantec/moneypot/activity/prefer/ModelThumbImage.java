package com.quantec.moneypot.activity.prefer;

public class ModelThumbImage {

    String image;
    String bunya;
    String title;
    int realPosition;

    public ModelThumbImage(String image, String bunya, String title, int realPosition) {
        this.image = image;
        this.bunya = bunya;
        this.title = title;
        this.realPosition = realPosition;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBunya() {
        return bunya;
    }

    public void setBunya(String bunya) {
        this.bunya = bunya;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRealPosition() {
        return realPosition;
    }

    public void setRealPosition(int realPosition) {
        this.realPosition = realPosition;
    }
}

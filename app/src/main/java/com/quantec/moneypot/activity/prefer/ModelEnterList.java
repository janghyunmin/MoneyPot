package com.quantec.moneypot.activity.prefer;

public class ModelEnterList {

    String title;
    String code;
    String image;
    boolean select;
    boolean endPoint;
    int category;
    boolean visible;
    boolean cate2Click;

    public ModelEnterList(String title, String code, String image, boolean select, boolean endPoint, int category, boolean visible, boolean cate2Click) {
        this.title = title;
        this.code = code;
        this.image = image;
        this.select = select;
        this.endPoint = endPoint;
        this.category = category;
        this.visible = visible;
        this.cate2Click = cate2Click;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isCate2Click() {
        return cate2Click;
    }

    public void setCate2Click(boolean cate2Click) {
        this.cate2Click = cate2Click;
    }
}

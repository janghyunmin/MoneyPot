package com.quantec.moneypot.activity.Login.resist;

public class ModelPropensityClick {

    boolean click;
    int position;

    public ModelPropensityClick(boolean click, int position) {
        this.click = click;
        this.position = position;
    }

    public boolean isClick() {
        return click;
    }

    public void setClick(boolean click) {
        this.click = click;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}

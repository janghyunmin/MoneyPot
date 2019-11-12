package com.quantec.moneypot.datamodel.nmodel;

import java.util.ArrayList;

public class ModelChartData {

    ArrayList<Content> content = new ArrayList<>();

    public ArrayList<Content> getContent() {
        return content;
    }

    public void setContent(ArrayList<Content> content) {
        this.content = content;
    }

    public class Content {

        String date;
        float ln;
        float exp;
        double price;

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

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }
    }
}

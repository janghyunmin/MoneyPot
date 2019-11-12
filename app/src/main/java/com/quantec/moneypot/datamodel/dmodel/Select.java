package com.quantec.moneypot.datamodel.dmodel;

public class Select {

      String code;
      String descript;
      boolean isDam;
      boolean isZim;
      int minPrice;
      String name;
      double rate;
      int type;
      String uid;

    public Select(String code, String descript, boolean isDam, boolean isZim, int minPrice, String name, double rate, int type, String uid) {
        this.code = code;
        this.descript = descript;
        this.isDam = isDam;
        this.isZim = isZim;
        this.minPrice = minPrice;
        this.name = name;
        this.rate = rate;
        this.type = type;
        this.uid = uid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public boolean isDam() {
        return isDam;
    }

    public void setDam(boolean dam) {
        isDam = dam;
    }

    public boolean isZim() {
        return isZim;
    }

    public void setZim(boolean zim) {
        isZim = zim;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "{" +
                "code='" + code + '\'' +
                ", descript='" + descript + '\'' +
                ", isDam=" + isDam +
                ", isZim=" + isZim +
                ", minPrice=" + minPrice +
                ", name='" + name + '\'' +
                ", rate=" + rate +
                ", type=" + type +
                ", uid='" + uid + '\'' +
                '}';
    }

    //    String code;
//    boolean isDam;
//    boolean isZim;
//
//    public Select(String code, boolean isDam, boolean isZim) {
//        this.code = code;
//        this.isDam = isDam;
//        this.isZim = isZim;
//    }
//
//    public String getCode() {
//        return code;
//    }
//
//    public void setCode(String code) {
//        this.code = code;
//    }
//
//    public boolean isDam() {
//        return isDam;
//    }
//
//    public void setDam(boolean dam) {
//        isDam = dam;
//    }
//
//    public boolean isZim() {
//        return isZim;
//    }
//
//    public void setZim(boolean zim) {
//        isZim = zim;
//    }
//
//    @Override
//    public String toString() {
//        return "{" +
//                "code=" + code +
//                ", isDam=" + isDam +
//                ", isZim=" + isZim +
//                "}";
//    }
}
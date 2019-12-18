package com.quantec.moneypot.activity.simulationsearch;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelSimulSum implements Parcelable {

    boolean empty;
    int total;
    String name;
    String code;
    String stock;
    double rate;

    public ModelSimulSum(boolean empty, int total, String name, String code, String stock, double rate) {
        this.empty = empty;
        this.total = total;
        this.name = name;
        this.code = code;
        this.stock = stock;
        this.rate = rate;
    }

    protected ModelSimulSum(Parcel in) {
        empty = in.readByte() != 0;
        total = in.readInt();
        name = in.readString();
        code = in.readString();
        stock = in.readString();
        rate = in.readDouble();
    }

    public static final Creator<ModelSimulSum> CREATOR = new Creator<ModelSimulSum>() {
        @Override
        public ModelSimulSum createFromParcel(Parcel in) {
            return new ModelSimulSum(in);
        }

        @Override
        public ModelSimulSum[] newArray(int size) {
            return new ModelSimulSum[size];
        }
    };

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (empty ? 1 : 0));
        dest.writeInt(total);
        dest.writeString(name);
        dest.writeString(code);
        dest.writeString(stock);
        dest.writeDouble(rate);
    }
}

package com.quantec.moneypot.activity.simulationsearch;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelSimulSingle implements Parcelable {

    boolean empty;
    int total;
    String name;
    String code;
    double rate;

    public ModelSimulSingle(boolean empty, int total, String name, String code, double rate) {
        this.empty = empty;
        this.total = total;
        this.name = name;
        this.code = code;
        this.rate = rate;
    }

    protected ModelSimulSingle(Parcel in) {
        empty = in.readByte() != 0;
        total = in.readInt();
        name = in.readString();
        code = in.readString();
        rate = in.readDouble();
    }

    public static final Creator<ModelSimulSingle> CREATOR = new Creator<ModelSimulSingle>() {
        @Override
        public ModelSimulSingle createFromParcel(Parcel in) {
            return new ModelSimulSingle(in);
        }

        @Override
        public ModelSimulSingle[] newArray(int size) {
            return new ModelSimulSingle[size];
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
        dest.writeDouble(rate);
    }
}

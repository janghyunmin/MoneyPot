package com.quantec.moneypot.activity.Main.Fragment.Tab3;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelPreChartList implements Parcelable {

    String name;
    String code;
    double rate;

    public ModelPreChartList(String name, String code, double rate) {
        this.name = name;
        this.code = code;
        this.rate = rate;
    }

    protected ModelPreChartList(Parcel in) {
        name = in.readString();
        code = in.readString();
        rate = in.readDouble();
    }

    public static final Creator<ModelPreChartList> CREATOR = new Creator<ModelPreChartList>() {
        @Override
        public ModelPreChartList createFromParcel(Parcel in) {
            return new ModelPreChartList(in);
        }

        @Override
        public ModelPreChartList[] newArray(int size) {
            return new ModelPreChartList[size];
        }
    };

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
        dest.writeString(name);
        dest.writeString(code);
        dest.writeDouble(rate);
    }
}

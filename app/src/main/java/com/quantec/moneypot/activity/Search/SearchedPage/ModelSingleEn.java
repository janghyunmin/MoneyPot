package com.quantec.moneypot.activity.Search.SearchedPage;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelSingleEn implements Parcelable {

    boolean empty;
    String title;
    String code;
    double rate;
    int follow;

    public ModelSingleEn(boolean empty, String title, String code, double rate, int follow) {
        this.empty = empty;
        this.title = title;
        this.code = code;
        this.rate = rate;
        this.follow = follow;
    }

    protected ModelSingleEn(Parcel in) {
        empty = in.readByte() != 0;
        title = in.readString();
        code = in.readString();
        rate = in.readDouble();
        follow = in.readInt();
    }

    public static final Creator<ModelSingleEn> CREATOR = new Creator<ModelSingleEn>() {
        @Override
        public ModelSingleEn createFromParcel(Parcel in) {
            return new ModelSingleEn(in);
        }

        @Override
        public ModelSingleEn[] newArray(int size) {
            return new ModelSingleEn[size];
        }
    };

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
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

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getFollow() {
        return follow;
    }

    public void setFollow(int follow) {
        this.follow = follow;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (empty ? 1 : 0));
        dest.writeString(title);
        dest.writeString(code);
        dest.writeDouble(rate);
        dest.writeInt(follow);
    }
}

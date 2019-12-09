package com.quantec.moneypot.activity.Search.SearchedPage;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelSumEn implements Parcelable {

    boolean empty;
    String title;
    String code;
    String stock;
    double rate;
    int follow;

    public ModelSumEn(boolean empty, String title, String code, String stock, double rate, int follow) {
        this.empty = empty;
        this.title = title;
        this.code = code;
        this.stock = stock;
        this.rate = rate;
        this.follow = follow;
    }

    protected ModelSumEn(Parcel in) {
        empty = in.readByte() != 0;
        title = in.readString();
        code = in.readString();
        stock = in.readString();
        rate = in.readDouble();
        follow = in.readInt();
    }

    public static final Creator<ModelSumEn> CREATOR = new Creator<ModelSumEn>() {
        @Override
        public ModelSumEn createFromParcel(Parcel in) {
            return new ModelSumEn(in);
        }

        @Override
        public ModelSumEn[] newArray(int size) {
            return new ModelSumEn[size];
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
        dest.writeString(stock);
        dest.writeDouble(rate);
        dest.writeInt(follow);
    }
}

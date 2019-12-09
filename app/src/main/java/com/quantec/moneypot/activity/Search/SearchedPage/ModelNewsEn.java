package com.quantec.moneypot.activity.Search.SearchedPage;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelNewsEn implements Parcelable {

    boolean empty;
    int total;
    String title;
    String date;
    String url;

    public ModelNewsEn(boolean empty, int total, String title, String date, String url) {
        this.empty = empty;
        this.total = total;
        this.title = title;
        this.date = date;
        this.url = url;
    }

    protected ModelNewsEn(Parcel in) {
        empty = in.readByte() != 0;
        total = in.readInt();
        title = in.readString();
        date = in.readString();
        url = in.readString();
    }

    public static final Creator<ModelNewsEn> CREATOR = new Creator<ModelNewsEn>() {
        @Override
        public ModelNewsEn createFromParcel(Parcel in) {
            return new ModelNewsEn(in);
        }

        @Override
        public ModelNewsEn[] newArray(int size) {
            return new ModelNewsEn[size];
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (empty ? 1 : 0));
        dest.writeInt(total);
        dest.writeString(title);
        dest.writeString(date);
        dest.writeString(url);
    }
}


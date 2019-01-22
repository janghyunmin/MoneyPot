package quantec.com.moneypot.Activity.SearchPort.SearchedPage.Model.dModel;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelPostStockItem implements Parcelable {

    int category;
    int totalNum;
    int code;
    String name;
    double rate;
    int sNum;
    String sname;
    int select;
    int type;

    public ModelPostStockItem(int category, int totalNum, int code, String name, double rate, int sNum, String sname, int select, int type) {
        this.category = category;
        this.totalNum = totalNum;
        this.code = code;
        this.name = name;
        this.rate = rate;
        this.sNum = sNum;
        this.sname = sname;
        this.select = select;
        this.type = type;
    }

    protected ModelPostStockItem(Parcel in) {
        category = in.readInt();
        totalNum = in.readInt();
        code = in.readInt();
        name = in.readString();
        rate = in.readDouble();
        sNum = in.readInt();
        sname = in.readString();
        select = in.readInt();
        type = in.readInt();
    }

    public static final Creator<ModelPostStockItem> CREATOR = new Creator<ModelPostStockItem>() {
        @Override
        public ModelPostStockItem createFromParcel(Parcel in) {
            return new ModelPostStockItem(in);
        }

        @Override
        public ModelPostStockItem[] newArray(int size) {
            return new ModelPostStockItem[size];
        }
    };

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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

    public int getsNum() {
        return sNum;
    }

    public void setsNum(int sNum) {
        this.sNum = sNum;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public int getSelect() {
        return select;
    }

    public void setSelect(int select) {
        this.select = select;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(category);
        dest.writeInt(totalNum);
        dest.writeInt(code);
        dest.writeString(name);
        dest.writeDouble(rate);
        dest.writeInt(sNum);
        dest.writeString(sname);
        dest.writeInt(select);
        dest.writeInt(type);
    }
}

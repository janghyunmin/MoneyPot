package quantec.com.moneypot.Activity.SearchPort.SearchedPage.Model.dModel;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelPostDescItem implements Parcelable {

    int category;
    int totalNum;
    int code;
    String name;
    String desc;
    double rate;
    int select;
    int type;

    public ModelPostDescItem(int category, int totalNum, int code, String name, String desc, double rate, int select, int type) {
        this.category = category;
        this.totalNum = totalNum;
        this.code = code;
        this.name = name;
        this.desc = desc;
        this.rate = rate;
        this.select = select;
        this.type = type;
    }

    protected ModelPostDescItem(Parcel in) {
        category = in.readInt();
        totalNum = in.readInt();
        code = in.readInt();
        name = in.readString();
        desc = in.readString();
        rate = in.readDouble();
        select = in.readInt();
        type = in.readInt();
    }

    public static final Creator<ModelPostDescItem> CREATOR = new Creator<ModelPostDescItem>() {
        @Override
        public ModelPostDescItem createFromParcel(Parcel in) {
            return new ModelPostDescItem(in);
        }

        @Override
        public ModelPostDescItem[] newArray(int size) {
            return new ModelPostDescItem[size];
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
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
        dest.writeString(desc);
        dest.writeDouble(rate);
        dest.writeInt(select);
        dest.writeInt(type);
    }
}

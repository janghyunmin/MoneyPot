package quantec.com.moneypot.Activity.SearchPort.SearchedPage.Model.dModel;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelTitleItem implements Parcelable {

    int category;
    int totalNum;
    String code;
    String name;
    double rate;
    boolean select;
    int type;

    public ModelTitleItem(int category, int totalNum, String code, String name, double rate, boolean select, int type) {
        this.category = category;
        this.totalNum = totalNum;
        this.code = code;
        this.name = name;
        this.rate = rate;
        this.select = select;
        this.type = type;
    }

    protected ModelTitleItem(Parcel in) {
        category = in.readInt();
        totalNum = in.readInt();
        code = in.readString();
        name = in.readString();
        rate = in.readDouble();
        select = in.readByte() != 0;
        type = in.readInt();
    }

    public static final Creator<ModelTitleItem> CREATOR = new Creator<ModelTitleItem>() {
        @Override
        public ModelTitleItem createFromParcel(Parcel in) {
            return new ModelTitleItem(in);
        }

        @Override
        public ModelTitleItem[] newArray(int size) {
            return new ModelTitleItem[size];
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
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
        dest.writeString(code);
        dest.writeString(name);
        dest.writeDouble(rate);
        dest.writeByte((byte) (select ? 1 : 0));
        dest.writeInt(type);
    }
}

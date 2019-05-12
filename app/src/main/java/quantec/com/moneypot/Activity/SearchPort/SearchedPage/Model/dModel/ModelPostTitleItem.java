package quantec.com.moneypot.Activity.SearchPort.SearchedPage.Model.dModel;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelPostTitleItem implements Parcelable {

    int category;
    int totalNum;
    String code;
    String name;
    double rate;
    boolean isZim;
    boolean isDam;
    int type;

    public ModelPostTitleItem(int category, int totalNum, String code, String name, double rate, boolean isZim, boolean isDam, int type) {
        this.category = category;
        this.totalNum = totalNum;
        this.code = code;
        this.name = name;
        this.rate = rate;
        this.isZim = isZim;
        this.isDam = isDam;
        this.type = type;
    }

    protected ModelPostTitleItem(Parcel in) {
        category = in.readInt();
        totalNum = in.readInt();
        code = in.readString();
        name = in.readString();
        rate = in.readDouble();
        isZim = in.readByte() != 0;
        isDam = in.readByte() != 0;
        type = in.readInt();
    }

    public static final Creator<ModelPostTitleItem> CREATOR = new Creator<ModelPostTitleItem>() {
        @Override
        public ModelPostTitleItem createFromParcel(Parcel in) {
            return new ModelPostTitleItem(in);
        }

        @Override
        public ModelPostTitleItem[] newArray(int size) {
            return new ModelPostTitleItem[size];
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

    public boolean isZim() {
        return isZim;
    }

    public void setZim(boolean zim) {
        isZim = zim;
    }

    public boolean isDam() {
        return isDam;
    }

    public void setDam(boolean dam) {
        isDam = dam;
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
        dest.writeByte((byte) (isZim ? 1 : 0));
        dest.writeByte((byte) (isDam ? 1 : 0));
        dest.writeInt(type);
    }
}

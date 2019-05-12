package quantec.com.moneypot.Activity.SearchPort.SearchedPage.Model.dModel;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelEmptyItem implements Parcelable {

    int category;
    int totalNum;
    String code;
    String name;

    public ModelEmptyItem(int category, int totalNum, String code, String name) {
        this.category = category;
        this.totalNum = totalNum;
        this.code = code;
        this.name = name;
    }

    protected ModelEmptyItem(Parcel in) {
        category = in.readInt();
        totalNum = in.readInt();
        code = in.readString();
        name = in.readString();
    }

    public static final Creator<ModelEmptyItem> CREATOR = new Creator<ModelEmptyItem>() {
        @Override
        public ModelEmptyItem createFromParcel(Parcel in) {
            return new ModelEmptyItem(in);
        }

        @Override
        public ModelEmptyItem[] newArray(int size) {
            return new ModelEmptyItem[size];
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
    }
}

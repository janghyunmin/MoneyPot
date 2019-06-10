package quantec.com.moneypot.ModelCommon.dModel;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelPortList implements Parcelable {

    String stCode;
    String stname;
    String category;

    public ModelPortList(String stCode, String stname, String category) {
        this.stCode = stCode;
        this.stname = stname;
        this.category = category;
    }

    protected ModelPortList(Parcel in) {
        stCode = in.readString();
        stname = in.readString();
        category = in.readString();
    }

    public static final Creator<ModelPortList> CREATOR = new Creator<ModelPortList>() {
        @Override
        public ModelPortList createFromParcel(Parcel in) {
            return new ModelPortList(in);
        }

        @Override
        public ModelPortList[] newArray(int size) {
            return new ModelPortList[size];
        }
    };

    public String getStCode() {
        return stCode;
    }

    public void setStCode(String stCode) {
        this.stCode = stCode;
    }

    public String getStname() {
        return stname;
    }

    public void setStname(String stname) {
        this.stname = stname;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(stCode);
        dest.writeString(stname);
        dest.writeString(category);
    }
}

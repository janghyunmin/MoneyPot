package quantec.com.moneypot.DataModel.dModel;

public class Select {

    String code;
    boolean isDam;
    boolean isZim;

    public Select(String code, boolean isDam, boolean isZim) {
        this.code = code;
        this.isDam = isDam;
        this.isZim = isZim;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isDam() {
        return isDam;
    }

    public void setDam(boolean dam) {
        isDam = dam;
    }

    public boolean isZim() {
        return isZim;
    }

    public void setZim(boolean zim) {
        isZim = zim;
    }

    @Override
    public String toString() {
        return "{" +
                "code=" + code +
                ", isDam=" + isDam +
                ", isZim=" + isZim +
                "}";
    }
}
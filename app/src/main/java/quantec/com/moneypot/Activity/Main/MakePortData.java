package quantec.com.moneypot.Activity.Main;

public class MakePortData {
    String pCode;
    long cost;
    int type;

    public void setpCode(String pCode) {
        this.pCode = pCode;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "{\"pCode\":"+pCode+",\"cost\":"+cost+",\"type\":"+type+"}";
    }
}

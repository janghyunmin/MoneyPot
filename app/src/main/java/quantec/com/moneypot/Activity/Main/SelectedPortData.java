package quantec.com.moneypot.Activity.Main;

public class SelectedPortData {

    String code;
    String descript;
    int isPot;
    String name;
    double rate;
    int type;

    @Override
    public String toString() {
        return "[{" +
                "code:" + code +
                ", descript:" + descript +
                ", isPot:" + isPot +
                ", name:" + name +
                ", rate:" + rate +
                ", type:" + type +
                "}]";
    }


    //    @Override
//    public String toString() {
//        return '[' +'{' +
//                "code:" + code + '\'' +
//                ", descript:'" + descript + '\'' +
//                ", isPot:" + isPot +
//                ", name:'" + name + '\'' +
//                ", rate:" + rate +
//                ", type:" + type +
//                '}'+
//                ']';
//    }

    public SelectedPortData(String code, String descript, int isPot, String name, double rate, int type) {
        this.code = code;
        this.descript = descript;
        this.isPot = isPot;
        this.name = name;
        this.rate = rate;
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public int getIsPot() {
        return isPot;
    }

    public void setIsPot(int isPot) {
        this.isPot = isPot;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

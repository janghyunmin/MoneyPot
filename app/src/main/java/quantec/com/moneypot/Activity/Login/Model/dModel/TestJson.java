package quantec.com.moneypot.Activity.Login.Model.dModel;

public class TestJson {

    long activeStep;
    String uid;
    String cid;

    public TestJson(long activeStep, String uid, String cid) {
        this.activeStep = activeStep;
        this.uid = uid;
        this.cid = cid;
    }

    @Override
    public String toString() {
        return "{"+"\"activeStep\":"+activeStep+",\"uid\":\""+uid +"\",\"cid\":\""+cid+"\"}";
    }
}

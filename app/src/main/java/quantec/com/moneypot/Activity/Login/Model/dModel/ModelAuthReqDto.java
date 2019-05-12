package quantec.com.moneypot.Activity.Login.Model.dModel;

public class ModelAuthReqDto {
    String authCode;
    String site;
    String uid;

    public ModelAuthReqDto(String authCode, String site, String uid) {
        this.authCode = authCode;
        this.site = site;
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "{"+"\"authCode\":"+authCode+",\"site\":\""+site +"\",\"uid\":\""+uid+"\"}";
    }
}

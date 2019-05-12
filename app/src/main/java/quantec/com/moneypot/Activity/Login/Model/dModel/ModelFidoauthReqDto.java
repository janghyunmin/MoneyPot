package quantec.com.moneypot.Activity.Login.Model.dModel;

public class ModelFidoauthReqDto {

    String authCode;
    String site;
    String uid;

    public ModelFidoauthReqDto(String authCode, String site, String uid) {
        this.authCode = authCode;
        this.site = site;
        this.uid = uid;
    }
}

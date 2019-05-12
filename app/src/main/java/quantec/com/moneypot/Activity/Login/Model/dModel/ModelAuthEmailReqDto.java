package quantec.com.moneypot.Activity.Login.Model.dModel;

public class ModelAuthEmailReqDto {

    String authCode;
    String mail;
    String title;
    String type;

    public ModelAuthEmailReqDto(String authCode, String mail, String title, String type) {
        this.authCode = authCode;
        this.mail = mail;
        this.title = title;
        this.type = type;
    }
}

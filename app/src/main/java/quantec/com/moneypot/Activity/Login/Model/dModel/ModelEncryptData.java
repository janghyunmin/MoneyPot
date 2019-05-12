package quantec.com.moneypot.Activity.Login.Model.dModel;

public class ModelEncryptData {
    String timestamp;
    String params;
    String returnUrl;

    public ModelEncryptData(String timestamp, String params, String returnUrl) {
        this.timestamp = timestamp;
        this.params = params;
        this.returnUrl = returnUrl;
    }

    @Override
    public String toString() {
        return "{"+"\"timestamp\":"+timestamp+",\"params\":\""+params +"\",\"returnUrl\":\""+returnUrl+"\"}";
    }
}

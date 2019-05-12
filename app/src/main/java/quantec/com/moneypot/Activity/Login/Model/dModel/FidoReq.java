package quantec.com.moneypot.Activity.Login.Model.dModel;

public class FidoReq {

    String authCode;
    String deviceId;
    String msg;
    String site;
    String status;
    String token;
    String type;
    String userId;

    public FidoReq(String authCode, String deviceId, String msg, String site, String status, String token, String type, String userId) {
        this.authCode = authCode;
        this.deviceId = deviceId;
        this.msg = msg;
        this.site = site;
        this.status = status;
        this.token = token;
        this.type = type;
        this.userId = userId;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

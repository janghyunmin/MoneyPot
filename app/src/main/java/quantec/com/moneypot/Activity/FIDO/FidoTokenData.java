package quantec.com.moneypot.Activity.FIDO;

public class FidoTokenData {

    String sub;
    String iss;
    String aud;
    int iat;
    int exp;
    String jti;
    String opcode;
    String id;
    String deviceid;
    String authcode;


    public FidoTokenData(String sub, String iss, String aud, int iat, int exp, String jti, String opcode, String id, String deviceid, String authcode) {
        this.sub = sub;
        this.iss = iss;
        this.aud = aud;
        this.iat = iat;
        this.exp = exp;
        this.jti = jti;
        this.opcode = opcode;
        this.id = id;
        this.deviceid = deviceid;
        this.authcode = authcode;
    }
}

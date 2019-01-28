package quantec.com.moneypot.Activity.Intro;

public class MyData {
    String aud;
    String role;
    String userLevel;
    String os;
    String nickName;
    String panalty;
    int iss;
    String userName;
    int exp;
    int iat;

    public MyData(String aud, String role, String userLevel, String os, String nickName, String panalty, int iss, String userName, int exp, int iat) {
        this.aud = aud;
        this.role = role;
        this.userLevel = userLevel;
        this.os = os;
        this.nickName = nickName;
        this.panalty = panalty;
        this.iss = iss;
        this.userName = userName;
        this.exp = exp;
        this.iat = iat;
    }
}

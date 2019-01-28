package quantec.com.moneypot.Activity.Intro;

public class User {
    String email;
    String password;
    String userId;

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }

    public User(String email, String password, String userId) {
        this.email = email;
        this.password = password;
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

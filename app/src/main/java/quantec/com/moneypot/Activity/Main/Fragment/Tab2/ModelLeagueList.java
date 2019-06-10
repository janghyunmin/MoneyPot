package quantec.com.moneypot.Activity.Main.Fragment.Tab2;

public class ModelLeagueList {

    int category;
    String userName;
    String stTitle;
    String stYield;
    String stCode;

    public ModelLeagueList(int category, String userName, String stTitle, String stYield, String stCode) {
        this.category = category;
        this.userName = userName;
        this.stTitle = stTitle;
        this.stYield = stYield;
        this.stCode = stCode;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStTitle() {
        return stTitle;
    }

    public void setStTitle(String stTitle) {
        this.stTitle = stTitle;
    }

    public String getStYield() {
        return stYield;
    }

    public void setStYield(String stYield) {
        this.stYield = stYield;
    }

    public String getStCode() {
        return stCode;
    }

    public void setStCode(String stCode) {
        this.stCode = stCode;
    }
}

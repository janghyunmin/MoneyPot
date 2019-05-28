package quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage;

public class ModelPortList {

    String stCode;
    String stname;
    String category;

    public ModelPortList(String stCode, String stname, String category) {
        this.stCode = stCode;
        this.stname = stname;
        this.category = category;
    }

    public String getStCode() {
        return stCode;
    }

    public void setStCode(String stCode) {
        this.stCode = stCode;
    }

    public String getStname() {
        return stname;
    }

    public void setStname(String stname) {
        this.stname = stname;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

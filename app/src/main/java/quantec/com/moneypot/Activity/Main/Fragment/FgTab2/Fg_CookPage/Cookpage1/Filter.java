package quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.Cookpage1;

public class Filter {
    public Filter() {
    }

    String name;
    String descript;
    String elName;

    public Filter(String name, String descript, String elName) {
        this.name = name;
        this.descript = descript;
        this.elName = elName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public String getElName() {
        return elName;
    }

    public void setElName(String elName) {
        this.elName = elName;
    }
}

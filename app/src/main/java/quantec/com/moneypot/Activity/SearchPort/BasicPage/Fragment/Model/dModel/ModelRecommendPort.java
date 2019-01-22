package quantec.com.moneypot.Activity.SearchPort.BasicPage.Fragment.Model.dModel;

public class ModelRecommendPort {

    String PortTitle;
    int PortCode;

    public ModelRecommendPort(String portTitle, int portCode) {
        PortTitle = portTitle;
        PortCode = portCode;
    }

    public String getPortTitle() {
        return PortTitle;
    }

    public void setPortTitle(String portTitle) {
        PortTitle = portTitle;
    }

    public int getPortCode() {
        return PortCode;
    }

    public void setPortCode(int portCode) {
        PortCode = portCode;
    }
}


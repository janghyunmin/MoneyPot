package quantec.com.moneypot.Activity.SearchPort.BasicPage.Fragment.Model.dModel;

public class ModelRecommendPort {

    String PortTitle;
    String PortCode;

    public ModelRecommendPort(String portTitle, String portCode) {
        PortTitle = portTitle;
        PortCode = portCode;
    }

    public String getPortTitle() {
        return PortTitle;
    }

    public void setPortTitle(String portTitle) {
        PortTitle = portTitle;
    }

    public String getPortCode() {
        return PortCode;
    }

    public void setPortCode(String portCode) {
        PortCode = portCode;
    }
}


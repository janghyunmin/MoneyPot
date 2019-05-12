package quantec.com.moneypot.ModelCommon.dModel;

public class ModelMyInquiryList {

    String title;
    String myContents;
    String ansContents;

    public ModelMyInquiryList(String title, String myContents, String ansContents) {
        this.title = title;
        this.myContents = myContents;
        this.ansContents = ansContents;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMyContents() {
        return myContents;
    }

    public void setMyContents(String myContents) {
        this.myContents = myContents;
    }

    public String getAnsContents() {
        return ansContents;
    }

    public void setAnsContents(String ansContents) {
        this.ansContents = ansContents;
    }
}

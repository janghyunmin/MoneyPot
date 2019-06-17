package quantec.com.moneypot.Activity.PotDetail;

public class ModelPotDetail {

    boolean empty;
    String title;
    String subTitle;
    String url;
    boolean addViewState;

    public ModelPotDetail(boolean empty, String title, String subTitle, String url, boolean addViewState) {
        this.empty = empty;
        this.title = title;
        this.subTitle = subTitle;
        this.url = url;
        this.addViewState = addViewState;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isAddViewState() {
        return addViewState;
    }

    public void setAddViewState(boolean addViewState) {
        this.addViewState = addViewState;
    }
}


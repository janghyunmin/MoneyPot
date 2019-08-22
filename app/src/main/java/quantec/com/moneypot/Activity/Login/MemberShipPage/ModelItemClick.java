package quantec.com.moneypot.Activity.Login.MemberShipPage;

public class ModelItemClick {

    boolean itemClick;
    String title;

    public ModelItemClick(boolean itemClick, String title) {
        this.itemClick = itemClick;
        this.title = title;
    }

    public boolean isItemClick() {
        return itemClick;
    }

    public void setItemClick(boolean itemClick) {
        this.itemClick = itemClick;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

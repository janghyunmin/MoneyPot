package quantec.com.moneypot.Activity.ActivityLifeChallenge;

public class ModelLifeCSelectList {

    String title;
    int category;

    public ModelLifeCSelectList(String title, int category) {
        this.title = title;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }
}

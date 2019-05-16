package quantec.com.moneypot.Activity.ActivityLifeChallenge;

public class ModelLifeCTextList {

    String Talk;
    String subTitle;
    int category;
    String time;

    public ModelLifeCTextList(String talk, String subTitle, int category, String time) {
        Talk = talk;
        this.subTitle = subTitle;
        this.category = category;
        this.time = time;
    }

    public String getTalk() {
        return Talk;
    }

    public void setTalk(String talk) {
        Talk = talk;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

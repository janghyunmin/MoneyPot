package quantec.com.moneypot.Activity.ActivityLifeChallenge;

public class ModelLifeCTextList {

    String Talk;
    String subTitle;
    String longSubTitle;
    int category;
    String time;

    public ModelLifeCTextList(String talk, String subTitle, String longSubTitle, int category, String time) {
        Talk = talk;
        this.subTitle = subTitle;
        this.longSubTitle = longSubTitle;
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

    public String getLongSubTitle() {
        return longSubTitle;
    }

    public void setLongSubTitle(String longSubTitle) {
        this.longSubTitle = longSubTitle;
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

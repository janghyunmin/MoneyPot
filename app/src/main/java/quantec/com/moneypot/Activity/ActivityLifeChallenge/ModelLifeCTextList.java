package quantec.com.moneypot.Activity.ActivityLifeChallenge;

public class ModelLifeCTextList {

    String Talk;
    int category;
    String time;


    public ModelLifeCTextList(String talk, int category, String time) {
        Talk = talk;
        this.category = category;
        this.time = time;
    }

    public String getTalk() {
        return Talk;
    }

    public void setTalk(String talk) {
        Talk = talk;
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

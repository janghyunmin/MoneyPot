package quantec.com.moneypot.Activity.ActivityLifeChallenge;

public class TmpChatBotTalkList {

    public String talk;
    public String time;
    public String subTitle;
    public String longSubTitle;
    public int category;

    public TmpChatBotTalkList(String talk, String time, String subTitle, String longSubTitle, int category) {
        this.talk = talk;
        this.time = time;
        this.subTitle = subTitle;
        this.longSubTitle = longSubTitle;
        this.category = category;
    }

    public String getTalk() {
        return talk;
    }

    public void setTalk(String talk) {
        this.talk = talk;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
}

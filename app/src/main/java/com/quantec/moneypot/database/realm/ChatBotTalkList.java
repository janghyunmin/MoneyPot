package com.quantec.moneypot.database.realm;

import io.realm.RealmObject;

public class ChatBotTalkList extends RealmObject {

    public String talk;
    public String time;
    public String subTitle;
    public String longSubTitle;
    public int category;

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

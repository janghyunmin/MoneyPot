package quantec.com.moneypot.Database.Realm;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.Required;

public class ChatBot extends RealmObject {

    @Ignore
    private int id;

    //chartState / 0:목표 있을때 차트 , 1:목표 없을때 차트
    private int chartState;

    private float basicPrice;

    private long monthlyPrice;

    private long selectYear;

    private long hadPrice;

    private long finalPrice;

    public RealmList<ChatBotTalkList> chatBotTalkLists;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RealmList<ChatBotTalkList> getChatBotTalkLists() {
        return chatBotTalkLists;
    }

    public void setChatBotTalkLists(RealmList<ChatBotTalkList> chatBotTalkLists) {
        this.chatBotTalkLists = chatBotTalkLists;
    }

    public int getChartState() {
        return chartState;
    }

    public void setChartState(int chartState) {
        this.chartState = chartState;
    }

    public float getBasicPrice() {
        return basicPrice;
    }

    public void setBasicPrice(float basicPrice) {
        this.basicPrice = basicPrice;
    }

    public long getMonthlyPrice() {
        return monthlyPrice;
    }

    public void setMonthlyPrice(long monthlyPrice) {
        this.monthlyPrice = monthlyPrice;
    }

    public long getSelectYear() {
        return selectYear;
    }

    public void setSelectYear(long selectYear) {
        this.selectYear = selectYear;
    }

    public long getHadPrice() {
        return hadPrice;
    }

    public void setHadPrice(long hadPrice) {
        this.hadPrice = hadPrice;
    }

    public long getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(long finalPrice) {
        this.finalPrice = finalPrice;
    }
}

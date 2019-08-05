package quantec.com.moneypot.Database.Realm;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import quantec.com.moneypot.Database.Realm.CommonList.AgeList;
import quantec.com.moneypot.Database.Realm.CommonList.ExpList;
import quantec.com.moneypot.Database.Realm.CommonList.GainList;
import quantec.com.moneypot.Database.Realm.CommonList.IncomeList;
import quantec.com.moneypot.Database.Realm.CommonList.PartnerList;
import quantec.com.moneypot.Database.Realm.CommonList.PurposeList;
import quantec.com.moneypot.Database.Realm.CommonList.TimeList;
import quantec.com.moneypot.Database.Realm.CommonList.TypeList;
import quantec.com.moneypot.Database.Realm.CommonList.WeightList;

public class CommonCode extends RealmObject {

    @Ignore
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RealmList<PartnerList> partnerLists;
    public RealmList<IncomeList> incomeLists;
    public RealmList<PurposeList> purposeLists;
    public RealmList<TimeList> timeLists;
    public RealmList<WeightList> weightLists;
    public RealmList<ExpList> expLists;
    public RealmList<TypeList> typeLists;
    public RealmList<GainList> gainLists;
    public RealmList<AgeList> ageLists;

    public RealmList<PartnerList> getPartnerLists() {
        return partnerLists;
    }

    public void setPartnerLists(RealmList<PartnerList> partnerLists) {
        this.partnerLists = partnerLists;
    }

    public RealmList<IncomeList> getIncomeLists() {
        return incomeLists;
    }

    public void setIncomeLists(RealmList<IncomeList> incomeLists) {
        this.incomeLists = incomeLists;
    }

    public RealmList<PurposeList> getPurposeLists() {
        return purposeLists;
    }

    public void setPurposeLists(RealmList<PurposeList> purposeLists) {
        this.purposeLists = purposeLists;
    }

    public RealmList<TimeList> getTimeLists() {
        return timeLists;
    }

    public void setTimeLists(RealmList<TimeList> timeLists) {
        this.timeLists = timeLists;
    }

    public RealmList<WeightList> getWeightLists() {
        return weightLists;
    }

    public void setWeightLists(RealmList<WeightList> weightLists) {
        this.weightLists = weightLists;
    }

    public RealmList<ExpList> getExpLists() {
        return expLists;
    }

    public void setExpLists(RealmList<ExpList> expLists) {
        this.expLists = expLists;
    }

    public RealmList<TypeList> getTypeLists() {
        return typeLists;
    }

    public void setTypeLists(RealmList<TypeList> typeLists) {
        this.typeLists = typeLists;
    }

    public RealmList<GainList> getGainLists() {
        return gainLists;
    }

    public void setGainLists(RealmList<GainList> gainLists) {
        this.gainLists = gainLists;
    }

    public RealmList<AgeList> getAgeLists() {
        return ageLists;
    }

    public void setAgeLists(RealmList<AgeList> ageLists) {
        this.ageLists = ageLists;
    }
}

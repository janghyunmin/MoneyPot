package quantec.com.moneypot.Database.Realm.accountData;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;

public class AccountData extends RealmObject {

    @Ignore
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RealmList<AccountDetail> accountDetails;

    public RealmList<AccountDetail> getAccountDetails() {
        return accountDetails;
    }

    public void setAccountDetails(RealmList<AccountDetail> accountDetails) {
        this.accountDetails = accountDetails;
    }
}

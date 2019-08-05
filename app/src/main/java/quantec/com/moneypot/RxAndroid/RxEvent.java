package quantec.com.moneypot.RxAndroid;

import android.os.Bundle;

public class RxEvent {

    public static final String REFRESH_POT_COOK="refresh_pot_cook";
    public static final String ZZIM_PORT_SELECT_ITEM="zzim_port_select_item";
    public static final String SEARCH_TRANS_PAGE = "search_teans_page";
    public static final String SEARCH_CLICK_ZZIM = "search_click_zzim";

    public static final String MOVED_POTCOOK_PAGE = "moved_potcook_page";

    public static final String REFRESH_MY_POT = "refresh_my_pot";

    private String action;
    private Bundle bundle;

    public RxEvent(String action, Bundle bundle) {
        this.action = action;
        this.bundle = bundle;
    }

    public String getActiion() {
        return action;
    }
    public void setActiion(String action) {
        this.action = action;
    }
    public Bundle getBundle() {
        return bundle;
    }
    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }
}

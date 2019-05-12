package quantec.com.moneypot.RxAndroid;

import android.os.Bundle;

public class RxEvent {

    public static final String ZZIM_PORT_MAKE_MYPORT="zzim_port_make_myport";
    public static final String ZZIM_PORT_MAKE_MYPORT_CLOSE="zzim_port_make_myport_close";
    public static final String ZZIM_PORT_SELECT_ITEM="zzim_port_select_item";
    public static final String ZZIM_PORT_MAKE_OK="zzim_port_make_ok";

    public static final String ZZIM_PORT_TRANS_PAGE="zzim_port_trans_page";
    public static final String ZZIM_PORT_LOAD="zzim_port_load";

    public static final String RANK_PORT_CHECK_OK="rank_port_check_ok";
    public static final String RANK_PORT_CHECK_NO="rank_port_check_no";

    public static final String ZZIM_PORT_DELETE_MODIFY="zzim_port_delete_modify";
    public static final String ZZIM_PORT_PRE_CLOSE="zzim_port_pre_close";

    public static final String SEARCH_TRANS_PAGE = "search_teans_page";
    public static final String SEARCH_CLICK_ZZIM = "search_click_zzim";

    public static final String COOK_PAGE3_MODIFY = "cook_page3_modify";
    public static final String COOK_PAGE_BASKET = "cook_page_basket";

    public static final String REFRESH_COOKP2 = "refresh_cookp2";

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

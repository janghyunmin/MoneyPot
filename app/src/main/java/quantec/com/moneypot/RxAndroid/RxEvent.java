package quantec.com.moneypot.RxAndroid;

import android.os.Bundle;

public class RxEvent {

//    public static final String SEARCH_PORT_FILTER_OPEN = "search_port_filter_open";
    public static final String SEARCH_PORT_FILTER_OK = "search_port_filter_ok";
    public static final String ZZIM_PORT_DELETE="zzim_port_delete";
    public static final String ZZIM_PORT_MAKE_MYPORT="zzim_port_make_myport";
    public static final String ZZIM_PORT_MAKE_MYPORT_CLOSE="zzim_port_make_myport_close";
    public static final String ZZIM_PORT_SEARCH_TRANS_PORT="zzim_port_search_trans_port";
    public static final String ZZIM_PORT_DELETE_FINISH="zzim_port_delete_finish";
    public static final String ZZIM_PORT_SELECT_ITEM="zzim_port_select_item";
    public static final String ZZIM_PORT_MAKE_OK="zzim_port_make_ok";

    public static final String ZZIM_PORT_TRANS_PAGE="zzim_port_trans_page";

    public static final String ZZIM_PORT_LOAD="zzim_port_load";
    public static final String ZZIM_PORT_OUT="zzim_port_out";

    public static final String RANK_PORT_CHECK_OK="rank_port_check_ok";
    public static final String RANK_PORT_CHECK_NO="rank_port_check_no";

    public static final String ZZIM_PORT_DELETE_MODIFY="zzim_port_delete_modify";

    public static final String ZZIM_PORT_DELETE_FINISH_BACK_BT="zzim_port_delete_finish_back_bt";

    public static final String ZZIM_PORT_CHECK_ITME_COUNT_AND_VIEW="zzim_port_check_item_count_and_view";

    public static final String MY_PORT_DELETE="my_port_delete";
    public static final String MY_PORT_DELETE_FINISH="my_port_delete_finish";

    public static final String MY_PORT_MODIFY_MOVE="my_port_modify_move";
    public static final String MY_PORT_DELETE_FINISH_BACK_BT="my_port_delete_finish_back_bt";

    public static final String ZZIM_PORT_PRE_OPEN="zzim_port_pre_open";
    public static final String ZZIM_PORT_PRE_CLOSE="zzim_port_pre_close";

    public static final String SEARCH_TRANS_PAGE = "search_teans_page";

    public static final String SEARCH_CLICK_ZZIM = "search_click_zzim";



    public static final String COOK_PAGE3_MODIFY = "cook_page3_modify";
    public static final String COOK_PAGE_BASKET = "cook_page_basket";


    public static final String REFRESH_COOKP2 = "tc";

//    public static final String PAGE = "page";

    public static final String COOK_DIOLOG = "cook_dialog";

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

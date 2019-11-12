package com.quantec.moneypot.rxandroid;

import android.os.Bundle;

public class RxEvent {

    public static final String REFRESH_POT_COOK="refresh_pot_cook";
    public static final String ZZIM_PORT_SELECT_ITEM="zzim_port_select_item";

    public static final String ZZIM_PORT_SELECT_ITEM2 = "zzim_port_select_item2";
    public static final String REFRESH_POT_COOK2="refresh_pot_cook2";

    public static final String SEARCH_TRANS_PAGE = "search_teans_page";
    public static final String SEARCH_CLICK_ZZIM = "search_click_zzim";

    public static final String MOVED_POTCOOK_PAGE = "moved_potcook_page";

    public static final String REFRESH_MY_POT = "refresh_my_pot";

    public static final String MARKET_ZZIM = "market_zzim";

    public static final String PRELIFECHART_ANIM = "prelifechart_anim";

    public static final String PRELIFECHART_ONEPAGE = "prelifechart_onepage";

    public static final String FILTER_VALUE = "filter_value";

    public static final String BACK_IMAGE = "back_image";

    public static final String PREFER_ITEM_DELETE = "prefer_item_delete";


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

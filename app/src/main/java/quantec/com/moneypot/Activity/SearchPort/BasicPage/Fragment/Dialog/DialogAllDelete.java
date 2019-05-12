package quantec.com.moneypot.Activity.SearchPort.BasicPage.Fragment.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import quantec.com.moneypot.R;

public class DialogAllDelete extends Dialog {

    Button RecentlySearchTab_cancle_bt, RecentlySearchTab_ok_bt;

    private View.OnClickListener mLeftClickListener;
    private View.OnClickListener mRightClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.dialog_activitysearch_recentlyalldelete);

        RecentlySearchTab_cancle_bt = findViewById(R.id.RecentlySearchTab_cancle_bt);
        RecentlySearchTab_ok_bt = findViewById(R.id.RecentlySearchTab_ok_bt);

        RecentlySearchTab_cancle_bt.setOnClickListener(mLeftClickListener);
        RecentlySearchTab_ok_bt.setOnClickListener(mRightClickListener);
    }

    public DialogAllDelete(Context context,
                           View.OnClickListener leftListener,
                           View.OnClickListener rightListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mLeftClickListener = leftListener;
        this.mRightClickListener = rightListener;
    }
}

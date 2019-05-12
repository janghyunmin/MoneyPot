package quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import quantec.com.moneypot.R;

public class DialogCookpage2AllDelete extends Dialog {

    Button tab1_dialog_all_cancle_bt, tab1_dialog_all_ok_bt;

    private View.OnClickListener mLeftClickListener;
    private View.OnClickListener mRightClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.dialog_fgtab2_cookpage2alldelete);

        tab1_dialog_all_cancle_bt = findViewById(R.id.tab1_dialog_all_cancle_bt);
        tab1_dialog_all_ok_bt = findViewById(R.id.tab1_dialog_all_ok_bt);

        tab1_dialog_all_cancle_bt.setOnClickListener(mLeftClickListener);
        tab1_dialog_all_ok_bt.setOnClickListener(mRightClickListener);

    }

    public DialogCookpage2AllDelete(Context context,
                              View.OnClickListener leftListener,
                              View.OnClickListener rightListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mLeftClickListener = leftListener;
        this.mRightClickListener = rightListener;
    }

}

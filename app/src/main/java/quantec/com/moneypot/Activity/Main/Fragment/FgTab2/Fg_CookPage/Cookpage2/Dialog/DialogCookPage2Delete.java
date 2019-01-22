package quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.Cookpage2.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import quantec.com.moneypot.R;

public class DialogCookPage2Delete extends Dialog {

    Button tab1_dialog_cancle_bt, tab1_dialog_ok_bt;
    TextView tab1_dialog_item;
    private String item;

    private View.OnClickListener mLeftClickListener;
    private View.OnClickListener mRightClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.dialog_fgtab2_cookpage2delete);

        tab1_dialog_cancle_bt = findViewById(R.id.tab1_dialog_cancle_bt);
        tab1_dialog_ok_bt = findViewById(R.id.tab1_dialog_ok_bt);
        tab1_dialog_item = findViewById(R.id.tab1_dialog_item);

        tab1_dialog_item.setText(item);

        tab1_dialog_cancle_bt.setOnClickListener(mLeftClickListener);
        tab1_dialog_ok_bt.setOnClickListener(mRightClickListener);

    }

    public DialogCookPage2Delete(Context context, String item,
                        View.OnClickListener leftListener,
                        View.OnClickListener rightListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.item = item;
        this.mLeftClickListener = leftListener;
        this.mRightClickListener = rightListener;
    }

}

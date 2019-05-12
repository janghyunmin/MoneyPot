package quantec.com.moneypot.Activity.PortProfileModify.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import quantec.com.moneypot.R;

public class DialogImageReset extends Dialog {

    Button port_profile_modify_image_reset_dialog_cancle_bt, port_profile_modify_image_reset_dialog_ok_bt;

    private View.OnClickListener mLeftClickListener;
    private View.OnClickListener mRightClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.dialog_activityportprofilemodify_imagereset);

        port_profile_modify_image_reset_dialog_cancle_bt = findViewById(R.id.port_profile_modify_image_reset_dialog_cancle_bt);
        port_profile_modify_image_reset_dialog_ok_bt = findViewById(R.id.port_profile_modify_image_reset_dialog_ok_bt);


        port_profile_modify_image_reset_dialog_cancle_bt.setOnClickListener(mLeftClickListener);
        port_profile_modify_image_reset_dialog_ok_bt.setOnClickListener(mRightClickListener);
    }
    public DialogImageReset(Context context,
                            View.OnClickListener leftListener,
                            View.OnClickListener rightListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mLeftClickListener = leftListener;
        this.mRightClickListener = rightListener;
    }
}

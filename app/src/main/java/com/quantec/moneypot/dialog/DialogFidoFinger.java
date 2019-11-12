package com.quantec.moneypot.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.quantec.moneypot.R;

public class DialogFidoFinger extends Dialog {

    private View.OnClickListener mLeftClickListener;
    private View.OnClickListener mRightClickListener;

    TextView fido_finger_resistBt, fido_finger_cancleBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.dialog_fido_finger);

        fido_finger_resistBt = findViewById(R.id.fido_finger_resistBt);
        fido_finger_cancleBt = findViewById(R.id.fido_finger_cancleBt);

        fido_finger_resistBt.setOnClickListener(mLeftClickListener);
        fido_finger_cancleBt.setOnClickListener(mRightClickListener);
    }

    public DialogFidoFinger(Context context,
                            View.OnClickListener leftListener,
                            View.OnClickListener rightListener){
        super(context, android.R.style.Theme_Translucent_NoTitleBar);

        this.mLeftClickListener = leftListener;
        this.mRightClickListener = rightListener;
    }

    @Override
    public void onBackPressed() {
    }
}
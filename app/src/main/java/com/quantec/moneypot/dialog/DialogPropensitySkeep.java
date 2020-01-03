package com.quantec.moneypot.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.quantec.moneypot.R;

public class DialogPropensitySkeep extends Dialog {

    TextView okBt, noBt;
    private View.OnClickListener okClickListener, cancleClickListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.dialog_propensityskeep);

        okBt = findViewById(R.id.okBt);
        okBt.setOnClickListener(okClickListener);

        noBt = findViewById(R.id.noBt);
        noBt.setOnClickListener(cancleClickListener);
    }

    public DialogPropensitySkeep(@NonNull Context context,
                                 View.OnClickListener okClickListener, View.OnClickListener cancleClickListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.okClickListener = okClickListener;
        this.cancleClickListener = cancleClickListener;
    }

    @Override
    public void onBackPressed() {
    }
}
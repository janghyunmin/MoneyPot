package com.quantec.moneypot.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.quantec.moneypot.R;

public class DialogExitApp extends Dialog {

    TextView okBt;
    private View.OnClickListener cancleClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.dialog_exit_app);

        okBt = findViewById(R.id.okBt);
        okBt.setOnClickListener(cancleClickListener);
    }

    public DialogExitApp(@NonNull Context context, View.OnClickListener cancleClickListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.cancleClickListener = cancleClickListener;
    }

    @Override
    public void onBackPressed() {
    }
}

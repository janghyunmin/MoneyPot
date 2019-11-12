package com.quantec.moneypot.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.quantec.moneypot.R;

public class DialogHelpInfo extends Dialog {

    TextView okBt, title, subTitle;
    private View.OnClickListener cancleClickListener;
    private String item1, item2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.dialog_help_info);

        okBt = findViewById(R.id.okBt);
        okBt.setOnClickListener(cancleClickListener);

        title = findViewById(R.id.title);
        subTitle = findViewById(R.id.subTitle);

        title.setText(item1);
        subTitle.setText(item2);
    }

    public DialogHelpInfo(@NonNull Context context, String item1, String item2,
                          View.OnClickListener cancleClickListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.item1 = item1;
        this.item2 = item2;
        this.cancleClickListener = cancleClickListener;
    }

    @Override
    public void onBackPressed() {
    }
}

package com.quantec.moneypot.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.quantec.moneypot.R;

public class DialogLoadingMakingPort extends Dialog {

    TextView port_loading_title;
    ImageView port_loading_image;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.dialog_loading_make_port);

        port_loading_title = findViewById(R.id.port_loading_title);
        port_loading_title.setText(title);

        port_loading_image = findViewById(R.id.port_loading_image);
        port_loading_image.setBackgroundResource(R.drawable.loding_logo);
    }

    public DialogLoadingMakingPort(Context context, String title){
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.title = title;
    }

    @Override
    public void onBackPressed() {
    }
}
package com.quantec.moneypot.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.quantec.moneypot.R;

public class DialogYieldFilter extends Dialog {

    private View.OnClickListener m1BtListen, m3BtListen, m6BtListen, mAllBtLiten, closeListen;
    private TextView m1Bt, m3Bt, m6Bt, mAllBt;
    private ImageView closeBt;
    private String Number;
    private Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.dialog_yieldfilter);

        closeBt = findViewById(R.id.closeBt);

        m1Bt = findViewById(R.id.m1Bt);
        m3Bt = findViewById(R.id.m3Bt);
        m6Bt = findViewById(R.id.m6Bt);
        mAllBt = findViewById(R.id.mAllBt);

        m1Bt.setOnClickListener(m1BtListen);
        m3Bt.setOnClickListener(m3BtListen);
        m6Bt.setOnClickListener(m6BtListen);
        mAllBt.setOnClickListener(mAllBtLiten);
        closeBt.setOnClickListener(closeListen);

        if(Number.equals("1")){
            m1Bt.setTextColor(context.getResources().getColor(R.color.c_7f61fd));
            m1Bt.setBackgroundResource(R.drawable.bottom_sheet_on);
        }else if(Number.equals("2")){
            m3Bt.setTextColor(context.getResources().getColor(R.color.c_7f61fd));
            m3Bt.setBackgroundResource(R.drawable.bottom_sheet_on);
        }else if(Number.equals("3")){
            m6Bt.setTextColor(context.getResources().getColor(R.color.c_7f61fd));
            m6Bt.setBackgroundResource(R.drawable.bottom_sheet_on);
        }else if(Number.equals("4")){
            mAllBt.setTextColor(context.getResources().getColor(R.color.c_7f61fd));
            mAllBt.setBackgroundResource(R.drawable.bottom_sheet_on);
        }
    }

    public DialogYieldFilter(Context context, String Number,
                             View.OnClickListener m1BtListen, View.OnClickListener m3BtListen,
                             View.OnClickListener m6BtListen, View.OnClickListener mAllBtLiten,
                             View.OnClickListener closeListen) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
        this.Number = Number;
        this.m1BtListen = m1BtListen;
        this.m3BtListen = m3BtListen;
        this.m6BtListen = m6BtListen;
        this.mAllBtLiten = mAllBtLiten;
        this.closeListen = closeListen;
    }
}

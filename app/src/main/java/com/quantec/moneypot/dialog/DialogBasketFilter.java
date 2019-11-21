package com.quantec.moneypot.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.quantec.moneypot.R;

public class DialogBasketFilter extends Dialog {

    private View.OnClickListener selectedAppBt, selectedAccountBt, selectedProductBt;
    private TextView allBt, singleBt, packBt;
    private String filterNumber;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.dialog_basketfilter);

        allBt = findViewById(R.id.allBt);
        singleBt = findViewById(R.id.singleBt);
        packBt = findViewById(R.id.packBt);

        allBt.setOnClickListener(selectedAppBt);
        singleBt.setOnClickListener(selectedAccountBt);
        packBt.setOnClickListener(selectedProductBt);

        allBt.setTextColor(context.getResources().getColor(R.color.c_7f61fd));

        if(filterNumber.equals("01")){
            allBt.setTextColor(context.getResources().getColor(R.color.c_7f61fd));
            singleBt.setTextColor(context.getResources().getColor(R.color.text_black_color));
            packBt.setTextColor(context.getResources().getColor(R.color.text_black_color));
        }else if(filterNumber.equals("02")){
            singleBt.setTextColor(context.getResources().getColor(R.color.c_7f61fd));
            allBt.setTextColor(context.getResources().getColor(R.color.text_black_color));
            packBt.setTextColor(context.getResources().getColor(R.color.text_black_color));
        }else if(filterNumber.equals("03")){
            packBt.setTextColor(context.getResources().getColor(R.color.c_7f61fd));
            allBt.setTextColor(context.getResources().getColor(R.color.text_black_color));
            singleBt.setTextColor(context.getResources().getColor(R.color.text_black_color));

        }
    }

    public DialogBasketFilter(Context context, String filterNumber,
                              View.OnClickListener selectedAppBt, View.OnClickListener selectedAccountBt, View.OnClickListener selectedProductBt) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
        this.filterNumber = filterNumber;
        this.selectedAppBt = selectedAppBt;
        this.selectedAccountBt = selectedAccountBt;
        this.selectedProductBt = selectedProductBt;
    }
}

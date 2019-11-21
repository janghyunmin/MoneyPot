package com.quantec.moneypot.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.quantec.moneypot.R;

public class DialogInquiryCategory extends Dialog {

    private View.OnClickListener selectedAppBt, selectedAccountBt, selectedProductBt, selectedYieldBt, selectedOtherBt;
    private TextView appBt, accountBt, productBt, yieldBt, otherBt;
    private String categoryNumber;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.dialog_inquirycategory);


        appBt = findViewById(R.id.appBt);
        accountBt = findViewById(R.id.accountBt);
        productBt = findViewById(R.id.productBt);
        yieldBt = findViewById(R.id.yieldBt);
        otherBt = findViewById(R.id.otherBt);

        appBt.setOnClickListener(selectedAppBt);
        accountBt.setOnClickListener(selectedAccountBt);
        productBt.setOnClickListener(selectedProductBt);
        yieldBt.setOnClickListener(selectedYieldBt);
        otherBt.setOnClickListener(selectedOtherBt);


        if(categoryNumber.equals("01")){
            appBt.setTextColor(context.getResources().getColor(R.color.c_7f61fd));
        }else if(categoryNumber.equals("02")){
            accountBt.setTextColor(context.getResources().getColor(R.color.c_7f61fd));
        }else if(categoryNumber.equals("03")){
            productBt.setTextColor(context.getResources().getColor(R.color.c_7f61fd));
        }else if(categoryNumber.equals("04")){
            yieldBt.setTextColor(context.getResources().getColor(R.color.c_7f61fd));
        }else if(categoryNumber.equals("05")){
            otherBt.setTextColor(context.getResources().getColor(R.color.c_7f61fd));
        }


    }


    public DialogInquiryCategory(Context context, String categoryNumber,
                             View.OnClickListener selectedAppBt, View.OnClickListener selectedAccountBt, View.OnClickListener selectedProductBt,
                             View.OnClickListener selectedYieldBt, View.OnClickListener selectedOtherBt) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
        this.categoryNumber = categoryNumber;
        this.selectedAppBt = selectedAppBt;
        this.selectedAccountBt = selectedAccountBt;
        this.selectedProductBt = selectedProductBt;
        this.selectedYieldBt = selectedYieldBt;
        this.selectedOtherBt = selectedOtherBt;
    }
}


package com.quantec.moneypot.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.quantec.moneypot.R;

public class DialogWeekFinance extends Dialog {

    ImageView closeBt;
    TextView subTitle1;
    private View.OnClickListener closeBtClickListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.dialog_propensityweek);

        closeBt = findViewById(R.id.closeBt);
        closeBt.setOnClickListener(closeBtClickListener);

        subTitle1 = findViewById(R.id.subTitle1);

        String str = "취약 금융소비자(고령자, 은퇴자, 주부, 정상적인 판단에 장애가 있는 투자자, 금융투자상품 투자 무경험자, 문맹자 등)에 해당하는 경우 투자시 유의사항을 다른 정보보다 우선하여 설명 받으실 수 있습니다. 해당 항목에 체크하여 주시기 바랍니다.";
        SpannableStringBuilder ssb = new SpannableStringBuilder(str);
        ssb.setSpan(new ForegroundColorSpan(Color.parseColor("#7f61fd")), 8, 66, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        subTitle1.setText(ssb);

    }

    public DialogWeekFinance(@NonNull Context context,
                                 View.OnClickListener closeBtClickListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.closeBtClickListener = closeBtClickListener;
    }

    @Override
    public void onBackPressed() {
    }
}
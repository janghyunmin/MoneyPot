package quantec.com.moneypot.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import quantec.com.moneypot.R;

public class DialogBottomSheet extends Dialog {

    private View.OnClickListener sktListen, ktListen, lgListen, sktRLiten, ktRListen, lgRListen, closeListen;
    private TextView sktBt, ktBt, lgBt, sktRBt, ktRBt, lgRBt;
    private ImageView closeBt;
    private String agencyNumber;
    private Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.dialog_bottom_sheet);

        closeBt = findViewById(R.id.closeBt);

        sktBt = findViewById(R.id.sktBt);
        ktBt = findViewById(R.id.ktBt);
        lgBt = findViewById(R.id.lgBt);
        sktRBt = findViewById(R.id.sktRBt);
        ktRBt = findViewById(R.id.ktRBt);
        lgRBt = findViewById(R.id.lgRBt);

        sktBt.setOnClickListener(sktListen);
        ktBt.setOnClickListener(ktListen);
        lgBt.setOnClickListener(lgListen);
        sktRBt.setOnClickListener(sktRLiten);
        ktRBt.setOnClickListener(ktRListen);
        lgRBt.setOnClickListener(lgRListen);
        closeBt.setOnClickListener(closeListen);

        sktBt.setBackgroundResource(R.drawable.bottom_sheet_line);
        ktBt.setBackgroundResource(R.drawable.bottom_sheet_line);
        lgBt.setBackgroundResource(R.drawable.bottom_sheet_line);
        sktRBt.setBackgroundResource(R.drawable.bottom_sheet_line);
        ktRBt.setBackgroundResource(R.drawable.bottom_sheet_line);
        lgRBt.setBackgroundResource(R.drawable.bottom_sheet_line);
        closeBt.setBackgroundResource(R.drawable.bottom_sheet_line);

        if(agencyNumber.equals("01")){
            sktBt.setTextColor(context.getResources().getColor(R.color.text_red_color));
            sktBt.setBackgroundResource(R.drawable.bottom_sheet_on);
        }else if(agencyNumber.equals("02")){
            ktBt.setTextColor(context.getResources().getColor(R.color.text_red_color));
            ktBt.setBackgroundResource(R.drawable.bottom_sheet_on);
        }else if(agencyNumber.equals("03")){
            lgBt.setTextColor(context.getResources().getColor(R.color.text_red_color));
            lgBt.setBackgroundResource(R.drawable.bottom_sheet_on);
        }else if(agencyNumber.equals("04")){
            sktRBt.setTextColor(context.getResources().getColor(R.color.text_red_color));
            sktRBt.setBackgroundResource(R.drawable.bottom_sheet_on);
        }else if(agencyNumber.equals("05")){
            ktRBt.setTextColor(context.getResources().getColor(R.color.text_red_color));
            ktRBt.setBackgroundResource(R.drawable.bottom_sheet_on);
        }else if(agencyNumber.equals("06")){
            lgRBt.setTextColor(context.getResources().getColor(R.color.text_red_color));
            lgRBt.setBackgroundResource(R.drawable.bottom_sheet_on);
        }

    }

    public DialogBottomSheet(Context context, String agencyNumber,
                             View.OnClickListener sktListen, View.OnClickListener ktListen, View.OnClickListener lgListen,
                             View.OnClickListener sktRLiten, View.OnClickListener ktRListen, View.OnClickListener lgRListen,
                             View.OnClickListener closeListen) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
        this.agencyNumber = agencyNumber;
        this.sktListen = sktListen;
        this.ktListen = ktListen;
        this.lgListen = lgListen;
        this.sktRLiten = sktRLiten;
        this.ktRListen = ktRListen;
        this.lgRListen = lgRListen;
        this.closeListen = closeListen;
    }

}

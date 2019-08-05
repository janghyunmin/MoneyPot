package quantec.com.moneypot.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;

import quantec.com.moneypot.R;

public class DialogMyPotFilter extends Dialog {

    private View.OnClickListener mostBtListen, pastBtListen, profitBtListen, dateBtListen;
    private TextView mostBt, pastBt, profitBt, dateBt;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.dialog_mypot_filter);

        mostBt = findViewById(R.id.mostBt);
        pastBt = findViewById(R.id.pastBt);
        profitBt = findViewById(R.id.profitBt);
        dateBt = findViewById(R.id.dateBt);


        mostBt.setOnClickListener(mostBtListen);
        pastBt.setOnClickListener(pastBtListen);
        profitBt.setOnClickListener(profitBtListen);
        dateBt.setOnClickListener(dateBtListen);

        mostBt.setBackgroundResource(R.drawable.bottom_sheet_line);
        pastBt.setBackgroundResource(R.drawable.bottom_sheet_line);
        profitBt.setBackgroundResource(R.drawable.bottom_sheet_line);
        dateBt.setBackgroundResource(R.drawable.bottom_sheet_line);
    }

    public DialogMyPotFilter(Context context,
                             View.OnClickListener mostBtListen, View.OnClickListener pastBtListen, View.OnClickListener profitBtListen, View.OnClickListener dateBtListen) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mostBtListen = mostBtListen;
        this.pastBtListen = pastBtListen;
        this.profitBtListen = profitBtListen;
        this.dateBtListen = dateBtListen;
    }

}
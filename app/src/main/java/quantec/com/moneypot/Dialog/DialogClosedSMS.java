package quantec.com.moneypot.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import quantec.com.moneypot.R;

public class DialogClosedSMS extends Dialog {

    TextView okBt, cancleBt;

    private View.OnClickListener cancleClickListener;
    private View.OnClickListener okClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.dialog_closed_sms);

        okBt = findViewById(R.id.okBt);
        cancleBt = findViewById(R.id.cancleBt);

        cancleBt.setOnClickListener(cancleClickListener);
        okBt.setOnClickListener(okClickListener);
    }

    public DialogClosedSMS(Context context,
                           View.OnClickListener cancleClickListener,
                           View.OnClickListener okClickListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.cancleClickListener = cancleClickListener;
        this.okClickListener = okClickListener;
    }

    @Override
    public void onBackPressed() {
    }
}

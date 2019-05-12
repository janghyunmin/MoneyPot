package quantec.com.moneypot.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import quantec.com.moneypot.R;

public class DialogRePassword extends Dialog {

    TextView okBt;
    private View.OnClickListener mLeftClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.dialog_repassword);

        okBt = findViewById(R.id.okBt);
        okBt.setOnClickListener(mLeftClickListener);
    }

    public DialogRePassword(@NonNull Context context,  View.OnClickListener leftListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mLeftClickListener = leftListener;
    }

    @Override
    public void onBackPressed() {
    }
}

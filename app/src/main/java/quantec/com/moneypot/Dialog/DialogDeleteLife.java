package quantec.com.moneypot.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;

import quantec.com.moneypot.R;

public class DialogDeleteLife extends Dialog {

    Button cancleBt, okBt;

    private View.OnClickListener cancleListener;
    private View.OnClickListener okListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.dialog_deletelife);

        cancleBt = findViewById(R.id.cancleBt);
        okBt = findViewById(R.id.okBt);

        cancleBt.setOnClickListener(cancleListener);
        okBt.setOnClickListener(okListener);
    }

    public DialogDeleteLife(Context context,
                           View.OnClickListener leftListener,
                           View.OnClickListener rightListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.cancleListener = leftListener;
        this.okListener = rightListener;
    }

}

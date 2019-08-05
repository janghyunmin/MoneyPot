package quantec.com.moneypot.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import quantec.com.moneypot.R;

public class DialogModifyPotDelete extends Dialog {

    Button cancleBt, okBt;

    private View.OnClickListener mLeftClickListener;
    private View.OnClickListener mRightClickListener;
    TextView subtitle;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.dialog_makepot_modifypotdelete);

        cancleBt = findViewById(R.id.cancleBt);
        okBt = findViewById(R.id.okBt);
        subtitle = findViewById(R.id.subtitle);

        cancleBt.setOnClickListener(mLeftClickListener);
        okBt.setOnClickListener(mRightClickListener);

        subtitle.setText(title+" 가 삭제됩니다.");

    }

    public DialogModifyPotDelete(Context context,
                                 View.OnClickListener leftListener,
                                 View.OnClickListener rightListener,
                                 String title) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mLeftClickListener = leftListener;
        this.mRightClickListener = rightListener;
        this.title = title;
    }
}

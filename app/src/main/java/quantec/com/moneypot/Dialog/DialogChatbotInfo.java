package quantec.com.moneypot.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import quantec.com.moneypot.R;

public class DialogChatbotInfo extends Dialog {

    ImageView closeBt;
    private View.OnClickListener cancleClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.dialog_chatbotinfo);

        closeBt = findViewById(R.id.closeBt);
        closeBt.setOnClickListener(cancleClickListener);

    }

    public DialogChatbotInfo(@NonNull Context context, View.OnClickListener cancleClickListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.cancleClickListener = cancleClickListener;
    }

    @Override
    public void onBackPressed() {
    }
}

package quantec.com.moneypot.Activity.FinishMakePort.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import quantec.com.moneypot.R;

public class DialogMyPortName extends Dialog {

    private View.OnClickListener mLeftClickListener;
    private View.OnClickListener mRightClickListener;


    EditText editText;
    Button my_port_name_cancle_bt, my_port_name_ok_bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.dialog_activityfinishmakeport_myportname);

        editText = findViewById(R.id.my_port_name_text);
        my_port_name_cancle_bt = findViewById(R.id.my_port_name_cancle_bt);
        my_port_name_ok_bt = findViewById(R.id.my_port_name_ok_bt);

        my_port_name_cancle_bt.setOnClickListener(mLeftClickListener);
        my_port_name_ok_bt.setOnClickListener(mRightClickListener);


        editText.requestFocus();

        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

    }
    public String callFuction(){

        return editText.getText().toString();
    }

    public DialogMyPortName(Context context,
                      View.OnClickListener leftListener,
                      View.OnClickListener rightListener){
        super(context);
        this.mLeftClickListener = leftListener;
        this.mRightClickListener = rightListener;
    }
}
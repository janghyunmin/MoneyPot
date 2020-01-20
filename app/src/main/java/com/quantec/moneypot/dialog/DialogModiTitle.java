package com.quantec.moneypot.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.quantec.moneypot.R;

import java.util.Objects;

public class DialogModiTitle extends Dialog{

    TextView okBt, cancleBt, subTitle;
    private View.OnClickListener cancleClickListener, okClickListener;
    EditText editTitle;

    private CustomDialogListener customDialogListener;

    //인터페이스 설정
    public interface CustomDialogListener{
        void onPositiveClicked(String name);
        void onNegativeClicked();
    }

    //호출할 리스너 초기화
    public void setDialogListener(CustomDialogListener customDialogListener){
        this.customDialogListener = customDialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        setContentView(R.layout.dialog_modititle);

        okBt = findViewById(R.id.okBt);
        okBt.setOnClickListener(okClickListener);

        cancleBt = findViewById(R.id.cancleBt);
        cancleBt.setOnClickListener(cancleClickListener);

        subTitle = findViewById(R.id.subTitle);
        editTitle = findViewById(R.id.editTitle);

        editTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {

                if(s.length() == 0){
                    okBt.setBackground(getContext().getResources().getDrawable(R.drawable.dialog_custom_unselectbt));
                    okBt.setTextColor(getContext().getResources().getColor(R.color.c_cccccc));
                    okBt.setEnabled(false);
                }else{
                    okBt.setBackground(getContext().getResources().getDrawable(R.drawable.dialog_custom_selectbt));
                    okBt.setTextColor(getContext().getResources().getColor(R.color.c_ffffff));
                    okBt.setEnabled(true);
                }
            }
        });

        okBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTitle.getText().toString();

                //인터페이스의 함수를 호출하여 변수에 저장된 값들을 Activity로 전달
                customDialogListener.onPositiveClicked(name);
            }
        });

        cancleBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialogListener.onNegativeClicked();
            }
        });

    }

    public DialogModiTitle(@NonNull Context context, View.OnClickListener okClickListener, View.OnClickListener cancleClickListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.okClickListener = okClickListener;
        this.cancleClickListener = cancleClickListener;
    }

    @Override
    public void onBackPressed() {
    }

}
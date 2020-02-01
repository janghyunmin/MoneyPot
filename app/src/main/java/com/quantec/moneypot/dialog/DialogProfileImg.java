package com.quantec.moneypot.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.quantec.moneypot.R;

public class DialogProfileImg extends Dialog {

    TextView okBt, noBt, desc, name1, name2, name3, name4, preName;
    private View.OnClickListener okClickListener, cancleClickListener;
    int img;
    int selectedImg;

    OnDialogResult onDialogResult;
    ImageView img1, img2, img3, img4, preImg;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.dialog_profileimg);

        okBt = findViewById(R.id.okBt);
        okBt.setOnClickListener(cancleClickListener);

        noBt = findViewById(R.id.noBt);
        noBt.setOnClickListener(okClickListener);

        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        img4 = findViewById(R.id.img4);

        desc = findViewById(R.id.desc);
        name1 = findViewById(R.id.name1);
        name2 = findViewById(R.id.name2);
        name3 = findViewById(R.id.name3);
        name4 = findViewById(R.id.name4);

        selectedImg = img;
        if(img == 1){
            preImg = img1;
            preName = name1;
            img1.setBackgroundResource(R.drawable.char_selected_1);
            img2.setBackgroundResource(R.drawable.char_deselected_2);
            img3.setBackgroundResource(R.drawable.char_deselected_3);
            img4.setBackgroundResource(R.drawable.char_deselected_4);
            name1.setTextColor(context.getResources().getColor(R.color.c_3d3f42));
            desc.setText("포푸리는 금융에 눈을 일찍 뜬 캐릭터로 얼굴 한가득 동전을 모으는 캐릭터입니다. 감정이 격해질 때마다 동전이 톡톡 튀어나오는 것이 특징입니다.");
        }else if(img == 2){
            preImg = img2;
            preName = name2;
            img2.setBackgroundResource(R.drawable.char_selected_2);
            img1.setBackgroundResource(R.drawable.char_deselected_1);
            img3.setBackgroundResource(R.drawable.char_deselected_3);
            img4.setBackgroundResource(R.drawable.char_deselected_4);
            name1.setTextColor(context.getResources().getColor(R.color.c_3d3f42));
            desc.setText("포트지니는 금융에 눈을 일찍 뜬 캐릭터로 얼굴 한가득 동전을 모으는 캐릭터입니다. 감정이 격해질 때마다 동전이 톡톡 튀어나오는 것이 특징입니다.");
        }else if(img == 3){
            preImg = img3;
            preName = name3;
            img3.setBackgroundResource(R.drawable.char_selected_3);
            img2.setBackgroundResource(R.drawable.char_deselected_2);
            img1.setBackgroundResource(R.drawable.char_deselected_1);
            img4.setBackgroundResource(R.drawable.char_deselected_4);
            name1.setTextColor(context.getResources().getColor(R.color.c_3d3f42));
            desc.setText("쇼니는 금융에 눈을 일찍 뜬 캐릭터로 얼굴 한가득 동전을 모으는 캐릭터입니다. 감정이 격해질 때마다 동전이 톡톡 튀어나오는 것이 특징입니다.");
        }else{
            preImg = img4;
            preName = name4;
            img4.setBackgroundResource(R.drawable.char_selected_4);
            img3.setBackgroundResource(R.drawable.char_deselected_3);
            img2.setBackgroundResource(R.drawable.char_deselected_2);
            img1.setBackgroundResource(R.drawable.char_deselected_1);
            name1.setTextColor(context.getResources().getColor(R.color.c_3d3f42));
            desc.setText("모니코니는 금융에 눈을 일찍 뜬 캐릭터로 얼굴 한가득 동전을 모으는 캐릭터입니다. 감정이 격해질 때마다 동전이 톡톡 튀어나오는 것이 특징입니다.");
        }

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedimg(1);
            }
        });

        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedimg(2);
            }
        });

        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedimg(3);
            }
        });

        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedimg(4);
            }
        });

        okBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (onDialogResult != null) {
                    onDialogResult.finish(selectedImg);
                }
            }
        });

    }//onCreate 끝


    void selectedimg(int img){
        selectedImg = img;
        preName.setTextColor(context.getResources().getColor(R.color.c_cccccc));
        if(img == 1){
            name1.setTextColor(context.getResources().getColor(R.color.c_3d3f42));
            img1.setBackgroundResource(R.drawable.char_selected_1);
            if(preImg != img1){
                preImg.setBackgroundResource(R.drawable.char_deselected_1);
            }
            preImg = img1;
            preName = name1;
        }else if(img == 2){
            name2.setTextColor(context.getResources().getColor(R.color.c_3d3f42));
            img2.setBackgroundResource(R.drawable.char_selected_2);
            if(preImg != img2){
                preImg.setBackgroundResource(R.drawable.char_deselected_2);
            }
            preImg = img2;
            preName = name2;
        }else if(img == 3){
            name3.setTextColor(context.getResources().getColor(R.color.c_3d3f42));
            img3.setBackgroundResource(R.drawable.char_selected_3);
            if(preImg != img3){
                preImg.setBackgroundResource(R.drawable.char_deselected_3);
            }
            preImg = img3;
            preName = name3;
        }else{
            name4.setTextColor(context.getResources().getColor(R.color.c_3d3f42));
            img4.setBackgroundResource(R.drawable.char_selected_4);
            if(preImg != img4){
                preImg.setBackgroundResource(R.drawable.char_deselected_4);
            }
            preImg = img4;
            preName = name4;
        }
    }

    public DialogProfileImg(@NonNull Context context, int img, View.OnClickListener okClickListener, View.OnClickListener cancleClickListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
        this.img = img;
        this.okClickListener = okClickListener;
        this.cancleClickListener = cancleClickListener;
    }

    @Override
    public void onBackPressed() {
    }

    public interface OnDialogResult{
        void finish(int img);
    }

    public void setOnDialogResult(OnDialogResult onDialogResult) {
        this.onDialogResult = onDialogResult;
    }
}

package com.quantec.moneypot.activity.center.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.quantec.moneypot.activity.center.ActivityInquiry;
import com.quantec.moneypot.dialog.DialogInquiryCategory;
import com.quantec.moneypot.R;

public class FgInquiryWrite extends Fragment implements View.OnClickListener {

    TextView category, okBt, contentsCount;
    EditText contents, email;
    ImageView agreeBt;

    ActivityInquiry activityInquiry;

    boolean agreeState = false;

    private DialogInquiryCategory dialogInquiryCategory;
    String categoryNumber = "00";

    public FgInquiryWrite() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_inquirywrite, container, false);

        category = view.findViewById(R.id.category);
        okBt = view.findViewById(R.id.okBt);
        contents = view.findViewById(R.id.contents);
        email = view.findViewById(R.id.email);
        agreeBt = view.findViewById(R.id.agreeBt);
        contentsCount = view.findViewById(R.id.contentsCount);

        category.setOnClickListener(this);
        agreeBt.setOnClickListener(this);

        initializeViews();

        return view;
    }

    private void initializeViews(){
        activityInquiry = (ActivityInquiry) getActivity();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof ActivityInquiry) {
            activityInquiry = (ActivityInquiry) context;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        okBt.setBackgroundResource(R.drawable.rectangle_dark_gray);
        okBt.setEnabled(false);

        agreeBt.setBackgroundResource(R.drawable.btn_back_off_whitegray);
        contentsCount.setText("0 / 3000");


        contents.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                contentsCount.setText(s.length() +" / 3000");
                checkedOkBt();
            }
        });
    }

    private View.OnClickListener selectedAppBt = new View.OnClickListener() {
        public void onClick(View v) {

            category.setText("앱 이용 문의");
            category.setTextColor(getResources().getColor(R.color.normal_text_color));
            categoryNumber = "01";
            dialogInquiryCategory.dismiss();
            checkedOkBt();

        }
    };
    private View.OnClickListener selectedAccountBt = new View.OnClickListener() {
        public void onClick(View v) {

            category.setText("계좌 관련 문의");
            category.setTextColor(getResources().getColor(R.color.normal_text_color));
            categoryNumber = "02";
            dialogInquiryCategory.dismiss();
            checkedOkBt();

        }
    };
    private View.OnClickListener selectedProductBt = new View.OnClickListener() {
        public void onClick(View v) {

            category.setText("상품 관련 문의");
            category.setTextColor(getResources().getColor(R.color.normal_text_color));
            categoryNumber = "03";
            dialogInquiryCategory.dismiss();
            checkedOkBt();

        }
    };
    private View.OnClickListener selectedYieldBt = new View.OnClickListener() {
        public void onClick(View v) {


            category.setText("수익률 관련 문의");
            category.setTextColor(getResources().getColor(R.color.normal_text_color));
            categoryNumber = "04";
            dialogInquiryCategory.dismiss();
            checkedOkBt();

        }
    };
    private View.OnClickListener selectedOtherBt = new View.OnClickListener() {
        public void onClick(View v) {


            category.setText("기타 문의");
            category.setTextColor(getResources().getColor(R.color.normal_text_color));
            categoryNumber = "05";
            dialogInquiryCategory.dismiss();
            checkedOkBt();

        }
    };


    private void checkedOkBt(){

        if(!categoryNumber.equals("00") && contents.getText().length() > 0 && agreeState){

            okBt.setEnabled(true);
            okBt.setBackgroundResource(R.drawable.rectangle_red_5dp);
            okBt.setTextColor(activityInquiry.getResources().getColor(R.color.text_white_color));


        }
        else{

            okBt.setEnabled(false);
            okBt.setBackgroundResource(R.drawable.rectangle_dark_gray);
            okBt.setTextColor(activityInquiry.getResources().getColor(R.color.text_gray_color));

        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.category:

                dialogInquiryCategory = new DialogInquiryCategory(activityInquiry, categoryNumber, selectedAppBt, selectedAccountBt,
                        selectedProductBt, selectedYieldBt, selectedOtherBt);
                dialogInquiryCategory.show();

                break;

            case R.id.agreeBt:

                if(agreeState){
                    agreeState = false;
                    agreeBt.setBackgroundResource(R.drawable.btn_back_off_whitegray);
                    checkedOkBt();

                }
                else{
                    agreeState = true;
                    agreeBt.setBackgroundResource(R.drawable.btn_checkbox_on_blue);
                    checkedOkBt();
                }

                break;

        }

    }

}

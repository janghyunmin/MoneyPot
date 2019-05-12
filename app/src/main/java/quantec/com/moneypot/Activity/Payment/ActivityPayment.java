package quantec.com.moneypot.Activity.Payment;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListPopupWindow;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import quantec.com.moneypot.Activity.Payment.AdapterListPopUp.ListPopupWindowAdapter;
import quantec.com.moneypot.R;
import quantec.com.moneypot.databinding.ActivityPaymentBinding;

public class ActivityPayment extends AppCompatActivity {

    Long TotalGetCash;
    ActivityPaymentBinding paymentBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        paymentBinding = DataBindingUtil.setContentView(this, R.layout.activity_payment);
        paymentBinding.setPaymentPage(this);

        Intent intent = getIntent();
        TotalGetCash = intent.getLongExtra("mincost",0);
        paymentBinding.paymentPageCash.setText(""+intent.getLongExtra("mincost",0));
        paymentBinding.paymentPageCashChangeText.setText(TotalGetCash+"만원부터 입력 가능합니다.");

        paymentBinding.paymentPageCashEdittextLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                paymentBinding.paymentPageCashEdittextLayout2.setVisibility(View.INVISIBLE);
                paymentBinding.paymentPageCashEdittextLayout.setVisibility(View.VISIBLE);

                paymentBinding.paymentPageCashChangeTextImage.setVisibility(View.VISIBLE);
                paymentBinding.paymentPageCashChangeText.setVisibility(View.VISIBLE);

                paymentBinding.paymentPageCashEdittext.post(new Runnable() {
                    @Override
                    public void run() {
                        paymentBinding.paymentPageCashEdittext.setFocusableInTouchMode(true);
                        paymentBinding.paymentPageCashEdittext.requestFocus();
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(paymentBinding.paymentPageCashEdittext,0);
                    }
                });
            }
        });

        paymentBinding.paymentPageSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showListPopupWindow(v);
            }
        });

        paymentBinding.paymentPageBackBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        paymentBinding.paymentPageCashEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 13) {
                    Toast.makeText(ActivityPayment.this, "허용치를 넘겼습니다",Toast.LENGTH_SHORT).show();
                }else{
                    if (!s.toString().equals("")) {

                        if (TotalGetCash <= Long.parseLong(s.toString())) {
                            paymentBinding.paymentPageCashChangeTextImage.setVisibility(View.INVISIBLE);
                            paymentBinding.paymentPageCashChangeText.setVisibility(View.INVISIBLE);
                        } else {
                            paymentBinding.paymentPageCashChangeTextImage.setVisibility(View.VISIBLE);
                            paymentBinding.paymentPageCashChangeText.setVisibility(View.VISIBLE);
                        }
                    } else {
                        paymentBinding.paymentPageCashChangeTextImage.setVisibility(View.VISIBLE);
                        paymentBinding.paymentPageCashChangeText.setVisibility(View.VISIBLE);
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        paymentBinding.paymentPageBottomBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void showListPopupWindow(View anchorView) {
        final ListPopupWindow listPopupWindow = new ListPopupWindow(this);
        listPopupWindow.setWidth(1300);
        List<String> sampleData = new ArrayList<>();
        sampleData.add("신한금융투자");
        sampleData.add("한화투자증권");

        listPopupWindow.setAnchorView(anchorView);
//        ListPopupWindowAdapter listPopupWindowAdapter = new ListPopupWindowAdapter(this, sampleData, new ListPopupWindowAdapter{
        ListPopupWindowAdapter listPopupWindowAdapter = new ListPopupWindowAdapter(this, sampleData, new ListPopupWindowAdapter.OnClickItemText() {
            @Override
            public void onClick(int position) {

                paymentBinding.paymentPageSpinnerText.setText(sampleData.get(position));
                if(sampleData.get(position).equals("한화투자증권")){
                    paymentBinding.paymentPageSpinnerImage.setBackgroundResource(R.drawable.hanhwa_logo);
                }else if(sampleData.get(position).equals("신한금융투자")){
                    paymentBinding.paymentPageSpinnerImage.setBackgroundResource(R.drawable.shinhan_logo);
                }

                listPopupWindow.dismiss();
            }
        });
        listPopupWindow.setAdapter(listPopupWindowAdapter);
        listPopupWindow.show();
    }

}

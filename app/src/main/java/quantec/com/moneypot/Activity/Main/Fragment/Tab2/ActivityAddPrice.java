package quantec.com.moneypot.Activity.Main.Fragment.Tab2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import quantec.com.moneypot.R;

public class ActivityAddPrice extends AppCompatActivity {

    long investPrice = 200;
    EditText priceEditText;
    TextView upBT1, upBT2, upBT3, upBT4, upBT5, confirmBt;
    ImageView refreshBt, backBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_price);

        priceEditText = findViewById(R.id.priceEditText);
        refreshBt = findViewById(R.id.refreshBt);
        upBT1 = findViewById(R.id.upBT1);
        upBT2 = findViewById(R.id.upBT2);
        upBT3 = findViewById(R.id.upBT3);
        upBT4 = findViewById(R.id.upBT4);
        upBT5 = findViewById(R.id.upBT5);

        confirmBt = findViewById(R.id.confirmBt);
        backBt = findViewById(R.id.backBt);


        //스테이터스 바 색상 변경 -> 화이트
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }else{
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.main_page_status_bar_color));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        Intent intent = getIntent();
        investPrice = intent.getLongExtra("investPrice",200);
        priceEditText.setText(String.valueOf(investPrice));
        confirmBtState();

        priceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                if(!s.toString().isEmpty()){

                    investPrice = Long.valueOf(s.toString());
                    confirmBtState();

                }
            }
        });

        confirmBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(investPrice >= 200){
                    Intent intent1 = new Intent(ActivityAddPrice.this, ActivityPotCook.class);
                    intent1.putExtra("investPrice", investPrice);
                    setResult(100, intent1);
                    finish();

                }else{
                    Toast.makeText(ActivityAddPrice.this, "200만원이상 해주세요.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        refreshBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                investPrice = 200;
                priceEditText.setText(String.valueOf(investPrice));
            }
        });

        backBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        upBT1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(investPrice <= 100000000){
                    investPrice+=10;
                    priceEditText.setText(String.valueOf(investPrice));
                }else{
                    Toast.makeText(ActivityAddPrice.this, "투자 허용 금액을 초과하셨습니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        upBT2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(investPrice <= 100000000){
                    investPrice+=50;
                    priceEditText.setText(String.valueOf(investPrice));
                }else{
                    Toast.makeText(ActivityAddPrice.this, "투자 허용 금액을 초과하셨습니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        upBT3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(investPrice <= 100000000){
                    investPrice+=100;
                    priceEditText.setText(String.valueOf(investPrice));
                }else{
                    Toast.makeText(ActivityAddPrice.this, "투자 허용 금액을 초과하셨습니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        upBT4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(investPrice <= 100000000){
                    investPrice+=200;
                    priceEditText.setText(String.valueOf(investPrice));
                }else{
                    Toast.makeText(ActivityAddPrice.this, "투자 허용 금액을 초과하셨습니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        upBT5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(investPrice <= 100000000){
                    investPrice+=500;
                    priceEditText.setText(String.valueOf(investPrice));
                }else{
                    Toast.makeText(ActivityAddPrice.this, "투자 허용 금액을 초과하셨습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }// onCreate 끝

    private void confirmBtState(){
        if(investPrice >= 200){
            confirmBt.setEnabled(true);
            confirmBt.setBackgroundColor(getResources().getColor(R.color.button_able));
            confirmBt.setTextColor(getResources().getColor(R.color.button_able_text));
        }
        else{
            confirmBt.setEnabled(false);
            confirmBt.setBackgroundColor(getResources().getColor(R.color.button_enable));
            confirmBt.setTextColor(getResources().getColor(R.color.button_enable_text));
        }
    }
}

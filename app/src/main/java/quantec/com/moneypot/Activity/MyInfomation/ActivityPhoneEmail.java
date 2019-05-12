package quantec.com.moneypot.Activity.MyInfomation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import quantec.com.moneypot.Activity.Login.Model.dModel.ModelAuthEmailReqDto;
import quantec.com.moneypot.Activity.Login.Model.nModel.ModelAuthEmail;
import quantec.com.moneypot.Network.Retrofit.RetrofitClient;
import quantec.com.moneypot.R;
import quantec.com.moneypot.Util.SharedPreferenceUtil.SharedPreferenceUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityPhoneEmail extends AppCompatActivity {

    TextView idText, emailText, phoneText, emailTextBt;

    String userId, hpNumber, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_email);

        email = "";
        userId = SharedPreferenceUtil.getInstance(ActivityPhoneEmail.this).getStringExtra("userId");
        hpNumber = SharedPreferenceUtil.getInstance(ActivityPhoneEmail.this).getStringExtra("hpNumber");

        emailTextBt = findViewById(R.id.emailTextBt);
        idText = findViewById(R.id.idText);
        emailText = findViewById(R.id.emailText);
        phoneText = findViewById(R.id.phoneText);

        if(email.isEmpty()){
            emailText.setText("info@quantec.co.kr");
        }else{
            emailText.setText(email);
        }
        idText.setText(userId);
        phoneText.setText(hpNumber);

        emailTextBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ActivityPhoneEmail.this, ActivityChangedEmail.class);
                startActivity(intent);

            }
        });

    }
}

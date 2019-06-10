package quantec.com.moneypot.Activity.MyInfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import quantec.com.moneypot.R;
import quantec.com.moneypot.util.SharedPreference.SharedPreferenceUtil;

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

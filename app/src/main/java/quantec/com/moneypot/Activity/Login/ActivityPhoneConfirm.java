package quantec.com.moneypot.Activity.Login;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import quantec.com.moneypot.R;

public class ActivityPhoneConfirm extends AppCompatActivity {

    private TextInputLayout activity_phone_confirm_namelayout, activity_phone_confirm_numberlayout, activity_phone_confirm_number2layout;
    private EditText activity_phone_confirm_nameEdittext, activity_phone_confirm_numberEdittext, activity_phone_confirm_number2Edittext;
    private TextView activity_phone_confirm_BT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_confirm);


        activity_phone_confirm_namelayout = (TextInputLayout) findViewById(R.id.activity_phone_confirm_namelayout);
        activity_phone_confirm_numberlayout = findViewById(R.id.activity_phone_confirm_numberlayout);

        activity_phone_confirm_nameEdittext = findViewById(R.id.activity_phone_confirm_nameEdittext);
        activity_phone_confirm_numberEdittext = findViewById(R.id.activity_phone_confirm_numberEdittext);

        activity_phone_confirm_BT = findViewById(R.id.activity_phone_confirm_BT);

        activity_phone_confirm_number2layout = findViewById(R.id.activity_phone_confirm_number2layout);
        activity_phone_confirm_number2Edittext = findViewById(R.id.activity_phone_confirm_number2Edittext);

        activity_phone_confirm_nameEdittext.addTextChangedListener(new MyTextWatcher(activity_phone_confirm_nameEdittext));
        activity_phone_confirm_numberEdittext.addTextChangedListener(new MyTextWatcher(activity_phone_confirm_numberEdittext));


        activity_phone_confirm_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });
    }

    private void submitForm() {
        if (!validateName()) {
            return;
        }

        if (!validateEmail()) {
            return;
        }
    }

    private boolean validateName() {
        if (activity_phone_confirm_nameEdittext.getText().toString().trim().isEmpty()) {
            activity_phone_confirm_namelayout.setError("이름을 입력해 주세요");
            requestFocus(activity_phone_confirm_nameEdittext);
            return false;
        } else {
            activity_phone_confirm_namelayout.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateEmail() {
        String email = activity_phone_confirm_numberEdittext.getText().toString().trim();

        if (email.isEmpty()) {
            activity_phone_confirm_numberlayout.setError("잘못된 형식의 생년월일입니다");
            requestFocus(activity_phone_confirm_numberEdittext);
            return false;
        } else {
            activity_phone_confirm_numberlayout.setErrorEnabled(false);
        }

        if(email.length() >= 6){
            nextFocus(activity_phone_confirm_number2layout);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private void nextFocus(View view){
        if(view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.activity_phone_confirm_nameEdittext:
                    validateName();
                    break;
                case R.id.activity_phone_confirm_numberEdittext:
                    validateEmail();
                    break;
            }
        }
    }
}

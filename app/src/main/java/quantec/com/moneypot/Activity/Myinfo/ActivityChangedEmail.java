package quantec.com.moneypot.Activity.MyInfo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import quantec.com.moneypot.Activity.Login.Model.dModel.ModelAuthEmailReqDto;
import quantec.com.moneypot.Activity.Login.Model.nModel.ModelAuthEmail;
import quantec.com.moneypot.Dialog.DialogLoadingMakingPort;
import quantec.com.moneypot.Network.Retrofit.RetrofitClient;
import quantec.com.moneypot.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityChangedEmail extends AppCompatActivity {

    ImageView backBt;
    TextView emailTitle, emailAuthBt, postAuthBt, authReBt, authOkBt, postAuthBt2, emailEditText2SubTitle;
    EditText emailEditText, emailEditText2;

    private DialogLoadingMakingPort loadingCustomMakingPort;

    String regex = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";
    Matcher matcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changed_email);

        backBt = findViewById(R.id.backBt);
        emailTitle = findViewById(R.id.emailTitle);
        emailAuthBt = findViewById(R.id.emailAuthBt);
        postAuthBt = findViewById(R.id.postAuthBt);
        emailEditText = findViewById(R.id.emailEditText);

        authReBt = findViewById(R.id.authReBt);
        authOkBt = findViewById(R.id.authOkBt);
        postAuthBt2 = findViewById(R.id.postAuthBt2);
        emailEditText2 = findViewById(R.id.emailEditText2);
        emailEditText2SubTitle = findViewById(R.id.emailEditText2SubTitle);

        emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                if(s.length() > 0){
                    emailAuthBt.setEnabled(true);
                    emailAuthBt.setBackgroundResource(R.drawable.rectangle_red_5dp);
                    emailAuthBt.setTextColor(getResources().getColor(R.color.text_white_color));
                }
                else{
                    emailAuthBt.setEnabled(false);
                    emailAuthBt.setBackgroundResource(R.drawable.rectangle_dark_gray);
                    emailAuthBt.setTextColor(getResources().getColor(R.color.text_gray_color));
                }

            }
        });

        emailEditText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                if(s.length() > 0){

                    authReBt.setVisibility(View.VISIBLE);

                }
                else{
                    authReBt.setVisibility(View.GONE);
                }

            }
        });

        emailAuthBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                matcher = Pattern.compile(regex).matcher(emailEditText.getText().toString());
                if(!matcher.matches()){
                    Toast.makeText(ActivityChangedEmail.this, "잘못된 형식의 이메일 입니다.", Toast.LENGTH_SHORT).show();
                }else{

                    loadingCustomMakingPort = new DialogLoadingMakingPort(ActivityChangedEmail.this, "주식투자는 단기적 수익을 쫒기 보다는\n장기적으로 보아야 성공할 수 있습니다.");
                    loadingCustomMakingPort.show();

                    ModelAuthEmailReqDto authEmailReqDto = new ModelAuthEmailReqDto("", emailEditText.getText().toString().trim(), "인증 메일 입니다.","");
                    Gson gson = new GsonBuilder().disableHtmlEscaping().create();
                    String jsonItentifyData = gson.toJson(authEmailReqDto).replace("\\n", "").replace(" ", "")
                            .replace("\\", "").replace("\"{", "{").replace("}\"", "}");

                    Call<ModelAuthEmail> getRegChkData = RetrofitClient.getInstance(ActivityChangedEmail.this).getService().getSendAuthEmailData("application/json", jsonItentifyData);
                    getRegChkData.enqueue(new Callback<ModelAuthEmail>() {
                        @Override
                        public void onResponse(Call<ModelAuthEmail> call, Response<ModelAuthEmail> response) {
                            if(response.code() == 200) {
                                loadingCustomMakingPort.dismiss();
                                emailEditText.setVisibility(View.INVISIBLE);
                                emailEditText2.setVisibility(View.VISIBLE);
                                postAuthBt.setVisibility(View.INVISIBLE);
                                emailAuthBt.setVisibility(View.INVISIBLE);

                                authReBt.setVisibility(View.GONE);
                                authOkBt.setVisibility(View.VISIBLE);
                                postAuthBt2.setVisibility(View.VISIBLE);
                                emailTitle.setText("이메일로 전송한 인증번호 8자리를 입력해주세요.");
                                emailEditText2SubTitle.setVisibility(View.VISIBLE);
                            }
                        }
                        @Override
                        public void onFailure(Call<ModelAuthEmail> call, Throwable t) {
                            loadingCustomMakingPort.dismiss();
                        }
                    });

                }


            }
        });

        backBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

package quantec.com.moneypot.Activity.Main.Fragment.Tab2.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import quantec.com.moneypot.Activity.Main.Fragment.Tab2.Adapter.AdapterLeagueForm;
import quantec.com.moneypot.Activity.Main.Fragment.Tab2.Adapter.ModelLeagueFormMadePot;
import quantec.com.moneypot.Activity.Main.Fragment.Tab2.Adapter.ModelLeagueFormSelectPot;
import quantec.com.moneypot.R;

public class ActivityLeagueForm extends AppCompatActivity {

    ImageView backBt, checkBt1, checkBt2;
    TextView checkBt1Title, checkBt2Title, viewBt, makeBt, name, adress;

    boolean checkBt1State, checkBt2State;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ModelLeagueFormSelectPot> modelLeagueFormSelectPots;
    ArrayList<ModelLeagueFormMadePot> modelLeagueFormMadePots;
    AdapterLeagueForm adapterLeagueForm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_league_form);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        modelLeagueFormSelectPots = new ArrayList<>();
        modelLeagueFormMadePots = new ArrayList<>();

        adapterLeagueForm = new AdapterLeagueForm(modelLeagueFormMadePots, this);
        recyclerView.setAdapter(adapterLeagueForm);

        backBt = findViewById(R.id.backBt);
        checkBt1 = findViewById(R.id.checkBt1);
        checkBt1.setImageDrawable(getResources().getDrawable(R.drawable.ic_checkbox_off_whitegray_24_dp));
        checkBt2 = findViewById(R.id.checkBt2);
        checkBt2.setImageDrawable(getResources().getDrawable(R.drawable.ic_checkbox_off_whitegray_24_dp));
        checkBt1Title = findViewById(R.id.checkBt1Title);
        checkBt2Title = findViewById(R.id.checkBt2Title);
        viewBt = findViewById(R.id.viewBt);
        makeBt = findViewById(R.id.makeBt);

        name = findViewById(R.id.name);
        adress = findViewById(R.id.adress);

        name.setText("이상근");
        adress.setText("01055554444");

        viewBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityLeagueForm.this, ActivityMyPotList.class);
                startActivityForResult(intent, 100);
            }
        });

        makeBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        checkBt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBt1State){
                    checkBt1State = false;
                    checkBt1.setImageDrawable(getResources().getDrawable(R.drawable.ic_checkbox_off_whitegray_24_dp));
                }else{
                    checkBt1State = true;
                    checkBt1.setImageDrawable(getResources().getDrawable(R.drawable.ic_checkbox_on_red_24_dp));
                }
            }
        });

        checkBt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBt2State){
                    checkBt2State = false;
                    checkBt2.setImageDrawable(getResources().getDrawable(R.drawable.ic_checkbox_off_whitegray_24_dp));
                }else{
                    checkBt2State = true;
                    checkBt2.setImageDrawable(getResources().getDrawable(R.drawable.ic_checkbox_on_red_24_dp));
                }
            }
        });


        checkBt1Title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityLeagueForm.this, ActivityWebViewLeagueForm.class);
                intent.putExtra("url","http://naver.com/");
                startActivity(intent);
            }
        });

        checkBt2Title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityLeagueForm.this, ActivityWebViewLeagueForm.class);
                intent.putExtra("url","http://google.com/");
                startActivity(intent);
            }
        });

    }//onCreate 끝

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == 100){
            if(requestCode == 100){
                Toast.makeText(ActivityLeagueForm.this, "받아옴",Toast.LENGTH_LONG).show();
            }
        }

    }
}

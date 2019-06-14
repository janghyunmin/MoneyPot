package quantec.com.moneypot.Activity.Main.Fragment.Tab2.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
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
import quantec.com.moneypot.Activity.PotDetail.ActivityPotDetail;
import quantec.com.moneypot.R;

public class ActivityLeagueForm extends AppCompatActivity {

    ImageView backBt, checkBt1, checkBt2;
    TextView checkBt1Title, checkBt2Title, viewBt, makeBt, name, adress, emptySelectPot, selectedTitle, selectedRate;

    boolean checkBt1State, checkBt2State;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
//    ArrayList<ModelLeagueFormSelectPot> modelLeagueFormSelectPots;
    ArrayList<ModelLeagueFormMadePot> modelLeagueFormMadePots;
    AdapterLeagueForm adapterLeagueForm;

    ConstraintLayout selectPotLayout;

    //클릭시 동일 포지션일경우 같은 코드를 거지치 않게 하기 위해서 잡아줌
    int prePosition = -1;
    int preCheckPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_league_form);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

//        modelLeagueFormSelectPots = new ArrayList<>();
        modelLeagueFormMadePots = new ArrayList<>();

        adapterLeagueForm = new AdapterLeagueForm(modelLeagueFormMadePots, this);
        recyclerView.setAdapter(adapterLeagueForm);

        recyclerView.setLayoutManager(new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        });

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

        emptySelectPot = findViewById(R.id.emptySelectPot);
        selectedTitle = findViewById(R.id.selectedTitle);
        selectedRate = findViewById(R.id.selectedRate);
        selectPotLayout = findViewById(R.id.selectPotLayout);

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


        adapterLeagueForm.setLeagueFormItemClick(new AdapterLeagueForm.LeagueFormItemClick() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(ActivityLeagueForm.this, ActivityPotDetail.class);
                startActivity(intent);
            }
        });

        adapterLeagueForm.setLeagueFormCheckBtClick(new AdapterLeagueForm.LeagueFormCheckBtClick() {
            @Override
            public void onClick(int position) {

                if(prePosition != position){

                    emptySelectPot.setVisibility(View.GONE);
                    selectPotLayout.setVisibility(View.VISIBLE);
                    selectedTitle.setText(modelLeagueFormMadePots.get(position).getTitle());
                    selectedRate.setText(String.valueOf(modelLeagueFormMadePots.get(position).getRate())+"%");
                    if(modelLeagueFormMadePots.get(position).getRate() >= 0){
                        selectedRate.setTextColor(getResources().getColor(R.color.red_text_color));
                    }else{
                        selectedRate.setTextColor(getResources().getColor(R.color.blue_color));
                    }

                    modelLeagueFormMadePots.get(preCheckPosition).setCheckBt(false);
                    modelLeagueFormMadePots.get(position).setCheckBt(true);

                    adapterLeagueForm.notifyDataSetChanged();

                }
                prePosition = position;
                preCheckPosition = position;
            }
        });

        modelLeagueFormMadePots.add(new ModelLeagueFormMadePot(false, false, "2019.03.14", "배당형 투자 전략", 22.12));
        modelLeagueFormMadePots.add(new ModelLeagueFormMadePot(false, false, "2019.03.14", "ETF 어벤저스", -15.12));
        modelLeagueFormMadePots.add(new ModelLeagueFormMadePot(false, false, "2019.03.14", "대박나면해외쪽박나면국내", 17.78));

        adapterLeagueForm.notifyDataSetChanged();

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

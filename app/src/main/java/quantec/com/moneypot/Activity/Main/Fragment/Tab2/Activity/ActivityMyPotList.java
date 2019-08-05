package quantec.com.moneypot.Activity.Main.Fragment.Tab2.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import quantec.com.moneypot.Activity.Main.Fragment.Tab2.Adapter.AdapterMyPotList;
import quantec.com.moneypot.Activity.PotDetail.ActivityPotDetail;
import quantec.com.moneypot.DataModel.dModel.ModelMyPotList;
import quantec.com.moneypot.R;

public class ActivityMyPotList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ModelMyPotList> modelMyPotLists;
    AdapterMyPotList adapterMyPotList;

    int prePosition;
    int potCount = 0;

    TextView cancleBt, okBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pot_list);

        cancleBt = findViewById(R.id.cancleBt);
        okBt = findViewById(R.id.okBt);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        modelMyPotLists = new ArrayList<>();

//        modelMyPotLists.add(new ModelMyPotList(true, false,"", "", 0));

        modelMyPotLists.add(new ModelMyPotList(false, false,"2019.03.24", "배당형 투자 전략", 14.23));
        modelMyPotLists.add(new ModelMyPotList(false,false,"2019.03.24", "ETF 어벤저스", 24.23));
        modelMyPotLists.add(new ModelMyPotList(false,false,"2019.03.24", "대박나면 해외 쪽박나면 국내", 34.23));
        modelMyPotLists.add(new ModelMyPotList(false,false,"2019.03.24", "이아름포트", 44.23));
        modelMyPotLists.add(new ModelMyPotList(false,false,"2019.03.24", "다 무찌르자 최강 조합", -22.23));
        modelMyPotLists.add(new ModelMyPotList(false,false,"2019.03.24", "어벤저스 앤드게임", 67.23));
        modelMyPotLists.add(new ModelMyPotList(false,false,"2019.03.24", "나만의 투자비법", 102.23));
        modelMyPotLists.add(new ModelMyPotList(false,false,"2019.03.24", "안정형 연금 투자 전략", -67.23));
        modelMyPotLists.add(new ModelMyPotList(false,false,"2019.03.24", "첫번째 포트", 102.23));

        adapterMyPotList = new AdapterMyPotList(modelMyPotLists, this);
        recyclerView.setAdapter(adapterMyPotList);

        adapterMyPotList.setMyPotListClick(new AdapterMyPotList.MyPotListClick() {
            @Override
            public void onClick(int position) {

//                Intent intent = new Intent(ActivityMyPotList.this, ActivityPotDetail.class);
//                startActivity(intent);
            }
        });

        adapterMyPotList.setMyPotListCheckBtClick(new AdapterMyPotList.MyPotListCheckBtClick() {
            @Override
            public void onClick(int position) {

                if(potCount == 0){
                    potCount++;
                    okBt.setEnabled(true);
                    okBt.setTextColor(getResources().getColor(R.color.text_black_color));
                }

                modelMyPotLists.get(prePosition).setCheckBt(false);
                prePosition = position;

                if(modelMyPotLists.get(position).isCheckBt()){
                    modelMyPotLists.get(position).setCheckBt(false);
                }else{
                    modelMyPotLists.get(position).setCheckBt(true);
                }

                adapterMyPotList.notifyDataSetChanged();

            }
        });

        cancleBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        okBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityMyPotList.this, ActivityLeagueForm.class);
                setResult(100, intent);
                finish();
            }
        });

    }
}

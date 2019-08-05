package quantec.com.moneypot.Activity.Main.Fragment.Tab3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import quantec.com.moneypot.Activity.Main.ActivityMain;
import quantec.com.moneypot.Activity.Main.Fragment.Tab3.Activity.ActivityPotMarket;
import quantec.com.moneypot.Activity.Main.Fragment.Tab3.Adapter.AdapterAllRankingTop3;
import quantec.com.moneypot.Activity.Main.Fragment.Tab3.Adapter.AdapterMini;
import quantec.com.moneypot.Activity.Main.Fragment.Tab3.Adapter.AdapterStrongTop3;
import quantec.com.moneypot.Activity.Main.Fragment.Tab3.Adapter.AdapterThemeTop3;
import quantec.com.moneypot.DataModel.dModel.ModelAllRankingTop3;
import quantec.com.moneypot.DataModel.dModel.ModelMini;
import quantec.com.moneypot.DataModel.dModel.ModelStrongTop3;
import quantec.com.moneypot.DataModel.dModel.ModelThemeTop3;
import quantec.com.moneypot.R;

public class FgTab3 extends Fragment implements View.OnClickListener {

    RecyclerView rankingRecyclerView, strongTop3RecyclerView, miniRecyclerView, themeTop3RecyclerView;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<ModelAllRankingTop3> modelAllRankingTop3s;
    ArrayList<ModelStrongTop3> modelStrongTop3s;
    ArrayList<ModelMini> modelMinis;
    ArrayList<ModelThemeTop3> modelThemeTop3s;

    AdapterAllRankingTop3 adapterAllRankingTop3;
    AdapterStrongTop3 adapterStrongTop3;
    AdapterMini adapterMini;
    AdapterThemeTop3 adapterThemeTop3;

    ImageView rankingAddBt, strongTop3AddBt, miiniAddBt, themeAddBt;

    private ActivityMain activityMain;

    public FgTab3() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_tab3, container, false);

        initializeViews();

        rankingAddBt = view.findViewById(R.id.rankingAddBt);
        rankingAddBt.setOnClickListener(this);
        strongTop3AddBt = view.findViewById(R.id.strongTop3AddBt);
        strongTop3AddBt.setOnClickListener(this);
        miiniAddBt = view.findViewById(R.id.miiniAddBt);
        miiniAddBt.setOnClickListener(this);
        themeAddBt = view.findViewById(R.id.themeAddBt);
        themeAddBt.setOnClickListener(this);

        rankingRecyclerView = view.findViewById(R.id.rankingRecyclerView);
        strongTop3RecyclerView = view.findViewById(R.id.strongTop3RecyclerView);
        miniRecyclerView = view.findViewById(R.id.miniRecyclerView);
        themeTop3RecyclerView = view.findViewById(R.id.themeTop3RecyclerView);

        rankingRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(activityMain, RecyclerView.HORIZONTAL, false);
        rankingRecyclerView.setLayoutManager(layoutManager);
        modelAllRankingTop3s = new ArrayList<>();
        adapterAllRankingTop3 = new AdapterAllRankingTop3(modelAllRankingTop3s, activityMain);
        rankingRecyclerView.setAdapter(adapterAllRankingTop3);

        rankingRecyclerView.addItemDecoration(new DecorationAllRankingTop3(activityMain, 12));


        strongTop3RecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(activityMain);
        strongTop3RecyclerView.setLayoutManager(layoutManager);
        modelStrongTop3s = new ArrayList<>();
        adapterStrongTop3 = new AdapterStrongTop3(modelStrongTop3s, activityMain);
        strongTop3RecyclerView.setAdapter(adapterStrongTop3);

        strongTop3RecyclerView.addItemDecoration(new  DecorationItemVertical(activityMain, 12));


        miniRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(activityMain, RecyclerView.HORIZONTAL, false);
        miniRecyclerView.setLayoutManager(layoutManager);
        modelMinis = new ArrayList<>();
        adapterMini = new AdapterMini(modelMinis, activityMain);
        miniRecyclerView.setAdapter(adapterMini);

        miniRecyclerView.addItemDecoration(new DecorationAllRankingTop3(activityMain, 12));


        themeTop3RecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(activityMain);
        themeTop3RecyclerView.setLayoutManager(layoutManager);
        modelThemeTop3s = new ArrayList<>();
        adapterThemeTop3 = new AdapterThemeTop3(modelThemeTop3s, activityMain);
        themeTop3RecyclerView.setAdapter(adapterThemeTop3);

        themeTop3RecyclerView.addItemDecoration(new  DecorationItemVertical(activityMain, 12));

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        modelAllRankingTop3s.add(new ModelAllRankingTop3(activityMain.getResources().getDrawable(R.drawable.img_gold_medal), "해외 여행 플랜", 30.13));
        modelAllRankingTop3s.add(new ModelAllRankingTop3(activityMain.getResources().getDrawable(R.drawable.img_silver_medal), "연금 준비 플랜", 26.91));
        modelAllRankingTop3s.add(new ModelAllRankingTop3(activityMain.getResources().getDrawable(R.drawable.img_brown_medal), "지속 성장 플랜", 24.13));

        adapterAllRankingTop3.notifyDataSetChanged();


        modelStrongTop3s.add(new ModelStrongTop3(1, "지속 상장 기업", activityMain.getResources().getDrawable(R.drawable.group_7), 24.13));
        modelStrongTop3s.add(new ModelStrongTop3(2, "스스로 준비하는 연금", activityMain.getResources().getDrawable(R.drawable.group_1625), 32.18));
        modelStrongTop3s.add(new ModelStrongTop3(3, "저평가 가치주", activityMain.getResources().getDrawable(R.drawable.group_1523), 19.72));

        adapterStrongTop3.notifyDataSetChanged();


        modelMinis.add(new ModelMini("반려동물 천만 시대", activityMain.getResources().getDrawable(R.drawable.img_mini_1)));
        modelMinis.add(new ModelMini("코리안 파워", activityMain.getResources().getDrawable(R.drawable.img_mini_2)));
        modelMinis.add(new ModelMini("둘이라서 더욱 좋아요", activityMain.getResources().getDrawable(R.drawable.img_mini_3)));

        adapterMini.notifyDataSetChanged();


        modelThemeTop3s.add(new ModelThemeTop3(1,"핵심은 4차 산업 기술", 24.13));
        modelThemeTop3s.add(new ModelThemeTop3(2,"한국을 IT 강국으로 이끈 기업들", 22.18));
        modelThemeTop3s.add(new ModelThemeTop3(3,"한국 경제의 중심! 삼성의 계열사", 19.72));

        adapterThemeTop3.notifyDataSetChanged();

        adapterAllRankingTop3.setAllRankingTop3ItemClick(new AdapterAllRankingTop3.AllRankingTop3ItemClick() {
            @Override
            public void onClick(int position) {
                Toast.makeText(activityMain, "상세페이지 이동 : "+modelAllRankingTop3s.get(position).getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        adapterStrongTop3.setStrongTop3ItemClick(new AdapterStrongTop3.StrongTop3ItemClick() {
            @Override
            public void onClick(int position) {

            }
        });

        adapterMini.setMiniItemClick(new AdapterMini.MiniItemClick() {
            @Override
            public void onClick(int position) {

            }
        });

        adapterThemeTop3.setThemeTop3itemClick(new AdapterThemeTop3.ThemeTop3itemClick() {
            @Override
            public void onClick(int position) {

            }
        });
    }


    private void initializeViews(){
        activityMain = (ActivityMain) getActivity();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof ActivityMain) {
            activityMain = (ActivityMain) context;
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.rankingAddBt:
                Intent intent1 = new Intent(activityMain, ActivityPotMarket.class);
                intent1.putExtra("title", "전체 랭킹");
                startActivity(intent1);
                break;

            case R.id.strongTop3AddBt:
                Intent intent2 = new Intent(activityMain, ActivityPotMarket.class);
                intent2.putExtra("title", "강력 시리즈");
                startActivity(intent2);
                break;

            case R.id.miiniAddBt:
                Intent intent3 = new Intent(activityMain, ActivityPotMarket.class);
                intent3.putExtra("title", "미니 시리즈");
                startActivity(intent3);
                break;

            case R.id.themeAddBt:
                Intent intent4 = new Intent(activityMain, ActivityPotMarket.class);
                intent4.putExtra("title", "테마 시리즈");
                startActivity(intent4);
                break;

        }
    }


}

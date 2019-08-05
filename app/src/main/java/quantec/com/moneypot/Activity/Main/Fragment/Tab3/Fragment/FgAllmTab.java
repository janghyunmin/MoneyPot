package quantec.com.moneypot.Activity.Main.Fragment.Tab3.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import quantec.com.moneypot.Activity.Main.Fragment.Tab3.Activity.ActivityPotMarket;
import quantec.com.moneypot.Activity.Main.Fragment.Tab3.Adapter.AdapterFgAllmTab;
import quantec.com.moneypot.DataModel.dModel.ModelFgAllmTab3Banner;
import quantec.com.moneypot.DataModel.dModel.ModelPotMarketData;
import quantec.com.moneypot.R;

public class FgAllmTab extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ModelPotMarketData> modelPotMarketData;
    AdapterFgAllmTab adapterFgAllmTab;

    ActivityPotMarket activityPotMarket;

    ArrayList<ModelFgAllmTab3Banner> modelFgAllmTab3Banners;

    public FgAllmTab() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_potmarket_fgallmtab, container, false);

        initializeViews();

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(activityPotMarket);
        recyclerView.setLayoutManager(layoutManager);
        modelPotMarketData = new ArrayList<>();

        modelFgAllmTab3Banners = new ArrayList<>();

        adapterFgAllmTab = new AdapterFgAllmTab(modelPotMarketData, activityPotMarket, modelFgAllmTab3Banners);
        recyclerView.setAdapter(adapterFgAllmTab);

        return view;
    }

    private void initializeViews(){
        activityPotMarket = (ActivityPotMarket) getActivity();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof ActivityPotMarket) {
            activityPotMarket = (ActivityPotMarket) context;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        modelPotMarketData.add(new ModelPotMarketData("", 0, "", "",0, false));

        modelPotMarketData.add(new ModelPotMarketData("", 1, "작지만 강한 기업에 투자", "",24.13, false));
        modelPotMarketData.add(new ModelPotMarketData("", 2, "반려동물 천만 시대", "",21.96, true));
        modelPotMarketData.add(new ModelPotMarketData("", 3, "나누는 삶, 기부 관련 테마주", "",19.01, false));
        modelPotMarketData.add(new ModelPotMarketData("", 4, "게임 관련 테마주", "",24.13, true));
        modelPotMarketData.add(new ModelPotMarketData("", 5, "우리 아이를 위한 테미", "",24.13, false));
        modelPotMarketData.add(new ModelPotMarketData("", 6, "해외 여행을 위한 테", "",24.13, false));
        modelPotMarketData.add(new ModelPotMarketData("", 7, "행복한 노후를 위한 준비 테마", "",24.13, false));
        modelPotMarketData.add(new ModelPotMarketData("", 8, "작지만 강한 기업에 투자", "",24.13, false));
        modelPotMarketData.add(new ModelPotMarketData("", 9, "작지만 강한 기업에 투자", "",24.13, false));
        modelPotMarketData.add(new ModelPotMarketData("", 10, "작지만 강한 기업에 투자", "",24.13, false));
        modelPotMarketData.add(new ModelPotMarketData("", 11, "작지만 강한 기업에 투자", "",24.13, false));


        modelFgAllmTab3Banners.add(new ModelFgAllmTab3Banner("","1번"));
        modelFgAllmTab3Banners.add(new ModelFgAllmTab3Banner("","2번"));
        modelFgAllmTab3Banners.add(new ModelFgAllmTab3Banner("","3번"));
        modelFgAllmTab3Banners.add(new ModelFgAllmTab3Banner("","4번"));
        modelFgAllmTab3Banners.add(new ModelFgAllmTab3Banner("","5번"));
        modelFgAllmTab3Banners.add(new ModelFgAllmTab3Banner("","6번"));

        adapterFgAllmTab.notifyDataSetChanged();


        adapterFgAllmTab.setFgAllmTabItemClick(new AdapterFgAllmTab.FgAllmTabItemClick() {
            @Override
            public void onClick(int position) {

            }
        });

        adapterFgAllmTab.setFgAllmTabZimClick(new AdapterFgAllmTab.FgAllmTabZimClick() {
            @Override
            public void onClick(int position) {

                if(modelPotMarketData.get(position).isCheck()){
                    modelPotMarketData.get(position).setCheck(false);
                }
                else{
                    modelPotMarketData.get(position).setCheck(true);
                }

                adapterFgAllmTab.notifyItemChanged(position);

            }
        });


    }
}

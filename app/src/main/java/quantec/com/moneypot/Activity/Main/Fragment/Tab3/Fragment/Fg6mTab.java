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
import quantec.com.moneypot.Activity.Main.Fragment.Tab3.Adapter.AdapterFg6mTab;
import quantec.com.moneypot.DataModel.dModel.ModelFg6mTab3Banner;
import quantec.com.moneypot.DataModel.dModel.ModelPotMarketData;
import quantec.com.moneypot.R;

public class Fg6mTab extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ModelPotMarketData> modelPotMarketData;
    AdapterFg6mTab adapterFg6mTab;

    ActivityPotMarket activityPotMarket;

    ArrayList<ModelFg6mTab3Banner> modelFg6mTab3Banners;

    public Fg6mTab() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_potmarket_fg6mtab, container, false);

        initializeViews();

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(activityPotMarket);
        recyclerView.setLayoutManager(layoutManager);
        modelPotMarketData = new ArrayList<>();

        modelFg6mTab3Banners = new ArrayList<>();

        adapterFg6mTab = new AdapterFg6mTab(modelPotMarketData, activityPotMarket, modelFg6mTab3Banners);
        recyclerView.setAdapter(adapterFg6mTab);

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

        modelFg6mTab3Banners.add(new ModelFg6mTab3Banner("","1번"));
        modelFg6mTab3Banners.add(new ModelFg6mTab3Banner("","2번"));
        modelFg6mTab3Banners.add(new ModelFg6mTab3Banner("","3번"));
        modelFg6mTab3Banners.add(new ModelFg6mTab3Banner("","4번"));
        modelFg6mTab3Banners.add(new ModelFg6mTab3Banner("","5번"));
        modelFg6mTab3Banners.add(new ModelFg6mTab3Banner("","6번"));

        adapterFg6mTab.notifyDataSetChanged();

        adapterFg6mTab.setFg6mTab3ItemClick(new AdapterFg6mTab.Fg6mTab3ItemClick() {
            @Override
            public void onClick(int position) {

            }
        });

        adapterFg6mTab.setFg6mTab3ZimBtClick(new AdapterFg6mTab.Fg6mTab3ZimBtClick() {
            @Override
            public void onClick(int position) {

                if(modelPotMarketData.get(position).isCheck()){
                    modelPotMarketData.get(position).setCheck(false);
                }
                else{
                    modelPotMarketData.get(position).setCheck(true);
                }

                adapterFg6mTab.notifyItemChanged(position);
            }

        });


    }
}

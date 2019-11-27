package com.quantec.moneypot.activity.account.fragment;

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

import com.quantec.moneypot.R;
import com.quantec.moneypot.activity.account.ActivityInvestHistory;
import com.quantec.moneypot.activity.account.ModelHistoryStock;
import com.quantec.moneypot.activity.account.adapter.AdapterHistoryStock;

import java.util.ArrayList;

public class FgHistoryStock extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ModelHistoryStock> modelHistoryStocks;
    AdapterHistoryStock adapterHistoryStock;

    ActivityInvestHistory activityPotMarket;

    public FgHistoryStock() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_history_stock, container, false);

        initializeViews();

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(activityPotMarket);
        recyclerView.setLayoutManager(layoutManager);

        modelHistoryStocks = new ArrayList<>();
        adapterHistoryStock = new AdapterHistoryStock(modelHistoryStocks, activityPotMarket);

        recyclerView.setAdapter(adapterHistoryStock);

        return view;
    }


    private void initializeViews(){
        activityPotMarket = (ActivityInvestHistory) getActivity();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof ActivityInvestHistory) {
            activityPotMarket = (ActivityInvestHistory) context;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        modelHistoryStocks.add(new ModelHistoryStock("","","",0, 0, true));
//        modelHistoryStocks.add(new ModelHistoryStock("","","",0, 0, false));
//        modelHistoryStocks.add(new ModelHistoryStock("","","",0, 0, false));
//        modelHistoryStocks.add(new ModelHistoryStock("","","",0, 0, false));
//        modelHistoryStocks.add(new ModelHistoryStock("","","",0, 0, false));
//        modelHistoryStocks.add(new ModelHistoryStock("","","",0, 0, false));
//        modelHistoryStocks.add(new ModelHistoryStock("","","",0, 0, false));


    }
}

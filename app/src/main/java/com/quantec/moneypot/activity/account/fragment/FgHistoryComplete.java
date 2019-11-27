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
import com.quantec.moneypot.activity.account.ModelHistoryComplete;
import com.quantec.moneypot.activity.account.adapter.AdapterHistoryComplete;

import java.util.ArrayList;

public class FgHistoryComplete extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ModelHistoryComplete> modelHistoryCompletes;
    AdapterHistoryComplete adapterHistoryComplete;

    ActivityInvestHistory activityPotMarket;

    public FgHistoryComplete() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_history_complete, container, false);

        initializeViews();

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(activityPotMarket);
        recyclerView.setLayoutManager(layoutManager);

        modelHistoryCompletes = new ArrayList<>();
        adapterHistoryComplete = new AdapterHistoryComplete(modelHistoryCompletes, activityPotMarket);

        recyclerView.setAdapter(adapterHistoryComplete);

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

        modelHistoryCompletes.add(new ModelHistoryComplete("", "", "", "", true));
//        modelHistoryCompletes.add(new ModelHistoryComplete("", "", "", "", false));
//        modelHistoryCompletes.add(new ModelHistoryComplete("", "", "", "", false));
//        modelHistoryCompletes.add(new ModelHistoryComplete("", "", "", "", false));
//        modelHistoryCompletes.add(new ModelHistoryComplete("", "", "", "", false));
//        modelHistoryCompletes.add(new ModelHistoryComplete("", "", "", "", false));
//        modelHistoryCompletes.add(new ModelHistoryComplete("", "", "", "", false));
//        modelHistoryCompletes.add(new ModelHistoryComplete("", "", "", "", false));

    }
}

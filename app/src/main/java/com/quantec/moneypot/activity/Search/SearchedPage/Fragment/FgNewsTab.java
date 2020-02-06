package com.quantec.moneypot.activity.Search.SearchedPage.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.quantec.moneypot.activity.Search.ActivitySearch;
import com.quantec.moneypot.activity.Search.SearchedPage.Adapter.AdapterNewsEn;
import com.quantec.moneypot.activity.Search.SearchedPage.Adapter.AdapterStockTab;
import com.quantec.moneypot.activity.Search.SearchedPage.ModelNewsEn;
import com.quantec.moneypot.activity.webview.ActivityWebViewArticle;
import com.quantec.moneypot.datamanager.DataManager;
import com.quantec.moneypot.datamodel.dmodel.ModelPostStockItem;
import com.quantec.moneypot.database.room.entry.RoomEntity;
import com.quantec.moneypot.database.room.local.RoomDao;
import com.quantec.moneypot.database.room.local.SearchRoomDatabase;
import com.quantec.moneypot.database.room.viewmodel.SearchViewModel;
import com.quantec.moneypot.R;
import com.quantec.moneypot.rxandroid.RxEvent;
import com.quantec.moneypot.rxandroid.RxEventBus;

public class FgNewsTab extends Fragment {

    ActivitySearch portSearchPageActivity;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ArrayList<ModelNewsEn> modelNewsEns;
    AdapterNewsEn adapterNewsEn;

    Bundle bundle;

    public FgNewsTab() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_newstab, container, false);
        //뷰 초기화
        initializeViews();

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        modelNewsEns = new ArrayList<>();

        adapterNewsEn = new AdapterNewsEn(modelNewsEns, getContext());
        recyclerView.setAdapter(adapterNewsEn);

        bundle = getArguments();

        return view;
    }

    private void initializeViews(){
        portSearchPageActivity = (ActivitySearch) getActivity();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof ActivitySearch) {
            portSearchPageActivity = (ActivitySearch) context;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        modelNewsEns.addAll(bundle.getParcelableArrayList("newsEn"));

        adapterNewsEn.setNewsEnClick(new AdapterNewsEn.NewsEnClick() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(portSearchPageActivity, ActivityWebViewArticle.class);
                intent.putExtra("url", modelNewsEns.get(position).getUrl());
                startActivity(intent);
            }
        });
    }
}
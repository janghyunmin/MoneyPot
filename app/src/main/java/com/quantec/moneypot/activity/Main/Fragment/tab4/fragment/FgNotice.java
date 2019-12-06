package com.quantec.moneypot.activity.Main.Fragment.tab4.fragment;

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

import com.quantec.moneypot.R;
import com.quantec.moneypot.activity.Main.ActivityMain;
import com.quantec.moneypot.activity.Main.Fragment.tab4.ActivityNotiWebView;
import com.quantec.moneypot.activity.Main.Fragment.tab4.ModelNotice;
import com.quantec.moneypot.activity.Main.Fragment.tab4.adapter.AdapterNotice;

import java.util.ArrayList;

public class FgNotice extends Fragment {

    private ActivityMain activityMain;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ModelNotice> modelNotices;
    AdapterNotice adapterNotice;

    public FgNotice() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_tab4_notice, container, false);

        initializeViews();

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(activityMain);
        recyclerView.setLayoutManager(layoutManager);

        modelNotices = new ArrayList<>();
        adapterNotice = new AdapterNotice(modelNotices, activityMain);

        recyclerView.setAdapter(adapterNotice);

        return view;
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        modelNotices.add(new ModelNotice("", "", ""));
        modelNotices.add(new ModelNotice("", "", ""));
        modelNotices.add(new ModelNotice("", "", ""));
        modelNotices.add(new ModelNotice("", "", ""));
        modelNotices.add(new ModelNotice("", "", ""));
        modelNotices.add(new ModelNotice("", "", ""));
        modelNotices.add(new ModelNotice("", "", ""));
        modelNotices.add(new ModelNotice("", "", ""));
        modelNotices.add(new ModelNotice("", "", ""));
        modelNotices.add(new ModelNotice("", "", ""));
        modelNotices.add(new ModelNotice("", "", ""));
        modelNotices.add(new ModelNotice("", "", ""));

        adapterNotice.setNoticeClick(new AdapterNotice.NoticeClick() {
            @Override
            public void onClick(int position) {
                Toast.makeText(activityMain, "이동합니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(activityMain, ActivityNotiWebView.class);
                startActivity(intent);
            }
        });

    }
}

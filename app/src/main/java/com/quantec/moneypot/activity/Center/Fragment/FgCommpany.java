package com.quantec.moneypot.activity.Center.Fragment;

import android.content.Intent;
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

import com.quantec.moneypot.activity.Center.ActivityWebViewQuestion;
import com.quantec.moneypot.activity.Center.Adapter.AdapterCommpany;
import com.quantec.moneypot.datamodel.dmodel.ModelCommpanyList;
import com.quantec.moneypot.R;

public class FgCommpany extends Fragment {

    ArrayList<ModelCommpanyList> commpanyLists;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    AdapterCommpany adapterCommpany;

    public FgCommpany() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_commpany, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        commpanyLists = new ArrayList<>();

        adapterCommpany = new AdapterCommpany(commpanyLists, getContext());
        recyclerView.setAdapter(adapterCommpany);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        commpanyLists.add(new ModelCommpanyList("머니포트는 어떤 회사인가요?","http://pizzaplanet.tistory.com/"));
        commpanyLists.add(new ModelCommpanyList("머니포트가 판매하고 있는 자산관리 상품이란 무엇인가요?","http://pizzaplanet.tistory.com/"));
        commpanyLists.add(new ModelCommpanyList("머니포트 서비스는 어떤 방식으로 진행되나요?","http://pizzaplanet.tistory.com/"));
        commpanyLists.add(new ModelCommpanyList("투자 자문업이란 무엇인가요?","http://pizzaplanet.tistory.com/"));
        commpanyLists.add(new ModelCommpanyList("머니포트의 유래는 무엇인가요?","http://pizzaplanet.tistory.com/"));
        adapterCommpany.notifyDataSetChanged();

        adapterCommpany.setCommpanyTitleClick(new AdapterCommpany.CommpanyTitleClick() {
            @Override
            public void onClick(int position) {

                Intent intent = new Intent(getActivity(), ActivityWebViewQuestion.class);
                intent.putExtra("url", commpanyLists.get(position).getUrl());
                startActivity(intent);
            }
        });
    }
}

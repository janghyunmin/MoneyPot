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
import com.quantec.moneypot.activity.Center.Adapter.AdapterAccount;
import com.quantec.moneypot.datamodel.dmodel.ModelAccountList;
import com.quantec.moneypot.R;

public class FgAccount extends Fragment {

    RecyclerView recyclerView;
    ArrayList<ModelAccountList> accountLists;
    RecyclerView.LayoutManager layoutManager;
    AdapterAccount adapterAccount;

    public FgAccount() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fg_account, container, false);


        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        accountLists = new ArrayList<>();

        adapterAccount = new AdapterAccount(accountLists, getContext());
        recyclerView.setAdapter(adapterAccount);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        accountLists.add(new ModelAccountList("머니포트는 어떤 회사인가요?","http://pizzaplanet.tistory.com/"));
        accountLists.add(new ModelAccountList("머니포트가 판매하고 있는 자산관리 상품이란 무엇인가요?","http://pizzaplanet.tistory.com/"));
        accountLists.add(new ModelAccountList("머니포트 서비스는 어떤 방식으로 진행되나요?","http://pizzaplanet.tistory.com/"));
        accountLists.add(new ModelAccountList("투자 자문업이란 무엇인가요?","http://pizzaplanet.tistory.com/"));
        accountLists.add(new ModelAccountList("머니포트의 유래는 무엇인가요?","http://pizzaplanet.tistory.com/"));
        adapterAccount.notifyDataSetChanged();

        adapterAccount.setAccountClick(position -> {

            Intent intent = new Intent(getActivity(), ActivityWebViewQuestion.class);
            intent.putExtra("url", accountLists.get(position).getUrl());
            startActivity(intent);
        });

    }
}

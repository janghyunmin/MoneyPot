package quantec.com.moneypot.Activity.ClientCenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import quantec.com.moneypot.Activity.ClientCenter.ActivityWebViewQuestion;
import quantec.com.moneypot.Activity.ClientCenter.AdapterAccount;
import quantec.com.moneypot.ModelCommon.dModel.ModelAccountList;
import quantec.com.moneypot.R;

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
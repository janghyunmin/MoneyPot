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
import quantec.com.moneypot.Activity.ClientCenter.AdapterCommpany;
import quantec.com.moneypot.ModelCommon.dModel.ModelCommpanyList;
import quantec.com.moneypot.R;

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

        recyclerView = view.findViewById(R.id.fgCommpanyRecyclerView);
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

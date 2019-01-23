package quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.Cookpage4;

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

import quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.Cookpage4.Adapter.AdapterCookPage4;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.Cookpage4.Model.dModel.ModelRankList;
import quantec.com.moneypot.R;

public class Fg_CookPage4 extends Fragment {

    RecyclerView recyclerView;

    LinearLayoutManager linearLayoutManager;
    ArrayList<ModelRankList> modelMyCookLists;
    AdapterCookPage4 adapterCookPage3;

    public Fg_CookPage4() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_fgtab2_fgcookpage4, container, false);

        recyclerView = view.findViewById(R.id.fg_cookpage4_recyclerView);
        recyclerView.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        modelMyCookLists = new ArrayList<>();

        adapterCookPage3 = new AdapterCookPage4(modelMyCookLists, getContext());
        recyclerView.setAdapter(adapterCookPage3);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        for(int a = 0 ; a < 10 ; a++) {
            modelMyCookLists.add(new ModelRankList(a+1, "내포트"+a, 43.44, 0));
        }
        adapterCookPage3.notifyDataSetChanged();
    }
}

package quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.Cookpage4;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

        modelMyCookLists.add(new ModelRankList(1, "피오트로스키 스타일", 83.24, 0));
        modelMyCookLists.add(new ModelRankList(2, "내가 만든 첫 투자", 67.14, 0));
        modelMyCookLists.add(new ModelRankList(3, "여행을 위한 자금 마련", 60.51, 0));
        modelMyCookLists.add(new ModelRankList(4, "저평가 자산가치 스타일", 58.31, 0));
        modelMyCookLists.add(new ModelRankList(5, "마법공식 스타일", 53.11, 0));
        modelMyCookLists.add(new ModelRankList(5, "피터린치 스타일", 48.21, 0));
        modelMyCookLists.add(new ModelRankList(6, "배당주 추세추종", 34.87, 0));
        modelMyCookLists.add(new ModelRankList(7, "BMW 마련 자금", 30.14, 0));
        modelMyCookLists.add(new ModelRankList(8, "주택자금 마련하기", 22.77, 0));
        modelMyCookLists.add(new ModelRankList(9, "이건 꼭 되는 전략", 20.54, 0));
        modelMyCookLists.add(new ModelRankList(10, "윌리엄오닐 스타일", 17.24, 0));

        adapterCookPage3.notifyDataSetChanged();
    }
}

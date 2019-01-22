package quantec.com.moneypot.Activity.SearchPort.BasicPage.Fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import quantec.com.moneypot.Activity.DetailPort.ActivityDetailPort;
import quantec.com.moneypot.Activity.SearchPort.BasicPage.Fragment.Adapter.AdapterRecommendPortTab;
import quantec.com.moneypot.Activity.SearchPort.BasicPage.Fragment.Model.dModel.ModelRecommendPort;
import quantec.com.moneypot.Activity.SearchPort.BasicPage.Fragment.Model.nModel.ModelRecommendHotPort;
import quantec.com.moneypot.Network.Retrofit.RetrofitClient;
import quantec.com.moneypot.R;
import quantec.com.moneypot.databinding.FgActivitysearchRecommendtabBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fg_RecommendTab extends Fragment {

    private AdapterRecommendPortTab adapterRecommendPortTab;
    LinearLayoutManager linearLayoutManager;
    ArrayList<ModelRecommendPort> modelRecommendPorts;

    FgActivitysearchRecommendtabBinding recommendtabBinding;

    public Fg_RecommendTab() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        recommendtabBinding = DataBindingUtil.inflate(inflater, R.layout.fg_activitysearch_recommendtab, container, false);

        modelRecommendPorts = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recommendtabBinding.recommendTabRecyclerView.setLayoutManager(linearLayoutManager);
        recommendtabBinding.recommendTabRecyclerView.setHasFixedSize(true);

        adapterRecommendPortTab = new AdapterRecommendPortTab(modelRecommendPorts, getContext());
        recommendtabBinding.recommendTabRecyclerView.setAdapter(adapterRecommendPortTab);

        return recommendtabBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //내가 만든 포트 데이터 초기 불러옴
        Call<ModelRecommendHotPort> getData = RetrofitClient.getInstance().getService().getHotPort();
        getData.enqueue(new Callback<ModelRecommendHotPort>() {
            @Override
            public void onResponse(Call<ModelRecommendHotPort> call, Response<ModelRecommendHotPort> response) {
                if (response.code() == 200) {
                    for(int a= 0 ; a < response.body().getProduct().size() ; a++) {
                        modelRecommendPorts.add(new ModelRecommendPort(response.body().getProduct().get(a).getName(),response.body().getProduct().get(a).getCode()));
                    }
                    if(modelRecommendPorts.size() == 0) {
                        recommendtabBinding.recommendTabTitle.setVisibility(View.GONE);
                    }else{
                        recommendtabBinding.recommendTabTitle.setVisibility(View.VISIBLE);
                    }
                    adapterRecommendPortTab.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<ModelRecommendHotPort> call, Throwable t) {
            }
        });

        adapterRecommendPortTab.setPortItemClick(new AdapterRecommendPortTab.PortItemClick() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getActivity(), ActivityDetailPort.class);
                intent.putExtra("detailcode", modelRecommendPorts.get(position).getPortCode());
                intent.putExtra("detailtitle", modelRecommendPorts.get(position).getPortTitle());
                startActivity(intent);
            }
        });

        recommendtabBinding.recommendTabRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(linearLayoutManager.findFirstCompletelyVisibleItemPosition() > 0) {
                    recommendtabBinding.fragmentRecommendPortTabTitleLine.setVisibility(View.VISIBLE);
                }else{
                    recommendtabBinding.fragmentRecommendPortTabTitleLine.setVisibility(View.INVISIBLE);
                }
            }
        });

    }
}
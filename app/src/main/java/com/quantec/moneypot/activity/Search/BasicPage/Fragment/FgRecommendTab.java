package com.quantec.moneypot.activity.Search.BasicPage.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.quantec.moneypot.activity.PotDetail.ActivitySingleDetail;
import com.quantec.moneypot.activity.Search.BasicPage.Adapter.AdapterRecommendTab;
import com.quantec.moneypot.datamodel.dmodel.Filter;
import com.quantec.moneypot.datamodel.dmodel.ModelRecommendPort;
import com.quantec.moneypot.datamodel.nmodel.ModelRecommendList;
import com.quantec.moneypot.datamodel.nmodel.ModelSearchOrder;
import com.quantec.moneypot.network.retrofit.RetrofitClient;
import com.quantec.moneypot.R;
import com.quantec.moneypot.databinding.FgRecommendtabBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FgRecommendTab extends Fragment {

    private AdapterRecommendTab adapterRecommendPortTab;
    LinearLayoutManager linearLayoutManager;
    ArrayList<ModelRecommendPort> modelRecommendPorts;

    FgRecommendtabBinding binding;

    public FgRecommendTab() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fg_recommendtab, container, false);

        modelRecommendPorts = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(getActivity());
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        binding.recyclerView.setHasFixedSize(true);

        adapterRecommendPortTab = new AdapterRecommendTab(modelRecommendPorts, getContext());
        binding.recyclerView.setAdapter(adapterRecommendPortTab);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        Call<ModelSearchOrder> getTest2 = RetrofitClient.getInstance().getService().getSearchOrder(5);
//        getTest2.enqueue(new Callback<ModelSearchOrder>() {
//            @Override
//            public void onResponse(Call<ModelSearchOrder> call, Response<ModelSearchOrder> response) {
//                if(response.code() == 200) {
//
//
//                }
//            }
//            @Override
//            public void onFailure(Call<ModelSearchOrder> call, Throwable t) {
//                Log.e("레트로핏 실패","값 : "+t.getMessage());
//            }
//        });

        adapterRecommendPortTab.setPortItemClick(new AdapterRecommendTab.PortItemClick() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getActivity(), ActivitySingleDetail.class);
                intent.putExtra("detailcode", modelRecommendPorts.get(position).getPortCode());
                intent.putExtra("detailtitle", modelRecommendPorts.get(position).getPortTitle());
                startActivity(intent);
            }
        });

        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(linearLayoutManager.findFirstCompletelyVisibleItemPosition() > 0) {
                    binding.line.setVisibility(View.VISIBLE);
                }else{
                    binding.line.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
}

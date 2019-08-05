package quantec.com.moneypot.Activity.Main.Fragment.Tab1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import quantec.com.moneypot.Activity.Main.ActivityMain;
import quantec.com.moneypot.Activity.Search.ActivitySearch;
import quantec.com.moneypot.DataModel.dModel.Filter;
import quantec.com.moneypot.DataModel.nModel.ModelLifeList;
import quantec.com.moneypot.Network.Retrofit.RetrofitClient;
import quantec.com.moneypot.R;
import quantec.com.moneypot.databinding.FgTab1Binding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FgTab1 extends Fragment {

    FgTab1Binding binding;
    private ActivityMain activityMain;

    RecyclerView.LayoutManager layoutManager;
    ArrayList<ModelFitPotList> modelFitPotLists;
    AdapterFitPot adapterFitPot;

    ArrayList<ModelAccountWebView> modelAccountWebViews;

    public FgTab1(){}
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fg_tab1, container, false);

        initializeViews();

        return binding.getRoot();
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



        Filter filter = new Filter();
        Call<ModelLifeList> getReList = RetrofitClient.getInstance().getService().getLifeList("application/json", filter, "C", 0, 0,10);
        getReList.enqueue(new Callback<ModelLifeList>() {
            @Override
            public void onResponse(Call<ModelLifeList> call, Response<ModelLifeList> response) {
                if (response.code() == 200) {

                    if(response.body().getNoContent()){

                        Log.e("노컨텐츠","내용이 없습니다.");

                    }else{
                        Log.e("노컨텐츠","내용이 들어 있습니다.");
                    }

                }
            }
            @Override
            public void onFailure(Call<ModelLifeList> call, Throwable t) {
                Log.e("에러값","값 : "+t.getMessage());
            }
        });


//        Call<Object> getSaveLifeList = RetrofitClient.getInstance().getService().getDelMyPot( "CP4000000001");
//        getSaveLifeList.enqueue(new Callback<Object>() {
//            @Override
//            public void onResponse(Call<Object> call, Response<Object> response) {
//                if (response.code() == 200) {
//                }
//            }
//            @Override
//            public void onFailure(Call<Object> call, Throwable t) {
//                Log.e("에러값","값 : "+t.getMessage());
//            }
//        });

//        LifeMap lifeMap = new LifeMap(10000, 701, 10, 100, 100, 10, 12);
//        Call<Object> getSaveLifeList = RetrofitClient.getInstance().getService().getSaveLife("application/json", lifeMap);
//        getSaveLifeList.enqueue(new Callback<Object>() {
//            @Override
//            public void onResponse(Call<Object> call, Response<Object> response) {
//                if (response.code() == 200) {
//                }
//            }
//            @Override
//            public void onFailure(Call<Object> call, Throwable t) {
//                Log.e("에러값","값 : "+t.getMessage());
//            }
//        });

        modelFitPotLists.add(new ModelFitPotList(true, false));
        modelAccountWebViews.add(new ModelAccountWebView(true, ""));


        binding.recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(activityMain);

        binding.recyclerView.setLayoutManager(layoutManager);
        modelFitPotLists = new ArrayList<>();

        modelAccountWebViews = new ArrayList<>();

        adapterFitPot = new AdapterFitPot(modelFitPotLists, activityMain, modelAccountWebViews);
        binding.recyclerView.setAdapter(adapterFitPot);



//        adapterFitPot.setNextBtClick(new AdapterFitPot.NextBtClick() {
//            @Override
//            public void onClick(int position) {
//                modelFitPotLists.get(position).setCustom(false);
//                adapterFitPot.notifyItemChanged(position);
//            }
//        });
//
//
//        adapterFitPot.setChartNextBtClick(new AdapterFitPot.ChartNextBtClick() {
//            @Override
//            public void onClick(int position) {
//
//                Toast.makeText(activityMain, "ㄲㄱ",Toast.LENGTH_SHORT).show();
//                modelFitPotLists.get(position).setLifeList(true);
//                adapterFitPot.notifyItemChanged(position);
//            }
//        });
    }

}

//public class FgTab1 extends Fragment {
//
//    FgTab1Binding binding;
//    private ActivityMain activityMain;
//
//    RecyclerView.LayoutManager layoutManager;
//    ArrayList<ModelFitPotList> modelFitPotLists;
//    AdapterFitPot adapterFitPot;
//
//    public static boolean more_top10 = false;
//    boolean limitedLife = false;
//
//    public FgTab1(){}
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//
//        binding = DataBindingUtil.inflate(inflater, R.layout.fg_tab1, container, false);
//
//        initializeViews();
//
//        return binding.getRoot();
//    }
//
//    private void initializeViews(){
//        activityMain = (ActivityMain) getActivity();
//    }
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if(context instanceof ActivityMain) {
//            activityMain = (ActivityMain) context;
//        }
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        binding.recyclerView.setHasFixedSize(true);
//        layoutManager = new LinearLayoutManager(activityMain);
//
//        binding.recyclerView.setLayoutManager(layoutManager);
//        modelFitPotLists = new ArrayList<>();
//
//        adapterFitPot = new AdapterFitPot(modelFitPotLists, activityMain);
//        binding.recyclerView.setAdapter(adapterFitPot);
//
//
//
//        modelFitPotLists.add(0, new ModelFitPotList(false, "","", "", "", "", "", "", "", "", ""));
////        modelFitPotLists.add(0, new ModelFitPotList(true, "http://pizzaplanet.tistory.com/","", "", "", "", "", "", "", "", ""));
//
//        modelFitPotLists.add(new ModelFitPotList(true, "","내집마련", "정말 더 없이 좋은 나만의 인생 라이프", "30", "1000000000",
//                "장기플랜", "투자의 고수", "조심조심", "안정형을 추구하는 연금 투자 전략", "44.56%"));
//        modelFitPotLists.add(new ModelFitPotList(false, "","", "", "", "", "", "", "", "", ""));
//
//        adapterFitPot.setEmptyTotalPriceClick(new AdapterFitPot.EmptyTotalPriceClick() {
//            @Override
//            public void onClick(int position) {
////                Intent intent = new Intent()
//                Toast.makeText(activityMain, "계좌 개설로 이동됩니다.", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        adapterFitPot.setEmptyLifeChallengeClick(new AdapterFitPot.EmptyLifeChallengeClick() {
//            @Override
//            public void onClick(int position) {
//                modelFitPotLists.add(modelFitPotLists.size()-1, new ModelFitPotList(true, "","내집마련"+(modelFitPotLists.size()-1), "정말 더 없이 좋은 나만의 인생 라이프", "30", "1000000000",
//                        "장기플랜", "투자의 고수", "조심조심", "안정형을 추구하는 연금 투자 전략", "44.56%"));
//                visibleAddLife();
//            }
//        });
//
//        adapterFitPot.setLifeChallengeClick(new AdapterFitPot.LifeChallengeClick() {
//            @Override
//            public void onClick(int position) {
//                modelFitPotLists.remove(position);
//                visibleAddLife();
//            }
//        });
//
//        binding.searchBt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(activityMain, ActivitySearch.class);
//                startActivity(intent);
//            }
//        });
//
//    }
//
//    private void visibleAddLife(){
//
//        if(modelFitPotLists.size() == 12){
//
//            modelFitPotLists.remove(modelFitPotLists.size()-1);
//            adapterFitPot.notifyDataSetChanged();
//
//            limitedLife = true;
//
//        }else{
//
//            if(limitedLife){
//
//                limitedLife = false;
//
//                modelFitPotLists.add(modelFitPotLists.size(), new ModelFitPotList(false, "","", "", "", "", "", "", "", "", ""));
//                adapterFitPot.notifyDataSetChanged();
//
//            }else{
//                adapterFitPot.notifyDataSetChanged();
//            }
//
//        }
//
//    }
//
//}

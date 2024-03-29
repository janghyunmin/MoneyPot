package com.quantec.moneypot.activity.prefer.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.quantec.moneypot.R;
import com.quantec.moneypot.activity.Main.Fragment.Tab1.DecorationItemVertical;
import com.quantec.moneypot.activity.prefer.ActivityPrefer;
import com.quantec.moneypot.activity.prefer.ModelEnterList;
import com.quantec.moneypot.activity.prefer.adapter.AdapterEnter;
import com.quantec.moneypot.datamodel.nmodel.ModelGetRateList;
import com.quantec.moneypot.network.retrofit.RetrofitClient;
import com.quantec.moneypot.rxandroid.RxEvent;
import com.quantec.moneypot.rxandroid.RxEventBus;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.quantec.moneypot.activity.prefer.ActivityPrefer.totalCount;

public class FgEnter extends Fragment {

    RecyclerView recyclerView;
    GridLayoutManager layoutManager;
    ArrayList<ModelEnterList> modelEnterLists;
    AdapterEnter adapterEnter;

    ActivityPrefer activityPrefer;
    View line;

    Bundle preferItem;

    boolean countState = true;

    ArrayList<ModelEnterList> addList;

    private ClickItem clickItem;
    public interface ClickItem{
        public void onClick(boolean state,int count, String item, String bunya, String title, int realPosition);
    }

    public FgEnter() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_prefer_enter, container, false);

        initializeViews();

        line = view.findViewById(R.id.line);

        preferItem = new Bundle();

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(activityPrefer, 3);
        recyclerView.setLayoutManager(layoutManager);

        modelEnterLists = new ArrayList<>();

        recyclerView.addItemDecoration(new DecorationItemVertical(activityPrefer, 23));
        adapterEnter = new AdapterEnter(modelEnterLists, getContext());
        recyclerView.setAdapter(adapterEnter);

        addList = new ArrayList<>();

        Call<ModelGetRateList> setSearch = RetrofitClient.getInstance(activityPrefer).getService().getRateList("one");
        setSearch.enqueue(new Callback<ModelGetRateList>() {
            @Override
            public void onResponse(Call<ModelGetRateList> call, Response<ModelGetRateList> response) {
                if(response.code() == 200) {
                    if(response.body().getStatus() == 200){
                        for(int index = 0; index < 9; index++){
                            if(response.body().getContent().get(index).getType() == 0){

                                modelEnterLists.add(new ModelEnterList(response.body().getContent().get(index).getName(),
                                        response.body().getContent().get(index).getCode(),
                                        response.body().getContent().get(index).getCode(),
                                        false, false, 2, false, false));
                            }
                        }

                        modelEnterLists.add(new ModelEnterList("","",
                                "",
                                false, false, 0, false, false));

                        for(int index = 9; index < response.body().getTotalElements(); index++){
                            if(response.body().getContent().get(index).getType() == 0){

                                addList.add(new ModelEnterList(response.body().getContent().get(index).getName(),
                                        response.body().getContent().get(index).getCode(),
                                        response.body().getContent().get(index).getCode(),
                                        false, false, 3, false, false));
                            }
                        }
                        adapterEnter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onFailure(Call<ModelGetRateList> call, Throwable t) {
                Log.e("레트로핏 실패","값 : "+t.getMessage());
            }
        });

        return view;
    }

    private void initializeViews(){
        activityPrefer = (ActivityPrefer) getActivity();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof ActivityPrefer) {
            activityPrefer = (ActivityPrefer) context;
            clickItem = (ActivityPrefer)getActivity();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if(modelEnterLists.get(position).getCategory() == 2){
                    return 1;
                }else{
                    return 3;
                }
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(layoutManager.findFirstCompletelyVisibleItemPosition() > 0) {
                    line.setVisibility(View.VISIBLE);
                }else{
                    line.setVisibility(View.INVISIBLE);
                }
            }
        });

        adapterEnter.setSectorItemClick(new AdapterEnter.SectorItemClick() {
            @Override
            public void onClick(int position) {

                if (modelEnterLists.get(position).isSelect()) {
                    modelEnterLists.get(position).setSelect(false);
                    totalCount--;
                    if(clickItem != null){
                        clickItem.onClick(false, totalCount, modelEnterLists.get(position).getImage(),"기업",modelEnterLists.get(position).getTitle(), position);
                    }

                }else {

                    if (totalCount < 5) {
                        modelEnterLists.get(position).setSelect(true);
                        totalCount++;
                        if (clickItem != null) {
                            clickItem.onClick(true, totalCount, modelEnterLists.get(position).getImage(), "기업", modelEnterLists.get(position).getTitle(), position);
                        }

                    } else {
                        Toast.makeText(activityPrefer, "최대 5개까지 선택 가능합니다.", Toast.LENGTH_SHORT).show();
                    }
                }
                adapterEnter.notifyItemChanged(position);
            }
        });


        RxEventBus.getInstance()
                .filteredObservable(RxEvent.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RxEvent>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }
                    @Override
                    public void onNext(RxEvent rxEvent) {

                        switch (rxEvent.getActiion()) {

                            case RxEvent.PREFER_ITEM_DELETE:
                                modelEnterLists.get(rxEvent.getBundle().getInt("realPosition")).setSelect(false);
                                adapterEnter.notifyItemChanged(rxEvent.getBundle().getInt("realPosition"));

                                if(totalCount == 0){
                                    countState = true;
                                    modelEnterLists.remove(modelEnterLists.size()-1);
                                    adapterEnter.notifyDataSetChanged();
                                }

                                break;
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                    }
                    @Override
                    public void onComplete() {
                    }
                });

        adapterEnter.setEnterMoreClick(new AdapterEnter.EnterMoreClick() {
            @Override
            public void onClick(int position) {
                modelEnterLists.remove(position);
                modelEnterLists.get(0).setVisible(true);

                modelEnterLists.add(new ModelEnterList("",
                        "", "",
                        false, false, 4, false, false));
                modelEnterLists.addAll(addList);
                adapterEnter.notifyDataSetChanged();
            }
        });

        adapterEnter.setEnterCate2Click(new AdapterEnter.EnterCate2Click() {
            @Override
            public void onClick(int position) {

                if(modelEnterLists.get(position).isCate2Click()){
                    modelEnterLists.get(position).setCate2Click(false);
                }else{
                    modelEnterLists.get(position).setCate2Click(true);
                }

                adapterEnter.notifyItemChanged(position);
            }
        });

    }//onViewCreated 끝
}

package quantec.com.moneypot.Activity.Main.Fragment.FgTab2;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.ModelPortList;
import quantec.com.moneypot.R;
import quantec.com.moneypot.RxAndroid.RxEvent;
import quantec.com.moneypot.RxAndroid.RxEventBus;

public class FgPotCookAllocation extends Fragment {

    ActivityPotCook activityPotCook;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ModelPotCookAllocation> modelPotCookAllocations;
    AdapterPotCookAllocation adapterPotCookAll;

    int currentPosition;
    //차트
    List<Entry> entries;
    LineDataSet lineDataSet;
    LineData lineData;

    Bundle stList;
    int itemCount;

    ArrayList<ModelPortList> modelPortLists;


    public FgPotCookAllocation() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_potcookallocation, container, false);

        initializeViews();

        stList = new Bundle();

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(activityPotCook);
        recyclerView.setLayoutManager(layoutManager);

        entries = new ArrayList<>();
        lineDataSet = new LineDataSet(entries, null);
        lineData = new LineData(lineDataSet);

        modelPotCookAllocations = new ArrayList<>();
        adapterPotCookAll = new AdapterPotCookAllocation(modelPotCookAllocations, activityPotCook, entries, lineDataSet, lineData);

        recyclerView.setAdapter(adapterPotCookAll);

        modelPortLists = new ArrayList<>();

        return view;
    }

    private void initializeViews(){
        activityPotCook = (ActivityPotCook) getActivity();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof ActivityPotCook) {
            activityPotCook = (ActivityPotCook) context;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        modelPotCookAllocations.add(new ModelPotCookAllocation(false,false,"대한민국 국가대표 기업들", "KODEX200", "", "MP0001"));
        modelPotCookAllocations.add(new ModelPotCookAllocation(false,false,"세계 경제를 이끄는 중국 기업", "TIGER 차이나CSI300", "", "MP0002"));
        modelPotCookAllocations.add(new ModelPotCookAllocation(false,false,"한국 경제의 중심! 삼성의 계열사", "KODEX 삼성그룹", "", "MP0003"));
        modelPotCookAllocations.add(new ModelPotCookAllocation(false,false,"헬스케어 업종에 투자", "TIGER 헬스케어", "", "MP0004"));
        modelPotCookAllocations.add(new ModelPotCookAllocation(false,false,"핵심은 4차 산업 기술", "TIGER 글로벌 4차산업 혁신기술(합성 H)", "", "MP0005"));
        modelPotCookAllocations.add(new ModelPotCookAllocation(false,false,"한국을 IT 강국으로 이끈 기업들", "TIGER 200 IT", "", "MP0006"));


//        for(int a = 0; a < 100 ; a++){
//            entries.add(new Entry(a, a*1.67f, a*1.25f));
//        }
//
//
//        adapterPotCookAll.setShowChartBtClick(new AdapterPotCookAll.ShowChartBtClick() {
//            @Override
//            public void onClick(int position) {
//
//                if(modelPotCookAlls.get(position).isOpenView()){
//                    modelPotCookAlls.get(position).setOpenView(false);
//                }else{
//                    modelPotCookAlls.get(currentPosition).setOpenView(false);
//                    modelPotCookAlls.get(position).setOpenView(true);
//                }
//
//                adapterPotCookAll.notifyDataSetChanged();
//                currentPosition = position;
//            }
//        });
//
//        adapterPotCookAll.setStAddBtClick(new AdapterPotCookAll.StAddBtClick() {
//            @Override
//            public void onClick(int position) {
//
//                if(modelPotCookAlls.get(position).isAddSt()){
//                    modelPotCookAlls.get(position).setAddSt(false);
//
//                    itemCount--;
//                    stList.putBoolean("click", false);
//                    stList.putInt("count",itemCount);
//                    stList.putString("code", modelPotCookAlls.get(position).getStCode());
//                    stList.putString("title", modelPotCookAlls.get(position).getStTitle());
//
//                    RxEventBus.getInstance().post(new RxEvent(RxEvent.ZZIM_PORT_SELECT_ITEM, stList));
//
//                }else{
//                    modelPotCookAlls.get(position).setAddSt(true);
//
//                    itemCount++;
//                    stList.putBoolean("click", true);
//                    stList.putInt("count",itemCount);
//                    stList.putString("code", modelPotCookAlls.get(position).getStCode());
//                    stList.putString("title", modelPotCookAlls.get(position).getStTitle());
//                    RxEventBus.getInstance().post(new RxEvent(RxEvent.ZZIM_PORT_SELECT_ITEM, stList));
//                }
//
//                adapterPotCookAll.notifyDataSetChanged();
//            }
//        });
//
//
//
//        RxEventBus.getInstance()
//                .filteredObservable(RxEvent.class)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<RxEvent>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                    }
//                    @Override
//                    public void onNext(RxEvent rxEvent) {
//
//                        switch (rxEvent.getActiion()) {
//
//                            case RxEvent.REFRESH_POT_COOK:
//
//                                for(int t = 0 ; t < modelPotCookAlls.size() ; t++){
//                                    modelPotCookAlls.get(t).setAddSt(false);
//                                }
//
//                                modelPortLists = rxEvent.getBundle().getParcelableArrayList("potList");
//                                itemCount = modelPortLists.size();
//
//                                for(int a = 0 ; a < modelPortLists.size() ; a++){
//                                    for(int b = 0 ; b < modelPotCookAlls.size() ; b++){
//                                        if(modelPotCookAlls.get(b).getStTitle().equals(modelPortLists.get(a).getStname())){
//                                            modelPotCookAlls.get(b).setAddSt(true);
//                                        }
//                                    }
//                                }
//                                adapterPotCookAll.notifyDataSetChanged();
//                                break;
//                        }
//                    }
//                    @Override
//                    public void onError(Throwable e) {
//                    }
//                    @Override
//                    public void onComplete() {
//                    }
//                });
    }
}

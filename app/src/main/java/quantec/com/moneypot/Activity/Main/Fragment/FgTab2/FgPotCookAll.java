package quantec.com.moneypot.Activity.Main.Fragment.FgTab2;

import android.content.Context;
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

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

import quantec.com.moneypot.R;
import quantec.com.moneypot.RxAndroid.RxEvent;
import quantec.com.moneypot.RxAndroid.RxEventBus;
import quantec.com.moneypot.Util.DecimalScale.DecimalScale;

public class FgPotCookAll extends Fragment{


    ActivityPotCook activityPotCook;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ModelPotCookAll> modelPotCookAlls;
    AdapterPotCookAll adapterPotCookAll;

    int currentPosition;

    //차트
    List<Entry> entries;
    LineDataSet lineDataSet;
    LineData lineData;

    Bundle stList;
    int itemCount;

    public FgPotCookAll() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fg_potcookall, container, false);

        initializeViews();

        stList = new Bundle();

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(activityPotCook);
        recyclerView.setLayoutManager(layoutManager);

        entries = new ArrayList<>();
        lineDataSet = new LineDataSet(entries, null);
        lineData = new LineData(lineDataSet);

        modelPotCookAlls = new ArrayList<>();
        adapterPotCookAll = new AdapterPotCookAll(modelPotCookAlls, activityPotCook, entries, lineDataSet, lineData);

        recyclerView.setAdapter(adapterPotCookAll);

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

        modelPotCookAlls.add(new ModelPotCookAll(false,false,"대한민국 국가대표 기업들", "KODEX200", "", ""));
        modelPotCookAlls.add(new ModelPotCookAll(false,false,"세계 경제를 이끄는 중국 기업", "TIGER 차이나CSI300", "", ""));
        modelPotCookAlls.add(new ModelPotCookAll(false,false,"한국 경제의 중심! 삼성의 계열사", "KODEX 삼성그룹", "", ""));
        modelPotCookAlls.add(new ModelPotCookAll(false,false,"헬스케어 업종에 투자", "TIGER 헬스케어", "", ""));
        modelPotCookAlls.add(new ModelPotCookAll(false,false,"핵심은 4차 산업 기술", "TIGER 글로벌 4차산업 혁신기술(합성 H)", "", ""));
        modelPotCookAlls.add(new ModelPotCookAll(false,false,"한국을 IT 강국으로 이끈 기업들", "TIGER 200 IT", "", ""));


        for(int a = 0; a < 100 ; a++){
            entries.add(new Entry(a, a*1.67f, a*1.25f));
        }


        adapterPotCookAll.setShowChartBtClick(new AdapterPotCookAll.ShowChartBtClick() {
            @Override
            public void onClick(int position) {

                if(modelPotCookAlls.get(position).isOpenView()){
                    modelPotCookAlls.get(position).setOpenView(false);
                }else{
                    modelPotCookAlls.get(currentPosition).setOpenView(false);
                    modelPotCookAlls.get(position).setOpenView(true);
                }

                adapterPotCookAll.notifyDataSetChanged();
                currentPosition = position;
            }
        });

        adapterPotCookAll.setStAddBtClick(new AdapterPotCookAll.StAddBtClick() {
            @Override
            public void onClick(int position) {

                if(modelPotCookAlls.get(position).isAddSt()){
                    modelPotCookAlls.get(position).setAddSt(false);

                    itemCount--;
                    stList.putBoolean("click", false);
                    stList.putInt("count",itemCount);
                    stList.putString("code", modelPotCookAlls.get(position).getStCode());
                    stList.putString("title", modelPotCookAlls.get(position).getStTitle());

                    RxEventBus.getInstance().post(new RxEvent(RxEvent.ZZIM_PORT_SELECT_ITEM, stList));

                }else{
                    modelPotCookAlls.get(position).setAddSt(true);

                    itemCount++;
                    Log.e("전 갯수","값 : "+itemCount);
                    stList.putBoolean("click", true);
                    stList.putInt("count",itemCount);
                    stList.putString("code", modelPotCookAlls.get(position).getStCode());
                    stList.putString("title", modelPotCookAlls.get(position).getStTitle());
                    RxEventBus.getInstance().post(new RxEvent(RxEvent.ZZIM_PORT_SELECT_ITEM, stList));
                }

                adapterPotCookAll.notifyDataSetChanged();
            }
        });



    }
}

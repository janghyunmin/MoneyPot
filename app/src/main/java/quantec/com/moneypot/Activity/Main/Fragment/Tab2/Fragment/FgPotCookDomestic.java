package quantec.com.moneypot.Activity.Main.Fragment.Tab2.Fragment;

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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import quantec.com.moneypot.Activity.Main.Fragment.Tab2.Activity.ActivityPotCook;
import quantec.com.moneypot.Activity.Main.Fragment.Tab2.Adapter.AdapterPotCookAll;
import quantec.com.moneypot.Activity.PotDetail.ActivityPotDetail;
import quantec.com.moneypot.Activity.PotDetail.DateMathDto;
import quantec.com.moneypot.Activity.PotDetail.DateMathDtoResult;
import quantec.com.moneypot.DataModel.dModel.Filter;
import quantec.com.moneypot.DataModel.dModel.ModelPortList;
import quantec.com.moneypot.DataModel.dModel.ModelPotCookAll;
import quantec.com.moneypot.DataModel.nModel.ModelChartData;
import quantec.com.moneypot.DataModel.nModel.ModelPotList;
import quantec.com.moneypot.Network.Retrofit.RetrofitClient;
import quantec.com.moneypot.R;
import quantec.com.moneypot.RxAndroid.RxEvent;
import quantec.com.moneypot.RxAndroid.RxEventBus;
import quantec.com.moneypot.util.DecimalScale.DecimalScale;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FgPotCookDomestic extends Fragment {

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

    ArrayList<ModelPortList> modelPortLists;

    ArrayList<DateMathDto> date;
    List<DateMathDtoResult> resultDate;
    double exp = 0.0;

    public FgPotCookDomestic() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_makepot_fgpotcookdomestic, container, false);

        initializeViews();

        date = new ArrayList<>();
        resultDate = new ArrayList<>();

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

//        modelPotCookAlls.add(new ModelPotCookAll(false,false,"대한민국 국가대표 기업들", "KODEX200", "", "MP0001"));
//        modelPotCookAlls.add(new ModelPotCookAll(false,false,"세계 경제를 이끄는 중국 기업", "TIGER 차이나CSI300", "", "MP0002"));
//        modelPotCookAlls.add(new ModelPotCookAll(false,false,"한국 경제의 중심! 삼성의 계열사", "KODEX 삼성그룹", "", "MP0003"));
//        modelPotCookAlls.add(new ModelPotCookAll(false,false,"헬스케어 업종에 투자", "TIGER 헬스케어", "", "MP0004"));
//        modelPotCookAlls.add(new ModelPotCookAll(false,false,"핵심은 4차 산업 기술", "TIGER 글로벌 4차산업 혁신기술(합성 H)", "", "MP0005"));
//        modelPotCookAlls.add(new ModelPotCookAll(false,false,"한국을 IT 강국으로 이끈 기업들", "TIGER 200 IT", "", "MP0006"));


        Filter filter = new Filter();
        Call<ModelPotList> getReList = RetrofitClient.getInstance().getService().getPotList("application/json", filter, "EP", 0,0,10);
        getReList.enqueue(new Callback<ModelPotList>() {
            @Override
            public void onResponse(Call<ModelPotList> call, Response<ModelPotList> response) {
                if (response.code() == 200) {

                    if(response.body().getTotalElements() == 0){
                        modelPotCookAlls.add(new ModelPotCookAll(false,false, "",
                                "",
                                "",
                                ""));
                    }else{
                        for(int index = 0; index < response.body().getContent().size() ; index++){

                            modelPotCookAlls.add(new ModelPotCookAll(false,false,response.body().getContent().get(index).getDescript(),
                                    response.body().getContent().get(index).getName(),
                                    "",
                                    response.body().getContent().get(index).getCode()));
                        }
                    }

//                    for(int index = 0; index < response.body().getContent().size() ; index++){
//
//                        modelPotCookAlls.add(new ModelPotCookAll(false,false,response.body().getContent().get(index).getDescript(),
//                                response.body().getContent().get(index).getName(),
//                                "",
//                                response.body().getContent().get(index).getCode()));
//                    }

                    adapterPotCookAll.notifyDataSetChanged();

                }
            }
            @Override
            public void onFailure(Call<ModelPotList> call, Throwable t) {
            }
        });


//        filter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                filter.setText("배당");
//
//                modelPotCookAlls.clear();
//
//                modelPotCookAlls.add(new ModelPotCookAll(false,false,"11대한민국 국가대표 기업들", "KODEX200", "", "MP0001"));
//                modelPotCookAlls.add(new ModelPotCookAll(false,false,"22세계 경제를 이끄는 중국 기업", "TIGER 차이나CSI300", "", "MP0002"));
//                modelPotCookAlls.add(new ModelPotCookAll(false,false,"33한국 경제의 중심! 삼성의 계열사", "KODEX 삼성그룹", "", "MP0003"));
//                modelPotCookAlls.add(new ModelPotCookAll(false,false,"44헬스케어 업종에 투자", "TIGER 헬스케어", "", "MP0004"));
//                modelPotCookAlls.add(new ModelPotCookAll(false,false,"55핵심은 4차 산업 기술", "TIGER 글로벌 4차산업 혁신기술(합성 H)", "", "MP0005"));
//                modelPotCookAlls.add(new ModelPotCookAll(false,false,"66한국을 IT 강국으로 이끈 기업들", "TIGER 200 IT", "", "MP0006"));
//
//                adapterPotCookAll.notifyDataSetChanged();
//            }
//        });

//        for(int a = 0; a < 100 ; a++){
//            entries.add(new Entry(a, a*1.67f, a*1.25f));
//        }


        adapterPotCookAll.setShowChartBtClick(new AdapterPotCookAll.ShowChartBtClick() {
            @Override
            public void onClick(int position) {

                if(modelPotCookAlls.get(position).isOpenView()){
                    modelPotCookAlls.get(position).setOpenView(false);

                    adapterPotCookAll.notifyItemChanged(position);

                }else{
                    modelPotCookAlls.get(currentPosition).setOpenView(false);
                    adapterPotCookAll.notifyItemChanged(currentPosition);

                    modelPotCookAlls.get(position).setOpenView(true);
                    ChartDur(modelPotCookAlls.get(position).getStCode(),0, 0, position);

                    recyclerView.smoothScrollToPosition(position);
                }
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

                        if(itemCount >= 4){
                            Toast.makeText(activityPotCook, "선택은 4개까지 가능합니다.", Toast.LENGTH_SHORT).show();
                        }else{
                            modelPotCookAlls.get(position).setAddSt(true);
                            itemCount++;
                            stList.putBoolean("click", true);
                            stList.putInt("count",itemCount);
                            stList.putString("code", modelPotCookAlls.get(position).getStCode());
                            stList.putString("title", modelPotCookAlls.get(position).getStTitle());
                            RxEventBus.getInstance().post(new RxEvent(RxEvent.ZZIM_PORT_SELECT_ITEM, stList));
                        }
                    }
                    adapterPotCookAll.notifyDataSetChanged();
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

                            case RxEvent.REFRESH_POT_COOK:

                                for(int t = 0 ; t < modelPotCookAlls.size() ; t++){
                                    modelPotCookAlls.get(t).setAddSt(false);
                                }

                                modelPortLists = rxEvent.getBundle().getParcelableArrayList("potList");
                                itemCount = modelPortLists.size();

                                for(int a = 0 ; a < modelPortLists.size() ; a++){
                                    for(int b = 0 ; b < modelPotCookAlls.size() ; b++){
                                        if(modelPotCookAlls.get(b).getStTitle().equals(modelPortLists.get(a).getStname())){
                                            modelPotCookAlls.get(b).setAddSt(true);
                                        }
                                    }
                                }
                                adapterPotCookAll.notifyDataSetChanged();
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


        adapterPotCookAll.setPotCookAllItemClick(new AdapterPotCookAll.PotCookAllItemClick() {
            @Override
            public void onClick(int position) {

                Intent intent = new Intent(activityPotCook, ActivityPotDetail.class);
                intent.putExtra("potCode", modelPotCookAlls.get(position).getStCode());
                startActivity(intent);

            }
        });

    }


    void ChartDur(String code, int pDate, int propensity, int position) {

        Call<ModelChartData> getTest2 = RetrofitClient.getInstance().getService().getPotChartData(code, pDate, propensity);
        getTest2.enqueue(new Callback<ModelChartData>() {
            @Override
            public void onResponse(Call<ModelChartData> call, Response<ModelChartData> response) {
                if(response.code() == 200) {

                    date.clear();
                    entries.clear();

                    for(int index = 0 ; index < response.body().getContent().size() ; index++) {

                        entries.add(new Entry(index,
                                DecimalScale.decimalScale2(String.valueOf(response.body().getContent().get(index).getExp()*100), 2, 2),
                                response.body().getContent().get(index).getDate()));
                    }

                    adapterPotCookAll.notifyItemChanged(position);
                }
            }
            @Override
            public void onFailure(Call<ModelChartData> call, Throwable t) {
                Log.e("레트로핏 실패","값 : "+t.getMessage());
            }
        });
    }


    private String startDate(String endDate, int month){
        exp = 0.0;
        resultDate.clear();
        entries.clear();

        String laterDay = null;
        try {
            Calendar cal = Calendar.getInstance();
            Date originDate = new Date();
            Date laterDate = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); //날짜 형식에 따라서 달리 할 수 있다.
            originDate = format.parse(endDate);
            cal.setTime(laterDate);
            cal.add(Calendar.MONTH, month);        //laterCnt 만큼 후의 날짜를 구한다. laterCnt 자리에 -7 을 입력하면 일주일전에 날짜를 구할 수 있다.
            laterDate = cal.getTime();
            laterDay = format.format(laterDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return laterDay;
    }

    private void calDate(String startDate, boolean monTh){
        for(int index = 0 ; index < date.size(); index++){
            if(monTh){
                exp += (date.get(index).getPrice() / date.get(index-1).getPrice()) -1;
                resultDate.add(new DateMathDtoResult(date.get(index).getDate(), exp));
            }
            if(date.get(index).getDate().equals(startDate) && !monTh){
                monTh =true;
                resultDate.add(new DateMathDtoResult(date.get(index).getDate(), exp));
            }
        }
        for(int index = 0 ; index < resultDate.size() ; index++){
            entries.add(new Entry(index, DecimalScale.decimalScale2(String.valueOf(resultDate.get(index).getExp()*100), 2, 2),
                    resultDate.get(index).getDate()));
        }
    }

}

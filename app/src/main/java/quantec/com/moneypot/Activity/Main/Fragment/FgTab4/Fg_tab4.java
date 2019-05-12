package quantec.com.moneypot.Activity.Main.Fragment.FgTab4;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import quantec.com.moneypot.Activity.DetailPort.ActivityDetailPort;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab3.Fragment.Tab1_3m.Model.nModel.ModelTab13mChartData;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab3.Fragment.Tab1_3m.Model.nModel.ModelZimData;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab3.Fragment.Tab1_3m.Select;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab4.Adapter.AdapterFgTab4;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab4.Dialog.DialogAllZzimDelete;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab4.Model.dModel.ModelFgTab4;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab4.Model.nModel.ModelFgTab4ZimData;
import quantec.com.moneypot.Activity.Main.MainActivity;
import quantec.com.moneypot.Activity.Payment.ActivityPayment;
import quantec.com.moneypot.DataManager.DataManager;
import quantec.com.moneypot.Network.Retrofit.RetrofitClient;
import quantec.com.moneypot.R;
import quantec.com.moneypot.RxAndroid.RxEvent;
import quantec.com.moneypot.RxAndroid.RxEventBus;
import quantec.com.moneypot.Util.DecimalScale.DecimalScale;
import quantec.com.moneypot.Util.SharedPreferenceUtil.SharedPreferenceUtil;
import quantec.com.moneypot.databinding.FgTab4Binding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fg_tab4 extends Fragment {

    FgTab4Binding tab4Binding;
    //차트
    List<Entry> entries;
    LineDataSet lineDataSet;
    LineData lineData;

    RecyclerView.LayoutManager mLayoutManager;
    AdapterFgTab4 myAdapter;
    ArrayList<ModelFgTab4> myData;

    private MainActivity mainActivity;

    String packName;
    //데이터 리프레쉬 할때 딜레이 줌 ( 반복적인 딜레이 막음 )
    boolean DelayedRefresh = false;
    private DialogAllZzimDelete customDialogAll;

    public static boolean portOpenState = true;
    public static boolean Delete_State = false;

    public Fg_tab4() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        tab4Binding = DataBindingUtil.inflate(inflater, R.layout.fg_tab4, container, false);

        initializeViews();

        tab4Binding.tab4RecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        tab4Binding.tab4RecyclerView.setLayoutManager(mLayoutManager);
        myData = new ArrayList<>();

        entries = new ArrayList<>();
        lineDataSet = new LineDataSet(entries, null);
        lineData = new LineData(lineDataSet);
        myAdapter = new AdapterFgTab4(getContext(),myData, entries, lineDataSet, lineData);
        tab4Binding.tab4RecyclerView.setAdapter(myAdapter);

        packName = getActivity().getPackageName();

        return tab4Binding.getRoot();
    }

    private void initializeViews(){
        mainActivity = (MainActivity) getActivity();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof MainActivity) {
            mainActivity = (MainActivity) context;
        }
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 데이터 초기화
        initData();
        //데이터 갱신
        tab4Binding.tab4RecyclerViewRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(DelayedRefresh) {
                    tab4Binding.tab4RecyclerViewRefresh.setRefreshing(false);
                }else {
                    DelayedRefresh = true;
                    tab4Binding.tab4RecyclerViewRefresh.setRefreshing(false);
                    initData();
                }
            }
        });
        // 찜 전체 해제
        tab4Binding.tab4DeleteBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialogAll = new DialogAllZzimDelete(getContext(), ALLleftListener, ALLrightListener);
                customDialogAll.show();
            }
        });
        //아이템 클릭이벤트 차트 열림
        myAdapter.setItemClick(new AdapterFgTab4.ItemClick() {
            @Override
            public void onClick(int position) {

                if (myData.get(position).isOnenChart()) {
                    myData.get(position).setOnenChart(false);
                    myAdapter.notifyDataSetChanged();
                } else {
                    for (int a = 0; a < myData.size(); a++) {
                        try {
                            myData.get(a).setOnenChart(false);
                        }catch (Exception e){}
                    }

                    Call<ModelTab13mChartData> getTest2 = RetrofitClient.getInstance().getService().getRankPort(1, myData.get(position).getCode(),700);
                    getTest2.enqueue(new Callback<ModelTab13mChartData>() {
                        @Override
                        public void onResponse(Call<ModelTab13mChartData> call, Response<ModelTab13mChartData> response) {
                            if(response.code() == 200) {
                                entries.clear();
                                for(int a = 0 ; a < response.body().getContent().size() ; a++) {
                                    entries.add(new Entry(a, DecimalScale.decimalScale2(String.valueOf(response.body().getContent().get(a).getExp()*100), 2, 2), response.body().getContent().get(a).getDate()));
                                }
                                myData.get(position).setOnenChart(true);
                                myAdapter.notifyDataSetChanged();
                                tab4Binding.tab4RecyclerView.smoothScrollToPosition(position);
                            }
                        }
                        @Override
                        public void onFailure(Call<ModelTab13mChartData> call, Throwable t) {
                            Log.e("레트로핏 실패","값 : "+t.getMessage());
                        }
                    });

                }
            }
        });

        //차트 1개월 버튼 클릭
        myAdapter.setSelectDurItemClick1(new AdapterFgTab4.SelectDur1ItemClick() {
            @Override
            public void onclick(int position) {
                ChartDur(position, 30);
            }
        });
        //차트 3개월 버튼 클릭
        myAdapter.setSelectDurItemClick3(new AdapterFgTab4.SelectDur3ItemClick() {
            @Override
            public void onclick(int position) {
                ChartDur(position, 90);
            }
        });
        //차트 6개월 버튼 클릭
        myAdapter.setSelectDurItemClick6(new AdapterFgTab4.SelectDur6ItemClick() {
            @Override
            public void onclick(int position) {
                ChartDur(position, 180);
            }
        });
        //차트 누적 버튼 클릭
        myAdapter.setSelectDurItemClicka(new AdapterFgTab4.SelectDuraItemClick() {
            @Override
            public void onclick(int position) {
                ChartDur(position, 700);
            }
        });

        //더보기 버튼
        myAdapter.setSelectAddPortDetailClick(new AdapterFgTab4.SelectAddPortDetailClick() {
            @Override
            public void onclick(int position) {
                Intent intent1 = new Intent(getActivity(), ActivityDetailPort.class);
                intent1.putExtra("detailcode", myData.get(position).getCode());
                intent1.putExtra("detailtitle", myData.get(position).getTitle());
                intent1.putExtra("ZzimPortPosition", position);
                startActivityForResult(intent1, 600);
            }
        });

        //투자하기 버튼
        myAdapter.setSelectInvestClick(new AdapterFgTab4.SelectInvestClick() {
            @Override
            public void onClick(int position) {
                Intent intent1 = new Intent(getActivity(), ActivityPayment.class);
                intent1.putExtra("detailcode", myData.get(position).getCode());
                intent1.putExtra("detailtitle", myData.get(position).getTitle());
                intent1.putExtra("mincost", myData.get(position).getMincost());
                startActivity(intent1);
            }
        });

        //포트 찜 클릭
        myAdapter.setSelectZzimClick(new AdapterFgTab4.SelectZzimClick() {
            @Override
            public void onClick(int position) {
                if(myData.get(position).isZim()) {

                    Select select = new Select(myData.get(position).getCode(),myData.get(position).isDam(),false);

                    Call<ModelZimData> getSelectPort = RetrofitClient.getInstance().getService().getSelectedPortDate("application/json",select, 1,"del");
                    getSelectPort.enqueue(new Callback<ModelZimData>() {
                        @Override
                        public void onResponse(Call<ModelZimData> call, Response<ModelZimData> response) {
                            if(response.code() == 200) {
                                if(response.body().getErrorcode() == 200){

                                    myData.get(position).setZim(false);
                                    myAdapter.notifyItemChanged(position);

                                    DataManager.get_INstance().setCheckTab1(true);
                                    DataManager.get_INstance().setCheckCookPage(true);

                                    Bundle bundle = new Bundle();
                                    bundle.putString("rankcode", myData.get(position).getCode());
                                    RxEventBus.getInstance().post(new RxEvent(RxEvent.RANK_PORT_CHECK_NO, bundle));

                                    int zimCount = 0;
                                    for(int index = 0 ; index < response.body().getTotalElements() ; index++) {
                                        if(response.body().getContent().get(index).isZim()) {
                                            zimCount++;
                                        }
                                    }
                                    SharedPreferenceUtil.getInstance(mainActivity).putIntZzimCount("PortZzimCount", zimCount);
                                }
                            }
                            else{

                                Log.e("에러 값 ","값 : "+ response.errorBody().toString());
                            }
                        }
                        @Override
                        public void onFailure(Call<ModelZimData> call, Throwable t) {
                            Toast.makeText(getActivity(),"네트워크가 불안정 합니다\n 다시 시도해 주세요.",Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                else{
                        Select select = new Select(myData.get(position).getCode(),myData.get(position).isDam(),true);

                        Call<ModelZimData> getSelectPort = RetrofitClient.getInstance().getService().getSelectedPortDate("application/json",select, 1,"add");
                        getSelectPort.enqueue(new Callback<ModelZimData>() {
                            @Override
                            public void onResponse(Call<ModelZimData> call, Response<ModelZimData> response) {
                                if(response.code() == 200) {
                                    if(response.body().getErrorcode() == 200){

                                        myData.get(position).setZim(true);
                                        myAdapter.notifyItemChanged(position);

                                        DataManager.get_INstance().setCheckTab1(true);
                                        DataManager.get_INstance().setCheckCookPage(true);

                                        Bundle bundle = new Bundle();
                                        bundle.putString("rankcode", myData.get(position).getCode());
                                        RxEventBus.getInstance().post(new RxEvent(RxEvent.RANK_PORT_CHECK_OK, bundle));

                                        int zimCount = 0;
                                        for(int index = 0 ; index < response.body().getTotalElements() ; index++) {
                                            if(response.body().getContent().get(index).isZim()) {
                                                zimCount++;
                                            }
                                        }
                                        SharedPreferenceUtil.getInstance(mainActivity).putIntZzimCount("PortZzimCount", zimCount);
                                    }
                                }
                                else{
                                    Log.e("에러 값 ","값 : "+ response.errorBody().toString());
                                }
                            }
                            @Override
                            public void onFailure(Call<ModelZimData> call, Throwable t) {
                                Toast.makeText(getActivity(),"네트워크가 불안정 합니다\n 다시 시도해 주세요.",Toast.LENGTH_SHORT).show();
                            }
                        });
                }
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
                            case RxEvent.ZZIM_PORT_LOAD:
                                portOpenState = true;
                                myData.clear();
                                tab4Binding.tab4LoadingBar.setVisibility(View.VISIBLE);
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Thread.sleep(1500);
                                            try {

                                                Call<ModelFgTab4ZimData> getData = RetrofitClient.getInstance().getService().getZimDamList();
                                                getData.enqueue(new Callback<ModelFgTab4ZimData>() {
                                                    @Override
                                                    public void onResponse(Call<ModelFgTab4ZimData> call, Response<ModelFgTab4ZimData> response) {
                                                        if(response.code() == 200) {
                                                            if(response.body().getErrorcode() == 200) {
                                                                if(response.body().getTotalElements() != 0){
                                                                    String name;
                                                                    for(int a = 0 ; a < response.body().getTotalElements(); a++) {
                                                                        if(response.body().getContent().get(a).isZim()){
                                                                            name = "@drawable/ic_" + response.body().getContent().get(a).getCode();

                                                                            myData.add(new ModelFgTab4(response.body().getContent().get(a).getName(), response.body().getContent().get(a).getCode(),
                                                                                    DecimalScale.decimalScale(String.valueOf(response.body().getContent().get(a).getRate()*100), 2, 2), response.body().getContent().get(a).isZim(),
                                                                                    response.body().getContent().get(a).isDam(), name, false, response.body().getContent().get(a).getMinPrice()));
                                                                        }
                                                                    }
                                                                    myAdapter.notifyDataSetChanged();
                                                                    tab4Binding.tab4LoadingBar.setVisibility(View.GONE);
                                                                    portOpenState = false;
                                                                }else{
                                                                    portOpenState = false;
                                                                    tab4Binding.tab4LoadingBar.setVisibility(View.GONE);
                                                                }
                                                            }
                                                        }
                                                    }
                                                    @Override
                                                    public void onFailure(Call<ModelFgTab4ZimData> call, Throwable t) {
                                                        Log.e("레트로핏 실패","값 : "+t.getMessage());
                                                    }
                                                });

                                            }catch (Exception e){}
                                        } catch (InterruptedException e) {
                                        }
                                    }
                                }).start();
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

    }//onViewCreated 끝

    //찜 전체 삭제 취소
    private View.OnClickListener ALLleftListener = new View.OnClickListener() {
        public void onClick(View v) {
            customDialogAll.dismiss();
        }
    };

    //찜 전체 삭제 확인
    private View.OnClickListener ALLrightListener = new View.OnClickListener() {
        public void onClick(View v) {
            zzimPortAllDeleteSave();
        }
    };

    //찜한 포트 삭제 후 갱신 ( 전체 삭제 )
    void zzimPortAllDeleteSave(){

        Select select = new Select("MP",false,false);

        Call<ModelZimData> getSelectPort = RetrofitClient.getInstance().getService().getSelectedPortDate("application/json",select, 1,"ALL");
        getSelectPort.enqueue(new Callback<ModelZimData>() {
            @Override
            public void onResponse(Call<ModelZimData> call, Response<ModelZimData> response) {
                if(response.code() == 200) {
                    if(response.body().getErrorcode() == 200){
                        for(int count = 0 ; count < myData.size() ; count++) {
                            myData.get(count).setZim(false);
                        }
                        myAdapter.notifyDataSetChanged();
                        customDialogAll.dismiss();
                        DataManager.get_INstance().setCheckTab1(true);
                        RxEventBus.getInstance().post(new RxEvent(RxEvent.ZZIM_PORT_DELETE_MODIFY, null));
                        DataManager.get_INstance().setCheckCookPage(true);

                        int zimCount = 0;
                        for(int index = 0 ; index < response.body().getTotalElements() ; index++) {
                            if(response.body().getContent().get(index).isZim()) {
                                zimCount++;
                            }
                        }
                        SharedPreferenceUtil.getInstance(mainActivity).putIntZzimCount("PortZzimCount", zimCount);
                    }
                }
                else{
                    Log.e("에러 값 ","값 : "+ response.errorBody().toString());
                }
            }
            @Override
            public void onFailure(Call<ModelZimData> call, Throwable t) {
                customDialogAll.dismiss();
                Toast.makeText(getActivity(),"네트워크가 불안정 합니다\n 다시 시도해 주세요.",Toast.LENGTH_SHORT).show();
            }
        });

    }


    //각 차트의 1개월 ~ 누적 버튼
    void ChartDur(int position, int dur) {


        Call<ModelTab13mChartData> getTest2 = RetrofitClient.getInstance().getService().getRankPort(1, myData.get(position).getCode(), dur);
        getTest2.enqueue(new Callback<ModelTab13mChartData>() {
            @Override
            public void onResponse(Call<ModelTab13mChartData> call, Response<ModelTab13mChartData> response) {
                if(response.code() == 200) {
                    entries.clear();
                    for(int a = 0 ; a < response.body().getContent().size() ; a++) {
                        entries.add(new Entry(a, DecimalScale.decimalScale2(String.valueOf(response.body().getContent().get(a).getExp()*100), 2, 2), response.body().getContent().get(a).getDate()));
                    }
                    myData.get(position).setOnenChart(true);
                    myAdapter.notifyDataSetChanged();
                    tab4Binding.tab4RecyclerView.smoothScrollToPosition(position);
                }
            }
            @Override
            public void onFailure(Call<ModelTab13mChartData> call, Throwable t) {
                Log.e("레트로핏 실패","값 : "+t.getMessage());
            }
        });
    }

    //찜 포트 데이터 초기화
    void initData() {

        myData.clear();
        tab4Binding.tab4LoadingBar.setVisibility(View.VISIBLE);
        DataManager.get_INstance().setCheckTab1(false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1500);
                    DelayedRefresh = false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Call<ModelFgTab4ZimData> getData = RetrofitClient.getInstance().getService().getZimDamList();
                getData.enqueue(new Callback<ModelFgTab4ZimData>() {
                    @Override
                    public void onResponse(Call<ModelFgTab4ZimData> call, Response<ModelFgTab4ZimData> response) {
                        if(response.code() == 200) {
                            if(response.body().getErrorcode() == 200) {
                                if(response.body().getTotalElements() != 0){
                                    String name;
                                    for(int a = 0 ; a < response.body().getTotalElements(); a++) {
                                        if(response.body().getContent().get(a).isZim()) {
                                            name = "@drawable/ic_" + response.body().getContent().get(a).getCode();

                                            myData.add(new ModelFgTab4(response.body().getContent().get(a).getName(), response.body().getContent().get(a).getCode(),
                                                    DecimalScale.decimalScale(String.valueOf(response.body().getContent().get(a).getRate()*100), 2, 2), response.body().getContent().get(a).isZim(),
                                                    response.body().getContent().get(a).isDam(), name, false, response.body().getContent().get(a).getMinPrice()));
                                        }
                                    }
                                    myAdapter.notifyDataSetChanged();
                                    tab4Binding.tab4LoadingBar.setVisibility(View.GONE);
                                    portOpenState = false;
                                }else{
                                    portOpenState = false;
                                    tab4Binding.tab4LoadingBar.setVisibility(View.GONE);
                                }
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<ModelFgTab4ZimData> call, Throwable t) {
                        Log.e("레트로핏 실패","값 : "+t.getMessage());
                    }
                });
            }
        }).start();

    }//initData 끝

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        if(DataManager.get_INstance().isCheckTab1()) {
            DataManager.get_INstance().setCheckTab1(false);
            RxEventBus.getInstance().post(new RxEvent(RxEvent.ZZIM_PORT_LOAD, null));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 600) {
            if(resultCode == 500) {
                myData.get(data.getIntExtra("ZzimPositionD",0)).setZim(true);
                myAdapter.notifyItemChanged(data.getIntExtra("ZzimPositionD",0));
                DataManager.get_INstance().setCheckTab1(true);
                DataManager.get_INstance().setCheckCookPage(true);
            }else if(resultCode == 501) {
                myData.get(data.getIntExtra("ZzimPositionD",0)).setZim(false);
                myAdapter.notifyItemChanged(data.getIntExtra("ZzimPositionD",0));
                DataManager.get_INstance().setCheckTab1(true);
                DataManager.get_INstance().setCheckCookPage(true);
            }
        }
    }
}

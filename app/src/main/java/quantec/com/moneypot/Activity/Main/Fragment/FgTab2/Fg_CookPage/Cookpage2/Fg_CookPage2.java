package quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.Cookpage2;

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
import android.widget.LinearLayout;
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
import quantec.com.moneypot.Activity.FinishMakePort.ActivityFinishMakePort;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab1.Model.nModel.ModelMiddleChartData;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.Cookpage2.Adapter.AdapterCookPage2;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.Cookpage2.Dialog.DialogCookPage2Delete;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.Cookpage2.Model.dModel.ModelCookList;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.Cookpage2.Model.nModel.ModelCookPage2;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab3.Fragment.Tab1_3m.Model.nModel.ModelTab13mChartData;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab3.Fragment.Tab1_3m.Model.nModel.ModelZimData;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab3.Fragment.Tab1_3m.Select;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab4.Model.nModel.ModelFgTab4ZimData;
import quantec.com.moneypot.Activity.Payment.ActivityPayment;
import quantec.com.moneypot.DataManager.DataManager;
import quantec.com.moneypot.Network.Retrofit.RetrofitClient;
import quantec.com.moneypot.R;
import quantec.com.moneypot.RxAndroid.RxEvent;
import quantec.com.moneypot.RxAndroid.RxEventBus;
import quantec.com.moneypot.databinding.FgFgtab2Fgcookpage2Binding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fg_CookPage2 extends Fragment {

    LinearLayoutManager linearLayoutManager;
    AdapterCookPage2 adapterCookPage2;
    ArrayList<ModelCookList> modelCookLists;

    public static boolean CookDeleteState = false;
    private DialogCookPage2Delete customDialog;
    //포트 삭제시 해당 포지션 //포트 선택시 포지션
    int DeletePosition, SelectPosition;
    //포트만들기 아이템 선택 갯수 ( 5개 이하 선택 가능 )
    int ItemCount = 0;
    //포트만들기 아이템 선택 포지션 저장 ( 클릭 온 오프에 따른 선택 및 취소 )
    ArrayList<Integer> savePosition;
    //차트
    List<Entry> entries;
    LineDataSet lineDataSet;
    LineData lineData;
    //
    Bundle minCostTeans;
    //데이터 리프레쉬 할때 딜레이 줌 ( 반복적인 딜레이 막음 )
    boolean DelayedRefresh = false;
    //포트만들기시 리프레쉬 막기
    boolean MakePortRefresh = false;
    FgFgtab2Fgcookpage2Binding fgcookpage2Binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fgcookpage2Binding = DataBindingUtil.inflate(inflater, R.layout.fg_fgtab2_fgcookpage2, container, false);

        fgcookpage2Binding.cookpage2RecyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext());
        fgcookpage2Binding.cookpage2RecyclerView.setLayoutManager(linearLayoutManager);

        modelCookLists = new ArrayList<>();

        entries = new ArrayList<>();
        lineDataSet = new LineDataSet(entries, null);
        lineData = new LineData(lineDataSet);

        adapterCookPage2 = new AdapterCookPage2(modelCookLists, getContext(), entries, lineDataSet, lineData);
        fgcookpage2Binding.cookpage2RecyclerView.setAdapter(adapterCookPage2);

        savePosition = new ArrayList<>();
        minCostTeans = new Bundle();

        return fgcookpage2Binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 데이터 초기화
        initData();

        //페이지 갱신 이벤트
        fgcookpage2Binding.fgCookpage2RecyclerViewRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if(MakePortRefresh) {
                    fgcookpage2Binding.fgCookpage2RecyclerViewRefresh.setRefreshing(false);
                }else{

                    if(DelayedRefresh){
                        fgcookpage2Binding.fgCookpage2RecyclerViewRefresh.setRefreshing(false);
                    }else{
                        DelayedRefresh = true;
                        fgcookpage2Binding.fgCookpage2RecyclerViewRefresh.setRefreshing(false);
                        initData();
                    }
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

                            case RxEvent.COOK_PAGE3_MODIFY:
                                if(rxEvent.getBundle().getInt("page") == 1) {
                                    //포트만들기 클릭시 이벤트
                                    if(rxEvent.getBundle().getInt("page3Modify") == 0) {
                                        CookDeleteState = true;
                                        AdapterCookPage2.CookDeleteBt = false;
                                        adapterCookPage2.notifyDataSetChanged();
                                    }
                                    //포트 만들기에서 상단 완료 버튼 클릭시 이벤트 ( 포트만들기 닫기 )
                                    else if(rxEvent.getBundle().getInt("page3Modify") == 9){
                                        //포트만들기 닫기 시 찜한 결과들 초기화
                                        ClearSelectedPort();
                                    }
                                    //편집시 상단 완료버튼 클릭시 이벤트
                                    else {
                                        CookDeleteState = false;
                                        AdapterCookPage2.CookDeleteBt = true;
                                        adapterCookPage2.notifyDataSetChanged();
                                    }
                                }
                                break;

                            case RxEvent.COOK_PAGE_BASKET:
                                if(rxEvent.getBundle().getInt("basket") == 0) {
                                    initData();
                                }
                                //전체 삭제 이벤트
                                else if(rxEvent.getBundle().getInt("basket") == 2){
                                    CookBasketDelete("", 0, false,false,"ALL");
                                }
                                break;

                            case RxEvent.REFRESH_COOKP2:
                                //포트만들기시 리사이클러뷰 바텀 마진
                                // 0 -> 마진 없앰 / 1 -> 마진 줌
                                if(rxEvent.getBundle().getInt("ViewMargin") == 0) {
                                    MakePortRefresh = false;
                                    fgcookpage2Binding.cookpage2BottomMarginView.setVisibility(View.GONE);
                                }else{
                                    fgcookpage2Binding.cookpage2BottomMarginView.setVisibility(View.VISIBLE);
                                    MakePortRefresh = true;
                                }
                                adapterCookPage2.notifyDataSetChanged();
                                break;
                            //포트 만들기 확인으로 미리보기 페이지 이동
                            case RxEvent.ZZIM_PORT_MAKE_OK:
                                try {
                                    Intent intent = new Intent(getActivity(), ActivityFinishMakePort.class);

                                    intent.putExtra("finishcash",rxEvent.getBundle().get("transcash").toString());
                                    intent.putExtra("finishcategory",rxEvent.getBundle().get("transcategory").toString());
                                    intent.putIntegerArrayListExtra("finishcode",rxEvent.getBundle().getIntegerArrayList("transcode"));
                                    intent.putStringArrayListExtra("finishtitle",rxEvent.getBundle().getStringArrayList("transtitle"));

                                    startActivityForResult(intent, 100);
                                }catch (Exception e){}
                                break;
                            case RxEvent.ZZIM_PORT_LOAD:
                                initData();
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

        //포트 삭제 이벤트
        adapterCookPage2.setCookDeleteClick(new AdapterCookPage2.CookDeleteClick() {
            @Override
            public void onClick(int position) {
                DeletePosition = position;
                customDialog = new DialogCookPage2Delete(getContext(), modelCookLists.get(position).getTitle(), leftListener, rightListener);
                customDialog.show();
            }
        });


        //포트 클릭 이벤트
        adapterCookPage2.setCookItemClick(new AdapterCookPage2.CookItemClick() {
            @Override
            public void onClick(int position) {

                SelectPosition = position;
                if (modelCookLists.get(SelectPosition).isOnenChart()) {
                    modelCookLists.get(SelectPosition).setOnenChart(false);
                    adapterCookPage2.notifyDataSetChanged();
                } else {
                    for (int a = 0; a < modelCookLists.size(); a++) {
                        modelCookLists.get(a).setOnenChart(false);
                    }

                    Call<ModelTab13mChartData> getTest2 = RetrofitClient.getInstance().getService().getRankPort(modelCookLists.get(position).getCode(),700);
                    getTest2.enqueue(new Callback<ModelTab13mChartData>() {
                        @Override
                        public void onResponse(Call<ModelTab13mChartData> call, Response<ModelTab13mChartData> response) {
                            if(response.code() == 200) {
                                entries.clear();
                                for(int a = 0 ; a < response.body().getContent().size() ; a++) {
                                    entries.add(new Entry(a, decimalScale2(String.valueOf(response.body().getContent().get(a).getExp()*100), 2, 2), response.body().getContent().get(a).getDate()));
                                }
                                modelCookLists.get(SelectPosition).setOnenChart(true);
                                adapterCookPage2.notifyDataSetChanged();
                                fgcookpage2Binding.cookpage2RecyclerView.smoothScrollToPosition(SelectPosition);
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


        adapterCookPage2.setCookP2MakeCheckClick(new AdapterCookPage2.CookP2MakeCheckClick() {
            @Override
            public void onClick(int position) {

                SelectPosition = position;
                //아이템 선택 off 시 -> on 변경
                if (modelCookLists.get(SelectPosition).getChecked() == 0) {
                    if(checkedItemNumber()) {
                        ItemCount++;
                        minCostTeans.putLong("mincost", modelCookLists.get(SelectPosition).getMincost());
                        minCostTeans.putBoolean("click", true);
                        minCostTeans.putInt("count",ItemCount);
                        minCostTeans.putString("code", modelCookLists.get(SelectPosition).getCode());
                        minCostTeans.putString("title", modelCookLists.get(SelectPosition).getTitle());

                        RxEventBus.getInstance().post(new RxEvent(RxEvent.ZZIM_PORT_SELECT_ITEM, minCostTeans));
                        savePosition.add(SelectPosition);

                        modelCookLists.get(SelectPosition).setChecked(1);
                        adapterCookPage2.notifyItemChanged(SelectPosition);

                    }else{
//                        toast.show();
                    }
                }
                //아이템 선택 on 시 -> off 변경
                else {
                    ItemCount--;
                    minCostTeans.putLong("mincost", modelCookLists.get(SelectPosition).getMincost());
                    minCostTeans.putBoolean("click", false);
                    minCostTeans.putInt("count",ItemCount);
                    minCostTeans.putString("code", modelCookLists.get(SelectPosition).getCode());
                    minCostTeans.putString("title", modelCookLists.get(SelectPosition).getTitle());

                    RxEventBus.getInstance().post(new RxEvent(RxEvent.ZZIM_PORT_SELECT_ITEM, minCostTeans));
                    savePosition.remove(savePosition.indexOf(SelectPosition));

                    modelCookLists.get(SelectPosition).setChecked(0);
                    adapterCookPage2.notifyItemChanged(SelectPosition);
                }
            }
        });

        //차트 1개월 클릭
        adapterCookPage2.setCookChartM1Click(new AdapterCookPage2.CookChartM1Click() {
            @Override
            public void onClick(int position) {
                ChartDur(modelCookLists.get(position).getCode(), 30);
            }
        });

        //차트 3개월 클릭
        adapterCookPage2.setCookChartM3Click(new AdapterCookPage2.CookChartM3Click() {
            @Override
            public void onclick(int position) {
                ChartDur(modelCookLists.get(position).getCode(), 90);
            }
        });

        //차트 6개월 클릭
        adapterCookPage2.setCookChartM6Click(new AdapterCookPage2.CookChartM6Click() {
            @Override
            public void onClick(int position) {
                ChartDur(modelCookLists.get(position).getCode(), 180);
            }
        });

        //차트 누적 클릭
        adapterCookPage2.setCookChartMaClick(new AdapterCookPage2.CookChartMaClick() {
            @Override
            public void onClick(int position) {
                ChartDur(modelCookLists.get(position).getCode(),700);
            }
        });

        //투자하기 클릭
        adapterCookPage2.setCookP2InvestClick(new AdapterCookPage2.CookP2InvestClick() {
            @Override
            public void onClick(int position) {
                Intent intent1 = new Intent(getActivity(), ActivityPayment.class);
                intent1.putExtra("detailcode", modelCookLists.get(position).getCode());
                intent1.putExtra("detailtitle", modelCookLists.get(position).getTitle());
                intent1.putExtra("mincost", modelCookLists.get(position).getMincost());
                startActivity(intent1);
            }
        });

        //더보기 클릭
        adapterCookPage2.setCookP2MoreViewClick(new AdapterCookPage2.CookP2MoreViewClick() {
            @Override
            public void onClick(int position) {
                Intent intent1 = new Intent(getActivity(), ActivityDetailPort.class);
                intent1.putExtra("detailcode", modelCookLists.get(position).getCode());
                intent1.putExtra("detailtitle", modelCookLists.get(position).getTitle());
                intent1.putExtra("ZzimPortPosition", position);
                startActivityForResult(intent1, 600);
            }
        });

        //공유 클릭
        adapterCookPage2.setCookP2ShareClick(new AdapterCookPage2.CookP2ShareClick() {
            @Override
            public void onClick(int position) {
            }
        });

    }//onViewCreated 끝

    //포트만들기 선택은 최대 5개까지
    boolean checkedItemNumber(){
        if(ItemCount < 5){
            return true;
        }else {
            return false;
        }
    }

    //포트만들기 닫기 시 찜한 결과들 초기화
    void ClearSelectedPort(){
        //5개 갯수 제한 초기화
        ItemCount = 0;
        savePosition.clear();
        for(int count = 0 ; count < modelCookLists.size() ; count ++) {
            modelCookLists.get(count).setChecked(0);
        }
        adapterCookPage2.notifyDataSetChanged();
    }

    //각 차트의 1개월 / 3개월 / 6개월 / 누적 버튼
    void ChartDur(String code, int dur) {

        Call<ModelTab13mChartData> getTest2 = RetrofitClient.getInstance().getService().getRankPort(code, dur);
        getTest2.enqueue(new Callback<ModelTab13mChartData>() {
            @Override
            public void onResponse(Call<ModelTab13mChartData> call, Response<ModelTab13mChartData> response) {
                if(response.code() == 200) {
                    entries.clear();
                    for(int a = 0 ; a < response.body().getContent().size() ; a++) {
                        entries.add(new Entry(a, decimalScale2(String.valueOf(response.body().getContent().get(a).getExp()*100), 2, 2), response.body().getContent().get(a).getDate()));
                    }
                    modelCookLists.get(SelectPosition).setOnenChart(true);
                    adapterCookPage2.notifyDataSetChanged();
                    fgcookpage2Binding.cookpage2RecyclerView.smoothScrollToPosition(SelectPosition);
                }
            }
            @Override
            public void onFailure(Call<ModelTab13mChartData> call, Throwable t) {
                Log.e("레트로핏 실패","값 : "+t.getMessage());
            }
        });

    }

    //개별 삭제 취소
    private View.OnClickListener leftListener = new View.OnClickListener() {
        public void onClick(View v) {
            customDialog.dismiss();
        }
    };

    //개별 삭제 확인
    private View.OnClickListener rightListener = new View.OnClickListener() {
        public void onClick(View v) {
            CookBasketDelete(modelCookLists.get(DeletePosition).getCode(), DeletePosition, false, modelCookLists.get(DeletePosition).isZim(), "del");
        }
    };

    //재료 삭제
    //del : 1 -> 단일 삭제 / 2 -> 전체 삭제
    void CookBasketDelete(String code, int position, boolean isDam, boolean isZim, String mode) {

        Select select = new Select(code,"",isDam, isZim, 0, "", 0, 1);

        Call<ModelZimData> getSelectPort = RetrofitClient.getInstance().getService().getSelectedPortDate("application/json", select, 2, mode);
        getSelectPort.enqueue(new Callback<ModelZimData>() {
            @Override
            public void onResponse(Call<ModelZimData> call, Response<ModelZimData> response) {
                if(response.code() == 200) {
                    if(response.body().getErrorcode() == 200){

                             if(mode.equals("del")) {
                                modelCookLists.remove(position);
                                adapterCookPage2.notifyItemRemoved(position);
                                adapterCookPage2.notifyItemRangeChanged(position, modelCookLists.size());
                                customDialog.dismiss();
                            }else{
                                modelCookLists.clear();
                                adapterCookPage2.notifyDataSetChanged();
                            }

                            Bundle bundle = new Bundle();
                            bundle.putInt("basket", 1);
                            RxEventBus.getInstance().post(new RxEvent(RxEvent.COOK_PAGE_BASKET, bundle));
                        }
                    }
                }
            @Override
            public void onFailure(Call<ModelZimData> call, Throwable t) {
                Toast.makeText(getActivity(),"네트워크가 불안정 합니다\n 다시 시도해 주세요.",Toast.LENGTH_SHORT).show();
            }
        });
    }

    void initData(){
        fgcookpage2Binding.fgCookpage2LoadingBar.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                DelayedRefresh = false;
                Call<ModelFgTab4ZimData> getData = RetrofitClient.getInstance().getService().getZimDamList();
                getData.enqueue(new Callback<ModelFgTab4ZimData>() {
                    @Override
                    public void onResponse(Call<ModelFgTab4ZimData> call, Response<ModelFgTab4ZimData> response) {
                        if(response.code() == 200) {
                            modelCookLists.clear();
                            if(response.body().getErrorcode() == 200) {
                                if(response.body().getTotalElements() != 0){
                                    String name;
                                    for(int a = 0 ; a < response.body().getTotalElements(); a++) {
                                        if(response.body().getContent().get(a).isDam()) {
                                            name = "@drawable/ic_" + response.body().getContent().get(a).getCode();
                                            modelCookLists.add(new ModelCookList(response.body().getContent().get(a).getName(), response.body().getContent().get(a).getCode(),
                                                    decimalScale(String.valueOf(response.body().getContent().get(a).getRate()*100), 2, 2), response.body().getContent().get(a).isZim(),
                                                    response.body().getContent().get(a).isDam(), name, false, response.body().getContent().get(a).getMinCost(), 0));
                                        }
                                    }
                                    adapterCookPage2.notifyDataSetChanged();
                                    fgcookpage2Binding.fgCookpage2LoadingBar.setVisibility(View.GONE);
                                }
                                else{
                                    modelCookLists.clear();
                                    adapterCookPage2.notifyDataSetChanged();
                                    fgcookpage2Binding.fgCookpage2LoadingBar.setVisibility(View.GONE);
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

    }// initData 끝

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //포트 저장시 내가 만든 포트로 데이터 전달
        if(requestCode == 100) {
            if(resultCode == 1) {
                Bundle bundle = new Bundle();
                bundle.putString("myportname", data.getStringExtra("portname"));
                bundle.putInt("myportcode",data.getIntExtra("portcode",0));
                bundle.putLong("myportcash",data.getLongExtra("portcash",0));
                bundle.putDouble("myportDrate", data.getDoubleExtra("portDrate", 0));
                RxEventBus.getInstance().post(new RxEvent(RxEvent.ZZIM_PORT_TRANS_PAGE, bundle));
            }
        }
    }


    public static double decimalScale(String decimal , int loc , int mode) {
        BigDecimal bd = new BigDecimal(decimal);
        BigDecimal result = null;
        if(mode == 1) {
            result = bd.setScale(loc, BigDecimal.ROUND_DOWN);       //내림
        }
        else if(mode == 2) {
            result = bd.setScale(loc, BigDecimal.ROUND_HALF_UP);   //반올림
        }
        else if(mode == 3) {
            result = bd.setScale(loc, BigDecimal.ROUND_UP);             //올림
        }
        return result.doubleValue();
    }

    public static float decimalScale2(String decimal , int loc , int mode) {
        BigDecimal bd = new BigDecimal(decimal);
        BigDecimal result = null;
        if(mode == 1) {
            result = bd.setScale(loc, BigDecimal.ROUND_DOWN);       //내림
        }
        else if(mode == 2) {
            result = bd.setScale(loc, BigDecimal.ROUND_HALF_UP);   //반올림
        }
        else if(mode == 3) {
            result = bd.setScale(loc, BigDecimal.ROUND_UP);             //올림
        }
        return result.floatValue();
    }
}

package quantec.com.moneypot.Activity.Main.Fragment.FgTab3.Fragment.Tab3_am;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import quantec.com.moneypot.Activity.DetailPort.ActivityDetailPort;
import quantec.com.moneypot.Activity.Intro.ErrorPojoClass;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab3.Fragment.Tab1_3m.Model.nModel.ModelTab13mChartData;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab3.Fragment.Tab1_3m.Model.nModel.ModelTab13mRank;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab3.Fragment.Tab3_am.Adapter.AdapterFgTab3am;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab3.Fragment.Tab3_am.Model.dModel.ModelTab3am;
import quantec.com.moneypot.Activity.Main.MainActivity;
import quantec.com.moneypot.Activity.Payment.ActivityPayment;
import quantec.com.moneypot.CustomView.EndlessScrollListener;
import quantec.com.moneypot.DataManager.DataManager;
import quantec.com.moneypot.Network.Retrofit.RetrofitClient;
import quantec.com.moneypot.R;
import quantec.com.moneypot.RxAndroid.RxEvent;
import quantec.com.moneypot.RxAndroid.RxEventBus;
import quantec.com.moneypot.Util.SharedPreferenceUtil.SharedPreferenceUtil;
import quantec.com.moneypot.databinding.FgFgtab3Fgtab3amBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fg_Tab3_am extends Fragment {

    FgFgtab3Fgtab3amBinding fgtab3amBinding;

    private AdapterFgTab3am tab3_amAdapter;
    private ArrayList<ModelTab3am> tab3_amItems;

    int countPage = 0;
    boolean NextPageLoadState = true;
    boolean LoadingState = true;

    List<ModelTab3am> list = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;

    String packName;

    List<Entry> entries;
    LineDataSet lineDataSet;
    LineData lineData;

    private MainActivity mainActivity;

    Toast toast;

    private boolean LoadDataState_a = false;

    public static boolean AnimState_a = true;

    public static boolean OpenChartAnim_a = false;

    public static boolean CheckDataAnim_a = false;

    public Fg_Tab3_am() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fgtab3amBinding = DataBindingUtil.inflate(inflater, R.layout.fg_fgtab3_fgtab3am, container, false);

        initializeViews();

        fgtab3amBinding.rankingPageTab3TaCover.setVisibility(View.GONE);
        fgtab3amBinding.rankingPageTab3TaCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        tab3_amItems = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(getActivity());
        fgtab3amBinding.rankingPageTab3TaRecyclerView.setLayoutManager(linearLayoutManager);

        packName = getActivity().getPackageName();

        entries = new ArrayList<>();
        lineDataSet = new LineDataSet(entries, null);
        lineData = new LineData(lineDataSet);
        tab3_amAdapter = new AdapterFgTab3am(tab3_amItems, getContext(),entries, lineDataSet, lineData);
        fgtab3amBinding.rankingPageTab3TaRecyclerView.setAdapter(tab3_amAdapter);

        loadData();

        return fgtab3amBinding.getRoot();
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

    private void loadData() {

        Call<ModelTab13mRank> getTest2 = RetrofitClient.getInstance().getService().getTest2(countPage,1,10);
        getTest2.enqueue(new Callback<ModelTab13mRank>() {
            @Override
            public void onResponse(Call<ModelTab13mRank> call, Response<ModelTab13mRank> response) {
                if(response.code() == 200) {
                    int resID;
                    String name = "@drawable/ic_rank_noname";
                    for(int a = 0 ; a < response.body().getContent().size() ; a++) {

                        resID = mainActivity.getResources().getIdentifier(name,"drawable",packName);

                        if(response.body().getContent().get(a).getSelect() != null) {
                            tab3_amItems.add(new ModelTab3am(response.body().getContent().get(a).getName(),
                                    response.body().getContent().get(a).getStCode(), decimalScale(String.valueOf(response.body().getContent().get(a).getRate()*100), 2, 2),1, resID, false, response.body().getContent().get(a).getMinCost()
                            ));
                        }else{
                            tab3_amItems.add(new ModelTab3am(response.body().getContent().get(a).getName(),
                                    response.body().getContent().get(a).getStCode(), decimalScale(String.valueOf(response.body().getContent().get(a).getRate()*100), 2, 2),0, resID, false, response.body().getContent().get(a).getMinCost()
                            ));
                        }

                    }
                    tab3_amAdapter.notifyDataSetChanged();
                    countPage++;
                    if(response.body().getPage().getTotalPages() == 1){
                        NextPageLoadState = false;
                    }
                }else{
                    Gson gson = new GsonBuilder().create();
                    ErrorPojoClass mError = new ErrorPojoClass();
                    try {
                        mError= gson.fromJson(response.errorBody().string(),ErrorPojoClass .class);
                        Log.e("스프링 에러", "에러메시지 값 : "+ mError.getDetails());
                        Log.e("스프링 에러", "에러메시지 값 : "+ mError.getMessage());
                        Log.e("스프링 에러", "에러메시지 값 : "+ mError.getTimestamp());
                        Log.e("스프링 에러", "에러메시지 값 : "+ mError.getErrorcode());
                    } catch (IOException e) {
                        // handle failure to read error
                    }
                }
            }
            @Override
            public void onFailure(Call<ModelTab13mRank> call, Throwable t) {
                Log.e("레트로핏 실패","값 : "+t.getMessage());
            }
        });
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fgtab3amBinding.rankingPageTab3TaRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy < 0) {
                    AnimState_a = true;
                }else{
                    AnimState_a = false;
                }
            }
        });

        //커스텀 토스트 메시지
        View toastView = View.inflate(getContext(), R.layout.dialog_toast_zzim_count_max, null);
        toast = new Toast(getContext());
        toast.setView(toastView);
        toast.setDuration(Toast.LENGTH_SHORT);

        //스크롤이 끝에 닿았을때 즉 마지막 아이템을 읽엇을때 다음 데이터 로드
        //데이터 끝에 닿으면 새로운 데이터 불러옴
        fgtab3amBinding.rankingPageTab3TaRecyclerView.addOnScrollListener(new EndlessScrollListener(linearLayoutManager) {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onLoadMore(int page, int totalItemsCount) {

                if(NextPageLoadState && LoadingState) {

                    LoadDataState_a = true;

                    LoadingState = false;
                    tab3_amItems.add(null);

                    Handler handler = new Handler();
                    final Runnable r = new Runnable() {
                        public void run() {
                            tab3_amAdapter.notifyItemInserted(tab3_amItems.size() - 1);
                            fgtab3amBinding.rankingPageTab3TaRecyclerView.scrollToPosition(tab3_amItems.size() - 1);
                        }
                    };
                    handler.post(r);

                    new AsyncTask<Void, Void, List<ModelTab3am>>() {
                        @Override
                        protected List<ModelTab3am> doInBackground(Void... voids) {
                            Call<ModelTab13mRank> getTest2 = RetrofitClient.getInstance().getService().getTest2(countPage,1,10);
                            getTest2.enqueue(new Callback<ModelTab13mRank>() {
                                @Override
                                public void onResponse(Call<ModelTab13mRank> call, Response<ModelTab13mRank> response) {
                                    if(response.code() == 200) {
                                        int resID;
                                        String name = "@drawable/ic_rank_noname";
                                        for(int a = 0 ; a < response.body().getContent().size() ; a++) {

                                            resID = mainActivity.getResources().getIdentifier(name,"drawable",packName);

                                            if(response.body().getContent().get(a).getSelect() != null) {
                                                list.add(new ModelTab3am(response.body().getContent().get(a).getName(),
                                                        response.body().getContent().get(a).getStCode(), decimalScale(String.valueOf(response.body().getContent().get(a).getRate()*100), 2, 2),1, resID, false, response.body().getContent().get(a).getMinCost()
                                                ));
                                            }else{
                                                list.add(new ModelTab3am(response.body().getContent().get(a).getName(),
                                                        response.body().getContent().get(a).getStCode(), decimalScale(String.valueOf(response.body().getContent().get(a).getRate()*100), 2, 2),0, resID, false, response.body().getContent().get(a).getMinCost()
                                                ));
                                            }
                                        }
                                        tab3_amAdapter.notifyDataSetChanged();
                                        countPage++;
                                        if(response.body().getPage().getTotalPages() == 1){
                                            NextPageLoadState = false;
                                        }
                                        else {
                                            LoadingState = true;
                                            countPage++;
                                        }
                                    }else{
                                        Gson gson = new GsonBuilder().create();
                                        ErrorPojoClass mError = new ErrorPojoClass();
                                        try {
                                            mError= gson.fromJson(response.errorBody().string(),ErrorPojoClass .class);
                                            Log.e("스프링 에러", "에러메시지 값 : "+ mError.getDetails());
                                            Log.e("스프링 에러", "에러메시지 값 : "+ mError.getMessage());
                                            Log.e("스프링 에러", "에러메시지 값 : "+ mError.getTimestamp());
                                            Log.e("스프링 에러", "에러메시지 값 : "+ mError.getErrorcode());
                                        } catch (IOException e) {
                                            // handle failure to read error
                                        }
                                    }
                                }
                                @Override
                                public void onFailure(Call<ModelTab13mRank> call, Throwable t) {
                                    Log.e("레트로핏 실패","값 : "+t.getMessage());
                                }
                            });

                            try {
                                Thread.sleep(1500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            return list;
                        }

                        @Override
                        protected void onPostExecute(List<ModelTab3am> items) {
                            super.onPostExecute(items);
                            tab3_amItems.remove(tab3_amItems.size()-1);
                            tab3_amAdapter.notifyItemRemoved(tab3_amItems.size());
                            int sizeInit = tab3_amItems.size();
                            tab3_amItems.addAll(items);
                            tab3_amAdapter.notifyItemRangeChanged(sizeInit, tab3_amItems.size());
                            list.clear();

                            LoadDataState_a = false;
                        }
                    }.execute();

                }else{
                    //더이상 불러올 아이템이 없을때
                }
            }
        });

        //아이템 새로 갱신
        fgtab3amBinding.rankingPageTab3TaWipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if(!LoadDataState_a) {

                    LoadingState = true;
                    tab3_amItems.clear();
                    NextPageLoadState = true;
                    countPage = 0;

                    fgtab3amBinding.rankingPageTab3TaCover.setVisibility(View.VISIBLE);
                    fgtab3amBinding.rankingPageTab3TaWipeRefresh.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            fgtab3amBinding.rankingPageTab3TaCover.setVisibility(View.GONE);
                            Snackbar.make(fgtab3amBinding.rankingPageTab3TaRecyclerView, "Refresh Success", Snackbar.LENGTH_SHORT).show();
                            fgtab3amBinding.rankingPageTab3TaWipeRefresh.setRefreshing(false);
                            loadData();
                        }
                    }, 1000);
                }else{
                    fgtab3amBinding.rankingPageTab3TaWipeRefresh.setRefreshing(false);
                }
            }
        });

        //포트 아이템 클릭시 차트 열림
        tab3_amAdapter.setT3SelectedItemClick(new AdapterFgTab3am.T3SelectedItemClick() {
            @Override
            public void onClick(int position) {

                if(tab3_amItems.get(position).isOnenChart()) {

                    OpenChartAnim_a = true;

                    tab3_amItems.get(position).setOnenChart(false);
                    tab3_amAdapter.notifyDataSetChanged();

                    OpenChartAnim_a = false;
                }else{
                    for(int a = 0 ; a < tab3_amItems.size() ; a++) {
                        tab3_amItems.get(a).setOnenChart(false);
                    }

                    Call<ModelTab13mChartData> getTest2 = RetrofitClient.getInstance().getService().getRankPort(tab3_amItems.get(position).getCode(),700);
                    getTest2.enqueue(new Callback<ModelTab13mChartData>() {
                        @Override
                        public void onResponse(Call<ModelTab13mChartData> call, Response<ModelTab13mChartData> response) {
                            if(response.code() == 200) {
                                entries.clear();
                                for(int a = 0 ; a < response.body().getContent().size() ; a++) {
                                    entries.add(new Entry(a, decimalScale2(String.valueOf(response.body().getContent().get(a).getExp()*100), 2, 2), response.body().getContent().get(a).getDate()));
                                }
                                OpenChartAnim_a = true;
                                tab3_amItems.get(position).setOnenChart(true);
                                tab3_amAdapter.notifyDataSetChanged();
                                fgtab3amBinding.rankingPageTab3TaRecyclerView.smoothScrollToPosition(position);
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

        // 포트 찜 체크 클릭
        tab3_amAdapter.setT3SelectedCheckedItem(new AdapterFgTab3am.T3SelectedCheckedItem() {
            @Override
            public void onClick(int position) {

                if(tab3_amItems.get(position).getCheck() == 1) {

//                    Call<ModelPortZzim> getData = RetrofitClient.getInstance().getService().getPortSaveData(tab3_amItems.get(position).getCode(), 1);
//                    getData.enqueue(new Callback<ModelPortZzim>() {
//                        @Override
//                        public void onResponse(Call<ModelPortZzim> call, Response<ModelPortZzim> response) {
//                            if(response.code() == 200) {
//
//                                CheckDataAnim_a = true;
//                                tab3_amItems.get(position).setCheck(false);
//                                tab3_amAdapter.notifyItemChanged(position);
//
//                                DataManager.get_INstance().setCheckTab1(true);
//
//                                Bundle bundle = new Bundle();
//                                bundle.putInt("rankcode", tab3_amItems.get(position).getCode());
//                                RxEventBus.getInstance().post(new RxEvent(RxEvent.RANK_PORT_CHECK_NO, bundle));
//
//                                SharedPreferenceUtil.getInstance(mainActivity).putIntZzimCount("PortZzimCount", response.body().getNum());
//                            }
//                        }
//                        @Override
//                        public void onFailure(Call<ModelPortZzim> call, Throwable t) {
//                            Toast.makeText(getActivity(),"네트워크가 불안정 합니다\n 다시 시도해 주세요.",Toast.LENGTH_SHORT).show();
//                        }
//                    });

                }else{

                    if(SharedPreferenceUtil.getInstance(mainActivity).getIntExtra("PortZzimCount") >= 25) {
                        //초과시 토스트
                        toast.show();
                        CheckDataAnim_a = false;
                    }else {
//                        Call<ModelPortZzim> getData = RetrofitClient.getInstance().getService().getPortSaveData(tab3_amItems.get(position).getCode(), 0);
//                        getData.enqueue(new Callback<ModelPortZzim>() {
//                            @Override
//                            public void onResponse(Call<ModelPortZzim> call, Response<ModelPortZzim> response) {
//                                if(response.code() == 200) {
//
//                                    CheckDataAnim_a = true;
//                                    tab3_amItems.get(position).setCheck(true);
//                                    tab3_amAdapter.notifyItemChanged(position);
//
//                                    DataManager.get_INstance().setCheckTab1(true);
//
//                                    Bundle bundle = new Bundle();
//                                    bundle.putInt("rankcode", tab3_amItems.get(position).getCode());
//                                    RxEventBus.getInstance().post(new RxEvent(RxEvent.RANK_PORT_CHECK_OK, bundle));
//
//                                    SharedPreferenceUtil.getInstance(mainActivity).putIntZzimCount("PortZzimCount", response.body().getNum());
//
//                                }
//                            }
//                            @Override
//                            public void onFailure(Call<ModelPortZzim> call, Throwable t) {
//                                Toast.makeText(getActivity(),"네트워크가 불안정 합니다\n 다시 시도해 주세요.",Toast.LENGTH_SHORT).show();
//                            }
//                        });
                    }
                }
            }
        });


        //차트 1개월 버튼 클릭
        tab3_amAdapter.setT3SelectedDur1(new AdapterFgTab3am.T3SelectedDur1() {
            @Override
            public void onClick(int position) {
                ChartDur(position, 180);
            }
        });
        //차트 3개월 버튼 클릭
        tab3_amAdapter.setT3SelectedDur3(new AdapterFgTab3am.T3SelectedDur3() {
            @Override
            public void onClick(int position) {
                ChartDur(position, 90);
            }
        });
        //차트 6개월 버튼 클릭
        tab3_amAdapter.setT3SelectedDur6(new AdapterFgTab3am.T3SelectedDur6() {
            @Override
            public void onClick(int position) {
                ChartDur(position, 180);
            }
        });
        //차트 누적 버튼 클릭
        tab3_amAdapter.setT3SelectedDura(new AdapterFgTab3am.T3SelectedDura() {
            @Override
            public void onClick(int position) {
                ChartDur(position, 700);
            }
        });


        //투자하기 클릭
        tab3_amAdapter.setT3SelectedInvest(new AdapterFgTab3am.T3SelectedInvest() {
            @Override
            public void onClick(int position) {

                Intent intent1 = new Intent(getActivity(), ActivityPayment.class);
                intent1.putExtra("detailcode",tab3_amItems.get(position).getCode());
                intent1.putExtra("detailtitle",tab3_amItems.get(position).getTitle());
                intent1.putExtra("mincost",tab3_amItems.get(position).getMincost());
                startActivity(intent1);
            }
        });

        //더보기 클릭
        tab3_amAdapter.setT3SelectedAdd(new AdapterFgTab3am.T3SelectedAdd() {
            @Override
            public void onClick(int position) {

                Intent intent1 = new Intent(getActivity(), ActivityDetailPort.class);
                intent1.putExtra("detailcode",tab3_amItems.get(position).getCode());
                intent1.putExtra("detailtitle",tab3_amItems.get(position).getTitle());
                startActivityForResult(intent1, 600);
            }
        });

        //공유 클릭
        tab3_amAdapter.setT3SelectedShare(new AdapterFgTab3am.T3SelectedShare() {
            @Override
            public void onClick(int position) {
                Toast.makeText(getContext(), "공유하기", Toast.LENGTH_SHORT).show();
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
                            case RxEvent.RANK_PORT_CHECK_OK:
                                int Gcode = rxEvent.getBundle().getInt("rankcode");
                                for(int a = 0 ; a < tab3_amItems.size() ; a++) {
//                                    if(tab3_amItems.get(a).getCode() == Gcode) {
//                                        tab3_amItems.get(a).setCheck(true);
//                                        tab3_amAdapter.notifyItemChanged(a);
//                                        break;
//                                    }
                                }
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Thread.sleep(300);
                                            CheckDataAnim_a = false;
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }).start();
                                break;

                            case RxEvent.RANK_PORT_CHECK_NO:
                                int Gcode2 = rxEvent.getBundle().getInt("rankcode");
                                for(int a = 0 ; a < tab3_amItems.size() ; a++) {
//                                    if(tab3_amItems.get(a).getCode() == Gcode2) {
//                                        tab3_amItems.get(a).setCheck(false);
//                                        tab3_amAdapter.notifyItemChanged(a);
//                                        break;
//                                    }
                                }
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Thread.sleep(300);
                                            CheckDataAnim_a = false;
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }).start();
                                break;

                            case RxEvent.ZZIM_PORT_DELETE_MODIFY:
//                                for (int count = 0; count < tab3_amItems.size(); count++) {
//                                    tab3_amItems.get(count).setCheck(false);
//                                }
                                tab3_amAdapter.notifyDataSetChanged();
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

    //각 차트의 1개월 ~ 누적 버튼
    void ChartDur(int position, int dur) {

        Call<ModelTab13mChartData> getTest2 = RetrofitClient.getInstance().getService().getRankPort(tab3_amItems.get(position).getCode(), dur);
        getTest2.enqueue(new Callback<ModelTab13mChartData>() {
            @Override
            public void onResponse(Call<ModelTab13mChartData> call, Response<ModelTab13mChartData> response) {
                if(response.code() == 200) {
                    entries.clear();
                    for(int a = 0 ; a < response.body().getContent().size() ; a++) {
                        entries.add(new Entry(a, decimalScale2(String.valueOf(response.body().getContent().get(a).getExp()*100), 2, 2), response.body().getContent().get(a).getDate()));
                    }
                    tab3_amItems.get(position).setOnenChart(true);
                    tab3_amAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<ModelTab13mChartData> call, Throwable t) {
                Log.e("레트로핏 실패","값 : "+t.getMessage());
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 600) {
            if(resultCode == 500) {
                CheckDataAnim_a = true;
                tab3_amAdapter.notifyDataSetChanged();

                DataManager.get_INstance().setCheckTab1(true);
            }else if(resultCode == 501) {

                CheckDataAnim_a = true;
                tab3_amAdapter.notifyDataSetChanged();

                DataManager.get_INstance().setCheckTab1(true);
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

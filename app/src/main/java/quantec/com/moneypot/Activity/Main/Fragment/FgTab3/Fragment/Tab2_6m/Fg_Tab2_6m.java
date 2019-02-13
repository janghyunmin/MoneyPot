package quantec.com.moneypot.Activity.Main.Fragment.FgTab3.Fragment.Tab2_6m;

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
import quantec.com.moneypot.Activity.Main.Fragment.FgTab3.Fragment.Tab1_3m.Model.nModel.ModelZimData;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab3.Fragment.Tab1_3m.Select;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab3.Fragment.Tab2_6m.Adapter.AdapterFgTab26m;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab3.Fragment.Tab2_6m.Model.dModel.ModelTab26m;
import quantec.com.moneypot.Activity.Main.MainActivity;
import quantec.com.moneypot.Activity.Payment.ActivityPayment;
import quantec.com.moneypot.CustomView.EndlessScrollListener;
import quantec.com.moneypot.DataManager.DataManager;
import quantec.com.moneypot.Network.Retrofit.RetrofitClient;
import quantec.com.moneypot.R;
import quantec.com.moneypot.RxAndroid.RxEvent;
import quantec.com.moneypot.RxAndroid.RxEventBus;
import quantec.com.moneypot.Util.SharedPreferenceUtil.SharedPreferenceUtil;
import quantec.com.moneypot.databinding.FgFgtab3Fgtab26mBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fg_Tab2_6m extends Fragment {

    FgFgtab3Fgtab26mBinding fgtab26mBinding;

    private AdapterFgTab26m tab2_6mAdapter;
    private ArrayList<ModelTab26m> tab2_6mItems;

    int countPage = 0;
    boolean NextPageLoadState = true;
    boolean LoadingState = true;

    List<ModelTab26m> list = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;

    String packName;

    List<Entry> entries;
    LineDataSet lineDataSet;
    LineData lineData;

    private MainActivity mainActivity;

    Toast toast;

    private boolean LoadDataState6 = false;

    public static boolean AnimState6 = true;

    public static boolean OpenChartAnim6 = false;

    public static boolean CheckDataAnim6 = false;


    public Fg_Tab2_6m() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fgtab26mBinding = DataBindingUtil.inflate(inflater, R.layout.fg_fgtab3_fgtab26m, container, false);

        initializeViews();

        fgtab26mBinding.rankingPageTab2T6Cover.setVisibility(View.GONE);
        fgtab26mBinding.rankingPageTab2T6Cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        tab2_6mItems = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(getActivity());
        fgtab26mBinding.rankingPageTab2T6RecyclerView.setLayoutManager(linearLayoutManager);

        packName = getActivity().getPackageName();

        entries = new ArrayList<>();
        lineDataSet = new LineDataSet(entries, null);
        lineData = new LineData(lineDataSet);
        tab2_6mAdapter = new AdapterFgTab26m(tab2_6mItems, getContext(),entries, lineDataSet, lineData);
        fgtab26mBinding.rankingPageTab2T6RecyclerView.setAdapter(tab2_6mAdapter);

        loadData();

        return fgtab26mBinding.getRoot();
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
        Call<ModelTab13mRank> getTest2 = RetrofitClient.getInstance().getService().getTest2(countPage,180,10);
        getTest2.enqueue(new Callback<ModelTab13mRank>() {
            @Override
            public void onResponse(Call<ModelTab13mRank> call, Response<ModelTab13mRank> response) {
                if(response.code() == 200) {
                    int resID;
                    String name = "@drawable/ic_rank_noname";
                    for(int a = 0 ; a < response.body().getContent().size() ; a++) {

                        resID = mainActivity.getResources().getIdentifier(name,"drawable",packName);

                        if(response.body().getContent().get(a).getSelect() != null) {
                            tab2_6mItems.add(new ModelTab26m(response.body().getContent().get(a).getName(),
                                    response.body().getContent().get(a).getStCode(), decimalScale(String.valueOf(response.body().getContent().get(a).getRate180()*100), 2, 2),response.body().getContent().get(a).getSelect().isZim(),
                                    response.body().getContent().get(a).getSelect().isDam(), resID, false, response.body().getContent().get(a).getMinCost()
                            ));
                        }else{
                            tab2_6mItems.add(new ModelTab26m(response.body().getContent().get(a).getName(),
                                    response.body().getContent().get(a).getStCode(), decimalScale(String.valueOf(response.body().getContent().get(a).getRate180()*100), 2, 2),false,
                                    false, resID, false, response.body().getContent().get(a).getMinCost()
                            ));
                        }
                    }
                    tab2_6mAdapter.notifyDataSetChanged();
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

        fgtab26mBinding.rankingPageTab2T6RecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy < 0) {
                    AnimState6 = true;
                }else{
                    AnimState6 = false;
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
        fgtab26mBinding.rankingPageTab2T6RecyclerView.addOnScrollListener(new EndlessScrollListener(linearLayoutManager) {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onLoadMore(int page, int totalItemsCount) {

                if(NextPageLoadState && LoadingState) {

                    LoadDataState6 = true;

                    LoadingState = false;
                    tab2_6mItems.add(null);

                    Handler handler = new Handler();
                    final Runnable r = new Runnable() {
                        public void run() {

                            tab2_6mAdapter.notifyItemInserted(tab2_6mItems.size() - 1);
                            fgtab26mBinding.rankingPageTab2T6RecyclerView.scrollToPosition(tab2_6mItems.size() - 1);
                        }
                    };
                    handler.post(r);

                    new AsyncTask<Void, Void, List<ModelTab26m>>() {
                        @Override
                        protected List<ModelTab26m> doInBackground(Void... voids) {

                            Call<ModelTab13mRank> getTest2 = RetrofitClient.getInstance().getService().getTest2(countPage,180,10);
                            getTest2.enqueue(new Callback<ModelTab13mRank>() {
                                @Override
                                public void onResponse(Call<ModelTab13mRank> call, Response<ModelTab13mRank> response) {
                                    if(response.code() == 200) {
                                        int resID;
                                        String name = "@drawable/ic_rank_noname";
                                        for(int a = 0 ; a < response.body().getContent().size() ; a++) {

                                            resID = mainActivity.getResources().getIdentifier(name,"drawable",packName);

                                            if(response.body().getContent().get(a).getSelect() != null) {
                                                list.add(new ModelTab26m(response.body().getContent().get(a).getName(),
                                                        response.body().getContent().get(a).getStCode(), decimalScale(String.valueOf(response.body().getContent().get(a).getRate180()*100), 2, 2),response.body().getContent().get(a).getSelect().isZim(),
                                                        response.body().getContent().get(a).getSelect().isDam(), resID, false, response.body().getContent().get(a).getMinCost()
                                                ));
                                            }else{
                                                list.add(new ModelTab26m(response.body().getContent().get(a).getName(),
                                                        response.body().getContent().get(a).getStCode(), decimalScale(String.valueOf(response.body().getContent().get(a).getRate180()*100), 2, 2),false,
                                                        false, resID, false, response.body().getContent().get(a).getMinCost()
                                                ));
                                            }
                                        }
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
                        protected void onPostExecute(List<ModelTab26m> items) {
                            super.onPostExecute(items);
                            tab2_6mItems.remove(tab2_6mItems.size()-1);
                            tab2_6mAdapter.notifyItemRemoved(tab2_6mItems.size());
                            int sizeInit = tab2_6mItems.size();
                            tab2_6mItems.addAll(items);
                            tab2_6mAdapter.notifyItemRangeChanged(sizeInit, tab2_6mItems.size());
                            list.clear();

                            LoadDataState6 = false;
                        }
                    }.execute();

                }else{
                    //더이상 불러올 아이템이 없을때
                }
            }
        });


        //아이템 새로 갱신
        fgtab26mBinding.rankingPageTab2T6WipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if(!LoadDataState6) {

                    LoadingState = true;
                    tab2_6mItems.clear();
                    NextPageLoadState = true;
                    countPage = 0;

                    tab2_6mAdapter.notifyDataSetChanged();
                    fgtab26mBinding.rankingPageTab2T6Cover.setVisibility(View.VISIBLE);
                    fgtab26mBinding.rankingPageTab2T6WipeRefresh.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            fgtab26mBinding.rankingPageTab2T6Cover.setVisibility(View.GONE);
                            Snackbar.make(fgtab26mBinding.rankingPageTab2T6RecyclerView, "Refresh Success", Snackbar.LENGTH_SHORT).show();
                            fgtab26mBinding.rankingPageTab2T6WipeRefresh.setRefreshing(false);
                            loadData();
                        }
                    }, 1000);
                }else{
                    fgtab26mBinding.rankingPageTab2T6WipeRefresh.setRefreshing(false);
                }
            }
        });


        //포트 아이템 클릭시 차트 열림
        tab2_6mAdapter.setT2SelectedItemClick(new AdapterFgTab26m.T2SelectedItemClick() {
            @Override
            public void onClick(int position) {

                if(tab2_6mItems.get(position).isOnenChart()) {

                    OpenChartAnim6 = true;

                    tab2_6mItems.get(position).setOnenChart(false);
                    tab2_6mAdapter.notifyDataSetChanged();

                    OpenChartAnim6 = false;
                }else{
                    for(int a = 0 ; a < tab2_6mItems.size() ; a++) {
                        try {
                            tab2_6mItems.get(a).setOnenChart(false);
                        }catch (Exception e){}
                    }

                    Call<ModelTab13mChartData> getTest2 = RetrofitClient.getInstance().getService().getRankPort(tab2_6mItems.get(position).getCode(),700);
                    getTest2.enqueue(new Callback<ModelTab13mChartData>() {
                        @Override
                        public void onResponse(Call<ModelTab13mChartData> call, Response<ModelTab13mChartData> response) {
                            if(response.code() == 200) {
                                entries.clear();
                                for(int a = 0 ; a < response.body().getContent().size() ; a++) {
                                    entries.add(new Entry(a, decimalScale2(String.valueOf(response.body().getContent().get(a).getExp()*100), 2, 2), response.body().getContent().get(a).getDate()));
                                }
                                OpenChartAnim6 = true;
                                tab2_6mItems.get(position).setOnenChart(true);
                                tab2_6mAdapter.notifyDataSetChanged();
                                fgtab26mBinding.rankingPageTab2T6RecyclerView.smoothScrollToPosition(position);
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
        tab2_6mAdapter.setT2SelectedCheckedItem(new AdapterFgTab26m.T2SelectedCheckedItem() {
            @Override
            public void onClick(int position) {

                if(tab2_6mItems.get(position).isZim()) {

                    Select select = new Select(tab2_6mItems.get(position).getCode(),"",tab2_6mItems.get(position).isDam(),false,0,"",0,0);

                    Call<ModelZimData> getSelectPort = RetrofitClient.getInstance().getService().getSelectedPortDate("application/json",select, 1,"del");
                    getSelectPort.enqueue(new Callback<ModelZimData>() {
                        @Override
                        public void onResponse(Call<ModelZimData> call, Response<ModelZimData> response) {
                            if(response.code() == 200) {
                                if(response.body().getErrorcode() == 200){

                                    CheckDataAnim6 = true;
                                    tab2_6mItems.get(position).setZim(false);
                                    tab2_6mAdapter.notifyItemChanged(position);

                                    DataManager.get_INstance().setCheckTab1(true);

                                    Bundle bundle = new Bundle();
                                    bundle.putString("rankcode", tab2_6mItems.get(position).getCode());
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


                }else {

                    if(SharedPreferenceUtil.getInstance(mainActivity).getIntExtra("PortZzimCount") >= 25) {
                        //초과시 토스트
                        toast.show();
                        CheckDataAnim6 = false;
                    }else{

                        Select select = new Select(tab2_6mItems.get(position).getCode(),"",tab2_6mItems.get(position).isDam(),true,0,"",0,1);

                        Call<ModelZimData> getSelectPort = RetrofitClient.getInstance().getService().getSelectedPortDate("application/json",select, 1,"add");
                        getSelectPort.enqueue(new Callback<ModelZimData>() {
                            @Override
                            public void onResponse(Call<ModelZimData> call, Response<ModelZimData> response) {
                                if(response.code() == 200) {
                                    if(response.body().getErrorcode() == 200){

                                        CheckDataAnim6 = true;
                                        tab2_6mItems.get(position).setZim(true);
                                        tab2_6mAdapter.notifyItemChanged(position);

                                        DataManager.get_INstance().setCheckTab1(true);

                                        Bundle bundle = new Bundle();
                                        bundle.putString("rankcode", tab2_6mItems.get(position).getCode());
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
            }
        });

        //차트 1개월 버튼 클릭
        tab2_6mAdapter.setT2SelectedDur1(new AdapterFgTab26m.T2SelectedDur1() {
            @Override
            public void onClick(int position) {
                ChartDur(position, 30);
            }
        });
        //차트 3개월 버튼 클릭
        tab2_6mAdapter.setT2SelectedDur3(new AdapterFgTab26m.T2SelectedDur3() {
            @Override
            public void onClick(int position) {
                ChartDur(position, 90);
            }
        });
        //차트 6개월 버튼 클릭
        tab2_6mAdapter.setT2SelectedDur6(new AdapterFgTab26m.T2SelectedDur6() {
            @Override
            public void onClick(int position) {
                ChartDur(position, 180);
            }
        });
        //차트 누적 버튼 클릭
        tab2_6mAdapter.setT2SelectedDura(new AdapterFgTab26m.T2SelectedDura() {
            @Override
            public void onClick(int position) {
                ChartDur(position, 700);
            }
        });

        //투자하기 클릭
        tab2_6mAdapter.setT2SelectedInvest(new AdapterFgTab26m.T2SelectedInvest() {
            @Override
            public void onClick(int position) {
                Intent intent1 = new Intent(getActivity(), ActivityPayment.class);
                intent1.putExtra("detailcode",tab2_6mItems.get(position).getCode());
                intent1.putExtra("detailtitle",tab2_6mItems.get(position).getTitle());
                intent1.putExtra("mincost",tab2_6mItems.get(position).getMincost());
                startActivity(intent1);

            }
        });

        //더보기 클릭
        tab2_6mAdapter.setT2SelectedAdd(new AdapterFgTab26m.T2SelectedAdd() {
            @Override
            public void onClick(int position) {
                Intent intent1 = new Intent(getActivity(), ActivityDetailPort.class);
                intent1.putExtra("detailcode",tab2_6mItems.get(position).getCode());
                intent1.putExtra("detailtitle",tab2_6mItems.get(position).getTitle());
                startActivityForResult(intent1, 600);
            }
        });

        //공유 클릭
        tab2_6mAdapter.setT2SelectedShare(new AdapterFgTab26m.T2SelectedShare() {
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
                                String Gcode = rxEvent.getBundle().getString("rankcode");
                                for(int a = 0 ; a < tab2_6mItems.size() ; a++) {
                                    if(tab2_6mItems.get(a).getCode().equals(Gcode)) {
                                        tab2_6mItems.get(a).setZim(true);
                                        tab2_6mAdapter.notifyItemChanged(a);
                                        break;
                                    }
                                }
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Thread.sleep(300);
                                            CheckDataAnim6 = false;
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }).start();
                                break;

                            case RxEvent.RANK_PORT_CHECK_NO:
                                String Gcode2 = rxEvent.getBundle().getString("rankcode");
                                for(int a = 0 ; a < tab2_6mItems.size() ; a++) {
                                    if(tab2_6mItems.get(a).getCode().equals(Gcode2)) {
                                        tab2_6mItems.get(a).setZim(false);
                                        tab2_6mAdapter.notifyItemChanged(a);
                                        break;
                                    }
                                }
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Thread.sleep(300);
                                            CheckDataAnim6 = false;
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }).start();
                                break;

                            case RxEvent.ZZIM_PORT_DELETE_MODIFY:
                                for (int count = 0; count < tab2_6mItems.size(); count++) {
                                    tab2_6mItems.get(count).setZim(false);
                                }
                                tab2_6mAdapter.notifyDataSetChanged();
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

    }//onViewCreate 끝

    //각 차트의 1개월 ~ 누적 버튼
    void ChartDur(int position, int dur) {

        Call<ModelTab13mChartData> getTest2 = RetrofitClient.getInstance().getService().getRankPort(tab2_6mItems.get(position).getCode(),dur);
        getTest2.enqueue(new Callback<ModelTab13mChartData>() {
            @Override
            public void onResponse(Call<ModelTab13mChartData> call, Response<ModelTab13mChartData> response) {
                if(response.code() == 200) {
                    entries.clear();
                    for(int a = 0 ; a < response.body().getContent().size() ; a++) {
                        entries.add(new Entry(a, decimalScale2(String.valueOf(response.body().getContent().get(a).getExp()*100), 2, 2), response.body().getContent().get(a).getDate()));
                    }
                    tab2_6mItems.get(position).setOnenChart(true);
                    tab2_6mAdapter.notifyDataSetChanged();
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
                CheckDataAnim6 = true;
                tab2_6mAdapter.notifyDataSetChanged();

                DataManager.get_INstance().setCheckTab1(true);
            }else if(resultCode == 501) {
                CheckDataAnim6 = true;
                tab2_6mAdapter.notifyDataSetChanged();

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

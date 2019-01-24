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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import quantec.com.moneypot.Activity.DetailPort.ActivityDetailPort;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab1.Model.nModel.ModelMiddleChartData;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab3.Fragment.Tab1_3m.Model.nModel.ModelTab13mRank;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab3.Fragment.Tab2_6m.Adapter.AdapterFgTab26m;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab3.Fragment.Tab2_6m.Model.dModel.ModelTab26m;
import quantec.com.moneypot.Activity.Main.MainActivity;
import quantec.com.moneypot.Activity.Payment.ActivityPayment;
import quantec.com.moneypot.Activity.SearchPort.SearchedPage.Fragment.AllPageTab.Model.nModel.ModelPortZzim;
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

    int countPage = 1;
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
        Call<ModelTab13mRank> getData = RetrofitClient.getInstance().getService().getRankData("6", countPage);
        getData.enqueue(new Callback<ModelTab13mRank>() {
            @Override
            public void onResponse(Call<ModelTab13mRank> call, Response<ModelTab13mRank> response) {
                if(response.code() == 200) {
                    boolean checkState;
                    int code, resID;
                    String name;

                    for(int a = 0 ; a < response.body().getProduct().size() ; a++) {
                        code = response.body().getProduct().get(a).getCode();
                        name = "@drawable/ic_" + code;
                        if(code >= 11 && code < 19 || code > 19) {
                            name = "@drawable/ic_rank_noname";
                        }
                        resID = mainActivity.getResources().getIdentifier(name,"drawable",packName);

                        tab2_6mItems.add(new ModelTab26m(response.body().getProduct().get(a).getName(),
                                response.body().getProduct().get(a).getCode(), String.format("%.2f",response.body().getProduct().get(a).getRate()),response.body().getProduct().get(a).getSelect(), resID, false, 50L
                        ));
                    }

                    countPage++;
                    tab2_6mAdapter.notifyDataSetChanged();

                    if(!(response.body().getNum() < response.body().getTotalNum() && response.body().getNum() >= 15)){
                        NextPageLoadState = false;
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
                            Call<ModelTab13mRank> getData = RetrofitClient.getInstance().getService().getRankData("6", countPage);
                            getData.enqueue(new Callback<ModelTab13mRank>() {
                                @Override
                                public void onResponse(Call<ModelTab13mRank> call, Response<ModelTab13mRank> response) {
                                    if (response.code() == 200) {
                                        boolean checkState;
                                        int code, resID;
                                        String name;

                                        for (int a = 0; a < response.body().getProduct().size(); a++) {
                                            code = response.body().getProduct().get(a).getCode();
                                            name = "@drawable/ic_" + code;
                                            if(code >= 11 && code < 19 || code > 19) {
                                                name = "@drawable/ic_rank_noname";
                                            }
                                            resID = mainActivity.getResources().getIdentifier(name,"drawable",packName);

                                            list.add(new ModelTab26m(response.body().getProduct().get(a).getName(),
                                                    response.body().getProduct().get(a).getCode(), String.format("%.2f",response.body().getProduct().get(a).getRate()), response.body().getProduct().get(a).getSelect(), resID,false, 50L
                                            ));
                                        }
                                        if(!(response.body().getNum() < response.body().getTotalNum() && response.body().getNum() >= 15)){
                                            NextPageLoadState = false;
                                        } else {
                                            LoadingState = true;
                                            countPage++;
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<ModelTab13mRank> call, Throwable t) {
                                    Log.e("레트로핏 실패", "값 : " + t.getMessage());
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
                    countPage = 1;

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
                    Call<ModelMiddleChartData> getchartItem = RetrofitClient.getInstance().getService().getChart(tab2_6mItems.get(position).getCode(),"a");
                    getchartItem.enqueue(new Callback<ModelMiddleChartData>() {
                        @Override
                        public void onResponse(Call<ModelMiddleChartData> call, Response<ModelMiddleChartData> response) {
                            if(response.code() == 200) {
                                entries.clear();
                                for(int a = 0 ; a < response.body().getElements().size() ; a++) {

                                    entries.add(new Entry(a, response.body().getElements().get(a).getRate(),response.body().getElements().get(a).getDate()));
                                }

                                OpenChartAnim6 = true;
                                tab2_6mItems.get(position).setOnenChart(true);
                                tab2_6mAdapter.notifyDataSetChanged();
                                fgtab26mBinding.rankingPageTab2T6RecyclerView.smoothScrollToPosition(position);
                            }
                        }
                        @Override
                        public void onFailure(Call<ModelMiddleChartData> call, Throwable t) {
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

                if(tab2_6mItems.get(position).getCheck() == 1) {


                    Call<ModelPortZzim> getData = RetrofitClient.getInstance().getService().getPortSaveData(tab2_6mItems.get(position).getCode(), 1);
                    getData.enqueue(new Callback<ModelPortZzim>() {
                        @Override
                        public void onResponse(Call<ModelPortZzim> call, Response<ModelPortZzim> response) {
                            if(response.code() == 200) {

                                CheckDataAnim6 = true;
                                tab2_6mItems.get(position).setCheck(0);
                                tab2_6mAdapter.notifyItemChanged(position);

                                DataManager.get_INstance().setCheckTab1(true);

                                Bundle bundle = new Bundle();
                                bundle.putInt("rankcode", tab2_6mItems.get(position).getCode());
                                RxEventBus.getInstance().post(new RxEvent(RxEvent.RANK_PORT_CHECK_NO, bundle));

                                SharedPreferenceUtil.getInstance(mainActivity).putIntZzimCount("PortZzimCount", response.body().getNum());
                            }
                        }
                        @Override
                        public void onFailure(Call<ModelPortZzim> call, Throwable t) {
                            Toast.makeText(getActivity(),"네트워크가 불안정 합니다\n 다시 시도해 주세요.",Toast.LENGTH_SHORT).show();
                        }
                    });

                }else {

                    if(SharedPreferenceUtil.getInstance(mainActivity).getIntExtra("PortZzimCount") >= 25) {
                        //초과시 토스트
                        toast.show();
                        CheckDataAnim6 = false;
                    }else{

                        Call<ModelPortZzim> getData = RetrofitClient.getInstance().getService().getPortSaveData(tab2_6mItems.get(position).getCode(), 0);
                        getData.enqueue(new Callback<ModelPortZzim>() {
                            @Override
                            public void onResponse(Call<ModelPortZzim> call, Response<ModelPortZzim> response) {
                                if(response.code() == 200) {

                                    CheckDataAnim6 = true;
                                    tab2_6mItems.get(position).setCheck(1);
                                    tab2_6mAdapter.notifyItemChanged(position);

                                    DataManager.get_INstance().setCheckTab1(true);

                                    Bundle bundle = new Bundle();
                                    bundle.putInt("rankcode", tab2_6mItems.get(position).getCode());
                                    RxEventBus.getInstance().post(new RxEvent(RxEvent.RANK_PORT_CHECK_OK, bundle));

                                    SharedPreferenceUtil.getInstance(mainActivity).putIntZzimCount("PortZzimCount", response.body().getNum());

                                }
                            }
                            @Override
                            public void onFailure(Call<ModelPortZzim> call, Throwable t) {
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
                ChartDur(position, "1");
            }
        });
        //차트 3개월 버튼 클릭
        tab2_6mAdapter.setT2SelectedDur3(new AdapterFgTab26m.T2SelectedDur3() {
            @Override
            public void onClick(int position) {
                ChartDur(position, "3");
            }
        });
        //차트 6개월 버튼 클릭
        tab2_6mAdapter.setT2SelectedDur6(new AdapterFgTab26m.T2SelectedDur6() {
            @Override
            public void onClick(int position) {
                ChartDur(position, "6");
            }
        });
        //차트 누적 버튼 클릭
        tab2_6mAdapter.setT2SelectedDura(new AdapterFgTab26m.T2SelectedDura() {
            @Override
            public void onClick(int position) {
                ChartDur(position, "a");
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
                                int Gcode = rxEvent.getBundle().getInt("rankcode");
                                for(int a = 0 ; a < tab2_6mItems.size() ; a++) {
                                    if(tab2_6mItems.get(a).getCode() == Gcode) {
                                        tab2_6mItems.get(a).setCheck(1);
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
                                int Gcode2 = rxEvent.getBundle().getInt("rankcode");
                                for(int a = 0 ; a < tab2_6mItems.size() ; a++) {
                                    if(tab2_6mItems.get(a).getCode() == Gcode2) {
                                        tab2_6mItems.get(a).setCheck(0);
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
                                    tab2_6mItems.get(count).setCheck(0);
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
    void ChartDur(int position, String dur) {

        Call<ModelMiddleChartData> getchartItem = RetrofitClient.getInstance().getService().getChart(tab2_6mItems.get(position).getCode(),dur);
        getchartItem.enqueue(new Callback<ModelMiddleChartData>() {
            @Override
            public void onResponse(Call<ModelMiddleChartData> call, Response<ModelMiddleChartData> response) {
                if(response.code() == 200) {
                    entries.clear();
                    for(int a = 0 ; a < response.body().getElements().size() ; a++) {
                        entries.add(new Entry(a, response.body().getElements().get(a).getRate(),response.body().getElements().get(a).getDate()));
                    }
                    tab2_6mItems.get(position).setOnenChart(true);
                    tab2_6mAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<ModelMiddleChartData> call, Throwable t) {
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

}

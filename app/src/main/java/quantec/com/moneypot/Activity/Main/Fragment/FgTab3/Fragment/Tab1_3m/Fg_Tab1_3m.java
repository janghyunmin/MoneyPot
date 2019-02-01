package quantec.com.moneypot.Activity.Main.Fragment.FgTab3.Fragment.Tab1_3m;

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
import com.google.gson.JsonArray;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import quantec.com.moneypot.Activity.DetailPort.ActivityDetailPort;
import quantec.com.moneypot.Activity.Intro.ErrorPojoClass;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab3.Fragment.Tab1_3m.Adapter.AdapterFgTab13m;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab3.Fragment.Tab1_3m.Model.dModel.ModelTab13m;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab3.Fragment.Tab1_3m.Model.nModel.ModelTab13mChartData;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab3.Fragment.Tab1_3m.Model.nModel.ModelTab13mRank;
import quantec.com.moneypot.Activity.Main.MainActivity;
import quantec.com.moneypot.Activity.Main.SelectedPortData;
import quantec.com.moneypot.Activity.Payment.ActivityPayment;
import quantec.com.moneypot.CustomView.EndlessScrollListener;
import quantec.com.moneypot.DataManager.DataManager;
import quantec.com.moneypot.Network.Retrofit.RetrofitClient;
import quantec.com.moneypot.R;
import quantec.com.moneypot.RxAndroid.RxEvent;
import quantec.com.moneypot.RxAndroid.RxEventBus;
import quantec.com.moneypot.Util.SharedPreferenceUtil.SharedPreferenceUtil;
import quantec.com.moneypot.databinding.FgFgtab3Fgtab13mBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fg_Tab1_3m extends Fragment {

    private AdapterFgTab13m tab1_3mAdapter;
    int countPage = 0;
    boolean NextPageLoadState = true;
    boolean LoadingState = true;

    List<ModelTab13m> list = new ArrayList<>();
    ArrayList<ModelTab13m> tab1_3mItems;

    LinearLayoutManager linearLayoutManager;

    String packName;

    List<Entry> entries;
    LineDataSet lineDataSet;
    LineData lineData;

    private MainActivity mainActivity;

    Toast toast;
    //다음 데이터 로딩시 바로 갱신 할경우에 갱신 막음
    private boolean LoadDataState = false;

    public static boolean AnimState = true;

    public static boolean OpenChartAnim = false;

    public static boolean CheckDataAnim = false;

    FgFgtab3Fgtab13mBinding fgtab13mBinding;

    public Fg_Tab1_3m() {
    }

    JSONArray personArray;
    JSONObject personInfo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fgtab13mBinding = DataBindingUtil.inflate(inflater, R.layout.fg_fgtab3_fgtab13m, container, false);

        initializeViews();

        tab1_3mItems = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(getActivity());
        fgtab13mBinding.rankingPageTab1T3RecyclerView.setLayoutManager(linearLayoutManager);

        packName = getActivity().getPackageName();

        entries = new ArrayList<>();
        entries.add(new Entry(0, 0));
        lineDataSet = new LineDataSet(entries, null);
        lineData = new LineData(lineDataSet);
        tab1_3mAdapter = new AdapterFgTab13m(tab1_3mItems, getContext(),entries, lineDataSet, lineData);

        fgtab13mBinding.rankingPageTab1T3RecyclerView.setAdapter(tab1_3mAdapter);
        fgtab13mBinding.rankingPageTab1T3Cover.setVisibility(View.GONE);
        fgtab13mBinding.rankingPageTab1T3Cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        loadData();

        return fgtab13mBinding.getRoot();
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

//       personArray = new JSONArray();
//       personInfo = new JSONObject();
//
//            personInfo.put("code", "MP0001");
//            personInfo.put("descript", "");
//            personInfo.put("isPot", 0);
//            personInfo.put("name", "");
//            personInfo.put("rate", 0);
//            personInfo.put("type", 1);
//        //Array에 입력
//        personArray.add(personInfo);
//
//        String jsonInfo = personArray.toJSONString();
//        System.out.print(jsonInfo);
//
//        Call<Object> getSelectPort = RetrofitClient.getInstance().getService().getSelectedPortDate("application/json",personArray);
//        getSelectPort.enqueue(new Callback<Object>() {
//            @Override
//            public void onResponse(Call<Object> call, Response<Object> response) {
//                if(response.code() == 200) {
//
//                    Log.e("받은 값 ","값 : "+ response.body().toString());
//                }else{
//
//                    Log.e("에러 값 ","값 : "+ response.errorBody().toString());
//                }
//            }
//            @Override
//            public void onFailure(Call<Object> call, Throwable t) {
//                Log.e("레트로핏 실패","값 : "+t.getMessage());
//            }
//        });


        Call<ModelTab13mRank> getTest2 = RetrofitClient.getInstance().getService().getTest2(countPage,90,10);
        getTest2.enqueue(new Callback<ModelTab13mRank>() {
            @Override
            public void onResponse(Call<ModelTab13mRank> call, Response<ModelTab13mRank> response) {
                if(response.code() == 200) {
                    int resID;
                    String name = "@drawable/ic_rank_noname";
                    for(int a = 0 ; a < response.body().getContent().size() ; a++) {

                        resID = mainActivity.getResources().getIdentifier(name,"drawable",packName);

                        if(response.body().getContent().get(a).getSelect() != null) {
                            tab1_3mItems.add(new ModelTab13m(response.body().getContent().get(a).getName(),
                                    response.body().getContent().get(a).getStCode(), decimalScale(String.valueOf(response.body().getContent().get(a).getRate90()*100), 2, 2),1, resID, false, response.body().getContent().get(a).getMinCost()
                            ));
                        }else{
                            tab1_3mItems.add(new ModelTab13m(response.body().getContent().get(a).getName(),
                                    response.body().getContent().get(a).getStCode(), decimalScale(String.valueOf(response.body().getContent().get(a).getRate90()*100), 2, 2),0, resID, false, response.body().getContent().get(a).getMinCost()
                            ));
                        }

                    }
                    tab1_3mAdapter.notifyDataSetChanged();
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

        fgtab13mBinding.rankingPageTab1T3RecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy < 0) {
                    AnimState = true;
                }else{
                    AnimState = false;
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
        fgtab13mBinding.rankingPageTab1T3RecyclerView.addOnScrollListener(new EndlessScrollListener(linearLayoutManager) {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onLoadMore(int page, int totalItemsCount) {

                if(NextPageLoadState && LoadingState) {

                    LoadDataState = true;
                    LoadingState = false;
                    tab1_3mItems.add(null);

                    Handler handler = new Handler();
                    final Runnable r = new Runnable() {
                        public void run() {
                            tab1_3mAdapter.notifyItemInserted(tab1_3mItems.size() - 1);
                            fgtab13mBinding.rankingPageTab1T3RecyclerView.scrollToPosition(tab1_3mItems.size() - 1);
                        }
                    };
                    handler.post(r);

                    new AsyncTask<Void, Void, List<ModelTab13m>>() {
                        @Override
                        protected List<ModelTab13m> doInBackground(Void... voids) {
                            Call<ModelTab13mRank> getTest2 = RetrofitClient.getInstance().getService().getTest2(countPage,90,10);
                            getTest2.enqueue(new Callback<ModelTab13mRank>() {
                                @Override
                                public void onResponse(Call<ModelTab13mRank> call, Response<ModelTab13mRank> response) {
                                    if(response.code() == 200) {
                                        int resID;
                                        String name = "@drawable/ic_rank_noname";
                                        for(int a = 0 ; a < response.body().getContent().size() ; a++) {

                                            resID = mainActivity.getResources().getIdentifier(name,"drawable",packName);
                                            if(response.body().getContent().get(a).getSelect() != null) {
                                                list.add(new ModelTab13m(response.body().getContent().get(a).getName(),
                                                        response.body().getContent().get(a).getStCode(), decimalScale(String.valueOf(response.body().getContent().get(a).getRate90()*100), 2, 2),1, resID, false, response.body().getContent().get(a).getMinCost()
                                                ));
                                            }else{
                                                list.add(new ModelTab13m(response.body().getContent().get(a).getName(),
                                                        response.body().getContent().get(a).getStCode(), decimalScale(String.valueOf(response.body().getContent().get(a).getRate90()*100), 2, 2),0, resID, false, response.body().getContent().get(a).getMinCost()
                                                ));
                                            }
                                        }
                                        tab1_3mAdapter.notifyDataSetChanged();
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
                                Thread.sleep(1400);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            return list;
                        }
                        @Override
                        protected void onPostExecute(List<ModelTab13m> items) {
                            super.onPostExecute(items);
                            try {
                                tab1_3mItems.remove(tab1_3mItems.size() - 1);
                            }catch (Exception e){}
                            tab1_3mAdapter.notifyItemRemoved(tab1_3mItems.size());

                            tab1_3mItems.addAll(items);
                            tab1_3mAdapter.notifyItemRangeChanged(tab1_3mItems.size(), tab1_3mItems.size());
                            list.clear();

                            LoadDataState = false;
                        }
                    }.execute();
                }else{
                    //더이상 불러올 아이템이 없을때
                }
            }
        });

        //아이템 새로 갱신
        fgtab13mBinding.rankingPageTab1T3WipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(!LoadDataState) {
                    LoadingState = true;
                    tab1_3mItems.clear();
                    NextPageLoadState = true;
                    countPage = 0;

                    tab1_3mAdapter.notifyDataSetChanged();
                    fgtab13mBinding.rankingPageTab1T3Cover.setVisibility(View.VISIBLE);
                    fgtab13mBinding.rankingPageTab1T3WipeRefresh.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            fgtab13mBinding.rankingPageTab1T3Cover.setVisibility(View.GONE);
                            Snackbar.make(fgtab13mBinding.rankingPageTab1T3RecyclerView, "Refresh Success", Snackbar.LENGTH_SHORT).show();
                            fgtab13mBinding.rankingPageTab1T3WipeRefresh.setRefreshing(false);
                            loadData();
                        }
                    }, 1000);
                }else{
                    fgtab13mBinding.rankingPageTab1T3WipeRefresh.setRefreshing(false);
                }
            }
        });

        //포트 아이템 클릭시 차트 열림
        tab1_3mAdapter.setSelectedItemClick(new AdapterFgTab13m.SelectedItemClick() {
            @Override
            public void onClick(int position) {

                if(tab1_3mItems.get(position).isOnenChart()) {

                    OpenChartAnim = true;

                    tab1_3mItems.get(position).setOnenChart(false);
                    tab1_3mAdapter.notifyItemChanged(position);

                    OpenChartAnim = false;

                }else{

                    for(int a = 0 ; a < tab1_3mItems.size() ; a++) {
                        //java.lang.NullPointerException: Attempt to invoke virtual method 'void com.quant.quantec.MainFragment.Tab3_ChildFragment.Tab1_3mItem.Tab1_3mItem.setOnenChart(boolean)' on a null object reference
                        try {
                            tab1_3mItems.get(a).setOnenChart(false);
                        }catch (Exception e){}
                    }

                    Call<ModelTab13mChartData> getTest2 = RetrofitClient.getInstance().getService().getRankPort(tab1_3mItems.get(position).getCode(),700);
                    getTest2.enqueue(new Callback<ModelTab13mChartData>() {
                        @Override
                        public void onResponse(Call<ModelTab13mChartData> call, Response<ModelTab13mChartData> response) {
                            if(response.code() == 200) {
                                entries.clear();
                                for(int a = 0 ; a < response.body().getContent().size() ; a++) {
                                    entries.add(new Entry(a, decimalScale2(String.valueOf(response.body().getContent().get(a).getExp()*100), 2, 2), response.body().getContent().get(a).getDate()));
                                }
                                OpenChartAnim = true;
                                tab1_3mItems.get(position).setOnenChart(true);
                                tab1_3mAdapter.notifyDataSetChanged();
                                fgtab13mBinding.rankingPageTab1T3RecyclerView.smoothScrollToPosition(position);
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
        tab1_3mAdapter.setSelectedCheckedItem(new AdapterFgTab13m.SelectedCheckedItem() {
            @Override
            public void onClick(int position) {

                if(tab1_3mItems.get(position).getCheck() == 1) {


           personArray = new JSONArray();
           personInfo = new JSONObject();

            personInfo.put("code", tab1_3mItems.get(position).getCode());
            personInfo.put("descript", "");
            personInfo.put("isPot", 0);
            personInfo.put("name", "");
            personInfo.put("rate", 0);
            personInfo.put("type", 1);
            //Array에 입력
            personArray.add(personInfo);

        String jsonInfo = personArray.toJSONString();
        System.out.print(jsonInfo);

        Call<Object> getSelectPort = RetrofitClient.getInstance().getService().getSelectedPortDate("application/json",personArray, );
        getSelectPort.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if(response.code() == 200) {

                    Log.e("받은 값 ","값 : "+ response.body().toString());
                }else{

                    Log.e("에러 값 ","값 : "+ response.errorBody().toString());
                }
            }
            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("레트로핏 실패","값 : "+t.getMessage());
            }
        });

//                    Call<ModelPortZzim> getData = RetrofitClient.getInstance().getService().getPortSaveData(tab1_3mItems.get(position).getCode(), 1);
//                    getData.enqueue(new Callback<ModelPortZzim>() {
//                        @Override
//                        public void onResponse(Call<ModelPortZzim> call, Response<ModelPortZzim> response) {
//                            if(response.code() == 200) {
//
//                                CheckDataAnim = true;
//
//                                tab1_3mItems.get(position).setCheck(0);
//                                tab1_3mAdapter.notifyItemChanged(position);
//
//                                DataManager.get_INstance().setCheckTab1(true);
//
//                                Bundle bundle = new Bundle();
//                                bundle.putInt("rankcode", tab1_3mItems.get(position).getCode());
//                                RxEventBus.getInstance().post(new RxEvent(RxEvent.RANK_PORT_CHECK_NO, bundle));
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
                        CheckDataAnim = false;
                    }else{
//                        Call<ModelPortZzim> getData = RetrofitClient.getInstance().getService().getPortSaveData(tab1_3mItems.get(position).getCode(), 0);
//                        getData.enqueue(new Callback<ModelPortZzim>() {
//                            @Override
//                            public void onResponse(Call<ModelPortZzim> call, Response<ModelPortZzim> response) {
//                                if(response.code() == 200) {
//
//                                    CheckDataAnim = true;
//
//                                    tab1_3mItems.get(position).setCheck(1);
//                                    tab1_3mAdapter.notifyItemChanged(position);
//
//                                    DataManager.get_INstance().setCheckTab1(true);
//
//                                    Bundle bundle = new Bundle();
//                                    bundle.putInt("rankcode", tab1_3mItems.get(position).getCode());
//                                    RxEventBus.getInstance().post(new RxEvent(RxEvent.RANK_PORT_CHECK_OK, bundle));
//                                    SharedPreferenceUtil.getInstance(mainActivity).putIntZzimCount("PortZzimCount", response.body().getNum());
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

        // 차트 1개월 버튼 클릭
        tab1_3mAdapter.setSelectedDur1(new AdapterFgTab13m.SelectedDur1() {
            @Override
            public void onClick(int position) {
                ChartDur(position, 30);
            }
        });
        // 차트 3개월 버튼 클릭
        tab1_3mAdapter.setSelectedDur3(new AdapterFgTab13m.SelectedDur3() {
            @Override
            public void onClick(int position) {
                ChartDur(position, 90);
            }
        });
        //차트 6개월 버튼 클릭
        tab1_3mAdapter.setSelectedDur6(new AdapterFgTab13m.SelectedDur6() {
            @Override
            public void onClick(int position) {
                ChartDur(position, 180);
            }
        });
        //차트 누적 버튼 클릭
        tab1_3mAdapter.setSelectedDura(new AdapterFgTab13m.SelectedDura() {
            @Override
            public void onClick(int position) {
                ChartDur(position, 700);
            }
        });

        //투자하기 클릭
        tab1_3mAdapter.setSelectedInvest(new AdapterFgTab13m.SelectedInvest() {
            @Override
            public void onClick(int position) {
                Intent intent1 = new Intent(getActivity(), ActivityPayment.class);
                intent1.putExtra("detailcode",tab1_3mItems.get(position).getCode());
                intent1.putExtra("detailtitle",tab1_3mItems.get(position).getTitle());
                intent1.putExtra("mincost",tab1_3mItems.get(position).getMincost());
                startActivity(intent1);
            }
        });

        //더보기 클릭
        tab1_3mAdapter.setSelectedAdd(new AdapterFgTab13m.SelectedAdd() {
            @Override
            public void onClick(int position) {
                Intent intent1 = new Intent(getActivity(), ActivityDetailPort.class);
                intent1.putExtra("detailcode",tab1_3mItems.get(position).getCode());
                intent1.putExtra("detailtitle",tab1_3mItems.get(position).getTitle());
                startActivityForResult(intent1, 600);
            }
        });

        //공유 클릭
        tab1_3mAdapter.setSelectedShare(new AdapterFgTab13m.SelectedShare() {
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
                                for(int a = 0 ; a < tab1_3mItems.size() ; a++) {
//                                    if(tab1_3mItems.get(a).getCode() == Gcode) {
//                                        tab1_3mItems.get(a).setCheck(1);
//                                        tab1_3mAdapter.notifyItemChanged(a);
//                                        break;
//                                    }
                                }
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Thread.sleep(300);
                                            CheckDataAnim = false;
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }).start();
                                break;
                            case RxEvent.RANK_PORT_CHECK_NO:
                                int Gcode2 = rxEvent.getBundle().getInt("rankcode");
                                for(int a = 0 ; a < tab1_3mItems.size() ; a++) {
//                                    if(tab1_3mItems.get(a).getCode() == Gcode2) {
//                                        tab1_3mItems.get(a).setCheck(0);
//                                        tab1_3mAdapter.notifyItemChanged(a);
//                                        break;
//                                    }
                                }
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Thread.sleep(300);
                                            CheckDataAnim = false;
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }).start();
                                break;
                            case RxEvent.ZZIM_PORT_DELETE_MODIFY:
                                for (int count = 0; count < tab1_3mItems.size(); count++) {
                                    tab1_3mItems.get(count).setCheck(0);
                                }
                                tab1_3mAdapter.notifyDataSetChanged();
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

    //각 차트의 1개월 ~ 누적 버튼
    void ChartDur(int position, int dur) {

        Call<ModelTab13mChartData> getTest2 = RetrofitClient.getInstance().getService().getRankPort(tab1_3mItems.get(position).getCode(),dur);
        getTest2.enqueue(new Callback<ModelTab13mChartData>() {
            @Override
            public void onResponse(Call<ModelTab13mChartData> call, Response<ModelTab13mChartData> response) {
                if(response.code() == 200) {
                    entries.clear();
                    for(int a = 0 ; a < response.body().getContent().size() ; a++) {
                        entries.add(new Entry(a, decimalScale2(String.valueOf(response.body().getContent().get(a).getExp()*100), 2, 2), response.body().getContent().get(a).getDate()));
                    }
                    OpenChartAnim = true;
                    tab1_3mItems.get(position).setOnenChart(true);
                    tab1_3mAdapter.notifyDataSetChanged();
                    fgtab13mBinding.rankingPageTab1T3RecyclerView.smoothScrollToPosition(position);
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
                CheckDataAnim = true;
                tab1_3mAdapter.notifyDataSetChanged();
                DataManager.get_INstance().setCheckTab1(true);
            }else if(resultCode == 501) {
                CheckDataAnim = true;
                tab1_3mAdapter.notifyDataSetChanged();
                DataManager.get_INstance().setCheckTab1(true);
            }
        }

    }
}

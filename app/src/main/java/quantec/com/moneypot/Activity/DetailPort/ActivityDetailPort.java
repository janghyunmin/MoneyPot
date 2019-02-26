package quantec.com.moneypot.Activity.DetailPort;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import quantec.com.moneypot.Activity.DetailPort.Adapter.AdapterDetailPort;
import quantec.com.moneypot.Activity.DetailPort.Model.dModel.ModelInvestItemData;
import quantec.com.moneypot.Activity.DetailPort.Model.nModel.ModelInvestItem;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab3.Fragment.Tab1_3m.Model.nModel.ModelTab13mChartData;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab3.Fragment.Tab1_3m.Model.nModel.ModelZimData;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab3.Fragment.Tab1_3m.Select;
import quantec.com.moneypot.Activity.Payment.ActivityPayment;
import quantec.com.moneypot.Activity.SearchPort.SearchedPage.Fragment.AllPageTab.Model.nModel.ModelPortZzim;
import quantec.com.moneypot.Network.Retrofit.RetrofitClient;
import quantec.com.moneypot.R;
import quantec.com.moneypot.RxAndroid.RxEvent;
import quantec.com.moneypot.RxAndroid.RxEventBus;
import quantec.com.moneypot.Util.DecimalScale.DecimalScale;
import quantec.com.moneypot.Util.SharedPreferenceUtil.SharedPreferenceUtil;
import quantec.com.moneypot.databinding.ActivityDetailPortBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityDetailPort extends AppCompatActivity {

    String PortCode;
    boolean getDam;

    RecyclerView.LayoutManager mLayoutManager;
    ArrayList<ModelInvestItemData> investItemData;
    ArrayList<ModelInvestItemData> investItemData5;

    public static boolean more = false;

    Long PassTotalCash;

    List<Entry> entries;
    LineDataSet lineDataSet;
    LineData lineData;
    //DetailPageAdapter 는 이전 버전이고 수정버전이 2임 ( 로딩 느려지는 부분 및 스크롤 느림 현상 해결 )
    AdapterDetailPort detailPageAdapter2;
    //메인 리사이클러뷰 아이템
    ArrayList<Integer> Detail;
    ArrayList<Double> Drate;
    ArrayList<String> infoItem;
    //메인 리사이클러뷰 갯수에 맞추기 위해서
    int CountSize;
    //스크롤 위치값 받아서 탑 이동 버튼 상태 변경
    int TopCount;
    //스크롤 위치값 받아서 탑 이동 버튼 상태 변경 ( 더보기 시 스크롤 범위 늘어나면 적용 )
    int TopCount2;
    //찜한포트에서 찜 체크 변화줄 포지션
    int ZzimDPosition;
    //내가 만든 포트일 경우 = false / 아닐 경우 = true
    // false -> 찜 불가능 / true -> 찜 가능
    boolean PortType = true;
    //포트 찜한 상태 체크
    boolean PortZzimState = false;
    //포트 찜 버튼 활성 여부 ( 1번이라도 터치시 연관 페이지 갱신 함)
    boolean PortZzimRefresh = false;
    //찜하기 초과시 토스트 알림
    Toast toast;

    int basketState;

    ActivityDetailPortBinding detailPageBinding;

    double rate, rate30, rate90, rate180;

    int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailPageBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail_port);
        detailPageBinding.setPortDetailPage(this);

        // 스테이터스 바 색상 변경 -> 화이트
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }else{
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.main_page_status_bar_color));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        detailPageBinding.portDetailPageLoading.setEnabled(false);

        Intent intent = getIntent();
        PortType = intent.getBooleanExtra("detailtype",true);
        ZzimDPosition = intent.getIntExtra("ZzimPortPosition", 0);
        PortCode = intent.getStringExtra("detailcode");

        infoItem = new ArrayList<>();
        Drate = new ArrayList<>();
        Detail = new ArrayList<>();

        for(int a = 0 ; a < 4 ; a++) {
            Detail.add(a);
        }
        CountSize = Detail.size()-1;

        detailPageBinding.portDetailPageRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(ActivityDetailPort.this);
        detailPageBinding.portDetailPageRecyclerView.setLayoutManager(mLayoutManager);
        investItemData = new ArrayList<>();
        investItemData5 = new ArrayList<>();

        entries = new ArrayList<>();
        entries.add(new Entry(0, 0));
        lineDataSet = new LineDataSet(entries, null);
        lineData = new LineData(lineDataSet);
        detailPageAdapter2 = new AdapterDetailPort(Detail, Drate, infoItem, investItemData, investItemData5,this, entries, lineDataSet, lineData);
        detailPageBinding.portDetailPageRecyclerView.setAdapter(detailPageAdapter2);

        //페이지 데이터 초기화
        PageInitDate();

        //뒤로 가기 버튼
        detailPageBinding.portDetailPageBackBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                more = false;

                //1번이라도 찜 활성화 됨
                if(PortZzimRefresh) {
                    //찜 선택 후 나가기
                    if (PortZzimState) {
                        Intent intent = getIntent();
                        intent.putExtra("ZzimPositionD", ZzimDPosition);
                        intent.putExtra("search_code_D", PortCode);
                        setResult(500, intent);
                        finish();
                    }
                    //찜 해제 후 나가기
                    else{
                        Intent intent = getIntent();
                        intent.putExtra("ZzimPositionD", ZzimDPosition);
                        intent.putExtra("search_code_D", PortCode);
                        setResult(501, intent);
                        finish();
                    }
                }
                //활성화 안됨
                else{
                    finish();
                }
            }
        });

        //스크롤시 최상단으로 이동하는 버튼 보임 ( 클릭시 최상단으로 이동 후 버튼 사라짐 ) -> 수정사항으로 더 보기시 스크롤 범위가 늘어나게 되면서 스크롤 범위 줄어들때와 늘어날때를 구분해서 구성함
        detailPageBinding.portDetailPageRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(more) {
                    TopCount2+=dy;
                    if(TopCount2 >= 70) {
                        detailPageBinding.portDetailPageInvestBtnTop.setVisibility(View.VISIBLE);
                    }else{
                        detailPageBinding.portDetailPageInvestBtnTop.setVisibility(View.GONE);
                    }
                }else{
                    TopCount+=dy;
                    if(TopCount >= 70) {
                        detailPageBinding.portDetailPageInvestBtnTop.setVisibility(View.VISIBLE);
                    }else{
                        detailPageBinding.portDetailPageInvestBtnTop.setVisibility(View.GONE);
                    }
                }
            }
        });

        //상단으로 이동 플로팅 버튼 클릭
        detailPageBinding.portDetailPageInvestBtnTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailPageBinding.portDetailPageRecyclerView.smoothScrollToPosition(0);
                detailPageBinding.portDetailPageInvestBtnTop.setVisibility(View.GONE);
            }
        });

        //종목 더보기 클릭
        detailPageAdapter2.setSelectedAddBT(new AdapterDetailPort.SelectedAddBT() {
            @Override
            public void onClick(int position) {
                if(more) {
                    more = false;
                    detailPageAdapter2.notifyDataSetChanged();
                }else {
                    TopCount2 = TopCount;
                    more = true;
                    detailPageAdapter2.notifyDataSetChanged();
                }
            }
        });

        //1개월 차트 버튼
        detailPageAdapter2.setDetailPageDur1(new AdapterDetailPort.DetailPageDur1() {
            @Override
            public void onClick(int position) {
                ChartDur(30, type);
            }
        });

        //3개월 차트 버튼
        detailPageAdapter2.setDetailPageDur3(new AdapterDetailPort.DetailPageDur3() {
            @Override
            public void onClick(int position) {
                ChartDur(90, type);
            }
        });

        //6개월 차트 버튼
        detailPageAdapter2.setDetailPageDur6(new AdapterDetailPort.DetailPageDur6() {
            @Override
            public void onClick(int postion) {
                ChartDur(180, type);
            }
        });

        //누적 차트 버튼
        detailPageAdapter2.setDetailPageDura(new AdapterDetailPort.DetailPageDura() {
            @Override
            public void onClick(int position) {
                ChartDur(700, type);
            }
        });

        //투자하기 버튼 클릭
        detailPageBinding.portDetailPageInvestBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ActivityDetailPort.this, ActivityPayment.class);
                intent1.putExtra("mincost",PassTotalCash);
                startActivity(intent1);
            }
        });

        //포트 찜하기
        detailPageBinding.portDetailPageCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PortZzimRefresh = true;
                //찜 헤제
                if(PortZzimState) {

                    Select select = new Select(PortCode, getDam,false);

                    Call<ModelZimData> getSelectPort = RetrofitClient.getInstance().getService().getSelectedPortDate("application/json",select, 1,"del");
                    getSelectPort.enqueue(new Callback<ModelZimData>() {
                        @Override
                        public void onResponse(Call<ModelZimData> call, Response<ModelZimData> response) {
                            if(response.code() == 200) {
                                if(response.body().getErrorcode() == 200){

                                    Bundle bundle = new Bundle();
                                    bundle.putString("rankcode", PortCode);
                                    RxEventBus.getInstance().post(new RxEvent(RxEvent.RANK_PORT_CHECK_NO, bundle));

                                    int zimCount = 0;
                                    for(int index = 0 ; index < response.body().getTotalElements() ; index++) {
                                        if(response.body().getContent().get(index).isZim()) {
                                            zimCount++;
                                        }
                                    }
                                    SharedPreferenceUtil.getInstance(ActivityDetailPort.this).putIntZzimCount("PortZzimCount", zimCount);
                                    PortZzimState = false;
                                    detailPageBinding.portDetailPageCheck.setImageResource(R.drawable.ic_star_gray_off);
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<ModelZimData> call, Throwable t) {
                            Toast.makeText(ActivityDetailPort.this,"네트워크가 불안정 합니다\n 다시 시도해 주세요.",Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    if(SharedPreferenceUtil.getInstance(ActivityDetailPort.this).getIntExtra("PortZzimCount") >= 25){
                        //초과시 토스트
                        toast.show();
                    }
                    //찜 선택
                    else{

                        Select select = new Select(PortCode, getDam,true);

                        Call<ModelZimData> getSelectPort = RetrofitClient.getInstance().getService().getSelectedPortDate("application/json",select, 1,"add");
                        getSelectPort.enqueue(new Callback<ModelZimData>() {
                            @Override
                            public void onResponse(Call<ModelZimData> call, Response<ModelZimData> response) {
                                if(response.code() == 200) {
                                    if(response.body().getErrorcode() == 200){

                                        Bundle bundle = new Bundle();
                                        bundle.putString("rankcode", PortCode);
                                        RxEventBus.getInstance().post(new RxEvent(RxEvent.RANK_PORT_CHECK_OK, bundle));

                                        int zimCount = 0;
                                        for(int index = 0 ; index < response.body().getTotalElements() ; index++) {
                                            if(response.body().getContent().get(index).isZim()) {
                                                zimCount++;
                                            }
                                        }
                                        SharedPreferenceUtil.getInstance(ActivityDetailPort.this).putIntZzimCount("PortZzimCount", zimCount);
                                        PortZzimState = true;
                                        detailPageBinding.portDetailPageCheck.setImageResource(R.drawable.start_on);
                                    }
                                }

                            }
                            @Override
                            public void onFailure(Call<ModelZimData> call, Throwable t) {
                                Toast.makeText(ActivityDetailPort.this,"네트워크가 불안정 합니다\n 다시 시도해 주세요.",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

            }
        });

        //커스텀 토스트 메시지
        View toastView = View.inflate(ActivityDetailPort.this, R.layout.dialog_toast_zzim_count_max, null);
        toast = new Toast(ActivityDetailPort.this);
        toast.setView(toastView);
        toast.setDuration(Toast.LENGTH_SHORT);

//        // 담기 클릭 이벤트
//        // basketState : 0 -> 안담긴 상품 / 1 -> 담긴 상품
//        detailPageBinding.portDetailPageTopTestBasketBT.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // 재료 담기
//                if(basketState == 0){
//                    basketClickEvent(PortCode, 0);
//                }
//                //재료 담기 해제
//                else{
//                    basketClickEvent(PortCode, 1);
//                }
//            }
//        });
    }

    // 포트 담기 이벤트
    void basketClickEvent(int code, int del){

//        Call<Object> getHotBasketData = RetrofitClient.getInstance().getService().getCookBasketData(code, del);
//        getHotBasketData.enqueue(new Callback<Object>() {
//            @Override
//            public void onResponse(Call<Object> call, Response<Object> response) {
//                if (response.code() == 200) {
//
//                    if (del == 0) {
//                        detailPageBinding.portDetailPageTopTestBasketBT.setTextColor(getResources().getColor(R.color.green_basket_color));
//                        basketState = 1;
//                    } else {
//                        detailPageBinding.portDetailPageTopTestBasketBT.setTextColor(getResources().getColor(R.color.dark_gray_color));
//                        basketState = 0;
//                    }
//
//                }
//            }
//            @Override
//            public void onFailure(Call<Object> call, Throwable t) {
//            }
//        });
    }

    //페이지 초기화
    void PageInitDate(){
        detailPageBinding.portDetailPageLoading.setVisibility(View.VISIBLE);

        Call<ModelInvestItem> getData = RetrofitClient.getInstance().getService().getDetailTest(PortCode);
        getData.enqueue(new Callback<ModelInvestItem>() {
            @Override
            public void onResponse(Call<ModelInvestItem> call, Response<ModelInvestItem> response) {
                if(response.code() == 200) {

                    infoItem.clear();
                    try {
                        infoItem.add(String.valueOf(response.body().getContent().getMinPrice()));
                        infoItem.add(response.body().getContent().getDescript());
                    }catch (Exception e){
                        infoItem.add("777");
                        infoItem.add("오류가 있는 포트입니다.");
                    }
                    detailPageAdapter2.notifyDataSetChanged();
                    try{
                        PassTotalCash = Long.parseLong(String.valueOf(response.body().getContent().getMinPrice()));
                    }catch (Exception e){
                        PassTotalCash = Long.parseLong("77777");
                    }
                    //찜 가능
                    if(PortType) {
                        if(response.body().getContent().getSelect() != null) {
                            if (response.body().getContent().getSelect().isZim()) {
                                detailPageBinding.portDetailPageCheck.setImageResource(R.drawable.start_on);
                                PortZzimState = true;
                            } else {
                                detailPageBinding.portDetailPageCheck.setImageResource(R.drawable.ic_star_gray_off);
                                PortZzimState = false;
                            }
                        }else{
                            detailPageBinding.portDetailPageCheck.setImageResource(R.drawable.ic_star_gray_off);
                            PortZzimState = false;
                        }
                    }
                    //찜 불가능
                    else{
                        detailPageBinding.portDetailPageCheck.setVisibility(View.GONE);
                        detailPageBinding.portDetailPageNoCheck.setVisibility(View.VISIBLE);
                        detailPageBinding.portDetailPageNoCheck.setImageResource(R.drawable.ic_star_gray_off);
                    }

                    detailPageBinding.portDetailPageTitleText.setText(response.body().getContent().getName());

                    if(response.body().getContent().getPackEls().size() >= 5) {
                        for(int a = 0 ; a < 5 ; a++) {
                            investItemData5.add(new ModelInvestItemData(response.body().getContent().getPackEls().get(a).getElName(),
                                    DecimalScale.decimalScale(String.valueOf(response.body().getContent().getPackEls().get(a).getRate() * 100), 2, 2),
                                    String.valueOf((int) response.body().getContent().getPackEls().get(a).getWeight())
                            ));
                        }
                        detailPageAdapter2.notifyDataSetChanged();
                    }else {
                        for (int a = 0; a < response.body().getContent().getPackEls().size(); a++) {
                            investItemData5.add(new ModelInvestItemData(response.body().getContent().getPackEls().get(a).getElName(),
                                    DecimalScale.decimalScale(String.valueOf(response.body().getContent().getPackEls().get(a).getRate() * 100), 2, 2),
                                    String.valueOf((int) response.body().getContent().getPackEls().get(a).getWeight())
                            ));
                        }
                        detailPageAdapter2.notifyDataSetChanged();
                    }

                    for(int a = 0 ; a < response.body().getContent().getPackEls().size() ; a++) {
                        CountSize++;
                        Detail.add(CountSize);
                        investItemData.add(new ModelInvestItemData(response.body().getContent().getPackEls().get(a).getElName(),
                                DecimalScale.decimalScale(String.valueOf(response.body().getContent().getPackEls().get(a).getRate()*100), 2, 2),
                                String.valueOf((int)response.body().getContent().getPackEls().get(a).getWeight())
                        ));
                        detailPageAdapter2.notifyDataSetChanged();
                    }

                    rate = DecimalScale.decimalScale(String.valueOf(response.body().getContent().getRate()*100), 2, 2);
                    rate30 = DecimalScale.decimalScale(String.valueOf(response.body().getContent().getRateOne()*100), 2, 2);
                    rate90 = DecimalScale.decimalScale(String.valueOf(response.body().getContent().getRateThr()*100), 2, 2);
                    rate180 = DecimalScale.decimalScale(String.valueOf(response.body().getContent().getRateSix()*100), 2, 2);

                    Drate.clear();
                    Drate.add(rate);

                    if(response.body().getContent().getSelect() != null){
                        getDam = response.body().getContent().getSelect().isDam();
                    }else{
                        getDam = false;
                    }

                    type = response.body().getContent().getType();

                    Call<ModelTab13mChartData> getTest2 = RetrofitClient.getInstance().getService().getRankPort(response.body().getContent().getType(), PortCode,700);
                    getTest2.enqueue(new Callback<ModelTab13mChartData>() {
                        @Override
                        public void onResponse(Call<ModelTab13mChartData> call, Response<ModelTab13mChartData> response) {
                            if(response.code() == 200) {
                                entries.clear();

                                for(int a = 0 ; a < response.body().getContent().size() ; a++) {
                                    entries.add(new Entry(a, DecimalScale.decimalScale2(String.valueOf(response.body().getContent().get(a).getExp()*100), 2, 2), response.body().getContent().get(a).getDate()));
                                }
                                detailPageAdapter2.notifyDataSetChanged();
                                detailPageBinding.portDetailPageLoading.setVisibility(View.GONE);
                            }
                        }
                        @Override
                        public void onFailure(Call<ModelTab13mChartData> call, Throwable t) {
                            Log.e("레트로핏 실패","값 : "+t.getMessage());
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<ModelInvestItem> call, Throwable t) {
                Log.e("레트로핏 실패","값 : "+t.getMessage());
            }
        });

    }

    void ChartDur(int dur, int type) {

        Call<ModelTab13mChartData> getTest2 = RetrofitClient.getInstance().getService().getRankPort(type, PortCode, dur);
        getTest2.enqueue(new Callback<ModelTab13mChartData>() {
            @Override
            public void onResponse(Call<ModelTab13mChartData> call, Response<ModelTab13mChartData> response) {
                if(response.code() == 200) {
                    Drate.clear();
                    if(dur == 30){
                        Drate.add(rate30);
                    }else if(dur == 90){
                        Drate.add(rate90);
                    }else if(dur == 180){
                        Drate.add(rate180);
                    }else {
                        Drate.add(rate);
                    }
                    entries.clear();

                    for(int a = 0 ; a < response.body().getContent().size() ; a++) {
                        entries.add(new Entry(a, DecimalScale.decimalScale2(String.valueOf(response.body().getContent().get(a).getExp()*100), 2, 2), response.body().getContent().get(a).getDate()));
                    }
                    detailPageAdapter2.notifyDataSetChanged();
                    detailPageBinding.portDetailPageLoading.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(Call<ModelTab13mChartData> call, Throwable t) {
                Log.e("레트로핏 실패","값 : "+t.getMessage());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        more = false;
    }

    @Override
    public void onBackPressed() {
        //1번이라도 찜 활성화 됨
        if(PortZzimRefresh) {
            //찜 선택 후 나가기
            if (PortZzimState) {
                Intent intent = getIntent();
                intent.putExtra("ZzimPositionD", ZzimDPosition);
                intent.putExtra("search_code_D", PortCode);
                setResult(500, intent);
                finish();
            }
            //찜 해제 후 나가기
            else{
                Intent intent = getIntent();
                intent.putExtra("ZzimPositionD", ZzimDPosition);
                intent.putExtra("search_code_D", PortCode);
                setResult(501, intent);
                finish();
            }
        }
        //활성화 안됨
        else{
            finish();
        }
    }
}

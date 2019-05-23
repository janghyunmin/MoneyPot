package quantec.com.moneypot.Activity.Main.Fragment.FgTab1;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import quantec.com.moneypot.Activity.DetailPort.ActivityDetailPort;
import quantec.com.moneypot.Activity.Intro.ErrorPojoClass;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab1.Adapter.AdapterTop10;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab1.Model.dModel.ModelMainPortTop10Item3;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab1.Model.dModel.ModelMainPortTop3Item10;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab1.Model.nModel.ModelTop10Item;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.Cookpage1.Filter;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab3.Fragment.Tab1_3m.Model.nModel.ModelTab13mChartData;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab3.Fragment.Tab1_3m.Model.nModel.ModelTab13mRank;
import quantec.com.moneypot.Activity.Main.MainActivity;
import quantec.com.moneypot.Activity.Myinfo.ActivityMyinfo;
import quantec.com.moneypot.Activity.SearchPort.ActivitySearchPort;
import quantec.com.moneypot.DataManager.DataManager;
import quantec.com.moneypot.Network.Retrofit.RetrofitClient;
import quantec.com.moneypot.R;
import quantec.com.moneypot.Util.DecimalScale.DecimalScale;
import quantec.com.moneypot.Util.TextTypeSpan.CustomTypefaceSpan;
import quantec.com.moneypot.databinding.FgTab1Binding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fg_tab1 extends Fragment {

    FgTab1Binding fgTab1Binding;
    private MainActivity mainActivity;
    public static boolean more_top10 = false;

    RecyclerView.LayoutManager layoutManager;
    ArrayList<ModelFitPotList> modelFitPotLists;
    AdapterFitPot adapterFitPot;

    boolean limitedLife = false;

    public Fg_tab1() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fgTab1Binding = DataBindingUtil.inflate(inflater, R.layout.fg_tab1, container, false);

        initializeViews();
        return fgTab1Binding.getRoot();
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

        fgTab1Binding.recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(mainActivity);
        fgTab1Binding.recyclerView.setLayoutManager(layoutManager);

        modelFitPotLists = new ArrayList<>();
        adapterFitPot = new AdapterFitPot(modelFitPotLists, mainActivity);

        fgTab1Binding.recyclerView.setAdapter(adapterFitPot);

        modelFitPotLists.add(0, new ModelFitPotList(false, "","", "", "", "", "", "", "", "", ""));
//        modelFitPotLists.add(0, new ModelFitPotList(true, "http://pizzaplanet.tistory.com/","", "", "", "", "", "", "", "", ""));

        modelFitPotLists.add(new ModelFitPotList(true, "","내집마련", "정말 더 없이 좋은 나만의 인생 라이프", "30", "1000000000",
                "장기플랜", "투자의 고수", "조심조심", "안정형을 추구하는 연금 투자 전략", "44.56%"));
        modelFitPotLists.add(new ModelFitPotList(false, "","", "", "", "", "", "", "", "", ""));

        adapterFitPot.setEmptyTotalPriceClick(new AdapterFitPot.EmptyTotalPriceClick() {
            @Override
            public void onClick(int position) {
//                Intent intent = new Intent()
                Toast.makeText(mainActivity, "계좌 개설로 이동됩니다.", Toast.LENGTH_SHORT).show();
            }
        });

        adapterFitPot.setEmptyLifeChallengeClick(new AdapterFitPot.EmptyLifeChallengeClick() {
            @Override
            public void onClick(int position) {
                modelFitPotLists.add(modelFitPotLists.size()-1, new ModelFitPotList(true, "","내집마련"+(modelFitPotLists.size()-1), "정말 더 없이 좋은 나만의 인생 라이프", "30", "1000000000",
                        "장기플랜", "투자의 고수", "조심조심", "안정형을 추구하는 연금 투자 전략", "44.56%"));
                visibleAddLife();
            }
        });

        adapterFitPot.setLifeChallengeClick(new AdapterFitPot.LifeChallengeClick() {
            @Override
            public void onClick(int position) {
                modelFitPotLists.remove(position);
                visibleAddLife();
            }
        });

    }//onViewCreate 끝


    private void visibleAddLife(){

        if(modelFitPotLists.size() == 12){

            modelFitPotLists.remove(modelFitPotLists.size()-1);
            adapterFitPot.notifyDataSetChanged();

            limitedLife = true;

        }else{

            if(limitedLife){

                limitedLife = false;

                modelFitPotLists.add(modelFitPotLists.size(), new ModelFitPotList(false, "","", "", "", "", "", "", "", "", ""));
                adapterFitPot.notifyDataSetChanged();

            }else{
                adapterFitPot.notifyDataSetChanged();
            }

        }

    }
}

















//public class Fg_tab1 extends Fragment {
//
//    RecyclerView.LayoutManager mLayoutManager;
//
//    FgTab1Binding fgTab1Binding;
//
//    private MainActivity mainActivity;
//
//    ArrayList<ModelMainPortTop10Item3> modelMainPortTop10Item3s;
//    ArrayList<ModelMainPortTop3Item10> modelMainPortTop3Item10s;
//    AdapterTop10 adapterTop10;
//
//    public static boolean more_top10 = false;
//    public static boolean middle_chart_open = false;
//    public static boolean bottom_chart_open = false;
//
//    List<Entry> middle_entries, bottom_entries;
//    LineDataSet middle_lineDataSet, bottom_lineDataSet;
//    LineData middle_lineData, bottom_lineData;
//
//    Animation image_anim, image_anim2;
//
//    float currentX, maxX;
//    Rect scrollBounds = new Rect();
//
//    boolean AnimationState1 = false;
//    boolean AnimationState2 = false;
//
//    Button CurrentMiddleView, CurrentBottomView;
//
//    public Fg_tab1() {}
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        fgTab1Binding = DataBindingUtil.inflate(inflater, R.layout.fg_tab1, container, false);
//
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);
//
//        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
//        initializeViews();
//
//        initData();
//
//        return fgTab1Binding.getRoot();
//    }
//
//    private void initializeViews(){
//        mainActivity = (MainActivity) getActivity();
//    }
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if(context instanceof MainActivity) {
//            mainActivity = (MainActivity) context;
//        }
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        // 내 정보 페이지로 이동
//        fgTab1Binding.firstPortPageTopImage2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), ActivityMyinfo.class);
//                startActivity(intent);
//            }
//        });
//
//        // 검색 페이지로 이동
//        fgTab1Binding.firstPortPageSearchBt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), ActivitySearchPort.class);
//                startActivity(intent);
//            }
//        });
//
//        image_anim = AnimationUtils.loadAnimation(getActivity(), R.anim.image_move_start_right);
//        image_anim2 = AnimationUtils.loadAnimation(getActivity(), R.anim.image_move_start_left);
//        // 팁 광고 삭제하기
//        fgTab1Binding.firstPortPageAdCloseBt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                fgTab1Binding.firstPortPageAdLayout.setVisibility(View.GONE);
//            }
//        });
//        // 탑10 더보기 및 접기 이벤트
//        fgTab1Binding.firstPortPageRecyclerViewAddViewBt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(more_top10) {
//                    fgTab1Binding.firstPortPageRecyclerViewAddViewBt.setText("더보기");
//                    more_top10 = false;
//                    scrollToView(fgTab1Binding.firstPortPageRecyclerView, fgTab1Binding.firstPortPageMainScroll, 0);
//                    adapterTop10.notifyDataSetChanged();
//                }else{
//                    fgTab1Binding.firstPortPageRecyclerViewAddViewBt.setText("접기");
//                    more_top10 = true;
//                    adapterTop10.notifyDataSetChanged();
//                    scrollToView2(fgTab1Binding.firstPortPageRecyclerView, fgTab1Binding.firstPortPageMainScroll, 0);
//                }
//            }
//        });
//        //탑10 클릭시 상세페이지 이동
//        adapterTop10.setTop10ItemClick(new AdapterTop10.Top10ItemClick() {
//            @Override
//            public void onClick(int position) {
//                if(more_top10) {
//                    Intent intent = new Intent(getActivity(), ActivityDetailPort.class);
//                    intent.putExtra("detailcode",modelMainPortTop3Item10s.get(position).getCode());
//                    startActivityForResult(intent, 600);
//                }else{
//                    Intent intent = new Intent(getActivity(), ActivityDetailPort.class);
//                    intent.putExtra("detailcode",modelMainPortTop10Item3s.get(position).getCode());
//                    startActivityForResult(intent, 600);
//                }
//            }
//        });
//        // 탑10 리사이클러뷰 스크롤 막기
//        fgTab1Binding.firstPortPageRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()){
//            @Override
//            public boolean canScrollVertically() { // 세로스크롤 막기
//                return false;
//            }
//            @Override
//            public boolean canScrollHorizontally() { //가로 스크롤막기
//                return false;
//            }
//        });
//
//        //스크롤시 해당 뷰가 보이는 시점에서 애니메이션 실행
//        fgTab1Binding.firstPortPageMainScroll.getViewTreeObserver().addOnScrollChangedListener(() -> {
//            fgTab1Binding.firstPortPageMainScroll.getHitRect(scrollBounds);
//
//            if(!AnimationState1) {
//                if (fgTab1Binding.firstPortPageMiddleSubtitle.getLocalVisibleRect(scrollBounds)) {
//                    AnimationState1 = true;
//                    new Handler().post(new Runnable() {
//                        @Override
//                        public void run() {
//                            fgTab1Binding.firstPortPageMiddleImage.setVisibility(View.VISIBLE);
//                            fgTab1Binding.firstPortPageMiddleImage.setAnimation(image_anim);
//                        }
//                    });
//                    // Any portion of the imageView, even a single pixel, is within the visible window
//                } else {
//                    // NONE of the imageView is within the visible window
//                }
//            }
//            if(!AnimationState2) {
//                if (fgTab1Binding.firstPortPageBottomLine.getLocalVisibleRect(scrollBounds)) {
//                    AnimationState2 = true;
//                    new Handler().post(new Runnable() {
//                        @Override
//                        public void run() {
//                            fgTab1Binding.firstPortPageBottomImage.setVisibility(View.VISIBLE);
//                            fgTab1Binding.firstPortPageBottomImage.setAnimation(image_anim2);
//                        }
//                    });
//                    // Any portion of the imageView, even a single pixel, is within the visible window
//                } else {
//                    // NONE of the imageView is within the visible window
//                }
//            }
//        });
//
//
//        //중간 위치 더보기 버튼
//        fgTab1Binding.firstPortPageMiddleChartOpen.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if(middle_chart_open){
//                    scrollToView2(fgTab1Binding.firstPortPageMiddleLayout, fgTab1Binding.firstPortPageMainScroll, 0);
//                    middle_chart_open= false;
//                    fgTab1Binding.firstPortPageMiddleChartLayout.setVisibility(View.GONE);
//                    fgTab1Binding.firstPortPageMiddleChartOpen.setText("더보기");
//                }else{
//                    //코드값과 기간을 기준으로 차트 데이터를 불러옴 ( 누적 데이터 )
//                    ChartDurMiddle(700);
//                    ChangedChartButton(fgTab1Binding.firstPortPageMiddleChartDuraBt, 0);
//                }
//            }
//        });
//
//        //바텀 더보기 버튼
//        fgTab1Binding.firstPortPageBottomChartOpen.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if(bottom_chart_open){
//                    scrollToView2(fgTab1Binding.firstPortPageBottomLayout, fgTab1Binding.firstPortPageMainScroll, 0);
//                    bottom_chart_open= false;
//                    fgTab1Binding.firstPortPageBottomChartLayout.setVisibility(View.GONE);
//                    fgTab1Binding.firstPortPageBottomChartOpen.setText("더보기");
//                }else{
//                    //코드값과 기간을 기준으로 차트 데이터를 불러옴 ( 누적 데이터 )
//                    ChartDurBottom(700);
//                    ChangedChartButton(fgTab1Binding.firstPortPageBottomChartDuraBt, 1);
//                }
//            }
//        });
//
//        // 바텀 위치 차트 1개월 클릭 이벤트
//        fgTab1Binding.firstPortPageBottomChartDur1Bt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ChartDurBottom(30);
//                ChangedChartButton(fgTab1Binding.firstPortPageBottomChartDur1Bt, 1);
//            }
//        });
//
//        // 바텀 위치 차트 3개월 클릭 이벤트
//        fgTab1Binding.firstPortPageBottomChartDur3Bt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                ChartDurBottom(90);
//                ChangedChartButton(fgTab1Binding.firstPortPageBottomChartDur3Bt, 1);
//            }
//        });
//
//        // 바텀 위치 차트 6개월 클릭 이벤트
//        fgTab1Binding.firstPortPageBottomChartDur6Bt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ChartDurBottom(180);
//                ChangedChartButton(fgTab1Binding.firstPortPageBottomChartDur6Bt, 1);
//            }
//        });
//
//        // 바텀 위치 차트 누적 클릭 이벤트
//        fgTab1Binding.firstPortPageBottomChartDuraBt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ChartDurBottom(700);
//                ChangedChartButton(fgTab1Binding.firstPortPageBottomChartDuraBt, 1);
//            }
//        });
//
//        // 중간 위치 차트 1개월 클릭 이벤트
//        fgTab1Binding.firstPortPageMiddleChartDur1Bt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ChartDurMiddle(30);
//                ChangedChartButton(fgTab1Binding.firstPortPageMiddleChartDur1Bt, 0);
//            }
//        });
//
//        // 중간 위치 차트 3개월 클릭 이벤트
//        fgTab1Binding.firstPortPageMiddleChartDur3Bt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ChartDurMiddle(90);
//                ChangedChartButton(fgTab1Binding.firstPortPageMiddleChartDur3Bt, 0);
//            }
//        });
//
//        // 중간 위치 차트 6개월 클릭 이벤트
//        fgTab1Binding.firstPortPageMiddleChartDur6Bt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ChartDurMiddle(180);
//                ChangedChartButton(fgTab1Binding.firstPortPageMiddleChartDur6Bt, 0);
//            }
//        });
//
//        // 중간 위치 차트 누적 클릭 이벤트
//        fgTab1Binding.firstPortPageMiddleChartDuraBt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ChartDurMiddle(700);
//                ChangedChartButton(fgTab1Binding.firstPortPageMiddleChartDuraBt, 0);
//            }
//        });
//
//
//    }//onViewCreate 끝
//
//    // 차트 위치 ChartPosition : 0 -> 미들 차트 / 1 -> 바텀 차트
//    void ChangedChartButton(Button currentBT, int ChartPosition){
//
//        if(ChartPosition == 0){
//            if(CurrentMiddleView != null) {
//                CurrentMiddleView.setBackgroundResource(R.drawable.unselected_round_button);
//                CurrentMiddleView.setTextColor(getResources().getColor(R.color.chart_dur_bt_color));
//            }
//            CurrentMiddleView = currentBT;
//            CurrentMiddleView.setBackgroundResource(R.drawable.round_button);
//            CurrentMiddleView.setTextColor(getResources().getColor(R.color.delete_pressed_text));
//        }else{
//            if(CurrentBottomView != null) {
//                CurrentBottomView.setBackgroundResource(R.drawable.unselected_round_button);
//                CurrentBottomView.setTextColor(getResources().getColor(R.color.chart_dur_bt_color));
//            }
//            CurrentBottomView = currentBT;
//            CurrentBottomView.setBackgroundResource(R.drawable.round_button);
//            CurrentBottomView.setTextColor(getResources().getColor(R.color.delete_pressed_text));
//        }
//    }
//
//    void initData(){
//
//        fgTab1Binding.firstPortPageRecyclerView.setHasFixedSize(true);
//        mLayoutManager = new LinearLayoutManager(getContext());
//        fgTab1Binding.firstPortPageRecyclerView.setLayoutManager(mLayoutManager);
//
//        modelMainPortTop10Item3s = new ArrayList<>();
//        modelMainPortTop3Item10s = new ArrayList<>();
//
//        adapterTop10 = new AdapterTop10(modelMainPortTop10Item3s, modelMainPortTop3Item10s, getContext());
//        fgTab1Binding.firstPortPageRecyclerView.setAdapter(adapterTop10);
//
//        Filter filter = new Filter();
//        Call<ModelTab13mRank> getData = RetrofitClient.getInstance(mainActivity).getService().getTop10("application/json", filter, "H", 0,1,10);
//        getData.enqueue(new Callback<ModelTab13mRank>() {
//            @Override
//            public void onResponse(Call<ModelTab13mRank> call, Response<ModelTab13mRank> response) {
//                if(response.code() == 200) {
//                    for(int a = 0 ; a < 10 ; a++) {
//                        if(a < 3) {
//                            modelMainPortTop10Item3s.add(new ModelMainPortTop10Item3(a+1, response.body().getContent().get(a).getName(),
//                                    DecimalScale.decimalScale(String.valueOf(response.body().getContent().get(a).getRate()*100), 2, 2), response.body().getContent().get(a).getCode()));
//                        }
//                        modelMainPortTop3Item10s.add(new ModelMainPortTop3Item10(a+1, response.body().getContent().get(a).getName(),
//                                DecimalScale.decimalScale(String.valueOf(response.body().getContent().get(a).getRate()*100), 2, 2),response.body().getContent().get(a).getCode()));
//                    }
//                    adapterTop10.notifyDataSetChanged();
//                }else if(response.code() == 406){
//
////                    Log.e("에러값", "값 "+response.body());
//                    Gson gson = new GsonBuilder().create();
//                    ErrorPojoClass mError = new ErrorPojoClass();
//                    try {
//                        mError= gson.fromJson(response.errorBody().string(),ErrorPojoClass .class);
//                        Log.e("스프링 에러", "에러메시지 값 : "+ mError.getDetails());
//                        Log.e("스프링 에러", "에러메시지 값 : "+ mError.getMessage());
//                        Log.e("스프링 에러", "에러메시지 값 : "+ mError.getTimestamp());
//                        Log.e("스프링 에러", "에러메시지 값 : "+ mError.getStatus());
//                    } catch (IOException e) {
//                        // handle failure to read error
//                    }
//                }
//            }
//            @Override
//            public void onFailure(Call<ModelTab13mRank> call, Throwable t) {
//                Log.e("레트로핏 실패","값 : "+t.getMessage());
//            }
//        });
//
//        middle_entries = new ArrayList<>();
//        bottom_entries = new ArrayList<>();
//
//        Typeface ab = Typeface.createFromAsset(getActivity().getAssets(), "fonts/noto_sans.ttf");
//        TypefaceSpan aab = new CustomTypefaceSpan("", ab);
//
//        String str1 = "머니포트의 자산관리 방법은 ";
//        SpannableStringBuilder sb = new SpannableStringBuilder(str1);
//        sb.setSpan(aab, 0, str1.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//
//        Typeface ab2 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/NotoSansKR-Bold-Hestia.otf");
//        TypefaceSpan aab2 = new CustomTypefaceSpan("", ab2);
//
//        String title1 = "100여 개 ";
//        SpannableStringBuilder sb2 = new SpannableStringBuilder(title1);
//        sb2.setSpan(aab2, 0, title1.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//
//
//        Typeface ab55 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/noto_sans.ttf");
//        TypefaceSpan aab55 = new CustomTypefaceSpan("", ab55);
//
//        String title55 = "입니다.";
//        SpannableStringBuilder sb55 = new SpannableStringBuilder(title55);
//        sb55.setSpan(aab55, 0, title55.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//
//
//        Typeface ab3 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/noto_sans.ttf");
//        TypefaceSpan aab3 = new CustomTypefaceSpan("", ab3);
//
//        String title2 = "\n*보통은 1+2개 정도 보유해요.";
//        SpannableStringBuilder sb3 = new SpannableStringBuilder(title2);
//        sb3.setSpan(aab3, 0, title2.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//
//        fgTab1Binding.firstPortPageAdText.setText(sb.append(sb2).append(sb55));
//        fgTab1Binding.firstPortPageAdText2.setText(sb3);
//
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(requestCode == 600) {
//            if(resultCode == 500) {
//                DataManager.get_INstance().setCheckTab1(true);
//            }else if(resultCode == 501) {
//                DataManager.get_INstance().setCheckTab1(true);
//            }
//        }
//    }
//
//    //더보기 시 스크롤
//    public static void scrollToView(View view, final ScrollView scrollView, int count) {
//        if (view != null && view != scrollView) {
//            count += view.getTop();
//            scrollToView((View) view.getParent(), scrollView, count);
//        } else if (scrollView != null) {
//            final int finalCount = count;
//            new Handler().post(new Runnable() {
//                @Override
//                public void run() {
//                    scrollView.smoothScrollTo(0, finalCount);
//                }
//            });
//        }
//    }
//
//    //접기 시 스크롤
//    public static void scrollToView2(View view, final ScrollView scrollView, int count) {
//        if (view != null && view != scrollView) {
//            count += view.getBottom()/2;
//            scrollToView((View) view.getParent(), scrollView, count);
//        } else if (scrollView != null) {
//            final int finalCount = count;
//            new Handler().post(new Runnable() {
//                @Override
//                public void run() {
//                    scrollView.smoothScrollTo(0, finalCount);
//                }
//            });
//        }
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        more_top10 = false;
//        AnimationState1 = false;
//        AnimationState2 = false;
//    }
//
//    void MiddleChart() {
//
//        middle_lineDataSet = new LineDataSet(middle_entries, null);
//        middle_lineDataSet.setLineWidth(1.5f);
//        middle_lineDataSet.setColor(Color.parseColor("#FFFF0000"));
//        middle_lineDataSet.setDrawHorizontalHighlightIndicator(false);
//        middle_lineDataSet.setDrawValues(false);
//        middle_lineDataSet.setHighlightEnabled(true);
//        middle_lineDataSet.setDrawHighlightIndicators(true);
//        middle_lineDataSet.setDrawHorizontalHighlightIndicator(false);
//        middle_lineDataSet.setHighLightColor(R.color.chart_limit_line_color);
//        middle_lineDataSet.setHighlightLineWidth(1f);
//        middle_lineDataSet.setDrawCircles(false);
//
//        middle_lineData = new LineData(middle_lineDataSet);
//        middle_lineData.setHighlightEnabled(true);
//
//        XAxis xAxis = fgTab1Binding.firstPortPageMiddleChart.getXAxis();
//        xAxis.setDrawLabels(false);
//        xAxis.setDrawGridLines(false);
//        xAxis.setDrawAxisLine(false);
//
//        YAxis yAxis = fgTab1Binding.firstPortPageMiddleChart.getAxisLeft();
//        yAxis.setDrawLabels(false);
//        yAxis.setDrawGridLines(false);
//        yAxis.setDrawAxisLine(false);
//        yAxis.setEnabled(false);
//
//        YAxis yAxis1 = fgTab1Binding.firstPortPageMiddleChart.getAxisRight();
//        yAxis1.setDrawLabels(false);
//        yAxis1.setDrawGridLines(false);
//        yAxis1.setDrawAxisLine(false);
//        yAxis1.setEnabled(false);
//
//        Legend legend = fgTab1Binding.firstPortPageMiddleChart.getLegend();
//        legend.setEnabled(false);
//        legend.setDrawInside(false);
//
//        fgTab1Binding.firstPortPageMiddleChart.setDescription(null);
//        fgTab1Binding.firstPortPageMiddleChart.setDrawGridBackground(false);
//        fgTab1Binding.firstPortPageMiddleChart.setData(middle_lineData);
//        fgTab1Binding.firstPortPageMiddleChart.setDoubleTapToZoomEnabled(false);
//        fgTab1Binding.firstPortPageMiddleChart.setDrawGridBackground(false);
//        fgTab1Binding.firstPortPageMiddleChart.animateY(600, Easing.EasingOption.EaseInCubic);
//        fgTab1Binding.firstPortPageMiddleChart.setPinchZoom(false);
//        fgTab1Binding.firstPortPageMiddleChart.setScaleEnabled(false);
//        fgTab1Binding.firstPortPageMiddleChart.invalidate();
//
//        maxX = fgTab1Binding.firstPortPageMiddleChart.getXRange();
//
//        CustomMarkerView marker = new CustomMarkerView(getContext(), R.layout.item_chart_marker);
//        marker.setChartView(fgTab1Binding.firstPortPageMiddleChart);
//        fgTab1Binding.firstPortPageMiddleChart.setMarker(marker);
//
//        scrollToView(fgTab1Binding.firstPortPageMiddleLayout, fgTab1Binding.firstPortPageMainScroll, 0);
//
//        middle_chart_open = true;
//        fgTab1Binding.firstPortPageMiddleChartLayout.setVisibility(View.VISIBLE);
//        fgTab1Binding.firstPortPageMiddleChartOpen.setText("접기");
//    }
//
//    void BottomChart(){
//
//        bottom_lineDataSet = new LineDataSet(bottom_entries, null);
//        bottom_lineDataSet.setLineWidth(1.5f);
//        bottom_lineDataSet.setColor(Color.parseColor("#FFFF0000"));
//        bottom_lineDataSet.setDrawHorizontalHighlightIndicator(false);
//        bottom_lineDataSet.setDrawValues(false);
//        bottom_lineDataSet.setHighlightEnabled(true);
//        bottom_lineDataSet.setDrawHighlightIndicators(true);
//        bottom_lineDataSet.setDrawHorizontalHighlightIndicator(false);
//        bottom_lineDataSet.setHighLightColor(R.color.chart_limit_line_color);
//        bottom_lineDataSet.setHighlightLineWidth(1f);
//        bottom_lineDataSet.setDrawCircles(false);
//
//        bottom_lineData = new LineData(bottom_lineDataSet);
//        bottom_lineData.setHighlightEnabled(true);
//
//        XAxis xAxis = fgTab1Binding.firstPortPageBottomChart.getXAxis();
//        xAxis.setDrawLabels(false);
//        xAxis.setDrawGridLines(false);
//        xAxis.setDrawAxisLine(false);
//
//        YAxis yAxis = fgTab1Binding.firstPortPageBottomChart.getAxisLeft();
//        yAxis.setDrawLabels(false);
//        yAxis.setDrawGridLines(false);
//        yAxis.setDrawAxisLine(false);
//        yAxis.setEnabled(false);
//
//        YAxis yAxis1 = fgTab1Binding.firstPortPageBottomChart.getAxisRight();
//        yAxis1.setDrawLabels(false);
//        yAxis1.setDrawGridLines(false);
//        yAxis1.setDrawAxisLine(false);
//        yAxis1.setEnabled(false);
//
//        Legend legend = fgTab1Binding.firstPortPageBottomChart.getLegend();
//        legend.setEnabled(false);
//        legend.setDrawInside(false);
//
//        fgTab1Binding.firstPortPageBottomChart.setDescription(null);
//        fgTab1Binding.firstPortPageBottomChart.setDrawGridBackground(false);
//        fgTab1Binding.firstPortPageBottomChart.setData(bottom_lineData);
//        fgTab1Binding.firstPortPageBottomChart.setDoubleTapToZoomEnabled(false);
//        fgTab1Binding.firstPortPageBottomChart.setDrawGridBackground(false);
//        fgTab1Binding.firstPortPageBottomChart.animateY(600, Easing.EasingOption.EaseInCubic);
//        fgTab1Binding.firstPortPageBottomChart.setPinchZoom(false);
//        fgTab1Binding.firstPortPageBottomChart.setScaleEnabled(false);
//        fgTab1Binding.firstPortPageBottomChart.invalidate();
//
//        maxX = fgTab1Binding.firstPortPageBottomChart.getXRange();
//
//        CustomMarkerView marker = new CustomMarkerView(getContext(), R.layout.item_chart_marker);
//        marker.setChartView(fgTab1Binding.firstPortPageBottomChart);
//        fgTab1Binding.firstPortPageBottomChart.setMarker(marker);
//
//        scrollToView(fgTab1Binding.firstPortPageBottomChartLayout, fgTab1Binding.firstPortPageMainScroll, 0);
//
//        bottom_chart_open = true;
//        fgTab1Binding.firstPortPageBottomChartLayout.setVisibility(View.VISIBLE);
//        fgTab1Binding.firstPortPageBottomChartOpen.setText("접기");
//    }
//
//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
//    }
//
//    public class CustomMarkerView extends MarkerView {
//        private TextView tvContent;
//        private TextView tvContent2;
//        public CustomMarkerView(Context context, int layoutResource){
//            super(context, layoutResource);
//            tvContent = findViewById(R.id.tvContent);
//            tvContent2 = findViewById(R.id.tvContent2);
//        }
//        @Override
//        public void refreshContent(Entry e, Highlight highlight) {
//            currentX = e.getX();
//            if (e instanceof CandleEntry) {
//                CandleEntry ce = (CandleEntry) e;
//                String num = String.format("%.2f",e.getY());
//                tvContent.setText(("" + ce.getData()).replace("-","."));
//                tvContent2.setText(num+"%");
//            } else {
//                String num = String.format("%.2f",e.getY());
//                tvContent.setText(("" + e.getData()).replace("-","."));
//                tvContent2.setText(num+"%");
//            }
//            super.refreshContent(e, highlight);
//        }
//        @Override
//        public MPPointF getOffset() {
//            if(maxX/3 < currentX) {
//                return new MPPointF(-(getWidth())-40, -getHeight()+80);
//            }else{
//                return new MPPointF((getWidth()/5), -getHeight()+80);
//            }
//        }
//        @Override
//        public void draw(Canvas canvas, float posX, float posY) {
//            Paint paint = new Paint();
//            paint.setColor(getContext().getResources().getColor(R.color.chart_point_color));
//            paint.setStrokeWidth(5f);
//            paint.setStyle(Paint.Style.STROKE);
//            canvas.drawCircle(posX, posY,18, paint);
//            super.draw(canvas, posX, posY);
//        }
//    }
//
//
//    void ChartDurMiddle(int dur) {
//        Call<ModelTab13mChartData> getTest2 = RetrofitClient.getInstance().getService().getRankPort(1, "MP0005",dur);
//        getTest2.enqueue(new Callback<ModelTab13mChartData>() {
//            @Override
//            public void onResponse(Call<ModelTab13mChartData> call, Response<ModelTab13mChartData> response) {
//                if(response.code() == 200) {
//                    middle_entries.clear();
//                    for(int a = 0 ; a < response.body().getContent().size() ; a++) {
//                        middle_entries.add(new Entry(a, DecimalScale.decimalScale2(String.valueOf(response.body().getContent().get(a).getExp()*100), 2, 2), response.body().getContent().get(a).getDate()));
//                    }
//                    if(response.body().getTotalElements() != 0){
//                        MiddleChart();
//                    }
//                }
//            }
//            @Override
//            public void onFailure(Call<ModelTab13mChartData> call, Throwable t) {
//                Log.e("레트로핏 실패","값 : "+t.getMessage());
//            }
//        });
//    }
//
//    void ChartDurBottom(int dur) {
//        Call<ModelTab13mChartData> getTest2 = RetrofitClient.getInstance().getService().getRankPort(1, "MP0006",dur);
//        getTest2.enqueue(new Callback<ModelTab13mChartData>() {
//            @Override
//            public void onResponse(Call<ModelTab13mChartData> call, Response<ModelTab13mChartData> response) {
//                if(response.code() == 200) {
//                    bottom_entries.clear();
//                    for(int a = 0 ; a < response.body().getContent().size() ; a++) {
//                        bottom_entries.add(new Entry(a, DecimalScale.decimalScale2(String.valueOf(response.body().getContent().get(a).getExp()*100), 2, 2), response.body().getContent().get(a).getDate()));
//                    }
//                    if(response.body().getTotalElements() != 0){
//                        BottomChart();
//                    }
//                }
//            }
//            @Override
//            public void onFailure(Call<ModelTab13mChartData> call, Throwable t) {
//                Log.e("레트로핏 실패","값 : "+t.getMessage());
//            }
//        });
//    }
//}

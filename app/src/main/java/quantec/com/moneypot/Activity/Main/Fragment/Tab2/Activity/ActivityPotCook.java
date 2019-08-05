package quantec.com.moneypot.Activity.Main.Fragment.Tab2.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import quantec.com.moneypot.Activity.Main.Fragment.Tab2.Adapter.AdapterPotCook;
import quantec.com.moneypot.Activity.Main.Fragment.Tab2.FgTab2;
import quantec.com.moneypot.Activity.Main.Fragment.Tab2.Fragment.FgPotCookDomestic;
import quantec.com.moneypot.Activity.Main.Fragment.Tab2.Fragment.FgPotCookBasic;
import quantec.com.moneypot.Activity.Main.Fragment.Tab2.Fragment.FgPotCookOverseas;
import quantec.com.moneypot.DataManager.ChartManager;
import quantec.com.moneypot.DataModel.dModel.ModelTransChartList;
import quantec.com.moneypot.DataModel.nModel.ModelChartData;
import quantec.com.moneypot.DataModel.nModel.ModelPrevPotContent;
import quantec.com.moneypot.DataModel.nModel.PotDto;
import quantec.com.moneypot.Dialog.DialogLoadingMakingPort;
import quantec.com.moneypot.DataModel.dModel.ModelPortList;
import quantec.com.moneypot.DataModel.dModel.TransChartList;
import quantec.com.moneypot.Network.Retrofit.RetrofitClient;
import quantec.com.moneypot.R;
import quantec.com.moneypot.RxAndroid.RxEvent;
import quantec.com.moneypot.RxAndroid.RxEventBus;
import quantec.com.moneypot.util.DecimalScale.DecimalScale;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityPotCook extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;

    int currentPage = 0;
    TextView defaultNum, defaultTitle, nextBt, prevBt, okBt, stableBt, middleBt, adventBt, priceText, infoText;
    LinearLayout next2Bt;

    ArrayList<String> stCode = new ArrayList<>();
    ArrayList<String> stName = new ArrayList<>();

    int portCount;
    ImageView downBt;
    ConstraintLayout portControlView;

    LinearLayout dimLayout;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ModelPortList> modelPortLists;
    AdapterPotCook adapterPotCook;

    boolean recyclerViewState = true;
    long investPrice = 200;

    Bundle changedPotList;
    boolean changedPot = false;
    int portCaterogy;

    DialogLoadingMakingPort loadingCustomMakingPort;

    ///임시포트페이지로 넘겨주는 데이터
    Bundle bundle;

    ArrayList<String> portName = new ArrayList<>();
    ArrayList<ModelTransChartList> modelTransChartLists;

    RelativeLayout makePotBt, makePotBt2;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pot_cook);

        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        //스테이터스 바 색상 변경 -> 화이트
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
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);


        makePotBt = findViewById(R.id.makePotBt);
        makePotBt2 = findViewById(R.id.makePotBt2);

        makePotBt.setVisibility(View.VISIBLE);
        makePotBt2.setVisibility(View.GONE);

        modelTransChartLists = new ArrayList<>();


        changedPotList = new Bundle();

        infoText = findViewById(R.id.infoText);

        stableBt = findViewById(R.id.stableBt);
        middleBt = findViewById(R.id.middleBt);
        adventBt = findViewById(R.id.adventBt);

        dimLayout = findViewById(R.id.dimLayout);

        downBt = findViewById(R.id.downBt);
        portControlView = findViewById(R.id.portControlView);

        defaultTitle = findViewById(R.id.defaultTitle);
        defaultNum = findViewById(R.id.defaultNum);

        nextBt = findViewById(R.id.nextBt);
        prevBt = findViewById(R.id.prevBt);
        okBt = findViewById(R.id.okBt);
        next2Bt = findViewById(R.id.next2Bt);

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        modelPortLists = new ArrayList<>();
        adapterPotCook = new AdapterPotCook(modelPortLists, this);

        recyclerView.setAdapter(adapterPotCook);

        dimLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(2);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if(positionOffset > 0 && positionOffset <= 0.9999){
                    makePotBt.setEnabled(false);
                    makePotBt2.setEnabled(false);
                }
                else{

                    if(position == 0){
                        makePotBt.setVisibility(View.VISIBLE);
                        makePotBt2.setVisibility(View.GONE);
                    }
                    else{
                        makePotBt.setVisibility(View.GONE);
                        makePotBt2.setVisibility(View.VISIBLE);
                    }
                }
            }
            @Override
            public void onPageSelected(int position) {
                currentPage = position;
                viewPager.setCurrentItem(currentPage);

               if(position == 0){
                   makePotBt.setVisibility(View.VISIBLE);
                   makePotBt2.setVisibility(View.GONE);
               }else{
                   makePotBt.setVisibility(View.GONE);
                   makePotBt2.setVisibility(View.VISIBLE);
               }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        nextBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Animation animation = AnimationUtils.loadAnimation(ActivityPotCook.this, R.anim.fade);
                animation.setFillAfter(false);
                animation.setFillEnabled(false);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        dimLayout.setVisibility(View.VISIBLE);
                        portControlView.setVisibility(View.VISIBLE);
                        nextBt.setEnabled(false);
                    }
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        nextBt.setVisibility(View.INVISIBLE);
                        prevBt.setEnabled(true);
                        okBt.setEnabled(true);
                        downBt.setVisibility(View.VISIBLE);
                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
                nextBt.startAnimation(animation);
            }
        });

        prevBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recyclerViewState = true;
                if(changedPot){
                    downBt.setImageDrawable(getResources().getDrawable(R.drawable.ic_up_gray_32_dp));
                    changedPot = false;
                    portCount = modelPortLists.size();
                    defaultNum.setText(String.valueOf(portCount)+"개");
                    changedPotList.putParcelableArrayList("potList", modelPortLists);
                    RxEventBus.getInstance().post(new RxEvent(RxEvent.REFRESH_POT_COOK, changedPotList));
                }

                nextBt.setVisibility(View.VISIBLE);
                TranslateAnimation animation3 = new TranslateAnimation(
                        Animation.ABSOLUTE, 700,
                        Animation.ABSOLUTE, 0,
                        Animation.ABSOLUTE,0,
                        Animation.ABSOLUTE,0);
                animation3.setDuration(700);
                animation3.setFillAfter(false);
                animation3.setFillEnabled(false);
                animation3.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        recyclerView.setVisibility(View.GONE);
                        dimLayout.setVisibility(View.GONE);
                        portControlView.setVisibility(View.GONE);
                        downBt.setVisibility(View.INVISIBLE);
                    }
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        prevBt.setEnabled(false);
                        okBt.setEnabled(false);
                        nextBt.setEnabled(true);
                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) {}
                });
                nextBt.startAnimation(animation3);
            }
        });

        //포트 조합 완료
        okBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadingCustomMakingPort = new DialogLoadingMakingPort(ActivityPotCook.this, "주식투자는 단기적 수익을 쫒기 보다는\n장기적으로 보아야 성공할 수 있습니다.");
                loadingCustomMakingPort.show();

                    portName.clear();
                    bundle = new Bundle();
                    PotDto potDto = new PotDto(stCode, portCaterogy, 1000L);

                    Call<ModelPrevPotContent> getchartItem = RetrofitClient.getInstance().getService().getCookPotCode("application/json", potDto);
                    getchartItem.enqueue(new Callback<ModelPrevPotContent>() {
                        @Override
                        public void onResponse(Call<ModelPrevPotContent> call, Response<ModelPrevPotContent> response) {

                            if(response.code() == 200) {

                                for(int index = 0 ; index < response.body().getContent().getPotEls().size() ; index++){
                                    portName.add(response.body().getContent().getPotEls().get(index).getElName());
                                }
                                bundle.putString("transptcode", response.body().getContent().getCode());
                                bundle.putStringArrayList("transtitle",portName);
                                bundle.putString("transcash", String.valueOf(response.body().getContent().getMinPrice()));
                                bundle.putString("transcategory", "중립형");
                                getMyChartData(response.body().getContent().getCode());

                            }
                        }
                        @Override
                        public void onFailure(Call<ModelPrevPotContent> call, Throwable t) {
                            loadingCustomMakingPort.dismiss();
                            Toast.makeText(ActivityPotCook.this, "서버가 불안정합니다\n잠시 후 다시 시도해 주세요.",Toast.LENGTH_SHORT).show();
                            Log.e("레트로핏 실패","값 : "+t.getMessage());
                        }
                    });
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

                            case RxEvent.ZZIM_PORT_SELECT_ITEM:

                                if(rxEvent.getBundle().getBoolean("click")) {
                                    portCount = rxEvent.getBundle().getInt("count");

                                    stCode.add(rxEvent.getBundle().getString("code"));
                                    stName.add(rxEvent.getBundle().getString("title"));

                                    modelPortLists.add(new ModelPortList(rxEvent.getBundle().getString("code"),rxEvent.getBundle().getString("title"),"배당"));

                                    if(portCount == 0){

                                        nextBtState(false);

                                        defaultNum.setText("선택 없음");
                                        defaultTitle.setText("2개 이상을 선택해주세요.");
                                        defaultTitle.setTextColor(getResources().getColor(R.color.text_gray_color));
                                        defaultNum.setTextColor(getResources().getColor(R.color.text_black_color));
                                    }
                                    else if(portCount < 2 && portCount > 0) {

                                        nextBtState(false);

                                        defaultNum.setText(String.valueOf(portCount)+"개");
                                        defaultTitle.setText("2개 이상을 선택해주세요.");
                                        defaultTitle.setTextColor(getResources().getColor(R.color.text_gray_color));
                                        defaultNum.setTextColor(getResources().getColor(R.color.text_black_color));

                                    }else{

                                        nextBtState(true);

                                        defaultNum.setText(String.valueOf(portCount)+"개");
                                        defaultTitle.setText("선택하신 포트의 최소 투자 금액은 200만입니다.");
                                        defaultNum.setTextColor(getResources().getColor(R.color.make_port_blue_color));
                                        defaultTitle.setTextColor(getResources().getColor(R.color.text_gray_color));
                                    }
                                }
                                else{

                                    for(int h = 0 ; h < modelPortLists.size() ; h++){
                                        if(modelPortLists.get(h).getStname().equals(rxEvent.getBundle().getString("title"))){
                                            modelPortLists.remove(h);
                                        }
                                    }


                                    portCount = rxEvent.getBundle().getInt("count");

                                    stCode.remove(stCode.indexOf(rxEvent.getBundle().getString("code")));
                                    stName.remove(stName.indexOf(rxEvent.getBundle().getString("title")));

                                    if(portCount == 0){

                                        nextBtState(false);

                                        defaultNum.setText("선택 없음");
                                        defaultTitle.setText("2개 이상을 선택해주세요.");
                                        defaultTitle.setTextColor(getResources().getColor(R.color.text_gray_color));
                                        defaultNum.setTextColor(getResources().getColor(R.color.text_black_color));
                                    }
                                    else if(portCount < 2 && portCount > 0) {

                                        nextBtState(false);

                                        defaultNum.setText(String.valueOf(portCount)+"개");
                                        defaultTitle.setText("2개 이상을 선택해주세요.");
                                        defaultTitle.setTextColor(getResources().getColor(R.color.text_gray_color));
                                        defaultNum.setTextColor(getResources().getColor(R.color.text_black_color));

                                    }else{

                                        nextBtState(true);

                                        nextBt.setBackgroundColor(getResources().getColor(R.color.red_text_color));

                                        defaultNum.setText(String.valueOf(portCount)+"개");
                                        defaultTitle.setText("선택하신 포트의 최소 투자 금액은 200만입니다.");
                                        defaultNum.setTextColor(getResources().getColor(R.color.make_port_blue_color));
                                        defaultTitle.setTextColor(getResources().getColor(R.color.text_gray_color));
                                    }
                                }
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

        downBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recyclerViewState){
                    recyclerViewState = false;
                    recyclerView.setVisibility(View.VISIBLE);
                    downBt.setImageDrawable(getResources().getDrawable(R.drawable.ic_down_whitegray_32_dp));

                    Animation animation = AnimationUtils.loadAnimation(ActivityPotCook.this, R.anim.fade_cook);
                    animation.setFillAfter(false);
                    animation.setFillEnabled(false);
                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            downBt.setEnabled(false);
                        }
                        @Override
                        public void onAnimationEnd(Animation animation) {
                            downBt.setEnabled(true);
                        }
                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }
                    });
                    recyclerView.startAnimation(animation);


                }else{
                    downBt.setImageDrawable(getResources().getDrawable(R.drawable.ic_up_gray_32_dp));
                    recyclerViewState = true;
                    recyclerView.setVisibility(View.GONE);

                }
            }
        });


        adapterPotCook.setPotDeleteClick(new AdapterPotCook.PotDeleteClick() {
            @Override
            public void onClick(int position) {
                if(modelPortLists.size() > 2){
                    changedPot = true;
                    modelPortLists.remove(position);
                    adapterPotCook.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(ActivityPotCook.this, "2개이하 삭제 불가",Toast.LENGTH_SHORT).show();
                }
            }
        });


        stableBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                portCaterogy = 701;
                infoText.setText("안정적인 수익률을 생각하는 방법");
                stableBt.setBackgroundResource(R.drawable.click_roundbt);
                stableBt.setTextColor(getResources().getColor(R.color.button_able_text));
                middleBt.setBackgroundResource(R.drawable.normal_roundbt);
                middleBt.setTextColor(getResources().getColor(R.color.button_enable_text));
                adventBt.setBackgroundResource(R.drawable.normal_roundbt);
                adventBt.setTextColor(getResources().getColor(R.color.button_enable_text));
            }
        });
        middleBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                portCaterogy = 703;
                infoText.setText("중립적인 수익률을 생각하는 방법");
                middleBt.setBackgroundResource(R.drawable.click_roundbt);
                middleBt.setTextColor(getResources().getColor(R.color.button_able_text));
                stableBt.setBackgroundResource(R.drawable.normal_roundbt);
                stableBt.setTextColor(getResources().getColor(R.color.button_enable_text));
                adventBt.setBackgroundResource(R.drawable.normal_roundbt);
                adventBt.setTextColor(getResources().getColor(R.color.button_enable_text));
            }
        });
        adventBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                portCaterogy = 704;
                infoText.setText("모험적인 수익률을 생각하는 방법");
                adventBt.setBackgroundResource(R.drawable.click_roundbt);
                adventBt.setTextColor(getResources().getColor(R.color.button_able_text));
                stableBt.setBackgroundResource(R.drawable.normal_roundbt);
                stableBt.setTextColor(getResources().getColor(R.color.button_enable_text));
                middleBt.setBackgroundResource(R.drawable.normal_roundbt);
                middleBt.setTextColor(getResources().getColor(R.color.button_enable_text));
            }
        });

    }// onCreate 끝

    //임시로 만든 차트의 데이터를 불러옴
    void getMyChartData(String ptCode) {

        Call<ModelChartData> getTest2 = RetrofitClient.getInstance().getService().getPotChartData(ptCode,0, 0);
        getTest2.enqueue(new Callback<ModelChartData>() {
            @Override
            public void onResponse(Call<ModelChartData> call, Response<ModelChartData> response) {
                if(response.code() == 200) {
                    for(int a = 0 ; a < response.body().getContent().size() ; a++) {

                        modelTransChartLists.add(new ModelTransChartList(response.body().getContent().get(a).getDate(), response.body().getContent().get(a).getPrice(), response.body().getContent().get(a).getExp()));
                    }

                    ChartManager.get_Instance().setTransChartLists(modelTransChartLists);

                    Intent intent = new Intent(ActivityPotCook.this, ActivityPreviewChart.class);
                    intent.putExtra("finishcategory", portCaterogy);
                    intent.putExtra("chartData", bundle);
                    startActivityForResult(intent, 100);

                    loadingCustomMakingPort.dismiss();
                }
            }
            @Override
            public void onFailure(Call<ModelChartData> call, Throwable t) {
                loadingCustomMakingPort.dismiss();
                Toast.makeText(ActivityPotCook.this, "서버가 불안정합니다\n잠시 후 다시 시도해 주세요.",Toast.LENGTH_SHORT).show();
                Log.e("레트로핏 실패","값 : "+t.getMessage());
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100){
            if(resultCode == 100){

                Intent intent1 = new Intent(ActivityPotCook.this, FgTab2.class);
                setResult(100, intent1);
                finish();
            }
        }
    }

    private void nextBtState(boolean state){
        if(state){
            nextBt.setEnabled(true);
            nextBt.setTextColor(getResources().getColor(R.color.button_able_text));
            nextBt.setBackgroundColor(getResources().getColor(R.color.button_able));
        }else{
            nextBt.setEnabled(false);
            nextBt.setBackgroundColor(getResources().getColor(R.color.button_enable));
            nextBt.setTextColor(getResources().getColor(R.color.button_enable_text));
        }
    }

    // Add Fragments to Tabs
    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new FgPotCookDomestic(), "국내");
        adapter.addFragment(new FgPotCookOverseas(), "해외");

        viewPager.setAdapter(adapter);
    }
    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        public Adapter(FragmentManager manager) {
            super(manager);
        }
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }
        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

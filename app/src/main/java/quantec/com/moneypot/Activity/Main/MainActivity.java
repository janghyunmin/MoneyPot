package quantec.com.moneypot.Activity.Main;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import quantec.com.moneypot.Activity.BaseActivity.BaseActivity;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab1.Fg_tab1;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_tab2;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab3.Fg_tab3;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab4.Fg_tab4;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab5.Fg_tab5;
import quantec.com.moneypot.DataManager.ChartManager;
import quantec.com.moneypot.Dialog.DialogLoadingMakingPort;
import quantec.com.moneypot.ModelCommon.nModel.PotDto;
import quantec.com.moneypot.Network.Retrofit.RetrofitClient;
import quantec.com.moneypot.R;
import quantec.com.moneypot.RxAndroid.RxEvent;
import quantec.com.moneypot.RxAndroid.RxEventBus;
import quantec.com.moneypot.ModelCommon.dModel.TransChartList;
import quantec.com.moneypot.Util.DecimalScale.DecimalScale;
import quantec.com.moneypot.Util.SoftKeyboardUtil.BackPressEditText;
import quantec.com.moneypot.databinding.ActivityMainBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

    ActivityMainBinding activityMainBinding;
    //포트만들기 로딩시 뒤로가기 막아둠
    public static boolean MyPortMakingState = false;
    Fg_tab1 fragment_tab1;
    Fg_tab2 fragment_tab2;
    Fg_tab3 fragment_tab3;
    Fg_tab4 fragment_tab4;
    Fg_tab5 fragment_tab5;
    //portCaterogy ( 0 : 안전형, 1 : 중립형 , 2 : 수익형 )
    long pre_cash, update_cash, portCount, Total_Cash;
    int portCaterogy = 1;
    ArrayList<String> code = new ArrayList<>();
    ArrayList<String> portName = new ArrayList<>();

    private DialogLoadingMakingPort loadingCustomMakingPort;
    //카테고리 입력 ( 중립, 공격, 안정 )
    String cate;
    //포트 만들기 조건 충족
    Boolean[] SelectItemFinishState = new Boolean[]{false, false};
    Fragment currentFragment;

    private BackPressCloseHandler backPressCloseHandler;
    //포트만들기시 열린 바텀 화면 여부
    boolean PortMakeVisible = false;

    Animation image_anim;
    //포트만들기 삭제 여부
    boolean PortMakeDeleteOpen = false;
    //내포트 삭제 여부
    boolean MyPortDeleteOprn = false;

    List<TransChartList> finishChart;

    //이전 레이아웃 포트만들기 패널 상태
    public static boolean preMakePortPannelState = false;

    public static boolean postMakePortPannelState = false;
    ////
    private static final long THRESHOLD_MILLIS = 300L;
    public static final PublishSubject<View> viewPublishSubject = PublishSubject.create();
    FragmentTransaction transaction;
    ///
    Button currentSelectedBT;

    ///임시포트페이지로 넘겨주는 데이터
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activityMainBinding.setMainActivity(this);

        mainList.add(0, this);

        activityMainBinding.makePortLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        activityMainBinding.makePortNumber.setText("0개");
        activityMainBinding.makePortPreNumber.setText("0개");
        backPressCloseHandler = new BackPressCloseHandler(this);
        finishChart = new ArrayList<>();
        activityMainBinding.makePortInfoTextBottom.setText(R.string.makePortInfo1);
        currentSelectedBT = activityMainBinding.makePortCenterBt;

        //안정형
        activityMainBinding.makePortUpBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityMainBinding.makePortInfoTextBottom.setText(R.string.makePortInfo0);
                portCaterogy = 0;
                SelectedCategoryBT(activityMainBinding.makePortUpBt);
            }
        });
        //중립형
        activityMainBinding.makePortCenterBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityMainBinding.makePortInfoTextBottom.setText(R.string.makePortInfo1);
                portCaterogy = 1;
                SelectedCategoryBT(activityMainBinding.makePortCenterBt);
            }
        });
        //수익형
        activityMainBinding.makePortStableBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityMainBinding.makePortInfoTextBottom.setText(R.string.makePortInfo2);
                portCaterogy = 2;
                SelectedCategoryBT(activityMainBinding.makePortStableBt);
            }
        });

        activityMainBinding.makePortPlusBt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_cash += 10;
                Total_Cash += update_cash;
                activityMainBinding.makePortCash.setText(String.valueOf(pre_cash+update_cash));
            }
        });
        activityMainBinding.makePortPlusBt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_cash += 50;
                Total_Cash += update_cash;
                activityMainBinding.makePortCash.setText(String.valueOf(pre_cash+update_cash));
            }
        });
        activityMainBinding.makePortPlusBt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_cash += 100;
                Total_Cash += update_cash;
                activityMainBinding.makePortCash.setText(String.valueOf(pre_cash+update_cash));
            }
        });
        activityMainBinding.makePortPlusBt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_cash += 200;
                Total_Cash += update_cash;
                activityMainBinding.makePortCash.setText(String.valueOf(pre_cash+update_cash));
            }
        });
        activityMainBinding.makePortPlusBt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_cash += 500;
                Total_Cash += update_cash;
                activityMainBinding.makePortCash.setText(String.valueOf(pre_cash+update_cash));
            }
        });

        activityMainBinding.makePortCashReplayBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_cash = 0;
                activityMainBinding.makePortCash.setText(String.valueOf(pre_cash));
            }
        });

        activityMainBinding.makePortCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        activityMainBinding.makePortCash.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 13) {
                    Toast.makeText(MainActivity.this, "허용치를 초과하였습니다.",Toast.LENGTH_SHORT).show();
                }else{
                    if(!s.toString().equals("")) {
                        if (pre_cash <= Long.parseLong(s.toString())) {
                            Total_Cash = Long.parseLong(s.toString());
                            activityMainBinding.makePortTitleInfoText.setTextColor(getResources().getColor(R.color.normal_text_color));
                            activityMainBinding.makePortTitlePreInfoText.setTextColor(getResources().getColor(R.color.normal_text_color));
                            SelectItemFinishState[1] = true;
                            PortMakeButton();
                        } else {
                            activityMainBinding.makePortTitleInfoText.setTextColor(getResources().getColor(R.color.delete_pressed_text));
                            activityMainBinding.makePortTitlePreInfoText.setTextColor(getResources().getColor(R.color.delete_pressed_text));
                            SelectItemFinishState[1] = false;
                            PortMakeButton();
                        }
                    } else {
                        activityMainBinding.makePortTitleInfoText.setTextColor(getResources().getColor(R.color.delete_pressed_text));
                        activityMainBinding.makePortTitlePreInfoText.setTextColor(getResources().getColor(R.color.delete_pressed_text));
                        SelectItemFinishState[1] = false;
                        PortMakeButton();
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        activityMainBinding.bottombar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                transaction = getSupportFragmentManager().beginTransaction();

                if(tabId == R.id.tab1) {
//                    스테이터스 바 색상 변경 -> 화이트
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

                    if(fragment_tab1 == null) {
                        fragment_tab1 = new Fg_tab1();
                        transaction.add(R.id.ContainerContain, fragment_tab1).commit();
                        currentFragment = fragment_tab1;
                    }else {
                        fragment_tab1.getView().setTag("tab1");
                        viewPublishSubject.onNext(fragment_tab1.getView());
                    }
                }else if(tabId == R.id.tab2){

                    if(fragment_tab2 == null) {
                        fragment_tab2 = new Fg_tab2();
                        transaction.hide(currentFragment).add(R.id.ContainerContain, fragment_tab2).commit();
                        currentFragment = fragment_tab2;
                    }else{
                        fragment_tab2.getView().setTag("tab2");
                        viewPublishSubject.onNext(fragment_tab2.getView());
                    }

                }else if(tabId == R.id.tab3){

                    if(fragment_tab3 == null) {
                        fragment_tab3 = new Fg_tab3();
                        transaction.hide(currentFragment).add(R.id.ContainerContain, fragment_tab3).commit();
                        currentFragment = fragment_tab3;
                    }else{
                        fragment_tab3.getView().setTag("tab3");
                        viewPublishSubject.onNext(fragment_tab3.getView());
                    }

                }else if(tabId == R.id.tab4){

                    if(fragment_tab4 == null) {
                        fragment_tab4 = new Fg_tab4();
                        transaction.hide(currentFragment).add(R.id.ContainerContain, fragment_tab4).commit();
                        currentFragment = fragment_tab4;
                    }else{
                        fragment_tab4.getView().setTag("tab4");
                        viewPublishSubject.onNext(fragment_tab4.getView());
                    }

                }else if(tabId == R.id.tab5){

                    if(fragment_tab5 == null) {
                        fragment_tab5 = new Fg_tab5();
                        transaction.hide(currentFragment).add(R.id.ContainerContain, fragment_tab5).commit();
                        currentFragment = fragment_tab5;
                    }else{
                        fragment_tab5.getView().setTag("tab5");
                        viewPublishSubject.onNext(fragment_tab5.getView());
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
                            case RxEvent.ZZIM_PORT_MAKE_MYPORT:
                                //이전 레이아웃 포트만들기 패널 업 상태
                                preMakePortPannelState = true;
                                //포트만들기 버튼 활성화 여부 이벤트
                                PortMakeButton();

                                PortMakeVisible = true;

                                activityMainBinding.makePortNumber.setText("0개");
                                activityMainBinding.makePortPreNumber.setText("0개");
                                if(portCount < 2) {
                                    activityMainBinding.makePortTitleInfoText.setText("포트 만들기는 2개 부터 가능합니다.");
                                    activityMainBinding.makePortTitlePreInfoText.setText("포트 만들기는 2개 부터 가능합니다.");
                                }else{
                                    activityMainBinding.makePortTitleInfoText.setText("선택하신 포트의 최소 투자 금액은 0만원 입니다.");
                                    activityMainBinding.makePortTitlePreInfoText.setText("선택하신 포트의 최소 투자 금액은 0만원 입니다.");
                                }

                                //포트만들기 패널 전체 레이아웃 보임
                                activityMainBinding.makePortLayout.setVisibility(View.VISIBLE);
                                //포트만들기 프리 레이아웃 보임
                                activityMainBinding.makePortLayoutPre.setVisibility(View.VISIBLE);
                                TranslateAnimation animation = new TranslateAnimation(
                                        Animation.ABSOLUTE, 0,
                                        Animation.ABSOLUTE, 0,
                                        Animation.ABSOLUTE,500,
                                        Animation.ABSOLUTE,0);
                                animation.setDuration(560);
                                animation.setFillAfter(true);
                                animation.setFillEnabled(true);
                                animation.setAnimationListener(new Animation.AnimationListener() {
                                    @Override
                                    public void onAnimationStart(Animation animation) {
                                    }
                                    @Override
                                    public void onAnimationEnd(Animation animation) {
                                    }
                                    @Override
                                    public void onAnimationRepeat(Animation animation) {
                                    }
                                });
                                activityMainBinding.makePortLayout.startAnimation(animation);
                                break;

                            case RxEvent.ZZIM_PORT_PRE_CLOSE:
                                makePortPreCloseBT();
                                break;

                            case RxEvent.ZZIM_PORT_SELECT_ITEM:
                                if(rxEvent.getBundle().getBoolean("click")) {
                                    pre_cash = pre_cash+rxEvent.getBundle().getLong("mincost");
                                    Total_Cash = pre_cash;
                                    portCount = rxEvent.getBundle().getInt("count");
                                    code.add(rxEvent.getBundle().getString("code"));

                                    portName.add(rxEvent.getBundle().getString("title"));

                                    activityMainBinding.makePortCash.setText(String.valueOf(pre_cash));
                                    activityMainBinding.makePortNumber.setText(String.valueOf(portCount)+"개");
                                    activityMainBinding.makePortPreNumber.setText(String.valueOf(portCount)+"개");
                                    if(portCount < 2) {
                                        activityMainBinding.makePortNumber.setTextColor(getResources().getColor(R.color.delete_pressed_text));
                                        activityMainBinding.makePortPreNumber.setTextColor(getResources().getColor(R.color.delete_pressed_text));
                                        SelectItemFinishState[0] = false;
                                        PortMakeButton();
                                    }else{
                                        activityMainBinding.makePortNumber.setTextColor(getResources().getColor(R.color.make_port_blue_color));
                                        activityMainBinding.makePortPreNumber.setTextColor(getResources().getColor(R.color.make_port_blue_color));
                                        SelectItemFinishState[0] = true;
                                        PortMakeButton();
                                    }
                                    if(portCount < 2) {
                                        activityMainBinding.makePortTitleInfoText.setText("포트 만들기는 2개 부터 가능합니다.");
                                        activityMainBinding.makePortTitlePreInfoText.setText("포트 만들기는 2개 부터 가능합니다.");
                                    }else{
                                        activityMainBinding.makePortTitleInfoText.setText("선택하신 포트의 최소 투자 금액은 "+String.valueOf(pre_cash)+"만원 입니다.");
                                        activityMainBinding.makePortTitlePreInfoText.setText("선택하신 포트의 최소 투자 금액은 "+String.valueOf(pre_cash)+"만원 입니다.");
                                    }
                                    update_cash = 0;

                                }else{
                                    pre_cash = pre_cash-rxEvent.getBundle().getLong("mincost");
                                    Total_Cash = pre_cash;
                                    activityMainBinding.makePortCash.setText(String.valueOf(pre_cash));
                                    portCount = rxEvent.getBundle().getInt("count");
                                    code.remove(code.indexOf(rxEvent.getBundle().getString("code")));

                                    portName.remove(portName.indexOf(rxEvent.getBundle().getString("title")));

                                    activityMainBinding.makePortNumber.setText(String.valueOf(portCount)+"개");
                                    activityMainBinding.makePortPreNumber.setText(String.valueOf(portCount)+"개");
                                    if(portCount < 2) {
                                        activityMainBinding.makePortNumber.setTextColor(getResources().getColor(R.color.delete_pressed_text));
                                        activityMainBinding.makePortPreNumber.setTextColor(getResources().getColor(R.color.delete_pressed_text));
                                        SelectItemFinishState[0] = false;
                                        PortMakeButton();
                                    }else{
                                        activityMainBinding.makePortNumber.setTextColor(getResources().getColor(R.color.make_port_blue_color));
                                        activityMainBinding.makePortPreNumber.setTextColor(getResources().getColor(R.color.make_port_blue_color));
                                        SelectItemFinishState[0] = true;
                                        PortMakeButton();
                                    }
                                    if(portCount < 2) {
                                        activityMainBinding.makePortTitleInfoText.setText("포트 만들기는 2개 부터 가능합니다.");
                                        activityMainBinding.makePortTitlePreInfoText.setText("포트 만들기는 2개 부터 가능합니다.");
                                    }else{
                                        activityMainBinding.makePortTitleInfoText.setText("선택하신 포트의 최소 투자 금액은 "+String.valueOf(pre_cash)+"만원 입니다.");
                                        activityMainBinding.makePortTitlePreInfoText.setText("선택하신 포트의 최소 투자 금액은 "+String.valueOf(pre_cash)+"만원 입니다.");
                                    }
                                    update_cash = 0;
                                }
                                break;

                            // 만든 포트 저장 시 탭2 ( 내가 만든 포트 페이지 )로 이동
                            case RxEvent.ZZIM_PORT_TRANS_PAGE:
                                makePortPreCloseBT();
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


//        Observable<Integer> source = Observable.just(1,2,3);
//        Disposable d = source.subscribe(
//                v -> Log.e("값", "이름 : "+ v),
//                err -> Log.e("에러", "값 : "+err),
//                () -> Log.e("완료", "완료")
//        );
//        Observable<Integer> sou = Observable.create(
//                (ObservableEmitter<Integer> emitter) ->{
//                emitter.onNext(100);
//                    emitter.onNext(200);
//                    emitter.onNext(300);
//                    emitter.onComplete();
//        });
//        sou.subscribe(data -> System.out.println(data));


        //포트 만들기 ok 클릭
        activityMainBinding.makePortPostOkBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(code.size()==0) {
                    Toast.makeText(getApplicationContext(), "선택된 포트가 없습니다.",Toast.LENGTH_SHORT).show();
                }else if(code.size() == 1){
                    Toast.makeText(getApplicationContext(), "포트는 2개이상 선택해 주세요.",Toast.LENGTH_SHORT).show();
                }
                else{
                    MyPortMakingState = true;

                    loadingCustomMakingPort = new DialogLoadingMakingPort(MainActivity.this, "주식투자는 단기적 수익을 쫒기 보다는\n장기적으로 보아야 성공할 수 있습니다.");
                    loadingCustomMakingPort.show();

                    if(portCaterogy==0) {
                        cate = "안정형";
                    }else if(portCaterogy == 1) {
                        cate = "중립형";
                    }else if(portCaterogy == 2) {
                        cate = "수익형";
                    }
                    bundle = new Bundle();
//                    bundle.putStringArrayList("transcode", code);
//                    bundle.putStringArrayList("transtitle",portName);
//                    bundle.putString("transcash", String.valueOf(Total_Cash));
//                    bundle.putString("transcategory", cate);

//                    Gson gson = new GsonBuilder()
//                            .setLenient()
//                            .create();
//                    String jsonCode = gson.toJson(code);
//                    MakePortData tm = new MakePortData();
//                    tm.setCost(Total_Cash);
//                    tm.setpCode(jsonCode);
//                    tm.setType(portCaterogy);

//                    ArrayList<PotEls> potEls = new ArrayList<>();
                    PotDto potDto = new PotDto(code, portCaterogy, Total_Cash);

                    Call<ModelPrevMyPot> getchartItem = RetrofitClient.getInstance().getService().getPrevMyPot("application/json", potDto);
                    getchartItem.enqueue(new Callback<ModelPrevMyPot>() {
                        @Override
                        public void onResponse(Call<ModelPrevMyPot> call, Response<ModelPrevMyPot> response) {

                            if(response.code() == 200) {

                                finishChart.clear();
                                portName.clear();

                                for(int index = 0 ; index < response.body().getContent().getPotEls().size() ; index++){
                                    portName.add(response.body().getContent().getPotEls().get(index).getElName());
                                }

//                                for(int a = 0 ; a < response.body().getElements().size() ; a++) {
//                                    finishChart.add(new TransChartList(a,response.body().getElements().get(a).getRate(), response.body().getElements().get(a).getDate()));
//                                }

                                bundle.putString("transptcode", response.body().getContent().getCode());
                                bundle.putStringArrayList("transtitle",portName);
                                bundle.putString("transcash", String.valueOf(response.body().getContent().getMinPrice()));
                                bundle.putString("transcategory", cate);

                                getMyChartData(response.body().getContent().getCode());

//                                ChartManager.get_Instance().setTransChartLists(finishChart);
//                                loadingCustomMakingPort.dismiss();
//
//                                MyPortMakingState = false;
//                                RxEventBus.getInstance().post(new RxEvent(RxEvent.ZZIM_PORT_MAKE_OK, bundle));
                            }
                        }
                        @Override
                        public void onFailure(Call<ModelPrevMyPot> call, Throwable t) {
                            loadingCustomMakingPort.dismiss();
                            Toast.makeText(MainActivity.this, "서버가 불안정합니다\n잠시 후 다시 시도해 주세요.",Toast.LENGTH_SHORT).show();
                            Log.e("레트로핏 실패","값 : "+t.getMessage());
                        }
                    });
                }
            }
        });

        activityMainBinding.makePortCash.setOnBackPressListener(onBackPressListener);
        activityMainBinding.makePortCash.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                }
            }
        });
        activityMainBinding.makePortCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            }
        });

        //포트만들기 다음 레이아웃시 백 단 막음
        activityMainBinding.FrizonLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        //이전 레이아웃 포트만들기 버튼 클릭 이벤트
        activityMainBinding.makePortPreOkBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //이전 레이아웃 포트만들기 패널 다운 상태
                preMakePortPannelState = false;
                //다음 레이아웃 포트만들기 패널 업 상태
                postMakePortPannelState = true;
                //포트만들기 다음 레이아웃 시 백 단 막는 이벤트
                activityMainBinding.FrizonLayout.setVisibility(View.VISIBLE);
                //이전 레이아웃 포트만들기 패널 사라짐
                activityMainBinding.makePortLayoutPre.setVisibility(View.GONE);
                //다음 레이아웃 포트만들기 패널 보임
                activityMainBinding.makePortLayoutContainer.setVisibility(View.VISIBLE);
                TranslateAnimation animation = new TranslateAnimation(
                        Animation.ABSOLUTE, 0,
                        Animation.ABSOLUTE, 0,
                        Animation.ABSOLUTE,1000,
                        Animation.ABSOLUTE,0);
                animation.setDuration(280);
                animation.setFillAfter(false);
                animation.setFillEnabled(false);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {}
                    @Override
                    public void onAnimationEnd(Animation animation) {
                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) {}
                });
                activityMainBinding.makePortLayoutContainer.startAnimation(animation);
                //이전 레이아웃 포트만들기 버튼 오른쪽 이동 애니메이션 후 사라짐
                TranslateAnimation animation3 = new TranslateAnimation(
                        Animation.ABSOLUTE, 0,
                        Animation.ABSOLUTE, 700,
                        Animation.ABSOLUTE,0,
                        Animation.ABSOLUTE,0);
                animation3.setDuration(560);
                animation3.setFillAfter(false);
                animation3.setFillEnabled(false);
                animation3.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {}
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        activityMainBinding.makePortPreOkBt.setVisibility(View.INVISIBLE);
                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) {}
                });
                activityMainBinding.makePortPreOkBt.startAnimation(animation3);
            }
        });
        //다음 레이아웃 포트만들기 이전 버튼 클릭
        activityMainBinding.makePortPostNoBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePortPostNoBT();
            }
        });
        viewPublishSubject.throttleFirst(THRESHOLD_MILLIS, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subject<View>() {
                    @Override
                    public boolean hasObservers() {
                        return false;
                    }
                    @Override
                    public boolean hasThrowable() {
                        return false;
                    }
                    @Override
                    public boolean hasComplete() {
                        return false;
                    }
                    @Override
                    public Throwable getThrowable() {
                        return null;
                    }
                    @Override
                    protected void subscribeActual(Observer<? super View> observer) {
                    }
                    @Override
                    public void onSubscribe(Disposable d) {
                    }
                    @Override
                    public void onNext(View view) {

//                        E/AndroidRuntime: FATAL EXCEPTION: main
//                        Process: quantec.com.moneypot, PID: 32240
//                        java.lang.IllegalStateException: Fatal Exception thrown on Scheduler.
//                                at io.reactivex.android.schedulers.HandlerScheduler$ScheduledRunnable.run(HandlerScheduler.java:111)
//                        at android.os.Handler.handleCallback(Handler.java:739)
//                        at android.os.Handler.dispatchMessage(Handler.java:95)
//                        at android.os.Looper.loop(Looper.java:148)
//                        at android.app.ActivityThread.main(ActivityThread.java:5551)
//                        at java.lang.reflect.Method.invoke(Native Method)
//                        at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:730)
//                        at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:620)
//                        Caused by: java.lang.IllegalStateException: commit already called
//                        at android.support.v4.app.BackStackRecord.commitInternal(BackStackRecord.java:664)
//                        at android.support.v4.app.BackStackRecord.commit(BackStackRecord.java:632)
//                        at quantec.com.moneypot.Activity.Main.MainActivity$21.onNext(MainActivity.java:737)
//                        at quantec.com.moneypot.Activity.Main.MainActivity$21.onNext(MainActivity.java:704)
//                        at io.reactivex.internal.operators.observable.ObservableObserveOn$ObserveOnObserver.drainNormal(ObservableObserveOn.java:200)
//                        at io.reactivex.internal.operators.observable.ObservableObserveOn$ObserveOnObserver.run(ObservableObserveOn.java:252)
//                        at io.reactivex.android.schedulers.HandlerScheduler$ScheduledRunnable.run(HandlerScheduler.java:109)
//                        at android.os.Handler.handleCallback(Handler.java:739) 
//                        at android.os.Handler.dispatchMessage(Handler.java:95) 
//                        at android.os.Looper.loop(Looper.java:148) 
//                        at android.app.ActivityThread.main(ActivityThread.java:5551) 
//                        at java.lang.reflect.Method.invoke(Native Method) 
//                        at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:730) 
//                        at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:620) 

                        if(view.getTag().equals("tab1")){
                            try {
                            transaction.hide(currentFragment).show(fragment_tab1).commit();
                            currentFragment = fragment_tab1;
                            }catch (Exception e){}
                        }
                        else if(view.getTag().equals("tab2")){
                            try {
                            transaction.hide(currentFragment).show(fragment_tab2).commit();
                            currentFragment = fragment_tab2;
                            }catch (Exception e){}
                        }
                        else if(view.getTag().equals("tab3")){
                            try {
                            transaction.hide(currentFragment).show(fragment_tab3).commit();
                            currentFragment = fragment_tab3;
                            }catch (Exception e){}
                        }
                        else if(view.getTag().equals("tab4")){
                            try {
                            transaction.hide(currentFragment).show(fragment_tab4).commit();
                            currentFragment = fragment_tab4;
                            }catch (Exception e){}
                        }
                        else if(view.getTag().equals("tab5")){
                            try {
                            transaction.hide(currentFragment).show(fragment_tab5).commit();
                            currentFragment = fragment_tab5;
                            }catch (Exception e){}
                        }
                        else if(view.getTag().equals("Fg2_cookBT")){
                            RxEventBus.getInstance().post(new RxEvent(RxEvent.ZZIM_PORT_MAKE_MYPORT, null));
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                    }
                    @Override
                    public void onComplete() {
                    }
                });


    }//onCreate 끝

    //임시로 만든 차트의 데이터를 불러옴
    void getMyChartData(String ptCode) {

        Call<ModelMyChartData> getTest2 = RetrofitClient.getInstance().getService().getMyPotChartData(11, ptCode,700);
        getTest2.enqueue(new Callback<ModelMyChartData>() {
            @Override
            public void onResponse(Call<ModelMyChartData> call, Response<ModelMyChartData> response) {
                if(response.code() == 200) {
                    finishChart.clear();
                    for(int a = 0 ; a < response.body().getContent().size() ; a++) {
                       finishChart.add(new TransChartList(a, DecimalScale.decimalScale2(String.valueOf(response.body().getContent().get(a).getExp()*100), 2, 2), response.body().getContent().get(a).getDate()));
                    }

                    ChartManager.get_Instance().setTransChartLists(finishChart);
                    loadingCustomMakingPort.dismiss();

                    MyPortMakingState = false;
                    RxEventBus.getInstance().post(new RxEvent(RxEvent.ZZIM_PORT_MAKE_OK, bundle));
                }
            }
            @Override
            public void onFailure(Call<ModelMyChartData> call, Throwable t) {
                loadingCustomMakingPort.dismiss();
                Toast.makeText(MainActivity.this, "서버가 불안정합니다\n잠시 후 다시 시도해 주세요.",Toast.LENGTH_SHORT).show();
                Log.e("레트로핏 실패","값 : "+t.getMessage());
            }
        });
    }


    // 포트만들기 중립/공격/안정 버튼 상태바꿈 이벤트
    void SelectedCategoryBT(Button selectedBT) {
        if(currentSelectedBT != null) {
            currentSelectedBT.setBackgroundResource(R.drawable.make_port_bt_normal_round);
            currentSelectedBT.setTextColor(getResources().getColor(R.color.make_port_normal_bt_color));
        }
        currentSelectedBT = selectedBT;
        currentSelectedBT.setBackgroundResource(R.drawable.make_port_bt_pressed_round);
        currentSelectedBT.setTextColor(getResources().getColor(R.color.normal_title_color));
    }

    private BackPressEditText.OnBackPressListener onBackPressListener = new BackPressEditText.OnBackPressListener() {
        @Override
        public void onBackPress()
        {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        }
    };

    //포트만들기 버튼 활성 비활성화 판단
    void PortMakeButton() {
        if(SelectItemFinishState[0] && SelectItemFinishState[1]) {
            activityMainBinding.makePortPreOkBt.setBackgroundResource(R.drawable.grade_bg);
            activityMainBinding.makePortPreOkBt.setTextColor(getResources().getColor(R.color.normal_title_color));
            activityMainBinding.makePortPreOkBt.setEnabled(true);
        }else{
            activityMainBinding.makePortPreOkBt.setBackgroundResource(R.drawable.make_port_normal_bt);
            activityMainBinding.makePortPreOkBt.setTextColor(getResources().getColor(R.color.make_port_normal_bt_color));
            activityMainBinding.makePortPreOkBt.setEnabled(false);
        }
    }

    public void onBackPressed() {

            if (!PortMakeDeleteOpen && !MyPortMakingState && !MyPortDeleteOprn && !preMakePortPannelState && !postMakePortPannelState) {
                backPressCloseHandler.onBackPressed();
            }
            else {
                if (PortMakeDeleteOpen) {
                    PortMakeDeleteOpen = false;
                    activityMainBinding.bottombar.setVisibility(View.VISIBLE);
                    image_anim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.zzim_port_delete_bottom_up);
                    activityMainBinding.bottombar.startAnimation(image_anim);

                } else if (MyPortDeleteOprn) {
                    MyPortDeleteOprn = false;
                }
                //이전 레이아웃 포트만들기 패널 업 상태 일때 뒤로가기 이벤트
                else if(preMakePortPannelState){
                    preMakePortPannelState = false;
                    makePortPreCloseBT();
                }
                //다음 레이아웃 포트만들기 패널 업 상태 일때 뒤로가기 이벤트
                else if(postMakePortPannelState){
                    //다음 레이아웃 포트만들기 패널 다운 상태
                    postMakePortPannelState = false;
                    makePortPostNoBT();
                }
            }
    }//onBackPressed 끝

    public class BackPressCloseHandler {
        private long backKeyPressedTime = 0;
        private Toast toast;
        private Activity activity;
        public BackPressCloseHandler(Activity context) {
            this.activity = context;
        }
        public void onBackPressed() {
            if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
                backKeyPressedTime = System.currentTimeMillis();
                showGuide();
                return;
            }
            if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
                activity.finish();
                toast.cancel();
            }
        }
        public void showGuide() {
            toast = Toast.makeText(activity, "\'뒤로\'버튼을 한번 더 누르시면 \n 앱이 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    //다음 레이아웃 포트만들기 이전 버튼 클릭 이벤트
    public void makePortPostNoBT() {
        //이전 레이아웃 포트만들기 패널 업 상태
        preMakePortPannelState = true;
        //포트만들기 다음 레이아웃 시 백 단 막는 이벤트 종료
        activityMainBinding.FrizonLayout.setVisibility(View.GONE);
        //다음 레이아웃 포트만들기 패널 사라짐
        activityMainBinding.makePortLayoutContainer.setVisibility(View.GONE);
        //이전 레이아웃 포트만들기 패널 보임
        activityMainBinding.makePortLayoutPre.setVisibility(View.VISIBLE);
        //이전 레이아웃 포트만들기 버튼 보임 후 왼쪽 이동 애니메이션
        activityMainBinding.makePortPreOkBt.setVisibility(View.VISIBLE);
        TranslateAnimation animation3 = new TranslateAnimation(
                Animation.ABSOLUTE, 700,
                Animation.ABSOLUTE, 0,
                Animation.ABSOLUTE,0,
                Animation.ABSOLUTE,0);
        animation3.setDuration(560);
        animation3.setFillAfter(false);
        animation3.setFillEnabled(false);
        activityMainBinding.makePortPreOkBt.startAnimation(animation3);
    }

    //포트 종료 이벤트
    public void makePortPreCloseBT(){
        currentSelectedBT = activityMainBinding.makePortCenterBt;
        activityMainBinding.makePortInfoTextBottom.setText(R.string.makePortInfo1);

        preMakePortPannelState = false;
        postMakePortPannelState = false;

        //포트만들기 다음 레이아웃 시 백 단 막는 이벤트 종료
        activityMainBinding.FrizonLayout.setVisibility(View.GONE);

        InputMethodManager makePortEditTextKeyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        makePortEditTextKeyboard.hideSoftInputFromWindow(activityMainBinding.makePortCash.getWindowToken(), 0);

        pre_cash = 0;
        update_cash = 0;
        activityMainBinding.makePortCash.setText(String.valueOf(0));
        portCount = 0;
        Total_Cash = 0;
        portCaterogy = 1;
        code.clear();

        portName.clear();

        SelectItemFinishState[0] = false;
        SelectItemFinishState[1] = false;
        PortMakeButton();

        activityMainBinding.makePortCenterBt.setBackgroundResource(R.drawable.make_port_bt_pressed_round);
        activityMainBinding.makePortCenterBt.setTextColor(getResources().getColor(R.color.normal_title_color));

        activityMainBinding.makePortStableBt.setBackgroundResource(R.drawable.make_port_bt_normal_round);
        activityMainBinding.makePortStableBt.setTextColor(getResources().getColor(R.color.make_port_normal_bt_color));

        activityMainBinding.makePortUpBt.setBackgroundResource(R.drawable.make_port_bt_normal_round);
        activityMainBinding.makePortUpBt.setTextColor(getResources().getColor(R.color.make_port_normal_bt_color));

        PortMakeVisible = false;

        RxEventBus.getInstance().post(new RxEvent(RxEvent.ZZIM_PORT_MAKE_MYPORT_CLOSE, null));

        TranslateAnimation animation = new TranslateAnimation(
                Animation.ABSOLUTE, 0,
                Animation.ABSOLUTE, 0,
                Animation.ABSOLUTE,0,
                Animation.ABSOLUTE,1200);
        animation.setDuration(400);
        animation.setFillAfter(false);
        animation.setFillEnabled(false);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                //전체 포트만들기 패널 레이아웃 숨김
                activityMainBinding.makePortLayout.setVisibility(View.GONE);
                //다음 레이아웃 포트만들기 패널 숨김
                activityMainBinding.makePortLayoutContainer.setVisibility(View.GONE);
                //이전 레이아웃 포트만들기 패널 보임
                activityMainBinding.makePortLayoutPre.setVisibility(View.VISIBLE);
                //이전 레이아웃 포트만들기 버튼 보임
                activityMainBinding.makePortPreOkBt.setVisibility(View.VISIBLE);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        activityMainBinding.makePortLayout.startAnimation(animation);
    }
}

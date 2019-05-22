package quantec.com.moneypot.Activity.ActivityLifeChallenge;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

import quantec.com.moneypot.Activity.Login.MemberShipPage.ActivityMemberShipMain;
import quantec.com.moneypot.Database.Realm.ChatBot;
import quantec.com.moneypot.Database.Realm.ChatBotTalkList;
import quantec.com.moneypot.Dialog.DialogChatbotInfo;
import quantec.com.moneypot.Dialog.DialogClosedChatBot;
import quantec.com.moneypot.R;
import quantec.com.moneypot.Util.SoftKeyboardUtil.KeyPressEditText;

public class ActivityLifeChallenge extends AppCompatActivity{

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ModelLifeCTextList> lifeCTextLists;
    AdapterLifeChallenge adapterLifeChallenge;
    ArrayList<ModelLifeCSelectList> lifeCSelectLists;
    TextView sendBt, yesBt, noBt, yesBt3, noBt3, monthBt, allBt, retryBt, joinBt;
    KeyPressEditText talkEditText, talkEditText2;

    ArrayList<ModelChartInfoLsit> chartInfoLsits;

    boolean TextFlag = false;
    boolean PriceFlag = false;
    boolean monthlyFlag = false;
    boolean chartFlag = false;

    boolean monFlag = false;
    boolean monYearFlag = false;

    boolean totalFlag = false;
    boolean totalYearFlag = false;

    List<Entry> entries;
    LineDataSet lineDataSet;
    LineData lineData;
    List<Entry> entries2;

    Long finalPrice, monthlyPrice, selectYear, hadPrice;
    float basicPrice = 0;

    long calTotalPrice, calYield;
    int calPotYear, calNormalYear;

    WheelYearPicker pickerWheel;
    TextView pickerWheelBt;

    LinearLayout bottomLayout;
    Handler handler = new Handler();
    ImageView botInfo;

    DialogChatbotInfo dialogChatbotInfo;
    DialogClosedChatBot dialogClosedChatBot;

    boolean finishedChart = false;

    Realm realm;
    ChatBot chatBot;
    ArrayList<TmpChatBotTalkList> tmpChatBotTalkLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_challenge);

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
        bottomLayout = findViewById(R.id.bottomLayout);

        yesBt = findViewById(R.id.yesBt);
        noBt = findViewById(R.id.noBt);

        yesBt3 = findViewById(R.id.yesBt3);
        noBt3 = findViewById(R.id.noBt3);

        monthBt = findViewById(R.id.monthBt);
        allBt = findViewById(R.id.allBt);

        retryBt = findViewById(R.id.retryBt);
        joinBt = findViewById(R.id.joinBt);

        botInfo = findViewById(R.id.botInfo);

        pickerWheel = findViewById(R.id.pickerWheel);
        pickerWheel.setYearStart(1);
        pickerWheel.setYearEnd(50);

        pickerWheelBt = findViewById(R.id.pickerWheelBt);

        sendBt = findViewById(R.id.sendBt);
        talkEditText = findViewById(R.id.talkEditText);
        talkEditText.setOnDoneListener(onPressDoneListener);
        talkEditText2 = findViewById(R.id.talkEditText2);
        talkEditText2.setOnDoneListener(onPressDoneListener);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        lifeCTextLists = new ArrayList<>();
        lifeCSelectLists = new ArrayList<>();
        chartInfoLsits = new ArrayList<>();

        Date date = new Date();
        String tiem = new SimpleDateFormat("aa hh:mm").format(date);

        entries = new ArrayList<>();
        lineDataSet = new LineDataSet(entries, null);
        lineData = new LineData(lineDataSet);
        entries2 = new ArrayList<>();

        adapterLifeChallenge = new AdapterLifeChallenge(lifeCSelectLists, lifeCTextLists, this, entries, lineDataSet, lineData, entries2, chartInfoLsits);
        recyclerView.setAdapter(adapterLifeChallenge);

        tmpChatBotTalkLists = new ArrayList<>();

        lifeCSelectLists.add(new ModelLifeCSelectList("내 집 마련",0));
        lifeCSelectLists.add(new ModelLifeCSelectList("자동차 구입",1));
        lifeCSelectLists.add(new ModelLifeCSelectList("노후 준비",2));
        lifeCSelectLists.add(new ModelLifeCSelectList("자녀 학비",3));
        lifeCSelectLists.add(new ModelLifeCSelectList("여행 자금",4));
        lifeCSelectLists.add(new ModelLifeCSelectList("직접 입력",5));

        initVisible();

        realm = Realm.getDefaultInstance();
        RealmResults<ChatBot> results = realm.where(ChatBot.class)
                .findAllAsync();

        if(results.where().count() == 0){

            /**
             *
             * 렘 카운트 증가되는 이슈사항
             *
             * 아래 코드를 넣어주면 렘에 데이터를 인서트 해주지 않아도 빈 데이터 값이 들어가서 카운트가 1로 증가되는것으로 보인다.
             * 따라서 필요한 위치 ( 마지막에 계산하는 메소드 )에 넣어줘서 해결함
             *
             * realm.beginTransaction();
             * chatBot = realm.createObject(ChatBot.class);
             *
             */

            lifeCTextLists.add(new ModelLifeCTextList("안녕? 나는 챗봇이야. \n너의 목표를 이뤄줄 수 있도록 도와줄게!","", "",0, ""));

            lifeCTextLists.add(new ModelLifeCTextList("먼저, 투자하려는 목적이 뭐야?","","",2, tiem));
            recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);

        }
        else{

                    lifeCTextLists.add(new ModelLifeCTextList("안녕? 나는 챗봇이야. \n너의 목표를 이뤄줄 수 있도록 도와줄게!","", "",0, ""));
                    lifeCTextLists.add(new ModelLifeCTextList("먼저, 투자하려는 목적이 뭐야?","","",2, tiem));

                    if(results.get(0).getChartState() == 0){
                        realmCalYield(results.get(0).getBasicPrice(), results.get(0).getMonthlyPrice(), results.get(0).getFinalPrice());

                    }
                    else if(results.get(0).getChartState() == 1){
                        realmCalYield2(results.get(0).getSelectYear(), results.get(0).getMonthlyPrice());
                    }
                    else {
                        realmCalYield3(results.get(0).getSelectYear(), results.get(0).getHadPrice());
                    }

                    for(ChatBotTalkList chatBotTalkList : results.get(0).getChatBotTalkLists()){

                        lifeCTextLists.add(new ModelLifeCTextList(chatBotTalkList.getTalk(),chatBotTalkList.getSubTitle(),chatBotTalkList.getLongSubTitle(),chatBotTalkList.getCategory()
                                , chatBotTalkList.getTime()));
                    }

                    realmVisible();
                    ((LinearLayoutManager) layoutManager).scrollToPositionWithOffset(lifeCTextLists.size()-2, -50);
        }

        adapterLifeChallenge.setBotSelectClick(new AdapterLifeChallenge.BotSelectClick() {
            @Override
            public void onClick(int category) {

                Date date = new Date();
                String time = new SimpleDateFormat("aa hh:mm").format(date);

                if(category == 0){

                    addTmpChatBotList(false, "내 집 마련","","",1,time);
                    addTmpChatBotList(false, "멋지다! 혹시 정해놓은 목표 금액이 있을까?","","",0,time);

                    lifeCTextLists.add(new ModelLifeCTextList("내 집 마련","","",1, time));
                    lifeCTextLists.add(new ModelLifeCTextList("멋지다! 혹시 정해놓은 목표 금액이 있을까?","","",0 ,time));

                    visibleSelectBt(true);

                    recyclerView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
                        }
                    }, 30);

                }
                else if(category == 1){

                    addTmpChatBotList(false, "자동차 구입","","",1,time);
                    addTmpChatBotList(false,"멋지다! 혹시 정해놓은 목표 금액이 있을까?","","",0,time);

                    lifeCTextLists.add(new ModelLifeCTextList("자동차 구입","","",1, time));
                    lifeCTextLists.add(new ModelLifeCTextList("멋지다! 혹시 정해놓은 목표 금액이 있을까?","","",0 ,time));

                    visibleSelectBt(true);

                    recyclerView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
                        }
                    }, 30);
                }
                else if(category == 2){

                    addTmpChatBotList(false,"노후 준비","","",1,time);
                    addTmpChatBotList(false, "멋지다! 혹시 정해놓은 목표 금액이 있을까?","","",0,time);

                    lifeCTextLists.add(new ModelLifeCTextList("노후 준비","","",1, time));
                    lifeCTextLists.add(new ModelLifeCTextList("멋지다! 혹시 정해놓은 목표 금액이 있을까?","","",0 ,time));

                    visibleSelectBt(true);

                    recyclerView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
                        }
                    }, 30);
                }

                else if(category == 3){

                    addTmpChatBotList(false, "자녀 학비","","",1,time);
                    addTmpChatBotList(false,"멋지다! 혹시 정해놓은 목표 금액이 있을까?","","",0,time);


                    lifeCTextLists.add(new ModelLifeCTextList("자녀 학비","","",1, time));
                    lifeCTextLists.add(new ModelLifeCTextList("멋지다! 혹시 정해놓은 목표 금액이 있을까?","","",0 ,time));

                    visibleSelectBt(true);

                    recyclerView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
                        }
                    }, 30);
                }

                else if(category == 4){

                    addTmpChatBotList(false, "여행 자금","","",1,time);
                    addTmpChatBotList(false, "멋지다! 혹시 정해놓은 목표 금액이 있을까?","","",0,time);

                    lifeCTextLists.add(new ModelLifeCTextList("여행 자금","","",1, time));
                    lifeCTextLists.add(new ModelLifeCTextList("멋지다! 혹시 정해놓은 목표 금액이 있을까?","","",0 ,time));

                    visibleSelectBt(true);

                    recyclerView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
                        }
                    }, 30);
                }
                else if(category == 5){

                    TextFlag = true;

                    talkEditText2.setEnabled(true);
                    sendBt.setEnabled(true);

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                            imm.toggleSoftInput(1, 0);
                        }
                    }, 30);

                    recyclerView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
                        }
                    }, 100);
                }
            }
        });

        talkEditText.setOnPressListener(onBackPressListener);
        talkEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
            }
        });
        talkEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
//                recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
            }
        });

        talkEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {

                if(talkEditText.getText().length() > 0){
                    sendBt.setEnabled(true);
                }else{
                    sendBt.setEnabled(false);
                }
            }
        });

        talkEditText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
            }
        });
        talkEditText2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
//                recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
            }
        });
        talkEditText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(talkEditText2.getText().length() > 0){
                    sendBt.setEnabled(true);
                }else{
                    sendBt.setEnabled(false);
                }
            }
        });

        sendBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextFlag){

                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(talkEditText2.getWindowToken(), 0);

                    Date date = new Date();
                    String time = new SimpleDateFormat("aa hh:mm").format(date);

                    TextFlag = false;

                    addTmpChatBotList(false, talkEditText2.getText().toString(),"","",1,time);
                    addTmpChatBotList(false, "멋지다! 혹시 정해놓은 목표 금액이 있을까?","","",0,time);

                    lifeCTextLists.add(new ModelLifeCTextList(talkEditText2.getText().toString(),"","",1, tiem));
                    lifeCTextLists.add(new ModelLifeCTextList("멋지다! 혹시 정해놓은 목표 금액이 있을까?","","",0, time));

                    visibleSelectBt(true);

                    talkEditText2.setText("");
                    talkEditText2.setEnabled(false);
                    sendBt.setEnabled(false);

                    recyclerView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
                        }
                    }, 30);
                }

                if(chartFlag){

                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(talkEditText.getWindowToken(), 0);

                    monthlyPrice = Long.valueOf(talkEditText.getText().toString());

                    if(basicPrice+monthlyPrice >= 10){

                        visiblePrice(false);

                        chartFlag = false;

                        Date date = new Date();
                        String time = new SimpleDateFormat("aa hh:mm").format(date);

                        addTmpChatBotList(false, talkEditText.getText().toString()+"만원","","",1,time);
                        addTmpChatBotList(false, "좋아! 잠시만 기다려줘~","","",0,time);

                        lifeCTextLists.add(new ModelLifeCTextList(talkEditText.getText().toString()+"만원","","",1, time));
                        lifeCTextLists.add(new ModelLifeCTextList("좋아! 잠시만 기다려줘~","","",0, time));

                        talkEditText.setText("");
                        talkEditText.setEnabled(false);
                        sendBt.setEnabled(false);

                        entries.clear();

                        calculatedYield();

                        addTmpChatBotList(false, "","","",3,"");
                        addTmpChatBotList(false, "이제 너만을 위한 라이프 챌린지가 완성됐어!\n딱 맞는 상품이 있는데, 바로 확인해볼래?",
                                "","",0,"");

                        lifeCTextLists.add(new ModelLifeCTextList("","","",3, time));
                        lifeCTextLists.add(new ModelLifeCTextList("이제 너만을 위한 라이프 챌린지가 완성됐어!\n딱 맞는 상품이 있는데, 바로 확인해볼래?","","",0, ""));

                        ((LinearLayoutManager) layoutManager).scrollToPositionWithOffset(lifeCTextLists.size()-3, -50);

                        insertRealmData();

                    }else{

                        Toast.makeText(ActivityLifeChallenge.this, "월 투자금액이 모자랍니다.",Toast.LENGTH_SHORT).show();
                    }
                }

                if(monthlyFlag){

                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(talkEditText.getWindowToken(), 0);

                    Date date = new Date();
                    String time = new SimpleDateFormat("aa hh:mm").format(date);

                    monthlyFlag = false;

                    basicPrice = Float.valueOf(talkEditText.getText().toString());


                    addTmpChatBotList(false, talkEditText.getText().toString()+"만원","","",1,time);


                    lifeCTextLists.add(new ModelLifeCTextList(talkEditText.getText().toString()+"만원","","",1, time));

                    if(basicPrice >= 10){

                        addTmpChatBotList(false, "월마다 일정 금액을 추가로 투자할 계획이 있어?","*적립식 투자는 최소 10만원부터 가능합니다.","",0,time);
                        lifeCTextLists.add(new ModelLifeCTextList("월마다 일정 금액을 추가로 투자할 계획이 있어?", "*적립식 투자는 최소 10만원부터 가능합니다.","",0, time));

                        talkEditText.setText("");
                        talkEditText.setEnabled(false);
                        sendBt.setEnabled(false);

                        recyclerView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
                            }
                        }, 30);

                        visibleSelectBt3(true);

                    }else{


                        addTmpChatBotList(false, "아하… 그러면 월마다 추가로 투자를 해야겠다!\n" +
                                "얼마씩 투자할래?","","*적립식 투자는 최소 10만원부터 가능합니다.",0,time);

                        lifeCTextLists.add(new ModelLifeCTextList("아하… 그러면 월마다 추가로 투자를 해야겠다!\n" +
                                "얼마씩 투자할래?","","*적립식 투자는 최소 10만원부터 가능합니다.",0, time));

                        talkEditText.setText("");
                        talkEditText.setEnabled(false);
                        sendBt.setEnabled(false);

                        recyclerView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
                            }
                        }, 30);

                        visiblePrice(true);

                        chartFlag = true;

                        talkEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
                        talkEditText.setEnabled(true);
                        sendBt.setEnabled(true);
                    }
                }

                if(PriceFlag){

                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(talkEditText.getWindowToken(), 0);

                    finalPrice = Long.valueOf(talkEditText.getText().toString());

                    if(finalPrice >= 500){

                        Date date = new Date();
                        String time = new SimpleDateFormat("aa hh:mm").format(date);

                        PriceFlag = false;

                        addTmpChatBotList(false, talkEditText.getText().toString()+"만원","","",1,time);
                        addTmpChatBotList(false, "그럼, 현재 준비되어 있는 돈이 있을까?","","",0,time);

                        lifeCTextLists.add(new ModelLifeCTextList(talkEditText.getText().toString()+"만원","","",1, time));
                        lifeCTextLists.add(new ModelLifeCTextList("그럼, 현재 준비되어 있는 돈이 있을까?","","",0, time));

                        talkEditText.setText("");
                        talkEditText.setEnabled(false);
                        sendBt.setEnabled(false);

                        recyclerView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
                            }
                        }, 30);

                        visiblePrice(true);

                        monthlyFlag = true;

                        talkEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
                        talkEditText.setEnabled(true);
                        sendBt.setEnabled(true);

                    }else{
                        Toast.makeText(ActivityLifeChallenge.this, "최소 목표금액은 500만원 이상입니다.",Toast.LENGTH_SHORT).show();
                    }
                }

                //목표금액 없이 모아둔돈 전부 넣을때
                if(totalFlag){

                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(talkEditText.getWindowToken(), 0);

                    totalFlag = false;

                    Date date = new Date();
                    String time = new SimpleDateFormat("aa hh:mm").format(date);

                    hadPrice = Long.valueOf(talkEditText.getText().toString());

                    addTmpChatBotList(false, talkEditText.getText().toString()+"만원","","",1,time);
                    addTmpChatBotList(false, "몇 년 동안 투자하고 싶어?","","",0,time);

                    lifeCTextLists.add(new ModelLifeCTextList(talkEditText.getText().toString()+"만원","","",1, time));
                    lifeCTextLists.add(new ModelLifeCTextList("몇 년 동안 투자하고 싶어?","","",0, time));
                    lifeCTextLists.add(new ModelLifeCTextList("","","",9,""));

                    visibleYear(true);

                    talkEditText.setText("");
                    talkEditText.setEnabled(false);
                    sendBt.setEnabled(false);

                    recyclerView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
                        }
                    }, 30);

                    totalYearFlag = true;
                }

                //목표금액 없이 월마다 넣을때
                if(monFlag){

                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(talkEditText.getWindowToken(), 0);

                    monFlag = false;

                    Date date = new Date();
                    String time = new SimpleDateFormat("aa hh:mm").format(date);

                    monthlyPrice = Long.valueOf(talkEditText.getText().toString());

                    addTmpChatBotList(false, talkEditText.getText().toString()+"만원","","",1,time);
                    addTmpChatBotList(false, "몇 년 동안 투자하고 싶어?","","",0,time);

                    lifeCTextLists.add(new ModelLifeCTextList(talkEditText.getText().toString()+"만원","","",1, time));
                    lifeCTextLists.add(new ModelLifeCTextList("몇 년 동안 투자하고 싶어?","","",0, time));
                    lifeCTextLists.add(new ModelLifeCTextList("","","",9,""));

                    visibleYear(true);

                    talkEditText.setText("");
                    talkEditText.setEnabled(false);
                    sendBt.setEnabled(false);

                    recyclerView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
                        }
                    }, 30);

                    monYearFlag = true;
                }
            }
        });


        pickerWheelBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(monYearFlag){

                    monYearFlag = false;

                    lifeCTextLists.remove(lifeCTextLists.size()-1);

                    Date date = new Date();
                    String time = new SimpleDateFormat("aa hh:mm").format(date);

                    selectYear = (long)pickerWheel.getCurrentYear();

                    addTmpChatBotList(false, String.valueOf(selectYear)+"년","","",1,time);
                    addTmpChatBotList(false, "좋아! 잠시만 기다려줘~","","",0,time);

                    lifeCTextLists.add(new ModelLifeCTextList(String.valueOf(selectYear)+"년","","",1, time));
                    lifeCTextLists.add(new ModelLifeCTextList("좋아! 잠시만 기다려줘~","","",0, time));

                    talkEditText.setText("");
                    talkEditText.setEnabled(false);
                    sendBt.setEnabled(false);

                    entries.clear();

                    calculatedYield2();

                    addTmpChatBotList(false, "","","",8,"");
                    addTmpChatBotList(false, "이제 너만을 위한 라이프 챌린지가 완성됐어!\n딱 맞는 상품이 있는데, 바로 확인해볼래?","","",0,"");

                    lifeCTextLists.add(new ModelLifeCTextList("","","",8, time));
                    lifeCTextLists.add(new ModelLifeCTextList("이제 너만을 위한 라이프 챌린지가 완성됐어!\n딱 맞는 상품이 있는데, 바로 확인해볼래?","","",0, ""));

                    ((LinearLayoutManager) layoutManager).scrollToPositionWithOffset(lifeCTextLists.size()-3, -50);

                    visibleYear(false);
                    visiblePrice(false);

                    insertRealmData();

                }else{

                    lifeCTextLists.remove(lifeCTextLists.size()-1);

                    Date date = new Date();
                    String time = new SimpleDateFormat("aa hh:mm").format(date);

                    selectYear = (long)pickerWheel.getCurrentYear();

                    addTmpChatBotList(false, String.valueOf(selectYear)+"년","","",1,"");
                    addTmpChatBotList(false, "좋아! 잠시만 기다려줘~","","",0,"");

                    lifeCTextLists.add(new ModelLifeCTextList(String.valueOf(selectYear)+"년","","",1, time));
                    lifeCTextLists.add(new ModelLifeCTextList("좋아! 잠시만 기다려줘~","","",0, time));

                    talkEditText.setText("");
                    talkEditText.setEnabled(false);
                    sendBt.setEnabled(false);

                    entries.clear();
                    calculatedYield3();


                    addTmpChatBotList(false, "","","",8,"");
                    addTmpChatBotList(false, "이제 너만을 위한 라이프 챌린지가 완성됐어!\n딱 맞는 상품이 있는데, 바로 확인해볼래?","","",0,"");

                    lifeCTextLists.add(new ModelLifeCTextList("","","",8, time));
                    lifeCTextLists.add(new ModelLifeCTextList("이제 너만을 위한 라이프 챌린지가 완성됐어!\n딱 맞는 상품이 있는데, 바로 확인해볼래?","","",0, ""));

                    ((LinearLayoutManager) layoutManager).scrollToPositionWithOffset(lifeCTextLists.size()-3, -50);

                    visibleYear(false);
                    visiblePrice(false);

                    insertRealmData();

                }

            }
        });

        yesBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visibleSelectBt(false);

                Date date = new Date();
                String time = new SimpleDateFormat("aa hh:mm").format(date);

                addTmpChatBotList(false, "응, 있지!","","",1,time);
                addTmpChatBotList(false, "와, 궁금하다! 얼마인지 알려줄 수 있어?","","",0,time);

                lifeCTextLists.add(new ModelLifeCTextList("응, 있지!","","",1, time));
                lifeCTextLists.add(new ModelLifeCTextList("와, 궁금하다! 얼마인지 알려줄 수 있어?","","",0, time));

                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
                    }
                }, 30);

                visiblePrice(true);

                talkEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
                talkEditText.setEnabled(true);
                sendBt.setEnabled(true);

                PriceFlag = true;

            }
        });

        noBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date date = new Date();
                String time = new SimpleDateFormat("aa hh:mm").format(date);

                addTmpChatBotList(false, "아니, 없어","","",1,time);
                addTmpChatBotList(false, "상관 없어. 지금부터 시작하면 돼!","","",0,time);

                lifeCTextLists.add(new ModelLifeCTextList("아니, 없어","","",1, time));
                lifeCTextLists.add(new ModelLifeCTextList("상관 없어. 지금부터 시작하면 돼!","","",0, time));
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        visibleSelectBt4(true);
                        recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
                    }
                });


                addTmpChatBotList(false, "월 마다 조금씩 모으고 싶어?\n아니면 한 번에 모아둔 돈으로 투자해볼까?","","",0,time);

                lifeCTextLists.add(new ModelLifeCTextList("월 마다 조금씩 모으고 싶어?\n아니면 한 번에 모아둔 돈으로 투자해볼까?","","",0, time));
                adapterLifeChallenge.notifyDataSetChanged();
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
                    }
                });
            }
        });

        //목표금액 있고 기본금액 잇고 월마다 넣음
        yesBt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                visibleSelectBt3(false);

                Date date = new Date();
                String time = new SimpleDateFormat("aa hh:mm").format(date);

                addTmpChatBotList(false, "응, 있지!","","",1,time);
                addTmpChatBotList(false, "월에 얼마씩 넣을꺼야?","","",0,time);

                lifeCTextLists.add(new ModelLifeCTextList("응, 있지!","","",1, time));
                lifeCTextLists.add(new ModelLifeCTextList("월에 얼마씩 넣을꺼야?","","",0, time));

                talkEditText.setText("");
                talkEditText.setEnabled(false);
                sendBt.setEnabled(false);

                adapterLifeChallenge.notifyDataSetChanged();
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
                        visiblePrice(true);
                    }
                }, 30);

                chartFlag = true;

                talkEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
                talkEditText.setEnabled(true);
                sendBt.setEnabled(true);

            }
        });

        //목표금액 있고 기본금액 있고 월마다는 안넣음
        noBt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visibleSelectBt3(false);

                Date date = new Date();
                String time = new SimpleDateFormat("aa hh:mm").format(date);

                addTmpChatBotList(false,"아니, 없어","","",1,time);

                lifeCTextLists.add(new ModelLifeCTextList("아니, 없어","","",1, time));

                chartFlag = false;
                monthlyPrice = 0L;

                addTmpChatBotList(false, "좋아! 잠시만 기다려줘~","","",0,time);

                lifeCTextLists.add(new ModelLifeCTextList("좋아! 잠시만 기다려줘~","","",0, time));

                ((LinearLayoutManager) layoutManager).scrollToPositionWithOffset(lifeCTextLists.size()-3, -500);

                talkEditText.setText("");
                talkEditText.setEnabled(false);
                sendBt.setEnabled(false);

                entries.clear();

                calculatedYield();
                visiblePrice(false);

                addTmpChatBotList(false, "","","",3,"");
                addTmpChatBotList(false, "이제 너만을 위한 라이프 챌린지가 완성됐어!\n딱 맞는 상품이 있는데, 바로 확인해볼래?",
                        "","",0,"");

                lifeCTextLists.add(new ModelLifeCTextList("","","",3, time));
                lifeCTextLists.add(new ModelLifeCTextList("이제 너만을 위한 라이프 챌린지가 완성됐어!\n딱 맞는 상품이 있는데, 바로 확인해볼래?","","",0, ""));

                insertRealmData();
            }
        });

        //목표 금액 없이 월마다 투자
        monthBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date date = new Date();
                String time = new SimpleDateFormat("aa hh:mm").format(date);

                addTmpChatBotList(false, "월 마다!","","",1,time);
                addTmpChatBotList(false, "월에 얼마씩 넣을꺼야?","","",0,time);

                lifeCTextLists.add(new ModelLifeCTextList("월 마다!","","",1, time));
                lifeCTextLists.add(new ModelLifeCTextList("월에 얼마씩 넣을꺼야?","","",0, time));

                talkEditText.setText("");
                talkEditText.setEnabled(false);
                sendBt.setEnabled(false);

                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
                    }
                }, 30);

                visibleSelectBt4(false);

                monFlag = true;

                talkEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
                talkEditText.setEnabled(true);
                sendBt.setEnabled(true);

            }
        });


        //목표 금액 없이 모아둔 돈으로 투자
        allBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date date = new Date();
                String time = new SimpleDateFormat("aa hh:mm").format(date);

                addTmpChatBotList(false, "모아둔 돈으로 한번에!","","",1,time);
                addTmpChatBotList(false, "월에 얼마씩 넣을꺼야?","","",0,time);


                lifeCTextLists.add(new ModelLifeCTextList("모아둔 돈으로 한번에!","","",1, time));
                lifeCTextLists.add(new ModelLifeCTextList("얼마나 넣을 생각이야?","","",0, time));

                talkEditText.setText("");
                talkEditText.setEnabled(false);
                sendBt.setEnabled(false);

                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
                    }
                }, 30);

                visibleSelectBt4(false);

                totalFlag = true;

                talkEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
                talkEditText.setEnabled(true);
                sendBt.setEnabled(true);

            }
        });


        //다시 시작하기
        retryBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                realm.beginTransaction();
                RealmResults<ChatBot> results = realm.where(ChatBot.class)
                        .findAll();

                results.deleteAllFromRealm();
                realm.commitTransaction();
                realm.close();

                Intent intent = new Intent(ActivityLifeChallenge.this, ActivityLifeChallenge.class);
                startActivity(intent);
                finish();

            }
        });

        //30초 가입하기
        joinBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ActivityLifeChallenge.this, ActivityMemberShipMain.class);
                startActivity(intent);
                finish();

            }
        });

        //챌린지 봇 정보 보기
        botInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogChatbotInfo = new DialogChatbotInfo(ActivityLifeChallenge.this, infoCloseBt);
                dialogChatbotInfo.show();
            }
        });

    }// onCreate 끝


    //챗봇 저장
    private void insertRealmData(){

        for(TmpChatBotTalkList tmpChatBotTalkList : tmpChatBotTalkLists){

            ChatBotTalkList chatBotTalkList = realm.createObject(ChatBotTalkList.class);
            chatBotTalkList.setTalk(tmpChatBotTalkList.getTalk());
            chatBotTalkList.setSubTitle(tmpChatBotTalkList.getSubTitle());
            chatBotTalkList.setLongSubTitle(tmpChatBotTalkList.getLongSubTitle());
            chatBotTalkList.setCategory(tmpChatBotTalkList.getCategory());
            chatBotTalkList.setTime(tmpChatBotTalkList.getTime());
            chatBot.getChatBotTalkLists().add(chatBotTalkList);
        }

        realm.commitTransaction();
    }

    //챗봇 대화 임시 저장
    private void addTmpChatBotList(boolean endState, String talk, String subTitle,
                                   String longSubTitle, int category, String time){

        tmpChatBotTalkLists.add(new TmpChatBotTalkList(talk, time, subTitle,longSubTitle, category));

    }



    //chatbot 정보 보기
    private View.OnClickListener infoCloseBt = new View.OnClickListener() {
        public void onClick(View v) {
            dialogChatbotInfo.dismiss();
        }
    };


    //뒤로가기 챗봇종료 확인 버튼
    private View.OnClickListener backOkListener = new View.OnClickListener() {
        public void onClick(View v) {
            dialogClosedChatBot.dismiss();
            finish();
        }
    };
    //뒤로가기 챗봇종료 취소 버튼
    private View.OnClickListener backCancleListener = new View.OnClickListener() {
        public void onClick(View v) {
            dialogClosedChatBot.dismiss();
        }
    };

    private void initVisible(){
        talkEditText2.setVisibility(View.VISIBLE);
        sendBt.setVisibility(View.VISIBLE);
        yesBt.setVisibility(View.GONE);
        noBt.setVisibility(View.GONE);
        yesBt3.setVisibility(View.GONE);
        noBt3.setVisibility(View.GONE);
        monthBt.setVisibility(View.GONE);
        allBt.setVisibility(View.GONE);
    }

    private void visibleSelectBt(boolean visible){
        if(visible){

            talkEditText2.setVisibility(View.GONE);
            sendBt.setVisibility(View.GONE);
            yesBt.setVisibility(View.VISIBLE);
            noBt.setVisibility(View.VISIBLE);

        }else{
            talkEditText.setVisibility(View.VISIBLE);
            sendBt.setVisibility(View.VISIBLE);
            yesBt.setVisibility(View.GONE);
            noBt.setVisibility(View.GONE);
        }
    }

    private void visibleSelectBt3(boolean visible){
        if(visible){
            talkEditText.setVisibility(View.GONE);
            sendBt.setVisibility(View.GONE);
            yesBt3.setVisibility(View.VISIBLE);
            noBt3.setVisibility(View.VISIBLE);
        }else{

            talkEditText.setVisibility(View.VISIBLE);
            sendBt.setVisibility(View.VISIBLE);
            yesBt3.setVisibility(View.GONE);
            noBt3.setVisibility(View.GONE);
        }
    }

    private void visibleSelectBt4(boolean visible){
        if(visible){
            talkEditText.setVisibility(View.GONE);
            sendBt.setVisibility(View.GONE);
            yesBt.setVisibility(View.GONE);
            noBt.setVisibility(View.GONE);
            monthBt.setVisibility(View.VISIBLE);
            allBt.setVisibility(View.VISIBLE);
        }else{
            talkEditText.setVisibility(View.VISIBLE);
            sendBt.setVisibility(View.VISIBLE);
            monthBt.setVisibility(View.GONE);
            allBt.setVisibility(View.GONE);
        }
    }

    private void visiblePrice(boolean visible){
        if(visible){

        }else{

            talkEditText.setVisibility(View.GONE);
            sendBt.setVisibility(View.GONE);
            retryBt.setVisibility(View.VISIBLE);
            joinBt.setVisibility(View.VISIBLE);
        }
    }

    private void visibleYear(boolean visible){
        if(visible){
            pickerWheel.setVisibility(View.VISIBLE);
            pickerWheelBt.setEnabled(true);
            pickerWheelBt.setVisibility(View.VISIBLE);
        }else{
            pickerWheel.setVisibility(View.INVISIBLE);
            pickerWheelBt.setEnabled(false);
            pickerWheelBt.setVisibility(View.INVISIBLE);
        }
    }

    private void realmVisible(){
        talkEditText2.setVisibility(View.GONE);
        talkEditText.setVisibility(View.GONE);
        sendBt.setVisibility(View.GONE);
        retryBt.setVisibility(View.VISIBLE);
        joinBt.setVisibility(View.VISIBLE);
    }


    private void realmCalYield(float basicPrice, long monthlyPrice, long finalPrice) {

        finishedChart = true;

        entries.add(new Entry(0, (basicPrice*10000), (basicPrice*10000)));

        float price = (basicPrice + (monthlyPrice * 12)) * 1.1f;

        entries.add(new Entry(1, price*10000, price*10000));

        calPotYear = 1;
        calTotalPrice = (long) (basicPrice + monthlyPrice);
        calYield = (long) (price - calTotalPrice);

        if (price < finalPrice) {

            for (int yield = 2; yield < 10000; yield++) {

                price = (price + (monthlyPrice * 12)) * 1.1f;
                entries.add(new Entry(yield, price*10000, price*10000));

                if (price >= finalPrice) {

                    calPotYear = yield;
                    calTotalPrice = (long) (basicPrice + (monthlyPrice * 12 * yield));
                    calYield = (long) (price - calTotalPrice);
                    break;
                }
            }
        }

        entries2.add(new Entry(0, basicPrice*10000, basicPrice*10000));
        float price2 = (basicPrice + (monthlyPrice * 12)) * 1.03f;
        entries2.add(new Entry(1, price2*10000, price2*10000));

        calNormalYear = 1;

        if(price2 < finalPrice){

            for (int yield2 = 2; yield2 < 1000; yield2++) {
                price2 = (price2 + (monthlyPrice * 12)) * 1.03f;
                entries2.add(new Entry(yield2, price2*10000, price2*10000));

                if (price2 >= finalPrice) {

                    calNormalYear = yield2;
                    break;
                }
            }
        }
        chartInfoLsits.add(new ModelChartInfoLsit(String.valueOf(calTotalPrice*10000), String.valueOf(calYield*10000), String.valueOf(calPotYear), String.valueOf(calNormalYear), "", ""));
    }

    private void realmCalYield2(long selectYear, long monthlyPrice){

        finishedChart = true;

        entries.add(new Entry(0, 0, 0));
        float price = (monthlyPrice*12)*1.1f;
        entries.add(new Entry(1, price*10000, price*10000));

        for(int yield = 2 ; yield <= selectYear ; yield++){
            price = (price+(monthlyPrice*12))*1.1f;
            entries.add(new Entry(yield, price*10000, price*10000));
        }

        calTotalPrice = (long)(monthlyPrice*12*selectYear);
        calYield = (long)(price - calTotalPrice);

        entries2.add(new Entry(0, 0, 0));
        float price2 = (monthlyPrice*12)*1.03f;
        entries2.add(new Entry(1, price2*10000, price2*10000));

        for(int yield2 = 2; yield2 <= selectYear ; yield2++){
            price2 = (price2+(monthlyPrice*12))*1.03f;
            entries2.add(new Entry(yield2, price2*10000, price2*10000));
        }
        chartInfoLsits.add(new ModelChartInfoLsit(String.valueOf(calTotalPrice*10000),String.valueOf(calYield*10000),"","",String.valueOf((long)price*10000), String.valueOf((long)price2*10000)));
    }

    private void realmCalYield3(long selectYear, long hadPrice){

        finishedChart = true;

        entries.add(new Entry(0, hadPrice, hadPrice));
        float price = hadPrice*1.1f;
        entries.add(new Entry(1, price*10000, price*10000));

        for(int yield = 2 ; yield <= selectYear ; yield++){
            price = price*1.1f;
            entries.add(new Entry(yield, price*10000, price*10000));
        }

        calTotalPrice = (long)(hadPrice*selectYear);
        calYield = (long)(price - calTotalPrice);

        entries2.add(new Entry(0, hadPrice, hadPrice));
        float price2 = hadPrice*1.03f;
        entries2.add(new Entry(1, price2*10000, price2*10000));

        for(int yield2 = 2; yield2 <= selectYear ; yield2++){
            price2 = price2*1.03f;
            entries2.add(new Entry(yield2, price2*10000, price2*10000));
        }
        chartInfoLsits.add(new ModelChartInfoLsit(String.valueOf(calTotalPrice*10000),String.valueOf(calYield*10000),"","",String.valueOf((long)price*10000), String.valueOf((long)price2*10000)));
    }


    private void calculatedYield() {
        realm.beginTransaction();
        chatBot = realm.createObject(ChatBot.class);


        chatBot.setChartState(0);
        chatBot.setBasicPrice(basicPrice);
        chatBot.setFinalPrice(finalPrice);
        chatBot.setMonthlyPrice(monthlyPrice);

        finishedChart = true;

        entries.add(new Entry(0, (basicPrice*10000), (basicPrice*10000)));

        float price = (basicPrice + (monthlyPrice * 12)) * 1.1f;

        entries.add(new Entry(1, price*10000, price*10000));

        calPotYear = 1;
        calTotalPrice = (long) (basicPrice + monthlyPrice);
        calYield = (long) (price - calTotalPrice);

        if (price < finalPrice) {

            for (int yield = 2; yield < 10000; yield++) {

                price = (price + (monthlyPrice * 12)) * 1.1f;
                entries.add(new Entry(yield, price*10000, price*10000));
                if (price >= finalPrice) {

                    calPotYear = yield;
                    calTotalPrice = (long) (basicPrice + (monthlyPrice * 12 * yield));
                    calYield = (long) (price - calTotalPrice);
                    break;
                }
            }
        }

        entries2.add(new Entry(0, basicPrice*10000, basicPrice*10000));
        float price2 = (basicPrice + (monthlyPrice * 12)) * 1.03f;
        entries2.add(new Entry(1, price2*10000, price2*10000));

        calNormalYear = 1;

        if(price2 < finalPrice){

            for (int yield2 = 2; yield2 < 1000; yield2++) {
                price2 = (price2 + (monthlyPrice * 12)) * 1.03f;
                entries2.add(new Entry(yield2, price2*10000, price2*10000));

                if (price2 >= finalPrice) {

                    calNormalYear = yield2;
                    break;
                }
            }
        }
        chartInfoLsits.add(new ModelChartInfoLsit(String.valueOf(calTotalPrice*10000), String.valueOf(calYield*10000), String.valueOf(calPotYear), String.valueOf(calNormalYear), "", ""));
    }

    private void calculatedYield2(){

        realm.beginTransaction();
        chatBot = realm.createObject(ChatBot.class);

        chatBot.setChartState(1);
        chatBot.setSelectYear(selectYear);
        chatBot.setMonthlyPrice(monthlyPrice);

        finishedChart = true;

        entries.add(new Entry(0, 0, 0));
        float price = (monthlyPrice*12)*1.1f;
        entries.add(new Entry(1, price*10000, price*10000));

        for(int yield = 2 ; yield <= selectYear ; yield++){
            price = (price+(monthlyPrice*12))*1.1f;
            entries.add(new Entry(yield, price*10000, price*10000));
        }

        calTotalPrice = (long)(monthlyPrice*12*selectYear);
        calYield = (long)(price - calTotalPrice);

        entries2.add(new Entry(0, 0, 0));
        float price2 = (monthlyPrice*12)*1.03f;
        entries2.add(new Entry(1, price2*10000, price2*10000));

        for(int yield2 = 2; yield2 <= selectYear ; yield2++){
            price2 = (price2+(monthlyPrice*12))*1.03f;
            entries2.add(new Entry(yield2, price2*10000, price2*10000));
        }
        chartInfoLsits.add(new ModelChartInfoLsit(String.valueOf(calTotalPrice*10000),String.valueOf(calYield*10000),"","",String.valueOf((long)price*10000), String.valueOf((long)price2*10000)));
    }


    private void calculatedYield3(){

        realm.beginTransaction();
        chatBot = realm.createObject(ChatBot.class);

        chatBot.setChartState(2);
        chatBot.setSelectYear(selectYear);
        chatBot.setHadPrice(hadPrice);

        finishedChart = true;

        entries.add(new Entry(0, hadPrice, hadPrice));
        float price = hadPrice*1.1f;
        entries.add(new Entry(1, price*10000, price*10000));

        for(int yield = 2 ; yield <= selectYear ; yield++){
            price = price*1.1f;
            entries.add(new Entry(yield, price*10000, price*10000));
        }

        calTotalPrice = (long)(hadPrice*selectYear);
        calYield = (long)(price - calTotalPrice);

        entries2.add(new Entry(0, hadPrice, hadPrice));
        float price2 = hadPrice*1.03f;
        entries2.add(new Entry(1, price2*10000, price2*10000));

        for(int yield2 = 2; yield2 <= selectYear ; yield2++){
            price2 = price2*1.03f;
            entries2.add(new Entry(yield2, price2*10000, price2*10000));
        }
        chartInfoLsits.add(new ModelChartInfoLsit(String.valueOf(calTotalPrice*10000),String.valueOf(calYield*10000),"","",String.valueOf((long)price*10000), String.valueOf((long)price2*10000)));
    }

    private KeyPressEditText.OnPressListener onBackPressListener = new KeyPressEditText.OnPressListener() {
        @Override
        public void onPress() {
        }
    };

    private KeyPressEditText.OnPressDoneListener onPressDoneListener = new KeyPressEditText.OnPressDoneListener() {
        @Override
        public void onPressDone() {
        }
    };


    @Override
    public void onBackPressed() {
        if(finishedChart){
            finish();
        }
        else{
            dialogClosedChatBot = new DialogClosedChatBot(ActivityLifeChallenge.this, backCancleListener, backOkListener);
            dialogClosedChatBot.show();
        }
    }

}





//public class ActivityLifeChallenge extends AppCompatActivity{
//
//    RecyclerView recyclerView;
//    RecyclerView.LayoutManager layoutManager;
//    ArrayList<ModelLifeCTextList> lifeCTextLists;
//    AdapterLifeChallenge adapterLifeChallenge;
//    ArrayList<ModelLifeCSelectList> lifeCSelectLists;
//    TextView sendBt, yesBt, noBt, yesBt3, noBt3, monthBt, allBt, retryBt, joinBt;
//    KeyPressEditText talkEditText, talkEditText2;
//
//    ArrayList<ModelChartInfoLsit> chartInfoLsits;
//
//    boolean TextFlag = false;
//    boolean PriceFlag = false;
//    boolean yearFlag = false;
//    boolean monthlyFlag = false;
//    boolean chartFlag = false;
//
//    boolean monFlag = false;
//    boolean monYearFlag = false;
//
//    boolean totalFlag = false;
//    boolean totalYearFlag = false;
//
//    List<Entry> entries;
//    LineDataSet lineDataSet;
//    LineData lineData;
//
//    List<Entry> entries2;
//
//    Long finalPrice, monthlyPrice, selectYear, hadPrice;
//    float basicPrice = 0;
//
//    long calTotalPrice, calYield;
//    int calPotYear, calNormalYear;
//
//    WheelYearPicker pickerWheel;
//    TextView pickerWheelBt;
//
//    LinearLayout bottomLayout;
//    Handler handler = new Handler();
//    ImageView botInfo;
//
//    DialogChatbotInfo dialogChatbotInfo;
//    DialogClosedChatBot dialogClosedChatBot;
//
//    boolean finishedChart = false;
//
//
//    Realm realm;
//    ChatBot chatBot;
//    ArrayList<TmpChatBotTalkList> tmpChatBotTalkLists;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_life_challenge);
//
//        //스테이터스 바 색상 변경 -> 화이트
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//            Window w = getWindow(); // in Activity's onCreate() for instance
//            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        }else{
//            Window window = getWindow();
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(getResources().getColor(R.color.main_page_status_bar_color));
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
//                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//        }
//        bottomLayout = findViewById(R.id.bottomLayout);
//
//        yesBt = findViewById(R.id.yesBt);
//        noBt = findViewById(R.id.noBt);
//
//        yesBt3 = findViewById(R.id.yesBt3);
//        noBt3 = findViewById(R.id.noBt3);
//
//        monthBt = findViewById(R.id.monthBt);
//        allBt = findViewById(R.id.allBt);
//
//        retryBt = findViewById(R.id.retryBt);
//        joinBt = findViewById(R.id.joinBt);
//
//        botInfo = findViewById(R.id.botInfo);
//
//        pickerWheel = findViewById(R.id.pickerWheel);
//        pickerWheel.setYearStart(1);
//        pickerWheel.setYearEnd(50);
//        pickerWheelBt = findViewById(R.id.pickerWheelBt);
//
//        sendBt = findViewById(R.id.sendBt);
//        talkEditText = findViewById(R.id.talkEditText);
//        talkEditText.setOnDoneListener(onPressDoneListener);
//        talkEditText2 = findViewById(R.id.talkEditText2);
//        talkEditText2.setOnDoneListener(onPressDoneListener);
//
//        recyclerView = findViewById(R.id.recyclerView);
//        recyclerView.setHasFixedSize(true);
//
//        layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//
//        lifeCTextLists = new ArrayList<>();
//        lifeCSelectLists = new ArrayList<>();
//        chartInfoLsits = new ArrayList<>();
//
//        Date date = new Date();
//        String tiem = new SimpleDateFormat("aa hh:mm").format(date);
//
//        entries = new ArrayList<>();
//        lineDataSet = new LineDataSet(entries, null);
//        lineData = new LineData(lineDataSet);
//        entries2 = new ArrayList<>();
//
//        adapterLifeChallenge = new AdapterLifeChallenge(lifeCSelectLists, lifeCTextLists, this, entries, lineDataSet, lineData, entries2, chartInfoLsits);
//        recyclerView.setAdapter(adapterLifeChallenge);
//
//
//
//        tmpChatBotTalkLists = new ArrayList<>();
//
//        realm = Realm.getDefaultInstance();
//        RealmResults<ChatBot> results = realm.where(ChatBot.class)
//                .findAllAsync();
//
////        results.addChangeListener(new RealmChangeListener<RealmResults<ChatBot>>() {
////            @Override
////            public void onChange(RealmResults<ChatBot> chatBots) {
////                Log.e("목표", "값 : "+chatBots.get(0).getTitle());
////                Log.e("총투입", "값 : "+chatBots.get(0).getPrice1());
////                Log.e("총수익", "값 : "+chatBots.get(0).getPrice2());
////
////                for(int a = 0 ; a < chatBots.get(0).getChatBotCharts().size() ; a++){
////                    Log.e("년도", "값 : "+chatBots.get(0).getChatBotCharts().get(a).getYear());
////                    Log.e("금액", "값 : "+chatBots.get(0).getChatBotCharts().get(a).getTotalPrice());
////                }
////            }
////        });
//
//        Log.e("결과값","값 : "+results.where().count());
//
//        if(results.where().count() == 0){
//
//            realm.beginTransaction();
//            chatBot = realm.createObject(ChatBot.class);
//
//            lifeCTextLists.add(new ModelLifeCTextList("안녕? 나는 챗봇이야. \n너의 목표를 이뤄줄 수 있도록 도와줄게!","", "",0, ""));
//
//            lifeCSelectLists.add(new ModelLifeCSelectList("내 집 마련",0));
//            lifeCSelectLists.add(new ModelLifeCSelectList("자동차 구입",1));
//            lifeCSelectLists.add(new ModelLifeCSelectList("노후 준비",2));
//            lifeCSelectLists.add(new ModelLifeCSelectList("자녀 학비",3));
//            lifeCSelectLists.add(new ModelLifeCSelectList("여행 자금",4));
//            lifeCSelectLists.add(new ModelLifeCSelectList("직접 입력",5));
//
//            lifeCTextLists.add(new ModelLifeCTextList("먼저, 투자하려는 목적이 뭐야?","","",2, tiem));
//            recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
//
//        }else{
//            results.addChangeListener(new RealmChangeListener<RealmResults<ChatBot>>() {
//            @Override
//            public void onChange(RealmResults<ChatBot> chatBots) {
//
//                lifeCTextLists.add(new ModelLifeCTextList("안녕? 나는 챗봇이야. \n너의 목표를 이뤄줄 수 있도록 도와줄게!","", "",0, ""));
//
//                lifeCSelectLists.add(new ModelLifeCSelectList("내 집 마련",0));
//                lifeCSelectLists.add(new ModelLifeCSelectList("자동차 구입",1));
//                lifeCSelectLists.add(new ModelLifeCSelectList("노후 준비",2));
//                lifeCSelectLists.add(new ModelLifeCSelectList("자녀 학비",3));
//                lifeCSelectLists.add(new ModelLifeCSelectList("여행 자금",4));
//                lifeCSelectLists.add(new ModelLifeCSelectList("직접 입력",5));
//
//                lifeCTextLists.add(new ModelLifeCTextList("먼저, 투자하려는 목적이 뭐야?","","",2, tiem));
//
//                realmCalYield(chatBots.get(0).getBasicPrice(), chatBots.get(0).getMonthlyPrice(), chatBots.get(0).getFinalPrice());
//
//                for(ChatBotTalkList chatBotTalkList : chatBots.get(0).getChatBotTalkLists()){
//
//                    lifeCTextLists.add(new ModelLifeCTextList(chatBotTalkList.getTalk(),chatBotTalkList.getSubTitle(),chatBotTalkList.getLongSubTitle(),chatBotTalkList.getCategory()
//                            , chatBotTalkList.getTime()));
//                }
//
//                realmVisible();
//
//                ((LinearLayoutManager) layoutManager).scrollToPositionWithOffset(lifeCTextLists.size()-2, -50);
//            }
//        });
//    }
//
//        adapterLifeChallenge.setBotSelectClick(new AdapterLifeChallenge.BotSelectClick() {
//            @Override
//            public void onClick(int category) {
//
//                Date date = new Date();
//                String time = new SimpleDateFormat("aa hh:mm").format(date);
//
//                if(category == 0){
//
//                    addTmpChatBotList(false, "내 집 마련","","",1,time, 0, 0, 0, 0, 0, 0);
//                    addTmpChatBotList(false, "멋지다! 혹시 정해놓은 목표 금액이 있을까?","","",0,time,
//                            0, 0, 0, 0, 0, 0);
//
//                    lifeCTextLists.add(new ModelLifeCTextList("내 집 마련","","",1, time));
//                    lifeCTextLists.add(new ModelLifeCTextList("멋지다! 혹시 정해놓은 목표 금액이 있을까?","","",0 ,time));
//
//                    visibleSelectBt(true);
//
//                    recyclerView.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
//                        }
//                    }, 30);
//
//                }
//                else if(category == 1){
//
//                    addTmpChatBotList(false, "자동차 구입","","",1,time, 0, 0, 0, 0, 0, 0);
//                    addTmpChatBotList(false,"멋지다! 혹시 정해놓은 목표 금액이 있을까?","","",0,time, 0, 0, 0, 0, 0, 0);
//
//                    lifeCTextLists.add(new ModelLifeCTextList("자동차 구입","","",1, time));
//                    lifeCTextLists.add(new ModelLifeCTextList("멋지다! 혹시 정해놓은 목표 금액이 있을까?","","",0 ,time));
//
//                    visibleSelectBt(true);
//
//                    recyclerView.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
//                        }
//                    }, 30);
//                }
//                else if(category == 2){
//
//                    addTmpChatBotList(false,"노후 준비","","",1,time, 0, 0, 0, 0, 0, 0);
//                    addTmpChatBotList(false, "멋지다! 혹시 정해놓은 목표 금액이 있을까?","","",0,time, 0, 0, 0, 0, 0, 0);
//
//                    lifeCTextLists.add(new ModelLifeCTextList("노후 준비","","",1, time));
//                    lifeCTextLists.add(new ModelLifeCTextList("멋지다! 혹시 정해놓은 목표 금액이 있을까?","","",0 ,time));
//
//                    visibleSelectBt(true);
//
//                    recyclerView.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
//                        }
//                    }, 30);
//                }
//
//                else if(category == 3){
//
//                    lifeCTextLists.add(new ModelLifeCTextList("자녀 학비","","",1, time));
//                    lifeCTextLists.add(new ModelLifeCTextList("멋지다! 혹시 정해놓은 목표 금액이 있을까?","","",0 ,time));
//
//                    visibleSelectBt(true);
//
//                    recyclerView.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
//                        }
//                    }, 30);
//                }
//
//                else if(category == 4){
//
//                    addTmpChatBotList(false, "여행 자금","","",1,time, 0, 0, 0, 0, 0, 0);
//                    addTmpChatBotList(false, "멋지다! 혹시 정해놓은 목표 금액이 있을까?","","",0,time, 0, 0, 0, 0, 0, 0);
//
//                    lifeCTextLists.add(new ModelLifeCTextList("여행 자금","","",1, time));
//                    lifeCTextLists.add(new ModelLifeCTextList("멋지다! 혹시 정해놓은 목표 금액이 있을까?","","",0 ,time));
//
//                    visibleSelectBt(true);
//
//                    recyclerView.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
//                        }
//                    }, 30);
//                }
//                else if(category == 5){
//
//                    TextFlag = true;
//
//                    talkEditText2.setEnabled(true);
//                    sendBt.setEnabled(true);
//
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
//                            imm.toggleSoftInput(1, 0);
//                        }
//                    }, 30);
//
//                    recyclerView.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
//                        }
//                    }, 100);
//                }
//            }
//        });
//
//        talkEditText.setOnPressListener(onBackPressListener);
//        talkEditText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
//            }
//        });
//        talkEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
////                recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
//            }
//        });
//
//        talkEditText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//            }
//            @Override
//            public void afterTextChanged(Editable s) {
//
//                if(talkEditText.getText().length() > 0){
//                    sendBt.setEnabled(true);
//                }else{
//                    sendBt.setEnabled(false);
//                }
//            }
//        });
//
//        talkEditText2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
//            }
//        });
//        talkEditText2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
////                recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
//            }
//        });
//        talkEditText2.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//            }
//            @Override
//            public void afterTextChanged(Editable s) {
//                if(talkEditText2.getText().length() > 0){
//                    sendBt.setEnabled(true);
//                }else{
//                    sendBt.setEnabled(false);
//                }
//            }
//        });
//
//        sendBt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if(TextFlag){
//
//                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(talkEditText2.getWindowToken(), 0);
//
//                    Date date = new Date();
//                    String time = new SimpleDateFormat("aa hh:mm").format(date);
//
//                    TextFlag = false;
//
//                    addTmpChatBotList(false, talkEditText2.getText().toString(),"","",1,time, 0, 0, 0, 0, 0, 0);
//                    addTmpChatBotList(false, "멋지다! 혹시 정해놓은 목표 금액이 있을까?","","",0,time, 0, 0, 0, 0, 0, 0);
//
//                    lifeCTextLists.add(new ModelLifeCTextList(talkEditText2.getText().toString(),"","",1, tiem));
//                    lifeCTextLists.add(new ModelLifeCTextList("멋지다! 혹시 정해놓은 목표 금액이 있을까?","","",0, time));
//
//                    visibleSelectBt(true);
//
//                    talkEditText2.setText("");
//                    talkEditText2.setEnabled(false);
//                    sendBt.setEnabled(false);
//
//                    recyclerView.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
//                        }
//                    }, 30);
//                }
//
//                if(yearFlag){
//
//                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(talkEditText.getWindowToken(), 0);
//
//                    Date date = new Date();
//                    String time = new SimpleDateFormat("aa hh:mm").format(date);
//
//                    yearFlag = false;
//
//                    selectYear = Long.valueOf(talkEditText.getText().toString());
//
//                    lifeCTextLists.add(new ModelLifeCTextList(talkEditText.getText().toString()+"년","","",1, time));
//                    lifeCTextLists.add(new ModelLifeCTextList("좋아! 잠시만 기다려줘~","","",0, time));
//
//                    talkEditText.setText("");
//                    talkEditText.setEnabled(false);
//                    sendBt.setEnabled(false);
//
//                    entries.clear();
//
//                    calculatedYield();
//
//                    lifeCTextLists.add(new ModelLifeCTextList("","","",3, tiem));
//                    lifeCTextLists.add(new ModelLifeCTextList("이제 너만을 위한 라이프 챌린지가 완성됐어!\n딱 맞는 상품이 있는데, 바로 확인해볼래?","","",0, ""));
//
//                    ((LinearLayoutManager) layoutManager).scrollToPositionWithOffset(lifeCTextLists.size()-3, -50);
//
//                    visiblePrice(false);
//
//                }
//
//                if(chartFlag){
//
//                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(talkEditText.getWindowToken(), 0);
//
//                    monthlyPrice = Long.valueOf(talkEditText.getText().toString());
//
//                    if(basicPrice+monthlyPrice >= 10){
//
//                        visiblePrice(false);
//
//                        chartFlag = false;
//
//                        Date date = new Date();
//                        String time = new SimpleDateFormat("aa hh:mm").format(date);
//
//                        addTmpChatBotList(false, talkEditText.getText().toString()+"만원","","",1,time,
//                                0, 0, 0, 0, 0, 0);
//                        addTmpChatBotList(false, "좋아! 잠시만 기다려줘~","","",0,time,
//                                0, 0, 0, 0, 0, 0);
//
//                        lifeCTextLists.add(new ModelLifeCTextList(talkEditText.getText().toString()+"만원","","",1, time));
//                        lifeCTextLists.add(new ModelLifeCTextList("좋아! 잠시만 기다려줘~","","",0, time));
//
//                        talkEditText.setText("");
//                        talkEditText.setEnabled(false);
//                        sendBt.setEnabled(false);
//
//                        entries.clear();
//
//                        calculatedYield();
//
//                        addTmpChatBotList(false, "","","",3,"", 0,
//                                0, 0, 0, 0, 0);
//                        addTmpChatBotList(false, "이제 너만을 위한 라이프 챌린지가 완성됐어!\n딱 맞는 상품이 있는데, 바로 확인해볼래?",
//                                "","",0,"", 0, 0, 0, 0, 0, 0);
//
//                        lifeCTextLists.add(new ModelLifeCTextList("","","",3, time));
//                        lifeCTextLists.add(new ModelLifeCTextList("이제 너만을 위한 라이프 챌린지가 완성됐어!\n딱 맞는 상품이 있는데, 바로 확인해볼래?","","",0, ""));
//
//                        ((LinearLayoutManager) layoutManager).scrollToPositionWithOffset(lifeCTextLists.size()-3, -50);
//
//                        insertRealmData();
//
//                    }else{
//
//                        Toast.makeText(ActivityLifeChallenge.this, "월 투자금액이 모자랍니다.",Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                if(monthlyFlag){
//
//                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(talkEditText.getWindowToken(), 0);
//
//                    Date date = new Date();
//                    String time = new SimpleDateFormat("aa hh:mm").format(date);
//
//                    monthlyFlag = false;
//
//                    basicPrice = Float.valueOf(talkEditText.getText().toString());
//
//
//                    addTmpChatBotList(false, talkEditText.getText().toString()+"만원","","",1,time,
//                            0, 0, 0, 0, 0, 0);
//
//
//                    lifeCTextLists.add(new ModelLifeCTextList(talkEditText.getText().toString()+"만원","","",1, time));
//
//                    if(basicPrice >= 10){
//
//                        addTmpChatBotList(false, "월마다 일정 금액을 추가로 투자할 계획이 있어?","*적립식 투자는 최소 10만원부터 가능합니다.","",0,time,
//                                0, 0, 0, 0, 0, 0);
//                        lifeCTextLists.add(new ModelLifeCTextList("월마다 일정 금액을 추가로 투자할 계획이 있어?", "*적립식 투자는 최소 10만원부터 가능합니다.","",0, time));
//
//                        talkEditText.setText("");
//                        talkEditText.setEnabled(false);
//                        sendBt.setEnabled(false);
//
//                        recyclerView.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
//                            }
//                        }, 30);
//
//                        visibleSelectBt3(true);
//
//                    }else{
//
//
//                        addTmpChatBotList(false, "아하… 그러면 월마다 추가로 투자를 해야겠다!\n" +
//                                "얼마씩 투자할래?","","*적립식 투자는 최소 10만원부터 가능합니다.",0,time, 0, 0, 0, 0, 0, 0);
//
//                        lifeCTextLists.add(new ModelLifeCTextList("아하… 그러면 월마다 추가로 투자를 해야겠다!\n" +
//                                "얼마씩 투자할래?","","*적립식 투자는 최소 10만원부터 가능합니다.",0, time));
//
//                        talkEditText.setText("");
//                        talkEditText.setEnabled(false);
//                        sendBt.setEnabled(false);
//
//                        recyclerView.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
//                            }
//                        }, 30);
//
//                        visiblePrice(true);
//
//                        chartFlag = true;
//
//                        talkEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
//                        talkEditText.setEnabled(true);
//                        sendBt.setEnabled(true);
//                    }
//                }
//
//                if(PriceFlag){
//
//                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(talkEditText.getWindowToken(), 0);
//
//                    finalPrice = Long.valueOf(talkEditText.getText().toString());
//
//                    if(finalPrice >= 500){
//
//                        Date date = new Date();
//                        String time = new SimpleDateFormat("aa hh:mm").format(date);
//
//                        PriceFlag = false;
//
//                        addTmpChatBotList(false, talkEditText.getText().toString()+"만원","","",1,time,
//                                0, 0, 0, 0, 0, 0);
//                        addTmpChatBotList(false, "그럼, 현재 준비되어 있는 돈이 있을까?","","",0,time,
//                                0, 0, 0, 0, 0, 0);
//
//                        lifeCTextLists.add(new ModelLifeCTextList(talkEditText.getText().toString()+"만원","","",1, time));
//                        lifeCTextLists.add(new ModelLifeCTextList("그럼, 현재 준비되어 있는 돈이 있을까?","","",0, time));
//
//                        talkEditText.setText("");
//                        talkEditText.setEnabled(false);
//                        sendBt.setEnabled(false);
//
//                        recyclerView.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
//                            }
//                        }, 30);
//
//                        visiblePrice(true);
//
//                        monthlyFlag = true;
//
//                        talkEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
//                        talkEditText.setEnabled(true);
//                        sendBt.setEnabled(true);
//
//                    }else{
//                        Toast.makeText(ActivityLifeChallenge.this, "최소 목표금액은 500만원 이상입니다.",Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                //목표금액 없이 모은금액 전부 넣을때 기간 설정
//                if(totalYearFlag){
//
//                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(talkEditText.getWindowToken(), 0);
//
//                    totalYearFlag = false;
//
//                    Date date = new Date();
//                    String time = new SimpleDateFormat("aa hh:mm").format(date);
//
//                    selectYear = (long)pickerWheel.getSelectedYear();
//
//                    lifeCTextLists.add(new ModelLifeCTextList(String.valueOf(selectYear)+"년","","",1, time));
//                    lifeCTextLists.add(new ModelLifeCTextList("좋아! 잠시만 기다려줘~","","",0, time));
//
//                    talkEditText.setText("");
//                    talkEditText.setEnabled(false);
//                    sendBt.setEnabled(false);
//
//                    entries.clear();
//
//
//                    calculatedYield3();
//
//                    lifeCTextLists.add(new ModelLifeCTextList("","","",8, time));
//                    lifeCTextLists.add(new ModelLifeCTextList("이제 너만을 위한 라이프 챌린지가 완성됐어!\n딱 맞는 상품이 있는데, 바로 확인해볼래?","","",0, ""));
//
//                    ((LinearLayoutManager) layoutManager).scrollToPositionWithOffset(lifeCTextLists.size()-3, -50);
//
//                    visiblePrice(false);
//                }
//
//
//                //목표금액 없이 모아둔돈 전부 넣을때
//                if(totalFlag){
//
//                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(talkEditText.getWindowToken(), 0);
//
//                    totalFlag = false;
//
//                    Date date = new Date();
//                    String time = new SimpleDateFormat("aa hh:mm").format(date);
//
//                    hadPrice = Long.valueOf(talkEditText.getText().toString());
//
//                    lifeCTextLists.add(new ModelLifeCTextList(talkEditText.getText().toString()+"만원","","",1, time));
//                    lifeCTextLists.add(new ModelLifeCTextList("몇 년 동안 투자하고 싶어?","","",0, time));
//                    lifeCTextLists.add(new ModelLifeCTextList("","","",9,""));
//
//                    visibleYear(true);
//
//                    talkEditText.setText("");
//                    talkEditText.setEnabled(false);
//                    sendBt.setEnabled(false);
//
//                    recyclerView.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
//                        }
//                    }, 30);
//
//                    totalYearFlag = true;
//                }
//
//
//                //목표금액 없이 월마다 넣을때 기간 설정
//                if(monYearFlag){
//
//                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(talkEditText.getWindowToken(), 0);
//
//                    Date date = new Date();
//                    String time = new SimpleDateFormat("aa hh:mm").format(date);
//
//
//                    selectYear = (long)pickerWheel.getSelectedYear();
//
//                    lifeCTextLists.add(new ModelLifeCTextList(String.valueOf(selectYear)+"년","","",1, time));
//                    lifeCTextLists.add(new ModelLifeCTextList("좋아! 잠시만 기다려줘~","","",0, time));
//
//                    talkEditText.setText("");
//                    talkEditText.setEnabled(false);
//                    sendBt.setEnabled(false);
//
//                    entries.clear();
//
//                    calculatedYield2();
//
//                    lifeCTextLists.add(new ModelLifeCTextList("","","",8, time));
//                    lifeCTextLists.add(new ModelLifeCTextList("이제 너만을 위한 라이프 챌린지가 완성됐어!\n딱 맞는 상품이 있는데, 바로 확인해볼래?","","",0, ""));
//
//                    ((LinearLayoutManager) layoutManager).scrollToPositionWithOffset(lifeCTextLists.size()-3, -50);
//
//                    visiblePrice(false);
//
//                }
//
//
//                //목표금액 없이 월마다 넣을때
//                if(monFlag){
//
//                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(talkEditText.getWindowToken(), 0);
//
//                    monFlag = false;
//
//                    Date date = new Date();
//                    String time = new SimpleDateFormat("aa hh:mm").format(date);
//
//                    monthlyPrice = Long.valueOf(talkEditText.getText().toString());
//
//                    lifeCTextLists.add(new ModelLifeCTextList(talkEditText.getText().toString()+"만원","","",1, time));
//                    lifeCTextLists.add(new ModelLifeCTextList("몇 년 동안 투자하고 싶어?","","",0, time));
//                    lifeCTextLists.add(new ModelLifeCTextList("","","",9,""));
//
//                    visibleYear(true);
//
//                    talkEditText.setText("");
//                    talkEditText.setEnabled(false);
//                    sendBt.setEnabled(false);
//
//                    recyclerView.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
//                        }
//                    }, 30);
//
//                    monYearFlag = true;
//                }
//            }
//        });
//
//
//        pickerWheelBt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if(monYearFlag){
//
//                    monYearFlag = false;
//
//                    lifeCTextLists.remove(lifeCTextLists.size()-1);
//
//                    Date date = new Date();
//                    String time = new SimpleDateFormat("aa hh:mm").format(date);
//
//                    selectYear = (long)pickerWheel.getCurrentYear();
//
//                    lifeCTextLists.add(new ModelLifeCTextList(String.valueOf(selectYear)+"년","","",1, time));
//                    lifeCTextLists.add(new ModelLifeCTextList("좋아! 잠시만 기다려줘~","","",0, time));
//
//                    talkEditText.setText("");
//                    talkEditText.setEnabled(false);
//                    sendBt.setEnabled(false);
//
//                    entries.clear();
//
//                    calculatedYield2();
//
//                    lifeCTextLists.add(new ModelLifeCTextList("","","",8, time));
//                    lifeCTextLists.add(new ModelLifeCTextList("이제 너만을 위한 라이프 챌린지가 완성됐어!\n딱 맞는 상품이 있는데, 바로 확인해볼래?","","",0, ""));
//
//                    ((LinearLayoutManager) layoutManager).scrollToPositionWithOffset(lifeCTextLists.size()-3, -50);
//
//                    visibleYear(false);
//                    visiblePrice(false);
//
//                }else{
//
//                    lifeCTextLists.remove(lifeCTextLists.size()-1);
//
//                    Date date = new Date();
//                    String time = new SimpleDateFormat("aa hh:mm").format(date);
//
//                    selectYear = (long)pickerWheel.getCurrentYear();
//
//                    lifeCTextLists.add(new ModelLifeCTextList(String.valueOf(selectYear)+"년","","",1, time));
//                    lifeCTextLists.add(new ModelLifeCTextList("좋아! 잠시만 기다려줘~","","",0, time));
//
//                    talkEditText.setText("");
//                    talkEditText.setEnabled(false);
//                    sendBt.setEnabled(false);
//
//                    entries.clear();
//
//                    calculatedYield3();
//
//                    lifeCTextLists.add(new ModelLifeCTextList("","","",8, time));
//                    lifeCTextLists.add(new ModelLifeCTextList("이제 너만을 위한 라이프 챌린지가 완성됐어!\n딱 맞는 상품이 있는데, 바로 확인해볼래?","","",0, ""));
//
//                    ((LinearLayoutManager) layoutManager).scrollToPositionWithOffset(lifeCTextLists.size()-3, -50);
//
//                    visibleYear(false);
//                    visiblePrice(false);
//
//                }
//
//            }
//        });
//
//        yesBt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                visibleSelectBt(false);
//
//                Date date = new Date();
//                String time = new SimpleDateFormat("aa hh:mm").format(date);
//
//                addTmpChatBotList(false, "응, 있지!","","",1,time, 0, 0, 0, 0, 0, 0);
//                addTmpChatBotList(false, "와, 궁금하다! 얼마인지 알려줄 수 있어?","","",0,time, 0, 0, 0, 0, 0, 0);
//
//                lifeCTextLists.add(new ModelLifeCTextList("응, 있지!","","",1, time));
//                lifeCTextLists.add(new ModelLifeCTextList("와, 궁금하다! 얼마인지 알려줄 수 있어?","","",0, time));
//
//                recyclerView.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
//                    }
//                }, 30);
//
//                visiblePrice(true);
//
//                talkEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
//                talkEditText.setEnabled(true);
//                sendBt.setEnabled(true);
//
//                PriceFlag = true;
//
//            }
//        });
//
//        noBt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                visibleSelectBt4(true);
//
//                Date date = new Date();
//                String time = new SimpleDateFormat("aa hh:mm").format(date);
//
//                lifeCTextLists.add(new ModelLifeCTextList("아니, 없어","","",1, time));
//                lifeCTextLists.add(new ModelLifeCTextList("상관 없어. 지금부터 시작하면 돼!","","",0, time));
//                recyclerView.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
//                    }
//                });
//
//                lifeCTextLists.add(new ModelLifeCTextList("월 마다 조금씩 모으고 싶어?\n아니면 한 번에 모아둔 돈으로 투자해볼까?","","",0, time));
//                adapterLifeChallenge.notifyDataSetChanged();
//                recyclerView.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
//                    }
//                });
//            }
//        });
//
//        //목표금액 있고 기본금액 잇고 월마다 넣음
//        yesBt3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                visibleSelectBt3(false);
//
//                Date date = new Date();
//                String time = new SimpleDateFormat("aa hh:mm").format(date);
//
//                addTmpChatBotList(false, "응, 있지!","","",1,time, 0, 0, 0, 0, 0, 0);
//                addTmpChatBotList(false, "월에 얼마씩 넣을꺼야?","","",0,time, 0, 0, 0, 0, 0, 0);
//
//                lifeCTextLists.add(new ModelLifeCTextList("응, 있지!","","",1, time));
//                lifeCTextLists.add(new ModelLifeCTextList("월에 얼마씩 넣을꺼야?","","",0, time));
//
//                talkEditText.setText("");
//                talkEditText.setEnabled(false);
//                sendBt.setEnabled(false);
//
//                adapterLifeChallenge.notifyDataSetChanged();
//                recyclerView.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
//                        visiblePrice(true);
//                    }
//                }, 30);
//
//                chartFlag = true;
//
//                talkEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
//                talkEditText.setEnabled(true);
//                sendBt.setEnabled(true);
//
//            }
//        });
//
//        //목표금액 있고 기본금액 있고 월마다는 안넣음
//        noBt3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                visibleSelectBt3(false);
//
//                Date date = new Date();
//                String time = new SimpleDateFormat("aa hh:mm").format(date);
//
//                addTmpChatBotList(false,"아니, 없어","","",1,time, 0, 0, 0, 0, 0, 0);
//
//                lifeCTextLists.add(new ModelLifeCTextList("아니, 없어","","",1, time));
//
//                chartFlag = false;
//                monthlyPrice = 0L;
//
//                addTmpChatBotList(false, "좋아! 잠시만 기다려줘~","","",0,time, 0, 0, 0, 0, 0, 0);
//
//                lifeCTextLists.add(new ModelLifeCTextList("좋아! 잠시만 기다려줘~","","",0, time));
//
//                ((LinearLayoutManager) layoutManager).scrollToPositionWithOffset(lifeCTextLists.size()-3, -500);
//
//                talkEditText.setText("");
//                talkEditText.setEnabled(false);
//                sendBt.setEnabled(false);
//
//                entries.clear();
//
//                calculatedYield();
//                visiblePrice(false);
//
//                lifeCTextLists.add(new ModelLifeCTextList("","","",3, time));
//                lifeCTextLists.add(new ModelLifeCTextList("이제 너만을 위한 라이프 챌린지가 완성됐어!\n딱 맞는 상품이 있는데, 바로 확인해볼래?","","",0, ""));
//
//            }
//        });
//
//        //목표 금액 없이 월마다 투자
//        monthBt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Date date = new Date();
//                String time = new SimpleDateFormat("aa hh:mm").format(date);
//                lifeCTextLists.add(new ModelLifeCTextList("월 마다!","","",1, time));
//
//                lifeCTextLists.add(new ModelLifeCTextList("월에 얼마씩 넣을꺼야?","","",0, time));
//
//                talkEditText.setText("");
//                talkEditText.setEnabled(false);
//                sendBt.setEnabled(false);
//
//                recyclerView.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
//                    }
//                }, 30);
//
//                visibleSelectBt4(false);
//
//                monFlag = true;
//
//                talkEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
//                talkEditText.setEnabled(true);
//                sendBt.setEnabled(true);
//
//            }
//        });
//
//
//        //목표 금액 없이 모아둔 돈으로 투자
//        allBt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Date date = new Date();
//                String time = new SimpleDateFormat("aa hh:mm").format(date);
//                lifeCTextLists.add(new ModelLifeCTextList("모아둔 돈으로 한번에!","","",1, time));
//                lifeCTextLists.add(new ModelLifeCTextList("얼마나 넣을 생각이야?","","",0, time));
//
//                talkEditText.setText("");
//                talkEditText.setEnabled(false);
//                sendBt.setEnabled(false);
//
//                recyclerView.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
//                    }
//                }, 30);
//
//                visibleSelectBt4(false);
//
//                totalFlag = true;
//
//                talkEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
//                talkEditText.setEnabled(true);
//                sendBt.setEnabled(true);
//
//            }
//        });
//
//
//        //다시 시작하기
//        retryBt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                realm.beginTransaction();
//                RealmResults<ChatBot> results = realm.where(ChatBot.class)
//                        .findAll();
//
//                results.deleteAllFromRealm();
//                realm.commitTransaction();
//
//                Intent intent = new Intent(ActivityLifeChallenge.this, ActivityLifeChallenge.class);
//                startActivity(intent);
//                finish();
//
//            }
//        });
//
//        //가입하기
//        joinBt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//        botInfo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialogChatbotInfo = new DialogChatbotInfo(ActivityLifeChallenge.this, infoCloseBt);
//                dialogChatbotInfo.show();
//            }
//        });
//
//    }// onCreate 끝
//
//
//    //챗봇 저장
//    private void insertRealmData(){
//
//        for(TmpChatBotTalkList tmpChatBotTalkList : tmpChatBotTalkLists){
//
//            ChatBotTalkList chatBotTalkList = realm.createObject(ChatBotTalkList.class);
//            chatBotTalkList.setTalk(tmpChatBotTalkList.getTalk());
//            chatBotTalkList.setSubTitle(tmpChatBotTalkList.getSubTitle());
//            chatBotTalkList.setLongSubTitle(tmpChatBotTalkList.getLongSubTitle());
//            chatBotTalkList.setCategory(tmpChatBotTalkList.getCategory());
//            chatBotTalkList.setTime(tmpChatBotTalkList.getTime());
//            chatBot.getChatBotTalkLists().add(chatBotTalkList);
//
//        }
//
//        realm.commitTransaction();
//    }
//
//    //챗봇 대화 임시 저장
//    private void addTmpChatBotList(boolean endState, String talk, String subTitle,
//                                   String longSubTitle, int category, String time,
//                                   int chartState, long basicPrice,
//                                   long monthlyPrice, long selectYear,
//                                   long hadPrice, long finalPrice){
//
//
//            tmpChatBotTalkLists.add(new TmpChatBotTalkList(talk, time, subTitle,longSubTitle, category));
//
//    }
//
//
//
//    //chatbot 정보 보기
//    private View.OnClickListener infoCloseBt = new View.OnClickListener() {
//        public void onClick(View v) {
//            dialogChatbotInfo.dismiss();
//        }
//    };
//
//
//    //뒤로가기 챗봇종료 확인 버튼
//    private View.OnClickListener backOkListener = new View.OnClickListener() {
//        public void onClick(View v) {
//            dialogClosedChatBot.dismiss();
//            finish();
//        }
//    };
//    //뒤로가기 챗봇종료 취소 버튼
//    private View.OnClickListener backCancleListener = new View.OnClickListener() {
//        public void onClick(View v) {
//            dialogClosedChatBot.dismiss();
//        }
//    };
//
//
//    private void visibleSelectBt(boolean visible){
//        if(visible){
//
//            talkEditText2.setVisibility(View.GONE);
//            sendBt.setVisibility(View.GONE);
//            yesBt.setVisibility(View.VISIBLE);
//            noBt.setVisibility(View.VISIBLE);
//
//        }else{
//            talkEditText.setVisibility(View.VISIBLE);
//            sendBt.setVisibility(View.VISIBLE);
//            yesBt.setVisibility(View.GONE);
//            noBt.setVisibility(View.GONE);
//        }
//    }
//
//    private void visibleSelectBt3(boolean visible){
//        if(visible){
//            talkEditText.setVisibility(View.GONE);
//            sendBt.setVisibility(View.GONE);
//            yesBt3.setVisibility(View.VISIBLE);
//            noBt3.setVisibility(View.VISIBLE);
//        }else{
//
//            talkEditText.setVisibility(View.VISIBLE);
//            sendBt.setVisibility(View.VISIBLE);
//            yesBt3.setVisibility(View.GONE);
//            noBt3.setVisibility(View.GONE);
//        }
//    }
//
//    private void visibleSelectBt4(boolean visible){
//        if(visible){
//            talkEditText.setVisibility(View.GONE);
//            sendBt.setVisibility(View.GONE);
//            yesBt.setVisibility(View.GONE);
//            noBt.setVisibility(View.GONE);
//            monthBt.setVisibility(View.VISIBLE);
//            allBt.setVisibility(View.VISIBLE);
//        }else{
//            talkEditText.setVisibility(View.VISIBLE);
//            sendBt.setVisibility(View.VISIBLE);
//            monthBt.setVisibility(View.GONE);
//            allBt.setVisibility(View.GONE);
//        }
//    }
//
//    private void visiblePrice(boolean visible){
//        if(visible){
//
//        }else{
//
//            talkEditText.setVisibility(View.GONE);
//            sendBt.setVisibility(View.GONE);
//            retryBt.setVisibility(View.VISIBLE);
//            joinBt.setVisibility(View.VISIBLE);
//        }
//    }
//
//    private void visibleYear(boolean visible){
//        if(visible){
//            pickerWheel.setVisibility(View.VISIBLE);
//            pickerWheelBt.setEnabled(true);
//            pickerWheelBt.setVisibility(View.VISIBLE);
//        }else{
//            pickerWheel.setVisibility(View.INVISIBLE);
//            pickerWheelBt.setEnabled(false);
//            pickerWheelBt.setVisibility(View.INVISIBLE);
//        }
//    }
//
//    private void realmVisible(){
//        talkEditText2.setVisibility(View.GONE);
//        talkEditText.setVisibility(View.GONE);
//        sendBt.setVisibility(View.GONE);
//        retryBt.setVisibility(View.VISIBLE);
//        joinBt.setVisibility(View.VISIBLE);
//    }
//
//
//    private void realmCalYield(float basicPrice, long monthlyPrice, long finalPrice) {
//
//        finishedChart = true;
//
//        entries.add(new Entry(0, (basicPrice*10000), (basicPrice*10000)));
//
//        float price = (basicPrice + (monthlyPrice * 12)) * 1.1f;
//
//        entries.add(new Entry(1, price*10000, price*10000));
//
//        calPotYear = 1;
//        calTotalPrice = (long) (basicPrice + monthlyPrice);
//        calYield = (long) (price - calTotalPrice);
//
//        if (price < finalPrice) {
//
//            for (int yield = 2; yield < 10000; yield++) {
//
//                price = (price + (monthlyPrice * 12)) * 1.1f;
//                entries.add(new Entry(yield, price*10000, price*10000));
//
//                if (price >= finalPrice) {
//
//                    calPotYear = yield;
//                    calTotalPrice = (long) (basicPrice + (monthlyPrice * 12 * yield));
//                    calYield = (long) (price - calTotalPrice);
//                    break;
//                }
//            }
//        }
//
//        entries2.add(new Entry(0, basicPrice*10000, basicPrice*10000));
//        float price2 = (basicPrice + (monthlyPrice * 12)) * 1.03f;
//        entries2.add(new Entry(1, price2*10000, price2*10000));
//
//        calNormalYear = 1;
//
//        if(price2 < finalPrice){
//
//            for (int yield2 = 2; yield2 < 1000; yield2++) {
//                price2 = (price2 + (monthlyPrice * 12)) * 1.03f;
//                entries2.add(new Entry(yield2, price2*10000, price2*10000));
//
//                if (price2 >= finalPrice) {
//
//                    calNormalYear = yield2;
//                    break;
//                }
//            }
//        }
//        chartInfoLsits.add(new ModelChartInfoLsit(String.valueOf(calTotalPrice*10000), String.valueOf(calYield*10000), String.valueOf(calPotYear), String.valueOf(calNormalYear), "", ""));
//    }
//
//
//
//    private void calculatedYield() {
//
//        chatBot.setBasicPrice(basicPrice);
//        chatBot.setFinalPrice(finalPrice);
//        chatBot.setMonthlyPrice(monthlyPrice);
//
//        finishedChart = true;
//
//        entries.add(new Entry(0, (basicPrice*10000), (basicPrice*10000)));
//
//
////        ChatBotChart chatBotChart = realm.createObject(ChatBotChart.class);
////        chatBotChart.setYear(0);
////        chatBotChart.setTotalPrice(basicPrice*10000);
////        chatBot.getChatBotCharts().add(chatBotChart);
//
//
//        float price = (basicPrice + (monthlyPrice * 12)) * 1.1f;
//
//        entries.add(new Entry(1, price*10000, price*10000));
//
////        ChatBotChart chatBotChart2 = realm.createObject(ChatBotChart.class);
////        chatBotChart2.setYear(1);
////        chatBotChart2.setTotalPrice(price*10000);
////        chatBot.getChatBotCharts().add(chatBotChart2);
//
//        calPotYear = 1;
//        calTotalPrice = (long) (basicPrice + monthlyPrice);
//        calYield = (long) (price - calTotalPrice);
//
//        if (price < finalPrice) {
//
//            for (int yield = 2; yield < 10000; yield++) {
//
//                price = (price + (monthlyPrice * 12)) * 1.1f;
//                entries.add(new Entry(yield, price*10000, price*10000));
//
////                ChatBotChart chatBotChart3 = realm.createObject(ChatBotChart.class);
////                chatBotChart3.setYear(yield);
////                chatBotChart3.setTotalPrice(price*10000);
////                chatBot.getChatBotCharts().add(chatBotChart3);
//
//                if (price >= finalPrice) {
//
//                    calPotYear = yield;
//                    calTotalPrice = (long) (basicPrice + (monthlyPrice * 12 * yield));
//                    calYield = (long) (price - calTotalPrice);
//                    break;
//                }
//            }
//        }
//
//        entries2.add(new Entry(0, basicPrice*10000, basicPrice*10000));
//        float price2 = (basicPrice + (monthlyPrice * 12)) * 1.03f;
//        entries2.add(new Entry(1, price2*10000, price2*10000));
//
//        calNormalYear = 1;
//
//        if(price2 < finalPrice){
//
//            for (int yield2 = 2; yield2 < 1000; yield2++) {
//                price2 = (price2 + (monthlyPrice * 12)) * 1.03f;
//                entries2.add(new Entry(yield2, price2*10000, price2*10000));
//
//                if (price2 >= finalPrice) {
//
//                    calNormalYear = yield2;
//                    break;
//                }
//            }
//        }
//        chartInfoLsits.add(new ModelChartInfoLsit(String.valueOf(calTotalPrice*10000), String.valueOf(calYield*10000), String.valueOf(calPotYear), String.valueOf(calNormalYear), "", ""));
//
////        chatBot.setBasicPrice((long)basicPrice);
////        chatBot.setFinalPrice(monthlyPrice);
////        chatBot.setMonthlyPrice(finalPrice);
//
////        chatBot.setTitle("우리집 마련하기");
////        chatBot.setPrice1(String.valueOf(calTotalPrice*10000));
////        chatBot.setPrice2(String.valueOf(calYield*10000));
////        realm.commitTransaction();
//
////        RealmResults<ChatBot> results = realm.where(ChatBot.class)
////                .findAllAsync();
////
////        results.addChangeListener(new RealmChangeListener<RealmResults<ChatBot>>() {
////            @Override
////            public void onChange(RealmResults<ChatBot> chatBots) {
////                Log.e("목표", "값 : "+chatBots.get(0).getTitle());
////                Log.e("총투입", "값 : "+chatBots.get(0).getPrice1());
////                Log.e("총수익", "값 : "+chatBots.get(0).getPrice2());
////
////                for(int a = 0 ; a < chatBots.get(0).getChatBotCharts().size() ; a++){
////                    Log.e("년도", "값 : "+chatBots.get(0).getChatBotCharts().get(a).getYear());
////                    Log.e("금액", "값 : "+chatBots.get(0).getChatBotCharts().get(a).getTotalPrice());
////                }
////            }
////        });
//
//
////        addTmpChatBotList(true, "","","",0,"", 0, (long)basicPrice, monthlyPrice, 0, 0, finalPrice);
//
//
//
//    }
//
//
////    private void calculatedYield() {
////
////        realm = Realm.getDefaultInstance();
////        realm.beginTransaction();
////        ChatBot chatBot = realm.createObject(ChatBot.class);
////        ChatBotChart chatBotChart = realm.createObject(ChatBotChart.class);
////
////
////        finishedChart = true;
////
////        entries.add(new Entry(0, (basicPrice*10000), (basicPrice*10000)));
////
////        float price = (basicPrice + (monthlyPrice * 12)) * 1.1f;
////
////        entries.add(new Entry(1, price*10000, price*10000));
////
////        calPotYear = 1;
////        calTotalPrice = (long) (basicPrice + monthlyPrice);
////        calYield = (long) (price - calTotalPrice);
////
////        if (price < finalPrice) {
////
////            for (int yield = 2; yield < 10000; yield++) {
////
////                price = (price + (monthlyPrice * 12)) * 1.1f;
////                entries.add(new Entry(yield, price*10000, price*10000));
////
////                if (price >= finalPrice) {
////
////                    calPotYear = yield;
////                    calTotalPrice = (long) (basicPrice + (monthlyPrice * 12 * yield));
////                    calYield = (long) (price - calTotalPrice);
////                    break;
////                }
////            }
////        }
////
////        entries2.add(new Entry(0, basicPrice*10000, basicPrice*10000));
////        float price2 = (basicPrice + (monthlyPrice * 12)) * 1.03f;
////        entries2.add(new Entry(1, price2*10000, price2*10000));
////
////        calNormalYear = 1;
////
////        if(price2 < finalPrice){
////
////            for (int yield2 = 2; yield2 < 1000; yield2++) {
////                price2 = (price2 + (monthlyPrice * 12)) * 1.03f;
////                entries2.add(new Entry(yield2, price2*10000, price2*10000));
////
////                if (price2 >= finalPrice) {
////
////                    calNormalYear = yield2;
////                    break;
////                }
////            }
////        }
////        chartInfoLsits.add(new ModelChartInfoLsit(String.valueOf(calTotalPrice*10000), String.valueOf(calYield*10000), String.valueOf(calPotYear), String.valueOf(calNormalYear), "", ""));
////
////
////        chatBot.setTitle("우리집 마련하기");
////        chatBot.setPrice1(String.valueOf(calTotalPrice*10000));
////        chatBot.setPrice2(String.valueOf(calYield*10000));
////        realm.commitTransaction();
////
//////        RealmResults<ChatBot> results = realm.where(ChatBot.class)
//////                .findAllAsync();
//////
//////        results.addChangeListener(new RealmChangeListener<RealmResults<ChatBot>>() {
//////            @Override
//////            public void onChange(RealmResults<ChatBot> chatBots) {
//////                Log.e("목표", "값 : "+chatBots.get(0).getTitle());
//////                Log.e("총투입", "값 : "+chatBots.get(0).getPrice1());
//////                Log.e("총수익", "값 : "+chatBots.get(0).getPrice2());
//////
//////            }
//////        });
////
////    }
//
//    private void calculatedYield2(){
//
//        finishedChart = true;
//
//        entries.add(new Entry(0, 0, 0));
//        float price = (monthlyPrice*12)*1.1f;
//        entries.add(new Entry(1, price*10000, price*10000));
//
//        for(int yield = 2 ; yield <= selectYear ; yield++){
//            price = (price+(monthlyPrice*12))*1.1f;
//            entries.add(new Entry(yield, price*10000, price*10000));
//        }
//
//        calTotalPrice = (long)(monthlyPrice*12*selectYear);
//        calYield = (long)(price - calTotalPrice);
//
//        entries2.add(new Entry(0, 0, 0));
//        float price2 = (monthlyPrice*12)*1.03f;
//        entries2.add(new Entry(1, price2*10000, price2*10000));
//
//        for(int yield2 = 2; yield2 <= selectYear ; yield2++){
//            price2 = (price2+(monthlyPrice*12))*1.03f;
//            entries2.add(new Entry(yield2, price2*10000, price2*10000));
//        }
//        chartInfoLsits.add(new ModelChartInfoLsit(String.valueOf(calTotalPrice*10000),String.valueOf(calYield*10000),"","",String.valueOf((long)price*10000), String.valueOf((long)price2*10000)));
//    }
//
//
//    private void calculatedYield3(){
//
//        finishedChart = true;
//
//        entries.add(new Entry(0, hadPrice, hadPrice));
//        float price = hadPrice*1.1f;
//        entries.add(new Entry(1, price*10000, price*10000));
//
//        for(int yield = 2 ; yield <= selectYear ; yield++){
//            price = price*1.1f;
//            entries.add(new Entry(yield, price*10000, price*10000));
//        }
//
//        calTotalPrice = (long)(hadPrice*selectYear);
//        calYield = (long)(price - calTotalPrice);
//
//        entries2.add(new Entry(0, hadPrice, hadPrice));
//        float price2 = hadPrice*1.03f;
//        entries2.add(new Entry(1, price2*10000, price2*10000));
//
//        for(int yield2 = 2; yield2 <= selectYear ; yield2++){
//            price2 = price2*1.03f;
//            entries2.add(new Entry(yield2, price2*10000, price2*10000));
//        }
//        chartInfoLsits.add(new ModelChartInfoLsit(String.valueOf(calTotalPrice*10000),String.valueOf(calYield*10000),"","",String.valueOf((long)price*10000), String.valueOf((long)price2*10000)));
//    }
//
//    private KeyPressEditText.OnPressListener onBackPressListener = new KeyPressEditText.OnPressListener() {
//        @Override
//        public void onPress() {
//        }
//    };
//
//    private KeyPressEditText.OnPressDoneListener onPressDoneListener = new KeyPressEditText.OnPressDoneListener() {
//        @Override
//        public void onPressDone() {
//        }
//    };
//
//
//    @Override
//    public void onBackPressed() {
//        if(finishedChart){
//            finish();
//        }
//        else{
//            dialogClosedChatBot = new DialogClosedChatBot(ActivityLifeChallenge.this, backCancleListener, backOkListener);
//            dialogClosedChatBot.show();
//        }
//    }
//}
package quantec.com.moneypot.Activity.ActivityLifeChallenge;

import android.content.Context;
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
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Delayed;
import java.util.concurrent.RunnableScheduledFuture;

import quantec.com.moneypot.R;
import quantec.com.moneypot.Util.SoftKeyboardUtil.KeyPressEditText;

public class ActivityLifeChallenge extends AppCompatActivity{

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ModelLifeCTextList> lifeCTextLists;
    AdapterLifeChallenge adapterLifeChallenge;
    ArrayList<ModelLifeCSelectList> lifeCSelectLists;
    TextView sendBt;
    KeyPressEditText talkEditText, talkEditText2;

    ArrayList<ModelChartInfoLsit> chartInfoLsits;

    boolean TextFlag = false;
    boolean PriceFlag = false;
    boolean yearFlag = false;
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
    TextView pickerWheelBt, talkEditTextUnit;

    View bottomLayout;
    Handler handler = new Handler();

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

        talkEditTextUnit = findViewById(R.id.talkEditTextUnit);

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


        lifeCTextLists.add(new ModelLifeCTextList("안녕? 나는 챗봇이야. 만나서 반가워!",0, ""));
        adapterLifeChallenge.notifyDataSetChanged();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                lifeCTextLists.add(new ModelLifeCTextList("너의 목표를 이루어 줄 수 있도록 도와줄게!",0, ""));

                adapterLifeChallenge.notifyDataSetChanged();

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        lifeCTextLists.add(new ModelLifeCTextList("제일 먼저, 왜 투자가 하고 싶어?",2, tiem));

                        lifeCSelectLists.add(new ModelLifeCSelectList("내집마련",0));
                        lifeCSelectLists.add(new ModelLifeCSelectList("빌딩사기",1));
                        lifeCSelectLists.add(new ModelLifeCSelectList("땅사기",2));
                        lifeCSelectLists.add(new ModelLifeCSelectList("직접입력",3));

                        adapterLifeChallenge.notifyDataSetChanged();
                    }
                }, 700);

            }
        }, 700);


        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
            }
        }, 100);

        adapterLifeChallenge.setBotSelectClick(new AdapterLifeChallenge.BotSelectClick() {
            @Override
            public void onClick(int category) {

                Date date = new Date();
                String time = new SimpleDateFormat("aa hh:mm").format(date);

                if(category == 0){

//                    visibleDestinyTitle(false);
//
//                    lifeCTextLists.add(new ModelLifeCTextList("내집마련",1, time));
//                    lifeCTextLists.add(new ModelLifeCTextList("멋지다!! 정해 놓은 목표금액이 있어?",5 ,time));
//
//                    adapterLifeChallenge.notifyItemChanged(lifeCTextLists.size()-1);
//
//                    recyclerView.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
//                        }
//                    }, 100);

                    visibleDestinyTitle(false);

                    lifeCTextLists.add(new ModelLifeCTextList("내집마련",1, time));

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                                    lifeCTextLists.add(new ModelLifeCTextList("멋지다!! 정해 놓은 목표금액이 있어?",5 ,time));
                                    adapterLifeChallenge.notifyItemChanged(lifeCTextLists.size()-1);

                                    recyclerView.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
                                        }
                                    }, 100);


                        }
                    }, 700);


                }
                else if(category == 1){

                    visibleDestinyTitle(false);

                    lifeCTextLists.add(new ModelLifeCTextList("빌딩사기",1, time));
                    lifeCTextLists.add(new ModelLifeCTextList("멋지다!! 정해 놓은 목표금액이 있어?",5 ,time));

                    adapterLifeChallenge.notifyItemChanged(lifeCTextLists.size()-1);

                    recyclerView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
                        }
                    }, 100);
                }
                else if(category == 2){

                    visibleDestinyTitle(false);

                    lifeCTextLists.add(new ModelLifeCTextList("땅사기",1, time));
                    lifeCTextLists.add(new ModelLifeCTextList("멋지다!! 정해 놓은 목표금액이 있어?",5 ,time));

                    adapterLifeChallenge.notifyItemChanged(lifeCTextLists.size()-1);

                    recyclerView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
                        }
                    }, 100);
                }
                else if(category == 3){

                    visibleDestinyTitle(true);

                    TextFlag = true;

                    talkEditText2.setEnabled(true);
                    sendBt.setEnabled(true);

                }
            }
        });

        talkEditText.setOnPressListener(onBackPressListener);
        talkEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
                    }
                }, 100);
            }
        });
        talkEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
                    }
                }, 100);
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
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
                    }
                }, 100);
            }
        });
        talkEditText2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
                    }
                }, 100);
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

                    visibleDestinyTitle(false);

                    Date date = new Date();
                    String time = new SimpleDateFormat("aa hh:mm").format(date);

                    TextFlag = false;

                    lifeCTextLists.add(new ModelLifeCTextList(talkEditText2.getText().toString(),1, tiem));
                    lifeCTextLists.add(new ModelLifeCTextList("멋지다!! 정해 놓은 목표금액이 있어?",5, time));

                    talkEditText2.setText("");
                    talkEditText2.setEnabled(false);
                    sendBt.setEnabled(false);

                    adapterLifeChallenge.notifyItemChanged(lifeCTextLists.size()-1);
                    recyclerView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
                        }
                    }, 100);

                }

                if(yearFlag){

                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(talkEditText.getWindowToken(), 0);

                    Date date = new Date();
                    String time = new SimpleDateFormat("aa hh:mm").format(date);

                    yearFlag = false;

                    selectYear = Long.valueOf(talkEditText.getText().toString());

                    lifeCTextLists.add(new ModelLifeCTextList(talkEditText.getText().toString()+"년",1, time));
                    lifeCTextLists.add(new ModelLifeCTextList("좋아! 이제 너만을 위한 라이프 챌린지가 완성됐어!!",0, time));

                    talkEditText.setText("");
                    talkEditText.setEnabled(false);
                    sendBt.setEnabled(false);

                    entries.clear();

                    calculatedYield();

                    lifeCTextLists.add(new ModelLifeCTextList("",3, tiem));
                    lifeCTextLists.add(new ModelLifeCTextList("너만을 위한 상품이 준비되어 있어!\n바로 확인해볼래?",0, ""));
                    lifeCTextLists.add(new ModelLifeCTextList("",4, ""));

                    adapterLifeChallenge.notifyItemChanged(lifeCTextLists.size()-1);
                    recyclerView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
                        }
                    }, 100);

                }

                if(chartFlag){


                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(talkEditText.getWindowToken(), 0);

                    visiblePrice(true);

                    monthlyPrice = Long.valueOf(talkEditText.getText().toString());

                    if(basicPrice+monthlyPrice >= 10){
                        chartFlag = false;

                        Date date = new Date();
                        String time = new SimpleDateFormat("aa hh:mm").format(date);

                        lifeCTextLists.add(new ModelLifeCTextList(talkEditText.getText().toString()+"만원",1, time));
                        lifeCTextLists.add(new ModelLifeCTextList("좋아! 이제 너만을 위한 라이프 챌린지가 완성됐어!!",0, time));

                        visiblePrice(false);

                        talkEditText.setText("");
                        talkEditText.setEnabled(false);
                        sendBt.setEnabled(false);

                        entries.clear();

                        calculatedYield();

                        lifeCTextLists.add(new ModelLifeCTextList("",3, time));
                        lifeCTextLists.add(new ModelLifeCTextList("너만을 위한 상품이 준비되어 있어!\n바로 확인해볼래?",0, ""));
                        lifeCTextLists.add(new ModelLifeCTextList("",4, ""));

                        adapterLifeChallenge.notifyItemChanged(lifeCTextLists.size()-1);
                        recyclerView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
                            }
                        }, 100);
                    }else{

                        Toast.makeText(ActivityLifeChallenge.this, "월 투자금액이 모자랍니다.",Toast.LENGTH_SHORT).show();

                    }

                }

                if(monthlyFlag){

                    visiblePrice(false);

                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(talkEditText.getWindowToken(), 0);

                    Date date = new Date();
                    String time = new SimpleDateFormat("aa hh:mm").format(date);

                    monthlyFlag = false;

                    basicPrice = Float.valueOf(talkEditText.getText().toString());

                    lifeCTextLists.add(new ModelLifeCTextList(talkEditText.getText().toString()+"만원",1, time));

                    if(basicPrice >= 10){

                        lifeCTextLists.add(new ModelLifeCTextList("월마다 일정 금액을 추가로 투자할 계획이 있어?", 7, time));

                        talkEditText.setText("");
                        talkEditText.setEnabled(false);
                        sendBt.setEnabled(false);

                        adapterLifeChallenge.notifyItemChanged(lifeCTextLists.size()-1);
                        recyclerView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
                            }
                        }, 100);

                    }else{

                        visiblePrice(true);

                        lifeCTextLists.add(new ModelLifeCTextList("월에 얼마씩 넣을꺼야?",0, time));

                        talkEditText.setText("");
                        talkEditText.setEnabled(false);
                        sendBt.setEnabled(false);

                        adapterLifeChallenge.notifyItemChanged(lifeCTextLists.size()-1);
                        recyclerView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
                            }
                        }, 100);

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

                        visiblePrice(true);

                        Date date = new Date();
                        String time = new SimpleDateFormat("aa hh:mm").format(date);

                        PriceFlag = false;

                        lifeCTextLists.add(new ModelLifeCTextList(talkEditText.getText().toString()+"만원",1, time));
                        lifeCTextLists.add(new ModelLifeCTextList("초기 자본금은 얼마를 원해?",0, time));

                        talkEditText.setText("");
                        talkEditText.setEnabled(false);
                        sendBt.setEnabled(false);

                        adapterLifeChallenge.notifyItemChanged(lifeCTextLists.size()-1);
                        recyclerView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
                            }
                        }, 100);

                        monthlyFlag = true;

                        talkEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
                        talkEditText.setEnabled(true);
                        sendBt.setEnabled(true);

                    }else{
                        Toast.makeText(ActivityLifeChallenge.this, "최소 목표금액은 500만원 이상입니다.",Toast.LENGTH_SHORT).show();
                    }

                }


                //목표금액 없이 모은금액 전부 넣을때 기간 설정
                if(totalYearFlag){

                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(talkEditText.getWindowToken(), 0);

                    totalYearFlag = false;

                    Date date = new Date();
                    String time = new SimpleDateFormat("aa hh:mm").format(date);

                    selectYear = (long)pickerWheel.getSelectedYear();

                    lifeCTextLists.add(new ModelLifeCTextList(String.valueOf(selectYear)+"년",1, time));
                    lifeCTextLists.add(new ModelLifeCTextList("좋아! 이제 너만을 위한 라이프 챌린지가 완성됐어!!",0, time));

                    talkEditText.setText("");
                    talkEditText.setEnabled(false);
                    sendBt.setEnabled(false);

                    entries.clear();

                    calculatedYield3();

                    lifeCTextLists.add(new ModelLifeCTextList("",8, time));
                    lifeCTextLists.add(new ModelLifeCTextList("너만을 위한 상품이 준비되어 있어!\n바로 확인해볼래?",0, ""));
                    lifeCTextLists.add(new ModelLifeCTextList("",4, ""));

                    adapterLifeChallenge.notifyItemChanged(lifeCTextLists.size()-1);
                    recyclerView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
                        }
                    }, 100);

                }


                //목표금액 없이 모아둔돈 전부 넣을때
                if(totalFlag){

                    visiblePrice(false);

                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(talkEditText.getWindowToken(), 0);

                    totalFlag = false;

                    Date date = new Date();
                    String time = new SimpleDateFormat("aa hh:mm").format(date);

                    hadPrice = Long.valueOf(talkEditText.getText().toString());

                    lifeCTextLists.add(new ModelLifeCTextList(talkEditText.getText().toString()+"만원",1, time));
                    lifeCTextLists.add(new ModelLifeCTextList("몇 년 동안 투자하고 싶어?",0, time));

                    lifeCTextLists.add(new ModelLifeCTextList("",9,""));

                    visibleYear(true);

                    talkEditText.setText("");
                    talkEditText.setEnabled(false);
                    sendBt.setEnabled(false);

                    adapterLifeChallenge.notifyItemChanged(lifeCTextLists.size()-1);
                    recyclerView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
                        }
                    }, 100);

                    totalYearFlag = true;
                }


                //목표금액 없이 월마다 넣을때 기간 설정
                if(monYearFlag){

                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(talkEditText.getWindowToken(), 0);

//                    monYearFlag = false;

                    Date date = new Date();
                    String time = new SimpleDateFormat("aa hh:mm").format(date);


                    selectYear = (long)pickerWheel.getSelectedYear();

                    lifeCTextLists.add(new ModelLifeCTextList(String.valueOf(selectYear)+"년",1, time));
                    lifeCTextLists.add(new ModelLifeCTextList("좋아! 이제 너만을 위한 라이프 챌린지가 완성됐어!!",0, time));

                    talkEditText.setText("");
                    talkEditText.setEnabled(false);
                    sendBt.setEnabled(false);

                    entries.clear();

                    calculatedYield2();

                    lifeCTextLists.add(new ModelLifeCTextList("",8, time));
                    lifeCTextLists.add(new ModelLifeCTextList("너만을 위한 상품이 준비되어 있어!\n바로 확인해볼래?",0, ""));
                    lifeCTextLists.add(new ModelLifeCTextList("",4, ""));

                    adapterLifeChallenge.notifyItemChanged(lifeCTextLists.size()-1);
                    recyclerView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
                        }
                    }, 100);

                }


                //목표금액 없이 월마다 넣을때
                if(monFlag){

                    visiblePrice(false);

                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(talkEditText.getWindowToken(), 0);

                    monFlag = false;

                    Date date = new Date();
                    String time = new SimpleDateFormat("aa hh:mm").format(date);

                    monthlyPrice = Long.valueOf(talkEditText.getText().toString());

                    lifeCTextLists.add(new ModelLifeCTextList(talkEditText.getText().toString()+"만원",1, time));
                    lifeCTextLists.add(new ModelLifeCTextList("몇 년 동안 투자하고 싶어?",0, time));

                    lifeCTextLists.add(new ModelLifeCTextList("",9,""));

                    visibleYear(true);

                    talkEditText.setText("");
                    talkEditText.setEnabled(false);
                    sendBt.setEnabled(false);

                    adapterLifeChallenge.notifyItemChanged(lifeCTextLists.size()-1);
                    recyclerView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
                        }
                    }, 100);

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
                    adapterLifeChallenge.notifyItemChanged(lifeCTextLists.size()-1);

                    Date date = new Date();
                    String time = new SimpleDateFormat("aa hh:mm").format(date);

                    selectYear = (long)pickerWheel.getCurrentYear();

                    lifeCTextLists.add(new ModelLifeCTextList(String.valueOf(selectYear)+"년",1, time));
                    lifeCTextLists.add(new ModelLifeCTextList("좋아! 이제 너만을 위한 라이프 챌린지가 완성됐어!!",0, time));

                    talkEditText.setText("");
                    talkEditText.setEnabled(false);
                    sendBt.setEnabled(false);

                    entries.clear();

                    calculatedYield2();

                    lifeCTextLists.add(new ModelLifeCTextList("",8, time));
                    lifeCTextLists.add(new ModelLifeCTextList("너만을 위한 상품이 준비되어 있어!\n바로 확인해볼래?",0, ""));
                    lifeCTextLists.add(new ModelLifeCTextList("",4, ""));

                    adapterLifeChallenge.notifyItemChanged(lifeCTextLists.size()-1);
                    recyclerView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
                        }
                    }, 100);

                    visibleYear(false);

                }else{

                    lifeCTextLists.remove(lifeCTextLists.size()-1);
                    adapterLifeChallenge.notifyItemChanged(lifeCTextLists.size()-1);

                    Date date = new Date();
                    String time = new SimpleDateFormat("aa hh:mm").format(date);

                    selectYear = (long)pickerWheel.getCurrentYear();

                    lifeCTextLists.add(new ModelLifeCTextList(String.valueOf(selectYear)+"년",1, time));
                    lifeCTextLists.add(new ModelLifeCTextList("좋아! 이제 너만을 위한 라이프 챌린지가 완성됐어!!",0, time));

                    talkEditText.setText("");
                    talkEditText.setEnabled(false);
                    sendBt.setEnabled(false);

                    entries.clear();

                    calculatedYield3();

                    lifeCTextLists.add(new ModelLifeCTextList("",8, time));
                    lifeCTextLists.add(new ModelLifeCTextList("너만을 위한 상품이 준비되어 있어!\n바로 확인해볼래?",0, ""));
                    lifeCTextLists.add(new ModelLifeCTextList("",4, ""));

                    adapterLifeChallenge.notifyItemChanged(lifeCTextLists.size()-1);
                    recyclerView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
                        }
                    }, 100);

                    visibleYear(false);

                }

            }
        });


        adapterLifeChallenge.setBotSelect2YesClick(new AdapterLifeChallenge.BotSelect2YesClick() {
            @Override
            public void onClick(int position) {

                visiblePrice(true);

                Date date = new Date();
                String time = new SimpleDateFormat("aa hh:mm").format(date);

                lifeCTextLists.add(new ModelLifeCTextList("응, 있지!",1, time));
                lifeCTextLists.add(new ModelLifeCTextList("와, 궁금하다! 얼마인지 알려줄 수 있어?",0, time));

                adapterLifeChallenge.notifyItemChanged(lifeCTextLists.size()-1);
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
                    }
                }, 100);

                talkEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
                talkEditText.setEnabled(true);
                sendBt.setEnabled(true);

                PriceFlag = true;

            }
        });

        adapterLifeChallenge.setBotSelect2NoClick(new AdapterLifeChallenge.BotSelect2NoClick() {
            @Override
            public void onClick(int position) {

                visiblePrice(false);

                Date date = new Date();
                String time = new SimpleDateFormat("aa hh:mm").format(date);

                lifeCTextLists.add(new ModelLifeCTextList("아니, 없어",1, time));
                lifeCTextLists.add(new ModelLifeCTextList("괜찮아, 이제부터 시작해도 늦지 않았으니까!",0, time));
                lifeCTextLists.add(new ModelLifeCTextList("월마다 조금씩 모으고 싶어?\n아니면 한번에 모아둔 돈으로 투자해볼까?",6, time));

                adapterLifeChallenge.notifyItemChanged(lifeCTextLists.size()-1);
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
                    }
                }, 100);

            }
        });

        //월마다 할래
        adapterLifeChallenge.setBotMonthlyYesClick(new AdapterLifeChallenge.BotMonthlyYesClick() {
            @Override
            public void onClick(int position) {

                visiblePrice(true);

                Date date = new Date();
                String time = new SimpleDateFormat("aa hh:mm").format(date);

                lifeCTextLists.add(new ModelLifeCTextList("응, 있지!",1, time));
                lifeCTextLists.add(new ModelLifeCTextList("월에 얼마씩 넣을꺼야?",0, time));

                talkEditText.setText("");
                talkEditText.setEnabled(false);
                sendBt.setEnabled(false);

                adapterLifeChallenge.notifyItemChanged(lifeCTextLists.size()-1);
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
                    }
                }, 100);

                chartFlag = true;

                talkEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
                talkEditText.setEnabled(true);
                sendBt.setEnabled(true);

            }
        });

        //월마다 안할래
        adapterLifeChallenge.setBotMonthlyNoClick(new AdapterLifeChallenge.BotMonthlyNoClick() {
            @Override
            public void onClick(int position) {

                visiblePrice(false);

                Date date = new Date();
                String time = new SimpleDateFormat("aa hh:mm").format(date);

                lifeCTextLists.add(new ModelLifeCTextList("아니, 없어",1, time));

                chartFlag = false;

                monthlyPrice = 0L;

                lifeCTextLists.add(new ModelLifeCTextList("좋아! 이제 너만을 위한 라이프 챌린지가 완성됐어!!",0, time));

                talkEditText.setText("");
                talkEditText.setEnabled(false);
                sendBt.setEnabled(false);

                entries.clear();

                calculatedYield();

                lifeCTextLists.add(new ModelLifeCTextList("",3, time));
                lifeCTextLists.add(new ModelLifeCTextList("너만을 위한 상품이 준비되어 있어!\n바로 확인해볼래?",0, ""));

                lifeCTextLists.add(new ModelLifeCTextList("",4, ""));

                adapterLifeChallenge.notifyDataSetChanged();
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
                    }
                }, 100);

            }
        });


        //월마다 클릭
        adapterLifeChallenge.setBotSelect3MonthlyBtClick(new AdapterLifeChallenge.BotSelect3MonthlyBtClick() {
            @Override
            public void onClick(int position) {

                visiblePrice(true);

                Date date = new Date();
                String time = new SimpleDateFormat("aa hh:mm").format(date);
                lifeCTextLists.add(new ModelLifeCTextList("월 마다!",1, time));

                lifeCTextLists.add(new ModelLifeCTextList("월에 얼마씩 넣을꺼야?",0, time));

                talkEditText.setText("");
                talkEditText.setEnabled(false);
                sendBt.setEnabled(false);

                recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
                adapterLifeChallenge.notifyItemChanged(lifeCTextLists.size()-1);

                monFlag = true;

                talkEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
                talkEditText.setEnabled(true);
                sendBt.setEnabled(true);

            }
        });

        //모아둔돈 한번에 클릭
        adapterLifeChallenge.setBotSelect3TatalBtClick(new AdapterLifeChallenge.BotSelect3TatalBtClick() {
            @Override
            public void onClick(int position) {

                visiblePrice(true);

                Date date = new Date();
                String time = new SimpleDateFormat("aa hh:mm").format(date);
                lifeCTextLists.add(new ModelLifeCTextList("모아둔 돈으로 한번에!",1, time));

                lifeCTextLists.add(new ModelLifeCTextList("얼마나 넣을 생각이야?",0, time));

                talkEditText.setText("");
                talkEditText.setEnabled(false);
                sendBt.setEnabled(false);

                recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
                adapterLifeChallenge.notifyItemChanged(lifeCTextLists.size()-1);

                totalFlag = true;

                talkEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
                talkEditText.setEnabled(true);
                sendBt.setEnabled(true);

            }
        });


        //다시 시작하기
        adapterLifeChallenge.setBotEndRetryBt(new AdapterLifeChallenge.BotEndRetryBt() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(ActivityLifeChallenge.this, ActivityLifeChallenge.class);
                startActivity(intent);
                finish();
            }
        });


        //30초 가입
        adapterLifeChallenge.setBotEndJoinClick(new AdapterLifeChallenge.BotEndJoinClick() {
            @Override
            public void onClick(int position) {

            }
        });


    }// onCreate 끝


    private void visibleDestinyTitle(boolean visible){
        if(visible){
            bottomLayout.setVisibility(View.VISIBLE);
            talkEditText2.setVisibility(View.VISIBLE);
            sendBt.setVisibility(View.VISIBLE);
        }else{
            bottomLayout.setVisibility(View.GONE);
            talkEditText2.setVisibility(View.GONE);
            sendBt.setVisibility(View.GONE);
        }
    }

    private void visiblePrice(boolean visible){
        if(visible){
            bottomLayout.setVisibility(View.VISIBLE);
            talkEditText.setVisibility(View.VISIBLE);
            sendBt.setVisibility(View.VISIBLE);
            talkEditTextUnit.setVisibility(View.VISIBLE);
        }else{
            bottomLayout.setVisibility(View.GONE);
            talkEditText.setVisibility(View.GONE);
            sendBt.setVisibility(View.GONE);
            talkEditTextUnit.setVisibility(View.GONE);
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

    private void calculatedYield() {

        entries.add(new Entry(0, (basicPrice*10000), 0));

        float price = (basicPrice + (monthlyPrice * 12)) * 1.1f;

        entries.add(new Entry(1, price*10000, 0));

        calPotYear = 1;
        calTotalPrice = (long) (basicPrice + monthlyPrice);
        calYield = (long) (price - calTotalPrice);

        if (price < finalPrice) {

            for (int yield = 2; yield < 10000; yield++) {

                price = (price + (monthlyPrice * 12)) * 1.1f;
                entries.add(new Entry(yield, price*10000, 0));

                if (price >= finalPrice) {

                    calPotYear = yield;
                    calTotalPrice = (long) (basicPrice + (monthlyPrice * 12 * yield));
                    calYield = (long) (price - calTotalPrice);

                    break;
                }
            }
        }

        entries2.add(new Entry(0, basicPrice*10000, 0));
        float price2 = (basicPrice + (monthlyPrice * 12)) * 1.03f;
        entries2.add(new Entry(1, price2*10000, 0));

        calNormalYear = 1;

        if(price2 < finalPrice){

            for (int yield2 = 2; yield2 < 1000; yield2++) {
                price2 = (price2 + (monthlyPrice * 12)) * 1.03f;
                entries2.add(new Entry(yield2, price2*10000, 0));

                if (price2 >= finalPrice) {

                    calNormalYear = yield2;
                    break;
                }
            }
        }
        chartInfoLsits.add(new ModelChartInfoLsit(String.valueOf(calTotalPrice*10000), String.valueOf(calYield*10000), String.valueOf(calPotYear), String.valueOf(calNormalYear), "", ""));
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

    private void calculatedYield2(){

        entries.add(new Entry(0, 0, 0));
        float price = (monthlyPrice*12)*1.1f;
        entries.add(new Entry(1, price*10000, 0));

        for(int yield = 2 ; yield <= selectYear ; yield++){
           price = (price+(monthlyPrice*12))*1.1f;
           entries.add(new Entry(yield, price*10000, 0));
        }

        calTotalPrice = (long)(monthlyPrice*12*selectYear);
        calYield = (long)(price - calTotalPrice);

        entries2.add(new Entry(0, 0, 0));
        float price2 = (monthlyPrice*12)*1.03f;
        entries2.add(new Entry(1, price2*10000, 0));

        for(int yield2 = 2; yield2 <= selectYear ; yield2++){
            price2 = (price2+(monthlyPrice*12))*1.03f;
            entries2.add(new Entry(yield2, price2*10000, 0));
        }
        chartInfoLsits.add(new ModelChartInfoLsit(String.valueOf(calTotalPrice*10000),String.valueOf(calYield*10000),"","",String.valueOf((long)price*10000), String.valueOf((long)price2*10000)));
    }


    private void calculatedYield3(){

        entries.add(new Entry(0, hadPrice, 0));
        float price = hadPrice*1.1f;
        entries.add(new Entry(1, price*10000, 0));

        for(int yield = 2 ; yield <= selectYear ; yield++){
            price = price*1.1f;
            entries.add(new Entry(yield, price*10000, 0));
        }

        calTotalPrice = (long)(hadPrice*selectYear);
        calYield = (long)(price - calTotalPrice);

        entries2.add(new Entry(0, hadPrice, 0));
        float price2 = hadPrice*1.03f;
        entries2.add(new Entry(1, price2*10000, 0));

        for(int yield2 = 2; yield2 <= selectYear ; yield2++){
            price2 = price2*1.03f;
            entries2.add(new Entry(yield2, price2*10000, 0));
        }
        chartInfoLsits.add(new ModelChartInfoLsit(String.valueOf(calTotalPrice*10000),String.valueOf(calYield*10000),"","",String.valueOf((long)price*10000), String.valueOf((long)price2*10000)));
    }

}
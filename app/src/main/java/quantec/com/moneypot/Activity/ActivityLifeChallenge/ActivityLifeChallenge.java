package quantec.com.moneypot.Activity.ActivityLifeChallenge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
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

import quantec.com.moneypot.R;

public class ActivityLifeChallenge extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ModelLifeCTextList> lifeCTextLists;
    AdapterLifeChallenge adapterLifeChallenge;
    ArrayList<ModelLifeCSelectList> lifeCSelectLists;
    TextView sendBt;
    EditText talkEditText;

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

    Long finalPrice, monthlyPrice, selectYear;
    float basicPrice = 0;

    long calTotalPrice, calYield;
    int calPotYear, calNormalYear;

    WheelYearPicker pickerWheel;
    TextView pickerWheelBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_challenge);

        pickerWheel = findViewById(R.id.pickerWheel);
        pickerWheel.setYearStart(1);
        pickerWheel.setYearEnd(50);
        pickerWheelBt = findViewById(R.id.pickerWheelBt);

        sendBt = findViewById(R.id.sendBt);
        talkEditText = findViewById(R.id.talkEditText);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        lifeCTextLists = new ArrayList<>();
        lifeCSelectLists = new ArrayList<>();

        chartInfoLsits = new ArrayList<>();

        Date date = new Date();
        String tiem = new SimpleDateFormat("aa hh:mm").format(date);

        lifeCTextLists.add(new ModelLifeCTextList("안녕? 나는 챗봇이야. 만나서 반가워!",0, ""));
        lifeCTextLists.add(new ModelLifeCTextList("너의 목표를 이루어 줄 수 있도록 도와줄게!",0, ""));
        lifeCTextLists.add(new ModelLifeCTextList("제일 먼저, 왜 투자가 하고 싶어?",2, tiem));

        lifeCSelectLists.add(new ModelLifeCSelectList("내집마련",0));
        lifeCSelectLists.add(new ModelLifeCSelectList("빌딩사기",1));
        lifeCSelectLists.add(new ModelLifeCSelectList("땅사기",2));
        lifeCSelectLists.add(new ModelLifeCSelectList("직접입력",3));

        entries = new ArrayList<>();
        lineDataSet = new LineDataSet(entries, null);
        lineData = new LineData(lineDataSet);

        entries2 = new ArrayList<>();

        adapterLifeChallenge = new AdapterLifeChallenge(lifeCSelectLists, lifeCTextLists, this, entries, lineDataSet, lineData, entries2, chartInfoLsits);
        recyclerView.setAdapter(adapterLifeChallenge);

        adapterLifeChallenge.setBotSelectClick(new AdapterLifeChallenge.BotSelectClick() {
            @Override
            public void onClick(int category) {

                Date date = new Date();
                String time = new SimpleDateFormat("aa hh:mm").format(date);

                if(category == 0){

                    lifeCTextLists.add(new ModelLifeCTextList("내집마련",1, time));
                    lifeCTextLists.add(new ModelLifeCTextList("멋지다!! 정해 놓은 목표금액이 있어?",5 ,time));

                    recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);

                    adapterLifeChallenge.notifyDataSetChanged();
                }
                else if(category == 1){
                    lifeCTextLists.add(new ModelLifeCTextList("빌딩사기",1, time));
                    lifeCTextLists.add(new ModelLifeCTextList("멋지다!! 정해 놓은 목표금액이 있어?",5 ,time));

                    recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);

                    adapterLifeChallenge.notifyDataSetChanged();
                }
                else if(category == 2){
                    lifeCTextLists.add(new ModelLifeCTextList("땅사기",1, time));
                    lifeCTextLists.add(new ModelLifeCTextList("멋지다!! 정해 놓은 목표금액이 있어?",5 ,time));

                    recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);

                    adapterLifeChallenge.notifyDataSetChanged();
                }
                else if(category == 3){

                    TextFlag = true;

                    talkEditText.setEnabled(true);
                    sendBt.setEnabled(true);
                }

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
        sendBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(TextFlag){
                    Date date = new Date();
                    String time = new SimpleDateFormat("aa hh:mm").format(date);

                    TextFlag = false;

                    lifeCTextLists.add(new ModelLifeCTextList(talkEditText.getText().toString(),1, tiem));
                    lifeCTextLists.add(new ModelLifeCTextList("멋지다!! 정해 놓은 목표금액이 있어?",5, time));

                    talkEditText.setText("");
                    talkEditText.setEnabled(false);
                    sendBt.setEnabled(false);

                    recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
                    adapterLifeChallenge.notifyDataSetChanged();

                }

                if(yearFlag){
                    Date date = new Date();
                    String time = new SimpleDateFormat("aa hh:mm").format(date);

                    yearFlag = false;

                    selectYear = Long.valueOf(talkEditText.getText().toString());

                    lifeCTextLists.add(new ModelLifeCTextList(talkEditText.getText().toString(),1, time));
                    lifeCTextLists.add(new ModelLifeCTextList("좋아! 이제 너만을 위한 라이프 챌린지가 완성됐어!!",0, time));

                    talkEditText.setText("");
                    talkEditText.setEnabled(false);
                    sendBt.setEnabled(false);

                    entries.clear();

                    calculatedYield();
//                    chartInfoLsits.add(new ModelChartInfoLsit("","","",""));


                    lifeCTextLists.add(new ModelLifeCTextList("",3, tiem));

                    recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
                    adapterLifeChallenge.notifyDataSetChanged();

                }

                if(chartFlag){

                    monthlyPrice = Long.valueOf(talkEditText.getText().toString());

                    if(basicPrice+monthlyPrice >= 100000){
                        chartFlag = false;

                        Date date = new Date();
                        String time = new SimpleDateFormat("aa hh:mm").format(date);

                        lifeCTextLists.add(new ModelLifeCTextList(talkEditText.getText().toString(),1, time));
                        lifeCTextLists.add(new ModelLifeCTextList("좋아! 이제 너만을 위한 라이프 챌린지가 완성됐어!!",0, time));

                        talkEditText.setText("");
                        talkEditText.setEnabled(false);
                        sendBt.setEnabled(false);

                        entries.clear();

                        calculatedYield();

                        lifeCTextLists.add(new ModelLifeCTextList("",3, time));

                        recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
                        adapterLifeChallenge.notifyDataSetChanged();
                    }else{

                        Toast.makeText(ActivityLifeChallenge.this, "월 투자금액이 모자랍니다.",Toast.LENGTH_SHORT).show();

                    }

                }

                if(monthlyFlag){

                    Date date = new Date();
                    String time = new SimpleDateFormat("aa hh:mm").format(date);

                    monthlyFlag = false;

                    basicPrice = Float.valueOf(talkEditText.getText().toString());

                    lifeCTextLists.add(new ModelLifeCTextList(talkEditText.getText().toString(),1, time));

                    if(basicPrice >= 100000){

                        lifeCTextLists.add(new ModelLifeCTextList("월마다 일정 금액을 추가로 투자할 계획이 있어?", 7, time));

//                        lifeCTextLists.add(new ModelLifeCTextList("월에 얼마씩 넣을꺼야?",0, time));
//
                        talkEditText.setText("");
                        talkEditText.setEnabled(false);
                        sendBt.setEnabled(false);
//
//
                        recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
                        adapterLifeChallenge.notifyDataSetChanged();
//
//                        chartFlag = true;
//
//                        talkEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
//                        talkEditText.setEnabled(true);
//                        sendBt.setEnabled(true);

                    }else{

                        lifeCTextLists.add(new ModelLifeCTextList("월에 얼마씩 넣을꺼야?",0, time));

                        talkEditText.setText("");
                        talkEditText.setEnabled(false);
                        sendBt.setEnabled(false);


                        recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
                        adapterLifeChallenge.notifyDataSetChanged();

                        chartFlag = true;

                        talkEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
                        talkEditText.setEnabled(true);
                        sendBt.setEnabled(true);
                    }

                }

                if(PriceFlag){
                    Date date = new Date();
                    String time = new SimpleDateFormat("aa hh:mm").format(date);

                    PriceFlag = false;

                    finalPrice = Long.valueOf(talkEditText.getText().toString());

                    lifeCTextLists.add(new ModelLifeCTextList(talkEditText.getText().toString(),1, time));
                    lifeCTextLists.add(new ModelLifeCTextList("초기 자본금은 얼마를 원해?",0, time));

                    talkEditText.setText("");
                    talkEditText.setEnabled(false);
                    sendBt.setEnabled(false);

                    recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
                    adapterLifeChallenge.notifyDataSetChanged();

                    monthlyFlag = true;

                    talkEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
                    talkEditText.setEnabled(true);
                    sendBt.setEnabled(true);

                }


                //목표금액 없이 월마다 넣을때 기간 설정
                if(monYearFlag){

                    monYearFlag = false;

                    Date date = new Date();
                    String time = new SimpleDateFormat("aa hh:mm").format(date);


                    selectYear = (long)pickerWheel.getSelectedYear();
//                    selectYear = Long.valueOf(talkEditText.getText().toString());

                    lifeCTextLists.add(new ModelLifeCTextList(String.valueOf(selectYear),1, time));
                    lifeCTextLists.add(new ModelLifeCTextList("좋아! 이제 너만을 위한 라이프 챌린지가 완성됐어!!",0, time));

                    talkEditText.setText("");
                    talkEditText.setEnabled(false);
                    sendBt.setEnabled(false);

                    entries.clear();

                    calculatedYield2();

                    lifeCTextLists.add(new ModelLifeCTextList("",8, time));

                    recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
                    adapterLifeChallenge.notifyDataSetChanged();

                }


                //목표금액 없이 월마다 넣을때
                if(monFlag){

                    monFlag = false;

                    Date date = new Date();
                    String time = new SimpleDateFormat("aa hh:mm").format(date);

                    monthlyPrice = Long.valueOf(talkEditText.getText().toString());

                    lifeCTextLists.add(new ModelLifeCTextList(talkEditText.getText().toString(),1, time));
                    lifeCTextLists.add(new ModelLifeCTextList("기간은 얼마나 생각하고 있어?",0, time));

                    pickerWheel.setVisibility(View.VISIBLE);
                    pickerWheelBt.setEnabled(true);
                    pickerWheelBt.setVisibility(View.VISIBLE);

                    talkEditText.setText("");
                    talkEditText.setEnabled(false);
                    sendBt.setEnabled(false);

                    recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
                    adapterLifeChallenge.notifyDataSetChanged();

                    monYearFlag = true;

//                    talkEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
//                    talkEditText.setEnabled(true);
//                    sendBt.setEnabled(true);

                }

            }
        });


        pickerWheelBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Date date = new Date();
                String time = new SimpleDateFormat("aa hh:mm").format(date);

                selectYear = (long)pickerWheel.getCurrentYear();

                lifeCTextLists.add(new ModelLifeCTextList(String.valueOf(selectYear),1, time));
                lifeCTextLists.add(new ModelLifeCTextList("좋아! 이제 너만을 위한 라이프 챌린지가 완성됐어!!",0, time));

                talkEditText.setText("");
                talkEditText.setEnabled(false);
                sendBt.setEnabled(false);

                entries.clear();

                calculatedYield2();

                lifeCTextLists.add(new ModelLifeCTextList("",8, time));

                recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
                adapterLifeChallenge.notifyDataSetChanged();

                pickerWheelBt.setEnabled(false);
                pickerWheelBt.setVisibility(View.INVISIBLE);
                pickerWheel.setVisibility(View.INVISIBLE);

            }
        });


        adapterLifeChallenge.setBotSelect2YesClick(new AdapterLifeChallenge.BotSelect2YesClick() {
            @Override
            public void onClick(int position) {
                Date date = new Date();
                String time = new SimpleDateFormat("aa hh:mm").format(date);

                lifeCTextLists.add(new ModelLifeCTextList("응, 있지!",1, time));
                lifeCTextLists.add(new ModelLifeCTextList("와, 궁금하다! 얼마인지 알려줄 수 있어?",0, time));

                adapterLifeChallenge.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);

                talkEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
                talkEditText.setEnabled(true);
                sendBt.setEnabled(true);

                PriceFlag = true;

            }
        });

        adapterLifeChallenge.setBotSelect2NoClick(new AdapterLifeChallenge.BotSelect2NoClick() {
            @Override
            public void onClick(int position) {
                Date date = new Date();
                String time = new SimpleDateFormat("aa hh:mm").format(date);

                lifeCTextLists.add(new ModelLifeCTextList("아니, 없어",1, time));
                lifeCTextLists.add(new ModelLifeCTextList("괜찮아, 이제부터 시작해도 늦지 않았으니까!",0, time));
                lifeCTextLists.add(new ModelLifeCTextList("월마다 조금씩 모으고 싶어?\n아니면 한번에 모아둔 돈으로 투자해볼까?",6, time));

                adapterLifeChallenge.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);

            }
        });

        //월마다 할래
        adapterLifeChallenge.setBotMonthlyYesClick(new AdapterLifeChallenge.BotMonthlyYesClick() {
            @Override
            public void onClick(int position) {

                Date date = new Date();
                String time = new SimpleDateFormat("aa hh:mm").format(date);

                lifeCTextLists.add(new ModelLifeCTextList("응, 있지!",1, time));
                lifeCTextLists.add(new ModelLifeCTextList("월에 얼마씩 넣을꺼야?",0, time));

                talkEditText.setText("");
                talkEditText.setEnabled(false);
                sendBt.setEnabled(false);


                recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
                adapterLifeChallenge.notifyDataSetChanged();

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
                Date date = new Date();
                String time = new SimpleDateFormat("aa hh:mm").format(date);
                lifeCTextLists.add(new ModelLifeCTextList("아니, 없어",1, time));

                chartFlag = false;

                monthlyPrice = 0L;

//                lifeCTextLists.add(new ModelLifeCTextList(talkEditText.getText().toString(),1, time));
                lifeCTextLists.add(new ModelLifeCTextList("좋아! 이제 너만을 위한 라이프 챌린지가 완성됐어!!",0, time));

                talkEditText.setText("");
                talkEditText.setEnabled(false);
                sendBt.setEnabled(false);

                entries.clear();

                calculatedYield();

                lifeCTextLists.add(new ModelLifeCTextList("",3, time));

                recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
                adapterLifeChallenge.notifyDataSetChanged();

            }
        });


        //월마다 클릭
        adapterLifeChallenge.setBotSelect3MonthlyBtClick(new AdapterLifeChallenge.BotSelect3MonthlyBtClick() {
            @Override
            public void onClick(int position) {

                Date date = new Date();
                String time = new SimpleDateFormat("aa hh:mm").format(date);
                lifeCTextLists.add(new ModelLifeCTextList("월 마다!",1, time));

                lifeCTextLists.add(new ModelLifeCTextList("월에 얼마씩 넣을꺼야?",0, time));

                talkEditText.setText("");
                talkEditText.setEnabled(false);
                sendBt.setEnabled(false);

                recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
                adapterLifeChallenge.notifyDataSetChanged();

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



            }
        });



    }// onCreate 끝


    private void calculatedYield(){

        entries.add(new Entry(0, basicPrice, 0));

        float price = (basicPrice+(monthlyPrice*12))*1.1f;

        entries.add(new Entry(1, price, 0));

        if(price < finalPrice){

            for(int yield = 2 ; yield < 1000 ; yield++){

                price = (price+(monthlyPrice*12))*1.1f;
                entries.add(new Entry(yield, price, 0));

                Log.e("기간","값 : "+yield);
                Log.e("초기금액","값 : "+price);
                Log.e("목표금액","값 : "+finalPrice);

                if(price >= finalPrice){

                    calPotYear = yield;
                    calTotalPrice = (long)(basicPrice+(monthlyPrice*12*yield));
                    calYield = (long)(price - calTotalPrice);

                    break;
                }
            }
        }

        entries2.add(new Entry(0, basicPrice, 0));
        float price2 = (basicPrice+(monthlyPrice*12))*1.03f;
        entries2.add(new Entry(1, price2, 0));

        for(int yield2 = 2; yield2 < 1000 ; yield2++){
            price2 = (price2+(monthlyPrice*12))*1.03f;
            entries2.add(new Entry(yield2, price2, 0));

            Log.e("기간","값 : "+yield2);
            Log.e("초기금액2","값 : "+price2);
            Log.e("목표금액2","값 : "+finalPrice);

            if(price2 >= finalPrice){

                calNormalYear = yield2;
                chartInfoLsits.add(new ModelChartInfoLsit(String.valueOf(calTotalPrice),String.valueOf(calYield),String.valueOf(calPotYear),String.valueOf(calNormalYear), "", ""));
                break;
            }
        }
    }


    private void calculatedYield2(){

        entries.add(new Entry(0, 0, 0));

        float price = (monthlyPrice*12)*1.1f;

        entries.add(new Entry(1, price, 0));



            for(int yield = 2 ; yield <= selectYear ; yield++){

                price = (price+(monthlyPrice*12))*1.1f;
                entries.add(new Entry(yield, price, 0));

                Log.e("기간","값 : "+yield);
                Log.e("초기금액","값 : "+price);
                Log.e("목표금액","값 : "+finalPrice);

            }

            calTotalPrice = (long)(monthlyPrice*12*selectYear);
            calYield = (long)(price - calTotalPrice);


        entries2.add(new Entry(0, 0, 0));
        float price2 = (monthlyPrice*12)*1.03f;
        entries2.add(new Entry(1, price2, 0));

        for(int yield2 = 2; yield2 <= selectYear ; yield2++){
            price2 = (price2+(monthlyPrice*12))*1.03f;
            entries2.add(new Entry(yield2, price2, 0));

            Log.e("기간","값 : "+yield2);
            Log.e("초기금액2","값 : "+price2);
            Log.e("목표금액2","값 : "+finalPrice);

        }

        chartInfoLsits.add(new ModelChartInfoLsit(String.valueOf(calTotalPrice),String.valueOf(calYield),"","",String.valueOf((long)price), String.valueOf((long)price2)));
    }

}





//public class ActivityLifeChallenge extends AppCompatActivity {
//
//
//    RecyclerView recyclerView;
//    RecyclerView.LayoutManager layoutManager;
//    ArrayList<ModelLifeCTextList> lifeCTextLists;
//    AdapterLifeChallenge adapterLifeChallenge;
//    ArrayList<ModelLifeCSelectList> lifeCSelectLists;
//    TextView sendBt;
//    EditText talkEditText;
//
//    boolean TextFlag = false;
//    boolean PriceFlag = false;
//    boolean yearFlag = false;
//
//    List<Entry> entries;
//    LineDataSet lineDataSet;
//    LineData lineData;
//
//    List<Entry> entries2;
//
//    Long finalPrice, selectYear;
//    float basicPrice = 0;
//    float yield = 1.1f;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_life_challenge);
//
//
//        sendBt = findViewById(R.id.sendBt);
//        talkEditText = findViewById(R.id.talkEditText);
//
//        recyclerView = findViewById(R.id.recyclerView);
//        recyclerView.setHasFixedSize(true);
//
//        layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//
//        lifeCTextLists = new ArrayList<>();
//        lifeCSelectLists = new ArrayList<>();
//
//        Date date = new Date();
//        String tiem = new SimpleDateFormat("aa hh:mm").format(date);
//
//        lifeCTextLists.add(new ModelLifeCTextList("안녕? 나는 챗봇이야. 만나서 반가워!",0, ""));
//        lifeCTextLists.add(new ModelLifeCTextList("너의 목표를 이루어 줄 수 있도록 도와줄게!",0, ""));
//        lifeCTextLists.add(new ModelLifeCTextList("제일 먼저, 왜 투자가 하고 싶어?",2, tiem));
//
//        lifeCSelectLists.add(new ModelLifeCSelectList("내집마련",0));
//        lifeCSelectLists.add(new ModelLifeCSelectList("빌딩사기",1));
//        lifeCSelectLists.add(new ModelLifeCSelectList("땅사기",2));
//        lifeCSelectLists.add(new ModelLifeCSelectList("직접입력",3));
//
//        entries = new ArrayList<>();
//        lineDataSet = new LineDataSet(entries, null);
//        lineData = new LineData(lineDataSet);
//
//        entries2 = new ArrayList<>();
//
//        adapterLifeChallenge = new AdapterLifeChallenge(lifeCSelectLists, lifeCTextLists, this, entries, lineDataSet, lineData, entries2);
//        recyclerView.setAdapter(adapterLifeChallenge);
//
//        adapterLifeChallenge.setBotSelectClick(new AdapterLifeChallenge.BotSelectClick() {
//            @Override
//            public void onClick(int category) {
//
//                Date date = new Date();
//                String tiem = new SimpleDateFormat("aa hh:mm").format(date);
//
//                if(category == 0){
//
//                    lifeCTextLists.add(new ModelLifeCTextList("내집마련",1, tiem));
//                    lifeCTextLists.add(new ModelLifeCTextList("멋지다!! 정해 놓은 목표금액이 있어?",0, tiem));
//                    lifeCTextLists.add(new ModelLifeCTextList("",5 ,""));
//
//                    recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
//
//                    adapterLifeChallenge.notifyDataSetChanged();
//                }
//                else if(category == 1){
//                    lifeCTextLists.add(new ModelLifeCTextList("빌딩사기",1, tiem));
//                    lifeCTextLists.add(new ModelLifeCTextList("멋지다!! 정해 놓은 목표금액이 있어?",0, tiem));
//                    lifeCTextLists.add(new ModelLifeCTextList("",5, ""));
//
//                    recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
//
//                    adapterLifeChallenge.notifyDataSetChanged();
//                }
//                else if(category == 2){
//                    lifeCTextLists.add(new ModelLifeCTextList("땅사기",1, tiem));
//                    lifeCTextLists.add(new ModelLifeCTextList("멋지다!! 정해 놓은 목표금액이 있어?",0, tiem));
//                    lifeCTextLists.add(new ModelLifeCTextList("",5, ""));
//
//                    recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
//
//                    adapterLifeChallenge.notifyDataSetChanged();
//                }
//                else if(category == 3){
//
//                    TextFlag = true;
//
//                    talkEditText.setEnabled(true);
//                    sendBt.setEnabled(true);
//                }
//
//            }
//        });
//
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
//        sendBt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                if(TextFlag){
//                    Date date = new Date();
//                    String tiem = new SimpleDateFormat("aa hh:mm").format(date);
//
//                    TextFlag = false;
//
//                    lifeCTextLists.add(new ModelLifeCTextList(talkEditText.getText().toString(),1, tiem));
//                    lifeCTextLists.add(new ModelLifeCTextList("멋지다!! 정해 놓은 목표금액이 있어?",0, tiem));
//                    lifeCTextLists.add(new ModelLifeCTextList("",5, ""));
//
//                    talkEditText.setText("");
//                    talkEditText.setEnabled(false);
//                    sendBt.setEnabled(false);
//
//                    recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
//                    adapterLifeChallenge.notifyDataSetChanged();
//
//                }
//
//                if(yearFlag){
//                    Date date = new Date();
//                    String tiem = new SimpleDateFormat("aa hh:mm").format(date);
//
//                    yearFlag = false;
//
//                    selectYear = Long.valueOf(talkEditText.getText().toString());
//
//                    basicPrice = Float.valueOf(talkEditText.getText().toString());
//
//                    lifeCTextLists.add(new ModelLifeCTextList(talkEditText.getText().toString(),1, tiem));
//                    lifeCTextLists.add(new ModelLifeCTextList("좋아! 이제 너만을 위한 라이프 챌린지가 완성됐어!!",0, tiem));
//
//                    talkEditText.setText("");
//                    talkEditText.setEnabled(false);
//                    sendBt.setEnabled(false);
//
//                    entries.clear();
////                    for(int a = 0 ; a < 10 ; a++) {
////                        entries.add(new Entry(a, a, a+1));
////                    }
//
//                    calculatedYield();
//
//                    lifeCTextLists.add(new ModelLifeCTextList("",3, tiem));
//
//                    recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
//                    adapterLifeChallenge.notifyDataSetChanged();
//
//                }
//
//                if(PriceFlag){
//                    Date date = new Date();
//                    String tiem = new SimpleDateFormat("aa hh:mm").format(date);
//
//                    PriceFlag = false;
//
//                    finalPrice = Long.valueOf(talkEditText.getText().toString());
//
//                    lifeCTextLists.add(new ModelLifeCTextList(talkEditText.getText().toString(),1, tiem));
//                    lifeCTextLists.add(new ModelLifeCTextList("언제까지 "+talkEditText.getText().toString()+" 을 모으고 싶어?",0, tiem));
//
//                    talkEditText.setText("");
//                    talkEditText.setEnabled(false);
//                    sendBt.setEnabled(false);
//
//                    recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
//                    adapterLifeChallenge.notifyDataSetChanged();
//
//                    yearFlag = true;
//
////                    talkEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5)});
//                    talkEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
//                    talkEditText.setEnabled(true);
//                    sendBt.setEnabled(true);
//
//                }
//
////                lifeCTextLists.add(new ModelLifeCTextList(talkEditText.getText().toString(),1));
////                lifeCTextLists.add(new ModelLifeCTextList("멋지다!! 정해 놓은 목표금액이 있어?",0));
////                lifeCTextLists.add(new ModelLifeCTextList("",5));
////
////                talkEditText.setText("");
////                talkEditText.setEnabled(false);
////                sendBt.setEnabled(false);
////
////                adapterLifeChallenge.notifyDataSetChanged();
//            }
//        });
//
//
//
//        adapterLifeChallenge.setBotSelect2YesClick(new AdapterLifeChallenge.BotSelect2YesClick() {
//            @Override
//            public void onClick(int position) {
//                Date date = new Date();
//                String tiem = new SimpleDateFormat("aa hh:mm").format(date);
//
//                lifeCTextLists.add(new ModelLifeCTextList("응, 있지!",1, tiem));
//                lifeCTextLists.add(new ModelLifeCTextList("와, 궁금하다! 얼마인지 알려줄 수 있어?",0, tiem));
//
//                adapterLifeChallenge.notifyDataSetChanged();
//                recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
//
////                talkEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5)});
//                talkEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
//                talkEditText.setEnabled(true);
//                sendBt.setEnabled(true);
//
//                PriceFlag = true;
//
//            }
//        });
//
//        adapterLifeChallenge.setBotSelect2NoClick(new AdapterLifeChallenge.BotSelect2NoClick() {
//            @Override
//            public void onClick(int position) {
//                Date date = new Date();
//                String tiem = new SimpleDateFormat("aa hh:mm").format(date);
//
//                lifeCTextLists.add(new ModelLifeCTextList("아니, 없어",1, tiem));
//                lifeCTextLists.add(new ModelLifeCTextList("괜찮아, 이제부터 시작해도 늦지 않았으니까!",0, tiem));
//
//                adapterLifeChallenge.notifyDataSetChanged();
//                recyclerView.smoothScrollToPosition(lifeCTextLists.size()-1);
//
//            }
//        });
//
//    }// onCreate 끝
//
//
//    private void calculatedYield(){
//
//        entries.add(new Entry(0, basicPrice, 0));
//
//        float price = (basicPrice+(300000*12))*1.1f;
//
//        entries.add(new Entry(1, price, 0));
//
//        if(price < finalPrice){
//
//            for(int yield = 2 ; yield < 1000 ; yield++){
//
//                price = (price+(300000*12))*1.1f;
//                entries.add(new Entry(yield, price, 0));
//
//                Log.e("기간","값 : "+yield);
//                Log.e("초기금액","값 : "+price);
//                Log.e("목표금액","값 : "+finalPrice);
//
//                if(price >= finalPrice){
//
//                    break;
//                }
//            }
//        }
//
//
//        entries2.add(new Entry(0, basicPrice, 0));
//        float price2 = (basicPrice+(300000*12))*1.03f;
//        entries2.add(new Entry(1, price2, 0));
//
//        for(int yield2 = 2; yield2 < 1000 ; yield2++){
//            price2 = (price2+(300000*12))*1.03f;
//            entries2.add(new Entry(yield2, price2, 0));
//
//            Log.e("기간","값 : "+yield2);
//            Log.e("초기금액2","값 : "+price2);
//            Log.e("목표금액2","값 : "+finalPrice);
//
//            if(price2 >= finalPrice){
//
//                break;
//            }
//        }
//
//    }
//}

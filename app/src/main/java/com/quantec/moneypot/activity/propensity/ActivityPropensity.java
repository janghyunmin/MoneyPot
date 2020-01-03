package com.quantec.moneypot.activity.propensity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.florent37.viewanimator.ViewAnimator;
import com.quantec.moneypot.R;
import com.quantec.moneypot.activity.Intro.ActivityIntro;
import com.quantec.moneypot.activity.Login.resist.ModelItemClick;
import com.quantec.moneypot.activity.Login.resist.ModelPropensityClick;
import com.quantec.moneypot.activity.Main.ActivityMain;
import com.quantec.moneypot.database.room.entry.RoomEntity2;
import com.quantec.moneypot.database.room.local.RoomDao;
import com.quantec.moneypot.database.room.local.SearchRoomDatabase;
import com.quantec.moneypot.database.room.viewmodel.SearchViewModel2;
import com.quantec.moneypot.datamodel.nmodel.ModelSearchDb;
import com.quantec.moneypot.dialog.DialogPropensitySkeep;
import com.quantec.moneypot.dialog.DialogWeekFinance;
import com.quantec.moneypot.network.retrofit.RetrofitClient;
import com.quantec.moneypot.rxandroid.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityPropensity extends BasePropensityActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager linearLayoutManager;
    ArrayList<ModelPropensity> modelPropensities;
    AdapterPropensity adapterPropensity;

    LinearLayout bottomLayout, bottomLayout1, bottomLayout2, bottomLayout3, bottomLayout4, dupliNext;
    TextView yesBt, noBt, yesBt1, noBt1, yesBt2, noBt2, yesBt3, noBt3, yesBt4, dupliNextBt;

    int removePosition;
    boolean dupliItemCheck = false;

    ArrayList<ModelItemClick> modelItemClicks;
    ArrayList<ModelPropensityClick> modelPropensityClicks;

    float dp;

    private SearchViewModel2 searchViewModel;

    private RoomDao roomDao;

    private DialogPropensitySkeep dialogPropensitySkeep;
    private DialogWeekFinance dialogWeekFinance;

    int ExpInvestedCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_propensity);

        actList.add(this);

        Resources res = getResources();
        String[] title = res.getStringArray(R.array.propensity);

        modelPropensityClicks = new ArrayList<>();
        for(int index = 0; index < 9; index++){
            modelPropensityClicks.add(new ModelPropensityClick(false, 0));
        }

        modelItemClicks = new ArrayList<>();
        for(int index = 0; index < 13 ; index++){
            modelItemClicks.add(new ModelItemClick(0,false, title[index]));
        }

        bottomLayout = findViewById(R.id.bottomLayout);
        bottomLayout1 = findViewById(R.id.bottomLayout1);
        bottomLayout2 = findViewById(R.id.bottomLayout2);
        bottomLayout3 = findViewById(R.id.bottomLayout3);
        bottomLayout4 = findViewById(R.id.bottomLayout4);

        yesBt = findViewById(R.id.yesBt);
        noBt = findViewById(R.id.noBt);
        yesBt1 = findViewById(R.id.yesBt1);
        noBt1 = findViewById(R.id.noBt1);
        yesBt2 = findViewById(R.id.yesBt2);
        noBt2 = findViewById(R.id.noBt2);
        yesBt3 = findViewById(R.id.yesBt3);
        noBt3 = findViewById(R.id.noBt3);
        yesBt4 = findViewById(R.id.yesBt4);

        dupliNext = findViewById(R.id.dupliNext);
        dupliNextBt = findViewById(R.id.dupliNextBt);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        modelPropensities = new ArrayList<>();

        DisplayMetrics px = getResources().getDisplayMetrics();
        float dpt = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, px);

        adapterPropensity = new AdapterPropensity(modelPropensities, this, dpt, modelItemClicks);
        recyclerView.setAdapter(adapterPropensity);

        modelPropensities.add(new ModelPropensity(0, ""));
        modelPropensities.add(new ModelPropensity(1, ""));

        adapterPropensity.notifyDataSetChanged();

        DisplayMetrics dm = getResources().getDisplayMetrics();
        dp = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 68, dm);

        ViewAnimator.animate(bottomLayout).translationY(0, -dp).alpha(0, 1).duration(1000).start();


        RxView.clicks(yesBt).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(empty -> {
            modelPropensities.add(new ModelPropensity(13, "정보 제공"));
            ViewAnimator.animate(bottomLayout).translationY(-dp, 0).alpha(1, 0).duration(400).start();
            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);

            modelPropensities.add(new ModelPropensity(-1, ""));
            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);
            recyclerView.smoothScrollToPosition(adapterPropensity.getItemCount()-1);

            ViewAnimator.animate(bottomLayout1).translationY(0, -dp).alpha(0, 1).duration(1000).start();
        });

        RxView.clicks(noBt).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(empty -> {

            dialogPropensitySkeep = new DialogPropensitySkeep(ActivityPropensity.this,
                    okClickListener, noClickListener);
            dialogPropensitySkeep.show();

        });


        RxView.clicks(yesBt1).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(empty -> {
            modelPropensities.add(new ModelPropensity(13, "권유를 원함"));
            ViewAnimator.animate(bottomLayout1).translationY(-dp, 0).alpha(1, 0).duration(400).start();
            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);

            modelPropensities.add(new ModelPropensity(2, ""));
            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);
            recyclerView.smoothScrollToPosition(adapterPropensity.getItemCount()-1);
        });

        RxView.clicks(noBt1).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(empty -> {

            ViewAnimator.animate(bottomLayout1).translationY(-dp, 0).alpha(1, 0).duration(400).start();
            modelPropensities.add(new ModelPropensity(13, "권유를 원하지 않음"));
            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);
            recyclerView.smoothScrollToPosition(adapterPropensity.getItemCount()-1);

            modelPropensities.add(new ModelPropensity(2, ""));
            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);
            recyclerView.smoothScrollToPosition(adapterPropensity.getItemCount()-1);

        });

        RxView.clicks(dupliNextBt).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(empty -> {
            if(modelItemClicks.get(0).getTotal() > 0){

                ViewAnimator.animate(dupliNext).translationY(-dp, 0).alpha(0, 1).duration(400).start();

                if (modelPropensityClicks.get(3).isClick()) {
                    modelPropensities.get(modelPropensityClicks.get(3).getPosition()).setText("");
                    adapterPropensity.notifyItemChanged(modelPropensityClicks.get(3).getPosition());
                } else {
                    modelPropensityClicks.get(3).setClick(true);
                    modelPropensityClicks.get(3).setPosition(adapterPropensity.getItemCount() - 1);

                    modelPropensities.add(new ModelPropensity(6, ""));
                    adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount() - 1);
                    recyclerView.smoothScrollToPosition(adapterPropensity.getItemCount() - 1);
                }
            }else{
                Toast.makeText(ActivityPropensity.this, "최소 1개 이상을 선택해 주세요", Toast.LENGTH_SHORT).show();
            }
        });

        ///향후 연 수입원////
        adapterPropensity.setYearInputOneClick(new AdapterPropensity.YearInputOneClick() {
            @Override
            public void onClick(int position) {
                YearInput("일정하지 않음");
            }
        });
        adapterPropensity.setYearInputTwoClick(new AdapterPropensity.YearInputTwoClick() {
            @Override
            public void onClick(int position) {
                YearInput("일정하지만, 줄어들 것으로 예상");
            }
        });
        adapterPropensity.setYearInputThreeClick(new AdapterPropensity.YearInputThreeClick() {
            @Override
            public void onClick(int position) {
                YearInput("일정하고 앞으로 더 많이 벌 것");
            }
        });


        adapterPropensity.setFinanceRate5Click(new AdapterPropensity.FinanceRate5Click() {
            @Override
            public void onClick(int position) {
                FinanceRate("5% 이하");
            }
        });
        adapterPropensity.setFinanceRate10Click(new AdapterPropensity.FinanceRate10Click() {
            @Override
            public void onClick(int position) {
                FinanceRate("10% 이하");
            }
        });
        adapterPropensity.setFinanceRate20Click(new AdapterPropensity.FinanceRate20Click() {
            @Override
            public void onClick(int position) {
                FinanceRate("20% 이하");
            }
        });
        adapterPropensity.setFinanceRate30Click(new AdapterPropensity.FinanceRate30Click() {
            @Override
            public void onClick(int position) {
                FinanceRate("30% 이하");
            }
        });
        adapterPropensity.setFinanceRate30UpClick(new AdapterPropensity.FinanceRate30UpClick() {
            @Override
            public void onClick(int position) {
                FinanceRate("30% 이상");
            }
        });


        //취약 금융소비자 다이얼로그
        adapterPropensity.setWeekFinanceClick(new AdapterPropensity.WeekFinanceClick() {
            @Override
            public void onClick(int position) {
                dialogWeekFinance = new DialogWeekFinance(ActivityPropensity.this, closeBtClickListener);
                dialogWeekFinance.show();
            }
        });

        RxView.clicks(yesBt2).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(empty -> {

            dupliItemCheck = true;

            removePosition = adapterPropensity.getItemCount() - 1;
            modelPropensities.remove(removePosition);
            adapterPropensity.notifyItemRemoved(removePosition);
            adapterPropensity.notifyItemRangeChanged(removePosition, adapterPropensity.getItemCount());

            modelPropensities.add(new ModelPropensity(13, "있음"));
            ViewAnimator.animate(bottomLayout2).translationY(-dp, 0).alpha(1, 0).duration(400).start();
            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount() - 1);

            modelPropensities.add(new ModelPropensity(5, ""));
            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount() - 1);
            recyclerView.smoothScrollToPosition(adapterPropensity.getItemCount() - 1);

            ViewAnimator.animate(dupliNext).translationY(0, -dp).alpha(0, 1).duration(1000).start();
        });

        RxView.clicks(noBt2).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(empty -> {

            dupliItemCheck = false;

            removePosition = adapterPropensity.getItemCount()-1;
            modelPropensities.remove(removePosition);
            adapterPropensity.notifyItemRemoved(removePosition);
            adapterPropensity.notifyItemRangeChanged(removePosition, adapterPropensity.getItemCount());

            modelPropensities.add(new ModelPropensity(13, "없음"));
            ViewAnimator.animate(bottomLayout2).translationY(-dp, 0).alpha(1, 0).duration(400).start();
            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);

            modelPropensities.add(new ModelPropensity(7, ""));
            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);
            recyclerView.smoothScrollToPosition(adapterPropensity.getItemCount()-1);
        });


//        RxView.clicks(dupliNext).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(empty -> {
//
//            dupliItemCheck = true;
//
//            removePosition = adapterPropensity.getItemCount() - 1;
//            modelPropensities.remove(removePosition);
//            adapterPropensity.notifyItemRemoved(removePosition);
//            adapterPropensity.notifyItemRangeChanged(removePosition, adapterPropensity.getItemCount());
//
//            modelPropensities.add(new ModelPropensity(13, "있음"));
//            ViewAnimator.animate(bottomLayout2).translationY(-dp, 0).alpha(1, 0).duration(400).start();
//            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount() - 1);
//
//            modelPropensities.add(new ModelPropensity(5, ""));
//            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount() - 1);
//            recyclerView.smoothScrollToPosition(adapterPropensity.getItemCount() - 1);
//
//            ViewAnimator.animate(dupliNext).translationY(-dp, 0).alpha(1, 0).duration(400).start();
//        });


        adapterPropensity.setInvestPupoOneClick(new AdapterPropensity.InvestPupoOneClick() {
            @Override
            public void onClick(int position) {
                InvestPupo("높은 원금 보존 + 약간의 수익");
            }
        });
        adapterPropensity.setInvestPupoTwoClick(new AdapterPropensity.InvestPupoTwoClick() {
            @Override
            public void onClick(int position) {
                InvestPupo("최소한의 원금 보존 + 좀 더 높은 수익");
            }
        });
        adapterPropensity.setInvestPupoThreeClick(new AdapterPropensity.InvestPupoThreeClick() {
            @Override
            public void onclick(int position) {
                InvestPupo("약간의 손실 + 아주 높은 수익");
            }
        });

        ////////////////투자목적 아이템 중복 선택
        adapterPropensity.setInvestPupoItemOneClick(new AdapterPropensity.InvestPupoItemOneClick() {
            @Override
            public void onClick(int position) {

                if(modelItemClicks.get(0).isItemClick()){
                    ExpInvestedCount--;
                    modelItemClicks.get(0).setItemClick(false);
                    modelItemClicks.get(0).setTotal(ExpInvestedCount);
                }else{
                    ExpInvestedCount++;
                    modelItemClicks.get(0).setItemClick(true);
                    modelItemClicks.get(0).setTotal(ExpInvestedCount);
                }

                adapterPropensity.notifyItemChanged(position, "click");
            }
        });
        adapterPropensity.setInvestPupoItemTwoClick(new AdapterPropensity.InvestPupoItemTwoClick() {
            @Override
            public void onClick(int position) {

                if(modelItemClicks.get(1).isItemClick()){
                    ExpInvestedCount--;
                    modelItemClicks.get(1).setItemClick(false);
                    modelItemClicks.get(0).setTotal(ExpInvestedCount);
                }else{
                    ExpInvestedCount++;
                    modelItemClicks.get(1).setItemClick(true);
                    modelItemClicks.get(0).setTotal(ExpInvestedCount);
                }

                adapterPropensity.notifyItemChanged(position, "click");
            }
        });
        adapterPropensity.setInvestPupoItemThreeClick(new AdapterPropensity.InvestPupoItemThreeClick() {
            @Override
            public void onClick(int position) {

                if(modelItemClicks.get(2).isItemClick()){
                    ExpInvestedCount--;
                    modelItemClicks.get(2).setItemClick(false);
                    modelItemClicks.get(0).setTotal(ExpInvestedCount);
                }else{
                    ExpInvestedCount++;
                    modelItemClicks.get(2).setItemClick(true);
                    modelItemClicks.get(0).setTotal(ExpInvestedCount);
                }

                adapterPropensity.notifyItemChanged(position, "click");
            }
        });
        adapterPropensity.setInvestPupoItemForeClick(new AdapterPropensity.InvestPupoItemForeClick() {
            @Override
            public void onClick(int position) {

                if(modelItemClicks.get(3).isItemClick()){
                    ExpInvestedCount--;
                    modelItemClicks.get(3).setItemClick(false);
                    modelItemClicks.get(0).setTotal(ExpInvestedCount);
                }else{
                    ExpInvestedCount++;
                    modelItemClicks.get(3).setItemClick(true);
                    modelItemClicks.get(0).setTotal(ExpInvestedCount);
                }

                adapterPropensity.notifyItemChanged(position, "click");
            }
        });
        adapterPropensity.setInvestPupoItemFiveClick(new AdapterPropensity.InvestPupoItemFiveClick() {
            @Override
            public void onClick(int position) {

                if(modelItemClicks.get(4).isItemClick()){
                    ExpInvestedCount--;
                    modelItemClicks.get(4).setItemClick(false);
                    modelItemClicks.get(0).setTotal(ExpInvestedCount);
                }else{
                    ExpInvestedCount++;
                    modelItemClicks.get(4).setItemClick(true);
                    modelItemClicks.get(0).setTotal(ExpInvestedCount);
                }

                adapterPropensity.notifyItemChanged(position, "click");
            }
        });
        adapterPropensity.setInvestPupoItemSixClick(new AdapterPropensity.InvestPupoItemSixClick() {
            @Override
            public void onClick(int position) {

                if(modelItemClicks.get(5).isItemClick()){
                    ExpInvestedCount--;
                    modelItemClicks.get(5).setItemClick(false);
                    modelItemClicks.get(0).setTotal(ExpInvestedCount);
                }else{
                    ExpInvestedCount++;
                    modelItemClicks.get(5).setItemClick(true);
                    modelItemClicks.get(0).setTotal(ExpInvestedCount);
                }

                adapterPropensity.notifyItemChanged(position, "click");
            }
        });
        adapterPropensity.setInvestPupoItemSevenClick(new AdapterPropensity.InvestPupoItemSevenClick() {
            @Override
            public void onClick(int position) {

                if(modelItemClicks.get(6).isItemClick()){
                    ExpInvestedCount--;
                    modelItemClicks.get(6).setItemClick(false);
                    modelItemClicks.get(0).setTotal(ExpInvestedCount);
                }else{
                    ExpInvestedCount++;
                    modelItemClicks.get(6).setItemClick(true);
                    modelItemClicks.get(0).setTotal(ExpInvestedCount);
                }

                adapterPropensity.notifyItemChanged(position, "click");
            }
        });
        adapterPropensity.setInvestPupoItemEightClick(new AdapterPropensity.InvestPupoItemEightClick() {
            @Override
            public void onClick(int position) {

                if(modelItemClicks.get(7).isItemClick()){
                    ExpInvestedCount--;
                    modelItemClicks.get(7).setItemClick(false);
                    modelItemClicks.get(0).setTotal(ExpInvestedCount);
                }else{
                    ExpInvestedCount++;
                    modelItemClicks.get(7).setItemClick(true);
                    modelItemClicks.get(0).setTotal(ExpInvestedCount);
                }

                adapterPropensity.notifyItemChanged(position, "click");
            }
        });
        adapterPropensity.setInvestPupoItemNineClick(new AdapterPropensity.InvestPupoItemNineClick() {
            @Override
            public void onClick(int position) {

                if(modelItemClicks.get(8).isItemClick()){
                    ExpInvestedCount--;
                    modelItemClicks.get(8).setItemClick(false);
                    modelItemClicks.get(0).setTotal(ExpInvestedCount);
                }else{
                    ExpInvestedCount++;
                    modelItemClicks.get(8).setItemClick(true);
                    modelItemClicks.get(0).setTotal(ExpInvestedCount);
                }

                adapterPropensity.notifyItemChanged(position, "click");
            }
        });
        adapterPropensity.setInvestPupoItemTenClick(new AdapterPropensity.InvestPupoItemTenClick() {
            @Override
            public void onClick(int position) {

                if(modelItemClicks.get(9).isItemClick()){
                    ExpInvestedCount--;
                    modelItemClicks.get(9).setItemClick(false);
                    modelItemClicks.get(0).setTotal(ExpInvestedCount);
                }else{
                    ExpInvestedCount++;
                    modelItemClicks.get(9).setItemClick(true);
                    modelItemClicks.get(0).setTotal(ExpInvestedCount);
                }

                adapterPropensity.notifyItemChanged(position, "click");
            }
        });

        adapterPropensity.setInvestPopoItemLayout11(new AdapterPropensity.InvestPupoItemLayout11() {
            @Override
            public void onClick(int position) {
                if(modelItemClicks.get(10).isItemClick()){
                    ExpInvestedCount--;
                    modelItemClicks.get(10).setItemClick(false);
                    modelItemClicks.get(0).setTotal(ExpInvestedCount);
                }else{
                    ExpInvestedCount++;
                    modelItemClicks.get(10).setItemClick(true);
                    modelItemClicks.get(0).setTotal(ExpInvestedCount);
                }

                adapterPropensity.notifyItemChanged(position, "click");
            }
        });

        adapterPropensity.setInvestPupoItemLayout12(new AdapterPropensity.InvestPupoItemLayout12() {
            @Override
            public void onClick(int position) {
                if(modelItemClicks.get(11).isItemClick()){
                    ExpInvestedCount--;
                    modelItemClicks.get(11).setItemClick(false);
                    modelItemClicks.get(0).setTotal(ExpInvestedCount);
                }else{
                    ExpInvestedCount++;
                    modelItemClicks.get(11).setItemClick(true);
                    modelItemClicks.get(0).setTotal(ExpInvestedCount);
                }

                adapterPropensity.notifyItemChanged(position, "click");
            }
        });

        adapterPropensity.setInvestPupoItemLayout13(new AdapterPropensity.InvestPupoItemLayout13() {
            @Override
            public void onClick(int position) {
                if(modelItemClicks.get(12).isItemClick()){
                    ExpInvestedCount--;
                    modelItemClicks.get(12).setItemClick(false);
                    modelItemClicks.get(0).setTotal(ExpInvestedCount);
                }else{
                    ExpInvestedCount++;
                    modelItemClicks.get(12).setItemClick(true);
                    modelItemClicks.get(0).setTotal(ExpInvestedCount);
                }

                adapterPropensity.notifyItemChanged(position, "click");
            }
        });


        adapterPropensity.setInvestPupoItemOkClick(new AdapterPropensity.InvestPupoItemOkClick() {
            @Override
            public void onClick(int position) {
//                adapterPropensity.notifyItemChanged(position, "closed");
//
//                String title="";
//                for(ModelItemClick modelItemClick : modelItemClicks){
//                    if(modelItemClick.isItemClick()){
//                        title = title +"\n"+modelItemClick.getTitle();
//                    }
//                }

//                if(title.equals("")){
//                    Toast.makeText(ActivityPropensity.this, "최소 1개 이상을 선택해 주세요", Toast.LENGTH_SHORT).show();
//                }else{
//                    if(modelPropensityClicks.get(3).isClick()){
//                        modelPropensities.get(modelPropensityClicks.get(3).getPosition()).setText(title);
//                        adapterPropensity.notifyItemChanged(modelPropensityClicks.get(3).getPosition());
//                    }else{
//                        modelPropensityClicks.get(3).setClick(true);
//
//                        modelPropensities.add(new ModelPropensity(13, title));
//                        adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);
//
//                        modelPropensityClicks.get(3).setPosition(adapterPropensity.getItemCount()-1);
//
//                        modelPropensities.add(new ModelPropensity(6, ""));
//                        adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);
//                        recyclerView.smoothScrollToPosition(adapterPropensity.getItemCount()-1);
//                    }
//                }

//                if(modelItemClicks.get(0).getTotal() > 0){
//                    if (modelPropensityClicks.get(3).isClick()) {
//                        modelPropensities.get(modelPropensityClicks.get(3).getPosition()).setText("");
//                        adapterPropensity.notifyItemChanged(modelPropensityClicks.get(3).getPosition());
//                    } else {
//                        modelPropensityClicks.get(3).setClick(true);
//
//                        modelPropensities.add(new ModelPropensity(13, ""));
//                        adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount() - 1);
//
//                        modelPropensityClicks.get(3).setPosition(adapterPropensity.getItemCount() - 1);
//
//                        modelPropensities.add(new ModelPropensity(6, ""));
//                        adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount() - 1);
//                        recyclerView.smoothScrollToPosition(adapterPropensity.getItemCount() - 1);
//                    }
//                }else{
//                    Toast.makeText(ActivityPropensity.this, "최소 1개 이상을 선택해 주세요", Toast.LENGTH_SHORT).show();
//                }

            }
        });

        ////////////////


        ///////////////중복 아이템 기간
        adapterPropensity.setInvestItemDateOneClick(new AdapterPropensity.InvestItemDateOneClick() {
            @Override
            public void onClick(int position) {
                InvestItemDate("1년미만");
            }
        });
        adapterPropensity.setInvestItemDateTwoClick(new AdapterPropensity.InvestItemDateTwoClick() {
            @Override
            public void onClick(int position) {
                InvestItemDate("1년~3년 미만");
            }
        });
        adapterPropensity.setInvestItemDateThreeClick(new AdapterPropensity.InvestItemDateThreeClick() {
            @Override
            public void onClick(int position) {
                InvestItemDate("3년 이상");
            }
        });
        ///////////////


        adapterPropensity.setInvestMddRate10Click(new AdapterPropensity.InvestMddRate10Click() {
            @Override
            public void onClick(int position) {
                InvestMddRate("원금은 지켜야해요!");
            }
        });
        adapterPropensity.setInvestMddRate20Click(new AdapterPropensity.InvestMddRate20Click() {
            @Override
            public void onClick(int position) {
                InvestMddRate("원금을 지키고 싶지만 수익이 더 중요합니다.");
            }
        });
        adapterPropensity.setInvestMddRate30Click(new AdapterPropensity.InvestMddRate30Click() {
            @Override
            public void onClick(int position) {
                InvestMddRate("수익이 높다면 위험해도 상관없어요.");
            }
        });


        adapterPropensity.setFinanceKnowOneClick(new AdapterPropensity.FinanceKnowOneClick() {
            @Override
            public void onClick(int position) {
                FinanceKnow("입문자\n(투자는 처음이에요)");
            }
        });
        adapterPropensity.setFinanceKnowTwoClick(new AdapterPropensity.FinanceKnowTwoClick() {
            @Override
            public void onClick(int position) {
                FinanceKnow("초보자\n(주식,채권,펀드의 차이는 구별해요)");
            }
        });
        adapterPropensity.setFinanceKnowThreeClick(new AdapterPropensity.FinanceKnowThreeClick() {
            @Override
            public void onClick(int position) {
                FinanceKnow("숙련자\n(대부분의 투자상품은 구별해요)");
            }
        });
        adapterPropensity.setFinanceKnowForeClick(new AdapterPropensity.FinanceKnowForeClick() {
            @Override
            public void onClick(int position) {
                FinanceKnow("마스터\n(파생상품을 비롯해 대부분 알고있어요)");
            }
        });


        adapterPropensity.setYieldClick1(new AdapterPropensity.YieldClick1() {
            @Override
            public void onClick(int position) {
                Yield("은행이자 수준의 수익 기대\n(제한적 손실 감수)");
            }
        });

        adapterPropensity.setYieldClick2(new AdapterPropensity.YieldClick2() {
            @Override
            public void onClick(int position) {
                Yield("은행이자보다 높은 수익 기대\n(약간의 원금 손실 감수)");
            }
        });

        adapterPropensity.setYieldClick3(new AdapterPropensity.YieldClick3() {
            @Override
            public void onClick(int position) {
                Yield("시장 수익률 수준의 수익 기대\n(원금 손실 감수)");
            }
        });

        adapterPropensity.setYieldClick4(new AdapterPropensity.YieldClick4() {
            @Override
            public void onClick(int position) {
                Yield("시장 수익률보다 높은 수익률 기대\n(원금보다 큰 손실도 감수)");
            }
        });


        adapterPropensity.setInvestDeriExpOneClick(new AdapterPropensity.InvestDeriExpOneClick() {
            @Override
            public void onClick(int position) {
                InvestDeriExp("1년 미만 (경험 없음)");
            }
        });
        adapterPropensity.setInvestDeriExpTwoClick(new AdapterPropensity.InvestDeriExpTwoClick() {
            @Override
            public void onClick(int position) {
                InvestDeriExp("1년 이상 ~ 3년 미만");
            }
        });
        adapterPropensity.setInvestDeriExpThreeClick(new AdapterPropensity.InvestDeriExpThreeClick() {
            @Override
            public void onClick(int position) {
                InvestDeriExp("3년 이상");
            }
        });


        RxView.clicks(yesBt3).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(empty -> {
            ViewAnimator.animate(bottomLayout3).translationY(-dp, 0).alpha(1, 0).duration(400).start();

            removePosition = adapterPropensity.getItemCount()-1;
            modelPropensities.remove(removePosition);
            adapterPropensity.notifyItemRemoved(removePosition);
            adapterPropensity.notifyItemRangeChanged(removePosition, adapterPropensity.getItemCount());

            modelPropensities.add(new ModelPropensity(13, "취약 금융소비자에 해당"));
            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);

            modelPropensities.add(new ModelPropensity(12, ""));
            modelPropensities.add(new ModelPropensity(20, ""));

            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);
            recyclerView.smoothScrollToPosition(adapterPropensity.getItemCount()-1);

            ViewAnimator.animate(bottomLayout4).translationY(0, -dp).alpha(0, 1).duration(1000).start();
        });

        RxView.clicks(noBt3).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(empty -> {
            ViewAnimator.animate(bottomLayout3).translationY(-dp, 0).alpha(1, 0).duration(400).start();

            removePosition = adapterPropensity.getItemCount()-1;
            modelPropensities.remove(removePosition);
            adapterPropensity.notifyItemRemoved(removePosition);
            adapterPropensity.notifyItemRangeChanged(removePosition, adapterPropensity.getItemCount());

            modelPropensities.add(new ModelPropensity(13, "해당사항 없음"));
            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);

            modelPropensities.add(new ModelPropensity(12, ""));
            modelPropensities.add(new ModelPropensity(20, ""));

            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);
            recyclerView.smoothScrollToPosition(adapterPropensity.getItemCount()-1);

            ViewAnimator.animate(bottomLayout4).translationY(0, -dp).alpha(0, 1).duration(1000).start();
        });

        RxView.clicks(yesBt4).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(empty -> {
//            ViewAnimator.animate(bottomLayout4).translationY(-dp, 0).alpha(1, 0).duration(400).start();
//            removePosition = adapterPropensity.getItemCount()-1;
//            modelPropensities.remove(removePosition);
//            adapterPropensity.notifyItemRemoved(removePosition);
//            adapterPropensity.notifyItemRangeChanged(removePosition, adapterPropensity.getItemCount());

            if (dupliItemCheck) {

                String name = "";
                for (ModelItemClick modelItemClick : modelItemClicks) {
                    if (modelItemClick.isItemClick()) {
                        name = name + "\n" + modelItemClick.getTitle();
                    }
                }
                if (name.equals("")) {
                    recyclerView.smoothScrollToPosition(9);
                    Toast.makeText(ActivityPropensity.this, "최소 1개 이상의 상품을 선택해 주세요.", Toast.LENGTH_SHORT).show();
                } else {
//                    insertDb();
                    Toast.makeText(ActivityPropensity.this, "확인", Toast.LENGTH_SHORT).show();

//                    removePosition = adapterPropensity.getItemCount()-1;
//                    modelPropensities.remove(removePosition);
//                    adapterPropensity.notifyItemRemoved(removePosition);
//                    adapterPropensity.notifyItemRangeChanged(removePosition, adapterPropensity.getItemCount());

                    Intent intent = new Intent(ActivityPropensity.this, ActivityPropensityFinish.class);
                    startActivity(intent);

                }

            } else {
//                insertDb();
                Toast.makeText(ActivityPropensity.this, "확인", Toast.LENGTH_SHORT).show();
//                removePosition = adapterPropensity.getItemCount()-1;
//                modelPropensities.remove(removePosition);
//                adapterPropensity.notifyItemRemoved(removePosition);
//                adapterPropensity.notifyItemRangeChanged(removePosition, adapterPropensity.getItemCount());

                Intent intent = new Intent(ActivityPropensity.this, ActivityPropensityFinish.class);
                startActivity(intent);
            }


//            modelPropensities.add(new ModelPropensity(13, "동의합니다"));
//            modelPropensities.add(new ModelPropensity(20, ""));
//
//            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);
//            recyclerView.smoothScrollToPosition(adapterPropensity.getItemCount()-1);

//            ViewAnimator.animate(yesBt5).translationY(0, -dp).alpha(0, 1).duration(1000).start();
        });

//        RxView.clicks(yesBt5).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(empty -> {
//
//            if(dupliItemCheck){
//
//                String name = "";
//                for(ModelItemClick modelItemClick : modelItemClicks){
//                    if(modelItemClick.isItemClick()){
//                        name = name +"\n"+modelItemClick.getTitle();
//                    }
//                }
//                if(name.equals("")){
//                    recyclerView.smoothScrollToPosition(9);
//                    Toast.makeText(ActivityPropensity.this,"최소 1개 이상의 상품을 선택해 주세요.", Toast.LENGTH_SHORT).show();
//                }else{
////                    insertDb();
//                    Toast.makeText(ActivityPropensity.this, "확인", Toast.LENGTH_SHORT).show();
//                }
//
//            }else{
////                insertDb();
//                Toast.makeText(ActivityPropensity.this, "확인", Toast.LENGTH_SHORT).show();
//            }
//
//        });

    }// onCreate 끝

    void YearInput(String title){
        if(modelPropensityClicks.get(0).isClick()){

            modelPropensities.get(modelPropensityClicks.get(0).getPosition()).setText(title);
            adapterPropensity.notifyItemChanged(modelPropensityClicks.get(0).getPosition());

        }else{
            modelPropensityClicks.get(0).setClick(true);

            modelPropensities.add(new ModelPropensity(13, title));
            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);
            recyclerView.smoothScrollToPosition(adapterPropensity.getItemCount()-1);

            modelPropensityClicks.get(0).setPosition(adapterPropensity.getItemCount()-1);

            modelPropensities.add(new ModelPropensity(3, ""));
            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);
            recyclerView.smoothScrollToPosition(adapterPropensity.getItemCount()-1);
        }
    }

    void FinanceRate(String title){
        if(modelPropensityClicks.get(1).isClick()){
            modelPropensities.get(modelPropensityClicks.get(1).getPosition()).setText(title);
            adapterPropensity.notifyItemChanged(modelPropensityClicks.get(1).getPosition());
        }else{
            modelPropensityClicks.get(1).setClick(true);

            modelPropensities.add(new ModelPropensity(13, title));
            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);
            recyclerView.smoothScrollToPosition(adapterPropensity.getItemCount()-1);

            modelPropensityClicks.get(1).setPosition(adapterPropensity.getItemCount()-1);

            modelPropensities.add(new ModelPropensity(4, ""));
            modelPropensities.add(new ModelPropensity(20, ""));

            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);
            recyclerView.smoothScrollToPosition(adapterPropensity.getItemCount()-1);

            ViewAnimator.animate(bottomLayout2).translationY(0, -dp).alpha(0, 1).duration(1000).start();
        }
    }

    void InvestPupo(String title){

        if(modelPropensityClicks.get(2).isClick()){
            modelPropensities.get(modelPropensityClicks.get(2).getPosition()).setText(title);
            adapterPropensity.notifyItemChanged(modelPropensityClicks.get(2).getPosition());
        }else{
            modelPropensityClicks.get(2).setClick(true);

            modelPropensities.add(new ModelPropensity(13, title));
            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);
            recyclerView.smoothScrollToPosition(adapterPropensity.getItemCount()-1);

            modelPropensityClicks.get(2).setPosition(adapterPropensity.getItemCount()-1);

            modelPropensities.add(new ModelPropensity(8, ""));
            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);
            recyclerView.smoothScrollToPosition(adapterPropensity.getItemCount()-1);
        }
    }

    void InvestItemDate(String title){

        if(modelPropensityClicks.get(4).isClick()){
            modelPropensities.get(modelPropensityClicks.get(4).getPosition()).setText(title);
            adapterPropensity.notifyItemChanged(modelPropensityClicks.get(4).getPosition());
        }else{
            modelPropensityClicks.get(4).setClick(true);

            modelPropensities.add(new ModelPropensity(13, title));
            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);
            recyclerView.smoothScrollToPosition(adapterPropensity.getItemCount()-1);

            modelPropensityClicks.get(4).setPosition(adapterPropensity.getItemCount()-1);

            modelPropensities.add(new ModelPropensity(7, ""));
            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);
            recyclerView.smoothScrollToPosition(adapterPropensity.getItemCount()-1);
        }
    }

    void InvestMddRate(String title){

        if(modelPropensityClicks.get(5).isClick()){
            modelPropensities.get(modelPropensityClicks.get(5).getPosition()).setText(title);
            adapterPropensity.notifyItemChanged(modelPropensityClicks.get(5).getPosition());
        }else{
            modelPropensityClicks.get(5).setClick(true);

            modelPropensities.add(new ModelPropensity(13, title));
            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);
            recyclerView.smoothScrollToPosition(adapterPropensity.getItemCount()-1);

            modelPropensityClicks.get(5).setPosition(adapterPropensity.getItemCount()-1);

            modelPropensities.add(new ModelPropensity(9, ""));
            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);
            recyclerView.smoothScrollToPosition(adapterPropensity.getItemCount()-1);
        }
    }

    void FinanceKnow(String title){

        if(modelPropensityClicks.get(6).isClick()){
            modelPropensities.get(modelPropensityClicks.get(6).getPosition()).setText(title);
            adapterPropensity.notifyItemChanged(modelPropensityClicks.get(6).getPosition());
        }else{
            modelPropensityClicks.get(6).setClick(true);

            modelPropensities.add(new ModelPropensity(13, title));
            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);
            recyclerView.smoothScrollToPosition(adapterPropensity.getItemCount()-1);

            modelPropensityClicks.get(6).setPosition(adapterPropensity.getItemCount()-1);

            modelPropensities.add(new ModelPropensity(14, ""));
            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);
            recyclerView.smoothScrollToPosition(adapterPropensity.getItemCount()-1);
        }
    }


    void Yield(String title){
        if(modelPropensityClicks.get(8).isClick()){
            modelPropensities.get(modelPropensityClicks.get(8).getPosition()).setText(title);
            adapterPropensity.notifyItemChanged(modelPropensityClicks.get(8).getPosition());
        }else{
            modelPropensityClicks.get(8).setClick(true);

            modelPropensities.add(new ModelPropensity(13, title));
            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);
            recyclerView.smoothScrollToPosition(adapterPropensity.getItemCount()-1);

            modelPropensityClicks.get(8).setPosition(adapterPropensity.getItemCount()-1);

            modelPropensities.add(new ModelPropensity(10, ""));
            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);
            recyclerView.smoothScrollToPosition(adapterPropensity.getItemCount()-1);
        }
    }

    void InvestDeriExp(String title){

        if(modelPropensityClicks.get(7).isClick()){
            modelPropensities.get(modelPropensityClicks.get(7).getPosition()).setText(title);
            adapterPropensity.notifyItemChanged(modelPropensityClicks.get(7).getPosition());
        }else{
            modelPropensityClicks.get(7).setClick(true);

            modelPropensities.add(new ModelPropensity(13, title));
            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);
            recyclerView.smoothScrollToPosition(adapterPropensity.getItemCount()-1);

            modelPropensityClicks.get(7).setPosition(adapterPropensity.getItemCount()-1);

            modelPropensities.add(new ModelPropensity(11, ""));
            modelPropensities.add(new ModelPropensity(20, ""));

            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);
            recyclerView.smoothScrollToPosition(adapterPropensity.getItemCount()-1);

            ViewAnimator.animate(bottomLayout3).translationY(0, -dp).alpha(0, 1).duration(1000).start();
        }
    }


    void insertDb(){

        Call<ModelSearchDb> getReList = RetrofitClient.getInstance(ActivityPropensity.this).getService().getStockDb();
        getReList.enqueue(new Callback<ModelSearchDb>() {
            @Override
            public void onResponse(Call<ModelSearchDb> call, Response<ModelSearchDb> response) {
                if (response.code() == 200) {

                    searchViewModel = ViewModelProviders.of(ActivityPropensity.this).get(SearchViewModel2.class);
                    roomDao = SearchRoomDatabase.getINSTANCE(ActivityPropensity.this).roomDao();
                    DeleteAll();

                    for (int index = 0; index < response.body().getContent().getRateList().size(); index++) {

                        String elCodes = "";
                        String descript = "";
                        int follow = 0;

                        if(response.body().getContent().getRateList().get(index).getUserSelect() != null){
                            follow = response.body().getContent().getRateList().get(index).getUserSelect().getIsFollow();
                        }else{
                            follow = 0;
                        }

                        if(response.body().getContent().getRateList().get(index).getElCodes() != null){
                            elCodes = response.body().getContent().getRateList().get(index).getElCodes().toString();
                        }else{
                            elCodes = "";
                        }

                        if(response.body().getContent().getRateList().get(index).getSearchField() != null){
                            descript = response.body().getContent().getRateList().get(index).getSearchField().toString();
                        }else{
                            descript = "";
                        }

                        RoomDataInsert("",
                                response.body().getContent().getRateList().get(index).getType(),
                                response.body().getContent().getRateList().get(index).getCode(),
                                response.body().getContent().getRateList().get(index).getName(),
                                elCodes,
                                descript,
                                response.body().getContent().getRateList().get(index).getRate(),
                                follow);
                    }

                    Intent intent1 = new Intent(ActivityPropensity.this, ActivityMain.class);
                    startActivity(intent1);
                    finish();
                }
            }
            @Override
            public void onFailure(Call<ModelSearchDb> call, Throwable t) {
                Log.e("실패","실패"+t.getMessage());
            }
        });
    }

    //최근 검색어 저장 이벤트
    void RoomDataInsert(String title, int type, String code, String name, String elStock, String descript, double rate, int follow){

        new Thread(new Runnable() {
            @Override
            public void run() {
                searchViewModel.insert2(new RoomEntity2(title, type, code, name, elStock, descript, rate, follow));
            }
        }).start();
    }

    //딜리트 올
    void DeleteAll(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                roomDao.delete2();
            }
        }).start();
    }


    private View.OnClickListener okClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            dialogPropensitySkeep.dismiss();
        }
    };
    private View.OnClickListener noClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            dialogPropensitySkeep.dismiss();
        }
    };

    private View.OnClickListener closeBtClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            dialogWeekFinance.dismiss();
        }
    };


}


//public class ActivityPropensity extends AppCompatActivity {
//
//    RecyclerView recyclerView;
//    RecyclerView.LayoutManager linearLayoutManager;
//    ArrayList<ModelPropensity> modelPropensities;
//    AdapterPropensity adapterPropensity;
//
//    LinearLayout bottomLayout, bottomLayout2, bottomLayout3, bottomLayout4;
//    TextView yesBt, noBt, yesBt2, noBt2, yesBt3, noBt3, yesBt4, yesBt5;
//
//    int removePosition;
//    boolean dupliItemCheck = false;
//
//    ArrayList<ModelItemClick> modelItemClicks;
//    ArrayList<ModelPropensityClick> modelPropensityClicks;
//
//    float dp;
//
//    private SearchViewModel2 searchViewModel;
//
//    private RoomDao roomDao;
//    private RoomEntity2 RoomSelectCode;
//    private List<RoomEntity2> RoomAllData;
//
//    private List<RoomEntity2> roomEntity2s;
//
//    private DialogPropensitySkeep dialogPropensitySkeep;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_propensity);
//
//        Resources res = getResources();
//        String[] title = res.getStringArray(R.array.propensity);
//
//        modelPropensityClicks = new ArrayList<>();
//        for(int index = 0; index < 8; index++){
//            modelPropensityClicks.add(new ModelPropensityClick(false, 0));
//        }
//
//        modelItemClicks = new ArrayList<>();
//        for(int index = 0; index < 10 ; index++){
//            modelItemClicks.add(new ModelItemClick(false, title[index]));
//        }
//
//        bottomLayout = findViewById(R.id.bottomLayout);
//        bottomLayout2 = findViewById(R.id.bottomLayout2);
//        bottomLayout3 = findViewById(R.id.bottomLayout3);
//        bottomLayout4 = findViewById(R.id.bottomLayout4);
//
//        yesBt = findViewById(R.id.yesBt);
//        noBt = findViewById(R.id.noBt);
//        yesBt2 = findViewById(R.id.yesBt2);
//        noBt2 = findViewById(R.id.noBt2);
//        yesBt3 = findViewById(R.id.yesBt3);
//        noBt3 = findViewById(R.id.noBt3);
//        yesBt4 = findViewById(R.id.yesBt4);
//        yesBt5 = findViewById(R.id.yesBt5);
//
//        recyclerView = findViewById(R.id.recyclerView);
//        recyclerView.setHasFixedSize(true);
//
//        linearLayoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(linearLayoutManager);
//
//        modelPropensities = new ArrayList<>();
//
//        DisplayMetrics px = getResources().getDisplayMetrics();
//        float dpt = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, px);
//
//        adapterPropensity = new AdapterPropensity(modelPropensities, this, dpt, modelItemClicks);
//        recyclerView.setAdapter(adapterPropensity);
//
//        modelPropensities.add(new ModelPropensity(0, ""));
//        modelPropensities.add(new ModelPropensity(1, ""));
//
//        adapterPropensity.notifyDataSetChanged();
//
//        DisplayMetrics dm = getResources().getDisplayMetrics();
//        dp = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 68, dm);
//
//        ViewAnimator.animate(bottomLayout).translationY(0, -dp).alpha(0, 1).duration(1000).start();
//
//
//        RxView.clicks(yesBt).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(empty -> {
//            modelPropensities.add(new ModelPropensity(13, "권유를 원함"));
//            ViewAnimator.animate(bottomLayout).translationY(-dp, 0).alpha(1, 0).duration(400).start();
//            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);
//
//            modelPropensities.add(new ModelPropensity(2, ""));
//            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);
//            recyclerView.smoothScrollToPosition(adapterPropensity.getItemCount()-1);
//        });
//
//        RxView.clicks(noBt).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(empty -> {
////            ViewAnimator.animate(bottomLayout).translationY(-dp, 0).alpha(1, 0).duration(400).start();
////            modelPropensities.add(new ModelPropensity(13, "권유를 원하지 않음"));
////            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);
////            recyclerView.smoothScrollToPosition(adapterPropensity.getItemCount()-1);
////
////            modelPropensities.add(new ModelPropensity(2, ""));
////            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);
////            recyclerView.smoothScrollToPosition(adapterPropensity.getItemCount()-1);
//
//            dialogPropensitySkeep = new DialogPropensitySkeep(ActivityPropensity.this,
//                    okClickListener, noClickListener);
//            dialogPropensitySkeep.show();
//
//        });
//
//
//        ///향후 연 수입원////
//        adapterPropensity.setYearInputOneClick(new AdapterPropensity.YearInputOneClick() {
//            @Override
//            public void onClick(int position) {
//                YearInput("일정하지 않음");
//            }
//        });
//        adapterPropensity.setYearInputTwoClick(new AdapterPropensity.YearInputTwoClick() {
//            @Override
//            public void onClick(int position) {
//                YearInput("일정하지만, 줄어들 것으로 예상");
//            }
//        });
//        adapterPropensity.setYearInputThreeClick(new AdapterPropensity.YearInputThreeClick() {
//            @Override
//            public void onClick(int position) {
//                YearInput("일정하고 앞으로 더 많이 벌 것");
//            }
//        });
//
//
//        adapterPropensity.setFinanceRate5Click(new AdapterPropensity.FinanceRate5Click() {
//            @Override
//            public void onClick(int position) {
//                FinanceRate("5% 이하");
//            }
//        });
//        adapterPropensity.setFinanceRate10Click(new AdapterPropensity.FinanceRate10Click() {
//            @Override
//            public void onClick(int position) {
//                FinanceRate("10% 이하");
//            }
//        });
//        adapterPropensity.setFinanceRate20Click(new AdapterPropensity.FinanceRate20Click() {
//            @Override
//            public void onClick(int position) {
//                FinanceRate("20% 이하");
//            }
//        });
//        adapterPropensity.setFinanceRate30Click(new AdapterPropensity.FinanceRate30Click() {
//            @Override
//            public void onClick(int position) {
//                FinanceRate("30% 이하");
//            }
//        });
//        adapterPropensity.setFinanceRate30UpClick(new AdapterPropensity.FinanceRate30UpClick() {
//            @Override
//            public void onClick(int position) {
//                FinanceRate("30% 이상");
//            }
//        });
//
//
//
//        RxView.clicks(yesBt2).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(empty -> {
//
//            dupliItemCheck = true;
//
//            removePosition = adapterPropensity.getItemCount() - 1;
//            modelPropensities.remove(removePosition);
//            adapterPropensity.notifyItemRemoved(removePosition);
//            adapterPropensity.notifyItemRangeChanged(removePosition, adapterPropensity.getItemCount());
//
//            modelPropensities.add(new ModelPropensity(13, "있음"));
//            ViewAnimator.animate(bottomLayout2).translationY(-dp, 0).alpha(1, 0).duration(400).start();
//            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount() - 1);
//
//            modelPropensities.add(new ModelPropensity(5, ""));
//            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount() - 1);
//            recyclerView.smoothScrollToPosition(adapterPropensity.getItemCount() - 1);
//        });
//
//        RxView.clicks(noBt2).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(empty -> {
//
//            dupliItemCheck = false;
//
//            removePosition = adapterPropensity.getItemCount()-1;
//            modelPropensities.remove(removePosition);
//            adapterPropensity.notifyItemRemoved(removePosition);
//            adapterPropensity.notifyItemRangeChanged(removePosition, adapterPropensity.getItemCount());
//
//            modelPropensities.add(new ModelPropensity(13, "없음"));
//            ViewAnimator.animate(bottomLayout2).translationY(-dp, 0).alpha(1, 0).duration(400).start();
//            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);
//
//            modelPropensities.add(new ModelPropensity(7, ""));
//            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);
//            recyclerView.smoothScrollToPosition(adapterPropensity.getItemCount()-1);
//        });
//
//
//        adapterPropensity.setInvestPupoOneClick(new AdapterPropensity.InvestPupoOneClick() {
//            @Override
//            public void onClick(int position) {
//                InvestPupo("높은 원금 보존 + 약간의 수익");
//            }
//        });
//        adapterPropensity.setInvestPupoTwoClick(new AdapterPropensity.InvestPupoTwoClick() {
//            @Override
//            public void onClick(int position) {
//                InvestPupo("최소한의 원금 보존 + 좀 더 높은 수익");
//            }
//        });
//        adapterPropensity.setInvestPupoThreeClick(new AdapterPropensity.InvestPupoThreeClick() {
//            @Override
//            public void onclick(int position) {
//                InvestPupo("약간의 손실 + 아주 높은 수익");
//            }
//        });
//
//        ////////////////투자목적 아이템 중복 선택
//        adapterPropensity.setInvestPupoItemOneClick(new AdapterPropensity.InvestPupoItemOneClick() {
//            @Override
//            public void onClick(int position) {
//
//                if(modelItemClicks.get(0).isItemClick()){
//                    modelItemClicks.get(0).setItemClick(false);
//                }else{
//                    modelItemClicks.get(0).setItemClick(true);
//                }
//
//                adapterPropensity.notifyItemChanged(position, "click");
//            }
//        });
//        adapterPropensity.setInvestPupoItemTwoClick(new AdapterPropensity.InvestPupoItemTwoClick() {
//            @Override
//            public void onClick(int position) {
//
//                if(modelItemClicks.get(1).isItemClick()){
//                    modelItemClicks.get(1).setItemClick(false);
//                }else{
//                    modelItemClicks.get(1).setItemClick(true);
//                }
//
//                adapterPropensity.notifyItemChanged(position, "click");
//            }
//        });
//        adapterPropensity.setInvestPupoItemThreeClick(new AdapterPropensity.InvestPupoItemThreeClick() {
//            @Override
//            public void onClick(int position) {
//
//                if(modelItemClicks.get(2).isItemClick()){
//                    modelItemClicks.get(2).setItemClick(false);
//                }else{
//                    modelItemClicks.get(2).setItemClick(true);
//                }
//
//                adapterPropensity.notifyItemChanged(position, "click");
//            }
//        });
//        adapterPropensity.setInvestPupoItemForeClick(new AdapterPropensity.InvestPupoItemForeClick() {
//            @Override
//            public void onClick(int position) {
//
//                if(modelItemClicks.get(3).isItemClick()){
//                    modelItemClicks.get(3).setItemClick(false);
//                }else{
//                    modelItemClicks.get(3).setItemClick(true);
//                }
//
//                adapterPropensity.notifyItemChanged(position, "click");
//            }
//        });
//        adapterPropensity.setInvestPupoItemFiveClick(new AdapterPropensity.InvestPupoItemFiveClick() {
//            @Override
//            public void onClick(int position) {
//
//                if(modelItemClicks.get(4).isItemClick()){
//                    modelItemClicks.get(4).setItemClick(false);
//                }else{
//                    modelItemClicks.get(4).setItemClick(true);
//                }
//
//                adapterPropensity.notifyItemChanged(position, "click");
//            }
//        });
//        adapterPropensity.setInvestPupoItemSixClick(new AdapterPropensity.InvestPupoItemSixClick() {
//            @Override
//            public void onClick(int position) {
//
//                if(modelItemClicks.get(5).isItemClick()){
//                    modelItemClicks.get(5).setItemClick(false);
//                }else{
//                    modelItemClicks.get(5).setItemClick(true);
//                }
//
//                adapterPropensity.notifyItemChanged(position, "click");
//            }
//        });
//        adapterPropensity.setInvestPupoItemSevenClick(new AdapterPropensity.InvestPupoItemSevenClick() {
//            @Override
//            public void onClick(int position) {
//
//                if(modelItemClicks.get(6).isItemClick()){
//                    modelItemClicks.get(6).setItemClick(false);
//                }else{
//                    modelItemClicks.get(6).setItemClick(true);
//                }
//
//                adapterPropensity.notifyItemChanged(position, "click");
//            }
//        });
//        adapterPropensity.setInvestPupoItemEightClick(new AdapterPropensity.InvestPupoItemEightClick() {
//            @Override
//            public void onClick(int position) {
//
//                if(modelItemClicks.get(7).isItemClick()){
//                    modelItemClicks.get(7).setItemClick(false);
//                }else{
//                    modelItemClicks.get(7).setItemClick(true);
//                }
//
//                adapterPropensity.notifyItemChanged(position, "click");
//            }
//        });
//        adapterPropensity.setInvestPupoItemNineClick(new AdapterPropensity.InvestPupoItemNineClick() {
//            @Override
//            public void onClick(int position) {
//
//                if(modelItemClicks.get(8).isItemClick()){
//                    modelItemClicks.get(8).setItemClick(false);
//                }else{
//                    modelItemClicks.get(8).setItemClick(true);
//                }
//
//                adapterPropensity.notifyItemChanged(position, "click");
//            }
//        });
//        adapterPropensity.setInvestPupoItemTenClick(new AdapterPropensity.InvestPupoItemTenClick() {
//            @Override
//            public void onClick(int position) {
//
//                if(modelItemClicks.get(9).isItemClick()){
//                    modelItemClicks.get(9).setItemClick(false);
//                }else{
//                    modelItemClicks.get(9).setItemClick(true);
//                }
//
//                adapterPropensity.notifyItemChanged(position, "click");
//            }
//        });
//
//        adapterPropensity.setInvestPupoItemOkClick(new AdapterPropensity.InvestPupoItemOkClick() {
//            @Override
//            public void onClick(int position) {
//                adapterPropensity.notifyItemChanged(position, "closed");
//
//                String title="";
//                for(ModelItemClick modelItemClick : modelItemClicks){
//                    if(modelItemClick.isItemClick()){
//                        title = title +"\n"+modelItemClick.getTitle();
//                    }
//                }
//
//                if(title.equals("")){
//                    Toast.makeText(ActivityPropensity.this, "최소 1개 이상을 선택해 주세요", Toast.LENGTH_SHORT).show();
//                }else{
//                    if(modelPropensityClicks.get(3).isClick()){
//                        modelPropensities.get(modelPropensityClicks.get(3).getPosition()).setText(title);
//                        adapterPropensity.notifyItemChanged(modelPropensityClicks.get(3).getPosition());
//                    }else{
//                        modelPropensityClicks.get(3).setClick(true);
//
//                        modelPropensities.add(new ModelPropensity(13, title));
//                        adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);
//
//                        modelPropensityClicks.get(3).setPosition(adapterPropensity.getItemCount()-1);
//
//                        modelPropensities.add(new ModelPropensity(6, ""));
//                        adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);
//                        recyclerView.smoothScrollToPosition(adapterPropensity.getItemCount()-1);
//                    }
//                }
//            }
//        });
//
//        ////////////////
//
//
//        ///////////////중복 아이템 기간
//        adapterPropensity.setInvestItemDateOneClick(new AdapterPropensity.InvestItemDateOneClick() {
//            @Override
//            public void onClick(int position) {
//                InvestItemDate("1년미만");
//            }
//        });
//        adapterPropensity.setInvestItemDateTwoClick(new AdapterPropensity.InvestItemDateTwoClick() {
//            @Override
//            public void onClick(int position) {
//                InvestItemDate("1년~3년 미만");
//            }
//        });
//        adapterPropensity.setInvestItemDateThreeClick(new AdapterPropensity.InvestItemDateThreeClick() {
//            @Override
//            public void onClick(int position) {
//                InvestItemDate("3년 이상");
//            }
//        });
//        ///////////////
//
//
//        adapterPropensity.setInvestMddRate10Click(new AdapterPropensity.InvestMddRate10Click() {
//            @Override
//            public void onClick(int position) {
//                InvestMddRate("10% 이하");
//            }
//        });
//        adapterPropensity.setInvestMddRate20Click(new AdapterPropensity.InvestMddRate20Click() {
//            @Override
//            public void onClick(int position) {
//                InvestMddRate("20% 이하");
//            }
//        });
//        adapterPropensity.setInvestMddRate30Click(new AdapterPropensity.InvestMddRate30Click() {
//            @Override
//            public void onClick(int position) {
//                InvestMddRate("30% 이하");
//            }
//        });
//        adapterPropensity.setInvestMddRate40Click(new AdapterPropensity.InvestMddRate40Click() {
//            @Override
//            public void onClick(int position) {
//                InvestMddRate("40% 이하");
//            }
//        });
//        adapterPropensity.setInvestMddRateUpClick(new AdapterPropensity.InvestMddRateUpClick() {
//            @Override
//            public void onClick(int position) {
//                InvestMddRate("높은 수익이라면 상관 없음");
//            }
//        });
//
//
//        adapterPropensity.setFinanceKnowOneClick(new AdapterPropensity.FinanceKnowOneClick() {
//            @Override
//            public void onClick(int position) {
//                FinanceKnow("예/적금 외 투자 경험 없음");
//            }
//        });
//        adapterPropensity.setFinanceKnowTwoClick(new AdapterPropensity.FinanceKnowTwoClick() {
//            @Override
//            public void onClick(int position) {
//                FinanceKnow("주식, 채권, 펀드에 대한 설명을 통해 투자 결정 가능");
//            }
//        });
//        adapterPropensity.setFinanceKnowThreeClick(new AdapterPropensity.FinanceKnowThreeClick() {
//            @Override
//            public void onClick(int position) {
//                FinanceKnow("투자 상품에 대한 설명 없이 투자 가능");
//            }
//        });
//        adapterPropensity.setFinanceKnowForeClick(new AdapterPropensity.FinanceKnowForeClick() {
//            @Override
//            public void onClick(int position) {
//                FinanceKnow("파생상품 포함 모든 금융상품을 이해함");
//            }
//        });
//
//
//        adapterPropensity.setInvestDeriExpOneClick(new AdapterPropensity.InvestDeriExpOneClick() {
//            @Override
//            public void onClick(int position) {
//                InvestDeriExp("1년 미만 (경험 없음)");
//            }
//        });
//        adapterPropensity.setInvestDeriExpTwoClick(new AdapterPropensity.InvestDeriExpTwoClick() {
//            @Override
//            public void onClick(int position) {
//                InvestDeriExp("1년 이상 ~ 3년 미만");
//            }
//        });
//        adapterPropensity.setInvestDeriExpThreeClick(new AdapterPropensity.InvestDeriExpThreeClick() {
//            @Override
//            public void onClick(int position) {
//                InvestDeriExp("3년 이상");
//            }
//        });
//
//
//        RxView.clicks(yesBt3).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(empty -> {
//            ViewAnimator.animate(bottomLayout3).translationY(-dp, 0).alpha(1, 0).duration(400).start();
//
//            removePosition = adapterPropensity.getItemCount()-1;
//            modelPropensities.remove(removePosition);
//            adapterPropensity.notifyItemRemoved(removePosition);
//            adapterPropensity.notifyItemRangeChanged(removePosition, adapterPropensity.getItemCount());
//
//            modelPropensities.add(new ModelPropensity(13, "취약 금융소비자에 해당"));
//            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);
//
//            modelPropensities.add(new ModelPropensity(12, ""));
//            modelPropensities.add(new ModelPropensity(20, ""));
//
//            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);
//            recyclerView.smoothScrollToPosition(adapterPropensity.getItemCount()-1);
//
//            ViewAnimator.animate(bottomLayout4).translationY(0, -dp).alpha(0, 1).duration(1000).start();
//        });
//
//        RxView.clicks(noBt3).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(empty -> {
//            ViewAnimator.animate(bottomLayout3).translationY(-dp, 0).alpha(1, 0).duration(400).start();
//
//            removePosition = adapterPropensity.getItemCount()-1;
//            modelPropensities.remove(removePosition);
//            adapterPropensity.notifyItemRemoved(removePosition);
//            adapterPropensity.notifyItemRangeChanged(removePosition, adapterPropensity.getItemCount());
//
//            modelPropensities.add(new ModelPropensity(13, "해당사항 없음"));
//            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);
//
//            modelPropensities.add(new ModelPropensity(12, ""));
//            modelPropensities.add(new ModelPropensity(20, ""));
//
//            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);
//            recyclerView.smoothScrollToPosition(adapterPropensity.getItemCount()-1);
//
//            ViewAnimator.animate(bottomLayout4).translationY(0, -dp).alpha(0, 1).duration(1000).start();
//        });
//
//        RxView.clicks(yesBt4).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(empty -> {
//            ViewAnimator.animate(bottomLayout4).translationY(-dp, 0).alpha(1, 0).duration(400).start();
//
//            removePosition = adapterPropensity.getItemCount()-1;
//            modelPropensities.remove(removePosition);
//            adapterPropensity.notifyItemRemoved(removePosition);
//            adapterPropensity.notifyItemRangeChanged(removePosition, adapterPropensity.getItemCount());
//
//            modelPropensities.add(new ModelPropensity(13, "동의합니다"));
//            modelPropensities.add(new ModelPropensity(20, ""));
//
//            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);
//            recyclerView.smoothScrollToPosition(adapterPropensity.getItemCount()-1);
//
//            ViewAnimator.animate(yesBt5).translationY(0, -dp).alpha(0, 1).duration(1000).start();
//        });
//
//        RxView.clicks(yesBt5).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(empty -> {
//
//            if(dupliItemCheck){
//
//                String name = "";
//                for(ModelItemClick modelItemClick : modelItemClicks){
//                    if(modelItemClick.isItemClick()){
//                        name = name +"\n"+modelItemClick.getTitle();
//                    }
//                }
//                if(name.equals("")){
//                    recyclerView.smoothScrollToPosition(9);
//                    Toast.makeText(ActivityPropensity.this,"최소 1개 이상의 상품을 선택해 주세요.", Toast.LENGTH_SHORT).show();
//                }else{
////                    insertDb();
//                    Toast.makeText(ActivityPropensity.this, "확인", Toast.LENGTH_SHORT).show();
//                }
//
//            }else{
////                insertDb();
//                Toast.makeText(ActivityPropensity.this, "확인", Toast.LENGTH_SHORT).show();
//            }
//
//        });
//
//    }// onCreate 끝
//
//    void YearInput(String title){
//        if(modelPropensityClicks.get(0).isClick()){
//
//            modelPropensities.get(modelPropensityClicks.get(0).getPosition()).setText(title);
//            adapterPropensity.notifyItemChanged(modelPropensityClicks.get(0).getPosition());
//
//        }else{
//            modelPropensityClicks.get(0).setClick(true);
//
//            modelPropensities.add(new ModelPropensity(13, title));
//            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);
//            recyclerView.smoothScrollToPosition(adapterPropensity.getItemCount()-1);
//
//            modelPropensityClicks.get(0).setPosition(adapterPropensity.getItemCount()-1);
//
//            modelPropensities.add(new ModelPropensity(3, ""));
//            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);
//            recyclerView.smoothScrollToPosition(adapterPropensity.getItemCount()-1);
//        }
//    }
//
//    void FinanceRate(String title){
//        if(modelPropensityClicks.get(1).isClick()){
//            modelPropensities.get(modelPropensityClicks.get(1).getPosition()).setText(title);
//            adapterPropensity.notifyItemChanged(modelPropensityClicks.get(1).getPosition());
//        }else{
//            modelPropensityClicks.get(1).setClick(true);
//
//            modelPropensities.add(new ModelPropensity(13, title));
//            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);
//            recyclerView.smoothScrollToPosition(adapterPropensity.getItemCount()-1);
//
//            modelPropensityClicks.get(1).setPosition(adapterPropensity.getItemCount()-1);
//
//            modelPropensities.add(new ModelPropensity(4, ""));
//            modelPropensities.add(new ModelPropensity(20, ""));
//
//            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);
//            recyclerView.smoothScrollToPosition(adapterPropensity.getItemCount()-1);
//
//            ViewAnimator.animate(bottomLayout2).translationY(0, -dp).alpha(0, 1).duration(1000).start();
//        }
//    }
//
//    void InvestPupo(String title){
//
//        if(modelPropensityClicks.get(2).isClick()){
//            modelPropensities.get(modelPropensityClicks.get(2).getPosition()).setText(title);
//            adapterPropensity.notifyItemChanged(modelPropensityClicks.get(2).getPosition());
//        }else{
//            modelPropensityClicks.get(2).setClick(true);
//
//            modelPropensities.add(new ModelPropensity(13, title));
//            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);
//            recyclerView.smoothScrollToPosition(adapterPropensity.getItemCount()-1);
//
//            modelPropensityClicks.get(2).setPosition(adapterPropensity.getItemCount()-1);
//
//            modelPropensities.add(new ModelPropensity(8, ""));
//            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);
//            recyclerView.smoothScrollToPosition(adapterPropensity.getItemCount()-1);
//        }
//    }
//
//    void InvestItemDate(String title){
//
//        if(modelPropensityClicks.get(4).isClick()){
//            modelPropensities.get(modelPropensityClicks.get(4).getPosition()).setText(title);
//            adapterPropensity.notifyItemChanged(modelPropensityClicks.get(4).getPosition());
//        }else{
//            modelPropensityClicks.get(4).setClick(true);
//
//            modelPropensities.add(new ModelPropensity(13, title));
//            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);
//            recyclerView.smoothScrollToPosition(adapterPropensity.getItemCount()-1);
//
//            modelPropensityClicks.get(4).setPosition(adapterPropensity.getItemCount()-1);
//
//            modelPropensities.add(new ModelPropensity(7, ""));
//            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);
//            recyclerView.smoothScrollToPosition(adapterPropensity.getItemCount()-1);
//        }
//    }
//
//    void InvestMddRate(String title){
//
//        if(modelPropensityClicks.get(5).isClick()){
//            modelPropensities.get(modelPropensityClicks.get(5).getPosition()).setText(title);
//            adapterPropensity.notifyItemChanged(modelPropensityClicks.get(5).getPosition());
//        }else{
//            modelPropensityClicks.get(5).setClick(true);
//
//            modelPropensities.add(new ModelPropensity(13, title));
//            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);
//            recyclerView.smoothScrollToPosition(adapterPropensity.getItemCount()-1);
//
//            modelPropensityClicks.get(5).setPosition(adapterPropensity.getItemCount()-1);
//
//            modelPropensities.add(new ModelPropensity(9, ""));
//            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);
//            recyclerView.smoothScrollToPosition(adapterPropensity.getItemCount()-1);
//        }
//    }
//
//    void FinanceKnow(String title){
//
//        if(modelPropensityClicks.get(6).isClick()){
//            modelPropensities.get(modelPropensityClicks.get(6).getPosition()).setText(title);
//            adapterPropensity.notifyItemChanged(modelPropensityClicks.get(6).getPosition());
//        }else{
//            modelPropensityClicks.get(6).setClick(true);
//
//            modelPropensities.add(new ModelPropensity(13, "파생상품 포함 모든 금융상품을 이해함"));
//            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);
//            recyclerView.smoothScrollToPosition(adapterPropensity.getItemCount()-1);
//
//            modelPropensityClicks.get(6).setPosition(adapterPropensity.getItemCount()-1);
//
//            modelPropensities.add(new ModelPropensity(10, ""));
//            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);
//            recyclerView.smoothScrollToPosition(adapterPropensity.getItemCount()-1);
//        }
//    }
//
//    void InvestDeriExp(String title){
//
//        if(modelPropensityClicks.get(7).isClick()){
//            modelPropensities.get(modelPropensityClicks.get(7).getPosition()).setText(title);
//            adapterPropensity.notifyItemChanged(modelPropensityClicks.get(7).getPosition());
//        }else{
//            modelPropensityClicks.get(7).setClick(true);
//
//            modelPropensities.add(new ModelPropensity(13, title));
//            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);
//            recyclerView.smoothScrollToPosition(adapterPropensity.getItemCount()-1);
//
//            modelPropensityClicks.get(7).setPosition(adapterPropensity.getItemCount()-1);
//
//            modelPropensities.add(new ModelPropensity(11, ""));
//            modelPropensities.add(new ModelPropensity(20, ""));
//
//            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);
//            recyclerView.smoothScrollToPosition(adapterPropensity.getItemCount()-1);
//
//            ViewAnimator.animate(bottomLayout3).translationY(0, -dp).alpha(0, 1).duration(1000).start();
//        }
//    }
//
//
//    void insertDb(){
//
//        Call<ModelSearchDb> getReList = RetrofitClient.getInstance(ActivityPropensity.this).getService().getStockDb();
//        getReList.enqueue(new Callback<ModelSearchDb>() {
//            @Override
//            public void onResponse(Call<ModelSearchDb> call, Response<ModelSearchDb> response) {
//                if (response.code() == 200) {
//
//                    searchViewModel = ViewModelProviders.of(ActivityPropensity.this).get(SearchViewModel2.class);
//                    roomDao = SearchRoomDatabase.getINSTANCE(ActivityPropensity.this).roomDao();
//                    DeleteAll();
//
//                    for (int index = 0; index < response.body().getContent().getRateList().size(); index++) {
//
//                        String elCodes = "";
//                        String descript = "";
//                        int follow = 0;
//
//                        if(response.body().getContent().getRateList().get(index).getUserSelect() != null){
//                            follow = response.body().getContent().getRateList().get(index).getUserSelect().getIsFollow();
//                        }else{
//                            follow = 0;
//                        }
//
//                        if(response.body().getContent().getRateList().get(index).getElCodes() != null){
//                            elCodes = response.body().getContent().getRateList().get(index).getElCodes().toString();
//                        }else{
//                            elCodes = "";
//                        }
//
//                        if(response.body().getContent().getRateList().get(index).getSearchField() != null){
//                            descript = response.body().getContent().getRateList().get(index).getSearchField().toString();
//                        }else{
//                            descript = "";
//                        }
//
//                        RoomDataInsert("",
//                                response.body().getContent().getRateList().get(index).getType(),
//                                response.body().getContent().getRateList().get(index).getCode(),
//                                response.body().getContent().getRateList().get(index).getName(),
//                                elCodes,
//                                descript,
//                                response.body().getContent().getRateList().get(index).getRate(),
//                                follow);
//                    }
//
//                    Intent intent1 = new Intent(ActivityPropensity.this, ActivityMain.class);
//                    startActivity(intent1);
//                    finish();
//                }
//            }
//            @Override
//            public void onFailure(Call<ModelSearchDb> call, Throwable t) {
//                Log.e("실패","실패"+t.getMessage());
//            }
//        });
//    }
//
//    //최근 검색어 저장 이벤트
//    void RoomDataInsert(String title, int type, String code, String name, String elStock, String descript, double rate, int follow){
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                searchViewModel.insert2(new RoomEntity2(title, type, code, name, elStock, descript, rate, follow));
//            }
//        }).start();
//    }
//
//    //딜리트 올
//    void DeleteAll(){
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                roomDao.delete2();
//            }
//        }).start();
//    }
//
//
//    private View.OnClickListener okClickListener = new View.OnClickListener() {
//        public void onClick(View v) {
//            dialogPropensitySkeep.dismiss();
//        }
//    };
//    private View.OnClickListener noClickListener = new View.OnClickListener() {
//        public void onClick(View v) {
//            dialogPropensitySkeep.dismiss();
//        }
//    };
//
//}

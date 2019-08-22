package quantec.com.moneypot.Activity.propensity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import quantec.com.moneypot.Activity.Login.MemberShipPage.ModelItemClick;
import quantec.com.moneypot.Activity.Login.MemberShipPage.ModelPropensityClick;
import quantec.com.moneypot.R;

public class ActivityPropensity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager linearLayoutManager;
    ArrayList<ModelPropensity> modelPropensities;
    AdapterPropensity adapterPropensity;

    LinearLayout bottomLayout, bottomLayout2, bottomLayout3, bottomLayout4;
    TextView yesBt, noBt, yesBt2, noBt2, yesBt3, noBt3, yesBt4, yesBt5;

    int removePosition;

    ArrayList<ModelItemClick> modelItemClicks;
    ArrayList<ModelPropensityClick> modelPropensityClicks;

    float dp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_propensity);

        Resources res = getResources();
        String[] title = res.getStringArray(R.array.propensity);

        modelPropensityClicks = new ArrayList<>();
        for(int index = 0; index < 8; index++){
            modelPropensityClicks.add(new ModelPropensityClick(false, 0));
        }

        modelItemClicks = new ArrayList<>();
        for(int index = 0; index < 10 ; index++){
            modelItemClicks.add(new ModelItemClick(false, title[index]));
        }

        bottomLayout = findViewById(R.id.bottomLayout);
        bottomLayout2 = findViewById(R.id.bottomLayout2);
        bottomLayout3 = findViewById(R.id.bottomLayout3);
        bottomLayout4 = findViewById(R.id.bottomLayout4);

        yesBt = findViewById(R.id.yesBt);
        noBt = findViewById(R.id.noBt);
        yesBt2 = findViewById(R.id.yesBt2);
        noBt2 = findViewById(R.id.noBt2);
        yesBt3 = findViewById(R.id.yesBt3);
        noBt3 = findViewById(R.id.noBt3);
        yesBt4 = findViewById(R.id.yesBt4);
        yesBt5 = findViewById(R.id.yesBt5);

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
            modelPropensities.add(new ModelPropensity(13, "권유를 원함"));
            ViewAnimator.animate(bottomLayout).translationY(-dp, 0).alpha(1, 0).duration(400).start();
            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);

            modelPropensities.add(new ModelPropensity(2, ""));
            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);
            recyclerView.smoothScrollToPosition(adapterPropensity.getItemCount()-1);
        });

        RxView.clicks(noBt).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(empty -> {
            ViewAnimator.animate(bottomLayout).translationY(-dp, 0).alpha(1, 0).duration(400).start();
            modelPropensities.add(new ModelPropensity(13, "권유를 원하지 않음"));
            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);
            recyclerView.smoothScrollToPosition(adapterPropensity.getItemCount()-1);

            modelPropensities.add(new ModelPropensity(2, ""));
            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);
            recyclerView.smoothScrollToPosition(adapterPropensity.getItemCount()-1);
        });


        ///향후 연 수입원////
        adapterPropensity.setYearInputOneClick(new AdapterPropensity.YearInputOneClick() {
            @Override
            public void onClick(int position) {
                YearInput("현재 일정한 수입이 없고, 연금이 주요 수입원");
            }
        });
        adapterPropensity.setYearInputTwoClick(new AdapterPropensity.YearInputTwoClick() {
            @Override
            public void onClick(int position) {
                YearInput("현재 일정한 수입이 잇으나, 향후 감소 하거나 불안정할 것으로 예상");
            }
        });
        adapterPropensity.setYearInputThreeClick(new AdapterPropensity.YearInputThreeClick() {
            @Override
            public void onClick(int position) {
                YearInput("현재 일정한 수입이 있꼬, 향후 현재 수준 유지 및 증가할 것으로 예상");
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



        RxView.clicks(yesBt2).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(empty -> {
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
        });

        RxView.clicks(noBt2).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(empty -> {
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
                    modelItemClicks.get(0).setItemClick(false);
                }else{
                    modelItemClicks.get(0).setItemClick(true);
                }

                adapterPropensity.notifyItemChanged(position, "click");
            }
        });
        adapterPropensity.setInvestPupoItemTwoClick(new AdapterPropensity.InvestPupoItemTwoClick() {
            @Override
            public void onClick(int position) {

                if(modelItemClicks.get(1).isItemClick()){
                    modelItemClicks.get(1).setItemClick(false);
                }else{
                    modelItemClicks.get(1).setItemClick(true);
                }

                adapterPropensity.notifyItemChanged(position, "click");
            }
        });
        adapterPropensity.setInvestPupoItemThreeClick(new AdapterPropensity.InvestPupoItemThreeClick() {
            @Override
            public void onClick(int position) {

                if(modelItemClicks.get(2).isItemClick()){
                    modelItemClicks.get(2).setItemClick(false);
                }else{
                    modelItemClicks.get(2).setItemClick(true);
                }

                adapterPropensity.notifyItemChanged(position, "click");
            }
        });
        adapterPropensity.setInvestPupoItemForeClick(new AdapterPropensity.InvestPupoItemForeClick() {
            @Override
            public void onClick(int position) {

                if(modelItemClicks.get(3).isItemClick()){
                    modelItemClicks.get(3).setItemClick(false);
                }else{
                    modelItemClicks.get(3).setItemClick(true);
                }

                adapterPropensity.notifyItemChanged(position, "click");
            }
        });
        adapterPropensity.setInvestPupoItemFiveClick(new AdapterPropensity.InvestPupoItemFiveClick() {
            @Override
            public void onClick(int position) {

                if(modelItemClicks.get(4).isItemClick()){
                    modelItemClicks.get(4).setItemClick(false);
                }else{
                    modelItemClicks.get(4).setItemClick(true);
                }

                adapterPropensity.notifyItemChanged(position, "click");
            }
        });
        adapterPropensity.setInvestPupoItemSixClick(new AdapterPropensity.InvestPupoItemSixClick() {
            @Override
            public void onClick(int position) {

                if(modelItemClicks.get(5).isItemClick()){
                    modelItemClicks.get(5).setItemClick(false);
                }else{
                    modelItemClicks.get(5).setItemClick(true);
                }

                adapterPropensity.notifyItemChanged(position, "click");
            }
        });
        adapterPropensity.setInvestPupoItemSevenClick(new AdapterPropensity.InvestPupoItemSevenClick() {
            @Override
            public void onClick(int position) {

                if(modelItemClicks.get(6).isItemClick()){
                    modelItemClicks.get(6).setItemClick(false);
                }else{
                    modelItemClicks.get(6).setItemClick(true);
                }

                adapterPropensity.notifyItemChanged(position, "click");
            }
        });
        adapterPropensity.setInvestPupoItemEightClick(new AdapterPropensity.InvestPupoItemEightClick() {
            @Override
            public void onClick(int position) {

                if(modelItemClicks.get(7).isItemClick()){
                    modelItemClicks.get(7).setItemClick(false);
                }else{
                    modelItemClicks.get(7).setItemClick(true);
                }

                adapterPropensity.notifyItemChanged(position, "click");
            }
        });
        adapterPropensity.setInvestPupoItemNineClick(new AdapterPropensity.InvestPupoItemNineClick() {
            @Override
            public void onClick(int position) {

                if(modelItemClicks.get(8).isItemClick()){
                    modelItemClicks.get(8).setItemClick(false);
                }else{
                    modelItemClicks.get(8).setItemClick(true);
                }

                adapterPropensity.notifyItemChanged(position, "click");
            }
        });
        adapterPropensity.setInvestPupoItemTenClick(new AdapterPropensity.InvestPupoItemTenClick() {
            @Override
            public void onClick(int position) {

                if(modelItemClicks.get(9).isItemClick()){
                    modelItemClicks.get(9).setItemClick(false);
                }else{
                    modelItemClicks.get(9).setItemClick(true);
                }

                adapterPropensity.notifyItemChanged(position, "click");
            }
        });

        adapterPropensity.setInvestPupoItemOkClick(new AdapterPropensity.InvestPupoItemOkClick() {
            @Override
            public void onClick(int position) {
                adapterPropensity.notifyItemChanged(position, "closed");

                String title="";
                for(ModelItemClick modelItemClick : modelItemClicks){
                    if(modelItemClick.isItemClick()){
                        title = title +"\n"+modelItemClick.getTitle();
                    }
                }

                if(title.equals("")){
                    Toast.makeText(ActivityPropensity.this, "최소 1개 이상을 선택해 주세요", Toast.LENGTH_SHORT).show();
                }else{
                    if(modelPropensityClicks.get(3).isClick()){
                        modelPropensities.get(modelPropensityClicks.get(3).getPosition()).setText(title);
                        adapterPropensity.notifyItemChanged(modelPropensityClicks.get(3).getPosition());
                    }else{
                        modelPropensityClicks.get(3).setClick(true);

                        modelPropensities.add(new ModelPropensity(13, title));
                        adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);

                        modelPropensityClicks.get(3).setPosition(adapterPropensity.getItemCount()-1);

                        modelPropensities.add(new ModelPropensity(6, ""));
                        adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);
                        recyclerView.smoothScrollToPosition(adapterPropensity.getItemCount()-1);
                    }
                }
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
                InvestMddRate("10% 이하");
            }
        });
        adapterPropensity.setInvestMddRate20Click(new AdapterPropensity.InvestMddRate20Click() {
            @Override
            public void onClick(int position) {
                InvestMddRate("20% 이하");
            }
        });
        adapterPropensity.setInvestMddRate30Click(new AdapterPropensity.InvestMddRate30Click() {
            @Override
            public void onClick(int position) {
                InvestMddRate("30% 이하");
            }
        });
        adapterPropensity.setInvestMddRate40Click(new AdapterPropensity.InvestMddRate40Click() {
            @Override
            public void onClick(int position) {
                InvestMddRate("40% 이하");
            }
        });
        adapterPropensity.setInvestMddRateUpClick(new AdapterPropensity.InvestMddRateUpClick() {
            @Override
            public void onClick(int position) {
                InvestMddRate("높은 수익이라면 상관 없음");
            }
        });


        adapterPropensity.setFinanceKnowOneClick(new AdapterPropensity.FinanceKnowOneClick() {
            @Override
            public void onClick(int position) {
                FinanceKnow("예/적금 외 투자 경험 없음");
            }
        });
        adapterPropensity.setFinanceKnowTwoClick(new AdapterPropensity.FinanceKnowTwoClick() {
            @Override
            public void onClick(int position) {
                FinanceKnow("주식, 채권, 펀드에 대한 설명을 통해 투자 결정 가능");
            }
        });
        adapterPropensity.setFinanceKnowThreeClick(new AdapterPropensity.FinanceKnowThreeClick() {
            @Override
            public void onClick(int position) {
                FinanceKnow("투자 상품에 대한 설명 없이 투자 가능");
            }
        });
        adapterPropensity.setFinanceKnowForeClick(new AdapterPropensity.FinanceKnowForeClick() {
            @Override
            public void onClick(int position) {
                FinanceKnow("파생상품 포함 모든 금융상품을 이해함");
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
            ViewAnimator.animate(bottomLayout4).translationY(-dp, 0).alpha(1, 0).duration(400).start();

            removePosition = adapterPropensity.getItemCount()-1;
            modelPropensities.remove(removePosition);
            adapterPropensity.notifyItemRemoved(removePosition);
            adapterPropensity.notifyItemRangeChanged(removePosition, adapterPropensity.getItemCount());

            modelPropensities.add(new ModelPropensity(13, "동의합니다"));
            modelPropensities.add(new ModelPropensity(20, ""));

            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);
            recyclerView.smoothScrollToPosition(adapterPropensity.getItemCount()-1);

            ViewAnimator.animate(yesBt5).translationY(0, -dp).alpha(0, 1).duration(1000).start();
        });

        RxView.clicks(yesBt5).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(empty -> {

            String name = "";

            for(ModelItemClick modelItemClick : modelItemClicks){
                if(modelItemClick.isItemClick()){
                    name = name +"\n"+modelItemClick.getTitle();
                }
            }

            Log.e("중복값","값 : "+name);

            Toast.makeText(ActivityPropensity.this, "확인", Toast.LENGTH_SHORT).show();
        });

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

            modelPropensities.add(new ModelPropensity(13, "파생상품 포함 모든 금융상품을 이해함"));
            adapterPropensity.notifyItemInserted(adapterPropensity.getItemCount()-1);
            recyclerView.smoothScrollToPosition(adapterPropensity.getItemCount()-1);

            modelPropensityClicks.get(6).setPosition(adapterPropensity.getItemCount()-1);

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

}

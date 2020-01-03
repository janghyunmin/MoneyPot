package com.quantec.moneypot.activity.propensity;

import android.content.Context;
import android.media.Image;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.github.florent37.viewanimator.ViewAnimator;
import com.quantec.moneypot.R;
import com.quantec.moneypot.activity.Login.resist.ModelItemClick;

import java.util.ArrayList;
import java.util.List;

public class AdapterPropensity extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int INTRO = 0;
    private final int HOPE = 1;
    private final int RECOM = -1;
    private final int YEARINPUT = 2;
    private final int FINANCERATE = 3;
    private final int INVESTEXP = 4;
    private final int INVESTPUPOITEM = 5;
    private final int INVESTITEMDATE = 6;
    private final int INVESTPUPO = 7;
    private final int INVESTMDDRATE = 8;
    private final int FINANCEKNOW = 9;
    private final int INVESTDERIEXP = 10;
    private final int WEEKFINANCE = 11;
    private final int INVESTVALIDITY = 12;
    private final int MYCHAT = 13;
    private final int YIELD = 14;
    private final int SPACE = 20;

    ArrayList<ModelPropensity> modelPropensities;
    Context context;

    float dp;
    boolean clickItem = false;

    ArrayList<ModelItemClick> modelItemClicks;

    public AdapterPropensity(ArrayList<ModelPropensity> modelPropensities, Context context, float dp, ArrayList<ModelItemClick> modelItemClicks) {
        this.modelPropensities = modelPropensities;
        this.context = context;
        this.dp = dp;
        this.modelItemClicks = modelItemClicks;
    }

    private YearInputOneClick yearInputOneClick;
    public interface YearInputOneClick {
        public void onClick(int position);
    }

    public void setYearInputOneClick(YearInputOneClick yearInputOneClick) {
        this.yearInputOneClick = yearInputOneClick;
    }

    private YearInputTwoClick yearInputTwoClick;
    public interface YearInputTwoClick {
        public void onClick(int position);
    }

    public void setYearInputTwoClick(YearInputTwoClick yearInputTwoClick) {
        this.yearInputTwoClick = yearInputTwoClick;
    }

    private YearInputThreeClick yearInputThreeClick;
    public interface YearInputThreeClick {
        public void onClick(int position);
    }

    public void setYearInputThreeClick(YearInputThreeClick yearInputThreeClick) {
        this.yearInputThreeClick = yearInputThreeClick;
    }

    private FinanceRate5Click financeRate5Click;
    public interface FinanceRate5Click {
        public void onClick(int position);
    }

    public void setFinanceRate5Click(FinanceRate5Click financeRate5Click) {
        this.financeRate5Click = financeRate5Click;
    }

    private FinanceRate10Click financeRate10Click;
    public interface FinanceRate10Click {
        public void onClick(int position);
    }

    public void setFinanceRate10Click(FinanceRate10Click financeRate10Click) {
        this.financeRate10Click = financeRate10Click;
    }


    private FinanceRate20Click financeRate20Click;
    public interface FinanceRate20Click {
        public void onClick(int position);
    }

    public void setFinanceRate20Click(FinanceRate20Click financeRate20Click) {
        this.financeRate20Click = financeRate20Click;
    }

    private FinanceRate30Click financeRate30Click;
    public interface FinanceRate30Click {
        public void onClick(int position);
    }

    public void setFinanceRate30Click(FinanceRate30Click financeRate30Click) {
        this.financeRate30Click = financeRate30Click;
    }

    private FinanceRate30UpClick financeRate30UpClick;
    public interface FinanceRate30UpClick {
        public void onClick(int position);
    }

    public void setFinanceRate30UpClick(FinanceRate30UpClick financeRate30UpClick) {
        this.financeRate30UpClick = financeRate30UpClick;
    }

    private InvestPupoOneClick investPupoOneClick;
    public interface InvestPupoOneClick {
        public void onClick(int position);
    }

    public void setInvestPupoOneClick(InvestPupoOneClick investPupoOneClick) {
        this.investPupoOneClick = investPupoOneClick;
    }

    private InvestPupoTwoClick investPupoTwoClick;
    public interface InvestPupoTwoClick {
        public void onClick(int position);
    }

    public void setInvestPupoTwoClick(InvestPupoTwoClick investPupoTwoClick) {
        this.investPupoTwoClick = investPupoTwoClick;
    }

    private InvestPupoThreeClick investPupoThreeClick;
    public interface InvestPupoThreeClick {
        public void onclick(int position);
    }

    public void setInvestPupoThreeClick(InvestPupoThreeClick investPupoThreeClick) {
        this.investPupoThreeClick = investPupoThreeClick;
    }

    private InvestMddRate10Click investMddRate10Click;
    public interface InvestMddRate10Click {
        public void onClick(int position);
    }

    public void setInvestMddRate10Click(InvestMddRate10Click investMddRate10Click) {
        this.investMddRate10Click = investMddRate10Click;
    }

    private InvestMddRate20Click investMddRate20Click;
    public interface InvestMddRate20Click {
        public void onClick(int position);
    }

    public void setInvestMddRate20Click(InvestMddRate20Click investMddRate20Click) {
        this.investMddRate20Click = investMddRate20Click;
    }

    private InvestMddRate30Click investMddRate30Click;
    public interface InvestMddRate30Click {
        public void onClick(int position);
    }

    public void setInvestMddRate30Click(InvestMddRate30Click investMddRate30Click) {
        this.investMddRate30Click = investMddRate30Click;
    }


    private FinanceKnowOneClick financeKnowOneClick;
    public interface FinanceKnowOneClick {
        public void onClick(int position);
    }

    public void setFinanceKnowOneClick(FinanceKnowOneClick financeKnowOneClick) {
        this.financeKnowOneClick = financeKnowOneClick;
    }

    private FinanceKnowTwoClick financeKnowTwoClick;
    public interface FinanceKnowTwoClick {
        public void onClick(int position);
    }

    public void setFinanceKnowTwoClick(FinanceKnowTwoClick financeKnowTwoClick) {
        this.financeKnowTwoClick = financeKnowTwoClick;
    }

    private FinanceKnowThreeClick financeKnowThreeClick;
    public interface FinanceKnowThreeClick {
        public void onClick(int position);
    }

    public void setFinanceKnowThreeClick(FinanceKnowThreeClick financeKnowThreeClick) {
        this.financeKnowThreeClick = financeKnowThreeClick;
    }

    private FinanceKnowForeClick financeKnowForeClick;
    public interface FinanceKnowForeClick {
        public void onClick(int position);
    }

    public void setFinanceKnowForeClick(FinanceKnowForeClick financeKnowForeClick) {
        this.financeKnowForeClick = financeKnowForeClick;
    }


    private YieldClick1 yieldClick1;
    public interface YieldClick1{
        public void onClick(int position);
    }

    public void setYieldClick1(YieldClick1 yieldClick1) {
        this.yieldClick1 = yieldClick1;
    }

    private YieldClick2 yieldClick2;
    public interface YieldClick2{
        public void onClick(int position);
    }

    public void setYieldClick2(YieldClick2 yieldClick2) {
        this.yieldClick2 = yieldClick2;
    }

    private YieldClick3 yieldClick3;
    public interface YieldClick3{
        public void onClick(int position);
    }

    public void setYieldClick3(YieldClick3 yieldClick3) {
        this.yieldClick3 = yieldClick3;
    }

    private YieldClick4 yieldClick4;
    public interface YieldClick4{
        public void onClick(int position);
    }

    public void setYieldClick4(YieldClick4 yieldClick4) {
        this.yieldClick4 = yieldClick4;
    }

    private InvestDeriExpOneClick investDeriExpOneClick;
    public interface InvestDeriExpOneClick {
        public void onClick(int position);
    }

    public void setInvestDeriExpOneClick(InvestDeriExpOneClick investDeriExpOneClick) {
        this.investDeriExpOneClick = investDeriExpOneClick;
    }

    private InvestDeriExpTwoClick investDeriExpTwoClick;
    public interface InvestDeriExpTwoClick {
        public void onClick(int position);
    }

    public void setInvestDeriExpTwoClick(InvestDeriExpTwoClick investDeriExpTwoClick) {
        this.investDeriExpTwoClick = investDeriExpTwoClick;
    }

    private InvestDeriExpThreeClick investDeriExpThreeClick;
    public interface InvestDeriExpThreeClick {
        public void onClick(int position);
    }

    public void setInvestDeriExpThreeClick(InvestDeriExpThreeClick investDeriExpThreeClick) {
        this.investDeriExpThreeClick = investDeriExpThreeClick;
    }

    private InvestPupoItemOneClick investPupoItemOneClick;
    public interface InvestPupoItemOneClick {
        public void onClick(int position);
    }

    public void setInvestPupoItemOneClick(InvestPupoItemOneClick investPupoItemOneClick) {
        this.investPupoItemOneClick = investPupoItemOneClick;
    }

    private InvestPupoItemTwoClick investPupoItemTwoClick;
    public interface InvestPupoItemTwoClick {
        public void onClick(int position);
    }

    public void setInvestPupoItemTwoClick(InvestPupoItemTwoClick investPupoItemTwoClick) {
        this.investPupoItemTwoClick = investPupoItemTwoClick;
    }

    private InvestPupoItemThreeClick investPupoItemThreeClick;
    public interface InvestPupoItemThreeClick {
        public void onClick(int position);
    }

    public void setInvestPupoItemThreeClick(InvestPupoItemThreeClick investPupoItemThreeClick) {
        this.investPupoItemThreeClick = investPupoItemThreeClick;
    }

    private InvestPupoItemForeClick investPupoItemForeClick;
    public interface InvestPupoItemForeClick {
        public void onClick(int position);
    }

    public void setInvestPupoItemForeClick(InvestPupoItemForeClick investPupoItemForeClick) {
        this.investPupoItemForeClick = investPupoItemForeClick;
    }

    private InvestPupoItemFiveClick investPupoItemFiveClick;
    public interface InvestPupoItemFiveClick{
        public void onClick(int position);
    }

    public void setInvestPupoItemFiveClick(InvestPupoItemFiveClick investPupoItemFiveClick) {
        this.investPupoItemFiveClick = investPupoItemFiveClick;
    }

    private InvestPupoItemSixClick investPupoItemSixClick;
    public interface InvestPupoItemSixClick{
        public void onClick(int position);
    }

    public void setInvestPupoItemSixClick(InvestPupoItemSixClick investPupoItemSixClick) {
        this.investPupoItemSixClick = investPupoItemSixClick;
    }

    private InvestPupoItemSevenClick investPupoItemSevenClick;
    public interface InvestPupoItemSevenClick {
        public void onClick(int position);
    }

    public void setInvestPupoItemSevenClick(InvestPupoItemSevenClick investPupoItemSevenClick) {
        this.investPupoItemSevenClick = investPupoItemSevenClick;
    }

    private InvestPupoItemEightClick investPupoItemEightClick;
    public interface InvestPupoItemEightClick{
        public void onClick(int position);
    }

    public void setInvestPupoItemEightClick(InvestPupoItemEightClick investPupoItemEightClick) {
        this.investPupoItemEightClick = investPupoItemEightClick;
    }

    private InvestPupoItemNineClick investPupoItemNineClick;
    public interface InvestPupoItemNineClick{
        public void onClick(int position);
    }

    public void setInvestPupoItemNineClick(InvestPupoItemNineClick investPupoItemNineClick) {
        this.investPupoItemNineClick = investPupoItemNineClick;
    }

    private InvestPupoItemTenClick investPupoItemTenClick;
    public interface InvestPupoItemTenClick{
        public void onClick(int position);
    }

    public void setInvestPupoItemTenClick(InvestPupoItemTenClick investPupoItemTenClick) {
        this.investPupoItemTenClick = investPupoItemTenClick;
    }

    private InvestPupoItemLayout11 investPopoItemLayout11;
    public interface InvestPupoItemLayout11{
        public void onClick(int position);
    }

    public void setInvestPopoItemLayout11(InvestPupoItemLayout11 investPopoItemLayout11) {
        this.investPopoItemLayout11 = investPopoItemLayout11;
    }

    private InvestPupoItemLayout12 investPupoItemLayout12;
    public interface InvestPupoItemLayout12{
        public void onClick(int position);
    }

    public void setInvestPupoItemLayout12(InvestPupoItemLayout12 investPupoItemLayout12) {
        this.investPupoItemLayout12 = investPupoItemLayout12;
    }

    private InvestPupoItemLayout13 investPupoItemLayout13;
    public interface InvestPupoItemLayout13{
        public void onClick(int position);
    }

    public void setInvestPupoItemLayout13(InvestPupoItemLayout13 investPupoItemLayout13) {
        this.investPupoItemLayout13 = investPupoItemLayout13;
    }

    private InvestPupoItemOkClick investPupoItemOkClick;
    public interface InvestPupoItemOkClick{
        public void onClick(int position);
    }

    public void setInvestPupoItemOkClick(InvestPupoItemOkClick investPupoItemOkClick) {
        this.investPupoItemOkClick = investPupoItemOkClick;
    }


    private InvestItemDateOneClick investItemDateOneClick;
    public interface InvestItemDateOneClick{
        public void onClick(int position);
    }

    public void setInvestItemDateOneClick(InvestItemDateOneClick investItemDateOneClick) {
        this.investItemDateOneClick = investItemDateOneClick;
    }

    private InvestItemDateTwoClick investItemDateTwoClick;
    public interface InvestItemDateTwoClick{
        public void onClick(int position);
    }

    public void setInvestItemDateTwoClick(InvestItemDateTwoClick investItemDateTwoClick) {
        this.investItemDateTwoClick = investItemDateTwoClick;
    }

    private InvestItemDateThreeClick investItemDateThreeClick;
    public interface InvestItemDateThreeClick{
        public void onClick(int position);
    }

    public void setInvestItemDateThreeClick(InvestItemDateThreeClick investItemDateThreeClick) {
        this.investItemDateThreeClick = investItemDateThreeClick;
    }

    private WeekFinanceClick weekFinanceClick;
    public interface WeekFinanceClick{
        public void onClick(int position);
    }

    public void setWeekFinanceClick(WeekFinanceClick weekFinanceClick) {
        this.weekFinanceClick = weekFinanceClick;
    }

    ImageView preChecked1, preChecked2, preChecked3, preChecked4, preChecked5, preChecked6, preChecked7, preChecked8;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                return new IntroViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_propensity_intro, parent, false));
            case 1:
                return new HopeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_propensity_hope, parent, false));
            case -1:
                return new RecomViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_propensity_recom, parent, false));
            case 2:
                return new YearInputViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_propensity_yearinput, parent, false));
            case 3:
                return new FinanceRateViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_propensity_financerate, parent, false));
            case 4:
                return new InvestExpViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_propensity_investexp, parent, false));
            case 5:
                return new InvestPupoItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_propensity_investpupoitem, parent, false));
            case 6:
                return new InvestItemDateViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_propensity_investitemdate, parent, false));
            case 7:
                return new InvestPupoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_propensity_investpupo, parent, false));
            case 8:
                return new InvestMddRateViewHodler(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_propensity_investmddrate, parent, false));
            case 9:
                return new FinanceKnowViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_propensity_financeknow, parent, false));
            case 14:
                return new YieldViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_propensity_yield, parent, false));
            case 10:
                return new InvestDeriExpViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_propensity_investderiexp, parent, false));
            case 11:
                return new WeekFinanceViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_propensity_weekfinance, parent, false));
            case 12:
                return new InvestValidityViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_propensity_investvalidity, parent, false));
            case 13:
                return new MyChatViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_propensity_mychat, parent, false));
            case 20:
                return new SpaceViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_propensity_space, parent, false));

            default:
                return new IntroViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_propensity_intro, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List<Object> payloads) {

        super.onBindViewHolder(holder, position, payloads);

        if(holder instanceof IntroViewHolder){

        }
        else if(holder instanceof HopeViewHolder){

        }
        else if(holder instanceof RecomViewHolder){

        }

        else if(holder instanceof YearInputViewHolder){


            ((YearInputViewHolder)holder).firstLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(yearInputOneClick != null){
                        yearInputOneClick.onClick(position);
                    }

                    if(preChecked1 == null){
                        preChecked1 = ((YearInputViewHolder)holder).firstImage;
                        preChecked1.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
                    }else{

                        preChecked1.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_off_whitegray));
                        ((YearInputViewHolder)holder).firstImage.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
                        preChecked1 = ((YearInputViewHolder)holder).firstImage;

                    }
                }
            });

            ((YearInputViewHolder)holder).secondLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(yearInputTwoClick != null) {
                        yearInputTwoClick.onClick(position);
                    }

                    if(preChecked1 == null){
                        preChecked1 = ((YearInputViewHolder)holder).secondImage;
                        preChecked1.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
                    }else{

                        preChecked1.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_off_whitegray));
                        ((YearInputViewHolder)holder).secondImage.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
                        preChecked1 = ((YearInputViewHolder)holder).secondImage;

                    }
                }
            });

            ((YearInputViewHolder)holder).thirdLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(yearInputThreeClick != null){
                        yearInputThreeClick.onClick(position);
                    }

                    if(preChecked1 == null){
                        preChecked1 = ((YearInputViewHolder)holder).thirdImage;
                        preChecked1.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
                    }else{

                        preChecked1.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_off_whitegray));
                        ((YearInputViewHolder)holder).thirdImage.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
                        preChecked1 = ((YearInputViewHolder)holder).thirdImage;

                    }
                }
            });
        }

        else if(holder instanceof FinanceRateViewHolder){

            ((FinanceRateViewHolder)holder).per5Layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(financeRate5Click != null){
                        financeRate5Click.onClick(position);
                    }

                    if(preChecked2 == null){
                        preChecked2 = ((FinanceRateViewHolder)holder).per5Image;
                        preChecked2.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
                    }else{
                        preChecked2.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_off_whitegray));
                        ((FinanceRateViewHolder)holder).per5Image.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
                        preChecked2 = ((FinanceRateViewHolder)holder).per5Image;
                    }
                }
            });

            ((FinanceRateViewHolder)holder).per10Layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(financeRate10Click != null){
                        financeRate10Click.onClick(position);
                    }

                    if(preChecked2 == null){
                        preChecked2 = ((FinanceRateViewHolder)holder).per10Image;
                        preChecked2.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
                    }else{
                        preChecked2.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_off_whitegray));
                        ((FinanceRateViewHolder)holder).per10Image.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
                        preChecked2 = ((FinanceRateViewHolder)holder).per10Image;
                    }
                }
            });

            ((FinanceRateViewHolder)holder).per20Layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(financeRate20Click != null){
                        financeRate20Click.onClick(position);
                    }

                    if(preChecked2 == null){
                        preChecked2 = ((FinanceRateViewHolder)holder).per20Image;
                        preChecked2.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
                    }else{
                        preChecked2.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_off_whitegray));
                        ((FinanceRateViewHolder)holder).per20Image.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
                        preChecked2 = ((FinanceRateViewHolder)holder).per20Image;
                    }
                }
            });

            ((FinanceRateViewHolder)holder).per30Layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(financeRate30Click != null){
                        financeRate30Click.onClick(position);
                    }

                    if(preChecked2 == null){
                        preChecked2 = ((FinanceRateViewHolder)holder).per30Image;
                        preChecked2.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
                    }else{
                        preChecked2.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_off_whitegray));
                        ((FinanceRateViewHolder)holder).per30Image.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
                        preChecked2 = ((FinanceRateViewHolder)holder).per30Image;
                    }
                }
            });

            ((FinanceRateViewHolder)holder).per30UpLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(financeRate30UpClick != null){
                        financeRate30UpClick.onClick(position);
                    }

                    if(preChecked2 == null){
                        preChecked2 = ((FinanceRateViewHolder)holder).per30UpImage;
                        preChecked2.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
                    }else{
                        preChecked2.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_off_whitegray));
                        ((FinanceRateViewHolder)holder).per30UpImage.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
                        preChecked2 = ((FinanceRateViewHolder)holder).per30UpImage;
                    }
                }
            });

        }

        else if(holder instanceof InvestExpViewHolder){

        }

        else if(holder instanceof InvestPupoItemViewHolder){

            if(!payloads.isEmpty()){
                for(Object payload : payloads){
                    if(payload instanceof String){
                        String type = (String)payload;
                        if(TextUtils.equals(type, "click")){
                            if(!clickItem){
                                ViewAnimator.animate(((InvestPupoItemViewHolder)holder).okText).translationY(0, dp).alpha(0, 1).duration(1000).start();
                                clickItem = true;
                            }
                        }
                        else if(TextUtils.equals(type, "closed")){
                            ViewAnimator.animate(((InvestPupoItemViewHolder)holder).okText).translationY(dp, 0).alpha(1, 0).duration(200).start();
                        }
                    }
                }
            }

            if(modelItemClicks.get(0).getTotal() > 0){
                ((InvestPupoItemViewHolder)holder).countLayout.setVisibility(View.VISIBLE);
                ((InvestPupoItemViewHolder)holder).countText.setText(modelItemClicks.get(0).getTotal()+"/13개 선택");
            }else{
                ((InvestPupoItemViewHolder)holder).countLayout.setVisibility(View.GONE);
            }

            if(modelItemClicks.get(0).isItemClick()){
                ((InvestPupoItemViewHolder)holder).stockImage.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
            }else{
                ((InvestPupoItemViewHolder)holder).stockImage.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_off_whitegray));
            }
            if(modelItemClicks.get(1).isItemClick()){
                ((InvestPupoItemViewHolder)holder).warpImage.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
            }else{
                ((InvestPupoItemViewHolder)holder).warpImage.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_off_whitegray));
            }
            if(modelItemClicks.get(2).isItemClick()){
                ((InvestPupoItemViewHolder)holder).mixImage.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
            }else{
                ((InvestPupoItemViewHolder)holder).mixImage.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_off_whitegray));
            }
            if(modelItemClicks.get(3).isItemClick()){
                ((InvestPupoItemViewHolder)holder).fundImage.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
            }else{
                ((InvestPupoItemViewHolder)holder).fundImage.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_off_whitegray));
            }
            if(modelItemClicks.get(4).isItemClick()){
                ((InvestPupoItemViewHolder)holder).outImage.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
            }else{
                ((InvestPupoItemViewHolder)holder).outImage.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_off_whitegray));
            }
            if(modelItemClicks.get(5).isItemClick()){
                ((InvestPupoItemViewHolder)holder).optionImage.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
            }else{
                ((InvestPupoItemViewHolder)holder).optionImage.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_off_whitegray));
            }
            if(modelItemClicks.get(6).isItemClick()){
                ((InvestPupoItemViewHolder)holder).trustImage.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
            }else{
                ((InvestPupoItemViewHolder)holder).trustImage.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_off_whitegray));
            }
            if(modelItemClicks.get(7).isItemClick()){
                ((InvestPupoItemViewHolder)holder).bondImage.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
            }else{
                ((InvestPupoItemViewHolder)holder).bondImage.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_off_whitegray));
            }
            if(modelItemClicks.get(8).isItemClick()){
                ((InvestPupoItemViewHolder)holder).elwImage.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
            }else{
                ((InvestPupoItemViewHolder)holder).elwImage.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_off_whitegray));
            }
            if(modelItemClicks.get(9).isItemClick()){
                ((InvestPupoItemViewHolder)holder).elsImage.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
            }else{
                ((InvestPupoItemViewHolder)holder).elsImage.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_off_whitegray));
            }
            if(modelItemClicks.get(10).isItemClick()){
                ((InvestPupoItemViewHolder)holder).Image11.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
            }else{
                ((InvestPupoItemViewHolder)holder).Image11.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_off_whitegray));
            }
            if(modelItemClicks.get(11).isItemClick()){
                ((InvestPupoItemViewHolder)holder).Image12.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
            }else{
                ((InvestPupoItemViewHolder)holder).Image12.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_off_whitegray));
            }
            if(modelItemClicks.get(12).isItemClick()){
                ((InvestPupoItemViewHolder)holder).Image13.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
            }else{
                ((InvestPupoItemViewHolder)holder).Image13.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_off_whitegray));
            }



            ((InvestPupoItemViewHolder)holder).okText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(investPupoItemOkClick != null){
                        investPupoItemOkClick.onClick(position);
                        clickItem = false;
                    }
                }
            });

            ((InvestPupoItemViewHolder)holder).oneLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(investPupoItemOneClick != null){
                        investPupoItemOneClick.onClick(position);
                    }
                }
            });

            ((InvestPupoItemViewHolder)holder).twoLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(investPupoItemTwoClick != null){
                        investPupoItemTwoClick.onClick(position);
                    }
                }
            });

            ((InvestPupoItemViewHolder)holder).threeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(investPupoItemThreeClick != null){
                        investPupoItemThreeClick.onClick(position);
                    }
                }
            });

            ((InvestPupoItemViewHolder)holder).foreLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(investPupoItemForeClick != null){
                        investPupoItemForeClick.onClick(position);
                    }
                }
            });

            ((InvestPupoItemViewHolder)holder).fiveLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(investPupoItemFiveClick != null){
                        investPupoItemFiveClick.onClick(position);
                    }
                }
            });

            ((InvestPupoItemViewHolder)holder).sixLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(investPupoItemSixClick != null){
                        investPupoItemSixClick.onClick(position);
                    }
                }
            });

            ((InvestPupoItemViewHolder)holder).sevenLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(investPupoItemSevenClick != null){
                        investPupoItemSevenClick.onClick(position);
                    }
                }
            });

            ((InvestPupoItemViewHolder)holder).eightLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(investPupoItemEightClick != null){
                        investPupoItemEightClick.onClick(position);
                    }
                }
            });

            ((InvestPupoItemViewHolder)holder).nineLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(investPupoItemNineClick != null){
                        investPupoItemNineClick.onClick(position);
                    }
                }
            });

            ((InvestPupoItemViewHolder)holder).tenLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(investPupoItemTenClick != null){
                        investPupoItemTenClick.onClick(position);
                    }
                }
            });


            ((InvestPupoItemViewHolder)holder).layout11.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(investPopoItemLayout11 != null){
                        investPopoItemLayout11.onClick(position);
                    }
                }
            });

            ((InvestPupoItemViewHolder)holder).layout12.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(investPupoItemLayout12 != null){
                        investPupoItemLayout12.onClick(position);
                    }
                }
            });

            ((InvestPupoItemViewHolder)holder).layout13.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(investPupoItemLayout13 != null){
                        investPupoItemLayout13.onClick(position);
                    }
                }
            });

        }

        else if(holder instanceof InvestItemDateViewHolder){

            ((InvestItemDateViewHolder)holder).firstLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(investItemDateOneClick != null){
                        investItemDateOneClick.onClick(position);
                    }


                    if(preChecked8 == null){
                        preChecked8 = ((InvestItemDateViewHolder)holder).firstImage;
                        preChecked8.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
                    }else{
                        preChecked8.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_off_whitegray));
                        ((InvestItemDateViewHolder)holder).firstImage.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
                        preChecked8 = ((InvestItemDateViewHolder)holder).firstImage;
                    }
                }
            });

            ((InvestItemDateViewHolder)holder).secondLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(investItemDateTwoClick != null){
                        investItemDateTwoClick.onClick(position);
                    }

                    if(preChecked8 == null){
                        preChecked8 = ((InvestItemDateViewHolder)holder).secondImage;
                        preChecked8.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
                    }else{
                        preChecked8.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_off_whitegray));
                        ((InvestItemDateViewHolder)holder).secondImage.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
                        preChecked8 = ((InvestItemDateViewHolder)holder).secondImage;
                    }
                }
            });

            ((InvestItemDateViewHolder)holder).thirdLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(investItemDateThreeClick != null){
                        investItemDateThreeClick.onClick(position);
                    }
                    if(preChecked8 == null){
                        preChecked8 = ((InvestItemDateViewHolder)holder).thirdImage;
                        preChecked8.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
                    }else{
                        preChecked8.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_off_whitegray));
                        ((InvestItemDateViewHolder)holder).thirdImage.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
                        preChecked8 = ((InvestItemDateViewHolder)holder).thirdImage;
                    }
                }
            });
        }

        else if(holder instanceof InvestPupoViewHolder){

            ((InvestPupoViewHolder)holder).firstLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(investPupoOneClick != null){
                        investPupoOneClick.onClick(position);
                    }

                    if(preChecked3 == null){
                        preChecked3 = ((InvestPupoViewHolder)holder).firstImage;
                        preChecked3.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
                    }else{
                        preChecked3.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_off_whitegray));
                        ((InvestPupoViewHolder)holder).firstImage.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
                        preChecked3 = ((InvestPupoViewHolder)holder).firstImage;
                    }
                }
            });

            ((InvestPupoViewHolder)holder).secondLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(investPupoTwoClick != null){
                        investPupoTwoClick.onClick(position);
                    }

                    if(preChecked3 == null){
                        preChecked3 = ((InvestPupoViewHolder)holder).secondImage;
                        preChecked3.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
                    }else{
                        preChecked3.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_off_whitegray));
                        ((InvestPupoViewHolder)holder).secondImage.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
                        preChecked3 = ((InvestPupoViewHolder)holder).secondImage;
                    }
                }
            });

            ((InvestPupoViewHolder)holder).thirdLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(investPupoThreeClick != null){
                        investPupoThreeClick.onclick(position);
                    }

                    if(preChecked3 == null){
                        preChecked3 = ((InvestPupoViewHolder)holder).thirdImage;
                        preChecked3.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
                    }else{
                        preChecked3.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_off_whitegray));
                        ((InvestPupoViewHolder)holder).thirdImage.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
                        preChecked3 = ((InvestPupoViewHolder)holder).thirdImage;
                    }
                }
            });

        }

        else if(holder instanceof InvestMddRateViewHodler){

            ((InvestMddRateViewHodler)holder).per10Layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(investMddRate10Click != null){
                        investMddRate10Click.onClick(position);
                    }

                    if(preChecked4 == null){
                        preChecked4 = ((InvestMddRateViewHodler)holder).per10Image;
                        preChecked4.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
                    }else{
                        preChecked4.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_off_whitegray));
                        ((InvestMddRateViewHodler)holder).per10Image.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
                        preChecked4 = ((InvestMddRateViewHodler)holder).per10Image;
                    }
                }
            });

            ((InvestMddRateViewHodler)holder).per20Layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(investMddRate20Click != null){
                        investMddRate20Click.onClick(position);
                    }


                    if(preChecked4 == null){
                        preChecked4 = ((InvestMddRateViewHodler)holder).per20Image;
                        preChecked4.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
                    }else{
                        preChecked4.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_off_whitegray));
                        ((InvestMddRateViewHodler)holder).per20Image.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
                        preChecked4 = ((InvestMddRateViewHodler)holder).per20Image;
                    }
                }
            });

            ((InvestMddRateViewHodler)holder).per30Layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(investMddRate30Click != null){
                        investMddRate30Click.onClick(position);
                    }

                    if(preChecked4 == null){
                        preChecked4 = ((InvestMddRateViewHodler)holder).per30Image;
                        preChecked4.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
                    }else{
                        preChecked4.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_off_whitegray));
                        ((InvestMddRateViewHodler)holder).per30Image.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
                        preChecked4 = ((InvestMddRateViewHodler)holder).per30Image;
                    }
                }
            });

        }

        else if(holder instanceof FinanceKnowViewHolder){

            ((FinanceKnowViewHolder)holder).oneLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(financeKnowOneClick != null){
                        financeKnowOneClick.onClick(position);
                    }

                    if(preChecked5 == null){
                        preChecked5 = ((FinanceKnowViewHolder)holder).oneImage;
                        preChecked5.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
                    }else{
                        preChecked5.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_off_whitegray));
                        ((FinanceKnowViewHolder)holder).oneImage.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
                        preChecked5 = ((FinanceKnowViewHolder)holder).oneImage;
                    }
                }
            });

            ((FinanceKnowViewHolder)holder).twoLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(financeKnowTwoClick != null){
                        financeKnowTwoClick.onClick(position);
                    }

                    if(preChecked5 == null){
                        preChecked5 = ((FinanceKnowViewHolder)holder).twoImage;
                        preChecked5.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
                    }else{
                        preChecked5.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_off_whitegray));
                        ((FinanceKnowViewHolder)holder).twoImage.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
                        preChecked5 = ((FinanceKnowViewHolder)holder).twoImage;
                    }
                }
            });

            ((FinanceKnowViewHolder)holder).threeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(financeKnowThreeClick != null){
                        financeKnowThreeClick.onClick(position);
                    }

                    if(preChecked5 == null){
                        preChecked5 = ((FinanceKnowViewHolder)holder).threeImage;
                        preChecked5.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
                    }else{
                        preChecked5.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_off_whitegray));
                        ((FinanceKnowViewHolder)holder).threeImage.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
                        preChecked5 = ((FinanceKnowViewHolder)holder).threeImage;
                    }
                }
            });

            ((FinanceKnowViewHolder)holder).foreLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(financeKnowForeClick != null){
                        financeKnowForeClick.onClick(position);
                    }

                    if(preChecked5 == null){
                        preChecked5 = ((FinanceKnowViewHolder)holder).foreImage;
                        preChecked5.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
                    }else{
                        preChecked5.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_off_whitegray));
                        ((FinanceKnowViewHolder)holder).foreImage.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
                        preChecked5 = ((FinanceKnowViewHolder)holder).foreImage;
                    }
                }
            });

        }

        else if(holder instanceof InvestDeriExpViewHolder){

            ((InvestDeriExpViewHolder)holder).firstLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(investDeriExpOneClick != null){
                        investDeriExpOneClick.onClick(position);
                    }

                    if(preChecked6 == null){
                        preChecked6 = ((InvestDeriExpViewHolder)holder).firstImage;
                        preChecked6.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
                    }else{
                        preChecked6.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_off_whitegray));
                        ((InvestDeriExpViewHolder)holder).firstImage.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
                        preChecked6 = ((InvestDeriExpViewHolder)holder).firstImage;
                    }
                }
            });

            ((InvestDeriExpViewHolder)holder).secondLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(investDeriExpTwoClick != null){
                        investDeriExpTwoClick.onClick(position);
                    }

                    if(preChecked6 == null){
                        preChecked6 = ((InvestDeriExpViewHolder)holder).secondImage;
                        preChecked6.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
                    }else{
                        preChecked6.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_off_whitegray));
                        ((InvestDeriExpViewHolder)holder).secondImage.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
                        preChecked6 = ((InvestDeriExpViewHolder)holder).secondImage;
                    }
                }
            });

            ((InvestDeriExpViewHolder)holder).thirdLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(investDeriExpThreeClick != null){
                        investDeriExpThreeClick.onClick(position);
                    }

                    if(preChecked6 == null){
                        preChecked6 = ((InvestDeriExpViewHolder)holder).thirdImage;
                        preChecked6.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
                    }else{
                        preChecked6.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_off_whitegray));
                        ((InvestDeriExpViewHolder)holder).thirdImage.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
                        preChecked6 = ((InvestDeriExpViewHolder)holder).thirdImage;
                    }
                }
            });

        }

        else if(holder instanceof WeekFinanceViewHolder){
            ((WeekFinanceViewHolder)holder).infoBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(weekFinanceClick != null){
                        weekFinanceClick.onClick(position);
                    }
                }
            });
        }

        else if(holder instanceof InvestValidityViewHolder){

        }

        else if(holder instanceof MyChatViewHolder){

            ((MyChatViewHolder)holder).myText.setText(modelPropensities.get(position).getText());

        }

        else if(holder instanceof SpaceViewHolder){

        }


        else if(holder instanceof YieldViewHolder) {
            ((YieldViewHolder)holder).oneLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(yieldClick1 != null){
                        yieldClick1.onClick(position);
                    }

                    if(preChecked7 == null){
                        preChecked7 = ((YieldViewHolder)holder).oneImage;
                        preChecked7.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
                    }else{
                        preChecked7.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_off_whitegray));
                        ((YieldViewHolder)holder).oneImage.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
                        preChecked7 = ((YieldViewHolder)holder).oneImage;
                    }
                }
            });

            ((YieldViewHolder)holder).twoLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(yieldClick2 != null){
                        yieldClick2.onClick(position);
                    }

                    if(preChecked7 == null){
                        preChecked7 = ((YieldViewHolder)holder).twoImage;
                        preChecked7.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
                    }else{
                        preChecked7.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_off_whitegray));
                        ((YieldViewHolder)holder).twoImage.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
                        preChecked7 = ((YieldViewHolder)holder).twoImage;
                    }
                }
            });

            ((YieldViewHolder)holder).threeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(yieldClick3 != null){
                        yieldClick3.onClick(position);
                    }

                    if(preChecked7 == null){
                        preChecked7 = ((YieldViewHolder)holder).threeImage;
                        preChecked7.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
                    }else{
                        preChecked7.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_off_whitegray));
                        ((YieldViewHolder)holder).threeImage.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
                        preChecked7 = ((YieldViewHolder)holder).threeImage;
                    }
                }
            });

            ((YieldViewHolder)holder).foreLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(yieldClick4 != null){
                        yieldClick4.onClick(position);
                    }

                    if(preChecked7 == null){
                        preChecked7 = ((YieldViewHolder)holder).foreImage;
                        preChecked7.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
                    }else{
                        preChecked7.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_off_whitegray));
                        ((YieldViewHolder)holder).foreImage.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_checkbox_on_blue));
                        preChecked7 = ((YieldViewHolder)holder).foreImage;
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return modelPropensities.size();
    }

    @Override
    public int getItemViewType(int position) {

        switch (modelPropensities.get(position).getType()) {
            case 0:
                return INTRO;
            case 1:
                return HOPE;
            case -1:
                return RECOM;
            case 2:
                return YEARINPUT;
            case 3:
                return FINANCERATE;
            case 4:
                return INVESTEXP;
            case 5:
                return INVESTPUPOITEM;
            case 6:
                return INVESTITEMDATE;
            case 7:
                return INVESTPUPO;
            case 8:
                return INVESTMDDRATE;
            case 9:
                return FINANCEKNOW;
            case 10:
                return INVESTDERIEXP;
            case 11:
                return WEEKFINANCE;
            case 12:
                return INVESTVALIDITY;
            case 13:
                return MYCHAT;
            case 14:
                return YIELD;
            case 20:
                return SPACE;
            default:
                return INTRO;
        }
    }

    public class YieldViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout oneLayout, twoLayout, threeLayout, foreLayout;
        ImageView oneImage, twoImage, threeImage, foreImage;

        public YieldViewHolder(@NonNull View itemView) {
            super(itemView);

            oneLayout = itemView.findViewById(R.id.oneLayout);
            twoLayout = itemView.findViewById(R.id.twoLayout);
            threeLayout = itemView.findViewById(R.id.threeLayout);
            foreLayout = itemView.findViewById(R.id.foreLayout);

            oneImage = itemView.findViewById(R.id.oneImage);
            twoImage = itemView.findViewById(R.id.twoImage);
            threeImage = itemView.findViewById(R.id.threeImage);
            foreImage = itemView.findViewById(R.id.foreImage);

        }
    }

    public class IntroViewHolder extends RecyclerView.ViewHolder {
        public IntroViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class HopeViewHolder extends RecyclerView.ViewHolder {
        public HopeViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class RecomViewHolder extends RecyclerView.ViewHolder {
        public RecomViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class YearInputViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout firstLayout, secondLayout, thirdLayout;
        ImageView firstImage, secondImage ,thirdImage;

        public YearInputViewHolder(@NonNull View itemView) {
            super(itemView);

            firstLayout = itemView.findViewById(R.id.firstLayout);
            secondLayout = itemView.findViewById(R.id.secondLayout);
            thirdLayout = itemView.findViewById(R.id.thirdLayout);

            firstImage = itemView.findViewById(R.id.firstImage);
            secondImage = itemView.findViewById(R.id.secondImage);
            thirdImage = itemView.findViewById(R.id.thirdImage);
        }
    }

    public class FinanceRateViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout per5Layout, per10Layout, per20Layout, per30Layout, per30UpLayout;
        ImageView per5Image, per10Image, per20Image, per30Image, per30UpImage;

        public FinanceRateViewHolder(@NonNull View itemView) {
            super(itemView);

            per5Layout = itemView.findViewById(R.id.per5Layout);
            per10Layout = itemView.findViewById(R.id.per10Layout);
            per20Layout = itemView.findViewById(R.id.per20Layout);
            per30Layout = itemView.findViewById(R.id.per30Layout);
            per30UpLayout = itemView.findViewById(R.id.per30UpLayout);

            per5Image = itemView.findViewById(R.id.per5Image);
            per10Image = itemView.findViewById(R.id.per10Image);
            per20Image = itemView.findViewById(R.id.per20Image);
            per30Image = itemView.findViewById(R.id.per30Image);
            per30UpImage = itemView.findViewById(R.id.per30UpImage);


        }
    }

    public class InvestExpViewHolder extends RecyclerView.ViewHolder {
        public InvestExpViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class InvestPupoItemViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout oneLayout, twoLayout, threeLayout, foreLayout, fiveLayout, sixLayout, sevenLayout, eightLayout, nineLayout, tenLayout, layout11, layout12, layout13, countLayout;
        ImageView stockImage, warpImage, mixImage, fundImage, outImage, optionImage, trustImage, bondImage, elwImage, elsImage, Image11, Image12, Image13;
        TextView okText, countText;
        Space space;

        public InvestPupoItemViewHolder(@NonNull View itemView) {
            super(itemView);

            oneLayout = itemView.findViewById(R.id.oneLayout);
            twoLayout = itemView.findViewById(R.id.twoLayout);
            threeLayout = itemView.findViewById(R.id.threeLayout);
            foreLayout = itemView.findViewById(R.id.foreLayout);
            fiveLayout = itemView.findViewById(R.id.fiveLayout);
            sixLayout = itemView.findViewById(R.id.sixLayout);
            sevenLayout = itemView.findViewById(R.id.sevenLayout);
            eightLayout = itemView.findViewById(R.id.eightLayout);
            nineLayout = itemView.findViewById(R.id.nineLayout);
            tenLayout = itemView.findViewById(R.id.tenLayout);

            layout11 = itemView.findViewById(R.id.layout11);
            layout12 = itemView.findViewById(R.id.layout12);
            layout13 = itemView.findViewById(R.id.layout13);

            okText = itemView.findViewById(R.id.okText);

            stockImage = itemView.findViewById(R.id.stockImage);
            warpImage = itemView.findViewById(R.id.warpImage);
            mixImage = itemView.findViewById(R.id.mixImage);
            fundImage = itemView.findViewById(R.id.fundImage);
            outImage = itemView.findViewById(R.id.outImage);
            optionImage = itemView.findViewById(R.id.optionImage);
            trustImage = itemView.findViewById(R.id.trustImage);
            bondImage = itemView.findViewById(R.id.bondImage);
            elwImage = itemView.findViewById(R.id.elwImage);
            elsImage = itemView.findViewById(R.id.elsImage);
            Image11 = itemView.findViewById(R.id.Image11);
            Image12 = itemView.findViewById(R.id.Image12);
            Image13 = itemView.findViewById(R.id.Image13);

            countLayout = itemView.findViewById(R.id.countLayout);
            countText = itemView.findViewById(R.id.countText);

            space = itemView.findViewById(R.id.space);
        }
    }

    public class InvestItemDateViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout firstLayout, secondLayout, thirdLayout;
        ImageView firstImage, secondImage, thirdImage;

        public InvestItemDateViewHolder(@NonNull View itemView) {
            super(itemView);
            firstLayout = itemView.findViewById(R.id.firstLayout);
            secondLayout = itemView.findViewById(R.id.secondLayout);
            thirdLayout = itemView.findViewById(R.id.thirdLayout);

            firstImage = itemView.findViewById(R.id.firstImage);
            secondImage = itemView.findViewById(R.id.secondImage);
            thirdImage = itemView.findViewById(R.id.thirdImage);

        }
    }

    public class InvestPupoViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout firstLayout, secondLayout, thirdLayout;
        ImageView firstImage, secondImage, thirdImage;

        public InvestPupoViewHolder(@NonNull View itemView) {
            super(itemView);

            firstLayout = itemView.findViewById(R.id.firstLayout);
            secondLayout = itemView.findViewById(R.id.secondLayout);
            thirdLayout = itemView.findViewById(R.id.thirdLayout);

            firstImage = itemView.findViewById(R.id.firstImage);
            secondImage = itemView.findViewById(R.id.secondImage);
            thirdImage = itemView.findViewById(R.id.thirdImage);

        }
    }

    public class InvestMddRateViewHodler extends RecyclerView.ViewHolder {

        ConstraintLayout per10Layout, per20Layout, per30Layout;
        ImageView per10Image, per20Image, per30Image;

        public InvestMddRateViewHodler(@NonNull View itemView) {
            super(itemView);

            per10Layout = itemView.findViewById(R.id.per10Layout);
            per20Layout = itemView.findViewById(R.id.per20Layout);
            per30Layout = itemView.findViewById(R.id.per30Layout);

            per10Image = itemView.findViewById(R.id.per10Image);
            per20Image = itemView.findViewById(R.id.per20Image);
            per30Image = itemView.findViewById(R.id.per30Image);
        }
    }

    public class FinanceKnowViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout oneLayout, twoLayout, threeLayout, foreLayout;
        ImageView oneImage, twoImage, threeImage, foreImage;

        public FinanceKnowViewHolder(@NonNull View itemView) {
            super(itemView);

            oneLayout = itemView.findViewById(R.id.oneLayout);
            twoLayout = itemView.findViewById(R.id.twoLayout);
            threeLayout = itemView.findViewById(R.id.threeLayout);
            foreLayout = itemView.findViewById(R.id.foreLayout);

            oneImage = itemView.findViewById(R.id.oneImage);
            twoImage = itemView.findViewById(R.id.twoImage);
            threeImage = itemView.findViewById(R.id.threeImage);
            foreImage = itemView.findViewById(R.id.foreImage);

        }
    }

    public class InvestDeriExpViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout firstLayout, secondLayout, thirdLayout;
        ImageView firstImage, secondImage, thirdImage;

        public InvestDeriExpViewHolder(@NonNull View itemView) {
            super(itemView);

            firstLayout = itemView.findViewById(R.id.firstLayout);
            secondLayout = itemView.findViewById(R.id.secondLayout);
            thirdLayout = itemView.findViewById(R.id.thirdLayout);

            firstImage = itemView.findViewById(R.id.firstImage);
            secondImage = itemView.findViewById(R.id.secondImage);
            thirdImage = itemView.findViewById(R.id.thirdImage);

        }
    }

    public class WeekFinanceViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout infoBt;

        public WeekFinanceViewHolder(@NonNull View itemView) {
            super(itemView);

            infoBt = itemView.findViewById(R.id.infoBt);
        }
    }

    public class InvestValidityViewHolder extends RecyclerView.ViewHolder {
        public InvestValidityViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class MyChatViewHolder extends RecyclerView.ViewHolder {

        TextView myText;

        public MyChatViewHolder(@NonNull View itemView) {
            super(itemView);

            myText = itemView.findViewById(R.id.myText);
        }
    }

    public class SpaceViewHolder extends RecyclerView.ViewHolder {
        public SpaceViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
package quantec.com.moneypot.Activity.Main.Fragment.Tab1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.MPPointF;
import com.tobiasschuerg.prefixsuffix.PrefixSuffixEditText;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import quantec.com.moneypot.R;
import quantec.com.moneypot.util.ChangeUnitToPrice.ChangeUnitToPrice;
import quantec.com.moneypot.util.removezero.RemoveZero;

public class AdapterFitPot extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int WEBVIEWEMPTY = 0;
    private final int WEBVIEWITEM = 1;

    private final int ADDVIEW = 2;
    private final int INSERTVIEW = 3;
    private final int CHARTVIEW = 4;
    private final int RECOMVIEW = 5;
    private final int INVESTVIEW = 6;


    ArrayList<ModelFitPotList> modelFitPotLists;
    Context context;

    ArrayList<ModelAccountWebView> modelAccountWebViews;


    List<Entry> entries;
    ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();
    LineDataSet lineDataSet, lineDataSet2;
    LineData lineData;
    XAxis xAxis;

    List<Entry> entries2;
    float currentX, maxX;


    private DecimalFormat decimalFormat = new DecimalFormat("#,###");
    private String nowResult="";
    private String addResult="";
    private String finalResult="";

    public AdapterFitPot(ArrayList<ModelFitPotList> modelFitPotLists, Context context, ArrayList<ModelAccountWebView> modelAccountWebViews) {
        this.modelFitPotLists = modelFitPotLists;
        this.context = context;
        this.modelAccountWebViews = modelAccountWebViews;
    }

    boolean isFront = true;
    private int DURATION = 500;
    private float centerX;
    private float centerY;


    private EmptyTotalPriceClick emptyTotalPriceClick;
    public interface EmptyTotalPriceClick{
        public void onClick(int position);
    }

    public void setEmptyTotalPriceClick(EmptyTotalPriceClick emptyTotalPriceClick) {
        this.emptyTotalPriceClick = emptyTotalPriceClick;
    }

    private EmptyLifeChallengeClick emptyLifeChallengeClick;
    public interface EmptyLifeChallengeClick{
        public void onClick(int position);
    }

    public void setEmptyLifeChallengeClick(EmptyLifeChallengeClick emptyLifeChallengeClick) {
        this.emptyLifeChallengeClick = emptyLifeChallengeClick;
    }

    private LifeChallengeClick lifeChallengeClick;
    public interface LifeChallengeClick{
        public void onClick(int position);
    }

    public void setLifeChallengeClick(LifeChallengeClick lifeChallengeClick) {
        this.lifeChallengeClick = lifeChallengeClick;
    }

    private ChangedStViewClick changedStViewClick;
    public interface ChangedStViewClick {
        public void onClick(int position);
    }

    public void setChangedStViewClick(ChangedStViewClick changedStViewClick) {
        this.changedStViewClick = changedStViewClick;
    }


    private NextBtClick nextBtClick;
    public interface NextBtClick {
        public void onClick(int position);
    }

    public void setNextBtClick(NextBtClick nextBtClick) {
        this.nextBtClick = nextBtClick;
    }


//    private ChartNextBtClick chartNextBtClick;
//    public interface ChartNextBtClick {
//        public void onClick(int position);
//    }
//
//    public void setChartNextBtClick(ChartNextBtClick chartNextBtClick) {
//        this.chartNextBtClick = chartNextBtClick;
//    }





    private AccountBtClick accountBtClick;
    public interface AccountBtClick {
        public void onClick(int position);
    }

    public void setAccountBtClick(AccountBtClick accountBtClick) {
        this.accountBtClick = accountBtClick;
    }

    private AddItemViewClick addItemViewClick;
    public interface AddItemViewClick {
        public void onClick(int position);
    }

    public void setAddItemViewClick(AddItemViewClick addItemViewClick) {
        this.addItemViewClick = addItemViewClick;
    }

    private InsertNextBtClick insertNextBtClick;
    public interface InsertNextBtClick {
        public void onClick(int position);
    }

    public void setInsertNextBtClick(InsertNextBtClick insertNextBtClick) {
        this.insertNextBtClick = insertNextBtClick;
    }

    private ChartNextBtClick chartNextBtClick;
    public interface ChartNextBtClick {
        public void onClick(int position);
    }

    public void setChartNextBtClick(ChartNextBtClick chartNextBtClick) {
        this.chartNextBtClick = chartNextBtClick;
    }

    private NowPriceText nowPriceText;
    public interface NowPriceText {
        public void onText(String text);
    }

    public void setNowPriceText(NowPriceText nowPriceText) {
        this.nowPriceText = nowPriceText;
    }

    private AddPriceText addPriceText;
    public interface AddPriceText {
        public void onText(String text);
    }

    public void setAddPriceText(AddPriceText addPriceText) {
        this.addPriceText = addPriceText;
    }

    private FinalPriceText finalPriceText;
    public interface FinalPriceText {
        public void onText(String text);
    }

    public void setFinalPriceText(FinalPriceText finalPriceText) {
        this.finalPriceText = finalPriceText;
    }

    private ChartBackBt chartBackBt;
    public interface ChartBackBt {
        public void onClick(int position);
    }

    public void setChartBackBt(ChartBackBt chartBackBt) {
        this.chartBackBt = chartBackBt;
    }

    private ChartDate chartDate;
    public interface ChartDate {
        public void onText(int year);
    }

    public void setChartDate(ChartDate chartDate) {
        this.chartDate = chartDate;
    }


    private ShowChartView showChartView;
    public interface ShowChartView {
        public void onClick(int position);
    }

    public void setShowChartView(ShowChartView showChartView) {
        this.showChartView = showChartView;
    }

    private DeleteLifeBt deleteLifeBt;
    public interface DeleteLifeBt {
        public void onClick(int position);
    }

    public void setDeleteLifeBt(DeleteLifeBt deleteLifeBt) {
        this.deleteLifeBt = deleteLifeBt;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == WEBVIEWEMPTY){
            return new WebViewEmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_maintab1_webviewempty, parent, false));
        }
        else if(viewType == WEBVIEWITEM){
            return new WebViewItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_maintab1_webviewitem, parent, false));
        }
        else if(viewType == ADDVIEW){
            return new LifeAddViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_maintab1_addview, parent, false));
        }
        else if(viewType == INSERTVIEW){
            return new LifeInsertViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_maintab1_insertview, parent, false));
        }
        else if(viewType == CHARTVIEW){
            return new LifeChartViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_maintab1_chartview, parent, false));
        }
        else if(viewType == RECOMVIEW){
            return new LifeRecomViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_maintab1_recomview, parent, false));
        }
        else{
            return new LifeInvestViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_maintab1_investview, parent, false));
        }
    }

//    if(viewType == WEBVIEWEMPTY){
//        return new WebViewEmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_maintab1_custom, parent, false));
//    }
//        else if(viewType == WEBVIEWITEM){
//        return new WebViewItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_maintab1_chart, parent, false));
//    }
//        else if(viewType == ADDVIEW){
//        return new LifeAddViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fitpot_emptytotalprice, parent, false));
//    }
//        else if(viewType == INSERTVIEW){
//        return new LifeInsertViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fitpot_lifechallenge, parent, false));
//    }
//        else if(viewType == CHARTVIEW){
//        return new LifeChartViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fitpot_lifechallenge, parent, false));
//    }
//        else if(viewType == RECOMVIEW){
//        return new LifeRecomViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fitpot_lifechallenge, parent, false));
//    }
//        else{
//        return new LifeInvestViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fitpot_totalprice, parent, false));
//    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof WebViewEmptyViewHolder){

            ((WebViewEmptyViewHolder)holder).accountBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(accountBtClick != null){
                        accountBtClick.onClick(position);
                    }
                }
            });
        }

        if(holder instanceof WebViewItemViewHolder){
        }

        if(holder instanceof LifeAddViewHolder){
            ((LifeAddViewHolder)holder).addItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(addItemViewClick != null){
                        addItemViewClick.onClick(position);
                    }
                }
            });
        }

        if(holder instanceof LifeInsertViewHolder){
            ((LifeInsertViewHolder)holder).nowPrice.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    ((LifeInsertViewHolder)holder).nowPrice.setSuffix("만원");

                    if(!TextUtils.isEmpty(s.toString()) && !s.toString().equals(nowResult)){
                        ((LifeInsertViewHolder)holder).preNowPrice.setVisibility(View.VISIBLE);
                        if(s.toString().startsWith("0")){
                            nowResult = RemoveZero.removeZero(s.toString());
                            ((LifeInsertViewHolder)holder).nowPrice.setText(nowResult);
                            ((LifeInsertViewHolder)holder).nowPrice.setSelection(nowResult.length());
                        }else{

                            nowResult = s.toString();
                            ((LifeInsertViewHolder)holder).nowPrice.setText(nowResult);
                            ((LifeInsertViewHolder)holder).nowPrice.setSelection(nowResult.length());
                        }
                        if(nowPriceText != null){
                            nowPriceText.onText(nowResult);
                        }
                    }
                    else if(TextUtils.isEmpty(s.toString())){
                        if(nowPriceText != null){
                            nowPriceText.onText(s.toString());
                        }
                        ((LifeInsertViewHolder)holder).preNowPrice.setVisibility(View.INVISIBLE);
                        ((LifeInsertViewHolder)holder).nowPrice.setSuffix("");
                    }
                    ((LifeInsertViewHolder)holder).preNowPrice.setText(ChangeUnitToPrice.changeUnitToPrice(nowResult));
//                    if(nowResult.length() <= 4){
//                        ((LifeInsertViewHolder)holder).preNowPrice.setText(nowResult+"만원");
//                    }else{
//                        ((LifeInsertViewHolder)holder).preNowPrice.setText(ChangeUnitToPrice.changeUnitToPrice(nowResult));
////                        String uc, man;
////                        if(nowResult.length() == 5){
////                            uc = nowResult.substring(0, 1);
////                            man = nowResult.substring(1);
////                        }else {
////                            uc = nowResult.substring(0, 2);
////                            man =nowResult.substring(2);
////                        }
////
////                        if(man.startsWith("0")){
////                            man = RemoveZero.removeZero(man);
////                        }
////
////                        if(man.length() != 0){
////                            ((LifeInsertViewHolder)holder).preNowPrice.setText(uc+"억 "+man+"만원");
////                        }else{
////                            ((LifeInsertViewHolder)holder).preNowPrice.setText(uc+"억");
////                        }
//                    }
                }
                @Override
                public void afterTextChanged(Editable s) {
                }
            });


            ((LifeInsertViewHolder)holder).addPrice.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    ((LifeInsertViewHolder)holder).addPrice.setSuffix("만원");

                    if(!TextUtils.isEmpty(s.toString()) && !s.toString().equals(addResult)){
                        ((LifeInsertViewHolder)holder).preAddPrice.setVisibility(View.VISIBLE);
                        if(s.toString().startsWith("0")){
                            addResult = RemoveZero.removeZero(s.toString());
                            ((LifeInsertViewHolder)holder).addPrice.setText(addResult);
                            ((LifeInsertViewHolder)holder).addPrice.setSelection(addResult.length());
                        }else{

                            addResult = s.toString();
                            ((LifeInsertViewHolder)holder).addPrice.setText(addResult);
                            ((LifeInsertViewHolder)holder).addPrice.setSelection(addResult.length());
                        }
                        if(addPriceText != null){
                            addPriceText.onText(addResult);
                        }
                    }
                    else if(TextUtils.isEmpty(s.toString())){
                        if(addPriceText != null){
                            addPriceText.onText(s.toString());
                        }
                        ((LifeInsertViewHolder)holder).preAddPrice.setVisibility(View.INVISIBLE);
                        ((LifeInsertViewHolder)holder).addPrice.setSuffix("");
                    }

                    ((LifeInsertViewHolder)holder).preAddPrice.setText(ChangeUnitToPrice.changeUnitToPrice(addResult));

//                    if(addResult.length() <= 4){
//                        ((LifeInsertViewHolder)holder).preAddPrice.setText(addResult+"만원");
//                    }else{
//                        ((LifeInsertViewHolder)holder).preAddPrice.setText(ChangeUnitToPrice.changeUnitToPrice(addResult));
////                        String uc, man;
////                        if(addResult.length() == 5){
////                            uc = addResult.substring(0, 1);
////                            man = addResult.substring(1);
////                        }else {
////                            uc = addResult.substring(0, 2);
////                            man =addResult.substring(2);
////                        }
////
////                        if(man.startsWith("0")){
////                            man = RemoveZero.removeZero(man);
////                        }
////
////                        if(man.length() != 0){
////                            ((LifeInsertViewHolder)holder).preAddPrice.setText(uc+"억 "+man+"만원");
////                        }else{
////                            ((LifeInsertViewHolder)holder).preAddPrice.setText(uc+"억");
////                        }
//                    }
                }
                @Override
                public void afterTextChanged(Editable s) {
                }
            });


            ((LifeInsertViewHolder)holder).finalPrice.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    ((LifeInsertViewHolder)holder).finalPrice.setSuffix("만원");

                    if(!TextUtils.isEmpty(s.toString()) && !s.toString().equals(finalResult)){
                        ((LifeInsertViewHolder)holder).preFinalPrice.setVisibility(View.VISIBLE);
                        if(s.toString().startsWith("0")){
                            finalResult = RemoveZero.removeZero(s.toString());
                            ((LifeInsertViewHolder)holder).finalPrice.setText(finalResult);
                            ((LifeInsertViewHolder)holder).finalPrice.setSelection(finalResult.length());
                        }else{

                            finalResult = s.toString();
                            ((LifeInsertViewHolder)holder).finalPrice.setText(finalResult);
                            ((LifeInsertViewHolder)holder).finalPrice.setSelection(finalResult.length());
                        }
                        if(finalPriceText != null){
                            finalPriceText.onText(finalResult);
                        }
                    }
                    else if(TextUtils.isEmpty(s.toString())){
                        if(finalPriceText != null){
                            finalPriceText.onText(s.toString());
                        }
                        ((LifeInsertViewHolder)holder).preFinalPrice.setVisibility(View.INVISIBLE);
                        ((LifeInsertViewHolder)holder).finalPrice.setSuffix("");
                    }

                    ((LifeInsertViewHolder)holder).preFinalPrice.setText(ChangeUnitToPrice.changeUnitToPrice(finalResult));
//                    if(finalResult.length() <= 4){
//                        ((LifeInsertViewHolder)holder).preFinalPrice.setText(finalResult+"만원");
//                    }else{
//
//                        ((LifeInsertViewHolder)holder).preFinalPrice.setText(ChangeUnitToPrice.changeUnitToPrice(finalResult));
//
////                        String uc, man;
////                        if(finalResult.length() == 5){
////                            uc = finalResult.substring(0, 1);
////                            man = finalResult.substring(1);
////                        }else {
////                            uc = finalResult.substring(0, 2);
////                            man = finalResult.substring(2);
////                        }
////
////                        if(man.startsWith("0")){
////                            man = RemoveZero.removeZero(man);
////                        }
////
////                        if(man.length() != 0){
////                            ((LifeInsertViewHolder)holder).preFinalPrice.setText(uc+"억 "+man+"만원");
////                        }else{
////                            ((LifeInsertViewHolder)holder).preFinalPrice.setText(uc+"억");
////                        }
//                    }

                }
                @Override
                public void afterTextChanged(Editable s) {
                }
            });


            ((LifeInsertViewHolder)holder).nextBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(insertNextBtClick != null){
                        insertNextBtClick.onClick(position);

                        ((LifeInsertViewHolder)holder).nowPrice.setText("");
                        ((LifeInsertViewHolder)holder).addPrice.setText("");
                        ((LifeInsertViewHolder)holder).finalPrice.setText("");
                    }
                }
            });
        }

        if(holder instanceof LifeChartViewHolder){

            ((LifeChartViewHolder)holder).backBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(chartBackBt != null){
                        chartBackBt.onClick(position);
                    }
                }
            });

            ((LifeChartViewHolder)holder).nextBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(chartNextBtClick != null){
                        chartNextBtClick.onClick(position);
                    }
                }
            });

            entries = new ArrayList<>();
            entries2 = new ArrayList<>();

            entries.clear();
            entries2.clear();

            float calTotalPrice, calTotalNormalPrice,
                  calPotYear, calYield,
                  calNormalYear, calNormalYield;

                entries.add(new Entry(0, (modelFitPotLists.get(position).getNowPrice()*10000), (modelFitPotLists.get(position).getNowPrice()*10000)));
                float price = (modelFitPotLists.get(position).getNowPrice() + (modelFitPotLists.get(position).getMonPrice() * 12)) * 1.09f;
                entries.add(new Entry(1, price*10000, price*10000));
                calPotYear = 1;

                calTotalPrice = (long)(modelFitPotLists.get(position).getNowPrice() + modelFitPotLists.get(position).getMonPrice()*12);
                calYield = (long) (price - calTotalPrice);
                if (price < modelFitPotLists.get(position).getFinalPrice()) {
                    for (int year = 2; year < 10000; year++) {
                        price = (price + (modelFitPotLists.get(position).getMonPrice() * 12)) * 1.09f;
                        entries.add(new Entry(year, price*10000, price*10000));
                        if (price >= modelFitPotLists.get(position).getFinalPrice()) {
                            calPotYear = year;
                            calTotalPrice = (long) (modelFitPotLists.get(position).getNowPrice() + (modelFitPotLists.get(position).getMonPrice() * 12 * calPotYear));
                            calYield = (long) (price - calTotalPrice);
                            break;
                        }
                    }
                }

                entries2.add(new Entry(0, modelFitPotLists.get(position).getNowPrice()*10000, modelFitPotLists.get(position).getNowPrice()*10000));
                float price2 = (modelFitPotLists.get(position).getNowPrice() + (modelFitPotLists.get(position).getMonPrice() * 12)) * 1.03f;
                entries2.add(new Entry(1, price2*10000, price2*10000));
                calNormalYear = 1;

                calTotalNormalPrice = (long)(modelFitPotLists.get(position).getNowPrice() + modelFitPotLists.get(position).getMonPrice()*12);
                calNormalYield = (long)(price2 - calTotalNormalPrice);
                if(price2 < modelFitPotLists.get(position).getFinalPrice()){
                    for (int year2 = 2; year2 < 1000; year2++) {
                        price2 = (price2 + (modelFitPotLists.get(position).getMonPrice() * 12)) * 1.03f;
                        entries2.add(new Entry(year2, price2*10000, price2*10000));
                        if (price2 >= modelFitPotLists.get(position).getFinalPrice()) {
                            calNormalYear = year2;
                            calTotalNormalPrice = (long) (modelFitPotLists.get(position).getNowPrice() + (modelFitPotLists.get(position).getMonPrice() * 12 * calNormalYear));
                            calNormalYield = (long) (price2 - calTotalNormalPrice);
                            break;
                        }
                    }
                }

                if(chartDate != null){
                    chartDate.onText((int)calPotYear);
                }


            ((LifeChartViewHolder)holder).datePotText.setText((int)calPotYear+"년");
            ((LifeChartViewHolder)holder).dateYText.setText((int)calNormalYear+"년");

            ((LifeChartViewHolder)holder).totalPotText.setText(addCom((long)calTotalPrice*10000)+"원");
            ((LifeChartViewHolder)holder).totalYText.setText(addCom((long)calTotalNormalPrice*10000)+"원");

            ((LifeChartViewHolder)holder).yieldPotText.setText(addCom((long)calYield*10000)+"원");
            ((LifeChartViewHolder)holder).yieldYText.setText(addCom((long)calNormalYield*10000)+"원");

                lineDataSet = new LineDataSet(entries, "머니포트");
                lineDataSet.setLineWidth(1.5f);
                lineDataSet.setColor(Color.parseColor("#FFFF0000"));
                lineDataSet.setDrawHorizontalHighlightIndicator(false);
                lineDataSet.setDrawValues(false);
                lineDataSet.setHighlightEnabled(true);
                lineDataSet.setDrawHighlightIndicators(true);
                lineDataSet.setDrawHorizontalHighlightIndicator(false);
                lineDataSet.setDrawVerticalHighlightIndicator(false);
                lineDataSet.setHighlightLineWidth(0f);
                lineDataSet.setHighLightColor(Color.parseColor("#FFFFFFFF"));
                lineDataSet.setDrawCircles(false);

                lineDataSet2 = new LineDataSet(entries2, "예적금");
                lineDataSet2.setLineWidth(1.5f);
                lineDataSet2.setColor(Color.parseColor("#E1E1E1"));
                lineDataSet2.setDrawHorizontalHighlightIndicator(false);
                lineDataSet2.setDrawValues(false);
                lineDataSet2.setHighLightColor(Color.parseColor("#FFFFFFFF"));
                lineDataSet2.setHighlightEnabled(true);
                lineDataSet2.setDrawHighlightIndicators(true);
                lineDataSet2.setDrawHorizontalHighlightIndicator(false);
                lineDataSet2.setDrawVerticalHighlightIndicator(false);
                lineDataSet2.setHighlightLineWidth(0f);
                lineDataSet2.setDrawCircles(false);

                lineDataSets.clear();

                lineDataSets.add(lineDataSet);
                lineDataSets.add(lineDataSet2);

                lineData = new LineData(lineDataSets);
                lineData.setHighlightEnabled(false);

                xAxis = ((LifeChartViewHolder) holder).chartView.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

                if(entries2.size() <= 12){
                    xAxis.setLabelCount(entries2.size(), true);
                }

                xAxis.setGranularity(1f);
                xAxis.setDrawLabels(true);
                xAxis.setDrawGridLines(false);
                xAxis.setDrawAxisLine(true);
                xAxis.setEnabled(true);

                YAxis yAxis = ((LifeChartViewHolder) holder).chartView.getAxisLeft();
                yAxis.setDrawLabels(false);
                yAxis.setDrawGridLines(false);
                yAxis.setDrawAxisLine(false);
                yAxis.setEnabled(false);

                YAxis yAxi1 = ((LifeChartViewHolder) holder).chartView.getAxisRight();
                yAxi1.setDrawLabels(false);
                yAxi1.setDrawGridLines(false);
                yAxi1.setDrawAxisLine(false);
                yAxi1.setEnabled(false);

                Legend legend = ((LifeChartViewHolder) holder).chartView.getLegend();
                legend.setForm(Legend.LegendForm.CIRCLE);
                legend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
                legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
                legend.setOrientation(Legend.LegendOrientation.VERTICAL);
                legend.setEnabled(true);
                legend.setDrawInside(true);

                ((LifeChartViewHolder) holder).chartView.setDescription(null);
                ((LifeChartViewHolder) holder).chartView.setDrawGridBackground(false);
                ((LifeChartViewHolder) holder).chartView.setData(lineData);
                ((LifeChartViewHolder) holder).chartView.setDoubleTapToZoomEnabled(false);
                ((LifeChartViewHolder) holder).chartView.setDrawGridBackground(false);
                ((LifeChartViewHolder) holder).chartView.animateY(600, Easing.EasingOption.EaseInCubic);
                ((LifeChartViewHolder) holder).chartView.setPinchZoom(false);
//                ((LifeChartViewHolder) holder).chartView.setScaleEnabled(false);
                ((LifeChartViewHolder) holder).chartView.invalidate();

                maxX = ((LifeChartViewHolder) holder).chartView.getXRange();

                CustomMarkerView marker = new CustomMarkerView(context, R.layout.item_chatbotchart_marker);
                marker.setChartView(((LifeChartViewHolder) holder).chartView);

                ((LifeChartViewHolder) holder).chartView.highlightValue(new Highlight(entries.get(entries.size()-1).getX(), entries.get(entries.size()-1).getY(), 0), false);
                marker.setEnabled(false);

                ((LifeChartViewHolder) holder).chartView.getMarkerView();
                ((LifeChartViewHolder) holder).chartView.setDrawMarkers(true);
                ((LifeChartViewHolder) holder).chartView.getData().setHighlightEnabled(true);
                ((LifeChartViewHolder) holder).chartView.setMarker(marker);
                ((LifeChartViewHolder) holder).chartView.invalidate();

                ((LifeChartViewHolder) holder).chartView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return true;
                    }
                });

        }

        if(holder instanceof LifeRecomViewHolder){

            ((LifeRecomViewHolder)holder).title.setText(modelFitPotLists.get(position).getLifeName());
            ((LifeRecomViewHolder)holder).desc.setText(modelFitPotLists.get(position).getLifeDesc());
            ((LifeRecomViewHolder)holder).dateTitle.setText(modelFitPotLists.get(position).getLifeDate());
            ((LifeRecomViewHolder)holder).priceTitle.setText(modelFitPotLists.get(position).getLifePrice());
            ((LifeRecomViewHolder)holder).typeTitle.setText(modelFitPotLists.get(position).getLifeTag());
            ((LifeRecomViewHolder)holder).potTitle.setText(modelFitPotLists.get(position).getLifePotName());
            ((LifeRecomViewHolder)holder).potImage.setImageDrawable(context.getResources().getDrawable(R.drawable.group_1523));
            ((LifeRecomViewHolder)holder).rate.setText(String.valueOf(modelFitPotLists.get(position).getLifePotRate()));

            Glide.with(context)
                    .load(modelFitPotLists.get(position).getLifeImageUrl())
                    .error(context.getResources().getDrawable(R.drawable.img_mini_2))
                    .into(((LifeRecomViewHolder)holder).image);


            if(modelFitPotLists.get(position).getLifePotRate() < 0){
                ((LifeRecomViewHolder)holder).rate.setTextColor(context.getResources().getColor(R.color.blue_color));
                ((LifeRecomViewHolder)holder).per.setTextColor(context.getResources().getColor(R.color.blue_color));
            }else{
                ((LifeRecomViewHolder)holder).rate.setTextColor(context.getResources().getColor(R.color.red_text_color));
                ((LifeRecomViewHolder)holder).per.setTextColor(context.getResources().getColor(R.color.red_text_color));
            }

            ((LifeRecomViewHolder)holder).deleteBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(deleteLifeBt != null){
                        deleteLifeBt.onClick(position);
                    }
                }
            });

            ((LifeRecomViewHolder)holder).showChartBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(showChartView != null){
                        showChartView.onClick(position);
                    }
                }
            });

            if(modelFitPotLists.get(position).isShowChart()){
                ((LifeRecomViewHolder)holder).chartLayout.setVisibility(View.VISIBLE);

                entries = new ArrayList<>();
                entries2 = new ArrayList<>();

                entries.clear();
                entries2.clear();

                float calTotalPrice, calTotalNormalPrice,
                        calPotYear, calYield,
                        calNormalYear, calNormalYield;

                entries.add(new Entry(0, (modelFitPotLists.get(position).getNowPrice()*10000), (modelFitPotLists.get(position).getNowPrice()*10000)));
                float price = (modelFitPotLists.get(position).getNowPrice() + (modelFitPotLists.get(position).getMonPrice() * 12)) * 1.09f;
                entries.add(new Entry(1, price*10000, price*10000));
                calPotYear = 1;

                calTotalPrice = (long)(modelFitPotLists.get(position).getNowPrice() + modelFitPotLists.get(position).getMonPrice()*12);
                calYield = (long) (price - calTotalPrice);
                if (price < modelFitPotLists.get(position).getFinalPrice()) {
                    for (int year = 2; year < 10000; year++) {
                        price = (price + (modelFitPotLists.get(position).getMonPrice() * 12)) * 1.09f;
                        entries.add(new Entry(year, price*10000, price*10000));
                        if (price >= modelFitPotLists.get(position).getFinalPrice()) {
                            calPotYear = year;
                            calTotalPrice = (long) (modelFitPotLists.get(position).getNowPrice() + (modelFitPotLists.get(position).getMonPrice() * 12 * calPotYear));
                            calYield = (long) (price - calTotalPrice);
                            break;
                        }
                    }
                }

                entries2.add(new Entry(0, modelFitPotLists.get(position).getNowPrice()*10000, modelFitPotLists.get(position).getNowPrice()*10000));
                float price2 = (modelFitPotLists.get(position).getNowPrice() + (modelFitPotLists.get(position).getMonPrice() * 12)) * 1.03f;
                entries2.add(new Entry(1, price2*10000, price2*10000));
                calNormalYear = 1;

                calTotalNormalPrice = (long)(modelFitPotLists.get(position).getNowPrice() + modelFitPotLists.get(position).getMonPrice()*12);
                calNormalYield = (long)(price2 - calTotalNormalPrice);
                if(price2 < modelFitPotLists.get(position).getFinalPrice()){
                    for (int year2 = 2; year2 < 1000; year2++) {
                        price2 = (price2 + (modelFitPotLists.get(position).getMonPrice() * 12)) * 1.03f;
                        entries2.add(new Entry(year2, price2*10000, price2*10000));
                        if (price2 >= modelFitPotLists.get(position).getFinalPrice()) {
                            calNormalYear = year2;
                            calTotalNormalPrice = (long) (modelFitPotLists.get(position).getNowPrice() + (modelFitPotLists.get(position).getMonPrice() * 12 * calNormalYear));
                            calNormalYield = (long) (price2 - calTotalNormalPrice);
                            break;
                        }
                    }
                }

                if(chartDate != null){
                    chartDate.onText((int)calPotYear);
                }


                ((LifeRecomViewHolder)holder).datePotText.setText((int)calPotYear+"년");
                ((LifeRecomViewHolder)holder).dateYText.setText((int)calNormalYear+"년");

                ((LifeRecomViewHolder)holder).totalPotText.setText(addCom((long)calTotalPrice*10000)+"원");
                ((LifeRecomViewHolder)holder).totalYText.setText(addCom((long)calTotalNormalPrice*10000)+"원");

                ((LifeRecomViewHolder)holder).yieldPotText.setText(addCom((long)calYield*10000)+"원");
                ((LifeRecomViewHolder)holder).yieldYText.setText(addCom((long)calNormalYield*10000)+"원");

                lineDataSet = new LineDataSet(entries, "머니포트");
                lineDataSet.setLineWidth(1.5f);
                lineDataSet.setColor(Color.parseColor("#FFFF0000"));
                lineDataSet.setDrawHorizontalHighlightIndicator(false);
                lineDataSet.setDrawValues(false);
                lineDataSet.setHighlightEnabled(true);
                lineDataSet.setDrawHighlightIndicators(true);
                lineDataSet.setDrawHorizontalHighlightIndicator(false);
                lineDataSet.setDrawVerticalHighlightIndicator(false);
                lineDataSet.setHighlightLineWidth(0f);
                lineDataSet.setHighLightColor(Color.parseColor("#FFFFFFFF"));
                lineDataSet.setDrawCircles(false);

                lineDataSet2 = new LineDataSet(entries2, "예적금");
                lineDataSet2.setLineWidth(1.5f);
                lineDataSet2.setColor(Color.parseColor("#E1E1E1"));
                lineDataSet2.setDrawHorizontalHighlightIndicator(false);
                lineDataSet2.setDrawValues(false);
                lineDataSet2.setHighLightColor(Color.parseColor("#FFFFFFFF"));
                lineDataSet2.setHighlightEnabled(true);
                lineDataSet2.setDrawHighlightIndicators(true);
                lineDataSet2.setDrawHorizontalHighlightIndicator(false);
                lineDataSet2.setDrawVerticalHighlightIndicator(false);
                lineDataSet2.setHighlightLineWidth(0f);
                lineDataSet2.setDrawCircles(false);

                lineDataSets.clear();

                lineDataSets.add(lineDataSet);
                lineDataSets.add(lineDataSet2);

                lineData = new LineData(lineDataSets);
                lineData.setHighlightEnabled(false);

                xAxis = ((LifeRecomViewHolder) holder).chartView.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

                if(entries2.size() <= 12){
                    xAxis.setLabelCount(entries2.size(), true);
                }

                xAxis.setGranularity(1f);
                xAxis.setDrawLabels(true);
                xAxis.setDrawGridLines(false);
                xAxis.setDrawAxisLine(true);
                xAxis.setEnabled(true);

                YAxis yAxis = ((LifeRecomViewHolder) holder).chartView.getAxisLeft();
                yAxis.setDrawLabels(false);
                yAxis.setDrawGridLines(false);
                yAxis.setDrawAxisLine(false);
                yAxis.setEnabled(false);

                YAxis yAxi1 = ((LifeRecomViewHolder) holder).chartView.getAxisRight();
                yAxi1.setDrawLabels(false);
                yAxi1.setDrawGridLines(false);
                yAxi1.setDrawAxisLine(false);
                yAxi1.setEnabled(false);

                Legend legend = ((LifeRecomViewHolder) holder).chartView.getLegend();
                legend.setForm(Legend.LegendForm.CIRCLE);
                legend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
                legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
                legend.setOrientation(Legend.LegendOrientation.VERTICAL);
                legend.setEnabled(true);
                legend.setDrawInside(true);

                ((LifeRecomViewHolder) holder).chartView.setDescription(null);
                ((LifeRecomViewHolder) holder).chartView.setDrawGridBackground(false);
                ((LifeRecomViewHolder) holder).chartView.setData(lineData);
                ((LifeRecomViewHolder) holder).chartView.setDoubleTapToZoomEnabled(false);
                ((LifeRecomViewHolder) holder).chartView.setDrawGridBackground(false);
                ((LifeRecomViewHolder) holder).chartView.animateY(600, Easing.EasingOption.EaseInCubic);
                ((LifeRecomViewHolder) holder).chartView.setPinchZoom(false);
//                ((LifeChartViewHolder) holder).chartView.setScaleEnabled(false);
                ((LifeRecomViewHolder) holder).chartView.invalidate();

                maxX = ((LifeRecomViewHolder) holder).chartView.getXRange();

                CustomMarkerView marker = new CustomMarkerView(context, R.layout.item_chatbotchart_marker);
                marker.setChartView(((LifeRecomViewHolder) holder).chartView);

                ((LifeRecomViewHolder) holder).chartView.highlightValue(new Highlight(entries.get(entries.size()-1).getX(), entries.get(entries.size()-1).getY(), 0), false);
                marker.setEnabled(false);

                ((LifeRecomViewHolder) holder).chartView.getMarkerView();
                ((LifeRecomViewHolder) holder).chartView.setDrawMarkers(true);
                ((LifeRecomViewHolder) holder).chartView.getData().setHighlightEnabled(true);
                ((LifeRecomViewHolder) holder).chartView.setMarker(marker);
                ((LifeRecomViewHolder) holder).chartView.invalidate();

                ((LifeRecomViewHolder) holder).chartView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return true;
                    }
                });

            }else{
                ((LifeRecomViewHolder)holder).chartLayout.setVisibility(View.GONE);
            }


        }

        if(holder instanceof LifeInvestViewHolder){

            ((LifeInvestViewHolder)holder).persent.setText(modelFitPotLists.get(position).getLifeInvestPersent()+"%");
            ((LifeInvestViewHolder)holder).date.setText(modelFitPotLists.get(position).getLifeInvestDate());
        }
    }


    @Override
    public int getItemViewType(int position) {

       if(modelFitPotLists.get(position).isWebView()){

           if(modelAccountWebViews.get(position).isEmpty()){
               return WEBVIEWEMPTY;
           }else{
               return WEBVIEWITEM;
           }

       }else{

           if(modelFitPotLists.get(position).isAddView()){
            return ADDVIEW;
           }else if(modelFitPotLists.get(position).isInsertView()){
               return INSERTVIEW;
           }else if(modelFitPotLists.get(position).isChartView()){
               return CHARTVIEW;
           }else if(modelFitPotLists.get(position).isRecomView()){
               return RECOMVIEW;
           }else{
               return INVESTVIEW;
           }
       }
    }

    @Override
    public int getItemCount() {
        return modelFitPotLists.size();
    }

    public class WebViewEmptyViewHolder extends RecyclerView.ViewHolder {

        TextView accountBt;

        public WebViewEmptyViewHolder(@NonNull View itemView) {
            super(itemView);

            accountBt = itemView.findViewById(R.id.accountBt);
        }
    }

    public class WebViewItemViewHolder extends RecyclerView.ViewHolder {
        public WebViewItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class LifeAddViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout addItemView;

        public LifeAddViewHolder(@NonNull View itemView) {
            super(itemView);

            addItemView = itemView.findViewById(R.id.addItemView);
        }
    }

    public class LifeInsertViewHolder extends RecyclerView.ViewHolder {

        TextView nextBt, preNowPrice, preAddPrice, preFinalPrice;
        PrefixSuffixEditText nowPrice, addPrice, finalPrice;

        public LifeInsertViewHolder(@NonNull View itemView) {
            super(itemView);

            nextBt = itemView.findViewById(R.id.nextBt);

            nowPrice = itemView.findViewById(R.id.nowPrice);
            addPrice = itemView.findViewById(R.id.addPrice);
            finalPrice = itemView.findViewById(R.id.finalPrice);

            preNowPrice = itemView.findViewById(R.id.preNowPrice);
            preAddPrice = itemView.findViewById(R.id.preAddPrice);
            preFinalPrice = itemView.findViewById(R.id.preFinalPrice);

//            nowPrice.setFilters(new InputFilter[]{new InputFilter() {
//                    @Override
//                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
//
//                        Pattern ps = Pattern.compile("^[0-9]+$");
//                        if(source.equals("") || ps.matcher(source).matches()){
//                            if(source.length() == 2 && source.equals("00")){
//                                return "0";
//                            }else{
//                                return source;
//                            }
//                        }else{
//                            return "0";
//                        }
//                    }
//                },new InputFilter.LengthFilter(6)});

        }
    }

    public class LifeChartViewHolder extends RecyclerView.ViewHolder {

        TextView nextBt, yieldYText, yieldPotText, totalYText, totalPotText, dateYText, datePotText;
        ImageView backBt;
        LineChart chartView;

        public LifeChartViewHolder(@NonNull View itemView) {
            super(itemView);

            nextBt = itemView.findViewById(R.id.nextBt);
            backBt = itemView.findViewById(R.id.backBt);
            chartView = itemView.findViewById(R.id.chartView);
            chartView.setNoDataText("");

            yieldYText = itemView.findViewById(R.id.yieldYText);
            yieldPotText = itemView.findViewById(R.id.yieldPotText);
            totalYText = itemView.findViewById(R.id.totalYText);
            totalPotText = itemView.findViewById(R.id.totalPotText);
            dateYText = itemView.findViewById(R.id.dateYText);
            datePotText = itemView.findViewById(R.id.datePotText);

        }
    }

    public class LifeRecomViewHolder extends RecyclerView.ViewHolder {

        TextView title, dateTitle, priceTitle, typeTitle, desc, potTitle, rate, per, deleteBt, yieldYText, yieldPotText, totalYText, totalPotText, dateYText, datePotText;
        ImageView image, potImage;
        ConstraintLayout chartLayout;
        LineChart chartView;
        View showChartBt;

        public LifeRecomViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            dateTitle = itemView.findViewById(R.id.dateTitle);
            priceTitle = itemView.findViewById(R.id.priceTitle);
            typeTitle = itemView.findViewById(R.id.typeTitle);
            desc = itemView.findViewById(R.id.desc);
            potTitle = itemView.findViewById(R.id.potTitle);
            rate = itemView.findViewById(R.id.rate);
            per = itemView.findViewById(R.id.per);

            image = itemView.findViewById(R.id.image);
            potImage = itemView.findViewById(R.id.potImage);

            chartLayout = itemView.findViewById(R.id.chartLayout);

            deleteBt = itemView.findViewById(R.id.deleteBt);
            chartView = itemView.findViewById(R.id.chartView);
            chartView.setNoDataText("");

            yieldYText = itemView.findViewById(R.id.yieldYText);
            yieldPotText = itemView.findViewById(R.id.yieldPotText);
            totalYText = itemView.findViewById(R.id.totalYText);
            totalPotText = itemView.findViewById(R.id.totalPotText);
            dateYText = itemView.findViewById(R.id.dateYText);
            datePotText = itemView.findViewById(R.id.datePotText);

            showChartBt = itemView.findViewById(R.id.showChartBt);
        }
    }

    public class LifeInvestViewHolder extends RecyclerView.ViewHolder {

        TextView persent, date;

        public LifeInvestViewHolder(@NonNull View itemView) {
            super(itemView);

            persent = itemView.findViewById(R.id.persent);
            date = itemView.findViewById(R.id.date);
        }
    }

    private void applyRotation(View view, View frontView, View backView, float start, float end, float mid, float depth){
        this.centerX = view.getWidth() / 2.0f;
        this.centerY = view.getHeight() / 2.0f;

        Rotate3dAnimation rot = new Rotate3dAnimation(start, mid, centerX, centerY, depth, true);
        rot.setDuration(DURATION);
        rot.setInterpolator(new AccelerateInterpolator());
        rot.setAnimationListener(new DisplayNextView(view, frontView, backView, mid, end, depth));
        view.startAnimation(rot);
    }

    private class DisplayNextView implements Animation.AnimationListener {

        private float mid;
        private float end;
        private float depth;
        private View view;
        private View frontView, backView;

        public DisplayNextView(View view, View frontView, View backView,float mid, float end, float depth) {
            this.mid = mid;
            this.end = end;
            this.depth = depth;
            this.view = view;
            this.frontView = frontView;
            this.backView = backView;
        }

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {

            view.post(new Runnable() {
                @Override
                public void run() {

                    if(isFront){
                        frontView.setVisibility(View.GONE);
                        backView.setVisibility(View.VISIBLE);
                        isFront = false;

                    }else{
                        frontView.setVisibility(View.VISIBLE);
                        backView.setVisibility(View.GONE);
                        isFront = true;
                    }
                }
            });
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }


    public class CustomMarkerView extends MarkerView {
        private TextView tvContent;
        public CustomMarkerView(Context context, int layoutResource){
            super(context, layoutResource);
            tvContent = findViewById(R.id.tvContent);
        }

        @Override
        public void refreshContent(Entry e, Highlight highlight) {

            currentX = e.getX();
            tvContent.setText("목표 금액 달성!");
            super.refreshContent(e, highlight);
        }

        @Override
        public MPPointF getOffset() {

            if(maxX/3 > currentX) {
                return new MPPointF(-(getWidth())-40, -getHeight()+80);
            }else{
                return new MPPointF((getWidth()/5), -getHeight()+80);
            }
        }

        @Override
        public void draw(Canvas canvas, float posX, float posY) {

            Paint paint = new Paint();
            paint.setColor(context.getResources().getColor(R.color.chart_point_color));
            paint.setStrokeWidth(5f);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(posX, posY,18, paint);
            super.draw(canvas, posX, posY);
        }
    }

    String addCom(long money){
        long value = money;
        DecimalFormat format = new DecimalFormat("###,###");
        //콤마
        format.format(value);
        return format.format(value);
    }
}


//public class AdapterFitPot extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
//
//    private final int EMPTYLIFECHALLENGE = 0;
//    private final int LIFECHALLENGE = 1;
//    private final int EMPTYTOTALPRICE = 2;
//    private final int TOTALPRICE = 3;
//
//    private final int CUSTOMLIFE = 4;
//
//    ArrayList<ModelFitPotList> modelFitPotLists;
//    Context context;
//
//    public AdapterFitPot(ArrayList<ModelFitPotList> modelFitPotLists, Context context) {
//        this.modelFitPotLists = modelFitPotLists;
//        this.context = context;
//    }
//
//    boolean isFront = true;
//    private int DURATION = 500;
//    private float centerX;
//    private float centerY;
//
//
//    private EmptyTotalPriceClick emptyTotalPriceClick;
//    public interface EmptyTotalPriceClick{
//        public void onClick(int position);
//    }
//
//    public void setEmptyTotalPriceClick(EmptyTotalPriceClick emptyTotalPriceClick) {
//        this.emptyTotalPriceClick = emptyTotalPriceClick;
//    }
//
//    private EmptyLifeChallengeClick emptyLifeChallengeClick;
//    public interface EmptyLifeChallengeClick{
//        public void onClick(int position);
//    }
//
//    public void setEmptyLifeChallengeClick(EmptyLifeChallengeClick emptyLifeChallengeClick) {
//        this.emptyLifeChallengeClick = emptyLifeChallengeClick;
//    }
//
//    private LifeChallengeClick lifeChallengeClick;
//    public interface LifeChallengeClick{
//        public void onClick(int position);
//    }
//
//    public void setLifeChallengeClick(LifeChallengeClick lifeChallengeClick) {
//        this.lifeChallengeClick = lifeChallengeClick;
//    }
//
//    private ChangedStViewClick changedStViewClick;
//    public interface ChangedStViewClick {
//        public void onClick(int position);
//    }
//
//    public void setChangedStViewClick(ChangedStViewClick changedStViewClick) {
//        this.changedStViewClick = changedStViewClick;
//    }
//
//
//    private NextBtClick nextBtClick;
//    public interface NextBtClick {
//        public void onClick(int position);
//    }
//
//    public void setNextBtClick(NextBtClick nextBtClick) {
//        this.nextBtClick = nextBtClick;
//    }
//
//
//    private ChartNextBtClick chartNextBtClick;
//    public interface ChartNextBtClick {
//        public void onClick(int position);
//    }
//
//    public void setChartNextBtClick(ChartNextBtClick chartNextBtClick) {
//        this.chartNextBtClick = chartNextBtClick;
//    }
//
//    @NonNull
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        if(viewType == EMPTYLIFECHALLENGE){
//            return new EmptyLifeChallengeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_maintab1_custom, parent, false));
////            return new EmptyLifeChallengeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fitpot_emptylifechallenge, parent, false));
//        }
//        else if(viewType == LIFECHALLENGE){
////            return new LifeChallengeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fitpot_lifechallenge, parent, false));\\
//            return new LifeChallengeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_maintab1_chart, parent, false));
//        }
//        else if(viewType == EMPTYTOTALPRICE){
//            return new EmptyTotalPriceViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fitpot_emptytotalprice, parent, false));
//        }
//        else if(viewType == CUSTOMLIFE){
//            return new LifeListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fitpot_lifechallenge, parent, false));
//        }
//        else{
//            return new TotalPriceViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fitpot_totalprice, parent, false));
//        }
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//
//        if(holder instanceof EmptyTotalPriceViewHolder){
//
//
//        }
//
//        else if(holder instanceof TotalPriceViewHolder){
//
//            if(!modelFitPotLists.get(position).isEmpty){
//
//            }else{
//                ((TotalPriceViewHolder)holder).webView.loadUrl(modelFitPotLists.get(position).getWebViewUrl());
//            }
//
//        }
//        else if(holder instanceof EmptyLifeChallengeViewHolder){
//
////            ((EmptyLifeChallengeViewHolder)holder).cardView.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View v) {
////                    if(emptyLifeChallengeClick != null) {
////                        emptyLifeChallengeClick.onClick(position);
////                    }
////                }
////            });
//
//
//            ((EmptyLifeChallengeViewHolder)holder).nextBt.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(nextBtClick != null){
//                        nextBtClick.onClick(position);
//                    }
//                }
//            });
//
//
//        }
//        else if(holder instanceof LifeChallengeViewHolder){
//
//            ((LifeChallengeViewHolder)holder).nextBt.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(chartNextBtClick != null){
//                        chartNextBtClick.onClick(position);
//                    }
//                }
//            });
//
////            ((LifeChallengeViewHolder)holder).lifeTitle.setText(modelFitPotLists.get(position).getLiftTitle());
////            ((LifeChallengeViewHolder)holder).lifeContent.setText(modelFitPotLists.get(position).getLifeContent());
////            ((LifeChallengeViewHolder)holder).lifeYear.setText(modelFitPotLists.get(position).getLifeYear());
////            ((LifeChallengeViewHolder)holder).lifePrice.setText(modelFitPotLists.get(position).getLifePrice());
////            ((LifeChallengeViewHolder)holder).lifeExp.setText(modelFitPotLists.get(position).getLifeExp());
////            ((LifeChallengeViewHolder)holder).lifePlan.setText(modelFitPotLists.get(position).getLifePlan());
////            ((LifeChallengeViewHolder)holder).lifeType.setText(modelFitPotLists.get(position).getLifeType());
////
////            ((LifeChallengeViewHolder)holder).lifeStName.setText(modelFitPotLists.get(position).getLifeStTitle());
////            ((LifeChallengeViewHolder)holder).lifeStYield.setText(modelFitPotLists.get(position).getLifeStYeild());
////
////            ((LifeChallengeViewHolder)holder).deleteBt.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View v) {
////                    if(lifeChallengeClick != null) {
////                        lifeChallengeClick.onClick(position);
////                    }
////                }
////            });
////
////
////            ((LifeChallengeViewHolder)holder).lifeRecomBt.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View v) {
////
//////                    applyRotation(((LifeChallengeViewHolder)holder).flipView, ((LifeChallengeViewHolder)holder).front,((LifeChallengeViewHolder)holder).back,
//////                            0f, 90f, 180f, 0f);
////                    applyRotation(((LifeChallengeViewHolder)holder).flipView, ((LifeChallengeViewHolder)holder).front,((LifeChallengeViewHolder)holder).back,
////                            0f, 90f, 180f, 0f);
////                }
////            });
////
////            ((LifeChallengeViewHolder)holder).lifeRecomBt2.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View v) {
//////                    applyRotation(((LifeChallengeViewHolder)holder).flipView, ((LifeChallengeViewHolder)holder).front,((LifeChallengeViewHolder)holder).back,
//////                            180f, 270f, 360f, 0f);
////                    applyRotation(((LifeChallengeViewHolder)holder).flipView, ((LifeChallengeViewHolder)holder).front,((LifeChallengeViewHolder)holder).back,
////                            360f, 270f, 180f, 0f);
////
////                }
////            });
//
//        }
//        else if(holder instanceof LifeListViewHolder){
//
//        }
//    }
//
//
//    @Override
//    public int getItemViewType(int position) {
//        if(position == 0) {
//
//            if(modelFitPotLists.get(position).isEmpty){
//                return TOTALPRICE;
//            }
//            else{
//                return EMPTYTOTALPRICE;
//            }
//        }
//        else {
//
//            if(modelFitPotLists.get(position).isCustom()){
//                return EMPTYLIFECHALLENGE;
//            }else{
//
//                if(modelFitPotLists.get(position).isLifeList()){
//                    return CUSTOMLIFE;
//                }else{
//                    return LIFECHALLENGE;
//                }
////                return LIFECHALLENGE;
//            }
//        }
//    }
//
////    @Override
////    public int getItemViewType(int position) {
////        if(position == 0) {
////
////            if(modelFitPotLists.get(position).isEmpty){
////                return TOTALPRICE;
////            }
////            else{
////                return EMPTYTOTALPRICE;
////            }
////        }
////        else {
////
////            if(modelFitPotLists.get(position).isEmpty){
////                return EMPTYLIFECHALLENGE;
////            }
////            else{
////                return LIFECHALLENGE;
////            }
////        }
////    }
//
//    @Override
//    public int getItemCount() {
//        return modelFitPotLists.size();
//    }
//
//    public class EmptyLifeChallengeViewHolder extends RecyclerView.ViewHolder {
//        CardView cardView;
//        TextView nextBt;
//
//        public EmptyLifeChallengeViewHolder(View itemView) {
//            super(itemView);
//
//            cardView = itemView.findViewById(R.id.cardView);
//
//            nextBt = itemView.findViewById(R.id.nextBt);
//        }
//
//    }
//    public class LifeChallengeViewHolder extends RecyclerView.ViewHolder {
//
//        TextView lifeTitle, lifeContent, lifeYear, lifePrice, lifePlan, lifeExp, lifeType, lifeStName, lifeStYield, deleteBt, lifeRecomBt, lifeRecomBt2, nextBt;
//        FrameLayout flipView;
//        ConstraintLayout front, back;
//
//        public LifeChallengeViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            lifeTitle = itemView.findViewById(R.id.lifeTitle);
//            lifeContent = itemView.findViewById(R.id.lifeContent);
//            lifeYear = itemView.findViewById(R.id.lifeYear);
//            lifePrice = itemView.findViewById(R.id.lifePrice);
//            lifePlan = itemView.findViewById(R.id.lifePlan);
//            lifeExp = itemView.findViewById(R.id.lifeExp);
//            lifeType = itemView.findViewById(R.id.lifeType);
//            lifeStName = itemView.findViewById(R.id.lifeStName);
//            lifeStYield = itemView.findViewById(R.id.lifeStYield);
//            deleteBt = itemView.findViewById(R.id.deleteBt);
//
//            flipView = itemView.findViewById(R.id.flipView);
//            front = itemView.findViewById(R.id.front);
//            back = itemView.findViewById(R.id.back);
//            lifeRecomBt = itemView.findViewById(R.id.lifeRecomBt);
//            lifeRecomBt2 = itemView.findViewById(R.id.lifeRecomBt2);
//
//            nextBt = itemView.findViewById(R.id.nextBt);
//        }
//    }
//
//    public class LifeListViewHolder extends RecyclerView.ViewHolder {
//        public LifeListViewHolder(@NonNull View itemView) {
//            super(itemView);
//        }
//    }
//
//    //    public class LifeChallengeViewHolder extends RecyclerView.ViewHolder {
////
////        TextView lifeTitle, lifeContent, lifeYear, lifePrice, lifePlan, lifeExp, lifeType, lifeStName, lifeStYield, deleteBt, lifeRecomBt, lifeRecomBt2;
////        FrameLayout flipView;
////        ConstraintLayout front, back;
////
////        public LifeChallengeViewHolder(View itemView) {
////            super(itemView);
////
////            lifeTitle = itemView.findViewById(R.id.lifeTitle);
////            lifeContent = itemView.findViewById(R.id.lifeContent);
////            lifeYear = itemView.findViewById(R.id.lifeYear);
////            lifePrice = itemView.findViewById(R.id.lifePrice);
////            lifePlan = itemView.findViewById(R.id.lifePlan);
////            lifeExp = itemView.findViewById(R.id.lifeExp);
////            lifeType = itemView.findViewById(R.id.lifeType);
////            lifeStName = itemView.findViewById(R.id.lifeStName);
////            lifeStYield = itemView.findViewById(R.id.lifeStYield);
////            deleteBt = itemView.findViewById(R.id.deleteBt);
////
////            flipView = itemView.findViewById(R.id.flipView);
////            front = itemView.findViewById(R.id.front);
////            back = itemView.findViewById(R.id.back);
////            lifeRecomBt = itemView.findViewById(R.id.lifeRecomBt);
////            lifeRecomBt2 = itemView.findViewById(R.id.lifeRecomBt2);
////
////
////        }
////    }
//
//    public class EmptyTotalPriceViewHolder extends RecyclerView.ViewHolder {
//
//        public EmptyTotalPriceViewHolder(@NonNull View itemView) {
//            super(itemView);
//        }
//    }
//
////    public class EmptyTotalPriceViewHolder extends RecyclerView.ViewHolder {
////
////        TextView makeBt;
////
////        public EmptyTotalPriceViewHolder(View itemView) {
////            super(itemView);
////
////            makeBt = itemView.findViewById(R.id.makeBt);
////        }
////    }
//
//
//    public class TotalPriceViewHolder extends RecyclerView.ViewHolder {
//
//        WebView webView;
//
//        public TotalPriceViewHolder(View itemView) {
//            super(itemView);
//
//            webView = itemView.findViewById(R.id.webView);
//
//            WebSettings mws=webView.getSettings();//Mobile Web Setting
//            mws.setJavaScriptEnabled(true);//자바스크립트 허용
//            mws.setLoadWithOverviewMode(true);//컨텐츠가 웹뷰보다 클 경우 스크린 크기에 맞게 조정
//
//            webView.setWebViewClient(new WebViewClient(){
//                @Override
//                public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                    view.loadUrl(url);
//                    return true;
//                }
//            });
//
//        }
//    }
//
//
//
//    private void applyRotation(View view, View frontView, View backView, float start, float end, float mid, float depth){
//        this.centerX = view.getWidth() / 2.0f;
//        this.centerY = view.getHeight() / 2.0f;
//
//        Rotate3dAnimation rot = new Rotate3dAnimation(start, mid, centerX, centerY, depth, true);
//        rot.setDuration(DURATION);
//        rot.setInterpolator(new AccelerateInterpolator());
//        rot.setAnimationListener(new DisplayNextView(view, frontView, backView, mid, end, depth));
//        view.startAnimation(rot);
//    }
//
//    private class DisplayNextView implements Animation.AnimationListener {
//
//        private float mid;
//        private float end;
//        private float depth;
//        private View view;
//        private View frontView, backView;
//
//        public DisplayNextView(View view, View frontView, View backView,float mid, float end, float depth) {
//            this.mid = mid;
//            this.end = end;
//            this.depth = depth;
//            this.view = view;
//            this.frontView = frontView;
//            this.backView = backView;
//        }
//
//        @Override
//        public void onAnimationStart(Animation animation) {
//
//        }
//
//        @Override
//        public void onAnimationEnd(Animation animation) {
//
//            view.post(new Runnable() {
//                @Override
//                public void run() {
//
//                    if(isFront){
//                        frontView.setVisibility(View.GONE);
//                        backView.setVisibility(View.VISIBLE);
//                        isFront = false;
//
//                    }else{
//                        frontView.setVisibility(View.VISIBLE);
//                        backView.setVisibility(View.GONE);
//                        isFront = true;
//                    }
//
////                    Rotate3dAnimation rot = new Rotate3dAnimation(mid, end, centerX, centerY, depth, true);
////                    rot.setDuration(DURATION);
////                    rot.setInterpolator(new AccelerateInterpolator());
////                    view.startAnimation(rot);
//
//                }
//            });
//
//        }
//
//        @Override
//        public void onAnimationRepeat(Animation animation) {
//
//        }
//    }
//}

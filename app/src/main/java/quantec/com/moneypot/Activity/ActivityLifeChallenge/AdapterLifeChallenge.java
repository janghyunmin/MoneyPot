package quantec.com.moneypot.Activity.ActivityLifeChallenge;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

import quantec.com.moneypot.R;

public class AdapterLifeChallenge extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private boolean constView = true;

    private final int BOTTALK = 0;
    private final int MYTALK = 1;
    private final int BOTSELECT = 2;
    private final int BOTCHART = 3;
    private final int BOTEND = 4;
    private final int BOTSELECT2 = 5;
    private final int BOTSELECT3 = 6;
    private final int BOTMONTHLYSELECT = 7;
    private final int BOTCHART2 = 8;
    private final int BOTSPACE = 9;

    ArrayList<ModelLifeCSelectList> lifeCSelectLists;
    ArrayList<ModelLifeCTextList> lifeCTextLists;
    Context context;

    List<Entry> entries;
    ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();
    LineDataSet lineDataSet, lineDataSet2;
    LineData lineData;
    XAxis xAxis;

    List<Entry> entries2;

    ArrayList<ModelChartInfoLsit> chartInfoLsits;

    public AdapterLifeChallenge(ArrayList<ModelLifeCSelectList> lifeCSelectLists, ArrayList<ModelLifeCTextList> lifeCTextLists,
                                Context context, List<Entry> entries, LineDataSet lineDataSet, LineData lineData, List<Entry> entries2, ArrayList<ModelChartInfoLsit> chartInfoLsits) {
        this.lifeCSelectLists = lifeCSelectLists;
        this.lifeCTextLists = lifeCTextLists;
        this.context = context;
        this.entries = entries;
        this.lineDataSet = lineDataSet;
        this.lineData = lineData;
        this.entries2 = entries2;
        this.chartInfoLsits = chartInfoLsits;
    }

    private BotSelectClick botSelectClick;
    public interface BotSelectClick {
        public void onClick(int category);
    }

    public void setBotSelectClick(BotSelectClick botSelectClick) {
        this.botSelectClick = botSelectClick;
    }

    private BotSelect2YesClick botSelect2YesClick;
    public interface BotSelect2YesClick {
        public void onClick(int position);
    }

    private BotSelect2NoClick botSelect2NoClick;
    public interface BotSelect2NoClick {
        public void onClick(int position);
    }

    public void setBotSelect2YesClick(BotSelect2YesClick botSelect2YesClick) {
        this.botSelect2YesClick = botSelect2YesClick;
    }

    public void setBotSelect2NoClick(BotSelect2NoClick botSelect2NoClick) {
        this.botSelect2NoClick = botSelect2NoClick;
    }


    private BotSelect3MonthlyBtClick botSelect3MonthlyBtClick;
    public interface BotSelect3MonthlyBtClick {
        public void onClick(int position);
    }

    private BotSelect3TatalBtClick botSelect3TatalBtClick;
    public interface BotSelect3TatalBtClick {
        public void onClick(int position);
    }

    public void setBotSelect3MonthlyBtClick(BotSelect3MonthlyBtClick botSelect3MonthlyBtClick) {
        this.botSelect3MonthlyBtClick = botSelect3MonthlyBtClick;
    }

    public void setBotSelect3TatalBtClick(BotSelect3TatalBtClick botSelect3TatalBtClick) {
        this.botSelect3TatalBtClick = botSelect3TatalBtClick;
    }


    private BotMonthlyYesClick botMonthlyYesClick;
    public interface BotMonthlyYesClick {
        public void onClick(int position);
    }

    private BotMonthlyNoClick botMonthlyNoClick;
    public interface BotMonthlyNoClick {
        public void onClick(int position);
    }

    public void setBotMonthlyYesClick(BotMonthlyYesClick botMonthlyYesClick) {
        this.botMonthlyYesClick = botMonthlyYesClick;
    }

    public void setBotMonthlyNoClick(BotMonthlyNoClick botMonthlyNoClick) {
        this.botMonthlyNoClick = botMonthlyNoClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == BOTTALK){
            return new BotTalkViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bottalk, parent, false));
        }
        else if(viewType == MYTALK){
            return new MyTalkViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mytalk, parent, false));
        }
        else if(viewType == BOTSELECT){
            return new BotSelctViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_botselect, parent, false));
        }
        else if(viewType == BOTCHART){
            return new BotChartViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_botchart, parent, false));
        }
        else if(viewType == BOTSELECT2){
            return new BotSelect2ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_botselect2, parent, false));
        }
        else if(viewType == BOTSELECT3){
            return new BotSelect3ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_botselect3, parent, false));
        }
        else if(viewType == BOTMONTHLYSELECT){

            return new BotMonthlySelectViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_botmonthlyselect, parent, false));
        }
        else if(viewType == BOTCHART2){
            return new BotChart2ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_botchart2, parent, false));
        }
        else if(viewType == BOTSPACE){
            return new BotSpaceViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_botspace, parent, false));
        }
        else {
            return new BotEndViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_botend, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof BotTalkViewHolder){

            ((BotTalkViewHolder)holder).talk.setText(lifeCTextLists.get(position).getTalk());
            if(!lifeCTextLists.get(position).getTime().equals("")){
                ((BotTalkViewHolder)holder).time.setText(lifeCTextLists.get(position).getTime());
            }

        }
        else if(holder instanceof MyTalkViewHolder){
            ((MyTalkViewHolder)holder).myTalk.setText(lifeCTextLists.get(position).getTalk());
            if(!lifeCTextLists.get(position).getTime().equals("")){
                ((MyTalkViewHolder)holder).time.setText(lifeCTextLists.get(position).getTime());
            }

        }
        else if(holder instanceof BotSelctViewHolder){

            ((BotSelctViewHolder)holder).selectTitle.setText(lifeCTextLists.get(position).getTalk());

            if(!lifeCTextLists.get(position).getTime().equals("")){
                ((BotSelctViewHolder)holder).time.setText(lifeCTextLists.get(position).getTime());
            }

            ((BotSelctViewHolder)holder).select1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(botSelectClick != null) {
                        botSelectClick.onClick(lifeCSelectLists.get(0).getCategory());
                        ((BotSelctViewHolder)holder).select1.setEnabled(false);
                        ((BotSelctViewHolder)holder).select2.setEnabled(false);
                        ((BotSelctViewHolder)holder).select3.setEnabled(false);
                        ((BotSelctViewHolder)holder).select4.setEnabled(false);
                    }
                }
            });

            ((BotSelctViewHolder)holder).select2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(botSelectClick != null) {
                        botSelectClick.onClick(lifeCSelectLists.get(1).getCategory());
                        ((BotSelctViewHolder)holder).select1.setEnabled(false);
                        ((BotSelctViewHolder)holder).select2.setEnabled(false);
                        ((BotSelctViewHolder)holder).select3.setEnabled(false);
                        ((BotSelctViewHolder)holder).select4.setEnabled(false);
                    }
                }
            });

            ((BotSelctViewHolder)holder).select3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(botSelectClick != null) {
                        botSelectClick.onClick(lifeCSelectLists.get(2).getCategory());
                        ((BotSelctViewHolder)holder).select1.setEnabled(false);
                        ((BotSelctViewHolder)holder).select2.setEnabled(false);
                        ((BotSelctViewHolder)holder).select3.setEnabled(false);
                        ((BotSelctViewHolder)holder).select4.setEnabled(false);
                    }
                }
            });

            ((BotSelctViewHolder)holder).select4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(botSelectClick != null) {
                        botSelectClick.onClick(lifeCSelectLists.get(3).getCategory());
                        ((BotSelctViewHolder)holder).select1.setEnabled(false);
                        ((BotSelctViewHolder)holder).select2.setEnabled(false);
                        ((BotSelctViewHolder)holder).select3.setEnabled(false);
                        ((BotSelctViewHolder)holder).select4.setEnabled(false);
                    }
                }
            });


        }
        else if(holder instanceof BotChartViewHolder){

            if(constView) {

//                chartInfoLsits

                constView = false;

                lineDataSet = new LineDataSet(entries, "전략수익률");
                lineDataSet.setLineWidth(1.5f);
                lineDataSet.setColor(Color.parseColor("#FFFF0000"));
                lineDataSet.setDrawHorizontalHighlightIndicator(false);
                lineDataSet.setDrawValues(false);
                lineDataSet.setHighlightEnabled(true);
                lineDataSet.setDrawHighlightIndicators(true);
                lineDataSet.setDrawHorizontalHighlightIndicator(false);
                lineDataSet.setHighLightColor(R.color.chart_limit_line_color);
                lineDataSet.setHighlightLineWidth(1f);
                lineDataSet.setDrawCircles(false);


                lineDataSet2 = new LineDataSet(entries2, "시장수익률");
                lineDataSet2.setLineWidth(1.5f);
                lineDataSet2.setColor(Color.parseColor("#E1E1E1"));
                lineDataSet2.setDrawHorizontalHighlightIndicator(false);
                lineDataSet2.setDrawValues(false);
                lineDataSet2.setHighlightEnabled(true);
                lineDataSet2.setDrawHighlightIndicators(true);
                lineDataSet2.setDrawHorizontalHighlightIndicator(false);
                lineDataSet2.setHighLightColor(R.color.chart_limit_line_color);
                lineDataSet2.setHighlightLineWidth(1f);
                lineDataSet2.setDrawCircles(false);

                lineDataSets.add(lineDataSet);
                lineDataSets.add(lineDataSet2);


                lineData = new LineData(lineDataSets);
                lineData.setHighlightEnabled(true);

//            xAxis = ((BotChartViewHolder) holder).chart.getXAxis();
//            xAxis.setDrawLabels(true);
//            xAxis.setDrawGridLines(false);
//            xAxis.setDrawAxisLine(true);
//            xAxis.setEnabled(true);

                xAxis = ((BotChartViewHolder) holder).chart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setGranularity(1);
//            xAxis.setLabelCount(20, true);
                xAxis.setDrawLabels(true);
                xAxis.setDrawGridLines(false);
                xAxis.setDrawAxisLine(true);
                xAxis.setEnabled(true);

                YAxis yAxis = ((BotChartViewHolder) holder).chart.getAxisLeft();
                yAxis.setDrawLabels(false);
                yAxis.setDrawGridLines(false);
                yAxis.setDrawAxisLine(false);
                yAxis.setEnabled(false);

                YAxis yAxi1 = ((BotChartViewHolder) holder).chart.getAxisRight();
                yAxi1.setDrawLabels(false);
                yAxi1.setDrawGridLines(false);
                yAxi1.setDrawAxisLine(false);
                yAxi1.setEnabled(false);

                Legend legend = ((BotChartViewHolder) holder).chart.getLegend();
                legend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
                legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
                legend.setOrientation(Legend.LegendOrientation.VERTICAL);
                legend.setEnabled(true);
                legend.setDrawInside(true);

                ((BotChartViewHolder) holder).chart.setDescription(null);
                ((BotChartViewHolder) holder).chart.setDrawGridBackground(false);
                ((BotChartViewHolder) holder).chart.setData(lineData);
                ((BotChartViewHolder) holder).chart.setDoubleTapToZoomEnabled(false);
                ((BotChartViewHolder) holder).chart.setDrawGridBackground(false);
                ((BotChartViewHolder) holder).chart.animateY(600, Easing.EasingOption.EaseInCubic);
                ((BotChartViewHolder) holder).chart.setPinchZoom(false);
                ((BotChartViewHolder) holder).chart.setScaleEnabled(false);
                ((BotChartViewHolder) holder).chart.invalidate();


                ((BotChartViewHolder) holder).priceText.setText(chartInfoLsits.get(0).getPotYear()+"년" + "( "+chartInfoLsits.get(0).getNormalYear()+"년 )");
                ((BotChartViewHolder) holder).priceText2.setText(chartInfoLsits.get(0).getTotalPrice()+"원");
                ((BotChartViewHolder) holder).priceText3.setText(chartInfoLsits.get(0).getYieldPrice()+"원");
            }

//            maxX = ((BotChartViewHolder) holder).chart.getXRange();

//            CustomMarkerView marker = new CustomMarkerView(context,R.layout.item_chart_marker);
//            marker.setChartView(((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mChartChartView);
//            ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mChartChartView.setMarker(marker);

        }
        else if(holder instanceof BotEndViewHolder){

        }
        else if(holder instanceof BotSelect2ViewHolder){

            if(!lifeCTextLists.get(position).getTime().equals("")){
                ((BotSelect2ViewHolder)holder).time.setText(lifeCTextLists.get(position).getTime());
            }

            ((BotSelect2ViewHolder)holder).selectTitle.setText(lifeCTextLists.get(position).getTalk());

            ((BotSelect2ViewHolder)holder).yesTalk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(botSelect2YesClick != null) {
                        botSelect2YesClick.onClick(position);
                    }
                }
            });

            ((BotSelect2ViewHolder)holder).noTalk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(botSelect2NoClick != null) {
                        botSelect2NoClick.onClick(position);
                    }
                }
            });
        }

        else if(holder instanceof BotSelect3ViewHolder){
            if(!lifeCTextLists.get(position).getTime().equals("")){
                ((BotSelect3ViewHolder)holder).time.setText(lifeCTextLists.get(position).getTime());
            }

            ((BotSelect3ViewHolder)holder).selectTitle.setText(lifeCTextLists.get(position).getTalk());

            ((BotSelect3ViewHolder)holder).monthlyBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(botSelect3MonthlyBtClick != null){
                        botSelect3MonthlyBtClick.onClick(position);
                    }
                }
            });

            ((BotSelect3ViewHolder)holder).totalBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(botSelect3TatalBtClick != null){
                        botSelect3TatalBtClick.onClick(position);
                    }
                }
            });
        }

        else if(holder instanceof BotMonthlySelectViewHolder){

            if(!lifeCTextLists.get(position).getTime().equals("")){
                ((BotMonthlySelectViewHolder)holder).time.setText(lifeCTextLists.get(position).getTime());
            }

            ((BotMonthlySelectViewHolder)holder).selectTitle.setText(lifeCTextLists.get(position).getTalk());

            ((BotMonthlySelectViewHolder)holder).yesBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(botMonthlyYesClick != null){
                        botMonthlyYesClick.onClick(position);
                    }
                }
            });

            ((BotMonthlySelectViewHolder)holder).noBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(botMonthlyNoClick != null){
                        botMonthlyNoClick.onClick(position);
                    }
                }
            });

        }

        else if(holder instanceof BotChart2ViewHolder){

            if(constView) {

                constView = false;

                lineDataSet = new LineDataSet(entries, "전략수익률");
                lineDataSet.setLineWidth(1.5f);
                lineDataSet.setColor(Color.parseColor("#FFFF0000"));
                lineDataSet.setDrawHorizontalHighlightIndicator(false);
                lineDataSet.setDrawValues(false);
                lineDataSet.setHighlightEnabled(true);
                lineDataSet.setDrawHighlightIndicators(true);
                lineDataSet.setDrawHorizontalHighlightIndicator(false);
                lineDataSet.setHighLightColor(R.color.chart_limit_line_color);
                lineDataSet.setHighlightLineWidth(1f);
                lineDataSet.setDrawCircles(false);


                lineDataSet2 = new LineDataSet(entries2, "시장수익률");
                lineDataSet2.setLineWidth(1.5f);
                lineDataSet2.setColor(Color.parseColor("#E1E1E1"));
                lineDataSet2.setDrawHorizontalHighlightIndicator(false);
                lineDataSet2.setDrawValues(false);
                lineDataSet2.setHighlightEnabled(true);
                lineDataSet2.setDrawHighlightIndicators(true);
                lineDataSet2.setDrawHorizontalHighlightIndicator(false);
                lineDataSet2.setHighLightColor(R.color.chart_limit_line_color);
                lineDataSet2.setHighlightLineWidth(1f);
                lineDataSet2.setDrawCircles(false);

                lineDataSets.add(lineDataSet);
                lineDataSets.add(lineDataSet2);


                lineData = new LineData(lineDataSets);
                lineData.setHighlightEnabled(true);

                xAxis = ((BotChart2ViewHolder) holder).chart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setGranularity(1);
                xAxis.setDrawLabels(true);
                xAxis.setDrawGridLines(false);
                xAxis.setDrawAxisLine(true);
                xAxis.setEnabled(true);

                YAxis yAxis = ((BotChart2ViewHolder) holder).chart.getAxisLeft();
                yAxis.setDrawLabels(false);
                yAxis.setDrawGridLines(false);
                yAxis.setDrawAxisLine(false);
                yAxis.setEnabled(false);

                YAxis yAxi1 = ((BotChart2ViewHolder) holder).chart.getAxisRight();
                yAxi1.setDrawLabels(false);
                yAxi1.setDrawGridLines(false);
                yAxi1.setDrawAxisLine(false);
                yAxi1.setEnabled(false);

                Legend legend = ((BotChart2ViewHolder) holder).chart.getLegend();
                legend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
                legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
                legend.setOrientation(Legend.LegendOrientation.VERTICAL);
                legend.setEnabled(true);
                legend.setDrawInside(true);

                ((BotChart2ViewHolder) holder).chart.setDescription(null);
                ((BotChart2ViewHolder) holder).chart.setDrawGridBackground(false);
                ((BotChart2ViewHolder) holder).chart.setData(lineData);
                ((BotChart2ViewHolder) holder).chart.setDoubleTapToZoomEnabled(false);
                ((BotChart2ViewHolder) holder).chart.setDrawGridBackground(false);
                ((BotChart2ViewHolder) holder).chart.animateY(600, Easing.EasingOption.EaseInCubic);
                ((BotChart2ViewHolder) holder).chart.setPinchZoom(false);
                ((BotChart2ViewHolder) holder).chart.setScaleEnabled(false);
                ((BotChart2ViewHolder) holder).chart.invalidate();

                ((BotChart2ViewHolder) holder).priceText.setText(chartInfoLsits.get(0).getPotTotal()+"원 \n" + "( "+chartInfoLsits.get(0).getNormalTotal()+"원 )");
                ((BotChart2ViewHolder) holder).priceText2.setText(chartInfoLsits.get(0).getTotalPrice()+"원");
                ((BotChart2ViewHolder) holder).priceText3.setText(chartInfoLsits.get(0).getYieldPrice()+"원");
            }


        }


    }

    @Override
    public int getItemCount() {
        return lifeCTextLists.size();
    }

    @Override
    public int getItemViewType(int position) {

        if(lifeCTextLists.get(position).getCategory() == 0){
            return BOTTALK;
        }
        else if(lifeCTextLists.get(position).getCategory() == 1){
            return MYTALK;
        }
        else if(lifeCTextLists.get(position).getCategory() == 2){
            return BOTSELECT;
        }
        else if(lifeCTextLists.get(position).getCategory() == 3){
            return BOTCHART;
        }
        else if(lifeCTextLists.get(position).getCategory() == 5){
            return BOTSELECT2;
        }
        else if(lifeCTextLists.get(position).getCategory() == 6){
            return BOTSELECT3;
        }
        else if(lifeCTextLists.get(position).getCategory() == 7){
            return BOTMONTHLYSELECT;
        }
        else if(lifeCTextLists.get(position).getCategory() == 8){
            return BOTCHART2;
        }
        else if(lifeCTextLists.get(position).getCategory() == 9){
            return BOTSPACE;
        }
        else{
            return BOTEND;
        }
    }


    public class BotTalkViewHolder extends RecyclerView.ViewHolder {

        TextView talk, time;

        public BotTalkViewHolder(View itemView) {
            super(itemView);

            talk = itemView.findViewById(R.id.talk);
            time = itemView.findViewById(R.id.time);
        }
    }

    public class MyTalkViewHolder extends RecyclerView.ViewHolder {

        TextView myTalk, time;

        public MyTalkViewHolder(View itemView) {
            super(itemView);

            myTalk = itemView.findViewById(R.id.myTalk);
            time = itemView.findViewById(R.id.time);
        }
    }

    public class BotSelctViewHolder extends RecyclerView.ViewHolder {

        TextView select1, select2, select3, select4, selectTitle, time;

        public BotSelctViewHolder(View itemView) {
            super(itemView);

            select1 = itemView.findViewById(R.id.select1);
            select2 = itemView.findViewById(R.id.select2);
            select3 = itemView.findViewById(R.id.select3);
            select4 = itemView.findViewById(R.id.select4);
            selectTitle = itemView.findViewById(R.id.selectTitle);
            time = itemView.findViewById(R.id.time);
        }
    }

    public class BotChartViewHolder extends RecyclerView.ViewHolder {

        LineChart chart;
        TextView priceText, priceText2, priceText3;

        public BotChartViewHolder(View itemView) {
            super(itemView);

            chart = itemView.findViewById(R.id.chart);

            priceText = itemView.findViewById(R.id.priceText);
            priceText2 = itemView.findViewById(R.id.priceText2);
            priceText3 = itemView.findViewById(R.id.priceText3);

        }
    }

    public class BotEndViewHolder extends RecyclerView.ViewHolder {
        public BotEndViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class BotSelect2ViewHolder extends RecyclerView.ViewHolder {

        TextView yesTalk, noTalk, selectTitle, time;

        public BotSelect2ViewHolder(View itemView) {
            super(itemView);

            yesTalk = itemView.findViewById(R.id.yesTalk);
            noTalk = itemView.findViewById(R.id.noTalk);
            selectTitle = itemView.findViewById(R.id.selectTitle);
            time = itemView.findViewById(R.id.time);
        }
    }

    public class BotSelect3ViewHolder extends RecyclerView.ViewHolder {

        TextView monthlyBt, totalBt, time, selectTitle;

        public BotSelect3ViewHolder(View itemView) {
            super(itemView);

            monthlyBt = itemView.findViewById(R.id.monthlyBt);
            totalBt = itemView.findViewById(R.id.totalBt);
            time = itemView.findViewById(R.id.time);
            selectTitle = itemView.findViewById(R.id.selectTitle);
        }
    }

    public class BotMonthlySelectViewHolder extends RecyclerView.ViewHolder {

        TextView yesBt, noBt, time, selectTitle;

        public BotMonthlySelectViewHolder(View itemView) {
            super(itemView);

            yesBt = itemView.findViewById(R.id.yesBt);
            noBt = itemView.findViewById(R.id.noBt);
            time = itemView.findViewById(R.id.time);
            selectTitle = itemView.findViewById(R.id.selectTitle);

        }
    }

    public class BotChart2ViewHolder extends RecyclerView.ViewHolder {

        LineChart chart;
        TextView priceText, priceText2, priceText3;

        public BotChart2ViewHolder(View itemView) {
            super(itemView);

            chart = itemView.findViewById(R.id.chart);

            priceText = itemView.findViewById(R.id.priceText);
            priceText2 = itemView.findViewById(R.id.priceText2);
            priceText3 = itemView.findViewById(R.id.priceText3);

        }
    }

    public class BotSpaceViewHolder extends RecyclerView.ViewHolder {

        public BotSpaceViewHolder(View itemView) {
            super(itemView);
        }
    }

}

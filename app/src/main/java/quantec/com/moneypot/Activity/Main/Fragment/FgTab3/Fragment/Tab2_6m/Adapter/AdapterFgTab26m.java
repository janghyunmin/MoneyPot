package quantec.com.moneypot.Activity.Main.Fragment.FgTab3.Fragment.Tab2_6m.Adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;
import java.util.List;

import quantec.com.moneypot.Activity.Main.Fragment.FgTab3.Fragment.Tab2_6m.Fg_Tab2_6m;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab3.Fragment.Tab2_6m.Model.dModel.ModelTab26m;
import quantec.com.moneypot.R;
import quantec.com.moneypot.databinding.ItemFgtab3Tab26mdataBinding;

public class AdapterFgTab26m extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    ArrayList<ModelTab26m> tab2_6mItems;

    Context context;
    List<Entry> entries;
    LineDataSet lineDataSet;
    LineData lineData;
    XAxis xAxis;

    float currentX,maxX;
    int checkedPosition;

    private T2SelectedItemClick t2SelectedItemClick;
    public interface T2SelectedItemClick{
        public void onClick(int position);
    }

    public void setT2SelectedItemClick(T2SelectedItemClick t2SelectedItemClick) {
        this.t2SelectedItemClick = t2SelectedItemClick;
    }


    private T2SelectedDur1 t2SelectedDur1;
    public interface T2SelectedDur1 {
        public void onClick(int position);
    }

    public void setT2SelectedDur1(T2SelectedDur1 t2SelectedDur1) {
        this.t2SelectedDur1 = t2SelectedDur1;
    }

    private T2SelectedDur3 t2SelectedDur3;
    public interface T2SelectedDur3{
        public void onClick(int position);
    }

    public void setT2SelectedDur3(T2SelectedDur3 t2SelectedDur3) {
        this.t2SelectedDur3 = t2SelectedDur3;
    }

    private T2SelectedDur6 t2SelectedDur6;
    public interface T2SelectedDur6 {
        public void onClick(int position);
    }

    public void setT2SelectedDur6(T2SelectedDur6 t2SelectedDur6) {
        this.t2SelectedDur6 = t2SelectedDur6;
    }

    private T2SelectedDura t2SelectedDura;
    public interface T2SelectedDura {
        public void onClick(int position);
    }

    public void setT2SelectedDura(T2SelectedDura t2SelectedDura) {
        this.t2SelectedDura = t2SelectedDura;
    }

    private T2SelectedAdd t2SelectedAdd;
    public interface T2SelectedAdd {
        public void onClick(int position);
    }

    public void setT2SelectedAdd(T2SelectedAdd t2SelectedAdd) {
        this.t2SelectedAdd = t2SelectedAdd;
    }


    private T2SelectedInvest t2SelectedInvest;
    public interface T2SelectedInvest {
        public void onClick(int position);
    }

    public void setT2SelectedInvest(T2SelectedInvest t2SelectedInvest) {
        this.t2SelectedInvest = t2SelectedInvest;
    }

    private T2SelectedShare t2SelectedShare;
    public interface T2SelectedShare {
        public void onClick(int position);
    }

    public void setT2SelectedShare(T2SelectedShare t2SelectedShare) {
        this.t2SelectedShare = t2SelectedShare;
    }

    private T2SelectedCheckedItem t2SelectedCheckedItem;
    public interface T2SelectedCheckedItem {
        public void onClick(int position);
    }

    public void setT2SelectedCheckedItem(T2SelectedCheckedItem t2SelectedCheckedItem) {
        this.t2SelectedCheckedItem = t2SelectedCheckedItem;
    }

    public AdapterFgTab26m(ArrayList<ModelTab26m> tab2_6mItems, Context context, List<Entry> entries, LineDataSet lineDataSet, LineData lineData) {
        this.tab2_6mItems = tab2_6mItems;
        this.context = context;
        this.entries = entries;
        this.lineDataSet = lineDataSet;
        this.lineData = lineData;
    }

    @Override
    public int getItemViewType(int position) {
        return tab2_6mItems.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == VIEW_ITEM) {
            return new Tab2_6mViewHolder(ItemFgtab3Tab26mdataBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }else{
            return new ProgressViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_public_progress, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof Tab2_6mViewHolder) {

            if(!Fg_Tab2_6m.OpenChartAnim6 && !Fg_Tab2_6m.CheckDataAnim6) {
                if (!Fg_Tab2_6m.AnimState6) {
                    TranslateAnimation animation = new TranslateAnimation(0, 0, 80, 0);
                    animation.setStartTime(50);
                    animation.setDuration(500);
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
                    ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mLayout.startAnimation(animation);
                } else {
                    TranslateAnimation animation = new TranslateAnimation(0, 0, -80, 0);
                    animation.setStartTime(50);
                    animation.setDuration(500);
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
                    ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mLayout.startAnimation(animation);
                }
            }

            ModelTab26m singleItem = tab2_6mItems.get(position);

            ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mNumber.setText(String.valueOf(position+1));
            ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mTitle.setText(singleItem.getTitle());
            ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mRate.setText(String.valueOf(singleItem.getRate()));

            if(singleItem.getRate() < 0){
                ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mRate.setTextColor(context.getResources().getColor(R.color.make_port_blue_color));
                ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mPer.setTextColor(context.getResources().getColor(R.color.make_port_blue_color));
            }else{
                ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mRate.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
                ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mPer.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
            }

            ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mImage.setImageResource(singleItem.getImage());

            if(singleItem.isZim()){
                ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mCheckImage.setImageResource(R.drawable.start_on);
            }else{
                ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mCheckImage.setImageResource(R.drawable.start_off);
            }

            ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(t2SelectedItemClick != null) {
                        t2SelectedItemClick.onClick(position);
                        checkedPosition = 4;
                    }
                }
            });

            ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mDur1Bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(t2SelectedDur1 != null) {
                        t2SelectedDur1.onClick(position);
                        checkedPosition = 1;
                    }
                }
            });

            ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mDur3Bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(t2SelectedDur3 != null) {
                        t2SelectedDur3.onClick(position);
                        checkedPosition = 2;
                    }
                }
            });

            ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mDur6Bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(t2SelectedDur6 != null) {
                        t2SelectedDur6.onClick(position);
                        checkedPosition = 3;
                    }
                }
            });

            ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mDuraBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(t2SelectedDura != null) {
                        t2SelectedDura.onClick(position);
                        checkedPosition = 4;
                    }
                }
            });


            if(checkedPosition == 1) {
                ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mDur1Bt.setBackgroundResource(R.drawable.round_button);
                ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mDur1Bt.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
                ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mDur3Bt.setBackgroundResource(R.drawable.unselected_round_button);
                ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mDur3Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
                ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mDur6Bt.setBackgroundResource(R.drawable.unselected_round_button);
                ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mDur6Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
                ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mDuraBt.setBackgroundResource(R.drawable.unselected_round_button);
                ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mDuraBt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
            }else if(checkedPosition == 2) {
                ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mDur1Bt.setBackgroundResource(R.drawable.unselected_round_button);
                ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mDur1Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
                ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mDur3Bt.setBackgroundResource(R.drawable.round_button);
                ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mDur3Bt.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
                ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mDur6Bt.setBackgroundResource(R.drawable.unselected_round_button);
                ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mDur6Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
                ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mDuraBt.setBackgroundResource(R.drawable.unselected_round_button);
                ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mDuraBt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
            }else if(checkedPosition == 3) {
                ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mDur1Bt.setBackgroundResource(R.drawable.unselected_round_button);
                ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mDur1Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
                ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mDur3Bt.setBackgroundResource(R.drawable.unselected_round_button);
                ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mDur3Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
                ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mDur6Bt.setBackgroundResource(R.drawable.round_button);
                ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mDur6Bt.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
                ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mDuraBt.setBackgroundResource(R.drawable.unselected_round_button);
                ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mDuraBt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
            }else  if(checkedPosition == 4) {
                ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mDur1Bt.setBackgroundResource(R.drawable.unselected_round_button);
                ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mDur1Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
                ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mDur3Bt.setBackgroundResource(R.drawable.unselected_round_button);
                ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mDur3Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
                ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mDur6Bt.setBackgroundResource(R.drawable.unselected_round_button);
                ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mDur6Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
                ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mDuraBt.setBackgroundResource(R.drawable.round_button);
                ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mDuraBt.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
            }

            //더보기
            ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mChartAddBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(t2SelectedAdd != null) {
                        t2SelectedAdd.onClick(position);
                    }
                }
            });

            //투자하기
            ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mChartInvestBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(t2SelectedInvest != null) {
                        t2SelectedInvest.onClick(position);
                    }
                }
            });

            //공유
            ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mChartShareBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(t2SelectedShare != null) {
                        t2SelectedShare.onClick(position);
                    }
                }
            });


            //찜 체크하기
            ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(t2SelectedCheckedItem != null) {
                        t2SelectedCheckedItem.onClick(position);
                    }
                }
            });


            if(singleItem.isOnenChart()){
                if ( entries.size() > 0) {

                    lineDataSet = new LineDataSet(entries, null);
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

                    lineData = new LineData(lineDataSet);
                    lineData.setHighlightEnabled(true);

                    xAxis = ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mChartChartView.getXAxis();
                    xAxis.setDrawLabels(false);
                    xAxis.setDrawGridLines(false);
                    xAxis.setDrawAxisLine(false);

                    YAxis yAxis = ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mChartChartView.getAxisLeft();
                    yAxis.setDrawLabels(false);
                    yAxis.setDrawGridLines(false);
                    yAxis.setDrawAxisLine(false);
                    yAxis.setEnabled(false);

                    YAxis yAxis1 = ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mChartChartView.getAxisRight();
                    yAxis1.setDrawLabels(false);
                    yAxis1.setDrawGridLines(false);
                    yAxis1.setDrawAxisLine(false);
                    yAxis1.setEnabled(false);

                    Legend legend = ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mChartChartView.getLegend();
                    legend.setEnabled(false);
                    legend.setDrawInside(false);


                    ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mChartChartView.setDescription(null);
                    ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mChartChartView.setDrawGridBackground(false);
                    ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mChartChartView.setData(lineData);
                    ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mChartChartView.setDoubleTapToZoomEnabled(false);
                    ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mChartChartView.setDrawGridBackground(false);
                    ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mChartChartView.animateY(600, Easing.EasingOption.EaseInCubic);
                    ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mChartChartView.setPinchZoom(false);
                    ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mChartChartView.setScaleEnabled(false);
                    ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mChartChartView.invalidate();

                    maxX = ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mChartChartView.getXRange();


                    CustomMarkerView marker = new CustomMarkerView(context,R.layout.item_chart_marker);
                    marker.setChartView(((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mChartChartView);
                    ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mChartChartView.setMarker(marker);

                    ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mChart.setVisibility(View.VISIBLE);
                }
            }else{
                ((Tab2_6mViewHolder) holder).tab26mdataBinding.fragment3Tab26mChart.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return tab2_6mItems.size();
    }


    static class Tab2_6mViewHolder extends RecyclerView.ViewHolder {

        ItemFgtab3Tab26mdataBinding tab26mdataBinding;

        public Tab2_6mViewHolder(ItemFgtab3Tab26mdataBinding tab26mdataBinding) {
            super(tab26mdataBinding.getRoot());

            this.tab26mdataBinding = tab26mdataBinding;
        }
    }

    static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar pBar;
        public ProgressViewHolder(View v) {
            super(v);
            pBar = (ProgressBar) v.findViewById(R.id.pBar);
        }
    }

    public class CustomMarkerView extends MarkerView {
        private TextView tvContent;
        private TextView tvContent2;
        public CustomMarkerView(Context context, int layoutResource){
            super(context, layoutResource);
            tvContent = findViewById(R.id.tvContent);
            tvContent2 = findViewById(R.id.tvContent2);
        }
        @Override
        public void refreshContent(Entry e, Highlight highlight) {
            currentX = e.getX();
            if (e instanceof CandleEntry) {
                CandleEntry ce = (CandleEntry) e;
                String num = String.format("%.2f",e.getY());
                tvContent.setText(("" + ce.getData()).replace("-","."));
                tvContent2.setText(num+"%");
            } else {
                String num = String.format("%.2f",e.getY());
                tvContent.setText(("" + e.getData()).replace("-","."));
                tvContent2.setText(num+"%");
            }
            super.refreshContent(e, highlight);
        }
        @Override
        public MPPointF getOffset() {
            if(maxX/3 < currentX) {
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
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawCircle(posX, posY,18, paint);
            super.draw(canvas, posX, posY);
        }
    }

}
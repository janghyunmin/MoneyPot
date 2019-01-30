package quantec.com.moneypot.Activity.Main.Fragment.FgTab3.Fragment.Tab1_3m.Adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import quantec.com.moneypot.Activity.Main.Fragment.FgTab3.Fragment.Tab1_3m.Fg_Tab1_3m;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab3.Fragment.Tab1_3m.Model.dModel.ModelTab13m;
import quantec.com.moneypot.R;
import quantec.com.moneypot.databinding.ItemFgtab3Tab13mdataBinding;

public class AdapterFgTab13m extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    private ArrayList<ModelTab13m> tab1_3mItems;

    Context context;
    List<Entry> entries;
    LineDataSet lineDataSet;
    LineData lineData;
    XAxis xAxis;

    float currentX, maxX;
    int checkedPosition;

    public interface OnLoadMoreListener{
        void onLoadMore();
    }

    private SelectedItemClick selectedItemClick;
    public interface SelectedItemClick {
        public void onClick(int position);
    }

    public void setSelectedItemClick(SelectedItemClick selectedItemClick) {
        this.selectedItemClick = selectedItemClick;
    }

    private SelectedCheckedItem selectedCheckedItem;
    public interface SelectedCheckedItem {
        public void onClick(int position);
    }

    public void setSelectedCheckedItem(SelectedCheckedItem selectedCheckedItem) {
        this.selectedCheckedItem = selectedCheckedItem;
    }


    private SelectedDur1 selectedDur1;
    public interface SelectedDur1 {
        public void onClick(int position);
    }

    public void setSelectedDur1(SelectedDur1 selectedDur1) {
        this.selectedDur1 = selectedDur1;
    }

    private SelectedDur3 selectedDur3;
    public interface SelectedDur3 {
        public void onClick(int position);
    }

    public void setSelectedDur3(SelectedDur3 selectedDur3) {
        this.selectedDur3 = selectedDur3;
    }

    private SelectedDur6 selectedDur6;
    public interface SelectedDur6{
        public void onClick(int position);
    }

    public void setSelectedDur6(SelectedDur6 selectedDur6) {
        this.selectedDur6 = selectedDur6;
    }

    private SelectedDura selectedDura;
    public interface SelectedDura {
        public void onClick(int position);
    }

    public void setSelectedDura(SelectedDura selectedDura) {
        this.selectedDura = selectedDura;
    }

    private SelectedInvest selectedInvest;
    public interface SelectedInvest {
        public void onClick(int position);
    }

    public void setSelectedInvest(SelectedInvest selectedInvest) {
        this.selectedInvest = selectedInvest;
    }

    private SelectedAdd selectedAdd;
    public interface SelectedAdd {
        public void onClick(int position);
    }

    public void setSelectedAdd(SelectedAdd selectedAdd) {
        this.selectedAdd = selectedAdd;
    }

    private SelectedShare selectedShare;
    public interface SelectedShare {
        public void onClick(int position);
    }

    public void setSelectedShare(SelectedShare selectedShare) {
        this.selectedShare = selectedShare;
    }

    public AdapterFgTab13m(ArrayList<ModelTab13m> tab1_3mItems, Context context, List<Entry> entries, LineDataSet lineDataSet, LineData lineData) {
        this.tab1_3mItems = tab1_3mItems;
        this.context = context;
        this.entries = entries;
        this.lineDataSet = lineDataSet;
        this.lineData = lineData;
    }

    @Override
    public int getItemViewType(int position) {

        return tab1_3mItems.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_ITEM) {
            return new FgTab13mViewHolder(ItemFgtab3Tab13mdataBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        } else {
            return new ProgressViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_public_progress, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof FgTab13mViewHolder) {
            if(!Fg_Tab1_3m.OpenChartAnim && !Fg_Tab1_3m.CheckDataAnim) {
                if (!Fg_Tab1_3m.AnimState) {
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
                    ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mLayout.startAnimation(animation);
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
                    ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mLayout.startAnimation(animation);
                }

            }
            ModelTab13m singleItem = tab1_3mItems.get(position);

            ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mNumber.setText(String.valueOf(position+1));
            ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mTitle.setText(singleItem.getTitle());
            ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mRate.setText(String.valueOf(singleItem.getRate()));


            if(singleItem.getRate() < 0) {
                ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mRate.setTextColor(context.getResources().getColor(R.color.make_port_blue_color));
                ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mPer.setTextColor(context.getResources().getColor(R.color.make_port_blue_color));
            }else{
                ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mRate.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
                ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mPer.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
            }

            ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mImage.setImageResource(singleItem.getImage());

            if(singleItem.getCheck() == 1){
                ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mCheckImage.setImageResource(R.drawable.start_on);
            }else{
                ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mCheckImage.setImageResource(R.drawable.start_off);
            }

            ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(selectedItemClick != null) {
                        selectedItemClick.onClick(position);
                        checkedPosition = 4;
                    }
                }
            });

            ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mDur1Bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(selectedDur1 != null) {
                        selectedDur1.onClick(position);
                        checkedPosition = 1;
                    }
                }
            });

            ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mDur3Bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(selectedDur3 != null) {
                        selectedDur3.onClick(position);
                        checkedPosition = 2;
                    }
                }
            });

            ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mDur6Bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(selectedDur6 != null) {
                        selectedDur6.onClick(position);
                        checkedPosition = 3;
                    }
                }
            });

            ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mDuraBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(selectedDura != null) {
                        selectedDura.onClick(position);
                        checkedPosition = 4;
                    }
                }
            });

            if(checkedPosition == 1) {
                ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mDur1Bt.setBackgroundResource(R.drawable.round_button);
                ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mDur1Bt.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
                ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mDur3Bt.setBackgroundResource(R.drawable.unselected_round_button);
                ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mDur3Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
                ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mDur6Bt.setBackgroundResource(R.drawable.unselected_round_button);
                ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mDur6Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
                ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mDuraBt.setBackgroundResource(R.drawable.unselected_round_button);
                ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mDuraBt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
            }else if(checkedPosition == 2) {
                ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mDur1Bt.setBackgroundResource(R.drawable.unselected_round_button);
                ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mDur1Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
                ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mDur3Bt.setBackgroundResource(R.drawable.round_button);
                ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mDur3Bt.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
                ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mDur6Bt.setBackgroundResource(R.drawable.unselected_round_button);
                ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mDur6Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
                ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mDuraBt.setBackgroundResource(R.drawable.unselected_round_button);
                ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mDuraBt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
            }else if(checkedPosition == 3) {
                ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mDur1Bt.setBackgroundResource(R.drawable.unselected_round_button);
                ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mDur1Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
                ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mDur3Bt.setBackgroundResource(R.drawable.unselected_round_button);
                ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mDur3Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
                ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mDur6Bt.setBackgroundResource(R.drawable.round_button);
                ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mDur6Bt.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
                ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mDuraBt.setBackgroundResource(R.drawable.unselected_round_button);
                ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mDuraBt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
            }else if(checkedPosition == 4) {
                ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mDur1Bt.setBackgroundResource(R.drawable.unselected_round_button);
                ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mDur1Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
                ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mDur3Bt.setBackgroundResource(R.drawable.unselected_round_button);
                ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mDur3Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
                ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mDur6Bt.setBackgroundResource(R.drawable.unselected_round_button);
                ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mDur6Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
                ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mDuraBt.setBackgroundResource(R.drawable.round_button);
                ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mDuraBt.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
            }

            //더보기
            ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mChartAddBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(selectedAdd != null) {
                        selectedAdd.onClick(position);
                    }
                }
            });

            //투자하기
            ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mChartInvestBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(selectedInvest != null) {
                        selectedInvest.onClick(position);
                    }
                }
            });

            //공유
            ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mChartShareBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(selectedShare != null) {
                        selectedShare.onClick(position);
                    }
                }
            });

            //찜 체크하기
            ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(selectedCheckedItem != null) {
                        selectedCheckedItem.onClick(position);
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

                    xAxis = ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mChartChartView.getXAxis();
                    xAxis.setDrawLabels(false);
                    xAxis.setDrawGridLines(false);
                    xAxis.setDrawAxisLine(false);

                    YAxis yAxis = ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mChartChartView.getAxisLeft();
                    yAxis.setDrawLabels(false);
                    yAxis.setDrawGridLines(false);
                    yAxis.setDrawAxisLine(false);
                    yAxis.setEnabled(false);

                    YAxis yAxis1 = ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mChartChartView.getAxisRight();
                    yAxis1.setDrawLabels(false);
                    yAxis1.setDrawGridLines(false);
                    yAxis1.setDrawAxisLine(false);
                    yAxis1.setEnabled(false);

                    Legend legend = ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mChartChartView.getLegend();
                    legend.setEnabled(false);
                    legend.setDrawInside(false);

                    ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mChartChartView.setDescription(null);
                    ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mChartChartView.setDrawGridBackground(false);
                    ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mChartChartView.setData(lineData);
                    ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mChartChartView.setDoubleTapToZoomEnabled(false);
                    ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mChartChartView.setDrawGridBackground(false);
                    ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mChartChartView.animateY(600, Easing.EasingOption.EaseInCubic);
                    ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mChartChartView.setPinchZoom(false);
                    ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mChartChartView.setScaleEnabled(false);
                    ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mChartChartView.invalidate();

                    maxX = ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mChartChartView.getXRange();

                    CustomMarkerView marker = new CustomMarkerView(context,R.layout.item_chart_marker);
                    marker.setChartView(((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mChartChartView);
                    ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mChartChartView.setMarker(marker);

                    ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mChart.setVisibility(View.VISIBLE);
                }
            }else{
                ((FgTab13mViewHolder) holder).itemTextBinding.fragment3Tab13mChart.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return tab1_3mItems.size();
    }

    static class FgTab13mViewHolder extends RecyclerView.ViewHolder {

        ItemFgtab3Tab13mdataBinding itemTextBinding;

        public FgTab13mViewHolder(ItemFgtab3Tab13mdataBinding itemTextBinding) {
            super(itemTextBinding.getRoot());
            this.itemTextBinding = itemTextBinding;
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

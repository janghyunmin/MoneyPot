package quantec.com.moneypot.Activity.Main.Fragment.FgTab3.Fragment.Tab3_am.Adapter;

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

import quantec.com.moneypot.Activity.Main.Fragment.FgTab3.Fragment.Tab3_am.Fg_Tab3_am;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab3.Fragment.Tab3_am.Model.dModel.ModelTab3am;
import quantec.com.moneypot.R;
import quantec.com.moneypot.databinding.ItemFgtab3Tab3amdataBinding;

public class AdapterFgTab3am extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    ArrayList<ModelTab3am> tab3_amItems;

    Context context;
    List<Entry> entries;
    LineDataSet lineDataSet;
    LineData lineData;
    XAxis xAxis;

    float currentX, maxX;
    int checkedPosition;

    private T3SelectedItemClick t3SelectedItemClick;
    public interface T3SelectedItemClick {
        public void onClick(int position);
    }

    public void setT3SelectedItemClick(T3SelectedItemClick t3SelectedItemClick) {
        this.t3SelectedItemClick = t3SelectedItemClick;
    }

    private T3SelectedDur1 t3SelectedDur1;
    public interface T3SelectedDur1{
        public void onClick(int position);
    }

    public void setT3SelectedDur1(T3SelectedDur1 t3SelectedDur1) {
        this.t3SelectedDur1 = t3SelectedDur1;
    }

    private T3SelectedDur3 t3SelectedDur3;
    public interface T3SelectedDur3 {
        public void onClick(int position);
    }

    public void setT3SelectedDur3(T3SelectedDur3 t3SelectedDur3) {
        this.t3SelectedDur3 = t3SelectedDur3;
    }


    private T3SelectedDur6 t3SelectedDur6;
    public interface T3SelectedDur6 {
        public void onClick(int position);
    }

    public void setT3SelectedDur6(T3SelectedDur6 t3SelectedDur6) {
        this.t3SelectedDur6 = t3SelectedDur6;
    }

    private T3SelectedDura t3SelectedDura;
    public interface T3SelectedDura {
        public void onClick(int position);
    }

    public void setT3SelectedDura(T3SelectedDura t3SelectedDura) {
        this.t3SelectedDura = t3SelectedDura;
    }

    private T3SelectedAdd t3SelectedAdd;
    public interface T3SelectedAdd {
        public void onClick(int position);
    }

    public void setT3SelectedAdd(T3SelectedAdd t3SelectedAdd) {
        this.t3SelectedAdd = t3SelectedAdd;
    }

    private T3SelectedInvest t3SelectedInvest;
    public interface T3SelectedInvest {
        public void onClick(int position);
    }

    public void setT3SelectedInvest(T3SelectedInvest t3SelectedInvest) {
        this.t3SelectedInvest = t3SelectedInvest;
    }

    private T3SelectedShare t3SelectedShare;
    public interface T3SelectedShare {
        public void onClick(int position);
    }

    public void setT3SelectedShare(T3SelectedShare t3SelectedShare) {
        this.t3SelectedShare = t3SelectedShare;
    }

    private T3SelectedCheckedItem t3SelectedCheckedItem;
    public interface T3SelectedCheckedItem {
        public void onClick(int position);
    }

    public void setT3SelectedCheckedItem(T3SelectedCheckedItem t3SelectedCheckedItem) {
        this.t3SelectedCheckedItem = t3SelectedCheckedItem;
    }

    public AdapterFgTab3am(ArrayList<ModelTab3am> tab3_amItems, Context context, List<Entry> entries, LineDataSet lineDataSet, LineData lineData) {
        this.tab3_amItems = tab3_amItems;
        this.context = context;
        this.entries = entries;
        this.lineDataSet = lineDataSet;
        this.lineData = lineData;
    }

    @Override
    public int getItemViewType(int position) {
        return tab3_amItems.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == VIEW_ITEM) {
            return new Tab3_amViewHolder(ItemFgtab3Tab3amdataBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }else{
            return new ProgressViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_public_progress, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof Tab3_amViewHolder) {

            if(!Fg_Tab3_am.OpenChartAnim_a && !Fg_Tab3_am.CheckDataAnim_a) {
                if (!Fg_Tab3_am.AnimState_a) {
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
                    ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmLayout.startAnimation(animation);
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
                    ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmLayout.startAnimation(animation);
                }
            }

            ModelTab3am singleItem = tab3_amItems.get(position);

            ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmNumber.setText(String.valueOf(position+1));
            ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmTitle.setText(singleItem.getTitle());
            ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmRate.setText(String.valueOf(singleItem.getRate()));

            if(singleItem.getRate() < 0) {
                ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmRate.setTextColor(context.getResources().getColor(R.color.make_port_blue_color));
                ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmPer.setTextColor(context.getResources().getColor(R.color.make_port_blue_color));
            }else{
                ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmRate.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
                ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmPer.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
            }

            ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmImage.setImageResource(singleItem.getImage());

            if(singleItem.isZim()){
                ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmCheckImage.setImageResource(R.drawable.start_on);
            }else{
                ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmCheckImage.setImageResource(R.drawable.start_off);
            }


            ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(t3SelectedItemClick != null) {
                        t3SelectedItemClick.onClick(position);
                        checkedPosition = 4;
                    }
                }
            });

            ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmDur1Bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(t3SelectedDur1 != null) {
                        t3SelectedDur1.onClick(position);
                        checkedPosition = 1;
                    }
                }
            });

            ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmDur3Bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(t3SelectedDur3 != null) {
                        t3SelectedDur3.onClick(position);
                        checkedPosition = 2;
                    }
                }
            });

            ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmDur6Bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(t3SelectedDur6 != null) {
                        t3SelectedDur6.onClick(position);
                        checkedPosition = 3;
                    }
                }
            });

            ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmDuraBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(t3SelectedDura != null) {
                        t3SelectedDura.onClick(position);
                        checkedPosition = 4;
                    }
                }
            });


            if(checkedPosition == 1) {
                ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmDur1Bt.setBackgroundResource(R.drawable.round_button);
                ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmDur1Bt.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
                ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmDur3Bt.setBackgroundResource(R.drawable.unselected_round_button);
                ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmDur3Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
                ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmDur6Bt.setBackgroundResource(R.drawable.unselected_round_button);
                ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmDur6Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
                ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmDuraBt.setBackgroundResource(R.drawable.unselected_round_button);
                ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmDuraBt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
            }else if(checkedPosition == 2){
                ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmDur1Bt.setBackgroundResource(R.drawable.unselected_round_button);
                ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmDur1Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
                ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmDur3Bt.setBackgroundResource(R.drawable.round_button);
                ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmDur3Bt.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
                ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmDur6Bt.setBackgroundResource(R.drawable.unselected_round_button);
                ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmDur6Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
                ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmDuraBt.setBackgroundResource(R.drawable.unselected_round_button);
                ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmDuraBt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
            }else if(checkedPosition == 3) {
                ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmDur1Bt.setBackgroundResource(R.drawable.unselected_round_button);
                ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmDur1Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
                ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmDur3Bt.setBackgroundResource(R.drawable.unselected_round_button);
                ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmDur3Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
                ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmDur6Bt.setBackgroundResource(R.drawable.round_button);
                ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmDur6Bt.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
                ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmDuraBt.setBackgroundResource(R.drawable.unselected_round_button);
                ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmDuraBt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
            }else if(checkedPosition == 4) {
                ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmDur1Bt.setBackgroundResource(R.drawable.unselected_round_button);
                ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmDur1Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
                ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmDur3Bt.setBackgroundResource(R.drawable.unselected_round_button);
                ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmDur3Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
                ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmDur6Bt.setBackgroundResource(R.drawable.unselected_round_button);
                ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmDur6Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
                ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmDuraBt.setBackgroundResource(R.drawable.round_button);
                ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmDuraBt.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
            }

            //더보기
            ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmChartAddBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(t3SelectedAdd != null) {
                        t3SelectedAdd.onClick(position);
                    }
                }
            });

            //투자하기
            ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmChartInvestBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(t3SelectedInvest != null) {
                        t3SelectedInvest.onClick(position);
                    }
                }
            });

            //공유
            ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmChartShareBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(t3SelectedShare != null) {
                        t3SelectedShare.onClick(position);
                    }
                }
            });


            //찜 체크하기
            ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(t3SelectedCheckedItem != null) {
                        t3SelectedCheckedItem.onClick(position);
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

                    xAxis = ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmChartChartView.getXAxis();
                    xAxis.setDrawLabels(false);
                    xAxis.setDrawGridLines(false);
                    xAxis.setDrawAxisLine(false);

                    YAxis yAxis = ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmChartChartView.getAxisLeft();
                    yAxis.setDrawLabels(false);
                    yAxis.setDrawGridLines(false);
                    yAxis.setDrawAxisLine(false);
                    yAxis.setEnabled(false);

                    YAxis yAxis1 = ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmChartChartView.getAxisRight();
                    yAxis1.setDrawLabels(false);
                    yAxis1.setDrawGridLines(false);
                    yAxis1.setDrawAxisLine(false);
                    yAxis1.setEnabled(false);

                    Legend legend = ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmChartChartView.getLegend();
                    legend.setEnabled(false);
                    legend.setDrawInside(false);


                    ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmChartChartView.setDescription(null);
                    ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmChartChartView.setDrawGridBackground(false);
                    ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmChartChartView.setData(lineData);
                    ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmChartChartView.setDoubleTapToZoomEnabled(false);
                    ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmChartChartView.setDrawGridBackground(false);
                    ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmChartChartView.animateY(600, Easing.EasingOption.EaseInCubic);
                    ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmChartChartView.setPinchZoom(false);
                    ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmChartChartView.setScaleEnabled(false);
                    ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmChartChartView.invalidate();

                    maxX = ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmChartChartView.getXRange();


                    CustomMarkerView marker = new CustomMarkerView(context,R.layout.item_chart_marker);
                    marker.setChartView(((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmChartChartView);
                    ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmChartChartView.setMarker(marker);

                    ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmChart.setVisibility(View.VISIBLE);
                }
            }else{
                ((Tab3_amViewHolder) holder).tab3amdataBinding.fragment3Tab3AmChart.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public int getItemCount() {
        return tab3_amItems.size();
    }

    static class Tab3_amViewHolder extends RecyclerView.ViewHolder {
        ItemFgtab3Tab3amdataBinding tab3amdataBinding;

        public Tab3_amViewHolder(ItemFgtab3Tab3amdataBinding tab3amdataBinding) {
            super(tab3amdataBinding.getRoot());
            this.tab3amdataBinding = tab3amdataBinding;
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
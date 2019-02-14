package quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.Cookpage2.Adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
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

import quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.Cookpage2.Fg_CookPage2;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.Cookpage2.Model.dModel.ModelCookList;
import quantec.com.moneypot.R;
import quantec.com.moneypot.databinding.ItemFgtab2Cookpage2Binding;

public class AdapterCookPage2 extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    ArrayList<ModelCookList> modelCookLists;
    Context context;

    public static boolean CookDeleteBt = true;
    public static boolean ACook = false;

    List<Entry> entries;
    LineDataSet lineDataSet;
    LineData lineData;

    float currentX, maxX;

    Bundle page;
    int ChartBtPosition;

    private CookItemClick cookItemClick;
    public interface CookItemClick {
        public void onClick(int position);
    }

    public void setCookItemClick(CookItemClick cookItemClick) {
        this.cookItemClick = cookItemClick;
    }

    private CookDeleteClick cookDeleteClick;
    public interface CookDeleteClick {
        public void onClick(int position);
    }

    public void setCookDeleteClick(CookDeleteClick cookDeleteClick) {
        this.cookDeleteClick = cookDeleteClick;
    }

    private CookCheckClick cookCheckClick;
    public interface CookCheckClick {
        public void onClick(int position);
    }

    public void setCookCheckClick(CookCheckClick cookCheckClick) {
        this.cookCheckClick = cookCheckClick;
    }


    //차트 1개월 클릭
    private CookChartM1Click cookChartM1Click;
    public interface CookChartM1Click {
        public void onClick(int position);
    }

    public void setCookChartM1Click(CookChartM1Click cookChartM1Click) {
        this.cookChartM1Click = cookChartM1Click;
    }

    //차트 3개월 클릭
    private CookChartM3Click cookChartM3Click;
    public interface CookChartM3Click {
        public void onclick(int position);
    }

    public void setCookChartM3Click(CookChartM3Click cookChartM3Click) {
        this.cookChartM3Click = cookChartM3Click;
    }

    //차트 6개월 클릭
    private CookChartM6Click cookChartM6Click;
    public interface CookChartM6Click {
        public void onClick(int position);
    }

    public void setCookChartM6Click(CookChartM6Click cookChartM6Click) {
        this.cookChartM6Click = cookChartM6Click;
    }

    //차트 누적 클릭
    private CookChartMaClick cookChartMaClick;
    public interface CookChartMaClick {
        public void onClick(int position);
    }

    public void setCookChartMaClick(CookChartMaClick cookChartMaClick) {
        this.cookChartMaClick = cookChartMaClick;
    }

    //투자하기 클릭
    private CookP2InvestClick cookP2InvestClick;
    public interface CookP2InvestClick {
        public void onClick(int position);
    }

    public void setCookP2InvestClick(CookP2InvestClick cookP2InvestClick) {
        this.cookP2InvestClick = cookP2InvestClick;
    }

    //더보기 클릭
    private CookP2MoreViewClick cookP2MoreViewClick;
    public interface CookP2MoreViewClick {
        public void onClick(int position);
    }

    public void setCookP2MoreViewClick(CookP2MoreViewClick cookP2MoreViewClick) {
        this.cookP2MoreViewClick = cookP2MoreViewClick;
    }

    //공유 클릭
    private CookP2ShareClick cookP2ShareClick;
    public interface CookP2ShareClick {
        public void onClick(int position);
    }

    public void setCookP2ShareClick(CookP2ShareClick cookP2ShareClick) {
        this.cookP2ShareClick = cookP2ShareClick;
    }

    private CookP2MakeCheckClick cookP2MakeCheckClick;
    public interface CookP2MakeCheckClick {
        public void onClick(int position);
    }

    public void setCookP2MakeCheckClick(CookP2MakeCheckClick cookP2MakeCheckClick) {
        this.cookP2MakeCheckClick = cookP2MakeCheckClick;
    }

    public AdapterCookPage2(ArrayList<ModelCookList> modelCookLists, Context context, List<Entry> entries, LineDataSet lineDataSet, LineData lineData) {
        this.modelCookLists = modelCookLists;
        this.context = context;
        this.entries = entries;
        this.lineDataSet = lineDataSet;
        this.lineData = lineData;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CookPageViewHolder(ItemFgtab2Cookpage2Binding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        page = new Bundle();
        if(holder instanceof CookPageViewHolder) {
            ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2Title.setText(modelCookLists.get(position).getTitle());
            ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2Rate.setText(String.valueOf(modelCookLists.get(position).getRate()));
            ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2Per.setText("%");

            if(modelCookLists.get(position).getRate() < 0){
                ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2Rate.setTextColor(context.getResources().getColor(R.color.make_port_blue_color));
                ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2Per.setTextColor(context.getResources().getColor(R.color.make_port_blue_color));
            }else{
                ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2Rate.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
                ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2Per.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
            }

            ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2CheckBT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(cookCheckClick != null) {
                        cookCheckClick.onClick(position);
                    }
                }
            });

            ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2DeleteBT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(cookDeleteClick != null) {
                        cookDeleteClick.onClick(position);
                    }
                }
            });

            //포트 아이템 클릭
            ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2RecyclerViewLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(cookItemClick != null) {
                        cookItemClick.onClick(position);
                        ChartBtPosition = 4;
                    }
                }
            });

            //차트 1개월 클릭
            ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2RecyclerViewDur1Bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(cookChartM1Click != null) {
                        cookChartM1Click.onClick(position);
                        ChartBtPosition = 1;
                    }
                }
            });
            //차트 3개월 클릭
            ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2RecyclerViewDur3Bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(cookChartM3Click != null) {
                        cookChartM3Click.onclick(position);
                        ChartBtPosition = 2;
                    }
                }
            });

            //차트 6개월 클릭
            ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2RecyclerViewDur6Bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(cookChartM6Click != null) {
                        cookChartM6Click.onClick(position);
                        ChartBtPosition = 3;
                    }
                }
            });

            //차트 누적 클릭
            ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2RecyclerViewDuraBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(cookChartMaClick != null){
                        cookChartMaClick.onClick(position);
                        ChartBtPosition = 4;
                    }
                }
            });

            //투자하기 클릭
            ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2RecyclerViewChartInvestBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(cookP2InvestClick != null){
                        cookP2InvestClick.onClick(position);
                    }
                }
            });

            //더보기 클릭
            ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2RecyclerViewChartAddBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(cookP2MoreViewClick != null) {
                        cookP2MoreViewClick.onClick(position);
                    }
                }
            });

            //공유하기 클릭
            ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2RecyclerViewChartShareBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(cookP2ShareClick != null) {
                        cookP2ShareClick.onClick(position);
                    }
                }
            });

            //포트만들기 아이템 체크 클릭
            ((CookPageViewHolder) holder).cookpage2Binding.itemCookpage2CheckBT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(cookP2MakeCheckClick != null) {
                        cookP2MakeCheckClick.onClick(position);
                    }
                }
            });


            if(ChartBtPosition == 1) {
                ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2RecyclerViewDur1Bt.setBackgroundResource(R.drawable.round_button);
                ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2RecyclerViewDur1Bt.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
                ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2RecyclerViewDur3Bt.setBackgroundResource(R.drawable.unselected_round_button);
                ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2RecyclerViewDur3Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
                ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2RecyclerViewDur6Bt.setBackgroundResource(R.drawable.unselected_round_button);
                ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2RecyclerViewDur6Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
                ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2RecyclerViewDuraBt.setBackgroundResource(R.drawable.unselected_round_button);
                ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2RecyclerViewDuraBt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
            }else if(ChartBtPosition == 2) {
                ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2RecyclerViewDur1Bt.setBackgroundResource(R.drawable.unselected_round_button);
                ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2RecyclerViewDur1Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
                ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2RecyclerViewDur3Bt.setBackgroundResource(R.drawable.round_button);
                ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2RecyclerViewDur3Bt.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
                ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2RecyclerViewDur6Bt.setBackgroundResource(R.drawable.unselected_round_button);
                ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2RecyclerViewDur6Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
                ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2RecyclerViewDuraBt.setBackgroundResource(R.drawable.unselected_round_button);
                ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2RecyclerViewDuraBt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
            }else if(ChartBtPosition == 3) {
                ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2RecyclerViewDur1Bt.setBackgroundResource(R.drawable.unselected_round_button);
                ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2RecyclerViewDur1Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
                ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2RecyclerViewDur3Bt.setBackgroundResource(R.drawable.unselected_round_button);
                ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2RecyclerViewDur3Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
                ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2RecyclerViewDur6Bt.setBackgroundResource(R.drawable.round_button);
                ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2RecyclerViewDur6Bt.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
                ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2RecyclerViewDuraBt.setBackgroundResource(R.drawable.unselected_round_button);
                ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2RecyclerViewDuraBt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
            }else if(ChartBtPosition == 4) {
                ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2RecyclerViewDur1Bt.setBackgroundResource(R.drawable.unselected_round_button);
                ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2RecyclerViewDur1Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
                ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2RecyclerViewDur3Bt.setBackgroundResource(R.drawable.unselected_round_button);
                ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2RecyclerViewDur3Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
                ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2RecyclerViewDur6Bt.setBackgroundResource(R.drawable.unselected_round_button);
                ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2RecyclerViewDur6Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
                ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2RecyclerViewDuraBt.setBackgroundResource(R.drawable.round_button);
                ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2RecyclerViewDuraBt.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
            }


            if(ACook) {

                ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2RecyclerViewPortCheckButtonLayout.setVisibility(View.VISIBLE);
                ((CookPageViewHolder) holder).cookpage2Binding.itemCookpage2DeleteBT.setVisibility(View.GONE);
                ((CookPageViewHolder) holder).cookpage2Binding.itemCookpage2CheckBT.setVisibility(View.VISIBLE);


                TranslateAnimation animation7 = new TranslateAnimation(100, 0, 0, 0);
                animation7.setDuration(500);
                animation7.setFillAfter(true);
                animation7.setFillEnabled(true);
                ((CookPageViewHolder) holder).cookpage2Binding.itemCookpage2CheckBT.startAnimation(animation7);


                if (modelCookLists.get(position).getChecked() == 0) {
                    ((CookPageViewHolder) holder).cookpage2Binding.itemCookpage2CheckBT.setImageResource(R.drawable.ic_rank_check_off);
                } else {
                    ((CookPageViewHolder) holder).cookpage2Binding.itemCookpage2CheckBT.setImageResource(R.drawable.ic_rank_check_on);
                }
            }
            else{
                ((CookPageViewHolder) holder).cookpage2Binding.itemCookpage2CheckBT.setVisibility(View.GONE);
                ((CookPageViewHolder) holder).cookpage2Binding.itemCookpage2DeleteBT.setVisibility(View.VISIBLE);


                if(!Fg_CookPage2.CookDeleteState) {
                    ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2RecyclerViewPortCheckButtonLayout.setVisibility(View.GONE);
                    ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2CheckBT.setVisibility(View.GONE);
                    ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2DeleteBT.setVisibility(View.GONE);
                }else{
                    ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2RecyclerViewPortCheckButtonLayout.setVisibility(View.VISIBLE);

                    ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2CheckBT.setVisibility(View.GONE);
                    ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2DeleteBT.setVisibility(View.VISIBLE);

                    if(!CookDeleteBt) {
                        TranslateAnimation animC = new TranslateAnimation(100, 0, 0, 0);
                        animC.setDuration(500);
                        animC.setFillAfter(false);
                        animC.setFillEnabled(false);
                        animC.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                            }
                            @Override
                            public void onAnimationEnd(Animation animation) {
                                CookDeleteBt = true;
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {
                            }
                        });
                        ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2DeleteBT.startAnimation(animC);
                    }
                }
            }

            if (modelCookLists.get(position).isOnenChart()) {
                if (entries.size() > 0) {

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
                    lineDataSet.setCircleHoleRadius(5f);
                    lineDataSet.setDrawCircleHole(true);
                    lineDataSet.setDrawCircles(false);

                    lineDataSet.setLabel("");

                    lineData = new LineData(lineDataSet);
                    lineData.setHighlightEnabled(true);

                    XAxis xAxis = ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2RecyclerViewChartView.getXAxis();
                    xAxis.setDrawLabels(false);
                    xAxis.setDrawGridLines(false);
                    xAxis.setDrawAxisLine(false);

                    YAxis yAxis = ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2RecyclerViewChartView.getAxisLeft();
                    yAxis.setDrawLabels(false);
                    yAxis.setDrawGridLines(false);
                    yAxis.setDrawAxisLine(false);
                    yAxis.setEnabled(false);

                    YAxis yAxis1 = ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2RecyclerViewChartView.getAxisRight();
                    yAxis1.setDrawLabels(false);
                    yAxis1.setDrawGridLines(false);
                    yAxis1.setDrawAxisLine(false);
                    yAxis1.setEnabled(false);

                    Legend legend = ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2RecyclerViewChartView.getLegend();
                    legend.setEnabled(false);
                    legend.setDrawInside(false);

                    ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2RecyclerViewChartView.setDescription(null);
                    ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2RecyclerViewChartView.setDrawGridBackground(false);
                    ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2RecyclerViewChartView.setData(lineData);
                    ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2RecyclerViewChartView.setDoubleTapToZoomEnabled(false);
                    ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2RecyclerViewChartView.setDrawGridBackground(false);
                    ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2RecyclerViewChartView.animateY(600, Easing.EasingOption.EaseInCubic);
                    ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2RecyclerViewChartView.setPinchZoom(false);
                    ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2RecyclerViewChartView.setScaleEnabled(false);
                    ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2RecyclerViewChartView.invalidate();

                    maxX = ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2RecyclerViewChartView.getXRange();

                    CustomMarkerView marker = new CustomMarkerView(context, R.layout.item_chart_marker);
                    marker.setChartView((((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2RecyclerViewChartView));
                    ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2RecyclerViewChartView.setMarker(marker);

                }

                ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2RecyclerViewChart.setVisibility(View.VISIBLE);
            } else {
                ((CookPageViewHolder)holder).cookpage2Binding.itemCookpage2RecyclerViewChart.setVisibility(View.GONE);
            }
        }
    }


    @Override
    public int getItemCount() {
        return modelCookLists.size();
    }

    public static class CookPageViewHolder extends RecyclerView.ViewHolder {
        ItemFgtab2Cookpage2Binding cookpage2Binding;

        public CookPageViewHolder(ItemFgtab2Cookpage2Binding cookpage2Binding) {
            super(cookpage2Binding.getRoot());
            this.cookpage2Binding = cookpage2Binding;
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

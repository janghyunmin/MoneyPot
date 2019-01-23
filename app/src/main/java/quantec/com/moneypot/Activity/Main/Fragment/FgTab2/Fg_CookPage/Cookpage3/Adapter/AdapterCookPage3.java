package quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.Cookpage3.Adapter;

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

import com.bumptech.glide.Glide;
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

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.Cookpage3.Fg_CookPage3;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.Cookpage3.Model.dModel.ModelMyCookList;
import quantec.com.moneypot.R;
import quantec.com.moneypot.databinding.ItemFgtab2Fgcookpage3Binding;

public class AdapterCookPage3 extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    ArrayList<ModelMyCookList> modelMyCookLists;
    Context context;

    float currentX, maxX;
    int checkedPosition;

    List<Entry> entries;
    LineDataSet lineDataSet;
    LineData lineData;

    public static boolean CookPage3_DeleteAnimState = false;

    private CookPage3ItemClick cookPage3ItemClick;
    public interface CookPage3ItemClick {
        public void onClick(int position);
    }
    public void setCookPage3ItemClick(CookPage3ItemClick cookPage3ItemClick) {
        this.cookPage3ItemClick = cookPage3ItemClick;
    }


    private CookPage3ItemDeleteClick cookPage3ItemDeleteClick;
    public interface CookPage3ItemDeleteClick {
        public void onClick(int position);
    }
    public void setCookPage3ItemDeleteClick(CookPage3ItemDeleteClick cookPage3ItemDeleteClick) {
        this.cookPage3ItemDeleteClick = cookPage3ItemDeleteClick;
    }

    private CookPage3ChartDur1Click cookPage3chartDur1Click;
    public interface CookPage3ChartDur1Click {
        public void onClick(int position);
    }

    public void setCookPage3chartDur1Click(CookPage3ChartDur1Click cookPage3chartDur1Click) {
        this.cookPage3chartDur1Click = cookPage3chartDur1Click;
    }

    private CookPage3ChartDur3Click cookPage3ChartDur3Click;
    public interface CookPage3ChartDur3Click {
        public void onClick(int position);
    }
    public void setCookPage3ChartDur3Click(CookPage3ChartDur3Click cookPage3ChartDur3Click) {
        this.cookPage3ChartDur3Click = cookPage3ChartDur3Click;
    }

    private CookPage3ChartDur6Click cookPage3ChartDur6Click;
    public interface CookPage3ChartDur6Click {
        public void onClick(int position);
    }
    public void setCookPage3ChartDur6Click(CookPage3ChartDur6Click cookPage3ChartDur6Click) {
        this.cookPage3ChartDur6Click = cookPage3ChartDur6Click;
    }

    private CookPage3ChartDuraClick cookPage3ChartDuraClick;
    public interface CookPage3ChartDuraClick {
        public void onClick(int position);
    }
    public void setCookPage3ChartDuraClick(CookPage3ChartDuraClick cookPage3ChartDuraClick) {
        this.cookPage3ChartDuraClick = cookPage3ChartDuraClick;
    }


    private CookPage3PortDetailPageClick cookPage3PortDetailPageClick;
    public interface CookPage3PortDetailPageClick {
        public void onClick(int position);
    }
    public void setCookPage3PortDetailPageClick(CookPage3PortDetailPageClick cookPage3PortDetailPageClick) {
        this.cookPage3PortDetailPageClick = cookPage3PortDetailPageClick;
    }

    private CookPage3PortInvestClick cookPage3PortInvestClick;
    public interface CookPage3PortInvestClick {
        public void onClick(int position);
    }
    public void setCookPage3PortInvestClick(CookPage3PortInvestClick cookPage3PortInvestClick) {
        this.cookPage3PortInvestClick = cookPage3PortInvestClick;
    }

    private CookPage3PortImageModify cookPage3PortImageModify;
    public interface CookPage3PortImageModify {
        public void onClick(int position);
    }
    public void setCookPage3PortImageModify(CookPage3PortImageModify cookPage3PortImageModify) {
        this.cookPage3PortImageModify = cookPage3PortImageModify;
    }

    public AdapterCookPage3(ArrayList<ModelMyCookList> modelMyCookLists, Context context, List<Entry> entries, LineDataSet lineDataSet, LineData lineData) {
        this.modelMyCookLists = modelMyCookLists;
        this.context = context;
        this.entries = entries;
        this.lineDataSet = lineDataSet;
        this.lineData = lineData;
    }

    @Override
    public int getItemViewType(int position) {
        return modelMyCookLists.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_ITEM) {
            return new CookPage3ItemHolder(ItemFgtab2Fgcookpage3Binding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        } else {
            return new ProgressViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_public_progress, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof CookPage3ItemHolder) {
            ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewPortName.setText(modelMyCookLists.get(position).getTitle());

            if(modelMyCookLists.get(position).getRate() < 0) {
                ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewPortRate.setText(String.valueOf(modelMyCookLists.get(position).getRate()));
                ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewPortRate.setTextColor(context.getResources().getColor(R.color.make_port_blue_color));
                ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewPortPersent.setTextColor(context.getResources().getColor(R.color.make_port_blue_color));
            }else{
                ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewPortRate.setText(String.valueOf(modelMyCookLists.get(position).getRate()));
                ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewPortRate.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
                ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewPortPersent.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
            }


            if(Fg_CookPage3.ImageAnimState) {
                ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewPortImageModify.setVisibility(View.VISIBLE);
            }else{
                ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewPortImageModify.setVisibility(View.GONE);
            }

            Glide.with(context)
                    .load(modelMyCookLists.get(position).getImage())
                    .placeholder(R.drawable.noname_img)
                    .error(R.drawable.noname_img)
                    .crossFade()
                    .bitmapTransform(new CropCircleTransformation(context))
                    .into(((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewPortImage);


            if (modelMyCookLists.get(position).isOpenchart()) {
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
                    lineDataSet.setDrawCircles(false);

                    lineData = new LineData(lineDataSet);
                    lineData.setHighlightEnabled(true);

                    XAxis xAxis = ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewChartView.getXAxis();
                    xAxis.setDrawLabels(false);
                    xAxis.setDrawGridLines(false);
                    xAxis.setDrawAxisLine(false);

                    YAxis yAxis = ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewChartView.getAxisLeft();
                    yAxis.setDrawLabels(false);
                    yAxis.setDrawGridLines(false);
                    yAxis.setDrawAxisLine(false);
                    yAxis.setEnabled(false);

                    YAxis yAxis1 = ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewChartView.getAxisRight();
                    yAxis1.setDrawLabels(false);
                    yAxis1.setDrawGridLines(false);
                    yAxis1.setDrawAxisLine(false);
                    yAxis1.setEnabled(false);

                    Legend legend = ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewChartView.getLegend();
                    legend.setEnabled(false);
                    legend.setDrawInside(false);

                    ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewChartView.setDescription(null);
                    ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewChartView.setDrawGridBackground(false);
                    ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewChartView.setData(lineData);
                    ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewChartView.setDoubleTapToZoomEnabled(false);
                    ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewChartView.setDrawGridBackground(false);
                    ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewChartView.animateY(600, Easing.EasingOption.EaseInCubic);
                    ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewChartView.setPinchZoom(false);
                    ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewChartView.setScaleEnabled(false);
                    ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewChartView.invalidate();

                    maxX = ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewChartView.getXRange();

                    CustomMarkerView marker = new CustomMarkerView(context, R.layout.item_chart_marker);
                    marker.setChartView((((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewChartView));
                    ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewChartView.setMarker(marker);
                }
                ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewChart.setVisibility(View.VISIBLE);
            } else {
                ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewChart.setVisibility(View.GONE);
            }

            //포트 아이템 클릭 이벤트
            ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3LinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(cookPage3ItemClick != null){
                        cookPage3ItemClick.onClick(position);
                        checkedPosition = 4;
                    }
                }
            });

            //상세페이지 클릭 이벤트
            ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewChartAddBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(cookPage3PortDetailPageClick != null) {
                        cookPage3PortDetailPageClick.onClick(position);
                    }
                }
            });

            //투자하기 클릭 이벤트
            ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewChartInvestBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(cookPage3PortInvestClick != null) {
                        cookPage3PortInvestClick.onClick(position);
                    }
                }
            });

            //포트 삭제하기 클릭 이벤트
            ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewPortDeleteBT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(cookPage3ItemDeleteClick != null) {
                        cookPage3ItemDeleteClick.onClick(position);
                    }
                }
            });

            //차트 1개월 클릭
            ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewDur1Bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(cookPage3chartDur1Click != null) {
                        cookPage3chartDur1Click.onClick(position);
                        checkedPosition = 1;
                    }
                }
            });

            //차트 3개월 클릭
            ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewDur3Bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(cookPage3ChartDur3Click != null) {
                        cookPage3ChartDur3Click.onClick(position);
                        checkedPosition = 2;
                    }
                }
            });

            //차트 6개월 클릭
            ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewDur6Bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(cookPage3ChartDur6Click != null) {
                        cookPage3ChartDur6Click.onClick(position);
                        checkedPosition = 3;
                    }
                }
            });

            //차트 누적 클릭
            ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewDuraBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(cookPage3ChartDuraClick != null){
                        cookPage3ChartDuraClick.onClick(position);
                        checkedPosition = 4;
                    }
                }
            });

            if (checkedPosition == 1) {
                ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewDur1Bt.setBackgroundResource(R.drawable.round_button);
                ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewDur1Bt.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
                ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewDur3Bt.setBackgroundResource(R.drawable.unselected_round_button);
                ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewDur3Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
                ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewDur6Bt.setBackgroundResource(R.drawable.unselected_round_button);
                ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewDur6Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
                ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewDuraBt.setBackgroundResource(R.drawable.unselected_round_button);
                ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewDuraBt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
            } else if (checkedPosition == 2) {
                ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewDur1Bt.setBackgroundResource(R.drawable.unselected_round_button);
                ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewDur1Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
                ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewDur3Bt.setBackgroundResource(R.drawable.round_button);
                ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewDur3Bt.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
                ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewDur6Bt.setBackgroundResource(R.drawable.unselected_round_button);
                ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewDur6Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
                ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewDuraBt.setBackgroundResource(R.drawable.unselected_round_button);
                ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewDuraBt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
            } else if (checkedPosition == 3) {
                ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewDur1Bt.setBackgroundResource(R.drawable.unselected_round_button);
                ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewDur1Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
                ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewDur3Bt.setBackgroundResource(R.drawable.unselected_round_button);
                ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewDur3Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
                ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewDur6Bt.setBackgroundResource(R.drawable.round_button);
                ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewDur6Bt.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
                ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewDuraBt.setBackgroundResource(R.drawable.unselected_round_button);
                ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewDuraBt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
            } else if (checkedPosition == 4) {
                ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewDur1Bt.setBackgroundResource(R.drawable.unselected_round_button);
                ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewDur1Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
                ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewDur3Bt.setBackgroundResource(R.drawable.unselected_round_button);
                ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewDur3Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
                ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewDur6Bt.setBackgroundResource(R.drawable.unselected_round_button);
                ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewDur6Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
                ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewDuraBt.setBackgroundResource(R.drawable.round_button);
                ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewDuraBt.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
            }

            if (!Fg_CookPage3.CookPage3_DeleteState) {
                ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewPortDeleteBT.setVisibility(View.GONE);
                ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewPortRate.setVisibility(View.VISIBLE);
                ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewPortPersent.setVisibility(View.VISIBLE);
            } else {
                ((CookPage3ItemHolder) holder).itemCookpage3Binding.itemCookpage3RecyclerViewPortDeleteBT.setVisibility(View.VISIBLE);
                ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewPortRate.setVisibility(View.GONE);
                ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewPortPersent.setVisibility(View.GONE);

                if (!CookPage3_DeleteAnimState) {
                    TranslateAnimation animatio = new TranslateAnimation(100, 0, 0, 0);
                    animatio.setDuration(500);
                    animatio.setFillAfter(false);
                    animatio.setFillEnabled(false);
                    animatio.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                        }
                        @Override
                        public void onAnimationEnd(Animation animation) {
                            CookPage3_DeleteAnimState = true;
                        }
                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }
                    });
                    ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewPortDeleteBT.startAnimation(animatio);
                }
            }

            ((CookPage3ItemHolder)holder).itemCookpage3Binding.itemCookpage3RecyclerViewPortImageModify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(cookPage3PortImageModify != null) {
                        cookPage3PortImageModify.onClick(position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return modelMyCookLists.size();
    }

    public static class CookPage3ItemHolder extends RecyclerView.ViewHolder {

        ItemFgtab2Fgcookpage3Binding itemCookpage3Binding;

        public CookPage3ItemHolder(ItemFgtab2Fgcookpage3Binding itemCookpage3Binding) {
            super(itemCookpage3Binding.getRoot());
            this.itemCookpage3Binding = itemCookpage3Binding;
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

package quantec.com.moneypot.Activity.Main.Fragment.FgTab4.Adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
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

import quantec.com.moneypot.Activity.Main.Fragment.FgTab4.Fg_tab4;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab4.Model.nModel.ModelFgTab4;
import quantec.com.moneypot.R;
import quantec.com.moneypot.databinding.ItemFgtab4Tab4dataBinding;

public class AdapterFgTab4 extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    //아이템 클릭시 실행 함수
    private ItemClick itemClick;
    public interface ItemClick {
        public void onClick(int position);
    }

    //아이템 클릭시 실행 함수 등록 함수
    public void setItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    private DeleteClick deleteClick;
    public interface DeleteClick {
        public void onClick(int position);
    }

    //아이템 삭제
    public void setDeleteClick(DeleteClick deleteClick) {
        this.deleteClick = deleteClick;
    }

    private SelectDur1ItemClick selectDurItemClick1;
    public interface SelectDur1ItemClick {
        public void onclick(int position);
    }

    //차트 1개월 버튼
    public void setSelectDurItemClick1(SelectDur1ItemClick selectDurItemClick1) {
        this.selectDurItemClick1 = selectDurItemClick1;
    }

    private SelectDur3ItemClick selectDurItemClick3;
    public interface SelectDur3ItemClick {
        public void onclick(int position);
    }

    //차트 3개월 버튼
    public void setSelectDurItemClick3(SelectDur3ItemClick selectDurItemClick3) {
        this.selectDurItemClick3 = selectDurItemClick3;
    }

    private SelectDur6ItemClick selectDurItemClick6;
    public interface SelectDur6ItemClick {
        public void onclick(int position);
    }

    //차트 6개월 버튼
    public void setSelectDurItemClick6(SelectDur6ItemClick selectDurItemClick6) {
        this.selectDurItemClick6 = selectDurItemClick6;
    }

    private SelectDuraItemClick selectDurItemClicka;
    public interface SelectDuraItemClick {
        public void onclick(int position);
    }

    //차트 누적 버튼
    public void setSelectDurItemClicka(SelectDuraItemClick selectDurItemClicka) {
        this.selectDurItemClicka = selectDurItemClicka;
    }

    private SelectAddPortDetailClick selectAddPortDetailClick;
    public interface SelectAddPortDetailClick {
        public void onclick(int position);
    }

    //포트 더보기 클릭
    public void setSelectAddPortDetailClick(SelectAddPortDetailClick selectAddPortDetailClick) {
        this.selectAddPortDetailClick = selectAddPortDetailClick;
    }

    private SelectInvestClick selectInvestClick;
    public interface SelectInvestClick {
        public void onClick(int position);
    }

    //포트 바로투자 클릭
    public void setSelectInvestClick(SelectInvestClick selectInvestClick) {
        this.selectInvestClick = selectInvestClick;
    }

    private SelectZzimClick selectZzimClick;
    public interface SelectZzimClick {
        public void onClick(int position);
    }

    //포트 찜 버튼 클릭
    public void setSelectZzimClick(SelectZzimClick selectZzimClick) {
        this.selectZzimClick = selectZzimClick;
    }

    List<Entry> entries;
    LineDataSet lineDataSet;
    LineData lineData;
    Context context;

    XAxis xAxis;

    float currentX, maxX;
    int checkedPosition;

    private ArrayList<ModelFgTab4> myData;

    public static boolean AnimState = false;
    public static boolean DeleteAnimState = false;

    public AdapterFgTab4(Context context, ArrayList<ModelFgTab4> myData, List<Entry> entries, LineDataSet lineDataSet, LineData lineData){
        this.context = context;
        this.myData = myData;
        this.entries = entries;
        this.lineDataSet = lineDataSet;
        this.lineData = lineData;
    }

    @Override
    public int getItemViewType(int position) {
        return myData.get(position) != null ? VIEW_ITEM : VIEW_PROG;}

    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_ITEM) {
            return new MyViewHolder(ItemFgtab4Tab4dataBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        } else {
            return new ProgressViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fgtab4_tab4dataempty, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof MyViewHolder) {

            ((MyViewHolder) holder).tab4dataBinding.tab4RecyclerViewPortCheckButtonLayout.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });

            ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewPortImage.setImageResource(myData.get(position).getImage());
            ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewPortName.setText(myData.get(position).getTitle());

            if(Double.parseDouble(myData.get(position).getRate()) < 0) {
                ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewPortRate.setText(myData.get(position).getRate());
                ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewPortRate.setTextColor(context.getResources().getColor(R.color.make_port_blue_color));
                ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewPortPersent.setTextColor(context.getResources().getColor(R.color.make_port_blue_color));
            }else{
                ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewPortRate.setText(myData.get(position).getRate());
                ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewPortRate.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
                ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewPortPersent.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
            }

            if(Fg_tab4.portOpenState) {
                ((MyViewHolder) holder).tab4dataBinding.tab4RecyclerViewPortZzimBt.setVisibility(View.GONE);
            }else{

                ((MyViewHolder) holder).tab4dataBinding.tab4RecyclerViewPortZzimBt.setVisibility(View.VISIBLE);

                if(myData.get(position).isZzimCheck()){
                    ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewPortZzimBt.setImageResource(R.drawable.start_on);
                }else{
                    ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewPortZzimBt.setImageResource(R.drawable.start_off);
                }

                if(AnimState) {
                    TranslateAnimation animation7 = new TranslateAnimation(100, 0, 0, 0);
                    animation7.setDuration(500);
                    animation7.setFillAfter(true);
                    animation7.setFillEnabled(true);
                    ((MyViewHolder) holder).tab4dataBinding.tab4RecyclerViewPortZzimBt.startAnimation(animation7);
                }
            }

            //포트 클릭 이벤트
            ((MyViewHolder)holder).tab4dataBinding.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClick != null) {
                        itemClick.onClick(position);
                        checkedPosition = 4;
                    }
                }
            });

            //포트삭제 버튼
            ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewPortDeleteBT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (deleteClick != null) {
                        deleteClick.onClick(position);
                    }
                }
            });


            //포트 차트 1개월 버튼
            ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewDur1Bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selectDurItemClick1 != null) {
                        selectDurItemClick1.onclick(position);
                        checkedPosition = 1;
                    }
                }
            });

            //포트 차트 3개월 버튼
            ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewDur3Bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selectDurItemClick3 != null) {
                        selectDurItemClick3.onclick(position);
                        checkedPosition = 2;
                    }
                }
            });

            //포트 차트 6개월 버튼
            ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewDur6Bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selectDurItemClick6 != null) {
                        selectDurItemClick6.onclick(position);
                        checkedPosition = 3;
                    }
                }
            });

            //포트 차트 누적 버튼
            ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewDuraBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selectDurItemClicka != null) {
                        selectDurItemClicka.onclick(position);
                        checkedPosition = 4;
                    }
                }
            });

            if(checkedPosition == 1) {
                ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewDur1Bt.setBackgroundResource(R.drawable.round_button);
                ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewDur1Bt.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
                ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewDur3Bt.setBackgroundResource(R.drawable.unselected_round_button);
                ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewDur3Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
                ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewDur6Bt.setBackgroundResource(R.drawable.unselected_round_button);
                ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewDur6Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
                ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewDuraBt.setBackgroundResource(R.drawable.unselected_round_button);
                ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewDuraBt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
            }else if(checkedPosition == 2) {
                ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewDur1Bt.setBackgroundResource(R.drawable.unselected_round_button);
                ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewDur1Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
                ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewDur3Bt.setBackgroundResource(R.drawable.round_button);
                ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewDur3Bt.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
                ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewDur6Bt.setBackgroundResource(R.drawable.unselected_round_button);
                ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewDur6Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
                ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewDuraBt.setBackgroundResource(R.drawable.unselected_round_button);
                ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewDuraBt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
            }else if(checkedPosition == 3) {
                ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewDur1Bt.setBackgroundResource(R.drawable.unselected_round_button);
                ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewDur1Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
                ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewDur3Bt.setBackgroundResource(R.drawable.unselected_round_button);
                ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewDur3Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
                ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewDur6Bt.setBackgroundResource(R.drawable.round_button);
                ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewDur6Bt.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
                ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewDuraBt.setBackgroundResource(R.drawable.unselected_round_button);
                ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewDuraBt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
            }else if(checkedPosition == 4) {
                ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewDur1Bt.setBackgroundResource(R.drawable.unselected_round_button);
                ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewDur1Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
                ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewDur3Bt.setBackgroundResource(R.drawable.unselected_round_button);
                ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewDur3Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
                ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewDur6Bt.setBackgroundResource(R.drawable.unselected_round_button);
                ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewDur6Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
                ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewDuraBt.setBackgroundResource(R.drawable.round_button);
                ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewDuraBt.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
            }

            //더보기 버튼
            ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewChartAddBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selectAddPortDetailClick != null) {
                        selectAddPortDetailClick.onclick(position);
                    }
                }
            });

            //투자하기 버튼
            ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewChartInvestBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (selectInvestClick != null) {
                        selectInvestClick.onClick(position);
                    }
                }
            });

            //포트 찜하기 버튼
            ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewPortZzimBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(selectZzimClick != null) {
                        selectZzimClick.onClick(position);
                    }
                }
            });

            if (myData.get(position).isOpenChart()) {
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

                    xAxis = ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewChartView.getXAxis();
                    xAxis.setDrawLabels(false);
                    xAxis.setDrawGridLines(false);
                    xAxis.setDrawAxisLine(false);

                    YAxis yAxis = ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewChartView.getAxisLeft();
                    yAxis.setDrawLabels(false);
                    yAxis.setDrawGridLines(false);
                    yAxis.setDrawAxisLine(false);
                    yAxis.setEnabled(false);

                    YAxis yAxis1 = ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewChartView.getAxisRight();
                    yAxis1.setDrawLabels(false);
                    yAxis1.setDrawGridLines(false);
                    yAxis1.setDrawAxisLine(false);
                    yAxis1.setEnabled(false);

                    Legend legend = ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewChartView.getLegend();
                    legend.setEnabled(false);
                    legend.setDrawInside(false);

                    ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewChartView.setDescription(null);
                    ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewChartView.setDrawGridBackground(false);
                    ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewChartView.setData(lineData);
                    ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewChartView.setDoubleTapToZoomEnabled(false);
                    ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewChartView.setDrawGridBackground(false);
                    ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewChartView.animateY(600, Easing.EasingOption.EaseInCubic);
                    ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewChartView.setPinchZoom(false);
                    ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewChartView.setScaleEnabled(false);
                    ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewChartView.invalidate();

                    maxX = ((MyViewHolder) holder).tab4dataBinding.tab4RecyclerViewChartView.getXRange();

                    CustomMarkerView marker = new CustomMarkerView(context, R.layout.item_chart_marker);
                    marker.setChartView(((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewChartView);
                    ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewChartView.setMarker(marker);

                }

                ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewChart.setVisibility(View.VISIBLE);
            } else {
                ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewChart.setVisibility(View.GONE);
            }

            if (!Fg_tab4.Delete_State) {
                ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewPortDeleteBT.setVisibility(View.GONE);
                ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewPortRate.setVisibility(View.VISIBLE);
                ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewPortPersent.setVisibility(View.VISIBLE);
            } else {
                ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewPortDeleteBT.setVisibility(View.VISIBLE);
                ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewPortRate.setVisibility(View.GONE);
                ((MyViewHolder)holder).tab4dataBinding.tab4RecyclerViewPortPersent.setVisibility(View.GONE);

                if(!DeleteAnimState) {
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
                            DeleteAnimState = true;
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }
                    });
                    ((MyViewHolder) holder).tab4dataBinding.tab4RecyclerViewPortDeleteBT.startAnimation(animatio);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return myData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ItemFgtab4Tab4dataBinding tab4dataBinding;

        MyViewHolder(ItemFgtab4Tab4dataBinding tab4dataBinding){
            super(tab4dataBinding.getRoot());
            this.tab4dataBinding = tab4dataBinding;
        }
    }

    static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressViewHolder(View v) {
        super(v);
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

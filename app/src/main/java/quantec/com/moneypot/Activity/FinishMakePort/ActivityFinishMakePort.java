package quantec.com.moneypot.Activity.FinishMakePort;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

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

import quantec.com.moneypot.Activity.FinishMakePort.Dialog.DialogMyPortName;
import quantec.com.moneypot.ModelCommon.nModel.SavedPotDto;
import quantec.com.moneypot.DataManager.ChartManager;
import quantec.com.moneypot.Dialog.LoadingMakePort.DialogLoadingMakingPort;
import quantec.com.moneypot.Network.Retrofit.RetrofitClient;
import quantec.com.moneypot.R;
import quantec.com.moneypot.databinding.ActivityFinishMakePortBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityFinishMakePort extends AppCompatActivity {

    ActivityFinishMakePortBinding finishMakePortBinding;

    private DialogMyPortName myPortNameDialog;
    private DialogLoadingMakingPort loadingCustomMakingPort;

    List<Entry> entries2;
    LineDataSet lineDataSet2;
    LineData lineData2;
    Intent intent;

    Long TotalCash;
    float currentX, maxX;
    Toast toast;
    String ptCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        finishMakePortBinding = DataBindingUtil.setContentView(this, R.layout.activity_finish_make_port);
        finishMakePortBinding.setFinishMakePort(this);

        // 스테이터스 바 색상 변경 -> 화이트
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }else{
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.main_page_status_bar_color));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        intent = getIntent();

        finishMakePortBinding.finishPortBackBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        finishMakePortBinding.finishPortSaveBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myPortNameDialog = new DialogMyPortName(ActivityFinishMakePort.this, leftListener, rightListener);
                myPortNameDialog.show();
            }
        });


        if(intent.getStringArrayListExtra("finishtitle").size() > 2) {
            for(int a = 0 ; a < intent.getStringArrayListExtra("finishtitle").size() ; a++) {
                if(a == 0) {
                    finishMakePortBinding.finishPortBottomPort.setText(intent.getStringArrayListExtra("finishtitle").get(a));
                }else if(a == 1) {
                    finishMakePortBinding.finishPortBottomPort2.setText(intent.getStringArrayListExtra("finishtitle").get(a));
                }else if(a == 2) {
                    finishMakePortBinding.finishPortBottomPort3.setVisibility(View.VISIBLE);
                    finishMakePortBinding.finishPortBottomPort3.setText(intent.getStringArrayListExtra("finishtitle").get(a));
                }else if(a == 3) {
                    finishMakePortBinding.finishPortBottomPort4.setVisibility(View.VISIBLE);
                    finishMakePortBinding.finishPortBottomPort4.setText(intent.getStringArrayListExtra("finishtitle").get(a));
                }else if(a == 4){
                    finishMakePortBinding.finishPortBottomPort5.setVisibility(View.VISIBLE);
                    finishMakePortBinding.finishPortBottomPort5.setText(intent.getStringArrayListExtra("finishtitle").get(a));
                }
            }
        }else{
            for(int a = 0 ; a < intent.getStringArrayListExtra("finishtitle").size() ; a++) {
                if(a == 0) {
                    finishMakePortBinding.finishPortBottomPort.setText(intent.getStringArrayListExtra("finishtitle").get(a));
                }else if(a == 1) {
                    finishMakePortBinding.finishPortBottomPort2.setText(intent.getStringArrayListExtra("finishtitle").get(a));
                }
            }
        }

        finishMakePortBinding.finishPortCategory.setText(intent.getStringExtra("finishcategory"));
        finishMakePortBinding.finishPortBottomCash.setText(intent.getStringExtra("finishcash")+"만원");

        TotalCash = Long.parseLong(intent.getStringExtra("finishcash"));
        ptCode = intent.getStringExtra("ptCode");

        entries2 = new ArrayList<>();

        for(int xCount = 0; xCount < ChartManager.get_Instance().getTransChartLists().size() ; xCount++) {

            entries2.add(new Entry(xCount, ChartManager.get_Instance().getTransChartLists().get(xCount).getRate(),ChartManager.get_Instance().getTransChartLists().get(xCount).getDate()));
            if(xCount == ChartManager.get_Instance().getTransChartLists().size() - 1){

                lineDataSet2 = new LineDataSet(entries2, null);
                lineDataSet2.setLineWidth(1.5f);
                lineDataSet2.setColor(Color.parseColor("#FFFF0000"));
                lineDataSet2.setDrawCircles(false);
                lineDataSet2.setDrawHorizontalHighlightIndicator(false);
                lineDataSet2.setDrawValues(false);
                lineDataSet2.setHighlightEnabled(true);
                lineDataSet2.setDrawHighlightIndicators(true);
                lineDataSet2.setDrawHorizontalHighlightIndicator(false);
                lineDataSet2.setHighLightColor(R.color.chart_limit_line_color);
                lineDataSet2.setHighlightLineWidth(1f);

                lineData2 = new LineData(lineDataSet2);

                XAxis xAxis = finishMakePortBinding.finishPortChartView.getXAxis();
                xAxis.setDrawLabels(false);
                xAxis.setDrawGridLines(false);
                xAxis.setDrawAxisLine(false);
                xAxis.setEnabled(false);

                YAxis yAxis = finishMakePortBinding.finishPortChartView.getAxisLeft();
                yAxis.setDrawLabels(false);
                yAxis.setDrawGridLines(false);
                yAxis.setDrawAxisLine(false);
                yAxis.setEnabled(false);

                YAxis yAxis1 = finishMakePortBinding.finishPortChartView.getAxisRight();
                yAxis1.setDrawLabels(false);
                yAxis1.setDrawGridLines(false);
                yAxis1.setDrawAxisLine(false);
                yAxis1.setEnabled(false);

                Legend legend = finishMakePortBinding.finishPortChartView.getLegend();
                legend.setEnabled(false);
                legend.setDrawInside(false);

                finishMakePortBinding.finishPortChartView.setDescription(null);
                finishMakePortBinding.finishPortChartView.setDrawGridBackground(false);
                finishMakePortBinding.finishPortChartView.setData(lineData2);
                finishMakePortBinding.finishPortChartView.setDoubleTapToZoomEnabled(false);
                finishMakePortBinding.finishPortChartView.setDrawGridBackground(false);
                finishMakePortBinding.finishPortChartView.animateY(600, Easing.EasingOption.EaseInCubic);
                finishMakePortBinding.finishPortChartView.setPinchZoom(false);
                finishMakePortBinding.finishPortChartView.setScaleEnabled(false);
                finishMakePortBinding.finishPortChartView.invalidate();

                maxX = finishMakePortBinding.finishPortChartView.getXRange();

                CustomMarkerView marker = new CustomMarkerView(ActivityFinishMakePort.this,R.layout.item_chart_marker);
                marker.setChartView(finishMakePortBinding.finishPortChartView);
                finishMakePortBinding.finishPortChartView.setMarker(marker);
            }
        }

    }//onCreate 끝

    //취소
    private View.OnClickListener leftListener = new View.OnClickListener() {
        public void onClick(View v) {

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

            myPortNameDialog.dismiss();
        }
    };

    //확인
    private View.OnClickListener rightListener = new View.OnClickListener() {
        public void onClick(View v) {

            if(myPortNameDialog.callFuction().length() > 20) {
                //커스텀 토스트 메시지
                View toastView = View.inflate(ActivityFinishMakePort.this, R.layout.toast_activityfinishmakeport_maxportname, null);
                toast = new Toast(ActivityFinishMakePort.this);
                toast.setView(toastView);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.show();
            }
            else if(myPortNameDialog.callFuction().length() == 0){
                Toast.makeText(ActivityFinishMakePort.this, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
            }
            else{

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                loadingCustomMakingPort = new DialogLoadingMakingPort(ActivityFinishMakePort.this, "주식투자는 단기적 수익을 쫒기 보다는\n장기적으로 보아야 성공할 수 있습니다.");
                loadingCustomMakingPort.show();


                SavedPotDto potDto = new SavedPotDto(ptCode, "", "");
                Call<Object> getchartItem = RetrofitClient.getInstance().getService().getSavedMyPot("application/json", potDto);
                getchartItem.enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {

                        if(response.code() == 200) {
                            Log.e("포트 저장 성공", "성공값 : "+ response.body().toString());
                            loadingCustomMakingPort.dismiss();
                        }
                    }
                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {
                        loadingCustomMakingPort.dismiss();
                        Toast.makeText(ActivityFinishMakePort.this, "서버가 불안정 합니다\n잠시후 다시 시도해 주세요.",Toast.LENGTH_SHORT).show();
                    }
                });


//                Call<ModelPortSavedInfo> getchartItem2 = RetrofitClient.getInstance().getService().getMyportInsert(myPortNameDialog.callFuction(), "1");
//                getchartItem2.enqueue(new Callback<ModelPortSavedInfo>() {
//                    @Override
//                    public void onResponse(Call<ModelPortSavedInfo> call, Response<ModelPortSavedInfo> response) {
//                        if(response.code() == 200) {
//                            myPortNameDialog.dismiss();
//                            loadingCustomMakingPort.dismiss();
//
//                            SharedPreferences pref = getSharedPreferences("myPortNumber", MODE_PRIVATE);
//                            SharedPreferences.Editor editor = pref.edit();
//                            editor.putInt("mpNumber", response.body().getTotal());
//                            editor.apply();
//
//                            Intent intent2 = new Intent(ActivityFinishMakePort.this, Fg_CookPage2.class);
//                            intent2.putExtra("portname", response.body().getName());
//                            intent2.putExtra("portcode", response.body().getUcode());
//                            intent2.putExtra("portDrate", response.body().getdRate());
//                            intent2.putExtra("portcash", TotalCash);
//                            setResult(1, intent2);
//                            finish();
//                        }else if(response.code() == 303){
//                            loadingCustomMakingPort.dismiss();
//                            Toast.makeText(ActivityFinishMakePort.this, "포트이름에 금칙어가 포함되어 있습니다\n수정 후에 다시 시도해주세요.",Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                    @Override
//                    public void onFailure(Call<ModelPortSavedInfo> call, Throwable t) {
//                        Toast.makeText(ActivityFinishMakePort.this, "서버가 불안정 합니다\n잠시후 다시 시도해 주세요.",Toast.LENGTH_SHORT).show();
//                    }
//                });
            }
        }
    };

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
            paint.setColor(getResources().getColor(R.color.chart_point_color));
            paint.setStrokeWidth(5f);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawCircle(posX, posY,18, paint);
            super.draw(canvas, posX, posY);
        }
    }
}

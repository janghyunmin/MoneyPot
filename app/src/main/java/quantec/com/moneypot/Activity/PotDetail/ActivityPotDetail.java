package quantec.com.moneypot.Activity.PotDetail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import quantec.com.moneypot.R;

public class ActivityPotDetail extends AppCompatActivity {

    PieChart pieChart;
    LinearLayout layout2;
    TextView warningTitle, investBt;

    boolean openState;

    ScrollView scrollView;
    ImageView warningImage;

    public static final int[] JOYFUL_COLORS = {
            Color.rgb(126, 111, 123),Color.rgb(255, 114, 114)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pot_detail);

        pieChart = findViewById(R.id.pieChart);

        pieChart.setTransparentCircleRadius(0f);
        pieChart.setDrawEntryLabels(false);
        pieChart.setUsePercentValues(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setRotationEnabled(false);

        ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();

        yValues.add(new PieEntry(50f,"채권"));
        yValues.add(new PieEntry(50f,"주식"));

        pieChart.animateY(600, Easing.EasingOption.EaseInOutCubic); //애니메이션
        pieChart.setDrawCenterText(false);

        Legend l = pieChart.getLegend();
        l.setDrawInside(false);
        l.setEnabled(false);

        PieDataSet dataSet = new PieDataSet(yValues,"");
        dataSet.setColors(JOYFUL_COLORS);
        dataSet.setDrawValues(false);

        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        pieChart.invalidate();

        ConstraintLayout getView = findViewById(R.id.includePotDetail);

        layout2 = getView.findViewById(R.id.layout2);
        warningTitle = findViewById(R.id.warningTitle);

        warningTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openState = !openState;

                changeVisibility(openState);

                scrollView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.setSmoothScrollingEnabled(true);
                        scrollView.fullScroll(View.FOCUS_DOWN);
                    }
                }, 500);

            }
        });

        investBt = getView.findViewById(R.id.investBt);
        warningImage = getView.findViewById(R.id.warningImage);


//        ScrollView getView = findViewById(R.id.includePotDetail);

//        scrollView = getView.findViewById(R.id.scrollView);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//                @Override
//                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                    Log.e("1qjs","1번");
//                }
//            });
//        }

        scrollView = findViewById(R.id.scrollView);

    }


    /**
     * 클릭된 Item의 상태 변경
     * @param isExpanded Item을 펼칠 것인지 여부
     */
    private void changeVisibility(final boolean isExpanded) {

        // height 값을 dp로 지정해서 넣고싶으면 아래 소스를 이용

        if(isExpanded){
            warningImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_up_gray));
        }else{
            warningImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_down_gray));
        }

        layout2.measure(View.MeasureSpec.UNSPECIFIED , View.MeasureSpec.UNSPECIFIED );
        int height = layout2.getMeasuredHeight();

        // ValueAnimator.ofInt(int... values)는 View가 변할 값을 지정, 인자는 int 배열
        ValueAnimator va = isExpanded ? ValueAnimator.ofInt(1, height) : ValueAnimator.ofInt(height, 1);
        // Animation이 실행되는 시간, n/1000초
        va.setDuration(450);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // value는 height 값
                int value = (int) animation.getAnimatedValue();

                // imageView의 높이 변경
                layout2.getLayoutParams().height = value;
                layout2.requestLayout();
                // imageView가 실제로 사라지게하는 부분
                layout2.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

            }
        });
        // Animation start
        va.start();
    }


}

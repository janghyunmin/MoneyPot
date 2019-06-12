package quantec.com.moneypot.Activity.PotDetail;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

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

    }
}

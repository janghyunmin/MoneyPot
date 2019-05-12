package quantec.com.moneypot.Activity.ActivityNotice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import quantec.com.moneypot.Activity.ClientCenter.AdapterNotice;
import quantec.com.moneypot.ModelCommon.dModel.ModelNoticeList;
import quantec.com.moneypot.R;

public class ActivityNotice extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ModelNoticeList> modelNoticeLists;
    AdapterNotice adapterNotice;
    ImageView backBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        backBt = findViewById(R.id.backBt);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);



        modelNoticeLists = new ArrayList<>();
        modelNoticeLists.add(new ModelNoticeList("안드로이드 이하 버전 지원 종료",""));
        modelNoticeLists.add(new ModelNoticeList("머니포트 x 신한금융투자 1차 이벤트",""));
        modelNoticeLists.add(new ModelNoticeList("3월 12일 상품 구매 오류 관련",""));
        modelNoticeLists.add(new ModelNoticeList("[종료] 머니포트 정식 서비스 기념 이벤트",""));
        modelNoticeLists.add(new ModelNoticeList("앱 베타버전 서비스 종료 안내",""));

        adapterNotice = new AdapterNotice(modelNoticeLists, this);
        recyclerView.setAdapter(adapterNotice);

        adapterNotice.setNoticeItemClcick(new AdapterNotice.NoticeItemClcick() {
            @Override
            public void onClick(int position) {
                Toast.makeText(ActivityNotice.this, modelNoticeLists.get(position).getTitle(), Toast.LENGTH_SHORT).show();
            }
        });


        backBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}

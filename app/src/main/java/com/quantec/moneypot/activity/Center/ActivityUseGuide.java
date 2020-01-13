package com.quantec.moneypot.activity.center;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

import com.quantec.moneypot.activity.center.Adapter.AdapterUseGuide;
import com.quantec.moneypot.datamodel.dmodel.ModelUseGuideList;
import com.quantec.moneypot.R;

public class ActivityUseGuide extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<ModelUseGuideList> useGuideLists;
    RecyclerView.LayoutManager layoutManager;
    AdapterUseGuide adapterUseGuide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_guide);


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        useGuideLists = new ArrayList<>();

        useGuideLists.add(new ModelUseGuideList("포트요리 ETF 꿀조합 만들기","ETF를 이렇게 저렇게 조합하면 또 이렇게 저렇게 조합이 되는데 그걸 통해서 수익률을 보고 또 다시 조합해보고 그 조합으로 포트 요리를 완성해서 나만의 꿀 조합을 찾아보자!",0));
        useGuideLists.add(new ModelUseGuideList("내가 만든 투자 전략으로 수익 쏠쏠!","ㅎ해ㅕ54흐ㅔㅈㄷ",0));
        useGuideLists.add(new ModelUseGuideList("내가 직접 만드는 나만의 투자 전략, 포트요리 완전 공…","ㅎ해쟈224ㅗㅎ4ㅈㄷ",0));
        useGuideLists.add(new ModelUseGuideList("구매와 매수, 판매와 매도 무엇이 다를까?","111111111독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐ㅔㅈㄷㅎ해쟈독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐ㅔㅈㄷ\n" +
                "ㅎ해쟈독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐ㅔㅈㄷㅎ해쟈독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐ㅔㅈㄷ\n" +
                "ㅎ해쟈독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐ㅔㅈㄷㅎ해쟈독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐ㅔㅈㄷ\n" +
                "ㅎ해쟈독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐ㅔㅈㄷㅎ해쟈독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐ㅔㅈㄷ\n" +
                "ㅎ해쟈독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐ㅔㅈㄷㅎ해쟈독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐ㅔㅈㄷ\n" +
                "ㅎ해쟈독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐ㅔㅈㄷㅎ해쟈독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐ㅔㅈㄷ\n" +
                "ㅎ해쟈독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐ㅔㅈㄷㅎ해쟈독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐ㅔㅈㄷ\n" +
                "ㅎ해쟈독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐ㅔㅈㄷㅎ해쟈독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐ㅔㅈㄷ\n" +
                "ㅎ해쟈독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐ㅔㅈㄷㅎ해쟈독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐ㅔㅈㄷ\n" +
                "ㅎ해쟈독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐ㅔㅈㄷㅎ해쟈독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐ㅔㅈㄷ\n" +
                "ㅎ해쟈독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐ㅔㅈㄷㅎ해쟈독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐ㅔㅈㄷ\n" +
                "ㅎ해쟈독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐ㅔㅈㄷㅎ해쟈독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐ㅔㅈㄷ\n" +
                "ㅎ해쟈독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐ㅔㅈㄷㅎ해쟈독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐ㅔㅈㄷ\n" +
                "ㅎ해쟈독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐ㅔㅈㄷㅎ해쟈독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐ㅔㅈㄷ\n" +
                "ㅎ해쟈독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐ㅔㅈㄷㅎ해쟈독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐ㅔㅈㄷ\n" +
                "ㅎ해쟈독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐ㅔㅈㄷㅎ해쟈독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐ㅔㅈㄷ\n" +
                "ㅎ해쟈독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐ㅔㅈㄷㅎ해쟈독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐ㅔㅈㄷ\n" +
                "ㅎ해쟈독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐ㅔㅈㄷㅎ해쟈독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐ㅔㅈㄷ\n" +
                "ㅎ해쟈독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐ㅔㅈㄷㅎ해쟈독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐ㅔㅈㄷ\n" +
                "ㅎ해쟈독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐ㅔㅈㄷㅎ해쟈독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐ㅔㅈㄷ\n" +
                "ㅎ해쟈독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐ㅔㅈㄷㅎ해쟈독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐ㅔㅈㄷ\n" +
                "ㅎ해쟈독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐ㅔㅈㄷㅎ해쟈독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐ㅔㅈㄷ\n" +
                "ㅎ해쟈독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐ㅔㅈㄷㅎ해쟈독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐ㅔㅈㄷ\n" +
                "ㅎ해쟈독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐ㅔㅈㄷㅎ해쟈독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐ㅔㅈㄷ\n" +
                "ㅎ해쟈독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐ㅔㅈㄷㅎ해쟈독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐ㅔㅈㄷ\n" +
                "ㅎ해쟈독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐ㅔㅈㄷㅎ해쟈독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐ㅔㅈㄷ\n" +
                "ㅎ해쟈독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐ㅔㅈㄷㅎ해쟈독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐ㅔㅈㄷ\n" +
                "ㅎ해쟈독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐ㅔㅈㄷㅎ해쟈독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐ㅔㅈㄷ\n" +
                "ㅎ해쟈독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐ㅔㅈㄷㅎ해쟈독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐ㅔㅈㄷ\n" +
                "ㅎ해쟈독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐ㅔㅈㄷㅎ해쟈독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐ㅔㅈㄷ\n" +
                "ㅎ해쟈독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐ㅔㅈㄷㅎ해쟈독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐ㅔㅈㄷ\n" +
                "ㅎ해쟈독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐ㅔㅈㄷㅎ해쟈독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐ㅔㅈㄷ\n" +
                "ㅎ해쟈독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐ㅔㅈㄷㅎ해쟈독ㅎ재ㅑ둫주댜해334ㅛ344ㅛ3ㅛㅔ쟏흐22222222",0));
        useGuideLists.add(new ModelUseGuideList("5번","ㅎ해쟈독ㅎ재22222222222222흐ㅔㅈㄷ",0));
        useGuideLists.add(new ModelUseGuideList("6번","ㅎ해쟈444444444444444ㅔㅈㄷ",0));
        useGuideLists.add(new ModelUseGuideList("7번","ㅓㅗㅜ4ㄷ5ㅗ",0));
        useGuideLists.add(new ModelUseGuideList("8번","3333333333",0));
        useGuideLists.add(new ModelUseGuideList("9번","11",0));
        useGuideLists.add(new ModelUseGuideList("10번","34ㅛ34ㅗ",0));
        useGuideLists.add(new ModelUseGuideList("11번","ㄷ곧곧ㄱ",0));
        useGuideLists.add(new ModelUseGuideList("12번","2#^^",0));
        useGuideLists.add(new ModelUseGuideList("13번","^^^^^^^^^^^^^",0));

        adapterUseGuide = new AdapterUseGuide(useGuideLists, ActivityUseGuide.this);
        recyclerView.setAdapter(adapterUseGuide);

    }
}

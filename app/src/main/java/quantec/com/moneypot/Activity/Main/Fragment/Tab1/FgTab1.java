package quantec.com.moneypot.Activity.Main.Fragment.Tab1;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import quantec.com.moneypot.Activity.Main.ActivityMain;
import quantec.com.moneypot.R;
import quantec.com.moneypot.databinding.FgTab1Binding;

public class FgTab1 extends Fragment {

    FgTab1Binding binding;
    private ActivityMain activityMain;

    RecyclerView.LayoutManager layoutManager;
    ArrayList<ModelFitPotList> modelFitPotLists;
    AdapterFitPot adapterFitPot;

    public static boolean more_top10 = false;
    boolean limitedLife = false;

    public FgTab1(){}
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fg_tab1, container, false);

        initializeViews();

        return binding.getRoot();
    }

    private void initializeViews(){
        activityMain = (ActivityMain) getActivity();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof ActivityMain) {
            activityMain = (ActivityMain) context;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(activityMain);

        binding.recyclerView.setLayoutManager(layoutManager);
        modelFitPotLists = new ArrayList<>();

        adapterFitPot = new AdapterFitPot(modelFitPotLists, activityMain);
        binding.recyclerView.setAdapter(adapterFitPot);



        modelFitPotLists.add(0, new ModelFitPotList(false, "","", "", "", "", "", "", "", "", ""));
//        modelFitPotLists.add(0, new ModelFitPotList(true, "http://pizzaplanet.tistory.com/","", "", "", "", "", "", "", "", ""));

        modelFitPotLists.add(new ModelFitPotList(true, "","내집마련", "정말 더 없이 좋은 나만의 인생 라이프", "30", "1000000000",
                "장기플랜", "투자의 고수", "조심조심", "안정형을 추구하는 연금 투자 전략", "44.56%"));
        modelFitPotLists.add(new ModelFitPotList(false, "","", "", "", "", "", "", "", "", ""));

        adapterFitPot.setEmptyTotalPriceClick(new AdapterFitPot.EmptyTotalPriceClick() {
            @Override
            public void onClick(int position) {
//                Intent intent = new Intent()
                Toast.makeText(activityMain, "계좌 개설로 이동됩니다.", Toast.LENGTH_SHORT).show();
            }
        });

        adapterFitPot.setEmptyLifeChallengeClick(new AdapterFitPot.EmptyLifeChallengeClick() {
            @Override
            public void onClick(int position) {
                modelFitPotLists.add(modelFitPotLists.size()-1, new ModelFitPotList(true, "","내집마련"+(modelFitPotLists.size()-1), "정말 더 없이 좋은 나만의 인생 라이프", "30", "1000000000",
                        "장기플랜", "투자의 고수", "조심조심", "안정형을 추구하는 연금 투자 전략", "44.56%"));
                visibleAddLife();
            }
        });

        adapterFitPot.setLifeChallengeClick(new AdapterFitPot.LifeChallengeClick() {
            @Override
            public void onClick(int position) {
                modelFitPotLists.remove(position);
                visibleAddLife();
            }
        });

    }

    private void visibleAddLife(){

        if(modelFitPotLists.size() == 12){

            modelFitPotLists.remove(modelFitPotLists.size()-1);
            adapterFitPot.notifyDataSetChanged();

            limitedLife = true;

        }else{

            if(limitedLife){

                limitedLife = false;

                modelFitPotLists.add(modelFitPotLists.size(), new ModelFitPotList(false, "","", "", "", "", "", "", "", "", ""));
                adapterFitPot.notifyDataSetChanged();

            }else{
                adapterFitPot.notifyDataSetChanged();
            }

        }

    }

}

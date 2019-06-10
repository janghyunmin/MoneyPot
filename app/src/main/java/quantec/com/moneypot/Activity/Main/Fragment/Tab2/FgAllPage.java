package quantec.com.moneypot.Activity.Main.Fragment.Tab2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import java.util.ArrayList;

import quantec.com.moneypot.Activity.Main.ActivityMain;
import quantec.com.moneypot.R;

public class FgAllPage extends Fragment {

    RecyclerView recyclerView1, recyclerView2;
    RecyclerView.LayoutManager layoutManager,layoutManager2;
    ArrayList<ModelLeagueList> modelLeagueLists;
    ArrayList<ModelAdImage> modelAdImages;
    AdapterLeagueList adapterLeagueList;
    AdapterAdImage adapterAdImage;

    SnapHelper snapHelper;

    private ActivityMain activityMain;

    TextView myPotNon, myPotName, myPotTime, myPotYield, myPotBt;
    ConstraintLayout myPot;

    public FgAllPage() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_allpage, container, false);

        initializeViews();

        myPotNon = view.findViewById(R.id.myPotNon);
        myPot = view.findViewById(R.id.myPot);

        myPotBt = view.findViewById(R.id.myPotBt);

        myPotName = view.findViewById(R.id.myPotName);
        myPotTime = view.findViewById(R.id.myPotTime);
        myPotYield = view.findViewById(R.id.myPotYield);

        recyclerView1 = view.findViewById(R.id.recyclerView1);
        recyclerView1.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(activityMain);
        recyclerView1.setLayoutManager(layoutManager);

        modelLeagueLists = new ArrayList<>();
        adapterLeagueList = new AdapterLeagueList(modelLeagueLists, activityMain);
        recyclerView1.setAdapter(adapterLeagueList);

        recyclerView2 = view.findViewById(R.id.recyclerView2);
        recyclerView2.setHasFixedSize(true);

        modelAdImages = new ArrayList<>();


        snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView2);

        layoutManager2 = new LinearLayoutManager(activityMain, RecyclerView.HORIZONTAL, false);
        recyclerView2.setLayoutManager(layoutManager2);

        adapterAdImage = new AdapterAdImage(modelAdImages, activityMain, activityMain);
        recyclerView2.setAdapter(adapterAdImage);

        recyclerView1.setLayoutManager(new LinearLayoutManager(activityMain){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        });

        return view;
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


        modelLeagueLists.add(0, new ModelLeagueList(0, "","","",""));

        adapterLeagueList.notifyDataSetChanged();

        modelAdImages.add(new ModelAdImage("aa"));
        modelAdImages.add(new ModelAdImage("bb"));
        modelAdImages.add(new ModelAdImage("cc"));

        adapterAdImage.notifyDataSetChanged();

        adapterLeagueList.setLeagueEnterBtClick(new AdapterLeagueList.LeagueEnterBtClick() {
            @Override
            public void onClick(int position) {
                Toast.makeText(activityMain, "리그참가합니다.", Toast.LENGTH_SHORT).show();
            }
        });

        adapterLeagueList.setLeagueShowClick(new AdapterLeagueList.LeagueShowClick() {
            @Override
            public void onClick(int position) {
                Toast.makeText(activityMain, "진행중인 리그참가페이지 이동.", Toast.LENGTH_SHORT).show();
            }
        });

        adapterLeagueList.setLeagueRankClick(new AdapterLeagueList.LeagueRankClick() {
            @Override
            public void onClick(int position) {
                Toast.makeText(activityMain, position+"등 랭커 상세보기", Toast.LENGTH_SHORT).show();
            }
        });

        myPotBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activityMain, ActivityPotCook.class);
                startActivity(intent);

            }
        });

        adapterAdImage.setImageClick(new AdapterAdImage.ImageClick() {
            @Override
            public void onClick(int position) {


            }
        });
    }
}

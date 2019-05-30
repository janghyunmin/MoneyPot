package quantec.com.moneypot.Activity.Main.Fragment.FgTab2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.ActivityPotCookBanner;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.ModelAdImage;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab2.expanding.ECBackgroundSwitcherView;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab2.expanding.ECCardData;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab2.expanding.ECPagerView;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab2.expanding.ECPagerViewAdapter;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab2.expanding.simple.CardDataImpl;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab2.expanding.simple.CardListItemAdapter;
import quantec.com.moneypot.Activity.Main.MainActivity;
import quantec.com.moneypot.R;

import static android.util.TypedValue.COMPLEX_UNIT_DIP;

public class FgAllPage extends Fragment {

    RecyclerView recyclerView1, recyclerView2;
    RecyclerView.LayoutManager layoutManager,layoutManager2;
    ArrayList<ModelLeagueList> modelLeagueLists;
    ArrayList<ModelAdImage> modelAdImages;
    AdapterLeagueList adapterLeagueList;
    AdapterAdImage adapterAdImage;

    SnapHelper snapHelper;

    private MainActivity mainActivity;

    TextView myPotNon, myPotName, myPotTime, myPotYield, myPotBt;
    ConstraintLayout myPot;

    private ECPagerView ecPagerView;
    private ECBackgroundSwitcherView ecBackgroundSwitcherView;

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

        layoutManager = new LinearLayoutManager(mainActivity);
        recyclerView1.setLayoutManager(layoutManager);

        modelLeagueLists = new ArrayList<>();
        adapterLeagueList = new AdapterLeagueList(modelLeagueLists, mainActivity);
        recyclerView1.setAdapter(adapterLeagueList);

        recyclerView2 = view.findViewById(R.id.recyclerView2);
        recyclerView2.setHasFixedSize(true);

        modelAdImages = new ArrayList<>();


        snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView2);

        layoutManager2 = new LinearLayoutManager(mainActivity, RecyclerView.HORIZONTAL, false);
        recyclerView2.setLayoutManager(layoutManager2);

        adapterAdImage = new AdapterAdImage(modelAdImages, mainActivity);
        recyclerView2.setAdapter(adapterAdImage);

        recyclerView1.setLayoutManager(new LinearLayoutManager(mainActivity){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        });


        // Get pager from layout
        ecPagerView = view.findViewById(R.id.ec_pager_element);
        ecBackgroundSwitcherView = view.findViewById(R.id.ec_bg_switcher_element);


        return view;
    }

    private void initializeViews(){
        mainActivity = (MainActivity) getActivity();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof MainActivity) {
            mainActivity = (MainActivity) context;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        modelLeagueLists.add(0, new ModelLeagueList(0, "","","",""));
//        modelLeagueLists.add(0, new ModelLeagueList(1, "","","",""));
//        modelLeagueLists.add(1, new ModelLeagueList(1, "똘이아빠님의","안정형 연금 투자 전략","26.03%",""));
//        modelLeagueLists.add(2, new ModelLeagueList(1, "이상근님의","배당형 투자 전략","22.23%",""));
//        modelLeagueLists.add(3, new ModelLeagueList(1, "진혜원님의","여행가자 해외여행가자","12.02%",""));

        adapterLeagueList.notifyDataSetChanged();

        modelAdImages.add(new ModelAdImage(""));
        modelAdImages.add(new ModelAdImage(""));
        modelAdImages.add(new ModelAdImage(""));

        adapterAdImage.notifyDataSetChanged();

        adapterLeagueList.setLeagueEnterBtClick(new AdapterLeagueList.LeagueEnterBtClick() {
            @Override
            public void onClick(int position) {
                Toast.makeText(mainActivity, "리그참가합니다.", Toast.LENGTH_SHORT).show();
            }
        });

        adapterLeagueList.setLeagueShowClick(new AdapterLeagueList.LeagueShowClick() {
            @Override
            public void onClick(int position) {
                Toast.makeText(mainActivity, "진행중인 리그참가페이지 이동.", Toast.LENGTH_SHORT).show();
            }
        });

        adapterLeagueList.setLeagueRankClick(new AdapterLeagueList.LeagueRankClick() {
            @Override
            public void onClick(int position) {
                Toast.makeText(mainActivity, position+"등 랭커 상세보기", Toast.LENGTH_SHORT).show();
            }
        });

        myPotBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mainActivity, ActivityPotCook.class);
                startActivity(intent);

            }
        });


        adapterAdImage.setImageClick(new AdapterAdImage.ImageClick() {
            @Override
            public void onClick(int position) {


            }
        });



        // Generate example dataset
        List<ECCardData> dataset = CardDataImpl.generateExampleData();

        // Implement pager adapter and attach it to pager view
        ECPagerViewAdapter ecPagerViewAdapter = new ECPagerViewAdapter(mainActivity, dataset) {
            @Override
            public void instantiateCard(LayoutInflater inflaterService, ViewGroup head, final ListView list, ECCardData data) {
                // Data object for current card
                CardDataImpl cardData = (CardDataImpl) data;

                // Set adapter and items to current card content list
                final List<String> listItems = cardData.getListItems();
                final CardListItemAdapter listItemAdapter = new CardListItemAdapter(mainActivity, listItems);
                list.setAdapter(listItemAdapter);
                // Also some visual tuning can be done here
                list.setBackgroundColor(Color.WHITE);

                // Here we can create elements for head view or inflate layout from xml using inflater service
                TextView cardTitle = new TextView(mainActivity);
                cardTitle.setText(cardData.getCardTitle());
                cardTitle.setTextSize(COMPLEX_UNIT_DIP, 20);
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.gravity = Gravity.CENTER;
                head.addView(cardTitle, layoutParams);

                // Card toggling by click on head element
                head.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        ecPagerView.toggle();
                    }
                });
            }
        };
        ecPagerView.setPagerViewAdapter(ecPagerViewAdapter);

        // Add background switcher to pager view
        ecPagerView.setBackgroundSwitcherView(ecBackgroundSwitcherView);

        // Directly modifying dataset
        dataset.remove(2);
        ecPagerViewAdapter.notifyDataSetChanged();


    }

}

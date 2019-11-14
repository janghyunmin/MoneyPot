package com.quantec.moneypot.activity.Main.Fragment.Tab3;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.quantec.moneypot.R;
import com.quantec.moneypot.activity.Main.ActivityMain;
import com.quantec.moneypot.activity.Main.Fragment.Tab2.fragment.ActivitySimulationSearch;
import com.quantec.moneypot.activity.Main.Fragment.Tab3.adapter.AdapterFgTab3;
import com.quantec.moneypot.activity.Main.Fragment.Tab3.adapter.AdapterSelectedItem;
import com.quantec.moneypot.activity.PotDetail.DecorationItemHorizontal;
import java.util.ArrayList;

public class FgTab3 extends Fragment {

    private ActivityMain activityMain;


    FloatingSearchView floatingSearchView;
    View searchBt;

    ConstraintLayout itemEmptyLayout, itemLayout;
    ImageView userBt;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ModelFgTab3Follow> modelFgTab3Follows;
    AdapterFgTab3 adapterFgTab3;


    RecyclerView recyclerViewItem;
    RecyclerView.LayoutManager layoutManager2;
    ArrayList<ModelSelectItem> modelSelectItems;
    AdapterSelectedItem adapterSelectedItem;

    SnapHelper snapHelper;

    ArrayList<String> codeList;
    private int count = 0;

    public FgTab3() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_tab3, container, false);

        initializeViews();

        floatingSearchView = view.findViewById(R.id.floating_search_view);
        floatingSearchView.setEnabled(false);

        searchBt = view.findViewById(R.id.searchBt);

        itemEmptyLayout = view.findViewById(R.id.itemEmptyLayout);
        itemLayout = view.findViewById(R.id.itemLayout);
        itemLayout.setVisibility(View.GONE);

        userBt = view.findViewById(R.id.userBt);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(activityMain);
        recyclerView.setLayoutManager(layoutManager);

        modelFgTab3Follows = new ArrayList<>();
        modelFgTab3Follows.add(new ModelFgTab3Follow("", "", 0, 0,false, false));
        adapterFgTab3 = new AdapterFgTab3(modelFgTab3Follows, activityMain);
        recyclerView.setAdapter(adapterFgTab3);

        recyclerViewItem = view.findViewById(R.id.recyclerViewItem);
        recyclerViewItem.setHasFixedSize(true);

        layoutManager2 = new LinearLayoutManager(activityMain, RecyclerView.HORIZONTAL, false);
        recyclerViewItem.setLayoutManager(layoutManager2);


        recyclerViewItem.addItemDecoration(new DecorationItemHorizontal(activityMain, 10));

        modelSelectItems = new ArrayList<>();

        snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerViewItem);

        adapterSelectedItem = new AdapterSelectedItem(modelSelectItems, activityMain);
        recyclerViewItem.setAdapter(adapterSelectedItem);

        codeList = new ArrayList<>();

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

        //스테이터스 바 색상 변경 -> 화이트
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Window w = activityMain.getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            Window window = activityMain.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.main_page_status_bar_color));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        searchBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activityMain, ActivitySimulationSearch.class);
// Pass data object in the bundle and populate details activity.
//                intent.putExtra(ActivitySimulationSearch.EXTRA_CONTACT, contact);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(activityMain, (View)floatingSearchView, "searchView");
                startActivity(intent, options.toBundle());
            }
        });

        floatingSearchView.setQueryTextSize(12);
        floatingSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
//                floatingSearchView.swapSuggestions();
            }
        });

        modelFgTab3Follows.add(new ModelFgTab3Follow("나이키", "NKE", 28.90, 0,false, false));
        modelFgTab3Follows.add(new ModelFgTab3Follow("제이피모건", "JPM", -2.16, 0,false, false));
        modelFgTab3Follows.add(new ModelFgTab3Follow("세계적인 대세 IT 기업들 모음", "묶음기업", 6.11, 1,false, false));
        modelFgTab3Follows.add(new ModelFgTab3Follow("나이키", "NKE", 28.90, 0,false, false));
        modelFgTab3Follows.add(new ModelFgTab3Follow("제이피모건", "JPM", -2.16, 0,false, false));
        modelFgTab3Follows.add(new ModelFgTab3Follow("세계적인 대세 IT 기업들 모음", "묶음기업", 6.11, 1,false, false));

        adapterFgTab3.setFollowAddClick(new AdapterFgTab3.FollowAddClick() {
            @Override
            public void onClick(int position, String title, String code, double rate, int ent) {
                if(modelFgTab3Follows.get(position).getGubun() == 0){
                    Log.e("받은값0", "값 : "+code);

                    if(count == 0){
                        itemEmptyLayout.setVisibility(View.GONE);
                        itemLayout.setVisibility(View.VISIBLE);
                    }

                    if(ent == 1){

                        if(count < 10){

                            count++;

                            if(count == 1){
                                modelSelectItems.add(new ModelSelectItem(title, code, rate, false));
                                codeList.add(code);
                                modelSelectItems.add(new ModelSelectItem("", "", 0, true));
                                modelSelectItems.add(new ModelSelectItem("", "", 0, true));
                            }else if(count == 2){
                                modelSelectItems.set(1, new ModelSelectItem(title, code,
                                        rate,false));
                                adapterSelectedItem.notifyItemChanged(1);
                                codeList.add(code);
                            }else if(count == 3){
                                modelSelectItems.set(2, new ModelSelectItem(title, code,
                                        rate,false));
                                codeList.add(code);
                            }else{
                                modelSelectItems.add(new ModelSelectItem(title, code,
                                        rate, false));
                                codeList.add(code);
                            }

//                            codeList.add(modelSelectItems.get(position).getCode());
                            adapterSelectedItem.notifyDataSetChanged();

                        }else{
                            Toast.makeText(activityMain, "더이상 추가할 수 없습니다.", Toast.LENGTH_SHORT).show();
                        }

                    }else{

                        if(count < 10){

                            count++;

                            if(count == 1){
                                modelSelectItems.add(new ModelSelectItem(modelFgTab3Follows.get(position).getTitle(), modelFgTab3Follows.get(position).getCode(), modelFgTab3Follows.get(position).getRate(), false));
                                codeList.add(modelFgTab3Follows.get(position).getCode());
                                modelSelectItems.add(new ModelSelectItem("", "", 0, true));
                                modelSelectItems.add(new ModelSelectItem("", "", 0, true));
                            }else if(count == 2){
                                modelSelectItems.set(1, new ModelSelectItem(modelFgTab3Follows.get(position).getTitle(), modelFgTab3Follows.get(position).getCode(),
                                        modelFgTab3Follows.get(position).getRate(),false)).setTitle(modelFgTab3Follows.get(position).getTitle());
                                codeList.add(modelFgTab3Follows.get(position).getCode());
                            }else if(count == 3){
                                modelSelectItems.set(2, new ModelSelectItem(modelFgTab3Follows.get(position).getTitle(), modelFgTab3Follows.get(position).getCode(),
                                        modelFgTab3Follows.get(position).getRate(),false)).setTitle(modelFgTab3Follows.get(position).getTitle());
                                codeList.add(modelFgTab3Follows.get(position).getCode());
                            }else{
                                modelSelectItems.add(new ModelSelectItem(modelFgTab3Follows.get(position).getTitle(), modelFgTab3Follows.get(position).getCode(), modelFgTab3Follows.get(position).getRate(), false));
                                codeList.add(modelFgTab3Follows.get(position).getCode());
                            }

//                            codeList.add(modelSelectItems.get(position).getCode());
                            adapterSelectedItem.notifyDataSetChanged();

                        }else{
                            Toast.makeText(activityMain, "더이상 추가할 수 없습니다.", Toast.LENGTH_SHORT).show();
                        }

                    }

                }else{
                    Log.e("받은값1", "값 : "+code+"  코드 : "+ent);

                    if(ent == 0){
                        if(modelFgTab3Follows.get(position).isOpen()){
                            modelFgTab3Follows.get(position).setOpen(false);
                        }else{
                            modelFgTab3Follows.get(position).setOpen(true);
                        }
                        adapterFgTab3.notifyDataSetChanged();
                    }else{

                        if(count == 0){
                            itemEmptyLayout.setVisibility(View.GONE);
                            itemLayout.setVisibility(View.VISIBLE);
                        }

                        if(count < 10){

                            count++;

                            if(count == 1){
                                modelSelectItems.add(new ModelSelectItem(title, code, rate, false));
                                codeList.add(code);
                                modelSelectItems.add(new ModelSelectItem("", "", 0, true));
                                modelSelectItems.add(new ModelSelectItem("", "", 0, true));
                            }else if(count == 2){
                                modelSelectItems.set(1, new ModelSelectItem(title, code,
                                        rate,false));
                                codeList.add(code);
                            }else if(count == 3){
                                modelSelectItems.set(2, new ModelSelectItem(title, code,
                                        rate,false));
                                codeList.add(code);
                            }else{
                                modelSelectItems.add(new ModelSelectItem(title, code,
                                        rate, false));
                                codeList.add(code);
                            }

//                            codeList.add(modelSelectItems.get(position).getCode());
                            adapterSelectedItem.notifyDataSetChanged();

                        }else{
                            Toast.makeText(activityMain, "더이상 추가할 수 없습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

//        adapterFgTab3.setFollowAddClick(new AdapterFgTab3.FollowAddClick() {
//            @Override
//            public void onClick(int position) {
//
//                if(modelFgTab3Follows.get(position).getGubun() == 0){
//
//                }else{
//
//                }
//
//
//                if(count == 0){
//                    itemEmptyLayout.setVisibility(View.GONE);
//                    itemLayout.setVisibility(View.VISIBLE);
//                }
//                if(count < 10){
//
//                    count++;
//
//                    if(count == 1){
//                        modelSelectItems.add(new ModelSelectItem(modelFgTab3Follows.get(position).getTitle(), modelFgTab3Follows.get(position).getCode(), modelFgTab3Follows.get(position).getRate(), false));
//                        modelSelectItems.add(new ModelSelectItem("", "", 0, true));
//                        modelSelectItems.add(new ModelSelectItem("", "", 0, true));
//                    }else if(count == 2){
//                        modelSelectItems.set(1, new ModelSelectItem(modelSelectItems.get(position).getTitle(), modelSelectItems.get(position).getCode(),
//                                modelSelectItems.get(position).getRate(),false)).setTitle(modelSelectItems.get(position).getTitle());
//                    }else if(count == 3){
//                        modelSelectItems.set(2, new ModelSelectItem(modelSelectItems.get(position).getTitle(), modelSelectItems.get(position).getCode(),
//                                modelSelectItems.get(position).getRate(),false)).setTitle(modelSelectItems.get(position).getTitle());
//                    }else{
//                        modelSelectItems.add(new ModelSelectItem(modelFgTab3Follows.get(position).getTitle(), modelFgTab3Follows.get(position).getCode(), modelFgTab3Follows.get(position).getRate(), false));
//                    }
//
//                    codeList.add(modelSelectItems.get(position).getCode());
//                    adapterSelectedItem.notifyDataSetChanged();
//
//                }else{
//                    Toast.makeText(activityMain, "더이상 추가할 수 없습니다.", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        adapterFgTab3.setFollowItemClick(new AdapterFgTab3.FollowItemClick() {
            @Override
            public void onClick(int position) {

            }
        });


        adapterSelectedItem.setSelectedDeleteClick(new AdapterSelectedItem.SelectedDeleteClick() {
            @Override
            public void onclick(int position) {

            }
        });

        adapterSelectedItem.setSelectedItemClick(new AdapterSelectedItem.SelectedItemClick() {
            @Override
            public void onClick(int position) {

            }
        });

    }
}

package com.quantec.moneypot.activity.Main.Fragment.Tab1.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.quantec.moneypot.R;
import com.quantec.moneypot.activity.Main.Fragment.Tab1.Activity.ActivityYieldChart;
import com.quantec.moneypot.activity.Main.Fragment.Tab1.Activity.AdapterSnpTab;
import com.quantec.moneypot.activity.Main.Fragment.Tab1.Activity.ModelSnpTab;
import com.quantec.moneypot.dialog.DialogYieldFilter;
import com.quantec.moneypot.util.FomatToWon.MoneyFormatToWon;
import com.quantec.moneypot.util.view.refresh.Footer.LoadingView;
import com.quantec.moneypot.util.view.refresh.RefreshListenerAdapter;
import com.quantec.moneypot.util.view.refresh.TwinklingRefreshLayout;
import com.quantec.moneypot.util.view.refresh.header.SinaRefreshView;

import java.util.ArrayList;

public class FgSnpTab extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ModelSnpTab> modelSnpTabs;
    AdapterSnpTab adapterSnpTab;

    TwinklingRefreshLayout refresh;
    ActivityYieldChart activityYieldChart;

    DialogYieldFilter dialogYieldFilter;

    String number = "4";

    public FgSnpTab() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_yieldchart_snptab, container, false);

        initializeViews();

        refresh = view.findViewById(R.id.refresh);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(activityYieldChart);
        recyclerView.setLayoutManager(layoutManager);

        modelSnpTabs = new ArrayList<>();
        adapterSnpTab = new AdapterSnpTab(modelSnpTabs, activityYieldChart);
        recyclerView.setAdapter(adapterSnpTab);

        return view;
    }

    private void initializeViews() {
        activityYieldChart = (ActivityYieldChart) getActivity();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ActivityYieldChart) {
            activityYieldChart = (ActivityYieldChart) context;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        modelSnpTabs.add(new ModelSnpTab("누적 수익률", "", "", 0, MoneyFormatToWon.moneyFormatToWon(659483), 0));
        modelSnpTabs.add(new ModelSnpTab("", "스타벅스", "AA", 11.04, MoneyFormatToWon.moneyFormatToWon(659483), 0));
        modelSnpTabs.add(new ModelSnpTab("", "스타벅스", "AA", -1.76, MoneyFormatToWon.moneyFormatToWon(659483), 0));
        modelSnpTabs.add(new ModelSnpTab("", "스타벅스", "AA", -6.31, MoneyFormatToWon.moneyFormatToWon(659483), 0));
        modelSnpTabs.add(new ModelSnpTab("", "스타벅스", "AA", 61.11, MoneyFormatToWon.moneyFormatToWon(15000), 0));
        modelSnpTabs.add(new ModelSnpTab("", "스타벅스", "AA", 1, MoneyFormatToWon.moneyFormatToWon(659483), 0));
        modelSnpTabs.add(new ModelSnpTab("", "스타벅스", "AA", 1, MoneyFormatToWon.moneyFormatToWon(659483), 0));
        modelSnpTabs.add(new ModelSnpTab("", "스타벅스", "AA", 1, MoneyFormatToWon.moneyFormatToWon(659483), 0));
        modelSnpTabs.add(new ModelSnpTab("", "스타벅스", "AA", 1, MoneyFormatToWon.moneyFormatToWon(659483), 0));
        modelSnpTabs.add(new ModelSnpTab("", "스타벅스", "AA", 1, MoneyFormatToWon.moneyFormatToWon(659483), 0));
        modelSnpTabs.add(new ModelSnpTab("", "스타벅스", "AA", 1, MoneyFormatToWon.moneyFormatToWon(659483), 0));
        modelSnpTabs.add(new ModelSnpTab("", "스타벅스", "AA", 1, MoneyFormatToWon.moneyFormatToWon(659483), 0));
        modelSnpTabs.add(new ModelSnpTab("", "스타벅스", "AA", 1, MoneyFormatToWon.moneyFormatToWon(659483), 0));
        modelSnpTabs.add(new ModelSnpTab("", "스타벅스", "AA", 1, MoneyFormatToWon.moneyFormatToWon(659483), 0));
        modelSnpTabs.add(new ModelSnpTab("", "스타벅스", "AA", 1, MoneyFormatToWon.moneyFormatToWon(659483), 0));
        modelSnpTabs.add(new ModelSnpTab("", "스타벅스", "AA", 1, MoneyFormatToWon.moneyFormatToWon(659483), 0));

        SinaRefreshView headerView = new SinaRefreshView(activityYieldChart);
        headerView.setArrowResource(R.drawable.anim_loading_view);
        headerView.setTextColor(0xff745D5C);
        refresh.setHeaderView(headerView);

        LoadingView loadingView = new LoadingView(activityYieldChart);
        refresh.setBottomView(loadingView);
        refresh.setOnRefreshListener(new RefreshListenerAdapter(){
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishRefreshing();
                        Toast.makeText(activityYieldChart.getApplicationContext(), "새로고침 완료.", Toast.LENGTH_SHORT).show();
                    }
                },1500);
            }
            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activityYieldChart.getApplicationContext(), "바텀입니다.", Toast.LENGTH_SHORT).show();
                        refreshLayout.finishLoadmore();
                    }
                },1500);
            }
        });

        adapterSnpTab.setSingleTabFilterClcick(new AdapterSnpTab.SingleTabFilterClcick() {
            @Override
            public void onClcick(int position) {
                dialogYieldFilter = new DialogYieldFilter(activityYieldChart, number, m1BtListener, m3BtListener, m6BtListener, mAllBtListener, closeListener);
                dialogYieldFilter.show();
            }
        });

        adapterSnpTab.setSingleTabFollowClick(new AdapterSnpTab.SingleTabFollowClick() {
            @Override
            public void onClick(int position) {
                if(modelSnpTabs.get(position).getFollow() == 0){
                    modelSnpTabs.get(position).setFollow(1);
                }else{
                    modelSnpTabs.get(position).setFollow(0);
                }
                adapterSnpTab.notifyItemChanged(position);
            }
        });
    }

    private View.OnClickListener m1BtListener = new View.OnClickListener() {
        public void onClick(View v) {
            modelSnpTabs.get(0).setFilter("1개월 수익률");
            number = "1";
            dialogYieldFilter.dismiss();

            adapterSnpTab.notifyItemChanged(0);
        }
    };
    private View.OnClickListener m3BtListener = new View.OnClickListener() {
        public void onClick(View v) {
            modelSnpTabs.get(0).setFilter("3개월 수익률");
            number = "2";
            dialogYieldFilter.dismiss();

            adapterSnpTab.notifyItemChanged(0);
        }
    };
    private View.OnClickListener m6BtListener = new View.OnClickListener() {
        public void onClick(View v) {
            modelSnpTabs.get(0).setFilter("6개월 수익률");
            number = "3";
            dialogYieldFilter.dismiss();

            adapterSnpTab.notifyItemChanged(0);
        }
    };
    private View.OnClickListener mAllBtListener = new View.OnClickListener() {
        public void onClick(View v) {
            modelSnpTabs.get(0).setFilter("누적 수익률");
            number = "4";
            dialogYieldFilter.dismiss();

            adapterSnpTab.notifyItemChanged(0);
        }
    };

    private View.OnClickListener closeListener = new View.OnClickListener() {
        public void onClick(View v) {
            dialogYieldFilter.dismiss();
        }
    };

}

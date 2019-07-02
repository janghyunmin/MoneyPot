package quantec.com.moneypot.Activity.Main.Fragment.Tab4;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.List;

import quantec.com.moneypot.Activity.Main.ActivityMain;
import quantec.com.moneypot.DataModel.dModel.ModelFgTab4;
import quantec.com.moneypot.R;
import quantec.com.moneypot.databinding.FgTab4Binding;
import quantec.com.moneypot.util.view.refresh.Footer.LoadingView;
import quantec.com.moneypot.util.view.refresh.RefreshListenerAdapter;
import quantec.com.moneypot.util.view.refresh.TwinklingRefreshLayout;
import quantec.com.moneypot.util.view.refresh.header.SinaRefreshView;

public class FgTab4 extends Fragment {

    FgTab4Binding tab4Binding;
    //차트
    List<Entry> entries;
    LineDataSet lineDataSet;
    LineData lineData;

    RecyclerView.LayoutManager mLayoutManager;
    AdapterFgTab4 myAdapter;
    ArrayList<ModelFgTab4> myData;

    private ActivityMain activityMain;

    String packName;

    public FgTab4() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        tab4Binding = DataBindingUtil.inflate(inflater, R.layout.fg_tab4, container, false);

        initializeViews();

        tab4Binding.recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        tab4Binding.recyclerView.setLayoutManager(mLayoutManager);
        myData = new ArrayList<>();

        entries = new ArrayList<>();
        lineDataSet = new LineDataSet(entries, null);
        lineData = new LineData(lineDataSet);
        myAdapter = new AdapterFgTab4(getContext(),myData, entries, lineDataSet, lineData);
        tab4Binding.recyclerView.setAdapter(myAdapter);

        packName = getActivity().getPackageName();


        return tab4Binding.getRoot();
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

        myData.add(new ModelFgTab4("스스로 준비하는 연금","222",26.58,true,true,"",true,0L));
        myData.add(new ModelFgTab4("안정적인 자산관리를 위해","222",16.24,true,true,"",true,0L));
        myData.add(new ModelFgTab4("투자 고수의 투자법","222",16.24,true,true,"",true,0L));
        myData.add(new ModelFgTab4("스스로 준비하는 연금","222",-26.58,true,true,"",true,0L));
        myData.add(new ModelFgTab4("안정적인 자산관리를 위해","222",16.24,true,true,"",true,0L));
        myData.add(new ModelFgTab4("투자 고수의 투자법","222",-16.24,true,true,"",true,0L));
        myData.add(new ModelFgTab4("스스로 준비하는 연금","222",26.58,true,true,"",true,0L));
        myData.add(new ModelFgTab4("안정적인 자산관리를 위해","222",16.24,true,true,"",true,0L));
        myData.add(new ModelFgTab4("투자 고수의 투자법","222",16.24,true,true,"",true,0L));
        myData.add(new ModelFgTab4("스스로 준비하는 연금","222",-26.58,true,true,"",true,0L));
        myData.add(new ModelFgTab4("안정적인 자산관리를 위해","222",16.24,true,true,"",true,0L));
        myData.add(new ModelFgTab4("투자 고수의 투자법","222",-16.24,true,true,"",true,0L));
        myData.add(new ModelFgTab4("스스로 준비하는 연금","222",26.58,true,true,"",true,0L));
        myData.add(new ModelFgTab4("안정적인 자산관리를 위해","222",16.24,true,true,"",true,0L));
        myData.add(new ModelFgTab4("투자 고수의 투자법","222",16.24,true,true,"",true,0L));



        SinaRefreshView headerView = new SinaRefreshView(activityMain);
        headerView.setArrowResource(R.drawable.anim_loading_view);
        headerView.setTextColor(0xff745D5C);
        tab4Binding.refresh.setHeaderView(headerView);

        LoadingView loadingView = new LoadingView(activityMain);
        tab4Binding.refresh.setBottomView(loadingView);

        tab4Binding.refresh.setOnRefreshListener(new RefreshListenerAdapter(){
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishRefreshing();

                        BottomSheetDialog bottomSheetDialog = BottomSheetDialog.newInstance();
                        bottomSheetDialog.show(activityMain.getSupportFragmentManager(),"Bottom Sheet Dialog Fragment");

                    }
                },2000);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishLoadmore();
                    }
                },2000);
            }
        });



    }

}

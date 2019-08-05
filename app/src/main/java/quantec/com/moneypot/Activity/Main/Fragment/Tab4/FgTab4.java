package quantec.com.moneypot.Activity.Main.Fragment.Tab4;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Toast;

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
import quantec.com.moneypot.Activity.PotDetail.ActivityPotDetail;
import quantec.com.moneypot.DataModel.dModel.Filter;
import quantec.com.moneypot.DataModel.dModel.ModelFgTab4;
import quantec.com.moneypot.DataModel.dModel.Select;
import quantec.com.moneypot.DataModel.nModel.ModelZimData;
import quantec.com.moneypot.DataModel.nModel.ModelZimPotList;
import quantec.com.moneypot.Network.Retrofit.RetrofitClient;
import quantec.com.moneypot.R;
import quantec.com.moneypot.databinding.FgTab4Binding;
import quantec.com.moneypot.util.DecimalScale.DecimalScale;
import quantec.com.moneypot.util.view.refresh.Footer.LoadingView;
import quantec.com.moneypot.util.view.refresh.RefreshListenerAdapter;
import quantec.com.moneypot.util.view.refresh.TwinklingRefreshLayout;
import quantec.com.moneypot.util.view.refresh.header.SinaRefreshView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        //찜 데이터 리스트
        initZimPotList();

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
                        Toast.makeText(activityMain, "탑입니다.", Toast.LENGTH_SHORT).show();
                        initZimPotList();
//                        BottomSheetDialog bottomSheetDialog = BottomSheetDialog.newInstance();
//                        bottomSheetDialog.show(activityMain.getSupportFragmentManager(),"Bottom Sheet Dialog Fragment");

                    }
                },2000);
            }
            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activityMain, "바텀입니다.", Toast.LENGTH_SHORT).show();
                        refreshLayout.finishLoadmore();
                    }
                },2000);
            }
        });

        //팟 찜 클릭
        myAdapter.setSelectZzimClick(new AdapterFgTab4.SelectZzimClick() {
            @Override
            public void onClick(int position) {

                if(myData.get(position).isZim()){
                    setZimPot(myData.get(position).getCode(), false, "del", position);
                }else{
                    setZimPot(myData.get(position).getCode(), true, "add", position);
                }
            }
        });

        //팟 아이템 클릭
        myAdapter.setItemClick(new AdapterFgTab4.ItemClick() {
            @Override
            public void onClick(int position) {
                Intent intent1 = new Intent(activityMain, ActivityPotDetail.class);
                intent1.putExtra("potCode", myData.get(position).getCode());
                startActivity(intent1);
            }
        });

        //팟 팔로우 클릭
        myAdapter.setPotFollowBtClick(new AdapterFgTab4.PotFollowBtClick() {
            @Override
            public void onClick(int position) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog();
                bottomSheetDialog.newInstance(myData.get(position).getTitle(), myData.get(position).getRate());
                bottomSheetDialog.show(activityMain.getSupportFragmentManager(),"Bottom Sheet Dialog Fragment");
            }
        });

    }//onViewCreate 끝

    void setZimPot(String potCode, boolean zim, String mode, int position){

        Select select = new Select(potCode, false, zim);
        Call<ModelZimData> getSelectPort = RetrofitClient.getInstance().getService().getSelectedPortDate("application/json", select, 1, mode);
        getSelectPort.enqueue(new Callback<ModelZimData>() {
            @Override
            public void onResponse(Call<ModelZimData> call, Response<ModelZimData> response) {
                if (response.code() == 200) {

                    if(zim){
                        myData.get(position).setZim(true);
                    }else{
                        myData.get(position).setZim(false);
                    }
                    myAdapter.notifyItemChanged(position);
                }
            }

            @Override
            public void onFailure(Call<ModelZimData> call, Throwable t) {
                Toast.makeText(getActivity(), "네트워크가 불안정 합니다\n 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void initZimPotList(){

        Filter filter = new Filter();
        Call<ModelZimPotList> getReList = RetrofitClient.getInstance().getService().getZimPotList("application/json", filter, "Z", 0, 0,10);
        getReList.enqueue(new Callback<ModelZimPotList>() {
            @Override
            public void onResponse(Call<ModelZimPotList> call, Response<ModelZimPotList> response) {
                if (response.code() == 200) {

                    myData.clear();

                    if(response.body().getTotalElements() == 0){
                        myData.add(new ModelFgTab4(true, "", "", 0, false, false, "", true, 0L));
                    }
                    else{
                        for(int index = 0 ; index < response.body().getContent().size() ; index++){
                            myData.add(new ModelFgTab4(false,
                                    response.body().getContent().get(index).getName(),
                                    response.body().getContent().get(index).getCode(),
                                    DecimalScale.decimalScale(String.valueOf(response.body().getContent().get(index).getRate()*100), 2, 2),
                                    response.body().getContent().get(index).getIsZim(),
                                    false,
                                    "",
                                    true,
                                    0L));
                        }
                    }
                    myAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<ModelZimPotList> call, Throwable t) {
                Log.e("에러값","값 : "+t.getMessage());
            }
        });
    }
}

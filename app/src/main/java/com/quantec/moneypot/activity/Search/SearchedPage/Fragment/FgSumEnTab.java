package com.quantec.moneypot.activity.Search.SearchedPage.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.quantec.moneypot.activity.PotDetail.ActivitySingleDetail;
import com.quantec.moneypot.activity.Search.ActivitySearch;
import com.quantec.moneypot.activity.Search.SearchedPage.Adapter.AdapterDescTab;
import com.quantec.moneypot.activity.Search.SearchedPage.Adapter.AdapterSumEn;
import com.quantec.moneypot.activity.Search.SearchedPage.ModelSumEn;
import com.quantec.moneypot.datamanager.DataManager;
import com.quantec.moneypot.datamodel.dmodel.ModelPostDescItem;
import com.quantec.moneypot.database.room.entry.RoomEntity;
import com.quantec.moneypot.database.room.local.RoomDao;
import com.quantec.moneypot.database.room.local.SearchRoomDatabase;
import com.quantec.moneypot.database.room.viewmodel.SearchViewModel;
import com.quantec.moneypot.R;
import com.quantec.moneypot.network.retrofit.RetrofitClient;
import com.quantec.moneypot.rxandroid.RxEvent;
import com.quantec.moneypot.rxandroid.RxEventBus;
import com.quantec.moneypot.util.SharedPreference.SharedPreferenceUtil;
import com.quantec.moneypot.datamodel.dmodel.ModelUserSelectDto;
import com.quantec.moneypot.datamodel.dmodel.userselectdto.Select;

public class FgSumEnTab extends Fragment {

    ActivitySearch activitySearch;

    ArrayList<ModelPostDescItem> postDescItemModels;
    RecyclerView recyclerView;
//    AdapterDescTab adapterDescPage;
    AdapterSumEn adapterSumEn;
    LinearLayoutManager linearLayoutManager;

    Bundle bundle, followInfo;
    Toast toastZzimLimit;

    private SearchViewModel searchViewModel;

    private RoomDao roomDao;
    private RoomEntity RoomSelectCode;
    private List<RoomEntity> RoomAllData;

    ArrayList<ModelSumEn> modelSumEn;

    public FgSumEnTab() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_desctab, container, false);

        //뷰 초기화
        initializeViews();

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        modelSumEn = new ArrayList<>();


        adapterSumEn = new AdapterSumEn(modelSumEn, getContext());
        recyclerView.setAdapter(adapterSumEn);

        bundle = getArguments();
        followInfo = new Bundle();

        searchViewModel = ViewModelProviders.of(getActivity()).get(SearchViewModel.class);

        return view;
    }

    private void initializeViews(){
        activitySearch = (ActivitySearch) getActivity();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof ActivitySearch) {
            activitySearch = (ActivitySearch) context;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        modelSumEn.addAll(bundle.getParcelableArrayList("sumEn"));

        adapterSumEn.setSumEnFollowClick(new AdapterSumEn.SumEnFollowClick() {
            @Override
            public void onClick(int position) {
                if(modelSumEn.get(position).getFollow() == 0){
                    FollowClick(1, position);
                }else{
                    FollowClick(0, position);
                }
            }
        });

        adapterSumEn.setSumEnItemClick(new AdapterSumEn.SumEnItemClick() {
            @Override
            public void onClick(int position) {
                MovedDetailPage(modelSumEn.get(position).getCode(), modelSumEn.get(position).getTitle(), 600);
            }
        });


        RxEventBus.getInstance()
                .filteredObservable(RxEvent.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RxEvent>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(RxEvent rxEvent) {

                        switch (rxEvent.getActiion()) {

                            case RxEvent.SEARCH_CLICK_ZZIM:
                                String code = rxEvent.getBundle().getString("search_code");
                                int page = rxEvent.getBundle().getInt("search_page");
                                int type = rxEvent.getBundle().getInt("search_type");

                                Log.e("1번","1번");
                                if(page == 0){
                                    Log.e("2번","2번");

                                    if(type == 1){
                                        Log.e("3번","3번");
                                        if (rxEvent.getBundle().getBoolean("search_follow")) {
                                            Log.e("4번","4번");
                                            for (int a = 0; a < modelSumEn.size(); a++) {
                                                Log.e("찜코드","값 : "+code + " | 가진코드 : "+modelSumEn.get(a).getCode());

                                                if (modelSumEn.get(a).getCode().equals(code)) {
                                                    Log.e("5번","5번");
                                                    modelSumEn.get(a).setFollow(1);
                                                    adapterSumEn.notifyItemChanged(a);
                                                    break;
                                                }
                                            }
                                        }
                                        //찜 헤제됨
                                        else {
                                            for (int a = 0; a < modelSumEn.size(); a++) {
                                                if (modelSumEn.get(a).getCode().equals(code)) {
                                                    modelSumEn.get(a).setFollow(0);
                                                    adapterSumEn.notifyItemChanged(a);
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });


    }//onViewCreated 끝


    void FollowClick(int follow, int position){

//      SharedPreferenceUtil.getInstance(activitySearch).putIntZzimCount("PortZzimCount", response.body().getNum());
        DataManager.get_INstance().setCheckTab1(true);
        DataManager.get_INstance().setCheckTab2(true);
        DataManager.get_INstance().setCheckHome(true);

        followInfo.putInt("search_type", 1);
        followInfo.putInt("search_page", 1);
        followInfo.putString("search_code", modelSumEn.get(position).getCode());

        List<Select> selects = new ArrayList<>();
        Select select = new Select();
        select.setIsDam(0);
        select.setIsLike(0);
        select.setIsZim(0);

        select.setCode(modelSumEn.get(position).getCode());
        select.setIsFollow(follow);
        select.setType(1);
        selects.add(select);

        ModelUserSelectDto modelUserSelectDto = new ModelUserSelectDto();
        modelUserSelectDto.setSelects(selects);

        Call<Object> getReList = RetrofitClient.getInstance().getService().setUserSelect("application/json", "follow", modelUserSelectDto);
        getReList.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.code() == 200) {
                    if(follow == 0){
                        modelSumEn.get(position).setFollow(0);
                        followInfo.putBoolean("search_follow", false);

                        Toast.makeText(activitySearch, "팔로우 취소", Toast.LENGTH_SHORT).show();
                        roomDao = SearchRoomDatabase.getINSTANCE(getContext()).roomDao();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                roomDao.updateData(0, modelSumEn.get(position).getCode());
                            }
                        }).start();

                    }else{
                        modelSumEn.get(position).setFollow(1);
                        followInfo.putBoolean("search_follow", true);

                        Toast.makeText(activitySearch, "팔로우", Toast.LENGTH_SHORT).show();
                        roomDao = SearchRoomDatabase.getINSTANCE(getContext()).roomDao();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                roomDao.updateData(1, modelSumEn.get(position).getCode());
                            }
                        }).start();
                    }
                    RxEventBus.getInstance().post(new RxEvent(RxEvent.SEARCH_CLICK_ZZIM, followInfo));
                    adapterSumEn.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("실패","실패"+t.getMessage());
            }
        });
    }

    void MovedDetailPage(String portCode, String portName, int requestCode){

        Intent intent = new Intent(getActivity(), ActivitySingleDetail.class);
        intent.putExtra("potCode", portCode);
        intent.putExtra("detailcode", portCode);
        intent.putExtra("detailtitle", portName);
        startActivityForResult(intent, requestCode);

        //최근 검색어 저장 이벤트
        RoomDataInsert(portName, portCode);

        Call<Object> setSearch = RetrofitClient.getInstance().getService().setSearch(5, portName);
        setSearch.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if(response.code() == 200) {

                }
            }
            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("레트로핏 실패","값 : "+t.getMessage());
            }
        });
    }


    void RoomDataInsert(String PortName, String PortCode){

        new Thread(new Runnable() {
            @Override
            public void run() {

                roomDao = SearchRoomDatabase.getINSTANCE(getContext()).roomDao();
                RoomSelectCode = roomDao.findCode(PortCode);
                RoomAllData = roomDao.findAll();

                //해당 포트에 대해서 Room에 저장된 동일한 데이터가 없을때
                if(RoomSelectCode == null) {

                    if(RoomAllData != null) {
                        if (RoomAllData.size() <= 4) {
                            searchViewModel.insert(new RoomEntity(PortName, PortCode));
                        }else {
                            searchViewModel.delete(RoomAllData.get(0));
                            searchViewModel.insert(new RoomEntity(PortName, PortCode));
                        }
                    }
                }
            }
        }).start();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 600) {
            if(resultCode == 500) {
                int codeD = data.getIntExtra("search_code_D",0);

                followInfo.putInt("search_category", 4);
                followInfo.putInt("search_page", 1);
                followInfo.putInt("search_code", codeD);

                followInfo.putBoolean("search_zzim_state", true);
                RxEventBus.getInstance().post(new RxEvent(RxEvent.SEARCH_CLICK_ZZIM, followInfo));

                DataManager.get_INstance().setCheckTab1(true);

            }
            else if(resultCode == 501) {
                int codeD = data.getIntExtra("search_code_D",0);

                followInfo.putInt("search_category", 4);
                followInfo.putInt("search_page", 1);
                followInfo.putInt("search_code", codeD);

                followInfo.putBoolean("search_zzim_state", false);
                RxEventBus.getInstance().post(new RxEvent(RxEvent.SEARCH_CLICK_ZZIM, followInfo));

                DataManager.get_INstance().setCheckTab1(true);
            }
        }
    }
}

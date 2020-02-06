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
import com.quantec.moneypot.activity.Search.SearchedPage.Adapter.AdapterSingleEn;
import com.quantec.moneypot.activity.Search.SearchedPage.Adapter.AdapterTitleTab;
import com.quantec.moneypot.activity.Search.SearchedPage.ModelSingleEn;
import com.quantec.moneypot.datamanager.DataManager;
import com.quantec.moneypot.datamodel.dmodel.ModelPostTitleItem;
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

public class FgSingleEnTab extends Fragment {

    ActivitySearch activitySearch;

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    AdapterSingleEn adapterSingleEn;

    Bundle bundle, followInfo;

    Toast toastZzimLimit;

    private SearchViewModel searchViewModel;

    private RoomDao roomDao;
    private RoomEntity RoomSelectCode;
    private List<RoomEntity> RoomAllData;

    ArrayList<ModelSingleEn> modelSingleEns;

    public FgSingleEnTab() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_titletab, container, false);

        //뷰 초기화
        initializeViews();

        recyclerView = view.findViewById(R.id.titlePage_tab_recyclerView);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        modelSingleEns = new ArrayList<>();
//
        adapterSingleEn = new AdapterSingleEn(modelSingleEns, getContext());
        recyclerView.setAdapter(adapterSingleEn);

        bundle = getArguments();
        followInfo = new Bundle();

        searchViewModel = ViewModelProviders.of(getActivity()).get(SearchViewModel.class);

        return view;

    }//onCreateView 끝

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

        modelSingleEns.addAll(bundle.getParcelableArrayList("singleEn"));

        adapterSingleEn.setSingleEnFollowClick(new AdapterSingleEn.SingleEnFollowClick() {
            @Override
            public void onClick(int position) {

                if(modelSingleEns.get(position).getFollow() == 0){
                    FollowClick(1, position);
                }else{
                    FollowClick(0, position);
                }
            }
        });

        adapterSingleEn.setSingleEnItemClick(new AdapterSingleEn.SingleEnItemClick() {
            @Override
            public void onClick(int position) {

                MovedDetailPage(modelSingleEns.get(position).getCode(), modelSingleEns.get(position).getTitle(), 600);
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

                                if(page == 0){
                                    if(type == 0){
                                        if (rxEvent.getBundle().getBoolean("search_follow")) {
                                            for (int a = 0; a < modelSingleEns.size(); a++) {
                                                if (modelSingleEns.get(a).getCode().equals(code)) {
                                                    modelSingleEns.get(a).setFollow(1);
                                                    adapterSingleEn.notifyItemChanged(a);
                                                    break;
                                                }
                                            }
                                        }
                                        //찜 헤제됨
                                        else {
                                            for (int a = 0; a < modelSingleEns.size(); a++) {
                                                if (modelSingleEns.get(a).getCode().equals(code)) {
                                                    modelSingleEns.get(a).setFollow(0);
                                                    adapterSingleEn.notifyItemChanged(a);
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



//        adapterTitlePage.notifyDataSetChanged();

        //커스텀 토스트 메시지
//        View toastView = View.inflate(getContext(), R.layout.dialog_toast_zzim_count_max, null);
//        toastZzimLimit = new Toast(getContext());
//        toastZzimLimit.setView(toastView);
//        toastZzimLimit.setDuration(Toast.LENGTH_SHORT);
//
//        RxEventBus.getInstance()
//                .filteredObservable(RxEvent.class)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<RxEvent>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                    }
//                    @Override
//                    public void onNext(RxEvent rxEvent) {
//
//                        switch (rxEvent.getActiion()) {
//
//                            case RxEvent.SEARCH_CLICK_ZZIM:
//                                String code = rxEvent.getBundle().getString("search_code");
//                                int page = rxEvent.getBundle().getInt("search_page");
//
//                                //통합페이지에서 찜 이벤트 있는 경우
//                                if(page == 0) {
//
//                                    //통합페이지에서 내용 찜 이벤트가 있는 경우
//                                    if(rxEvent.getBundle().getInt("search_category") == 1) {
//                                        if (rxEvent.getBundle().getBoolean("search_zzim_state")) {
//
//                                            for(int a = 0 ; a < postTitleItemModels.size() ; a++) {
//                                                if(postTitleItemModels.get(a).getCode().equals(code)) {
//                                                    postTitleItemModels.get(a).setZim(true);
//                                                    adapterTitlePage.notifyItemChanged(a);
//                                                }
//                                            }
//                                        } else {
//
//                                            for(int a = 0 ; a < postTitleItemModels.size() ; a++) {
//                                                if(postTitleItemModels.get(a).getCode().equals(code)) {
//                                                    postTitleItemModels.get(a).setZim(false);
//                                                    adapterTitlePage.notifyItemChanged(a);
//                                                }
//                                            }
//                                        }
//                                    }
//                                    //통합페이지에서 내용 외 찜 이벤트가 있는경우 ( 중복되는 포트에 대해서 찜 이벤트 실행 )
//                                    else {
//
//                                        if (rxEvent.getBundle().getBoolean("search_zzim_state")) {
//                                            for(int a = 0 ; a < postTitleItemModels.size() ; a++) {
//                                                if(postTitleItemModels.get(a).getCode().equals(code)) {
//                                                    postTitleItemModels.get(a).setZim(true);
//                                                    adapterTitlePage.notifyItemChanged(a);
//                                                }
//                                            }
//                                        } else {
//                                            for(int a = 0 ; a < postTitleItemModels.size() ; a++) {
//                                                if(postTitleItemModels.get(a).getCode().equals(code)) {
//                                                    postTitleItemModels.get(a).setZim(false);
//                                                    adapterTitlePage.notifyItemChanged(a);
//                                                }
//                                            }
//                                        }
//                                    }
//                                }
//                                //각 페이지에서 찜 이벤트가 있는 경우
//                                else if(page == 1) {
//
//                                    if (rxEvent.getBundle().getBoolean("search_zzim_state")) {
//                                        for(int a = 0 ; a < postTitleItemModels.size() ; a++) {
//                                            if(postTitleItemModels.get(a).getCode().equals(code)) {
//                                                postTitleItemModels.get(a).setZim(true);
//                                                adapterTitlePage.notifyItemChanged(a);
//                                            }
//                                        }
//                                    } else {
//                                        for(int a = 0 ; a < postTitleItemModels.size() ; a++) {
//                                            if(postTitleItemModels.get(a).getCode().equals(code)) {
//                                                postTitleItemModels.get(a).setZim(false);
//                                                adapterTitlePage.notifyItemChanged(a);
//                                            }
//                                        }
//                                    }
//                                }
//                                break;
//                        }
//                    }
//                    @Override
//                    public void onError(Throwable e) {
//                    }
//                    @Override
//                    public void onComplete() {
//                    }
//                });
//
////        찜하기 클릭
//        adapterTitlePage.setTitlePageZzimClicke(new AdapterTitleTab.TitlePageZzimClicke() {
//            @Override
//            public void onClick(int position) {
//                //찜 안된 상태 -> 찜 하기
//                if(postTitleItemModels.get(position).isZim()){
//
//                    if(SharedPreferenceUtil.getInstance(activitySearch).getIntExtra("PortZzimCount") >= 25) {
//                        //초과시 토스트
//                        toastZzimLimit.show();
//                    }else {
//                        ItemZzim(postTitleItemModels.get(position).getCode(), position, 0);
//                    }
//                }
//                //찜한 상태 -> 찜 풀기
//                else{
//                    ItemZzim(postTitleItemModels.get(position).getCode(), position, 1);
//                }
//            }
//        });
//
////        상세 페이지로 이동
//        adapterTitlePage.setTitlePageItemClick(new AdapterTitleTab.TitlePageItemClick() {
//            @Override
//            public void onClick(int position) {
//                Intent intent = new Intent(getActivity(), ActivitySingleDetail.class);
//                intent.putExtra("detailcode", postTitleItemModels.get(position).getCode());
//                intent.putExtra("detailtitle", postTitleItemModels.get(position).getName());
//                startActivityForResult(intent, 600);
//
//                RoomDataInsert(postTitleItemModels.get(position).getName(), postTitleItemModels.get(position).getCode());
//            }
//        });

    }//onViewCreate 끝

    void FollowClick(int follow, int position){

//        SharedPreferenceUtil.getInstance(activitySearch).putIntZzimCount("PortZzimCount", response.body().getNum());
        DataManager.get_INstance().setCheckTab1(true);
        DataManager.get_INstance().setCheckTab2(true);
        DataManager.get_INstance().setCheckHome(true);

        followInfo.putInt("search_type", 0);
        followInfo.putInt("search_page", 1);
        followInfo.putString("search_code", modelSingleEns.get(position).getCode());

        List<Select> selects = new ArrayList<>();
        Select select = new Select();
        select.setIsDam(0);
        select.setIsLike(0);
        select.setIsZim(0);

        select.setCode(modelSingleEns.get(position).getCode());
        select.setIsFollow(follow);
        select.setType(0);
        selects.add(select);

        ModelUserSelectDto modelUserSelectDto = new ModelUserSelectDto();
        modelUserSelectDto.setSelects(selects);

        Call<Object> getReList = RetrofitClient.getInstance().getService().setUserSelect("application/json", "follow", modelUserSelectDto);
        getReList.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.code() == 200) {
                    if(follow == 0){
                        modelSingleEns.get(position).setFollow(0);
                        followInfo.putBoolean("search_follow", false);

                        Toast.makeText(activitySearch, "팔로우 취소", Toast.LENGTH_SHORT).show();
                        roomDao = SearchRoomDatabase.getINSTANCE(getContext()).roomDao();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                roomDao.updateData(0, modelSingleEns.get(position).getCode());
                            }
                        }).start();

                    }else{
                        modelSingleEns.get(position).setFollow(1);
                        followInfo.putBoolean("search_follow", true);

                        Toast.makeText(activitySearch, "팔로우", Toast.LENGTH_SHORT).show();
                        roomDao = SearchRoomDatabase.getINSTANCE(getContext()).roomDao();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                roomDao.updateData(1, modelSingleEns.get(position).getCode());
                            }
                        }).start();
                    }
                    RxEventBus.getInstance().post(new RxEvent(RxEvent.SEARCH_CLICK_ZZIM, followInfo));
                    adapterSingleEn.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("실패","실패"+t.getMessage());
            }
        });
    }


    // SelectedState : 0 -> 포트 찜하기 / 1 -> 포트 찜 해제
    void ItemZzim(String PortCode, int PortPosition, int SelectedState){

//        Call<ModelPortZzim> getData = RetrofitClient.getInstance().getService().getPortSaveData(PortCode, SelectedState);
//        getData.enqueue(new Callback<ModelPortZzim>() {
//            @Override
//            public void onResponse(Call<ModelPortZzim> call, Response<ModelPortZzim> response) {
//                if (response.code() == 200) {
//
//                    zzimInfo.putInt("search_category", 1);
//                    zzimInfo.putInt("search_zzim_position", PortPosition);
//                    zzimInfo.putInt("search_page", 1);
//                    zzimInfo.putString("search_code", PortCode);
//                    //찜하기
//                    if(SelectedState == 0) {
//                        zzimInfo.putBoolean("search_zzim_state", true);
//                        postTitleItemModels.get(PortPosition).setSelect(true);
//                    }
//                    //찜 해제
//                    else{
//                        zzimInfo.putBoolean("search_zzim_state", false);
//                        postTitleItemModels.get(PortPosition).setSelect(false);
//                    }
//                    adapterTitlePage.notifyItemChanged(PortPosition);
//                    RxEventBus.getInstance().post(new RxEvent(RxEvent.SEARCH_CLICK_ZZIM, zzimInfo));
//                    SharedPreferenceUtil.getInstance(portSearchPageActivity).putIntZzimCount("PortZzimCount", response.body().getNum());
//                }
//            }
//            @Override
//            public void onFailure(Call<ModelPortZzim> call, Throwable t) {
//                Toast.makeText(getActivity(), "네트워크가 불안정 합니다\n 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
//            }
//        });
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

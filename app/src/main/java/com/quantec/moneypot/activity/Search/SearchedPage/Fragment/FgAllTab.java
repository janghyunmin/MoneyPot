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
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

import com.quantec.moneypot.activity.PotDetail.ActivitySingleDetail;
import com.quantec.moneypot.activity.Search.ActivitySearch;
import com.quantec.moneypot.activity.Search.SearchedPage.Adapter.AdapterAllTabDesc;
import com.quantec.moneypot.activity.Search.SearchedPage.Adapter.AdapterAllTabSingleEn;
import com.quantec.moneypot.activity.Search.SearchedPage.Adapter.AdapterAllTabStock;
import com.quantec.moneypot.activity.Search.SearchedPage.Adapter.AdapterAllTabSumEn;
import com.quantec.moneypot.activity.Search.SearchedPage.Adapter.AdapterAllTabTitle;
import com.quantec.moneypot.activity.Search.SearchedPage.ModelNewsEn;
import com.quantec.moneypot.activity.Search.SearchedPage.ModelPreNewsEn;
import com.quantec.moneypot.activity.Search.SearchedPage.ModelPreSingleEn;
import com.quantec.moneypot.activity.Search.SearchedPage.ModelPreSumEn;
import com.quantec.moneypot.activity.Search.SearchedPage.ModelSingleEn;
import com.quantec.moneypot.activity.Search.SearchedPage.ModelSumEn;
import com.quantec.moneypot.datamanager.DataManager;
import com.quantec.moneypot.datamodel.dmodel.ModelDescItem;
import com.quantec.moneypot.datamodel.dmodel.ModelEmptyItem;
import com.quantec.moneypot.datamodel.dmodel.ModelStockItem;
import com.quantec.moneypot.datamodel.dmodel.ModelTitleItem;
import com.quantec.moneypot.datamodel.dmodel.ModelUserSelectDto;
import com.quantec.moneypot.datamodel.dmodel.userselectdto.Select;
import com.quantec.moneypot.datamodel.nmodel.ModelZimData;
import com.quantec.moneypot.database.room.entry.RoomEntity;
import com.quantec.moneypot.database.room.local.RoomDao;
import com.quantec.moneypot.database.room.local.SearchRoomDatabase;
import com.quantec.moneypot.database.room.viewmodel.SearchViewModel;
import com.quantec.moneypot.network.retrofit.RetrofitClient;
import com.quantec.moneypot.R;
import com.quantec.moneypot.rxandroid.RxEvent;
import com.quantec.moneypot.rxandroid.RxEventBus;
import com.quantec.moneypot.databinding.FgAlltabBinding;
import com.quantec.moneypot.util.SharedPreference.SharedPreferenceUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FgAllTab extends Fragment {

    AdapterAllTabSingleEn adapterAllTabSingleEn;
    AdapterAllTabSumEn adapterAllTabSumEn;

    RecyclerView.LayoutManager layoutManagerT, layoutManagerD, layoutManagerS;

    ArrayList<ModelPreSingleEn> modelPreSingleEns;
    ArrayList<ModelPreSumEn> modelPreSumEn;
    ArrayList<ModelPreNewsEn> modelPreNewsEns;

    ArrayList<ModelSingleEn> modelSingleEns;
    ArrayList<ModelSumEn> modelSumEn;
    ArrayList<ModelNewsEn> modelNewsEns;

    ActivitySearch activitySearch;

    Bundle getSearchedData, MovedTab, followInfo;

    //검색 제안 데이터 전달
    private OnClickEmptyText onClickEmptyText;

    Toast toastZzimLimit;

    private SearchViewModel searchViewModel;

    private RoomDao roomDao;
    private RoomEntity RoomSelectCode;
    private List<RoomEntity> RoomAllData;

    FgAlltabBinding binding;

    public FgAllTab() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fg_alltab, container, false);

        //뷰 초기화
        initializeViews();
        //리사이클러뷰 초기화
        InitRecyclerView();

        binding.recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()){
            @Override
            public boolean canScrollVertically() { // 세로스크롤 막기
                return false;
            }
        });
        binding.recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()){
            @Override
            public boolean canScrollVertically() { // 세로스크롤 막기
                return false;
            }
        });
        binding.recyclerView3.setLayoutManager(new LinearLayoutManager(getContext()){
            @Override
            public boolean canScrollVertically() { // 세로스크롤 막기
                return false;
            }
        });

        //검색시 Fragment_SearchPage에서 받은 데이터를 가져옴
        getSearchedData = new Bundle();
        getSearchedData = getArguments();

        //전체보기시 클릭시 탭이동 구분
        // 0 : 단일 / 1 : 묶음 / 2 : 기사
        MovedTab = new Bundle();
        followInfo = new Bundle();

        searchViewModel = ViewModelProviders.of(getActivity()).get(SearchViewModel.class);

        return binding.getRoot();

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

        if(getActivity() != null && getActivity() instanceof OnClickEmptyText) {
            onClickEmptyText = (OnClickEmptyText) getActivity();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        modelSingleEns.addAll(getSearchedData.getParcelableArrayList("singleEn"));
        modelSumEn.addAll(getSearchedData.getParcelableArrayList("sumEn"));

        if(modelSingleEns.size() > 0){
            int size;
            if(modelSingleEns.size() < 7){
                size = modelSingleEns.size();
            }else{
                size = 7;
            }
            for(int index = 0 ; index < size ; index++){
                modelPreSingleEns.add(new ModelPreSingleEn(modelSingleEns.size()-1,false, modelSingleEns.get(index).getTitle(),
                        modelSingleEns.get(index).getCode(), modelSingleEns.get(index).getRate(), modelSingleEns.get(index).getFollow()));
            }
        }

        if(modelSumEn.size() > 0){
            int size;
            if(modelSumEn.size() < 7){
                size = modelSumEn.size();
            }else{
                size = 7;
            }
            for(int index = 0 ; index < size; index++){
                modelPreSumEn.add(new ModelPreSumEn(modelSumEn.size()-1,false, modelSumEn.get(index).getTitle(), modelSumEn.get(index).getCode(),
                        modelSumEn.get(index).getStock(), modelSumEn.get(index).getRate(), modelSumEn.get(index).getFollow()));
            }
        }

        RecyclerViewState(0);

        adapterAllTabSingleEn.notifyDataSetChanged();
        adapterAllTabSumEn.notifyDataSetChanged();

        //커스텀 토스트 메시지
        View toastView = View.inflate(getContext(), R.layout.dialog_toast_zzim_count_max, null);
        toastZzimLimit = new Toast(getContext());
        toastZzimLimit.setView(toastView);
        toastZzimLimit.setDuration(Toast.LENGTH_SHORT);


        adapterAllTabSingleEn.setSingleEnFollowBt(new AdapterAllTabSingleEn.SingleEnFollowBt() {
            @Override
            public void onClick(int position) {

                if(modelPreSingleEns.get(position).getFollow() == 0){
                    FollowClick(1, 0, position);
                }else{
                    FollowClick(0, 0, position);
                }
            }
        });

        adapterAllTabSumEn.setSumEnFollowBt(new AdapterAllTabSumEn.SumEnFollowBt() {
            @Override
            public void onClick(int position) {

                if(modelPreSumEn.get(position).getFollow() == 0){
                    FollowClick(1, 1, position);
                }else{
                    FollowClick(0, 1, position);
                }
            }
        });

        adapterAllTabSingleEn.setSingleEnDetailBt(new AdapterAllTabSingleEn.SingleEnDetailBt() {
            @Override
            public void onClick(int position) {
                MovedTab.putInt("moveTab", 0);
                RxEventBus.getInstance().post(new RxEvent(RxEvent.SEARCH_TRANS_PAGE, MovedTab));
            }
        });

        adapterAllTabSumEn.setSumEnDetailBt(new AdapterAllTabSumEn.SumEnDetailBt() {
            @Override
            public void onClick(int position) {
                MovedTab.putInt("moveTab", 1);
                RxEventBus.getInstance().post(new RxEvent(RxEvent.SEARCH_TRANS_PAGE, MovedTab));
            }
        });

        adapterAllTabSingleEn.setSingleItemClick(new AdapterAllTabSingleEn.SingleItemClick() {
            @Override
            public void onClick(int position) {
                MovedDetailPage(modelPreSingleEns.get(position).getCode(), modelPreSingleEns.get(position).getTitle(), position, 600);
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

                                if(page == 1){
                                    if(type == 0){
                                        if (rxEvent.getBundle().getBoolean("search_follow")) {
                                            for (int a = 0; a < modelPreSingleEns.size(); a++) {
                                                if (modelPreSingleEns.get(a).getCode().equals(code)) {
                                                    modelPreSingleEns.get(a).setFollow(1);
                                                    adapterAllTabSingleEn.notifyItemChanged(a);
                                                    break;
                                                }
                                            }
                                        }
                                        //찜 헤제됨
                                        else {
                                            for (int a = 0; a < modelPreSingleEns.size(); a++) {
                                                if (modelPreSingleEns.get(a).getCode().equals(code)) {
                                                    modelPreSingleEns.get(a).setFollow(0);
                                                    adapterAllTabSingleEn.notifyItemChanged(a);
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                    else{

                                        if (rxEvent.getBundle().getBoolean("search_follow")) {
                                            for (int a = 0; a < modelPreSumEn.size(); a++) {
                                                if (modelPreSumEn.get(a).getCode().equals(code)) {
                                                    modelPreSumEn.get(a).setFollow(1);
                                                    adapterAllTabSumEn.notifyItemChanged(a);
                                                    break;
                                                }
                                            }
                                        }
                                        //찜 헤제됨
                                        else {
                                            for (int a = 0; a < modelPreSumEn.size(); a++) {
                                                if (modelPreSumEn.get(a).getCode().equals(code)) {
                                                    modelPreSumEn.get(a).setFollow(0);
                                                    adapterAllTabSumEn.notifyItemChanged(a);
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

    // 각 카테고리에서 상세페이지로 이동
    void MovedDetailPage(String portCode, String portName, int position, int requestCode){

        Intent intent = new Intent(getActivity(), ActivitySingleDetail.class);
        intent.putExtra("potCode", portCode);
        intent.putExtra("detailcode", portCode);
        intent.putExtra("detailtitle", portName);
        intent.putExtra("potPosition", position);
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

    //최근 검색어 저장 이벤트
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
                String code = data.getStringExtra("searched_code");

                Log.e("받은 코드값1", "값 : "+code);

//                followInfo.putInt("search_type", 4);
//                followInfo.putInt("search_page", 0);
//                followInfo.putString("search_code", codeD);
//
//                followInfo.putBoolean("search_follow", true);
//                RxEventBus.getInstance().post(new RxEvent(RxEvent.SEARCH_CLICK_ZZIM, followInfo));
//
//                DataManager.get_INstance().setCheckTab1(true);

            }
            else if(resultCode == 501) {
                String code = data.getStringExtra("searched_code");

                Log.e("받은 코드값2", "값 : "+code);

//                followInfo.putInt("search_type", 4);
//                followInfo.putInt("search_page", 0);
//                followInfo.putString("search_code", codeD);
//
//                followInfo.putBoolean("search_follow", false);
//                RxEventBus.getInstance().post(new RxEvent(RxEvent.SEARCH_CLICK_ZZIM, followInfo));
//
//                DataManager.get_INstance().setCheckTab1(true);
            }
        }
    }


    void FollowClick(int follow, int type, int position){

        if(type == 0){

            followInfo.putInt("search_type", 0);
            followInfo.putInt("search_page", 0);
            followInfo.putString("search_code", modelSingleEns.get(position).getCode());

            List<Select> selects = new ArrayList<>();
            Select select = new Select();
            select.setIsDam(0);
            select.setIsLike(0);
            select.setIsZim(0);

            select.setCode(modelPreSingleEns.get(position).getCode());
            select.setIsFollow(follow);
            select.setType(type);
            selects.add(select);

            ModelUserSelectDto modelUserSelectDto = new ModelUserSelectDto();
            modelUserSelectDto.setSelects(selects);

            Call<Object> getReList = RetrofitClient.getInstance().getService().setUserSelect("application/json", "follow", modelUserSelectDto);
            getReList.enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    if (response.code() == 200) {
                        if(follow == 0){
                            modelPreSingleEns.get(position).setFollow(0);
                            followInfo.putBoolean("search_follow", false);

                            Toast.makeText(activitySearch, "팔로우 취소", Toast.LENGTH_SHORT).show();
                            roomDao = SearchRoomDatabase.getINSTANCE(getContext()).roomDao();
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    roomDao.updateData(0, modelPreSingleEns.get(position).getCode());
                                }
                            }).start();

                        }else{
                            modelPreSingleEns.get(position).setFollow(1);
                            followInfo.putBoolean("search_follow", true);

                            Toast.makeText(activitySearch, "팔로우", Toast.LENGTH_SHORT).show();
                            roomDao = SearchRoomDatabase.getINSTANCE(getContext()).roomDao();
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    roomDao.updateData(1, modelPreSingleEns.get(position).getCode());
                                }
                            }).start();
                        }
                        RxEventBus.getInstance().post(new RxEvent(RxEvent.SEARCH_CLICK_ZZIM, followInfo));
                        adapterAllTabSingleEn.notifyDataSetChanged();
                    }
                }
                @Override
                public void onFailure(Call<Object> call, Throwable t) {
                    Log.e("실패","실패"+t.getMessage());
                }
            });

        }else{

            followInfo.putInt("search_type", 1);
            followInfo.putInt("search_page", 0);
            followInfo.putString("search_code", modelPreSumEn.get(position).getCode());

            List<Select> selects = new ArrayList<>();
            Select select = new Select();
            select.setIsDam(0);
            select.setIsLike(0);
            select.setIsZim(0);

            select.setCode(modelPreSumEn.get(position).getCode());
            select.setIsFollow(follow);
            select.setType(type);
            selects.add(select);

            ModelUserSelectDto modelUserSelectDto = new ModelUserSelectDto();
            modelUserSelectDto.setSelects(selects);

            Call<Object> getReList = RetrofitClient.getInstance().getService().setUserSelect("application/json", "follow", modelUserSelectDto);
            getReList.enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    if (response.code() == 200) {
                        if(follow == 0){
                            modelPreSumEn.get(position).setFollow(0);
                            followInfo.putBoolean("search_follow", false);

                            Toast.makeText(activitySearch, "팔로우 취소", Toast.LENGTH_SHORT).show();
                            roomDao = SearchRoomDatabase.getINSTANCE(getContext()).roomDao();
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    roomDao.updateData(0, modelPreSumEn.get(position).getCode());
                                }
                            }).start();

                        }else{
                            modelPreSumEn.get(position).setFollow(1);
                            followInfo.putBoolean("search_follow", true);

                            Toast.makeText(activitySearch, "팔로우", Toast.LENGTH_SHORT).show();
                            roomDao = SearchRoomDatabase.getINSTANCE(getContext()).roomDao();
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    roomDao.updateData(1, modelPreSumEn.get(position).getCode());
                                }
                            }).start();
                        }
                        RxEventBus.getInstance().post(new RxEvent(RxEvent.SEARCH_CLICK_ZZIM, followInfo));
                        adapterAllTabSumEn.notifyDataSetChanged();
                    }
                }
                @Override
                public void onFailure(Call<Object> call, Throwable t) {
                    Log.e("실패","실패"+t.getMessage());
                }
            });

        }
    }

    //검색결과 없을때 보여주는 추천 검색어 클릭 이벤트
    public interface OnClickEmptyText {
        void onReceivedData(String text);
    }

    //검색된 데이터에 따라서 리사이클러뷰를 숨김
    void RecyclerViewState(int stateNum){
        if(modelSingleEns.size()==0){
            binding.recyclerView1.setVisibility(View.GONE);
        }else{
            binding.recyclerView1.setVisibility(View.VISIBLE);
        }
        if(modelSumEn.size()==0){
            binding.recyclerView2.setVisibility(View.GONE);
        }else{
            binding.recyclerView2.setVisibility(View.VISIBLE);
        }
        if(modelNewsEns.size()==0){
            binding.recyclerView3.setVisibility(View.GONE);
        }else{
            binding.recyclerView3.setVisibility(View.VISIBLE);
        }
    }

    //    //리사이클러뷰 초기화
    void InitRecyclerView(){

        binding.recyclerView1.setHasFixedSize(true);
        binding.recyclerView1.setNestedScrollingEnabled(false);
        binding.recyclerView2.setHasFixedSize(true);
        binding.recyclerView3.setHasFixedSize(true);

        layoutManagerT = new LinearLayoutManager(getContext());
        layoutManagerD = new LinearLayoutManager(getContext());
        layoutManagerS = new LinearLayoutManager(getContext());

        binding.recyclerView1.setLayoutManager(layoutManagerT);
        binding.recyclerView2.setLayoutManager(layoutManagerD);
        binding.recyclerView3.setLayoutManager(layoutManagerS);

        modelSingleEns = new ArrayList<>();
        modelSumEn = new ArrayList<>();
        modelNewsEns = new ArrayList<>();

        modelPreSingleEns = new ArrayList<>();
        modelPreSumEn = new ArrayList<>();
        modelNewsEns = new ArrayList<>();

        adapterAllTabSingleEn = new AdapterAllTabSingleEn(modelPreSingleEns, activitySearch);
        adapterAllTabSumEn = new AdapterAllTabSumEn(modelPreSumEn, activitySearch);
//        adapterAllTabSingleEn = new AdapterAllTabSingleEn(modelSingleEns, activitySearch);
//        adapterAllTabSumEn = new AdapterAllTabSumEn(modelSumEn, activitySearch);

        binding.recyclerView1.setAdapter(adapterAllTabSingleEn);
        binding.recyclerView2.setAdapter(adapterAllTabSumEn);
    }
}

//public class FgAllTab extends Fragment {
//
//    AdapterAllTabSingleEn adapterAllPageTitle;
////    AdapterAllTabTitle adapterAllPageTitle;
//    AdapterAllTabDesc adapterAllPageDesc;
//    AdapterAllTabStock adapterAllPageStock;
//
//    RecyclerView.LayoutManager layoutManagerT, layoutManagerD, layoutManagerS;
//
//    ArrayList<ModelSingleEn> titleItemModels;
////    ArrayList<ModelTitleItem> titleItemModels;
//    ArrayList<ModelDescItem> descItemModels;
//    ArrayList<ModelStockItem> stockItemModels;
//    ArrayList<ModelEmptyItem> emptyItemModels;
//
//    ActivitySearch activitySearch;
//
//    Bundle getSearchedData, MovedTab, zzimInfo;
//
//    //검색 제안 데이터 전달
//    private OnClickEmptyText onClickEmptyText;
//
//    Toast toastZzimLimit;
//
//    private SearchViewModel searchViewModel;
//
//    private RoomDao roomDao;
//    private RoomEntity RoomSelectCode;
//    private List<RoomEntity> RoomAllData;
//
//    FgAlltabBinding binding;
//
//    public FgAllTab() {
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        binding = DataBindingUtil.inflate(inflater, R.layout.fg_alltab, container, false);
//
//        //뷰 초기화
//        initializeViews();
//        //리사이클러뷰 초기화
//        InitRecyclerView();
//
//        binding.recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()){
//            @Override
//            public boolean canScrollVertically() { // 세로스크롤 막기
//                return false;
//            }
//            @Override
//            public boolean canScrollHorizontally() { //가로 스크롤막기
//                return false;
//            }
//        });
//        binding.recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()){
//            @Override
//            public boolean canScrollVertically() { // 세로스크롤 막기
//                return false;
//            }
//            @Override
//            public boolean canScrollHorizontally() { //가로 스크롤막기
//                return false;
//            }
//        });
//        binding.recyclerView3.setLayoutManager(new LinearLayoutManager(getContext()){
//            @Override
//            public boolean canScrollVertically() { // 세로스크롤 막기
//                return false;
//            }
//            @Override
//            public boolean canScrollHorizontally() { //가로 스크롤막기
//                return false;
//            }
//        });
//
//        //검색시 Fragment_SearchPage에서 받은 데이터를 가져옴
//        getSearchedData = getArguments();
//
//        //전체보기시 클릭시 탭이동 구분
//        // 0 : 제목 / 1 : 내용 / 2 : 종목
//        MovedTab = new Bundle();
//        zzimInfo = new Bundle();
//
//        searchViewModel = ViewModelProviders.of(getActivity()).get(SearchViewModel.class);
//
//        return binding.getRoot();
//    }//onCreateView 끝
//
//
//    private void initializeViews(){
//        activitySearch = (ActivitySearch) getActivity();
//    }
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if(context instanceof ActivitySearch) {
//            activitySearch = (ActivitySearch) context;
//        }
//
//        if(getActivity() != null && getActivity() instanceof OnClickEmptyText) {
//            onClickEmptyText = (OnClickEmptyText) getActivity();
//        }
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        titleItemModels.addAll(getSearchedData.getParcelableArrayList("title_list"));
//
//        Log.e("받은 총값","갯수 : "+titleItemModels.size());
//
////        descItemModels.addAll(getSearchedData.getParcelableArrayList("desc_list"));
////        stockItemModels.addAll(getSearchedData.getParcelableArrayList("stock_list"));
////        emptyItemModels.addAll(getSearchedData.getParcelableArrayList("empty_list"));
//
//        RecyclerViewState(getSearchedData.getInt("category_empty"));
//
//        adapterAllPageTitle.notifyDataSetChanged();
//        adapterAllPageDesc.notifyDataSetChanged();
//        adapterAllPageStock.notifyDataSetChanged();
//
//        //커스텀 토스트 메시지
//        View toastView = View.inflate(getContext(), R.layout.dialog_toast_zzim_count_max, null);
//        toastZzimLimit = new Toast(getContext());
//        toastZzimLimit.setView(toastView);
//        toastZzimLimit.setDuration(Toast.LENGTH_SHORT);
//
////        RxEventBus.getInstance()
////                .filteredObservable(RxEvent.class)
////                .observeOn(AndroidSchedulers.mainThread())
////                .subscribe(new Observer<RxEvent>() {
////                    @Override
////                    public void onSubscribe(Disposable d) {
////                    }
////                    @Override
////                    public void onNext(RxEvent rxEvent) {
////
////                        switch (rxEvent.getActiion()) {
////
////                            case RxEvent.SEARCH_CLICK_ZZIM:
////                                String code = rxEvent.getBundle().getString("search_code");
////                                int page = rxEvent.getBundle().getInt("search_page");
////                                int category = rxEvent.getBundle().getInt("search_category");
////
////                                //통합페이지에서 찜 이벤트 있을때
////                                if(page == 0) {
////                                    //제목에서 찜 이벤트 있을때
////                                    if (category == 1) {
////                                        //찜 체크됨
////                                        if (rxEvent.getBundle().getBoolean("search_zzim_state")) {
////                                            for (int a = 0; a < descItemModels.size(); a++) {
////                                                if (descItemModels.get(a).getCode().equals(code)) {
////                                                    descItemModels.get(a).setZim(true);
////                                                    adapterAllPageDesc.notifyItemChanged(a);
////                                                    break;
////                                                }
////                                            }
////                                            for (int b = 0; b < stockItemModels.size(); b++) {
////                                                if (stockItemModels.get(b).getCode().equals(code)) {
////                                                    stockItemModels.get(b).setZim(true);
////                                                    adapterAllPageStock.notifyItemChanged(b);
////                                                    break;
////                                                }
////                                            }
////                                        }
////                                        //찜 헤제됨
////                                        else {
////                                            for (int a = 0; a < descItemModels.size(); a++) {
////                                                if (descItemModels.get(a).getCode().equals(code)) {
////                                                    descItemModels.get(a).setZim(false);
////                                                    adapterAllPageDesc.notifyItemChanged(a);
////                                                    break;
////                                                }
////                                            }
////                                            for (int b = 0; b < stockItemModels.size(); b++) {
////                                                if (stockItemModels.get(b).getCode().equals(code)) {
////                                                    stockItemModels.get(b).setZim(false);
////                                                    adapterAllPageStock.notifyItemChanged(b);
////                                                    break;
////                                                }
////                                            }
////                                        }
////                                    }
////                                    //내용에서 찜 이벤트 있을때
////                                    else if (category == 2) {
////                                        //찜 체크됨
////                                        if (rxEvent.getBundle().getBoolean("search_zzim_state")) {
////                                            for (int a = 0; a < titleItemModels.size(); a++) {
////                                                if (titleItemModels.get(a).getCode().equals(code)) {
////                                                    titleItemModels.get(a).setZim(true);
////                                                    adapterAllPageTitle.notifyItemChanged(a);
////                                                    break;
////                                                }
////                                            }
////                                            for (int b = 0; b < stockItemModels.size(); b++) {
////                                                if (stockItemModels.get(b).getCode().equals(code)) {
////                                                    stockItemModels.get(b).setZim(true);
////                                                    adapterAllPageStock.notifyItemChanged(b);
////                                                    break;
////                                                }
////                                            }
////                                        }
////                                        //찜 헤제됨
////                                        else {
////                                            for (int a = 0; a < titleItemModels.size(); a++) {
////                                                if (titleItemModels.get(a).getCode().equals(code)) {
////                                                    titleItemModels.get(a).setZim(false);
////                                                    adapterAllPageTitle.notifyItemChanged(a);
////                                                    break;
////                                                }
////                                            }
////                                            for (int b = 0; b < stockItemModels.size(); b++) {
////                                                if (stockItemModels.get(b).getCode().equals(code)) {
////                                                    stockItemModels.get(b).setZim(false);
////                                                    adapterAllPageStock.notifyItemChanged(b);
////                                                    break;
////                                                }
////                                            }
////                                        }
////                                    }
////                                    //종목에서 찜 이벤트 있을때
////                                    else if (category == 3) {
////                                        //찜 체크됨
////                                        if (rxEvent.getBundle().getBoolean("search_zzim_state")) {
////                                            for (int a = 0; a < titleItemModels.size(); a++) {
////                                                if (titleItemModels.get(a).getCode().equals(code)) {
////                                                    titleItemModels.get(a).setZim(true);
////                                                    adapterAllPageTitle.notifyItemChanged(a);
////                                                    break;
////                                                }
////                                            }
////                                            for (int b = 0; b < descItemModels.size(); b++) {
////                                                if (descItemModels.get(b).getCode().equals(code)) {
////                                                    descItemModels.get(b).setZim(true);
////                                                    adapterAllPageDesc.notifyItemChanged(b);
////                                                    break;
////                                                }
////                                            }
////                                        }
////                                        //찜 헤제됨
////                                        else {
////                                            for (int a = 0; a < titleItemModels.size(); a++) {
////                                                if (titleItemModels.get(a).getCode().equals(code)) {
////                                                    titleItemModels.get(a).setZim(false);
////                                                    adapterAllPageTitle.notifyItemChanged(a);
////                                                    break;
////                                                }
////                                            }
////                                            for (int b = 0; b < descItemModels.size(); b++) {
////                                                if (descItemModels.get(b).getCode().equals(code)) {
////                                                    descItemModels.get(b).setZim(false);
////                                                    adapterAllPageDesc.notifyItemChanged(b);
////                                                    break;
////                                                }
////                                            }
////                                        }
////
////                                    }
////                                    //상세페이지에서 찜 이벤트 후 전달
////                                    else if(category == 4) {
////
////                                        //찜 체크됨
////                                        if (rxEvent.getBundle().getBoolean("search_zzim_state")) {
////                                            for (int a = 0; a < titleItemModels.size(); a++) {
////                                                if (titleItemModels.get(a).getCode().equals(code)) {
////                                                    titleItemModels.get(a).setZim(true);
////                                                    adapterAllPageTitle.notifyItemChanged(a);
////                                                    break;
////                                                }
////                                            }
////                                            for (int b = 0; b < descItemModels.size(); b++) {
////                                                if (descItemModels.get(b).getCode().equals(code)) {
////                                                    descItemModels.get(b).setZim(true);
////                                                    adapterAllPageDesc.notifyItemChanged(b);
////                                                    break;
////                                                }
////                                            }
////                                            for (int c = 0; c < stockItemModels.size(); c++) {
////                                                if (stockItemModels.get(c).getCode().equals(code)) {
////                                                    stockItemModels.get(c).setZim(true);
////                                                    adapterAllPageStock.notifyItemChanged(c);
////                                                    break;
////                                                }
////                                            }
////                                        }
////                                        //찜 헤제됨
////                                        else {
////                                            for (int a = 0; a < titleItemModels.size(); a++) {
////                                                if (titleItemModels.get(a).getCode().equals(code)) {
////                                                    titleItemModels.get(a).setZim(false);
////                                                    adapterAllPageTitle.notifyItemChanged(a);
////                                                    break;
////                                                }
////                                            }
////                                            for (int b = 0; b < descItemModels.size(); b++) {
////                                                if (descItemModels.get(b).getCode().equals(code)) {
////                                                    descItemModels.get(b).setZim(false);
////                                                    adapterAllPageDesc.notifyItemChanged(b);
////                                                    break;
////                                                }
////                                            }
////                                            for (int c = 0; c < stockItemModels.size(); c++) {
////                                                if (stockItemModels.get(c).getCode().equals(code)) {
////                                                    stockItemModels.get(c).setZim(false);
////                                                    adapterAllPageStock.notifyItemChanged(c);
////                                                    break;
////                                                }
////                                            }
////                                        }
////
////                                    }
////
////                                }
////                                //각 페이지에서 찜 이벤트 있을때
////                                else if(page == 1){
////                                    //찜 체크됨
////                                    if (rxEvent.getBundle().getBoolean("search_zzim_state")) {
////                                        for (int a = 0; a < titleItemModels.size(); a++) {
////                                            if (titleItemModels.get(a).getCode().equals(code)) {
////                                                titleItemModels.get(a).setZim(true);
////                                                adapterAllPageTitle.notifyItemChanged(a);
////                                                break;
////                                            }
////                                        }
////                                        for (int b = 0; b < descItemModels.size(); b++) {
////                                            if (descItemModels.get(b).getCode().equals(code)) {
////                                                descItemModels.get(b).setZim(true);
////                                                adapterAllPageDesc.notifyItemChanged(b);
////                                                break;
////                                            }
////                                        }
////                                        for (int c = 0; c < stockItemModels.size(); c++) {
////                                            if (stockItemModels.get(c).getCode().equals(code)) {
////                                                stockItemModels.get(c).setZim(true);
////                                                adapterAllPageStock.notifyItemChanged(c);
////                                                break;
////                                            }
////                                        }
////                                    }
////                                    //찜 헤제됨
////                                    else {
////                                        for (int a = 0; a < titleItemModels.size(); a++) {
////                                            if (titleItemModels.get(a).getCode().equals(code)) {
////                                                titleItemModels.get(a).setZim(false);
////                                                adapterAllPageTitle.notifyItemChanged(a);
////                                                break;
////                                            }
////                                        }
////                                        for (int b = 0; b < descItemModels.size(); b++) {
////                                            if (descItemModels.get(b).getCode().equals(code)) {
////                                                descItemModels.get(b).setZim(false);
////                                                adapterAllPageDesc.notifyItemChanged(b);
////                                                break;
////                                            }
////                                        }
////                                        for (int c = 0; c < stockItemModels.size(); c++) {
////                                            if (stockItemModels.get(c).getCode().equals(code)) {
////                                                stockItemModels.get(c).setZim(false);
////                                                adapterAllPageStock.notifyItemChanged(c);
////                                                break;
////                                            }
////                                        }
////                                    }
////                                }
////                                break;
////                        }
////                    }
////                    @Override
////                    public void onError(Throwable e) {
////                    }
////                    @Override
////                    public void onComplete() {
////                    }
////                });
//
//
////        //제목에서 상세 페이지로 이동
////        adapterAllPageTitle.setTitleItemClick(new AdapterAllTabTitle.TitleItemClick() {
////            @Override
////            public void onClick(int position) {
////                MovedDetailPage(titleItemModels.get(position).getCode(), titleItemModels.get(position).getName(), 600);
////            }
////        });
//
////        //제목에서 찜하기 클릭
////        adapterAllPageTitle.setTitleZzimClick(new AdapterAllTabTitle.TitleZzimClick() {
////            @Override
////            public void onClick(int position) {
////
////                //찜 안된 상태 -> 찜 하기
////                if(!titleItemModels.get(position).isZim()){
////
////                    if(SharedPreferenceUtil.getInstance(activitySearch).getIntExtra("PortZzimCount") >= 25) {
////                        //초과시 토스트
////                        toastZzimLimit.show();
////                    }else {
////                        ItemZzim(titleItemModels.get(position).getCode(), position, 0, 1, titleItemModels.get(position).isDam(), true);
////                    }
////                }
////                //찜한 상태 -> 찜 풀기
////                else{
////                    ItemZzim(titleItemModels.get(position).getCode(), position, 1, 1, titleItemModels.get(position).isDam(), false);
////                }
////            }
////        });
//
//
////        //제목에서 전체보기 클릭시 상세제목으로 이동
////        adapterAllPageTitle.setTitleAddViewClick(new AdapterAllTabTitle.TitleAddViewClick() {
////            @Override
////            public void onClick(int position) {
////                MovedTab.putInt("allPage_MovedTab", 0);
////                RxEventBus.getInstance().post(new RxEvent(RxEvent.SEARCH_TRANS_PAGE, MovedTab));
////            }
////        });
////
////
//        //내용에서 상세페이지로 이동
//        adapterAllPageDesc.setDescItemClick(new AdapterAllTabDesc.DescItemClick() {
//            @Override
//            public void onClick(int position) {
//                MovedDetailPage(descItemModels.get(position).getCode(), descItemModels.get(position).getName(), 600);
//            }
//        });
//
////        //내용에서 찜하기 클릭
////        adapterAllPageDesc.setDescZzimClick(new AdapterAllTabDesc.DescZzimClick() {
////            @Override
////            public void onClick(int position) {
////
////                //찜 안된 상태 -> 찜 하기
////                if(!descItemModels.get(position).isZim()){
////
////                    if(SharedPreferenceUtil.getInstance(activitySearch).getIntExtra("PortZzimCount") >= 25) {
////                        //초과시 토스트
////                        toastZzimLimit.show();
////                    }else {
////                        ItemZzim(descItemModels.get(position).getCode(), position, 0, 2, descItemModels.get(position).isDam(), true);
////                    }
////                }
////                //찜한 상태 -> 찜 풀기
////                else{
////                    ItemZzim(descItemModels.get(position).getCode(), position, 1, 2,  descItemModels.get(position).isDam(), false);
////                }
////
////            }
////        });
//
//
//        //내용에서 전체보기 클릭시 상세내용으로 이동
//        adapterAllPageDesc.setDescAddviewClick(new AdapterAllTabDesc.DescAddviewClick() {
//            @Override
//            public void onClick(int position) {
//                MovedTab.putInt("allPage_MovedTab", 1);
//                RxEventBus.getInstance().post(new RxEvent(RxEvent.SEARCH_TRANS_PAGE, MovedTab));
//            }
//        });
//
//        //종목에서 상세페이지로 이동
//        adapterAllPageStock.setStockItemClick(new AdapterAllTabStock.StockItemClick() {
//            @Override
//            public void onClick(int position) {
//                MovedDetailPage(stockItemModels.get(position).getCode(), stockItemModels.get(position).getName(), 600);
//            }
//        });
//
//
////        //종목에서 찜하기 클릭
////        adapterAllPageStock.setStockZzimClick(new AdapterAllTabStock.StockZzimClick() {
////            @Override
////            public void onClick(int position) {
////
////                //찜 안된 상태 -> 찜 하기
////                if(!stockItemModels.get(position).isZim()){
////
////                    if(SharedPreferenceUtil.getInstance(activitySearch).getIntExtra("PortZzimCount") >= 25) {
////                        //초과시 토스트
////                        toastZzimLimit.show();
////                    }else {
////                        ItemZzim(stockItemModels.get(position).getCode(), position, 0, 3,  stockItemModels.get(position).isDam(), true);
////                    }
////                }
////                //찜한 상태 -> 찜 풀기
////                else{
////                    ItemZzim(stockItemModels.get(position).getCode(), position, 1, 3, stockItemModels.get(position).isDam(), false);
////                }
////            }
////        });
//
//        //종목에서 전체보기 클릭시 상세종목으로 이동
//        adapterAllPageStock.setStockAddviewClick(new AdapterAllTabStock.StockAddviewClick() {
//            @Override
//            public void onClick(int position) {
//                MovedTab.putInt("allPage_MovedTab", 2);
//                RxEventBus.getInstance().post(new RxEvent(RxEvent.SEARCH_TRANS_PAGE, MovedTab));
//            }
//        });
//
////        //검색 데이터가 없을때 검색 제안 키워드 클릭시 검색됨
////        adapterAllPageTitle.setEmptyTextClick(new AdapterAllTabTitle.EmptyTextClick() {
////            @Override
////            public void onClick(int position) {
////
////                if(onClickEmptyText != null) {
////                    onClickEmptyText.onReceivedData(emptyItemModels.get(position).getName());
////                }
////            }
////        });
//
//    }//onViewCreated 끝
//
//
//    // 각 카테고리에서 상세페이지로 이동
//    void MovedDetailPage(String portCode, String portName, int requestCode){
//
//        Intent intent = new Intent(getActivity(), ActivitySingleDetail.class);
//        intent.putExtra("potCode", portCode);
//        intent.putExtra("detailcode", portCode);
//        intent.putExtra("detailtitle", portName);
//        startActivityForResult(intent, requestCode);
//
//        //최근 검색어 저장 이벤트
//        RoomDataInsert(portName, portCode);
//    }
//
//    // SelectedState : 0 -> 포트 찜하기 / 1 -> 포트 찜 해제
//    // SearchCategory : 1 -> 제목 / 2 -> 내용 / 3 -> 종목
////    void ItemZzim(String PortCode, int PortPosition, int SelectedState, int SearchCategory, boolean isDam, boolean isZim) {
////
////        Select select = new Select(PortCode, "",isDam, isZim, 0, "", 0, 0, "");
////
////
////        Call<ModelZimData> getSelectPort = RetrofitClient.getInstance().getService().getSelectedPortDate("application/json",select, 1,"add");
////        getSelectPort.enqueue(new Callback<ModelZimData>() {
////            @Override
////            public void onResponse(Call<ModelZimData> call, Response<ModelZimData> response) {
////                if(response.code() == 200) {
////                    if(response.body().getErrorcode() == 200){
////
////                        zzimInfo.putInt("search_category", SearchCategory);
////                        zzimInfo.putInt("search_zzim_position", PortPosition);
////                        zzimInfo.putInt("search_page", 0);
////                        zzimInfo.putString("search_code", PortCode);
////                        //찜하기
////                        if(SelectedState == 0) {
////                            zzimInfo.putBoolean("search_zzim_state", true);
////
////                            if(SearchCategory == 1){
////                                titleItemModels.get(PortPosition).setZim(true);
////                                adapterAllPageTitle.notifyItemChanged(PortPosition);
////                            }else if(SearchCategory == 2){
////                                descItemModels.get(PortPosition).setZim(true);
////                                adapterAllPageDesc.notifyItemChanged(PortPosition);
////                            }else{
////                                stockItemModels.get(PortPosition).setZim(true);
////                                adapterAllPageStock.notifyItemChanged(PortPosition);
////                            }
////                        }
////                        //찜 해제
////                        else{
////                            zzimInfo.putBoolean("search_zzim_state", false);
////
////                            if(SearchCategory == 1){
////                                titleItemModels.get(PortPosition).setZim(false);
////                                adapterAllPageTitle.notifyItemChanged(PortPosition);
////                            }else if(SearchCategory == 2){
////                                descItemModels.get(PortPosition).setZim(false);
////                                adapterAllPageDesc.notifyItemChanged(PortPosition);
////                            }else{
////                                stockItemModels.get(PortPosition).setZim(false);
////                                adapterAllPageStock.notifyItemChanged(PortPosition);
////                            }
////                        }
////                        RxEventBus.getInstance().post(new RxEvent(RxEvent.SEARCH_CLICK_ZZIM, zzimInfo));
////                    }
////                }
////                else{
////                    Log.e("에러 값 ","값 : "+ response.errorBody().toString());
////                }
////            }
////            @Override
////            public void onFailure(Call<ModelZimData> call, Throwable t) {
////                Toast.makeText(getActivity(),"네트워크가 불안정 합니다\n 다시 시도해 주세요.",Toast.LENGTH_SHORT).show();
////            }
////        });
////    }
//
//    //최근 검색어 저장 이벤트
//    void RoomDataInsert(String PortName, String PortCode){
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                roomDao = SearchRoomDatabase.getINSTANCE(getContext()).roomDao();
//                RoomSelectCode = roomDao.findCode(PortCode);
//                RoomAllData = roomDao.findAll();
//
//                //해당 포트에 대해서 Room에 저장된 동일한 데이터가 없을때
//                if(RoomSelectCode == null) {
//
//                    if(RoomAllData != null) {
//                        if (RoomAllData.size() <= 4) {
//                            searchViewModel.insert(new RoomEntity(PortName, PortCode));
//                        }else {
//                            searchViewModel.delete(RoomAllData.get(0));
//                            searchViewModel.insert(new RoomEntity(PortName, PortCode));
//                        }
//                    }
//                }
//            }
//        }).start();
//
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(requestCode == 600) {
//            if(resultCode == 500) {
//                String codeD = data.getStringExtra("search_code_D");
//
//                zzimInfo.putInt("search_category", 4);
//                zzimInfo.putInt("search_page", 0);
//                zzimInfo.putString("search_code", codeD);
//
//                zzimInfo.putBoolean("search_zzim_state", true);
//                RxEventBus.getInstance().post(new RxEvent(RxEvent.SEARCH_CLICK_ZZIM, zzimInfo));
//
//                DataManager.get_INstance().setCheckTab1(true);
//
//            }
//            else if(resultCode == 501) {
//                String codeD = data.getStringExtra("search_code_D");
//
//                zzimInfo.putInt("search_category", 4);
//                zzimInfo.putInt("search_page", 0);
//                zzimInfo.putString("search_code", codeD);
//
//                zzimInfo.putBoolean("search_zzim_state", false);
//                RxEventBus.getInstance().post(new RxEvent(RxEvent.SEARCH_CLICK_ZZIM, zzimInfo));
//
//                DataManager.get_INstance().setCheckTab1(true);
//            }
//        }
//
//    }
//
//
//
//
//    //검색결과 없을때 보여주는 추천 검색어 클릭 이벤트
//    public interface OnClickEmptyText {
//        void onReceivedData(String text);
//    }
//
//    //검색된 데이터에 따라서 리사이클러뷰를 숨김
//    void RecyclerViewState(int stateNum){
//        switch (stateNum) {
//            //제목만 없음
//            case 206:
//                binding.recyclerView1.setVisibility(View.GONE);
//                binding.recyclerView2.setVisibility(View.VISIBLE);
//                binding.recyclerView3.setVisibility(View.VISIBLE);
//                break;
//            //내용만 없음
//            case 205:
//                binding.recyclerView1.setVisibility(View.VISIBLE);
//                binding.recyclerView2.setVisibility(View.GONE);
//                binding.recyclerView3.setVisibility(View.VISIBLE);
//                break;
//            //종목만 없음
//            case 203:
//                binding.recyclerView1.setVisibility(View.VISIBLE);
//                binding.recyclerView2.setVisibility(View.VISIBLE);
//                binding.recyclerView3.setVisibility(View.GONE);
//                break;
//            //제목만 있음
//            case 201:
//                binding.recyclerView1.setVisibility(View.VISIBLE);
//                binding.recyclerView2.setVisibility(View.GONE);
//                binding.recyclerView3.setVisibility(View.GONE);
//                break;
//            //내용만 있음
//            case 202:
//                binding.recyclerView1.setVisibility(View.GONE);
//                binding.recyclerView2.setVisibility(View.VISIBLE);
//                binding.recyclerView3.setVisibility(View.GONE);
//                break;
//            //종목만 있음
//            case 204:
//                binding.recyclerView1.setVisibility(View.GONE);
//                binding.recyclerView2.setVisibility(View.GONE);
//                binding.recyclerView3.setVisibility(View.VISIBLE);
//                break;
//            // 전부 비었을때
//            case 208:
//                binding.recyclerView1.setVisibility(View.VISIBLE);
//                binding.recyclerView2.setVisibility(View.GONE);
//                binding.recyclerView3.setVisibility(View.GONE);
//                break;
//        }
//    }
//
////    //리사이클러뷰 초기화
//    void InitRecyclerView(){
//
//        binding.recyclerView1.setHasFixedSize(true);
//        binding.recyclerView2.setHasFixedSize(true);
//        binding.recyclerView3.setHasFixedSize(true);
//
//        layoutManagerT = new LinearLayoutManager(getContext());
//        layoutManagerD = new LinearLayoutManager(getContext());
//        layoutManagerS = new LinearLayoutManager(getContext());
//
//        binding.recyclerView1.setLayoutManager(layoutManagerT);
//        binding.recyclerView2.setLayoutManager(layoutManagerD);
//        binding.recyclerView3.setLayoutManager(layoutManagerS);
//
//        titleItemModels = new ArrayList<>();
//        descItemModels = new ArrayList<>();
//        stockItemModels = new ArrayList<>();
//        emptyItemModels = new ArrayList<>();
//
//        adapterAllPageTitle = new AdapterAllTabSingleEn(titleItemModels, getContext());
////        adapterAllPageTitle = new AdapterAllTabTitle(titleItemModels, emptyItemModels, getContext());
//        adapterAllPageDesc = new AdapterAllTabDesc(descItemModels, getContext());
//        adapterAllPageStock = new AdapterAllTabStock(stockItemModels, getContext());
//
//        binding.recyclerView1.setAdapter(adapterAllPageTitle);
//        binding.recyclerView2.setAdapter(adapterAllPageDesc);
//        binding.recyclerView3.setAdapter(adapterAllPageStock);
//    }
//}

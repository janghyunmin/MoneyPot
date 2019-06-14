package quantec.com.moneypot.Activity.Search.SearchedPage.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import quantec.com.moneypot.Activity.PotDetail.ActivityPotDetail;
import quantec.com.moneypot.Activity.Search.ActivitySearch;
import quantec.com.moneypot.Activity.Search.SearchedPage.Adapter.AdapterTitleTab;
import quantec.com.moneypot.DataManager.DataManager;
import quantec.com.moneypot.DataModel.dModel.ModelPostTitleItem;
import quantec.com.moneypot.Database.Room.Entry.RoomEntity;
import quantec.com.moneypot.Database.Room.Local.RoomDao;
import quantec.com.moneypot.Database.Room.Local.SearchRoomDatabase;
import quantec.com.moneypot.Database.Room.ViewModel.SearchViewModel;
import quantec.com.moneypot.R;
import quantec.com.moneypot.RxAndroid.RxEvent;
import quantec.com.moneypot.RxAndroid.RxEventBus;
import quantec.com.moneypot.util.SharedPreference.SharedPreferenceUtil;

public class FgTitleTab extends Fragment {

    ActivitySearch activitySearch;

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    AdapterTitleTab adapterTitlePage;
    ArrayList<ModelPostTitleItem> postTitleItemModels;

    Bundle bundle, zzimInfo;

    Toast toastZzimLimit;

    private SearchViewModel searchViewModel;

    private RoomDao roomDao;
    private RoomEntity RoomSelectCode;
    private List<RoomEntity> RoomAllData;

    public FgTitleTab() {
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
        postTitleItemModels = new ArrayList<>();

        adapterTitlePage = new AdapterTitleTab(postTitleItemModels, getContext());
        recyclerView.setAdapter(adapterTitlePage);

        bundle = getArguments();
        zzimInfo = new Bundle();

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

        postTitleItemModels.addAll(bundle.getParcelableArrayList("post_title_list"));
        adapterTitlePage.notifyDataSetChanged();

        //커스텀 토스트 메시지
        View toastView = View.inflate(getContext(), R.layout.dialog_toast_zzim_count_max, null);
        toastZzimLimit = new Toast(getContext());
        toastZzimLimit.setView(toastView);
        toastZzimLimit.setDuration(Toast.LENGTH_SHORT);

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

                                //통합페이지에서 찜 이벤트 있는 경우
                                if(page == 0) {

                                    //통합페이지에서 내용 찜 이벤트가 있는 경우
                                    if(rxEvent.getBundle().getInt("search_category") == 1) {
                                        if (rxEvent.getBundle().getBoolean("search_zzim_state")) {

                                            for(int a = 0 ; a < postTitleItemModels.size() ; a++) {
                                                if(postTitleItemModels.get(a).getCode().equals(code)) {
                                                    postTitleItemModels.get(a).setZim(true);
                                                    adapterTitlePage.notifyItemChanged(a);
                                                }
                                            }
                                        } else {

                                            for(int a = 0 ; a < postTitleItemModels.size() ; a++) {
                                                if(postTitleItemModels.get(a).getCode().equals(code)) {
                                                    postTitleItemModels.get(a).setZim(false);
                                                    adapterTitlePage.notifyItemChanged(a);
                                                }
                                            }
                                        }
                                    }
                                    //통합페이지에서 내용 외 찜 이벤트가 있는경우 ( 중복되는 포트에 대해서 찜 이벤트 실행 )
                                    else {

                                        if (rxEvent.getBundle().getBoolean("search_zzim_state")) {
                                            for(int a = 0 ; a < postTitleItemModels.size() ; a++) {
                                                if(postTitleItemModels.get(a).getCode().equals(code)) {
                                                    postTitleItemModels.get(a).setZim(true);
                                                    adapterTitlePage.notifyItemChanged(a);
                                                }
                                            }
                                        } else {
                                            for(int a = 0 ; a < postTitleItemModels.size() ; a++) {
                                                if(postTitleItemModels.get(a).getCode().equals(code)) {
                                                    postTitleItemModels.get(a).setZim(false);
                                                    adapterTitlePage.notifyItemChanged(a);
                                                }
                                            }
                                        }
                                    }
                                }
                                //각 페이지에서 찜 이벤트가 있는 경우
                                else if(page == 1) {

                                    if (rxEvent.getBundle().getBoolean("search_zzim_state")) {
                                        for(int a = 0 ; a < postTitleItemModels.size() ; a++) {
                                            if(postTitleItemModels.get(a).getCode().equals(code)) {
                                                postTitleItemModels.get(a).setZim(true);
                                                adapterTitlePage.notifyItemChanged(a);
                                            }
                                        }
                                    } else {
                                        for(int a = 0 ; a < postTitleItemModels.size() ; a++) {
                                            if(postTitleItemModels.get(a).getCode().equals(code)) {
                                                postTitleItemModels.get(a).setZim(false);
                                                adapterTitlePage.notifyItemChanged(a);
                                            }
                                        }
                                    }
                                }
                                break;
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                    }
                    @Override
                    public void onComplete() {
                    }
                });

//        찜하기 클릭
        adapterTitlePage.setTitlePageZzimClicke(new AdapterTitleTab.TitlePageZzimClicke() {
            @Override
            public void onClick(int position) {
                //찜 안된 상태 -> 찜 하기
                if(postTitleItemModels.get(position).isZim()){

                    if(SharedPreferenceUtil.getInstance(activitySearch).getIntExtra("PortZzimCount") >= 25) {
                        //초과시 토스트
                        toastZzimLimit.show();
                    }else {
                        ItemZzim(postTitleItemModels.get(position).getCode(), position, 0);
                    }
                }
                //찜한 상태 -> 찜 풀기
                else{
                    ItemZzim(postTitleItemModels.get(position).getCode(), position, 1);
                }
            }
        });

//        상세 페이지로 이동
        adapterTitlePage.setTitlePageItemClick(new AdapterTitleTab.TitlePageItemClick() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getActivity(), ActivityPotDetail.class);
                intent.putExtra("detailcode", postTitleItemModels.get(position).getCode());
                intent.putExtra("detailtitle", postTitleItemModels.get(position).getName());
                startActivityForResult(intent, 600);

                RoomDataInsert(postTitleItemModels.get(position).getName(), postTitleItemModels.get(position).getCode());
            }
        });

    }//onViewCreate 끝

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

                zzimInfo.putInt("search_category", 4);
                zzimInfo.putInt("search_page", 1);
                zzimInfo.putInt("search_code", codeD);

                zzimInfo.putBoolean("search_zzim_state", true);
                RxEventBus.getInstance().post(new RxEvent(RxEvent.SEARCH_CLICK_ZZIM, zzimInfo));

                DataManager.get_INstance().setCheckTab1(true);
            }
            else if(resultCode == 501) {
                int codeD = data.getIntExtra("search_code_D",0);

                zzimInfo.putInt("search_category", 4);
                zzimInfo.putInt("search_page", 1);
                zzimInfo.putInt("search_code", codeD);

                zzimInfo.putBoolean("search_zzim_state", false);
                RxEventBus.getInstance().post(new RxEvent(RxEvent.SEARCH_CLICK_ZZIM, zzimInfo));

                DataManager.get_INstance().setCheckTab1(true);
            }
        }
    }
}

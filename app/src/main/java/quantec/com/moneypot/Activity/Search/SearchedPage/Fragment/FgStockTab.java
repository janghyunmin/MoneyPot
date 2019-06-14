package quantec.com.moneypot.Activity.Search.SearchedPage.Fragment;
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
import quantec.com.moneypot.Activity.Search.SearchedPage.Adapter.AdapterStockTab;
import quantec.com.moneypot.DataManager.DataManager;
import quantec.com.moneypot.DataModel.dModel.ModelPostStockItem;
import quantec.com.moneypot.DataModel.dModel.ModelStockItem;
import quantec.com.moneypot.Database.Room.Entry.RoomEntity;
import quantec.com.moneypot.Database.Room.Local.RoomDao;
import quantec.com.moneypot.Database.Room.Local.SearchRoomDatabase;
import quantec.com.moneypot.Database.Room.ViewModel.SearchViewModel;
import quantec.com.moneypot.R;
import quantec.com.moneypot.RxAndroid.RxEvent;
import quantec.com.moneypot.RxAndroid.RxEventBus;
import quantec.com.moneypot.databinding.ItemAlltabStockBinding;
import quantec.com.moneypot.util.SharedPreference.SharedPreferenceUtil;

public class FgStockTab extends Fragment {

    ActivitySearch portSearchPageActivity;

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ArrayList<ModelPostStockItem> postStockItemModels;
    AdapterStockTab adapterStockPage;

    Bundle bundle, zzimInfo;
    Toast toastZzimLimit;

    private SearchViewModel searchViewModel;

    private RoomDao roomDao;
    private RoomEntity RoomSelectCode;
    private List<RoomEntity> RoomAllData;

    public FgStockTab() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_stocktab, container, false);

        //뷰 초기화
        initializeViews();

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        postStockItemModels = new ArrayList<>();

        adapterStockPage = new AdapterStockTab(postStockItemModels, getContext());
        recyclerView.setAdapter(adapterStockPage);

        bundle = getArguments();

        zzimInfo = new Bundle();

        searchViewModel = ViewModelProviders.of(getActivity()).get(SearchViewModel.class);

        return view;
    }

    private void initializeViews(){
        portSearchPageActivity = (ActivitySearch) getActivity();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof ActivitySearch) {
            portSearchPageActivity = (ActivitySearch) context;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        postStockItemModels.addAll(bundle.getParcelableArrayList("post_stock_list"));
        adapterStockPage.notifyDataSetChanged();

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

                                    //통합페이지에서 종목 찜 이벤트가 있는 경우
                                    if(rxEvent.getBundle().getInt("search_category") == 3) {
                                        if (rxEvent.getBundle().getBoolean("search_zzim_state")) {
                                            for(int a = 0 ; a < postStockItemModels.size() ; a++) {
                                                if(postStockItemModels.get(a).getCode().equals(code)) {
                                                    postStockItemModels.get(a).setZim(true);
                                                    adapterStockPage.notifyItemChanged(a);
                                                }
                                            }
                                        } else {
                                            for(int a = 0 ; a < postStockItemModels.size() ; a++) {
                                                if(postStockItemModels.get(a).getCode().equals(code)) {
                                                    postStockItemModels.get(a).setZim(false);
                                                    adapterStockPage.notifyItemChanged(a);
                                                }
                                            }
                                        }
                                    }
                                    //통합페이지에서 종목 외 찜 이벤트가 있는경우 ( 중복되는 포트에 대해서 찜 이벤트 실행 )
                                    else {

                                        if (rxEvent.getBundle().getBoolean("search_zzim_state")) {
                                            for(int a = 0 ; a < postStockItemModels.size() ; a++) {
                                                if(postStockItemModels.get(a).getCode().equals(code)) {
                                                    postStockItemModels.get(a).setZim(true);
                                                    adapterStockPage.notifyItemChanged(a);
                                                }
                                            }
                                        } else {
                                            for(int a = 0 ; a < postStockItemModels.size() ; a++) {
                                                if(postStockItemModels.get(a).getCode().equals(code)) {
                                                    postStockItemModels.get(a).setZim(false);
                                                    adapterStockPage.notifyItemChanged(a);
                                                }
                                            }
                                        }
                                    }
                                }
                                //각 페이지에서 찜 이벤트가 있는 경우
                                else if(page == 1) {

                                    if (rxEvent.getBundle().getBoolean("search_zzim_state")) {
                                        for(int a = 0 ; a < postStockItemModels.size() ; a++) {
                                            if(postStockItemModels.get(a).getCode().equals(code)) {
                                                postStockItemModels.get(a).setZim(true);
                                                adapterStockPage.notifyItemChanged(a);
                                            }
                                        }
                                    } else {
                                        for(int a = 0 ; a < postStockItemModels.size() ; a++) {
                                            if(postStockItemModels.get(a).getCode().equals(code)) {
                                                postStockItemModels.get(a).setZim(true);
                                                adapterStockPage.notifyItemChanged(a);
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

        //상세페이지로 이동
        adapterStockPage.setStockPageItemClick(new AdapterStockTab.StockPageItemClick() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getActivity(), ActivityPotDetail.class);
                intent.putExtra("detailcode", postStockItemModels.get(position).getCode());
                intent.putExtra("detailtitle", postStockItemModels.get(position).getName());
                startActivityForResult(intent, 600);

                RoomDataInsert(postStockItemModels.get(position).getName(), postStockItemModels.get(position).getCode());
            }
        });

        //찜 클릭
        adapterStockPage.setStockPageZzimClick(new AdapterStockTab.StockPageZzimClick() {
            @Override
            public void onClick(int position) {

                //찜 안된 상태 -> 찜 하기
                if(postStockItemModels.get(position).isZim()){
                    if(SharedPreferenceUtil.getInstance(portSearchPageActivity).getIntExtra("PortZzimCount") >= 25) {
                        //초과시 토스트
                        toastZzimLimit.show();
                    }else {
                        ItemZzim(postStockItemModels.get(position).getCode(), position, 0);
                    }
                }
                //찜한 상태 -> 찜 풀기
                else{
                    ItemZzim(postStockItemModels.get(position).getCode(), position, 1);
                }

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
//                    zzimInfo.putInt("search_category", 3);
//                    zzimInfo.putInt("search_zzim_position", PortPosition);
//                    zzimInfo.putInt("search_page", 1);
//                    zzimInfo.putInt("search_code", PortCode);
//                    //찜하기
//                    if(SelectedState == 0) {
//                        zzimInfo.putBoolean("search_zzim_state", true);
////                        postStockItemModels.get(PortPosition).setSelect(1);
//                    }
//                    //찜 해제
//                    else{
//                        zzimInfo.putBoolean("search_zzim_state", false);
////                        postStockItemModels.get(PortPosition).setSelect(0);
//                    }
////                    adapterStockPage.notifyItemChanged(PortPosition);
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
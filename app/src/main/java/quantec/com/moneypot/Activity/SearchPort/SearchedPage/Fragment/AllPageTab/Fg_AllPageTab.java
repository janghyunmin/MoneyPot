package quantec.com.moneypot.Activity.SearchPort.SearchedPage.Fragment.AllPageTab;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import quantec.com.moneypot.Activity.DetailPort.ActivityDetailPort;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab3.Fragment.Tab1_3m.Model.nModel.ModelZimData;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab3.Fragment.Tab1_3m.Select;
import quantec.com.moneypot.Activity.SearchPort.ActivitySearchPort;
import quantec.com.moneypot.Activity.SearchPort.SearchedPage.Fragment.AllPageTab.Adapter.AdapterAllPageDesc;
import quantec.com.moneypot.Activity.SearchPort.SearchedPage.Fragment.AllPageTab.Adapter.AdapterAllPageStock;
import quantec.com.moneypot.Activity.SearchPort.SearchedPage.Fragment.AllPageTab.Adapter.AdapterAllPageTitle;
import quantec.com.moneypot.Activity.SearchPort.SearchedPage.Fragment.AllPageTab.Model.nModel.ModelPortZzim;
import quantec.com.moneypot.Activity.SearchPort.SearchedPage.Model.dModel.ModelDescItem;
import quantec.com.moneypot.Activity.SearchPort.SearchedPage.Model.dModel.ModelEmptyItem;
import quantec.com.moneypot.Activity.SearchPort.SearchedPage.Model.dModel.ModelStockItem;
import quantec.com.moneypot.Activity.SearchPort.SearchedPage.Model.dModel.ModelTitleItem;
import quantec.com.moneypot.DataManager.DataManager;
import quantec.com.moneypot.Database.Room.Entry.RoomEntity;
import quantec.com.moneypot.Database.Room.Local.RoomDao;
import quantec.com.moneypot.Database.Room.Local.SearchRoomDatabase;
import quantec.com.moneypot.Database.Room.ViewModel.SearchViewModel;
import quantec.com.moneypot.Network.Retrofit.RetrofitClient;
import quantec.com.moneypot.R;
import quantec.com.moneypot.RxAndroid.RxEvent;
import quantec.com.moneypot.RxAndroid.RxEventBus;
import quantec.com.moneypot.Util.SharedPreferenceUtil.SharedPreferenceUtil;
import quantec.com.moneypot.databinding.FgActivitysearchAllpagetabBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fg_AllPageTab extends Fragment {

    AdapterAllPageTitle adapterAllPageTitle;
    AdapterAllPageDesc adapterAllPageDesc;
    AdapterAllPageStock adapterAllPageStock;

    RecyclerView.LayoutManager layoutManagerT, layoutManagerD, layoutManagerS;

    ArrayList<ModelTitleItem> titleItemModels;
    ArrayList<ModelDescItem> descItemModels;
    ArrayList<ModelStockItem> stockItemModels;
    ArrayList<ModelEmptyItem> emptyItemModels;

    ActivitySearchPort portSearchPageActivity;

    Bundle getSearchedData, MovedTab, zzimInfo;

    //검색 제안 데이터 전달
    private OnClickEmptyText onClickEmptyText;

    Toast toastZzimLimit;

    private SearchViewModel searchViewModel;

    private RoomDao roomDao;
    private RoomEntity RoomSelectCode;
    private List<RoomEntity> RoomAllData;

    FgActivitysearchAllpagetabBinding AllPageBinding;

    public Fg_AllPageTab() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AllPageBinding = DataBindingUtil.inflate(inflater, R.layout.fg_activitysearch_allpagetab, container, false);

        //뷰 초기화
        initializeViews();
        //리사이클러뷰 초기화
        InitRecyclerView();

        AllPageBinding.allPageTabTitleRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()){
            @Override
            public boolean canScrollVertically() { // 세로스크롤 막기
                return false;
            }
            @Override
            public boolean canScrollHorizontally() { //가로 스크롤막기
                return false;
            }
        });
        AllPageBinding.allPageTabDescRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()){
            @Override
            public boolean canScrollVertically() { // 세로스크롤 막기
                return false;
            }
            @Override
            public boolean canScrollHorizontally() { //가로 스크롤막기
                return false;
            }
        });
        AllPageBinding.allPageTabStockRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()){
            @Override
            public boolean canScrollVertically() { // 세로스크롤 막기
                return false;
            }
            @Override
            public boolean canScrollHorizontally() { //가로 스크롤막기
                return false;
            }
        });

        //검색시 Fragment_SearchPage에서 받은 데이터를 가져옴
        getSearchedData = getArguments();

        //전체보기시 클릭시 탭이동 구분
        // 0 : 제목 / 1 : 내용 / 2 : 종목
        MovedTab = new Bundle();
        zzimInfo = new Bundle();

        searchViewModel = ViewModelProviders.of(getActivity()).get(SearchViewModel.class);

        return AllPageBinding.getRoot();
    }//onCreateView 끝


    private void initializeViews(){
        portSearchPageActivity = (ActivitySearchPort) getActivity();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof ActivitySearchPort) {
            portSearchPageActivity = (ActivitySearchPort) context;
        }

        if(getActivity() != null && getActivity() instanceof OnClickEmptyText) {
            onClickEmptyText = (OnClickEmptyText) getActivity();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        titleItemModels.addAll(getSearchedData.getParcelableArrayList("title_list"));
        descItemModels.addAll(getSearchedData.getParcelableArrayList("desc_list"));
        stockItemModels.addAll(getSearchedData.getParcelableArrayList("stock_list"));
        emptyItemModels.addAll(getSearchedData.getParcelableArrayList("empty_list"));

        RecyclerViewState(getSearchedData.getInt("category_empty"));

        adapterAllPageTitle.notifyDataSetChanged();
        adapterAllPageDesc.notifyDataSetChanged();
        adapterAllPageStock.notifyDataSetChanged();

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
                                int category = rxEvent.getBundle().getInt("search_category");

                                //통합페이지에서 찜 이벤트 있을때
                                if(page == 0) {
                                    //제목에서 찜 이벤트 있을때
                                    if (category == 1) {
                                        //찜 체크됨
                                        if (rxEvent.getBundle().getBoolean("search_zzim_state")) {
                                            for (int a = 0; a < descItemModels.size(); a++) {
                                                if (descItemModels.get(a).getCode().equals(code)) {
                                                    descItemModels.get(a).setZim(true);
                                                    adapterAllPageDesc.notifyItemChanged(a);
                                                    break;
                                                }
                                            }
                                            for (int b = 0; b < stockItemModels.size(); b++) {
                                                if (stockItemModels.get(b).getCode().equals(code)) {
                                                    stockItemModels.get(b).setZim(true);
                                                    adapterAllPageStock.notifyItemChanged(b);
                                                    break;
                                                }
                                            }
                                        }
                                        //찜 헤제됨
                                        else {
                                            for (int a = 0; a < descItemModels.size(); a++) {
                                                if (descItemModels.get(a).getCode().equals(code)) {
                                                    descItemModels.get(a).setZim(false);
                                                    adapterAllPageDesc.notifyItemChanged(a);
                                                    break;
                                                }
                                            }
                                            for (int b = 0; b < stockItemModels.size(); b++) {
                                                if (stockItemModels.get(b).getCode().equals(code)) {
                                                    stockItemModels.get(b).setZim(false);
                                                    adapterAllPageStock.notifyItemChanged(b);
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                    //내용에서 찜 이벤트 있을때
                                    else if (category == 2) {
                                        //찜 체크됨
                                        if (rxEvent.getBundle().getBoolean("search_zzim_state")) {
                                            for (int a = 0; a < titleItemModels.size(); a++) {
                                                if (titleItemModels.get(a).getCode().equals(code)) {
                                                    titleItemModels.get(a).setZim(true);
                                                    adapterAllPageTitle.notifyItemChanged(a);
                                                    break;
                                                }
                                            }
                                            for (int b = 0; b < stockItemModels.size(); b++) {
                                                if (stockItemModels.get(b).getCode().equals(code)) {
                                                    stockItemModels.get(b).setZim(true);
                                                    adapterAllPageStock.notifyItemChanged(b);
                                                    break;
                                                }
                                            }
                                        }
                                        //찜 헤제됨
                                        else {
                                            for (int a = 0; a < titleItemModels.size(); a++) {
                                                if (titleItemModels.get(a).getCode().equals(code)) {
                                                    titleItemModels.get(a).setZim(false);
                                                    adapterAllPageTitle.notifyItemChanged(a);
                                                    break;
                                                }
                                            }
                                            for (int b = 0; b < stockItemModels.size(); b++) {
                                                if (stockItemModels.get(b).getCode().equals(code)) {
                                                    stockItemModels.get(b).setZim(false);
                                                    adapterAllPageStock.notifyItemChanged(b);
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                    //종목에서 찜 이벤트 있을때
                                    else if (category == 3) {
                                        //찜 체크됨
                                        if (rxEvent.getBundle().getBoolean("search_zzim_state")) {
                                            for (int a = 0; a < titleItemModels.size(); a++) {
                                                if (titleItemModels.get(a).getCode().equals(code)) {
                                                    titleItemModels.get(a).setZim(true);
                                                    adapterAllPageTitle.notifyItemChanged(a);
                                                    break;
                                                }
                                            }
                                            for (int b = 0; b < descItemModels.size(); b++) {
                                                if (descItemModels.get(b).getCode().equals(code)) {
                                                    descItemModels.get(b).setZim(true);
                                                    adapterAllPageDesc.notifyItemChanged(b);
                                                    break;
                                                }
                                            }
                                        }
                                        //찜 헤제됨
                                        else {
                                            for (int a = 0; a < titleItemModels.size(); a++) {
                                                if (titleItemModels.get(a).getCode().equals(code)) {
                                                    titleItemModels.get(a).setZim(false);
                                                    adapterAllPageTitle.notifyItemChanged(a);
                                                    break;
                                                }
                                            }
                                            for (int b = 0; b < descItemModels.size(); b++) {
                                                if (descItemModels.get(b).getCode().equals(code)) {
                                                    descItemModels.get(b).setZim(false);
                                                    adapterAllPageDesc.notifyItemChanged(b);
                                                    break;
                                                }
                                            }
                                        }

                                    }
                                    //상세페이지에서 찜 이벤트 후 전달
                                    else if(category == 4) {

                                        //찜 체크됨
                                        if (rxEvent.getBundle().getBoolean("search_zzim_state")) {
                                            for (int a = 0; a < titleItemModels.size(); a++) {
                                                if (titleItemModels.get(a).getCode().equals(code)) {
                                                    titleItemModels.get(a).setZim(true);
                                                    adapterAllPageTitle.notifyItemChanged(a);
                                                    break;
                                                }
                                            }
                                            for (int b = 0; b < descItemModels.size(); b++) {
                                                if (descItemModels.get(b).getCode().equals(code)) {
                                                    descItemModels.get(b).setZim(true);
                                                    adapterAllPageDesc.notifyItemChanged(b);
                                                    break;
                                                }
                                            }
                                            for (int c = 0; c < stockItemModels.size(); c++) {
                                                if (stockItemModels.get(c).getCode().equals(code)) {
                                                    stockItemModels.get(c).setZim(true);
                                                    adapterAllPageStock.notifyItemChanged(c);
                                                    break;
                                                }
                                            }
                                        }
                                        //찜 헤제됨
                                        else {
                                            for (int a = 0; a < titleItemModels.size(); a++) {
                                                if (titleItemModels.get(a).getCode().equals(code)) {
                                                    titleItemModels.get(a).setZim(false);
                                                    adapterAllPageTitle.notifyItemChanged(a);
                                                    break;
                                                }
                                            }
                                            for (int b = 0; b < descItemModels.size(); b++) {
                                                if (descItemModels.get(b).getCode().equals(code)) {
                                                    descItemModels.get(b).setZim(false);
                                                    adapterAllPageDesc.notifyItemChanged(b);
                                                    break;
                                                }
                                            }
                                            for (int c = 0; c < stockItemModels.size(); c++) {
                                                if (stockItemModels.get(c).getCode().equals(code)) {
                                                    stockItemModels.get(c).setZim(false);
                                                    adapterAllPageStock.notifyItemChanged(c);
                                                    break;
                                                }
                                            }
                                        }

                                    }

                                }
                                //각 페이지에서 찜 이벤트 있을때
                                else if(page == 1){
                                    //찜 체크됨
                                    if (rxEvent.getBundle().getBoolean("search_zzim_state")) {
                                        for (int a = 0; a < titleItemModels.size(); a++) {
                                            if (titleItemModels.get(a).getCode().equals(code)) {
                                                titleItemModels.get(a).setZim(true);
                                                adapterAllPageTitle.notifyItemChanged(a);
                                                break;
                                            }
                                        }
                                        for (int b = 0; b < descItemModels.size(); b++) {
                                            if (descItemModels.get(b).getCode().equals(code)) {
                                                descItemModels.get(b).setZim(true);
                                                adapterAllPageDesc.notifyItemChanged(b);
                                                break;
                                            }
                                        }
                                        for (int c = 0; c < stockItemModels.size(); c++) {
                                            if (stockItemModels.get(c).getCode().equals(code)) {
                                                stockItemModels.get(c).setZim(true);
                                                adapterAllPageStock.notifyItemChanged(c);
                                                break;
                                            }
                                        }
                                    }
                                    //찜 헤제됨
                                    else {
                                        for (int a = 0; a < titleItemModels.size(); a++) {
                                            if (titleItemModels.get(a).getCode().equals(code)) {
                                                titleItemModels.get(a).setZim(false);
                                                adapterAllPageTitle.notifyItemChanged(a);
                                                break;
                                            }
                                        }
                                        for (int b = 0; b < descItemModels.size(); b++) {
                                            if (descItemModels.get(b).getCode().equals(code)) {
                                                descItemModels.get(b).setZim(false);
                                                adapterAllPageDesc.notifyItemChanged(b);
                                                break;
                                            }
                                        }
                                        for (int c = 0; c < stockItemModels.size(); c++) {
                                            if (stockItemModels.get(c).getCode().equals(code)) {
                                                stockItemModels.get(c).setZim(false);
                                                adapterAllPageStock.notifyItemChanged(c);
                                                break;
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


        //제목에서 상세 페이지로 이동
        adapterAllPageTitle.setTitleItemClick(new AdapterAllPageTitle.TitleItemClick() {
            @Override
            public void onClick(int position) {
                MovedDetailPage(titleItemModels.get(position).getCode(), titleItemModels.get(position).getName(), 600);
            }
        });


        //제목에서 찜하기 클릭
        adapterAllPageTitle.setTitleZzimClick(new AdapterAllPageTitle.TitleZzimClick() {
            @Override
            public void onClick(int position) {

                    //찜 안된 상태 -> 찜 하기
                    if(!titleItemModels.get(position).isZim()){

                            if(SharedPreferenceUtil.getInstance(portSearchPageActivity).getIntExtra("PortZzimCount") >= 25) {
                                //초과시 토스트
                                toastZzimLimit.show();
                            }else {
                                ItemZzim(titleItemModels.get(position).getCode(), position, 0, 1, titleItemModels.get(position).isDam(), true);
                            }
                    }
                    //찜한 상태 -> 찜 풀기
                    else{
                        ItemZzim(titleItemModels.get(position).getCode(), position, 1, 1, titleItemModels.get(position).isDam(), false);
                    }
            }
        });


        //제목에서 전체보기 클릭시 상세제목으로 이동
        adapterAllPageTitle.setTitleAddViewClick(new AdapterAllPageTitle.TitleAddViewClick() {
            @Override
            public void onClick(int position) {
                MovedTab.putInt("allPage_MovedTab", 0);
                RxEventBus.getInstance().post(new RxEvent(RxEvent.SEARCH_TRANS_PAGE, MovedTab));
            }
        });


        //내용에서 상세페이지로 이동
        adapterAllPageDesc.setDescItemClick(new AdapterAllPageDesc.DescItemClick() {
            @Override
            public void onClick(int position) {
                MovedDetailPage(descItemModels.get(position).getCode(), descItemModels.get(position).getName(), 600);
            }
        });


        //내용에서 찜하기 클릭
        adapterAllPageDesc.setDescZzimClick(new AdapterAllPageDesc.DescZzimClick() {
            @Override
            public void onClick(int position) {

                //찜 안된 상태 -> 찜 하기
                if(!descItemModels.get(position).isZim()){

                    if(SharedPreferenceUtil.getInstance(portSearchPageActivity).getIntExtra("PortZzimCount") >= 25) {
                        //초과시 토스트
                        toastZzimLimit.show();
                    }else {
                        ItemZzim(descItemModels.get(position).getCode(), position, 0, 2, descItemModels.get(position).isDam(), true);
                    }
                }
                //찜한 상태 -> 찜 풀기
                else{
                    ItemZzim(descItemModels.get(position).getCode(), position, 1, 2,  descItemModels.get(position).isDam(), false);
                }

            }
        });


        //내용에서 전체보기 클릭시 상세내용으로 이동
        adapterAllPageDesc.setDescAddviewClick(new AdapterAllPageDesc.DescAddviewClick() {
            @Override
            public void onClick(int position) {
                MovedTab.putInt("allPage_MovedTab", 1);
                RxEventBus.getInstance().post(new RxEvent(RxEvent.SEARCH_TRANS_PAGE, MovedTab));
            }
        });

        //종목에서 상세페이지로 이동
        adapterAllPageStock.setStockItemClick(new AdapterAllPageStock.StockItemClick() {
            @Override
            public void onClick(int position) {
                MovedDetailPage(stockItemModels.get(position).getCode(), stockItemModels.get(position).getName(), 600);
            }
        });


        //종목에서 찜하기 클릭
        adapterAllPageStock.setStockZzimClick(new AdapterAllPageStock.StockZzimClick() {
            @Override
            public void onClick(int position) {

                //찜 안된 상태 -> 찜 하기
                if(!stockItemModels.get(position).isZim()){

                    if(SharedPreferenceUtil.getInstance(portSearchPageActivity).getIntExtra("PortZzimCount") >= 25) {
                        //초과시 토스트
                        toastZzimLimit.show();
                    }else {
                        ItemZzim(stockItemModels.get(position).getCode(), position, 0, 3,  stockItemModels.get(position).isDam(), true);
                    }
                }
                //찜한 상태 -> 찜 풀기
                else{
                    ItemZzim(stockItemModels.get(position).getCode(), position, 1, 3, stockItemModels.get(position).isDam(), false);
                }
            }
        });

        //종목에서 전체보기 클릭시 상세종목으로 이동
        adapterAllPageStock.setStockAddviewClick(new AdapterAllPageStock.StockAddviewClick() {
            @Override
            public void onClick(int position) {
                MovedTab.putInt("allPage_MovedTab", 2);
                RxEventBus.getInstance().post(new RxEvent(RxEvent.SEARCH_TRANS_PAGE, MovedTab));
            }
        });


        //검색 데이터가 없을때 검색 제안 키워드 클릭시 검색됨
        adapterAllPageTitle.setEmptyTextClick(new AdapterAllPageTitle.EmptyTextClick() {
            @Override
            public void onClick(int position) {

                if(onClickEmptyText != null) {
                    onClickEmptyText.onReceivedData(emptyItemModels.get(position).getName());
                }
            }
        });

    }//onViewCreated 끝


    // 각 카테고리에서 상세페이지로 이동
    void MovedDetailPage(String portCode, String portName, int requestCode){

        Intent intent = new Intent(getActivity(), ActivityDetailPort.class);
        intent.putExtra("detailcode", portCode);
        intent.putExtra("detailtitle", portName);
        startActivityForResult(intent, requestCode);

        //최근 검색어 저장 이벤트
        RoomDataInsert(portName, portCode);
    }

    // SelectedState : 0 -> 포트 찜하기 / 1 -> 포트 찜 해제
    // SearchCategory : 1 -> 제목 / 2 -> 내용 / 3 -> 종목
    void ItemZzim(String PortCode, int PortPosition, int SelectedState, int SearchCategory, boolean isDam, boolean isZim) {

            Select select = new Select(PortCode, isDam, isZim);

            Call<ModelZimData> getSelectPort = RetrofitClient.getInstance().getService().getSelectedPortDate("application/json",select, 1,"add");
            getSelectPort.enqueue(new Callback<ModelZimData>() {
                @Override
                public void onResponse(Call<ModelZimData> call, Response<ModelZimData> response) {
                    if(response.code() == 200) {
                        if(response.body().getErrorcode() == 200){

                            zzimInfo.putInt("search_category", SearchCategory);
                            zzimInfo.putInt("search_zzim_position", PortPosition);
                            zzimInfo.putInt("search_page", 0);
                            zzimInfo.putString("search_code", PortCode);
                            //찜하기
                            if(SelectedState == 0) {
                                zzimInfo.putBoolean("search_zzim_state", true);

                                    if(SearchCategory == 1){
                                        titleItemModels.get(PortPosition).setZim(true);
                                        adapterAllPageTitle.notifyItemChanged(PortPosition);
                                    }else if(SearchCategory == 2){
                                        descItemModels.get(PortPosition).setZim(true);
                                        adapterAllPageDesc.notifyItemChanged(PortPosition);
                                    }else{
                                        stockItemModels.get(PortPosition).setZim(true);
                                        adapterAllPageStock.notifyItemChanged(PortPosition);
                                    }
                            }
                            //찜 해제
                            else{
                                zzimInfo.putBoolean("search_zzim_state", false);

                                if(SearchCategory == 1){
                                    titleItemModels.get(PortPosition).setZim(false);
                                    adapterAllPageTitle.notifyItemChanged(PortPosition);
                                }else if(SearchCategory == 2){
                                    descItemModels.get(PortPosition).setZim(false);
                                    adapterAllPageDesc.notifyItemChanged(PortPosition);
                                }else{
                                    stockItemModels.get(PortPosition).setZim(false);
                                    adapterAllPageStock.notifyItemChanged(PortPosition);
                                }
                            }
                            RxEventBus.getInstance().post(new RxEvent(RxEvent.SEARCH_CLICK_ZZIM, zzimInfo));
                        }
                    }
                    else{
                        Log.e("에러 값 ","값 : "+ response.errorBody().toString());
                    }
                }
                @Override
                public void onFailure(Call<ModelZimData> call, Throwable t) {
                    Toast.makeText(getActivity(),"네트워크가 불안정 합니다\n 다시 시도해 주세요.",Toast.LENGTH_SHORT).show();
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
                String codeD = data.getStringExtra("search_code_D");

                zzimInfo.putInt("search_category", 4);
                zzimInfo.putInt("search_page", 0);
                zzimInfo.putString("search_code", codeD);

                zzimInfo.putBoolean("search_zzim_state", true);
                RxEventBus.getInstance().post(new RxEvent(RxEvent.SEARCH_CLICK_ZZIM, zzimInfo));

                DataManager.get_INstance().setCheckTab1(true);

            }
            else if(resultCode == 501) {
                String codeD = data.getStringExtra("search_code_D");

                zzimInfo.putInt("search_category", 4);
                zzimInfo.putInt("search_page", 0);
                zzimInfo.putString("search_code", codeD);

                zzimInfo.putBoolean("search_zzim_state", false);
                RxEventBus.getInstance().post(new RxEvent(RxEvent.SEARCH_CLICK_ZZIM, zzimInfo));

                DataManager.get_INstance().setCheckTab1(true);
            }
        }

    }




    //검색결과 없을때 보여주는 추천 검색어 클릭 이벤트
    public interface OnClickEmptyText {
        void onReceivedData(String text);
    }

    //검색된 데이터에 따라서 리사이클러뷰를 숨김
    void RecyclerViewState(int stateNum){
        switch (stateNum) {
            //제목만 없음
            case 206:
                AllPageBinding.allPageTabTitleRecyclerView.setVisibility(View.GONE);
                AllPageBinding.allPageTabDescRecyclerView.setVisibility(View.VISIBLE);
                AllPageBinding.allPageTabStockRecyclerView.setVisibility(View.VISIBLE);
                break;
            //내용만 없음
            case 205:
                AllPageBinding.allPageTabTitleRecyclerView.setVisibility(View.VISIBLE);
                AllPageBinding.allPageTabDescRecyclerView.setVisibility(View.GONE);
                AllPageBinding.allPageTabStockRecyclerView.setVisibility(View.VISIBLE);
                break;
            //종목만 없음
            case 203:
                AllPageBinding.allPageTabTitleRecyclerView.setVisibility(View.VISIBLE);
                AllPageBinding.allPageTabDescRecyclerView.setVisibility(View.VISIBLE);
                AllPageBinding.allPageTabStockRecyclerView.setVisibility(View.GONE);
                break;
            //제목만 있음
            case 201:
                AllPageBinding.allPageTabTitleRecyclerView.setVisibility(View.VISIBLE);
                AllPageBinding.allPageTabDescRecyclerView.setVisibility(View.GONE);
                AllPageBinding.allPageTabStockRecyclerView.setVisibility(View.GONE);
                break;
            //내용만 있음
            case 202:
                AllPageBinding.allPageTabTitleRecyclerView.setVisibility(View.GONE);
                AllPageBinding.allPageTabDescRecyclerView.setVisibility(View.VISIBLE);
                AllPageBinding.allPageTabStockRecyclerView.setVisibility(View.GONE);
                break;
            //종목만 있음
            case 204:
                AllPageBinding.allPageTabTitleRecyclerView.setVisibility(View.GONE);
                AllPageBinding.allPageTabDescRecyclerView.setVisibility(View.GONE);
                AllPageBinding.allPageTabStockRecyclerView.setVisibility(View.VISIBLE);
                break;
             // 전부 비었을때
            case 208:
                AllPageBinding.allPageTabTitleRecyclerView.setVisibility(View.VISIBLE);
                AllPageBinding.allPageTabDescRecyclerView.setVisibility(View.GONE);
                AllPageBinding.allPageTabStockRecyclerView.setVisibility(View.GONE);
                break;
        }
    }

    //리사이클러뷰 초기화
    void InitRecyclerView(){

        AllPageBinding.allPageTabTitleRecyclerView.setHasFixedSize(true);
        AllPageBinding.allPageTabDescRecyclerView.setHasFixedSize(true);
        AllPageBinding.allPageTabStockRecyclerView.setHasFixedSize(true);

        layoutManagerT = new LinearLayoutManager(getContext());
        layoutManagerD = new LinearLayoutManager(getContext());
        layoutManagerS = new LinearLayoutManager(getContext());

        AllPageBinding.allPageTabTitleRecyclerView.setLayoutManager(layoutManagerT);
        AllPageBinding.allPageTabDescRecyclerView.setLayoutManager(layoutManagerD);
        AllPageBinding.allPageTabStockRecyclerView.setLayoutManager(layoutManagerS);

        titleItemModels = new ArrayList<>();
        descItemModels = new ArrayList<>();
        stockItemModels = new ArrayList<>();
        emptyItemModels = new ArrayList<>();

        adapterAllPageTitle = new AdapterAllPageTitle(titleItemModels, emptyItemModels, getContext());
        adapterAllPageDesc = new AdapterAllPageDesc(descItemModels, getContext());
        adapterAllPageStock = new AdapterAllPageStock(stockItemModels, getContext());

        AllPageBinding.allPageTabTitleRecyclerView.setAdapter(adapterAllPageTitle);
        AllPageBinding.allPageTabDescRecyclerView.setAdapter(adapterAllPageDesc);
        AllPageBinding.allPageTabStockRecyclerView.setAdapter(adapterAllPageStock);
    }
}

package quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.Cookpage1;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import quantec.com.moneypot.Activity.AllCookList.ActivityAllCookList;
import quantec.com.moneypot.Activity.DetailPort.ActivityDetailPort;
import quantec.com.moneypot.Activity.Intro.ErrorPojoClass;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.Cookpage1.Adapter.AdapterHot;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.Cookpage1.Adapter.AdapterStable;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.Cookpage1.Fragment.Fg_NewItem;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.Cookpage1.Fragment.Fg_StoryImage;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.Cookpage1.Model.dModel.ModelHotList;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.Cookpage1.Model.dModel.ModelStableList;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.Cookpage1.Model.nModel.ModelCookpage1Item;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab3.Fragment.Tab1_3m.Model.nModel.ModelTab13mRank;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab3.Fragment.Tab1_3m.Model.nModel.ModelZimData;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab3.Fragment.Tab1_3m.Select;
import quantec.com.moneypot.Activity.Main.MainActivity;
import quantec.com.moneypot.Activity.SearchPort.SearchedPage.Fragment.AllPageTab.Model.nModel.ModelPortZzim;
import quantec.com.moneypot.DataManager.DataManager;
import quantec.com.moneypot.Network.Retrofit.RetrofitClient;
import quantec.com.moneypot.R;
import quantec.com.moneypot.RxAndroid.RxEvent;
import quantec.com.moneypot.RxAndroid.RxEventBus;
import quantec.com.moneypot.Util.SharedPreferenceUtil.SharedPreferenceUtil;
import quantec.com.moneypot.databinding.FgFgtab2Fgcookpage1Binding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fg_CookPage1 extends Fragment {

    LinearLayoutManager HotlayoutManager, StablelayoutManager;
    AdapterHot adapterHot;
    AdapterStable adapterStable;

    ArrayList<ModelHotList> modelHotLists;
    ArrayList<ModelStableList> modelStableLists;

    private MainActivity mainActivity;

    FragmentAdapter fragmentAdapter;
    AdapterNewItem adapterNewItem;

    FgFgtab2Fgcookpage1Binding fgcookpage1Binding;

    public Fg_CookPage1() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fgcookpage1Binding = DataBindingUtil.inflate(inflater, R.layout.fg_fgtab2_fgcookpage1, container, false);

        fgcookpage1Binding.pageIndicatorView.setCount(5); // specify total count of indicators
        fgcookpage1Binding.pageIndicatorView.setSelection(2);

        initializeViews();

        fragmentAdapter = new FragmentAdapter(getActivity().getSupportFragmentManager());
        fgcookpage1Binding.fgCookpage1StoryImageViewPager.setAdapter(fragmentAdapter);

        adapterNewItem = new AdapterNewItem(getActivity().getSupportFragmentManager());

        fgcookpage1Binding.fgCookpage1NewItemViewPager.setScrollDurationFactor(2);
        fgcookpage1Binding.fgCookpage1NewItemViewPager.setInterval(5000);
        fgcookpage1Binding.fgCookpage1NewItemViewPager.startAutoScroll();
        fgcookpage1Binding.fgCookpage1NewItemViewPager.setAdapter(adapterNewItem);

        fgcookpage1Binding.fgCookpage1StoryImageViewPager.setClipToPadding(false);
        fgcookpage1Binding.fgCookpage1StoryImageViewPager.setPadding(20,0,20,0);
        fgcookpage1Binding.fgCookpage1StoryImageViewPager.setPageMargin(getActivity().getResources().getDisplayMetrics().widthPixels / -30);

        fgcookpage1Binding.fgCookpage1HotRecyclerview.setHasFixedSize(true);
        fgcookpage1Binding.fgCookpage1StableRecyclerview.setHasFixedSize(true);

        HotlayoutManager = new LinearLayoutManager(getContext());
        fgcookpage1Binding.fgCookpage1HotRecyclerview.setLayoutManager(HotlayoutManager);

        StablelayoutManager = new LinearLayoutManager(getContext());
        fgcookpage1Binding.fgCookpage1StableRecyclerview.setLayoutManager(StablelayoutManager);

        modelHotLists = new ArrayList<>();
        modelStableLists = new ArrayList<>();

        adapterHot = new AdapterHot(modelHotLists, getContext());
        adapterStable = new AdapterStable(modelStableLists, getContext());

        fgcookpage1Binding.fgCookpage1HotRecyclerview.setAdapter(adapterHot);
        fgcookpage1Binding.fgCookpage1StableRecyclerview.setAdapter(adapterStable);

        return fgcookpage1Binding.getRoot();
    }

    private void initializeViews(){
        mainActivity = (MainActivity) getActivity();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof MainActivity) {
            mainActivity = (MainActivity) context;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initData();

//      FragmentAdapter에 Fragment 추가, Image 개수만큼 추가
        for (int i = 0; i < 4; i++) {
            Fg_StoryImage imageFragment = new Fg_StoryImage();
            Bundle bundle = new Bundle();
            bundle.putInt("imgRes",i);
            imageFragment.setArguments(bundle);
            fragmentAdapter.addItem(imageFragment);
        }
        fragmentAdapter.notifyDataSetChanged();

        for(int b = 0 ; b < 3 ; b++) {
            Fg_NewItem fg_newItem = new Fg_NewItem();
            Bundle bundle = new Bundle();
            bundle.putInt("imgRes",b);
            fg_newItem.setArguments(bundle);
            adapterNewItem.addItem(fg_newItem);
        }
        adapterNewItem.notifyDataSetChanged();

        fgcookpage1Binding.fgCookpage1NewItemViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                fgcookpage1Binding.pageIndicatorView.setSelection(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
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
                            case RxEvent.COOK_PAGE_BASKET:
                                if(rxEvent.getBundle().getInt("basket") == 1) {
                                    initData();
                                }
                                break;
                            case RxEvent.ZZIM_PORT_LOAD:

                                initData();
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

        //뜨거운 재료에서 재료 찜
        adapterHot.setHotZzimClick(new AdapterHot.HotZzimClick() {
            @Override
            public void onClick(int position) {
                //찜 하기
                if(!modelHotLists.get(position).isZim()) {
                    CookZzim(0, modelHotLists.get(position).getCode(), position, modelHotLists.get(position).isDam(),true, "add");
                }
                //찜 해제
                else{
                    CookZzim(0, modelHotLists.get(position).getCode(), position, modelHotLists.get(position).isDam(),false, "del");
                }
            }
        });

        //뜨거운재료에서 재료 담기
        adapterHot.setHotBasketClick(new AdapterHot.HotBasketClick() {
            @Override
            public void onClick(int position) {
                //basket : 0 -> 포트요리 홈에서 재료 담기 함 ( 내가 담은 포트 갯수 변경해줌 ) / 1 -> 내가 담은 포트에서 재료 삭제 함 ( 포트요리 홈에서 담은 포트 갯수 변경해줌 )
                //재료 담기
                if(!modelHotLists.get(position).isDam()) {
                    CookBasket(0, modelHotLists.get(position).getCode(), position, true, modelHotLists.get(position).isZim(), "add");
                }
                //재료 삭제
                else{
                    CookBasket(0, modelHotLists.get(position).getCode(), position, false, modelHotLists.get(position).isZim(), "del");
                }
            }
        });

        //뜨거운 재료에서 상세페이지 이동
        adapterHot.setHotItemClick(new AdapterHot.HotItemClick() {
            @Override
            public void onClick(int position) {
                MovedDetailPage(modelHotLists.get(position).getCode(), modelHotLists.get(position).getTitle(), position,600);
            }
        });

        //안정 재료에서 재료 담기
        adapterStable.setStableBasketClick(new AdapterStable.StableBasketClick() {
            @Override
            public void onClick(int position) {
                //재료 담기
                if(!modelStableLists.get(position).isDam()) {
                    CookBasket(2, modelStableLists.get(position).getCode(), position, true, modelStableLists.get(position).isZim(), "add");

                    Bundle bundle = new Bundle();
                    bundle.putInt("basket", 0);
                    RxEventBus.getInstance().post(new RxEvent(RxEvent.COOK_PAGE_BASKET, bundle));
                }
                //재료 삭제
                else{
                    CookBasket(2, modelStableLists.get(position).getCode(), position, false, modelStableLists.get(position).isZim(), "del");

                    Bundle bundle = new Bundle();
                    bundle.putInt("basket", 0);
                    RxEventBus.getInstance().post(new RxEvent(RxEvent.COOK_PAGE_BASKET, bundle));
                }
            }
        });

        //안정 재료에서 찜하기
        adapterStable.setStableZzimClick(new AdapterStable.StableZzimClick() {
            @Override
            public void onClick(int position) {
                //찜 하기
                if(!modelStableLists.get(position).isZim()) {
                    CookZzim(2, modelStableLists.get(position).getCode(), position, modelStableLists.get(position).isDam(),true, "add");
                }
                //찜 해제
                else{
                    CookZzim(2, modelStableLists.get(position).getCode(), position, modelStableLists.get(position).isDam(), false, "del");
                }
            }
        });

        //안정 재료에서 상세페이지 이동
        adapterStable.setStableItemClick(new AdapterStable.StableItemClick() {
            @Override
            public void onClick(int position) {
                MovedDetailPage(modelStableLists.get(position).getCode(), modelStableLists.get(position).getTitle(), position,700);
            }
        });

        fgcookpage1Binding.fgCookpage1HotRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()){
            @Override
            public boolean canScrollVertically() { // 세로스크롤 막기
                return false;
            }
        });

        fgcookpage1Binding.fgCookpage1StableRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()){
            @Override
            public boolean canScrollVertically() { // 세로스크롤 막기
                return false;
            }
        });

        //모든 재료 클릭
        fgcookpage1Binding.fgCookpage1AllListLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), ActivityAllCookList.class);
//                startActivity(intent);
                Toast.makeText(getActivity(), "준비중 입니다", Toast.LENGTH_SHORT).show();
            }
        });

    }//onViewCreated 끝

    // 각 카테고리에서 상세페이지 이동시 이벤트
    void MovedDetailPage(String PortCode,  String PortName, int PortPosition, int requestCode){
        Intent intent1 = new Intent(getActivity(), ActivityDetailPort.class);
        intent1.putExtra("detailcode", PortCode);
        intent1.putExtra("detailtitle",PortName);
        intent1.putExtra("ZzimPortPosition", PortPosition);
        startActivityForResult(intent1, requestCode);
    }

    //포트요리사들의 이야기 어답터
    class FragmentAdapter  extends FragmentPagerAdapter {
        // ViewPager에 들어갈 Fragment들을 담을 리스트
        private ArrayList<Fragment> fragments = new ArrayList<>();
        // 필수 생성자
        FragmentAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }
        @Override
        public int getCount() {
            return fragments.size();
        }
        // List에 Fragment를 담을 함수
        void addItem(Fragment fragment) {
            fragments.add(fragment);
        }
        @Override
        public float getPageWidth(int position) {
            return (0.9f);
        }
    }

    // 방금 나온 재료 배너 어답터
    class AdapterNewItem  extends FragmentPagerAdapter {
        // ViewPager에 들어갈 Fragment들을 담을 리스트
        private ArrayList<Fragment> fragments = new ArrayList<>();
        // 필수 생성자
        AdapterNewItem(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }
        @Override
        public int getCount() {
            return fragments.size();
        }
        // List에 Fragment를 담을 함수
        void addItem(Fragment fragment) {
            fragments.add(fragment);
        }
    }

    //찜 이벤트시 다른 페이지에도 업데이트 전달
    void NotifiedZzim(int zzimState, String code) {
        // 찜 해제 이벤트 전달
        if(zzimState == 0){
            Bundle bundle = new Bundle();
            bundle.putString("rankcode", code);
            RxEventBus.getInstance().post(new RxEvent(RxEvent.RANK_PORT_CHECK_NO, bundle));
        }
        // 찜 하기 이벤트 전달
        else{
            Bundle bundle = new Bundle();
            bundle.putString("rankcode", code);
            RxEventBus.getInstance().post(new RxEvent(RxEvent.RANK_PORT_CHECK_OK, bundle));
        }
    }

    //담기 이벤트시 내가 담은 포트에도 업데이트 전달
    void NotifiedBasket(){
        Bundle bundle = new Bundle();
        bundle.putInt("basket", 0);
        RxEventBus.getInstance().post(new RxEvent(RxEvent.COOK_PAGE_BASKET, bundle));
    }

    void initData(){

        fgcookpage1Binding.fgCookpage1LoadingBar.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
//                try {
//                    Thread.sleep(1500);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }

                Filter filter = new Filter();
                //내가 만든 포트 데이터 초기 불러옴
                Call<ModelTab13mRank> getTest2 = RetrofitClient.getInstance().getService().getPageList("application/json",filter, "M", 0,90,10);
                getTest2.enqueue(new Callback<ModelTab13mRank>() {
                    @Override
                    public void onResponse(Call<ModelTab13mRank> call, Response<ModelTab13mRank> response) {
                        if(response.code() == 200) {
                            if(response.body().getStatus() == 200) {
                                modelHotLists.clear();
                                modelStableLists.clear();

                                for (int index = 0; index < response.body().getContent().size(); index++) {

                                    if (index < 3) {
                                        if (response.body().getContent().get(index).getSelect() != null) {
                                            modelHotLists.add(new ModelHotList(response.body().getContent().get(index).getName(), response.body().getContent().get(index).getRate(),
                                                    response.body().getContent().get(index).getSelect().isZim(), response.body().getContent().get(index).getSelect().isDam(),
                                                    response.body().getContent().get(index).getCode()));
                                        } else {
                                            modelHotLists.add(new ModelHotList(response.body().getContent().get(index).getName(), response.body().getContent().get(index).getRate(),
                                                    false, false,
                                                    response.body().getContent().get(index).getCode()));
                                        }
                                    } else if (index < 6) {

                                        if (response.body().getContent().get(index).getSelect() != null) {
                                            modelStableLists.add(new ModelStableList(response.body().getContent().get(index).getName(), response.body().getContent().get(index).getRate(),
                                                    response.body().getContent().get(index).getSelect().isZim(), response.body().getContent().get(index).getSelect().isDam(),
                                                    response.body().getContent().get(index).getCode()));
                                        } else {
                                            modelStableLists.add(new ModelStableList(response.body().getContent().get(index).getName(), response.body().getContent().get(index).getRate(),
                                                    false, false,
                                                    response.body().getContent().get(index).getCode()));
                                        }
                                    }
                                }

                                adapterHot.notifyDataSetChanged();
                                adapterStable.notifyDataSetChanged();

                                fgcookpage1Binding.fgCookpage1LoadingBar.setVisibility(View.GONE);
                            }
                        }else if(response.code() == 406){

                            Log.e("에러값", "값 : "+ response.body());

//                            Gson gson = new GsonBuilder().create();
//                            ErrorPojoClass mError = new ErrorPojoClass();
//                            try {
//                                mError= gson.fromJson(response.errorBody().string(),ErrorPojoClass.class);
//                                Log.e("스프링 에러", "에러메시지 값 : "+ mError.getDetails());
//                                Log.e("스프링 에러", "에러메시지 값 : "+ mError.getMessage());
//                                Log.e("스프링 에러", "에러메시지 값 : "+ mError.getTimestamp());
//                                Log.e("스프링 에러", "에러메시지 값 : "+ mError.getStatus());
//                            } catch (IOException e) {
//                                // handle failure to read error
//                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<ModelTab13mRank> call, Throwable t) {
                        Log.e("레트로핏 실패","값 : "+t.getMessage());
                    }
                });

            }
        }).start();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //뜨거운 재료 찜 결과
        if(requestCode == 600) {
            if(resultCode == 500) {
                modelHotLists.get(data.getIntExtra("ZzimPositionD",0)).setZim(true);
                adapterHot.notifyItemChanged(data.getIntExtra("ZzimPositionD",0));
                DataManager.get_INstance().setCheckTab1(true);

                DupliCheckedItem(0, 1, modelHotLists.get(data.getIntExtra("ZzimPositionD",0)).getCode());

            }else if(resultCode == 501) {
                modelHotLists.get(data.getIntExtra("ZzimPositionD",0)).setZim(false);
                adapterHot.notifyItemChanged(data.getIntExtra("ZzimPositionD",0));
                DataManager.get_INstance().setCheckTab1(true);

                DupliCheckedItem(0, 0, modelHotLists.get(data.getIntExtra("ZzimPositionD",0)).getCode());
            }
        }
        //안정적인 재료 찜 결과
        else if(requestCode == 700) {
            if(resultCode == 500) {
                modelStableLists.get(data.getIntExtra("ZzimPositionD",0)).setZim(true);
                adapterStable.notifyItemChanged(data.getIntExtra("ZzimPositionD",0));
                DataManager.get_INstance().setCheckTab1(true);

                DupliCheckedItem(2, 1, modelStableLists.get(data.getIntExtra("ZzimPositionD",0)).getCode());

            }else if(resultCode == 501) {
                modelStableLists.get(data.getIntExtra("ZzimPositionD",0)).setZim(false);
                adapterStable.notifyItemChanged(data.getIntExtra("ZzimPositionD",0));
                DataManager.get_INstance().setCheckTab1(true);

                DupliCheckedItem(2, 0, modelStableLists.get(data.getIntExtra("ZzimPositionD",0)).getCode());
            }
        }
    }

    // 찜을 하는 경우 다른 카테고리의 리스트에 중복되는 값 있을때 체크
    // duplication : 0 -> 뜨거운 리스트 찜 / 1 -> 베스트 리스트 찜 / 2 -> 안정 리스트 찜
    // check : 0 -> 찜 해제 / 1 -> 찜 하기
    void DupliCheckedItem(int duplication, int check, String code){
        switch (duplication) {
            case 0:
                if(check == 1) {
                    //안정 리스트
                    for(int index = 0 ; index < modelStableLists.size() ; index++){
                        if(modelStableLists.get(index).getCode().equals(code)) {
                            modelStableLists.get(index).setZim(true);
                        }
                    }
                    adapterStable.notifyDataSetChanged();

                }else{
                    //안정 리스트
                    for(int index = 0 ; index < modelStableLists.size() ; index++){
                        if(modelStableLists.get(index).getCode().equals(code)) {
                            modelStableLists.get(index).setZim(false);
                        }
                    }
                    adapterStable.notifyDataSetChanged();
                }
                break;
            case 1:
                if(check == 1) {
                    //뜨거운 리스트
                    for(int index = 0 ; index < modelHotLists.size() ; index++) {
                        if(modelHotLists.get(index).getCode().equals(code)){
                            modelHotLists.get(index).setZim(true);
                        }
                    }
                    //안정 리스트
                    for(int index = 0 ; index < modelStableLists.size() ; index++){
                        if(modelStableLists.get(index).getCode().equals(code)) {
                            modelStableLists.get(index).setZim(true);
                        }
                    }
                    adapterHot.notifyDataSetChanged();
                    adapterStable.notifyDataSetChanged();

                }else{

                    //뜨거운 리스트
                    for(int index = 0 ; index < modelHotLists.size() ; index++) {
                        if(modelHotLists.get(index).getCode().equals(code)){
                            modelHotLists.get(index).setZim(false);
                        }
                    }
                    //안정 리스트
                    for(int index = 0 ; index < modelStableLists.size() ; index++){
                        if(modelStableLists.get(index).getCode().equals(code)) {
                            modelStableLists.get(index).setZim(false);
                        }
                    }
                    adapterHot.notifyDataSetChanged();
                    adapterStable.notifyDataSetChanged();
                }
                break;
            case 2:
                if(check == 1) {
                    //뜨거운 리스트
                    for(int index = 0 ; index < modelHotLists.size() ; index++) {
                        if(modelHotLists.get(index).getCode().equals(code)) {
                            modelHotLists.get(index).setZim(true);
                        }
                    }
                    adapterHot.notifyDataSetChanged();
                }else{

                    //뜨거운 리스트
                    for(int index = 0 ; index < modelHotLists.size() ; index++) {
                        if(modelHotLists.get(index).getCode().equals(code)) {
                            modelHotLists.get(index).setZim(false);
                        }
                    }
                    adapterHot.notifyDataSetChanged();
                }
                break;
            default:
                break;
        }
    }

    // 담기를 하는 경우 다른 카테고리의 리스트에 중복되는 값 있을때 체크
    // duplication : 0 -> 뜨거운 리스트 담기 / 1 -> 베스트 리스트 담기 / 2 -> 안정 리스트 담기
    // check : 0 -> 담기 해제 / 1 -> 담기
    void DupliCheckedBasket(int duplication, int check, String code){
        switch (duplication) {
            case 0:
                if(check == 1) {
                    //안정 리스트
                    for(int index = 0 ; index < modelStableLists.size() ; index++){
                        if(modelStableLists.get(index).getCode().equals(code)) {
                            modelStableLists.get(index).setDam(true);
                        }
                    }
                    adapterStable.notifyDataSetChanged();

                }else{
                    //안정 리스트
                    for(int index = 0 ; index < modelStableLists.size() ; index++){
                        if(modelStableLists.get(index).getCode().equals(code)) {
                            modelStableLists.get(index).setDam(false);
                        }
                    }
                    adapterStable.notifyDataSetChanged();
                }
                break;
            case 1:
                if(check == 1) {
                    //뜨거운 리스트
                    for(int index = 0 ; index < modelHotLists.size() ; index++) {
                        if(modelHotLists.get(index).getCode().equals(code)){
                            modelHotLists.get(index).setDam(true);
                        }
                    }
                    //안정 리스트
                    for(int index = 0 ; index < modelStableLists.size() ; index++){
                        if(modelStableLists.get(index).getCode().equals(code)) {
                            modelStableLists.get(index).setDam(true);
                        }
                    }
                    adapterHot.notifyDataSetChanged();
                    adapterStable.notifyDataSetChanged();

                }else{

                    //뜨거운 리스트
                    for(int index = 0 ; index < modelHotLists.size() ; index++) {
                        if(modelHotLists.get(index).getCode().equals(code)){
                            modelHotLists.get(index).setDam(false);
                        }
                    }
                    //안정 리스트
                    for(int index = 0 ; index < modelStableLists.size() ; index++){
                        if(modelStableLists.get(index).getCode().equals(code)) {
                            modelStableLists.get(index).setDam(false);
                        }
                    }
                    adapterHot.notifyDataSetChanged();
                    adapterStable.notifyDataSetChanged();
                }
                break;
            case 2:
                if(check == 1) {
                    //뜨거운 리스트
                    for(int index = 0 ; index < modelHotLists.size() ; index++) {
                        if(modelHotLists.get(index).getCode().equals(code)) {
                            modelHotLists.get(index).setDam(true);
                        }
                    }
                    adapterHot.notifyDataSetChanged();
                }else{

                    //뜨거운 리스트
                    for(int index = 0 ; index < modelHotLists.size() ; index++) {
                        if(modelHotLists.get(index).getCode().equals(code)) {
                            modelHotLists.get(index).setDam(false);
                        }
                    }
                    adapterHot.notifyDataSetChanged();
                }
                break;
            default:
                break;
        }
    }

    //재료 찜하기
//    category : 0 ( Hot ) / 1 ( Best ) / 2 ( Stable )
    void CookZzim(int category, String code, int position, boolean isDam, boolean isZim, String mode){

        if(SharedPreferenceUtil.getInstance(mainActivity).getIntExtra("PortZzimCount") >= 25) {
            //초과시 토스트
//                toastZzimLimit.show();
        }else {

            Select select = new Select(code, isDam, isZim);

            Call<ModelZimData> getSelectPort = RetrofitClient.getInstance().getService().getSelectedPortDate("application/json",select, 1,mode);
            getSelectPort.enqueue(new Callback<ModelZimData>() {
                @Override
                public void onResponse(Call<ModelZimData> call, Response<ModelZimData> response) {
                    if(response.code() == 200) {
                        if(response.body().getErrorcode() == 200){

                            if(category == 0) {
                            //찜 안된 상태 -> 찜 하기
                            if (isZim) {
                                modelHotLists.get(position).setZim(true);

                                DupliCheckedItem(0, 1, code);
                                NotifiedZzim(1, code);
                            }
                            //찜 된 상태 -> 찜 해제 하기
                            else {
                                modelHotLists.get(position).setZim(false);

                                DupliCheckedItem(0, 0, code);
                                NotifiedZzim(0, code);
                            }
                            adapterHot.notifyItemChanged(position);
                        }else if(category == 2){
                            //찜 안된 상태 -> 찜 하기
                            if (isZim) {
                                modelStableLists.get(position).setZim(true);

                                DupliCheckedItem(2, 1, code);
                                NotifiedZzim(1, code);
                            }
                            //찜 된 상태 -> 찜 해제 하기
                            else {
                                modelStableLists.get(position).setZim(false);

                                DupliCheckedItem(2, 0, code);
                                NotifiedZzim(0, code);
                            }
                            adapterStable.notifyItemChanged(position);
                        }

                            DataManager.get_INstance().setCheckTab1(true);

                            int zimCount = 0;
                            for(int index = 0 ; index < response.body().getTotalElements() ; index++) {
                                if(response.body().getContent().get(index).isZim()) {
                                    zimCount++;
                                }
                            }
                            SharedPreferenceUtil.getInstance(mainActivity).putIntZzimCount("PortZzimCount", zimCount);
                        }
                    }
                }
                @Override
                public void onFailure(Call<ModelZimData> call, Throwable t) {
                    Toast.makeText(getActivity(),"네트워크가 불안정 합니다\n 다시 시도해 주세요.",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    //재료 담기
    //category : 0 ( Hot ) / 1 ( Best ) / 2 ( Stable )
    void CookBasket(int category, String code, int position, boolean isDam, boolean isZim, String mode) {

        Select select = new Select(code, isDam, isZim);

        Call<ModelZimData> getSelectPort = RetrofitClient.getInstance().getService().getSelectedPortDate("application/json",select, 2, mode);
        getSelectPort.enqueue(new Callback<ModelZimData>() {
            @Override
            public void onResponse(Call<ModelZimData> call, Response<ModelZimData> response) {
                if(response.code() == 200) {
                    if(response.body().getErrorcode() == 200){
                        // 뜨거운 담기
                    if(category == 0) {
                        if (isDam) {
                            modelHotLists.get(position).setDam(true);

                            DupliCheckedBasket(0, 1, code);
                            NotifiedBasket();
                        } else {
                            modelHotLists.get(position).setDam(false);
                            DupliCheckedBasket(0, 0, code);
                            NotifiedBasket();
                        }
                        adapterHot.notifyItemChanged(position);
                    }
                    // 안정적 담기
                    else if(category == 2) {
                        if (isDam) {
                            modelStableLists.get(position).setDam(true);

                            DupliCheckedBasket(2, 1, code);
                            NotifiedBasket();
                        } else {
                            modelStableLists.get(position).setDam(false);

                            DupliCheckedBasket(2, 0, code);
                            NotifiedBasket();
                        }
                        adapterStable.notifyItemChanged(position);
                    }

                        int zimCount = 0;
                        for(int index = 0 ; index < response.body().getTotalElements() ; index++) {
                            if(response.body().getContent().get(index).isZim()) {
                                zimCount++;
                            }
                        }
                        SharedPreferenceUtil.getInstance(mainActivity).putIntZzimCount("PortZzimCount", zimCount);
                    }
                }
            }
            @Override
            public void onFailure(Call<ModelZimData> call, Throwable t) {
                Toast.makeText(getActivity(),"네트워크가 불안정 합니다\n 다시 시도해 주세요.",Toast.LENGTH_SHORT).show();
            }
        });
    }


}


package quantec.com.moneypot.Activity.Main.Fragment.FgTab2;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Dialog.DialogCookpage2AllDelete;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.Cookpage1.Fg_CookPage1;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.Cookpage2.Fg_CookPage2;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.Cookpage3.Fg_CookPage3;
import quantec.com.moneypot.Activity.Main.MainActivity;
import quantec.com.moneypot.Activity.Myinfo.ActivityMyinfo;
import quantec.com.moneypot.Activity.SearchPort.ActivitySearchPort;
import quantec.com.moneypot.DataManager.DataManager;
import quantec.com.moneypot.R;
import quantec.com.moneypot.RxAndroid.RxEvent;
import quantec.com.moneypot.RxAndroid.RxEventBus;
import quantec.com.moneypot.databinding.FgTab2Binding;

public class Fg_tab2 extends Fragment {

    FgTab2Binding fgTab2Binding;
    private MainActivity mainActivity;

    Animation pageAnim;

    boolean currentState = false;
    int currentPage = 0;

    Bundle cookPage3State, cookPageMarginState;
    private DialogCookpage2AllDelete customDialogAll;

    public Fg_tab2() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fgTab2Binding = DataBindingUtil.inflate(inflater, R.layout.fg_tab2, container, false);

        initializeViews();

        setupViewPager(fgTab2Binding.fragmentTab2Viewpager);
        fgTab2Binding.SearchTabLayout.setupWithViewPager(fgTab2Binding.fragmentTab2Viewpager);
        fgTab2Binding.fragmentTab2Viewpager.setOffscreenPageLimit(3);
        fgTab2Binding.fragmentTab2Viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if(positionOffset != 0){
                    fgTab2Binding.fgCookpage3DeleteBt.setVisibility(View.INVISIBLE);
                    fgTab2Binding.cookpage1Bt.setEnabled(false);
                }else{
                    fgTab2Binding.fgCookpage3DeleteBt.setVisibility(View.VISIBLE);
                    fgTab2Binding.cookpage1Bt.setEnabled(true);
                }

                fgTab2Binding.cookpage1Bt.setTranslationY(-Math.abs(positionOffset-position)*200);
                if(position == 2) {
                    fgTab2Binding.cookpage1Bt.setVisibility(View.GONE);
                }else{
                    fgTab2Binding.cookpage1Bt.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onPageSelected(int position) {
                if(position == 0){
                    fgTab2Binding.fragmentTab2ModifyLayout.setVisibility(View.GONE);
                }else{
                    fgTab2Binding.fragmentTab2ModifyLayout.setVisibility(View.VISIBLE);
                }

                currentPage = position;

                if(currentState) {
                    fgTab2Binding.fragmentTab2Viewpager.setCurrentItem(1);
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        cookPage3State = new Bundle();
        cookPageMarginState = new Bundle();

        return fgTab2Binding.getRoot();
    }//onCreateView 끝

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

        //내가 담은 포트 편집 클릭
        fgTab2Binding.fgCookpage3DeleteBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fgTab2Binding.SearchTopMyBT.setVisibility(View.GONE);

                if(fgTab2Binding.fgCookpage3DeleteBt.getText().equals("편집")) {

                    fgTab2Binding.fgCookpage3DeleteBt.setText("전체삭제");
                    fgTab2Binding.fgCookpage3DeleteBt.setTextColor(getResources().getColor(R.color.delete_pressed_text));
                    fgTab2Binding.fgCookpage3DeleteBt.setBackgroundResource(R.drawable.delete_round_pressed_bt);

                    PageAnimation(1);
                }
                else{
                    customDialogAll = new DialogCookpage2AllDelete(getContext(), ALLleftListener, ALLrightListener);
                    customDialogAll.show();
                }
            }
        });


        //내 자산보기 클릭
        fgTab2Binding.SearchTopMyBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivityMyinfo.class);
                startActivity(intent);
            }
        });


        //상단 완료 버튼
        fgTab2Binding.SearchTopCookpage3Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cookPage3State.putInt("page", 2);
                cookPage3State.putInt("page3Modify", 1);
                RxEventBus.getInstance().post(new RxEvent(RxEvent.COOK_PAGE3_MODIFY, cookPage3State));

                currentState = false;
                fgTab2Binding.SearchTabLayout.setVisibility(View.VISIBLE);

                fgTab2Binding.fragmentTab2Viewpager.setPagingEnabled(true);
                pageAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.cookpage_down);
                fgTab2Binding.fragment2ContainerLayout.startAnimation(pageAnim);
                pageAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                        fgTab2Binding.SearchTopCookpage3Ok.setVisibility(View.GONE);
                        fgTab2Binding.SearchTopMyBT.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        fgTab2Binding.appbar.setVisibility(View.VISIBLE);
                        fgTab2Binding.fragmentTab2ModifyLayout.setVisibility(View.VISIBLE);

                        fgTab2Binding.fgCookpage3DeleteBt.setText("편집");
                        fgTab2Binding.fgCookpage3DeleteBt.setTextColor(getResources().getColor(R.color.delete_normal_text));
                        fgTab2Binding.fgCookpage3DeleteBt.setBackgroundResource(R.drawable.delete_round_bt);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });

            }
        });


        fgTab2Binding.SearchTopCookpage2Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cookPage3State.putInt("page",1);
                cookPage3State.putInt("page3Modify",1);
                RxEventBus.getInstance().post(new RxEvent(RxEvent.COOK_PAGE3_MODIFY, cookPage3State));

                currentState = false;
                fgTab2Binding.SearchTabLayout.setVisibility(View.VISIBLE);

                fgTab2Binding.fragmentTab2Viewpager.setPagingEnabled(true);
                pageAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.cookpage_down);
                fgTab2Binding.fragment2ContainerLayout.startAnimation(pageAnim);
                pageAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        fgTab2Binding.cookpage1Bt.setVisibility(View.VISIBLE);

                        fgTab2Binding.SearchTopCookpage2Ok.setVisibility(View.GONE);
                        fgTab2Binding.SearchTopMyBT.setVisibility(View.VISIBLE);
                    }
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        fgTab2Binding.appbar.setVisibility(View.VISIBLE);
                        fgTab2Binding.fragmentTab2ModifyLayout.setVisibility(View.VISIBLE);

                        fgTab2Binding.fgCookpage3DeleteBt.setText("편집");
                        fgTab2Binding.fgCookpage3DeleteBt.setTextColor(getResources().getColor(R.color.delete_normal_text));
                        fgTab2Binding.fgCookpage3DeleteBt.setBackgroundResource(R.drawable.delete_round_bt);
                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
            }
        });


        //포트만들기시 상단 완료 버튼 클릭
        fgTab2Binding.SearchTopMakePortOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fgTab2Binding.SearchBackBT.setVisibility(View.VISIBLE);
                MakeOkButtonAnim();
            }
        });


        //상단 검색 버튼 클릭
        fgTab2Binding.SearchBackBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivitySearchPort.class);
                startActivity(intent);
            }
        });

        RxEventBus.getInstance()
                .filteredObservable(RxEvent.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RxEvent>() {
                    @Override
                    public void onSubscribe(Disposable d) {}
                    @Override
                    public void onNext(RxEvent rxEvent) {

                        switch (rxEvent.getActiion()) {

                            case RxEvent.ZZIM_PORT_MAKE_MYPORT:
                                PageAnimation(0);
                                break;

                            case RxEvent.ZZIM_PORT_TRANS_PAGE:

                                fgTab2Binding.SearchBackBT.setVisibility(View.VISIBLE);

                                MakeOkButtonAnim();
                                fgTab2Binding.fragmentTab2Viewpager.setPagingEnabled(true);
                                fgTab2Binding.fragmentTab2Viewpager.setCurrentItem(2, true);
                                break;

//                            //포트 만들기 닫기
                            case RxEvent.ZZIM_PORT_MAKE_MYPORT_CLOSE:

                                fgTab2Binding.SearchBackBT.setVisibility(View.VISIBLE);

//                                AdapterCookPage2.ACook = false;

                                // ViewMargin : 0 -> 마진 없애기 / 1 - > 마진 주기
                                cookPageMarginState.putInt("ViewMargin", 0);
                                RxEventBus.getInstance().post(new RxEvent(RxEvent.REFRESH_COOKP2, cookPageMarginState));

                                fgTab2Binding.SearchTopMakePortOk.setVisibility(View.GONE);

                                cookPage3State.putInt("page",1);
                                cookPage3State.putInt("page3Modify",9);
                                RxEventBus.getInstance().post(new RxEvent(RxEvent.COOK_PAGE3_MODIFY, cookPage3State));

                                currentState = false;
                                fgTab2Binding.SearchTabLayout.setVisibility(View.VISIBLE);

                                fgTab2Binding.fragmentTab2Viewpager.setPagingEnabled(true);
                                pageAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.cookpage_down);
                                fgTab2Binding.fragment2ContainerLayout.startAnimation(pageAnim);
                                pageAnim.setAnimationListener(new Animation.AnimationListener() {
                                    @Override
                                    public void onAnimationStart(Animation animation) {
                                        fgTab2Binding.cookpage1Bt.setVisibility(View.VISIBLE);

                                        fgTab2Binding.SearchTopCookpage2Ok.setVisibility(View.GONE);
                                        fgTab2Binding.SearchTopMyBT.setVisibility(View.VISIBLE);
                                    }
                                    @Override
                                    public void onAnimationEnd(Animation animation) {
                                        fgTab2Binding.appbar.setVisibility(View.VISIBLE);
                                        fgTab2Binding.fragmentTab2ModifyLayout.setVisibility(View.VISIBLE);
                                        fgTab2Binding.fgCookpage3DeleteBt.setText("편집");
                                    }
                                    @Override
                                    public void onAnimationRepeat(Animation animation) {
                                    }

                                });

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


        //포트만들기 버튼
        fgTab2Binding.cookpage1Bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fgTab2Binding.SearchBackBT.setVisibility(View.GONE);

                fgTab2Binding.SearchTopMyBT.setVisibility(View.GONE);
                fgTab2Binding.SearchTopMakePortOk.setVisibility(View.VISIBLE);
                v.setTag("Fg2_cookBT");
                MainActivity.viewPublishSubject.onNext(v);
//                AdapterCookPage2.ACook = true;

                cookPageMarginState.putInt("ViewMargin", 1);
                RxEventBus.getInstance().post(new RxEvent(RxEvent.REFRESH_COOKP2, cookPageMarginState));

            }
        });

    }//onViewCreate 끝


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if(DataManager.get_INstance().isCheckTab1() || DataManager.get_INstance().isCheckCookPage()) {
            DataManager.get_INstance().setCheckTab1(false);
            DataManager.get_INstance().setCheckCookPage(false);
            RxEventBus.getInstance().post(new RxEvent(RxEvent.ZZIM_PORT_LOAD, null));
        }
    }

    //전체 삭제 취소
    private View.OnClickListener ALLleftListener = new View.OnClickListener() {
        public void onClick(View v) {
            customDialogAll.dismiss();
        }
    };

    //전체 삭제 확인
    private View.OnClickListener ALLrightListener = new View.OnClickListener() {
        public void onClick(View v) {

            //현재페이지
            //currentPage = 1 일때 내가 담은 포트에서 전체삭제시 이벤트
            if(currentPage == 1){
                Bundle bundle = new Bundle();
                bundle.putInt("basket", 2);
                RxEventBus.getInstance().post(new RxEvent(RxEvent.COOK_PAGE_BASKET, bundle));
            }
            //currentPage = 2 일때 내가 만든 포트에서 전체삭제시 이벤트
            else{
                Bundle bundle = new Bundle();
                bundle.putInt("basket", 3);
                RxEventBus.getInstance().post(new RxEvent(RxEvent.COOK_PAGE_BASKET, bundle));
            }
            customDialogAll.dismiss();
        }
    };


    //포트만들기시 완료버튼 클릭
    void MakeOkButtonAnim(){
//        AdapterCookPage2.ACook = false;

        cookPageMarginState.putInt("ViewMargin", 0);
        RxEventBus.getInstance().post(new RxEvent(RxEvent.REFRESH_COOKP2, cookPageMarginState));

        fgTab2Binding.SearchTopMakePortOk.setVisibility(View.GONE);

        cookPage3State.putInt("page",1);
        cookPage3State.putInt("page3Modify",9);
        RxEventBus.getInstance().post(new RxEvent(RxEvent.COOK_PAGE3_MODIFY, cookPage3State));

        currentState = false;
        fgTab2Binding.SearchTabLayout.setVisibility(View.VISIBLE);

        fgTab2Binding.fragmentTab2Viewpager.setPagingEnabled(true);
        pageAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.cookpage_down);
        fgTab2Binding.fragment2ContainerLayout.startAnimation(pageAnim);
        pageAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                fgTab2Binding.cookpage1Bt.setVisibility(View.VISIBLE);

                RxEventBus.getInstance().post(new RxEvent(RxEvent.ZZIM_PORT_PRE_CLOSE, null));
                fgTab2Binding.SearchTopCookpage2Ok.setVisibility(View.GONE);
                fgTab2Binding.SearchTopMyBT.setVisibility(View.VISIBLE);
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                fgTab2Binding.appbar.setVisibility(View.VISIBLE);
                fgTab2Binding.fragmentTab2ModifyLayout.setVisibility(View.VISIBLE);
                fgTab2Binding.fgCookpage3DeleteBt.setText("편집");
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }

        });
    }


    //category : 0 -> 포트만들기 클릭 / 1 -> 편집클릭
    void PageAnimation(int category){

        if(category == 0) {
            fgTab2Binding.cookpage1Bt.setVisibility(View.GONE);

            currentState = true;
            fgTab2Binding.fragmentTab2Viewpager.setCurrentItem(1);

            fgTab2Binding.SearchTopCookpage2Ok.setVisibility(View.VISIBLE);
            Animation();
        }else{

            if (currentPage == 1) {
                fgTab2Binding.cookpage1Bt.setVisibility(View.GONE);

                currentState = true;
                fgTab2Binding.fragmentTab2Viewpager.setCurrentItem(1);
                cookPage3State.putInt("page", 1);
                cookPage3State.putInt("page3Modify", 0);
                RxEventBus.getInstance().post(new RxEvent(RxEvent.COOK_PAGE3_MODIFY, cookPage3State));

                fgTab2Binding.SearchTopCookpage2Ok.setVisibility(View.VISIBLE);
                Animation2();
            } else if (currentPage == 2) {
                cookPage3State.putInt("page", 2);
                cookPage3State.putInt("page3Modify", 0);
                RxEventBus.getInstance().post(new RxEvent(RxEvent.COOK_PAGE3_MODIFY, cookPage3State));
                fgTab2Binding.fragmentTab2Viewpager.setCurrentItem(2);

                fgTab2Binding.SearchTopCookpage3Ok.setVisibility(View.VISIBLE);
                Animation2();
            }
        }
    }


    //포트만들기 애니메이션
    void Animation(){

        fgTab2Binding.SearchTabLayout.setVisibility(View.GONE);
        fgTab2Binding.fragmentTab2Viewpager.setPagingEnabled(false);
        pageAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.cookpage_up);

        fgTab2Binding.fragment2ContainerLayout.startAnimation(pageAnim);
        pageAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                fgTab2Binding.appbar.setVisibility(View.INVISIBLE);
                fgTab2Binding.fragmentTab2ModifyLayout.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                fgTab2Binding.appbar.setVisibility(View.GONE);
                fgTab2Binding.fragmentTab2ModifyLayout.setVisibility(View.GONE);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }
    //포트편집 애니메이션
    void Animation2(){

        fgTab2Binding.SearchTabLayout.setVisibility(View.GONE);
        fgTab2Binding.fragmentTab2Viewpager.setPagingEnabled(false);
        pageAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.cookpage_delete_up);

        fgTab2Binding.fragment2ContainerLayout.startAnimation(pageAnim);
        pageAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                fgTab2Binding.appbar.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                fgTab2Binding.appbar.setVisibility(View.GONE);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    // Add Fragments to Tabs
    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getChildFragmentManager());
        adapter.addFragment(new Fg_CookPage1(), "HOME");
        adapter.addFragment(new Fg_CookPage2(), "내가 담은 포트");
        adapter.addFragment(new Fg_CookPage3(), "내가 만든 포트");
        viewPager.setAdapter(adapter);
    }
    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        public Adapter(FragmentManager manager) {
            super(manager);
        }
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }
        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}

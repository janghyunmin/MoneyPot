package com.quantec.moneypot.activity.prefer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.quantec.moneypot.R;
import com.quantec.moneypot.activity.Main.ActivityMain;
import com.quantec.moneypot.activity.prefer.adapter.AdapterThumbImage;
import com.quantec.moneypot.activity.prefer.fragment.FgEnter;
import com.quantec.moneypot.activity.prefer.fragment.FgSector;
import com.quantec.moneypot.datamodel.nmodel.ModelUserFollow;
import com.quantec.moneypot.network.retrofit.RetrofitClient;
import com.quantec.moneypot.rxandroid.RxEvent;
import com.quantec.moneypot.rxandroid.RxEventBus;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.quantec.moneypot.datamodel.dmodel.ModelUserSelectDto;
import com.quantec.moneypot.datamodel.dmodel.userselectdto.Select;

public class ActivityPrefer extends AppCompatActivity implements FgSector.ClickItem2, FgEnter.ClickItem {

    ViewPager viewPager;
    TextView sectorTab, enterTab;

    View backImage;

    ConstraintLayout okLayout;
    ImageView image1, image2, image3, image4, image5, openBt, closedBt;

    Bundle backImageValue, realPosition;

    RecyclerView recyclerViewSmall;
    RecyclerView.LayoutManager layoutManager2;
    ArrayList<ModelThumbImage> modelThumbImage;
    AdapterThumbImage adapterThumbImage;

    boolean openState = false;

    View line, thumbBackImage, bottomLine;
    TextView okBt, countNum;

    public static int totalCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prefer);

        // 스테이터스 바 색상 변경 -> 화이트
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }else{
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.main_page_status_bar_color));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        closedBt = findViewById(R.id.closedBt);

        recyclerViewSmall = findViewById(R.id.recyclerViewSmall);

        recyclerViewSmall.setHasFixedSize(true);
        layoutManager2 = new LinearLayoutManager(this);
        recyclerViewSmall.setLayoutManager(layoutManager2);
        modelThumbImage = new ArrayList<>();
        adapterThumbImage = new AdapterThumbImage(modelThumbImage, this);
        recyclerViewSmall.setAdapter(adapterThumbImage);

        thumbBackImage = findViewById(R.id.thumbBackImage);
        bottomLine = findViewById(R.id.bottomLine);

        line = findViewById(R.id.line);
        okBt = findViewById(R.id.okBt);
        backImage = findViewById(R.id.backImage);
        backImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        openBt = findViewById(R.id.openBt);
        openBt.setImageDrawable(getResources().getDrawable(R.drawable.btn_arrow_up));

        backImageValue = new Bundle();
        realPosition = new Bundle();

        countNum = findViewById(R.id.countNum);

        okLayout = findViewById(R.id.okLayout);
        okLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);
        image4 = findViewById(R.id.image4);
        image5 = findViewById(R.id.image5);


        okLayout = findViewById(R.id.okLayout);
        okLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        backImage = findViewById(R.id.backImage);
        backImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        viewPager = findViewById(R.id.viewPager);
        sectorTab = findViewById(R.id.sectorTab);
        enterTab = findViewById(R.id.enterTab);

        sectorTab.setBackgroundResource(R.drawable.tab_bt);
        sectorTab.setTextColor(getResources().getColor(R.color.tab_click));

        enterTab.setBackgroundResource(0);
        enterTab.setTextColor(getResources().getColor(R.color.tab_unclick));

        setupViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if(position == 0) {
                    sectorTab.setBackgroundResource(R.drawable.tab_bt);
                    sectorTab.setTextColor(getResources().getColor(R.color.tab_click));

                    enterTab.setBackgroundResource(0);
                    enterTab.setTextColor(getResources().getColor(R.color.tab_unclick));
                }else{
                    sectorTab.setBackgroundResource(0);
                    sectorTab.setTextColor(getResources().getColor(R.color.tab_unclick));

                    enterTab.setBackgroundResource(R.drawable.tab_bt);
                    enterTab.setTextColor(getResources().getColor(R.color.tab_click));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


        sectorTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sectorTab.setBackgroundResource(R.drawable.tab_bt);
                sectorTab.setTextColor(getResources().getColor(R.color.tab_click));

                enterTab.setBackgroundResource(0);
                enterTab.setTextColor(getResources().getColor(R.color.tab_unclick));
                viewPager.setCurrentItem(0, true);

            }
        });

        enterTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sectorTab.setBackgroundResource(0);
                sectorTab.setTextColor(getResources().getColor(R.color.tab_unclick));

                enterTab.setBackgroundResource(R.drawable.tab_bt);
                enterTab.setTextColor(getResources().getColor(R.color.tab_click));

                viewPager.setCurrentItem(1, true);

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

                            case RxEvent.BACK_IMAGE:
                                if(rxEvent.getBundle().getInt("state") == 0){
                                    backImage.setVisibility(View.GONE);
                                }else{
                                    backImage.setVisibility(View.VISIBLE);
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

        image1.setTag("");
        image2.setTag("");
        image3.setTag("");
        image4.setTag("");
        image5.setTag("");

        countNum.setText("0/5");

        openBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(openState){
                    bottomLine.setVisibility(View.GONE);
                    backImage.setVisibility(View.GONE);
                    backImageValue.putInt("state", 0);
                    RxEventBus.getInstance().post(new RxEvent(RxEvent.BACK_IMAGE, backImageValue));

                    thumbBackImage.setVisibility(View.GONE);
                    openState = false;
                    recyclerViewSmall.setVisibility(View.GONE);

                }else{

                    bottomLine.setVisibility(View.VISIBLE);
                    backImage.setVisibility(View.VISIBLE);
                    backImageValue.putInt("state", 1);
                    RxEventBus.getInstance().post(new RxEvent(RxEvent.BACK_IMAGE, backImageValue));

                    adapterThumbImage.notifyDataSetChanged();
                    thumbBackImage.setVisibility(View.VISIBLE);
                    openState = true;
                    recyclerViewSmall.setVisibility(View.VISIBLE);
                }
            }
        });

        adapterThumbImage.setThumbImageDeleteBt(new AdapterThumbImage.ThumbImageDeleteBt() {
            @Override
            public void onClick(int position) {

                realPosition.putInt("realPosition", modelThumbImage.get(position).getRealPosition());
                RxEventBus.getInstance().post(new RxEvent(RxEvent.PREFER_ITEM_DELETE, realPosition));

                totalCount--;
                thumbImage(modelThumbImage.get(position).getImage(), totalCount, false);
                modelThumbImage.remove(position);
                adapterThumbImage.notifyDataSetChanged();

                if(totalCount == 0){
                    totalCount = 0;
                    modelThumbImage.clear();
                    okLayout.setTranslationY(dpToPx(ActivityPrefer.this, 110));

                    bottomLine.setVisibility(View.GONE);
                    backImage.setVisibility(View.GONE);
                    backImageValue.putInt("state", 0);
                    RxEventBus.getInstance().post(new RxEvent(RxEvent.BACK_IMAGE, backImageValue));

                    thumbBackImage.setVisibility(View.GONE);
                    openState = false;
                    recyclerViewSmall.setVisibility(View.GONE);
                }
            }
        });

        closedBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ActivityPrefer.this, ActivityMain.class);
                startActivity(intent1);
                finish();
            }
        });

        okBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                List<Select> selects = new ArrayList<>();
//                Select select = new Select();
//                select.setIsDam(0);
//                select.setIsZim(0);
//                select.setIsFollow(0);
//
//                select.setCode("APPLE");
//                select.setIsLike(1);
//                select.setType(0);
//                selects.add(select);
//
//                ModelUserSelectDto modelUserSelectDto = new ModelUserSelectDto();
//                modelUserSelectDto.setSelects(selects);
//
//                Call<Object> getReList = RetrofitClient.getInstance().getService().setUserSelect("application/json", "follow", modelUserSelectDto);
//                getReList.enqueue(new Callback<Object>() {
//                    @Override
//                    public void onResponse(Call<Object> call, Response<Object> response) {
//                        if (response.code() == 200) {
//
//                        }
//                    }
//                    @Override
//                    public void onFailure(Call<Object> call, Throwable t) {
//                        Log.e("실패","실패"+t.getMessage());
//                    }
//                });
            }
        });

    }//onCreate 끝

    private void setupViewPager(ViewPager viewPager) {
        PreferAdapter adapter = new PreferAdapter(getSupportFragmentManager());
        adapter.addFragment(new FgSector(), "분야");
        adapter.addFragment(new FgEnter(), "기업");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(boolean state, int count, String item, String bunya, String title, int realPosition) {

        if(!state){
                for(int index = 0 ; index < modelThumbImage.size() ; index++){
                    if(item.equals(modelThumbImage.get(index).getImage())){
                        modelThumbImage.remove(index);
                    }
                }
                thumbImage(item, count, false);
        }else{
            if(count <= 5){
                modelThumbImage.add(new ModelThumbImage(item, bunya, title, realPosition));
                thumbImage(item, count, true);
            }
        }

        if(count == 1){
            okLayout.setTranslationY(0);
        }
        if(count == 0){
            okLayout.setTranslationY(dpToPx(ActivityPrefer.this, 110));
        }
    }

    @Override
    public void onClick2(boolean state, int count, String item, String bunya, String title, int realPosition) {
        if(!state){
            for(int index = 0 ; index < modelThumbImage.size() ; index++){
                if(item.equals(modelThumbImage.get(index).getImage())){
                    modelThumbImage.remove(index);
                }
            }
            thumbImage(item, count, false);
        }else{
            if(count <= 5){
                modelThumbImage.add(new ModelThumbImage(item, bunya, title, realPosition));
                thumbImage(item, count, true);
            }
        }

        if(count == 1){
            okLayout.setTranslationY(0);
        }
        if(count == 0){
            okLayout.setTranslationY(dpToPx(ActivityPrefer.this, 110));
        }
    }


    static class PreferAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        public PreferAdapter(FragmentManager manager) {
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


    private int dpToPx(Context context, int dp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    void thumbImage(String name, int count, boolean selected){

        countNum.setText(count+"/5");

        if(selected){
//            int resource = getResources().getIdentifier("ci_"+name+"_thumbnail", "drawable", getPackageName());
            int resource = getResources().getIdentifier("ci_"+name+"_small", "drawable", getPackageName());

            if(image1.getTag().equals("")){
                image1.setVisibility(View.VISIBLE);
                image1.setImageDrawable(getResources().getDrawable(resource));
                image1.setTag(name);
            }else if(image2.getTag().equals("")){
                image2.setVisibility(View.VISIBLE);
                image2.setImageDrawable(getResources().getDrawable(resource));
                image2.setTag(name);
            }else if(image3.getTag().equals("")){
                image3.setVisibility(View.VISIBLE);
                image3.setImageDrawable(getResources().getDrawable(resource));
                image3.setTag(name);
            }else if(image4.getTag().equals("")){
                image4.setVisibility(View.VISIBLE);
                image4.setImageDrawable(getResources().getDrawable(resource));
                image4.setTag(name);
            }else if(image5.getTag().equals("")){
                image5.setVisibility(View.VISIBLE);
                image5.setImageDrawable(getResources().getDrawable(resource));
                image5.setTag(name);
            }
        }
        else{

            if(image1.getTag().equals(name)){
                image1.setVisibility(View.GONE);
                image1.setImageDrawable(null);
                image1.setTag("");
            }
            else if(image2.getTag().equals(name)){
                image2.setVisibility(View.GONE);
                image2.setImageDrawable(null);
                image2.setTag("");
            }
            else if(image3.getTag().equals(name)){
                image3.setVisibility(View.GONE);
                image3.setImageDrawable(null);
                image3.setTag("");
            }
            else if(image4.getTag().equals(name)){
                image4.setVisibility(View.GONE);
                image4.setImageDrawable(null);
                image4.setTag("");
            }
            else if(image5.getTag().equals(name)){
                image5.setVisibility(View.GONE);
                image5.setImageDrawable(null);
                image5.setTag("");
            }
        }
    }

}

package quantec.com.moneypot.Activity.Main.Fragment.Tab2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.asksira.loopingviewpager.LoopingPagerAdapter;
import com.asksira.loopingviewpager.LoopingViewPager;
import com.rd.PageIndicatorView;
import com.rd.animation.type.AnimationType;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import quantec.com.moneypot.Activity.Main.ActivityMain;
import quantec.com.moneypot.Activity.Main.Fragment.Tab2.Activity.ActivityCookPotImage;
import quantec.com.moneypot.Activity.Main.Fragment.Tab2.Activity.ActivityLeagueForm;
import quantec.com.moneypot.Activity.Main.Fragment.Tab2.Activity.ActivityPotCook;
import quantec.com.moneypot.Activity.Main.Fragment.Tab2.Adapter.AdapterAdImage;
import quantec.com.moneypot.Activity.Main.Fragment.Tab2.Adapter.AdapterLeagueList;
import quantec.com.moneypot.Activity.Main.Fragment.Tab2.Adapter.ModelMyPotPage;
import quantec.com.moneypot.Activity.PotDetail.ActivityPotDetail;
import quantec.com.moneypot.DataModel.dModel.Filter;
import quantec.com.moneypot.DataModel.dModel.ModelAdImage;
import quantec.com.moneypot.DataModel.dModel.ModelLeagueList;
import quantec.com.moneypot.DataModel.nModel.ModelPotList;
import quantec.com.moneypot.Network.Retrofit.RetrofitClient;
import quantec.com.moneypot.R;
import quantec.com.moneypot.RxAndroid.RxEvent;
import quantec.com.moneypot.RxAndroid.RxEventBus;
import quantec.com.moneypot.util.DateTransForm;
import quantec.com.moneypot.util.DecimalScale.DecimalScale;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FgAllPage extends Fragment {

    RecyclerView recyclerView1, recyclerView2;
    RecyclerView.LayoutManager layoutManager,layoutManager2;
    ArrayList<ModelLeagueList> modelLeagueLists;
    ArrayList<ModelAdImage> modelAdImages;
    AdapterLeagueList adapterLeagueList;
    AdapterAdImage adapterAdImage;

    SnapHelper snapHelper;

    private ActivityMain activityMain;

    DemoInfiniteAdapter adapter;
    LoopingViewPager viewPager;
    PageIndicatorView indicatorView;

    ArrayList<ModelMakeMyPot> modelMakeMyPot;

    ImageView movedPage;

    public FgAllPage() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_allpage, container, false);

        initializeViews();
        movedPage = view.findViewById(R.id.movedPage);

        recyclerView1 = view.findViewById(R.id.recyclerView1);
        recyclerView1.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(activityMain);
        recyclerView1.setLayoutManager(layoutManager);

        modelLeagueLists = new ArrayList<>();
        adapterLeagueList = new AdapterLeagueList(modelLeagueLists, activityMain);
        recyclerView1.setAdapter(adapterLeagueList);

        recyclerView2 = view.findViewById(R.id.recyclerView2);
        recyclerView2.setHasFixedSize(true);

        modelAdImages = new ArrayList<>();


        snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView2);

        layoutManager2 = new LinearLayoutManager(activityMain, RecyclerView.HORIZONTAL, false);
        recyclerView2.setLayoutManager(layoutManager2);

        adapterAdImage = new AdapterAdImage(modelAdImages, activityMain, activityMain);
        recyclerView2.setAdapter(adapterAdImage);

        recyclerView1.setLayoutManager(new LinearLayoutManager(activityMain){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        });

        modelMakeMyPot = new ArrayList<>();
//        modelMakeMyPot.add(new ModelMakeMyPot("", "", "", 0, true));

        indicatorView = view.findViewById(R.id.indicator);
        indicatorView.setAlpha(0.7f);
        indicatorView.setSelectedColor(activityMain.getResources().getColor(R.color.red_text_color));
        indicatorView.setUnselectedColor(activityMain.getResources().getColor(R.color.text_gray_color));
        indicatorView.setAnimationType(AnimationType.THIN_WORM);
        viewPager = view.findViewById(R.id.viewPager);

        return view;
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


    public class DemoInfiniteAdapter extends LoopingPagerAdapter<ModelMakeMyPot> {

        private final int VIEW_EMPTY = 0;
        private final int VIEW_NORMAL = 100;

        ArrayList<ModelMakeMyPot> itemList;

        public DemoInfiniteAdapter(Context context, ArrayList<ModelMakeMyPot> itemList, boolean isInfinite) {
            super(context, itemList, isInfinite);
            this.itemList = itemList;
        }
        @Override
        protected int getItemViewType(int listPosition) {

            if(itemList.get(listPosition).dataEmpty){
                return VIEW_EMPTY;
            }
            else{
                return VIEW_NORMAL;
            }
        }
        @Override
        protected View inflateView(int viewType, ViewGroup container, int listPosition) {

            if(viewType == VIEW_EMPTY){
                return LayoutInflater.from(activityMain).inflate(R.layout.item_makepot_recentlyempty, container, false);
            }
            else{
                return LayoutInflater.from(activityMain).inflate(R.layout.item_makepot_recentlymypot, container, false);
            }

        }
        @Override
        protected void bindView(View convertView, int listPosition, int viewType) {

            TextView title, date, rate, per;
            ConstraintLayout itemLayout;

            if(viewType == VIEW_EMPTY){

            }

            if(viewType == VIEW_NORMAL){

                title = convertView.findViewById(R.id.title);
                date = convertView.findViewById(R.id.date);
                rate = convertView.findViewById(R.id.rate);
                per = convertView.findViewById(R.id.per);

                itemLayout = convertView.findViewById(R.id.itemLayout);

                title.setText(modelMakeMyPot.get(listPosition).getPotTitle());
                date.setText(modelMakeMyPot.get(listPosition).getPotDate());
                rate.setText(String.valueOf(modelMakeMyPot.get(listPosition).getPotRate()));

                if(modelMakeMyPot.get(listPosition).getPotRate() < 0){
                    rate.setTextColor(getResources().getColor(R.color.blue_color));
                    per.setTextColor(getResources().getColor(R.color.blue_color));
                }
                else{
                    rate.setTextColor(getResources().getColor(R.color.red_text_color));
                    per.setTextColor(getResources().getColor(R.color.red_text_color));
                }

                itemLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent1 = new Intent(activityMain, ActivityPotDetail.class);
                        intent1.putExtra("potCode",modelMakeMyPot.get(listPosition).getPotCode());
                        startActivity(intent1);
                    }
                });
            }
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //내가 만든 포트 불러옴
        setMyPotDate();

        movedPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxEventBus.getInstance().post(new RxEvent(RxEvent.MOVED_POTCOOK_PAGE, null));
            }
        });

        modelLeagueLists.add(0, new ModelLeagueList(0, "","","",""));

        adapterLeagueList.notifyDataSetChanged();

        modelAdImages.add(new ModelAdImage(""));
        modelAdImages.add(new ModelAdImage(""));
        modelAdImages.add(new ModelAdImage(""));

        adapterAdImage.notifyDataSetChanged();

        adapterLeagueList.setLeagueEnterBtClick(new AdapterLeagueList.LeagueEnterBtClick() {
            @Override
            public void onClick(int position) {

                Intent intent = new Intent(activityMain, ActivityLeagueForm.class);
                startActivity(intent);

            }
        });

        adapterLeagueList.setLeagueShowClick(new AdapterLeagueList.LeagueShowClick() {
            @Override
            public void onClick(int position) {
                Toast.makeText(activityMain, "진행중인 리그참가페이지 이동.", Toast.LENGTH_SHORT).show();
            }
        });

        adapterLeagueList.setLeagueRankClick(new AdapterLeagueList.LeagueRankClick() {
            @Override
            public void onClick(int position) {
                Toast.makeText(activityMain, position+"등 랭커 상세보기", Toast.LENGTH_SHORT).show();
            }
        });

        adapterAdImage.setImageClick(new AdapterAdImage.ImageClick() {
            @Override
            public void onClick(int position, ImageView imageView) {
                Intent intent1 = new Intent(activityMain, ActivityCookPotImage.class);
                intent1.putExtra("imageUrl", position);
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(activityMain, imageView, "pair_thumb");
                startActivity(intent1, optionsCompat.toBundle());
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

                            case RxEvent.REFRESH_MY_POT:
                                setMyPotDate();
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

    }//onViewCreate 끝

    void setMyPotDate(){

        Filter filter = new Filter();
        Call<ModelPotList> getReList = RetrofitClient.getInstance().getService().getPotList("application/json", filter, "U", 0,0,10);
        getReList.enqueue(new Callback<ModelPotList>() {
            @Override
            public void onResponse(Call<ModelPotList> call, Response<ModelPotList> response) {
                modelMakeMyPot.clear();
                if (response.code() == 200) {
                    if (response.body().getTotalElements() == 0) {

                        modelMakeMyPot.add(new ModelMakeMyPot("", "", "", 0, true));
                    } else {

                        for (int index = 0; index < response.body().getContent().size(); index++) {

                            if(index == 3){
                                break;
                            }

                            modelMakeMyPot.add(new ModelMakeMyPot(response.body().getContent().get(index).getName(),
                                    response.body().getContent().get(index).getCode(),
                                    DateTransForm.dateTransForm(response.body().getContent().get(index).getDate()),
                                    DecimalScale.decimalScale(String.valueOf(response.body().getContent().get(index).getRate()*100), 2, 2), false));
                        }
                    }

                    adapter = new DemoInfiniteAdapter(activityMain, modelMakeMyPot, true);
                    viewPager.setAdapter(adapter);
                    //Custom bind indicator
                    indicatorView.setCount(viewPager.getIndicatorCount());
                    viewPager.setIndicatorPageChangeListener(new LoopingViewPager.IndicatorPageChangeListener() {
                        @Override
                        public void onIndicatorProgress(int selectingPosition, float progress) {
                            indicatorView.setProgress(selectingPosition, progress);
                        }
                        @Override
                        public void onIndicatorPageChange(int newIndicatorPosition) {
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<ModelPotList> call, Throwable t) {
                Log.e("받은값 에러","값 : "+t.getMessage());
            }
        });
    }


}

package com.quantec.moneypot.activity.prefer.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.quantec.moneypot.R;
import com.quantec.moneypot.activity.Main.Fragment.Tab1.DecorationItemVertical;
import com.quantec.moneypot.activity.prefer.ActivityPrefer;
import com.quantec.moneypot.activity.prefer.ModelEnterList;
import com.quantec.moneypot.activity.prefer.adapter.AdapterEnter;
import com.quantec.moneypot.rxandroid.RxEvent;
import com.quantec.moneypot.rxandroid.RxEventBus;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

import static com.quantec.moneypot.activity.prefer.ActivityPrefer.totalCount;

public class FgEnter extends Fragment {

    RecyclerView recyclerView;
    GridLayoutManager layoutManager;
    ArrayList<ModelEnterList> modelEnterLists;
    AdapterEnter adapterEnter;

    ActivityPrefer activityPrefer;
    View line;

    Bundle preferItem;

    boolean countState = true;

    private ClickItem clickItem;
    public interface ClickItem{
        public void onClick(boolean state,int count, String item, String bunya, String title, int realPosition);
    }

    public FgEnter() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_prefer_enter, container, false);

        initializeViews();

        line = view.findViewById(R.id.line);

        preferItem = new Bundle();

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(activityPrefer, 3);
        recyclerView.setLayoutManager(layoutManager);

        modelEnterLists = new ArrayList<>();

        modelEnterLists.add(new ModelEnterList("알파벳", "alphabet",false, false));
        modelEnterLists.add(new ModelEnterList("애플", "apple",false, false));
        modelEnterLists.add(new ModelEnterList("맥도날드", "mcdonald",false, false));

        modelEnterLists.add(new ModelEnterList("코카콜라", "cocacola",false, false));
        modelEnterLists.add(new ModelEnterList("아마존", "amazon",false, false));
        modelEnterLists.add(new ModelEnterList("페이스북", "facebook",false, false));

        modelEnterLists.add(new ModelEnterList("3M", "3_m",false, false));
        modelEnterLists.add(new ModelEnterList("비자", "visa",false, false));
        modelEnterLists.add(new ModelEnterList("마이크로소프트", "microsoft",false, false));

        modelEnterLists.add(new ModelEnterList("펩시", "pepsi",false, false));
        modelEnterLists.add(new ModelEnterList("알리바바", "alibaba",false, false));
        modelEnterLists.add(new ModelEnterList("넷플릭스", "netflix",false, false));

        recyclerView.addItemDecoration(new DecorationItemVertical(activityPrefer, 23));
        adapterEnter = new AdapterEnter(modelEnterLists, getContext());
        recyclerView.setAdapter(adapterEnter);

        return view;
    }

    private void initializeViews(){
        activityPrefer = (ActivityPrefer) getActivity();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof ActivityPrefer) {
            activityPrefer = (ActivityPrefer) context;
            clickItem = (ActivityPrefer)getActivity();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(layoutManager.findFirstCompletelyVisibleItemPosition() > 0) {
                    line.setVisibility(View.VISIBLE);
                }else{
                    line.setVisibility(View.INVISIBLE);
                }
            }
        });

        adapterEnter.setSectorItemClick(new AdapterEnter.SectorItemClick() {
            @Override
            public void onClick(int position) {

                if(modelEnterLists.get(position).isSelect()){
                    modelEnterLists.get(position).setSelect(false);

                    totalCount--;

                    if(clickItem != null){
                        clickItem.onClick(false, totalCount, modelEnterLists.get(position).getImage(),"기업",modelEnterLists.get(position).getTitle(), position);
                    }
                    adapterEnter.notifyItemChanged(position);
                }else{
                    if(totalCount < 5){
                        modelEnterLists.get(position).setSelect(true);
                        totalCount++;
                        adapterEnter.notifyItemChanged(position);

                        if(clickItem != null){
                            clickItem.onClick(true, totalCount, modelEnterLists.get(position).getImage(),"기업",modelEnterLists.get(position).getTitle(), position);
                        }

                    }else{
                        Toast.makeText(activityPrefer, "최대 5개까지 선택 가능합니다.", Toast.LENGTH_SHORT).show();
                    }
                }

                if(totalCount == 1 && countState){
                    countState = false;
                    modelEnterLists.add(new ModelEnterList("", "",false, true));
                    adapterEnter.notifyItemChanged(position);
                }
                if(totalCount == 0){
                    countState = true;
                    modelEnterLists.remove(modelEnterLists.size()-1);
                    adapterEnter.notifyDataSetChanged();
                }
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

                            case RxEvent.PREFER_ITEM_DELETE:
                                modelEnterLists.get(rxEvent.getBundle().getInt("realPosition")).setSelect(false);
                                adapterEnter.notifyItemChanged(rxEvent.getBundle().getInt("realPosition"));

                                if(totalCount == 0){
                                    countState = true;
                                    modelEnterLists.remove(modelEnterLists.size()-1);
                                    adapterEnter.notifyDataSetChanged();
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
    }
}

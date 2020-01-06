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
import com.quantec.moneypot.activity.prefer.ModelSectorList;
import com.quantec.moneypot.activity.prefer.adapter.AdapterSector;
import com.quantec.moneypot.rxandroid.RxEvent;
import com.quantec.moneypot.rxandroid.RxEventBus;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

import static com.quantec.moneypot.activity.prefer.ActivityPrefer.totalCount;

public class FgSector extends Fragment {

    RecyclerView recyclerView;
    AdapterSector adapterSector;
    GridLayoutManager layoutManager;
    ArrayList<ModelSectorList> modelSectorLists;

    ActivityPrefer activityPrefer;

    View line;

    Bundle preferItem;

    boolean countState = true;

    private ClickItem2 clickItem2;
    public interface ClickItem2{
        public void onClick2(boolean state,int count, String item, String bunya, String title, int realPosition);
    }

    public FgSector() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_prefer_sector, container, false);

        initializeViews();

        line = view.findViewById(R.id.line);

        preferItem = new Bundle();

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(activityPrefer, 3);
        recyclerView.setLayoutManager(layoutManager);

        modelSectorLists = new ArrayList<>();

        modelSectorLists.add(new ModelSectorList("IT 기술", "it",false,false));
        modelSectorLists.add(new ModelSectorList("애니메이션", "it",false,false));
        modelSectorLists.add(new ModelSectorList("게임", "it",false,false));

        modelSectorLists.add(new ModelSectorList("생활용품", "it",false,false));
        modelSectorLists.add(new ModelSectorList("패션", "it",false,false));
        modelSectorLists.add(new ModelSectorList("전자기기", "it",false,false));

        modelSectorLists.add(new ModelSectorList("담배", "it",false,false));
        modelSectorLists.add(new ModelSectorList("음식", "it",false,false));
        modelSectorLists.add(new ModelSectorList("청소", "it",false,false));

        modelSectorLists.add(new ModelSectorList("IT 기술", "it",false,false));
        modelSectorLists.add(new ModelSectorList("애니메이션", "it",false,false));
        modelSectorLists.add(new ModelSectorList("게임", "it",false,false));

        recyclerView.addItemDecoration(new DecorationItemVertical(activityPrefer, 23));
        adapterSector = new AdapterSector(modelSectorLists, getContext());
        recyclerView.setAdapter(adapterSector);

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
            clickItem2 = (ActivityPrefer) getActivity();
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

        adapterSector.setSectorItemClick(new AdapterSector.SectorItemClick() {
            @Override
            public void onClick(int position) {

                if(modelSectorLists.get(position).isSelect()){
                    modelSectorLists.get(position).setSelect(false);

                    totalCount--;

                    if(clickItem2 != null){
                        clickItem2.onClick2(false, totalCount, modelSectorLists.get(position).getImage(),"분야", modelSectorLists.get(position).getTitle(), position);
                    }
                    adapterSector.notifyItemChanged(position);
                }else{
                    if(totalCount < 5){
                        modelSectorLists.get(position).setSelect(true);
                        totalCount++;
                        adapterSector.notifyItemChanged(position);

                        if(clickItem2 != null){
                            clickItem2.onClick2(true, totalCount, modelSectorLists.get(position).getImage(),"분야", modelSectorLists.get(position).getTitle(), position);
                        }

                    }else{
                        Toast.makeText(activityPrefer, "최대 5개까지 선택 가능합니다.", Toast.LENGTH_SHORT).show();
                    }
                }

                if(totalCount == 1 && countState){
                    countState = false;
                    modelSectorLists.add(new ModelSectorList("", "",false, true));
                    adapterSector.notifyItemChanged(position);
                }
                if(totalCount == 0){
                    countState = true;
                    modelSectorLists.remove(modelSectorLists.size()-1);
                    adapterSector.notifyDataSetChanged();
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
                                modelSectorLists.get(rxEvent.getBundle().getInt("realPosition")).setSelect(false);
                                adapterSector.notifyItemChanged(rxEvent.getBundle().getInt("realPosition"));

                                if(totalCount == 0){
                                    countState = true;
                                    modelSectorLists.remove(modelSectorLists.size()-1);
                                    adapterSector.notifyDataSetChanged();
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

package quantec.com.moneypot.Activity.SearchPort.BasicPage.Fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import quantec.com.moneypot.Activity.DetailPort.ActivityDetailPort;
import quantec.com.moneypot.Activity.SearchPort.BasicPage.Fragment.Adapter.AdapterRecentlySearchTab;
import quantec.com.moneypot.Activity.SearchPort.BasicPage.Fragment.Dialog.DialogAllDelete;
import quantec.com.moneypot.Database.Room.Entry.RoomEntity;
import quantec.com.moneypot.Database.Room.ViewModel.SearchViewModel;
import quantec.com.moneypot.R;
import quantec.com.moneypot.databinding.FgActivitysearchRecentlysearchtabBinding;

public class Fg_RecentlySearchTab extends Fragment {

    private AdapterRecentlySearchTab adapterRecentlySearchTab;
    LinearLayoutManager linearLayoutManager;
    ArrayList<String> recentlySearchItems;

    private SearchViewModel searchViewModel;
    boolean DragState = true;

    private DialogAllDelete dialogAllDelete;

    FgActivitysearchRecentlysearchtabBinding recentlysearchtabBinding;

    public Fg_RecentlySearchTab() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        recentlysearchtabBinding = DataBindingUtil.inflate(inflater, R.layout.fg_activitysearch_recentlysearchtab, container ,false);
        recentlysearchtabBinding.setRecentlySearchedTab(Fg_RecentlySearchTab.this);

        recentlySearchItems = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recentlysearchtabBinding.portSearchPageRecommendRecyclerview.setLayoutManager(linearLayoutManager);
        recentlysearchtabBinding.portSearchPageRecommendRecyclerview.setHasFixedSize(true);

        adapterRecentlySearchTab = new AdapterRecentlySearchTab();
        recentlysearchtabBinding.portSearchPageRecommendRecyclerview.setAdapter(adapterRecentlySearchTab);

        return recentlysearchtabBinding.getRoot();

    }//onCreateView 끝

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchViewModel = ViewModelProviders.of(getActivity()).get(SearchViewModel.class);

        recentlysearchtabBinding.fragmentRecentlySearchTabTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAllDelete = new DialogAllDelete(getContext(), ALLleftListener, ALLrightListener);
                dialogAllDelete.show();
            }
        });

        searchViewModel.getmAllSearch().observe(this, new Observer<List<RoomEntity>>() {
            @Override
            public void onChanged(@Nullable List<RoomEntity> roomEntities) {
                if(roomEntities.size() == 0) {
                    DragState = false;
                    recentlysearchtabBinding.fragmentRecentlySearchTabTitle.setVisibility(View.GONE);
                }else{
                    DragState = true;
                    recentlysearchtabBinding.fragmentRecentlySearchTabTitle.setVisibility(View.VISIBLE);
                }
                adapterRecentlySearchTab.setRoomEntities(roomEntities);
            }
        });


        recentlysearchtabBinding.portSearchPageRecommendRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(linearLayoutManager.findFirstCompletelyVisibleItemPosition() > 0) {
                    recentlysearchtabBinding.fragmentRecentlySearchTabTitleLine.setVisibility(View.VISIBLE);
                }else{
                    recentlysearchtabBinding.fragmentRecentlySearchTabTitleLine.setVisibility(View.INVISIBLE);
                }
            }
        });

        // 최근 검색어 클릭시 상세페이지 이동
        adapterRecentlySearchTab.setRecentlySearchItemClick(new AdapterRecentlySearchTab.RecentlySearchItemClick() {
            @Override
            public void onClick(RoomEntity roomEntity) {
                Intent intent = new Intent(getActivity(), ActivityDetailPort.class);
                intent.putExtra("detailcode", roomEntity.getSearchCode());
                intent.putExtra("detailtitle", roomEntity.getSearchTItle());
                startActivity(intent);

            }
        });

        //최근 검색어 밀어서 삭제
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

            @Override
            public boolean isItemViewSwipeEnabled() {
                return DragState;
            }

            @Override
            public boolean isLongPressDragEnabled() {
                return DragState;
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return true;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                searchViewModel.delete(adapterRecentlySearchTab.getRoomAt(viewHolder.getAdapterPosition()));
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recentlysearchtabBinding.portSearchPageRecommendRecyclerview);

    }

    //최근 검색어 전체 삭제 취소
    private View.OnClickListener ALLleftListener = new View.OnClickListener() {
        public void onClick(View v) {
            dialogAllDelete.dismiss();
        }
    };

    //최근 검색어 전체 삭제 확인
    private View.OnClickListener ALLrightListener = new View.OnClickListener() {
        public void onClick(View v) {
            searchViewModel.deleteAll();
            dialogAllDelete.dismiss();
        }
    };

}

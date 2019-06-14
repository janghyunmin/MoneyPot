package quantec.com.moneypot.Activity.Search.BasicPage.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import quantec.com.moneypot.Activity.PotDetail.ActivityPotDetail;
import quantec.com.moneypot.Activity.Search.BasicPage.Adapter.AdapterRecentlySearchTab;
import quantec.com.moneypot.Database.Room.Entry.RoomEntity;
import quantec.com.moneypot.Database.Room.ViewModel.SearchViewModel;
import quantec.com.moneypot.Dialog.DialogAllDelete;
import quantec.com.moneypot.R;
import quantec.com.moneypot.databinding.FgRecentlysearchtabBinding;

public class FgRecentlySearchTab extends Fragment {

    private AdapterRecentlySearchTab adapterRecentlySearchTab;
    LinearLayoutManager linearLayoutManager;
    ArrayList<String> recentlySearchItems;

    private SearchViewModel searchViewModel;
    boolean DragState = true;

    private DialogAllDelete dialogAllDelete;

    FgRecentlysearchtabBinding binding;

    public FgRecentlySearchTab() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fg_recentlysearchtab, container ,false);

        recentlySearchItems = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(getActivity());
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        binding.recyclerView.setHasFixedSize(true);

        adapterRecentlySearchTab = new AdapterRecentlySearchTab();
        binding.recyclerView.setAdapter(adapterRecentlySearchTab);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchViewModel = ViewModelProviders.of(getActivity()).get(SearchViewModel.class);

        binding.allDeleteBt.setOnClickListener(new View.OnClickListener() {
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
                    binding.allDeleteBt.setVisibility(View.GONE);
                }else{
                    DragState = true;
                    binding.allDeleteBt.setVisibility(View.VISIBLE);
                }
                adapterRecentlySearchTab.setRoomEntities(roomEntities);
            }
        });


        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(linearLayoutManager.findFirstCompletelyVisibleItemPosition() > 0) {
                    binding.line.setVisibility(View.VISIBLE);
                }else{
                    binding.line.setVisibility(View.INVISIBLE);
                }
            }
        });

        // 최근 검색어 클릭시 상세페이지 이동
        adapterRecentlySearchTab.setRecentlySearchItemClick(new AdapterRecentlySearchTab.RecentlySearchItemClick() {
            @Override
            public void onClick(RoomEntity roomEntity) {
                Intent intent = new Intent(getActivity(), ActivityPotDetail.class);
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
        itemTouchHelper.attachToRecyclerView(binding.recyclerView);
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

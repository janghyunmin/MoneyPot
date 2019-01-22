package quantec.com.moneypot.Activity.SearchPort.BasicPage.Fragment.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import quantec.com.moneypot.Database.Room.Entry.RoomEntity;
import quantec.com.moneypot.R;

public class AdapterRecentlySearchTab extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int EMPTYITEM = 0;
    private final int RECENTLYITEM = 1;

    private List<RoomEntity> roomEntities = new ArrayList<>();

    private RecentlySearchItemClick recentlySearchItemClick;
    public interface RecentlySearchItemClick {
        public void onClick(RoomEntity roomEntity);
    }

    public void setRecentlySearchItemClick(RecentlySearchItemClick recentlySearchItemClick) {
        this.recentlySearchItemClick = recentlySearchItemClick;
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == EMPTYITEM){
            return new ItemEmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activitysearch_recentlyempty, parent, false));
        }else{
            return new RecentlySearchItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activitysearch_recentlydata, parent, false));
        }
    }

    @Override
    public int getItemViewType(int position) {

        if(roomEntities.size() == EMPTYITEM) {
            return EMPTYITEM;
        }
        else{
            return RECENTLYITEM;
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof RecentlySearchItemViewHolder) {

            RoomEntity currentRoom = roomEntities.get(position);

            ((RecentlySearchItemViewHolder)holder).item_search_page_recently_text.setBackgroundResource(R.drawable.bt_red_line_search_page);
            ((RecentlySearchItemViewHolder)holder).item_search_page_recently_text.setText(currentRoom.getSearchTItle());
        }
    }

    @Override
    public int getItemCount() {
        if(roomEntities.size() == 0) {
            return 1;
        }else{
            return roomEntities.size();
        }
    }

    public void setRoomEntities(List<RoomEntity> roomEntities){
        this.roomEntities = roomEntities;
        notifyDataSetChanged();
    }

    public RoomEntity getRoomAt(int position) {
        return roomEntities.get(position);
    }

    class RecentlySearchItemViewHolder extends RecyclerView.ViewHolder {
        TextView item_search_page_recently_text;
        public RecentlySearchItemViewHolder(View itemView) {
            super(itemView);
            item_search_page_recently_text = itemView.findViewById(R.id.item_search_page_recently_text);

            item_search_page_recently_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(recentlySearchItemClick != null && position != RecyclerView.NO_POSITION) {
                        recentlySearchItemClick.onClick(roomEntities.get(position));
                    }
                }
            });
        }
    }

    class ItemEmptyViewHolder extends RecyclerView.ViewHolder {
        public ItemEmptyViewHolder(View itemView) {
            super(itemView);
        }
    }
}

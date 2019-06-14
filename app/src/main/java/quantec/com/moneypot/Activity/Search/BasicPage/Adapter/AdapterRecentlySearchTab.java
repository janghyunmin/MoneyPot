package quantec.com.moneypot.Activity.Search.BasicPage.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
            return new ItemEmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_emptyrecentlysearchtab, parent, false));
        }else{
            return new RecentlySearchItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recentlysearchtab, parent, false));
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

            ((RecentlySearchItemViewHolder)holder).title.setBackgroundResource(R.drawable.bt_red_line_search_page);
            ((RecentlySearchItemViewHolder)holder).title.setText(currentRoom.getSearchTItle());
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
        TextView title;
        public RecentlySearchItemViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);

            title.setOnClickListener(new View.OnClickListener() {
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

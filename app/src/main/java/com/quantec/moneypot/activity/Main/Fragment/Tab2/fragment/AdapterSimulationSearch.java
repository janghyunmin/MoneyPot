package com.quantec.moneypot.activity.Main.Fragment.Tab2.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.quantec.moneypot.R;

public class AdapterSimulationSearch extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int SEARCH_ITEM = 0;
    private final int SEARCH_EMPTY = 1;

    ArrayList<ModelSearchItem> modelSearchItems;
    Context context;

    public AdapterSimulationSearch(ArrayList<ModelSearchItem> modelSearchItems, Context context) {
        this.modelSearchItems = modelSearchItems;
        this.context = context;
    }

    private SearchItemClick searchItemClick;
    public interface SearchItemClick {
        public void onClick(int position);
    }

    public void setSearchItemClick(SearchItemClick searchItemClick) {
        this.searchItemClick = searchItemClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == SEARCH_ITEM){
            return new SimulationSearchViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_simulationsearch, parent, false));
        }else{
            return new SimulationSearchEmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_simulationsearchempty, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof SimulationSearchViewHolder){
            ((SimulationSearchViewHolder)holder).title.setText(modelSearchItems.get(position).getTitle());

            ((SimulationSearchViewHolder)holder).itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(searchItemClick != null){
                        searchItemClick.onClick(position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return modelSearchItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(modelSearchItems.get(0).isEmpty()){
            return SEARCH_EMPTY;
        }else{
            return SEARCH_ITEM;
        }
    }

    public class SimulationSearchViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ConstraintLayout itemLayout;

        public SimulationSearchViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            itemLayout = itemView.findViewById(R.id.itemLayout);
        }
    }

    public class SimulationSearchEmptyViewHolder extends RecyclerView.ViewHolder {
        public SimulationSearchEmptyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

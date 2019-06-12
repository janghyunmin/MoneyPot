package quantec.com.moneypot.Activity.Main.Fragment.Tab2.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import quantec.com.moneypot.R;

public class AdapterLeagueForm extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int EMPTY = 0;
    private final int ITEM = 1;

    ArrayList<ModelLeagueFormMadePot> modelMadePots;
    Context context;

    public AdapterLeagueForm(ArrayList<ModelLeagueFormMadePot> modelMadePots, Context context) {
        this.modelMadePots = modelMadePots;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == EMPTY){
            return new EmptyItem(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_emptyleagueform, parent, false));
        }else{
            return new SelectPotViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_leagueform, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return modelMadePots.size();
    }

    @Override
    public int getItemViewType(int position) {

        if(modelMadePots.get(position).isEmpty()){
            return EMPTY;
        }
        else{
            return ITEM;
        }
    }

    public class SelectPotViewHolder extends RecyclerView.ViewHolder {
        public SelectPotViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class EmptyItem extends RecyclerView.ViewHolder {
        public EmptyItem(@NonNull View itemView) {
            super(itemView);
        }
    }


}

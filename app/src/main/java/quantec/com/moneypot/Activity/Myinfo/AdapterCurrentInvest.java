package quantec.com.moneypot.Activity.Myinfo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import quantec.com.moneypot.R;

public class AdapterCurrentInvest extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int EMPTYPOSITION = 0;
    private final int CURRENTINVESTPOSITION = 1;

    ArrayList<ModelCurrentInvestList> modelCurrentInvestLists;
    Context context;

    public AdapterCurrentInvest(ArrayList<ModelCurrentInvestList> modelCurrentInvestLists, Context context) {
        this.modelCurrentInvestLists = modelCurrentInvestLists;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == EMPTYPOSITION){
            return new EmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_emptycurrentinvest, parent, false));
        }else{
            return new CurrentInvestViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_currentinvest, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return modelCurrentInvestLists.size();
    }

    @Override
    public int getItemViewType(int position) {

        if(modelCurrentInvestLists.size() == 0){
            return EMPTYPOSITION;
        }else{
            return CURRENTINVESTPOSITION;
        }
    }

    public class CurrentInvestViewHolder extends RecyclerView.ViewHolder {
        public CurrentInvestViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class EmptyViewHolder extends RecyclerView.ViewHolder {
        public EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }
}

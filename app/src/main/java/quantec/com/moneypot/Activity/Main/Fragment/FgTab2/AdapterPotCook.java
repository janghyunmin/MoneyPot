package quantec.com.moneypot.Activity.Main.Fragment.FgTab2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.ModelPortList;
import quantec.com.moneypot.R;

public class AdapterPotCook extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<ModelPortList> modelPortLists;
    Context context;

    public AdapterPotCook(ArrayList<ModelPortList> modelPortLists, Context context) {
        this.modelPortLists = modelPortLists;
        this.context = context;
    }

    private PotDeleteClick potDeleteClick;
    public interface PotDeleteClick {
        public void onClick(int position);
    }

    public void setPotDeleteClick(PotDeleteClick potDeleteClick) {
        this.potDeleteClick = potDeleteClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PotCookViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_potcook, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof PotCookViewHolder){

            ((PotCookViewHolder)holder).category.setText(modelPortLists.get(position).getCategory());
            ((PotCookViewHolder)holder).stTitle.setText(modelPortLists.get(position).getStname());

            ((PotCookViewHolder)holder).deleteBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(potDeleteClick != null) {
                        potDeleteClick.onClick(position);
                    }
                }
            });
        }
    }

    public class PotCookViewHolder extends RecyclerView.ViewHolder {

        TextView category, stTitle, deleteBt;

        public PotCookViewHolder(View itemView) {
            super(itemView);

            category = itemView.findViewById(R.id.category);
            stTitle = itemView.findViewById(R.id.stTitle);
            deleteBt = itemView.findViewById(R.id.deleteBt);
        }
    }

    @Override
    public int getItemCount() {
        return modelPortLists.size();
    }
}

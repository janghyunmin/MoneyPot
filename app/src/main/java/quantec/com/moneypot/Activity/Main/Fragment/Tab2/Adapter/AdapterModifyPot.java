package quantec.com.moneypot.Activity.Main.Fragment.Tab2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import quantec.com.moneypot.R;

public class AdapterModifyPot extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int EMPTYVIEW = 0;
    private final int ITEMVIEW = 1;

    ArrayList<ModelModifyPot> modelModifyPots;
    Context context;

    public AdapterModifyPot(ArrayList<ModelModifyPot> modelModifyPots, Context context) {
        this.modelModifyPots = modelModifyPots;
        this.context = context;
    }

    private ModifyPotDeleteClick modifyPotDeleteClick;
    public interface ModifyPotDeleteClick {
        public void onClick(int position);
    }

    public void setModifyPotDeleteClick(ModifyPotDeleteClick modifyPotDeleteClick) {
        this.modifyPotDeleteClick = modifyPotDeleteClick;
    }

    private ModifyPotItemClick modifyPotItemClick;
    public interface ModifyPotItemClick {
        public void onClick(int position);
    }

    public void setModifyPotItemClick(ModifyPotItemClick modifyPotItemClick) {
        this.modifyPotItemClick = modifyPotItemClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == EMPTYVIEW){
            return new ModifyPotEmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_makepot_modifypotempty, parent, false));
        }else{
            return new ModifyPotViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_makepot_modifypot, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ModifyPotViewHolder){
            ((ModifyPotViewHolder)holder).title.setText(modelModifyPots.get(position).getTitle());

            ((ModifyPotViewHolder)holder).deleteBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(modifyPotDeleteClick != null){
                        modifyPotDeleteClick.onClick(position);
                    }
                }
            });

            ((ModifyPotViewHolder)holder).itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(modifyPotItemClick != null){
                        modifyPotItemClick.onClick(position);
                    }
                }
            });


        }
        else if(holder instanceof ModifyPotEmptyViewHolder){
        }
    }

    @Override
    public int getItemCount() {
        return modelModifyPots.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(modelModifyPots.get(position).isEmpty()){
            return EMPTYVIEW;
        }else{
            return ITEMVIEW;
        }
    }

    public class ModifyPotViewHolder extends RecyclerView.ViewHolder {

        TextView title, date, deleteBt;
        ConstraintLayout itemLayout;

        public ModifyPotViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            date = itemView.findViewById(R.id.date);
            deleteBt = itemView.findViewById(R.id.deleteBt);

            itemLayout = itemView.findViewById(R.id.itemLayout);
        }
    }

    public class ModifyPotEmptyViewHolder extends RecyclerView.ViewHolder {


        public ModifyPotEmptyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

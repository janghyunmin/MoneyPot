package com.quantec.moneypot.activity.Main.Fragment.Tab2.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.quantec.moneypot.R;
import com.quantec.moneypot.activity.Main.Fragment.Tab2.ModelModifyFollow;

import java.util.ArrayList;

public class AdapterModifyFollow extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int EMPTY = 0;
    private final int TOP = 1;
    private final int ITEM = 2;

    ArrayList<ModelModifyFollow> modelModifyFollows;
    Context context;

    public AdapterModifyFollow(ArrayList<ModelModifyFollow> modelModifyFollows, Context context) {
        this.modelModifyFollows = modelModifyFollows;
        this.context = context;
    }

    private ModifyFollowDelClick modifyFollowDelClick;
    public interface ModifyFollowDelClick{
        public void onClick(int position);
    }

    public void setModifyFollowDelClick(ModifyFollowDelClick modifyFollowDelClick) {
        this.modifyFollowDelClick = modifyFollowDelClick;
    }

    private ModifyFollowModiClick modifyFollowModiClick;
    public interface ModifyFollowModiClick{
        public void onClick(int position);
    }

    public void setModifyFollowModiClick(ModifyFollowModiClick modifyFollowModiClick) {
        this.modifyFollowModiClick = modifyFollowModiClick;
    }

    private ModifyFollowAllDelClick modifyFollowAllDelClick;
    public interface ModifyFollowAllDelClick{
        public void onClick(int position);
    }

    public void setModifyFollowAllDelClick(ModifyFollowAllDelClick modifyFollowAllDelClick) {
        this.modifyFollowAllDelClick = modifyFollowAllDelClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == EMPTY){
            return new ModifyFollowEmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_modifyfollow_empty, parent, false));
        }else if(viewType == TOP){
            return new ModifyFollowTopViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_modifyfollow_top, parent, false));
        }else{
            return new ModifyFollowViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_modifyfollow_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof ModifyFollowViewHolder){
            ((ModifyFollowViewHolder)holder).title.setText(modelModifyFollows.get(position).getTitle());
            ((ModifyFollowViewHolder)holder).delBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(modifyFollowDelClick != null){
                        modifyFollowDelClick.onClick(position);
                    }
                }
            });
            ((ModifyFollowViewHolder)holder).ModiBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(modifyFollowModiClick != null){
                        modifyFollowModiClick.onClick(position);
                    }
                }
            });
        }

        if(holder instanceof ModifyFollowTopViewHolder){
            ((ModifyFollowTopViewHolder)holder).num.setText(modelModifyFollows.get(0).getTotal()+"/10개");
            ((ModifyFollowTopViewHolder)holder).allDelBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(modifyFollowAllDelClick != null){
                        modifyFollowAllDelClick.onClick(position);
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return modelModifyFollows.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(modelModifyFollows.get(position).isEmpty()){
            return EMPTY;
        }else{
            if(position == 0){
                return TOP;
            }else{
                return ITEM;
            }
        }
    }

    public class ModifyFollowViewHolder extends RecyclerView.ViewHolder {

        TextView title, delBt;
        ImageView ModiBt;

        public ModifyFollowViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            delBt = itemView.findViewById(R.id.delBt);
            ModiBt = itemView.findViewById(R.id.ModiBt);
        }
    }

    public class ModifyFollowTopViewHolder extends RecyclerView.ViewHolder {

        TextView num, allDelBt;

        public ModifyFollowTopViewHolder(@NonNull View itemView) {
            super(itemView);

            num = itemView.findViewById(R.id.num);
            allDelBt = itemView.findViewById(R.id.allDelBt);

        }
    }

    public class ModifyFollowEmptyViewHolder extends RecyclerView.ViewHolder {
        public ModifyFollowEmptyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

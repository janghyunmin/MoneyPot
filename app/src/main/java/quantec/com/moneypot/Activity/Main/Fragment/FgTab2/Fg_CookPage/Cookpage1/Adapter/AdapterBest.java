package quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.Cookpage1.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.Cookpage1.Model.dModel.ModelBestList;
import quantec.com.moneypot.R;
import quantec.com.moneypot.databinding.ItemFgtab2Bestcookpage1Binding;

public class AdapterBest extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    ArrayList<ModelBestList> modelBestLists;
    Context context;

    private MoreItemClick moreItemClick;
    public interface MoreItemClick {
        public void onClick(int position);
    }

    public void setMoreItemClick(MoreItemClick moreItemClick) {
        this.moreItemClick = moreItemClick;
    }

    private MoreViewClick moreViewClick;
    public interface MoreViewClick{
        public void onClick(int position);
    }

    public void setMoreViewClick(MoreViewClick moreViewClick) {
        this.moreViewClick = moreViewClick;
    }

    public AdapterBest(ArrayList<ModelBestList> modelBestLists, Context context) {
        this.modelBestLists = modelBestLists;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BestViewHolder(ItemFgtab2Bestcookpage1Binding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof BestViewHolder) {

            ((BestViewHolder)holder).bestcookpage1Binding.itemBestCookpage1Title.setText(modelBestLists.get(position).getTitle());

            if(modelBestLists.get(position).getRate() < 0){
                ((BestViewHolder)holder).bestcookpage1Binding.itemBestCookpage1Rate.setText(String.valueOf(modelBestLists.get(position).getRate()));
                ((BestViewHolder)holder).bestcookpage1Binding.itemBestCookpage1Rate.setTextColor(context.getResources().getColor(R.color.make_port_blue_color));
                ((BestViewHolder)holder).bestcookpage1Binding.itemBestCookpage1Per.setTextColor(context.getResources().getColor(R.color.make_port_blue_color));
            }else{
                ((BestViewHolder)holder).bestcookpage1Binding.itemBestCookpage1Rate.setText(String.valueOf(modelBestLists.get(position).getRate()));
                ((BestViewHolder)holder).bestcookpage1Binding.itemBestCookpage1Rate.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
                ((BestViewHolder)holder).bestcookpage1Binding.itemBestCookpage1Per.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
            }

            ((BestViewHolder)holder).bestcookpage1Binding.itemBestCookpage1Rank.setText(modelBestLists.get(position).getRank());

            if(position == 0) {
                ((BestViewHolder)holder).bestcookpage1Binding.itemBestCookpage1Crown.setVisibility(View.VISIBLE);
            }else{
                ((BestViewHolder)holder).bestcookpage1Binding.itemBestCookpage1Crown.setVisibility(View.INVISIBLE);
            }

            ((BestViewHolder)holder).bestcookpage1Binding.itemBestCookpage1AddViewLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(moreViewClick != null) {
                        moreViewClick.onClick(position);
                    }
                }
            });

            ((BestViewHolder)holder).bestcookpage1Binding.itemBestCookpage1Layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(moreItemClick != null) {
                        moreItemClick.onClick(position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return modelBestLists.size();
    }

    public class BestViewHolder extends RecyclerView.ViewHolder {

        ItemFgtab2Bestcookpage1Binding bestcookpage1Binding;

        public BestViewHolder(ItemFgtab2Bestcookpage1Binding bestcookpage1Binding) {
            super(bestcookpage1Binding.getRoot());
            this.bestcookpage1Binding = bestcookpage1Binding;
        }
    }
}


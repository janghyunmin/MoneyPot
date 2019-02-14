package quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.Cookpage1.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.Cookpage1.Model.dModel.ModelStableList;
import quantec.com.moneypot.R;
import quantec.com.moneypot.databinding.ItemFgtab2Stablecookpage1Binding;

public class AdapterStable extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    ArrayList<ModelStableList> modelStableLists;
    Context context;

    private StableItemClick stableItemClick;
    public interface StableItemClick {
        public void onClick(int position);
    }

    public void setStableItemClick(StableItemClick stableItemClick) {
        this.stableItemClick = stableItemClick;
    }

    private StableBasketClick stableBasketClick;
    public interface StableBasketClick {
        public void onClick(int position);
    }

    public void setStableBasketClick(StableBasketClick stableBasketClick) {
        this.stableBasketClick = stableBasketClick;
    }

    private StableZzimClick stableZzimClick;
    public interface StableZzimClick {
        public void onClick(int position);
    }

    public void setStableZzimClick(StableZzimClick stableZzimClick) {
        this.stableZzimClick = stableZzimClick;
    }

    public AdapterStable(ArrayList<ModelStableList> modelStableLists, Context context) {
        this.modelStableLists = modelStableLists;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StableViewHolder(ItemFgtab2Stablecookpage1Binding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof StableViewHolder) {
            ((StableViewHolder)holder).stablecookpage1Binding.itemStableTitle.setText(modelStableLists.get(position).getTitle());
            ((StableViewHolder)holder).stablecookpage1Binding.itemStableRate.setText(String.valueOf(modelStableLists.get(position).getRate()));

            if(modelStableLists.get(position).getRate() < 0){
                ((StableViewHolder)holder).stablecookpage1Binding.itemStableRate.setTextColor(context.getResources().getColor(R.color.make_port_blue_color));
                ((StableViewHolder)holder).stablecookpage1Binding.itemStablePer.setTextColor(context.getResources().getColor(R.color.make_port_blue_color));
            }else{
                ((StableViewHolder)holder).stablecookpage1Binding.itemStableRate.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
                ((StableViewHolder)holder).stablecookpage1Binding.itemStablePer.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
            }

            if(!modelStableLists.get(position).isDam()) {
                ((StableViewHolder)holder).stablecookpage1Binding.itemStableCookbasket.setTextColor(context.getResources().getColor(R.color.dark_gray_color));
            }else{
                ((StableViewHolder)holder).stablecookpage1Binding.itemStableCookbasket.setTextColor(context.getResources().getColor(R.color.green_basket_color));
            }

            if(!modelStableLists.get(position).isZim()) {
                ((StableViewHolder)holder).stablecookpage1Binding.itemStableZzim.setBackgroundResource(R.drawable.start_off);
            }else{
                ((StableViewHolder)holder).stablecookpage1Binding.itemStableZzim.setBackgroundResource(R.drawable.start_on);
            }


            ((StableViewHolder)holder).stablecookpage1Binding.itemStableCookbasketLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(stableBasketClick != null) {
                        stableBasketClick.onClick(position);
                    }
                }
            });

            ((StableViewHolder)holder).stablecookpage1Binding.itemStableZzimLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(stableZzimClick != null) {
                        stableZzimClick.onClick(position);
                    }
                }
            });

            ((StableViewHolder)holder).stablecookpage1Binding.itemStableLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(stableItemClick != null) {
                        stableItemClick.onClick(position);
                    }
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return modelStableLists.size();
    }

    public class StableViewHolder extends RecyclerView.ViewHolder {
        ItemFgtab2Stablecookpage1Binding stablecookpage1Binding;

        public StableViewHolder(ItemFgtab2Stablecookpage1Binding stablecookpage1Binding) {
            super(stablecookpage1Binding.getRoot());
            this.stablecookpage1Binding = stablecookpage1Binding;

        }
    }

}

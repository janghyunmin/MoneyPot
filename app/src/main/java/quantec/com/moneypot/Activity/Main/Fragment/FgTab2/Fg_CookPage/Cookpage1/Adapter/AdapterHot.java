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

import quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.Cookpage1.Model.dModel.ModelHotList;
import quantec.com.moneypot.R;
import quantec.com.moneypot.databinding.ItemFgtab2Hotcookpage1Binding;

public class AdapterHot extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    ArrayList<ModelHotList> modelHotLists;
    Context context;

    private HotItemClick hotItemClick;
    public interface HotItemClick {
        public void onClick(int position);
    }

    public void setHotItemClick(HotItemClick hotItemClick) {
        this.hotItemClick = hotItemClick;
    }

    private HotZzimClick hotZzimClick;
    public interface HotZzimClick {
        public void onClick(int position);
    }

    public void setHotZzimClick(HotZzimClick hotZzimClick) {
        this.hotZzimClick = hotZzimClick;
    }


    private HotBasketClick hotBasketClick;
    public interface HotBasketClick {
        public void onClick(int position);
    }

    public void setHotBasketClick(HotBasketClick hotBasketClick) {
        this.hotBasketClick = hotBasketClick;
    }

    public AdapterHot(ArrayList<ModelHotList> modelHotLists, Context context) {
        this.modelHotLists = modelHotLists;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HotViewHolder(ItemFgtab2Hotcookpage1Binding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof HotViewHolder) {

            ((HotViewHolder)holder).hotcookpage1Binding.itemHotTitle.setText(modelHotLists.get(position).getTitle());

            if(modelHotLists.get(position).getRate() < 0) {
                ((HotViewHolder)holder).hotcookpage1Binding.itemHotRate.setText(String.valueOf(modelHotLists.get(position).getRate()));
                ((HotViewHolder)holder).hotcookpage1Binding.itemHotRate.setTextColor(context.getResources().getColor(R.color.make_port_blue_color));
                ((HotViewHolder)holder).hotcookpage1Binding.itemHotPer.setTextColor(context.getResources().getColor(R.color.make_port_blue_color));
            }else{
                ((HotViewHolder)holder).hotcookpage1Binding.itemHotRate.setText(String.valueOf(modelHotLists.get(position).getRate()));
                ((HotViewHolder)holder).hotcookpage1Binding.itemHotRate.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
                ((HotViewHolder)holder).hotcookpage1Binding.itemHotPer.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
            }

            if(modelHotLists.get(position).getBasket() == 0) {
                ((HotViewHolder)holder).hotcookpage1Binding.itemHotCookbasket.setTextColor(context.getResources().getColor(R.color.dark_gray_color));
            }else{
                ((HotViewHolder)holder).hotcookpage1Binding.itemHotCookbasket.setTextColor(context.getResources().getColor(R.color.green_basket_color));
            }

            if(modelHotLists.get(position).getZzim() == 0) {
                ((HotViewHolder)holder).hotcookpage1Binding.itemHotZzim.setBackgroundResource(R.drawable.start_off);
            }else{
                ((HotViewHolder)holder).hotcookpage1Binding.itemHotZzim.setBackgroundResource(R.drawable.start_on);
            }

            // 찜하기 이벤트
            ((HotViewHolder)holder).hotcookpage1Binding.itemHotZzim.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(hotZzimClick != null){
                        hotZzimClick.onClick(position);
                    }
                }
            });

            // 담기 이벤트
            ((HotViewHolder)holder).hotcookpage1Binding.itemHotCookbasketLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(hotBasketClick != null) {
                        hotBasketClick.onClick(position);
                    }
                }
            });

            // 핫 포트 클릭 이벤트
            ((HotViewHolder)holder).hotcookpage1Binding.itemHotLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(hotItemClick != null) {
                        hotItemClick.onClick(position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return modelHotLists.size();
    }

    public class HotViewHolder extends RecyclerView.ViewHolder {

        ItemFgtab2Hotcookpage1Binding hotcookpage1Binding;

        public HotViewHolder(ItemFgtab2Hotcookpage1Binding hotcookpage1Binding) {
            super(hotcookpage1Binding.getRoot());
            this.hotcookpage1Binding = hotcookpage1Binding;

        }
    }

}

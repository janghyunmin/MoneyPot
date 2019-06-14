package quantec.com.moneypot.Activity.Search.SearchedPage.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import quantec.com.moneypot.DataModel.dModel.ModelStockItem;
import quantec.com.moneypot.R;
import quantec.com.moneypot.databinding.ItemAlltabStockBinding;

public class AdapterAllTabStock extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int STOCKNUMPOSITION = 0;
    private final int STOCKPOSITION = 1;
    private final int STOCKADDVIEWPOSITION  = 2;

    ArrayList<ModelStockItem> stockItemModels;
    Context context;

    public AdapterAllTabStock(ArrayList<ModelStockItem> stockItemModels, Context context) {
        this.stockItemModels = stockItemModels;
        this.context = context;
    }

    private StockItemClick stockItemClick;
    public interface StockItemClick{
        public void onClick(int position);
    }

    public void setStockItemClick(StockItemClick stockItemClick) {
        this.stockItemClick = stockItemClick;
    }


    private StockZzimClick stockZzimClick;
    public interface StockZzimClick {
        public void onClick(int position);
    }

    public void setStockZzimClick(StockZzimClick stockZzimClick) {
        this.stockZzimClick = stockZzimClick;
    }

    private StockAddviewClick stockAddviewClick;
    public interface StockAddviewClick {
        public void onClick(int position);
    }

    public void setStockAddviewClick(StockAddviewClick stockAddviewClick) {
        this.stockAddviewClick = stockAddviewClick;
    }

    @Override
    public int getItemViewType(int position) {

        if(position == 0){
            return STOCKNUMPOSITION;
        }else if(position == stockItemModels.size()-1){
            return STOCKADDVIEWPOSITION;
        }else{
            return STOCKPOSITION;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == STOCKNUMPOSITION){
            return new StockNumViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alltab_number, parent, false));
        }else if(viewType == STOCKADDVIEWPOSITION){
            return new StockAddViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alltab_clickbt, parent, false));
        }else{
            return new StockViewHolder(ItemAlltabStockBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof StockNumViewHolder) {
            String num = "포트 종목 "+stockItemModels.get(position).getTotalNum() + "건";
            ((StockNumViewHolder)holder).number.setText(num);

        }

        if(holder instanceof StockAddViewHolder) {

            ((StockAddViewHolder)holder).allPage_tab_stock_addView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(stockAddviewClick != null) {
                        stockAddviewClick.onClick(position);
                    }
                }
            });
        }

        if(holder instanceof StockViewHolder) {
            ((StockViewHolder)holder).binding.name.setText(stockItemModels.get(position).getName());
            ((StockViewHolder)holder).binding.rate.setText(String.valueOf(stockItemModels.get(position).getRate()));
            ((StockViewHolder)holder).binding.desc.setText(stockItemModels.get(position).getSname());


            if(!stockItemModels.get(position).isZim()) {
                ((StockViewHolder) holder).binding.checkImage.setBackgroundResource(R.drawable.start_off);
            }else{
                ((StockViewHolder) holder).binding.checkImage.setBackgroundResource(R.drawable.start_on);
            }

            if(stockItemModels.get(position).getRate() < 0) {
                ((StockViewHolder) holder).binding.rate.setTextColor(context.getResources().getColor(R.color.make_port_blue_color));
                ((StockViewHolder) holder).binding.per.setTextColor(context.getResources().getColor(R.color.make_port_blue_color));
            }else{
                ((StockViewHolder) holder).binding.rate.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
                ((StockViewHolder) holder).binding.per.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
            }


            ((StockViewHolder) holder).binding.stockLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(stockItemClick != null) {
                        stockItemClick.onClick(position);
                    }
                }
            });

            ((StockViewHolder) holder).binding.checkBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(stockZzimClick != null) {
                        stockZzimClick.onClick(position);
                    }
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return stockItemModels.size();
    }


    public static class StockNumViewHolder extends RecyclerView.ViewHolder {
        TextView number;
        public StockNumViewHolder(View itemView) {
            super(itemView);
            number = itemView.findViewById(R.id.number);
        }
    }

    public static class StockViewHolder extends RecyclerView.ViewHolder {
        ItemAlltabStockBinding binding;

        public StockViewHolder(ItemAlltabStockBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public static class StockAddViewHolder extends RecyclerView.ViewHolder {
        TextView allPage_tab_stock_addView;
        public StockAddViewHolder(View itemView) {
            super(itemView);
            allPage_tab_stock_addView = itemView.findViewById(R.id.allPage_tab_title_addView);

        }
    }
}

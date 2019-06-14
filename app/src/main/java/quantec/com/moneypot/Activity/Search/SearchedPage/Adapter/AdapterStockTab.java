package quantec.com.moneypot.Activity.Search.SearchedPage.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import quantec.com.moneypot.DataModel.dModel.ModelPostStockItem;
import quantec.com.moneypot.R;
import quantec.com.moneypot.databinding.ItemStocktabBinding;

public class AdapterStockTab extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final int STOCKNUMBER = 0;
    private final int STOCKDATA = 1;

    private final int[] EMPTY = {205, 206, 207, 208};


    ArrayList<ModelPostStockItem> postStockItemModels;
    Context context;


    public AdapterStockTab(ArrayList<ModelPostStockItem> postStockItemModels, Context context) {
        this.postStockItemModels = postStockItemModels;
        this.context = context;
    }

    private StockPageItemClick stockPageItemClick;
    public interface StockPageItemClick {
        public void onClick(int position);
    }

    public void setStockPageItemClick(StockPageItemClick stockPageItemClick) {
        this.stockPageItemClick = stockPageItemClick;
    }

    private StockPageZzimClick stockPageZzimClick;
    public interface StockPageZzimClick {
        public void onClick(int position);
    }

    public void setStockPageZzimClick(StockPageZzimClick stockPageZzimClick) {
        this.stockPageZzimClick = stockPageZzimClick;
    }

    @Override
    public int getItemViewType(int position) {

        if(position == 0){
            return STOCKNUMBER;
        }else{
            return STOCKDATA;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int Hcategory = postStockItemModels.get(0).getCategory();
        if(Hcategory == EMPTY[0] || Hcategory == EMPTY[1] || Hcategory == EMPTY[2] || Hcategory == EMPTY[3]){
            return new EmptyStockViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_stocktab_empty, parent, false));
        }else{

            if(viewType == STOCKNUMBER){
                return new StockNumberViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_stocktab_number, parent, false));
            }else{
                return new StockDataViewHolder(ItemStocktabBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof StockNumberViewHolder) {
            String StockNum = "포트 종목 "+postStockItemModels.get(position).getTotalNum()+"건";
            ((StockNumberViewHolder)holder).number.setText(StockNum);
        }

        if(holder instanceof StockDataViewHolder) {

            ((StockDataViewHolder)holder).binding.name.setText(postStockItemModels.get(position).getName());
            ((StockDataViewHolder)holder).binding.desc.setText(postStockItemModels.get(position).getSname());
            ((StockDataViewHolder)holder).binding.rate.setText(String.valueOf(postStockItemModels.get(position).getRate()));

            if(!postStockItemModels.get(position).isZim()) {
                ((StockDataViewHolder) holder).binding.checkImage.setBackgroundResource(R.drawable.start_off);
            }else{
                ((StockDataViewHolder) holder).binding.checkImage.setBackgroundResource(R.drawable.start_on);
            }

            if(postStockItemModels.get(position).getRate() < 0) {
                ((StockDataViewHolder) holder).binding.rate.setTextColor(context.getResources().getColor(R.color.make_port_blue_color));
                ((StockDataViewHolder) holder).binding.per.setTextColor(context.getResources().getColor(R.color.make_port_blue_color));
            }else{
                ((StockDataViewHolder) holder).binding.rate.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
                ((StockDataViewHolder) holder).binding.per.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
            }

            ((StockDataViewHolder) holder).binding.checkBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(stockPageZzimClick != null) {
                        stockPageZzimClick.onClick(position);
                    }
                }
            });

            ((StockDataViewHolder) holder).binding.tabLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(stockPageItemClick != null) {
                        stockPageItemClick.onClick(position);
                    }
                }
            });

        }

        if(holder instanceof EmptyStockViewHolder) {
            String EmptyNum = "포트 종목 "+postStockItemModels.get(position).getTotalNum()+"건";
            ((EmptyStockViewHolder)holder).title.setText(EmptyNum);
        }

    }

    @Override
    public int getItemCount() {
        int category = postStockItemModels.get(0).getCategory();
        if(category == EMPTY[0] || category == EMPTY[1] || category == EMPTY[2] || category == EMPTY[3]){
            return 1;
        }else{
            return postStockItemModels.size();
        }
    }

    public static class StockNumberViewHolder extends RecyclerView.ViewHolder {
        TextView number;
        public StockNumberViewHolder(View itemView) {
            super(itemView);
            number = itemView.findViewById(R.id.number);
        }
    }

    public static class StockDataViewHolder extends RecyclerView.ViewHolder {

        ItemStocktabBinding binding;

        public StockDataViewHolder(ItemStocktabBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }

    public static class EmptyStockViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        public EmptyStockViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
        }
    }


}
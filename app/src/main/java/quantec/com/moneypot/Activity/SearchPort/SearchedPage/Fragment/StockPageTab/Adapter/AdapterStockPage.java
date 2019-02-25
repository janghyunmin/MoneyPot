package quantec.com.moneypot.Activity.SearchPort.SearchedPage.Fragment.StockPageTab.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import quantec.com.moneypot.Activity.SearchPort.SearchedPage.Model.dModel.ModelPostStockItem;
import quantec.com.moneypot.R;
import quantec.com.moneypot.databinding.ItemActivitysearchStockpagetabdataBinding;

public class AdapterStockPage extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final int STOCKNUMBER = 0;
    private final int STOCKDATA = 1;

    private final int[] EMPTY = {205, 206, 207, 208};


    ArrayList<ModelPostStockItem> postStockItemModels;
    Context context;


    public AdapterStockPage(ArrayList<ModelPostStockItem> postStockItemModels, Context context) {
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
            return new EmptyStockViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activitysearch_stockpageempty, parent, false));
        }else{

            if(viewType == STOCKNUMBER){
                return new StockNumberViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activitysearch_stockpagenumber, parent, false));
            }else{
                return new StockDataViewHolder(ItemActivitysearchStockpagetabdataBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof StockNumberViewHolder) {
            String StockNum = "포트 종목 "+postStockItemModels.get(position).getTotalNum()+"건";
            ((StockNumberViewHolder)holder).stockPage_tab_desc_num.setText(StockNum);
        }

        if(holder instanceof StockDataViewHolder) {

            ((StockDataViewHolder)holder).stockpagetabdataBinding.stockPageTabStockName.setText(postStockItemModels.get(position).getName());
            ((StockDataViewHolder)holder).stockpagetabdataBinding.stockPageTabStockDescript.setText(postStockItemModels.get(position).getSname());
            ((StockDataViewHolder)holder).stockpagetabdataBinding.stockPageTabStockRate.setText(String.valueOf(postStockItemModels.get(position).getRate()));

            if(!postStockItemModels.get(position).isZim()) {
                ((StockDataViewHolder) holder).stockpagetabdataBinding.stockPageTabStockCheckImage.setBackgroundResource(R.drawable.start_off);
            }else{
                ((StockDataViewHolder) holder).stockpagetabdataBinding.stockPageTabStockCheckImage.setBackgroundResource(R.drawable.start_on);
            }

            if(postStockItemModels.get(position).getRate() < 0) {
                ((StockDataViewHolder) holder).stockpagetabdataBinding.stockPageTabStockRate.setTextColor(context.getResources().getColor(R.color.make_port_blue_color));
                ((StockDataViewHolder) holder).stockpagetabdataBinding.stockPageTabStockPer.setTextColor(context.getResources().getColor(R.color.make_port_blue_color));
            }else{
                ((StockDataViewHolder) holder).stockpagetabdataBinding.stockPageTabStockRate.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
                ((StockDataViewHolder) holder).stockpagetabdataBinding.stockPageTabStockPer.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
            }

            ((StockDataViewHolder) holder).stockpagetabdataBinding.stockPageTabStockCheckBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(stockPageZzimClick != null) {
                        stockPageZzimClick.onClick(position);
                    }
                }
            });

            ((StockDataViewHolder) holder).stockpagetabdataBinding.stockPageTabLayout.setOnClickListener(new View.OnClickListener() {
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
            ((EmptyStockViewHolder)holder).stockPage_tab_empty_title.setText(EmptyNum);
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
        TextView stockPage_tab_desc_num;
        public StockNumberViewHolder(View itemView) {
            super(itemView);
            stockPage_tab_desc_num = itemView.findViewById(R.id.stockPage_tab_desc_num);
        }
    }

    public static class StockDataViewHolder extends RecyclerView.ViewHolder {

        ItemActivitysearchStockpagetabdataBinding stockpagetabdataBinding;

        public StockDataViewHolder(ItemActivitysearchStockpagetabdataBinding stockpagetabdataBinding) {
            super(stockpagetabdataBinding.getRoot());

            this.stockpagetabdataBinding = stockpagetabdataBinding;
        }
    }

    public static class EmptyStockViewHolder extends RecyclerView.ViewHolder {
        TextView stockPage_tab_empty_title;
        public EmptyStockViewHolder(View itemView) {
            super(itemView);
            stockPage_tab_empty_title = itemView.findViewById(R.id.stockPage_tab_empty_title);
        }
    }


}

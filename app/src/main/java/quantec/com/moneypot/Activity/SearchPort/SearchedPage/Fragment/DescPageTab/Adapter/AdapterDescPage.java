package quantec.com.moneypot.Activity.SearchPort.SearchedPage.Fragment.DescPageTab.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import quantec.com.moneypot.Activity.SearchPort.SearchedPage.Model.dModel.ModelPostDescItem;
import quantec.com.moneypot.R;
import quantec.com.moneypot.databinding.ItemActivitysearchAllpagedescpagetabdataBinding;

public class AdapterDescPage extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final int DESCNUMBER = 0;
    private final int DESCDATA = 1;

    private final int[] EMPTY = {202, 203, 207, 208};

    ArrayList<ModelPostDescItem> postDescItemModels;
    Context context;

    public AdapterDescPage(ArrayList<ModelPostDescItem> postDescItemModels, Context context) {
        this.postDescItemModels = postDescItemModels;
        this.context = context;
    }


    private DescPageItemClick descPageItemClick;
    public interface DescPageItemClick {
        public void onClick(int position);
    }

    public void setDescPageItemClick(DescPageItemClick descPageItemClick) {
        this.descPageItemClick = descPageItemClick;
    }

    private DescPageZzimClick descPageZzimClick;
    public interface DescPageZzimClick {
        public void onClick(int position);
    }

    public void setDescPageZzimClick(DescPageZzimClick descPageZzimClick) {
        this.descPageZzimClick = descPageZzimClick;
    }

    @Override
    public int getItemViewType(int position) {

        if(position == 0){
            return DESCNUMBER;
        }else{
            return DESCDATA;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int Hcategory = postDescItemModels.get(0).getCategory();
        if(Hcategory == EMPTY[0] || Hcategory == EMPTY[1] || Hcategory == EMPTY[2] || Hcategory == EMPTY[3]){
            return new EmptyDescViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activitysearch_allpagedescpageempty, parent, false));
        }else{

            if(viewType == DESCNUMBER){
                return new DescNumberViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activitysearch_allpagedescpagenumber, parent, false));
            }else{

                return new DescDataViewHolder(ItemActivitysearchAllpagedescpagetabdataBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof DescNumberViewHolder) {
            String DescNum = "포트 내용 "+postDescItemModels.get(position).getTotalNum()+"건";
            ((DescNumberViewHolder)holder).descPage_tab_desc_num.setText(DescNum);
        }

        if(holder instanceof DescDataViewHolder) {

            ((DescDataViewHolder)holder).descpagetabdataBinding.descPageTabDescName.setText(postDescItemModels.get(position).getName());
            ((DescDataViewHolder)holder).descpagetabdataBinding.descPageTabDescDescript.setText(postDescItemModels.get(position).getDesc());
            ((DescDataViewHolder)holder).descpagetabdataBinding.descPageTabDescRate.setText(String.valueOf(postDescItemModels.get(position).getRate()));

            if(!postDescItemModels.get(position).isSelect()) {
                ((DescDataViewHolder) holder).descpagetabdataBinding.descPageTabDescCheckImage.setBackgroundResource(R.drawable.start_off);
            }else{
                ((DescDataViewHolder) holder).descpagetabdataBinding.descPageTabDescCheckImage.setBackgroundResource(R.drawable.start_on);
            }

            if(postDescItemModels.get(position).getRate() < 0) {
                ((DescDataViewHolder) holder).descpagetabdataBinding.descPageTabDescRate.setTextColor(context.getResources().getColor(R.color.make_port_blue_color));
                ((DescDataViewHolder) holder).descpagetabdataBinding.descPageTabDescPer.setTextColor(context.getResources().getColor(R.color.make_port_blue_color));
            }else{
                ((DescDataViewHolder) holder).descpagetabdataBinding.descPageTabDescRate.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
                ((DescDataViewHolder) holder).descpagetabdataBinding.descPageTabDescPer.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
            }

            ((DescDataViewHolder) holder).descpagetabdataBinding.descPageTabDescCheckBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(descPageZzimClick != null) {
                        descPageZzimClick.onClick(position);
                    }
                }
            });

            ((DescDataViewHolder) holder).descpagetabdataBinding.descPageTabLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(descPageItemClick != null) {
                        descPageItemClick.onClick(position);
                    }
                }
            });

        }

        if(holder instanceof EmptyDescViewHolder) {
            String EmptyNum = "포트 내용 "+postDescItemModels.get(position).getTotalNum()+"건";
            ((EmptyDescViewHolder)holder).descPage_tab_empty_title.setText(EmptyNum);
        }

    }

    @Override
    public int getItemCount() {
        int category = postDescItemModels.get(0).getCategory();
        if(category == EMPTY[0] || category == EMPTY[1] || category == EMPTY[2] || category == EMPTY[3]){
            return 1;
        }else{
            return postDescItemModels.size();
        }
    }

    public static class DescNumberViewHolder extends RecyclerView.ViewHolder {
        TextView descPage_tab_desc_num;
        public DescNumberViewHolder(View itemView) {
            super(itemView);
            descPage_tab_desc_num = itemView.findViewById(R.id.descPage_tab_desc_num);
        }
    }

    public static class DescDataViewHolder extends RecyclerView.ViewHolder {

        ItemActivitysearchAllpagedescpagetabdataBinding descpagetabdataBinding;

        public DescDataViewHolder(ItemActivitysearchAllpagedescpagetabdataBinding descpagetabdataBinding) {
            super(descpagetabdataBinding.getRoot());
            this.descpagetabdataBinding = descpagetabdataBinding;
        }
    }

    public static class EmptyDescViewHolder extends RecyclerView.ViewHolder {
        TextView descPage_tab_empty_title;
        public EmptyDescViewHolder(View itemView) {
            super(itemView);
            descPage_tab_empty_title = itemView.findViewById(R.id.descPage_tab_empty_title);
        }
    }

}

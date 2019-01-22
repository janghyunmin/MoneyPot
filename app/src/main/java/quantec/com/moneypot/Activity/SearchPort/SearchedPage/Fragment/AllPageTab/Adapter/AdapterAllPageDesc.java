package quantec.com.moneypot.Activity.SearchPort.SearchedPage.Fragment.AllPageTab.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import quantec.com.moneypot.Activity.SearchPort.SearchedPage.Model.dModel.ModelDescItem;
import quantec.com.moneypot.R;
import quantec.com.moneypot.databinding.ItemActivitysearchAllpagetabdescBinding;

public class AdapterAllPageDesc extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int PORTNUMPOSITION = 0;
    private final int PORTDESCPOSITION = 1;
    private final int PORTADDVIEWPOSITION = 2;

    ArrayList<ModelDescItem> descItemModels;
    Context context;

    public AdapterAllPageDesc(ArrayList<ModelDescItem> descItemModels, Context context) {
        this.descItemModels = descItemModels;
        this.context = context;
    }

    private DescItemClick descItemClick;
    public interface DescItemClick {
        public void onClick(int position);
    }

    public void setDescItemClick(DescItemClick descItemClick) {
        this.descItemClick = descItemClick;
    }

    private DescZzimClick descZzimClick;
    public interface DescZzimClick{
        public void onClick(int position);
    }

    public void setDescZzimClick(DescZzimClick descZzimClick) {
        this.descZzimClick = descZzimClick;
    }

    private DescAddviewClick descAddviewClick;
    public interface DescAddviewClick {
        public void onClick(int position);
    }

    public void setDescAddviewClick(DescAddviewClick descAddviewClick) {
        this.descAddviewClick = descAddviewClick;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return PORTNUMPOSITION;
        }else if(position == descItemModels.size()-1){
            return PORTADDVIEWPOSITION;
        }else{
            return PORTDESCPOSITION;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == PORTNUMPOSITION){
            return new DescNumViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activitysearch_allpagenumber, parent, false));
        }else if(viewType == PORTADDVIEWPOSITION){
            return new DescAddViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activitysearch_allpageclickbt, parent, false));
        }else{

            return new DescViewHolder(ItemActivitysearchAllpagetabdescBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof DescNumViewHolder) {

            String num = "포트 내용 "+descItemModels.get(position).getTotalNum()+"건";
            ((DescNumViewHolder) holder).allPage_tab_desc_num.setText(num);

        }

        if(holder instanceof DescAddViewHolder) {

            ((DescAddViewHolder)holder).allPage_tab_desc_addView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(descAddviewClick !=null) {
                        descAddviewClick.onClick(position);
                    }
                }
            });

        }

        if(holder instanceof DescViewHolder) {

            ((DescViewHolder) holder).tabDescBinding.allPageTabDescName.setText(descItemModels.get(position).getName());
            ((DescViewHolder) holder).tabDescBinding.allPageTabDescDescript.setText(descItemModels.get(position).getDesc());
            ((DescViewHolder) holder).tabDescBinding.allPageTabDescRate.setText(String.valueOf(descItemModels.get(position).getRate()));

            if(descItemModels.get(position).getSelect() == 0) {
                ((DescViewHolder) holder).tabDescBinding.allPageTabDescCheckImage.setBackgroundResource(R.drawable.start_off);
            }else{
                ((DescViewHolder) holder).tabDescBinding.allPageTabDescCheckImage.setBackgroundResource(R.drawable.start_on);
            }

            if(descItemModels.get(position).getRate() < 0) {
                ((DescViewHolder) holder).tabDescBinding.allPageTabDescRate.setTextColor(context.getResources().getColor(R.color.make_port_blue_color));
                ((DescViewHolder) holder).tabDescBinding.allPageTabDescPer.setTextColor(context.getResources().getColor(R.color.make_port_blue_color));
            }else{
                ((DescViewHolder) holder).tabDescBinding.allPageTabDescRate.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
                ((DescViewHolder) holder).tabDescBinding.allPageTabDescPer.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
            }


            ((DescViewHolder) holder).tabDescBinding.allPageTabDescLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(descItemClick != null) {
                        descItemClick.onClick(position);
                    }
                }
            });

            ((DescViewHolder) holder).tabDescBinding.allPageTabDescCheckBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(descZzimClick != null) {
                        descZzimClick.onClick(position);
                    }
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return descItemModels.size();
    }

    public static class DescNumViewHolder extends RecyclerView.ViewHolder {
        TextView allPage_tab_desc_num;
        public DescNumViewHolder(View itemView) {
            super(itemView);
            allPage_tab_desc_num = itemView.findViewById(R.id.allPage_tab_title_num);
        }
    }

    public static class DescViewHolder extends RecyclerView.ViewHolder {
        ItemActivitysearchAllpagetabdescBinding tabDescBinding;

        public DescViewHolder(ItemActivitysearchAllpagetabdescBinding tabDescBinding) {
            super(tabDescBinding.getRoot());

            this.tabDescBinding = tabDescBinding;
        }
    }

    public static class DescAddViewHolder extends RecyclerView.ViewHolder {
        TextView allPage_tab_desc_addView;
        public DescAddViewHolder(View itemView) {
            super(itemView);
            allPage_tab_desc_addView = itemView.findViewById(R.id.allPage_tab_title_addView);
        }
    }
}

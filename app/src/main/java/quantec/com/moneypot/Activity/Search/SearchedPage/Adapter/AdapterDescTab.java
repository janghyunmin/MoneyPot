package quantec.com.moneypot.Activity.Search.SearchedPage.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import quantec.com.moneypot.DataModel.dModel.ModelPostDescItem;
import quantec.com.moneypot.R;
import quantec.com.moneypot.databinding.ItemDesctabBinding;

public class AdapterDescTab extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final int DESCNUMBER = 0;
    private final int DESCDATA = 1;

    private final int[] EMPTY = {202, 203, 207, 208};

    ArrayList<ModelPostDescItem> postDescItemModels;
    Context context;

    public AdapterDescTab(ArrayList<ModelPostDescItem> postDescItemModels, Context context) {
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
            return new EmptyDescViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_desctab_empty, parent, false));
        }else{

            if(viewType == DESCNUMBER){
                return new DescNumberViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_desctab_number, parent, false));
            }else{

                return new DescDataViewHolder(ItemDesctabBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof DescNumberViewHolder) {
            String DescNum = "포트 내용 "+postDescItemModels.get(position).getTotalNum()+"건";
            ((DescNumberViewHolder)holder).number.setText(DescNum);
        }

        if(holder instanceof DescDataViewHolder) {

            ((DescDataViewHolder)holder).binding.name.setText(postDescItemModels.get(position).getName());
            ((DescDataViewHolder)holder).binding.desc.setText(postDescItemModels.get(position).getDesc());
            ((DescDataViewHolder)holder).binding.rate.setText(String.valueOf(postDescItemModels.get(position).getRate()));

            if(!postDescItemModels.get(position).isSelect()) {
                ((DescDataViewHolder) holder).binding.checkImage.setBackgroundResource(R.drawable.start_off);
            }else{
                ((DescDataViewHolder) holder).binding.checkImage.setBackgroundResource(R.drawable.start_on);
            }

            if(postDescItemModels.get(position).getRate() < 0) {
                ((DescDataViewHolder) holder).binding.rate.setTextColor(context.getResources().getColor(R.color.make_port_blue_color));
                ((DescDataViewHolder) holder).binding.per.setTextColor(context.getResources().getColor(R.color.make_port_blue_color));
            }else{
                ((DescDataViewHolder) holder).binding.rate.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
                ((DescDataViewHolder) holder).binding.per.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
            }

            ((DescDataViewHolder) holder).binding.checkBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(descPageZzimClick != null) {
                        descPageZzimClick.onClick(position);
                    }
                }
            });

            ((DescDataViewHolder) holder).binding.tabLayout.setOnClickListener(new View.OnClickListener() {
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
            ((EmptyDescViewHolder)holder).title.setText(EmptyNum);
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
        TextView number;
        public DescNumberViewHolder(View itemView) {
            super(itemView);
            number = itemView.findViewById(R.id.number);
        }
    }

    public static class DescDataViewHolder extends RecyclerView.ViewHolder {

        ItemDesctabBinding binding;

        public DescDataViewHolder(ItemDesctabBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public static class EmptyDescViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        public EmptyDescViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
        }
    }

}
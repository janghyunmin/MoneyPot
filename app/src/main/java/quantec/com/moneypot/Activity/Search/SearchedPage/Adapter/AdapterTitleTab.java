package quantec.com.moneypot.Activity.Search.SearchedPage.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import quantec.com.moneypot.DataModel.dModel.ModelPostTitleItem;
import quantec.com.moneypot.R;
import quantec.com.moneypot.databinding.ItemTitletabBinding;

public class AdapterTitleTab extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final int TITLENUMBER = 0;
    private final int TITLEDATA = 1;

    private final int[] EMPTY = {201, 203, 206, 208};

    ArrayList<ModelPostTitleItem> postTitleItemModels;
    Context context;

    public AdapterTitleTab(ArrayList<ModelPostTitleItem> postTitleItemModels, Context context) {
        this.postTitleItemModels = postTitleItemModels;
        this.context = context;
    }

    private TitlePageItemClick titlePageItemClick;
    public interface TitlePageItemClick {
        public void onClick(int position);
    }

    public void setTitlePageItemClick(TitlePageItemClick titlePageItemClick) {
        this.titlePageItemClick = titlePageItemClick;
    }

    private TitlePageZzimClicke titlePageZzimClicke;
    public interface TitlePageZzimClicke {
        public void onClick(int position);
    }

    public void setTitlePageZzimClicke(TitlePageZzimClicke titlePageZzimClicke) {
        this.titlePageZzimClicke = titlePageZzimClicke;
    }

    @Override
    public int getItemViewType(int position) {

        if(position == 0) {
            return TITLENUMBER;
        }else{
            return TITLEDATA;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int Hcategory = postTitleItemModels.get(0).getCategory();
        if(Hcategory == EMPTY[0] || Hcategory == EMPTY[1] || Hcategory == EMPTY[2] || Hcategory == EMPTY[3]) {
            return new EmptyTitleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_titletab_empty, parent, false));
        }else{
            if(viewType == TITLENUMBER) {
                return new TitleNumberViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_titletab_number, parent, false));
            }else{
                return new TitleDataViewHolder(ItemTitletabBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof TitleNumberViewHolder) {
            String num = "포트 제목 "+postTitleItemModels.get(position).getTotalNum()+"건";
            ((TitleNumberViewHolder)holder).number.setText(num);
        }

        if(holder instanceof TitleDataViewHolder) {
            ((TitleDataViewHolder)holder).binding.name.setText(postTitleItemModels.get(position).getName());
            ((TitleDataViewHolder)holder).binding.rate.setText(String.valueOf(postTitleItemModels.get(position).getRate()));

            if(!postTitleItemModels.get(position).isZim()) {
                ((TitleDataViewHolder) holder).binding.checkImage.setBackgroundResource(R.drawable.start_off);
            }else{
                ((TitleDataViewHolder) holder).binding.checkImage.setBackgroundResource(R.drawable.start_on);
            }

            if(postTitleItemModels.get(position).getRate() < 0) {
                ((TitleDataViewHolder) holder).binding.rate.setTextColor(context.getResources().getColor(R.color.make_port_blue_color));
                ((TitleDataViewHolder) holder).binding.per.setTextColor(context.getResources().getColor(R.color.make_port_blue_color));
            }else{
                ((TitleDataViewHolder) holder).binding.rate.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
                ((TitleDataViewHolder) holder).binding.per.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
            }

            ((TitleDataViewHolder) holder).binding.checkBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(titlePageZzimClicke != null) {
                        titlePageZzimClicke.onClick(position);
                    }
                }
            });

            ((TitleDataViewHolder) holder).binding.titlePageTabLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(titlePageItemClick != null) {
                        titlePageItemClick.onClick(position);
                    }
                }
            });

        }

        if(holder instanceof EmptyTitleViewHolder) {
            String EmptyNum = "포트 제목 "+postTitleItemModels.get(position).getTotalNum()+"건";
            ((EmptyTitleViewHolder)holder).title.setText(EmptyNum);
        }
    }

    @Override
    public int getItemCount() {
        int category = postTitleItemModels.get(0).getCategory();
        if(category == EMPTY[0] || category == EMPTY[1] || category == EMPTY[2] || category == EMPTY[3]) {
            return 1;
        }else{
            return postTitleItemModels.size();
        }
    }

    public static class TitleNumberViewHolder extends RecyclerView.ViewHolder {
        TextView number;

        public TitleNumberViewHolder(View itemView) {
            super(itemView);

            number = itemView.findViewById(R.id.number);
        }
    }

    public static class TitleDataViewHolder extends RecyclerView.ViewHolder {
        ItemTitletabBinding binding;

        public TitleDataViewHolder(ItemTitletabBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }

    public static class EmptyTitleViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        public EmptyTitleViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
        }
    }

}
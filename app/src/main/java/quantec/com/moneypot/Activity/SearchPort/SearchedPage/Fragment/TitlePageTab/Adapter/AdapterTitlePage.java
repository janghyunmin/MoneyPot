package quantec.com.moneypot.Activity.SearchPort.SearchedPage.Fragment.TitlePageTab.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import quantec.com.moneypot.Activity.SearchPort.SearchedPage.Model.dModel.ModelPostTitleItem;
import quantec.com.moneypot.R;
import quantec.com.moneypot.databinding.ItemActivitysearchTitlepagetabdataBinding;

public class AdapterTitlePage extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final int TITLENUMBER = 0;
    private final int TITLEDATA = 1;

    private final int[] EMPTY = {201, 203, 206, 208};

    ArrayList<ModelPostTitleItem> postTitleItemModels;
    Context context;

    public AdapterTitlePage(ArrayList<ModelPostTitleItem> postTitleItemModels, Context context) {
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
            return new EmptyTitleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activitysearch_titlepageempty, parent, false));
        }else{
            if(viewType == TITLENUMBER) {
                return new TitleNumberViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activitysearch_titlepagenumber, parent, false));
            }else{
                return new TitleDataViewHolder(ItemActivitysearchTitlepagetabdataBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof TitleNumberViewHolder) {
            String num = "포트 제목 "+postTitleItemModels.get(position).getTotalNum()+"건";
            ((TitleNumberViewHolder)holder).titlePage_tab_title_num.setText(num);
        }

        if(holder instanceof TitleDataViewHolder) {
            ((TitleDataViewHolder)holder).titlepagetabdataBinding.titlePageTabTitleName.setText(postTitleItemModels.get(position).getName());
            ((TitleDataViewHolder)holder).titlepagetabdataBinding.titlePageTabTitleRate.setText(String.valueOf(postTitleItemModels.get(position).getRate()));

            if(postTitleItemModels.get(position).getSelect() == 0) {
                ((TitleDataViewHolder) holder).titlepagetabdataBinding.titlePageTabTitleCheckImage.setBackgroundResource(R.drawable.start_off);
            }else{
                ((TitleDataViewHolder) holder).titlepagetabdataBinding.titlePageTabTitleCheckImage.setBackgroundResource(R.drawable.start_on);
            }

            if(postTitleItemModels.get(position).getRate() < 0) {
                ((TitleDataViewHolder) holder).titlepagetabdataBinding.titlePageTabTitleRate.setTextColor(context.getResources().getColor(R.color.make_port_blue_color));
                ((TitleDataViewHolder) holder).titlepagetabdataBinding.titlePageTabTitlePer.setTextColor(context.getResources().getColor(R.color.make_port_blue_color));
            }else{
                ((TitleDataViewHolder) holder).titlepagetabdataBinding.titlePageTabTitleRate.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
                ((TitleDataViewHolder) holder).titlepagetabdataBinding.titlePageTabTitlePer.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
            }

            ((TitleDataViewHolder) holder).titlepagetabdataBinding.titlePageTabTitleCheckBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(titlePageZzimClicke != null) {
                        titlePageZzimClicke.onClick(position);
                    }
                }
            });

            ((TitleDataViewHolder) holder).titlepagetabdataBinding.titlePageTabLayout.setOnClickListener(new View.OnClickListener() {
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
            ((EmptyTitleViewHolder)holder).titlePage_tab_empty_title.setText(EmptyNum);
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
        TextView titlePage_tab_title_num;

        public TitleNumberViewHolder(View itemView) {
            super(itemView);

            titlePage_tab_title_num = itemView.findViewById(R.id.titlePage_tab_title_num);
        }
    }

    public static class TitleDataViewHolder extends RecyclerView.ViewHolder {
        ItemActivitysearchTitlepagetabdataBinding titlepagetabdataBinding;

        public TitleDataViewHolder(ItemActivitysearchTitlepagetabdataBinding titlepagetabdataBinding) {
            super(titlepagetabdataBinding.getRoot());

            this.titlepagetabdataBinding = titlepagetabdataBinding;
        }
    }

    public static class EmptyTitleViewHolder extends RecyclerView.ViewHolder {
        TextView titlePage_tab_empty_title;

        public EmptyTitleViewHolder(View itemView) {
            super(itemView);
            titlePage_tab_empty_title = itemView.findViewById(R.id.titlePage_tab_empty_title);
        }
    }

}


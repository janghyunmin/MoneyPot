package quantec.com.moneypot.Activity.SearchPort.SearchedPage.Fragment.AllPageTab.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import quantec.com.moneypot.Activity.SearchPort.SearchedPage.Model.dModel.ModelEmptyItem;
import quantec.com.moneypot.Activity.SearchPort.SearchedPage.Model.dModel.ModelTitleItem;
import quantec.com.moneypot.R;
import quantec.com.moneypot.databinding.ItemActivitysearchAllpagetabtitleBinding;

public class AdapterAllPageTitle extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int PORTNUMBERPOSITION = 0;
    private final int PORTTITLEPOSITION = 1;
    private final int PORTADDVIEWPOSITION = 2;

    private final int EMPTYTOPPOSITION = 10;
    private final int EMPTYMIDDLEPOSITION = 11;
    private final int EMPTYVIEWPOSITION = 12;

    private final int EMPTY = 208;

    ArrayList<ModelTitleItem> titleItemModels;
    ArrayList<ModelEmptyItem> emptyItemModels;
    Context context;

    public AdapterAllPageTitle(ArrayList<ModelTitleItem> titleItemModels, ArrayList<ModelEmptyItem> emptyItemModels, Context context) {
        this.titleItemModels = titleItemModels;
        this.emptyItemModels = emptyItemModels;
        this.context = context;
    }

    private TitleItemClick titleItemClick;
    public interface TitleItemClick {
        public void onClick(int position);
    }

    public void setTitleItemClick(TitleItemClick titleItemClick) {
        this.titleItemClick = titleItemClick;
    }

    private TitleZzimClick titleZzimClick;
    public interface TitleZzimClick {
        public void onClick(int position);
    }

    public void setTitleZzimClick(TitleZzimClick titleZzimClick) {
        this.titleZzimClick = titleZzimClick;
    }

    private TitleAddViewClick titleAddViewClick;
    public interface TitleAddViewClick {
        public void onClick(int position);
    }

    public void setTitleAddViewClick(TitleAddViewClick titleAddViewClick) {
        this.titleAddViewClick = titleAddViewClick;
    }

    private EmptyTextClick emptyTextClick;
    public interface EmptyTextClick {
        public void onClick(int position);
    }

    public void setEmptyTextClick(EmptyTextClick emptyTextClick) {
        this.emptyTextClick = emptyTextClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(titleItemModels.get(0).getCategory() == EMPTY) {
            if (viewType == EMPTYTOPPOSITION) {
                return new AdapterAllPageTitle.EmptyTopTitleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activitysearch_allpageemptyresulttop, parent, false));
            } else if (viewType == EMPTYMIDDLEPOSITION) {
                return new AdapterAllPageTitle.EmptyMiddleTitleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activitysearch_allpageemptyresultmiddle, parent, false));
            } else {
                return new AdapterAllPageTitle.EmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activitysearch_allpageemptytext, parent, false));
            }


        }else {
            if (viewType == PORTNUMBERPOSITION) {
                return new AdapterAllPageTitle.TitleNumViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activitysearch_allpagenumber, parent, false));
            } else if (viewType == PORTADDVIEWPOSITION) {
                return new AdapterAllPageTitle.TitleAddViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activitysearch_allpageclickbt, parent, false));
            } else {

                return new AdapterAllPageTitle.TitleViewHolder(ItemActivitysearchAllpagetabtitleBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
            }
        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof AdapterAllPageTitle.TitleNumViewHolder) {

            String number = "포트 제목 "+titleItemModels.get(position).getTotalNum()+"건";
            ((AdapterAllPageTitle.TitleNumViewHolder) holder).allPage_tab_title_num.setText(number);
        }

        if(holder instanceof AdapterAllPageTitle.TitleAddViewHolder) {

            ((AdapterAllPageTitle.TitleAddViewHolder) holder).allPage_tab_title_addView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(titleAddViewClick != null) {
                        titleAddViewClick.onClick(position);
                    }
                }
            });
        }

        if(holder instanceof AdapterAllPageTitle.TitleViewHolder) {

            ((AdapterAllPageTitle.TitleViewHolder) holder).itemTitleBinding.allPageTabTitleName.setText(titleItemModels.get(position).getName());
            ((AdapterAllPageTitle.TitleViewHolder) holder).itemTitleBinding.allPageTabTitleRate.setText(String.valueOf(titleItemModels.get(position).getRate()));

            if(titleItemModels.get(position).getSelect() == 0) {
                ((AdapterAllPageTitle.TitleViewHolder) holder).itemTitleBinding.allPageTabTitleCheckImage.setBackgroundResource(R.drawable.start_off);
            }else{
                ((AdapterAllPageTitle.TitleViewHolder) holder).itemTitleBinding.allPageTabTitleCheckImage.setBackgroundResource(R.drawable.start_on);
            }

            if(titleItemModels.get(position).getRate() < 0) {
                ((AdapterAllPageTitle.TitleViewHolder) holder).itemTitleBinding.allPageTabTitleRate.setTextColor(context.getResources().getColor(R.color.make_port_blue_color));
                ((AdapterAllPageTitle.TitleViewHolder) holder).itemTitleBinding.allPageTabTitlePer.setTextColor(context.getResources().getColor(R.color.make_port_blue_color));
            }else{
                ((AdapterAllPageTitle.TitleViewHolder) holder).itemTitleBinding.allPageTabTitleRate.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
                ((AdapterAllPageTitle.TitleViewHolder) holder).itemTitleBinding.allPageTabTitlePer.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
            }


            ((AdapterAllPageTitle.TitleViewHolder) holder).itemTitleBinding.allPageTabTitleLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(titleItemClick != null) {
                        titleItemClick.onClick(position);
                    }
                }
            });


            ((AdapterAllPageTitle.TitleViewHolder) holder).itemTitleBinding.allPageTabTitleCheckBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(titleZzimClick != null) {
                        titleZzimClick.onClick(position);
                    }
                }
            });

        }

        if(holder instanceof AdapterAllPageTitle.EmptyViewHolder) {
            ((AdapterAllPageTitle.EmptyViewHolder)holder).allPage_tab_empty_text.setText("#"+emptyItemModels.get(position).getName());

            ((AdapterAllPageTitle.EmptyViewHolder)holder).allPage_tab_empty_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(emptyTextClick != null) {
                        emptyTextClick.onClick(position);
                    }
                }
            });

        }
    }

    @Override
    public int getItemViewType(int position) {

        if(titleItemModels.get(0).getCategory() == EMPTY) {

            if(position == 0) {
                return EMPTYTOPPOSITION;
            }else if(position == 1) {
                return EMPTYMIDDLEPOSITION;
            }else{
                return EMPTYVIEWPOSITION;
            }

        }else{

            if(position == 0) {
                return PORTNUMBERPOSITION;
            }else if(position == titleItemModels.size()-1) {
                return PORTADDVIEWPOSITION;
            }else{
                return PORTTITLEPOSITION;
            }

        }
    }

    @Override
    public int getItemCount() {
        if(titleItemModels.size() != 0) {
            if(titleItemModels.get(0).getCategory() == 208){
                return emptyItemModels.size();
            }else{
                return titleItemModels.size();
            }
        }else{
            return titleItemModels.size();
        }
    }

    public static class TitleNumViewHolder extends RecyclerView.ViewHolder {

        TextView allPage_tab_title_num;
        public TitleNumViewHolder(View itemView) {
            super(itemView);

            allPage_tab_title_num = itemView.findViewById(R.id.allPage_tab_title_num);
        }
    }

    public static class TitleViewHolder extends RecyclerView.ViewHolder {
        ItemActivitysearchAllpagetabtitleBinding itemTitleBinding;

        public TitleViewHolder(ItemActivitysearchAllpagetabtitleBinding itemTitleBinding) {
            super(itemTitleBinding.getRoot());
            this.itemTitleBinding = itemTitleBinding;
        }
    }

    public static class TitleAddViewHolder extends RecyclerView.ViewHolder {
        TextView allPage_tab_title_addView;

        public TitleAddViewHolder(View itemView) {
            super(itemView);
            allPage_tab_title_addView = itemView.findViewById(R.id.allPage_tab_title_addView);
        }
    }
    public static class EmptyTopTitleViewHolder extends RecyclerView.ViewHolder {
        public EmptyTopTitleViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class EmptyMiddleTitleViewHolder extends RecyclerView.ViewHolder {
        public EmptyMiddleTitleViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class EmptyViewHolder extends RecyclerView.ViewHolder {
        TextView allPage_tab_empty_text;
        public EmptyViewHolder(View itemView) {
            super(itemView);
            allPage_tab_empty_text = itemView.findViewById(R.id.allPage_tab_empty_text);
        }
    }
}

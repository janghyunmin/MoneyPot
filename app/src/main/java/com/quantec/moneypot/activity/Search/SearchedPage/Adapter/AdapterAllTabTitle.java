package com.quantec.moneypot.activity.Search.SearchedPage.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.quantec.moneypot.datamodel.dmodel.ModelEmptyItem;
import com.quantec.moneypot.datamodel.dmodel.ModelTitleItem;
import com.quantec.moneypot.R;
import com.quantec.moneypot.databinding.ItemAlltabTitleBinding;

public class AdapterAllTabTitle extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
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

    public AdapterAllTabTitle(ArrayList<ModelTitleItem> titleItemModels, ArrayList<ModelEmptyItem> emptyItemModels, Context context) {
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
                return new EmptyTopTitleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alltab_emptytop, parent, false));
            } else if (viewType == EMPTYMIDDLEPOSITION) {
                return new EmptyMiddleTitleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alltab_emptymiddle, parent, false));
            } else {
                return new EmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alltab_emptytext, parent, false));
            }

        }else {
            if (viewType == PORTNUMBERPOSITION) {
                return new TitleNumViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alltab_number, parent, false));
            } else if (viewType == PORTADDVIEWPOSITION) {
                return new TitleAddViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alltab_clickbt, parent, false));
            } else {

                return new TitleViewHolder(ItemAlltabTitleBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
            }
        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof TitleNumViewHolder) {

            String number = "포트 제목 "+titleItemModels.get(position).getTotalNum()+"건";
            ((TitleNumViewHolder) holder).number.setText(number);
        }

        if(holder instanceof TitleAddViewHolder) {

            ((TitleAddViewHolder) holder).allPage_tab_title_addView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(titleAddViewClick != null) {
                        titleAddViewClick.onClick(position);
                    }
                }
            });
        }

        if(holder instanceof TitleViewHolder) {

            ((TitleViewHolder) holder).binding.titleName.setText(titleItemModels.get(position).getName());
            ((TitleViewHolder) holder).binding.titleRate.setText(String.valueOf(titleItemModels.get(position).getRate()));

            if(!titleItemModels.get(position).isZim()) {
                ((TitleViewHolder) holder).binding.checkImage.setBackgroundResource(R.drawable.start_off);
            }else{
                ((TitleViewHolder) holder).binding.checkImage.setBackgroundResource(R.drawable.start_on);
            }

            if(titleItemModels.get(position).getRate() < 0) {
                ((TitleViewHolder) holder).binding.titleRate.setTextColor(context.getResources().getColor(R.color.make_port_blue_color));
                ((TitleViewHolder) holder).binding.titlePer.setTextColor(context.getResources().getColor(R.color.make_port_blue_color));
            }else{
                ((TitleViewHolder) holder).binding.titleRate.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
                ((TitleViewHolder) holder).binding.titlePer.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
            }


            ((TitleViewHolder) holder).binding.titleLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(titleItemClick != null) {
                        titleItemClick.onClick(position);
                    }
                }
            });


            ((TitleViewHolder)holder).binding.titleCheckBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(titleZzimClick != null) {
                        titleZzimClick.onClick(position);
                    }
                }
            });

        }

        if(holder instanceof EmptyViewHolder) {
            ((EmptyViewHolder)holder).allPage_tab_empty_text.setText("#"+emptyItemModels.get(position).getName());

            ((EmptyViewHolder)holder).allPage_tab_empty_text.setOnClickListener(new View.OnClickListener() {
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

        TextView number;
        public TitleNumViewHolder(View itemView) {
            super(itemView);

            number = itemView.findViewById(R.id.number);
        }
    }

    public static class TitleViewHolder extends RecyclerView.ViewHolder {
        ItemAlltabTitleBinding binding;

        public TitleViewHolder(ItemAlltabTitleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
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
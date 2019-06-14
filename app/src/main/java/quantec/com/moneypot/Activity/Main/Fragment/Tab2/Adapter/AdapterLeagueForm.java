package quantec.com.moneypot.Activity.Main.Fragment.Tab2.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import quantec.com.moneypot.R;

public class AdapterLeagueForm extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int EMPTY = 0;
    private final int ITEM = 1;

    ArrayList<ModelLeagueFormMadePot> modelMadePots;
    Context context;

    public AdapterLeagueForm(ArrayList<ModelLeagueFormMadePot> modelMadePots, Context context) {
        this.modelMadePots = modelMadePots;
        this.context = context;
    }


    private LeagueFormCheckBtClick leagueFormCheckBtClick;
    public interface LeagueFormCheckBtClick {
        public void onClick(int position);
    }

    public void setLeagueFormCheckBtClick(LeagueFormCheckBtClick leagueFormCheckBtClick) {
        this.leagueFormCheckBtClick = leagueFormCheckBtClick;
    }

    private LeagueFormItemClick leagueFormItemClick;
    public interface LeagueFormItemClick {
        public void onClick(int position);
    }

    public void setLeagueFormItemClick(LeagueFormItemClick leagueFormItemClick) {
        this.leagueFormItemClick = leagueFormItemClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == EMPTY){
            return new EmptyItem(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_emptyleagueform, parent, false));
        }else{
            return new SelectPotViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_leagueform, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof EmptyItem){

        }

        else if(holder instanceof SelectPotViewHolder){

            if(modelMadePots.get(position).isCheckBt()){
                ((SelectPotViewHolder)holder).checkBt.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_checkbox_on_red));
            }else{
                ((SelectPotViewHolder)holder).checkBt.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_checkbox_off_whitegray));
            }

            ((SelectPotViewHolder)holder).title.setText(modelMadePots.get(position).getTitle());
            ((SelectPotViewHolder)holder).date.setText(modelMadePots.get(position).getDate());
            ((SelectPotViewHolder)holder).rate.setText(String.valueOf(modelMadePots.get(position).getRate()));

            if(modelMadePots.get(position).getRate() >= 0){
                ((SelectPotViewHolder)holder).rate.setTextColor(context.getResources().getColor(R.color.red_text_color));
                ((SelectPotViewHolder)holder).per.setTextColor(context.getResources().getColor(R.color.red_text_color));
            }else{
                ((SelectPotViewHolder)holder).rate.setTextColor(context.getResources().getColor(R.color.blue_color));
                ((SelectPotViewHolder)holder).per.setTextColor(context.getResources().getColor(R.color.blue_color));
            }

            ((SelectPotViewHolder)holder).itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(leagueFormItemClick != null){
                        leagueFormItemClick.onClick(position);
                    }
                }
            });

            ((SelectPotViewHolder)holder).checkBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(leagueFormCheckBtClick != null){
                        leagueFormCheckBtClick.onClick(position);
                    }
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return modelMadePots.size();
    }

    @Override
    public int getItemViewType(int position) {

        if(modelMadePots.get(position).isEmpty()){
            return EMPTY;
        }
        else{
            return ITEM;
        }
    }

    public class SelectPotViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout itemLayout;
        ImageView checkBt;
        TextView date, title, rate, per;


        public SelectPotViewHolder(@NonNull View itemView) {
            super(itemView);

            itemLayout = itemView.findViewById(R.id.itemLayout);
            checkBt = itemView.findViewById(R.id.checkBt);
            date = itemView.findViewById(R.id.date);
            title = itemView.findViewById(R.id.title);
            rate = itemView.findViewById(R.id.rate);
            per = itemView.findViewById(R.id.per);

        }
    }

    public class EmptyItem extends RecyclerView.ViewHolder {
        public EmptyItem(@NonNull View itemView) {
            super(itemView);
        }
    }


}

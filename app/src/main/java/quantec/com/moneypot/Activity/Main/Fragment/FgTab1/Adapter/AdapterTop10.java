package quantec.com.moneypot.Activity.Main.Fragment.FgTab1.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import quantec.com.moneypot.Activity.Main.Fragment.FgTab1.Fg_tab1;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab1.Model.dModel.ModelMainPortTop10Item3;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab1.Model.dModel.ModelMainPortTop3Item10;
import quantec.com.moneypot.R;

public class AdapterTop10 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<ModelMainPortTop10Item3> modelMainPortTop10Item3s;
    ArrayList<ModelMainPortTop3Item10> modelMainPortTop3Item10s;
    Context context;

    private Top10ItemClick top10ItemClick;
    public interface Top10ItemClick {
        public void onClick(int position);
    }

    public void setTop10ItemClick(Top10ItemClick top10ItemClick) {
        this.top10ItemClick = top10ItemClick;
    }

    public AdapterTop10(ArrayList<ModelMainPortTop10Item3> modelMainPortTop10Item3s, ArrayList<ModelMainPortTop3Item10> modelMainPortTop3Item10s, Context context) {
        this.modelMainPortTop10Item3s = modelMainPortTop10Item3s;
        this.modelMainPortTop3Item10s = modelMainPortTop3Item10s;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Top10ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fgtab1_top10, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof Top10ViewHolder) {

            ((Top10ViewHolder)holder).Item_Top10_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(top10ItemClick != null) {
                        top10ItemClick.onClick(position);
                    }
                }
            });


            if(Fg_tab1.more_top10){

                ((Top10ViewHolder)holder).Item_Top10_num.setText(String.valueOf(modelMainPortTop3Item10s.get(position).getNum()));
                ((Top10ViewHolder)holder).Item_Top10_name.setText(modelMainPortTop3Item10s.get(position).getName());

                if(modelMainPortTop3Item10s.get(position).getRete() < 0) {
                    ((Top10ViewHolder)holder).Item_Top10_rate.setText(String.format("%.2f",modelMainPortTop3Item10s.get(position).getRete()));
                    ((Top10ViewHolder)holder).Item_Top10_rate.setTextColor(context.getResources().getColor(R.color.make_port_blue_color));
                    ((Top10ViewHolder)holder).Item_Top10_per.setTextColor(context.getResources().getColor(R.color.make_port_blue_color));
                }else{
                    ((Top10ViewHolder)holder).Item_Top10_rate.setText(String.format("%.2f",modelMainPortTop3Item10s.get(position).getRete()));
                    ((Top10ViewHolder)holder).Item_Top10_rate.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
                    ((Top10ViewHolder)holder).Item_Top10_per.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
                }

            }else{

                ((Top10ViewHolder)holder).Item_Top10_num.setText(String.valueOf(modelMainPortTop10Item3s.get(position).getNum()));
                ((Top10ViewHolder)holder).Item_Top10_name.setText(modelMainPortTop10Item3s.get(position).getName());

                if(modelMainPortTop10Item3s.get(position).getRete() < 0) {
                    ((Top10ViewHolder)holder).Item_Top10_rate.setText(String.format("%.2f",modelMainPortTop10Item3s.get(position).getRete()));
                    ((Top10ViewHolder)holder).Item_Top10_rate.setTextColor(context.getResources().getColor(R.color.make_port_blue_color));
                    ((Top10ViewHolder)holder).Item_Top10_per.setTextColor(context.getResources().getColor(R.color.make_port_blue_color));
                }else{
                    ((Top10ViewHolder)holder).Item_Top10_rate.setText(String.format("%.2f",modelMainPortTop10Item3s.get(position).getRete()));
                    ((Top10ViewHolder)holder).Item_Top10_rate.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
                    ((Top10ViewHolder)holder).Item_Top10_per.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
                }
            }

        }
    }

    @Override
    public int getItemCount() {
        if(Fg_tab1.more_top10) {
            return modelMainPortTop3Item10s.size();
        }else{
            return modelMainPortTop10Item3s.size();
        }
    }

    public class Top10ViewHolder extends RecyclerView.ViewHolder {

        TextView Item_Top10_num, Item_Top10_name, Item_Top10_rate, Item_Top10_per;
        ConstraintLayout Item_Top10_layout;
        public Top10ViewHolder(View itemView) {
            super(itemView);
            Item_Top10_per = itemView.findViewById(R.id.Item_Top10_per);
            Item_Top10_rate = itemView.findViewById(R.id.Item_Top10_rate);
            Item_Top10_name = itemView.findViewById(R.id.Item_Top10_name);
            Item_Top10_num = itemView.findViewById(R.id.Item_Top10_num);
            Item_Top10_layout = itemView.findViewById(R.id.Item_Top10_layout);
        }
    }
}

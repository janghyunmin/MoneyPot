package quantec.com.moneypot.Activity.Main.Fragment.Tab2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import quantec.com.moneypot.DataModel.dModel.ModelMyPotList;
import quantec.com.moneypot.R;

public class AdapterMyPotList extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int EMPTY = 0;
    private final int ITEM = 1;

    ArrayList<ModelMyPotList> modelMyPotLists;
    Context context;

    public AdapterMyPotList(ArrayList<ModelMyPotList> modelMyPotLists, Context context) {
        this.modelMyPotLists = modelMyPotLists;
        this.context = context;
    }

    private MyPotListClick myPotListClick;
    public interface MyPotListClick {
        public void onClick(int position);
    }

    public void setMyPotListClick(MyPotListClick myPotListClick) {
        this.myPotListClick = myPotListClick;
    }

    private MyPotListCheckBtClick myPotListCheckBtClick;
    public interface MyPotListCheckBtClick {
        public void onClick(int position);
    }

    public void setMyPotListCheckBtClick(MyPotListCheckBtClick myPotListCheckBtClick) {
        this.myPotListCheckBtClick = myPotListCheckBtClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == EMPTY){
            return new EmptyMyPotListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_emptymypotlist, parent, false));
        }else{
            return new MyPotListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mypotlist, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof EmptyMyPotListViewHolder){

        }

        else if(holder instanceof MyPotListViewHolder){

            ((MyPotListViewHolder)holder).date.setText("만든 날 "+modelMyPotLists.get(position).getDate());
            ((MyPotListViewHolder)holder).title.setText(modelMyPotLists.get(position).getTitle());
            ((MyPotListViewHolder)holder).rate.setText(String.valueOf(modelMyPotLists.get(position).getRate()));

            if(modelMyPotLists.get(position).getRate() >= 0){
                ((MyPotListViewHolder)holder).rate.setTextColor(context.getResources().getColor(R.color.red_text_color));
                ((MyPotListViewHolder)holder).per.setTextColor(context.getResources().getColor(R.color.red_text_color));
            }else{
                ((MyPotListViewHolder)holder).rate.setTextColor(context.getResources().getColor(R.color.blue_color));
                ((MyPotListViewHolder)holder).per.setTextColor(context.getResources().getColor(R.color.blue_color));
            }


            if(modelMyPotLists.get(position).isCheckBt()){
                ((MyPotListViewHolder)holder).checkBt.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_checkbox_on_red));
            }else{
                ((MyPotListViewHolder)holder).checkBt.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_checkbox_off_whitegray));
            }

            ((MyPotListViewHolder)holder).itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(myPotListClick != null){
                        myPotListClick.onClick(position);
                    }
                }
            });

            ((MyPotListViewHolder)holder).checkBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(myPotListCheckBtClick != null){
                        myPotListCheckBtClick.onClick(position);
                    }
                }
            });

        }
    }

    @Override
    public int getItemCount() {

        return modelMyPotLists.size();
    }

    @Override
    public int getItemViewType(int position) {

        if(modelMyPotLists.get(position).isEmpty()){
            return EMPTY;
        }else{
            return ITEM;
        }
    }

    public class EmptyMyPotListViewHolder extends RecyclerView.ViewHolder {
        public EmptyMyPotListViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


    public class MyPotListViewHolder extends RecyclerView.ViewHolder {

        ImageView checkBt;
        ConstraintLayout itemLayout;
        TextView date, title, rate, per;

        public MyPotListViewHolder(@NonNull View itemView) {
            super(itemView);

            checkBt = itemView.findViewById(R.id.checkBt);
            itemLayout = itemView.findViewById(R.id.itemLayout);

            date = itemView.findViewById(R.id.date);
            title = itemView.findViewById(R.id.title);
            rate = itemView.findViewById(R.id.rate);
            per = itemView.findViewById(R.id.per);
        }
    }

}

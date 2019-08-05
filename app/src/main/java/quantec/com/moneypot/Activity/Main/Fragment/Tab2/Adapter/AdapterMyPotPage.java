package quantec.com.moneypot.Activity.Main.Fragment.Tab2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import quantec.com.moneypot.R;

public class AdapterMyPotPage extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int EMPTY = 0;
    private final int ITEM = 1;

    ArrayList<ModelMyPotPage> modelMyPotPages;
    Context context;

    public AdapterMyPotPage(ArrayList<ModelMyPotPage> modelMyPotPages, Context context) {
        this.modelMyPotPages = modelMyPotPages;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == EMPTY){
            return new MyPotPageEmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mypot_mypotpageempty, parent, false));
        }else{
            return new MyPotPageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mypot_mypotpage, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof MyPotPageViewHolder){
            ((MyPotPageViewHolder)holder).title.setText(modelMyPotPages.get(position).getTitle());

            ((MyPotPageViewHolder)holder).rate.setText(String.valueOf(modelMyPotPages.get(position).getRate()));
            ((MyPotPageViewHolder)holder).date.setText(modelMyPotPages.get(position).getDate());

            if(modelMyPotPages.get(position).getRate() < 0){
                ((MyPotPageViewHolder)holder).rate.setTextColor(context.getResources().getColor(R.color.blue_color));
                ((MyPotPageViewHolder)holder).per.setTextColor(context.getResources().getColor(R.color.blue_color));
            }else{
                ((MyPotPageViewHolder)holder).rate.setTextColor(context.getResources().getColor(R.color.red_text_color));
                ((MyPotPageViewHolder)holder).per.setTextColor(context.getResources().getColor(R.color.red_text_color));
            }
        }
        else if(holder instanceof MyPotPageEmptyViewHolder){

        }
    }

    @Override
    public int getItemCount() {
        return modelMyPotPages.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(modelMyPotPages.get(position).isEmpty()){
            return EMPTY;
        }else{
            return ITEM;
        }
    }

    public class MyPotPageViewHolder extends RecyclerView.ViewHolder {

        TextView title, date, rate, per;
        ImageView image;

        public MyPotPageViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            date = itemView.findViewById(R.id.date);
            rate = itemView.findViewById(R.id.rate);
            per = itemView.findViewById(R.id.per);
            image = itemView.findViewById(R.id.image);
        }
    }

    public class MyPotPageEmptyViewHolder extends RecyclerView.ViewHolder {
        public MyPotPageEmptyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

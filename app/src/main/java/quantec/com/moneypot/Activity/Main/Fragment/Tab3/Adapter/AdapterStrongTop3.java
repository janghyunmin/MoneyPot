package quantec.com.moneypot.Activity.Main.Fragment.Tab3.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import quantec.com.moneypot.DataModel.dModel.ModelStrongTop3;
import quantec.com.moneypot.R;

public class AdapterStrongTop3 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<ModelStrongTop3> modelStrongTop3s;
    Context context;

    public AdapterStrongTop3(ArrayList<ModelStrongTop3> modelStrongTop3s, Context context) {
        this.modelStrongTop3s = modelStrongTop3s;
        this.context = context;
    }

    private StrongTop3ItemClick strongTop3ItemClick;
    public interface StrongTop3ItemClick {

        public void onClick(int position);
    }

    public void setStrongTop3ItemClick(StrongTop3ItemClick strongTop3ItemClick) {
        this.strongTop3ItemClick = strongTop3ItemClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StrongTop3ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_potmarket_strongtop3, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof StrongTop3ViewHolder){

            ((StrongTop3ViewHolder)holder).number.setText(String.valueOf(modelStrongTop3s.get(position).getNumber()));

            if(modelStrongTop3s.get(position).getNumber() == 1){
                ((StrongTop3ViewHolder)holder).number.setTextColor(context.getResources().getColor(R.color.red_text_color));
            }else{
                ((StrongTop3ViewHolder)holder).number.setTextColor(context.getResources().getColor(R.color.text_black_color));
            }

            ((StrongTop3ViewHolder)holder).title.setText(modelStrongTop3s.get(position).getTitle());

            ((StrongTop3ViewHolder)holder).rate.setText(String.valueOf(modelStrongTop3s.get(position).getRate()));
            if(modelStrongTop3s.get(position).getRate() < 0){
                ((StrongTop3ViewHolder)holder).rate.setTextColor(context.getResources().getColor(R.color.blue_color));
                ((StrongTop3ViewHolder)holder).per.setTextColor(context.getResources().getColor(R.color.blue_color));
            }else{
                ((StrongTop3ViewHolder)holder).rate.setTextColor(context.getResources().getColor(R.color.red_text_color));
                ((StrongTop3ViewHolder)holder).per.setTextColor(context.getResources().getColor(R.color.red_text_color));
            }

            ((StrongTop3ViewHolder)holder).itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(strongTop3ItemClick != null){
                        strongTop3ItemClick.onClick(position);
                    }
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return modelStrongTop3s.size();
    }


    public class StrongTop3ViewHolder extends RecyclerView.ViewHolder {

        TextView number, title, rate, per;
        ConstraintLayout itemLayout;

        public StrongTop3ViewHolder(@NonNull View itemView) {
            super(itemView);

            number = itemView.findViewById(R.id.number);
            title = itemView.findViewById(R.id.title);
            rate = itemView.findViewById(R.id.rate);
            per = itemView.findViewById(R.id.per);

            itemLayout = itemView.findViewById(R.id.itemLayout);
        }
    }
}

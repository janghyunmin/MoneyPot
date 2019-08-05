package quantec.com.moneypot.Activity.Main.Fragment.Tab3.Adapter;

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

import quantec.com.moneypot.DataModel.dModel.ModelAllRankingTop3;
import quantec.com.moneypot.R;

public class AdapterAllRankingTop3 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<ModelAllRankingTop3> modelAllRankingTop3s;
    Context context;

    public AdapterAllRankingTop3(ArrayList<ModelAllRankingTop3> modelAllRankingTop3s, Context context) {
        this.modelAllRankingTop3s = modelAllRankingTop3s;
        this.context = context;
    }

    private AllRankingTop3ItemClick allRankingTop3ItemClick;
    public interface AllRankingTop3ItemClick {
        public void onClick(int position);
    }

    public void setAllRankingTop3ItemClick(AllRankingTop3ItemClick allRankingTop3ItemClick) {
        this.allRankingTop3ItemClick = allRankingTop3ItemClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AllRankingTop3ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_potmarket_allrankingtop3, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof AllRankingTop3ViewHolder){

            ((AllRankingTop3ViewHolder)holder).image.setImageDrawable(modelAllRankingTop3s.get(position).getImage());
            ((AllRankingTop3ViewHolder)holder).title.setText(modelAllRankingTop3s.get(position).getTitle());
            ((AllRankingTop3ViewHolder)holder).rate.setText(String.valueOf(modelAllRankingTop3s.get(position).getRate()));
            if(modelAllRankingTop3s.get(position).getRate() < 0){
                ((AllRankingTop3ViewHolder)holder).rate.setTextColor(context.getResources().getColor(R.color.blue_color));
                ((AllRankingTop3ViewHolder)holder).per.setTextColor(context.getResources().getColor(R.color.blue_color));
            }else{
                ((AllRankingTop3ViewHolder)holder).rate.setTextColor(context.getResources().getColor(R.color.red_text_color));
                ((AllRankingTop3ViewHolder)holder).per.setTextColor(context.getResources().getColor(R.color.red_text_color));
            }

            ((AllRankingTop3ViewHolder)holder).itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(allRankingTop3ItemClick != null){
                        allRankingTop3ItemClick.onClick(position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return modelAllRankingTop3s.size();
    }




    public class AllRankingTop3ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView title, rate, per;
        ConstraintLayout itemLayout;

        public AllRankingTop3ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            rate = itemView.findViewById(R.id.rate);
            per = itemView.findViewById(R.id.per);

            itemLayout = itemView.findViewById(R.id.itemLayout);

        }
    }
}

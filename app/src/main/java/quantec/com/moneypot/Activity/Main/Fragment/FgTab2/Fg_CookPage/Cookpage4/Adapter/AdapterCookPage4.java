package quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.Cookpage4.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.Cookpage4.Model.dModel.ModelRankList;
import quantec.com.moneypot.R;

public class AdapterCookPage4 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<ModelRankList> modelRankLists;
    Context context;

    public AdapterCookPage4(ArrayList<ModelRankList> modelRankLists, Context context) {
        this.modelRankLists = modelRankLists;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CookPage4ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fgtab2_fgcookpage4data, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof CookPage4ViewHolder) {


            ((CookPage4ViewHolder)holder).item_cookpage3_recyclerView_port_name.setText(modelRankLists.get(position).getName());
            ((CookPage4ViewHolder)holder).item_cookpage3_recyclerView_port_rate.setText(String.valueOf(modelRankLists.get(position).getRate()));
            ((CookPage4ViewHolder)holder).rank.setText(String.valueOf(modelRankLists.get(position).getRank()+"위"));

//            Glide.with(context)
//                    .load(modelRankLists.get(position).getImageUrl())
//                    .placeholder(R.drawable.noname_img)
//                    .error(R.drawable.noname_img)
//                    .crossFade()
//                    .bitmapTransform(new CropCircleTransformation(context))
//                    .into(((CookPage4ViewHolder)holder).item_cookpage3_recyclerView_port_image);

            if(position == 0){
                ((CookPage4ViewHolder)holder).item_cookpage3_recyclerView_port_image.setImageResource(R.drawable.ic_rank_crown);
            }else {
                ((CookPage4ViewHolder)holder).item_cookpage3_recyclerView_port_image.setImageResource(0);
            }


            if(modelRankLists.get(position).getRate() < 0) {
                ((CookPage4ViewHolder)holder).item_cookpage3_recyclerView_port_rate.setTextColor(context.getResources().getColor(R.color.make_port_blue_color));
                ((CookPage4ViewHolder)holder).item_cookpage3_recyclerView_port_persent.setTextColor(context.getResources().getColor(R.color.make_port_blue_color));
            }else{
                ((CookPage4ViewHolder)holder).item_cookpage3_recyclerView_port_rate.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
                ((CookPage4ViewHolder)holder).item_cookpage3_recyclerView_port_persent.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
            }

        }
    }

    @Override
    public int getItemCount() {
        return modelRankLists.size();
    }

    public class CookPage4ViewHolder extends RecyclerView.ViewHolder {
        TextView rank, item_cookpage3_recyclerView_port_name, item_cookpage3_recyclerView_port_rate, item_cookpage3_recyclerView_port_persent;
        ImageView item_cookpage3_recyclerView_port_image;

        public CookPage4ViewHolder(View itemView) {
            super(itemView);

            rank = itemView.findViewById(R.id.rank);
            item_cookpage3_recyclerView_port_name = itemView.findViewById(R.id.item_cookpage3_recyclerView_port_name);
            item_cookpage3_recyclerView_port_rate = itemView.findViewById(R.id.item_cookpage3_recyclerView_port_rate);
            item_cookpage3_recyclerView_port_image = itemView.findViewById(R.id.item_cookpage3_recyclerView_port_image);
            item_cookpage3_recyclerView_port_persent = itemView.findViewById(R.id.item_cookpage3_recyclerView_port_persent);

        }
    }

}

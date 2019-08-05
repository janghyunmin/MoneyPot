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

import quantec.com.moneypot.DataModel.dModel.ModelThemeTop3;
import quantec.com.moneypot.R;

public class AdapterThemeTop3 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<ModelThemeTop3> modelThemeTop3s;
    Context context;

    public AdapterThemeTop3(ArrayList<ModelThemeTop3> modelThemeTop3s, Context context) {
        this.modelThemeTop3s = modelThemeTop3s;
        this.context = context;
    }

    private ThemeTop3itemClick themeTop3itemClick;
    public interface ThemeTop3itemClick {
        public void onClick(int position);
    }

    public void setThemeTop3itemClick(ThemeTop3itemClick themeTop3itemClick) {
        this.themeTop3itemClick = themeTop3itemClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ThemeTop3ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_potmarket_themetop3, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        if(holder instanceof ThemeTop3ViewHolder){

            ((ThemeTop3ViewHolder)holder).number.setText(String.valueOf(modelThemeTop3s.get(position).getNumber()));
            if(modelThemeTop3s.get(position).getNumber() == 1){
                ((ThemeTop3ViewHolder)holder).number.setTextColor(context.getResources().getColor(R.color.red_text_color));
            }else{
                ((ThemeTop3ViewHolder)holder).number.setTextColor(context.getResources().getColor(R.color.text_black_color));
            }

            ((ThemeTop3ViewHolder)holder).title.setText(modelThemeTop3s.get(position).getTitle());
            ((ThemeTop3ViewHolder)holder).rate.setText(String.valueOf(modelThemeTop3s.get(position).getRate()));
            if(modelThemeTop3s.get(position).getRate() < 0){
                ((ThemeTop3ViewHolder)holder).rate.setTextColor(context.getResources().getColor(R.color.blue_color));
                ((ThemeTop3ViewHolder)holder).per.setTextColor(context.getResources().getColor(R.color.blue_color));
            }else{
                ((ThemeTop3ViewHolder)holder).rate.setTextColor(context.getResources().getColor(R.color.red_text_color));
                ((ThemeTop3ViewHolder)holder).per.setTextColor(context.getResources().getColor(R.color.red_text_color));
            }

            ((ThemeTop3ViewHolder)holder).itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(themeTop3itemClick != null){
                        themeTop3itemClick.onClick(position);
                    }
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return modelThemeTop3s.size();
    }

    public class ThemeTop3ViewHolder extends RecyclerView.ViewHolder {

        TextView number, title, rate, per;
        ConstraintLayout itemLayout;

        public ThemeTop3ViewHolder(@NonNull View itemView) {
            super(itemView);

            number = itemView.findViewById(R.id.number);
            title = itemView.findViewById(R.id.title);
            rate = itemView.findViewById(R.id.rate);
            per = itemView.findViewById(R.id.per);

            itemLayout = itemView.findViewById(R.id.itemLayout);

        }
    }
}

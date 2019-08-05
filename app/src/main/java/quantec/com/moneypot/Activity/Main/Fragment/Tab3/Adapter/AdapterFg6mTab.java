package quantec.com.moneypot.Activity.Main.Fragment.Tab3.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.asksira.loopingviewpager.LoopingPagerAdapter;
import com.asksira.loopingviewpager.LoopingViewPager;
import com.rd.PageIndicatorView;
import com.rd.animation.type.AnimationType;

import java.util.ArrayList;

import quantec.com.moneypot.DataModel.dModel.ModelFg6mTab3Banner;
import quantec.com.moneypot.DataModel.dModel.ModelPotMarketData;
import quantec.com.moneypot.R;

public class AdapterFg6mTab extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int IMAGE = 0;
    private final int ITEM = 1;

    ArrayList<ModelPotMarketData> modelPotMarketData;
    Context context;
    ArrayList<ModelFg6mTab3Banner> modelFg6mTab3Banners;

    public AdapterFg6mTab(ArrayList<ModelPotMarketData> modelPotMarketData, Context context, ArrayList<ModelFg6mTab3Banner> modelFg6mTab3Banners) {
        this.modelPotMarketData = modelPotMarketData;
        this.context = context;
        this.modelFg6mTab3Banners = modelFg6mTab3Banners;
    }

    private Fg6mTab3ItemClick fg6mTab3ItemClick;
    public interface Fg6mTab3ItemClick {
        public void onClick(int position);
    }

    public void setFg6mTab3ItemClick(Fg6mTab3ItemClick fg6mTab3ItemClick) {
        this.fg6mTab3ItemClick = fg6mTab3ItemClick;
    }

    private Fg6mTab3ZimBtClick fg6mTab3ZimBtClick;
    public interface Fg6mTab3ZimBtClick {
        public void onClick(int position);
    }

    public void setFg6mTab3ZimBtClick(Fg6mTab3ZimBtClick fg6mTab3ZimBtClick) {
        this.fg6mTab3ZimBtClick = fg6mTab3ZimBtClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == IMAGE){
            return new Fg6mTabImageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_potmarket_fg3mtabimage, parent, false));
        }else{
            return new Fg6mTabItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_potmarket_fg3mitem, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof Fg6mTabImageViewHolder){

        }

        if(holder instanceof Fg6mTabItemViewHolder){

            ((Fg6mTabItemViewHolder)holder).number.setText(String.valueOf(modelPotMarketData.get(position).getNumber()));
            if(modelPotMarketData.get(position).getNumber() == 1){
                ((Fg6mTabItemViewHolder)holder).number.setTextColor(context.getResources().getColor(R.color.red_text_color));
            }else{
                ((Fg6mTabItemViewHolder)holder).number.setTextColor(context.getResources().getColor(R.color.text_black_color));
            }


            ((Fg6mTabItemViewHolder)holder).title.setText(modelPotMarketData.get(position).getTitle());

            ((Fg6mTabItemViewHolder)holder).rate.setText(String.valueOf(modelPotMarketData.get(position).getRate()));
            if(modelPotMarketData.get(position).getRate() < 0){
                ((Fg6mTabItemViewHolder)holder).rate.setTextColor(context.getResources().getColor(R.color.blue_color));
                ((Fg6mTabItemViewHolder)holder).per.setTextColor(context.getResources().getColor(R.color.blue_color));
            }
            else{
                ((Fg6mTabItemViewHolder)holder).rate.setTextColor(context.getResources().getColor(R.color.red_text_color));
                ((Fg6mTabItemViewHolder)holder).per.setTextColor(context.getResources().getColor(R.color.red_text_color));
            }


            if(modelPotMarketData.get(position).isCheck()){
                ((Fg6mTabItemViewHolder)holder).zimBt.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_on_red_48_px));
            }
            else{
                ((Fg6mTabItemViewHolder)holder).zimBt.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_off_whitegray_48_px));
            }

            ((Fg6mTabItemViewHolder)holder).zimBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(fg6mTab3ZimBtClick != null){
                        fg6mTab3ZimBtClick.onClick(position);
                    }
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return modelPotMarketData.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return IMAGE;
        }else{
            return ITEM;
        }
    }


    public class Fg6mTabImageViewHolder extends RecyclerView.ViewHolder {

        DemoInfiniteAdapter adapter;
        LoopingViewPager viewPager;
        PageIndicatorView indicatorView;

        public Fg6mTabImageViewHolder(@NonNull View itemView) {
            super(itemView);

            indicatorView = itemView.findViewById(R.id.indicator);
            indicatorView.setAlpha(0.7f);
            indicatorView.setSelectedColor(context.getResources().getColor(R.color.red_text_color));
            indicatorView.setUnselectedColor(context.getResources().getColor(R.color.text_gray_color));
            indicatorView.setAnimationType(AnimationType.THIN_WORM);
            viewPager = itemView.findViewById(R.id.viewPager);
            adapter = new DemoInfiniteAdapter(context, modelFg6mTab3Banners, true);

            viewPager.setAdapter(adapter);

            //Custom bind indicator
            indicatorView.setCount(viewPager.getIndicatorCount());
            viewPager.setIndicatorPageChangeListener(new LoopingViewPager.IndicatorPageChangeListener() {
                @Override
                public void onIndicatorProgress(int selectingPosition, float progress) {
                    indicatorView.setProgress(selectingPosition, progress);
                }
                @Override
                public void onIndicatorPageChange(int newIndicatorPosition) {
                }
            });

        }
    }

    public class Fg6mTabItemViewHolder extends RecyclerView.ViewHolder {

        TextView number, title, rate, per;
        ImageView image, zimBt;

        public Fg6mTabItemViewHolder(@NonNull View itemView) {
            super(itemView);

            number = itemView.findViewById(R.id.number);
            title = itemView.findViewById(R.id.title);
            rate = itemView.findViewById(R.id.rate);
            per = itemView.findViewById(R.id.per);

            image = itemView.findViewById(R.id.image);
            zimBt = itemView.findViewById(R.id.zimBt);

        }
    }



    public class DemoInfiniteAdapter extends LoopingPagerAdapter<ModelFg6mTab3Banner> {

        private static final int VIEW_TYPE_NORMAL = 100;

        public DemoInfiniteAdapter(Context context, ArrayList<ModelFg6mTab3Banner> itemList, boolean isInfinite) {
            super(context, itemList, isInfinite);
        }

        @Override
        protected int getItemViewType(int listPosition) {
            return VIEW_TYPE_NORMAL;
        }

        @Override
        protected View inflateView(int viewType, ViewGroup container, int listPosition) {
            return LayoutInflater.from(context).inflate(R.layout.item_potmarket_pager, container, false);
        }

        @Override
        protected void bindView(View convertView, int listPosition, int viewType) {

            convertView.findViewById(R.id.image).setBackgroundResource(getBackgroundResouce(listPosition));


            convertView.findViewById(R.id.image).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "배너 클릭 : "+modelFg6mTab3Banners.get(listPosition).getTitle(), Toast.LENGTH_SHORT).show();

//                    Intent intent = new Intent(context, ActivityMyInfo.class);
//                    context.startActivity(intent);

                }
            });
        }


        private int getBackgroundResouce (int number) {
            switch (number) {
                case 0:
                    return R.drawable.img_market_banner_1;
                case 1:
                    return R.drawable.img_market_banner_1;
                case 2:
                    return R.drawable.img_market_banner_1;
                case 3:
                    return R.drawable.img_market_banner_1;
                case 4:
                    return R.drawable.img_market_banner_1;
                case 5:
                    return R.drawable.img_market_banner_1;
                default:
                    return R.drawable.img_market_banner_1;
            }
        }
    }
}

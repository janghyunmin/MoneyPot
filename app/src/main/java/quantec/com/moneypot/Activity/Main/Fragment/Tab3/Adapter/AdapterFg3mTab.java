package quantec.com.moneypot.Activity.Main.Fragment.Tab3.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.asksira.loopingviewpager.LoopingPagerAdapter;
import com.asksira.loopingviewpager.LoopingViewPager;
import com.rd.PageIndicatorView;
import com.rd.animation.type.AnimationType;

import java.util.ArrayList;

import quantec.com.moneypot.DataModel.dModel.ModelFg3mTab3Banner;
import quantec.com.moneypot.DataModel.dModel.ModelPotMarketData;
import quantec.com.moneypot.R;

public class AdapterFg3mTab extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private boolean zimState = false;

    private int lastPosition = -1;

    private final int IMAGE = 0;
    private final int ITEM = 1;

    ArrayList<ModelPotMarketData> modelPotMarketData;
    Context context;
    ArrayList<ModelFg3mTab3Banner> modelFg3mTab3Banners;

    public AdapterFg3mTab(ArrayList<ModelPotMarketData> modelPotMarketData, Context context, ArrayList<ModelFg3mTab3Banner> modelFg3mTab3Banners) {
        this.modelPotMarketData = modelPotMarketData;
        this.context = context;
        this.modelFg3mTab3Banners = modelFg3mTab3Banners;
    }

    private Fg3mTabItemClick fg3mTabItemClick;
    public interface Fg3mTabItemClick {

        public void onClick(int position);
    }

    public void setFg3mTabItemClick(Fg3mTabItemClick fg3mTabItemClick) {
        this.fg3mTabItemClick = fg3mTabItemClick;
    }

    private Fg3mTabZimBtClick fg3mTabZimBtClick;
    public interface Fg3mTabZimBtClick {
        public void onClick(int position);
    }

    public void setFg3mTabZimBtClick(Fg3mTabZimBtClick fg3mTabZimBtClick) {
        this.fg3mTabZimBtClick = fg3mTabZimBtClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == IMAGE){
            return new Fg3mTabImageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_potmarket_fg3mtabimage, parent, false));
        }else{
            return new Fg3mTabItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_potmarket_fg3mitem, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof Fg3mTabImageViewHolder){

        }

        if(holder instanceof Fg3mTabItemViewHolder){

            ((Fg3mTabItemViewHolder)holder).number.setText(String.valueOf(modelPotMarketData.get(position).getNumber()));
            if(modelPotMarketData.get(position).getNumber() == 1){
                ((Fg3mTabItemViewHolder)holder).number.setTextColor(context.getResources().getColor(R.color.red_text_color));
            }else{
                ((Fg3mTabItemViewHolder)holder).number.setTextColor(context.getResources().getColor(R.color.text_black_color));
            }


            ((Fg3mTabItemViewHolder)holder).title.setText(modelPotMarketData.get(position).getTitle());

            ((Fg3mTabItemViewHolder)holder).rate.setText(String.valueOf(modelPotMarketData.get(position).getRate()));
            if(modelPotMarketData.get(position).getRate() < 0){
                ((Fg3mTabItemViewHolder)holder).rate.setTextColor(context.getResources().getColor(R.color.blue_color));
                ((Fg3mTabItemViewHolder)holder).per.setTextColor(context.getResources().getColor(R.color.blue_color));
            }
            else{
                ((Fg3mTabItemViewHolder)holder).rate.setTextColor(context.getResources().getColor(R.color.red_text_color));
                ((Fg3mTabItemViewHolder)holder).per.setTextColor(context.getResources().getColor(R.color.red_text_color));
            }


            if(modelPotMarketData.get(position).isCheck()){
                ((Fg3mTabItemViewHolder)holder).zimBt.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_on_red_48_px));
            }
            else{
                ((Fg3mTabItemViewHolder)holder).zimBt.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_off_whitegray_48_px));
            }

            ((Fg3mTabItemViewHolder)holder).zimBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    zimState = true;
                    if(fg3mTabZimBtClick != null){
                        fg3mTabZimBtClick.onClick(position);
                        Log.e("1번","1번");
                    }
                }
            });

            ((Fg3mTabItemViewHolder)holder).itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(fg3mTabItemClick != null){
                        fg3mTabItemClick.onClick(position);
                    }
                }
            });
        }

        if(!zimState){
            //최상단의 배너는 애니메이션을 주기 않기 위해서 뺴주는 코드
            if(position == 0){
                lastPosition = position;
            }else{
                Animation animation = AnimationUtils.loadAnimation(context,
                        (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_bottom);
                holder.itemView.startAnimation(animation);
                lastPosition = position;
            }
        }
        else{
            zimState = false;
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

    @Override
    public void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }

    public class Fg3mTabImageViewHolder extends RecyclerView.ViewHolder {

        DemoInfiniteAdapter adapter;
        LoopingViewPager viewPager;
        PageIndicatorView indicatorView;

        public Fg3mTabImageViewHolder(@NonNull View itemView) {
            super(itemView);

            indicatorView = itemView.findViewById(R.id.indicator);
            indicatorView.setAlpha(0.7f);
            indicatorView.setSelectedColor(context.getResources().getColor(R.color.red_text_color));
            indicatorView.setUnselectedColor(context.getResources().getColor(R.color.text_gray_color));
            indicatorView.setAnimationType(AnimationType.THIN_WORM);
            viewPager = itemView.findViewById(R.id.viewPager);
            adapter = new DemoInfiniteAdapter(context, modelFg3mTab3Banners, true);

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

    public class Fg3mTabItemViewHolder extends RecyclerView.ViewHolder {

        TextView number, title, rate, per;
        ImageView image, zimBt;
        ConstraintLayout itemLayout;

        public Fg3mTabItemViewHolder(@NonNull View itemView) {
            super(itemView);

            number = itemView.findViewById(R.id.number);
            title = itemView.findViewById(R.id.title);
            rate = itemView.findViewById(R.id.rate);
            per = itemView.findViewById(R.id.per);

            image = itemView.findViewById(R.id.image);
            zimBt = itemView.findViewById(R.id.zimBt);

            itemLayout = itemView.findViewById(R.id.itemLayout);

        }
    }


    public class DemoInfiniteAdapter extends LoopingPagerAdapter<ModelFg3mTab3Banner> {

        private static final int VIEW_TYPE_NORMAL = 100;

        public DemoInfiniteAdapter(Context context, ArrayList<ModelFg3mTab3Banner> itemList, boolean isInfinite) {
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
                    Toast.makeText(context, "배너 클릭 : "+modelFg3mTab3Banners.get(listPosition).getTitle(), Toast.LENGTH_SHORT).show();

//                    Intent intent = new Intent(context, ActivityMyInfo.class);
//                    context.startActivity(intent);
                    Intent intent1 = new Intent(Intent.ACTION_SEND);
                    Intent ch = Intent.createChooser(intent1, "머니팟 공유 하기");
                    context.startActivity(ch);

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

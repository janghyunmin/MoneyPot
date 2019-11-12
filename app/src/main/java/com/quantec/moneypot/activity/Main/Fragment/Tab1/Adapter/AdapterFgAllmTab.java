package com.quantec.moneypot.activity.Main.Fragment.Tab1.Adapter;

import android.content.Context;
import android.content.Intent;
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

import com.quantec.moneypot.datamodel.dmodel.ModelFgAllmTab3Banner;
import com.quantec.moneypot.datamodel.dmodel.ModelPotMarketData;
import com.quantec.moneypot.R;

public class AdapterFgAllmTab extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private boolean zimState = false;
    private int lastPosition = -1;

    private final int IMAGE = 0;
    private final int ITEM = 1;

    ArrayList<ModelPotMarketData> modelPotMarketData;
    Context context;
    ArrayList<ModelFgAllmTab3Banner> modelFgAllmTab3Banners;

    public AdapterFgAllmTab(ArrayList<ModelPotMarketData> modelPotMarketData, Context context, ArrayList<ModelFgAllmTab3Banner> modelFgAllmTab3Banners) {
        this.modelPotMarketData = modelPotMarketData;
        this.context = context;
        this.modelFgAllmTab3Banners = modelFgAllmTab3Banners;
    }

    private FgAllmTabItemClick fgAllmTabItemClick;
    public interface FgAllmTabItemClick{
        public void onClick(int position);
    }

    public void setFgAllmTabItemClick(FgAllmTabItemClick fgAllmTabItemClick) {
        this.fgAllmTabItemClick = fgAllmTabItemClick;
    }

    private FgAllmTabZimClick fgAllmTabZimClick;
    public interface FgAllmTabZimClick {
        public void onClick(int position);
    }

    public void setFgAllmTabZimClick(FgAllmTabZimClick fgAllmTabZimClick) {
        this.fgAllmTabZimClick = fgAllmTabZimClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == IMAGE){
            return new FgAllmTabImageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_potmarket_fg3mtabimage, parent, false));
        }else{
            return new FgAllmTabItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_potmarket_fg3mitem, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        if(holder instanceof FgAllmTabImageViewHolder){

        }

        if(holder instanceof FgAllmTabItemViewHolder){

            ((FgAllmTabItemViewHolder)holder).number.setText(String.valueOf(modelPotMarketData.get(position).getNumber()));
            if(modelPotMarketData.get(position).getNumber() == 1){
                ((FgAllmTabItemViewHolder)holder).number.setTextColor(context.getResources().getColor(R.color.red_text_color));
            }else{
                ((FgAllmTabItemViewHolder)holder).number.setTextColor(context.getResources().getColor(R.color.text_black_color));
            }


            ((FgAllmTabItemViewHolder)holder).title.setText(modelPotMarketData.get(position).getTitle());

            ((FgAllmTabItemViewHolder)holder).rate.setText(String.valueOf(modelPotMarketData.get(position).getRate()));
            if(modelPotMarketData.get(position).getRate() < 0){
                ((FgAllmTabItemViewHolder)holder).rate.setTextColor(context.getResources().getColor(R.color.blue_color));
                ((FgAllmTabItemViewHolder)holder).per.setTextColor(context.getResources().getColor(R.color.blue_color));
            }
            else{
                ((FgAllmTabItemViewHolder)holder).rate.setTextColor(context.getResources().getColor(R.color.red_text_color));
                ((FgAllmTabItemViewHolder)holder).per.setTextColor(context.getResources().getColor(R.color.red_text_color));
            }


            if(modelPotMarketData.get(position).isCheck()){
                ((FgAllmTabItemViewHolder)holder).zimBt.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_on_red_48_px));
            }
            else{
                ((FgAllmTabItemViewHolder)holder).zimBt.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_off_whitegray_48_px));
            }

            ((FgAllmTabItemViewHolder)holder).zimBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(fgAllmTabZimClick != null){
                        fgAllmTabZimClick.onClick(position);
                    }
                }
            });

            ((FgAllmTabItemViewHolder)holder).itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(fgAllmTabItemClick != null){
                        fgAllmTabItemClick.onClick(position);
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

    public class FgAllmTabImageViewHolder extends RecyclerView.ViewHolder {

        DemoInfiniteAdapter adapter;
        LoopingViewPager viewPager;
        PageIndicatorView indicatorView;

        public FgAllmTabImageViewHolder(@NonNull View itemView) {
            super(itemView);

            indicatorView = itemView.findViewById(R.id.indicator);
            indicatorView.setAlpha(0.7f);
            indicatorView.setSelectedColor(context.getResources().getColor(R.color.red_text_color));
            indicatorView.setUnselectedColor(context.getResources().getColor(R.color.text_gray_color));
            indicatorView.setAnimationType(AnimationType.THIN_WORM);
            viewPager = itemView.findViewById(R.id.viewPager);
            adapter = new DemoInfiniteAdapter(context, modelFgAllmTab3Banners, true);

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

    public class FgAllmTabItemViewHolder extends RecyclerView.ViewHolder {

        TextView number, title, rate, per;
        ImageView image, zimBt;
        ConstraintLayout itemLayout;

        public FgAllmTabItemViewHolder(@NonNull View itemView) {
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



    public class DemoInfiniteAdapter extends LoopingPagerAdapter<ModelFgAllmTab3Banner> {

        private static final int VIEW_TYPE_NORMAL = 100;

        public DemoInfiniteAdapter(Context context, ArrayList<ModelFgAllmTab3Banner> itemList, boolean isInfinite) {
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
                    Toast.makeText(context, "배너 클릭 : "+modelFgAllmTab3Banners.get(listPosition).getTitle(), Toast.LENGTH_SHORT).show();

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

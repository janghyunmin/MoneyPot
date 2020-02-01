package com.quantec.moneypot.activity.prefer.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.quantec.moneypot.R;
import com.quantec.moneypot.activity.prefer.ModelThumbImage;

import java.util.ArrayList;

public class AdapterThumbImage extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<ModelThumbImage> modelThumbImages;
    Context context;

    public AdapterThumbImage(ArrayList<ModelThumbImage> modelThumbImages, Context context) {
        this.modelThumbImages = modelThumbImages;
        this.context = context;
    }

    private ThumbImageDeleteBt thumbImageDeleteBt;
    public interface ThumbImageDeleteBt {
        public void onClick(int position);
    }

    public void setThumbImageDeleteBt(ThumbImageDeleteBt thumbImageDeleteBt) {
        this.thumbImageDeleteBt = thumbImageDeleteBt;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ThumbImageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thumbimage, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof ThumbImageViewHolder){

            int resource = context.getResources().getIdentifier("ci_it"+"_small", "drawable", context.getPackageName());
            ((ThumbImageViewHolder)holder).image.setImageDrawable(context.getResources().getDrawable(resource));

            ((ThumbImageViewHolder)holder).title.setText(modelThumbImages.get(position).getTitle());
            ((ThumbImageViewHolder)holder).category.setText(modelThumbImages.get(position).getBunya());

            ((ThumbImageViewHolder)holder).deleteBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(thumbImageDeleteBt != null){
                        thumbImageDeleteBt.onClick(position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return modelThumbImages.size();
    }

    public class ThumbImageViewHolder extends RecyclerView.ViewHolder {

        TextView category, title, deleteBt;
        ImageView image;

        public ThumbImageViewHolder(@NonNull View itemView) {
            super(itemView);

            category = itemView.findViewById(R.id.category);
            title = itemView.findViewById(R.id.title);
            deleteBt = itemView.findViewById(R.id.deleteBt);

            image = itemView.findViewById(R.id.image);

        }
    }

}

//public class AdapterThumbImage extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
//
//    ArrayList<ModelThumbImage> modelThumbImages;
//    Context context;
//
//    public AdapterThumbImage(ArrayList<ModelThumbImage> modelThumbImages, Context context) {
//        this.modelThumbImages = modelThumbImages;
//        this.context = context;
//    }
//
//    private ThumbImageDeleteBt thumbImageDeleteBt;
//    public interface ThumbImageDeleteBt {
//        public void onClick(int position);
//    }
//
//    public void setThumbImageDeleteBt(ThumbImageDeleteBt thumbImageDeleteBt) {
//        this.thumbImageDeleteBt = thumbImageDeleteBt;
//    }
//
//    @NonNull
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return new ThumbImageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thumbimage, parent, false));
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//
//        if(holder instanceof ThumbImageViewHolder){
//
//            int resource = context.getResources().getIdentifier("ci_"+modelThumbImages.get(position).getImage()+"_small", "drawable", context.getPackageName());
//            ((ThumbImageViewHolder)holder).image.setImageDrawable(context.getResources().getDrawable(resource));
//
//            ((ThumbImageViewHolder)holder).title.setText(modelThumbImages.get(position).getTitle());
//            ((ThumbImageViewHolder)holder).category.setText(modelThumbImages.get(position).getBunya());
//
//            ((ThumbImageViewHolder)holder).deleteBt.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    if(thumbImageDeleteBt != null){
//                        thumbImageDeleteBt.onClick(position);
//                    }
//                }
//            });
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return modelThumbImages.size();
//    }
//
//    public class ThumbImageViewHolder extends RecyclerView.ViewHolder {
//
//        TextView category, title, deleteBt;
//        ImageView image;
//
//        public ThumbImageViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            category = itemView.findViewById(R.id.category);
//            title = itemView.findViewById(R.id.title);
//            deleteBt = itemView.findViewById(R.id.deleteBt);
//
//            image = itemView.findViewById(R.id.image);
//
//        }
//    }
//
//}

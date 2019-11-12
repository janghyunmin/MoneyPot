package com.quantec.moneypot.activity.PotDetail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.quantec.moneypot.R;

public class AdapterArticle extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<ModelArticle> modelArticles;
    Context context;

    public AdapterArticle(ArrayList<ModelArticle> modelArticles, Context context) {
        this.modelArticles = modelArticles;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ArticleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof ArticleViewHolder){
            ((ArticleViewHolder)holder).title.setText(modelArticles.get(position).getTitle());
        }

    }

    @Override
    public int getItemCount() {
        return modelArticles.size();
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
        }
    }
}

package com.quantec.moneypot.activity.article.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.quantec.moneypot.R;
import com.quantec.moneypot.activity.buttondoublecheck.RxView;
import com.quantec.moneypot.datamodel.dmodel.ModelArticle;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class AdapterArticle extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<ModelArticle> modelArticles;
    Context context;

    public AdapterArticle(ArrayList<ModelArticle> modelArticles, Context context) {
        this.modelArticles = modelArticles;
        this.context = context;
    }

    private ArticleClick articleClick;
    public interface ArticleClick{
        public void onClick(int position);
    }

    public void setArticleClick(ArticleClick articleClick) {
        this.articleClick = articleClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ArticleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof ArticleViewHolder){
            ((ArticleViewHolder)holder).title.setText(modelArticles.get(position).getTitle());
            ((ArticleViewHolder)holder).newspaper.setText(modelArticles.get(position).getNewspaper());
            ((ArticleViewHolder)holder).date.setText(modelArticles.get(position).getDate());

            RxView.clicks(((ArticleViewHolder)holder).itemLayout).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(empty -> {
                if(articleClick != null){
                    articleClick.onClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return modelArticles.size();
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder {

        TextView title, newspaper, date;
        ConstraintLayout itemLayout;

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            newspaper = itemView.findViewById(R.id.newspaper);
            date = itemView.findViewById(R.id.date);

            itemLayout = itemView.findViewById(R.id.itemLayout);
        }
    }
}

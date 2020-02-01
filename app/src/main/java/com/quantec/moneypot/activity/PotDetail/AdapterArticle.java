package com.quantec.moneypot.activity.PotDetail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.quantec.moneypot.R;
import com.quantec.moneypot.activity.buttondoublecheck.RxView;

public class AdapterArticle extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int EMPTY = 0;
    private final int ITEM = 1;
    private final int BOTTOM = 2;

    ArrayList<ModelArticle> modelArticles;
    Context context;

    public AdapterArticle(ArrayList<ModelArticle> modelArticles, Context context) {
        this.modelArticles = modelArticles;
        this.context = context;
    }

    private ArticleAddBtClick articleAddBtClick;
    public interface ArticleAddBtClick{
        public void onClick(int position);
    }

    public void setArticleAddBtClick(ArticleAddBtClick articleAddBtClick) {
        this.articleAddBtClick = articleAddBtClick;
    }

    private ArticleEnterClick articleEnterClick;
    public interface ArticleEnterClick{
        public void onClick(int position);
    }

    public void setArticleEnterClick(ArticleEnterClick articleEnterClick) {
        this.articleEnterClick = articleEnterClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == EMPTY){
            return new ArticleEmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article_empty, parent, false));
        }else if(viewType == ITEM){
            return new ArticleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false));
        }else{
            return new ArticleBottomViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article_bottom, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof ArticleViewHolder){
            ((ArticleViewHolder)holder).title.setText(modelArticles.get(position).getTitle());
            ((ArticleViewHolder)holder).newspaper.setText(modelArticles.get(position).getNewspaper());
            ((ArticleViewHolder)holder).date.setText(modelArticles.get(position).getDate());

            RxView.clicks(((ArticleViewHolder)holder).itemLayout).throttleFirst(700, TimeUnit.MILLISECONDS).subscribe(empty -> {
                if(articleEnterClick != null){
                    articleEnterClick.onClick(position);
                }
            });
        }

        if(holder instanceof ArticleBottomViewHolder){

            if(modelArticles.get(position).isBottomState()){
                ((ArticleBottomViewHolder)holder).addBt.setTextColor(context.getResources().getColor(R.color.c_9a9a9a));
            }else{
                ((ArticleBottomViewHolder)holder).addBt.setTextColor(context.getResources().getColor(R.color.c_dedede));
            }

            RxView.clicks(((ArticleBottomViewHolder)holder).addBt).throttleFirst(700, TimeUnit.MILLISECONDS).subscribe(empty -> {
                if(articleAddBtClick != null){
                    articleAddBtClick.onClick(position);
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


    public class ArticleEmptyViewHolder extends RecyclerView.ViewHolder {
        public ArticleEmptyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class ArticleBottomViewHolder extends RecyclerView.ViewHolder {

        TextView addBt;

        public ArticleBottomViewHolder(@NonNull View itemView) {
            super(itemView);

            addBt = itemView.findViewById(R.id.addBt);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(modelArticles.get(0).isEmpty()){
            return EMPTY;
        }else{
            if(modelArticles.get(position).isBottom()){
              return BOTTOM;
            }else{
              return ITEM;
            }
        }
    }
}

package com.quantec.moneypot.activity.Main.Fragment.Tab1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.quantec.moneypot.R;

import java.util.ArrayList;

public class AdapterScroll extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    ArrayList<String> modelString;
    Context context;

    public AdapterScroll(ArrayList<String> modelString, Context context) {
        this.modelString = modelString;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ScrollViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_scroll_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof ScrollViewHolder){
            ((ScrollViewHolder)holder).title.setText(modelString.get(position));
        }

    }

    @Override
    public int getItemCount() {
        return modelString.size();
    }

    public class ScrollViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        public ScrollViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
        }
    }
}

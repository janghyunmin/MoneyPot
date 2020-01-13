package com.quantec.moneypot.activity.center.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.quantec.moneypot.datamodel.dmodel.ModelAccountList;
import com.quantec.moneypot.R;

public class AdapterAccount extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<ModelAccountList> accountLists;
    Context context;

    public AdapterAccount(ArrayList<ModelAccountList> accountLists, Context context) {
        this.accountLists = accountLists;
        this.context = context;
    }

    private AccountClick accountClick;
    public interface AccountClick {
        public void onClick(int position);
    }

    public void setAccountClick(AccountClick accountClick) {
        this.accountClick = accountClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AccountViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fgaccount, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof AccountViewHolder){
            ((AccountViewHolder)holder).title.setText(accountLists.get(position).getTitle());

            ((AccountViewHolder)holder).layoutView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(accountClick != null){
                        accountClick.onClick(position);
                    }
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return accountLists.size();
    }

    public class AccountViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ConstraintLayout layoutView;

        public AccountViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            layoutView = itemView.findViewById(R.id.layoutView);
        }
    }
}

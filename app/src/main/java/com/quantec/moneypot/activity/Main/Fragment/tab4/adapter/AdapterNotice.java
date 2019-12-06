package com.quantec.moneypot.activity.Main.Fragment.tab4.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import com.quantec.moneypot.R;
import com.quantec.moneypot.activity.Main.Fragment.tab4.ModelNotice;

import java.util.ArrayList;

public class AdapterNotice extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<ModelNotice> modelNotices;
    Context context;

    public AdapterNotice(ArrayList<ModelNotice> modelNotices, Context context) {
        this.modelNotices = modelNotices;
        this.context = context;
    }

    private NoticeClick noticeClick;
    public interface NoticeClick {
        public void onClick(int position);
    }

    public void setNoticeClick(NoticeClick noticeClick) {
        this.noticeClick = noticeClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
     return new NoticeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fgtab4_notice, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if(holder instanceof NoticeViewHolder){
                ((NoticeViewHolder)holder).noticeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(noticeClick != null){
                            noticeClick.onClick(position);
                        }
                    }
                });

                ((NoticeViewHolder)holder).title.setText(modelNotices.get(position).getTitle());
                ((NoticeViewHolder)holder).date.setText(modelNotices.get(position).getDate());
            }
    }

    @Override
    public int getItemCount() {
        return modelNotices.size();
    }

    public class NoticeViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout noticeLayout;
        TextView title, date;

        public NoticeViewHolder(@NonNull View itemView) {
            super(itemView);

            noticeLayout = itemView.findViewById(R.id.noticeLayout);
            title = itemView.findViewById(R.id.title);
            date = itemView.findViewById(R.id.date);

        }
    }
}

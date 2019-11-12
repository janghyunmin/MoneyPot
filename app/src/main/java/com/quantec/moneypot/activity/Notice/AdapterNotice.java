package com.quantec.moneypot.activity.Notice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.quantec.moneypot.datamodel.dmodel.ModelNoticeList;
import com.quantec.moneypot.R;

public class AdapterNotice extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<ModelNoticeList> modelNoticeLists;
    Context context;

    public AdapterNotice(ArrayList<ModelNoticeList> modelNoticeLists, Context context) {
        this.modelNoticeLists = modelNoticeLists;
        this.context = context;
    }

    private NoticeItemClcick noticeItemClcick;
    public interface NoticeItemClcick {
        public void onClick(int position);
    }

    public void setNoticeItemClcick(NoticeItemClcick noticeItemClcick) {
        this.noticeItemClcick = noticeItemClcick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoticeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notice, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof NoticeViewHolder) {

            ((NoticeViewHolder)holder).title.setText(modelNoticeLists.get(position).getTitle());

            ((NoticeViewHolder)holder).layoutView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if(noticeItemClcick != null) {

                        noticeItemClcick.onClick(position);
                    }
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return modelNoticeLists.size();
    }

    private class NoticeViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ConstraintLayout layoutView;

        public NoticeViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            layoutView = itemView.findViewById(R.id.layoutView);
        }
    }
}
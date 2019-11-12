package com.quantec.moneypot.activity.PotDetail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.quantec.moneypot.R;

public class AdapterSingleDetail extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<ModelSingleDetail> modelSingleDetails;
    Context context;

    public AdapterSingleDetail(ArrayList<ModelSingleDetail> modelSingleDetails, Context context) {
        this.modelSingleDetails = modelSingleDetails;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ItemSingleDetailAdapter(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_test, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return modelSingleDetails.size();
    }


    public class ItemSingleDetailAdapter extends RecyclerView.ViewHolder {
        public ItemSingleDetailAdapter(@NonNull View itemView) {
            super(itemView);
        }
    }

}

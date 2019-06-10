package quantec.com.moneypot.Activity.Center.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import quantec.com.moneypot.ModelCommon.dModel.ModelProductCraftList;
import quantec.com.moneypot.R;

public class AdapterProductCraft extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<ModelProductCraftList> productCraftLists;
    Context context;

    public AdapterProductCraft(ArrayList<ModelProductCraftList> productCraftLists, Context context) {
        this.productCraftLists = productCraftLists;
        this.context = context;
    }

    private ProductCraftClick productCraftClick;
    public interface ProductCraftClick {
        public void onClick(int position);
    }

    public void setProductCraftClick(ProductCraftClick productCraftClick) {
        this.productCraftClick = productCraftClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductCraftViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fgproductcraft, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof ProductCraftViewHolder){
            ((ProductCraftViewHolder)holder).title.setText(productCraftLists.get(position).getTitle());

            ((ProductCraftViewHolder)holder).layoutView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(productCraftClick != null) {
                        productCraftClick.onClick(position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return productCraftLists.size();
    }

    public class ProductCraftViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ConstraintLayout layoutView;

        public ProductCraftViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            layoutView = itemView.findViewById(R.id.layoutView);

        }
    }
}
package quantec.com.moneypot.Activity.ClientCenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import quantec.com.moneypot.ModelCommon.dModel.ModelServiceList;
import quantec.com.moneypot.R;

public class AdapterService extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<ModelServiceList> serviceLists;
    Context context;

    public AdapterService(ArrayList<ModelServiceList> serviceLists, Context context) {
        this.serviceLists = serviceLists;
        this.context = context;
    }

    private ServiceTitleClick serviceTitleClick;
    public interface ServiceTitleClick{
        public void onClick(int position);
    }

    public void setServiceTitleClick(ServiceTitleClick serviceTitleClick) {
        this.serviceTitleClick = serviceTitleClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ServiceViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fgservice, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof ServiceViewHolder){
            ((ServiceViewHolder)holder).serviceTitle.setText(serviceLists.get(position).getTitle());

            ((ServiceViewHolder)holder).layoutView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(serviceTitleClick != null){
                        serviceTitleClick.onClick(position);
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return serviceLists.size();
    }

    public class ServiceViewHolder extends RecyclerView.ViewHolder {

        TextView serviceTitle;
        ConstraintLayout layoutView;

        public ServiceViewHolder(View itemView) {
            super(itemView);

            serviceTitle = itemView.findViewById(R.id.serviceTitle);
            layoutView = itemView.findViewById(R.id.layoutView);
        }
    }
}

package quantec.com.moneypot.Activity.SearchPort.BasicPage.Fragment.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import quantec.com.moneypot.Activity.SearchPort.BasicPage.Fragment.Model.dModel.ModelRecommendPort;
import quantec.com.moneypot.R;

public class AdapterRecommendPortTab extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<ModelRecommendPort> modelRecommendPorts;
    Context context;

    private final int EMPTY = 0;
    private final int PORTITEM = 1;

    public AdapterRecommendPortTab(ArrayList<ModelRecommendPort> modelRecommendPorts, Context context) {
        this.modelRecommendPorts = modelRecommendPorts;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        return modelRecommendPorts.size() != 0 ? PORTITEM : EMPTY;
    }

    private PortItemClick portItemClick;
    public interface PortItemClick {
        public void onClick(int position);
    }

    public void setPortItemClick(PortItemClick portItemClick) {
        this.portItemClick = portItemClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == EMPTY){
            return new EmptyItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activitysearch_recommendempty, parent, false));
        }else{
            return new PortItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activitysearch_recommenddata, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof PortItemViewHolder) {

            ((PortItemViewHolder)holder).item_search_page_recommend_port_text.setText(modelRecommendPorts.get(position).getPortTitle());
            ((PortItemViewHolder)holder).item_search_page_recommend_port_text.setBackgroundResource(R.drawable.bt_blue_line_search_page);

            ((PortItemViewHolder)holder).item_search_page_recommend_port_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(portItemClick != null) {
                        portItemClick.onClick(position);
                    }
                }
            });

        }

    }
    @Override
    public int getItemCount() {
        if(modelRecommendPorts.size() == 0){
            return 1;
        }else{
            return modelRecommendPorts.size();
        }
    }

    public class PortItemViewHolder extends RecyclerView.ViewHolder {
        TextView item_search_page_recommend_port_text;
        public PortItemViewHolder(View itemView) {
            super(itemView);
            item_search_page_recommend_port_text = itemView.findViewById(R.id.item_search_page_recommend_port_text);
        }
    }

    public class EmptyItemViewHolder extends RecyclerView.ViewHolder {
        public EmptyItemViewHolder(View itemView) {
            super(itemView);
        }
    }

}

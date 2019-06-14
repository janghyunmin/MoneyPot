package quantec.com.moneypot.Activity.Search.BasicPage.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import quantec.com.moneypot.DataModel.dModel.ModelRecommendPort;
import quantec.com.moneypot.R;

public class AdapterRecommendTab extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<ModelRecommendPort> modelRecommendPorts;
    Context context;

    private final int EMPTY = 0;
    private final int PORTITEM = 1;

    public AdapterRecommendTab(ArrayList<ModelRecommendPort> modelRecommendPorts, Context context) {
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
            return new EmptyItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_emptyrecommendtab, parent, false));
        }else{
            return new PortItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommendtab, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof PortItemViewHolder) {

            ((PortItemViewHolder)holder).title.setText(modelRecommendPorts.get(position).getPortTitle());
            ((PortItemViewHolder)holder).title.setBackgroundResource(R.drawable.bt_blue_line_search_page);

            ((PortItemViewHolder)holder).title.setOnClickListener(new View.OnClickListener() {
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
        TextView title;
        public PortItemViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
        }
    }

    public class EmptyItemViewHolder extends RecyclerView.ViewHolder {
        public EmptyItemViewHolder(View itemView) {
            super(itemView);
        }
    }

}

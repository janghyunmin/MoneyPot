package quantec.com.moneypot.Activity.Main.Fragment.FgTab1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import quantec.com.moneypot.R;

public class AdapterFitPot extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int EMPTYLIFECHALLENGE = 0;
    private final int LIFECHALLENGE = 1;
    private final int EMPTYTOTALPRICE = 2;
    private final int TOTALPRICE = 3;

    ArrayList<ModelFitPotList> modelFitPotLists;
    Context context;

    public AdapterFitPot(ArrayList<ModelFitPotList> modelFitPotLists, Context context) {
        this.modelFitPotLists = modelFitPotLists;
        this.context = context;
    }

    private EmptyTotalPriceClick emptyTotalPriceClick;
    public interface EmptyTotalPriceClick{
        public void onClick(int position);
    }

    public void setEmptyTotalPriceClick(EmptyTotalPriceClick emptyTotalPriceClick) {
        this.emptyTotalPriceClick = emptyTotalPriceClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == EMPTYLIFECHALLENGE){
            return new EmptyLifeChallengeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fitpot_emptylifechallenge, parent, false));
        }
        else if(viewType == LIFECHALLENGE){
            return new LifeChallengeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fitpot_lifechallenge, parent, false));
        }
        else if(viewType == EMPTYTOTALPRICE){
            return new EmptyTotalPriceViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fitpot_emptytotalprice, parent, false));
        }
        else{
            return new TotalPriceViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fitpot_totalprice, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof EmptyTotalPriceViewHolder){

            ((EmptyTotalPriceViewHolder)holder).makeBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(emptyTotalPriceClick != null){
                        emptyTotalPriceClick.onClick(position);
                    }
                }
            });

        }
        else if(holder instanceof TotalPriceViewHolder){

        }
        else if(holder instanceof EmptyLifeChallengeViewHolder){

        }
        else if(holder instanceof LifeChallengeViewHolder){

            ((LifeChallengeViewHolder)holder).lifeTitle.setText(modelFitPotLists.get(position).getLiftTitle());
            ((LifeChallengeViewHolder)holder).lifeContent.setText(modelFitPotLists.get(position).getLifeContent());
            ((LifeChallengeViewHolder)holder).lifeYear.setText(modelFitPotLists.get(position).getLifeYear());
            ((LifeChallengeViewHolder)holder).lifePrice.setText(modelFitPotLists.get(position).getLifePrice());
            ((LifeChallengeViewHolder)holder).lifeExp.setText(modelFitPotLists.get(position).getLifeExp());
            ((LifeChallengeViewHolder)holder).lifePlan.setText(modelFitPotLists.get(position).getLifePlan());
            ((LifeChallengeViewHolder)holder).lifeType.setText(modelFitPotLists.get(position).getLifeType());

            ((LifeChallengeViewHolder)holder).lifeStName.setText(modelFitPotLists.get(position).getLifeStTitle());
            ((LifeChallengeViewHolder)holder).lifeStYield.setText(modelFitPotLists.get(position).getLifeStYeild());

        }

    }

    @Override
    public int getItemViewType(int position) {

        if(position == 0) {

            if(modelFitPotLists.get(position).isEmpty){
                return TOTALPRICE;
            }
            else{
                return EMPTYTOTALPRICE;
            }

        }else {

            if(modelFitPotLists.get(position).isEmpty){
                return LIFECHALLENGE;
            }
            else{
                return EMPTYLIFECHALLENGE;
            }

        }
    }

    @Override
    public int getItemCount() {
        return modelFitPotLists.size();
    }

    public class EmptyLifeChallengeViewHolder extends RecyclerView.ViewHolder {

        public EmptyLifeChallengeViewHolder(View itemView) {
            super(itemView);
        }
    }
    public class LifeChallengeViewHolder extends RecyclerView.ViewHolder {

        TextView lifeTitle, lifeContent, lifeYear, lifePrice, lifePlan, lifeExp, lifeType, lifeStName, lifeStYield;

        public LifeChallengeViewHolder(View itemView) {
            super(itemView);

            lifeTitle = itemView.findViewById(R.id.lifeTitle);
            lifeContent = itemView.findViewById(R.id.lifeContent);
            lifeYear = itemView.findViewById(R.id.lifeYear);
            lifePrice = itemView.findViewById(R.id.lifePrice);
            lifePlan = itemView.findViewById(R.id.lifePlan);
            lifeExp = itemView.findViewById(R.id.lifeExp);
            lifeType = itemView.findViewById(R.id.lifeType);
            lifeStName = itemView.findViewById(R.id.lifeStName);
            lifeStYield = itemView.findViewById(R.id.lifeStYield);

        }
    }
    public class EmptyTotalPriceViewHolder extends RecyclerView.ViewHolder {

        TextView makeBt;

        public EmptyTotalPriceViewHolder(View itemView) {
            super(itemView);

            makeBt = itemView.findViewById(R.id.makeBt);
        }
    }
    public class TotalPriceViewHolder extends RecyclerView.ViewHolder {
        public TotalPriceViewHolder(View itemView) {
            super(itemView);
        }
    }
}

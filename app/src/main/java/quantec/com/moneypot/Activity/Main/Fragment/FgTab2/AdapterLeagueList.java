package quantec.com.moneypot.Activity.Main.Fragment.FgTab2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import quantec.com.moneypot.R;

public class AdapterLeagueList extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int TITLELEAGUE = 1;
    private final int EMPTYLEAGUELIST = 0;
    private final int LEAGUELIST = 2;

    ArrayList<ModelLeagueList> modelLeagueLists;
    Context context;

    public AdapterLeagueList(ArrayList<ModelLeagueList> modelLeagueLists, Context context) {
        this.modelLeagueLists = modelLeagueLists;
        this.context = context;
    }

    private LeagueEnterBtClick leagueEnterBtClick;
    public interface LeagueEnterBtClick {
        public void onClick(int position);
    }

    public void setLeagueEnterBtClick(LeagueEnterBtClick leagueEnterBtClick) {
        this.leagueEnterBtClick = leagueEnterBtClick;
    }


    private LeagueShowClick leagueShowClick;
    public interface LeagueShowClick {
        public void onClick(int position);
    }

    public void setLeagueShowClick(LeagueShowClick leagueShowClick) {
        this.leagueShowClick = leagueShowClick;
    }


    public LeagueRankClick leagueRankClick;
    public interface LeagueRankClick {
        public void onClick(int position);
    }

    public void setLeagueRankClick(LeagueRankClick leagueRankClick) {
        this.leagueRankClick = leagueRankClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == EMPTYLEAGUELIST){
            return new EmptyLeagueListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_emptyleague, parent, false));
        }
        else if(viewType == TITLELEAGUE){
            return new TitleLeagueViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_titleleague, parent, false));
        }
        else{
            return new LeagueListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_league, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        if(holder instanceof EmptyLeagueListViewHolder){

            ((EmptyLeagueListViewHolder)holder).leagueEnterBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(leagueEnterBtClick != null) {
                        leagueEnterBtClick.onClick(position);
                    }
                }
            });
        }

        if(holder instanceof TitleLeagueViewHolder){
            ((TitleLeagueViewHolder)holder).titleLeagueView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(leagueShowClick != null) {
                        leagueShowClick.onClick(position);
                    }
                }
            });
        }


        if(holder instanceof LeagueListViewHolder){

            if(position == 1){
                ((LeagueListViewHolder)holder).rankImage.setBackgroundResource(R.drawable.img_first);
            }else if(position == 2){
                ((LeagueListViewHolder)holder).rankImage.setBackgroundResource(R.drawable.img_second);
            }else{
                ((LeagueListViewHolder)holder).rankImage.setBackgroundResource(R.drawable.img_third);
            }

            ((LeagueListViewHolder)holder).rankName.setText(modelLeagueLists.get(position).getUserName());
            ((LeagueListViewHolder)holder).rankStName.setText(modelLeagueLists.get(position).getStTitle());
            ((LeagueListViewHolder)holder).rankStYield.setText(modelLeagueLists.get(position).getStYield());

            ((LeagueListViewHolder)holder).rankView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(leagueRankClick != null){
                        leagueRankClick.onClick(position);
                    }
                }
            });
        }


    }

    @Override
    public int getItemCount() {

       return modelLeagueLists.size();

    }

    @Override
    public int getItemViewType(int position) {

        if(modelLeagueLists.get(position).getCategory() == 0){
            return EMPTYLEAGUELIST;
        }
        else{
            if(position == 0){
                return TITLELEAGUE;
            }else{
                return LEAGUELIST;
            }
        }

    }

    public class TitleLeagueViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout titleLeagueView;

        public TitleLeagueViewHolder(View itemView) {
            super(itemView);

            titleLeagueView = itemView.findViewById(R.id.titleLeagueView);
        }
    }

    public class LeagueListViewHolder extends RecyclerView.ViewHolder {

        TextView rankName, rankStName, rankStYield;
        ImageView rankImage;
        ConstraintLayout rankView;

        public LeagueListViewHolder(View itemView) {
            super(itemView);

            rankImage = itemView.findViewById(R.id.rankImage);
            rankName = itemView.findViewById(R.id.rankName);
            rankStName = itemView.findViewById(R.id.rankStName);
            rankStYield = itemView.findViewById(R.id.rankStYield);
            rankView = itemView.findViewById(R.id.rankView);

        }
    }

    public class EmptyLeagueListViewHolder extends RecyclerView.ViewHolder {

        TextView leagueEnterBt;

        public EmptyLeagueListViewHolder(View itemView) {
            super(itemView);

            leagueEnterBt = itemView.findViewById(R.id.leagueEnterBt);
        }
    }
}

package quantec.com.moneypot.Activity.PotDetail.AdpaterPotDetail;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import quantec.com.moneypot.Activity.PotDetail.ModelPotDetail;
import quantec.com.moneypot.R;

public class AdapterPotDetail extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int EMPTY = 0;
    private final int ITEM = 1;
    private final int ADD = 2;
    private final int HEADER = 3;

    ArrayList<ModelPotDetail> modelPotDetails;
    Context context;

    public AdapterPotDetail(ArrayList<ModelPotDetail> modelPotDetails, Context context) {
        this.modelPotDetails = modelPotDetails;
        this.context = context;
    }

    private AddViewBtClick addViewBtClick;
    public interface AddViewBtClick {
        public void onClick(int position);
    }

    public void setAddViewBtClick(AddViewBtClick addViewBtClick) {
        this.addViewBtClick = addViewBtClick;
    }

    private PotDetailItemClick potDetailItemClick;
    public interface PotDetailItemClick {
        public void onClick(int position);
    }

    public void setPotDetailItemClick(PotDetailItemClick potDetailItemClick) {
        this.potDetailItemClick = potDetailItemClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == EMPTY){

            return new EmptyPotDetailViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_emptypotdetail, parent, false));

        }
        else if(viewType == HEADER){
            return new HeaderPotDetailViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_headerpotdetail, parent, false));
        }
        else if(viewType == ADD){

            return new AddPotDetailViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_addpotdetail, parent, false));

        }else{

            return new PotDetailViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_potdetail, parent, false));

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof EmptyPotDetailViewHolder){

        }

        else if(holder instanceof HeaderPotDetailViewHolder){

        }

        else if(holder instanceof AddPotDetailViewHolder){


            if(modelPotDetails.size() > 5){

                if(modelPotDetails.get(0).isAddViewState()){
                    ((AddPotDetailViewHolder)holder).addViewBt.setText("접기");
                }else{
                    ((AddPotDetailViewHolder)holder).addViewBt.setText("더보기");
                }

                ((AddPotDetailViewHolder)holder).addViewLayout.setVisibility(View.VISIBLE);
            }else{
                ((AddPotDetailViewHolder)holder).addViewLayout.setVisibility(View.GONE);
            }

            ((AddPotDetailViewHolder)holder).addViewBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(addViewBtClick != null){
                        addViewBtClick.onClick(position);
                    }
                }
            });

        }

        else if(holder instanceof PotDetailViewHolder){

            ((PotDetailViewHolder)holder).title.setText(modelPotDetails.get(position).getTitle());
            ((PotDetailViewHolder)holder).subTitle.setText(modelPotDetails.get(position).getSubTitle());

            ((PotDetailViewHolder)holder).itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(potDetailItemClick != null){
                        potDetailItemClick.onClick(position);
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {

        if(modelPotDetails.size() > 5){
            if(modelPotDetails.get(0).isAddViewState()){
                return modelPotDetails.size();
            }
            else{
                return 5;
            }
        }
        else{
            return modelPotDetails.size();
        }
    }

    @Override
    public int getItemViewType(int position) {

        if(modelPotDetails.get(position).isEmpty()){
            return EMPTY;
        }
        else{

            if(modelPotDetails.size() > 5){

                if(modelPotDetails.get(0).isAddViewState()){

                    if(position == 0){
                        return HEADER;
                    }
                    else if(modelPotDetails.size()-1 == position){
                        return ADD;
                    }
                    else{
                        return ITEM;
                    }

                }else{

                    if(position == 0){
                        return HEADER;
                    }
                    else if(4 == position){
                        return ADD;
                    }
                    else{
                        return ITEM;
                    }

                }

            }else{

                if(position == 0){
                    return HEADER;
                }
                else if(modelPotDetails.size()-1 == position){
                    return ADD;
                }
                else{
                    return ITEM;
                }

            }
        }
    }

    public class EmptyPotDetailViewHolder extends RecyclerView.ViewHolder {
        public EmptyPotDetailViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class PotDetailViewHolder extends RecyclerView.ViewHolder {

        TextView title, subTitle;
        ConstraintLayout itemLayout;


        public PotDetailViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            subTitle = itemView.findViewById(R.id.subTitle);

            itemLayout = itemView.findViewById(R.id.itemLayout);
        }
    }

    public class AddPotDetailViewHolder extends RecyclerView.ViewHolder {

        LinearLayout addViewLayout;
        TextView addViewBt;

        public AddPotDetailViewHolder(@NonNull View itemView) {
            super(itemView);

            addViewLayout = itemView.findViewById(R.id.addViewLayout);
            addViewBt = itemView.findViewById(R.id.addViewBt);
        }
    }

    public class HeaderPotDetailViewHolder extends RecyclerView.ViewHolder {
        public HeaderPotDetailViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}

package quantec.com.moneypot.Activity.Main.Fragment.FgTab2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.ModelAdImage;
import quantec.com.moneypot.R;

public class AdapterAdImage extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<ModelAdImage> modelAdImages;
    Context context;

    public AdapterAdImage(ArrayList<ModelAdImage> modelAdImages, Context context) {
        this.modelAdImages = modelAdImages;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdImageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_leaguebanner, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof AdImageViewHolder){
            ((AdImageViewHolder)holder).bannerImage.setBackgroundResource(R.drawable.img_researcher_1);
        }
    }

    @Override
    public int getItemCount() {
        return modelAdImages.size();
    }

    public class AdImageViewHolder extends RecyclerView.ViewHolder {

        ImageView bannerImage;

        public AdImageViewHolder(View itemView) {
            super(itemView);

            bannerImage = itemView.findViewById(R.id.bannerImage);
        }
    }

}

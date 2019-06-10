package quantec.com.moneypot.Activity.Main.Fragment.Tab2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import quantec.com.moneypot.Activity.Main.ActivityMain;
import quantec.com.moneypot.R;

public class AdapterAdImage extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<ModelAdImage> modelAdImages;
    Context context;
    ActivityMain mainActivity;

    public AdapterAdImage(ArrayList<ModelAdImage> modelAdImages, Context context, ActivityMain mainActivity) {
        this.modelAdImages = modelAdImages;
        this.context = context;
        this.mainActivity = mainActivity;
    }

    private ImageClick imageClick;
    public interface ImageClick {
        public void onClick(int position);
    }

    public void setImageClick(ImageClick imageClick) {
        this.imageClick = imageClick;
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

            ViewCompat.setTransitionName(((AdImageViewHolder)holder).bannerImage, modelAdImages.get(position).getUrl());

            ((AdImageViewHolder)holder).bannerImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(imageClick != null) {
                        imageClick.onClick(position);

//                        Intent intent = new Intent(context, ActivityDetailAdImage.class);
//
//                        intent.putExtra("albumVO", modelAdImages.get(position).getUrl());
//                        Pair<View, String> pair_thumb = null;
//                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//                            pair_thumb = Pair.create(((AdImageViewHolder)holder).bannerImage, ((AdImageViewHolder)holder).bannerImage.getTransitionName());
//                        }
//
//                        ActivityOptionsCompat optionsCompat =
//
//                                ActivityOptionsCompat.makeSceneTransitionAnimation(mainActivity, pair_thumb);
//
//                        mainActivity.startActivity(intent, optionsCompat.toBundle());
                    }
                }
            });
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

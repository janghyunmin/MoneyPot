package quantec.com.moneypot.Activity.Main.Fragment.Tab1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

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

    boolean isFront = true;
    private int DURATION = 500;
    private float centerX;
    private float centerY;


    private EmptyTotalPriceClick emptyTotalPriceClick;
    public interface EmptyTotalPriceClick{
        public void onClick(int position);
    }

    public void setEmptyTotalPriceClick(EmptyTotalPriceClick emptyTotalPriceClick) {
        this.emptyTotalPriceClick = emptyTotalPriceClick;
    }

    private EmptyLifeChallengeClick emptyLifeChallengeClick;
    public interface EmptyLifeChallengeClick{
        public void onClick(int position);
    }

    public void setEmptyLifeChallengeClick(EmptyLifeChallengeClick emptyLifeChallengeClick) {
        this.emptyLifeChallengeClick = emptyLifeChallengeClick;
    }

    private LifeChallengeClick lifeChallengeClick;
    public interface LifeChallengeClick{
        public void onClick(int position);
    }

    public void setLifeChallengeClick(LifeChallengeClick lifeChallengeClick) {
        this.lifeChallengeClick = lifeChallengeClick;
    }

    private ChangedStViewClick changedStViewClick;
    public interface ChangedStViewClick {
        public void onClick(int position);
    }

    public void setChangedStViewClick(ChangedStViewClick changedStViewClick) {
        this.changedStViewClick = changedStViewClick;
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

            if(!modelFitPotLists.get(position).isEmpty){

            }else{
                ((TotalPriceViewHolder)holder).webView.loadUrl(modelFitPotLists.get(position).getWebViewUrl());
            }

        }
        else if(holder instanceof EmptyLifeChallengeViewHolder){

            ((EmptyLifeChallengeViewHolder)holder).cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(emptyLifeChallengeClick != null) {
                        emptyLifeChallengeClick.onClick(position);
                    }
                }
            });

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

            ((LifeChallengeViewHolder)holder).deleteBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(lifeChallengeClick != null) {
                        lifeChallengeClick.onClick(position);
                    }
                }
            });


            ((LifeChallengeViewHolder)holder).lifeRecomBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    applyRotation(((LifeChallengeViewHolder)holder).flipView, ((LifeChallengeViewHolder)holder).front,((LifeChallengeViewHolder)holder).back,
//                            0f, 90f, 180f, 0f);
                    applyRotation(((LifeChallengeViewHolder)holder).flipView, ((LifeChallengeViewHolder)holder).front,((LifeChallengeViewHolder)holder).back,
                            0f, 90f, 180f, 0f);
                }
            });

            ((LifeChallengeViewHolder)holder).lifeRecomBt2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    applyRotation(((LifeChallengeViewHolder)holder).flipView, ((LifeChallengeViewHolder)holder).front,((LifeChallengeViewHolder)holder).back,
//                            180f, 270f, 360f, 0f);
                    applyRotation(((LifeChallengeViewHolder)holder).flipView, ((LifeChallengeViewHolder)holder).front,((LifeChallengeViewHolder)holder).back,
                            360f, 270f, 180f, 0f);

                }
            });

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
        CardView cardView;

        public EmptyLifeChallengeViewHolder(View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardView);
        }

    }
    public class LifeChallengeViewHolder extends RecyclerView.ViewHolder {

        TextView lifeTitle, lifeContent, lifeYear, lifePrice, lifePlan, lifeExp, lifeType, lifeStName, lifeStYield, deleteBt, lifeRecomBt, lifeRecomBt2;
        FrameLayout flipView;
        ConstraintLayout front, back;

        public LifeChallengeViewHolder(@NonNull View itemView) {
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
            deleteBt = itemView.findViewById(R.id.deleteBt);

            flipView = itemView.findViewById(R.id.flipView);
            front = itemView.findViewById(R.id.front);
            back = itemView.findViewById(R.id.back);
            lifeRecomBt = itemView.findViewById(R.id.lifeRecomBt);
            lifeRecomBt2 = itemView.findViewById(R.id.lifeRecomBt2);
        }
    }

    //    public class LifeChallengeViewHolder extends RecyclerView.ViewHolder {
//
//        TextView lifeTitle, lifeContent, lifeYear, lifePrice, lifePlan, lifeExp, lifeType, lifeStName, lifeStYield, deleteBt, lifeRecomBt, lifeRecomBt2;
//        FrameLayout flipView;
//        ConstraintLayout front, back;
//
//        public LifeChallengeViewHolder(View itemView) {
//            super(itemView);
//
//            lifeTitle = itemView.findViewById(R.id.lifeTitle);
//            lifeContent = itemView.findViewById(R.id.lifeContent);
//            lifeYear = itemView.findViewById(R.id.lifeYear);
//            lifePrice = itemView.findViewById(R.id.lifePrice);
//            lifePlan = itemView.findViewById(R.id.lifePlan);
//            lifeExp = itemView.findViewById(R.id.lifeExp);
//            lifeType = itemView.findViewById(R.id.lifeType);
//            lifeStName = itemView.findViewById(R.id.lifeStName);
//            lifeStYield = itemView.findViewById(R.id.lifeStYield);
//            deleteBt = itemView.findViewById(R.id.deleteBt);
//
//            flipView = itemView.findViewById(R.id.flipView);
//            front = itemView.findViewById(R.id.front);
//            back = itemView.findViewById(R.id.back);
//            lifeRecomBt = itemView.findViewById(R.id.lifeRecomBt);
//            lifeRecomBt2 = itemView.findViewById(R.id.lifeRecomBt2);
//
//
//        }
//    }

    public class EmptyTotalPriceViewHolder extends RecyclerView.ViewHolder {
        TextView makeBt;

        public EmptyTotalPriceViewHolder(@NonNull View itemView) {
            super(itemView);
            makeBt = itemView.findViewById(R.id.makeBt);
        }
    }

//    public class EmptyTotalPriceViewHolder extends RecyclerView.ViewHolder {
//
//        TextView makeBt;
//
//        public EmptyTotalPriceViewHolder(View itemView) {
//            super(itemView);
//
//            makeBt = itemView.findViewById(R.id.makeBt);
//        }
//    }


    public class TotalPriceViewHolder extends RecyclerView.ViewHolder {

        WebView webView;

        public TotalPriceViewHolder(View itemView) {
            super(itemView);

            webView = itemView.findViewById(R.id.webView);

            WebSettings mws=webView.getSettings();//Mobile Web Setting
            mws.setJavaScriptEnabled(true);//자바스크립트 허용
            mws.setLoadWithOverviewMode(true);//컨텐츠가 웹뷰보다 클 경우 스크린 크기에 맞게 조정

            webView.setWebViewClient(new WebViewClient(){
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
            });

        }
    }



    private void applyRotation(View view, View frontView, View backView, float start, float end, float mid, float depth){
        this.centerX = view.getWidth() / 2.0f;
        this.centerY = view.getHeight() / 2.0f;

        Rotate3dAnimation rot = new Rotate3dAnimation(start, mid, centerX, centerY, depth, true);
        rot.setDuration(DURATION);
        rot.setInterpolator(new AccelerateInterpolator());
        rot.setAnimationListener(new DisplayNextView(view, frontView, backView, mid, end, depth));
        view.startAnimation(rot);
    }

    private class DisplayNextView implements Animation.AnimationListener {

        private float mid;
        private float end;
        private float depth;
        private View view;
        private View frontView, backView;

        public DisplayNextView(View view, View frontView, View backView,float mid, float end, float depth) {
            this.mid = mid;
            this.end = end;
            this.depth = depth;
            this.view = view;
            this.frontView = frontView;
            this.backView = backView;
        }

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {

            view.post(new Runnable() {
                @Override
                public void run() {

                    if(isFront){
                        frontView.setVisibility(View.GONE);
                        backView.setVisibility(View.VISIBLE);
                        isFront = false;

                    }else{
                        frontView.setVisibility(View.VISIBLE);
                        backView.setVisibility(View.GONE);
                        isFront = true;
                    }

//                    Rotate3dAnimation rot = new Rotate3dAnimation(mid, end, centerX, centerY, depth, true);
//                    rot.setDuration(DURATION);
//                    rot.setInterpolator(new AccelerateInterpolator());
//                    view.startAnimation(rot);

                }
            });

        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}

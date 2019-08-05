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

    private final int WEBVIEWEMPTY = 0;
    private final int WEBVIEWITEM = 1;

    private final int ADDVIEW = 2;
    private final int INSERTVIEW = 3;
    private final int CHARTVIEW = 4;
    private final int RECOMVIEW = 5;
    private final int INVESTVIEW = 6;


    ArrayList<ModelFitPotList> modelFitPotLists;
    Context context;

    ArrayList<ModelAccountWebView> modelAccountWebViews;


    public AdapterFitPot(ArrayList<ModelFitPotList> modelFitPotLists, Context context, ArrayList<ModelAccountWebView> modelAccountWebViews) {
        this.modelFitPotLists = modelFitPotLists;
        this.context = context;
        this.modelAccountWebViews = modelAccountWebViews;
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


    private NextBtClick nextBtClick;
    public interface NextBtClick {
        public void onClick(int position);
    }

    public void setNextBtClick(NextBtClick nextBtClick) {
        this.nextBtClick = nextBtClick;
    }


    private ChartNextBtClick chartNextBtClick;
    public interface ChartNextBtClick {
        public void onClick(int position);
    }

    public void setChartNextBtClick(ChartNextBtClick chartNextBtClick) {
        this.chartNextBtClick = chartNextBtClick;
    }





    private AccountBtClick accountBtClick;
    public interface AccountBtClick {
        public void onClick(int position);
    }

    public void setAccountBtClick(AccountBtClick accountBtClick) {
        this.accountBtClick = accountBtClick;
    }

    private AddItemViewClick addItemViewClick;
    public interface AddItemViewClick {
        public void onClick(int position);
    }

    public void setAddItemViewClick(AddItemViewClick addItemViewClick) {
        this.addItemViewClick = addItemViewClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == WEBVIEWEMPTY){
            return new WebViewEmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_maintab1_webviewempty, parent, false));
        }
        else if(viewType == WEBVIEWITEM){
            return new WebViewItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_maintab1_webviewitem, parent, false));
        }
        else if(viewType == ADDVIEW){
            return new LifeAddViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_maintab1_addview, parent, false));
        }
        else if(viewType == INSERTVIEW){
            return new LifeInsertViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_maintab1_insertview, parent, false));
        }
        else if(viewType == CHARTVIEW){
            return new LifeChartViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_maintab1_chartview, parent, false));
        }
        else if(viewType == RECOMVIEW){
            return new LifeRecomViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_maintab1_recomview, parent, false));
        }
        else{
            return new LifeInvestViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_maintab1_investview, parent, false));
        }
    }

//    if(viewType == WEBVIEWEMPTY){
//        return new WebViewEmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_maintab1_custom, parent, false));
//    }
//        else if(viewType == WEBVIEWITEM){
//        return new WebViewItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_maintab1_chart, parent, false));
//    }
//        else if(viewType == ADDVIEW){
//        return new LifeAddViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fitpot_emptytotalprice, parent, false));
//    }
//        else if(viewType == INSERTVIEW){
//        return new LifeInsertViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fitpot_lifechallenge, parent, false));
//    }
//        else if(viewType == CHARTVIEW){
//        return new LifeChartViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fitpot_lifechallenge, parent, false));
//    }
//        else if(viewType == RECOMVIEW){
//        return new LifeRecomViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fitpot_lifechallenge, parent, false));
//    }
//        else{
//        return new LifeInvestViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fitpot_totalprice, parent, false));
//    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof WebViewEmptyViewHolder){

            ((WebViewEmptyViewHolder)holder).accountBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(accountBtClick != null){
                        accountBtClick.onClick(position);
                    }
                }
            });
        }

        if(holder instanceof WebViewItemViewHolder){


        }

        if(holder instanceof LifeAddViewHolder){

            ((LifeAddViewHolder)holder).addItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(addItemViewClick != null){
                        addItemViewClick.onClick(position);
                    }
                }
            });

        }

        if(holder instanceof LifeInsertViewHolder){


        }

        if(holder instanceof LifeChartViewHolder){


        }

        if(holder instanceof LifeRecomViewHolder){


        }

        if(holder instanceof LifeInvestViewHolder){


        }
    }


    @Override
    public int getItemViewType(int position) {

       if(modelFitPotLists.get(position).isWebView()){

           if(modelAccountWebViews.get(position).isEmpty()){
               return WEBVIEWEMPTY;
           }else{
               return WEBVIEWITEM;
           }

       }else{

           if(modelFitPotLists.get(position).isAddView()){
            return ADDVIEW;
           }else if(modelFitPotLists.get(position).isInsertView()){
               return INSERTVIEW;
           }else if(modelFitPotLists.get(position).isChartView()){
               return CHARTVIEW;
           }else if(modelFitPotLists.get(position).isRecomView()){
               return RECOMVIEW;
           }else{
               return INVESTVIEW;
           }
       }
    }

    @Override
    public int getItemCount() {
        return modelFitPotLists.size();
    }

    public class WebViewEmptyViewHolder extends RecyclerView.ViewHolder {

        TextView accountBt;

        public WebViewEmptyViewHolder(@NonNull View itemView) {
            super(itemView);

            accountBt = itemView.findViewById(R.id.accountBt);
        }
    }

    public class WebViewItemViewHolder extends RecyclerView.ViewHolder {
        public WebViewItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class LifeAddViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout addItemView;

        public LifeAddViewHolder(@NonNull View itemView) {
            super(itemView);

            addItemView = itemView.findViewById(R.id.addItemView);
        }
    }

    public class LifeInsertViewHolder extends RecyclerView.ViewHolder {

        TextView nextBt;

        public LifeInsertViewHolder(@NonNull View itemView) {
            super(itemView);

            nextBt = itemView.findViewById(R.id.nextBt);
        }
    }

    public class LifeChartViewHolder extends RecyclerView.ViewHolder {
        public LifeChartViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class LifeRecomViewHolder extends RecyclerView.ViewHolder {
        public LifeRecomViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class LifeInvestViewHolder extends RecyclerView.ViewHolder {
        public LifeInvestViewHolder(@NonNull View itemView) {
            super(itemView);
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
                }
            });
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}


//public class AdapterFitPot extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
//
//    private final int EMPTYLIFECHALLENGE = 0;
//    private final int LIFECHALLENGE = 1;
//    private final int EMPTYTOTALPRICE = 2;
//    private final int TOTALPRICE = 3;
//
//    private final int CUSTOMLIFE = 4;
//
//    ArrayList<ModelFitPotList> modelFitPotLists;
//    Context context;
//
//    public AdapterFitPot(ArrayList<ModelFitPotList> modelFitPotLists, Context context) {
//        this.modelFitPotLists = modelFitPotLists;
//        this.context = context;
//    }
//
//    boolean isFront = true;
//    private int DURATION = 500;
//    private float centerX;
//    private float centerY;
//
//
//    private EmptyTotalPriceClick emptyTotalPriceClick;
//    public interface EmptyTotalPriceClick{
//        public void onClick(int position);
//    }
//
//    public void setEmptyTotalPriceClick(EmptyTotalPriceClick emptyTotalPriceClick) {
//        this.emptyTotalPriceClick = emptyTotalPriceClick;
//    }
//
//    private EmptyLifeChallengeClick emptyLifeChallengeClick;
//    public interface EmptyLifeChallengeClick{
//        public void onClick(int position);
//    }
//
//    public void setEmptyLifeChallengeClick(EmptyLifeChallengeClick emptyLifeChallengeClick) {
//        this.emptyLifeChallengeClick = emptyLifeChallengeClick;
//    }
//
//    private LifeChallengeClick lifeChallengeClick;
//    public interface LifeChallengeClick{
//        public void onClick(int position);
//    }
//
//    public void setLifeChallengeClick(LifeChallengeClick lifeChallengeClick) {
//        this.lifeChallengeClick = lifeChallengeClick;
//    }
//
//    private ChangedStViewClick changedStViewClick;
//    public interface ChangedStViewClick {
//        public void onClick(int position);
//    }
//
//    public void setChangedStViewClick(ChangedStViewClick changedStViewClick) {
//        this.changedStViewClick = changedStViewClick;
//    }
//
//
//    private NextBtClick nextBtClick;
//    public interface NextBtClick {
//        public void onClick(int position);
//    }
//
//    public void setNextBtClick(NextBtClick nextBtClick) {
//        this.nextBtClick = nextBtClick;
//    }
//
//
//    private ChartNextBtClick chartNextBtClick;
//    public interface ChartNextBtClick {
//        public void onClick(int position);
//    }
//
//    public void setChartNextBtClick(ChartNextBtClick chartNextBtClick) {
//        this.chartNextBtClick = chartNextBtClick;
//    }
//
//    @NonNull
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        if(viewType == EMPTYLIFECHALLENGE){
//            return new EmptyLifeChallengeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_maintab1_custom, parent, false));
////            return new EmptyLifeChallengeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fitpot_emptylifechallenge, parent, false));
//        }
//        else if(viewType == LIFECHALLENGE){
////            return new LifeChallengeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fitpot_lifechallenge, parent, false));\\
//            return new LifeChallengeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_maintab1_chart, parent, false));
//        }
//        else if(viewType == EMPTYTOTALPRICE){
//            return new EmptyTotalPriceViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fitpot_emptytotalprice, parent, false));
//        }
//        else if(viewType == CUSTOMLIFE){
//            return new LifeListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fitpot_lifechallenge, parent, false));
//        }
//        else{
//            return new TotalPriceViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fitpot_totalprice, parent, false));
//        }
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//
//        if(holder instanceof EmptyTotalPriceViewHolder){
//
//
//        }
//
//        else if(holder instanceof TotalPriceViewHolder){
//
//            if(!modelFitPotLists.get(position).isEmpty){
//
//            }else{
//                ((TotalPriceViewHolder)holder).webView.loadUrl(modelFitPotLists.get(position).getWebViewUrl());
//            }
//
//        }
//        else if(holder instanceof EmptyLifeChallengeViewHolder){
//
////            ((EmptyLifeChallengeViewHolder)holder).cardView.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View v) {
////                    if(emptyLifeChallengeClick != null) {
////                        emptyLifeChallengeClick.onClick(position);
////                    }
////                }
////            });
//
//
//            ((EmptyLifeChallengeViewHolder)holder).nextBt.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(nextBtClick != null){
//                        nextBtClick.onClick(position);
//                    }
//                }
//            });
//
//
//        }
//        else if(holder instanceof LifeChallengeViewHolder){
//
//            ((LifeChallengeViewHolder)holder).nextBt.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(chartNextBtClick != null){
//                        chartNextBtClick.onClick(position);
//                    }
//                }
//            });
//
////            ((LifeChallengeViewHolder)holder).lifeTitle.setText(modelFitPotLists.get(position).getLiftTitle());
////            ((LifeChallengeViewHolder)holder).lifeContent.setText(modelFitPotLists.get(position).getLifeContent());
////            ((LifeChallengeViewHolder)holder).lifeYear.setText(modelFitPotLists.get(position).getLifeYear());
////            ((LifeChallengeViewHolder)holder).lifePrice.setText(modelFitPotLists.get(position).getLifePrice());
////            ((LifeChallengeViewHolder)holder).lifeExp.setText(modelFitPotLists.get(position).getLifeExp());
////            ((LifeChallengeViewHolder)holder).lifePlan.setText(modelFitPotLists.get(position).getLifePlan());
////            ((LifeChallengeViewHolder)holder).lifeType.setText(modelFitPotLists.get(position).getLifeType());
////
////            ((LifeChallengeViewHolder)holder).lifeStName.setText(modelFitPotLists.get(position).getLifeStTitle());
////            ((LifeChallengeViewHolder)holder).lifeStYield.setText(modelFitPotLists.get(position).getLifeStYeild());
////
////            ((LifeChallengeViewHolder)holder).deleteBt.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View v) {
////                    if(lifeChallengeClick != null) {
////                        lifeChallengeClick.onClick(position);
////                    }
////                }
////            });
////
////
////            ((LifeChallengeViewHolder)holder).lifeRecomBt.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View v) {
////
//////                    applyRotation(((LifeChallengeViewHolder)holder).flipView, ((LifeChallengeViewHolder)holder).front,((LifeChallengeViewHolder)holder).back,
//////                            0f, 90f, 180f, 0f);
////                    applyRotation(((LifeChallengeViewHolder)holder).flipView, ((LifeChallengeViewHolder)holder).front,((LifeChallengeViewHolder)holder).back,
////                            0f, 90f, 180f, 0f);
////                }
////            });
////
////            ((LifeChallengeViewHolder)holder).lifeRecomBt2.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View v) {
//////                    applyRotation(((LifeChallengeViewHolder)holder).flipView, ((LifeChallengeViewHolder)holder).front,((LifeChallengeViewHolder)holder).back,
//////                            180f, 270f, 360f, 0f);
////                    applyRotation(((LifeChallengeViewHolder)holder).flipView, ((LifeChallengeViewHolder)holder).front,((LifeChallengeViewHolder)holder).back,
////                            360f, 270f, 180f, 0f);
////
////                }
////            });
//
//        }
//        else if(holder instanceof LifeListViewHolder){
//
//        }
//    }
//
//
//    @Override
//    public int getItemViewType(int position) {
//        if(position == 0) {
//
//            if(modelFitPotLists.get(position).isEmpty){
//                return TOTALPRICE;
//            }
//            else{
//                return EMPTYTOTALPRICE;
//            }
//        }
//        else {
//
//            if(modelFitPotLists.get(position).isCustom()){
//                return EMPTYLIFECHALLENGE;
//            }else{
//
//                if(modelFitPotLists.get(position).isLifeList()){
//                    return CUSTOMLIFE;
//                }else{
//                    return LIFECHALLENGE;
//                }
////                return LIFECHALLENGE;
//            }
//        }
//    }
//
////    @Override
////    public int getItemViewType(int position) {
////        if(position == 0) {
////
////            if(modelFitPotLists.get(position).isEmpty){
////                return TOTALPRICE;
////            }
////            else{
////                return EMPTYTOTALPRICE;
////            }
////        }
////        else {
////
////            if(modelFitPotLists.get(position).isEmpty){
////                return EMPTYLIFECHALLENGE;
////            }
////            else{
////                return LIFECHALLENGE;
////            }
////        }
////    }
//
//    @Override
//    public int getItemCount() {
//        return modelFitPotLists.size();
//    }
//
//    public class EmptyLifeChallengeViewHolder extends RecyclerView.ViewHolder {
//        CardView cardView;
//        TextView nextBt;
//
//        public EmptyLifeChallengeViewHolder(View itemView) {
//            super(itemView);
//
//            cardView = itemView.findViewById(R.id.cardView);
//
//            nextBt = itemView.findViewById(R.id.nextBt);
//        }
//
//    }
//    public class LifeChallengeViewHolder extends RecyclerView.ViewHolder {
//
//        TextView lifeTitle, lifeContent, lifeYear, lifePrice, lifePlan, lifeExp, lifeType, lifeStName, lifeStYield, deleteBt, lifeRecomBt, lifeRecomBt2, nextBt;
//        FrameLayout flipView;
//        ConstraintLayout front, back;
//
//        public LifeChallengeViewHolder(@NonNull View itemView) {
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
//            nextBt = itemView.findViewById(R.id.nextBt);
//        }
//    }
//
//    public class LifeListViewHolder extends RecyclerView.ViewHolder {
//        public LifeListViewHolder(@NonNull View itemView) {
//            super(itemView);
//        }
//    }
//
//    //    public class LifeChallengeViewHolder extends RecyclerView.ViewHolder {
////
////        TextView lifeTitle, lifeContent, lifeYear, lifePrice, lifePlan, lifeExp, lifeType, lifeStName, lifeStYield, deleteBt, lifeRecomBt, lifeRecomBt2;
////        FrameLayout flipView;
////        ConstraintLayout front, back;
////
////        public LifeChallengeViewHolder(View itemView) {
////            super(itemView);
////
////            lifeTitle = itemView.findViewById(R.id.lifeTitle);
////            lifeContent = itemView.findViewById(R.id.lifeContent);
////            lifeYear = itemView.findViewById(R.id.lifeYear);
////            lifePrice = itemView.findViewById(R.id.lifePrice);
////            lifePlan = itemView.findViewById(R.id.lifePlan);
////            lifeExp = itemView.findViewById(R.id.lifeExp);
////            lifeType = itemView.findViewById(R.id.lifeType);
////            lifeStName = itemView.findViewById(R.id.lifeStName);
////            lifeStYield = itemView.findViewById(R.id.lifeStYield);
////            deleteBt = itemView.findViewById(R.id.deleteBt);
////
////            flipView = itemView.findViewById(R.id.flipView);
////            front = itemView.findViewById(R.id.front);
////            back = itemView.findViewById(R.id.back);
////            lifeRecomBt = itemView.findViewById(R.id.lifeRecomBt);
////            lifeRecomBt2 = itemView.findViewById(R.id.lifeRecomBt2);
////
////
////        }
////    }
//
//    public class EmptyTotalPriceViewHolder extends RecyclerView.ViewHolder {
//
//        public EmptyTotalPriceViewHolder(@NonNull View itemView) {
//            super(itemView);
//        }
//    }
//
////    public class EmptyTotalPriceViewHolder extends RecyclerView.ViewHolder {
////
////        TextView makeBt;
////
////        public EmptyTotalPriceViewHolder(View itemView) {
////            super(itemView);
////
////            makeBt = itemView.findViewById(R.id.makeBt);
////        }
////    }
//
//
//    public class TotalPriceViewHolder extends RecyclerView.ViewHolder {
//
//        WebView webView;
//
//        public TotalPriceViewHolder(View itemView) {
//            super(itemView);
//
//            webView = itemView.findViewById(R.id.webView);
//
//            WebSettings mws=webView.getSettings();//Mobile Web Setting
//            mws.setJavaScriptEnabled(true);//자바스크립트 허용
//            mws.setLoadWithOverviewMode(true);//컨텐츠가 웹뷰보다 클 경우 스크린 크기에 맞게 조정
//
//            webView.setWebViewClient(new WebViewClient(){
//                @Override
//                public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                    view.loadUrl(url);
//                    return true;
//                }
//            });
//
//        }
//    }
//
//
//
//    private void applyRotation(View view, View frontView, View backView, float start, float end, float mid, float depth){
//        this.centerX = view.getWidth() / 2.0f;
//        this.centerY = view.getHeight() / 2.0f;
//
//        Rotate3dAnimation rot = new Rotate3dAnimation(start, mid, centerX, centerY, depth, true);
//        rot.setDuration(DURATION);
//        rot.setInterpolator(new AccelerateInterpolator());
//        rot.setAnimationListener(new DisplayNextView(view, frontView, backView, mid, end, depth));
//        view.startAnimation(rot);
//    }
//
//    private class DisplayNextView implements Animation.AnimationListener {
//
//        private float mid;
//        private float end;
//        private float depth;
//        private View view;
//        private View frontView, backView;
//
//        public DisplayNextView(View view, View frontView, View backView,float mid, float end, float depth) {
//            this.mid = mid;
//            this.end = end;
//            this.depth = depth;
//            this.view = view;
//            this.frontView = frontView;
//            this.backView = backView;
//        }
//
//        @Override
//        public void onAnimationStart(Animation animation) {
//
//        }
//
//        @Override
//        public void onAnimationEnd(Animation animation) {
//
//            view.post(new Runnable() {
//                @Override
//                public void run() {
//
//                    if(isFront){
//                        frontView.setVisibility(View.GONE);
//                        backView.setVisibility(View.VISIBLE);
//                        isFront = false;
//
//                    }else{
//                        frontView.setVisibility(View.VISIBLE);
//                        backView.setVisibility(View.GONE);
//                        isFront = true;
//                    }
//
////                    Rotate3dAnimation rot = new Rotate3dAnimation(mid, end, centerX, centerY, depth, true);
////                    rot.setDuration(DURATION);
////                    rot.setInterpolator(new AccelerateInterpolator());
////                    view.startAnimation(rot);
//
//                }
//            });
//
//        }
//
//        @Override
//        public void onAnimationRepeat(Animation animation) {
//
//        }
//    }
//}

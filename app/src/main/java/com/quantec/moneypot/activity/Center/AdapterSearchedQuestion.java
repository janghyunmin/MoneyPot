package com.quantec.moneypot.activity.center;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.quantec.moneypot.R;

import java.util.ArrayList;

public class AdapterSearchedQuestion extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int EMPTY = 0;
    private final int ITEM = 1;

    ArrayList<ModelQuestion> modelQuestions;
    Context context;

    public AdapterSearchedQuestion(ArrayList<ModelQuestion> modelQuestions, Context context) {
        this.modelQuestions = modelQuestions;
        this.context = context;
    }

    private EmptyInquiryBt emptyInquiryBt;
    public interface EmptyInquiryBt{
        public void onClick(int position);
    }

    public void setEmptyInquiryBt(EmptyInquiryBt emptyInquiryBt) {
        this.emptyInquiryBt = emptyInquiryBt;
    }

    private SearchedQuestionClick searchedQuestionClick;
    public interface SearchedQuestionClick{
        public void onClick(int position);
    }

    public void setSearchedQuestionClick(SearchedQuestionClick searchedQuestionClick) {
        this.searchedQuestionClick = searchedQuestionClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == EMPTY) {
            return new SearchedQuestionEmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_searchedquestion_empty, parent, false));
        } else {
            return new SearchedQuestionViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_searchedquestion_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof SearchedQuestionViewHolder){
            ((SearchedQuestionViewHolder)holder).title.setText("Q. "+modelQuestions.get(position).getTitle());

            ((SearchedQuestionViewHolder)holder).linearItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(searchedQuestionClick != null){
                        searchedQuestionClick.onClick(position);
                    }
                }
            });
        }
        if(holder instanceof SearchedQuestionEmptyViewHolder){
            ((SearchedQuestionEmptyViewHolder)holder).inquiryBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(emptyInquiryBt != null){
                        emptyInquiryBt.onClick(position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return modelQuestions.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(modelQuestions.get(0).isEmpty()){
            return EMPTY;
        }else{
            return ITEM;
        }
    }

    public class SearchedQuestionEmptyViewHolder extends RecyclerView.ViewHolder {

        TextView inquiryBt;

        public SearchedQuestionEmptyViewHolder(@NonNull View itemView) {
            super(itemView);

            inquiryBt = itemView.findViewById(R.id.inquiryBt);
        }
    }

    public class SearchedQuestionViewHolder extends RecyclerView.ViewHolder{

        private TextView title;
        ConstraintLayout linearItem;

        public SearchedQuestionViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            linearItem = itemView.findViewById(R.id.linearItem);
        }
    }
}


//public class AdapterSearchedQuestion extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
//
//    private final int EMPTY = 0;
//    private final int ITEM = 1;
//
//    ArrayList<ModelQuestion> modelQuestions;
//    Context context;
//
//
//    // Item의 클릭 상태를 저장할 array 객체
//    private SparseBooleanArray selectedItems = new SparseBooleanArray();
//    // 직전에 클릭됐던 Item의 position
//    private int prePosition = -1;
//
//    public AdapterSearchedQuestion(ArrayList<ModelQuestion> modelQuestions, Context context) {
//        this.modelQuestions = modelQuestions;
//        this.context = context;
//    }
//
//    private EmptyInquiryBt emptyInquiryBt;
//    public interface EmptyInquiryBt{
//        public void onClick(int position);
//    }
//
//    public void setEmptyInquiryBt(EmptyInquiryBt emptyInquiryBt) {
//        this.emptyInquiryBt = emptyInquiryBt;
//    }
//
//    @NonNull
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        if (viewType == EMPTY) {
//            return new SearchedQuestionEmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_searchedquestion_empty, parent, false));
//        } else {
//            return new SearchedQuestionViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_searchedquestion_item, parent, false));
//        }
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        if(holder instanceof SearchedQuestionViewHolder){
//            ((SearchedQuestionViewHolder)holder).onBind(modelQuestions.get(position), position);
//        }
//        if(holder instanceof SearchedQuestionEmptyViewHolder){
//            ((SearchedQuestionEmptyViewHolder)holder).inquiryBt.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(emptyInquiryBt != null){
//                        emptyInquiryBt.onClick(position);
//                    }
//                }
//            });
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return modelQuestions.size();
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        if(modelQuestions.get(0).isEmpty()){
//            return EMPTY;
//        }else{
//            return ITEM;
//        }
//    }
//
//    public class SearchedQuestionEmptyViewHolder extends RecyclerView.ViewHolder {
//
//        TextView inquiryBt;
//
//        public SearchedQuestionEmptyViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            inquiryBt = itemView.findViewById(R.id.inquiryBt);
//        }
//    }
//
//    public class SearchedQuestionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
//
//        private TextView title, myContents;
//        LinearLayout layout2;
//        ImageView arrowImg;
//        private ModelQuestion data;
//        private int position;
//
//        public SearchedQuestionViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            title = itemView.findViewById(R.id.title);
//            myContents = itemView.findViewById(R.id.myContents);
//            layout2 = itemView.findViewById(R.id.layout2);
//            arrowImg = itemView.findViewById(R.id.arrowImg);
//        }
//
//        void onBind(ModelQuestion data, int position) {
//            this.data = data;
//            this.position = position;
//
//            title.setText("Q. "+data.getTitle());
//            myContents.setText(data.getDesc());
//
//            changeVisibility(selectedItems.get(position));
//
//            itemView.setOnClickListener(this);
//        }
//
//        @Override
//        public void onClick(View v) {
//            switch (v.getId()) {
//                case R.id.linearItem:
//                    if (selectedItems.get(position)) {
//                        // 펼쳐진 Item을 클릭 시
//                        selectedItems.delete(position);
//                    } else {
//                        // 직전의 클릭됐던 Item의 클릭상태를 지움
//                        selectedItems.delete(prePosition);
//                        // 클릭한 Item의 position을 저장
//                        selectedItems.put(position, true);
//                    }
//                    // 해당 포지션의 변화를 알림
//                    if (prePosition != -1) notifyItemChanged(prePosition);
//                    notifyItemChanged(position);
//                    // 클릭된 position 저장
//                    prePosition = position;
//                    break;
//            }
//        }
//
//        /**
//         * 클릭된 Item의 상태 변경
//         * @param isExpanded Item을 펼칠 것인지 여부
//         */
//        private void changeVisibility(final boolean isExpanded) {
//            // height 값을 dp로 지정해서 넣고싶으면 아래 소스를 이용
//
//            if(isExpanded){
//                arrowImg.setBackgroundResource(R.drawable.btn_arrow_up_whitegray);
//            }else{
//                arrowImg.setBackgroundResource(R.drawable.btn_arrow_down_whitegray);
//            }
//
//            layout2.measure(View.MeasureSpec.UNSPECIFIED , View.MeasureSpec.UNSPECIFIED );
//            int height = layout2.getMeasuredHeight();
//
//            // ValueAnimator.ofInt(int... values)는 View가 변할 값을 지정, 인자는 int 배열
//            ValueAnimator va = isExpanded ? ValueAnimator.ofInt(0, height) : ValueAnimator.ofInt(height, 0);
//            // Animation이 실행되는 시간, n/1000초
//            va.setDuration(800);
//            va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                @Override
//                public void onAnimationUpdate(ValueAnimator animation) {
//                    // value는 height 값
//                    int value = (int) animation.getAnimatedValue();
//
//                    // imageView의 높이 변경
//                    layout2.getLayoutParams().height = value;
//                    layout2.requestLayout();
//                    // imageView가 실제로 사라지게하는 부분
//                    layout2.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
//                }
//            });
//            // Animation start
//            va.start();
//        }
//    }
//}

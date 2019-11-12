package com.quantec.moneypot.activity.Center.Adapter;

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
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.quantec.moneypot.datamodel.dmodel.ModelUseGuideList;
import com.quantec.moneypot.R;

public class AdapterUseGuide extends RecyclerView.Adapter<AdapterUseGuide.UseGuideViewHolder> {

    ArrayList<ModelUseGuideList> useGuideLists;
    Context context;

    public AdapterUseGuide(ArrayList<ModelUseGuideList> useGuideLists, Context context) {
        this.useGuideLists = useGuideLists;
        this.context = context;
    }

    // Item의 클릭 상태를 저장할 array 객체
    private SparseBooleanArray selectedItems = new SparseBooleanArray();
    // 직전에 클릭됐던 Item의 position
    private int prePosition = -1;


    private UseGuideClick useGuideClick;
    public interface UseGuideClick {
        public void onClick(int position);
    }

    public void setUseGuideClick(UseGuideClick useGuideClick) {
        this.useGuideClick = useGuideClick;
    }

    @NonNull
    @Override
    public UseGuideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fguseguide, parent, false);
        return new UseGuideViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UseGuideViewHolder holder, int position) {
        holder.onBind(useGuideLists.get(position), position);
    }


    @Override
    public int getItemCount() {
        return useGuideLists.size();
    }

    public class UseGuideViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textView1;
        private TextView textView2;
        LinearLayout layout2;
        ImageView arrowImg;
        private ModelUseGuideList data;
        private int position;

        UseGuideViewHolder(View itemView) {
            super(itemView);

            textView1 = itemView.findViewById(R.id.textView1);
            textView2 = itemView.findViewById(R.id.textView2);
            layout2 = itemView.findViewById(R.id.layout2);
            arrowImg = itemView.findViewById(R.id.arrowImg);
        }

        void onBind(ModelUseGuideList data, int position) {
            this.data = data;
            this.position = position;

            textView1.setText(data.getTitle());
            textView2.setText(data.getContent());

            changeVisibility(selectedItems.get(position));

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.linearItem:
                    if (selectedItems.get(position)) {
                        // 펼쳐진 Item을 클릭 시
                        selectedItems.delete(position);
                    } else {
                        // 직전의 클릭됐던 Item의 클릭상태를 지움
                        selectedItems.delete(prePosition);
                        // 클릭한 Item의 position을 저장
                        selectedItems.put(position, true);
                    }
                    // 해당 포지션의 변화를 알림
                    if (prePosition != -1) notifyItemChanged(prePosition);
                    notifyItemChanged(position);
                    // 클릭된 position 저장
                    prePosition = position;
                    break;
            }
        }

        /**
         * 클릭된 Item의 상태 변경
         * @param isExpanded Item을 펼칠 것인지 여부
         */
        private void changeVisibility(final boolean isExpanded) {
            // height 값을 dp로 지정해서 넣고싶으면 아래 소스를 이용

            if(isExpanded){
                arrowImg.setBackgroundResource(R.drawable.ic_arrow_bottom);
            }else{
                arrowImg.setBackgroundResource(R.drawable.ic_arrow_down_gray);
            }

            layout2.measure(View.MeasureSpec.UNSPECIFIED , View.MeasureSpec.UNSPECIFIED );
            int height = layout2.getMeasuredHeight();

            // ValueAnimator.ofInt(int... values)는 View가 변할 값을 지정, 인자는 int 배열
            ValueAnimator va = isExpanded ? ValueAnimator.ofInt(0, height) : ValueAnimator.ofInt(height, 0);
            // Animation이 실행되는 시간, n/1000초
            va.setDuration(600);
            va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    // value는 height 값
                    int value = (int) animation.getAnimatedValue();

                    // imageView의 높이 변경
                    layout2.getLayoutParams().height = value;
                    layout2.requestLayout();
                    // imageView가 실제로 사라지게하는 부분
                    layout2.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
                }
            });
            // Animation start
            va.start();

        }
    }
}
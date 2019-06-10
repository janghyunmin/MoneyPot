package quantec.com.moneypot.Activity.Center.Adapter;

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

import quantec.com.moneypot.ModelCommon.dModel.ModelMyInquiryList;
import quantec.com.moneypot.R;

public class AdapterMyInquiry extends RecyclerView.Adapter<AdapterMyInquiry.MyInquiryViewHolder> {

    ArrayList<ModelMyInquiryList> myInquiryLists;
    Context context;

    public AdapterMyInquiry(ArrayList<ModelMyInquiryList> myInquiryLists, Context context) {
        this.myInquiryLists = myInquiryLists;
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
    public MyInquiryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_myinquiry, parent, false);
        return new MyInquiryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyInquiryViewHolder holder, int position) {
        holder.onBind(myInquiryLists.get(position), position);
    }

    @Override
    public int getItemCount() {
        return myInquiryLists.size();
    }

    public class MyInquiryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView title, myContents, ansContents;
        LinearLayout layout2;
        ImageView arrowImg;
        private ModelMyInquiryList data;
        private int position;

        MyInquiryViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            myContents = itemView.findViewById(R.id.myContents);
            ansContents = itemView.findViewById(R.id.ansContents);
            layout2 = itemView.findViewById(R.id.layout2);
            arrowImg = itemView.findViewById(R.id.arrowImg);
        }

        void onBind(ModelMyInquiryList data, int position) {
            this.data = data;
            this.position = position;

            title.setText(data.getTitle());
            myContents.setText(data.getMyContents());
            ansContents.setText(data.getAnsContents());

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
            va.setDuration(800);
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

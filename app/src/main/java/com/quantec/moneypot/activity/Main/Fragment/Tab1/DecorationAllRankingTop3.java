package com.quantec.moneypot.activity.Main.Fragment.Tab1;

import android.content.Context;
import android.graphics.Rect;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DecorationAllRankingTop3 extends RecyclerView.ItemDecoration {

    private int size;

    public DecorationAllRankingTop3(Context context, int divSize){

        size = dpToPx(context, divSize);

    }

    private int dpToPx(Context context, int dp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1)
            outRect.right = size;
    }
}

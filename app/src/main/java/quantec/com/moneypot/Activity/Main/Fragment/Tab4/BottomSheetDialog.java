package quantec.com.moneypot.Activity.Main.Fragment.Tab4;

import android.app.Dialog;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import quantec.com.moneypot.R;

public class BottomSheetDialog extends BottomSheetDialogFragment implements View.OnClickListener{

    TextView saveBt, title, rate, per;
    String potName;
    double potRate;

    public BottomSheetDialog newInstance(String potName, double potRate) {
        BottomSheetDialog fragment = new BottomSheetDialog();
        this.potName = potName;
        this.potRate = potRate;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {

        View contentView = View.inflate(getContext(), R.layout.bottom_sheet, null);
        dialog.setContentView(contentView);
        ((View) contentView.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));

        saveBt = contentView.findViewById(R.id.saveBt);
        saveBt.setFocusableInTouchMode(true);
        saveBt.requestFocus();

        title = contentView.findViewById(R.id.title);
        title.setText(potName);

        rate = contentView.findViewById(R.id.rate);
        per = contentView.findViewById(R.id.per);

        rate.setText(String.valueOf(potRate));
        if(potRate < 0 ){
            rate.setTextColor(getResources().getColor(R.color.blue_color));
            per.setTextColor(getResources().getColor(R.color.blue_color));
        }
        else{
            rate.setTextColor(getResources().getColor(R.color.red_text_color));
            per.setTextColor(getResources().getColor(R.color.red_text_color));
        }

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }


        View parent = (View) contentView.getParent();
        parent.setFitsSystemWindows(true);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(parent);
        contentView.measure(0, 0);
        DisplayMetrics displaymetrics = new DisplayMetrics();        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenHeight = displaymetrics.heightPixels;
        bottomSheetBehavior.setPeekHeight(screenHeight);

        if (params.getBehavior() instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior)params.getBehavior()).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }

        params.height = screenHeight;
        parent.setLayoutParams(params);

    }

    @Override
    public void onClick(View v) {

    }


    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {

        }
    };

}
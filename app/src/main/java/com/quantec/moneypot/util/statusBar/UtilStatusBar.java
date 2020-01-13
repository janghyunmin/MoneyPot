package com.quantec.moneypot.util.statusBar;

import android.app.Activity;
import android.os.Build;

import androidx.core.content.ContextCompat;

import com.quantec.moneypot.R;

public class UtilStatusBar {
    public enum StatusBarColorType {
        STATUS_BAR(R.drawable.custom_back);

        private int backgroundColorId;

        StatusBarColorType(int backgroundColorId){
            this.backgroundColorId = backgroundColorId;
        }

        public int getBackgroundColorId() {
            return backgroundColorId;
        }
    }

    public static void setStatusBarColor(Activity activity, StatusBarColorType colorType) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setStatusBarColor(ContextCompat.getColor(activity, colorType.getBackgroundColorId()));
        }
    }
}
